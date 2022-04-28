package testCases.inspectionTemplates;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pageFactories.DashboardPageFactory;
import pageFactories.InspectionTemplates.InspectionTemplatesPageFactory;
import pageFactories.InspectionTemplates.InspectionTemplatesQuestionsPageFactory;
import testCases.BaseTestScriptConfig;
import testCases.modularTests.LoginMod;
import utilities.ExcelDataConfig;

/**
 * 
 * This Test Script performs the act of creating a new Inspection Template,
 * ensuring that the template is created correctly, and then deleting it. It
 * will ensure that the deletion worked correctly and the template is removed.
 * 
 * @author Jesse Childress
 *
 */
public class InspectionTemplateQuestionsCreationDeletion extends BaseTestScriptConfig {

	/**
	 * Constructor
	 */
	public InspectionTemplateQuestionsCreationDeletion() {

	}

	String inspectionTemplateName;

	// Global because we need them in multiple methods
	boolean formLayout;
	boolean tableLayout;

	@Test(dataProvider = "InspectionTemplate")
	public void createInspectionTemplate(String inspectionTemplateName) {

		ExcelDataConfig templateDatafile = getExcelFile("Inspection Template - Questions.xlsx", "Templates");

		// Making a global variable for use elsewhere
		this.inspectionTemplateName = inspectionTemplateName;

		int rowIndex = templateDatafile.getRowIndex("Inspection Template Name", inspectionTemplateName);

		// Log into the application as an Admin in the correct division
		LoginMod login = new LoginMod("Admin");
		login.login(DIVISION);

		/*
		 * NAVIGATE TO INSPECTION TEMPLATES
		 */
		DashboardPageFactory dashboardPage = new DashboardPageFactory();
		dashboardPage.clickInspectionTemplates();

		/*
		 * INSPECTION TEMPLATES PAGE
		 */

		// If the inspection template already exist, lets delete it
		deleteExistingInspection();

		// If the last method ran successfully, we should be back on the Inspection
		// Templates Page. If it did not run at all, we need to start here anyway.
		InspectionTemplatesPageFactory inspectionTemplatesPage = new InspectionTemplatesPageFactory();

		// Ensure that the Template is not already in the table. If so, stop processing
		// throw an error.
		boolean projectExists = inspectionTemplatesPage.getInspectionTemplatesTable().isRowInTable("Name",
				inspectionTemplateName);
		Assert.assertEquals(projectExists, false, "Inspection Template - Project Exists Already");

		// Click the Add Inspection Template button
		inspectionTemplatesPage.clickAddInspectionTemplate();

		// The Add Inspection Template popup is displayed
		inspectionTemplatesPage.getAddInspectionTemplatePopup().setNameInput(inspectionTemplateName);
		inspectionTemplatesPage.getAddInspectionTemplatePopup().clickOKOnPopup();

		/*
		 * INSPECTION TEMPLATES QUESTIONS PAGE
		 */

		InspectionTemplatesQuestionsPageFactory inspectionQuestionsPage = new InspectionTemplatesQuestionsPageFactory();
		inspectionQuestionsPage.clickAddQuestionGroup();// This is the blank box in which we start the addition of the
														// form.

		/*
		 * ADD A QUESTION GROUP MODAL DISPLAYED
		 */
		String questionGroupName = templateDatafile.getData(rowIndex,
				templateDatafile.getColumnIndex("Question Group Name"));
		formLayout = Boolean
				.valueOf(templateDatafile.getData(rowIndex, templateDatafile.getColumnIndex("Form Layout")));
		tableLayout = Boolean
				.valueOf(templateDatafile.getData(rowIndex, templateDatafile.getColumnIndex("Table Layout")));
		inspectionQuestionsPage.getAddAQuestionGroupModal().setNameInput(questionGroupName);
		inspectionQuestionsPage.getAddAQuestionGroupModal().setFormLayoutRadiobutton(formLayout);
		inspectionQuestionsPage.getAddAQuestionGroupModal().setTableLayoutRadiobutton(tableLayout);

		inspectionQuestionsPage.getAddAQuestionGroupModal().clickOKOnModal();

		/*
		 * BACK TO TEMPLATE QUESTIONS PAGE
		 */

		/*
		 * ADD A QUESTION MODAL DISPLAYED
		 */
		populateTemplateQuestions(questionGroupName);

		validateTemplateData(questionGroupName);

		deleteExistingInspection();

	}

