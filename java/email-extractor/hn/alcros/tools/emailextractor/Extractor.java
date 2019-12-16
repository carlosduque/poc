package hn.alcros.tools.extractor;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Extractor {
	
	private static final String REGEX_EMAIL = "[a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}";
	private static StringBuffer result = null;
	
	private static String read(String filename) throws IOException {
		
		StringBuffer sb = new StringBuffer();
		BufferedReader in = new BufferedReader(new FileReader(filename));
		String s;

		while((s = in.readLine()) != null) {
			sb.append(s);
			sb.append("\n");
		}
		
		return sb.toString();
		
	}
	
	public String extract(String filename, String pattern) {
	
		String input = "";
		try {

			input = this.read(filename);		
			result = new StringBuffer("");

			if (pattern.equals(":email:")) {
				pattern = REGEX_EMAIL;
			}
			
			Pattern expression = Pattern.compile(pattern);
			Matcher matcher = expression.matcher(input);

			while(matcher.find()) {
				result.append(matcher.group());
			}
			
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result.toString();
	}

/*	
	public void write(String filename) throws IOException {
		
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(fileName)));
		
		for(int i = 0; i < size(); i++) {
			out.println(get(i));
		}
		
		out.close();
	}
*/
}
