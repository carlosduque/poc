package o.exceptions;

import o.beans.Book;
import o.beans.Library;

public class MainException {

    public static void main(String[] args) throws HigherLevelException {
        Library library = new Library();
        library.addBook(new Book("Game of thrones"));
        try {
            library.throwException();
        } catch (MiddleLevelException e) {
            System.out.println("e.getMessage(): " + e.getMessage());
            System.out.println("e.getLocalizedMessage(): " + e.getLocalizedMessage());
            System.out.println("e.toString(): " + e.toString());
            Throwable cause = e.getCause();
            System.out.println("cause.getMessage(): " + cause.getMessage());
            //throw new HigherLevelException("Something failed running main");
            throw new HigherLevelException("Something failed running main", e);
        }
    }

}
