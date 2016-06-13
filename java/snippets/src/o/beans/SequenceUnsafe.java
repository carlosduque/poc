package o.beans;

public class SequenceUnsafe {
    //GuardedBy("this")
	private int val;

    public void increment() {
        val++;
	}

    public void decrement() {
        val--;
	}

	public int value() {
		return val;
	}
}