package o.collections;

import java.util.HashSet;
import java.util.Set;

public class Pets {

	private String grupo="";
	private Set cats;

	public Pets() {	} 
	
	public Pets(String g, Set c) {
		this.grupo = g;
		this.cats = c;
	}

	public String toString() {

		return " " + this.getGrupo() +
				   " " + this.getCats();
			}

	public void init(Set s) {
		this.cats = s;
	}

	public Set getCats() {
		return cats;
	}

	public void setCats(Set cats) {
		this.cats = cats;
	}

	public String getGrupo() {
		return grupo;
	}

	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}

}
