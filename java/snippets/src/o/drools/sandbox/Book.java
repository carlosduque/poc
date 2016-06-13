package o.drools.sandbox;

import java.util.Date;

public class Book {
    private String title;
    private Author author;
    private Date pubDate;
    private BookType type;
    private int rating;

    public Book() { }

    public Book(String pTitle, BookType pType, int pRating, Author pAuthor, Date pPubDate) {
        super();
        this.title = pTitle;
        this.type = pType;
        this.rating = pRating;
        this.author = pAuthor;
        this.pubDate = pPubDate;
    }

    public final String getTitle() {
        return title;
    }

    public final void setTitle(final String pTitle) {
        this.title = pTitle;
    }
    public final BookType getType() {
        return type;
    }

    public final void setType(final BookType pType) {
        this.type = pType;
    }

    public final int getRating() {
        return rating;
    }

    public final void setRating(final int pRating) {
        if(pRating < 0 || pRating > 10)
            throw new IllegalArgumentException();
        this.rating = pRating;
    }

    public final Author getAuthor() {
        return author;
    }

    public final void setAuthor(final Author pAuthor) {
        this.author = pAuthor;
    }

    public final Date getPubDate() {
        return pubDate;
    }

    public final void setPubDate(final Date pPubDate) {
        this.pubDate = pPubDate;
    }

}
