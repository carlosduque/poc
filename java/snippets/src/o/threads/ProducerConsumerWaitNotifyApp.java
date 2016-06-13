package o.threads;

import java.util.LinkedList;
import java.util.Random;

class ProducerConsumerWaitNotifyProcessor {
   private LinkedList<Integer> list = new LinkedList<Integer>();
   private final int LIMIT = 10;
   Object lock = new Object();

   public void produce() throws InterruptedException {
      int value = 0;
      while (true) {
         synchronized (lock) {
            while (list.size() == LIMIT) {
               lock.wait(); // if the list is full, then wait (stop producing)
            }
            list.add(value++);
            lock.notify(); // I can produce now, so notify consumers they can do their job
         }
      }
   }

   public void consume() throws InterruptedException {
      Random random = new Random();
      while (true) {
         synchronized (lock) {
            while (list.size() == 0) {
               lock.wait(); // if the list is empty, this consumer should wait until
                             // there is something in the list to consume
            }
            System.out.print("list size: " + list.size());
            int value = list.removeFirst();
            System.out.println(", value: " + value);
            lock.notify(); // I was able to consume, notify producer he can produce more
         }
         Thread.sleep(random.nextInt(1000));
      }
   }
}

public class ProducerConsumerWaitNotifyApp {

   /**
    * @param args
    */
   public static void main(String[] args) throws InterruptedException {
      final ProducerConsumerWaitNotifyProcessor processor = new ProducerConsumerWaitNotifyProcessor();
      Thread t1 = new Thread(new Runnable() {
         @Override
         public void run() {
            try {
               processor.produce();
            } catch (InterruptedException e) {
               e.printStackTrace();
            }
         }
         
      });

      Thread t2 = new Thread(new Runnable() {
         @Override
         public void run() {
            try {
               processor.consume();
            } catch (InterruptedException e) {
               e.printStackTrace();
            }
         }
         
      });
     System.out.println("Starting threads...");
      t1.start();
      t2.start();
      t1.join();
      t2.join();
   }

}
