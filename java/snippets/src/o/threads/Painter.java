package o.threads;

import java.util.Random;

import o.beans.ColorPoint;

class PainterTask implements Runnable {

  ColorPoint colorPoint = null;

  public PainterTask(String color) {
    System.out.printf("PainterTask(%s) - %s\n", color,Thread.currentThread().getName());
    colorPoint = new ColorPoint(color);
  }

  public void run() {
    System.out.printf("PainterTask.run() - %s\n", Thread.currentThread().getName());
    colorPoint.move();
  }
}

public class Painter {
  public static void main(String[] args) {
    System.out.printf("Painter.main()\n");

    String[] color = {"Yellow", "Blue", "Pink", "Green"};
    Random rnd = new Random();
    Thread[] threads = new Thread[5];
    for(int i = 0; i < 5; i++) {
      threads[i] = new Thread(new PainterTask(color[rnd.nextInt(color.length)]));
    }
    
    System.out.println();
    System.out.printf("%d threads about to be started\n", threads.length);
    for(int i = 0; i < threads.length; i++) {
      threads[i].start();
    }

    System.out.printf("/Painter.main()\n");
  }
}
