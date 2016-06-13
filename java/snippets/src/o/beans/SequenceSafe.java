package o.beans;

public class SequenceSafe {
    //GuardedBy("this")
	private int val;

    public synchronized void increment() {
        val++;
	}

    public synchronized void decrement() {
        val--;
	}

	public synchronized int value() {
		return val;
	}
}