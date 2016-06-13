package o.quote.facade;

import java.util.List;

import javax.ejb.Remote;

import o.quote.common.dto.QuoteDto;

@Remote
public interface QuoteSessionFacadeRemote {
    void create(QuoteDto dto);
    QuoteDto read(long id);
    void update(QuoteDto dto);
    void delete(long id);
    List<QuoteDto> findAll();
    void process(String messageBody);
}
