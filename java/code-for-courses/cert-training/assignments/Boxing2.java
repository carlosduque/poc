public class Boxing2 {
  static Integer x;
  public static void main(String[] args) {
    doStuff(x);  // throws NullPointerException, x doesn't refer to an Integer object
  }
  static void doStuff(int z) {
    int z2 = 5;
    System.out.println(z2 + z);
  }
}

