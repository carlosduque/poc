/**
 * 1.1.24 Give the sequence of values of p and q that are computed when Euclid’s algorithm is used to compute the
 * greatest common divisor of 105 and 24. Extend the code given on page 4 to develop a program Euclid that takes two
 * integers from the command line and computes their greatest common divisor, printing out the two arguments for each 
 * call on the recursive method. Use your program to compute the greatest common divisor or 1111111 and 1234567.
 **/
class Euclid {
    public static int gcd(int p, int q) {
        StdOut.printf("p = %7d, q = %7d \n", p, q);
        if (q == 0) return p;
        int r = p % q;
        return gcd(q, r);
    }
}

public class E1124 {
    public static void main(String... args) {
        int p = Integer.parseInt(args[0]);
        int q = Integer.parseInt(args[1]);
        StdOut.printf("greatest common divisor for %d and %d is '%d' \n", p, q, Euclid.gcd(p, q));
    }
}
