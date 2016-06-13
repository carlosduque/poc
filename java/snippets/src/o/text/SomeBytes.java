/**
 *
 */
package o.text;

import java.io.UnsupportedEncodingException;

/**
 * @author a07942a
 * @version May 24, 2014
 *
 */
public class SomeBytes {
    public void from(byte[] input) {
        System.out.println(input);
        System.out.println();

        System.out.println(new String(input));
    }

    /**
     * @param args
     * @throws UnsupportedEncodingException 
     */
    public static void main(String[] args) throws UnsupportedEncodingException {
        SomeBytes sb = new SomeBytes();
        sb.from("some text".getBytes());
    }

}
