/**
 * 1.1.16 Give the value of exR1(6):
 * <code>
 * public static String exR1(int n) {
 * if (n <= 0) return "";
 * return exR1(n-3) + n + exR1(n-2) + n;
 * }
 * </code>
*/
public class E1116 {
    public static void main(String[] args) {
        int num = Integer.parseInt(args[0]);
        StdOut.println("exR1(" + num + "): " + exR1(num));
    }

    public static String exR1(int n) {
        if (n <= 0) return "";
        return exR1(n-3) + n + exR1(n-2) + n;
    }
}
