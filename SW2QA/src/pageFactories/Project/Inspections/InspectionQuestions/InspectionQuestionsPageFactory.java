package pageFactories.Project.Inspections.InspectionQuestions;

import java.time.DateTimeException;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.ElementNotSelectableException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Reporter;

import pageFactories.Project.Inspections.InspectionDetails.SW2InspectionDetailsMain;
import utilities.AutomationHelper;

/**
 * Page class that contains methods to interact with objects on the Inspection
 * Questions page. Specific details of the page will depend on the Inspection
 * Form Type, and will be included as a nested class here.
 * 
 * @author Jesse Childress
 *
 */
public class InspectionQuestionsPageFactory extends SW2InspectionDetailsMain {

	public static String regexURL = BASE_URL + ".*" + "inspection" + ".*" + "questionnaire";

	/**
	 * Page Constructor - Calls constructor in MenusPageFactory.
	 */
	public InspectionQuestionsPageFactory() {
		super(regexURL);
	}

	/**
	 * Method to click the Save button.
	 */
	public void clickSave() {
		AutomationHelper.printMethodName();
		clickButtonByText("Save");
		waitForPageToLoad();
		// The item here is the container that says "Saving" or "Inspection Saved"
		waitForInspectionToSave();

	}

	/**
	 * Getter method to return a class type of JRCInspectionTemplates, giving access
	 * to all methods within.
	 * 
	 * @return JRCInspectionTemplate
	 */
	public JRCInspectionTemplate getJRCInspectionTemplate() {
		return new JRCInspectionTemplate();
	}

	/**
	 * Nested class containing methods for JRC Inspection Template
	 * 
	 * @author Jesse Childress
	 *
	 */
	public class JRCInspectionTemplate extends InspectionQuestionsPageFactory {

		@FindBy(xpath = "//div[starts-with(@class, 'inspection-question')]//span[contains(text(),'Inspection Type')]//parent::label//parent::span//parent::div//select")
		WebElement inspectionTypeSelect;

		/**
		 * Method to read the Inspection Type field
		 * 
		 * @return String
		 */
		public String readInspectionType() {
			AutomationHelper.printMethodName();
			return AutomationHelper.readSelectedSubItem(inspectionTypeSelect);
		}

		/**
		 * Method to select the Inspection Type value. <br>
		 * Note: Valid values are:<br>
		 * <li>Routine
		 * <li>Routine + Post Storm
		 * <li>Post Storm
		 * <li>Final
		 * 
		 * @param value
		 */
		public void selectInspectionType(String value) {
			AutomationHelper.printMethodName();
			AutomationHelper.selectDropdownItem(inspectionTypeSelect, value);
		}

		@FindBy(xpath = "//div[starts-with(@class, 'inspection-question')]//span[contains(text(),'Date of Inspection')]//parent::label//parent::span//parent::div//input")
		WebElement dateOfInspectionInput;

		/**
		 * Method to read the Date of Inspection field
		 * 
		 * @return String
		 */
		public String readDateOfInspection() {
			AutomationHelper.printMethodName();
			return dateOfInspectionInput.getAttribute("value");
		}

		/**
		 * Method to set the Date of Inspection. <br>
		 * Note: Use format MM-dd-yyyy
		 * 
		 * @param dateOfInspection
		 */
		public void setDateOfInspection(String dateOfInspection) {
			// TODO - Updated to setCalendarDate
			if (!AutomationHelper.isDateFormatValid("MM-dd-yyyy", dateOfInspection)) {
				throw new DateTimeException("You must pass in a date in the correct format of MM-dd-yyyy");
			}

			AutomationHelper.printMethodName();
			AutomationHelper.setTextField(dateOfInspectionInput, dateOfInspection);
		}

		@FindBy(xpath = "//div[starts-with(@class, 'inspection-question')]//span[contains(text(),'Are you at the site?')]//parent::label//parent::span//parent::div//input")
		WebElement areYouAtTheSiteCheckbox;

		/**
		 * Method to read the selected value of the "Are you at the site?" checkbox.
		 * 
		 * @return boolean
		 */
		public boolean readAreYouAtTheSite() {
			AutomationHelper.printMethodName();
			return areYouAtTheSiteCheckbox.isSelected();
		}

		/**
		 * Method to set the "Are you at the site?" check box with the desired status.
		 * 
		 * @param desiredStatus
		 */
		public void setAreYouAtTheSite(boolean desiredStatus) {
			AutomationHelper.printMethodName();
			AutomationHelper.setCheckbox(areYouAtTheSiteCheckbox, desiredStatus);
		}

		@FindBy(xpath = "//div[starts-with(@class, 'inspection-question')]//span[contains(text(),'Date Started')]//parent::label//parent::span//parent::div//input")
		WebElement dateStartedInput;

		/**
		 * Method to read the Date Started value.
		 * 
		 * @return String
		 */
		public String readDateStarted() {
			AutomationHelper.printMethodName();
			return dateStartedInput.getAttribute("value");
		}

		/**
		 * Method to set the Date Started field with the passed in text.
		 * 
		 * @param dateStarted
		 */
		public void setDateStarted(String dateStarted) {
			AutomationHelper.printMethodName();
			// TODO - Update to Date and Time
			if (!AutomationHelper.isDateFormatValid("MM-dd-yyyy", dateStarted)) {
				throw new DateTimeException("You must pass in a date in the correct format of MM-dd-yyyy");
			}

			AutomationHelper.setTextField(dateStartedInput, dateStarted);
		}

		@FindBy(xpath = "//div[starts-with(@class, 'inspection-question')]//span[contains(text(),'Date & Time Finished')]//parent::label//parent::span//parent::div//input")
		WebElement dateAndTimeFinishedInput;

		/**
		 * Method to read the Date and Time Finished field.
		 * 
		 * @return String
		 */
		public String readDateAndTimeFinished() {
			AutomationHelper.printMethodName();

			return dateAndTimeFinishedInput.getAttribute("value");
		}

		/**
		 * Method to set the Date and Time Finished field with the passed in text. <br>
		 * Note: Must use correct format, e.g. 08-15-2022 2:25 PM
		 * 
		 * @param dateAndTimeFinished
		 */
		public void setDateAndTimeFinished(String dateAndTimeFinished) {
			AutomationHelper.printMethodName();
			// TODO - This will need to be updated
			if (!AutomationHelper.isDateFormatValid("MMMM dd, yyyy h:mm a", dateAndTimeFinished)) {
				throw new DateTimeException("You must pass in a date in the correct format of MMMM dd, yyyy h:mm a");
			}
			// TODO - Update to Date and Time method
			AutomationHelper.setTextField(dateAndTimeFinishedInput, dateAndTimeFinished);
		}

		@FindBy(xpath = "//div[starts-with(@class, 'inspection-question')]//span[contains(text(),'Are other people present?')]//parent::label//parent::span//parent::div//select")
		WebElement areOtherPeoplePresentSelect;

		/**
		 * Method to read the currently selected value in the "Are Other People Present"
		 * drop down.
		 * 
		 * @return String
		 */
		public String readAreOtherPeoplePresent() {
			AutomationHelper.printMethodName();
			return AutomationHelper.readSelectedSubItem(areOtherPeoplePresentSelect);
		}

		/**
		 * Method to select a value for "Are Other People Present' drop down. <br>
		 * Note: Valid values are Yes or No
		 * 
		 * @param areOtherPeoplePresent
		 */
		public void selectAreOtherPeoplePresent(String areOtherPeoplePresent) {
			AutomationHelper.printMethodName();
			AutomationHelper.selectDropdownItem(areOtherPeoplePresentSelect, areOtherPeoplePresent);
		}

		@FindBy(xpath = "//div[starts-with(@class, 'inspection-question')]//span[contains(text(),'Are you having a nice day?')]//parent::label//parent::span//parent::div//input")
		WebElement areYouHavingANiceDateCheckbox;

