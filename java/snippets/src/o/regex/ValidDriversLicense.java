/**
 *
 */
package o.regex;

/**
 * @author a07942a
 * @version Jul 11, 2013
 *
 */
public class ValidDriversLicense {
    public static void main(String[] args) {
        System.out.println(isValidDriversLicense("someWord"));
        System.out.println(isValidDriversLicense(""));
        System.out.println(isValidDriversLicense("1234567"));
        System.out.println(isValidDriversLicense("12345*7"));
        System.out.println(isValidDriversLicense("1-23 45*7"));
        System.out.println(isValidDriversLicense("12345 7"));
        System.out.println(isValidDriversLicense("12345-7"));
        System.out.println(isValidDriversLicense("12345:7"));
        System.out.println(isValidDriversLicense("12345|"));
        System.out.println(isValidDriversLicense("12345(7"));
        System.out.println(isValidDriversLicense("12345)7"));
        System.out.println(isValidDriversLicense("     "));
        System.out.println(isValidDriversLicense("*****"));
        System.out.println(isValidDriversLicense("-----"));
        System.out.println(isValidDriversLicense("1234567890123456789012"));
    }

    private static boolean isValidDriversLicense(String str) {
        System.out.format("'%s': ", str);
        return str.matches("(([a-zA-Z0-9\\s*-]{1,21}){1})?");
    }

}
