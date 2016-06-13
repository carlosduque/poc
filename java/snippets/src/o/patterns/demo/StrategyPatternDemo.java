package o.patterns.demo;

/**
 * The Strategy Pattern defines a family of algorithms,
 * encapsulates each one, and makes them interchangeable.
 * Strategy lets the algorithm vary independently from clients that use it.
*/
public class StrategyPatternDemo {
	public static void main(String[] args) {
		GameCharacter alcros = new GameCharacter("Alcros");
		System.out.println("Picking up an axe.");
		alcros.setWeapon(new AxeBehavior());
		alcros.fight();

		System.out.println("Found an ancient book.");
		alcros.setWeapon(new AncientBookBehavior());
		alcros.fight();

	}
}


class GameCharacter {
	private WeaponBehavior weapon;
	private String name;
	GameCharacter(String name) {
		this.name = name;
	}
	public void setWeapon(WeaponBehavior weapon) {
		this.weapon = weapon;
	}
	public void fight() {
		weapon.attack();
	}
}

interface WeaponBehavior {
	public void attack();
}
class AxeBehavior implements WeaponBehavior {
	public void attack() {
		System.out.println("Hacking like a madman.");
	}
}

class AncientBookBehavior implements WeaponBehavior {
	public void attack() {
		System.out.println("Summoning something dark with a powerful spell.");
	}
}
