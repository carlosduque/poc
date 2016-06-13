/**
 * 1.1.30 Array exercise. Write a code fragment that creates an N-by-N boolean array a[][] such that a[i][j] is true if
 * i and j are relatively prime (have no common factors), and false otherwise.
 **/
import java.util.Arrays;

public class E1130 {

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        boolean[][] a = new boolean[n][n];
        for (int i = 0; i < a.length; i++)
            for (int j = 0; j < a.length; j++) {
                a[i][j] = relativePrime(i, j);
            }
        print(a);
    }

    private static boolean relativePrime(int x, int y) {
        return 1 == gcd(x, y);
    }

    private static int gcd(int a, int b) {
        if (b == 0) return a;
        return gcd(b, a % b);
    }

    private static void print(boolean[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
                if (i == 0) printTopRow(matrix.length);
            for (int j = 0; j < matrix.length; j++) {
                if (j == 0) StdOut.printf("a[%2d][]\t", i);
                StdOut.printf("%6s\t", matrix[i][j] == true ? "TRUE" : "FALSE");
            }
            StdOut.println();
        }
    }
    private static void printTopRow(int len) {
        StdOut.printf("%8s", " ");
        for (int i = 0; i < len; i++) {
            StdOut.printf("a[][%2s]\t", i);
        }
        StdOut.println();
    }

}
