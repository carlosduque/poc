class Car {
  private String name = "";
  Car() { name = "unknown"; }
  public String getName() { return name; }
  public void setName(String val) { this.name = val; }
}

class Subaru extends Car { }
class Ferrari extends Car { }

public class Ary {
  public static void main(String[] args) {
    int unidimA [] = {29, 33, 17};  //declare, create and initialize
    int unidimB [] = new int[] {29, 33, 17}; //anonymous array creation
    Car[][] multidim = new Car[3][];
    multidim[0] = new Car[2];
    multidim[0][0] = new Car();
    multidim[0][1] = new Car();
    System.out.println("multidim[0][0] = " + multidim[0][0]);
    System.out.println("multidim[0][1] = " + multidim[0][1]);
    //multidim[1][0] = new Car();
    //multidim[1][1] = new Car();
  
    //Car myCars [] = { new Subaru(), new Car(), new Ferrari() };
  }
}

