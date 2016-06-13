public class Autoboxing {
  public static void main(String[] args) {
    //Before java 5
    Integer y = new Integer(567);   // make it
    int x = y.intValue();           // unwrap it
    x++;                            // use it
    y = new Integer(x);             // re-wrap it
    System.out.println("y = " + y); // print it

    //Java 5
    Integer y2 = new Integer(567);    // make it
    y2++;                             // unwrap it, increment it, rewrap it
    System.out.println("y2 = " + y2); // print it

    Integer a = 567;                 // make a wrapper
    Integer b = a;                   // assign a second ref
                                     // var to THE wrapper
    System.out.println(a == b);      // verify that they refer to the same object
    a++;                             // unwrap, use, "rewrap"
         // equivalent to:
           // int x2 = y.intValue();   // unwrap it
           // x2++;                    // use it
           // y = new Integer(x2);     // re-wrap it
    System.out.println(a + " " + b); // print values
    System.out.println(a == b);      // verify that they refer to different objects

    // Boxing, ==, and equals()
    Integer in1 = 1000;
    Integer in2 = 1000;
    System.out.println("in1 = " + in1 + ", in2 = " + in2);
    if(in1 != in2) System.out.println("- different objects");
    if(in1.equals(in2)) System.out.println("meaningfully equal");

    // (!=, ==) In order to save memory two instances of the following wrapper objects
    // created through boxing will always be == when their primitive values are the same
    // Boolean, Byte, Character, Short and Integer
    Integer in3 = 10;
    Integer in4 = 10;
    System.out.println("in3 = " + in3 + ", in4 = " + in4);
    if(in3 == in4) System.out.println("* same objects");
    if(in3.equals(in4)) System.out.println("meaningfully equal");
  }
}
