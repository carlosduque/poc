package o.threads;

class MyMonitorObject {
}

public class MyWaitNotify {

  MyMonitorObject myMonitorObject = new MyMonitorObject();

  public void doWait() {
    synchronized(myMonitorObject) {
      try {
        myMonitorObject.wait();
      } catch(InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  public void doNotify() {
    synchronized(myMonitorObject) {
      myMonitorObject.notify();
    }
  }

}