		/**
		 * Method to read the checked status of the "Are you having a nice day?"
		 * checkbox.
		 * 
		 * @return boolean
		 */
		public boolean readAreYouHavingANiceDay() {
			AutomationHelper.printMethodName();
			return areYouHavingANiceDateCheckbox.isSelected();
		}

		/**
		 * Method to set the "Are you having a nice day" checkbox with the passed in
		 * true / false value.
		 * 
		 * @param desiredStatus
		 */
		public void setAreYouHavingANiceDay(boolean desiredStatus) {
			AutomationHelper.printMethodName();
			AutomationHelper.setCheckbox(areYouHavingANiceDateCheckbox, desiredStatus);
		}

	}

	/**
	 * Getter method to return a class type of SQAInspectionTemplateAlpha, giving
	 * access to all methods within.
	 * 
	 * @return JRCInspectionTemplate
	 */
	public SQAInspectionTemplateAlpha getSQAInspectionTemplateAlpha() {
		return new SQAInspectionTemplateAlpha();
	}

	/**
	 * Nested class containing methods for SQA Inspection Template Alpha
	 * 
	 * @author Jesse Childress
	 *
	 */
	public class SQAInspectionTemplateAlpha extends InspectionQuestionsPageFactory {

		@FindBy(xpath = "//div[starts-with(@class, 'inspection-question')]//span[contains(text(),'Inspection Type')]//parent::label//parent::span//parent::div//select")
		WebElement inspectionTypeSelect;

		/**
		 * Method to read the Inspection Type field
		 * 
		 * @return String
		 */
		public String readInspectionType() {
			AutomationHelper.printMethodName();
			return AutomationHelper.readSelectedSubItem(inspectionTypeSelect);
		}

		/**
		 * Method to select the Inspection Type value. <br>
		 * Note: Valid values are:<br>
		 * <li>Routine
		 * <li>Routine + Post Storm
		 * <li>Post Storm
		 * <li>Final
		 * 
		 * @param value
		 */
		public void selectInspectionType(String value) {
			AutomationHelper.printMethodName();
			AutomationHelper.selectDropdownItem(inspectionTypeSelect, value);
		}

		@FindBy(xpath = "//div[starts-with(@class, 'inspection-question')]//span[contains(text(),'Date of Inspection')]//parent::label//parent::span//parent::div//input")
		WebElement dateOfInspectionInput;

		/**
		 * Method to read the Date of Inspection field
		 * 
		 * @return String
		 */
		public String readDateOfInspection() {
			AutomationHelper.printMethodName();
			return dateOfInspectionInput.getAttribute("value");
		}

		/**
		 * Method to set the Date of Inspection. <br>
		 * Note: Use format MM-dd-yyyy
		 * 
		 * @param dateOfInspection
		 */
		public void setDateOfInspection(String dateOfInspection) {
			AutomationHelper.printMethodName();
			AutomationHelper.setCalendarDate(dateOfInspectionInput,
					AutomationHelper.generateCalendarDate(dateOfInspection));
		}

		@FindBy(xpath = "//div[starts-with(@class, 'inspection-question')]//span[contains(text(),'Are you at the site?')]//parent::label//parent::span//parent::div//input")
		WebElement areYouAtTheSiteCheckbox;

		/**
		 * Method to read the selected value of the "Are you at the site?" checkbox.
		 * 
		 * @return boolean
		 */
		public boolean readAreYouAtTheSite() {
			AutomationHelper.printMethodName();
			return areYouAtTheSiteCheckbox.isSelected();
		}

		/**
		 * Method to set the "Are you at the site?" check box with the desired status.
		 * 
		 * @param desiredStatus
		 */
		public void setAreYouAtTheSite(boolean desiredStatus) {
			AutomationHelper.printMethodName();
			AutomationHelper.setCheckbox(areYouAtTheSiteCheckbox, desiredStatus);
		}

		@FindBy(xpath = "//div[starts-with(@class, 'inspection-question')]//span[contains(text(),'Date Started')]//parent::label//parent::span//parent::div//input")
		WebElement dateStartedInput;

		/**
		 * Method to read the Date Started value.
		 * 
		 * @return String
		 */
		public String readDateStarted() {
			AutomationHelper.printMethodName();
			return dateStartedInput.getAttribute("value");
		}

		/**
		 * Method to set the Date Started field with the passed in text.
		 * 
		 * @param dateStarted
		 */
		public void setDateStarted(String dateStarted) {
			AutomationHelper.printMethodName();
			AutomationHelper.setCalendarDate(dateStartedInput, AutomationHelper.generateCalendarDate(dateStarted));
		}

		@FindBy(xpath = "//div[starts-with(@class, 'inspection-question')]//span[contains(text(),'Date & Time Finished')]//parent::label//parent::span//parent::div//input")
		WebElement dateAndTimeFinishedInput;

		/**
		 * Method to read the Date and Time Finished field.
		 * 
		 * @return String
		 */
		public String readDateAndTimeFinished() {
			AutomationHelper.printMethodName();

			return dateAndTimeFinishedInput.getAttribute("value");
		}

		/**
		 * Method to set the Date and Time Finished field with the passed in text. <br>
		 * Note: Must use correct format, e.g. 08-15-2022 2:25 PM NOTE: We currently ARE
		 * NOT doing time on the end. It will fail.
		 * 
		 * @param dateFinished
		 */
		public void setDateAndTimeFinished(String dateFinished) {
			AutomationHelper.printMethodName();
			// TODO - This will need to be updated to Date and Time
			AutomationHelper.setCalendarDateAndTime(dateAndTimeFinishedInput,
					AutomationHelper.generateCalendarDate(dateFinished));
		}

		@FindBy(xpath = "//div[starts-with(@class, 'inspection-question')]//span[contains(text(),'Are other people present?')]//parent::label//parent::span//parent::div//select")
		WebElement areOtherPeoplePresentSelect;

		/**
		 * Method to read the currently selected value in the "Are Other People Present"
		 * drop down.
		 * 
		 * @return String
		 */
		public String readAreOtherPeoplePresent() {
			AutomationHelper.printMethodName();
			return AutomationHelper.readSelectedSubItem(areOtherPeoplePresentSelect);
		}

		/**
		 * Method to select a value for "Are Other People Present' drop down. <br>
		 * Note: Valid values are Yes or No
		 * 
		 * @param areOtherPeoplePresent
		 */
		public void selectAreOtherPeoplePresent(String areOtherPeoplePresent) {
			AutomationHelper.printMethodName();
			AutomationHelper.selectDropdownItem(areOtherPeoplePresentSelect, areOtherPeoplePresent);
		}

		@FindBy(xpath = "//div[starts-with(@class, 'inspection-question')]//span[contains(text(),'Are you having a nice day?')]//parent::label//parent::span//parent::div//input")
		WebElement areYouHavingANiceDateCheckbox;

		/**
		 * Method to read the checked status of the "Are you having a nice day?"
		 * checkbox.
		 * 
		 * @return boolean
		 */
		public boolean readAreYouHavingANiceDay() {
			AutomationHelper.printMethodName();
			return areYouHavingANiceDateCheckbox.isSelected();
		}

		/**
		 * Method to set the "Are you having a nice day" checkbox with the passed in
		 * true / false value.
		 * 
		 * @param desiredStatus
		 */
		public void setAreYouHavingANiceDay(boolean desiredStatus) {
			AutomationHelper.printMethodName();
			AutomationHelper.setCheckbox(areYouHavingANiceDateCheckbox, desiredStatus);
		}

	}

	/**
	 * Getter method to return a class type of ColoradoInspectionTemplate, giving
	 * access to all methods within.
	 * 
	 * @return ColoradoInspectionTemplate
	 */
	public ColoradoInspectionTemplate getColoradoTemplate() {
		return new ColoradoInspectionTemplate();
	}

