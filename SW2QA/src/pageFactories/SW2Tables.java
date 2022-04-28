package pageFactories;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import utilities.AutomationHelper;
import utilities.Tables;

/**
 * Table class for SW2. Most methods in this class utilize the methods in the
 * core table class of selenium-core, but include pagination checks.
 * 
 * @author jesse.childress
 *
 */
public class SW2Tables extends Tables {

	// This is required in order to give the table class access to the base, which
	// is needed in order to call waitForPageToLoad() between page refreshes (the
	// spinner)
	SW2Base base = new SW2Base() {
	};

	// Constructor, calling super.
	public SW2Tables(WebElement tableReference) {
		super(tableReference);
		// Until we initialize the elements, we can't use them. Without this, the code
		// appears to execute, but does not actually.
		base.initializeElements();
	}


	/**
	 * Method to click a cell with the passed in text. This method will throw
	 * exceptions if there is more than one cell with the passed in value, or if
	 * there are no cells with that value found.
	 * 
	 * @param cellText
	 */
	public void clickCell(String cellText) {

		Reporter.log("Beginning cell click for row cell with text '" + cellText + "'.", true);

		WebElement myCell = null;

		// Create a list of table cells
		List<WebElement> allCellsList = table.findElements(By.tagName("td"));

		// Track the number of cells found that match, so that we can throw an exception
		// if needed.
		int noOfCellsFound = 0;

		// Loop through all of the cells in the table
		for (WebElement currentCell : allCellsList) {
			if (currentCell.getText().trim().equalsIgnoreCase(cellText)) {
				myCell = currentCell;
				noOfCellsFound++;
			}
		}

		// If we did not find a row, we should throw an exception and not
		// process further.
		if (myCell == null) {
			throw new ElementNotInteractableException(
					"Cannot click cell. There are no cells found in the table for " + cellText);
		}

		// If we found more than one cell, we should throw an exception as we are not
		// sure which cell to click since there are multiples.
		if (noOfCellsFound > 1) {
			throw new ElementNotInteractableException("There are multiple cells with the value of " + cellText);
		}

		myCell.click();
	}

	/**
	 * Method to click a row that matches the passed in primary column name and
	 * primary column value.
	 * 
	 * @param primaryColumnName
	 * @param primaryColumnValue
	 */
	public void clickRow(String primaryColumnName, String primaryColumnValue) {

		boolean rowFound = false;

		// First, try to find the row on the initial page without considering
		// pagination. If we find it, good.
		WebElement row = getRow(primaryColumnName, primaryColumnValue, true);

		if (row != null) {
			rowFound = true;
			row.click();
		}

		else {

			// Check to see if pagination is present. If pagination is present, click
			// GoToNextPage, then try again. Do this UNTIL row is not null and were not at
			// the end of the pagination.

			if (isPaginationPresent()) {

				// Ensure we start on the first page before we page through
				paginationClickFirstPage();

				do {

					// Click the next page > button in pagination
					paginationClickGoToNextPage();

					// Get a reference to the table again.
					table = getSW2Table();

					// Attempt to find the row again
					row = getRow(primaryColumnName, primaryColumnValue, true);

					// If we found the row on this page, click it
					if (row != null) {
						rowFound = true;
						row.click();
						break;
					}
				}

				while (!paginationIsGoToNextPageDisabled());

			}

		}

		// Finally, if the row was never found, throw an exception
		if (!rowFound) {
			throw new ElementNotVisibleException("There is not a row with primary column name of '" + primaryColumnName
					+ "' and primary column value of '" + primaryColumnValue + "'.");
		}

	}

	/**
	 * Method to look for a value in a row that matches the passed in primary column
	 * name and primary column value.
	 * 
	 * @param primaryColumnName
	 * @param primaryColumnValue
	 */
	public boolean isRowInTable(String primaryColumnName, String primaryColumnValue) {
		
		table = null;
		table = getSW2Table();

		boolean rowFound = false;

		// First, try to find the row on the initial page without considering
		// pagination. If we find it, good.
		WebElement row = getRow(primaryColumnName, primaryColumnValue, true);
		
		if (row != null) {
			rowFound = true;
		}

		else {

			// Check to see if pagination is present. If pagination is present, click
			// GoToNextPage, then try again. Do this UNTIL row is not null and were not at
			// the end of the pagination.

			if (isPaginationPresent()) {

				// Ensure we start on the first page before we page through
				paginationClickFirstPage();

				do {

					// Click the next page > button in pagination
					paginationClickGoToNextPage();

//					// Get a reference to the table again.
					table = null;
					table = getSW2Table();

					// Attempt to find the row again
					row = getRow(primaryColumnName, primaryColumnValue, true);

					// If we found the row on this page, click it
					if (row != null) {
						rowFound = true;
						break;
					}
				}

				while (!paginationIsGoToNextPageDisabled());

			}

		}

//		// Finally, if the row was never found, throw an exception
//		if (!rowFound) {
//			throw new ElementNotVisibleException("There is not a row with primary column name of '" + primaryColumnName
//					+ "' and primary column value of '" + primaryColumnValue + "'.");
//		}

		return rowFound;

	}

