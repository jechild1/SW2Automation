package pageFactories.Project.Inspections;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import pageFactories.SW2Modals;
import pageFactories.SW2Tables;
import pageFactories.Project.SW2ProjectPageFactory;
import utilities.AutomationHelper;

/**
 * This is an abstract class that will provide access to common methods for the
 * Project Inspections pages.
 * 
 * @author Jesse Childress
 *
 */
public abstract class ProjectInspectionsMainPageFactory extends SW2ProjectPageFactory {

	/**
	 * Page Constructor - Calls constructor in MenusPageFactory.
	 */
	public ProjectInspectionsMainPageFactory(String regexURL) {
		super(regexURL);
	}

	/**
	 * Method to return the Project Name on the Project > Inspections page.
	 * 
	 * @return Project Name
	 */
	public String readProjectName() {
		AutomationHelper.printMethodName();
		String xpath = "//div[starts-with(@class, 'project-info pure')]//h2";
		WebElement projectName = driver.findElement(By.xpath(xpath));
		return projectName.getText();
	}

	/**
	 * Method to return the Client Name on the Project > Inspections page.
	 * 
	 * @return Client Name
	 */
	public String readClientName() {
		AutomationHelper.printMethodName();
		String xpath = "//div[starts-with(@class, 'project-info pure')]/div/div/h5";
		WebElement clientName = driver.findElement(By.xpath(xpath));

		// The Client was sometimes slow to load. Put this expected condition (with NOT
		// to invert it), waits for 10 seconds until the text attribute does NOT contain
		// an empty string.
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.not(ExpectedConditions.attributeContains(clientName, "text", "")));

