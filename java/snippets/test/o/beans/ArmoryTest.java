package o.beans;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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
	    assertNull(armory.get());
	  }

}
