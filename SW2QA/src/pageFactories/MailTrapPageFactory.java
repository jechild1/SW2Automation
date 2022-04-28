package pageFactories;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import utilities.AutomationHelper;

/**
 * Page factory for the Mail Trap site. The mail trap can be used to verify
 * password resets, perform activations, etc. <br>
 * This page factory will NOT encompass all the features of the mail trap
 * application. Rather, it will only contain methods to perform minimum
 * functions.
 * 
 * @author Jesse Childress
 *
 */
public class MailTrapPageFactory extends SW2Base {

	/**
	 * Page Constructor: Accepts the WebDriver from the calling page & instantiates
	 * the elements on the page.
	 */
	public MailTrapPageFactory() {
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//a[.='Log In']")
	WebElement logIn;

//	/**
//	 * Clicks the Log In button on the main page.
//	 */
//	public void clickLogin() {
//		AutomationHelper.printMethodName();
//		logIn.click();
//	}

	@FindBy(id = "user_email")
	WebElement emailAddress;

	/**
	 * Sets the Email field with the passed in value.
	 * 
	 * @param emailAddress
	 */
	public void setEmail(String emailAddress) {
		AutomationHelper.printMethodName();
		AutomationHelper.setTextField(this.emailAddress, emailAddress);
	}

	@FindBy(id = "user_password")
	WebElement password;

	/**
	 * Sets the Password field with the passed in value.
	 * 
	 * @param password
	 */
	public void setPassword(String password) {
		AutomationHelper.printMethodName();
		AutomationHelper.setTextField(this.password, password);
	}
	
	/**
	 * Clicks the next button, which appears after setting the user name.
	 */
	public void clickNext() {
		AutomationHelper.printMethodName();
		driver.findElement(By.linkText("Next")).click();
	}

	@FindBy(xpath = "//input[@value='Log In']")
	WebElement logInAfterSettingUserNamePass;



	/**
	 * Clicks the Log In button on the Sign On Page <br>
	 * This is the Login Button on the Sign On page.
	 * 
	 */
	public void clickLogInAfterSettingUserNameAndPass() {
		AutomationHelper.printMethodName();
		logInAfterSettingUserNamePass.click();
	}

	@FindBy(xpath = "//a[@title='Demo inbox']")
	WebElement demoInbox;

	/**
	 * Clicks the "Demo inbox" link from the Projects page.
	 */
	public void clickDemoInbox() {
		AutomationHelper.printMethodName();
		demoInbox.click();
	}

	/**
	 * This method selects an email from the SW2 Mail Trap that contains the subject
	 * and
	 * 
	 * @param emailSubject
	 * @param emailAddressee
	 */
	public void clickEmail(String emailSubject, String emailAddressee) {
		AutomationHelper.printMethodName();

		String xpath = "//a[contains(@class, 'unread')][descendant::span[contains(.,'" + emailAddressee + "')] and child::span[text()='SW2 - Reset Password']]";

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));

		WebElement emailToClick = driver.findElement(By.xpath(xpath));
		emailToClick.click();

	}
	
	public void test() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("")));
	}

}
