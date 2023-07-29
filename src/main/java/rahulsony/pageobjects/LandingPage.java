package rahulsony.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import rahulsony.AbstractComponents.AbstractComponent;


public class LandingPage extends AbstractComponent {
	
	WebDriver driver;
	public LandingPage(WebDriver driver) {
		super(driver);
		//Initialization
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	//PageFactory
	@FindBy(xpath="//input[@id='userEmail']")
	WebElement userEmail;
	
	@FindBy(xpath="//input[@id='userPassword']")
	WebElement passwordEle;
	
	@FindBy(xpath="//input[@id='login']")
	WebElement submit;
	
	@FindBy(css="[class*='flyInOut']")
	WebElement errorMessage;
	
	//Action Methods
	public void loginApplication(String email,String password)
	{
		userEmail.sendKeys(email);
		passwordEle.sendKeys(password);
		submit.click();
	}
	
	public String getErrorMessage()
	{
		watForWebElementToAppear(errorMessage);
		return errorMessage.getText();
	}
	
	public void goTo()
	{
		driver.get("https://rahulshettyacademy.com/client");
	}


}
