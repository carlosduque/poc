package o.sockets.ibm;

import java.io.*;
import java.net.*;

public class URLClient {
	protected HttpURLConnection connection;

	public String getDocumentAt(String urlString) {
		StringBuffer document = new StringBuffer();
		try {
			URL url = new URL(urlString);
			URLConnection conn = url.openConnection();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));
			String line = null;
			while ((line = reader.readLine()) != null)
				document.append(line + "\n");
			reader.close();
		} catch (MalformedURLException e) {
			System.out.println("Unable to connect to URL: " + urlString);
		} catch (IOException e) {
			System.out.println("IOException when connecting to URL: "
					+ urlString);
		}
		return document.toString();
	}

	public static void main(String[] args) {
		URLClient client = new URLClient();
		String site = client.getDocumentAt("http://www.google.hn");
		System.out.println(site);
	}
}
