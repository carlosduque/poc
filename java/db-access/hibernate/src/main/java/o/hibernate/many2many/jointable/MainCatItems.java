package o.hibernate.many2many.jointable;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainCatItems {

    private static final Logger LOGGER = LoggerFactory.getLogger(MainCatItems.class);

    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        SessionFactory factory = conf.configure().buildSessionFactory();
        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();

        Item tv = new Item((long) 100, "TV");
        Item ipad = new Item((long) 101, "iPad");
        Item radio = new Item((long) 102, "Radio");
        LOGGER.debug("saving items...");
        session.save(tv);
        session.save(ipad);
        session.save(radio);

        Category video = new Category((long) 910, "Video");
        Category audio = new Category((long) 920, "Audio");
        LOGGER.debug("saving categories...");
        session.save(video);
        session.save(audio);

        CategorizedItem catItem = null;
        LOGGER.debug("saving categorized items...");
        catItem = new CategorizedItem(video, tv);
        session.save(catItem);
        catItem = new CategorizedItem(video, ipad);
        session.save(catItem);
        catItem = new CategorizedItem(audio, radio);
        session.save(catItem);
        catItem = new CategorizedItem(audio, ipad);
        session.save(catItem);

        tx.commit();
        session.close();
    }

}
