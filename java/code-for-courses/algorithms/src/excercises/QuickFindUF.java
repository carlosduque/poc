public class QuickFindUF {

  private static int[] id;

  private int count = 0;

  public QuickFindUF(int n) {
    id = new int[n];
    for (int i = 0; i < n; i++) {
      id[i] = i;
    }
    count = n;
  }

  public int find(int x) {
    return id[x];
  }

  public boolean connected(int p, int q) {
    return find(p) == find(q);
  }

  public int count() {
    return count;
  }

  public void union(int p, int q) {
    if (connected(p, q)) return;
    int pId = id[p];
    int qId = id[q];
    for(int i = 0; i < id.length; i++)
      if (id[i] == pId) id[i] = qId;
    --count;
  }

  public static void main(String[] args) {
    int size = StdIn.readInt();
    QuickFindUF qf = new QuickFindUF(size);
    while (!StdIn.isEmpty()) {
      int p = StdIn.readInt();
      int q = StdIn.readInt();
      if (qf.connected(p, q)) continue;
      qf.union(p, q);
      StdOut.println(p + " " + q);
    }
    StdOut.println(" * " + qf.count() + " count");
    //qf.print();
  }

  public void print() {
    for (int i = 0; i < id.length; i++)
      StdOut.printf("%d ", id[i]);
    StdOut.println();
  }
}

