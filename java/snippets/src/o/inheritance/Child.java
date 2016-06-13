package o.inheritance;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Child extends Parent {
    public static Logger MYLOGGER = LoggerFactory.getLogger(Child.class);

    @Override
    void addMusic() {
        MYLOGGER.info("called ~ .addMusic()");
        LOGGER.info("printed with the ascendant's logger");
    }

    @Override
    void addTwist() {
        MYLOGGER.info("called ~ .addTwist()");
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        Child kid = new Child();
        kid.tellStory();
        kid.addMusic();
    }

}
