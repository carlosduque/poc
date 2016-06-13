import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
  private MinPQ<SearchNode> pq = null;
  private Stack<Board> solution = null;
  private SearchNode start = null;
  private SearchNode current = null;

  // find a solution to the initial board (using the A* algorithm)
  public Solver(Board initial) {
    pq = new MinPQ<SearchNode>();
    start = new SearchNode(initial, 0, null);
    pq.insert(start);
    current = pq.delMin();

  }

  // is the initial board solvable?
  public boolean isSolvable() {
    return false;
  }

  // min number of moves to solve initial board; -1 if unsolvable
  public int moves() {
    return 0;
  }

  // sequence of boards in a shortest solution; null if unsolvable
  public Iterable<Board> solution() {
    return solution;
  }

  private class SearchNode {
    private final Board board;
    private final int moves;
    private final SearchNode parent;
    public SearchNode(Board b, int m, SearchNode p) {
      this.board = b;
      this.moves = m;
      this.parent = p;
    }
  }

  // solve a slider puzzle (given below)
  public static void main(String[] args) {
    // create initial board from file
    In in = new In(args[0]);
    int N = in.readInt();
    int[][] blocks = new int[N][N];
    for (int i = 0; i < N; i++)
        for (int j = 0; j < N; j++)
            blocks[i][j] = in.readInt();
    Board initial = new Board(blocks);

    // solve the puzzle
    Solver solver = new Solver(initial);

    // print solution to standard output
    if (!solver.isSolvable())
        StdOut.println("No solution possible");
    else {
        StdOut.println("Minimum number of moves = " + solver.moves());
        for (Board board : solver.solution())
            StdOut.println(board);
    }
  }
}
