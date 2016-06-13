package o.beans;

import java.util.Random;

public class Point {
    protected int x;
    protected int y;
    protected final Random rng = new Random();

    public Point(int initialX, int initialY) {
        System.out.printf("Point(%d, %d) - %s\n", initialX, initialY, Thread.currentThread().getName());
        this.x = initialX;
        this.y = initialY;
    }

    public String toString() {
        return "(" + this.x + ", " + this.y + ")";
    }

    public synchronized void move() {
        this.x += rng.nextInt(10) - 5;
        this.y += rng.nextInt(20) - 10;
        System.out.printf("Point.move(%d, %d) - %s\n", this.x, this.y, Thread.currentThread().getName());
    }

}

