/**
 *
 */
package o.regex;

/**
 * @author a07942a
 * @version Aug 28, 2014
 *
 */
public class OR {
    public static void main(String[] args) {
        System.out.println(check("create_date"));
        System.out.println(check("create_date_1"));
        System.out.println(check("ssn"));
        System.out.println(check("dob"));
        System.out.println(check("ssn_ssn"));
        System.out.println(check("ssn1"));
    }

    private static boolean check(String str) {
        return str.matches("(.*)create(.*)||(.*)ssn(.*)");
    }
}