	/**
	 * Method to return all of the td vlues in a list that are beneath the passed in
	 * THEAD column.
	 * 
	 * @param tHeadColumnName
	 * @return List of Cell Values
	 */
	public List<String> getCellValues(String tHeadColumnName) {

		// Find the thead object
		int primaryColIndex = getColumnIndex(tHeadColumnName, true);

		List<String> cellValues = new ArrayList<String>();

		// Get a list of all all the TD's in the table
		List<WebElement> tds = table.findElements(By.xpath("//td"));

		for (WebElement x : tds) {

			if (Integer.valueOf(x.getAttribute("cellIndex")) == primaryColIndex) {
				cellValues.add(x.getText());
			}

		}

		return cellValues;

	}

	/**
	 * Method to select a menu item from a drop down within a cell. This method will
	 * require that you pass in the Primary Column Name (The THEAD), and a unique
	 * row value beneath that column. This will identify a unique row. Lastly, we
	 * will get the last cell in the row, which contains the drop down element.
	 * 
	 * @param primaryColHeaderValue - The column THEAD in which the unique row is to
	 *                              be found under
	 * @param rowUniqueValue        - The value in the row under the primary column
	 *                              that is unique
	 * @param menuItemText          - The text of the menu item, e.g. "Delete" or
	 *                              "Print"
	 */
	public void selectMenuInCell(String primaryColHeaderValue, String rowUniqueValue, String menuItemText) {

		Reporter.log("Beginning Menu Select for " + menuItemText + " with primary column " + primaryColHeaderValue
				+ " and row value " + rowUniqueValue + ".", true);

		// Get CellIndex for Primary column Header
		int primaryColIndex = getColumnIndex(primaryColHeaderValue, true);

		// Get CellIndex for Column to Read
		int colToReadCellIndex = getLastTHeadInRow(true);

		// Get the rowIndex for the row in the primary column.
		int rowIndex = getRowIndex(primaryColIndex, rowUniqueValue, true);

		// Get the cell that the menu will live in
		WebElement cell = getCell(rowIndex, colToReadCellIndex, true);

		cell.click();

		// Wait for the menu to be active
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='sw-dropdown__menu--active']")));

		// Once the menu is open, get a list of items inside it
		List<WebElement> menuItems = driver.findElements(
				By.xpath("//div[@class='sw-dropdown__menu--active']//div[@class='sw-dropdown__menu-item']"));

