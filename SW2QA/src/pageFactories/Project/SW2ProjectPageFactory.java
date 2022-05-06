package pageFactories.Project;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import pageFactories.SW2MenusPageFactory;
import pageFactories.SW2Modals;
import utilities.AutomationHelper;

/**
 * Abstract class created to contain methods common to all project pages.
 * Specifically, the Create / Edit project modal is used by both
 * ProjectInspectionsMainPageFactory and ProjectPageFactory. A common class that
 * they both extend solves the issue of the page URL not matching.
 * 
 * @author Jesse Childress
 *
 */
public abstract class SW2ProjectPageFactory extends SW2MenusPageFactory {

	/**
	 * Constructor - passes URL information upstream
	 * 
	 * @param regexURL
	 */
	public SW2ProjectPageFactory(String regexURL) {
		super(regexURL);
		waitForPageToLoad();
	}

	/**
	 * Class to contain methods that interact with the Create or Edit Project modal.
	 * 
	 * @author Jesse Childress
	 *
	 */
	public class CreateOrEditProjectModal extends SW2Modals {

		WebElement clientDropDown = MODAL.findElement(By.id("formGroup-clientId"));

		/**
		 * Reads the currently selected value in the Client drop down.
		 * 
		 * @return String
		 */
		public String readClientDropdown() {
			AutomationHelper.printMethodName();
			return AutomationHelper.readSelectedSubItem(clientDropDown);
		}

		/**
		 * Method to select a Client, based on the passed in client name.
		 * 
		 * @param client
		 */
		public void selectClientDropdown(String client) {
			AutomationHelper.printMethodName();
			AutomationHelper.selectDropdownItem(clientDropDown, client);
		}

		WebElement permitteeInput = MODAL.findElement(By.id("formGroup-permittee"));

		/**
		 * Reads the current text inside the Permittee field
		 * 
		 * @return String
		 */
		public String readPermitteeTextbox() {
			AutomationHelper.printMethodName();
			return AutomationHelper.getText(permitteeInput);
		}

		/**
		 * Method to set the Permittee text box with the passed in value.
		 * 
		 * @param permittee
		 */
		public void setPermitteeTextbox(String permittee) {
			AutomationHelper.printMethodName();
			AutomationHelper.setTextField(permitteeInput, permittee);
		}

		WebElement inspectionTypeDropdown = MODAL.findElement(By.id("formGroup-inspectionTemplateId"));

		/**
		 * Reads the currently selected value in the Inspection Form Type drop down.
		 * 
		 * @return String
		 */
		public String readInspectionFormTypeDropdown() {
			AutomationHelper.printMethodName();
			return AutomationHelper.readSelectedSubItem(inspectionTypeDropdown);
		}

		/**
		 * Method to select a Inspection Form Type, based on the passed in inspection
		 * form name.
		 * 
		 * @param client
		 */
		public void selectInspectionFormTypeDropdown(String inspectionFormType) {
			AutomationHelper.printMethodName();
			AutomationHelper.selectDropdownItem(inspectionTypeDropdown, inspectionFormType);
		}

		WebElement projectNameInput = MODAL.findElement(By.id("formGroup-name"));;

		/**
		 * Reads the current text inside the Project Name field
		 * 
		 * @return String
		 */
		public String readProjectNameTextbox() {
			AutomationHelper.printMethodName();
			return AutomationHelper.getText(projectNameInput);
		}

		/**
		 * Method to set the Project Name text box with the passed in value.
		 * 
		 * @param projectName
		 */
		public void setProjectNameTextbox(String projectName) {
			AutomationHelper.printMethodName();
			AutomationHelper.setTextField(projectNameInput, projectName);
		}

		WebElement addressInput = MODAL.findElement(By.id("formGroup-addressStreet"));;

