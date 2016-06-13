package o.threads.util;

import o.beans.Armory;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ArmoryTest {
  private Armory armory;

  @Before
  public void setup() {
      armory = new Armory();
  }

  @After
  public void cleanup() {
      armory = null;
  }
  
  @Test
  public void testCheckingOutWeapon() {
    assertNotNull(armory.get());
    assertNotNull(armory.get());
    assertNotNull(armory.get());
    assertNotNull(armory.get());
    assertNotNull(armory.get());
    assertNotNull(armory.get());
    assertNotNull(armory.get());
  }
}

