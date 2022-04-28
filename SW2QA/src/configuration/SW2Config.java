package configuration;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import utilities.AutomationHelper;

/**
 * @author Jesse Childress
 *
 */
public abstract class SW2Config extends TestConfig {

	// Development
	protected static final String BASE_URL = "https://dev.sw2.net/";
	protected static final String USERS_WORKSHEET = "AutomationUsers";
	protected static final String DIVISION = "SWQA Alpha";
	
	//Silverleaf
//	protected static final String BASE_URL = "https://silverleafswppp.sw2.net/";
//	protected static final String USERS_WORKSHEET = "Silver Leaf";
//	protected static final String DIVISION = "Northern Utah - Commercial";
	
	// Staging
//	protected static final String BASE_URL = "https://stage.sw2.net/";
//	protected static final String USERS_WORKSHEET = "AutomationUsers";
//	protected static final String DIVISION = "SWQA Alpha";

	// Production
//	protected static final String BASE_URL = "https://app.sw2.net/";
//	protected static final String USERS_WORKSHEET = "ProductionUsers";
//	protected static final String DIVISION = "Demo";

	public SW2Config() {
		super(BASE_URL);
	}

	/*
	 * PAGINATION CODE
	 */

	// TODO - Check for pagination on this given page. If it's not there, throw a
	// meaningful error
	// TODO - Create a method to click first page, previousPage, Page number x, next
	// page, last page

	public void paginationClickFirstPage() {
		AutomationHelper.printMethodName();
		paginationPresentValidator();

	}

	/**
	 * Method that clicks the > "Go to next page" link when pagination exists. This
	 * method will handle checking for the presence of pagination, and will also
	 * throw appropriate exceptions when at the end of lists.
	 */
	public void paginationClickGoToNextPage() {
		AutomationHelper.printMethodName();

		// Print current active page
		String xpathForCurrentPage = "//ul[@class='pagination']/li[@class='pagination-item active']/a";
		String currentPageForPagination = driver.findElement(By.xpath(xpathForCurrentPage)).getText().trim();
		Reporter.log("Pagination Active Page: " + currentPageForPagination, true);
		Reporter.log("", true);

		// Ensure that pagination is present
		paginationPresentValidator();

		// String to hold the Xpath
		String xpath = "//ul[@class='pagination']/li/a[@aria-label = 'Go to next page']//parent::li";

		// Create a List of WebElements, which shoudl ONLY return one, if it's present.
		List<WebElement> element = driver.findElements(By.xpath(xpath));

		// First, ensure that pagination exist for the button.
		if (element.size() == 0) {
			throw new ElementNotVisibleException("Pagination doesn't exist on this page.");
		}

		else if (element.size() == 1) {
			WebElement goToNextPageButton = element.get(0);

			// If the button is disabled, throw an exception and say so.
			if (goToNextPageButton.getAttribute("class").contains("disabled")) {
				throw new ElementNotInteractableException(
						"Either there are no pages to scroll to, or you are at the end of the pagination and cannot click the 'Go to nextpage >' button");
			}
			// If we find an element and it's not disabled, we can finally click it.
			else {
				goToNextPageButton.click();
			}
		}
	}

	/**
	 * Method to see if the go to next page is disabled.
	 * 
	 * @return boolean - true if go to next page ">" is enabled; false if it is not.
	 */
	public boolean paginationIsGoToNextPageDisabled() {

		AutomationHelper.printMethodName();
		// First, see if pagination is present
		paginationPresentValidator();

		boolean paginationDisabled = false;

		// Get a reference for the ">" button on the page
		String xpathForGoToNextPage = "//ul[@class='pagination']/li/a[@aria-label = 'Go to next page']//parent::li";
		WebElement goToNextPageButton = driver.findElement(By.xpath(xpathForGoToNextPage));

		if (goToNextPageButton.getAttribute("class").contains("disabled")) {
			paginationDisabled = true;
		}
		return paginationDisabled;

////		AutomationHelper.printMethodName();
//		// First, see if pagination is present
//		paginationPresentValidator();
//
//		// Default pagination variable set to false
//		boolean paginationDisabled = false;
//
//		// String to hold the Xpath
//		String xpathForGoToNextPage = "//ul[@class='pagination']/li/a[@aria-label = 'Go to next page']//parent::li";
//		String xpathForNumber1Button = "//ul[@class='pagination']/li/a[@aria-label = 'Go to page number 1']//parent::li";
//
//		// Create a reference to the button (it's parent either says "pagination-item"
//		// or "pagination-item disabled"
//		WebElement goToNextPageButton = driver.findElement(By.xpath(xpathForGoToNextPage));
//		List<WebElement> goToPageNumber1Button = driver.findElements(By.xpath(xpathForNumber1Button));
//
//		boolean goToPageNumber1ButtonPresent = AutomationHelper.isWebElementOnPage(goToPageNumber1Button);
//
//		// See if the class contains disabled and see that if it is disabled, that
//		// pagination exists with
//		if (goToNextPageButton.getAttribute("class").contains("disabled") && !goToPageNumber1ButtonPresent) {
//			paginationDisabled = true;
//		}
//
//		return paginationDisabled;

	}