		/**
		 * Reads the current text inside the Address field <br>
		 * Note: There is only one address. No address 2.
		 * 
		 * @return String
		 */
		public String readAddressTextbox() {
			AutomationHelper.printMethodName();
			return AutomationHelper.getText(addressInput);
		}

		/**
		 * Method to set the Address text box with the passed in value.
		 * 
		 * @param address
		 */
		public void setAddressTextbox(String address) {
			AutomationHelper.printMethodName();
			AutomationHelper.setTextField(addressInput, address);
		}

		WebElement cityInput = MODAL.findElement(By.id("formGroup-addressCity"));;

		/**
		 * Reads the current text inside the City field
		 * 
		 * @return String
		 */
		public String readCityTextbox() {
			AutomationHelper.printMethodName();
			return AutomationHelper.getText(cityInput);
		}

		/**
		 * Method to set the City text box with the passed in value.
		 * 
		 * @param city
		 */
		public void setCityTextbox(String city) {
			AutomationHelper.printMethodName();
			AutomationHelper.setTextField(cityInput, city);
		}

		WebElement stateDropdown = MODAL.findElement(By.id("formGroup-addressState"));;

		/**
		 * Reads the currently selected value in the State drop down.
		 * 
		 * @return String
		 */
		public String readStateDropdown() {
			AutomationHelper.printMethodName();
			return AutomationHelper.readSelectedSubItem(stateDropdown);
		}

		/**
		 * Method to select a State, based on the passed in state.
		 * 
		 * @param state
		 */
		public void selectStateDropdown(String state) {
			AutomationHelper.printMethodName();
			if (!state.equals("")) {
				AutomationHelper.selectDropdownItem(stateDropdown, state);
			}
		}

		WebElement zipInput = MODAL.findElement(By.id("formGroup-addressZip"));;

		/**
		 * Reads the current text inside the Zip field
		 * 
		 * @return String
		 */
		public String readZipTextbox() {
			AutomationHelper.printMethodName();
			return AutomationHelper.getText(zipInput);
		}

		/**
		 * Method to set the Zip text box with the passed in value.
		 * 
		 * @param zip
		 */
		public void setZipTextbox(String zip) {
			AutomationHelper.printMethodName();
			AutomationHelper.setTextField(zipInput, zip);
		}

		WebElement latituideInput = MODAL.findElement(By.id("formGroup-latitude"));;

		/**
		 * Reads the current text inside the Latitude field
		 * 
		 * @return String
		 */
		public String readLatitudeTextbox() {
			AutomationHelper.printMethodName();
			return AutomationHelper.getText(latituideInput);
		}

		/**
		 * Method to set the Latitude text box with the passed in value.
		 * 
		 * @param latitude
		 */
		public void setLatitudeTextbox(String latitude) {
			AutomationHelper.printMethodName();
			AutomationHelper.setTextField(latituideInput, latitude);
		}

		WebElement longitudeInput = MODAL.findElement(By.id("formGroup-longitude"));;

		/**
		 * Reads the current text inside the Longitude field
		 * 
		 * @return String
		 */
		public String readLongitudeTextbox() {
			AutomationHelper.printMethodName();
			return AutomationHelper.getText(longitudeInput);
		}

		/**
		 * Method to set the Longitude text box with the passed in value.
		 * 
		 * @param longitude
		 */
		public void setLongitudeTextbox(String longitude) {
			AutomationHelper.printMethodName();
			AutomationHelper.setTextField(longitudeInput, longitude);
		}

		WebElement timeZoneDropdown = MODAL.findElement(By.id("formGroup-timezone"));;

		/**
		 * Reads the currently selected value in the Time Zone drop down.
		 * 
		 * @return String
		 */
		public String readTimeZoneDropdown() {
			AutomationHelper.printMethodName();
			return AutomationHelper.readSelectedSubItem(timeZoneDropdown);
		}

