/*
 * 1.1.14 Write a static method lg() that takes an int value N as argument and returns
 * the largest int not larger than the base-2 logarithm of N. Do not use Math.
*/

public class E1114 {

    public static void main(String[] args) {
        int input = Integer.parseInt(args[0]);
        StdOut.println("N: " + input);
        StdOut.println("largest int not larger than the base-2 logarithm of N: " + lg(input));
    }

    public static int lg(int n) {
        int num = 0;
        for(int result = n; (result / 2) >= 1; result = result / 2) {
            num++;
        }
        return num;
    }

}
