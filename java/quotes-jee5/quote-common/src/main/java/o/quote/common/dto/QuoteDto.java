package o.quote.common.dto;

import java.io.Serializable;

public class QuoteDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private long id;
    private String quote;
    private String author;

    public QuoteDto() {
        super();
    }

    public QuoteDto(long id, String f, String a) {
        this.id = id;
        this.quote = f;
        this.author = a;
    }

    public long getId() {
        return id;
    }

    public String getQuote() {
        return quote;
    }

    public String getAuthor() {
        return author;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setQuote(String f) {
        this.quote = f;
    }

    public void setAuthor(String a) {
        this.author = a;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("QuoteDto [id=");
        builder.append(id);
        builder.append(", quote=");
        builder.append(quote);
        builder.append(", author=");
        builder.append(author);
        builder.append("]");

        return builder.toString();
    }
}
