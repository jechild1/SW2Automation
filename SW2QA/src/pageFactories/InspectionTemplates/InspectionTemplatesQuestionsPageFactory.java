package pageFactories.InspectionTemplates;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import pageFactories.SW2Modals;
import utilities.AutomationHelper;

/**
 * Page factory for the Inspection Templates page for SW2.<br>
 * (1) Finds a reference to objects on the page <br>
 * (2) Defines methods to interact with objects on the page.<br>
 * 
 * @author Jesse Childress
 *
 */
public class InspectionTemplatesQuestionsPageFactory extends SW2InspectionTemplatesMainPageFactory {

	public static String regexURL = BASE_URL + ".*" + "inspection-templates" + ".*" + "questions";

	/**
	 * Page Constructor - Calls constructor in MenusPageFactory.
	 */
	public InspectionTemplatesQuestionsPageFactory() {
		super(regexURL);
		waitForPageToLoad();
	}

	/**
	 * Clicks the <b>Edit Inspection Template</b> button.
	 */
	public void clickEditInspectionTemplate() {
		AutomationHelper.printMethodName();
		WebElement button = driver.findElement(By.xpath("//button[@class = 'outline create-new contact.buttons']"));
		button.click();
	}

	/**
	 * Clicks the Trash Can / Delete button.
	 */
	public void clickDelete() {
		AutomationHelper.printMethodName();
		WebElement deleteButton = driver.findElement(By.xpath("//button[@class='icon-only-button outline warn']"));
		deleteButton.click();
	}

	/**
	 * Method to click the Add Question group. This is present to start a new
	 * question group. It's used when it's blank.
	 */
	public void clickAddQuestionGroup() {
		AutomationHelper.printMethodName();
		WebElement questionGroup = driver.findElement(By.xpath("//div[@class = 'add-question-group']"));
		questionGroup.click();
	}

	/**
	 * Clicks the + icon inside of the Template Group with the passed in template
	 * name.
	 * 
	 * @param questionGroupName
	 */
	public void clickAddQuestionGroup(String questionGroupName) {
		AutomationHelper.printMethodName();

		String xpath = "//h3[text() = '" + questionGroupName
				+ "']//ancestor::div[@class = 'question-group container']//*[name()='svg' and contains (@class,'svg-inline--fa fa-plus fa-w-14 ')]";
		WebElement addQuestionToGroupButton = driver.findElement(By.xpath(xpath));
		addQuestionToGroupButton.click();
	}

	/**
	 * Method to click the Edit icon (looks like a pencil and paper) for a given
	 * question section and question Name.
	 * 
	 * @param questionGroup - e.g. Template 1 Questions
	 * @param questionLabel - e.g. My Checkbox
	 */
	public void clickEditForQuestion(String questionGroup, String questionLabel) {

		WebElement questionGroupDiv = driver.findElement(
				By.xpath("//h3[text() = '" + questionGroup + "']//ancestor::div[@class = 'question-group container']"));

		WebElement questionBox = questionGroupDiv
				.findElement(By.xpath(".//div[@class='question-header']//child::span[text()='" + questionLabel
						+ "']//parent::div//*[local-name()='svg' and @class='svg-inline--fa fa-edit fa-w-18 fa-fw ']"));

		questionBox.click();
		waitForModalToLoad();
		waitForPageToLoad();
	}

	/**
	 * Method to return a reference to the "Add a Question Group" modal.
	 * 
	 * @return AddAQuestionGroupModal
	 */
	public AddAQuestionGroupModal getAddAQuestionGroupModal() {
		return new AddAQuestionGroupModal();
	}

	/**
	 * Class to contain objects to work with the "Add a Question Group" modal.
	 * 
	 * @author Jesse Childress
	 *
	 */
	public class AddAQuestionGroupModal extends SW2Modals {

		/**
		 * Returns the value of the <b>Name</b> text field.
		 * 
		 * @return String
		 */
		public String readNameInput() {
			AutomationHelper.printMethodName();
			WebElement object = driver.findElement(By.id("formGroup-name"));
			return AutomationHelper.getText(object);
		}

		/**
		 * Sets the value of the <b>Name</b> text field.
		 * 
		 * @param name
		 */
		public void setNameInput(String name) {
			AutomationHelper.printMethodName();
			WebElement object = driver.findElement(By.id("formGroup-name"));
			AutomationHelper.setTextField(object, name);
		}

