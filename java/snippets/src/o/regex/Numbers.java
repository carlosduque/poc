/**
 *
 */
package o.regex;

/**
 * @author a07942a
 * @version Jul 11, 2013
 *
 */
public class Numbers {
    public static void main(String[] args) {
        System.out.println(isNumber("20130525"));
        System.out.println(isNumber("201X0525"));
        System.out.println(isNumber(""));
        System.out.println(isNumber(" "));
        System.out.println(isNumber("-1"));
    }

    private static boolean isNumber(String str) {
        return str.matches("\\d+");
    }

}
