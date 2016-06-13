package o.patterns.demo;
/**
 * The Decorator Pattern attaches additional
 * responsibilities to an object dynamically.
 * Decorators provide a flexible alternative to
 * subclassing for extending functionality.
 */
public class DecoratorPatternDemo {
	
	public static void main(String[] args) {
		Fighter goku = new Goku();
		Fighter gohan = new Gohan();
		System.out.print("Goku con enseñanzas del abuelo: ");
		System.out.println("Nivel: " + goku.level() + " Ataques: " + goku.attack());

		System.out.print("Gohan con enseñanzas de su mamá: ");
		System.out.println("Nivel: " + gohan.level() + " Ataques: " + gohan.attack());

		System.out.print("Goku luego de entrenar con Muten Roshi: ");
		goku = new KameHameHa(goku);
		System.out.println("Nivel: " + goku.level() + " Ataques: " + goku.attack());

		System.out.print("Goku luego de entrenar con el maestro Karim: ");
		goku = new KaioKen(goku);
		goku = new SuperKameHameHa(goku);
		System.out.println("Nivel: " + goku.level() + " Ataques: " + goku.attack());

		System.out.print("Goku luego de entrenar en el templo del tiempo: ");
		goku = new GenkiDama(goku);
		System.out.println("Nivel: " + goku.level() + " Ataques: " + goku.attack());
		
		System.out.print("Gohan luego de entrenar con Goku: ");
		gohan = new SuperKameHameHa(new Kienzan(gohan));
		System.out.println("Nivel: " + gohan.level() + " Ataques: " + gohan.attack());
	}

}

abstract class Fighter {
	public abstract String attack();
	public abstract double level();
}

class Goku extends Fighter {
	
	@Override
	public String attack() {
		return "Puños y patadas";
	}
	
	@Override
	public double level() {
		return 1000.1;
	}
}

class Gohan extends Fighter {

	@Override
	public String attack() {
		return "Puños, patadas y cabezazos";
	}
	
	@Override
	public double level() {
		return 875.23;
	}
}

abstract class SkillDecorator extends Fighter {
	public abstract String attack();
	public abstract double level();
}

class KameHameHa extends SkillDecorator {

	Fighter fighter;

	KameHameHa(Fighter fighter) {
		this.fighter = fighter;
	}

	@Override
	public String attack() {
		return fighter.attack() + addKameSkill();
	}

	@Override
	public double level() {
		return 1500 + fighter.level();
	}

	private String addKameSkill() {
		return ", KameHameHa";
	}
}

class SuperKameHameHa extends SkillDecorator {

	Fighter fighter;

	SuperKameHameHa(Fighter fighter) {
		this.fighter = fighter;
	}

	@Override
	public String attack() {
		return fighter.attack() + addSuperKameSkill();
	}

	@Override
	public double level() {
		return 2750 + fighter.level();
	}

	private String addSuperKameSkill() {
		return ", SuperKameHameHa";
	}
}

class GenkiDama extends SkillDecorator {

	Fighter fighter;

	GenkiDama(Fighter fighter) {
		this.fighter = fighter;
	}

	@Override
	public String attack() {
		return fighter.attack() + addGenkiSkill();
	}
	
	@Override
	public double level() {
		return 5750 + fighter.level();
	}

	private String addGenkiSkill() {
		return ", GenkiDama";
	}
}

class KaioKen extends SkillDecorator {

	Fighter fighter;

	KaioKen(Fighter fighter) {
		this.fighter = fighter;
	}

	@Override
	public String attack() {
		return fighter.attack() + addKaioSkill();
	}
	
	@Override
	public double level() {
		return 1173.84 + fighter.level();
	}

	private String addKaioSkill() {
		return ", KaioKen";
	}
}

class Kienzan extends SkillDecorator {

	Fighter fighter;

	Kienzan(Fighter fighter) {
		this.fighter = fighter;
	}

	@Override
	public String attack() {
		return fighter.attack() + addKienSkill();
	}
	
	@Override
	public double level() {
		return 2899.99 + fighter.level();
	}

	private String addKienSkill() {
		return ", Kienzan";
	}
}