	/**
	 * Nested class containing methods for Colorado Inspection Template
	 * 
	 * @author Jesse Childress
	 *
	 */
	public class ColoradoInspectionTemplate extends InspectionQuestionsPageFactory {

		@FindBy(xpath = "//div[starts-with(@class, 'inspection-question')]//label[text() = 'Facility Name']//parent::div//input")
		WebElement facilityNameInput;

		/**
		 * Returns the value currently in the Facility Name field
		 * 
		 * @return String
		 */
		public String readFaciltiyName() {
			AutomationHelper.printMethodName();
			return AutomationHelper.getText(facilityNameInput);
		}

		/**
		 * Sets the <b>Facility Name</b> field with the passed in text.
		 * 
		 * @param facilityName
		 */
		public void setFacilityName(String facilityName) {
			AutomationHelper.printMethodName();
			AutomationHelper.setTextField(facilityNameInput, facilityName);
		}

		@FindBy(xpath = "//div[starts-with(@class, 'inspection-question')]//label[text() = 'Client Name']//parent::div//input")
		WebElement clientNameInput;

		/**
		 * Returns the value currently in the <b>Client Name</b> field
		 * 
		 * @return String
		 */
		public String readClientName() {
			AutomationHelper.printMethodName();
			return AutomationHelper.getText(clientNameInput);
		}

		/**
		 * Sets the <b>Client Name</b> field with the passed in text.
		 * 
		 * @param clientName
		 */
		public void setClientName(String clientName) {
			AutomationHelper.printMethodName();
			AutomationHelper.setTextField(clientNameInput, clientName);
		}

		@FindBy(xpath = "//div[starts-with(@class, 'inspection-question')]//label[text() = 'Inspector Name']//parent::div//input")
		WebElement inspectorNameInput;

		/**
		 * Returns the value currently in the <b>Inspector Name</b> field
		 * 
		 * @return String
		 */
		public String readInspectorName() {
			AutomationHelper.printMethodName();
			return AutomationHelper.getText(inspectorNameInput);
		}

		/**
		 * Sets the <b>Inspector Name</b> field with the passed in text.
		 * 
		 * @param inspectorName
		 */
		public void setInspectorName(String inspectorName) {
			AutomationHelper.printMethodName();
			AutomationHelper.setTextField(inspectorNameInput, inspectorName);
		}

		@FindBy(xpath = "//div[starts-with(@class, 'inspection-question')]//label[text() = 'Phase of Construction']//parent::div//input")
		WebElement phaseOfConstructionInput;

		/**
		 * Returns the value currently in the <b>Phase of Construction</b> field
		 * 
		 * @return String
		 */
		public String readPhaseOfConstruction() {
			AutomationHelper.printMethodName();
			return AutomationHelper.getText(phaseOfConstructionInput);
		}

		/**
		 * Sets the <b>Phase of Construction</b> field with the passed in text.
		 * 
		 * @param phaseOfConstruction
		 */
		public void setPhaseOfConstruction(String phaseOfConstruction) {
			AutomationHelper.printMethodName();
			AutomationHelper.setTextField(phaseOfConstructionInput, phaseOfConstruction);
		}

		@FindBy(xpath = "//div[starts-with(@class, 'inspection-question')]//label[text() = 'Municipality']//parent::div//input")
		WebElement municipalityInput;

		/**
		 * Returns the value currently in the <b>Municipality</b> field
		 * 
		 * @return String
		 */
		public String readMunicipality() {
			AutomationHelper.printMethodName();
			return AutomationHelper.getText(municipalityInput);
		}

		/**
		 * Sets the <b>Municipality</b> field with the passed in text.
		 * 
		 * @param municipality
		 */
		public void setMunicipality(String municipality) {
			AutomationHelper.printMethodName();
			AutomationHelper.setTextField(municipalityInput, municipality);
		}

		@FindBy(xpath = "//div[starts-with(@class, 'inspection-question')]//label[text() = 'Date of Inspection']//parent::div//input")
		WebElement dateOfInspectionDatepicker;

		/**
		 * Returns the value currently in the <b>Date of Inspection</b> field
		 * 
		 * @return String
		 */
		public String readDateOfInspection() {
			AutomationHelper.printMethodName();
			return AutomationHelper.getText(dateOfInspectionDatepicker);
		}

		/**
		 * Sets the <b>Date of Inspection</b> field with the passed in text.
		 * 
		 * @param dateStarted
		 */
		public void setDateOfInspection(String dateStarted) {
			AutomationHelper.printMethodName();
			AutomationHelper.setCalendarDate(dateOfInspectionDatepicker,
					AutomationHelper.generateCalendarDate(dateStarted));
		}

		@FindBy(xpath = "//div[starts-with(@class, 'inspection-question')]//label[contains (text(), 'Reason for Inspection')]//parent::div//select")
		WebElement reasonForInspectionDropdown;

		/**
		 * Returns the value currently in the <b>Reason for Inspection</b> field
		 * 
		 * @return String
		 */
		public String readReasonForInspection() {
			AutomationHelper.printMethodName();
			return AutomationHelper.readSelectedSubItem(reasonForInspectionDropdown);
		}

		/**
		 * Selects the <b>Reason for Inspection</b> drop down with the passed in text.
		 * 
		 * @param reasonForInspection
		 */
		public void selectReasonForInspection(String reasonForInspection) {
			AutomationHelper.printMethodName();
			AutomationHelper.selectDropdownItem(this.reasonForInspectionDropdown, reasonForInspection);
		}

		@FindBy(xpath = "//div[starts-with(@class, 'inspection-question')]//label[text() = 'Existing Weather Conditions']//parent::div//input")
		WebElement existingWeatherConditionsInput;

		/**
		 * Returns the value currently in the <b>Existing Weather Conditions</b> field
		 * 
		 * @return String
		 */
		public String readExistingWeatherConditions() {
			AutomationHelper.printMethodName();
			return AutomationHelper.getText(existingWeatherConditionsInput);
		}

		/**
		 * Sets the <b>Existing Weather Conditions</b> field with the passed in text.
		 * 
		 * @param existingWeatherConditions
		 */
		public void setExistingWeatherConditions(String existingWeatherConditions) {
			AutomationHelper.printMethodName();
			AutomationHelper.setTextField(existingWeatherConditionsInput, existingWeatherConditions);
		}

		@FindBy(xpath = "//div[starts-with(@class, 'inspection-question')]//label[text() = 'Estimated Acreage of Disturbance']//parent::div//input")
		WebElement estimatedAcreageOfDisturbanceInput;

		/**
		 * Returns the value currently in the <b>Estimated Acreage of Disturbance</b>
		 * field
		 * 
		 * @return String
		 */
		public String readEstimatedAcerageOfDisturbance() {
			AutomationHelper.printMethodName();
			return AutomationHelper.getText(estimatedAcreageOfDisturbanceInput);
		}

		/**
		 * Sets the <b>Estimated Acreage of Disturbance</b> field with the passed in
		 * text.
		 * 
		 * @param estimatedAcerageOfDisturbance
		 */
		public void setEstimatedAcerageOfDisturbance(String estimatedAcerageOfDisturbance) {
			AutomationHelper.printMethodName();
			try {
				Double.valueOf(estimatedAcerageOfDisturbance);
			} catch (NumberFormatException ex) {
				System.out.println(
						"String for setting the Estimated Acreage of Disturbance isn't parsable to double, which means the field will not set.");
			}

			AutomationHelper.setTextField(estimatedAcreageOfDisturbanceInput, estimatedAcerageOfDisturbance);
		}

		@FindBy(xpath = "//div[starts-with(@class, 'inspection-question')]//label[text() = 'Storm Event']//parent::div//input")
		WebElement stormEventInput;

		/**
		 * Returns the value currently in the <b>Storm Event</b> field
		 * 
		 * @return String
		 */
		public String readStormEvent() {
			AutomationHelper.printMethodName();
			return AutomationHelper.getText(stormEventInput);
		}

