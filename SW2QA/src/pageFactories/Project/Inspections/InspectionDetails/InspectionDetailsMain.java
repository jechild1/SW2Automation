package pageFactories.Project.Inspections.InspectionDetails;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import pageFactories.SW2MenusPageFactory;
import pageFactories.SW2Popups;
import utilities.AutomationHelper;

/**
 * This is an abstract class that will provide access to common methods for the
 * Project Inspections Details pages.
 * 
 * @author Jesse Childress
 *
 */
public abstract class InspectionDetailsMain extends SW2MenusPageFactory {

	/**
	 * Page Constructor - Calls constructor in MenusPageFactory.
	 */
	public InspectionDetailsMain(String regexURL) {
		super(regexURL);
		waitForPageToLoad();
	}

	/**
	 * Method to click the "Inspection Questions" tab.
	 */
	public void clickInspectionQuestionsTab() {
		AutomationHelper.printMethodName();
		clickLinkByLinkText("Inspection Questions");
	}

	/**
	 * Method to click the "Findings" tab.
	 */
	public void clickFindingsTab() {
		AutomationHelper.printMethodName();
		clickLinkByLinkText("Findings");
		waitForPageToLoad();
	}

	/**
	 * Method to click the "Map" tab.
	 */
	public void clickMapTab() {
		AutomationHelper.printMethodName();
		clickLinkByLinkText("Map");
	}

	/**
	 * Method to click the "Certification" tab.
	 */
	public void clickCertificationTab() {
		AutomationHelper.printMethodName();
		clickLinkByLinkText("Certification");
	}

	/**
	 * Method to read the inspection date in the header section of a given
	 * inspection.
	 * 
	 * @return String
	 */
	public String readInspectionDate() {
		AutomationHelper.printMethodName();

		// Find the object
		WebElement h2Element = driver
				.findElement(By.xpath("//div[@class = 'inspection-header-info text-truncate']/h2"));

		// Find the text of the object
		String h2Text = h2Element.getText();

		// Strip off everything after the pipe |
		h2Text = h2Text.substring(0, h2Text.lastIndexOf("|") - 1);

		return h2Text;
	}

	/**
	 * Method to read the inspection type in the header section of a given
	 * inspection, e.g. Post Storm
	 * 
	 * @return String
	 */
	public String readInspectionType() {
		AutomationHelper.printMethodName();

		// Find the object
		WebElement h2Element = driver
				.findElement(By.xpath("//div[@class = 'inspection-header-info text-truncate']/h2"));

		// Find the text of the object
		String h2Text = h2Element.getText();

		// Strip off everything after the pipe |
		h2Text = h2Text.substring(h2Text.lastIndexOf("|") + 2, h2Text.length());

		return h2Text;
	}

	/**
	 * Method to read the inspection number in the header section of a given
	 * inspection.
	 * 
	 * @return String
	 */
	public String readInspectionNumber() {
		AutomationHelper.printMethodName();

		// Find the object
		WebElement h5Element = driver
				.findElement(By.xpath("//div[@class = 'inspection-header-info text-truncate']/h5[1]"));

		// Find the text of the object
		String h5Text = h5Element.getText();

		// Strip off everything after the pipe |
		h5Text = h5Text.substring(0, h5Text.lastIndexOf("|") - 1);

		return h5Text;
	}

	/**
	 * Method to read the inspection ID in the header section of a given inspection.
	 * 
	 * @return String
	 */
	public String readInspectionID() {
		AutomationHelper.printMethodName();

		// Find the object
		WebElement h5Element = driver
				.findElement(By.xpath("//div[@class = 'inspection-header-info text-truncate']/h5[1]"));

		// Find the text of the object
		String h5Text = h5Element.getText();

		// Strip off everything after the pipe |
		h5Text = h5Text.substring(h5Text.lastIndexOf("ID") + 3, h5Text.length());

		return h5Text;
	}

	/**
	 * Method to read the Project in the header section of a given inspection.
	 * 
	 * @return String
	 */
	public String readInspectionProject() {
		AutomationHelper.printMethodName();

		// Find the object
		WebElement h5Element = driver
				.findElement(By.xpath("//div[@class = 'inspection-header-info text-truncate']/h5[2]"));

		// Find the text of the object
		String h5Text = h5Element.getText();

		return h5Text;
	}

	/**
	 * Method to read the Client in the header section of a given inspection.
	 * 
	 * @return String
	 */
	public String readInspectionClient() {
		AutomationHelper.printMethodName();
		// Find the object
		WebElement h5Element = driver
				.findElement(By.xpath("//div[@class = 'inspection-header-info text-truncate']/h5[3]"));

		// Find the text of the object
		String h5Text = h5Element.getText();

		return h5Text;
	}

