public class QuickUnionUF {

  private static int[] id;
  private int count = 0;

  public QuickUnionUF(int n) {
    id = new int[n];
    for (int i = 0; i < n; i++) {
      id[i] = i;
    }
    count = n;
  }

  public int find(int x) {
    int val = x;
    while (val != id[val]) 
      val = id[val]; 
    return val;
  }

  public boolean connected(int p, int q) {
    return find(p) == find(q);
  }

  public int count() {
    return count;
  }

  public void union(int p, int q) {
    if (connected(p, q)) return;
    id[find(q)] = find(p);
    --count;
  }

  public static void main(String[] args) {
    int size = StdIn.readInt();
    QuickUnionUF qu = new QuickUnionUF(size);
    while (!StdIn.isEmpty()) {
      int p = StdIn.readInt();
      int q = StdIn.readInt();
      if (qu.connected(p, q)) continue;
      qu.union(p, q);
      StdOut.println(p + " " + q);
    }
    StdOut.println(":: " + qu.count() + " count");
    qu.print();
  }

  public void print() {
    for (int i = 0; i < id.length; i++)
      StdOut.printf("%d ", id[i]);
    StdOut.println();
  }
}

