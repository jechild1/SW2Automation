package pageFactories;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import utilities.AutomationHelper;

/**
 * Page factory for the Login page for SW2.<br>
 * (1) Finds a reference to objects on the page <br>
 * (2) Defines methods to interact with objects on the page.<br>
 * 
 * @author Jesse Childress
 *
 */
public class LoginPageFactory extends SW2Base {

	/**
	 * Page Constructor: Accepts the WebDriver from the calling page & instantiates
	 * the elements on the page.
	 */
	public LoginPageFactory() {
		PageFactory.initElements(driver, this);
	}

	@FindBy(id = "formGroup-email")
	WebElement email;

	/**
	 * This method sets the Email text field
	 * 
	 * @param text
	 */
	public void setEmail(String text) {
		AutomationHelper.printMethodName();
		AutomationHelper.setTextField(email, text);
	}

	/**
	 * Method to read the data inside the Email field.
	 * 
	 * @return String
	 */
	public String readEmail() {
		AutomationHelper.printMethodName();
		return email.getText();
	}

	@FindBy(xpath = "//div[@class='form-group form-group--text form-group--invalid bottom-outline']/p")
	List<WebElement> emailError;

	/**
	 * Reads the error for the email text field if exists
	 */
	public String readEmailError() {

		AutomationHelper.printMethodName();
		// Do not want this to error if doesn't exist - so passing web element
		// to a list and then checking list for null

		String error = "";

		AutomationHelper.adjustWaitTimeToShort();
		if (emailError.size() > 0) {
			error = emailError.get(0).getText();
		}
		AutomationHelper.adjustWaitTimeToNormal();

		return error;
	}

	@FindBy(id = "formGroup-password")
	WebElement password;

	/**
	 * This method sets the Password text field
	 * 
	 * @param text
	 */
	public void setPassword(String text) {
		AutomationHelper.printMethodName();
		AutomationHelper.setTextField(password, text);
	}
	
	/**
	 * Method to read the data inside the password field.
	 * 
	 * @return String
	 */
	public String readPassword() {
		AutomationHelper.printMethodName();
		return password.getText();
	}

	@FindBy(id = "formGroup-password")
	WebElement newPassword;

	/**
	 * Sets the New Password field.
	 * <p>
	 * Note: This field is visible on the Forgot Password page, after following the
	 * URL from the mail trap
	 * 
	 * @param password
	 */
	public void setNewPassword(String password) {
		AutomationHelper.printMethodName();
		newPassword.sendKeys(password);
	}

	@FindBy(xpath = "//div[@class='form-group form-group--password form-group--invalid bottom-outline']/p")
	List<WebElement> passwordError;

	/**
	 * Reads the error for the email text field if exists
	 */
	public String readPasswordError() {

		AutomationHelper.printMethodName();
		// Do not want this to error if doesn't exist - so passing web element
		// to a list and then checking list for null

		String error = "";

		AutomationHelper.adjustWaitTimeToShort();
		if (passwordError.size() > 0) {
			error = passwordError.get(0).getText();
		}
		AutomationHelper.adjustWaitTimeToNormal();

		return error;
	}

	@FindBy(xpath = "//button[@type='submit' and text()='Login']")
	WebElement login;

	/**
	 * Click on "Login"
	 */
	public void clickLogin() {
		AutomationHelper.printMethodName();
		login.click();
	}

	@FindBy(xpath = "//button[@type='submit' and text()='Send']")
	WebElement send;

	/**
	 * Click on "Send". Note: Only present when on the Forgot Password modal.
	 */
	public void clickSend() {
		AutomationHelper.printMethodName();
		send.click();
	}

	@FindBy(xpath = "//button[@type='submit' and text()='Submit']")
	WebElement submit;

	/**
	 * Click on "Submit". <br>
	 * Note: Only present when on the New Password page.
	 */
	public void clickSubmit() {
		AutomationHelper.printMethodName();
		submit.click();
	}

	@FindBy(linkText = "Forgot password")
	WebElement forgotPasswordLink;

	/**
	 * Clicks the Forgot Password link
	 */
	public void clickForgotPassword() {
		AutomationHelper.printMethodName();
		forgotPasswordLink.click();
	}

	@FindBy(linkText = "Cancel")
	WebElement cancelLink;

	/**
	 * Clicks the Cancel button. This is only visible when the Forgot Password page
	 * is displayed.
	 */
	public void clickCancel() {
		AutomationHelper.printMethodName();
		cancelLink.click();
	}

	@FindBy(xpath = "//div[@class='form-actions']/p")
	List<WebElement> mainErrorMessageText;

	/**
	 * Returns the text of the main error message on the page.
	 * 
	 * @return String - The text of the main error message
	 */
	public String readMainErrorMessageText() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOfAllElements(mainErrorMessageText));

		AutomationHelper.printMethodName();
		// Do not want this to error if doesn't exist - so passing web element
		// to a list and then checking list for null

		String error = "";

		AutomationHelper.adjustWaitTimeToShort();
		if (mainErrorMessageText.size() > 0) {
			error = mainErrorMessageText.get(0).getText();
		}
		AutomationHelper.adjustWaitTimeToNormal();

		return error;
	}

}
