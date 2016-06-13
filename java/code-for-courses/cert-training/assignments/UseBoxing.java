public class UseBoxing {
  public static void main(String[] args) {
    UseBoxing u = new UseBoxing();
    u.go(5);
  }
  boolean go(Integer i) {   // boxes the int it was passed
    Boolean ifSo = true;    // boxes the literal
    Short s = 3000;         // boxes the primitive
    if (ifSo) {             // unboxing
      System.out.println(++s);  // unboxes, increments, reboxes
    }
    return !ifSo;           // unboxes, returns the inverse
  }
}
