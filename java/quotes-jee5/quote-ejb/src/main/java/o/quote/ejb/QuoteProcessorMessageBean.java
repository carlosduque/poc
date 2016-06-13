package o.quote.ejb;

import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.MessageDriven;
import javax.ejb.MessageDrivenContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import o.quote.common.dto.QuoteDto;
import o.quote.dao.impl.QuoteDaoHibernateImpl;
import o.quote.service.QuoteService;
import o.quote.service.impl.QuoteServiceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@MessageDriven(name="QuoteProcessorMessage", mappedName="jms/quotesQueue")
public class QuoteProcessorMessageBean implements MessageListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(QuoteProcessorMessageBean.class);

    @Resource
    private MessageDrivenContext context;

    private QuoteService service;

    @PostConstruct
    public void initialize() {
        service = new QuoteServiceImpl();
        service.setQuoteDao(new QuoteDaoHibernateImpl());
    }

    @PreDestroy
    public void cleanup() {
        LOGGER.debug("mdb cleanup");
    }

    @Override
    public void onMessage(Message inMessage) {
        TextMessage message = null;
        String contents = null;
        QuoteDto dto = null;
        try {
            if (inMessage instanceof TextMessage) {
                message = (TextMessage) inMessage;
                contents = message.getText();
                LOGGER.info("mdb:received: " + contents);
                String[] tokens = contents.split(Pattern.quote("|"));
                if (tokens.length != 3) {
                    LOGGER.error("mdb:wrong number of tokens: " + tokens.length +", should be 3.");
                } else {
                    dto = new QuoteDto(Integer.parseInt(tokens[0]), tokens[2], tokens[1]);
                    service.create(dto);
                }
            } else {
                LOGGER.error("mdb:wrong type: " + inMessage.getClass().getName());
            }
        } catch (JMSException e) {
            LOGGER.error("message processing failed. " + e);
            context.setRollbackOnly();
        }

    }

}
