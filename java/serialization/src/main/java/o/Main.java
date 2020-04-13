package o;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import o.Serializer;
import o.avro.AvroSerializer;
import o.beans.Author;
import o.beans.Book;

public class Main {
    public static void main(String[] args) throws IOException {
        Serializer s = new AvroSerializer("./src/main/resources/author.avsc");

        List<Book> books = new ArrayList<Book>(2);
        books.add(new Book("Black", 377));
        books.add(new Book("Bat", 443));
        Author author = new Author("Bruce", "Wayne", new Date().getTime(), books);
        System.out.println(author);

        System.out.println(":::serializing");
        s.serialize(author, new File("author.avro"));
        author = null;

        System.out.println("=====================");
        author = s.deserialize(new File("author.avro"));
        System.out.println("Avro read author: " + author);
    }

}