		/**
		 * Method to select a Time Zone, based on the passed in time zone.
		 * 
		 * @param timeZone
		 */
		public void selectTimeZoneDropdown(String timeZone) {
			AutomationHelper.printMethodName();
			AutomationHelper.selectDropdownItem(timeZoneDropdown, timeZone);
		}

		WebElement inspectorDropdown = MODAL.findElement(By.id("formGroup-inspector_user_id"));;

		/**
		 * Reads the currently selected value in the Inspector drop down.
		 * 
		 * @return String
		 */
		public String readInspectorDropdown() {
			AutomationHelper.printMethodName();
			return AutomationHelper.readSelectedSubItem(inspectorDropdown);
		}

		/**
		 * Method to select a Inspector, based on the passed in inspection form name.
		 * 
		 * @param inspector
		 */
		public void selectInspectorDropdown(String inspector) {
			AutomationHelper.printMethodName();
			AutomationHelper.selectDropdownItem(inspectorDropdown, inspector);
		}

		WebElement projectTimelineStartDateTextbox = MODAL.findElement(By.id("formGroup-startDate"));;

		/**
		 * Reads the current text inside the Anticipated Time Line - Start Date field
		 * 
		 * @return String
		 */
		public String readTimeLineStartDateTextbox() {
			AutomationHelper.printMethodName();
			return AutomationHelper.getText(projectTimelineStartDateTextbox);
		}

		/**
		 * Method to set the Anticipated Time Line - End Date text box with the passed
		 * in value. <br>
		 * Note: Pass the date in MM-dd-yyyy format.
		 * 
		 * @param startDate
		 */
		public void setTimeLineStartDateTextbox(String startDate) {
			AutomationHelper.printMethodName();
			AutomationHelper.setCalendarDate(projectTimelineStartDateTextbox, startDate);
		}

		WebElement projectTimelineEndDateTextbox = MODAL.findElement(By.id("formGroup-endDate"));;

		/**
		 * Reads the current text inside the Anticipated Time Line - End Date field
		 * 
		 * @return String
		 */
		public String readTimeLineEndDateTextbox() {
			AutomationHelper.printMethodName();
			return AutomationHelper.getText(projectTimelineEndDateTextbox);
		}

		/**
		 * Method to set the Anticipated Time Line - End Date text box with the passed
		 * in value. <br>
		 * Note: Pass the date in MM-dd-yyyy format.
		 * 
		 * @param endDate
		 */
		public void setTimeLineEndDateTextbox(String endDate) {
			AutomationHelper.printMethodName();
			AutomationHelper.setCalendarDate(projectTimelineEndDateTextbox, endDate);

		}

		WebElement nextRoutineInspectionDateTextbox = MODAL.findElement(By.id("formGroup-next_inspection_date"));

		/**
		 * Reads the current text inside the Next Routine Inspection Date field
		 * 
		 * @return String
		 */
		public String readNextRoutineInspectionDateTextbox() {
			AutomationHelper.printMethodName();
			return AutomationHelper.getText(nextRoutineInspectionDateTextbox);
		}

		/**
		 * Method to set the Next Routine Inspection Date text box with the passed in
		 * value. <br>
		 * Note: Pass the date in MM-dd-yyyy format.
		 * 
		 * @param nextRoutineInspectionDate
		 */
		public void setNextRoutineInspectionDateTextbox(String nextRoutineInspectionDate) {
			AutomationHelper.printMethodName();
			AutomationHelper.setCalendarDate(nextRoutineInspectionDateTextbox, nextRoutineInspectionDate);
		}

		WebElement routineInspectionIntervalTextbox = MODAL.findElement(By.id("formGroup-routine_interval"));

		/**
		 * Reads the current text inside the Routine Inspection Interval field
		 * 
		 * @return String
		 */
		public String readRoutineInspectionIntervalTextbox() {
			AutomationHelper.printMethodName();
			return AutomationHelper.getText(routineInspectionIntervalTextbox);
		}

