package o.beans;

import java.util.List;
import java.util.Random;

import o.exceptions.LowerLevelException;

public class Book {

    private int id;
    private String content;
    private String title;
    private List<Author> authors;

    public List<Author> getAuthors() {
        return authors;
    }

    public void addAuthor(Author newAuthor) {
        authors.add(newAuthor);
    }

    public Book(String title) {
        super();
        this.title = title;
        id = new Random().nextInt();
    }

    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getTitle() {
        return title;
    }

    public void throwException() throws LowerLevelException {
        throw new LowerLevelException("Something failed running Book#throwException()");
    }

    public int getId() {
        return id;
    }

}
