package pageFactories;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import utilities.AutomationHelper;

/**
 * Page factory for the Dashboards page for SW2.<br>
 * (1) Finds a reference to objects on the page <br>
 * (2) Defines methods to interact with objects on the page.<br>
 * 
 * @author Jesse Childress
 *
 */
public class DashboardPageFactory extends SW2MenusPageFactory {

	public static String pageURL = BASE_URL + ".*" + "dashboard";

	/**
	 * Page Constructor - Calls constructor in MenusPageFactor.
	 */
	public DashboardPageFactory() {
		super(pageURL);
		waitForPageToLoad();
	}

	@FindBy(xpath = "//div[@class='inline-nav user-details flex-row']/span[@class='flex-row pointer']/h4")
	WebElement userName;

	/**
	 * Returns the user name text in the navigation heading as one String. E.g.
	 * "Jesse Regional-Manager"
	 * 
	 * @return String
	 */
	public String readUserNameInNavigationHeading() {
		AutomationHelper.printMethodName();
		return userName.getText();
	}

}
