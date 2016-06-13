package o.io;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.After;
import org.junit.Before;
import java.io.File;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;

public class IOFileInputStreamTest {

  private final Map<String, File> files = new HashMap<String, File>();
  private File file;

  @Before
  public void setup() {
    files.put("quangle", new File("src/test/resources/quangle.txt"));
    files.put("git", new File("src/test/resources/git.pdf"));
    files.put("white-noise", new File("src/test/resources/white-noise.mp3"));
    files.put("thundercats", new File("src/test/resources/thundercats.jpg"));
  }

  @After
  public void cleanup() {
    file = null;
  }

  @Test
  public void testInputStreamAvailable() throws IOException {
    System.out.println("== Testing available bytes");
    file = files.get("quangle");
    InputStream is = new FileInputStream(file);
    System.out.format("%d bytes to read before blocking%n", is.available());
    assertTrue(is.available() > 0);
    is.close();
  }

  @Test
  public void testFileInputStreamRead() throws IOException {
    System.out.println("== Reading 1 byte at a time");
    file = files.get("quangle");
    InputStream is = new FileInputStream(file);
    int abyte;
    while((abyte = is.read()) != -1) {
      System.out.format("byte: %d  -  as char: %s%n", abyte, (char)abyte);
    }
    is.close();
  }

  @Test
  public void testInputStreamRead() throws IOException {
    System.out.println("== Reading 64 bytes at a time");
    byte[] buff = new byte[64];
    file = files.get("quangle");
    InputStream is = new FileInputStream(file);
    StringBuffer sb = new StringBuffer();
    int i;
    while((i = is.read(buff)) != -1) {
      sb.append(new String(buff, 0, i));
      System.out.format("%d bytes read %n", i);
      System.out.format("  -> %s %n%n", sb.toString());
    }
    is.close();
  }

  @Test
  public void testMp3IDTagInfo() throws IOException {
    System.out.println("== MP3 Tag Info");
    byte[] buff = new byte[3];
    file = files.get("white-noise");
    StringBuffer sb = new StringBuffer();
    InputStream is = new FileInputStream(file);
    int i = is.read(buff);
    sb.append(new String(buff, 0, i));
    System.out.format("%d bytes read, buff: %s %n", i, buff);
    System.out.format("  -> %s %n", sb.toString());
    is.close();
  }
}

