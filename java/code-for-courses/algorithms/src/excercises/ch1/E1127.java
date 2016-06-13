/**
 * 1.1.27 Binomial distribution. Estimate the number of recursive calls that would be used by the code
 *
 * public static double binomial(int N, int k, double p) {
 *    if ((N == 0) && (k == 0)) return 1.0;
 *    if (N < 0 || k < 0) return 0.0;
 *    return (1.0 - p) * binomial(N-1, k, p) + p * binomial(N-1, k-1, p);
 * }
 *
 * to compute binomial(100, 50).
 *
 * Develop a better implementation that is based on saving computed values in an array.
 **/
class Binomial {
    public static double binomial(int N, int k, double p) {
        StdOut.printf("N = %d, k = %d, p = %.2f \n", N, k, p);
       if ((N == 0) && (k == 0)) return 1.0;
       if (N < 0 || k < 0) return 0.0;
       return (1.0 - p) * binomial(N-1, k, p) + p * binomial(N-1, k-1, p);
    }
}

public class E1127 {
    public static void main(String... args) {
        int a = Integer.parseInt(args[0]);
        int b = Integer.parseInt(args[1]);
        double c = Double.parseDouble(args[2]);

        StdOut.printf("binomial(%d, %d, %d): %d \n", a, b, c, Binomial.binomial(a, b, c));
    }
}

