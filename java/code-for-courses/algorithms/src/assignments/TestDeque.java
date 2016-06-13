import java.util.NoSuchElementException;

public class TestDeque {

  private Deque<Integer> d = null;

  public void test1() {
    StdOut.println("test1() =============");
    StdOut.println("creating deque");
    d = new Deque<Integer>();
    StdOut.println("addFirst()");
    d.addFirst(1);
    StdOut.println("addFirst()");
    d.addFirst(2);
    StdOut.printf("size: %d %n", d.size());
    StdOut.println("creating deque");
    d.addLast(9);
    d = new Deque<Integer>();
    StdOut.println("addLast()");
    d.addLast(9);
    StdOut.printf("size: %d %n", d.size());
  }

  public void test2() {
    StdOut.println("test2() =============");
    StdOut.println("creating deque");
    d = new Deque<Integer>();
    StdOut.println("addFirst()");
    d.addFirst(1);
    StdOut.printf("size: %d %n", d.size());
    StdOut.printf("removeLast(): %s, new size: %d %n", d.removeLast(), d.size());
  }

  public void test3() {
    StdOut.println("test3() =============");
    StdOut.println("creating deque");
    d = new Deque<Integer>();
    StdOut.println("addFirst()");
    d.addFirst(1);
    StdOut.println("addFirst()");
    d.addFirst(2);
    StdOut.println("addFirst()");
    d.addFirst(3);
    StdOut.println("addFirst()");
    d.addFirst(4);
    for (Integer i : d)
      StdOut.printf("%s ", i);
    StdOut.println();
    StdOut.printf("removeLast(): %s, new size: %d %n", d.removeLast(), d.size());
    StdOut.printf("removeLast(): %s, new size: %d %n", d.removeLast(), d.size());
    StdOut.printf("removeLast(): %s, new size: %d %n", d.removeLast(), d.size());
    StdOut.printf("isEmpty(): %s %n", d.isEmpty());
    StdOut.printf("removeLast(): %s, new size: %d %n", d.removeLast(), d.size());
    StdOut.printf("isEmpty(): %s %n", d.isEmpty());
    try {
      StdOut.printf("removeLast(): %s, new size: %d %n", d.removeLast(), d.size());
    } catch (NoSuchElementException e) {
      StdOut.println("caught right exception: " + e);
    }
  }

  public static void main(String[] args) {
    TestDeque td = new TestDeque();
    td.test1();
    td.test2();
    td.test3();
  }
}