		/**
		 * Sets the <b>Storm Event</b> field with the passed in text.
		 * 
		 * @param stormEvent
		 */
		public void setStormEvent(String stormEvent) {
			AutomationHelper.printMethodName();
			AutomationHelper.setTextField(stormEventInput, stormEvent);
		}

		@FindBy(xpath = "//div[starts-with(@class, 'inspection-question')]//label[text() = 'Date Began']//parent::div//input")
		WebElement dateBeganDatepicker;

		/**
		 * Returns the value currently in the <b>Date Began</b> field
		 * 
		 * @return String
		 */
		public String readDateBegan() {
			AutomationHelper.printMethodName();
			return AutomationHelper.getText(dateBeganDatepicker);
		}

		/**
		 * Sets the <b>Date Began</b> field with the passed in text.
		 * 
		 * @param dateBegan
		 */
		public void setDateBegan(String dateBegan) {
			AutomationHelper.printMethodName();
			AutomationHelper.setCalendarDate(dateBeganDatepicker, AutomationHelper.generateCalendarDate(dateBegan));
		}

		@FindBy(xpath = "//div[starts-with(@class, 'inspection-question')]//label[text() = 'Duration']//parent::div//input")
		WebElement durationInput;

		/**
		 * Returns the value currently in the <b>Duration</b> field
		 * 
		 * @return String
		 */
		public String readDuration() {
			AutomationHelper.printMethodName();
			return AutomationHelper.getText(durationInput);
		}

		/**
		 * Sets the <b>Duration</b> field with the passed in text.
		 * 
		 * @param duration
		 */
		public void setDuration(String duration) {
			AutomationHelper.printMethodName();
			try {
				Double.valueOf(duration);
			} catch (NumberFormatException ex) {
				System.out.println(
						"String for setting the Duration isn't parsable to double, which means the field will not set.");
			}

			AutomationHelper.setTextField(durationInput, duration);
		}

		@FindBy(xpath = "//div[starts-with(@class, 'inspection-question')]//label[text() = 'Amount']//parent::div//input")
		WebElement amountInput;

		/**
		 * Returns the value currently in the <b>Amount</b> field
		 * 
		 * @return String
		 */
		public String readAmount() {
			AutomationHelper.printMethodName();
			return AutomationHelper.getText(amountInput);
		}

		/**
		 * Sets the <b>Amount</b> field with the passed in text.
		 * 
		 * @param amount
		 */
		public void setAmount(String amount) {
			AutomationHelper.printMethodName();
			try {
				Double.valueOf(amount);
			} catch (NumberFormatException ex) {
				System.out.println(
						"String for setting the Amount isn't parsable to double, which means the field will not set.");
			}

			AutomationHelper.setTextField(amountInput, amount);
		}

		/**
		 * Returns a reference to the SWMP Information class. This class is simply meant
		 * to organize methods.
		 * 
		 * @return SWMPInformation page
		 */
		public SWMPInformation getSWMPInformation() {
			return new SWMPInformation();
		}

		/**
		 * Nested (three deep) class to contain methods that interact with SWMP
		 * Information section radio buttons and comments
		 * 
		 * @author Jesse Childress
		 *
		 */
		public class SWMPInformation extends InspectionQuestionsPageFactory {

			String doesCopyOfSWMPExist = "Does a copy of the SWMP and accompanying sediment and erosion control drawings exist at the facility site?";

			/**
			 * Method to interact with the different Radio Buttons for the question <b>Does
			 * a copy of the SWMP and accompanying sediment and erosion control drawings
			 * exist at the facility site?</b>.
			 * 
			 * @param radioButtonLabel - Yes, No, or N/A
			 */
			public void clickDoesCopyOfSWMPExistRadioButton(String radioButtonLabel) {
				AutomationHelper.printMethodName();
				// Ensure that check box name is valid
				validateRadioButtonName(radioButtonLabel);
				clickRadioButton(doesCopyOfSWMPExist, radioButtonLabel);

			}

			/**
			 * Checks to see if the Radio Button for the question <b>Does a copy of the SWMP
			 * and accompanying sediment and erosion control drawings exist at the facility
			 * site?</b> is checked or not. It will check the radioButtonLabel that you pass
			 * in, e.g. Yes, No, or N/A
			 * 
			 * @param radioButtonLabel - Yes, No, or N/A
			 */
			public boolean isDoesCopyOfSWMPExistRadioButtonClicked(String radioButtonLabel) {
				AutomationHelper.printMethodName();
				// Ensure that check box name is valid
				validateRadioButtonName(radioButtonLabel);
				return getRadioButton(doesCopyOfSWMPExist, radioButtonLabel).isSelected();
			}

			String dischargePermitAndAck = "Is the discharge permit & acknowledgement letter at the facility site?";

			/**
			 * Method to interact with the different Radio Buttons for the question <b>Is
			 * the discharge permit & acknowledgement letter at the facility site?</b>.
			 * 
			 * @param radioButtonLabel - Yes, No, or N/A
			 */
			public void clickDischargePermitAndAckRadioButton(String radioButtonLabel) {
				AutomationHelper.printMethodName();
				// Ensure that check box name is valid
				validateRadioButtonName(radioButtonLabel);
				clickRadioButton(dischargePermitAndAck, radioButtonLabel);
			}

			/**
			 * Checks to see if the Radio Button for the question <b>Is the discharge permit
			 * & acknowledgement letter at the facility site?</b> is checked or not. It will
			 * check the radioButtonLabel that you pass in, e.g. Yes, No, or N/A
			 * 
			 * @param radioButtonLabel - Yes, No, or N/A
			 */
			public boolean isDischargePermitAndAckRadioButtonClicked(String radioButtonLabel) {
				AutomationHelper.printMethodName();
				// Ensure that check box name is valid
				validateRadioButtonName(radioButtonLabel);
				return getRadioButton(dischargePermitAndAck, radioButtonLabel).isSelected();
			}

			String areSWMPDrawingsUpdated = "Are the SWMP and/or accompanying sediment and erosion control drawings updated and documented?";

			/**
			 * Method to interact with the different Radio Buttons for the question <b>Are
			 * the SWMP and/or accompanying sediment and erosion control drawings updated
			 * and documented?</b>.
			 * 
			 * @param radioButtonLabel - Yes, No, or N/A
			 */
			public void clickAreSWMPDrawingsUpdatedRadioButton(String radioButtonLabel) {
				AutomationHelper.printMethodName();
				// Ensure that check box name is valid
				validateRadioButtonName(radioButtonLabel);
				clickRadioButton(areSWMPDrawingsUpdated, radioButtonLabel);
			}

			/**
			 * Checks to see if the Radio Button for the question <b>Are the SWMP and/or
			 * accompanying sediment and erosion control drawings updated and
			 * documented?</b> is checked or not. It will check the radioButtonLabel that
			 * you pass in, e.g. Yes, No, or N/A
			 * 
			 * @param radioButtonLabel - Yes, No, or N/A
			 */
			public boolean isAreSWMPDrawingsUpdatedRadioButtonClicked(String radioButtonLabel) {
				AutomationHelper.printMethodName();
				// Ensure that check box name is valid
				validateRadioButtonName(radioButtonLabel);
				return getRadioButton(areSWMPDrawingsUpdated, radioButtonLabel).isSelected();
			}

			String doInspectionRecordsExistAtFacility = "Do inspection records exist at the facility site?";

			/**
			 * Method to interact with the different Radio Buttons for the question <b>Do
			 * inspection records exist at the facility site?</b>.
			 * 
			 * @param radioButtonLabel - Yes, No, or N/A
			 */
			public void clickDoInspectionRecordsExistAtFacilityRadioButton(String radioButtonLabel) {
				AutomationHelper.printMethodName();
				// Ensure that check box name is valid
				validateRadioButtonName(radioButtonLabel);
				clickRadioButton(doInspectionRecordsExistAtFacility, radioButtonLabel);
			}

