package o.threads;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class LaunchpadExecutorService {
   private static final int NTHREDS = 5;

   public static void main(String[] args) throws InterruptedException {
      ExecutorService executor = Executors.newFixedThreadPool(NTHREDS);
      for (int i = 0; i < NTHREDS; i++) {
         Runnable worker = new LiftOffRunnable(10);
         executor.execute(worker);
      }
      // This will make the executor accept no new threads
      // and finish all existing threads in the queue
      executor.shutdown();
      // Wait until all threads are finish
      //executor.awaitTermination();
      executor.awaitTermination(5, TimeUnit.MINUTES);
      System.out.println("Finished all threads");
   }

}
