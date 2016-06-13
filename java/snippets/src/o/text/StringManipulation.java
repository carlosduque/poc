/**
 *
 */
package o.text;

/**
 * @author a07942a
 * @version May 29, 2014
 *
 */
public class StringManipulation {

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {
        String str = "1234567890";

        System.out.println("'" + String.format("%-25s", str) + "'");
        System.out.println("'" + String.format("%-5s", str) + "'");
        System.out.println("'" + String.format("%02d", 9) + "'");
        System.out.println("'" + String.format("%020d", 13) + "'");
        System.out.println("'" + String.format("%06d", 321569) + "'");
    }

}
