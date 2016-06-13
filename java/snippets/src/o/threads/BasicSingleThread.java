package o.threads;


public class BasicSingleThread {

    public static void main(String[] args) {
        LiftOffRunnable launch = new LiftOffRunnable(15);
        launch.run();
    }

}
