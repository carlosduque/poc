import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueueTest {

  RandomizedQueue<String> rq = null;

  public void test1() {
    StdOut.println("create queue");
    rq = new RandomizedQueue<String>();
    StdOut.printf("enqueue() %n");
    rq.enqueue("A");
    StdOut.printf("enqueue() %n");
    rq.enqueue("B");
    StdOut.printf("enqueue() %n");
    rq.enqueue("C");
    StdOut.printf("enqueue() %n");
    rq.enqueue("D");
    StdOut.printf("enqueue() %n");
    rq.enqueue("E");
    for (String s : rq)
      StdOut.printf("%s ", s);
    StdOut.println();
    StdOut.printf("dequeue(): %s, isEmpty(): %s, new size: %d %n", rq.dequeue(), rq.isEmpty(), rq.size());
    StdOut.printf("dequeue(): %s, isEmpty(): %s, new size: %d %n", rq.dequeue(), rq.isEmpty(), rq.size());
    StdOut.printf("dequeue(): %s, isEmpty(): %s, new size: %d %n", rq.dequeue(), rq.isEmpty(), rq.size());
    StdOut.printf("dequeue(): %s, isEmpty(): %s, new size: %d %n", rq.dequeue(), rq.isEmpty(), rq.size());
    StdOut.printf("dequeue(): %s, isEmpty(): %s, new size: %d %n", rq.dequeue(), rq.isEmpty(), rq.size());
    try {
    StdOut.printf("dequeue(): %s, new size: %d %n", rq.dequeue(), rq.size());
    } catch (Exception e) {
      StdOut.println("caught right exception:" + e);
    }
  }

  // unit testing
  public static void main(String[] args) {
    RandomizedQueueTest test = new RandomizedQueueTest();
    test.test1();
  }

}
