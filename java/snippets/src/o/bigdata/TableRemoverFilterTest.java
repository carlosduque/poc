package o.bigdata;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

public class TableRemoverFilterTest {
	private static final Logger logger = Logger.getLogger(TableRemoverFilterTest.class);
	private static final String DATE_FORMAT = "yyyyMMdd";
	private static final int HOURS = 24; 
	private static final int MINUTES = 60;
	private static final int SECONDS = 60; 
	private static final int MILLISECONDS = 1000;
	private static String prefixRegex = "RESIDENTIAL";
	private static int retentionDays = 0;
	private static Date today = new Date();
	
	static String[] allDescriptors = {"RESIDENTIAL-20130101",
			"RESIDENTIAL-20130102",
			"RESIDENTIAL-20130103",
			"RESIDENTIAL-20130104",
			"RESIDENTIAL-20130105",
			"RESIDENTIAL-20130106",
			"RESIDENTIAL-20130107",
			"RESIDENTIAL-20130108",
			"RESIDENTIAL-20130109",
			"RESIDENTIAL-20130110",
			"PIDCM-20130102",
			"PIDCM-20130103",
			"PIDCM-20130104",
			"CPMSSSN-20130105",
			"CPMSSSN-20130106",
			"DLICENSE-20130107",
			"DLICENSE-20130108",};
	String[] descriptorsToRemove = new String[0];

	private static String[]  filterOld() throws Exception {
		String[] descriptors = new String[0];
		String tablename;
		Matcher matcher;
		SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
		Pattern pattern = Pattern.compile(prefixRegex + "-.*");
		List<String> list = new ArrayList<String>();
		for(int i = 0; i < allDescriptors.length; i++) {
			 tablename = allDescriptors[i];
			 matcher = pattern.matcher(tablename);
			 if(matcher.matches()) {
				 String[] nameSplit = tablename.split("-");
				 long tableDate = format.parse(nameSplit[1]).getTime();
				 long retentionMillis = (retentionDays * HOURS * MINUTES * SECONDS * MILLISECONDS);
				 //long boundaryDate = today.getTime() - retentionMillis;
				 
				 //format.parse(format.format(today)) to get rid of the milliseconds, seconds and hours of today
				 long boundaryDate = format.parse(format.format(today)).getTime() - retentionMillis;

				 System.out.println("======================================");
				 System.out.println("      tableDate     : " + "ms[" + tableDate + "] " + nameSplit[1]);
				 System.out.println("      date          : " + "ms[" + today.getTime() + "] " + today);
				 System.out.println("      retentionDays : " + "ms[" + retentionMillis + "] " + retentionDays);
				 System.out.println("      boundaryDate  : " + "ms["+ boundaryDate+ "] " + new Date(boundaryDate));
				 if (tableDate < boundaryDate) {
					 System.out.println("==>>  MATCH: " + tablename);
					list.add(allDescriptors[i]);
				 }
			 }
		}
		return list.toArray(descriptors);
	}
	
	private static String[] filterNew() throws Exception {
		String[] descriptors = new String[0];
		String tablename;
		
		SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
		
		List<String> list = new ArrayList<String>();
		for(int i = 0; i < allDescriptors.length; i++) {
			 tablename = allDescriptors[i];
			 if (tablename.contains(prefixRegex)) {
				 String[] nameSplit = tablename.split("-");
				 long tableDate = format.parse(nameSplit[1]).getTime();
				 long boundaryDate = format.parse(format.format(new Date())).getTime();
				 long retentionMillisOneDay = (HOURS * MINUTES * SECONDS * MILLISECONDS);
				 long difference = (boundaryDate-tableDate)/retentionMillisOneDay;
				 if (difference <=0) difference = 0; // This will not delete the table

				 System.out.println("**************************************");
				 System.out.println(" table date	: [" + nameSplit[1] + "] for table " + tablename);
				 System.out.println(" todays date	: [" + format.format(new Date()) + "] for " + (new Date()));
				 System.out.println(" difference	: [" + difference + "] days ");
				 if (difference > retentionDays) {
					 System.out.println("**>>  MATCHED Table for delete : " + tablename);
					list.add(allDescriptors[i]);
				 }
			 }
		}
		return list.toArray(descriptors);
	}
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		System.out.println("====== Filter Old Output ======");
		String[] oldFilter = filterOld();
		System.out.println("=========");
		System.out.println("= Tables:");
		System.out.println("=========");
		for(int i = 0; i < oldFilter.length; i++){
			System.out.println(oldFilter[i]);
		}
		
		System.out.println("****** Filter New Output ******");
		String[] newFilter = filterNew();
		System.out.println("*********");
		System.out.println("* Tables:");
		System.out.println("*********");
		for(int i = 0; i < newFilter.length; i++){
			System.out.println(newFilter[i]);
		}
	}
}
