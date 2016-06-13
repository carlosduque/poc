package o.quote.ejb;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.jms.ConnectionFactory;
import javax.jms.Queue;
import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import o.quote.common.dto.QuoteDto;
import o.quote.dao.impl.QuoteDaoHibernateImpl;
import o.quote.facade.QuoteSessionFacadeLocal;
import o.quote.facade.QuoteSessionFacadeRemote;
import o.quote.service.QuoteService;
import o.quote.service.impl.QuoteServiceImpl;

@Stateless(name="QuoteSession", mappedName="QuoteSessionFacade")
public class QuoteSessionBean implements QuoteSessionFacadeRemote, QuoteSessionFacadeLocal {
    private static final Logger LOGGER = LoggerFactory.getLogger(QuoteSessionBean.class);

    //@PersistenceContext(unitName = "quote-jpa-unit")
    //@PersistenceContext(unitName = "quote-jpa-hibernate-unit")
    private EntityManager em;

    @Resource(mappedName="jms/quotesConnectionFactory")
    private ConnectionFactory cf;
    @Resource(mappedName="jms/quotesQueue")
    private Queue q;

    private QuoteService service;

    public QuoteSessionBean() { }

    @PostConstruct
    public void initialize() {
        service = new QuoteServiceImpl();
        //service.setQuoteDao(new QuoteDaoHardCodedImpl());
        //service.setQuoteDao(new QuoteDaoJpaImpl(em));
        service.setQuoteDao(new QuoteDaoHibernateImpl());
        service.setQuoteConnectionFactory(cf);
        service.setQuoteQueue(q);
    }

    @PreDestroy
    public void cleanup() {
        LOGGER.debug("bean cleanup");
    }

    @Override
    public void create(QuoteDto quoteDto) {
        LOGGER.info("bean:create:" + quoteDto);
        service.create(quoteDto);
    }

    @Override
    public QuoteDto read(long id) {
        LOGGER.info("bean:read:" + id);
        return service.read(id);
    }

    @Override
    public void update(QuoteDto quoteDto) {
        LOGGER.info("bean:update:" + quoteDto);
        service.update(quoteDto);
    }

    @Override
    public void delete(long id) {
        LOGGER.info("bean:delete:" + id);
        service.delete(id);
    }

    @Override
    public List<QuoteDto> findAll() {
        LOGGER.info("bean:findAll");
        return service.findAll();
    }

    @Override
    public void process(String messageBody) {
        LOGGER.info("bean:process:" + messageBody);
        service.process(messageBody);
    }

    public void setEntityManager(EntityManager entityManager) {
        em = entityManager;
    }

    public void setConnectionFactory(ConnectionFactory connectionFactory) {
        cf = connectionFactory;
    }

    public void setQueue(Queue queue) {
        q = queue;
    }
}