			/**
			 * Checks to see if the Radio Button for the question <b>Do inspection records
			 * exist at the facility site?</b> is checked or not. It will check the
			 * radioButtonLabel that you pass in, e.g. Yes, No, or N/A
			 * 
			 * @param radioButtonLabel - Yes, No, or N/A
			 */
			public boolean isDoInspectionRecordsExistAtFacilityRadioButtonClicked(String radioButtonLabel) {
				AutomationHelper.printMethodName();
				// Ensure that check box name is valid
				validateRadioButtonName(radioButtonLabel);
				return getRadioButton(doInspectionRecordsExistAtFacility, radioButtonLabel).isSelected();
			}

			String haveThereBeenDeviationsInSWMP = "Have there been any deviations from the minimum inspection frequency as specified in the SWMP?";

			/**
			 * Method to interact with the different Radio Buttons for the question <b>Have
			 * there been any deviations from the minimum inspection frequency as specified
			 * in the SWMP?</b>.
			 * 
			 * @param radioButtonLabel - Yes, No, or N/A
			 */
			public void clickHaveThereBeenDeviationsInSWMPRadioButton(String radioButtonLabel) {
				AutomationHelper.printMethodName();
				// Ensure that check box name is valid
				validateRadioButtonName(radioButtonLabel);
				clickRadioButton(haveThereBeenDeviationsInSWMP, radioButtonLabel);
			}

			/**
			 * Checks to see if the Radio Button for the question <b>Have there been any
			 * deviations from the minimum inspection frequency as specified in the
			 * SWMP?</b> is checked or not. It will check the radioButtonLabel that you pass
			 * in, e.g. Yes, No, or N/A
			 * 
			 * @param radioButtonLabel - Yes, No, or N/A
			 */
			public boolean isHaveThereBeenDeviationsInSWMPRadioButtonClicked(String radioButtonLabel) {
				AutomationHelper.printMethodName();
				// Ensure that check box name is valid
				validateRadioButtonName(radioButtonLabel);
				return getRadioButton(haveThereBeenDeviationsInSWMP, radioButtonLabel).isSelected();
			}
		}

		/**
		 * Returns a reference to the InspectionScope class. This class is simply meant
		 * to organize methods.
		 * 
		 * @return InspectionScope page
		 */
		public InspectionScope getInspectionScope() {
			return new InspectionScope();
		}

		/**
		 * Nested (three deep) class to contain methods that interact with Inspection
		 * Scope section check boxes and comments
		 * 
		 * @author Jesse Childress
		 *
		 */
		public class InspectionScope extends InspectionQuestionsPageFactory {

			String areasToBeInspected = "Areas to be inspected: When conducting a site inspection, the following areas, if applicable, must be inspected for evidence of, or potential for, pollutants leaving the construction site boundaries, entering the stormwater drainage system or discharging to state waters.";

			/**
			 * Method to interact with the different Radio Buttons for the question <b>Areas
			 * to be inspected: When conducting a site inspection, the following areas, if
			 * applicable, must be inspected for evidence of, or potential for, pollutants
			 * leaving the construction site boundaries, entering the stormwater drainage
			 * system or discharging to state waters.</b>.
			 * 
			 * @param radioButtonLabel - Yes, No, or N/A
			 */
			public void clickAreasToBeInspectedRadioButton(String radioButtonLabel) {
				AutomationHelper.printMethodName();
				// Ensure that check box name is valid
				validateRadioButtonName(radioButtonLabel);
				clickRadioButton(areasToBeInspected, radioButtonLabel);
			}

			/**
			 * Checks to see if the Radio Button for the question <b>Areas to be inspected:
			 * When conducting a site inspection, the following areas, if applicable, must
			 * be inspected for evidence of, or potential for, pollutants leaving the
			 * construction site boundaries, entering the stormwater drainage system or
			 * discharging to state waters.</b> is checked or not. It will check the
			 * radioButtonLabel that you pass in, e.g. Yes, No, or N/A
			 * 
			 * @param radioButtonLabel - Yes, No, or N/A
			 */
			public boolean isAreasToBeInspectedRadioButtonClicked(String radioButtonLabel) {
				AutomationHelper.printMethodName();
				// Ensure that check box name is valid
				validateRadioButtonName(radioButtonLabel);
				return getRadioButton(areasToBeInspected, radioButtonLabel).isSelected();
			}

			String constructionSitePerimeter = "Construction site perimeter;";

			/**
			 * Method to interact with the different Radio Buttons for the question
			 * <b>Construction site perimeter;</b>.
			 * 
			 * @param radioButtonLabel - Yes, No, or N/A
			 */
			public void clickConstructionSitePerimeterRadioButton(String radioButtonLabel) {
				AutomationHelper.printMethodName();
				// Ensure that check box name is valid
				validateRadioButtonName(radioButtonLabel);
				clickRadioButton(constructionSitePerimeter, radioButtonLabel);
			}

			/**
			 * Checks to see if the Radio Button for the question <b>Construction site
			 * perimeter;</b> is clicked or not. It will check the radioButtonLabel that you
			 * pass in, e.g. Yes, No, or N/A
			 * 
			 * @param radioButtonLabel - Yes, No, or N/A
			 */
			public boolean isConstructionSitePerimeterRadioButtonClicked(String radioButtonLabel) {
				AutomationHelper.printMethodName();
				// Ensure that check box name is valid
				validateRadioButtonName(radioButtonLabel);
				return getRadioButton(constructionSitePerimeter, radioButtonLabel).isSelected();
			}

			String allDisturbedAreas = "All disturbed areas;";

			/**
			 * Method to interact with the different Radio Buttons for the question <b>All
			 * disturbed areas;</b>.
			 * 
			 * @param radioButtonLabel - Yes, No, or N/A
			 */
			public void clickAllDisturbedAreasRadioButton(String radioButtonLabel) {
				AutomationHelper.printMethodName();
				// Ensure that check box name is valid
				validateRadioButtonName(radioButtonLabel);
				clickRadioButton(allDisturbedAreas, radioButtonLabel);
			}

			/**
			 * Checks to see if the Radio Button for the question <b>All disturbed
			 * areas;</b> is clicked or not. It will check the radioButtonLabel that you
			 * pass in, e.g. Yes, No, or N/A
			 * 
			 * @param radioButtonLabel - Yes, No, or N/A
			 */
			public boolean isAllDisturbedAreasRadioButtonClicked(String radioButtonLabel) {
				AutomationHelper.printMethodName();
				// Ensure that check box name is valid
				validateRadioButtonName(radioButtonLabel);
				return getRadioButton(allDisturbedAreas, radioButtonLabel).isSelected();
			}

			String allSedimentAndErosionPractices = "All implemented sediment and erosion control practices;";

			/**
			 * Method to interact with the different Radio Buttons for the question <b>All
			 * implemented sediment and erosion control practices;</b>.
			 * 
			 * @param radioButtonLabel - Yes, No, or N/A
			 */
			public void clickAllSedimentAndErosionPracticesRadioButton(String radioButtonLabel) {
				AutomationHelper.printMethodName();
				// Ensure that check box name is valid
				validateRadioButtonName(radioButtonLabel);
				clickRadioButton(allSedimentAndErosionPractices, radioButtonLabel);
			}

			/**
			 * Checks to see if the Radio Button for the question <b>All implemented
			 * sediment and erosion control practices;</b> is clicked or not. It will check
			 * the radioButtonLabel that you pass in, e.g. Yes, No, or N/A
			 * 
			 * @param radioButtonLabel - Yes, No, or N/A
			 */
			public boolean isAllSedimentAndErosionPracticesRadioButtonClicked(String radioButtonLabel) {
				AutomationHelper.printMethodName();
				// Ensure that check box name is valid
				validateRadioButtonName(radioButtonLabel);
				return getRadioButton(allSedimentAndErosionPractices, radioButtonLabel).isSelected();
			}

			String designatedHaulRoutes = "Designated haul routes;";

