package o.patterns.demo;

import java.util.ArrayList;
import java.util.List;

/**
 * The Observer Pattern defines a one-to-many
 * dependency between objects so that when one
 * object changes state, all of its dependents are
 * notified and updated automatically.
 */
public class ObserverPattern {

	public static void main(String[] args) {
		ISubject subject = new ConcreteSubject();
		IObserver ob1 = new ConcreteObserverA();
		IObserver ob2 = new ConcreteObserverB();

		subject.registerObserver(ob1);
		subject.registerObserver(ob2);

		subject.changeState();

		System.out.println("subject.removeObserver(" + ob1.getClass().getSimpleName() + ")");
		subject.removeObserver(ob1);
		subject.changeState();
	}

}

interface ISubject {
	public void changeState();
	public void registerObserver(IObserver observer);
	public void removeObserver(IObserver observer);
	public void notifyObservers();
}

interface IObserver {
	public void update();
}

class ConcreteSubject implements ISubject {
	List<IObserver> observers = new ArrayList<IObserver>();

	@Override
	public void registerObserver(IObserver observer) {
		observers.add(observer);
	}

	@Override
	public void removeObserver(IObserver observer) {
		observers.remove(observer);
	}

	@Override
	public void notifyObservers() {
		for(IObserver observer : observers) {
			observer.update();
		}
	}

	@Override
	public void changeState() {
		System.out.println("State changed.");
		notifyObservers();
	}
}

class ConcreteObserverA implements IObserver {
	@Override
	public void update() {
		System.out.println(this.getClass().getSimpleName() + " got the message.");
	}
}

class ConcreteObserverB implements IObserver {
	@Override
	public void update() {
		System.out.println(this.getClass().getSimpleName() + " got the message.");
	}
}