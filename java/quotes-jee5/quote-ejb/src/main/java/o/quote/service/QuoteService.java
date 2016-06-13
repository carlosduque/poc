package o.quote.service;

import java.util.List;

import javax.jms.ConnectionFactory;
import javax.jms.Queue;

import o.quote.dao.QuoteDao;
import o.quote.common.dto.QuoteDto;

public interface QuoteService {
    void create(QuoteDto dto);
    QuoteDto read(long id);
    void update(QuoteDto dto);
    void delete(long id);
    List<QuoteDto> findAll();
    void process(String messageBody);

    void setQuoteDao(QuoteDao quoteDao);
    void setQuoteConnectionFactory(ConnectionFactory connectionFactory);
    void setQuoteQueue(Queue queue);
}
