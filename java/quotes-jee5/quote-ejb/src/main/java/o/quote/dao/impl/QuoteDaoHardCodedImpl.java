package o.quote.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import o.quote.dao.QuoteDao;
import o.quote.model.Quote;

public class QuoteDaoHardCodedImpl implements QuoteDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(QuoteDaoHardCodedImpl.class);

    private List<Quote> quotes = null;

    public QuoteDaoHardCodedImpl() {
        quotes = new ArrayList<Quote>();
        quotes.add(new Quote(1, "Aut viam inveniam aut faciam", "Unknown"));
        quotes.add(new Quote(2, "Yabba dabba doo", "Fred Flintstone"));
        quotes.add(new Quote(3, "Scooby dooby doo", "Scooby Doo"));
    }

    @Override
    public List<Quote> findAll() {
        LOGGER.info(" * dao");
        return quotes;
    }

    @Override
    public void save(Quote quote) {
        LOGGER.info(" | dao:save");
        quotes.add(quote);
    }

    @Override
    public Quote findBy(long id) {
        LOGGER.info(" | dao:findById");
        throw new UnsupportedOperationException();
    }

    @Override
    public void edit(Quote quote){
        LOGGER.info(" | dao:edit");
        quotes.add(quote);
    }

    @Override
    public void delete(long id) {
        LOGGER.info(" | dao:delete");
        quotes.remove(findBy(id));
    }

}