	/**
	 * Method to click the "Back to Project" link behind the three dot ellipsis.
	 */
	public void clickBackToProject() {
		AutomationHelper.printMethodName();
		clickButtonBehindThreeDotEllipsis("Back to Project");
	}

	/**
	 * Method to click the "Delete Inspection" link behind the three dot ellipsis.
	 */
	public void clickDeleteInspection() {
		AutomationHelper.printMethodName();
		clickButtonBehindThreeDotEllipsis("Delete Inspection");
	}

	/**
	 * Method to click the "Print Inspection" link behind the three dot ellipsis.
	 */
	public void clickPrintInspection() {
		AutomationHelper.printMethodName();
		clickButtonBehindThreeDotEllipsis("Print Inspection");
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
	 * Method to return a reference to the Delete Inspection Popup.
	 * 
	 * @return DeleteInspectionPopup
	 */
	public DeleteInspectionPopup getDeleteInspectionPopup() {
		return new DeleteInspectionPopup();
	}

	/**
	 * Class to contain objects to work with the "Are you sure you want to delete
	 * this inspection" popup.
	 * 
	 * @author Jesse Childress
	 *
	 */
	public class DeleteInspectionPopup extends SW2Popups {

		public void clickDeleteClient() {
			AutomationHelper.printMethodName();
			POPUP.findElement(By.xpath("//button[text()='Delete Client']"));
		}

	}

	/**
	 * Method to return a reference to the Print Inspection Popup.
	 * 
	 * @return PrintInspectionPopup
	 */
	public PrintInspectionPopup getPrintInspectionPopup() {
		return new PrintInspectionPopup();
	}

	/**
	 * Class to contain objects to work with the "Print Inspection" popup.
	 * 
	 * @author Jesse Childress
	 *
	 */
	
	public class PrintInspectionPopup extends SW2Popups {

		/**
		 * Method to click the Print button within a popup modal
		 */
		public void clickPrint() {
			AutomationHelper.printMethodName();
			WebElement printButton = POPUP.findElement(By.xpath("//button[text()='Print']"));
			printButton.click();

			waitForDownloadLinkPresence();
			
			Robot r = null;
			try {
				r = new Robot();
			} catch (AWTException e) {
				e.printStackTrace();
			}
			AutomationHelper.waitSeconds(2);
			r.keyPress(KeyEvent.VK_ESCAPE);
			r.keyRelease(KeyEvent.VK_ESCAPE);
			

		}

		/**
		 * Reads the selected value of the <b>Entire Inspection</b> radio button.
		 * 
		 * @return boolean - true if checked; false if not.
		 */
		public boolean readEntireInspection() {
			AutomationHelper.printMethodName();
			return AutomationHelper.readRadioButtonStaus(getInputObject("Entire Inspection"));
		}

		/**
		 * Clicks the <b>Entire Inspection</b> radio button.
		 */
		public void setEntireInspection(boolean desiredStatus) {
			AutomationHelper.printMethodName();
			AutomationHelper.setRadioButton(getInputObject("Entire Inspection"), desiredStatus);
		}

		/**
		 * Reads the selected value of the <b>Corrective Action Log Only</b> radio
		 * button.
		 * 
		 * @return boolean - true if checked; false if not.
		 */
		public boolean readCorrectiveActionLogOnly() {
			AutomationHelper.printMethodName();
			return AutomationHelper.readRadioButtonStaus(getInputObject("Corrective Action Log Only"));
		}

		/**
		 * Clicks the <b>Corrective Action Log Only</b> radio button.
		 */
		public void setCorrectiveActionLogOnly(boolean desiredStatus) {
			AutomationHelper.printMethodName();
			AutomationHelper.setRadioButton(getInputObject("Corrective Action Log Only"), desiredStatus);
		}

		/**
		 * Reads the selected value of the <b>Signature Page Onlyy</b> radio button.
		 * 
		 * @return boolean - true if checked; false if not.
		 */
		public boolean readSignaturePageOnly() {
			AutomationHelper.printMethodName();
			return AutomationHelper.readRadioButtonStaus(getInputObject("Signature Page Only"));
		}

		/**
		 * Clicks the <b>Signature Page Only</b> radio button.
		 */
		public void setSignaturePageOnly(boolean desiredStatus) {
			AutomationHelper.printMethodName();
			AutomationHelper.setRadioButton(getInputObject("Signature Page Only"), desiredStatus);
		}

		/**
		 * Reads the selected value of the <b>Finding Locations</b> check box.
		 * 
		 * @return boolean - true if checked; false if not.
		 */
		public boolean readFindingLocations() {
			AutomationHelper.printMethodName();
			return AutomationHelper.readCheckboxStaus(getInputObject("Finding Locations"));
		}

		/**
		 * Clicks the <b>Finding Locations</b> check box.
		 */
		public void setFindingLocations(boolean desiredStatus) {
			AutomationHelper.printMethodName();
			AutomationHelper.setCheckbox(getInputObject("Finding Locations"), desiredStatus);
		}

		/**
		 * Reads the selected value of the <b>Control Measures</b> check box.
		 * 
		 * @return boolean - true if checked; false if not.
		 */
		public boolean readControlMeasures() {
			AutomationHelper.printMethodName();
			return AutomationHelper.readCheckboxStaus(getInputObject("Control Measures"));
		}

		/**
		 * Clicks the <b>Control Measures</b> check box.
		 */
		public void setControlMeasures(boolean desiredStatus) {
			AutomationHelper.printMethodName();
			AutomationHelper.setCheckbox(getInputObject("Control Measures"), desiredStatus);
		}

		/**
		 * Reads the selected value of the <b>Include Changelog</b> check box.
		 * 
		 * @return boolean - true if checked; false if not.
		 */
		public boolean readIncludeChangelog() {
			AutomationHelper.printMethodName();
			return AutomationHelper.readCheckboxStaus(getInputObject("Include Changelog"));
		}

		/**
		 * Clicks the <b>Include Changelog</b> check box.
		 */
		public void setIncludeChangelog(boolean desiredStatus) {
			AutomationHelper.printMethodName();
			AutomationHelper.setCheckbox(getInputObject("Include Changelog"), desiredStatus);
		}

		/**
		 * Reads the selected value of the <b>Finding Images</b> check box.
		 * 
		 * @return boolean - true if checked; false if not.
		 */
		public boolean readFindingImages() {
			AutomationHelper.printMethodName();
			return AutomationHelper.readCheckboxStaus(getInputObject("Finding Images"));
		}

		/**
		 * Clicks the <b>Finding Images</b> check box.
		 */
		public void setFindingImages(boolean desiredStatus) {
			AutomationHelper.printMethodName();
			AutomationHelper.setCheckbox(getInputObject("Finding Images"), desiredStatus);
		}

		/**
		 * Utility method to find Radio Button or Check Boxes on a popup.
		 * 
		 * @param objectName
		 * @return WebElement
		 */
		private WebElement getInputObject(String objectName) {
			String xpath = "//span[text()='" + objectName + "']//following-sibling::input";
			WebElement object = POPUP.findElement(By.xpath(xpath));
			return object;
		}

		/**
		 * Method to return the status of the presence of the download link. This link
		 * can take a bit to generate as it has to gather data and generate a file. This
		 * method will wait up to 5 minutes (300 seconds) for the link to appear.
		 * 
		 * @return boolean
		 */
		public boolean isDownloadLinkPresent() {
			AutomationHelper.printMethodName();
			boolean link;

			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));

			try {
				// Lets time the method
				Instant start = Instant.now();
				wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
						By.xpath("//section[@class='modal-main modal-small']//a[text()='Download']")));
				Instant end = Instant.now();
				Reporter.log("Waited " + Duration.between(start, end).toSeconds() + " for the download link to appear",
						true);

