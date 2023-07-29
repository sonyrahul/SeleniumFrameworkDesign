package rahulsony.tests;

import java.util.List;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import rahulsony.TestComponents.BaseTest;
import rahulsony.TestComponents.Retry;
import rahulsony.pageobjects.CartCheckOut;
import rahulsony.pageobjects.ProductCatalogue;

public class ErrorValidationTest extends BaseTest {
	
	@Test(groups= {"ErrorHandling"},retryAnalyzer=Retry.class)
	public void LoginErrorValidation ()
	{
		landingpage.loginApplication("abcdxyz@gmail.com", "Xyz@123");
		Assert.assertEquals("Incorrect email or password.", landingpage.getErrorMessage());
	}
	
	@Test
	public void ProductErrorValidation() throws InterruptedException
	{
		String productname = "ZARA COAT 3";
		landingpage.loginApplication("abcdxyz@gmail.com", "Abcd@123");
		//Capture all items in a web-page for shopping
		ProductCatalogue productCatalogue = new ProductCatalogue(driver);
		List<WebElement> products = productCatalogue.getProductList();
		productCatalogue.addProductToCart(productname);
		productCatalogue.goToCartPage();
				
		//Verify
		CartCheckOut cartCheckIn = new CartCheckOut(driver);
		Boolean match = cartCheckIn.verifyProductDisplay(productname);
		Assert.assertTrue(match);
	}

}
