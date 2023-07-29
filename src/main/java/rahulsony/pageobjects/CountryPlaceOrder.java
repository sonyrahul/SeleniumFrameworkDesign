package rahulsony.pageobjects;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import rahulsony.AbstractComponents.AbstractComponent;

public class CountryPlaceOrder extends AbstractComponent{
	
	WebDriver driver;
	public CountryPlaceOrder(WebDriver driver) //Use of constructors 
	{ 
		super(driver);
		//Initialization
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath="//input[@placeholder='Select Country']")
	WebElement CountryEle;
	
	@FindBy(css="section[class='ta-results list-group ng-star-inserted'] button[type='button']")
	List<WebElement> options;
	
	@FindBy(css=".action__submit")
	WebElement place;
	
	By GrabOrderID = By.xpath("//label[@class='ng-star-inserted']");
	
	
	public void Country() throws InterruptedException 
	{
		CountryEle.sendKeys("Ind");
		Thread.sleep(3000);
		for(WebElement option : options)
		{
			if(option.getText().equalsIgnoreCase("India"))
			{
				option.click();
				System.out.println("Successfully picked country");
				break;
			}
		}
	}
	
	public void PlaceGrabOrderID() throws InterruptedException
	{
		Thread.sleep(2000);
		place.click();	
		waitForElementToAppear(GrabOrderID);
	}
	
	public void verifyFinal()
	{
		System.out.println("The Order ID :- "+driver.findElement(By.xpath("//label[@class='ng-star-inserted']")).getText());
		String confirmMessage = driver.findElement(By.cssSelector(".hero-primary")).getText();
		Assert.assertEquals(confirmMessage, "THANKYOU FOR THE ORDER.");
		System.out.println("Able to complete the entire transaction for a order");
	}
		
}
