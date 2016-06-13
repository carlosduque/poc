package o.binary.otp;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.security.SignatureException;

import o.binary.otp.OTPGenerator;

import org.junit.Before;
import org.junit.Test;

public class OTPGeneratorTest {
    private static final String key = "THESECRETPOWERISTHEFLUXCAPACITOR";
    OTPGenerator sut = null;

    @Before
    public void setUp() throws Exception {
        sut = new OTPGenerator(Base32String.decode(key));
    }

    @Test
    public void testCodeGeneration() throws SignatureException {
        assertThat(sut.generateCode(1234), is("862017"));
        assertThat(sut.generateCode(1235), is("393194"));
        assertThat(sut.generateCode(1236), is("980761"));
        assertThat(sut.generateCode(1237), is("643008"));
    }

}
