package cl.internetmedia.tracker.backend.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
	private static final Logger LOG = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) {

		LOG.info("Launching Spring's application context");

		//ApplicationContext ctx = new ClassPathXmlApplicationContext("app-context.xml");
		AbstractApplicationContext ctx = new ClassPathXmlApplicationContext("application-context.xml");
		
        // add a shutdown hook for the above context... 
        ctx.registerShutdownHook();

	}
}