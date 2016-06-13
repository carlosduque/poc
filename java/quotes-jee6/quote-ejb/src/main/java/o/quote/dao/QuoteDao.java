package o.quote.dao;

import java.util.List;
import o.quote.model.Quote;

public interface QuoteDao {
    public long save(Quote quote);
    public Quote findById(long id);
    public void saveOrUpdate(Quote quote);
    public void delete(long id);
    public List<Quote> findAll();
}