		/**
		 * Method to set the Routine Inspection Interval text box with the passed in
		 * value.
		 * 
		 * @param routineInspectionInterval
		 */
		public void setRoutineInspectionIntervalTextbox(String routineInspectionInterval) {
			AutomationHelper.printMethodName();
			AutomationHelper.setTextField(routineInspectionIntervalTextbox, routineInspectionInterval);
		}

		WebElement additionalNotesInput = MODAL.findElement(By.id("formGroup-notes"));;

		/**
		 * Reads the current text inside the Additional Notes field
		 * 
		 * @return String
		 */
		public String readAdditionalNotesTextbox() {
			AutomationHelper.printMethodName();
			return AutomationHelper.getText(additionalNotesInput);
		}

		/**
		 * Method to set the Zip text box with the passed in value.
		 * 
		 * @param additionalNotes
		 */
		public void setAdditionalNotesTextbox(String additionalNotes) {
			AutomationHelper.printMethodName();
			AutomationHelper.setTextField(additionalNotesInput, additionalNotes);
		}

		WebElement regulationsDropdown = MODAL.findElement(By.id("formGroup-document_group"));;

		/**
		 * Reads the currently selected value in the Regulations drop down.
		 * 
		 * @return String
		 */
		public String readRegulationsDropdown() {
			AutomationHelper.printMethodName();
			return AutomationHelper.readSelectedSubItem(regulationsDropdown);
		}

		/**
		 * Method to select a Regulation, based on the passed in regulation.
		 * 
		 * @param regulation
		 */
		public void selectRegulationsDropdown(String regulation) {
			AutomationHelper.printMethodName();
			AutomationHelper.selectDropdownItem(regulationsDropdown, regulation);
		}

		WebElement activeSiteMapCheckbox = MODAL.findElement(By.xpath(
				"//div[@class='form-group form-group--checkboxGroup']//span[text() = 'Active Site Maps']/following-sibling::input"));

		/**
		 * Method to read the value of the Active Site Map check box
		 * 
		 * @return returns true if checked; false if not checked.
		 */
		public boolean readActiveSiteMapCheckbox() {
			AutomationHelper.printMethodName();
			return AutomationHelper.readCheckboxStaus(activeSiteMapCheckbox);
		}

		/**
		 * Method to set the Active Site Map Checkbox with the desired boolean status.
		 * 
		 * @param desiredStatus
		 */
		public void setActiveSiteMapCheckbox(boolean desiredStatus) {
			AutomationHelper.printMethodName();
			AutomationHelper.setCheckbox(activeSiteMapCheckbox, desiredStatus);
		}

		WebElement constructionDocsCivilDrawingsCheckbox = MODAL.findElement(By.xpath(
				"//div[@class='form-group form-group--checkboxGroup']//span[text() = 'Construction Docs/Civil Drawings']/following-sibling::input"));

		/**
		 * Method to read the value of the Construction Docs/Civil Drawings check box
		 * 
		 * @return returns true if checked; false if not checked.
		 */
		public boolean readConstructionDocsCivilDrawingsCheckbox() {
			AutomationHelper.printMethodName();
			return AutomationHelper.readCheckboxStaus(constructionDocsCivilDrawingsCheckbox);
		}

		/**
		 * Method to set the Construction Docs/Civil Drawings Checkbox with the desired
		 * boolean status.
		 * 
		 * @param desiredStatus
		 */
		public void setConstructionDocsCivilDrawingsCheckbox(boolean desiredStatus) {
			AutomationHelper.printMethodName();
			AutomationHelper.setCheckbox(constructionDocsCivilDrawingsCheckbox, desiredStatus);
		}

		WebElement soilReportsCheckbox = MODAL.findElement(By.xpath(
				"//div[@class='form-group form-group--checkboxGroup']//span[text() = 'Soil Reports']/following-sibling::input"));

