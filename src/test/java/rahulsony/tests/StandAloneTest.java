package rahulsony.tests;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import io.github.bonigarcia.wdm.WebDriverManager;

public class StandAloneTest {
	DataFormatter formatter = new DataFormatter();
	ExtentReports extent;
	
	@BeforeTest
	public void config() {
		
		//ExtentReports , ExtentSparkReporter
		String path = System.getProperty("user.dir")+"\\reports\\index.html";
		ExtentSparkReporter reporter = new ExtentSparkReporter(path);
		reporter.config().setReportName("Web Automation Results");
		reporter.config().setDocumentTitle("Test Results");
		extent = new ExtentReports();
		extent.attachReporter(reporter);
		extent.setSystemInfo("Tester", "Rahul Sony");
	}
	
	@Test(dataProvider="driveTest")
	public void testCaseData(String email,String password,String productname) throws InterruptedException {
	
		// TODO Auto-generated method stub
		ExtentTest test = extent.createTest("StanAlone Test");
		WebDriverManager.chromedriver().setup();
		ChromeOptions co = new ChromeOptions();
		co.addArguments("--remote-allow-origins=*");
		WebDriver driver = new ChromeDriver(co);
		driver.get("https://rahulshettyacademy.com/client");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
		//Email
		driver.findElement(By.xpath("//input[@id='userEmail']")).sendKeys("abcdxyz@gmail.com");
		//Enter password
		driver.findElement(By.xpath("//input[@id='userPassword']")).sendKeys("Abcd@123");
		//Login
		driver.findElement(By.xpath("//input[@id='login']")).click();
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(".mb-3")));
		
		//Capture all items in a WebPage for shopping
		List<WebElement> products = driver.findElements(By.cssSelector(".mb-3"));
		WebElement prod = products.stream().filter(product->product.findElement(By.cssSelector("b")).getText()
		.equals(productname)).findFirst().orElse(null);
		Thread.sleep(500);
		prod.findElement(By.cssSelector(".card-body button[style='float: right;']")).click();
		
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("#toast-container")));
		
		//ng-animating
		wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ng-animating"))));
		
		//Click on Cart
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//button[@routerlink='/dashboard/cart']")));
		driver.findElement(By.xpath("//button[@routerlink='/dashboard/cart']")).click();
		System.out.println("Successfully Added into the cart");
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//li/div/div/h3")));
		
		List<WebElement> cartMessage = driver.findElements(By.xpath("//li/div/div/h3"));
		Boolean match = cartMessage.stream().anyMatch(s->s.getText().equals(productname));
		Assert.assertTrue(match);
		
		//Checkout
		driver.findElement(By.xpath("//div//ul/li/button")).click();
		
		//Country
		driver.findElement(By.xpath("//input[@placeholder='Select Country']")).sendKeys("ind");
		Thread.sleep(3000);
		
		List<WebElement> options = driver.findElements(By.cssSelector("section[class='ta-results list-group ng-star-inserted'] button[type='button']"));
		
		for(WebElement option : options)
		{
			if(option.getText().equalsIgnoreCase("India"))
			{
				option.click();
				System.out.println("Successfully picked country");
				break;
			}
		}
		
		Thread.sleep(2000);
		//Place Order
		driver.findElement(By.cssSelector(".action__submit")).click();
		
		//Grab Order ID
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//label[@class='ng-star-inserted']")));
		System.out.println("The Order ID :- "+driver.findElement(By.xpath("//label[@class='ng-star-inserted']")).getText());
		String confirmMessage = driver.findElement(By.cssSelector(".hero-primary")).getText();
		Assert.assertEquals(confirmMessage, "THANKYOU FOR THE ORDER.");
		System.out.println("Able to complete the entire transaction for a order");
		driver.close();	
		extent.flush();

	}

	@DataProvider(name="driveTest")
	public Object[][] getData() throws IOException {
		FileInputStream fis = new FileInputStream("C:\\Users\\DELL\\OneDrive\\Desktop\\Selenium WebDriver\\New_folder\\IntegrationWithExcel\\demoData.xlsx");
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		int sheets = workbook.getNumberOfSheets();
		System.out.println(sheets);
		
		XSSFSheet sheet = workbook.getSheetAt(1);
		int rowCount = sheet.getPhysicalNumberOfRows();
		XSSFRow row = sheet.getRow(0);
		int columnCount = row.getLastCellNum();
		Object[][] data = new Object[rowCount-1][columnCount];
		for(int j=0;j<rowCount-1;j++)
		{
			row = sheet.getRow(j+1);
			for(int k=0;k<columnCount;k++)
			{
				XSSFCell cell = row.getCell(k);
				data[j][k]=formatter.formatCellValue(cell);
							
			}
		}
		return data;
	}
}