			/**
			 * Method to interact with the different Radio Buttons for the question
			 * <b>Designated Haul Routes</b>.
			 * 
			 * @param radioButtonLabel - Yes, No, or N/A
			 */
			public void clickDesignatedHaulRoutesRadioButton(String radioButtonLabel) {
				AutomationHelper.printMethodName();
				// Ensure that check box name is valid
				validateRadioButtonName(radioButtonLabel);
				clickRadioButton(designatedHaulRoutes, radioButtonLabel);
			}

			/**
			 * Checks to see if the Radio Button for the question <b>Designated Haul
			 * Routes</b> is clicked or not. It will check the radioButtonLabel that you
			 * pass in, e.g. Yes, No, or N/A
			 * 
			 * @param radioButtonLabel - Yes, No, or N/A
			 */
			public boolean isDesignatedHaulRoutesRadioButtonClicked(String radioButtonLabel) {
				AutomationHelper.printMethodName();
				// Ensure that check box name is valid
				validateRadioButtonName(radioButtonLabel);
				return getRadioButton(designatedHaulRoutes, radioButtonLabel).isSelected();
			}

			String materialsAndWasteStorageAreas = "Materials and waste storage areas exposed to precipitation;";

			/**
			 * Method to interact with the different Radio Buttons for the question
			 * <b>Materials and waste storage areas exposed to precipitation;</b>.
			 * 
			 * @param radioButtonLabel - Yes, No, or N/A
			 */
			public void clickMaterialsAndWasteStorageAreasRadioButton(String radioButtonLabel) {
				AutomationHelper.printMethodName();
				// Ensure that check box name is valid
				validateRadioButtonName(radioButtonLabel);
				clickRadioButton(materialsAndWasteStorageAreas, radioButtonLabel);
			}

			/**
			 * Checks to see if the Radio Button for the question <b>Materials and waste
			 * storage areas exposed to precipitation;</b> is clicked or not. It will check
			 * the radioButtonLabel that you pass in, e.g. Yes, No, or N/A
			 * 
			 * @param radioButtonLabel - Yes, No, or N/A
			 */
			public boolean isMaterialsAndWasteStorageAreasRadioButtonClicked(String radioButtonLabel) {
				AutomationHelper.printMethodName();
				// Ensure that check box name is valid
				validateRadioButtonName(radioButtonLabel);
				return getRadioButton(materialsAndWasteStorageAreas, radioButtonLabel).isSelected();
			}

			String locationsWhereStormwaterDischarge = "Locations where stormwater has the potential to discharge offsite;";

			/**
			 * Method to interact with the different Radio Buttons for the question
			 * <b>Locations where stormwater has the potential to discharge offsite;</b>.
			 * 
			 * @param radioButtonLabel - Yes, No, or N/A
			 */
			public void clickLocationsWhereStormwaterDischargeRadioButton(String radioButtonLabel) {
				AutomationHelper.printMethodName();
				// Ensure that check box name is valid
				validateRadioButtonName(radioButtonLabel);
				clickRadioButton(locationsWhereStormwaterDischarge, radioButtonLabel);
			}

			/**
			 * Checks to see if the Radio Button for the question <b>Locations where
			 * stormwater has the potential to discharge offsite;</b> is clicked or not. It
			 * will check the radioButtonLabel that you pass in, e.g. Yes, No, or N/A
			 * 
			 * @param radioButtonLabel - Yes, No, or N/A
			 */
			public boolean isLocationsWhereStormwaterDischargeRadioButtonClicked(String radioButtonLabel) {
				AutomationHelper.printMethodName();
				// Ensure that check box name is valid
				validateRadioButtonName(radioButtonLabel);
				return getRadioButton(locationsWhereStormwaterDischarge, radioButtonLabel).isSelected();
			}

			String locationsWhereVehiclesExit = "Locations where vehicles exit the site;";

			/**
			 * Method to interact with the different Radio Buttons for the question
			 * <b>Locations where vehicles exit the site;</b>.
			 * 
			 * @param radioButtonLabel - Yes, No, or N/A
			 */
			public void clickLocationsWhereVehiclesExitRadioButton(String radioButtonLabel) {
				AutomationHelper.printMethodName();
				// Ensure that check box name is valid
				validateRadioButtonName(radioButtonLabel);
				clickRadioButton(locationsWhereVehiclesExit, radioButtonLabel);
			}

			/**
			 * Checks to see if the Radio Button for the question <b>Locations where
			 * vehicles exit the site;</b> is clicked or not. It will check the
			 * radioButtonLabel that you pass in, e.g. Yes, No, or N/A
			 * 
			 * @param radioButtonLabel - Yes, No, or N/A
			 */
			public boolean isLocationsWhereVehiclesExitRadioButtonClicked(String radioButtonLabel) {
				AutomationHelper.printMethodName();
				// Ensure that check box name is valid
				validateRadioButtonName(radioButtonLabel);
				return getRadioButton(locationsWhereVehiclesExit, radioButtonLabel).isSelected();
			}

			String allInspectionsFollowed = "Have all inspection requirements been followed in accordance with part Part 1.D.5.b.i-iv of The General Permit COR400000?";

			/**
			 * Method to interact with the different Radio Buttons for the question <b>Have
			 * all inspection requirements been followed in accordance with part Part
			 * 1.D.5.b.i-iv of The General Permit COR400000?</b>.
			 * 
			 * @param radioButtonLabel - Yes, No, or N/A
			 */
			public void clickAllInspectionsFollowedPermitRadioButton(String radioButtonLabel) {
				AutomationHelper.printMethodName();
				// Ensure that check box name is valid
				validateRadioButtonName(radioButtonLabel);
				clickRadioButton(allInspectionsFollowed, radioButtonLabel);
			}

			/**
			 * Checks to see if the Radio Button for the question <b>Have all inspection
			 * requirements been followed in accordance with part Part 1.D.5.b.i-iv of The
			 * General Permit COR400000?</b> is clicked or not. It will check the
			 * radioButtonLabel that you pass in, e.g. Yes, No, or N/A
			 * 
			 * @param radioButtonLabel - Yes, No, or N/A
			 */
			public boolean isAllInspectionsFollowedPermitRadioButtonClicked(String radioButtonLabel) {
				AutomationHelper.printMethodName();
				// Ensure that check box name is valid
				validateRadioButtonName(radioButtonLabel);
				return getRadioButton(allInspectionsFollowed, radioButtonLabel).isSelected();
			}

		}

		/**
		 * Returns a reference to the InspectionReports class. This class is simply
		 * meant to organize methods.
		 * 
		 * @return InspectionReports page
		 */
		public InspectionReports getInspectionReports() {
			return new InspectionReports();
		}

		/**
		 * Nested (three deep) class to contain methods that interact with Inspection
		 * Reports section check boxes and comments
		 * 
		 * @author Jesse Childress
		 *
		 */
		public class InspectionReports extends InspectionQuestionsPageFactory {

			String evidenceOfFloatingMaterials = "Is there evidence of the presence of floating materials, visible oil sheen, discoloration, turbidity, odor, etc. in the storm water discharge(s)?";

			/**
			 * Method to interact with the different Radio Buttons for the question <b>Is
			 * there evidence of the presence of floating materials, visible oil sheen,
			 * discoloration, turbidity, odor, etc. in the storm water discharge(s)?</b>.
			 * 
			 * @param radioButtonLabel - Yes, No, or N/A
			 */
			public void clickEvidenceOfFloatingMaterialsRadioButton(String radioButtonLabel) {
				AutomationHelper.printMethodName();
				// Ensure that check box name is valid
				validateRadioButtonName(radioButtonLabel);
				clickRadioButton(evidenceOfFloatingMaterials, radioButtonLabel);
			}

