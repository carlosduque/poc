/*----------------------------------------------------------------
 *  Author:        Carlos Duque
 *  Login:         carlosduque.hn@gmail.com
 *  Last updated:  2015-07-07
 *
 *  Compilation:   javac Percolation.java
 *  Execution:     java Percolation
 *
 *  Solves the Percolation problem.
 *
 *----------------------------------------------------------------*/

public class Percolation {

  private static final boolean DEBUG = true;

  private static final int BLOCKED = 0;
  private static final int OPEN = 1;
  private static final int INVALID = -9;

  private final int DIM;
  private final int GRID_LIM;

  private final int vTop;
  private final int vBot;
  private int[][] grid = null;
  private WeightedQuickUnionUF uf;

  public Percolation(int N) {
    if (N <= 0) throw new IllegalArgumentException("Size should be > 0");

    // create N-by-N grid, with all sites blocked
    DIM = N;
    GRID_LIM = N + 1;
    uf = new WeightedQuickUnionUF((DIM * DIM) + 2);
    vTop = (DIM * DIM);
    vBot = vTop + 1;
    // initialize all sites to be blocked
    grid = new int[GRID_LIM][GRID_LIM];
    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid.length; j++) {
         if (i == 0 || j == 0) {
           grid[i][j] = INVALID;
         } else {
           grid[i][j] = BLOCKED;
         }
      }
    }
    if (DIM >= 2) {
      // connect top row to vTop
      for (int i = 0; i < DIM; i++) {
        uf.union(vTop, i);
      }

      // connect bottom row to vBot
      for (int i = (DIM * DIM) - 1; i >= ((DIM * DIM) - DIM); i--) {
        uf.union(vBot, i);
      }
    }
  }

  public void open(int i, int j) {
    // open site (row i, column j) if it is not already
    validate(i, j);
    // mark the site as open
    if (!isOpen(i, j)) {
      grid[i][j] = OPEN;
      // link the site to open neighbors
      int site = xyTo1D(i, j);
      if (i > 1 && isOpen(i - 1, j)) {
        uf.union(site, xyTo1D(i - 1, j));
      }
      if (i < DIM && isOpen(i + 1, j)) {
        uf.union(site, xyTo1D(i + 1, j));
      }
      if (j > 1 && isOpen(i, j - 1)) {
        uf.union(site, xyTo1D(i, j - 1));
      }
      if (j < DIM && isOpen(i, j + 1)) {
        uf.union(site, xyTo1D(i, j + 1));
      }
    }
  }

  public boolean isOpen(int i, int j) {
    // is site (row i, column j) open?
    validate(i, j);
    boolean result = (OPEN == grid[i][j]);
    return result;
  }

  public boolean isFull(int i, int j) {
    // is site (row i, column j) full?
    validate(i, j);
    return OPEN == grid[i][j] && uf.connected(xyTo1D(i, j), vTop);
  }

  public boolean percolates() {
    // does the system percolate?
    boolean q = uf.connected(vTop, vBot);
    return q;
  }

  private void validate(int x, int y) {
    check(x);
    check(y);
  }

  private void check(int val) {
    if (val <= 0 || val > DIM)
      throw new IndexOutOfBoundsException(val + " is out of range.");
  }

  private int xyTo1D(int x, int y) {
    validate(x, y);
    return (((x - 1) * DIM) + y) - 1;
 }

  public static void main(String[] args) {
    In in = new In(args[0]);
    int size = in.readInt();
    Percolation p = new Percolation(size);
    while (!p.percolates()) {
      // repeat the following until the system percolates
      // choose a site (row i, column j) uniformly at random among all blocked sites
      int i = in.readInt(); //StdRandom.uniform(1, size + 1);
      int j = in.readInt(); //StdRandom.uniform(1, size + 1);
      // open the site (row i, column j).
     if (!p.isOpen(i, j))
       p.open(i, j);
    }
    //p.print();
  }

  private void print() {
    for (int i = 0; i < grid.length; i++) {
      StdOut.println();
      for (int j = 0; j < grid.length; j++) {
        StdOut.printf("%3d", grid[i][j]);
      }
    }
    StdOut.println();
  }
}
