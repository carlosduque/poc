package o;

public class OuterClass {

  public OuterClass() {
    System.out.println("Constructing OuterClass");
  }

  /** Nested classes **/
  // non-static nested class
  class InnerClass {
    public InnerClass() {
      System.out.println("Constructing InnerClass");
    }
  }

  // static nested class
  static class StaticNestedClass {
    public StaticNestedClass() {
      System.out.println("constructing StaticNestedClass");
    }
  }

  public static void main(String[] args) {
    OuterClass outerObj = new OuterClass();
    OuterClass.InnerClass innerObj = outerObj.new InnerClass();
    OuterClass.StaticNestedClass staticNestedObj = new OuterClass.StaticNestedClass();

  }
}

