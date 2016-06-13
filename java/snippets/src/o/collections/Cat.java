package o.collections;

public class Cat {
	
	private String nombre;
	private int edad;
	
	public int getEdad() {
		return edad;
	}
	public void setEdad(int edad) {
		this.edad = edad;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String toString(){
		return this.getNombre() + " " + this.getEdad();
	}
	
	public Cat(String n, int e) {
		this.nombre = n;
		this.edad = e;
	}
	
	

}
