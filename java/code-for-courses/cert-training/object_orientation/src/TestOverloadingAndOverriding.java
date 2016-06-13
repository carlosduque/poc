
class Parent {
  public int m1(int a) {
    return a;
  }

  public float m2(float b) throws IllegalArgumentException {
    if (b < 0.0) {
      throw new IllegalArgumentException("Cannot be lower than 0.0");
    }
    return b; 
  }
}

class Child extends Parent {

}

public class TestOverloadingAndOverriding {
  public static void main(String[] args) {
    Parent p = new Parent();
    System.out.println("p.m1(4): " + p.m1(4));
    System.out.println("p.m2(4.4): " + p.m2(4.4f));

    Child c = new Child();
    System.out.println("c.m1(6): " + c.m1(6));
    System.out.println("c.m2(4): " + c.m2(4f));

  }
}

