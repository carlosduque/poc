/**
 * 1.2.6 A string s is a circular rotation of a string t if it matches when the characters
 * are circularly shifted by any number of positions; e.g., ACTGACG is a circular shift of
 * TGACGAC, and vice versa. Detecting this condition is important in the study of genomic
 * sequences. Write a program that checks whether two given strings s and t are circular
 * shifts of one another. Hint : The solution is a one-liner with indexOf(), length(), and
 * string concatenation.
 **/

public class E1206 {
    public static void main(String... args) {
        String a = args[0].toLowerCase();
        String b = args[1].toLowerCase();
        StdOut.printf("Is circular shift: %s \n", isCircularShift(a, b));
    }

    public static boolean isCircularShift(String s, String t) {
        return t.equals(s.substring((s+s).indexOf(t), s.length()) + s.substring(0, (s+s).indexOf(t)));
    }
}
