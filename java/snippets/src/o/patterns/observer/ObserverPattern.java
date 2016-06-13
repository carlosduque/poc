package o.patterns.observer;

import java.util.ArrayList;
import java.util.List;

interface IObserver {
    public void update(String state);
}

interface ISubject {
    public void attach(IObserver o);
    public void detach(IObserver o);
    public void notifyObservers();
}

class ConcreteSubject implements ISubject {
    private List<IObserver> observers;
    private String subjectState = "";

    ConcreteSubject() {
        System.out.println("ConcreteSubject()");
        observers = new ArrayList<IObserver>();
    }

    public void setState(String newState) {
        System.out.println(this + ".setState(" + newState + ")");
        this.subjectState = newState;
        notifyObservers();
    }
    
    public void attach(IObserver o) {
        System.out.println(this + ".attach(" + o + ")");
        observers.add(o);
    }
    public void detach(IObserver o) {
        System.out.println(this + ".detach(" + o + ")");
        observers.remove(o);
    }

    public void notifyObservers() {
        System.out.println(this + ".notifyObservers()");
        for(IObserver obs : observers) {
            obs.update(subjectState);
        }
    }
}

class ConcreteObserverA implements IObserver {
	ConcreteObserverA() {
        System.out.println("ConcreteObserverA()");
	}
    public void update(String state) {
        System.out.println(this + ".update(" + state + ")");
    }
    
}
class ConcreteObserverB implements IObserver {
	ConcreteObserverB() {
        System.out.println("ConcreteObserverB()");
	}
    public void update(String state) {
        System.out.println(this + ".update(" + state + ")");
    }
}
class ConcreteObserverC implements IObserver {

	ConcreteObserverC() {
        System.out.println("ConcreteObserverC()");
	}

    public void update(String state) {
        System.out.println(this + ".update(" + state + ")");
    }

}
public class ObserverPattern {

    public static void main(String[] args) {
        System.out.println("*** ObserverPattern ***");
        ConcreteSubject subject = new ConcreteSubject();
        subject.attach(new ConcreteObserverA());
        IObserver obsB = new ConcreteObserverB();
        subject.attach(obsB);
        subject.attach(new ConcreteObserverC());

        subject.setState("GREEN");
        subject.setState("YELLOW");
        subject.detach(obsB);
        subject.setState("RED");

    }

}
