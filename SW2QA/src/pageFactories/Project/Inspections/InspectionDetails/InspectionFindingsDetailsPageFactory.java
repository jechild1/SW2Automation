package pageFactories.Project.Inspections.InspectionDetails;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.WebElement;

import utilities.AutomationHelper;

/**
 * Page factory for the Project page > Inspections > (Specific Inspection) >
 * Findings > Finding Details page. This is the page that you'll find the
 * details of a specific inspection listed on..<br>
 * (1) Finds a reference to objects on the page <br>
 * (2) Defines methods to interact with objects on the page.<br>
 * 
 * @author Jesse Childress
 *
 */
public class InspectionFindingsDetailsPageFactory extends SW2InspectionDetailsMain {

	public static String regexURL = BASE_URL + ".*" + "inspection" + ".*" + "findings" + ".*" + "details";

	/**
	 * Page Constructor - Calls constructor in MenusPageFactory.
	 */
	public InspectionFindingsDetailsPageFactory() {
		super(regexURL);
	}

	/**
	 * Method to click the Save button
	 */
	public void clickSave() {
		AutomationHelper.printMethodName();
		clickButtonByText("Save");

		waitForCheckSuccessToAppearAndDisappear();
		waitForPageToLoad();
	}

	WebElement correctiveActionRadioButton = driver
			.findElement(By.xpath("//span[text()='Corrective Action']//following-sibling::input[@type='radio']"));

	/**
	 * Method to click the Corrective Action radio button.
	 */
	public void clickCorrectiveActionRadioButton() {
		AutomationHelper.printMethodName();
		correctiveActionRadioButton.click();
	}

	/**
	 * Method to return the clicked status of the Corrective Action radio button.
	 * 
	 * @return boolean; returns true if selected, false if not selected.
	 */
	public boolean isCorrectiveActionRadioButtonSelected() {
		AutomationHelper.printMethodName();
		return correctiveActionRadioButton.isSelected();
	}

	WebElement maintenanceItemRadioButton = driver
			.findElement(By.xpath("//span[text()='Maintenance Item']//following-sibling::input[@type='radio']"));

	/**
	 * Method to click the Maintenance Item radio button.
	 */
	public void clickMaintainenceItemRadioButton() {
		AutomationHelper.printMethodName();
		maintenanceItemRadioButton.click();
	}

	/**
	 * Method to return the clicked status of the Maintenance Item radio button.
	 * 
	 * @return boolean; returns true if selected, false if not selected.
	 */
	public boolean isMaintenanceItemRadioButtonSelected() {
		AutomationHelper.printMethodName();
		return maintenanceItemRadioButton.isSelected();
	}

	WebElement achievementRadioButton = driver
			.findElement(By.xpath("//span[text()='Achievement']//following-sibling::input[@type='radio']"));

	/**
	 * Method to click the Achievement radio button.
	 */
	public void clickAchievementRadioButton() {
		AutomationHelper.printMethodName();
		achievementRadioButton.click();
	}

	/**
	 * Method to return the clicked status of the Achievement radio button.
	 * 
	 * @return boolean; returns true if selected, false if not selected.
	 */
	public boolean isAchievementRadioButtonSelected() {
		AutomationHelper.printMethodName();
		return achievementRadioButton.isSelected();
	}

	/**
	 * Reads the selected radio button in the Finding Type group.
	 * 
	 * @return String
	 */
	public String readSelectedFindingType() {

		// Find a list of radio buttons
		List<WebElement> radioButtons = driver
				.findElements(By.xpath("//div[@class = 'form-control form-control--radio']//input[@type='radio']"));

		// Getting the span was particularly difficult (the text from it).
		String radioButtonValue = "";
		@SuppressWarnings("unused")
		String findingType = "";

		// Loop through the radio buttons to find which one is checked.
		for (WebElement currentRadioButton : radioButtons) {
			if (currentRadioButton.isSelected()) {
				radioButtonValue = currentRadioButton.getAttribute("value");
				break;
			}
		}

		switch (radioButtonValue) {

		case "CA":
			return findingType = "Corrective Action";
		case "MI":
			return findingType = "Maintenance Item";
		case "A":
			return findingType = "Achievement";
		default:
			throw new ElementNotVisibleException(
					"There is not a finding type on this page of Corrective Action, Maintenance Item, or Achievement");
		}
	}

	WebElement locationTextbox = driver.findElement(By.id("formGroup-location"));

	/**
	 * Returns a the value in the Location text box.
	 * 
	 * @return String
	 */
	public String readLocationTextbox() {
		AutomationHelper.printMethodName();
		return AutomationHelper.getText(locationTextbox);
	}

