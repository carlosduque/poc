package o.db.hibernate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.StringTokenizer;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

public class EventManager {
	
	static {
		try {
			Configuration config =
				new Configuration()
					.setProperty(
						"hibernate.dialect",
						"org.hibernate.dialect.HSQLDialect")
					.setProperty(
						"hibernate.connection.driver_class",
						"org.hsqldb.jdbcDriver")
					.setProperty(
						"hibernate.connection.url",
						"jdbc:hsqldb:mem:hibdemo")
					.setProperty("hibernate.connection.username", "sa")
					.setProperty("hibernate.connection.password", "")
					.setProperty("hibernate.connection.pool_size", "1")
					.setProperty("hibernate.connection.autocommit", "true")
					.setProperty("hibernate.connection.current_session_context_class","thread")
					.setProperty(
						"hibernate.cache.provider_class",
						"org.hibernate.cache.HashtableCacheProvider")
					.setProperty("hibernate.hbm2ddl.auto", "create-drop")
					.setProperty("hibernate.show_sql", "true")
					.addClass(o.db.hibernate.Person.class)
					.addClass(o.db.hibernate.Event.class);

			HibernateUtil.setSessionFactory(config.buildSessionFactory());
		} catch (HibernateException e) {
			e.printStackTrace();
		}

	}

    public static void main(String[] args) {

    	String comando = "";    	 	
        EventManager mgr = new EventManager();

        while(!comando.equalsIgnoreCase("salir")) {        	
        	try {
				comando = new BufferedReader(new InputStreamReader(System.in)).readLine();	
				StringTokenizer tokens = new StringTokenizer(comando," ");
				String cmd = tokens.nextToken();
				String param = "";
				
				if(tokens.hasMoreTokens())
					param = tokens.nextToken();							
				if(comando == null)
					continue;
				if ( comando.equalsIgnoreCase("agregar") && param.equalsIgnoreCase("evento") ) {
					
					System.out.println("detalle: ");
					String detalle = new BufferedReader(new InputStreamReader(System.in)).readLine();	
					mgr.createAndStoreEvent(detalle,new Date());
					
				} else if ( comando.equalsIgnoreCase("agregar") && param.equalsIgnoreCase("persona") ) {					
					
					String resp="";
					Set eventos = new HashSet();
					System.out.println("nombre: ");
					String nombre = new BufferedReader(new InputStreamReader(System.in)).readLine();
					System.out.println("apellido: ");
					String apellido = new BufferedReader(new InputStreamReader(System.in)).readLine();
					System.out.println("edad: ");
					String edad = new BufferedReader(new InputStreamReader(System.in)).readLine();
					
					while (!resp.equalsIgnoreCase("n")) {
						System.out.println("leer evento? [s/n]:");
						resp = new BufferedReader(new InputStreamReader(System.in)).readLine();					
						if (resp.equalsIgnoreCase("n")) {
							continue;
						}						
						System.out.println("detalle: ");
						String detalle = new BufferedReader(new InputStreamReader(System.in)).readLine();
						
						Event evento = new Event();
						evento.setDate(new Date());
						evento.setTitle(detalle);
						
						eventos.add(evento);
						//mgr.createAndStoreEvent(detalle,new Date());				
					}					
					resp = "";
					mgr.createAndStorePerson(nombre, apellido, edad, eventos);
					
				} 
			} catch (IOException e) {
				e.printStackTrace();
			}

        }

        HibernateUtil.getSessionFactory().close();
    }

    private Long createAndStoreEvent(String title, Date theDate) {

        Long eventId = new Long(1);
    	Session session = HibernateUtil.getSession();
        session.beginTransaction();

        Event theEvent = new Event();
        theEvent.setTitle(title);
        theEvent.setDate(theDate);

        session.save(theEvent);

        session.getTransaction().commit();
        
        return eventId;
    }
    
    private Long createAndStorePerson(String nombre, String apellido, String edad, Set eventos) {
    	Random random = new Random();    	
        Session session = HibernateUtil.getSession();

        session.beginTransaction();
        Long personId = new Long(random.nextLong());
        Person thePerson = new Person();
        thePerson.setFirstname(nombre);
        thePerson.setLastname(apellido);
        thePerson.setAge(edad);
        thePerson.setId(personId);
        thePerson.setEvents(eventos);       

        session.save(thePerson);

        session.getTransaction().commit();

        return personId;
    }

    private void addPersonToEvent(Long personId, Long eventId) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();

        Person aPerson = (Person) session.load(Person.class, personId);
        Event anEvent = (Event) session.load(Event.class, eventId);

        aPerson.getEvents().add(anEvent);

        session.getTransaction().commit();
    }

}