		// If we do not find any menu items, throw an exception.
		if (menuItems.size() == 0) {
			throw new ElementNotVisibleException("There are no menu items that are visible on the page");
		} else {

			// Flag to monitor when we find the item.
			boolean menuItemFound = false;

			// Cycle through the menu items until we find the one that matches the passed in
			// menuItemText.
			for (WebElement currentMenuItem : menuItems) {
				if (currentMenuItem.getText().equalsIgnoreCase(menuItemText)) {
					menuItemFound = true;
					currentMenuItem.click();
					break;
				}
			}

			// If we didn't find the item in the menu, let the user know. This can be a
			// problem when users don't have the correct permissions.
			if (!menuItemFound) {
				throw new ElementNotVisibleException(
						"There is no menu option with text '" + menuItemText + "' found in the menu.");
			}

		}
	}

	/**
	 * This method the last column index number of a row.
	 * 
	 * @return int - the last column index for a given table.
	 */
	protected int getLastTHeadInRow(boolean throwEx) {
		int columnIndex = -1;
		// Get all the THEADS in a given table
		List<WebElement> tHeads = table.findElements(By.tagName("th"));

		if (tHeads.size() > 0) {

			// Have to subtract one here because it's zero based. Although there can be, for
			// example, 6 columns, the index of the 6th will be 5.
			columnIndex = tHeads.size() - 1;

		} else {
			if (throwEx) {
				throw new RuntimeException("The table does not have any THEAD objects");
			}
		}
		return columnIndex;
	}

	// TODO - Why is this not in the table class?
	/**
	 * Method to click the File Upload button beside a given table. <br>
	 * Note: You must pass in the table name exactly, case and all.
	 * 
	 * @param tableName, e.g. Regulations, Site Map. Must match exactly
	 */
	public void clickFileUploadButton(String tableName) {
		AutomationHelper.printMethodName();

		// Xpath finds the table based on the table name passed in, e.g. Regulations,
		// Site Map. Then finds the parent div. The button beneath is an "i" object.
		String xpath = "//h3[text()='" + tableName + "']/parent::div//i";
		WebElement fileUploadButton = driver.findElement(By.xpath(xpath));
		fileUploadButton.click();
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
		base.waitForPageToLoad();

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
		Reporter.log("Pagination active page before click: " + currentPageForPagination, true);
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
		base.waitForPageToLoad();

	}

	/**
	 * Method to see if the go to next page is disabled.
	 * 
	 * @return boolean - true if go to next page ">" is enabled; false if it is not.
	 */
	public boolean paginationIsGoToNextPageDisabled() {
//		AutomationHelper.printMethodName();
		// First, see if pagination is present
		paginationPresentValidator();

		// Default pagination variable set to false
		boolean paginationDisabled = false;

		// String to hold the Xpath
		String xpathForGoToNextPage = "//ul[@class='pagination']/li/a[@aria-label = 'Go to next page']//parent::li";
//		String xpathForNumber1Button = "//ul[@class='pagination']/li/a[@aria-label = 'Go to page number 1']//parent::li";

		// Create a reference to the button (it's parent either says "pagination-item"
		// or "pagination-item disabled"
		WebElement goToNextPageButton = driver.findElement(By.xpath(xpathForGoToNextPage));
//		List<WebElement> goToPageNumber1Button = driver.findElements(By.xpath(xpathForNumber1Button));

//		boolean goToPageNumber1ButtonPresent = AutomationHelper.isWebElementOnPage(goToPageNumber1Button);
//
//		// See if the class contains disabled and see that if it is disabled, that
//		// pagination exists with
//		if (goToNextPageButton.getAttribute("class").contains("disabled") && !goToPageNumber1ButtonPresent) {
//			paginationDisabled = true;
//		}

		if (goToNextPageButton.getAttribute("class").contains("disabled")) {
			paginationDisabled = true;
		}

		return paginationDisabled;

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
		base.waitForPageToLoad();

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
		
		base.waitForPageToLoad();


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

	protected boolean isPaginationPresent() {

		boolean isPresent = false;
		
		AutomationHelper.adjustWaitTimeToShort();

		if (driver.findElements(By.xpath("//ul[@class='pagination']")).size() > 0) {
			isPresent = true;
		}
		AutomationHelper.adjustWaitTimeToNormal();
		return isPresent;
	}

	/**
	 * This method will work to get a reference to the most common table in SW2. It
	 * is needed here due to Stale Reference Errors. We have to get a new reference
	 * to the table each time we use pagination because the DOM changes.
	 * 
	 * @return WebElement
	 */
	private WebElement getSW2Table() {
		//By using contains, we can account for the word "empty" on the end of the table where no results are present.

		return driver.findElement(By.xpath("//table[contains(@class , 'list-table pure-table')]"));
	}

}

//	protected void getRow(String primaryColumnHeader, String rowValue) {

// Search a list of rows in the table

//	/**
//	 * Clicks a link in table row with that matches a single passed in value, and
//	 * contains the passed in Link Text.
//	 * 
//	 * @param rowValueOne
//	 * @param linkText
//	 */
//	public void clickLinkInRow(String rowValueOne, String linkText) {
//		Reporter.log("Beginning link click for row " + rowValueOne + " and link title " + linkText + ".", true);
//
//		WebElement myRow = null;
//		// First, try to find the table row / link on the current page before we do
//		// paging.
//		System.out.println("Searching for value '" + rowValueOne + "' on the initial page " + readTablePageNumber() + " for link text '" + linkText + "'.");
//
//		// Create a list of table rows
//		List<WebElement> tableRows = table.findElements(By.tagName("tr"));
//
//		// Loop through all the table rows to find the row containing rowValueOne
//		for (WebElement currentRow : tableRows) {
//			String currentRowText = currentRow.getText().trim().replace("\n", "");
//			//TODO - ADDED
//			AutomationHelper.escapeStringForRegEx(currentRowText);
//			if (currentRowText.matches(rowValueOne)) {
//				myRow = currentRow;
//				break;
//			}
//		}
//
//		// If we did not find the row on the first pass, we must work with pagination.
//		if (myRow == null) {
//
//			// Make sure we go to the first page of the table.
//			while (isSkipToPreviousNavigationPresent()) {
//				clickSkipToPreviousNavigationLink();
//				base.waitForPageToLoad();
//			}
//
//			//Keeps track of which table page were on.
//			int tableCounter = 1;
//			//Keeps track of the final table, when reached.
//			boolean finalTablePage = false;
//
//			//Keep performing this loop until we reach the end of the table and haven't found a row.
//			while (myRow == null && !finalTablePage) {
//
//				System.out.println("Searching for value '" + rowValueOne + "' on table page " + tableCounter + " for link text '" + linkText + "'.");
//
//				if (!isSkipToNextNavigationPresent()) {
//					finalTablePage = true;
//					
//				}
//				
//				// Create a list of table rows
//				tableRows = table.findElements(By.tagName("tr"));
//
//				// Loop through all the table rows to find the row containing rowValueOne
//				for (WebElement currentRow : tableRows) {
//					String currentRowText = currentRow.getText().trim();
//					if (currentRowText.contains(rowValueOne)) {
//						myRow = currentRow;
//						break;
//					}
//				}
//
//				if (myRow == null) {
//					if (isSkipToNextNavigationPresent()) {
//						clickSkipToNextNavigationLink();
//						base.waitForPageToLoad();
//						tableCounter++;
//					}
//				} else {
//					System.out.println("Found row text '" + rowValueOne + "' on table page " + tableCounter + " with link text of '" + linkText + "'.");
//					break;
//				}
//
//			}
//
//			// If we did not find a row, we should throw an exception and not
//			// process further.
//			if (myRow == null) {
//				throw new RuntimeException("Cannot click link. There are no rows found in the table for " + rowValueOne);
//			}
//		} // end If Row Wasnt found initially
//
//		WebElement link = myRow.findElement(By.linkText(linkText));
//		link.click();
//
//		Reporter.log("Link " + linkText + " clicked for row " + rowValueOne + ".", true);
//		Reporter.log("", true);
//	}

