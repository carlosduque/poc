package o.beans;

import o.beans.Point;
import o.beans.ColorPoint;

public class GraphicsMain {
    public static void main(String[] args) {
        System.out.println("main");
        Point p = new Point(0, 0);
        System.out.println(p.toString());
        p.move();
        System.out.println(p.toString());
        System.out.println("--------------------");
        ColorPoint cp = new ColorPoint("Red");
        System.out.println(cp.toString());
        cp.setColor("Yellow");
        cp.move();
        System.out.println(cp.toString());
    }
}