	/**
	 * Method that clicks the > "Go to previous page" link when pagination exists.
	 * This method will handle checking for the presence of pagination, and will
	 * also throw appropriate exceptions when at the end of lists.
	 */
	public void paginationClickGoToPreviousPage() {
		AutomationHelper.printMethodName();

		// Print current active page
		String xpathForCurrentPage = "//ul[@class='pagination']/li[@class='pagination-item active']/a";
		String currentPageForPagination = driver.findElement(By.xpath(xpathForCurrentPage)).getText().trim();
		Reporter.log("Pagination Active Page: " + currentPageForPagination, true);
		Reporter.log("", true);

		// Ensure that pagination is present
		paginationPresentValidator();

		// String to hold the Xpath
		String xpath = "//ul[@class='pagination']/li/a[@aria-label = 'Go to previous page']//parent::li";

		// Create a List of WebElements, which shou7ld ONLY return one, if it's present.
		List<WebElement> element = driver.findElements(By.xpath(xpath));

		// First, ensure that pagination exist for the button.
		if (element.size() == 0) {
			throw new ElementNotVisibleException("Pagination doesn't exist on this page.");
		}

		else if (element.size() == 1) {
			WebElement goToPreviousPageButton = element.get(0);

			// If the button is disabled, throw an exception and say so.
			if (goToPreviousPageButton.getAttribute("class").contains("disabled")) {
				throw new ElementNotInteractableException(
						"Either there are no pages to scroll to, or you are at the end of the pagination and cannot click the 'Go to previous <' button");
			}
			// If we find an element and it's not disabled, we can finally click it.
			else {
				goToPreviousPageButton.click();
			}
		}
	}

