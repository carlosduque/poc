/*----------------------------------------------------------------
 *  Author:        Carlos Duque
 *  Login:         carlosduque.hn@gmail.com
 *  Last updated:  2015-07-07
 *
 *  Compilation:   javac PercolationStats.java
 *  Execution:     java PercolationStats
 *
 *  Gather's statistics for the Percolation problem.
 *
 *----------------------------------------------------------------*/
public class PercolationStats {

  private final int n;
  private final int t;
  private double[] values = null;

  // perform T independent computational experiments on an N-by-N grid
  public PercolationStats(int N, int T) {
    if (N <= 0 || T <= 0) throw new IllegalArgumentException();

    this.n = N;
    this.t = T;
    int dim = n + 1;
    values = new double[t];

    Percolation p = null;
    for (int i = 0; i < t; i++) {
      p = new Percolation(n);
      int openSites = 0;
      while (!p.percolates()) {
        int x = StdRandom.uniform(1, dim);
        int y = StdRandom.uniform(1, dim);
        if (!p.isOpen(x, y)) {
          p.open(x, y);
          openSites++;
        }
      }
      values[i] = openSites / ((double) dim * dim);
    }
  }

  // sample mean of percolation threshold
  public double mean() {
    return StdStats.mean(values);
  }

  // sample standard deviation of percolation threshold
  public double stddev() {
    return StdStats.stddev(values);
  }

  // returns lower bound of the 95% confidence interval
  public double confidenceLo() {
    double val = mean() - ((1.96 * stddev())/ Math.sqrt(t));
    return val;
 }

  // returns upper bound of the 95% confidence interval
  public double confidenceHi() {
    double val = mean() + ((1.96 * stddev())/ Math.sqrt(t));
    return val;
  }

  // test client, described below
  public static void main(String[] args) {
    int gridSize = Integer.parseInt(args[0]);
    int repetitions = Integer.parseInt(args[1]);
    PercolationStats stats = new PercolationStats(gridSize, repetitions);
    StdOut.printf("mean                    = %f %n", stats.mean());
    StdOut.printf("stddev                  = %f %n", stats.stddev());
    StdOut.printf("95%% confidence interval = %f, %f %n", stats.confidenceLo(), stats.confidenceHi());
  }

}
