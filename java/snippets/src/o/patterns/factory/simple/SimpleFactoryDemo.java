package o.patterns.factory.simple;

interface Product {
	void describe();
}

class ProductA implements Product {
	public void describe() {
		System.out.println("I'm product A");
	}
}

class ProductB implements Product {
	public void describe() {
		System.out.println("I'm product B");
	}
}

class SimpleFactory {
	Product createProduct(String type) {

		Product product = null;

		if(type.equals("A")) {
			product = new ProductA();
		} else if(type.equals("B")) {
			product = new ProductB();
		}

		return product;
	}
}
public class SimpleFactoryDemo {

	public static void main(String[] args) {
		SimpleFactory factory = new SimpleFactory();
		Product p1 = factory.createProduct("A");
		Product p2 = factory.createProduct("B");
		
		p1.describe();
		p2.describe();
	}

}
