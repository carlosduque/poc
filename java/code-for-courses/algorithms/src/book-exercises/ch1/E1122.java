/**
 * 1.1.22 Write a version of BinarySearch that uses the recursive rank() given on page 25 and traces the method calls.
 * Each time the recursive method is called, print the argument values lo and hi, indented by the depth of the
 * recursion.
 * Hint: Add an argument to the recursive method that keeps track of the depth.
 */
import java.util.Arrays;

class BinarySearch {

    public static int rank(int target, int[] a) {
        return rank(target, a, 0, a.length - 1, 1);
    }

    public static int rank(int target, int[] a, int lo, int hi, int indent) {
        int mid = lo + (hi - lo) / 2;
        StdOut.printf("%" + indent + "s lo[%2d] = %2d | mid[%2d] = %2d | hi[%2d] = %2d \n", " ", lo, a[lo], mid, a[mid], hi, a[hi]);
        if (lo > hi) return -1;
        if (target > a[mid]) {
            return rank(target, a, mid + 1, hi, ++indent);
        } else if (target < a[mid]) {
            return rank(target, a, lo, mid - 1, ++indent);
        } else {
            return mid;
        }
    }
}

public class E1122 {

    public static void main(String[] args) {
        int criteria = Integer.parseInt(args[0]);
        In in = new In(args[1]);
        int[] ints = in.readAllInts();
        Arrays.sort(ints);
        StdOut.printf("result: '%d' \n", BinarySearch.rank(criteria, ints));
    }

}
