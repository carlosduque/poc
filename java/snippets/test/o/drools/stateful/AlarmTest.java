package o.drools.stateful;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import o.drools.stateful.Fire;
import o.drools.stateful.Room;
import o.drools.stateful.Sprinkler;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.command.Command;
import org.drools.command.CommandFactory;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.rule.FactHandle;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author carlos.duque
 * @date Jan 26, 2012
 *
 */
public class AlarmTest {
    /** the logger. */
    private static final Logger log = LoggerFactory.getLogger(AlarmTest.class);

    /** the path to the rules file. */
    private static final String PATH_FIREALARM_VALIDATION_DRL = "com/example/drools/resources/drl/firealarm-application.drl";
    /** stateful knowledge session. */
    private static StatefulKnowledgeSession ksession = null;
    /** knowledge builder. */
    private static KnowledgeBuilder kbuilder = null;
    /** knowledge base. */
    private static KnowledgeBase kbase = null;

    @BeforeClass
    public static void globalSetup() {
        kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
        log.debug("Creating KnowledgeBuilder() with rules from: " + PATH_FIREALARM_VALIDATION_DRL);
        kbuilder.add(ResourceFactory.newClassPathResource(PATH_FIREALARM_VALIDATION_DRL), ResourceType.DRL);
        if (kbuilder.hasErrors()) {
            log.error(kbuilder.getErrors().toString());
        }
        log.debug("Creating KnowledgeBase().");
        kbase = KnowledgeBaseFactory.newKnowledgeBase();
        kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());
        log.debug("Creating StatefulKnowledgeSession().");
        ksession = kbase.newStatefulKnowledgeSession();
    }


    @AfterClass
    public static void tearDown() {
        ksession = null;
        kbase = null;
        kbuilder = null;
    }

    @Test
    public void testSprinklerActivationDeActivation() {
        log.debug("Creating new objects for Room() and Sprinkler().");

        String[] names = new String[]{"kitchen", "bedroom", "office", "livingroom"};
        Map<String, Room> name2room = new HashMap<String, Room>();
        Sprinkler sprinkler = null;
        for (String name : names) {
            Room room = new Room(name);
            name2room.put(name, room);
            ksession.insert(room);
            sprinkler = new Sprinkler(room);
            ksession.insert(sprinkler);
            log.debug("Inserted Sprinkler(Room(" + name + ")) into the session.");
        }
        //not setting the fire yet
        log.debug("Executing rules w/o a Fire().");
        ksession.fireAllRules();

        Fire kitchenFire = null;
        Fire officeFire = null;
        kitchenFire = new Fire(name2room.get("kitchen"));
        officeFire = new Fire(name2room.get("office"));

        /**
         * A Fact Handle is an internal engine reference to the inserted instance and allows
         * instances to be retracted or modified at a later point in time.
         */
        FactHandle kitchenFireHandle = null;
        FactHandle officeFireHandle = null;
        kitchenFireHandle = ksession.insert(kitchenFire);
        officeFireHandle = ksession.insert(officeFire);
        log.debug("Inserted " + kitchenFire + " and " + officeFire + " into the session.");
        log.debug("Executing rules with two Fire() objects.");
        ksession.fireAllRules();

        ksession.retract(kitchenFireHandle);
        ksession.retract(officeFireHandle);
        log.debug("Retracted " + kitchenFire + " and " + officeFire + " from the session.");
        log.debug("Executing rules w/o Fire() objects.");
        ksession.fireAllRules();

        ksession.dispose();
    }
}