//	/**
//	 * Clicks a link in table row with that matches a single passed in value, and
//	 * contains the passed in Link Text.
//	 * 
//	 * @param rowValueOne
//	 * @param rowValueTwo
//	 * @param linkText
//	 */
//	public void clickLinkInRow(String rowValueOne, String rowValueTwo, String linkText) {
//		Reporter.log(
//				"Beginning link click for row " + rowValueOne + " "	+ rowValueTwo + " and link text " + linkText + ".", true);
//
//		// Take the two row values and concatenate them.
//		String myConcatenatedString = rowValueOne.trim() + " "	+ rowValueTwo.trim();
//		
//		WebElement myRow = null;
//		
//		// First, try to find the table row / link on the current page before we do
//		// paging.
//		System.out.println("Searching for value '" + rowValueOne + "' and '" + rowValueTwo + "'" + "' on the initial page " + readTablePageNumber() + " for link text '" + linkText + "'.");
//
//		// Create a list of table rows
//		List<WebElement> tableRows = table.findElements(By.tagName("tr"));
//
//		// Loop through all the table rows to find the row containing the value
//		// 1 and value 2 combination.
//		for (WebElement currentRow : tableRows) {
//			if (currentRow.getText().trim().contains(myConcatenatedString)) {
//				myRow = currentRow;
//				break;
//			}
//		}
//
//		// If we did not find the row on the first pass, we must work with pagination.
//		if (myRow == null) {
//
//			// Make sure we go to the first page of the table.
//			while (isSkipToPreviousNavigationPresent()) {
//				clickSkipToPreviousNavigationLink();
//				base.waitForPageToLoad();
//			}
//
//			//Keeps track of which table page were on.
//			int tableCounter = 1;
//			//Keeps track of the final table, when reached.
//			boolean finalTablePage = false;
//
//			//Keep performing this loop until we reach the end of the table and haven't found a row.
//			while (myRow == null && !finalTablePage) {
//
//				System.out.println("Searching for value '" + rowValueOne + "' and '" + rowValueTwo + "' on table page " + tableCounter + " for link text '" + linkText + "'.");
//
//				if (!isSkipToNextNavigationPresent()) {
//					finalTablePage = true;
//					
//				}
//				
//				// Create a list of table rows
//				tableRows = table.findElements(By.tagName("tr"));
//
//				// Loop through all the table rows to find the row containing the value
//				// 1 and value 2 combination.
//				for (WebElement currentRow : tableRows) {
//					if (currentRow.getText().trim().contains(myConcatenatedString)) {
//						myRow = currentRow;
//						break;
//					}
//				}
//
//				if (myRow == null) {
//					if (isSkipToNextNavigationPresent()) {
//						clickSkipToNextNavigationLink();
//						base.waitForPageToLoad();
//						tableCounter++;
//					}
//				} else {
//					System.out.println("Found row text '" + rowValueOne + "' and '" + rowValueTwo + "' on table page " + tableCounter + " with link text of '" + linkText + "'.");
//					break;
//				}
//
//			}
//
//			// If we did not find a row, we should throw an exception and not
//			// process further.
//			if (myRow == null) {
//				throw new RuntimeException("Cannot click link. There are no rows found in the table for " + rowValueOne  + "' and '" + rowValueTwo );
//			}
//		} // end If Row Was'nt found initially
//
//		WebElement link = myRow.findElement(By.linkText(linkText));
//		link.click();
//
//		Reporter.log("Link " + linkText + " clicked for row " + rowValueOne + "' and '" + rowValueTwo , true);
//		Reporter.log("", true);
//	}

//}