		return clientName.getText();
	}

	/**
	 * Method to return the Address on the Project > Inspections page.
	 * 
	 * @return Address
	 */
	public String readAddress() {
		AutomationHelper.printMethodName();
		String xpath = "//div[starts-with(@class, 'project-info pure')]/div/following-sibling::h5";
		WebElement address = driver.findElement(By.xpath(xpath));
		return address.getText();
	}

	/**
	 * Clicks the "Edit" button on the Project Inspections main page (header area).
	 * <br>
	 * Note: This button is behind the three dot ellipsis button.
	 */
	public void clickEditMenu() {
		AutomationHelper.printMethodName();
		clickButtonBehindThreeDotEllipsis("Edit");
	}

	/**
	 * Clicks the "Print SWMP" button on the Project Inspections main page (header
	 * area). <br>
	 * Note: This button is behind the three dot ellipsis button.
	 */
	public void clickPrintSWMPMenu() {
		AutomationHelper.printMethodName();
		clickButtonBehindThreeDotEllipsis("Print SWMP");
		waitForPageToLoad();
	}

	/**
	 * Clicks the "Print Inspections" button on the Project Inspections main page
	 * (header area). <br>
	 * Note: This button is behind the three dot ellipsis button.
	 */
	public void clickPrintInspectionsMenu() {
		AutomationHelper.printMethodName();
		clickButtonBehindThreeDotEllipsis("Print Inspections");
		waitForPageToLoad();
	}

	/*
	 * NOTE: Commenting this out until we are sure we need it, as to not delete
	 * projects accidently.
	 */

	/**
	 * Clicks the "Delete" button on the Project Inspections main page (header
	 * area). <br>
	 * Note: This button is behind the three dot ellipsis button.
	 */
	public void clickDeleteMenu() {
		AutomationHelper.printMethodName();
		clickButtonBehindThreeDotEllipsis("Delete");
	}

	/**
	 * Clicks the "Archive" button on the Project Inspections main page (header
	 * area). <br>
	 * Note: This button is behind the three dot ellipsis button.
	 */
	public void clickArchiveMenu() {
		AutomationHelper.printMethodName();
		clickButtonBehindThreeDotEllipsis("Archive");
	}

	/**
	 * Clicks the All Inspections tab on the Project > Inspections page.
	 */
	public void clickAllInspectionsTab() {
		AutomationHelper.printMethodName();
		WebElement link = driver.findElement(By.linkText("All Inspections"));
		link.click();
	}

	/**
	 * Clicks the Project Documents tab on the Project > Inspections page.
	 */
	public void clickProjectDocumentsTab() {
		AutomationHelper.printMethodName();
		WebElement link = driver.findElement(By.linkText("Project Documents"));
		link.click();
	}

	/**
	 * Clicks the BMP Details tab on the Project > Inspections page.
	 */
	public void clickBMPDetailsTab() {
		AutomationHelper.printMethodName();
		WebElement link = driver.findElement(By.linkText("BMP Details"));
		link.click();
	}

	/**
	 * Clicks the Active Site Map tab on the Project > Inspections page.
	 */
	public void clickActiveSiteMapTab() {
		AutomationHelper.printMethodName();
		WebElement link = driver.findElement(By.linkText("Active Site Map"));
		link.click();
	}

	/**
	 * Clicks the Contacts tab on the Project > Inspections page.
	 */
	public void clickContactsTab() {
		AutomationHelper.printMethodName();
		WebElement link = driver.findElement(By.linkText("Contacts"));
		link.click();
	}

	/**
	 * Clicks the Corrective Actions tab on the Project > Inspections page.
	 */
	public void clickCorrectiveActionsTab() {
		AutomationHelper.printMethodName();
		WebElement link = driver.findElement(By.linkText("Corrective Actions"));
		link.click();
	}

	/**
	 * Clicks the Log tab on the Project > Inspections page.
	 */
	public void clickLogTab() {
		AutomationHelper.printMethodName();
		WebElement link = driver.findElement(By.linkText("Log"));
		link.click();
	}

	/**
	 * Protected method to click a link behind the three dot ellipsis on the
	 * Projects landing page.
	 */
	@Override
	protected void clickButtonBehindThreeDotEllipsis(String linkText) {

		// Containing Div (three dot ellipsis button)
		WebElement threeDotEllipsis = driver.findElement(By.xpath(
				"//button[@class = 'outline overflow'] | //button[@class = 'dropdown-menu-toggle action-buttons-secondary-toggle outline']"));

		WebElement ul = driver.findElement(By.xpath("//ul[@class='dropdown-menu-items']"));

		selectDropdownItemFromFlexSelector(threeDotEllipsis, ul, linkText);
	}

	/**
	 * Method to handle clicking drop down items in a flex selector. This method
	 * requires the div that contains the pop out element, the pop out element, and
	 * also a value for the item to select.
	 * 
	 * @param containingDiv
	 * @param ul
	 * @param itemToSelect
	 */
	@Override
	protected void selectDropdownItemFromFlexSelector(WebElement containingDiv, WebElement ul, String itemToSelect) {

		// First, click the containing div that holds the pop out menu. This could be
		// clicking the division.
		containingDiv.click();

		// To wait for element visible
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(NORMAL_TIMEOUT));
		wait.until(ExpectedConditions.elementToBeClickable(ul));

		// Create a list of all of the list items beneath the popper div
		List<WebElement> listItems = driver.findElements(By.xpath("//ul[@class='dropdown-menu-items']/li"));

		// If we do not find any list items, let the user know.
		if (listItems.size() == 0) {
			throw new ElementNotVisibleException("There are no list items avaliable");
		}

		// This boolean is required so that we can throw an exception if we do not find
		// an item to click.
		boolean listItemClicked = false;

		// Loop through all of the list items and click the one that matches the passed
		// in value by the user.
		for (WebElement currentListItem : listItems) {
			if (currentListItem.getText().equalsIgnoreCase(itemToSelect)) {
				currentListItem.click();
				listItemClicked = true;
				break;
			}
		}

		// If we didn't get to click an item, throw a meaningful error.
		if (!listItemClicked) {
			throw new ElementNotVisibleException("The item of '" + itemToSelect + "' is not visible in the list.");
		}
	}

	/**
	 * Method to upload a file(s) when an HTML.Input object is present on the page.
	 * 
	 * <br>
	 * Note: this method uses the same code as uploadFile in SW2Base, except that it
	 * takes in the Document Type as a parameter. As such, it's not necessarily an
	 * override. This is necessary to reduce scope to the specific file upload as
	 * there are MANY tables on the page (or can be).
	 * 
	 * @param documentType  - e.g. Big Red, Check Dam, Concrete Washout Area - case
	 *                      must match.
	 * @param filesToUpload - only the path forward from localDataSets. E.g.
	 *                      "example-finding-images//picture1.jpeg"
	 */
	public void uploadFile(String documentType, String filesToUpload) {

		// There are issues in which sometimes a file is uploaded, but it doesn't
		// display right away or perhaps doesn't get uploaded at all. This cannot be
		// reproduced manually. Automation has been modified significantly to
		// accommodate this
		// "buggyness" in the system in that we put a while loop in to check for the
		// value in the table. If it did not get placed in the table, it re-attempts it
		// a second time. With this code, I cannot get the automation to fail.

		// First, see if the data has multiple files, as separated by a comma.
		if (!filesToUpload.equals("")) {

			String[] files = filesToUpload.split(",");
			// Remove the white space from before and after each separate file name
			for (int i = 0; i < files.length; i++) {
				files[i] = files[i].trim();
			}

			// Loop through each item in the array and perform an upload
			for (String currentFile : files) {

				// Grab a new reference to the File Upload Object each time
				WebElement fileUploadObject = driver.findElement(By.xpath(
						"//h3[text() = '" + documentType + "']//parent::div//input[@type='file'][@multiple='']"));

				String filePath = generateFullFileNameAndPath(currentFile);

				fileUploadObject.sendKeys(filePath);
				waitForFileToLoad();
				waitForPageToLoad();

				// Because the file will duplicate the last file each time, we have to perform a
				// refresh. It's not pretty, but it works.
				driver.navigate().refresh();
				waitForPageToLoad();

				String fileNameOnly = filePath.substring(filePath.lastIndexOf("\\") + 1, filePath.length());

				// Ensure the file made it in the table. If it did not, then upload it again.
				// This is needed in some instances where we upload multipes at the same time.
				while (!getTable(documentType).isRowInTableByValue("Name", fileNameOnly)) {

					fileUploadObject = driver.findElement(By.xpath(
							"//h3[text() = '" + documentType + "']//parent::div//input[@type='file'][@multiple='']"));

					fileUploadObject.sendKeys(filePath);
					waitForFileToLoad();

					driver.navigate().refresh();
					waitForPageToLoad();

				}

				// Good to print in the log
				Reporter.log("File Uploaded: " + filePath, true);
				Reporter.log("", true);
			}
		}
	}

	/**
	 * Returns a reference to the a Table corresponding with the passed in table
	 * Document Type. <br>
	 * Note: This is the table of items within a particular regulation.
	 * 
	 * @String tableDocumentType - The Table heading, e.g. Concrete Washout Area,
	 *         Check Dam
	 * @return Tables
	 */
	public SW2Tables getTable(String tableDocumentType) {

		long methodStartTime = System.currentTimeMillis();

		// Lets safely wait on the table. Ensure that it comes back.
		AutomationHelper.adjustWaitTimeToShort();
		List<WebElement> table = driver
				.findElements(By.xpath("//h3[text() = '" + tableDocumentType + "']//ancestor::section[1]//table"));
		int counter = 0;
		while (table.size() < 1 && counter < 300) {
			AutomationHelper.waitSeconds(1);
			table = driver
					.findElements(By.xpath("//h3[text() = '" + tableDocumentType + "']//ancestor::section[1]//table"));
			counter++;
		}
		if (table.size() == 0) {
			throw new ElementNotVisibleException("Cannot find a table with text of " + tableDocumentType
					+ ". There is a chance that the DOCUMENT TYPES section of the project does NOT have this value checked. If this is true, it will NOT show up in the list. ");
		}
		AutomationHelper.adjustWaitTimeToNormal();

		// Creating a list of "Show More" buttons. Only expecting One. If we find at
		// least one, and the button is Show More and not Show Less, then click it to
		// expand the list.

		AutomationHelper.adjustWaitTimeToShort();
		List<WebElement> showMoreButtons = driver
				.findElements(By.xpath("//h3[text() = '" + tableDocumentType + "']//ancestor::section[1]//button"));
		AutomationHelper.adjustWaitTimeToNormal();

		if (showMoreButtons.size() > 0) {
			if (showMoreButtons.get(0).getText().equals("Show More")) {
				showMoreButtons.get(0).click();
			}
		}

		long methodEndTime = System.currentTimeMillis();
		System.out.println("Method getTable(" + tableDocumentType + ") took "
				+ ((methodEndTime - methodStartTime) / 1000) + " seconds to complete.");

		return new SW2Tables(table.get(0));
	}

	/**
	 * Method to return a reference to the Print SWMP Modal
	 * 
	 * @return PrintSWMPModal
	 */
	public PrintSWMPModal getPrintSWMPModal() {
		return new PrintSWMPModal();
	}

	/**
	 * Class to contain objects to work with the "Print SWMP" modal.
	 * 
	 * @author Jesse Childress
	 *
	 */
	public class PrintSWMPModal extends SW2Modals {

	}

	/**
	 * Method to return a reference to the Print Inspections modal
	 * 
	 * @return PrintInspectionsModal
	 */
	public PrintInspectionsModal getPrintInspectionsModal() {
		return new PrintInspectionsModal();
	}

	/**
	 * Class to contain objects to work with the "Print Inspections" modal.
	 * 
	 * @author Jesse Childress
	 *
	 */
	public class PrintInspectionsModal extends SW2Modals {

	}

	/**
	 * Method to return a reference to the Delete Project Modal
	 * 
	 * @return DeleteProjectModal
	 */
	public DeleteProjectModal getDeleteProjectModal() {
		return new DeleteProjectModal();
	}

	/**
	 * Class to contain object to work with the "Delete Project" modal.
	 * 
	 * @author Jesse Childress
	 *
	 */
	public class DeleteProjectModal extends SW2Modals {

		/**
		 * Method to click the Yes, I understand button on a modal.
		 */
		public void clickYesIUnderstand() {
			AutomationHelper.printMethodName();
			WebElement yesIUnderstandButton = MODAL
					.findElement(By.xpath("//div[@class='buttons']//button[text()='Yes, I understand']"));
			yesIUnderstandButton.click();
			waitForModalToDisappear();
			waitForPageToLoad();
		}

	}
}
