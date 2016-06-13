package o.patterns.demo;

/**
 * The Decorator Pattern attaches additional
 * responsibilities to an object dynamically.
 * Decorators provide a flexible alternative to
 * subclassing for extending functionality.
 */
public class DecoratorPattern {
	
	public static void main(String[] args) {
		Component c1 = new ConcreteComponentX();
		c1.operation();
		System.out.println();
		c1 = new ConcreteDecoratorA(c1);
		c1.operation();
		System.out.println();
		c1 = new ConcreteDecoratorB(c1);
		c1.operation();
		System.out.println();
		c1 = new ConcreteComponentY();
		c1.operation();
		System.out.println();
		c1 = new ConcreteDecoratorB(new ConcreteDecoratorA(new ConcreteDecoratorB(c1)));
		c1.operation();
	}

}

abstract class Component {
	public abstract void operation();
}

class ConcreteComponentX extends Component {
	public void operation() {
		System.out.print(this.getClass().getSimpleName() + ".operation()");
	}
}

class ConcreteComponentY extends Component {
	public void operation() {
		System.out.print(this.getClass().getSimpleName() + ".operation()");
	}
}

abstract class Decorator extends Component {
	public abstract void operation();
}

class ConcreteDecoratorA extends Decorator {
	
	Component component;
	
	ConcreteDecoratorA(Component wrappedObj) {
		this.component = wrappedObj;
	}

	@Override
	public void operation() {
		component.operation();
		System.out.print(this.getClass().getSimpleName() + ".operation() ");
	}	
}

class ConcreteDecoratorB extends Decorator {
	
	Component component;
	
	ConcreteDecoratorB(Component wrappedObj) {
		this.component = wrappedObj;
	}

	@Override
	public void operation() {
		component.operation();
		System.out.print(this.getClass().getSimpleName() + ".operation() ");
	}	
}	
