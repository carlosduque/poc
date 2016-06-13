/**
 * 1.1.18 Consider the following recursive function:
 * <code>
 * public static int mystery(int a, int b)
 * {
 * if (b == 0) return 0;
 * if (b % 2 == 0) return mystery(a+a, b/2);
 * return mystery(a+a, b/2) + a;
 * }
 * </code>
 * What are the values of mystery(2, 25) and mystery(3, 11)? Given positive integers
 * a and b, describe what value mystery(a, b) computes. Answer the same question, but
 * replace + with * and replace return 0 with return 1.
*/
public class E1118 {
    public static void main(String[] args) {
        int a = Integer.parseInt(args[0]);
        int b = Integer.parseInt(args[1]);
        StdOut.println("mystery(" + a +", " + b + "): " + mystery(a, b));
    }

    public static int mystery(int a, int b) {
        if (b == 0) {
            StdOut.println("(" + a + ", " + b + ") ::return: 0");
            return 0;
        }
        if (b % 2 == 0) {
            int x = mystery(a+a, b/2);
            StdOut.println("(" + a + ", " + b + ") ::return: " + x);
            return x;
        }
        int y = mystery(a+a, b/2) + a;
        StdOut.println("(" + a + ", " + b + ") ::return: " + y);
        return y;
    }
}