	/**
	 * Method to validate the template with data.
	 * 
	 * @param questionGroupName
	 */
	private void validateTemplateData(String questionGroupName) {

		// This gives an opportunity for the table to refresh, as it is super
		// slow sometimes.
		driver.navigate().refresh();

		// Obtain a reference to the main Inspection Templates page factory.
		InspectionTemplatesPageFactory inspectionTemplatesMainPage = new InspectionTemplatesPageFactory();

		// Click the project to open up the Inspection Templates Questions page
		inspectionTemplatesMainPage.getInspectionTemplatesTable().clickRow("Name", inspectionTemplateName);

		// Ensure that you are selecting the right template group.
		InspectionTemplatesQuestionsPageFactory questionsPage = new InspectionTemplatesQuestionsPageFactory();

		// Get the datasheet with the questions on it.
		ExcelDataConfig questionsFile = getExcelFile("Inspection Template - Questions.xlsx", "Questions");

		// Grab the row count so that we can ensure that our loop runs correctly.
		int rowCount = questionsFile.getRowCount();

		// Loop through each row and grab the values. Then assert them for accuracy.
		for (int i = 1; i <= rowCount; i++) {

			// Create Variables
			String questionType = questionsFile.getData(i, questionsFile.getColumnIndex("Question Type"));
//			String options = file.getData(i, file.getColumnIndex("Options"));
			String questionLabel = questionsFile.getData(i, questionsFile.getColumnIndex("Question Label"));
			String description = questionsFile.getData(i, questionsFile.getColumnIndex("Description"));
			String questionWidth = questionsFile.getData(i, questionsFile.getColumnIndex("Question Width"));

			boolean required = Boolean.valueOf(questionsFile.getData(i, questionsFile.getColumnIndex("Required")));
			boolean carryOver = Boolean.valueOf(questionsFile.getData(i, questionsFile.getColumnIndex("Carry Over")));
			boolean enableCommentsNotes = Boolean
					.valueOf(questionsFile.getData(i, questionsFile.getColumnIndex("Enable Comments/Notes")));
			boolean defaultToDateOfCurrentInspection = Boolean.valueOf(
					questionsFile.getData(i, questionsFile.getColumnIndex("Default To Date of Current Inspection")));
			boolean defaultToDateOfPreviousInspection = Boolean.valueOf(
					questionsFile.getData(i, questionsFile.getColumnIndex("Default To Date of Previous Inspection")));
			boolean defaultToInspectorUserName = Boolean
					.valueOf(questionsFile.getData(i, questionsFile.getColumnIndex("Default To Inspector User Name")));
			boolean defaultToProjectName = Boolean
					.valueOf(questionsFile.getData(i, questionsFile.getColumnIndex("Default To Project Name")));
			boolean defaultToClientName = Boolean
					.valueOf(questionsFile.getData(i, questionsFile.getColumnIndex("Default To Client Name")));
			boolean featurePostStormEvent = Boolean
					.valueOf(questionsFile.getData(i, questionsFile.getColumnIndex("Feature: Post-storm Event")));
			boolean featureInspectionDateFeature = Boolean.valueOf(
					questionsFile.getData(i, questionsFile.getColumnIndex("Feature: Inspection Date Feature")));
			boolean featureInspectionType = Boolean
					.valueOf(questionsFile.getData(i, questionsFile.getColumnIndex("Feature: Inspection Type")));

			Reporter.log("Performing assertion for " + inspectionTemplateName + " - " + questionGroupName + " - "
					+ questionType, true);

			// This look will operate based on checking each individual "Question Label"
			questionsPage.clickEditForQuestion(questionGroupName, questionLabel);

			/*
			 * The Edit Question modal is displayed. Perform asserts for each field.
			 */

			// If the question type drop down is disabled, lets not check it. This is
			// because we can select certain requirements, e.g. Feature: Inspection Type
			// check box, which disables this feature.
			if (questionsPage.getEditQuestionModal().isQuestionTypeDrownDownEnabled()) {

				Assert.assertEquals(questionsPage.getEditQuestionModal().readQuestionTypeDropDown(), questionType,
						"Inspection Template - Edit Question - Question Type");
			}

			Assert.assertEquals(questionsPage.getEditQuestionModal().readQuestionLabelInput(), questionLabel,
					"Inspection Template - Edit Question - Question Label");
			Assert.assertEquals(questionsPage.getEditQuestionModal().readDescriptionInput(), description,
					"Inspection Template - Edit Question - Description");

			// This will only be present with Form Layout. Question Width doesn't show
			// otherwise.
			if (formLayout) {
				Assert.assertEquals(questionsPage.getEditQuestionModal().readQuestionWidthInput(), questionWidth,
						"Inspection Template - Edit Question - Question Width");
			}

			Assert.assertEquals(questionsPage.getEditQuestionModal().readRequiredCheckbox(), required,
					"Inspection Template - Edit Question - Required");
			Assert.assertEquals(questionsPage.getEditQuestionModal().readCarryOverCheckbox(), carryOver,
					"Inspection Template - Edit Question - Carry Over");
			Assert.assertEquals(questionsPage.getEditQuestionModal().readEnableCommentsNotesCheckbox(),
					enableCommentsNotes, "Inspection Template - Edit Question - Enable Comments");
			Assert.assertEquals(questionsPage.getEditQuestionModal().readDefaultToDateOfCurrentInspectionCheckbox(),
					defaultToDateOfCurrentInspection,
					"Inspection Template - Edit Question - Default Date To Current Inspection");
			Assert.assertEquals(questionsPage.getEditQuestionModal().readDefaultToDateOfPreviousInspectionCheckbox(),
					defaultToDateOfPreviousInspection,
					"Inspection Template - Edit Question - Default Date To Previous Inspection");
			Assert.assertEquals(questionsPage.getEditQuestionModal().readDefaultToInspectorUserNameCheckbox(),
					defaultToInspectorUserName, "Inspection Template - Edit Question - Default To Inspector User Name");
			Assert.assertEquals(questionsPage.getEditQuestionModal().readDefaultToProjectNameCheckbox(),
					defaultToProjectName, "Inspection Template - Edit Question - Default to Project Name");
			Assert.assertEquals(questionsPage.getEditQuestionModal().readDefaultToClientNameCheckbox(),
					defaultToClientName, "Inspection Template - Edit Question - Default to Client Name");
			Assert.assertEquals(questionsPage.getEditQuestionModal().readFeaturePostStormEventCheckbox(),
					featurePostStormEvent, "Inspection Template - Edit Question - Feature: Post-storm event");
			Assert.assertEquals(questionsPage.getEditQuestionModal().readFeatureInspectionDateFeatureCheckbox(),
					featureInspectionDateFeature,
					"Inspection Template - Edit Question - Feature: Inspection Date Feature");
			Assert.assertEquals(questionsPage.getEditQuestionModal().readFeatureInspectionTypeCheckbox(),
					featureInspectionType, "Inspection Template - Edit Question - Feature: Inspection Type");

			questionsPage.getEditQuestionModal().clickCancelOnModal();

		}

		questionsPage.clickInspectionTemplates();

	}

