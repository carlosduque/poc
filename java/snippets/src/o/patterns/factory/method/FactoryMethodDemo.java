package o.patterns.factory.method;

abstract class Product {
	protected String name = null;
	abstract void describe();
	public void process() {
		describe();
	}
}

class ProductAlpha extends Product {
	ProductAlpha() {
		name = "Alpha";
	}
	public void describe() {
		System.out.println("Hooray, I'm " + name);
	}
}

class ProductBeta extends Product {
	ProductBeta() {
		name = "Beta";
	}
	public void describe() {
		System.out.println("Yay, I'm " + name);
	}
}

class ProductOne extends Product {
	ProductOne() {
		name = "One";
	}
	public void describe() {
		System.out.println("Yoohoo, I'm " + name);
	}
}

class ProductTwo extends Product {
	ProductTwo() {
		name = "Two";
	}
	public void describe() {
		System.out.println("Yoooo, I'm " + name);
	}
}


abstract class AbstractCreator {

	protected String name;
	abstract Product factoryMethod(String type);
	
	Product operation(String type) {
		System.out.println("Factory " + name + " is creating a product of type " + type);
		// using the abstract method which should be implemented by 
		// some other concrete class
		Product p = factoryMethod(type);
		System.out.println("Created Product: " + p.name);
		return p;
	}
}

class LetterCreator extends AbstractCreator {
	LetterCreator() {
		name = "LetterCreator";
	}
	@Override
	Product factoryMethod(String type) {
		Product product = null;

		if(type.equals("A")) {
			product = new ProductAlpha();
		} else if(type.equals("B")) {
			product = new ProductBeta();
		}

		return product;
	}
}

class NumberCreator extends AbstractCreator {
	NumberCreator() {
		name = "NumberCreator";
	}
	@Override
	Product factoryMethod(String type) {
		Product product = null;

		if(type.equals("1")) {
			product = new ProductOne();
		} else if(type.equals("2")) {
			product = new ProductTwo();
		}
		return product;
	}
}

public class FactoryMethodDemo {

	public static void main(String[] args) {
		AbstractCreator creator = new LetterCreator();

		Product a = creator.operation("A");
		a.describe();
		
		Product b = creator.operation("B");
		b.describe();
		
		creator = new NumberCreator();

		Product o = creator.operation("1");
		o.describe();
		
		Product t = creator.operation("2");
		t.describe();

	}

}
