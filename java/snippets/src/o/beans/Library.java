package o.beans;

import java.util.ArrayList;
import java.util.List;

import o.exceptions.LowerLevelException;
import o.exceptions.MiddleLevelException;

public class Library {
	private List<Book> stock = null;

	public Library() {
		stock = new ArrayList<Book>();
	}

	public void addBook(Book e) {
		stock.add(e);
	}

	public Book findById(int idValue) {
		for (Book book : stock) {
			if (book.getId() == idValue)
				return book;
		}
		return null;
	}

	public void throwException() throws MiddleLevelException {
		if (!stock.isEmpty()) {
			try {
				stock.get(0).throwException();
			} catch (LowerLevelException e) {
				// Chaining + Translation Exceptions
				throw new MiddleLevelException("Something failed running Library#throwException()", e);
			}
		}
	}
}
