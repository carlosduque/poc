import java.util.Arrays;

public class Fast {
    public static void main(String[] args) {
    // rescale coordinates and turn on animation mode
    StdDraw.setXscale(0, 32768);
    StdDraw.setYscale(0, 32768);
    StdDraw.show(0);
    StdDraw.setPenRadius(0.01);  // make the points a bit larger

    In in = new In(args[0]);
    int N = in.readInt();
    Point[] points = new Point[N];

    for (int i = 0; i < N; i++) {
      int x = in.readInt();
      int y = in.readInt();
      points[i] = new Point(x, y);
      points[i].draw();
    }

    // for every point p
    for (int p = 0; p < N; p++) {
      // sort according to the slopes with p
      Arrays.sort(points, points[p].SLOPE_ORDER); 

      Point[] collinear = new Point[1];
      int size = 0;
      // compare with every point q
      for (int q = 0; q < N - 1; q++) {
        if (p == q) continue; // avoid process with self
        if (size == 0) {
          collinear[size++] = points[p];
          //StdOut.printf(" + [%d:%d] collinear[%d] = points[%d] = %s ** %n", p, q, size-1, p, points[p]);
        }

        //StdOut.printf(" | comparing slopes for: points[%d], points[%d] and points[%d] %n", p, q, q + 1);
        if (points[p].slopeTo(points[q]) == points[p].slopeTo(points[q + 1])) {
          Arrays.sort(collinear);

          if (Arrays.binarySearch(collinear, points[q]) < 0) {
            collinear = Arrays.copyOf(collinear, size + 1);
            //StdOut.printf(" + [%d:%d] collinear[%d] = points[%d] = %s %n", p, q, size, q, points[q]);
            collinear[size++] = points[q];
          }

          if (Arrays.binarySearch(collinear, points[q + 1]) < 0) {
            collinear = Arrays.copyOf(collinear, size + 1);
            //StdOut.printf(" + [%d:%d] collinear[%d] = points[%d] = %s %n", p, q, size, q+1, points[q + 1]);
            collinear[size++] = points[q + 1];
          }
          Arrays.sort(collinear);
        }
      }

      if (size > 3) {
        for (int i = 0; i < size; i++) {
          StdOut.printf((i == size - 1? " %s" : "%s -> "), collinear[i], i, size);
        }
        StdOut.println();
      }
      collinear[0].drawTo(collinear[size - 1]);
    }

    // display to screen all at once
    StdDraw.show(0);

    // reset the pen radius
    StdDraw.setPenRadius();
  }

}


