/*
* 1.1.13 Write a code fragment to print the transposition (rows and columns changed)
 * of a two-dimensional array with M rows and N columns.
*/
import java.lang.Integer;

public class E1113 {
    public static void print(int[][] a) {
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[0].length; j++) {
                StdOut.print(a[i][j] + " ");
            }
            StdOut.println();
        }
        StdOut.println();
    }

    public static int[][] transpose(int[][] t) {

        int m = t.length;
        int n = t[0].length;
        int[][] tmp = new int[n][m];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                tmp[j][i] = t[i][j];
            }
        }
        return tmp;
    }

    public static void main(String[] args) {
        int[][] matrix = {{29, 31},
                          {4, 98},
                          {23, 47},
                          {67, 61},
                          {23, 76}};

        StdOut.println("## original");
        print(matrix);
        StdOut.println("## transpose");
        print(transpose(matrix));
    }
}
