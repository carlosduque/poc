package o.threads;

import java.util.concurrent.Callable;

public class MyCallable implements Callable<Long> {
	  @Override
	  public Long call() throws Exception {
	    long sum = 0;
	    for (long i = 0; i <= 3; i++) {
	      sum += i;
	    }
	    return sum;
	  }
}