		/**
		 * Method to read the value of the Soil Reports check box
		 * 
		 * @return returns true if checked; false if not checked.
		 */
		public boolean readSoilReportsCheckbox() {
			AutomationHelper.printMethodName();
			return AutomationHelper.readCheckboxStaus(soilReportsCheckbox);
		}

		/**
		 * Method to set the Soil Reports Checkbox with the desired boolean status.
		 * 
		 * @param desiredStatus
		 */
		public void setSoilReportsCheckbox(boolean desiredStatus) {
			AutomationHelper.printMethodName();
			AutomationHelper.setCheckbox(soilReportsCheckbox, desiredStatus);
		}

		WebElement delegationLettersCheckbox = MODAL.findElement(By.xpath(
				"//div[@class='form-group form-group--checkboxGroup']//span[text() = 'Delegation Letters']/following-sibling::input"));

		/**
		 * Method to read the value of the Delegation Letters check box
		 * 
		 * @return returns true if checked; false if not checked.
		 */
		public boolean readDelegationLettersCheckbox() {
			AutomationHelper.printMethodName();
			return AutomationHelper.readCheckboxStaus(delegationLettersCheckbox);
		}

		/**
		 * Method to set the Delegation Letters Checkbox with the desired boolean
		 * status.
		 * 
		 * @param desiredStatus
		 */
		public void setDelegationLettersCheckbox(boolean desiredStatus) {
			AutomationHelper.printMethodName();
			AutomationHelper.setCheckbox(delegationLettersCheckbox, desiredStatus);
		}

		WebElement endangeredSpeciesCheckbox = MODAL.findElement(By.xpath(
				"//div[@class='form-group form-group--checkboxGroup']//span[text() = 'Endangered Species']/following-sibling::input"));

		/**
		 * Method to read the value of the Endangered Species check box
		 * 
		 * @return returns true if checked; false if not checked.
		 */
		public boolean readEndangeredSpeciesCheckbox() {
			AutomationHelper.printMethodName();
			return AutomationHelper.readCheckboxStaus(endangeredSpeciesCheckbox);
		}

		/**
		 * Method to set the Endangered Species Checkbox with the desired boolean
		 * status.
		 * 
		 * @param desiredStatus
		 */
		public void setEndangeredSpeciesCheckbox(boolean desiredStatus) {
			AutomationHelper.printMethodName();
			AutomationHelper.setCheckbox(endangeredSpeciesCheckbox, desiredStatus);
		}

		WebElement historicalPropertiesCheckbox = MODAL.findElement(By.xpath(
				"//div[@class='form-group form-group--checkboxGroup']//span[text() = 'Historical Properties']/following-sibling::input"));

		/**
		 * Method to read the value of the Historical Properties check box
		 * 
		 * @return returns true if checked; false if not checked.
		 */
		public boolean readHistoricalPropertiesCheckbox() {
			AutomationHelper.printMethodName();
			return AutomationHelper.readCheckboxStaus(historicalPropertiesCheckbox);
		}

		/**
		 * Method to set the Historical Properties Checkbox with the desired boolean
		 * status.
		 * 
		 * @param desiredStatus
		 */
		public void setHistoricalPropertiesCheckbox(boolean desiredStatus) {
			AutomationHelper.printMethodName();
			AutomationHelper.setCheckbox(historicalPropertiesCheckbox, desiredStatus);
		}

		WebElement permitsCheckbox = MODAL.findElement(By.xpath(
				"//div[@class='form-group form-group--checkboxGroup']//span[text() = 'Permits']/following-sibling::input"));

		/**
		 * Method to read the value of the Permits check box
		 * 
		 * @return returns true if checked; false if not checked.
		 */
		public boolean readPermitsCheckbox() {
			AutomationHelper.printMethodName();
			return AutomationHelper.readCheckboxStaus(permitsCheckbox);
		}