	/**
	 * Method to populate the Template Questions.
	 * 
	 * @param questionGroupName
	 */
	private void populateTemplateQuestions(String questionGroupName) {

		InspectionTemplatesQuestionsPageFactory page = new InspectionTemplatesQuestionsPageFactory();

		ExcelDataConfig file = getExcelFile("Inspection Template - Questions.xlsx", "Questions");

		int rowCount = file.getRowCount();

		// Click on the Add Questions + icon
		page.clickAddQuestionGroup(questionGroupName);

		for (int i = 1; i <= rowCount; i++) {

			// Create Variables
			String questionType = file.getData(i, file.getColumnIndex("Question Type"));
//			String options = file.getData(i, file.getColumnIndex("Options"));
			String questionLabel = file.getData(i, file.getColumnIndex("Question Label"));
			String description = file.getData(i, file.getColumnIndex("Description"));
			String questionWidth = file.getData(i, file.getColumnIndex("Question Width"));

			boolean required = Boolean.valueOf(file.getData(i, file.getColumnIndex("Required")));
			boolean carryOver = Boolean.valueOf(file.getData(i, file.getColumnIndex("Carry Over")));
			boolean enableCommentsNotes = Boolean
					.valueOf(file.getData(i, file.getColumnIndex("Enable Comments/Notes")));
			boolean defaultToDateOfCurrentInspection = Boolean
					.valueOf(file.getData(i, file.getColumnIndex("Default To Date of Current Inspection")));
			boolean defaultToDateOfPreviousInspection = Boolean
					.valueOf(file.getData(i, file.getColumnIndex("Default To Date of Previous Inspection")));
			boolean defaultToInspectorUserName = Boolean
					.valueOf(file.getData(i, file.getColumnIndex("Default To Inspector User Name")));
			boolean defaultToProjectName = Boolean
					.valueOf(file.getData(i, file.getColumnIndex("Default To Project Name")));
			boolean defaultToClientName = Boolean
					.valueOf(file.getData(i, file.getColumnIndex("Default To Client Name")));
			boolean featurePostStormEvent = Boolean
					.valueOf(file.getData(i, file.getColumnIndex("Feature: Post-storm Event")));
			boolean featureInspectionDateFeature = Boolean
					.valueOf(file.getData(i, file.getColumnIndex("Feature: Inspection Date Feature")));
			boolean featureInspectionType = Boolean
					.valueOf(file.getData(i, file.getColumnIndex("Feature: Inspection Type")));

			page.getAddAQuestionModal().selectQuestionTypeDropDown(questionType);
			page.getAddAQuestionModal().setQuestionLabelInput(questionLabel);
			page.getAddAQuestionModal().setDescriptionInput(description);

			// Can only add the Width if the type is FORM
			if (formLayout) {
				page.getAddAQuestionModal().setQuestionWidthInput(questionWidth);
			}

			page.getAddAQuestionModal().setRequiredCheckbox(required);
			page.getAddAQuestionModal().setCarryOverCheckbox(carryOver);
			page.getAddAQuestionModal().setEnableCommentsNotesCheckbox(enableCommentsNotes);
			page.getAddAQuestionModal().setDefaultToDateOfCurrentInspectionCheckbox(defaultToDateOfCurrentInspection);
			page.getAddAQuestionModal().setDefaultToDateOfPreviousInspectionCheckbox(defaultToDateOfPreviousInspection);
			page.getAddAQuestionModal().setDefaultToInspectorUserNameCheckbox(defaultToInspectorUserName);
			page.getAddAQuestionModal().setDefaultToProjectNameCheckbox(defaultToProjectName);
			page.getAddAQuestionModal().setDefaultToClientNameCheckbox(defaultToClientName);
			page.getAddAQuestionModal().setFeaturePostStormEventCheckbox(featurePostStormEvent);
			page.getAddAQuestionModal().setFeatureInspectionDateFeatureCheckbox(featureInspectionDateFeature);
			page.getAddAQuestionModal().setFeatureInspectionTypeCheckbox(featureInspectionType);

			// Click OK on the modal.
			page.getAddAQuestionModal().clickOKOnModal();

			page = new InspectionTemplatesQuestionsPageFactory();

			// If it's the last record, don't click add again.
			if (!(i == rowCount)) {
				page.clickAddQuestionGroup(questionGroupName);
			}

		}

		page.clickInspectionTemplates();
	}

