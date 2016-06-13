package o.drools.sandbox;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.Date;

import o.drools.sandbox.Author;
import o.drools.sandbox.Book;
import o.drools.sandbox.BookType;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author carlos.duque
 * @date Jan 26, 2012
 *
 */
public class SandboxTest {
    /** the logger. */
    private static final Logger log = LoggerFactory.getLogger(SandboxTest.class);

    /** the path to the rules file. */
    private static final String PATH_SANDBOX_DRL = "com/example/drools/resources/drl/sandbox-application.drl";
    /** stateful knowledge session. */
    private static StatefulKnowledgeSession ksession = null;
    /** knowledge builder. */
    private static KnowledgeBuilder kbuilder = null;
    /** knowledge base. */
    private static KnowledgeBase kbase = null;
    /** calendar. */
    private static Calendar cal = null;

    @BeforeClass
    public static void globalSetup() {
        cal = Calendar.getInstance();
        kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
        log.debug("Creating KnowledgeBuilder() with rules from: " + PATH_SANDBOX_DRL);
        kbuilder.add(ResourceFactory.newClassPathResource(PATH_SANDBOX_DRL), ResourceType.DRL);
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
    public void testRules() {
        log.debug("Creating new objects.");
        //--
        Author gotAuthor = new Author("George","Martin");
        cal.set(1996, Calendar.SEPTEMBER, 11);
        Date gotDate = new Date(cal.getTimeInMillis());
        Book got = new Book("Game of Java thrones", BookType.FANTASY, 10, gotAuthor, gotDate);
        //--
        Author lahAuthor = new Author("George","Nemeth");
        cal.set(2003, Calendar.JULY, 25);
        Date lahDate = new Date(cal.getTimeInMillis());
        Book lah = new Book("Java Linux Administration Handbook", BookType.TECHNICAL, 9, lahAuthor, lahDate);
        //--
        Author tijAuthor = new Author("Bruce","Eckel");
        cal.set(2011, Calendar.APRIL, 5);
        Date tijDate = new Date(cal.getTimeInMillis());
        Book tij = new Book("Thinking in Java", BookType.TECHNICAL, 4, tijAuthor, tijDate);
        //--
        Author casAuthor = new Author("Gabriel","Garcia Marquez");
        cal.set(1980, Calendar.MAY, 19);
        Date casDate = new Date(cal.getTimeInMillis());
        Book cas = new Book("Cien años de Java", BookType.FICTION, 5, casAuthor, casDate);
        //--
        Author l7hAuthor = new Author("George", "Covey");
        cal.set(1998, Calendar.AUGUST, 24);
        Date l7hDate = new Date(cal.getTimeInMillis());
        Book l7h = new Book("Los 7 habitos de la gente altamente efectiva", BookType.SELFIMPROVEMENT, 7, l7hAuthor, l7hDate);
        //--
        Author bfaAuthor = new Author("Richard", "Shell");
        cal.set(2009, Calendar.NOVEMBER, 29);
        Date bfaDate = new Date(cal.getTimeInMillis());
        Book bfa = new Book("Bargaining for Java advantage", BookType.SELFIMPROVEMENT, 2, bfaAuthor, bfaDate);
        //--
        Author ejAuthor = new Author("Joshua", "Bloch");
        cal.set(1997, Calendar.DECEMBER, 1);
        Date ejDate = new Date(cal.getTimeInMillis());
        Book ej = new Book("Effective Java", BookType.TECHNICAL, 3, ejAuthor, ejDate);

        log.debug("Inserting objects into the session.");
        ksession.insert(got);
        ksession.insert(lah);
        ksession.insert(tij);
        ksession.insert(cas);
        ksession.insert(l7h);
        ksession.insert(bfa);
        ksession.insert(ej);
        ksession.insert(gotAuthor);
        ksession.insert(lahAuthor);
        ksession.insert(tijAuthor);
        ksession.insert(casAuthor);
        ksession.insert(l7hAuthor);
        ksession.insert(bfaAuthor);
        ksession.insert(ejAuthor);
        log.debug("Executing rules.");
        ksession.fireAllRules();
        log.debug("DONE.");

        ksession.dispose();
    }
}
