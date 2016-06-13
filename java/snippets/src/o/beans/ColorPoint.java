package o.beans;

import o.beans.Point;

public class ColorPoint extends Point {
    protected String color;

    public ColorPoint(String color) {
        super(0, 0);
        System.out.printf("ColorPoint(%s)\n", color);
        this.color = color;
    }

    public ColorPoint(String color, int initialX, int initialY) {
        super(initialX, initialY);
        System.out.printf("ColorPoint(%s, %d, %d)\n", color, initialX, initialY);
        this.color = color;
    }

    public String toString() {
        return super.toString() + " " + this.color;
    }

    public void setColor(String newColor) {
        System.out.printf("new color: %s \n", newColor);
        this.color = newColor;
    }

}
