package rahulsony.data;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.io.FileUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DataReader {
	
	public List<HashMap<String, String>> getJsonDataToMap() throws IOException {
		//Read Json to String
		String jsonContent = FileUtils.readFileToString(new File("C:\\Users\\DELL\\eclipse-workspace\\SeleniumFrameworkDesign\\src\\test\\java\\rahulsony\\data\\PurchaseOrder.json")
		,StandardCharsets.UTF_8);
		
		//String to HashMap using jackson DataBind pom.xml
		ObjectMapper mapper = new ObjectMapper();
		List<HashMap<String,String>> data = mapper.readValue(jsonContent, new TypeReference<List<HashMap<String,String>>>() {
		});
		
		//data contains list of two map {{map},{map}}
		return data;
		}
		
		
	}

