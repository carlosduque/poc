package o.threads;

class Horse implements Runnable {
   private static final int MAX_STEPS = 5;
    private final String name;

    public Horse(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        for(int i = 0; i < MAX_STEPS; i++) {
            System.out.println(this.name + " step: " + i);
        }
        System.out.println(this.name + " finished the race !");
    }

}

public class Race {
    public static void main(String[] args) {
        new Thread(new Horse("stripes")).start();
        new Thread(new Horse("sugar")).start();
        new Thread(new Horse("rocket")).start();
        new Thread(new Horse("2cents")).start();
        new Thread(new Horse("jerry")).start();
        System.out.println("End of main.");
    }
}
