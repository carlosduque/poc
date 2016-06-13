/*
 * 1.1.15 Write a static method histogram() that takes an array a[] of int values and
 * an integer M as arguments and returns an array of length M whose ith entry is the number
 * of times the integer i appeared in the argument array. If the values in a[] are all
 * between 0 and M–1, the sum of the values in the returned array should be equal to
 * a.length.
*/
public class E1115 {

    public static void main(String[] args) {
        int[] sample = {1, 2, 1, 3, 5, 4, 2, 1, 5, 5};
        int[] x = histogram(sample, 12);
        for (int i = 0; i < x.length; i++) {
            StdOut.println("x[" + i + "]: " + x[i]);
        }
    }

    public static int[] histogram(int[] a, int m) {
        int[] ary = new int[m];
        for (int i = 0; i < a.length; i++) {
            ary[a[i]]++;
        }
        return ary;
    }

}
