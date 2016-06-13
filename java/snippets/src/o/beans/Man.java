package o.beans;

import o.beans.GameCharacter;

public class Man extends GameCharacter {
    public Man(String name) {
        super(name);
    }

    @Override
    public void move() {
        System.out.println(getName() + " moved.");
    }
}
