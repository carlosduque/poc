package o.quote.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import o.quote.common.dto.QuoteDto;
import o.quote.dao.QuoteDao;
import o.quote.model.Quote;
import o.quote.service.QuoteService;

public class QuoteServiceImpl implements QuoteService {

    private static final Logger LOGGER = LoggerFactory.getLogger(QuoteServiceImpl.class);

    private QuoteDao dao;
    private ConnectionFactory cf;
    private Queue q;

    @Override
    public void create(QuoteDto dto) {
        LOGGER.debug("service:create");
        Quote q = Quote.valueOf(dto);
        dao.save(q);
    }

    @Override
    public QuoteDto read(long id) {
        LOGGER.debug("service:read");
        Quote q = dao.findBy(id);
        return q.asDto();
    }

    @Override
    public void update(QuoteDto dto) {
        LOGGER.debug("service:update");
        Quote q = Quote.valueOf(dto);
        LOGGER.debug("service:update: {} ", q);
        dao.edit(q);
    }

    @Override
    public void delete(long id) {
        LOGGER.debug("service:delete");
        dao.delete(id);
    }

    @Override
    public List<QuoteDto> findAll() {
        LOGGER.debug("service:findAll");
        List<Quote> quotes = dao.findAll();
        List<QuoteDto> dtos = new ArrayList<QuoteDto>();

        QuoteDto quoteDto = null;
        for(Quote quote : quotes){
            quoteDto = quote.asDto();
            dtos.add(quoteDto);
        }

        return dtos;
    }

    @Override
    public void process(String messageBody) {
        LOGGER.debug("service:process[" + messageBody + "]");
        boolean transacted = false;
        Session jmsSession = null;
        Connection jmsConn = null;
        MessageProducer producer = null;
        TextMessage message = null;
        try {
            jmsConn = cf.createConnection();
            jmsSession = jmsConn.createSession(transacted, Session.AUTO_ACKNOWLEDGE);
            producer = jmsSession.createProducer(q);
            message = jmsSession.createTextMessage();
            message.setText(messageBody);
            producer.send(message);
        } catch (JMSException e) {
            LOGGER.error("JMS processing failed." + e);
        } finally {
            try {
                jmsSession.close();
                jmsConn.close();
            } catch (JMSException e) {
                LOGGER.error("JMS closing session or connection failed." + e);
            }
        }
        LOGGER.debug("service:process:message successfully sent.");
    }

    @Override
    public void setQuoteDao(QuoteDao quoteDao) {
        dao = quoteDao;
    }

    @Override
    public void setQuoteConnectionFactory(ConnectionFactory connectionFactory) {
        cf = connectionFactory;
    }

    @Override
    public void setQuoteQueue(Queue queue) {
        q = queue;
    }
}
