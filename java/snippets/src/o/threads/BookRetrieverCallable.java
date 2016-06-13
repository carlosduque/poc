package o.threads;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import o.beans.Book;

public class BookRetrieverCallable implements Callable<Book> {
	private List<Book> library = new ArrayList<Book>();

	public BookRetrieverCallable(List<Book> lib) {
		this.library = lib;
	}

	@Override
	public Book call() throws Exception {
		int index = (int) (Math.random() * library.size());
		Book b = library.get(index);
		b.setContent(Thread.currentThread().getName());
		return b;
	}

}
