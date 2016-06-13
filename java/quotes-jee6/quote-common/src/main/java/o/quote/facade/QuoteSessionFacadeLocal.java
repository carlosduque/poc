package o.quote.facade;

import java.util.List;

import o.quote.common.dto.QuoteDto;

public interface QuoteSessionFacadeLocal {
    long create(QuoteDto dto);
    QuoteDto read(long id);
    void update(QuoteDto dto);
    void delete(long id);
    List<QuoteDto> findAll();
}
