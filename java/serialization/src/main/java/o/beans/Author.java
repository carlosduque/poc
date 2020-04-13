package o.beans;

import java.util.Date;
import java.util.Set;
import java.util.HashSet;

import o.beans.Person;

public class Author extends Person {

    Set<Book> books = new HashSet<Book>();

    public Author(String name, String lastname, Date dob) {
        super(name, lastname, dob);
    }

    public Author addBook(Book book) {
        if (null == book) return this;
        this.books.add(book);
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("Author[" + super.toString()
                + " ::books->");
        for (Book b : books)
            sb.append(b);
        sb.append("]");

        return sb.toString();
    }

}
