package o.threads.util;

import o.beans.GameCharacter;
import o.beans.Man;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ManTest {
    GameCharacter man;

    @Before
    public void setup() {
        man = new Man("Alcros");
    }

    @After
    public void cleanup() {
        man = null;
    }

    @Test
    public void testCharacterStatus() {
        System.out.println(man.toString());
        assertEquals(2, man.getSpeed());
        System.out.println("incrementing speed");
        man.incrementSpeed();
        assertEquals(7, man.getSpeed());
        System.out.println(man.toString());
        System.out.println("adding a weapon");
        man.addWeapon("BowAndArrow");
        assertNotNull(man.getWeapons());
        assertTrue(man.getWeapons().size() > 0);
        System.out.println(man.toString());
        man.move();
    }
}

