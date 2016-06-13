package o.binary.otp;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.Arrays;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import o.binary.otp.Base32String.DecodingException;

public class OTPGenerator {

    private static final String HMAC_SHA1_ALGORITHM = "HmacSHA1";
    /** Default decimal passcode length */
    private static final int MIN_PASS_CODE_LENGTH = 6;
    private static final int MAX_PASSCODE_LENGTH = 9;
    /** Powers of 10 used to shorten the pin to the desired number of digits */

    byte[] secret;
    int passwordLength = MIN_PASS_CODE_LENGTH;

    public OTPGenerator(byte[] secret) {
        this.secret = Arrays.copyOf(secret, secret.length);
    }

    /**
     * Generate an HMAC based One-time password.
     * @see <a href="http://www.rfc-editor.org/info/rfc4226">http://www.rfc-editor.org/info/rfc4226</a>
     * @param movingFactor
     * @return
     * @throws SignatureException
     */
    public String generateCode(long movingFactor) throws SignatureException {
        byte[] digest = null;
        try {
            digest = hmacSha1(movingFactor);
        } catch (Exception e) {
            throw new SignatureException("Failed to generate HMAC : " + e.getMessage());
        }

        return dynamicTruncation(digest);
    }

    /**
     * @param factor the moving factor that must be hashed
     * @return the digest
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     */
    private byte[] hmacSha1(long factor) throws NoSuchAlgorithmException, InvalidKeyException {
        ByteBuffer byteBufferMovingFactor = ByteBuffer.allocate(8);
        byteBufferMovingFactor.putLong(factor);

        SecretKeySpec signingKey = new SecretKeySpec(secret, HMAC_SHA1_ALGORITHM);
        Mac mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
        mac.init(signingKey);

        return mac.doFinal(byteBufferMovingFactor.array());
    }

    /**
     * Extract a 4-byte dynamic binary code from a 160-bit (20-byte) HMAC-SHA-1 result.
     * @param digest the hmac-sha-1 result
     * @return
     */
    private String dynamicTruncation(byte[] digest) {
        //The value of the lower 4 bits of the digest.
        int offset = digest[digest.length - 1] & 0xf;
        //The value located at the offset
        int binary = 0;

        //The reason for masking the most significant bit is to avoid
        //confusion about signed vs. unsigned modulo computations. (& 0x7f)
        binary = (digest[offset] & 0x7f) << 24;

        binary |= (digest[offset + 1] & 0xff) << 16;
        binary |= (digest[offset + 2] & 0xff) << 8;
        binary |= (digest[offset + 3] & 0xff);

        // Create digits divisor
        int divisor = 1;
        for (int i = passwordLength; i > 0; i--)
            divisor *= 10;

        binary = binary % divisor;
        return String.format("%0" + passwordLength + "d", binary);
    }

    /**
     * @param passwordLength the passwordLength to set
     */
    public void setPasswordLength(int passwordLength) {
        if ((passwordLength < MIN_PASS_CODE_LENGTH) || (passwordLength > MAX_PASSCODE_LENGTH)) {
            throw new IllegalArgumentException("passwordLength must be between " + MIN_PASS_CODE_LENGTH
                      + " and " + MAX_PASSCODE_LENGTH + " digits.");
        }
        this.passwordLength = passwordLength;
    }

    public static void main(String[] args) throws SignatureException, UnsupportedEncodingException, DecodingException {
        String secret = "THESECRETPOWERISTHEFLUXCAPACITOR";
        //String secret = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";

        table(new OTPGenerator(Base32String.decode(secret)));
    }

    private static void table(OTPGenerator gen) throws SignatureException, DecodingException {
        System.out.println("secret: " + gen);
        for (int i = 1234; i < 1238; i++) {
            System.out.printf("counter: %d --> %s%n", i, gen.generateCode(i));
        }
        System.out.println();
    }
}
