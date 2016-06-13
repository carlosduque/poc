package o.quote.ejb;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.inject.Inject;
import o.quote.dao.QuoteDao;
import o.quote.model.Quote;
import o.quote.common.dto.QuoteDto;
import o.quote.facade.QuoteSessionFacadeLocal;
import o.quote.facade.QuoteSessionFacadeRemote;

/**
* This is an EJB 3 stateless session bean, configured using an EJB 3
* deployment descriptor as opposed to using annotations.
* This EJB has 1 business interface for remote and local
*/
public class QuoteSessionBean implements QuoteSessionFacadeRemote, QuoteSessionFacadeLocal {
    private static final Logger LOGGER = LoggerFactory.getLogger(QuoteSessionBean.class);

    @Inject
    private QuoteDao quoteDao;

    public QuoteSessionBean() { }

    @Override
    public long create(QuoteDto quoteDto) {
        long id = 0;
        Quote quote = new Quote();
        quote.setQuote(quoteDto.getQuote());
        quote.setAuthor(quoteDto.getAuthor());
        LOGGER.debug("----------------------------------service-");
        LOGGER.debug("Saving to database: {}", quote);
        LOGGER.debug("----------------------------------service-");
        id = quoteDao.save(quote);

        return id;
    }

    @Override
    public QuoteDto read(long id) {
        LOGGER.debug("----------------------------------service-");
        LOGGER.debug("Searching a quote with  id: {}", id);
        LOGGER.debug("----------------------------------service-");
        Quote q = quoteDao.findById(id);
        return q.getQuoteDto();
    }

    @Override
    public void update(QuoteDto quoteDto) {
        Quote quote = Quote.valueOf(quoteDto);
        LOGGER.debug("----------------------------------service-");
        LOGGER.debug("Updating a quote: {}", quote);
        LOGGER.debug("----------------------------------service-");
        quoteDao.saveOrUpdate(quote);
    }


    @Override
    public void delete(long id) {
        LOGGER.debug("----------------------------------service-");
        LOGGER.debug("Deleting a quote: {}", id);
        LOGGER.debug("----------------------------------service-");
        quoteDao.delete(id);
    }

    @Override
    public List<QuoteDto> findAll() {
        LOGGER.debug("----------------------------------service-");
        LOGGER.debug("Retrieving quotes");
        LOGGER.debug("----------------------------------service-");
        List<Quote> quotes = quoteDao.findAll();
        List<QuoteDto> dtos = new ArrayList<QuoteDto>();

        QuoteDto quoteDto = null;
        for(Quote quote : quotes){
            quoteDto = quote.getQuoteDto();
            dtos.add(quoteDto);
        }

        return dtos;
    }
}
