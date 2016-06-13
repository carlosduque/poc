package o.quote.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Query;

import o.quote.dao.QuoteDao;
import o.quote.model.Quote;
import o.quote.util.HibernateUtil;

public class QuoteDaoHibernateImpl implements QuoteDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(QuoteDaoHibernateImpl.class);

    private List<Quote> quotes = null;
    private Session session;
    SessionFactory sessionFactory;

    public QuoteDaoHibernateImpl() {
        sessionFactory = HibernateUtil.getSessionFactory("hibernate/quotesSessionFactory");
    }

    @Override
    public void save(Quote quote) {
        LOGGER.debug("dao:save:quote: {}", quote);
        session = sessionFactory.getCurrentSession();
        session.save(quote);
        session.flush();
        session.close();
    }

    @Override
    public Quote findBy(long id) {
        LOGGER.debug("dao:findBy:id: {}", id);
        session = sessionFactory.getCurrentSession();
        Quote q = (Quote) session.get(Quote.class, id);
        return q;
    }

    @Override
    public void edit(Quote quote) {
        LOGGER.debug("dao:edit:quote: {} ", quote);
        session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(quote);
        session.flush();
        session.close();
    }

    @Override
    public void delete(long id) {
        LOGGER.debug("dao:delete:id: {}", id);
        session = sessionFactory.getCurrentSession();
        session.delete(findBy(id));
        session.flush();
        session.close();
    }

    @Override
    public List<Quote> findAll() {
        session = sessionFactory.getCurrentSession();
        //Query query = session.createQuery("from Quote as quote"); 
        //Query query = session.getNamedQuery("Quote.findAll");
        //Query query = session.getNamedQuery("Quote.fetchQuotesF"); // call a DB function
        Query query = session.getNamedQuery("Quote.findQuotesSP"); // call a DB stored proc
        LOGGER.debug("dao:query string: " + query.getQueryString());
        List<Quote> list = query.list();
        session.flush();
        session.close();
        return list;
    }
}
