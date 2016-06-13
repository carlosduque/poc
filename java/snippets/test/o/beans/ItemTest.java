package o.beans;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ItemTest {

	  private Item item = null;

	  @Before
	  public void setup() {
	  }

	  @After
	  public void cleanup() {
	    item = null;
	  }

	  @Test
	  public void testItemCounter() {
	    item = new Item();
	    assertTrue(1 == item.getCounter());
	    Item item2 = new Item();
	    assertTrue(2 == item.getCounter());
	    new Item();
	    assertTrue(3 == item.getCounter());
	  }

	  @Test
	  public void testItemMove() {
	    item = new Item(5);
	    System.out.println(item.toString());
	    item.move(2);
	    System.out.println(item.toString());
	    item.move(2);
	    System.out.println(item.toString());
	    item.move(2);
	    System.out.println(item.toString());
	  }

}
