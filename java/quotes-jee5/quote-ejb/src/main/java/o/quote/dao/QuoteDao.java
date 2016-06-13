package o.quote.dao;

import java.util.List;
import o.quote.model.Quote;

public interface QuoteDao {
    public void save(Quote quote);
    public Quote findBy(long id);
    public void edit(Quote quote);
    public void delete(long id);

    public List<Quote> findAll();
}
