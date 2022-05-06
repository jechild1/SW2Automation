package pageFactories;

import static org.testng.Assert.assertTrue;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import utilities.AutomationHelper;

/**
 * Abstract Page factory for Menus that can be found throughout any page in
 * SW2.<br>
 * (1) Finds a reference to objects on the page <br>
 * (2) Defines methods to interact with objects on the page.<br>
 * 
 * Extends SW2Base
 * 
 * @author Jesse Childress
 *
 */
public abstract class SW2MenusPageFactory extends SW2Base {

	public SW2MenusPageFactory(String regexURL) {
		PageFactory.initElements(driver, this);
		new WebDriverWait(driver, Duration.ofSeconds(LONG_TIMEOUT)).until(ExpectedConditions.urlMatches(regexURL));
		assertTrue(this.getCurrentUrl().matches(regexURL), "Validate URL changed to " + regexURL);
	}

	@FindBy(linkText = "Dashboard")
	WebElement dashboardLink;

	/**
	 * Clicks the "Dashboard" link in the left hand navigation menu
	 */
	public void clickDashboard() {
		AutomationHelper.printMethodName();
		dashboardLink.click();
	}

	@FindBy(linkText = "Client")
	WebElement clientLink;

	/**
	 * Clicks the "Client" link in the left hand navigation menu
	 */
	public void clickClient() {
		AutomationHelper.printMethodName();
		clientLink.click();
	}

	@FindBy(linkText = "Project")
	WebElement projectLink;

	/**
	 * Clicks the "Project" link in the left hand navigation menu
	 */
	public void clickProject() {
		AutomationHelper.printMethodName();
		projectLink.click();
	}

	@FindBy(linkText = "Regulations")
	WebElement regulationsLink;

	/**
	 * Clicks the "Regulations" link in the left hand navigation menu
	 */
	public void clickRegulations() {
		AutomationHelper.printMethodName();
		regulationsLink.click();
	}

	@FindBy(linkText = "Legend Item")
	WebElement legendItemLink;

	/**
	 * Clicks the "Legend Item" link in the left hand navigation menu
	 */
	public void clickLegendItem() {
		AutomationHelper.printMethodName();
		legendItemLink.click();
	}

	@FindBy(linkText = "Admin")
	WebElement adminLink;

	/**
	 * Clicks the "Admin" link in the left hand navigation menu
	 */
	public void clickAdmin() {
		AutomationHelper.printMethodName();
		adminLink.click();
	}

	@FindBy(linkText = "Divisions")
	WebElement divisionsLink;

	/**
	 * Clicks the "Divisions" link in the left hand navigation menu
	 */
	public void clickDivisions() {
		AutomationHelper.printMethodName();
		divisionsLink.click();
	}

	@FindBy(linkText = "Inspection Templates")
	WebElement inspectionTemplatesLink;

	/**
	 * Clicks the "Inspection Templates" link in the left hand navigation menu
	 */
	public void clickInspectionTemplates() {
		AutomationHelper.printMethodName();
		inspectionTemplatesLink.click();
		waitForPageToLoad();
	}

	@FindBy(linkText = "User Manuals")
	WebElement userManualsLink;

	/**
	 * Clicks the "User Manuals" link in the left hand navigation menu
	 */
	public void clickUserManuals() {
		AutomationHelper.printMethodName();
		userManualsLink.click();
	}

