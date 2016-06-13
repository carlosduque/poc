package o.drools.stateless;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import o.drools.stateless.Applicant;
import o.drools.stateless.Application;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.command.Command;
import org.drools.command.CommandFactory;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatelessKnowledgeSession;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ApplicantTest for JUnit 4.
 * @author a07942a
 *
 */
public class ApplicantTest {

    /** the logger. */
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    /** knowledge base. */
    private KnowledgeBase kbase = null;
    /** the path to the rules file. */
    private static final String PATH_LICENSE_VALIDATION_DRL = "com/example/drools/resources/drl/license-application.drl";

	@Before
	public void setup() {
		KnowledgeBuilder kbuilder = null;
		kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
		kbuilder.add(ResourceFactory.newClassPathResource(PATH_LICENSE_VALIDATION_DRL, getClass()), ResourceType.DRL);
		if (kbuilder.hasErrors()) {
			log.error(kbuilder.getErrors().toString());
		}
		kbase = KnowledgeBaseFactory.newKnowledgeBase();
		kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());
	}

	@After
	public void teardown() {
	    kbase = null;
	}

	@Test
	public void testInvalidApplication() {
        List<Command> cmds = new ArrayList<Command>();
	    StatelessKnowledgeSession ksession = kbase.newStatelessKnowledgeSession();
		Applicant pebbles = new Applicant("Pebbles Picapiedra", 16);
		Application application = new Application(new Date(), true);

		cmds.add(CommandFactory.newInsert(pebbles));
		cmds.add(CommandFactory.newInsert(application));

        assertTrue(application.isValid());
		//ksession.execute(Arrays.asList(new Object[] {application, pebbles}));
		ksession.execute(CommandFactory.newBatchExecution(cmds));

		//ksession.execute(applicant); // for only one object
		assertFalse(application.isValid());
	}

	@Test
	public void testValidApplication() {
		StatelessKnowledgeSession ksession = kbase.newStatelessKnowledgeSession();
		Applicant bambam = new Applicant("Bambam Marmol", 18);
		Application application = new Application(new Date(), true);
		assertTrue(application.isValid());
		ksession.execute(Arrays.asList(new Object[] {application, bambam}));
		//ksession.execute(CommandFactory.newInsert(new Object[] {application, bambam}));
		assertTrue(application.isValid());
	}

}
