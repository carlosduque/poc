/**
 * 1.1.11 Write a code fragment that prints the contents of a two-dimensional boolean
 * array, using * to represent true and a space to represent false. Include row and column
 * numbers.
*/
public class E1111 {
    public static void main(String[] args) {
        boolean[][] sample = {{true, true, false, false, false},
                              {false, false, false, false, true},
                              {false, true, false, true, false},
                              {false, false, false, true, true},
                              {false, true, false, true, false}};
        char sym = 'x';
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                sym = sample[i][j] ? '*' : ' ';
                StdOut.print(sym);
            }
            StdOut.println();
        }
    }
}
