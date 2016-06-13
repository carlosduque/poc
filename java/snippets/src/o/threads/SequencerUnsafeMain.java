package o.threads;

import o.beans.SequenceUnsafe;

class UnsafeSeqTask implements Runnable {
	private SequenceUnsafe seq = null;
	UnsafeSeqTask(SequenceUnsafe seq) {
		this.seq = seq;
	}
    public void run() {
        for(int i = 0; i < 5; i++) {
			seq.increment();
			int value = seq.value();
			System.out.println(Thread.currentThread().getName() + " - : " + value);
        }
    }
}

public class SequencerUnsafeMain {

	private static final int NTHREADS = Runtime.getRuntime().availableProcessors();;

	public static void main(String... args) {

		SequenceUnsafe s = new SequenceUnsafe();
		Thread[] t = new Thread[NTHREADS];

		for (int i = 0; i < NTHREADS; i++) {
			t[i] = new Thread(new UnsafeSeqTask(s));
			t[i].setName("UT#" + i);
		}

		for (int i = 0; i < NTHREADS; i++) {
			t[i].start();
		}
	}
}
