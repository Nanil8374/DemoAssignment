package seleniumproject;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.*;

public class Assignment extends MySeleniumTest {
	WebDriver driver;

	@BeforeClass
	public void setup() {
		System.setProperty("webdriver.chrome.driver",
				"C:\\Users\\anil.narendruni\\Desktop\\Benfits_UIAutomation\\slx-worklinx-v6-ui-tests\\UITestsV6\\bin\\stg-abcm0318\\chromedriver.exe\\");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
	}

	@Test(priority = 1)
	public void login() {
		driver.get("http://demowebshop.tricentis.com/");
		driver.findElement(By.className("ico-login")).click();
		driver.findElement(By.id("Email")).sendKeys("planittest78@gmail.com");
		driver.findElement(By.id("Password")).sendKeys("123456");
		driver.findElement(By.cssSelector("input[value='Log in']")).click();
		String expectedUserAccountId = "planittest78@gmail.com";
		String actualUserAccountId = driver.findElement(By.className("account")).getText();
		Assert.assertEquals(actualUserAccountId, expectedUserAccountId);
	}

	@Test(priority = 2)
	public void clearShoppingCart() {
		driver.findElement(By.className("cart-label")).click();
		WebElement cartItemCheckbox = driver.findElement(By.cssSelector(".cart-item input[type='checkbox']"));
		if (cartItemCheckbox.isSelected()) {
			cartItemCheckbox.click();
			driver.findElement(By.name("updatecart")).click();
		}
	}

	@Test(priority = 3)
	public void selectDesktop() throws InterruptedException {
		Actions action = new Actions(driver);
		WebElement computersMenu = driver.findElement(By.linkText("Computers"));
		action.moveToElement(computersMenu).perform();
		Thread.sleep(1000);
		driver.findElement(By.linkText("Desktops")).click();
		driver.findElement(By.linkText("Build your own cheap computer")).click();
		String expectedPrice = "1815.00";
		String actualPrice = driver.findElement(By.cssSelector(".product-price .price")).getText();
		Assert.assertEquals(actualPrice, expectedPrice);
		driver.findElement(By.id("product_enteredQuantity_1")).clear();
		driver.findElement(By.id("product_enteredQuantity_1")).sendKeys("2");
		driver.findElement(By.id("add-to-cart-button-1")).click();
		//String expectedMessage = "The product has been added to your shopping cart";
		//String actualMessage = driver.findElement(By.cssSelector(".bar-notification.success")).getText();
		//Assert.assertEquals(actualMessage, expectedMessage);
	}

	@Test(priority = 4)
	public void validateSubTotalPrice() {
		//driver.findElement(By.className("cart-label")).click();
		//String expectedSubTotalPrice = "3630.00";
		//String actualSubTotalPrice = driver.findElement(By.cssSelector(".cart-total-right .product-price")).getText();
		//Assert.assertEquals(actualSubTotalPrice, expectedSubTotalPrice);
		driver.findElement(By.id("termsofservice")).click();
		driver.findElement(By.id("checkout")).click();
		if (driver.findElements(By.cssSelector("#billing-address-select option[value='NewAddress']")).size() > 0) {
			driver.findElement(By.cssSelector("#billing-address-select option[value='NewAddress']")).click();
		}
		driver.findElement(By.id("BillingNewAddress_FirstName")).sendKeys("John");
		driver.findElement(By.id("BillingNewAddress_LastName")).sendKeys("Doe");
		driver.findElement(By.id("BillingNewAddress_Email")).sendKeys("john.doe@gmail.com");
		driver.findElement(By.id("BillingNewAddress_CountryId")).sendKeys("United States");
		driver.findElement(By.id("BillingNewAddress_City")).sendKeys("Hyderabad");
		driver.findElement(By.id("BillingNewAddress_Address1")).sendKeys("123 Main St");
		driver.findElement(By.id("BillingNewAddress_ZipPostalCode")).sendKeys("10001");
		driver.findElement(By.id("BillingNewAddress_PhoneNumber")).sendKeys("1234567890");
		driver.findElement(By.cssSelector(".button-1.new-address-next-step-button")).click();

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		//17. Select the "Shipping Address" as same as "Billing Address" from "Shipping Address" drop down and click on "Continue"
		Select selectAddress = new Select(driver.findElement(By.id("shipping-address-select")));
		selectAddress.selectByVisibleText("planitJohn testDoe, 123 Main St, New York, AA (Armed Forces Americas) 10001, United States");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		//driver.findElement(By.cssSelector(".button-1.shipping-method-next-step-button")).click();
		driver.findElement(By.xpath("//div[contains(@id,'shipping-buttons-container')]/input")).click();
		
		//18. Select the shipping method as "Next Day Air" and click on "Continue"
		driver.findElement(By.id("shippingoption_1")).click();
		driver.findElement(By.xpath("//div[contains(@id,'shipping-method-buttons-container')]/input")).click();

		//19. Choose the payment method as COD (Cash on delivery) and click on "Continue"
		driver.findElement(By.id("paymentmethod_0")).click();
		//driver.findElement(By.cssSelector(".button-1.payment-info-next-step-button")).click();
		driver.findElement(By.xpath("//div[contains(@id,'payment-method-buttons-container')]/input")).click();
		
		//20. Validate the message "You will pay by COD" and click on "Continue"
		String paymentMethodText = driver.findElement(By.xpath("//p[contains(text(), 'You will pay by COD')]")).getText();
		if(paymentMethodText.equals("You will pay by COD")) {
		    System.out.println("Message validated successfully.");
		}
		driver.findElement(By.xpath("//div[contains(@id,'payment-info-buttons-container')]/input")).click();
		
		//21. Click on "Confirm" button
		//driver.findElement(By.cssSelector(".button-1.confirm-order-next-step-button")).click();
		driver.findElement(By.xpath("//div[contains(@id,'confirm-order-buttons-container')]/input")).click();
		
		//22. Validate the message "Your order has been successfully processed!" and print the Order number
		//String orderConfirmationText = driver.findElement(By.xpath("//h1[contains(text(), 'Your order has been successfully processed!')]")).getText();
		//if(orderConfirmationText.equals("Your order has been successfully processed!")) {
		//    System.out.println("Order confirmation message validated successfully.");
		//}
		String orderNumber = driver.findElement(By.className("details")).getText();
		//String orderNumber = driver.findElement(By.xpath("//strong[contains(text(), 'Order number:')]")).getText();
		System.out.println("Order number is: " + orderNumber);
		
		driver.findElement(By.className("buttons")).click();
		
		driver.findElement(By.className("ico-logout")).click();
		System.out.println("Loggedout");
		
		// Close the browser
		
		
		
		driver.quit();
	}

	}