	/**
	 * This method will use pagination to navigate to the passed in page number.
	 * 
	 * @param pageNumber
	 */
	public void paginationGoToPage(int pageNumber) {
		AutomationHelper.printMethodName();

		// First, ensure that pagination is present on the page.
		paginationPresentValidator();
		// Only 5 page numbers are visible at a time, provided that there are multiple
		// pages. Sometimes there is only one. We must guarantee that we start on
		// page 1 to ensure that the pages are visible

		// Find a list of only the ACTIVE list items in the pagination ul. This ensures
		// our List only has elements that it can work with.
		List<WebElement> paginationItems = getActivePaginationItems();

		// Boolean to determine if we found the page. Default is false;
		boolean pageFound = false;

		/*
		 * FIRST ATTEMPT - Trying to find the item on the page without clicking
		 * "Go to first page".
		 */

		// Cycle through each of the items in the WebElement list.
		for (WebElement currentObject : paginationItems) {
			if (currentObject.getText().trim().equals(String.valueOf(pageNumber))) {
				pageFound = true;
				currentObject.click();
				break;
			}
		}

		/*
		 * SECOND ATTEMPT - If the page still isn't found, lets attempt to start from
		 * the beginning from the "Go to first page"
		 */
		if (!pageFound) {

			// Obtain a reference to the << button. We want to ensure that we start from the
			// beginning.
			WebElement goToFirstPageButton = driver
					.findElement(By.xpath("//ul[@class='pagination']/li/a[@aria-label='Go to first page']"));

			// Click the << button
			goToFirstPageButton.click();

			// Obtain a new reference to the active pagination items.
			paginationItems = getActivePaginationItems();

			// Cycle through each of the items in the WebElement list.
			for (WebElement currentObject : paginationItems) {
				if (currentObject.getText().trim().equals(String.valueOf(pageNumber))) {
					pageFound = true;
					currentObject.click();
					break;
				}
			}
		}

		/*
		 * THIRD ATTEMPT - While the page is still not found, and the "Go to next page"
		 * button is active, click it and perform the search all over again.
		 */

		if (!pageFound) {

			// Find a reference to the Go To Next Page button
			// Finds the a object, then gets the parent LI which has a tag that says either
			// "pagination-item" or "pagination-item disabled"
			WebElement goToNextPageButton = driver
					.findElement(By.xpath("//ul[@class='pagination']/li/a[@aria-label='Go to next page']//parent::li"));

			String goToNextPageClassAttribute = goToNextPageButton.getAttribute("class");

			// While the pagination item is enabled, we need to keep cycling through. Else,
			// it has a property of pagination-item disabled
			while (goToNextPageClassAttribute.equals("pagination-item") && !pageFound) {

				// Click the page to advance by one page
				goToNextPageButton.click();

				// Obtain a new reference to the active pagination items.
				paginationItems = getActivePaginationItems();

				// Cycle through each of the items in the WebElement list.
				for (WebElement currentObject : paginationItems) {
					if (currentObject.getText().trim().equals(String.valueOf(pageNumber))) {
						pageFound = true;
						currentObject.click();
						break;
					}
				}

				// Get a new reference to goToNextPage
				goToNextPageButton = driver.findElement(
						By.xpath("//ul[@class='pagination']/li/a[@aria-label='Go to next page']//parent::li"));
				goToNextPageClassAttribute = goToNextPageButton.getAttribute("class");

			}

		}

		if (!pageFound) {
			throw new ElementNotVisibleException(
					"You are searching for page " + pageNumber + ", however, this page is not included in pagination.");
		}

	}

	/**
	 * Returns a List of WebElements where the Pagination exists and is active
	 * 
	 * @return List<WebElement>
	 */
	private List<WebElement> getActivePaginationItems() {
		return driver.findElements(By.xpath(
				"//ul[@class='pagination']//li[@class='pagination-item active'] | //ul[@class='pagination']/li[@class='pagination-item']"));

	}

	// TODO - Overload this method so that we DO NOT throw an exception for certain
	// cases.
	/**
	 * Private method to see if pagination is found on the page. If it is NOT found,
	 * the the exception is handled here.
	 * 
	 */
	private void paginationPresentValidator() {

		// This method will throw an exception by default if we not find the ul
		// containing the pagination within the SHORT_TIMEOUT
		// Error Example:
		// org.openqa.selenium.TimeoutException: Expected condition failed: waiting for
		// presence of element located by: By.xpath: //ul[@class='pagination'] (tried
		// for 1 second(s) with 500 milliseconds interval)
		WebDriverWait waiter = new WebDriverWait(driver, Duration.ofSeconds(SHORT_TIMEOUT));
		waiter.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//ul[@class='pagination']")));

	}

	/**
	 * Takes you to the Home / Dashboard page in SW2 no matter where you call the
	 * method from.
	 */
	public void clickHome() {
		AutomationHelper.printMethodName();
		WebElement homeLogo = driver.findElement(By
				.xpath("//section[@class = 'sidebar pure-u']//img[@src = '/static/media/SW-Logo-White.b9dd2c1d.svg']"));
		homeLogo.click();

	}

	/**
	 * Method to check to see if a page is present in the navigation links. E.g.,
	 * Admin or Legend Item. <br>
	 * Note: You muss pass in the exact case and text. All parameters that have
	 * incorrect case or misspellings will not match.
	 * 
	 * @param pageTitle
	 * @return boolean
	 */
	public boolean isPageLinkPresent(String pageTitle) {

		boolean linkFound = false;

		List<WebElement> link = driver.findElements(By.linkText(pageTitle));

		if (link.size() > 0) {
			linkFound = true;
		}
		return linkFound;
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

}
