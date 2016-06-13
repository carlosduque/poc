package o.threads;

import java.util.Scanner;

class WaitNotifyProcessor {
   public void produce() throws InterruptedException {
      synchronized (this) {
         System.out.println("producer thread is running...");
         wait();
         System.out.println("resumed");
      }
   }
   public void consume() throws InterruptedException {
      Scanner scanner = new Scanner(System.in);
      Thread.sleep(2000);
      
      synchronized (this) {
         System.out.println("waiting for return key");
         scanner.nextLine();
         System.out.println("return key pressed");
         notify();
      }
   }
}

public class WaitNotifyApp {

   /**
    * @param args
    */
   public static void main(String[] args) throws InterruptedException {
      final WaitNotifyProcessor processor = new WaitNotifyProcessor();
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

      t1.start();
      t2.start();
      t1.join();
      t2.join();
   }

}
