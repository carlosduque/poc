package uni.xml;

import java.util.ArrayList;
import java.util.ListIterator;

/**
 * Representa un elemento "registro" del XML de configuración de registros.
 *   
 * @author cduque
 * @version 1.0
 * PLT: HON-00195
 * Fecha: Abr 10, 2006
 */

public class Registro {

	/** ID del socket que representa la instancia */
	private String id;
	/** Nombre */
	private String nombre;
	/** Talones */
	private ArrayList talones;
	

	/**
	 * Constructor default.
	 */
	public Registro() {
		talones = new ArrayList();
	}
	
	/**
	 * Constructor completo.
	 * @param newId ID del estudiante
	 * @param newNombre nombre
	 * @param newTalones talones
	 
	 */
	public Registro(String newId, String newNombre, ArrayList newTalones) {		
		id = newId;
		nombre = newNombre;
		talones = newTalones;
	}

	/**
	 * Retorna el ID del Estudiante
	 * @return el ID del Estudiante
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * Retorna el Nombre del Estudiante.
	 * @return el Nombre del Estudiante
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Retorna los Talones.
	 * @return los Talones
	 */
	public ArrayList getTalones() {
		return talones;
	}

       /**
       * Establece el ID del Estudiante.
       * @param newId 
       */
	public void setId(String newId) {
		id = newId;
	}
	
	/**
	 * Establece el Nombre del Estudiante
	 * @param newNombre el nombre del Estudiante
	 */
	public void setNombre(String newNombre) {
		nombre = newNombre;
	}
	
	/**
	 * Agrega un talon.
	 * @param newTalon El talon
	 */
	public void addTalon(Talon newTalon) {
		talones.add(newTalon);
	}
	
	public String toString () {		
                ListIterator it = talones.listIterator();              
                StringBuffer buf = new StringBuffer();
		for (; it.hasNext() ;) {
			buf.append(it.next());
		}
		return buf.toString();		
	}

}
