package o.collections;

import java.util.HashSet;
import java.util.Set;

public class HashSetDemo {	

	public static void main(String[] args) {
		Set x = new HashSet();		
		x.add(new Cat("uno",1));
		x.add(new Cat("dos",2));
		x.add(new Cat("tres",3));
		x.add(new Cat("cuatro",4));
		x.add(new Cat("cinco",5));
		Pets pets = new Pets("Callejeros", x);		
		
		System.out.println("Pets>> " + pets.getCats());

	}

}
