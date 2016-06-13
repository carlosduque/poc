package o.db.hibernate;

import org.hibernate.HibernateException;
import org.hibernate.cfg.Configuration;

public class DB {

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
                        "jdbc:hsqldb:mem:unitec")
                    .setProperty("hibernate.connection.username", "sa")
                    .setProperty("hibernate.connection.password", "")
                    .setProperty("hibernate.connection.pool_size", "1")
                    .setProperty("hibernate.connection.autocommit", "true")
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
    
    public DB() {}

}