		/**
		 * Reads the <b>Form Layout</b> radio button.
		 * 
		 * @return boolean - true of checked; false if un-checked;
		 */
		public boolean readFormLayoutRadiobutton() {
			AutomationHelper.printMethodName();
			WebElement object = driver.findElement(By.xpath("//input[@value='FORM']"));
			return AutomationHelper.readRadioButtonStaus(object);
		}

		/**
		 * Clicks the <b>Form Layout</b> radio button.
		 * 
		 * @param desiredStatus - true if checked, false if not checked.
		 */
		public void setFormLayoutRadiobutton(boolean desiredStatus) {
			AutomationHelper.printMethodName();
			WebElement object = driver.findElement(By.xpath("//input[@value='FORM']"));
			AutomationHelper.setCheckbox(object, desiredStatus);
		}

		/**
		 * Reads the <b>Table Layout</b> radio button.
		 * 
		 * @return boolean - true of checked; false if un-checked;
		 */
		public boolean readTableLayoutRadiobutton() {
			AutomationHelper.printMethodName();
			WebElement object = driver.findElement(By.xpath("//input[@value='TABLE']"));
			return AutomationHelper.readRadioButtonStaus(object);
		}

		/**
		 * Clicks the <b>Table Layout</b> radio button.
		 * 
		 * @param desiredStatus - true if checked, false if not checked.
		 */
		public void setTableLayoutRadiobutton(boolean desiredStatus) {
			AutomationHelper.printMethodName();
			WebElement object = driver.findElement(By.xpath("//input[@value='TABLE']"));
			AutomationHelper.setCheckbox(object, desiredStatus);
		}

	}

	/**
	 * Method to return a reference to the <b>Add a Question</b> modal.
	 * 
	 * @return AddAQuestionModal
	 */
	public AddAQuestionModal getAddAQuestionModal() {
		return new AddAQuestionModal();
	}

	/**
	 * Method to return a reference to the <b>Edit Question</b> modal. <br>
	 * This method returns a type of Add A Question modal. It is the same form.
	 * 
	 * @return AddAQuestionModal
	 */
	public AddAQuestionModal getEditQuestionModal() {
		return new AddAQuestionModal();

	}

	/**
	 * Class to contain objects to work with the <b>Add a question</b> modal.
	 * 
	 * @author Jesse Childress
	 *
	 */
	public class AddAQuestionModal extends SW2Modals {

		public AddAQuestionModal() {
		}

		@FindBy(id = "formGroup-question_type_id")
		WebElement questionTypeDropDown;

		/**
		 * Reads the currently selected <b>Question Type</b> drop down option.
		 * 
		 * @return String
		 */
		public String readQuestionTypeDropDown() {
			AutomationHelper.printMethodName();
			return AutomationHelper.readSelectedSubItem(questionTypeDropDown);
		}

		/**
		 * Selects an item for the <b>Question Type</b> drop down.
		 * 
		 * @param questionType
		 */
		public void selectQuestionTypeDropDown(String questionType) {
			AutomationHelper.printMethodName();
			AutomationHelper.selectDropdownItem(questionTypeDropDown, questionType);
		}

		/**
		 * Checks to see if the <b>Question Type</b> drop down is disabled.
		 * 
		 * @return boolean
		 */
		public boolean isQuestionTypeDrownDownEnabled() {
			AutomationHelper.printMethodName();
			boolean isEnabled = questionTypeDropDown.isEnabled();
			return isEnabled;
		}

		@FindBy(id = "formGroup-input_label")
		WebElement questionLabelInput;

		/**
		 * Reads the value of the <b>Question Label</b> text field.
		 * 
		 * @return String
		 */
		public String readQuestionLabelInput() {
			AutomationHelper.printMethodName();
			return AutomationHelper.getText(questionLabelInput);
		}

		/**
		 * Sets the value of the <b>Question Label</b> text field.
		 * 
		 * @param questionLabel
		 */
		public void setQuestionLabelInput(String questionLabel) {
			AutomationHelper.printMethodName();
			AutomationHelper.setTextField(questionLabelInput, questionLabel);
		}

		@FindBy(id = "formGroup-description")
		WebElement descriptionInput;

