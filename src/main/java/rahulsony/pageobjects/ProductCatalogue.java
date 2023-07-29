package rahulsony.pageobjects;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import rahulsony.AbstractComponents.AbstractComponent;

public class ProductCatalogue extends AbstractComponent{
	
	WebDriver driver;
	public ProductCatalogue(WebDriver driver) //Use of constructors 
	{ 
		super(driver);
		//Initialization
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	//PageFactory
	@FindBy(css=".mb-3")
	List<WebElement> products;
	
	@FindBy(css=".ng-animating")
	List<WebElement> ngAnimating;
	
	By productBy = By.cssSelector(".mb-3");
	
	By addToCart = By.cssSelector(".card-body button[style='float: right;']");
	
	By toast = By.cssSelector("#toast-container");
	
	public List<WebElement> getProductList()
	{
		waitForElementToAppear(productBy);
		return products;
	}
	
	public WebElement getProductByName(String productname)
	{
		WebElement prod = products.stream().filter(product->product.findElement(By.cssSelector("b")).getText()
				.equals(productname)).findFirst().orElse(null);
		return prod;
	}
	
	public void addProductToCart(String productname) throws InterruptedException
	{
		WebElement prod = getProductByName(productname);
		prod.findElement(addToCart).click();
		waitForElementToAppear(toast);
		Thread.sleep(2000);
	}
	
}
