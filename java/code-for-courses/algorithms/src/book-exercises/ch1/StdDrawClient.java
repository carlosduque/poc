/**
 * 1.1.31 Random connections. Write a program that takes as command-line arguments an integer N and a double value
 * p (between 0 and 1), plots N equally spaced dots of size .05 on the circumference of a circle, and then, with
 * probability p for each pair of points, draws a gray line connecting them.
 **/
import java.lang.Math;
import java.awt.Point;

enum Shape {
    CIRCLE,
    LINE,
    POINT,
    RECTANGLE,
    SQUARE;
}

public class StdDrawClient {

    public static void main(String[] args) {
        double x0 = Double.parseDouble(args[0]);
        double x1 = Double.parseDouble(args[1]);
        double y0 = Double.parseDouble(args[2]);
        double y1 = Double.parseDouble(args[3]);
        double penRadius = Double.parseDouble(args[4]);
        Shape shape = Shape.valueOf(args[5].toUpperCase());

        StdDraw.setXscale(x0, x1); // we will be using x-coordinates between x and N
        StdDraw.setYscale(y0, y1); // we will be using y-coordinates between y and N
        StdDraw.setPenRadius(penRadius);

        switch (shape) {
            case CIRCLE:
                StdOut.printf("write x, y and radius. \n");
                StdDraw.circle(StdIn.readDouble(), StdIn.readDouble(), StdIn.readDouble());
                break;
            case LINE:
                StdOut.printf("write x0, y0 and x1, y1. \n");
                StdDraw.line(StdIn.readDouble(), StdIn.readDouble(), StdIn.readDouble(), StdIn.readDouble());
                break;
               // StdDraw.point(StdIn.readDouble(), StdIn.readDouble());
               // StdDraw.rectangle(StdIn.readDouble(), StdIn.readDouble(), StdIn.readDouble(), StdIn.readDouble());
               // StdDraw.square(StdIn.readDouble(), StdIn.readDouble(), StdIn.readDouble());
            default:
                throw new IllegalArgumentException();
        }
    }
}
