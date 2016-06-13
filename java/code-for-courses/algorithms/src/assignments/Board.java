import java.util.Arrays;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Board {
  private final int N;
  private int[][] grid = null;
  private static int[][] goal = null;
  private int misplaced = 0;

  // construct a board from an N-by-N array of blocks
  // (where blocks[i][j] = block in row i, column j)
  public Board(int[][] blocks) {
    N = blocks.length;
    grid = new int[N][N];
    goal = new int[N][N];
    for (int i = 0, tile = 1; i < N; i++)
      for (int j = 0; j < N; j++, tile++) {
        grid[i][j] = blocks[i][j];
        goal[i][j] = tile;
        if (grid[i][j] > 0 && tile != grid[i][j])
          misplaced++;
      }
    goal[N - 1][N - 1] = 0;
  }

  // board dimension N
  public int dimension() {
      return N;
  }

  //  Should the hamming() and manhattan() methods in Board return the Hamming and Manhattan priority functions,
  //  respectively?
  //  No, hamming() should return the number of blocks out of position and
  //  manhattan() should return the sum of the Manhattan distances between the blocks and their goal positions.
  //  Recall that the blank square is not considered a block.
  //  You will compute the priority function in Solver by calling hamming() or manhattan() and
  //  adding to it the number of moves.

  //  number of blocks out of place
  public int hamming() {
      return misplaced;
  }

  // sum of Manhattan distances between blocks and goal
  public int manhattan() {
      return 0;
  }

  // is this board the goal board?
  public boolean isGoal() {
      return goal.equals(grid);
  }

  // a board that is obtained by exchanging two adjacent blocks in the same row
  public Board twin() {
    int[][] twin = new int[N][N];
    for (int i = 0; i < N; i++)
        for (int j = 0; j < N; j++)
            twin[i][j] = grid[i][j];
    int tmp = twin[1][0];
    twin[1][0] = twin[0][0];
    twin[0][0] = tmp;
    return new Board(twin);
  }

  // does this board equal y?
  public boolean equals(Object y) {
    if (this == y) return true;
    if (null == y) return false;
    if (this.getClass() != y.getClass()) return false;
    Board that = (Board) y;

    return ((this.dimension() == that.dimension())
      && (this.hamming() == that.hamming())
      && (this.manhattan() == that.manhattan())
      && (this.twin() == that.twin())
      && (this.neighbors() == that.neighbors())
      && (this.toString().equals(that.toString())));
      //&& (Arrays.deepEquals(this, that)));
  }

  // all neighboring boards
  public Iterable<Board> neighbors() {
    return null;
  }

  // string representation of this board (in the output format specified below)
  public String toString() {
    return asString(grid);
  }

  private static String asString(int[][] ary) {
    int n = ary.length;
    StringBuilder s = new StringBuilder();
    s.append(n + "\n");
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
            s.append(String.format("%2d ", ary[i][j]));
        }
        s.append("\n");
    }
    return s.toString();
  }

  // unit tests (not graded)
  public static void main(String[] args) {
    In in = new In(args[0]);
    int N = in.readInt();
    int[][] blocks = new int[N][N];
    for (int i = 0; i < N; i++)
        for (int j = 0; j < N; j++)
            blocks[i][j] = in.readInt();
    Board board = new Board(blocks);
    StdOut.printf("dimension: %d %n", board.dimension());
    StdOut.printf("hamming:   %d %n", board.hamming());
    StdOut.println(board.toString());
    StdOut.printf("goal*: %n%s %n", asString(goal));
  }
}
