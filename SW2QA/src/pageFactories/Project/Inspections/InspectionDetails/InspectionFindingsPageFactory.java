package pageFactories.Project.Inspections.InspectionDetails;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import pageFactories.SW2Modals;
import utilities.AutomationHelper;

/**
 * Page factory for the Project page > Inspections > (Specific Inspection) >
 * Findings. This is the page that you'll a list of all of the findings.<br>
 * (1) Finds a reference to objects on the page <br>
 * (2) Defines methods to interact with objects on the page.<br>
 * 
 * @author Jesse Childress
 *
 */
public class InspectionFindingsPageFactory extends InspectionDetailsMain {

	public static String regexURL = BASE_URL + ".*" + "inspection" + ".*" + "findings";

	/**
	 * Page Constructor - Calls constructor in MenusPageFactory.
	 */
	public InspectionFindingsPageFactory() {
		super(regexURL);
	}

	/**
	 * Clicks the Add Finding button
	 */
	public void clickAddFinding() {
		clickButtonByText("Add Finding");
		waitForPageToLoad();
	}

	/**
	 * Method to click on a finding. The Completed Date can be a value, empty
	 * string, or null as we do not always want to use it.
	 * 
	 * @param findingType
	 * @param initiatedDate
	 * @param completedDate
	 */
	public void clickFinding(String findingType, String initiatedDate, String completedDate) {
		AutomationHelper.printMethodName();
		waitForPageToLoad(); // Necessary because sometimes the spinner still exists

		// Check for date formats
		AutomationHelper.isDateFormatValid("MM-dd-yyyy", initiatedDate);
		if (!completedDate.equals("") && (completedDate != null)) {
			AutomationHelper.isDateFormatValid("MM-dd-yyyy", completedDate);
		}

		boolean multiplesFound = false;
		WebElement finalCard = null;

		// STEP ONE: Find the cards and ensure that there is some
		// The xpath is written with a contain that helps with when the card is
		// completed vs in progress
		String xpathForCards = "//div[contains (@class,'finding-wrapper pure-u pure-u-1 pure-u-md-1-2')]";
		List<WebElement> inspectionCards = driver.findElements(By.xpath(xpathForCards));
		if (inspectionCards.size() < 1) {
			throw new ElementNotVisibleException("There are no inspections on the page");
		}

		// STEP TWO: With each inspection card on the page, limit the scope only to
		// those that have the correct finding type, e.g. Achievement, Corrective
		// Action, or Maintenance Item.
		List<WebElement> inspectionCardsOfSameFindingType = new ArrayList<WebElement>();

		// Sometimes the page is slow to load the last finding. As a result, it won't
		// find the correct cards. It needs another chance to find a card, if need be.
		// This cost a day to figure out why things would fail if they didn't find a
		// card. In short, if we don't find one, were going to refresh the page before
		// we error out.
		int attemptCounter = 0;

		do {

			for (WebElement currentInspectionCard : inspectionCards) {

				String spanInQuestionText = currentInspectionCard.getText();

				// The string will appear as something like this:
				// "#72\\nAchievement\\nObservation: This is an observation that I will
				// put.\\nLocation: 123 Fake Location, Fakeburg, CO
				// 39093\\nInitiated:01-28-2022\\nCompleted:01-28-2022"
				String[] inspectionCardSplit = spanInQuestionText.split("\n");

//			//If we need to see what is in the string.
//			for(String w : inspectionCardSplit) {
//				System.out.println(w);
//			}

				spanInQuestionText = inspectionCardSplit[1];

				if (spanInQuestionText.equalsIgnoreCase(findingType)) {
					inspectionCardsOfSameFindingType.add(currentInspectionCard);
				}

				attemptCounter++;

			}
			if (inspectionCardsOfSameFindingType.size() == 0) {
				AutomationHelper.waitSeconds(1);
				inspectionCards = driver.findElements(By.xpath(xpathForCards));
				Reporter.log("Waiting for inspection type of: " + findingType + ". Seconds: " + attemptCounter, true);
			}
		} while (inspectionCardsOfSameFindingType.size() == 0 && attemptCounter < 300);

		// STEP THREE: Ensure we found a value. If so, further reduce scope to those
		// with a initiated date that matches the date passed in.

		if (inspectionCardsOfSameFindingType.size() < 1) {
			throw new ElementNotVisibleException(
					"There are no inspections on the page with a finding type of: " + findingType);
		}

		List<WebElement> sameFindingTypeAndDateInitiated = new ArrayList<WebElement>();

		for (WebElement currentInspectionCard : inspectionCardsOfSameFindingType) {

			String spanInQuestionText = currentInspectionCard.getText();

			// The string will appear as something like this:
			// "#72\\nAchievement\\nObservation: This is an observation that I will
			// put.\\nLocation: 123 Fake Location, Fakeburg, CO
			// 39093\\nInitiated:01-28-2022\\nCompleted:01-28-2022"
			String[] inspectionCardSplit = spanInQuestionText.split("\n");

//			//If we need to see what is in the string.
//			for(String w : inspectionCardSplit) {
//				System.out.println(w);
//			}

			// Because we never know which index the "Initiated" will be (because sometimes
			// we don't have an observation), we must loop through the String[] until we
			// find the text that begins with initiated. It will always be present in a
			// card.
			for (int i = 0; i < inspectionCardSplit.length; i++) {
				if (inspectionCardSplit[i].startsWith("Initiated:")) {
					spanInQuestionText = inspectionCardSplit[i];
					break;
				}
			}

			if (spanInQuestionText.equalsIgnoreCase("Initiated:" + initiatedDate)) {
				sameFindingTypeAndDateInitiated.add(currentInspectionCard);
			}
		}

		// STEP FOUR: IF we found the date initiated, and we pass in a date for date
		// completed, we want to further reduce scope
		if (sameFindingTypeAndDateInitiated.size() < 1) {
			throw new ElementNotVisibleException("There are no inspections on the page with a finding type of: "
					+ findingType + " and Initiated Date of " + initiatedDate);
		} else if (sameFindingTypeAndDateInitiated.size() > 1) {
			multiplesFound = true;
		} else {
			finalCard = inspectionCardsOfSameFindingType.get(0);
		}

		// If there is a desired completedDate, e.g. not null or empty string, then
		// check this.
		if (!completedDate.equals("") && (completedDate != null)) {

			multiplesFound = false;

			List<WebElement> sameFindingTypeDateInitiatedAndCompletedDate = new ArrayList<WebElement>();

			for (WebElement currentInspectionCard : sameFindingTypeAndDateInitiated) {

				String spanInQuestionText = currentInspectionCard.getText();

				// The string will appear as something like this:
				// "#72\\nAchievement\\nObservation: This is an observation that I will
				// put.\\nLocation: 123 Fake Location, Fakeburg, CO
				// 39093\\nInitiated:01-28-2022\\nCompleted:01-28-2022"
				String[] inspectionCardSplit = spanInQuestionText.split("\n");

//				//If we need to see what is in the string.
//				for(String w : inspectionCardSplit) {
//					System.out.println(w);
//				}

				// Because we never know which index the "Initiated" will be (because sometimes
				// we don't have an observation), we must loop through the String[] until we
				// find the text that begins with initiated. It will always be present in a
				// card.
				for (int i = 0; i < inspectionCardSplit.length; i++) {
					if (inspectionCardSplit[i].startsWith("Completed:")) {
						spanInQuestionText = inspectionCardSplit[i];
					}
				}

				if (spanInQuestionText.equalsIgnoreCase("Completed:" + completedDate)) {
					sameFindingTypeDateInitiatedAndCompletedDate.add(currentInspectionCard);
				}
			}

			if (sameFindingTypeDateInitiatedAndCompletedDate.size() < 1) {
				throw new ElementNotVisibleException("There are no inspections on the page with a finding type of: "
						+ findingType + " and Initiated Date of " + initiatedDate + " and Completion Date of "
						+ completedDate);
			} else if (sameFindingTypeDateInitiatedAndCompletedDate.size() > 1) {
				multiplesFound = true;
			} else {
				finalCard = sameFindingTypeDateInitiatedAndCompletedDate.get(0);
			}

		}

		// STEP FIVE: If we have more than one, we need to let the user know that it's
		// not unique

		if (multiplesFound) {
			throw new ElementNotInteractableException(
					"There are multiple inspections on the page with a finding type of: " + findingType
							+ " and Initiated Date of " + initiatedDate + " and Completion Date of " + completedDate);
		}

		// Now, lets click on the object
		finalCard.click();
		waitForPageToLoad();

	}

