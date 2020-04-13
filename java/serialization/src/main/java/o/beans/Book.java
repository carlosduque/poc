package o.beans;

public class Book {

    private final String title;
    private final int pages;

    public Book(String title, int pages) {
        this.title = title;
        this.pages = pages;
    }

    public String getTitle() {
        return title;
    }

    public int getPages() {
        return pages;
    }

    public String toString() {
        return "Book<title:" + this.title + ", #pages:" + this.pages +">";
    }

}
