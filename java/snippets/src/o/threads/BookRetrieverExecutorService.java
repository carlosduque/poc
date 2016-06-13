package o.threads;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import o.beans.Book;

public class BookRetrieverExecutorService {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	    ExecutorService executor = Executors.newFixedThreadPool(10);

	    List<Future<Book>> list = new ArrayList<Future<Book>>();
	    List<Book> library = Arrays.asList(
	    		new Book("Mares eat oats"),
	    		new Book("Does eat oats"),
	    		new Book("Little lambs eat ivy"),
	   			new Book("A kid will eat ivy too"));

	    for (int i = 0; i < 20; i++) {
	      Callable<Book> worker = new BookRetrieverCallable(library);
	      Future<Book> submit = executor.submit(worker);
	      list.add(submit);
	    }

	    //System.out.println(list.size());
	    // now retrieve the result
	    Book book = null;
	    for (Future<Book> future : list) {
	      try {
	        book = future.get();
		    System.out.println(book.getContent() + " - "+ book.getTitle());
	      } catch (InterruptedException e) {
	        e.printStackTrace();
	      } catch (ExecutionException e) {
	        e.printStackTrace();
	      }
	    }
	    executor.shutdown();
	}

}