	/**
	 * Returns a reference to the Add A Finding modal
	 * 
	 * @return AddAFindingModal
	 */
	public AddAFindingModal getAddAFindingModal() {
		return new AddAFindingModal();
	}

	/**
	 * Class to contain objects for the Add a Finding modal
	 * 
	 * @author Jesse Childress
	 *
	 */
	public class AddAFindingModal extends SW2Modals {

		/**
		 * Clicks a finding type radio button corresponding with the passed in finding
		 * type.
		 * 
		 * @param findingType
		 */
		public void clickFindingType(String findingType) {

			if (findingType.equalsIgnoreCase("Corrective Action")) {
				clickCorrectiveActionRadioButton();
			}

			else if (findingType.equalsIgnoreCase("Maintenance Item")) {
				clickMaintenanceItemRadioButton();
			}

			else if (findingType.equalsIgnoreCase("Achievement")) {
				clickAchievementRadioButton();
			}

			else {
				throw new ElementNotVisibleException(
						"There is no radio button with the finding type of " + findingType);
			}
		}

		WebElement correctiveActionRadioButton = MODAL
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

		WebElement maintenanceItemRadioButton = MODAL
				.findElement(By.xpath("//span[text()='Maintenance Item']//following-sibling::input[@type='radio']"));

		/**
		 * Method to click the Maintenance Item radio button.
		 */
		public void clickMaintenanceItemRadioButton() {
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

		WebElement achievementRadioButton = MODAL
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
			List<WebElement> radioButtons = MODAL
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

//			return findingType;

		}