				link = true;
			} catch (NoSuchElementException e) {
				link = false;
			}
			;

			return link;

		}

		public void waitForDownloadLinkPresence() {
			AutomationHelper.printMethodName();

			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));

			Instant start = Instant.now();
			wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
					By.xpath("//section[@class='modal-main modal-small']//a[text()='Download']")));
			Instant end = Instant.now();
			Reporter.log("Waited " + Duration.between(start, end).toSeconds() + " for the download link to appear",
					true);

		}

		/**
		 * Method to click the Download link on the Print modal. <br>
		 * Note: This method will call waitForFileDownload(String fileName) and wait two minutes for the file to be present in the path as specified in this 
		 */
		public void clickDownloadLink() {
			AutomationHelper.printMethodName();


			// Lets time the method
			waitForDownloadLinkPresence();

			WebElement downloadLink = driver.findElement(By.xpath("//section[@class='modal-main modal-small']//a[text()='Download']"));
			
			//This gets the full name of the download link
			String fileName = downloadLink.getAttribute("href");
			fileName = fileName.substring(fileName.lastIndexOf("/")+1, fileName.lastIndexOf(".pdf")+4);	
			
			
			downloadLink.click();
			
			AutomationHelper.waitForFileDownload(fileName);
			

		}

	}
}
