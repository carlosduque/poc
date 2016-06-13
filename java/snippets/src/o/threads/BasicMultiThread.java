package o.threads;


public class BasicMultiThread {

    public static void main(String[] args) {
        for(int i = 0; i < 5; i++) {
            new Thread(new LiftOffRunnable()).start();
        }
        System.out.println("Waiting for Liftoff");
    }

}
