package o.drools.sandbox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Library {

    private String name;
    private Map<String, Book> books = new HashMap<String, Book>();
    
    public Library(final String pName) {
        super();
        this.name = pName;
    }
    
    public final String getName() {
        return name;
    }

    public void addBook(final Book pBook) {
        this.books.put(pBook.getTitle(), pBook);
    }
    
    public Book getBookByTitle(final String pTitle) {
        Book book = (Book) this.books.get(pTitle);
        return(book);
    }
    
    public List<Book> getAllBooks() {
        List<Book> bookList = new ArrayList<Book>();
        bookList.addAll(this.books.values());
        return(bookList);
    }

}
