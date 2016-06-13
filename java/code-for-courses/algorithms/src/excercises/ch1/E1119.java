/**
 * What is the largest value of N for which this program takes less 1 hour to compute the
 * value of F(N)? Develop a better implementation of F(N) that saves computed values in
 * an array.
 *
 *     <code>
 *     public static long F(int N) {
 *         if (N == 0) return 0;
 *         if (N == 1) return 1;
 *         return F(N-1) + F(N-2);
 *     }
 *
 *     public static void main(String[] args) {
 *         for (int N = 0; N < 100; N++)
 *             StdOut.println(N + " " + F(N));
 *     }
 *     </code>
 */

public class E1119 {
    private static long[] fibs = null;
    public static long F(int N) {
        if (N == 0) {
            fibs[N]= 0;
            return 0;
        }
        if (N == 1) {
            fibs[N-1]= 1;
            return 1;
        }

        fibs[N-1] = F(N-1) + F(N-2);
        return F(N-1) + F(N-2);
    }

    public static void main(String[] args) {
        int num = Integer.parseInt(args[0]);
        StdOut.println(num + "(st/nd/th) fibonacci number.");
        fibs = new long[num];
        long f = F(num);
        for (int i = 0; i < fibs.length; i++) {
            StdOut.println(i + " " + fibs[i]);
        }
    }
}
