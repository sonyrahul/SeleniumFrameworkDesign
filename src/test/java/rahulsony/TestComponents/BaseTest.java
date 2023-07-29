package rahulsony.TestComponents;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.bonigarcia.wdm.WebDriverManager;
import rahulsony.pageobjects.LandingPage;

public class BaseTest {
	
	public WebDriver driver;
	public LandingPage landingpage;
	
	public WebDriver inialiseDriver() throws IOException {
		
		//Properties class
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream("C:\\Users\\DELL\\eclipse-workspace\\SeleniumFrameworkDesign\\src\\main\\java\\rahulsony\\resources\\GlobalData.properties");
		prop.load(fis);
		String browsername = System.getProperty("browser")!=null ? System.getProperty("browser") : prop.getProperty("browser");
		
		if(browsername.equalsIgnoreCase("chrome")) {
			WebDriverManager.chromedriver().setup();
			ChromeOptions co = new ChromeOptions();
			co.addArguments("--remote-allow-origins=*");
			driver = new ChromeDriver(co);
		}
		
		else if(browsername.equalsIgnoreCase("edge")) {
			//firefox code
			WebDriverManager.edgedriver().setup();
			EdgeOptions co = new EdgeOptions();
			co.addArguments("--remote-allow-origins=*");
			driver = new EdgeDriver(co);
		}
		else if(browsername.equalsIgnoreCase("firefox")) {
			//edge code
		}
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		return driver;
	}
	
	public String getScreenSot(String testCaseName,WebDriver driver) throws IOException
	{
		TakesScreenshot ts = (TakesScreenshot)driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		File file = new File(System.getProperty("user.dir")+"\\reports\\"+ testCaseName +".png");
		FileUtils.copyFile(source, file);
	
		return "C:\\Users\\DELL\\eclipse-workspace\\ErrorSnap"+ testCaseName +".png";
	}
	
	@BeforeMethod(alwaysRun=true)
	public LandingPage launchApplication() throws IOException
	{
		driver = inialiseDriver();
		landingpage = new LandingPage(driver);
		landingpage.goTo();
		return landingpage;
		
	}
	
	public List<HashMap<String, String>> getJsonDataToMap(String filePath) throws IOException {
		
		//Read Json to String
		String jsonContent = FileUtils.readFileToString(new File(filePath),StandardCharsets.UTF_8);
		
		//String to HashMap using jackson DataBind pom.xml
		ObjectMapper mapper = new ObjectMapper();
		List<HashMap<String,String>> data = mapper.readValue(jsonContent, new TypeReference<List<HashMap<String,String>>>() {
		});
		
		//data contains list of two map {{map},{map}}
		return data;
		}
	
	@AfterMethod(alwaysRun=true)
	public void tearDown()
	{
		driver.close();
	}

}
