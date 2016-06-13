public class Wrappers {
  public static void main(String[] args) {
    Integer i1 = new Integer(42);
    Integer i2 = new Integer("42");

    Float f1 = new Float(3.14f);
    Float f2 = new Float("3.14f");

    Character c1 = new Character('c');
    Boolean b = new Boolean("false");

    // The valueOf() methods
    Integer ival1 = Integer.valueOf("101011", 2); // converts 101011
                                                  // to 43 and
                                                  // assigns the value 43 to the
                                                  // Integer object ival1
    System.out.println("Integer.valueOf(\"101011\", 2) " + ival1);

    Float fval1 = Float.valueOf("3.14f");         // assigns 3.14 to the
                                                  // Float object fval1
    System.out.println("Float.valueOf(\"3.14f\") " + fval1);

    System.out.println("i1 " + i1);
    byte b2 = i1.byteValue();                      // convert i1's value to a byte primitive
    System.out.println("i1.byteValue() " + b2);
    short s = i1.shortValue();                    // convert i1's value to a short primitive
    System.out.println("i1.shortValue() " + s);
    double d = i1.doubleValue();                  // yet another of Integer's xxxValue methods
    System.out.println("i1.doubleValue() " + d);

    short shrt = f1.shortValue();
    System.out.println("f1 " + f1);
    System.out.println("f1.shortValue() " + shrt);

    double d4 = Double.parseDouble("3.14");   // convert a String to a primitive
    System.out.println("d4 = " + d4);

    Double d5 = Double.valueOf("3.14");
    System.out.println("d5 instanceof Double " + (d5 instanceof Double));

    long L2 = Long.parseLong("101010", 2);   // binary String to a primitive
    System.out.println("L2 = " + L2);

    long L3 = Long.valueOf("101010", 2);   // binary String to Long object
    System.out.println("L3 value = " + L3);

    // toString
    String dStr = Double.toString(3.14);  // d = "3.14"
    System.out.println(dStr);
    String LStr = "hex = " + Long.toString(254, 16); // s = "hex = fe"
    System.out.println(LStr);

    String s3 = Integer.toHexString(254);
    System.out.println("254 is " + s3);

    String s4 = Long.toOctalString(254);
    System.out.println("254(oct) = " + s4);

  }
}

