package o.db.hibernate;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class HibernateUtil {

	private static SessionFactory factory;
	private static ThreadLocal threadSession = new ThreadLocal();
	
	public static synchronized Session getSession() {
		
		Session s = (Session)threadSession.get();		
				
		try {
			if (factory == null) {
				factory = new Configuration().configure().buildSessionFactory();
			}			
			s = factory.openSession();
			
		} catch (HibernateException e) {			
			e.printStackTrace();
		}
		
		return s;
		
	}
	
	public static void setSessionFactory(SessionFactory factory) {
		HibernateUtil.factory = factory;
	}
	
	public static SessionFactory getSessionFactory() {
		return HibernateUtil.factory;
	}
}