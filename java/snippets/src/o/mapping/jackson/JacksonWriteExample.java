package o.mapping.jackson;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class JacksonWriteExample {

	public static void main(String[] args) {
		 
		ObjectMapper mapper = new ObjectMapper();
	 
		Map<String, Object> userInMap = new HashMap<String, Object>();
		userInMap.put("name", "alcros");
		userInMap.put("age", 34);
	 
		List<Object> list = new ArrayList<Object>();
		list.add("msg 1");
		list.add("msg 2");
		list.add("msg 3");
	 
		userInMap.put("messages", list);
	 
		try {
	 
		  // write JSON to a file
		  File f = new File("src/test/resources/user.json");
		  mapper.writeValue(f, userInMap);
		  System.out.println("File written: " + f.getAbsolutePath());
	 
		} catch (JsonGenerationException e) {
		  e.printStackTrace();
		} catch (JsonMappingException e) {
		  e.printStackTrace();
		} catch (IOException e) {
		  e.printStackTrace();
		}
	     }

}
