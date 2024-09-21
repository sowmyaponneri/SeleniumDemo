package automation.stepdefinitions;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;

import automation.TestComponents.BaseTest;
import automation.pageobjects.CartPage;
import automation.pageobjects.CheckoutPage;
import automation.pageobjects.ConfirmationPage;
import automation.pageobjects.LandingPage;
import automation.pageobjects.ProductCatalouge;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class StepDefinitionImpl extends BaseTest{

	public LandingPage landingPage;
	public ProductCatalouge pdtCatalouge;
	public CartPage cartPage;
	public ConfirmationPage confirmationPage;
	
	@Given("I landed on Eccomerce Page")
	public void I_landed_on_Eccomerce_Page() throws IOException
	{
		landingPage = launchApplication();
	}
	
	@Given("^Logged in with username (.+) and password (.+)$")
	public void logged_in_with_username_and_password(String username, String password)
	{
		pdtCatalouge=landingPage.loginApp(username,password);
	}
	@When("^I add the product (.+) to Cart$")
	public void I_add_the_Product_to_Cart(String productName) throws InterruptedException
	{
		List<WebElement> products=pdtCatalouge.getProductList();
		pdtCatalouge.addProductToCart(productName);
	}
	@When("^Checkout (.+) and submit the order$")
	public void Checkout_Submit_Order(String productName)
	{
		cartPage=pdtCatalouge.goToCartPage();
		boolean match=cartPage.verifyProductDisplay(productName);
		Assert.assertTrue(match);
		CheckoutPage checkoutPage=cartPage.goToCheckout();
		checkoutPage.selectCountry("india");
		confirmationPage=checkoutPage.submitOrder();
	}
	@Then("{string} message is displayed in ConfirmationPage")
	public void message_displayed_ConfirmationPage(String string)
	{
		String confirmMsg=confirmationPage.getConfirmationMsg();
		Assert.assertTrue(confirmMsg.equalsIgnoreCase(string));
	}
}
