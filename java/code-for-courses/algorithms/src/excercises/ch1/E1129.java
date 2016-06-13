/**
 * 1.1.29 Equal keys. Add to BinarySearch a static method rank() that takes a key and
 * a sorted array of int values (some of which may be equal) as arguments and returns
 * the number of elements that are smaller than the key and a similar method count()
 * that returns the number of elements equal to the key. Note : If i and j are the
 * values returned by rank(key, a) and count(key,a) respectively, then a[i..i+j-1] are the values in the array that are
 **/
import java.util.Arrays;

class BinarySearchFindSmaller {

    public static int rank(int key, int[] a) {
        return rank(key, a, 0, a.length - 1);
    }

    public static int count(int key, int[] a) {
        int num = rank(key, a, 0, a.length - 1);
        if (num > 0) return a.length - rank(key, a, 0, a.length - 1);
        else return -1;
    }

    public static int rank(int key, int[] a, int lo, int hi) {
        int mid = lo + (hi - lo) / 2;
        StdOut.printf("lo[%2d] = %2d | mid[%2d] = %2d | hi[%2d] = %2d \n", lo, a[lo], mid, a[mid], hi, a[hi]);
        if (mid <= 0 || (lo == mid && mid == hi) && key != a[mid]) return -1;
        if (key > a[mid]) {
            return rank(key, a, mid + 1, hi);
        } else if (key < a[mid]) {
            return rank(key, a, lo, mid - 1);
        } else if (key == a[mid] && key == a[mid - 1]) {
            return rank(key, a, 0, mid - 1);
        } else {
            return mid;
        }
    }
}

public class E1129 {

    public static void main(String[] args) {
        int criteria = Integer.parseInt(args[0]);
        In in = new In(args[1]);
        int[] ints = in.readAllInts();
        Arrays.sort(ints);
        StdOut.printf("found @ %d \n", BinarySearchFindSmaller.rank(criteria, ints));
        StdOut.printf("count: %d \n", BinarySearchFindSmaller.count(criteria, ints));
    }

}
