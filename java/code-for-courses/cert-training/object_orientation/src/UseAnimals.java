class Animal {}
class Horse extends Animal {}
public class UseAnimals {
  public void doStuff(Animal a) {
    System.out.println("In the Animal version");
  }

  public void doStuff(Horse h) {
    System.out.println("In the Horse version");
  }

  public static void main (String[] args) {
    UseAnimals ua = new UseAnimals();
    Animal animalObj = new Animal();
    Horse horseObj = new Horse();
    ua.doStuff(animalObj);
    ua.doStuff(horseObj);

    Animal animalRefToHorse = new Horse();
    ua.doStuff(animalRefToHorse);
  }
}
