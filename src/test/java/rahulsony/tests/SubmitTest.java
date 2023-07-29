package rahulsony.tests;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import rahulsony.TestComponents.BaseTest;
import rahulsony.pageobjects.CartCheckOut;
import rahulsony.pageobjects.CountryPlaceOrder;
import rahulsony.pageobjects.ProductCatalogue;

public class SubmitTest extends BaseTest{
	
	@Test(dataProvider="driveTest",groups= {"Purchase"})
	public void SubmitOrder(HashMap<String,String> input) throws InterruptedException
	{
		landingpage.loginApplication(input.get("email"), input.get("password"));
		
		//Capture all items in a web-page for shopping
		ProductCatalogue productCatalogue = new ProductCatalogue(driver);
		List<WebElement> products = productCatalogue.getProductList();
		productCatalogue.addProductToCart(input.get("productname"));
		productCatalogue.goToCartPage();
		
		//Verify & Click on CheckOut
		CartCheckOut cartCheckIn = new CartCheckOut(driver);
		Boolean match = cartCheckIn.verifyProductDisplay(input.get("productname"));
		Assert.assertTrue(match);
		cartCheckIn.goToCheckOut();
		
		//Country
		CountryPlaceOrder countryPlaceOrder = new CountryPlaceOrder(driver);
		countryPlaceOrder.Country();
		countryPlaceOrder.PlaceGrabOrderID();
		countryPlaceOrder.verifyFinal();
		
	}
	
	@Test(dependsOnMethods= {"SubmitOrder"},dataProvider="driveTest")
	public void orderHistory(HashMap<String,String>input) throws InterruptedException
	{
		landingpage.loginApplication(input.get("email"), input.get("password"));
		ProductCatalogue productCatalogue = new ProductCatalogue(driver);
		productCatalogue.goToOrderPage();
		
		//Verify & CLick on CheckOut
		CartCheckOut OrderIn = new CartCheckOut(driver);
		Boolean match = OrderIn.verifyOrderDisplay(input.get("productname"));
		Assert.assertTrue(match);
		OrderIn.deleteOrder();
		
	}
	
	@DataProvider(name="driveTest")
	public Object[][] getData() throws IOException
	{
		List<HashMap<String,String>> data = getJsonDataToMap(System.getProperty("user.dir")+"\\src\\test\\java\\rahulsony\\data\\PurchaseOrder.json");
		return new Object[][] {{data.get(0)},{data.get(1)}};
	}

}
