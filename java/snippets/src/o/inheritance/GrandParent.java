package o.inheritance;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class GrandParent {
    public static Logger LOGGER = LoggerFactory.getLogger(GrandParent.class);

    public void tellStory() {
        LOGGER.info("called ~ .tellStory()");
        addTwist();
    }

    abstract void addTwist();
}