		/**
		 * Reads the value of the <b>Description</b> text field.
		 * 
		 * @return String
		 */
		public String readDescriptionInput() {
			AutomationHelper.printMethodName();
			return AutomationHelper.getText(descriptionInput);
		}

		/**
		 * Sets the value of the <b>Description</b> text field.
		 * 
		 * @param description
		 */
		public void setDescriptionInput(String description) {
			AutomationHelper.printMethodName();
			AutomationHelper.setTextField(descriptionInput, description);
			AutomationHelper.tab();
		}

//		@FindBy(id = "formGroup-question_width")
//		WebElement questionWidthInput;

		/**
		 * Reads the value of the <b>Question Width</b> text field.
		 * 
		 * @return String
		 */
		public String readQuestionWidthInput() {
			AutomationHelper.printMethodName();
			WebElement questionWidthInput = driver
					.findElement(By.xpath("//div[@class='form-group form-group--number']//input"));
//			WebElement questionWidthInput = driver.findElement(By.id("formGroup-question_width"));
			return AutomationHelper.getText(questionWidthInput);
		}

		/**
		 * Sets the value of the <b>Question Width</b> text field.
		 * 
		 * @param questionWidth
		 */
		public void setQuestionWidthInput(String questionWidth) {
			AutomationHelper.printMethodName();

			try {
				Integer.parseInt(questionWidth);
			} catch (NumberFormatException e) {
				System.out.println("You must use a number for question width.");
			}

//			WebElement questionWidthInput = driver.findElement(By.id("formGroup-question_width"));

			WebElement questionWidthInput = driver
					.findElement(By.xpath("//div[@class='form-group form-group--number']//input"));
			AutomationHelper.setTextField(questionWidthInput, questionWidth);

			// Perhaps because this is a number field, the next click operation doesn't
			// work. We must draw attention away from the field by tabbing.
			AutomationHelper.tab();

		}

		// Because there are a lot of checkboxes on the page, lets simplify the code
		/**
		 * Utility method to return a checkbox by simply passing in a name
		 * 
		 * @param name
		 * @return WebElement checkbox
		 */
		private WebElement getCheckboxWithName(String name) {
			String xpath = "//span[text()='" + name + "']//following-sibling::input";
			return driver.findElement(By.xpath(xpath));
		}

		/**
		 * Reads the currently selected value of the <b>Required</b> checkbox
		 * 
		 * @return String
		 */
		public boolean readRequiredCheckbox() {
			AutomationHelper.printMethodName();
			return AutomationHelper.readCheckboxStaus(getCheckboxWithName("Required"));
		}

		/**
		 * Sets the <b>Required</b> checkbox with the passed in value
		 * 
		 * @param desiredStatus
		 */
		public void setRequiredCheckbox(boolean desiredStatus) {
			AutomationHelper.printMethodName();
			AutomationHelper.setCheckbox(getCheckboxWithName("Required"), desiredStatus);
		}

		/**
		 * Reads the currently selected value of the <b>Carry Over</b> checkbox
		 * 
		 * @return String
		 */
		public boolean readCarryOverCheckbox() {
			AutomationHelper.printMethodName();
			return AutomationHelper.readCheckboxStaus(getCheckboxWithName("Carry Over"));
		}

		/**
		 * Sets the <b>Carry Over</b> checkbox with the passed in value
		 * 
		 * @param desiredStatus
		 */
		public void setCarryOverCheckbox(boolean desiredStatus) {
			AutomationHelper.printMethodName();
			AutomationHelper.setCheckbox(getCheckboxWithName("Carry Over"), desiredStatus);
		}

		/**
		 * Reads the currently selected value of the <b>Enable Comment/Notes</b>
		 * checkbox
		 * 
		 * @return String
		 */
		public boolean readEnableCommentsNotesCheckbox() {
			AutomationHelper.printMethodName();
			return AutomationHelper.readCheckboxStaus(getCheckboxWithName("Enable Comment/Notes"));
		}

		/**
		 * Sets the <b>Enable Comment/Notes</b> checkbox with the passed in value
		 * 
		 * @param desiredStatus
		 */
		public void setEnableCommentsNotesCheckbox(boolean desiredStatus) {
			AutomationHelper.printMethodName();
			AutomationHelper.setCheckbox(getCheckboxWithName("Enable Comment/Notes"), desiredStatus);
		}