	/**
	 * Method to select a notification from the top menu.
	 * 
	 * @param notificationType - e.g. Missed Inspection, Inspection Complete, etc.
	 * @param project          - JRC Concert Arena
	 * @param date             - format MM/dd/yyyy (will be in the future)
	 */
	public void selectNotification(String notificationType, String project, String date) {
		AutomationHelper.printMethodName();

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

		// Click on the bell icon to open the popper div.
		// Note: the "SVG" object is difficult to work with. With much research, we may
		// be able to find it a different way.
		List<WebElement> bellIcons = driver
				.findElements(By.xpath("//*[name()='svg' and contains (@data-icon,'bell') ]"));

		// There are two objects that have the same properties. There is no way to
		// differentiate, so we must get the first one in the list.
		WebElement bellIcon = bellIcons.get(1);

		// Wait until the bell icon is loaded before we try to click it.
		wait.until(ExpectedConditions.elementToBeClickable(bellIcon));
		bellIcon.click();

		// Ensure that the popper is visible / clickable
		WebElement popper = driver.findElement(By.id("popper"));
		wait.until(ExpectedConditions.elementToBeClickable(popper));

		// Xpath will be built to find the object
		// We will combine three different Strings to make the xpath. This is only done
		// for readability.
		// xpath1 will find the popper, and the child span that contains the text of the
		// notification type.
		// xpath2 will find the following sibling with the text of the project
		// xpath3 will find the following sibling with the text of the date.

//		String temp = "//div[@id='popper']//li/span[contains(text(),'Inspection Complete')]//following-sibling::div[./descendant::span[text()='JRC Concert Arena'] and span[text()='2021-11-22']]";
		String xpath1 = "//div[@id='popper']//li/span[contains(text(),'" + notificationType + "')]";
		String xpath2 = "//following-sibling::div[./descendant::span[text()='" + project + "']";
		String xpath3 = " and span[text()='" + date + "']]";

		String xpath = xpath1 + xpath2 + xpath3;

		WebElement notification = driver.findElement(By.xpath(xpath));
		notification.click();
	}

	/*
	 * MENUS FOR HEADER
	 */
	/**
	 * Returns the currently selected value of the Division.
	 * 
	 * @return String
	 */
	public String readDivision() {
		AutomationHelper.printMethodName();
		WebElement divisionDiv = driver
				.findElement(By.xpath("//span[@class='flex-row division-selector pointer']//h4"));
		return divisionDiv.getText();
	}

	/**
	 * Method to read the divisions for a given user and return them in a list
	 * object.
	 * 
	 * @return
	 */
	public List<String> readDivisions() {
		AutomationHelper.printMethodName();
		return getDivisionsInDropdown();
	}

	/**
	 * Method to select the division in the Header of every page.
	 * 
	 * @param divisionToSelect
	 */
	public void selectDivision(String divisionToSelect) {
		AutomationHelper.printMethodName();
		WebElement divisionsDiv = driver.findElement(By.xpath("//span[@class = 'flex-row division-selector pointer']"));
		WebElement popperDiv = driver.findElement(By.id("popper"));

		selectDropdownItemFromFlexSelector(divisionsDiv, popperDiv, divisionToSelect);
	}

	/**
	 * Method to click the My Profile link under the signed in user name
	 */
	public void clickMyProfile() {
		AutomationHelper.printMethodName();
		// Click My Profile div
		WebElement myProfileDiv = driver.findElement(By.xpath("//span[@class='flex-row pointer']"));
		myProfileDiv.click();

		// Wait for the popper to appear
		// To wait for element visible
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(NORMAL_TIMEOUT));
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='popper user-profile-menu']")));

		// Find the WebElement with link text of My Profile on the li
		WebElement myProfileLink = driver.findElement(By.xpath("//li[text()='My Profile']"));
		myProfileLink.click();

	}

	/**
	 * Method to click the Log Out link under the signed in user name.
	 */
	public void clickLogOut() {

		AutomationHelper.printMethodName();
		// Click My Profile div
		WebElement myProfileDiv = driver.findElement(By.xpath("//span[@class='flex-row pointer']"));
		myProfileDiv.click();

		// Wait for the popper to appear
		// To wait for element visible
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(NORMAL_TIMEOUT));
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='popper user-profile-menu']")));

		// Find the WebElement with link text of My Profile on the li
		WebElement logOutLink = driver.findElement(By.xpath("//li[text()='Log Out']"));
		logOutLink.click();
	}

	/*
	 * Methods common to all pages
	 */

	/**
	 * Reads the page title in the top navigation section, also called the
	 * "Breadcrumb"
	 */
	public String readPageTitleFromBreadcrumb() {
		AutomationHelper.printMethodName();

		List<WebElement> breadCrumbWebElements = driver
				.findElements(By.xpath("//nav[@class='inline-nav breadcrumbs']/a"));

		if (breadCrumbWebElements.size() == 0) {
			throw new ElementNotVisibleException("Cannot find a breadcrumb on this page.");
		}

		String breadCrumbText = "";

		for (WebElement currentCrumb : breadCrumbWebElements) {
			breadCrumbText += currentCrumb.getText() + " ";
		}

		return breadCrumbText.trim();
	}
}
