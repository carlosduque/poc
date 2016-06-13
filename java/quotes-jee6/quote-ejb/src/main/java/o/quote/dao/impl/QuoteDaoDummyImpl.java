package o.quote.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import o.quote.dao.QuoteDao;
import o.quote.model.Quote;

public class QuoteDaoDummyImpl implements QuoteDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(QuoteDaoDummyImpl.class);

    private List<Quote> quotes = null;

    public QuoteDaoDummyImpl() {
        quotes = new ArrayList<Quote>();
        quotes.add(new Quote(1, "Aut viam inveniam aut faciam", "Unknown"));
        quotes.add(new Quote(2, "Yabba dabba doo", "Fred Flintstone"));
        quotes.add(new Quote(3, "Scooby dooby doo", "Scooby Doo"));
    }

    @Override
    public long save(Quote quote) {
        LOGGER.debug("**********************************dao*****");
        LOGGER.debug("save(): {}", quote);
        LOGGER.debug("**********************************dao*****");
        quotes.add(quote);

        return quotes.indexOf(quote);
    }

    @Override
    public Quote findById(long id){
        LOGGER.debug("**********************************dao*****");
        LOGGER.debug("findById(): {}", id);
        LOGGER.debug("**********************************dao*****");
        return quotes.get((int) id);
    }

    @Override
    public void saveOrUpdate(Quote quote){
        LOGGER.debug("**********************************dao*****");
        LOGGER.debug("saveOrUpdate(): {}", quote);
        LOGGER.debug("**********************************dao*****");
        quotes.add(quote);
    }

    @Override
    public void delete(long id) {
        LOGGER.debug("**********************************dao*****");
        LOGGER.debug("delete(): {}", id);
        LOGGER.debug("**********************************dao*****");
        quotes.remove(id);
    }

    @Override
    public List<Quote> findAll() {
        LOGGER.debug("**********************************dao*****");
        LOGGER.debug("findAll()");
        LOGGER.debug("found {}", quotes.size());
        LOGGER.debug("**********************************dao*****");

        return quotes;
    }
}
