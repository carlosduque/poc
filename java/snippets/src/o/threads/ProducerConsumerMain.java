package o.threads;

import java.util.List;
import java.util.ArrayList;

class Item {
    private static int counter = 0;
    private String id;
    Item() {
        this.id = "i::" + counter++;
    }
    public String toString() {
        return id;
    }
}

class ProducerTask implements Runnable {
    private List<Item> container;
    ProducerTask(List<Item> l) {
        this.container = l;
    }

    public void run() {
       while (true) {
          synchronized (container) {
             Item item = new Item();
             System.out.println(Thread.currentThread().getName() + " producing: " + item);
             container.add(item);
             container.notify();
          }
       }
    }
}

class ConsumerTask implements Runnable {
    private List<Item> container;

    ConsumerTask(List<Item> l) {
        this.container = l;
    }

    public void run() {
       while (true) {
          synchronized (container) {
             while (container.size() == 0) {
                try {
                  container.wait();
               } catch (InterruptedException e) {
                  e.printStackTrace();
               }
             }
             System.out.println(Thread.currentThread().getName() + " consuming " + container.remove(0) + ", container size: " + container.size());
             container.notify();
          }
       }
    }
}

public class ProducerConsumerMain {
   static final int NUM_CONSUMERS = 20;
   static final int NUM_PRODUCERS = 1;
   public static void main(String[] args) {
      List<Item> products = new ArrayList<Item>();
      Thread[] producers = new Thread[NUM_PRODUCERS];
      for (Thread p : producers) {
         p = new Thread(new ProducerTask(products));
         System.out.println("initialized producer " + p);
         p.start();
      }

      Thread[] consumers = new Thread[NUM_CONSUMERS];
      for (Thread c : consumers) {
         c = new Thread(new ConsumerTask(products));
         System.out.println("initialized consumer " + c);
         c.start();
      }


      System.out.println("end of main");
   }
}
