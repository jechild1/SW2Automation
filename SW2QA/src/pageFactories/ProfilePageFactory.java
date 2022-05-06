package pageFactories;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import utilities.AutomationHelper;

/**
 * Page factory for the Profile page for SW2.<br>
 * (1) Finds a reference to objects on the page <br>
 * (2) Defines methods to interact with objects on the page.<br>
 * 
 * @author Jesse Childress
 *
 */
public class ProfilePageFactory extends SW2MenusPageFactory {

	public static String pageURL = BASE_URL + "my-profile";

	/**
	 * Page Constructor - Calls constructor in MenusPageFactor.
	 */
	public ProfilePageFactory() {
		super(pageURL);
		waitForPageToLoad();
	}

	/**
	 * Reads the Role type of the currently logged in user, e.g Admin, Inspector,
	 * SWMP Admin
	 * 
	 * @return String
	 */
	public String readRoleType() {
		AutomationHelper.printMethodName();
		WebElement role = driver.findElement(By.xpath("//i[@class='fa fa-user']//following-sibling::span"));
		return AutomationHelper.getText(role);
	}

	/**
	 * Reads the Email of the currently logged in user.
	 * 
	 * @return String
	 */
	public String readEmailAddress() {
		AutomationHelper.printMethodName();
		WebElement emailAddress = driver.findElement(By.xpath("//i[@class='fa fa-envelope']//following-sibling::span"));
		return AutomationHelper.getText(emailAddress);
	}

}
