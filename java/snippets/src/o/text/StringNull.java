/**
 *
 */
package o.text;

/**
 * @author a07942a
 * @version Sep 17, 2014
 *
 */
public class StringNull {

    /**
     * @param args
     */
    public static void main(String[] args) {
        String str = "str";
        Object obj = null;
        String newStr = str + obj;
        System.out.println(str);
        System.out.println(obj);
        System.out.println(newStr);

    }

}
