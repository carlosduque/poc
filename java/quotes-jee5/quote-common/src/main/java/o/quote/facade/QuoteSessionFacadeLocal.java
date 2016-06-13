package o.quote.facade;

import java.util.List;

import javax.ejb.Local;

import o.quote.common.dto.QuoteDto;

@Local
public interface QuoteSessionFacadeLocal {
    void create(QuoteDto dto);
    QuoteDto read(long id);
    void update(QuoteDto dto);
    void delete(long id);
    List<QuoteDto> findAll();
    void process(String messageBody);
}
