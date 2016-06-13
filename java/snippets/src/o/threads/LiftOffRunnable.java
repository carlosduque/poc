package o.threads;

public class LiftOffRunnable implements Runnable {

    protected int countDown = 10; //default
    private static int taskCount = 0;
    private final int id = taskCount++;
    
    public LiftOffRunnable() {}

    public LiftOffRunnable(int countDown) {
        this.countDown = countDown;
    }
    
    public String status() {
        return "#" + id + "(" + (this.countDown > 0 ? this.countDown : "Liftoff!") + "), ";
    }
    
    @Override
    public void run() {
        while(countDown-- > 0) {
            System.out.print(status());
            Thread.yield();
        }
        
    }

}
