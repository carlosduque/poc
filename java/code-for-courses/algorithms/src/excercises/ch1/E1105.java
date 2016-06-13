/*
 * 1.1.5 Write a code fragment that prints true if the double variables x and y are both
 * strictly between 0 and 1 and false otherwise.
*/
public class E1105 {
    public static boolean check(double num) {
        if (num > 0.0 && num < 1.0)
            return true;
        return false;
    }
    public static void main(String[] args) {
        double x = Double.parseDouble(args[0]);
        double y = Double.parseDouble(args[1]);
        StdOut.println(check(x) && check(y));
    }
}
