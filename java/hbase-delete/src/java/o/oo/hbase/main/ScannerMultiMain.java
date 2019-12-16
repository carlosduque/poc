package o.oo.hbase.main;

import o.oo.hbase.job.ScannerMultiJob;

public class ScannerMultiMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		String[] tablenames = {"a07942a", "a07942b", "a07942c", "a07942d"};
		String[] jobArgs = new String[10];
		ScannerMultiJob job;
		for(int i = 0; i < tablenames.length; i++) {
			jobArgs[0] = "-t";
			jobArgs[1] = tablenames[i];
			jobArgs[2] = "-f";
			jobArgs[3] = "record";
			jobArgs[4] = "-c";
			jobArgs[5] = "data";
			jobArgs[6] = "-v";
			jobArgs[7] = "1";
			jobArgs[8] = "-z";
			jobArgs[9] = "ALNFRDSLE101.expdagdev.local:8021";
			job = new ScannerMultiJob();
			try {
				System.out.format("Running Job for table %s", jobArgs[1]);
				job.run(jobArgs);
			} catch (Exception e) {
				System.out.format("ERROR: %s", e.getMessage());
			}
		}

	}

}
