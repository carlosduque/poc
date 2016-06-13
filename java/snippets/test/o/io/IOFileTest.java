package o.io;

import static org.junit.Assert.*;

import org.junit.Test;
import java.io.File;

public class IOFileTest {

  @Test
  public void testFileInfo() {
    File f = new File("src/test/resources/quangle.txt");
    assertTrue(f.exists());
    assertTrue(f.length() > 0);
    assertTrue(f.isFile());
    assertFalse(f.isHidden());
  }

}

