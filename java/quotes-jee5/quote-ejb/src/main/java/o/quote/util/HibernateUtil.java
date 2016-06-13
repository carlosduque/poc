package o.quote.util;

import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.hibernate.cfg.Configuration;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HibernateUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(HibernateUtil.class);
    private static Context jndiContext;
    static {
        try {
            // Build it and bind it to JNDI
            new Configuration().configure().buildSessionFactory();

            // Get a handle to the registry (reads jndi.properties)
            jndiContext = new InitialContext();
        } catch(Throwable ex) {
           LOGGER.error("Initial SessionFactory creation failed. " + ex);
           throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory(String sfName) {
        SessionFactory sf;
        try {
            sf = (SessionFactory) jndiContext.lookup(sfName);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
        return sf;
    }
}
