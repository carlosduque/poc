package o.beans;

import java.util.LinkedList;
import java.util.Queue;

public class Armory {
    private Queue<String> inventory;
    public Armory() {
        inventory = new LinkedList<String>();
        inventory.add("BowAndArrow");
        inventory.add("Axe");
        inventory.add("Sword");
        inventory.add("BookOfSpells");
        inventory.add("LongHook");
        inventory.add("Shield");
        inventory.add("MagicRing");
    }
    public String get() {
         return inventory.poll();
    }

}
