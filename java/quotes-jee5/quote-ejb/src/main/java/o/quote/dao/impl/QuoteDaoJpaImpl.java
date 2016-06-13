package o.quote.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import o.quote.dao.QuoteDao;
import o.quote.model.Quote;

public class QuoteDaoJpaImpl implements QuoteDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(QuoteDaoJpaImpl.class);

    private List<Quote> quotes = null;
    private EntityManager entityManager;

    public QuoteDaoJpaImpl(EntityManager em) {
        entityManager = em;
    }

    @Override
    public void save(Quote quote) {
        LOGGER.debug(" | dao:save:quote: {}", quote);
        entityManager.persist(quote);
    }

    @Override
    public Quote findBy(long id) {
        LOGGER.debug(" | dao:findBy:id: {}", id);
        return entityManager.find(Quote.class, id);
    }

    @Override
    public void edit(Quote quote) {
        LOGGER.debug(" | dao:edit:quote: {} ", quote);
        //This 'find' seems to be necessary only for Weblogic app server
        Quote q = entityManager.find(Quote.class, quote.getId());
        q.setId(quote.getId());
        q.setQuote(quote.getQuote());
        q.setAuthor(quote.getAuthor());

        entityManager.merge(q);
    }

    @Override
    public void delete(long id) {
        LOGGER.debug(" | dao:delete:id: {}", id);
        entityManager.remove(findBy(id));
    }

    @Override
    public List<Quote> findAll() {
        LOGGER.debug(" | dao:findAll");
        //Query query = entityManager.createQuery("SELECT q FROM Quote q");
        Query query = entityManager.createNamedQuery("Quote.findAll");
        //Query query = entityManager.createNamedQuery("Quote.findQuotesSP"); //Calls to stored procedures don't work in JPA1.0
        return query.getResultList();
    }
}
