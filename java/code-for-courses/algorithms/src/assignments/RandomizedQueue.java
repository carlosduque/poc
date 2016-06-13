import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

  private int n;
  private int size;
  private Item[] a = null;

  // construct an empty randomized queue
  public RandomizedQueue() {
    a = (Item[]) new Object[2];
    size = 0;
  }

  // is the queue empty?
  public boolean isEmpty() {
    return size == 0;
  }

  // return the number of items on the queue
  public int size() {
    return size;
  }

  // add the item
  public void enqueue(Item item) {
    if (item == null)
      throw new NullPointerException();
    if (size == a.length)
      resize(2 * a.length);
    a[size++] = item;
  }

  // remove and return a random item
  public Item dequeue() {
    if (isEmpty())
      throw new NoSuchElementException();
    int pos = StdRandom.uniform(0, size);
    Item item = a[pos];
    a[pos] = null;
    swap(pos, size - 1);
    size--;
    if (size > 0 && size == a.length / 4)
      resize(a.length / 2);
    return item;
  }

  // return (but do not remove) a random item
  public Item sample() {
    if (isEmpty())
      throw new NoSuchElementException();
    return a[StdRandom.uniform(0, size - 1)];
  }

  // return an independent iterator over items in random order
  public Iterator<Item> iterator() {
    return new RandomizedIterator();
  }

  private void resize(int capacity) {
    Item[] temp = (Item[]) new Object[capacity];
    for (int i = 0, k = 0; i < a.length; i++)
      if (null != a[i]) temp[k++] = a[i];
    a = temp;
  }

  private void swap(int src, int dst) {
    Item tmp = a[src];
    a[src] = a[dst]; 
    a[dst] = tmp;
  }

  private class RandomizedIterator implements Iterator<Item> {
    int k = 0;
    Item[] copy = null;
    public RandomizedIterator() {
      copy = (Item[]) new Object[a.length];
      for (int i = 0; i < copy.length; i++)
        copy[i] = a[i];
      StdRandom.shuffle(copy, 0, size - 1);
    }

    public boolean hasNext() {
      return k < size;
    }

    public void remove() {
      throw new UnsupportedOperationException();
    }

    public Item next() {
      return copy[k++];
    }
  }

  // unit testing
  public static void main(String[] args) {
    RandomizedQueue<String> rq = new RandomizedQueue<String>();
    rq.enqueue("A");
    rq.enqueue("B");
    rq.enqueue("C");
    rq.enqueue("D");
    rq.enqueue("E");
    StdOut.println("iterator:");
    for (String str : rq)
      StdOut.printf("%s ", str);
    StdOut.println();
    StdOut.printf("sample: %s %n", rq.sample());
    StdOut.printf("sample: %s %n", rq.sample());
    StdOut.printf("dequeue: %s %n", rq.dequeue());
    StdOut.printf("dequeue: %s %n", rq.dequeue());
    StdOut.printf("dequeue: %s %n", rq.dequeue());
    StdOut.printf("dequeue: %s %n", rq.dequeue());
    StdOut.printf("dequeue: %s %n", rq.dequeue());
    try {
      StdOut.printf("dequeue: %s %n", rq.dequeue());
    } catch (Exception e) {
      StdOut.println("Caught the right Exception. " + e);
    }
  }

}
