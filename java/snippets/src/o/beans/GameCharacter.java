package o.beans;

import java.util.Set;
import java.util.HashSet;

public abstract class GameCharacter {
    private String name;
    private int speed;
    private Set<String> weapons;

    GameCharacter(String newName) {
        name = newName;
        speed = 2;
        weapons = new HashSet<String>();
    }

    @Override
    public String toString() {
        String str = "";
        str += "{ class:" + getClass().getSimpleName() + ", name:" + this.name + ", speed:" + this.speed + " weapons: [";
        for(String weapon : weapons) {
            str += weapon + ",";
        }
        str += "] }";
        return str;
    }

    public void incrementSpeed() {
        incrementSpeed(5);
    }

    public void incrementSpeed(int value) {
        speed = speed + value;
    }

    public void decrementSpeed() {
        decrementSpeed(5);
    }

    public void decrementSpeed(int value) {
        speed = speed - value;
        speed = (speed > 0) ? speed : 0;
    }

    public void addWeapon(String newWeapon) {
        weapons.add(newWeapon);
    }

    public Set<String> getWeapons() {
        return weapons;
    }

    public int getSpeed() {
        return speed;
    }

    public String getName() {
        return name;
    }

    public abstract void move();
}
