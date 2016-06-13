package o.xml.jaxb;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Animal {
	private String name;
	public void setName(String n) { this.name = n; }
	public String toString() { return name; }
}
class Dog extends Animal {
	public String toString() { return super.toString() + " says woof" ; }
}
class Cat extends Animal {
	public String toString() { return super.toString() + " says meow" ; }
}
class Fish extends Animal {
	public String toString() { return super.toString() + " says gulp" ; }
}
class Person {
	private String name;
	private List<Animal> pets = new ArrayList<Animal>();
	public Person(String n) { this.name = n; }
	public void add(Animal a) {
		pets.add(a);
	}
	public String toString() {
		StringBuilder b = new StringBuilder();
		for (Animal a : pets) {
			b.append(a + ",");
		}
		return name + ": " + b.toString();
	}
}
public class BenchmarkJaxbMain {

	private static String[] personNames = {"Melissa","Natalia","Amelia","Lucas","Carlos"};
	private static String[] petNames = {"Suppi Missifu","Chipper","Wercholis","Loi","Manfred"};
	private static Class<?>[] animals = { Dog.class, Cat.class, Fish.class };

	static Random r = new Random();
	public static void main(String[] args) throws InstantiationException, IllegalAccessException {
		Person p;
		for (int i = 0; i < 5; i++) {
			p = new Person(personNames[r.nextInt(personNames.length)]);
			for (int j = 0; j < 2; j++) {
				Animal a = (Animal) animals[r.nextInt(animals.length)].newInstance();
				a.setName(petNames[r.nextInt(petNames.length)]);
				p.add(a);
			}
			System.out.println(p);
		}
	}

}
