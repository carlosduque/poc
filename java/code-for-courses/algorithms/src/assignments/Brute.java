public class Brute {
    public static void main(String[] args) {
    // rescale coordinates and turn on animation mode
    StdDraw.setXscale(0, 32768);
    StdDraw.setYscale(0, 32768);
    StdDraw.show(0);
    StdDraw.setPenRadius(0.01);  // make the points a bit larger

    In in = new In(args[0]);
    int n = in.readInt();
    Point[] point = new Point[n];

    for (int i = 0; i < n; i++) {
      int x = in.readInt();
      int y = in.readInt();
      point[i] = new Point(x, y);
      point[i].draw();
    }
    //StdOut.printf("read %d points %n", point.length);

    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        for (int k = 0; k < n; k++) {
          for (int l = 0; l < n; l++) {
            double slope2Q = point[i].slopeTo(point[j]);
            double slope2R = point[i].slopeTo(point[k]);
            double slope2S = point[i].slopeTo(point[l]);
            if (slope2Q == slope2R && slope2Q == slope2S) {
              //StdOut.printf("slope = %.2f for points %s --> %s --> %s >>> %s %n", slope2Q, point[i], point[j], point[k], point[l]);
              if (less(point[i], point[j]) &&
                 less(point[j], point[k]) &&
                 less(point[k], point[l])
                 ) {
                  point[i].drawTo(point[l]);
                  StdOut.printf("%s -> %s -> %s -> %s%n", point[i], point[j], point[k], point[l]);
              }
            }
          }
        }
      }
    }

    // display to screen all at once
    StdDraw.show(0);

    // reset the pen radius
    StdDraw.setPenRadius();
  }

  private static boolean less(Point a, Point b) {
    return a.compareTo(b) < 0;
  }
}