		/**
		 * Reads the currently selected value of the <b>Default To Date of Current
		 * Inspection</b> checkbox
		 * 
		 * @return String
		 */
		public boolean readDefaultToDateOfCurrentInspectionCheckbox() {
			AutomationHelper.printMethodName();
			return AutomationHelper.readCheckboxStaus(getCheckboxWithName("Default To Date of Current Inspection"));
		}

		/**
		 * Sets the <b>Default To Date of Current Inspection</b> checkbox with the
		 * passed in value
		 * 
		 * @param desiredStatus
		 */
		public void setDefaultToDateOfCurrentInspectionCheckbox(boolean desiredStatus) {
			AutomationHelper.printMethodName();
			AutomationHelper.setCheckbox(getCheckboxWithName("Default To Date of Current Inspection"), desiredStatus);
		}

		/**
		 * Reads the currently selected value of the <b>Default To Date of Previous
		 * Inspection</b> checkbox
		 * 
		 * @return String
		 */
		public boolean readDefaultToDateOfPreviousInspectionCheckbox() {
			AutomationHelper.printMethodName();
			return AutomationHelper.readCheckboxStaus(getCheckboxWithName("Default To Date of Previous Inspection"));
		}

		/**
		 * Sets the <b>Default To Date of Previous Inspection</b> checkbox with the
		 * passed in value
		 * 
		 * @param desiredStatus
		 */
		public void setDefaultToDateOfPreviousInspectionCheckbox(boolean desiredStatus) {
			AutomationHelper.printMethodName();
			AutomationHelper.setCheckbox(getCheckboxWithName("Default To Date of Previous Inspection"), desiredStatus);
		}

		/**
		 * Reads the currently selected value of the <b>Default To Inspector User
		 * Name</b> checkbox
		 * 
		 * @return String
		 */
		public boolean readDefaultToInspectorUserNameCheckbox() {
			AutomationHelper.printMethodName();
			return AutomationHelper.readCheckboxStaus(getCheckboxWithName("Default To Inspector User Name"));
		}

		/**
		 * Sets the <b>Default To Inspector User Name</b> checkbox with the passed in
		 * value
		 * 
		 * @param desiredStatus
		 */
		public void setDefaultToInspectorUserNameCheckbox(boolean desiredStatus) {
			AutomationHelper.printMethodName();
			AutomationHelper.setCheckbox(getCheckboxWithName("Default To Inspector User Name"), desiredStatus);
		}

		/**
		 * Reads the currently selected value of the <b>Default To Project Name</b>
		 * checkbox
		 * 
		 * @return String
		 */
		public boolean readDefaultToProjectNameCheckbox() {
			AutomationHelper.printMethodName();
			return AutomationHelper.readCheckboxStaus(getCheckboxWithName("Default To Project Name"));
		}

		/**
		 * Sets the <b>Default To Project Name</b> checkbox with the passed in value
		 * 
		 * @param desiredStatus
		 */
		public void setDefaultToProjectNameCheckbox(boolean desiredStatus) {
			AutomationHelper.printMethodName();
			AutomationHelper.setCheckbox(getCheckboxWithName("Default To Project Name"), desiredStatus);
		}

		/**
		 * Reads the currently selected value of the <b>Default To Client Name</b>
		 * checkbox
		 * 
		 * @return String
		 */
		public boolean readDefaultToClientNameCheckbox() {
			AutomationHelper.printMethodName();
			return AutomationHelper.readCheckboxStaus(getCheckboxWithName("Default To Client Name"));
		}

		/**
		 * Sets the <b>Default To Client Name</b> checkbox with the passed in value
		 * 
		 * @param desiredStatus
		 */
		public void setDefaultToClientNameCheckbox(boolean desiredStatus) {
			AutomationHelper.printMethodName();
			AutomationHelper.setCheckbox(getCheckboxWithName("Default To Client Name"), desiredStatus);
		}

		/**
		 * Reads the currently selected value of the <b>Feature: Post-storm Event</b>
		 * checkbox
		 * 
		 * @return String
		 */
		public boolean readFeaturePostStormEventCheckbox() {
			AutomationHelper.printMethodName();
			return AutomationHelper.readCheckboxStaus(getCheckboxWithName("Feature: Post-storm Event"));
		}

