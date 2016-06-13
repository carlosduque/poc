/**
 * 1.1.20 Write a recursive static method that computes the value of ln(N!)
 */

import java.lang.Math;

public class E1120 {

    public static void main(String[] args) {
        int num = Integer.parseInt(args[0]);
        StdOut.printf("lgFactorial(%d) = %f%n", num, lgFactorial(num));
    }

    public static double lgBase2(double n) {
        StdOut.printf("lgBase2(%f)%n", n);
        return Math.log(n) / Math.log(2.0);
    }

    public static double lgFactorial(double n) {
        StdOut.printf("lgFactorial(%f)%n", n);
        if (n == 1) return lgBase2(n);
        return lgFactorial(n-1) + lgBase2(n);
    }

}
