package automation.tests;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import automation.TestComponents.BaseTest;
import automation.TestComponents.Retry;
import automation.pageobjects.CartPage;
import automation.pageobjects.CheckoutPage;
import automation.pageobjects.ConfirmationPage;
import automation.pageobjects.LandingPage;
import automation.pageobjects.ProductCatalouge;
import io.github.bonigarcia.wdm.WebDriverManager;

public class ErrorValidationsTest extends BaseTest {

	
		@Test(groups= {"ErrorHandling"},retryAnalyzer=Retry.class)
		public void LoginErrorValidation()throws IOException
		{
		landingPage.loginApp("ramso@gmail.com","Dec16mar1");
		Assert.assertEquals("Incorrect email or password.",landingPage.getErrorMsg());
		}
		
		@Test	
		public void ProductErrorValidation()throws IOException, InterruptedException
		{
		String productName="ZARA COAT 3";
		ProductCatalouge pdtCatalouge=landingPage.loginApp("ramso1@gmail.com","Roshan-12");
		List<WebElement> products=pdtCatalouge.getProductList();
		pdtCatalouge.addProductToCart(productName);
		CartPage cartPage=pdtCatalouge.goToCartPage();
		boolean match=cartPage.verifyProductDisplay("ZARA COAT 33");
		Assert.assertFalse(match);
		}

}