		/**
		 * Sets the <b>Feature: Post-storm Event</b> checkbox with the passed in value
		 * 
		 * @param desiredStatus
		 */
		public void setFeaturePostStormEventCheckbox(boolean desiredStatus) {
			AutomationHelper.printMethodName();
			AutomationHelper.setCheckbox(getCheckboxWithName("Feature: Post-storm Event"), desiredStatus);
		}

		/**
		 * Reads the currently selected value of the <b>Feature: Inspection Date
		 * Feature</b> checkbox
		 * 
		 * @return String
		 */
		public boolean readFeatureInspectionDateFeatureCheckbox() {
			AutomationHelper.printMethodName();
			return AutomationHelper.readCheckboxStaus(getCheckboxWithName("Feature: Inspection Date Feature"));
		}

		/**
		 * Sets the <b>Feature: Inspection Date Feature</b> checkbox with the passed in
		 * value
		 * 
		 * @param desiredStatus
		 */
		public void setFeatureInspectionDateFeatureCheckbox(boolean desiredStatus) {
			AutomationHelper.printMethodName();
			AutomationHelper.setCheckbox(getCheckboxWithName("Feature: Inspection Date Feature"), desiredStatus);
		}

		/**
		 * Reads the currently selected value of the <b>Feature: Inspection Type</b>
		 * checkbox
		 * 
		 * @return String
		 */
		public boolean readFeatureInspectionTypeCheckbox() {
			AutomationHelper.printMethodName();
			return AutomationHelper.readCheckboxStaus(getCheckboxWithName("Feature: Inspection Type"));
		}

		/**
		 * Sets the <b>Feature: Inspection Type</b> checkbox with the passed in value
		 * 
		 * @param desiredStatus
		 */
		public void setFeatureInspectionTypeCheckbox(boolean desiredStatus) {
			AutomationHelper.printMethodName();
			AutomationHelper.setCheckbox(getCheckboxWithName("Feature: Inspection Type"), desiredStatus);
		}
	}

	/**
	 * Method to return a reference to the <b>Delete Inspection Template</b> modal.
	 * 
	 * @return AddAQuestionModal
	 */
	public DeleteInspectionTemplatesModal getDeleteInspectionTemplateModal() {
		return new DeleteInspectionTemplatesModal();
	}

	/**
	 * Class to handle creating objects for the Delete Inspection Template Modal.
	 * 
	 * @author Jesse Childress
	 *
	 */
	public class DeleteInspectionTemplatesModal extends SW2Modals {

		/**
		 * Method to read the Warning on the <b>Delete Inspection Template</b> modal.
		 * 
		 * @return
		 */
		public String readModalWarning() {
			AutomationHelper.printMethodName();
			WebElement modalTitle = MODAL.findElement(By.xpath("//div[@id='modal']//h4"));
			return AutomationHelper.getText(modalTitle).trim();

		}

		/**
		 * Method to read the Modal Body Text on the <b>Delete Inspection Template</b>
		 * modal. This method overrides the method in SW2Modals.
		 */
		public String readModalBodyText() {
			AutomationHelper.printMethodName();
			WebElement modalBodyText = MODAL.findElement(By.xpath("//h4//following-sibling::p[1]"));
			return AutomationHelper.getText(modalBodyText).trim();
		}

		@FindBy(id = "proceed")
		WebElement yesIUnderstandCheckbox;

		/**
		 * Reads the <b>Yes, I understand</b> checkbox status on the <b>Delete
		 * Inspection Template</b> page.
		 * 
		 * @return boolean
		 */
		public boolean readYesIUnderstandCheckbox() {
			AutomationHelper.printMethodName();
			return AutomationHelper.readCheckboxStaus(yesIUnderstandCheckbox);
		}

		/**
		 * Sets the <b>Yes, I Understand</b> check box on the <b>Delete Inspection
		 * Template</b> with the passed in status.
		 * 
		 * @param desiredStatus
		 */
		public void setYesIUnderstandCheckbox(boolean desiredStatus) {
			AutomationHelper.printMethodName();
			AutomationHelper.setCheckbox(yesIUnderstandCheckbox, desiredStatus);
		}

	}

}