			/**
			 * Checks to see if the Radio Button for the question <b>Is there evidence of
			 * the presence of floating materials, visible oil sheen, discoloration,
			 * turbidity, odor, etc. in the storm water discharge(s)?</b> is clicked or not.
			 * It will check the radioButtonLabel that you pass in, e.g. Yes, No, or N/A
			 * 
			 * @param radioButtonLabel - Yes, No, or N/A
			 */
			public boolean isEvidenceOfFloatingMaterialsRadioButtonClicked(String radioButtonLabel) {
				AutomationHelper.printMethodName();
				// Ensure that check box name is valid
				validateRadioButtonName(radioButtonLabel);
				return getRadioButton(evidenceOfFloatingMaterials, radioButtonLabel).isSelected();
			}

			String evidenceOfIllicitDischarges = "Is there evidence of illicit discharges of sediment or other non-permitted pollutants from the site? If yes, provide location and notify the SWMP manager immediately.";

			/**
			 * Method to interact with the different Radio Buttons for the question <b>Is
			 * there evidence of illicit discharges of sediment or other non-permitted
			 * pollutants from the site? If yes, provide location and notify the SWMP
			 * manager immediately.</b>.
			 * 
			 * @param radioButtonLabel - Yes, No, or N/A
			 */
			public void clickEvidenceOfIllicitDischargesRadioButton(String radioButtonLabel) {
				AutomationHelper.printMethodName();
				// Ensure that check box name is valid
				validateRadioButtonName(radioButtonLabel);
				clickRadioButton(evidenceOfIllicitDischarges, radioButtonLabel);
			}

			/**
			 * Checks to see if the Radio Button for the question <b>Is there evidence of
			 * illicit discharges of sediment or other non-permitted pollutants from the
			 * site? If yes, provide location and notify the SWMP manager immediately.</b>
			 * is clicked or not. It will check the radioButtonLabel that you pass in, e.g.
			 * Yes, No, or N/A
			 * 
			 * @param radioButtonLabel - Yes, No, or N/A
			 */
			public boolean isEvidenceOfIllicitDischargesRadioButtonClicked(String radioButtonLabel) {
				AutomationHelper.printMethodName();
				// Ensure that check box name is valid
				validateRadioButtonName(radioButtonLabel);
				return getRadioButton(evidenceOfIllicitDischarges, radioButtonLabel).isSelected();
			}

			String locationsOfControlMeasuresMaintenance = "Are there locations of control measures needing maintenance?";

			/**
			 * Method to interact with the different Radio Buttons for the question <b>Are
			 * there locations of control measures needing maintenance?</b>.
			 * 
			 * @param radioButtonLabel - Yes, No, or N/A
			 */
			public void clickLocationsOfControlMeasuresMaintenanceRadioButton(String radioButtonLabel) {
				AutomationHelper.printMethodName();
				// Ensure that check box name is valid
				validateRadioButtonName(radioButtonLabel);
				clickRadioButton(locationsOfControlMeasuresMaintenance, radioButtonLabel);
			}

			/**
			 * Checks to see if the Radio Button for the question <b>Are there locations of
			 * control measures needing maintenance?</b> is clicked or not. It will check
			 * the radioButtonLabel that you pass in, e.g. Yes, No, or N/A
			 * 
			 * @param radioButtonLabel - Yes, No, or N/A
			 */
			public boolean isLocationsOfControlMeasuresMaintenanceRadioButtonClicked(String radioButtonLabel) {
				AutomationHelper.printMethodName();
				// Ensure that check box name is valid
				validateRadioButtonName(radioButtonLabel);
				return getRadioButton(locationsOfControlMeasuresMaintenance, radioButtonLabel).isSelected();
			}

			String locationsOfControlMeasuresInadequate = "Are there locations where control measures are identified as inadequate?";

			/**
			 * Method to interact with the different Radio Buttons for the question <b>Are
			 * there locations where control measures are identified as inadequate?</b>.
			 * 
			 * @param radioButtonLabel - Yes, No, or N/A
			 */
			public void clickLocationsOfControlMeasuresInadequateRadioButton(String radioButtonLabel) {
				AutomationHelper.printMethodName();
				// Ensure that check box name is valid
				validateRadioButtonName(radioButtonLabel);
				clickRadioButton(locationsOfControlMeasuresInadequate, radioButtonLabel);
			}

			/**
			 * Checks to see if the Radio Button for the question <b>Are there locations
			 * where control measures are identified as inadequate?</b> is clicked or not.
			 * It will check the radioButtonLabel that you pass in, e.g. Yes, No, or N/A
			 * 
			 * @param radioButtonLabel - Yes, No, or N/A
			 */
			public boolean isLocationsOfControlMeasuresInadequateRadioButtonClicked(String radioButtonLabel) {
				AutomationHelper.printMethodName();
				// Ensure that check box name is valid
				validateRadioButtonName(radioButtonLabel);
				return getRadioButton(locationsOfControlMeasuresInadequate, radioButtonLabel).isSelected();
			}

			String locationsAdditionalControlMeasuresNeeded = "Are there locations where additional control measures are needed that were not in place at the time of the inspection?";

			/**
			 * Method to interact with the different Radio Buttons for the question <b>Are
			 * there locations where additional control measures are needed that were not in
			 * place at the time of the inspection?</b>.
			 * 
			 * @param radioButtonLabel - Yes, No, or N/A
			 */
			public void clickLocationsAdditionalControlMeasuresNeededRadioButton(String radioButtonLabel) {
				AutomationHelper.printMethodName();
				// Ensure that check box name is valid
				validateRadioButtonName(radioButtonLabel);
				clickRadioButton(locationsAdditionalControlMeasuresNeeded, radioButtonLabel);
			}

			/**
			 * Checks to see if the Radio Button for the question <b>Are there locations
			 * where additional control measures are needed that were not in place at the
			 * time of the inspection?</b> is clicked or not. It will check the
			 * radioButtonLabel that you pass in, e.g. Yes, No, or N/A
			 * 
			 * @param radioButtonLabel - Yes, No, or N/A
			 */
			public boolean isLocationsAdditionalControlMeasuresNeededRadioButtonClicked(String radioButtonLabel) {
				AutomationHelper.printMethodName();
				// Ensure that check box name is valid
				validateRadioButtonName(radioButtonLabel);
				return getRadioButton(locationsAdditionalControlMeasuresNeeded, radioButtonLabel).isSelected();
			}

		}

		/**
		 * Utility method to ensure that the Radio Button text/Label is either Yes, No,
		 * or N/A. Case matters.
		 * 
		 * @param radioButtonLabel
		 */
		private void validateRadioButtonName(String radioButtonLabel) {
			switch (radioButtonLabel) {
			case "Yes":
				break;
			case "No":
				break;
			case "N/A":
				break;
			default:
				throw new ElementNotSelectableException("There is no such thing as a radioButton name of "
						+ radioButtonLabel + ". Check text and case and try again.");
			}
		}

		private void clickRadioButton(String question, String yesNoOrNA) {

			WebElement radioButton = getRadioButton(question, yesNoOrNA);
			try {
				radioButton.click();
			} catch (ElementClickInterceptedException e) {
//				Actions actions = new Actions(driver);
				Reporter.log("Had to move down to click.", true);
//				actions.moveToElement(radioButton).click().perform();
//				actions.moveToElement(radioButton).perform();
//				radioButton.click();

				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", radioButton);
				radioButton.click();
			}

		}

		private WebElement getRadioButton(String question, String yesNoOrNA) {

			// The xpath is written in such a way as to handle the questions in spans and
			// labels. The spans exist where there is a hyphen in front of the question
			String xpath = "//label[text()='" + question + "']//ancestor::tr//span[text()='" + yesNoOrNA
					+ "']//preceding-sibling::input | //span[text()='" + question + "']//ancestor::tr//span[text()='"
					+ yesNoOrNA + "']//preceding-sibling::input";
			WebElement radioButton = driver.findElement(By.xpath(xpath));

			return radioButton;

		}

	}