		/**
		 * Method to set the Permits Checkbox with the desired boolean status.
		 * 
		 * @param desiredStatus
		 */
		public void setPermitsCheckbox(boolean desiredStatus) {
			AutomationHelper.printMethodName();
			AutomationHelper.setCheckbox(permitsCheckbox, desiredStatus);
		}

		WebElement qualificationsCertificationsCheckbox = MODAL.findElement(By.xpath(
				"//div[@class='form-group form-group--checkboxGroup']//span[text() = 'Qualifications/Certifications']/following-sibling::input"));

		/**
		 * Method to read the value of the Qualifications/Certifications check box
		 * 
		 * @return returns true if checked; false if not checked.
		 */
		public boolean readQualificationsCertificationsCheckbox() {
			AutomationHelper.printMethodName();
			return AutomationHelper.readCheckboxStaus(qualificationsCertificationsCheckbox);
		}

		/**
		 * Method to set the Qualifications/Certifications Checkbox with the desired
		 * boolean status.
		 * 
		 * @param desiredStatus
		 */
		public void setQualificationsCertificationsCheckbox(boolean desiredStatus) {
			AutomationHelper.printMethodName();
			AutomationHelper.setCheckbox(qualificationsCertificationsCheckbox, desiredStatus);
		}

		WebElement localStormwaterRegulationsCheckbox = MODAL.findElement(By.xpath(
				"//div[@class='form-group form-group--checkboxGroup']//span[text() = 'Local Stormwater Regulations']/following-sibling::input"));

		/**
		 * Method to read the value of the Local Stormwater Regulations check box
		 * 
		 * @return returns true if checked; false if not checked.
		 */
		public boolean readLocalStormwaterRegulationsCheckbox() {
			AutomationHelper.printMethodName();
			return AutomationHelper.readCheckboxStaus(localStormwaterRegulationsCheckbox);
		}

		/**
		 * Method to set the Local Stormwater Regulations Checkbox with the desired
		 * boolean status.
		 * 
		 * @param desiredStatus
		 */
		public void setLocalStormwaterRegulationsCheckbox(boolean desiredStatus) {
			AutomationHelper.printMethodName();
			AutomationHelper.setCheckbox(localStormwaterRegulationsCheckbox, desiredStatus);
		}

		WebElement miscCheckbox = MODAL.findElement(By.xpath(
				"//div[@class='form-group form-group--checkboxGroup']//span[text() = 'MISC']/following-sibling::input"));

		/**
		 * Method to read the value of the MISC check box
		 * 
		 * @return returns true if checked; false if not checked.
		 */
		public boolean readMISCCheckbox() {
			AutomationHelper.printMethodName();
			return AutomationHelper.readCheckboxStaus(miscCheckbox);
		}

		/**
		 * Method to set the MISC Checkbox with the desired boolean status.
		 * 
		 * @param desiredStatus
		 */
		public void setMISCCheckbox(boolean desiredStatus) {
			AutomationHelper.printMethodName();
			AutomationHelper.setCheckbox(miscCheckbox, desiredStatus);
		}

		WebElement swpNarrativeMapsCheckbox = MODAL.findElement(By.xpath(
				"//div[@class='form-group form-group--checkboxGroup']//span[text() = 'SWP Narrative/Maps']/following-sibling::input"));

		/**
		 * Method to read the value of the SWP Narrative/Maps check box
		 * 
		 * @return returns true if checked; false if not checked.
		 */
		public boolean readSWPNarrativeMapsCheckbox() {
			AutomationHelper.printMethodName();
			return AutomationHelper.readCheckboxStaus(swpNarrativeMapsCheckbox);
		}

		/**
		 * Method to set the SWP Narrative/Maps Checkbox with the desired boolean
		 * status.
		 * 
		 * @param desiredStatus
		 */
		public void setSWPNarrativeMapsCheckbox(boolean desiredStatus) {
			AutomationHelper.printMethodName();
			AutomationHelper.setCheckbox(swpNarrativeMapsCheckbox, desiredStatus);
		}
	}
}
