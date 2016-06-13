/**
 *
 */
package o.text;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Base64;


/**
 * @author a07942a
 * @version May 9, 2014
 *
 */
public class Encoding {

    /**
     * @param args
     */
    public static void main(String[] args) {
        String key = "000000000000002";
        
        MessageDigest md;
        byte[] byteData = null;
        byte[] byteData2 = null;
        String encodedKey = "";
        String encodedUtf8Key = key.toString();

        try {
        md = MessageDigest.getInstance("MD5");
        md.update(key.getBytes());
        byteData = md.digest();
        encodedKey = Base64.encodeBase64String(byteData);
        
        md.update(key.toString().getBytes());
        byteData2 = md.digest();
        encodedUtf8Key = Base64.encodeBase64String(byteData2);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        System.out.println("'"+encodedKey+"'");
        System.out.println("'"+encodedUtf8Key+"'");


    }

}
