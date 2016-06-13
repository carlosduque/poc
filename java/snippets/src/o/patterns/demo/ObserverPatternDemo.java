package o.patterns.demo;

import java.util.ArrayList;
import java.util.List;

/**
 * The Observer Pattern defines a one-to-many
 * dependency between objects so that when one
 * object changes state, all of its dependents are
 * notified and updated automatically.
 */
public class ObserverPatternDemo {

	public static void main(String[] args) {
		News latestNews = new LatestNews();
		
		Subscriber s = new CommonSubscriber("Lois Lane");
		latestNews.registerSubscriber(new CommonSubscriber("Clark Kent"));
		latestNews.registerSubscriber(s);
		latestNews.registerSubscriber(new CommonSubscriber("Lex Luthor"));
		
		latestNews.setMessage("Kurt is dead.");
		System.out.println();
		System.out.println("Remove subscriber: " + s.getName());
		latestNews.removeSubscriber(s);
		System.out.println();
		latestNews.setMessage("Darth Vader is Luke's father.");
	}

}

interface News {
	public void setMessage(String message);
	public void registerSubscriber(Subscriber subscriber);
	public void removeSubscriber(Subscriber subscriber);
	public void notifySubscribers();
}

interface Subscriber {
	public void update(String message);
	public String getName();
}

class LatestNews implements News {
	List<Subscriber> subscribers = new ArrayList<Subscriber>();
	private String message = "No new messages.";

	@Override
	public void registerSubscriber(Subscriber subscriber) {
		subscribers.add(subscriber);
	}

	@Override
	public void removeSubscriber(Subscriber subscriber) {
		subscribers.remove(subscriber);
	}

	@Override
	public void notifySubscribers() {
		for(Subscriber subscriber : subscribers) {
			subscriber.update(message);
		}
	}

	@Override
	public void setMessage(String message) {
		this.message = message;
		notifySubscribers();
	}
}

class CommonSubscriber implements Subscriber {
	private String name;
	CommonSubscriber(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}

	@Override
	public void update(String message) {
		System.out.println(this.getName() + " got the message that \"" + message + "\"");
	}
}
