/*************************************************************************
 * Name:
 * Email:
 *
 * Compilation:  javac Point.java
 * Execution:
 * Dependencies: StdDraw.java
 *
 * Description: An immutable data type for points in the plane.
 *
 *************************************************************************/

import java.util.Arrays;
import java.util.Comparator;

public class Point implements Comparable<Point> {

    // compare points by slope
    public final Comparator<Point> SLOPE_ORDER = new BySlope();

    private final int x;                              // x coordinate
    private final int y;                              // y coordinate

    // create the point (x, y)
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    // plot this point to standard drawing
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    // draw line between this point and that point to standard drawing
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // slope between this point and that point
    public double slopeTo(Point that) {
      //degenerate line segment (a point and itself): return negative infinity
      if (this == that) {
        return Double.NEGATIVE_INFINITY;
      }
      if (this.compareTo(that) == 0) {
        return Double.NEGATIVE_INFINITY;
      }

      //horizontal line: positive zero
      if (that.y == this.y) {
        return 0.0;
      }

      //vertical line: positive infinity
      if (that.x == this.x) {
        return Double.POSITIVE_INFINITY;
      }

      return (double) (that.y - this.y) / (double) (that.x - this.x);
    }

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    public int compareTo(Point that) {
      if (this.y < that.y || ((this.y == that.y) && (this.x < that.x)))
        return -1;
      else if (this.y == that.y && this.x == that.x)
        return 0;
      else
        return 1;
    }

    // return string representation of this point
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    // unit test
    public static void main(String[] args) {
      Point p0 = new Point(19000, 10000);
      Point p1 = new Point(18000, 10000);
      Point p2 = new Point(32000, 10000);
      Point p3 = new Point(21000, 10000);
      Point p4 = new Point(1234, 5678);
      Point p5 = new Point(14000, 10000);

      Point ph = new Point(19500, 10000); //horizontal with p0
      Point pv = new Point(19000, 10500); //vertical with p0

      StdOut.printf("%s <=> %s: %s %n", p0, p1, p0.compareTo(p1)); //-1
      StdOut.printf("%s <=> %s: %s %n", p1, p2, p1.compareTo(p2)); //1
      StdOut.printf("%s <=> %s: %s %n", p4, p4, p4.compareTo(p4)); //0

      StdOut.printf("== slopes %n");
      StdOut.printf("%s / %s: %s %n", p4, p5, p4.slopeTo(p5));
      StdOut.printf("%s / %s: %s %n", pv, ph, pv.slopeTo(ph));

      StdOut.printf("%s / %s: %s degenerate line segment %n", p2, p2, p2.slopeTo(p2));
      StdOut.printf("%s / %s: %s degenerate line segment %n", p2, new Point(32000, 10000),
                                                              p2.slopeTo(new Point(32000, 10000)));
      StdOut.printf("%s / %s: %s horizontal line %n", p0, ph, p0.slopeTo(ph));
      StdOut.printf("%s / %s: %s vertical line%n", p0, pv, p0.slopeTo(pv));

      StdOut.println("===============================");
      StdOut.printf("%s / %s: %s %n", new Point(142, 473), new Point(142, 218),
                                      new Point(142, 473).slopeTo(new Point(142, 218)));
      StdOut.printf("%s / %s: %s %n", new Point(373, 231), new Point(373, 231),
                                      new Point(373, 231).slopeTo(new Point(373, 231)));
      StdOut.printf("%s / %s: %s %n", new Point(10083, 5292), new Point(10083, 5292),
                                      new Point(10083, 5292).slopeTo(new Point(10083, 5292)));
      StdOut.println("===============================");
      StdOut.printf("== comparator %n");
      Point[] points = {p5, p3, p1, p4, p2, p0};
      StdOut.printf("  - unsorted %n");
      for (Point p : points)
        StdOut.println(p);

      StdOut.printf("  - sorted by natural order%n");
      Arrays.sort(points);
      for (Point p : points)
        StdOut.println(p);

      StdOut.printf("  - test comparator manually %n");
      StdOut.printf("%s.SLOPE_ORDER.compare(%s, %s): '%d' %n", p0, p1, p2, p0.SLOPE_ORDER.compare(p1, p2));
      StdOut.printf("%s.SLOPE_ORDER.compare(%s, %s): '%d' %n", p2, p1, p0, p2.SLOPE_ORDER.compare(p1, p0));

      StdOut.printf("%s.SLOPE_ORDER.compare(%s, %s): '%d' %n", p3, p4, p5, p3.SLOPE_ORDER.compare(p4, p5));
      StdOut.printf("%s.SLOPE_ORDER.compare(%s, %s): '%d' %n", p5, p4, p3, p5.SLOPE_ORDER.compare(p4, p3));

      StdOut.printf("%s.SLOPE_ORDER.compare(%s, %s): '%d' %n", p0, ph, pv, p0.SLOPE_ORDER.compare(ph, pv));
      StdOut.printf("%s.SLOPE_ORDER.compare(%s, %s): '%d' %n", ph, ph, ph, p0.SLOPE_ORDER.compare(ph, ph));

      StdOut.println("-------------------------------------------------");
      StdOut.println();
      StdOut.printf("  - sorted by slope %n");
      //Point[] aux = points.clone();
      Point[] aux = {new Point(10, 10), new Point(11, 11), new Point(2, 3), new Point(12, 12)};
      StdOut.println(":: Aux Original *");
      for (int idx = 0; idx < aux.length; idx++) {
        StdOut.printf(" %s ", aux[idx]);
      }
      StdOut.println();

      for (int i = 0; i < aux.length; i++) {
        StdOut.printf("Arrays.sort(aux, aux[%d].SLOPE_ORDER) ===== %n", i);
        Arrays.sort(aux, aux[i].SLOPE_ORDER);
        StdOut.printf(" current aux: ");
        for (int idx = 0; idx < aux.length; idx++) {
          StdOut.printf(" %s ", aux[idx]);
        }
        StdOut.println();
      }

      StdOut.println(":: Aux Sorted *");
      for (int idx = 0; idx < aux.length; idx++) {
        StdOut.printf(" %s ", aux[idx]);
      }
    }

    private class BySlope implements Comparator<Point> {
      public int compare(Point v, Point w) {
        int result = -1;
        if (slopeTo(v) < slopeTo(w))
          result = -1;
        else if (slopeTo(v) > slopeTo(w))
          result = 1;
        else
          result = 0;
//        StdOut.printf(" * %s | slopeTo%s = %.2f :: slopeTo%s = %.2f   <=> [%d] %n",
//            Point.this, v, slopeTo(v), w, slopeTo(w), result);
        return result;
      }
    }
}
