/**
 *
 */
package o.text;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author a07942a
 * @version May 24, 2014
 *
 */
public class Hex {

    /**
     * Takes a byte array and returns as a hex string
     *
     * @param input the original string
     * @return the hex string
     */
    public static String toHex(byte[] input) {
        assert input.length > 0;
        String encodedKey = String.format("%032x", new BigInteger(1, input));
        encodedKey = encodedKey.replaceAll("\\x0d|\\x0a", "");

        return encodedKey;
    }

    public byte[][] getHexSplits(String startKey, String endKey, int numRegions) {
        byte[][] splits = new byte[numRegions-1][];
        BigInteger lowestKey = new BigInteger(startKey, 16);
        BigInteger highestKey = new BigInteger(endKey, 16);
        BigInteger range = highestKey.subtract(lowestKey);
        BigInteger regionIncrement = range.divide(BigInteger.valueOf(numRegions));
        lowestKey = lowestKey.add(regionIncrement);
        for(int i=0; i < numRegions-1;i++) {
          BigInteger key = lowestKey.add(regionIncrement.multiply(BigInteger.valueOf(i)));
          byte[] b = String.format("%016x", key).getBytes();
          splits[i] = b;
        }
        return splits;
      }

    /**
     * Takes a byte array and creates an md5 hash
     *
     * @param input the original string
     * @return the md5 hash
     */
    public byte[] md5Hash(byte[] input) {
        assert input.length > 0;
        MessageDigest md = null;
        byte[] byteData = null;

        try {
            md = MessageDigest.getInstance("MD5");
            byteData = md.digest(input);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return byteData;
    }
    /**
     * @param args
     * @throws UnsupportedEncodingException 
     */
    public static void main(String[] args) throws UnsupportedEncodingException {
        String str = "hola terrícolas";
        String hexed = "";
        Hex hex = new Hex();
        System.out.println(str);
        hexed = hex.toHex(str.getBytes("UTF-8"));
        System.out.println(hexed);

        str = "aut viam inveni";
        System.out.println(str);
        hexed = hex.toHex(hex.md5Hash(str.getBytes("UTF-8")));
        System.out.println(hexed);
        
        str = "000000000000000";
        System.out.println(str);
        hexed = hex.toHex(hex.md5Hash(str.getBytes("UTF-8")));
        System.out.println(hexed);
        
        str = "000000000000001";
        System.out.println(str);
        hexed = hex.toHex(hex.md5Hash(str.getBytes("UTF-8")));
        System.out.println(hexed);

        str = "000000000000002";
        System.out.println(str);
        hexed = hex.toHex(hex.md5Hash(str.getBytes("UTF-8")));
        System.out.println(hexed);

        byte[][] a = hex.getHexSplits("0000000000000000", "ffffffffffffffff", 18);
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a.length; j++)
                System.out.format("a[%d][%d] = %s  ", i, j, a[i][j] );
            System.out.println();
        }
    }

}
