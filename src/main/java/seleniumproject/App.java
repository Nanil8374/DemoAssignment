package seleniumproject;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class App {

	public static void main(String[] args) {
		// Set the path of the ChromeDriver executable
		System.setProperty("webdriver.chrome.driver",
				"C:\\Users\\anil.narendruni\\Desktop\\Benfits_UIAutomation\\slx-worklinx-v6-ui-tests\\UITestsV6\\bin\\stg-abcm0318\\chromedriver.exe\\");

		// Create a new instance of the ChromeDriver
		WebDriver driver = new ChromeDriver();

		// Navigate to the URL
		driver.get("http://demowebshop.tricentis.com/");
		// Window Maximize
		driver.manage().window().maximize();
		// Find the login button element by id
		WebElement loginButton = driver.findElement(By.className("ico-login"));

		// Click on the login button
		loginButton.click();

		// Find the username and password fields and enter the credentials
		WebElement usernameField = driver.findElement(By.id("Email"));
		WebElement passwordField = driver.findElement(By.id("Password"));
		usernameField.sendKeys("planittest78@gmail.com");
		passwordField.sendKeys("123456");

		// Find the submit button and click it
		WebElement submitButton = driver.findElement(By.xpath("//input[@class='button-1 login-button']"));
		submitButton.click();

		// Wait for the login to complete and verify that the user is logged in
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.urlToBe("https://demowebshop.tricentis.com/"));

		// Validate the user account ID on top right
		WebElement accountId = driver.findElement(By.className("account"));
		String expectedAccountId = "planittest78@gmail.com";
		String actualAccountId = accountId.getText();
		if (actualAccountId.equals(expectedAccountId)) {
			System.out.println("User account ID validation passed");
		} else {
			System.out.println("User account ID validation failed");
		}
		// Clear the shopping cart
		if (!driver.findElement(By.className("cart-qty")).getText().equals("(0)")) {
			driver.findElement(By.className("cart-label")).click();
			driver.findElement(By.name("removefromcart")).click();
			driver.findElement(By.name("updatecart")).click();
		}
		// Mouse hover on the "Computers" category and select "Desktops"
		Actions actions = new Actions(driver);
		WebElement computersMenu = driver.findElement(By.xpath("/html/body/div[4]/div[1]/div[2]/ul[1]/li[2]/a"));
		actions.moveToElement(computersMenu).build().perform();
		actions.moveToElement(computersMenu).click();
		driver.findElement(By.linkText("Desktops")).click();

		// select a computer from the list displayed
		WebElement productLink = driver.findElement(By.linkText("Build your own expensive computer"));
		productLink.click();

		// get the price details and enter the quantity
		WebElement priceLabel = driver.findElement(By.xpath("//span[@class='price-value-74'][1]"));
		String price = priceLabel.getText();
		WebElement quantityField = driver.findElement(By.id("addtocart_74_EnteredQuantity"));
		quantityField.clear();
		quantityField.sendKeys("2");

		// click on "Add to cart"
		WebElement addToCartButton = driver.findElement(By.id("add-to-cart-button-74"));
		addToCartButton.click();

		// Validate "The product has been added to your shopping cart" message
		WebElement successMessage = driver.findElement(By.id("bar-notification"));
		String expectedMessage = "The product has been added to your ";
		String actualMessage = successMessage.getText();
		if (actualMessage.contains(expectedMessage)) {
			System.out.println("Success message validation passed");
		} else {
			System.out.println("Success message validation failed");
		}

		// Click on “shopping cart” on top right and validate the “Sub-Total” Price for
		// selected computer
		driver.findElement(By.className("cart-label")).click();
		WebElement subTotal = driver.findElement(By.cssSelector("span.product-subtotal"));
		assert subTotal.getText().contains("$");

		// Select I agree Checkbox and click on Check-out
		driver.findElement(By.id("termsofservice")).click();
		driver.findElement(By.cssSelector("button[type='submit'][name='checkout']")).click();

		if (driver.findElements(By.id("billing-address-select")).size() != 0) {
			Select selectAddress = new Select(driver.findElement(By.id("billing-address-select")));
			selectAddress.selectByVisibleText("New Address");
		}
		// Fill all mandatory fields in "Billing Address" and click "Continue"
		driver.findElement(By.id("BillingNewAddress_FirstName")).sendKeys("John");
		driver.findElement(By.id("BillingNewAddress_LastName")).sendKeys("Doe");
		//driver.findElement(By.id("BillingNewAddress_Email")).sendKeys("johndoe@test.com");
		driver.findElement(By.id("BillingNewAddress_CountryId")).sendKeys("United States");
		driver.findElement(By.id("BillingNewAddress_City")).sendKeys("New York");
		driver.findElement(By.id("BillingNewAddress_Address1")).sendKeys("123 Main St");
		driver.findElement(By.id("BillingNewAddress_ZipPostalCode")).sendKeys("10001");
		driver.findElement(By.id("BillingNewAddress_PhoneNumber")).sendKeys("1234567890");
		driver.findElement(By.cssSelector(".button-1.new-address-next-step-button")).click();

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		// Select the "Shipping Address" as same as "Billing Address" from "Shipping Address" drop down and click on "Continue"
		Select selectAddress = new Select(driver.findElement(By.id("shipping-address-select")));
		selectAddress.selectByVisibleText(
				"planitJohn testDoe, 123 Main St, New York, AA (Armed Forces Americas) 10001, United States");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		// driver.findElement(By.cssSelector(".button-1.shipping-method-next-step-button")).click();
		driver.findElement(By.xpath("//div[contains(@id,'shipping-buttons-container')]/input")).click();

		// Select the shipping method as "Next Day Air" and click on "Continue"
		driver.findElement(By.id("shippingoption_1")).click();
		driver.findElement(By.xpath("//div[contains(@id,'shipping-method-buttons-container')]/input")).click();

		// Choose the payment method as COD (Cash on delivery) and click on "Continue"
		driver.findElement(By.id("paymentmethod_0")).click();
		// driver.findElement(By.cssSelector(".button-1.payment-info-next-step-button")).click();
		driver.findElement(By.xpath("//div[contains(@id,'payment-method-buttons-container')]/input")).click();

		//Validate the message "You will pay by COD" and click on "Continue"
		String paymentMethodText = driver.findElement(By.xpath("//p[contains(text(), 'You will pay by COD')]"))
				.getText();
		if (paymentMethodText.equals("You will pay by COD")) {
			System.out.println("Message validated successfully.");
		}
		driver.findElement(By.xpath("//div[contains(@id,'payment-info-buttons-container')]/input")).click();

		//Click on "Confirm" button
		driver.findElement(By.xpath("//div[contains(@id,'confirm-order-buttons-container')]/input")).click();

		//Validate the message "Your order has been successfully processed!" and print the Order number
		 String orderConfirmationText =driver.findElement(By.xpath("//strong[contains(text(),'Your order has been successfully processed!')]")).getText();
		if(orderConfirmationText.equals("Your order has been successfully processed!")) {
		System.out.println("Order confirmation message validated successfully.");
		}
		String orderNumber = driver.findElement(By.className("details")).getText();
		System.out.println("Order number is: " + orderNumber);

		//Click on “Continue” and log out from the application
		driver.findElement(By.className("buttons")).click();

		driver.findElement(By.className("ico-logout")).click();
		System.out.println("Logged out");

		// Close the browser
		driver.quit();
	}
}