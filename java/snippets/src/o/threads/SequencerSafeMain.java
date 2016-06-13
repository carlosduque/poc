package o.threads;

import o.beans.SequenceSafe;

class SafeSeqTask implements Runnable {

    private SequenceSafe seq = null;

    SafeSeqTask(SequenceSafe seq) {
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

public class SequencerSafeMain {

    private static final int NTHREADS = Runtime.getRuntime().availableProcessors();;

    public static void main(String... args) {

        SequenceSafe s = new SequenceSafe();
        Thread[] t = new Thread[NTHREADS];

        for (int i = 0; i < NTHREADS; i++) {
            t[i] = new Thread(new SafeSeqTask(s));
            t[i].setName("ST#" + i);
        }

        for (int i = 0; i < NTHREADS; i++) {
            t[i].start();
        }
    }
}
