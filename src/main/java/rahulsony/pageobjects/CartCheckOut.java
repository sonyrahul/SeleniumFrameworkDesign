package rahulsony.pageobjects;

import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import rahulsony.AbstractComponents.AbstractComponent;

public class CartCheckOut extends AbstractComponent {
	
	WebDriver driver;
	public CartCheckOut(WebDriver driver) //Use of constructors 
	{ 
		super(driver);
		//Initialization
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	//PageFactory
	@FindBy(xpath="//li/div/div/h3")
	private List<WebElement> cartProducts;
	
	@FindBy(css="tr td:nth-child(3)")
	private List<WebElement> ProductNames;	
	
	@FindBy(css="tr td:nth-child(7)")
	private WebElement Delete;
	
	@FindBy(xpath="//div//ul/li/button")
	WebElement CheckOut;
	
	public Boolean verifyProductDisplay(String productname)
	{
		Boolean match = cartProducts.stream().anyMatch(product->product.getText().equalsIgnoreCase(productname));
		return match;
	}
	
	public Boolean verifyOrderDisplay(String productname) throws InterruptedException 
	{
		Thread.sleep(2000);
		Boolean match = ProductNames.stream().anyMatch(product->product.getText().equalsIgnoreCase(productname));
		return match;
	}
	
	public void goToCheckOut()
	{
		CheckOut.click();
	}
	
	public void deleteOrder() throws InterruptedException
	{
		Thread.sleep(1000);
		Delete.click();
	}
	
}