	/**
	 * Utility method to perform deletes when an existing Inspection Template is
	 * already on the page.
	 * 
	 * @param inspectionTemplateName
	 */
	private void deleteExistingInspection() {

		// Assume that we are on the Inspection Templates Page
		InspectionTemplatesPageFactory inspectionTemplatesPage = new InspectionTemplatesPageFactory();

		boolean projectExists = inspectionTemplatesPage.getInspectionTemplatesTable().isRowInTable("Name",
				inspectionTemplateName);

		if (projectExists) {

			// Click the project to open up the Inspection Templates Questions page
			inspectionTemplatesPage.getInspectionTemplatesTable().clickRow("Name", inspectionTemplateName);

			InspectionTemplatesQuestionsPageFactory inspectionTemplateQuestionsPage = new InspectionTemplatesQuestionsPageFactory();
			inspectionTemplateQuestionsPage.clickDelete();

			// The "Delete Inspection Modal" is displayed. Ensure it is rendering as
			// expected.
			// Read Modal Title
			String modalTitle = "Delete Inspection Template";
			Assert.assertEquals(inspectionTemplateQuestionsPage.getDeleteInspectionTemplateModal().readModalTitle(),
					modalTitle, "Delete Inspection Template - Modal Title");

			// Read Modal Warning
			String modalWarning = "Warning: This cannot be undone";
			Assert.assertEquals(inspectionTemplateQuestionsPage.getDeleteInspectionTemplateModal().readModalWarning(),
					modalWarning, "Delete Inspection Template - Modal Warning");

			// Read Modal Body
			String modalBody = "Are your sure that you want to delete the " + inspectionTemplateName
					+ " inspection template?";
			Assert.assertEquals(inspectionTemplateQuestionsPage.getDeleteInspectionTemplateModal().readModalBodyText(),
					modalBody, "Delete Inspection Templage - Modal Body");

			// Set the checkbox
			inspectionTemplateQuestionsPage.getDeleteInspectionTemplateModal().setYesIUnderstandCheckbox(true);

			// Click Delete
			inspectionTemplateQuestionsPage.getDeleteInspectionTemplateModal().clickDelete();

			// Left on the Inspection Templates page.

		} else {
			Reporter.log("The project " + inspectionTemplateName + " doesn't exist. We will not need to delete it.",
					true);
		}

	}

	@DataProvider(name = "InspectionTemplate")
	private String[] inspectionTemplates() {
		ExcelDataConfig templateWorksheet = getExcelFile("Inspection Template - Questions.xlsx", "Templates");

		// Get the row count
		int rowCount = templateWorksheet.getRowCount();

		List<String> templateList = new ArrayList<String>();

		if (rowCount >= 1) {

			for (int i = 1; i <= rowCount; i++) {

				// get each individual line of the datasheet
				templateList.add(templateWorksheet.getData(i, "Inspection Template Name"));
			}

			if ((templateList.size() < 1)) {

				Reporter.log("There are no values in the Inspection Template - Questions.xlsx / Templates worksheet.", true);
				System.exit(1);
			}
		}
		return templateList.stream().toArray(String[]::new);
	}

}