/**
 *
 */
package o.flowcontrol;

/**
 * @author a07942a
 * @version May 8, 2014
 *
 */
public class IfDemo {

    private static String name = "pin";
    private static String value = "";
    private static long numOfSsn = 0;
    private static long numOfDob = 0;
    private static long numOfPhones = 0;

    /**
     * 
     * @param args
     */
    public static void main(String[] args) {
        onlyIf();
        ifAndElse();
    }
    
    private static void onlyIf() {
        if (name.equals("street_address_1") ) {
            System.out.println("match");
        }
        if (name.equals("pin")) {
            System.out.println("found pin");
        }
        if (name.equals("phone_number_25") ) {
            System.out.println("match");
        }
        if (name.equals("city") ) {
            System.out.println("match");
        }
        if (name.equals("unit_1") ) {
            System.out.println("match");
        }
    }
    
    private static void ifAndElse() {
        if (name.equals("street_address_1")) {
            System.out.println("match");
        } else if (name.equals("pin") ) {
            System.out.println("found pin");
        } else if (name.equals("phone_number_25") ) {
            System.out.println("match");
        } else if (name.equals("city") ) {
            System.out.println("match");
        } else if (name.equals("unit_1") ) {
            System.out.println("match");
        }
    }

}