	/**
	 * Sets the Location field with the passed in text.
	 * 
	 * @param location
	 */
	public void setLocationTextbox(String location) {
		AutomationHelper.printMethodName();
		AutomationHelper.setTextField(locationTextbox, location);
	}

	WebElement dateInitiatedTextbox = driver.findElement(By.id("formGroup-date_initiated"));

	/**
	 * Method to read the Date Initiated text box / date picker.
	 * 
	 * @return String
	 */
	public String readDateInitiatedTextbox() {
		AutomationHelper.printMethodName();
		return AutomationHelper.getText(dateInitiatedTextbox);
	}

	/**
	 * Method to set the Date Initiated text box / date picker
	 * 
	 * @param dateInitiated - MM-dd-yyyy format
	 */
	public void setDateInitiatedTextbox(String dateInitiated) {
		AutomationHelper.printMethodName();
		AutomationHelper.setCalendarDate(dateInitiatedTextbox, dateInitiated);
	}

	WebElement dateCompletedTextbox = driver.findElement(By.id("formGroup-date_completed"));

	/**
	 * Method to read the Date Completed text box / date picker.
	 * 
	 * @return String
	 */
	public String readDateCompletedTextbox() {
		AutomationHelper.printMethodName();
		return AutomationHelper.getText(dateCompletedTextbox);
	}

	/**
	 * Method to set the Date Completed text box / date picker
	 * 
	 * @param dateCompleted - MM-dd-yyyy format
	 */
	public void setDateCompletedTextbox(String dateCompleted) {
		AutomationHelper.printMethodName();
		AutomationHelper.setCalendarDate(dateCompletedTextbox, dateCompleted);
	}

	/**
	 * Method to clear the Date Completed text box / date picker.
	 */
	public void clearDateCompletedTextbox() {
		AutomationHelper.printMethodName();
		dateCompletedTextbox.clear();
	}

	// Use Template button and sub menus do not provide value. Not coding at this
	// time.

	WebElement observationsTextArea = driver.findElement(By.id("formGroup-comments"));

	/**
	 * Reads the Observations text area. Note, after observations save, they appear
	 * in a different location so use of this method may be limited.
	 * 
	 * @return String
	 */
	public String readObervationsTextArea() {
		AutomationHelper.printMethodName();
		return AutomationHelper.getText(observationsTextArea);
	}

	/**
	 * Sets the Observation Text area with the passed in text.
	 * 
	 * @param observation
	 */
	public void setObservationsTextArea(String observation) {
		AutomationHelper.printMethodName();
		AutomationHelper.setTextField(observationsTextArea, observation);
	}
	
	/**
	 * Method that checks for the presence of an image on the page. <br>
	 * This method is smart enough to check for ALL images, separated by a comma.
	 * 
	 * @param images
	 * @return boolean
	 */
	public boolean isImagePresent(String images) {
		AutomationHelper.printMethodName();

		/*
		 * First, capture a list of all of the images on the page and store them in an
		 * array.
		 */
		List<WebElement> imagesOnPage = driver
				.findElements(By.xpath("//section[@class='upload-gallery pure-g']//div[@class = 'controls']"));
		// Ensure that there are images found
		if (imagesOnPage.size() < 1) {
			throw new ElementNotVisibleException("There are no images on the finding.");
		}

		List<String> imageFileNames = new ArrayList<String>();

		for (WebElement currentFileNameSpan : imagesOnPage) {
			imageFileNames.add(currentFileNameSpan.getText());
		}

		/*
		 * Second, take the passed in string and parse the data out to make a list of
		 * files. The files are separated by comma.
		 */
		String[] splitString = images.split(",");

		// Above line gets us an array with this data
		// example-finding-images//DSC_01112.jpg
		// example-finding-images//DSC_0137.jpg
		// example-finding-images//DSC_0138.jpg

		// Create a list string array to hold the file names
		List<String> fileNames = new ArrayList<String>();

		// Cycle through the split string and then get the substring
		for (String currentFilePath : splitString) {
			fileNames.add(currentFilePath.substring(currentFilePath.indexOf("//") + 2, currentFilePath.length()));
		}

		/*
		 * Third, Check that the image exists in a loop
		 */

		int imageCountFound = 0;

		// Start with the file names on the page
		for (String currentImageFileNameOnPage : imageFileNames) {

			// Attempt a match for each one
			for (String currentFileNameInString : fileNames) {
				if (currentImageFileNameOnPage.equals(currentFileNameInString)) {
					imageCountFound++;

				}
			}

		}
		// If the imageCountFound is the same as the file names size, return true, else
		// return false;
		return (imageCountFound == imageFileNames.size()) ? true : false;

	}
}
