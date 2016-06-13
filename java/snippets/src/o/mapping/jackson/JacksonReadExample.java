package o.mapping.jackson;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

public class JacksonReadExample {
	public static void main(String[] args) {
		 
		ObjectMapper mapper = new ObjectMapper();
	 
		try {
	 
		   // read JSON from a file
		   Map<String, Object> userInMap = mapper.readValue(
			new File("src/test/resources/user.json"), 
			new TypeReference<Map<String, Object>>() {});
	 
		   System.out.println(userInMap.get("name"));
		   System.out.println(userInMap.get("age"));
	 
		   ArrayList<String> list = (ArrayList<String>) userInMap.get("messages");
	 
		   for (String msg : list) {
			System.out.println(msg);
		   }
	 
		} catch (JsonGenerationException e) {
	    	   e.printStackTrace();
		} catch (JsonMappingException e) {
		   e.printStackTrace();
		} catch (IOException e) {
		   e.printStackTrace();
		}
	     }
}
