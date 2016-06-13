/**
 * 1.1.31 Random connections. Write a program that takes as command-line arguments an integer N and a double value
 * p (between 0 and 1), plots N equally spaced dots of size .05 on the circumference of a circle, and then, with
 * probability p for each pair of points, draws a gray line connecting them.
 **/
import java.lang.Math;

public class E1131 {
    private static final double MIN = 0;
    private static final double MAX = 5;
    private static final double CIRCLE_X = 3;
    private static final double CIRCLE_Y = 4;
    private static final double RADIUS = 1;

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        double p = Double.parseDouble(args[1]);
        StdDraw.setXscale(MIN, MAX); // we will be using x-coordinates between x and N
        StdDraw.setYscale(MIN, MAX); // we will be using y-coordinates between x and N
        StdDraw.setPenRadius(0.05);
        int distance = (int) (360 / n);
        for (int i = 0; i < n; i++) {
            Point2D point = calcPoint(i * distance);
            //StdOut.printf("*(%f, %f) %d*%d=%d \n", point.x(), point.y(), i,distance, i*distance);
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.point(point.x(), point.y());
            if (StdRandom.bernoulli(p)) {
                StdDraw.setPenColor(StdDraw.GRAY);
                StdDraw.line(point.x(), point.y(), CIRCLE_X, CIRCLE_Y);
            }
        }
    }

    private static Point2D calcPoint(int i) {
        double factor = Math.PI / 180f;
        double pointX = RADIUS * Math.cos(i * factor) + CIRCLE_X;
        double pointY = RADIUS * Math.sin(i * factor) + CIRCLE_Y;
        return new Point2D(pointX, pointY);
    }

    private static boolean circumferenceHas(Point2D p) {
        double a = Math.pow((CIRCLE_X - p.x()), 2);
        double b = Math.pow((CIRCLE_Y - p.y()), 2);
        double distance = Math.sqrt(a + b);
        return distance == RADIUS;
    }
}
