package o.quote.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.NamedQuery;
import javax.persistence.NamedQueries;
**/
import o.quote.common.dto.QuoteDto;
/**
@Entity
@Table(name="Quotes")

@NamedQueries({
    @NamedQuery(name="Quote.findAll", query="SELECT q FROM Quote q"),
    //@NamedQuery(name="Quote.findQuotesSP", query="{ call find_quotes(?) }"), //Calls to stored procedures don't work in JPA1.0
    @NamedQuery(name="Quote.findByAuthor", query="SELECT q FROM Quote q WHERE q.author = :author")
})
**/
public class Quote {
    private static final Logger LOGGER = LoggerFactory.getLogger(Quote.class);

    //@Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    //@Column(name="q_id")
    private long id;
    //@Column(name="quote")
    private String quote;
    //@Column (name="author")
    private String author;

    public static Quote valueOf(QuoteDto dto) {
        LOGGER.debug("Converting dto: {} ", dto);
        Quote quote = new Quote();

        quote.setId(dto.getId());
        if(dto.getQuote() != null && !dto.getQuote().isEmpty()) {
            quote.setQuote(dto.getQuote());
        }
        if(dto.getAuthor() != null && !dto.getAuthor().isEmpty()) {
            quote.setAuthor(dto.getAuthor());
        }

        return quote;
    }

    public Quote() { }

    public Quote(long newId, String newQuote, String newAuthor) {
        id = newId;
        quote = newQuote;
        author = newAuthor;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String q) {
        this.quote = q;
    }

    public String getAuthor() {
        return author;
    }

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

    public QuoteDto asDto() {
        QuoteDto dto = new QuoteDto();
        dto.setId(this.id);
        dto.setQuote(this.quote);
        dto.setAuthor(this.author);

        return dto;
    }
}
