package o.patterns.demo;

/**
 * The Strategy Pattern defines a family of algorithms,
 * encapsulates each one, and makes them interchangeable.
 * Strategy lets the algorithm vary independently from clients that use it.
*/
public class StrategyPattern {
	public static void main(String[] args) {
		StrategyContext ctx;
		ctx = new StrategyContext(new StrategyA());
		ctx.call();

		ctx = new StrategyContext(new StrategyB());
		ctx.call();
	}
}


interface IStrategy {
	public void execute();
}

class StrategyContext {
	private IStrategy strategy;

	StrategyContext(IStrategy strategy) {
		this.strategy = strategy;
	}
	public void call() {
		System.out.println(this.getClass().getSimpleName() + ".call() was called...");
		strategy.execute();
	}
}

class StrategyA implements IStrategy {
	public void execute() {
		System.out.println(this.getClass().getSimpleName() + ".execute() was called.");
	}
}

class StrategyB implements IStrategy {
	public void execute() {
		System.out.println(this.getClass().getSimpleName() + ".execute() was called.");
	}
}