	/**
	 * Getter method to return a class type of <b>SQAQuestionGroup1</b>, giving
	 * access to all methods within.
	 * 
	 * @return SQAQuestionGroup1
	 */
	public SQAQuestionGroup1 getSQAQuestionGroup1() {
		return new SQAQuestionGroup1();
	}

	/**
	 * Nested class containing methods for <b>SQA Question Group 1</b>
	 * 
	 * @author Jesse Childress
	 *
	 */
	public class SQAQuestionGroup1 extends InspectionQuestionsPageFactory {

		@FindBy(xpath = "//h3[text()='SQA Question Group 1']//ancestor::table")
		WebElement questionGroupContainer;

		WebElement myDateInput = questionGroupContainer
				.findElement(By.xpath(".//span[text() = 'My Date']//ancestor::tr//input"));

		/**
		 * Returns the text in the <b>My Date</b> field.
		 * 
		 * @return String
		 */
		public String readMyDate() {
			AutomationHelper.printMethodName();
			return AutomationHelper.getText(myDateInput);
		}

		/**
		 * Sets a date for the <b>My Date</b> field.
		 * 
		 * @param myDate
		 */
		public void setMyDate(String myDate) {
			AutomationHelper.printMethodName();
			AutomationHelper.setCalendarDate(myDateInput, AutomationHelper.generateCalendarDate(myDate));
		}

		WebElement mySelectOne = questionGroupContainer
				.findElement(By.xpath(".//span[text() = 'My Select One']//ancestor::tr//select"));

		/**
		 * Method to read the <b>My Select One</b> field
		 * 
		 * @return String
		 */
		public String readMySelectOne() {
			AutomationHelper.printMethodName();
			return AutomationHelper.readSelectedSubItem(mySelectOne);
		}

		/**
		 * Method to select the <b>My Select One</b> value. <br>
		 * Note: Valid values are:<br>
		 * <li>Routine
		 * <li>Routine + Post Storm
		 * <li>Post Storm
		 * <li>Final
		 * 
		 * @param value
		 */
		public void selectMySelectOne(String value) {
			AutomationHelper.printMethodName();
			AutomationHelper.selectDropdownItem(mySelectOne, value);
		}

	}

	/**
	 * Getter method to return a class type of <b>SQAQuestionGroup2</b>, giving
	 * access to all methods within.
	 * 
	 * @return SQAQuestionGroup2
	 */
	public SQAQuestionGroup2 getSQAQuestionGroup2() {
		return new SQAQuestionGroup2();
	}

	/**
	 * Nested class containing methods for <b>SQA Question Group 2</b>
	 * 
	 * @author Jesse Childress
	 *
	 */
	public class SQAQuestionGroup2 extends InspectionQuestionsPageFactory {

		@FindBy(xpath = "//h3[text()='SQA Question Group 2']//ancestor::table")
		WebElement questionGroupContainer;

		WebElement myCheckbox = questionGroupContainer
				.findElement(By.xpath(".//span[text() = 'My Checkbox']//ancestor::tr//input"));

		/**
		 * Method to read the selected value of the <b>My Checkbox</b> checkbox.
		 * 
		 * @return boolean
		 */
		public boolean readMyCheckbox() {
			AutomationHelper.printMethodName();
			return myCheckbox.isSelected();
		}

		/**
		 * Method to set the <b>My Checkbox</b> check box with the desired status.
		 * 
		 * @param desiredStatus
		 */
		public void setMyCheckbox(boolean desiredStatus) {
			AutomationHelper.printMethodName();
			AutomationHelper.setCheckbox(myCheckbox, desiredStatus);
		}

		WebElement myDateInput = questionGroupContainer
				.findElement(By.xpath(".//span[text() = 'My Date']//ancestor::tr//input"));

		/**
		 * Returns the text in the <b>My Date</b> field.
		 * 
		 * @return String
		 */
		public String readMyDate() {
			AutomationHelper.printMethodName();
			return AutomationHelper.getText(myDateInput);
		}

		/**
		 * Sets a date for the <b>My Date</b> field.
		 * 
		 * @param myDate
		 */
		public void setMyDate(String myDate) {
			AutomationHelper.printMethodName();
			AutomationHelper.setCalendarDate(myDateInput, AutomationHelper.generateCalendarDate(myDate));
		}

		WebElement myNumberInput = questionGroupContainer
				.findElement(By.xpath(".//span[text() = 'My Number']//ancestor::tr//input"));

		/**
		 * Reads the text value of the <b>My Number</b> text field.
		 * 
		 * @return String
		 */
		public String readMyNumber() {
			AutomationHelper.printMethodName();
			return AutomationHelper.getText(myNumberInput);
		}

		/**
		 * Sets the <b>My Number</b> text field with the passed in number.
		 * 
		 * @param number
		 */
		public void setMyNumber(String number) {
			AutomationHelper.printMethodName();
			AutomationHelper.setTextField(myNumberInput, number);
			AutomationHelper.tab();
		}

		WebElement myPhoneInput = questionGroupContainer
				.findElement(By.xpath(".//span[text() = 'My Phone']//ancestor::tr//input"));

		/**
		 * Reads the text value of the <b>My Phone</b> text field.
		 * 
		 * @return String
		 */
		public String readMyPhone() {
			AutomationHelper.printMethodName();
			return AutomationHelper.getText(myPhoneInput);
		}

		/**
		 * Sets the <b>My Phone</b> text field with the passed in number.
		 * 
		 * @param phoneNumber
		 */
		public void setMyPhone(String phoneNumber) {
			AutomationHelper.printMethodName();
			AutomationHelper.setTextField(myPhoneInput, phoneNumber);
		}

		// My Radio Group
		WebElement myRadioGroupRadioButton = questionGroupContainer
				.findElement(By.xpath("//span[text() = 'My Radio Group']//ancestor::tr//input"));

		/**
		 * Read the <b>My Radio Group</b> radio button
		 * 
		 * @return boolean
		 */
		public boolean readMyRadioGroup() {
			AutomationHelper.printMethodName();
			return AutomationHelper.readRadioButtonStaus(myRadioGroupRadioButton);
		}

		/**
		 * Sets the <b>My Radio Group</b> with the passed in value.
		 * 
		 * @param desiredStatus
		 */
		public void setMyRadioGroup(boolean desiredStatus) {
			AutomationHelper.printMethodName();
			AutomationHelper.setRadioButton(myRadioGroupRadioButton, desiredStatus);
		}

		// TODO - Add select many later

		WebElement mySelectOne = questionGroupContainer
				.findElement(By.xpath(".//span[text() = 'My Select One']//ancestor::tr//select"));

		/**
		 * Method to read the <b>My Select One</b> field
		 * 
		 * @return String
		 */
		public String readMySelectOne() {
			AutomationHelper.printMethodName();
			return AutomationHelper.readSelectedSubItem(mySelectOne);
		}

		/**
		 * Method to select the <b>My Select One</b> value. <br>
		 * Note: Valid values are:<br>
		 * <li>Routine
		 * <li>Routine + Post Storm
		 * <li>Post Storm
		 * <li>Final
		 * 
		 * @param value
		 */
		public void selectMySelectOne(String value) {
			AutomationHelper.printMethodName();
			AutomationHelper.selectDropdownItem(mySelectOne, value);
		}
		
		WebElement myTextInput = questionGroupContainer
				.findElement(By.xpath(".//span[text() = 'My Text']//ancestor::tr//input"));

		/**
		 * Reads the text value of the <b>My Text</b> text field.
		 * 
		 * @return String
		 */
		public String readMyText() {
			AutomationHelper.printMethodName();
			return AutomationHelper.getText(myTextInput);
		}

		/**
		 * Sets the <b>My Text</b> text field with the passed in number.
		 * 
		 * @param text
		 */
		public void setMyText(String text) {
			AutomationHelper.printMethodName();
			AutomationHelper.setTextField(myTextInput, text);
		}
		
		//TODO - create methods for My Yes No NA later

	}

}
