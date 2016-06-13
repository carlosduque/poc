package o.drools.cashflow;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;

import o.drools.cashflow.Account;
import o.drools.cashflow.AccountPeriod;
import o.drools.cashflow.CashFlow;

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
 * @date Jan 27, 2012
 *
 */
public class CashFlowTest {
    /** the logger. */
    private static final Logger log = LoggerFactory.getLogger(CashFlowTest.class);

    /** the path to the rules file. */
    private static final String PATH_CASHFLOW_DRL = "o/oo/drools/resources/drl/cashflow-application.drl";
    /** stateful knowledge session. */
    private static StatefulKnowledgeSession ksession = null;
    /** knowledge builder. */
    private static KnowledgeBuilder kbuilder = null;
    /** knowledge base. */
    private static KnowledgeBase kbase = null;

    @BeforeClass
    public static void globalSetup() {
        kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
        log.debug("Creating KnowledgeBuilder() with rules from: " + PATH_CASHFLOW_DRL);
        kbuilder.add(ResourceFactory.newClassPathResource(PATH_CASHFLOW_DRL), ResourceType.DRL);
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
    public void testIncreaseBalanceForCredits() {
        log.debug("Creating new objects for CashFlow(), Account() and AccountPeriod().");

        Account account = null;
        AccountPeriod accountPeriod = null;
        CashFlow cashFlow = null;
        Calendar cal = null;
        Date startDate = null;
        Date endDate = null;

        account = new Account(1);
        cal = Calendar.getInstance();
        cal.set(2007, Calendar.JANUARY, 12);
        startDate = new Date(cal.getTimeInMillis());
        //reusing the cal object to set the endDate
        cal.set(2007, Calendar.FEBRUARY, 2);
        endDate = new Date(cal.getTimeInMillis());

        accountPeriod = new AccountPeriod(startDate, endDate);

        cal.set(2007, Calendar.FEBRUARY, 1);
        Date trxDate = new Date(cal.getTimeInMillis());
        cashFlow = new CashFlow(trxDate, 100, "CREDIT", 1);

        log.debug("Inserting objects into the session.");
        ksession.insert(account);
        ksession.insert(accountPeriod);
        ksession.insert(cashFlow);
        log.debug("Executing the rules.");
        //Fire the rules
        ksession.fireAllRules();

        assertEquals("Balance should be the same", cashFlow.getAmount(), account.getBalance(), 2);

        ksession.dispose();
    }
}
