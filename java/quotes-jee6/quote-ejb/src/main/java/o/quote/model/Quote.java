package o.quote.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import o.quote.common.dto.QuoteDto;

public class Quote {
    private static final Logger LOGGER = LoggerFactory.getLogger(Quote.class);
    private long id;
    private String quote;
    private String author;

    public static Quote valueOf(QuoteDto dto) {
        LOGGER.debug("Converting {} ", dto);
        Quote quote = new Quote();
        if(dto.getId() > 0)
            quote.setId(dto.getId());
        if(dto.getQuote() != null && !dto.getQuote().isEmpty())
            quote.setQuote(dto.getQuote());
        if(dto.getAuthor() != null && !dto.getAuthor().isEmpty())
            quote.setAuthor(dto.getAuthor());
        LOGGER.debug("        --> ", quote);
        return quote;
    }

    public Quote() {
    }

    public Quote(long newId, String newQuote, String newAuthor) {
        id = newId;
        quote = newQuote;
        author = newAuthor;
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }
    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }
    /**
     * @return the quote
     */
    public String getQuote() {
        return quote;
    }
    /**
     * @param f the quote to set
     */
    public void setQuote(String f) {
        this.quote = f;
    }
    /**
     * @return the author
     */
    public String getAuthor() {
        return author;
    }
    /**
     * @param author the last name to set
     */
    public void setAuthor(String a) {
        this.author = a;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Quote [id=");
        builder.append(id);
        builder.append(", quote=");
        builder.append(quote);
        builder.append(", author=");
        builder.append(author);
        builder.append("]");
        return builder.toString();
    }

    public QuoteDto getQuoteDto() {
        QuoteDto dto = new QuoteDto();
        dto.setId(this.id);
        dto.setQuote(this.quote);
        dto.setAuthor(this.author);

        return dto;
    }
}
