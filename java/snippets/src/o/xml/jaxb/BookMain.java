package o.xml.jaxb;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "book")
//If you want you can define the order in which the fields are written
//Optional
@XmlType(propOrder = { "author", "name", "publisher", "isbn" })
class Book {

	private String name;
	private String author;
	private String publisher;
	private String isbn;

	// If you like the variable name, e.g. "name", you can easily change this
	// name for your XML-Output:
	@XmlElement(name = "title")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

} 

//This statement means that class "Bookstore.java" is the root-element of our example
@XmlRootElement(namespace = "o.xml.jaxb")
class Bookstore {

	// XmLElementWrapper generates a wrapper element around XML representation
	@XmlElementWrapper(name = "bookList")
	// XmlElement sets the name of the entities
	@XmlElement(name = "book")
	private ArrayList<BookItem> bookList;
	private String name;
	private String location;

	public void setBookList(ArrayList<BookItem> bookList) {
		this.bookList = bookList;
	}

	public ArrayList<BookItem> getBooksList() {
		return bookList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
}

public class BookMain {

	private static final String BOOKSTORE_XML = "bookstore-jaxb.xml";

	public static void main(String[] args) throws JAXBException, IOException {

		ArrayList<BookItem> bookList = new ArrayList<BookItem>();

		// create books
		BookItem book1 = new BookItem();
		book1.setIsbn("978-0060554736");
		book1.setName("The Game");
		book1.setAuthor("Neil Strauss");
		book1.setPublisher("Harpercollins");
		bookList.add(book1);

		BookItem book2 = new BookItem();
		book2.setIsbn("978-3832180577");
		book2.setName("Feuchtgebiete");
		book2.setAuthor("Charlotte Roche");
		book2.setPublisher("Dumont Buchverlag");
		bookList.add(book2);

		// create bookstore, assigning book
		Bookstore bookstore = new Bookstore();
		bookstore.setName("Fraport Bookstore");
		bookstore.setLocation("Frankfurt Airport");
		bookstore.setBookList(bookList);

		// create JAXB context and instantiate marshaller
		JAXBContext context = JAXBContext.newInstance(Bookstore.class);
		Marshaller m = context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

		// Write to System.out
		m.marshal(bookstore, System.out);

		// Write to File
		m.marshal(bookstore, new File(BOOKSTORE_XML));

		// get variables from our xml file, created before
		System.out.println();
		System.out.println("Output from our XML File: ");
		Unmarshaller um = context.createUnmarshaller();
		Bookstore bookstore2 = (Bookstore) um.unmarshal(new FileReader(BOOKSTORE_XML));
		ArrayList<BookItem> list = bookstore2.getBooksList();
		for (BookItem bookItem : list) {
			System.out.println("Book: " + bookItem.getName() + " from "
					+ bookItem.getAuthor());
		}
	}
}