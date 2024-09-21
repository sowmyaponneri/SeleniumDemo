package automation.tests;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import automation.TestComponents.BaseTest;
import automation.pageobjects.CartPage;
import automation.pageobjects.CheckoutPage;
import automation.pageobjects.ConfirmationPage;
import automation.pageobjects.LandingPage;
import automation.pageobjects.ProductCatalouge;
import io.github.bonigarcia.wdm.WebDriverManager;

public class SubmitOrderTest extends BaseTest {

	String productName="ZARA COAT 3";
		@Test(dataProvider="getData",groups= {"Purchase"})	
		public void submitOrder(HashMap<String,String> input)throws IOException, InterruptedException
		{
		ProductCatalouge pdtCatalouge=landingPage.loginApp(input.get("email"),input.get("password"));
		List<WebElement> products=pdtCatalouge.getProductList();
		pdtCatalouge.addProductToCart(productName);
		CartPage cartPage=pdtCatalouge.goToCartPage();
		boolean match=cartPage.verifyProductDisplay(productName);
		Assert.assertTrue(match);
		CheckoutPage checkoutPage=cartPage.goToCheckout();
		checkoutPage.selectCountry("india");
		ConfirmationPage confirmationPage=checkoutPage.submitOrder();
		String confirmMsg=confirmationPage.getConfirmationMsg();
		Assert.assertTrue(confirmMsg.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
	}
		

		@DataProvider
		public Object[][] getData() throws IOException
		{
			/*HashMap<String,String> map=new HashMap<String,String>();
			map.put("email", "ramso@gmail.com");
			map.put("password", "Dec16mar12bm@");
			
			HashMap<String,String> map1=new HashMap<String,String>();
			map1.put("email", "ramso1@gmail.com");
			map1.put("password", "Roshan-12");*/
			
			List<HashMap<String,String>> data= getJasonDataToMap(System.getProperty("user.dir")+"//src//test//java//automation//data//PurchaseOrder.json");
			
		    return new Object[][] {{data.get(0)},{data.get(1)}};
		}
}
