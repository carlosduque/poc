package hn.alcros.tools.extractor;

import hn.alcros.tools.extractor.Extractor;

public class ExtractorDemo {
	
	public static void main(String[] args) {
		
		if (args.length < 2) {
			System.out.println("Usage: ExtractorDemo filename pattern");
			System.out.println("");
			System.out.println("pattern: any custom pattern or select one from these options:");
			System.out.println(":email:");
			System.out.println();
			System.exit(1);
		}	
		
		String filename = args[0];
		String pattern = args[1];
		String s = "";
		
		Extractor extractor = new Extractor();
		s = extractor.extract(filename, pattern);
		System.out.println(s);
	
	}

}