		WebElement locationTextbox = MODAL.findElement(By.id("formGroup-location"));

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

		WebElement dateInitiatedTextbox = MODAL.findElement(By.id("formGroup-date_initiated"));

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

		WebElement dateCompletedTextbox = MODAL.findElement(By.id("formGroup-date_completed"));

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
//			dateCompletedTextbox.clear();

			// The clear doesn't work in Chrome and Edge. Instead, the value looks like it's
			// there, but it's still hidden in the DOM.

			while (!dateCompletedTextbox.getAttribute("value").equals("")) {
				dateCompletedTextbox.sendKeys(Keys.BACK_SPACE);
			}
			// Setting focus outside the date field.
			observationsTextArea.click();
		}

		// Use Template button and sub menus do not provide value. Not coding at this
		// time.

		WebElement observationsTextArea = MODAL.findElement(By.id("formGroup-comments"));

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
		 * Method to upload a file(s) when an HTML.Input object is present on the page.
		 * <br>
		 * Note: This method will override uploadFile in SW2Base when multiple files
		 * exist; else it's re-directed to SUPER. The reason is that when using sendKeys
		 * on the modal, we find that we upload duplicates. This is an ugly fix, but it
		 * works. When multiple files exist, it simply saves the modal, and opens /
		 * re-opens the finding and adds images one at a time.
		 * 
		 * <br>
		 * IMPORTANT: If using multiple files, this method will leave you on the
		 * Inspection Finding Details page with a saved product. If uploading a single
		 * file, this will leave the file uploaded on the Modal, but not saved.
		 * 
		 * @param filesToUpload - only the path forward from localDataSets. E.g.
		 *                      "example-finding-images//picture1.jpeg"
		 */
		public void uploadFile(String filesToUpload) {

			// First, see if the data has multiple files, as separated by a comma.
			String[] files = filesToUpload.split(",");
			// Remove the white space from before and after each separate file name
			for (int i = 0; i < files.length; i++) {
				files[i] = files[i].trim();
			}

			if (files.length == 1) {
				super.uploadFile(filesToUpload);
			} else {

				// Loop through each item in the array and perform an upload

				// Declare some higher level variables to be used.
				String findingType;
				String initiatedDate;
				@SuppressWarnings("unused")
				String completionDate;
				String originalCompletionDate = readDateCompletedTextbox();

				int counter = 0;
				for (String currentFile : files) {

					String xpath = "//input[@type='file'][@multiple = '']";
					// Added wait on the object because sometimes it doesn't show up right away.
					WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
					wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));

					WebElement fileUploadObject = driver.findElement(By.xpath(xpath));

					String filePath = generateFullFileNameAndPath(currentFile);
					// Good to print in the log
					Reporter.log("File Uploaded: " + filePath, true);

					fileUploadObject.sendKeys(filePath);
					waitForFileToLoad();

					/*
					 * Two ways the test can go (1) - Modal (2) - Inspection Finding Details Page
					 */

					// MODAL ROUTE
					if (counter == 0) {
						// We must save each individual picture. However, lets grab the values we will
						// need to find the inspection after the save.
						findingType = getAddAFindingModal().readSelectedFindingType();
						initiatedDate = getAddAFindingModal().readDateInitiatedTextbox();
						completionDate = getAddAFindingModal().readDateCompletedTextbox();

						getAddAFindingModal().clearDateCompletedTextbox();

						getAddAFindingModal().clickOKOnModal();

						counter++;

					}
					// INSPECTION DETAILS ROUTE
					else {

						InspectionFindingsDetailsPageFactory findingDetailsPage = new InspectionFindingsDetailsPageFactory();

						findingType = findingDetailsPage.readSelectedFindingType();
						initiatedDate = findingDetailsPage.readDateInitiatedTextbox();
						completionDate = findingDetailsPage.readDateCompletedTextbox();

						findingDetailsPage.clickSave();

						counter++;

						findingDetailsPage.clickFindingsTab();
					}

					// Returned to the "Findings" tab and the card for the inspection is present.
					InspectionFindingsPageFactory findingsPage = new InspectionFindingsPageFactory();
					findingsPage.waitForPageToLoad();
					findingsPage.clickFinding(findingType, initiatedDate, "");

					// The popup doesn't appear again after the first time. Instead, it's the
					// Inspection Details page
					// The last time through the loop, add the completion date
					if (counter == files.length) {
						InspectionFindingsDetailsPageFactory findingDetailsPage = new InspectionFindingsDetailsPageFactory();
						findingDetailsPage.setDateCompletedTextbox(originalCompletionDate);
						findingDetailsPage.clickSave();
					}

				}
			}
		}

		
	}

}
