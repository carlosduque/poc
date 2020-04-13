package o;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import o.Serializer;
import o.avro.AvroSerializer;
import o.beans.Author;
import o.beans.Book;

public class Main {
    public static void main(String[] args) throws IOException {
        Serializer s = new AvroSerializer("./src/main/resources/author.avsc");

        Author author = new Author("Bruce", "Wayne", new Date());
        author.addBook(new Book("Black", 377)).addBook(new Book("Crow", 443));
        System.out.println(author);

        s.serialize(author, new File("author.avro"));
        author = null;

        author = s.deserialize(new File("author.avro"));
        System.out.println("Avro read author: " + author);
    }

}
