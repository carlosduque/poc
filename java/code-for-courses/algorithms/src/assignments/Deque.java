import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
  private int size;
  private Node<Item> first;
  private Node<Item> last;

  // construct an empty deque
  public Deque() {
    first = null;
    last = null;
    size = 0;
  }

  // is the deque empty?
  public boolean isEmpty() {
    return size <= 0;
  }

  // return the number of items on the deque
  public int size() {
    return size;
  }

  // add the item to the front
  public void addFirst(Item item) {
    if (item == null) throw new NullPointerException();
    Node<Item> newFirst = new Node<Item>();
    newFirst.item = item;
    newFirst.next = first;
    if (null != first) first.prev = newFirst;
    first = newFirst;
    if (size == 0) last = first;
    size++;
  }

  // add the item to the end
  public void addLast(Item item) {
    if (item == null) throw new NullPointerException();
    Node<Item> newLast = new Node<Item>();
    newLast.item = item;
    newLast.prev = last;
    if (last != null) last.next = newLast;
    last = newLast;
    size++;
  }

  // remove and return the item from the front
  public Item removeFirst() {
    if (isEmpty())
      throw new NoSuchElementException();
    Item item = first.item;
    first = first.next;
    if (null != first) first.prev = null;
    size--;
    if (isEmpty()) first = last;
    return item;
  }

  // remove and return the item from the end
  public Item removeLast() {
    if (isEmpty())
      throw new NoSuchElementException();
    Item item = last.item;
    last = last.prev;
    if (null != last) last.next = null;
    size--;
    if (isEmpty()) first = last;
    return item;
  }

  private static class Node<Item> {
    private Item item;
    private Node<Item> prev;
    private Node<Item> next;
  }

  // return an iterator over items in order from front to end
  public Iterator<Item> iterator() {
    return new FrontToEndIterator<Item>(first);
  }

  private class FrontToEndIterator<Item> implements Iterator<Item> {
    private Node<Item> current;

    public FrontToEndIterator(Node<Item> first) {
      current = first;
    }

    public boolean hasNext() {
      return current != null;
    }

    public void remove() {
      throw new UnsupportedOperationException();
    }

    public Item next() {
      if (!hasNext()) throw new NoSuchElementException();
      Item item = current.item;
      current = current.next;
      return item;
    }

  }

  // unit testing
  public static void main(String[] args) {
    Deque<String> deque = new Deque<String>();
    deque.addFirst("inveniam");
    deque.addFirst("viam");
    deque.addFirst("Aut");
    deque.addLast("aut");
    deque.addLast("faciam");
    StdOut.printf("is empty? %s, size = %d %n", deque.isEmpty(), deque.size());

    StdOut.printf("%s %n", deque.removeFirst());
    StdOut.printf("%s %n", deque.removeLast());

    StdOut.printf("%s %n", deque.removeFirst());
    StdOut.printf("%s %n", deque.removeLast());
    print(deque);

    StdOut.printf("%s %n", deque.removeFirst());
    try {
      deque.removeFirst();
    } catch (NoSuchElementException e) {
      StdOut.println("Caught the right Exception: " + e);
    }

    Deque<Integer> dequeInt = new Deque<Integer>();
    dequeInt.addFirst(5);
    dequeInt.addLast(6);
    dequeInt.addFirst(4);
    Deque<Object> dequeObj = new Deque<Object>();
    dequeObj.addFirst(new Object());
    dequeObj.addLast(new Object());
    dequeObj.addFirst(new Object());
    for (Integer i : dequeInt)
      for (Object obj : dequeObj)
        StdOut.printf("%d : %s %n", i, obj);
  }
  private static <Item> void print(Deque<Item> d) {
    StdOut.printf("=Deque is empty? %s, size = %d, contents: %n", d.isEmpty(), d.size());
    for (Item i : d)
      StdOut.printf("%s ", i);
    StdOut.println();
  }
}
