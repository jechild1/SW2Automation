package testCases.inspectionTemplates;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pageFactories.DashboardPageFactory;
import pageFactories.InspectionTemplates.InspectionTemplatesCertificationPageFactory;
import pageFactories.InspectionTemplates.InspectionTemplatesPageFactory;
import pageFactories.InspectionTemplates.InspectionTemplatesQuestionsPageFactory;
import pageFactories.UserManuals.UserManualsPageFactory;
import testCases.BaseTestScriptConfig;
import testCases.modularTests.AddInspectionTemplateToDivisionMod;
import testCases.modularTests.AddNewProjectMod;
import testCases.modularTests.DeleteProjectMod;
import testCases.modularTests.LoginMod;
import testCases.modularTests.Inspections.CreateNewInspectionMod;
import utilities.ExcelDataConfig;

/**
 * 
 * This Test Script performs performs the following:
 * <li>Creates a new Inspection Template (Questions)
 * <li>Validates questions are saved correctly
 * <li>Creates a new Inspection Template (Certification)
 * <li>Validates Certifications are modified accordingly and correctly
 * <li>Builds a new project for the template to be associated with
 * <li>Validates the project is created successfully
 * <li>Attempts to do an inspection / finding.
 * <li>Validates that the finding is correct.
 * <li>Signs the inspection (logging out and in with a user with permissions
 * that can sign completion)
 * <li>Tear down the project that was created
 * <li>Tear down the Inspection Template that was created.
 * 
 * 
 * @author Jesse Childress
 *
 */
public class InspectionTemplateCertificationCreationDeletion extends BaseTestScriptConfig {

	/**
	 * Constructor
	 */
	public InspectionTemplateCertificationCreationDeletion() {

	}

	String inspectionTemplateName;

	String projectName = "Temp Project - Inspection Template Cert";

	// Global because we need them in multiple methods
	boolean formLayout;
	boolean tableLayout;
	int rowIndex;

	@Test(dataProvider = "InspectionTemplate", priority = 0)

	public void createInspectionTemplateCertification(String inspectionTemplateName) {

		ExcelDataConfig templateDatafile = getExcelFile("Inspection Template - Certification.xlsx", "Templates");

		// Making a global variable for use elsewhere
		this.inspectionTemplateName = inspectionTemplateName;

		rowIndex = templateDatafile.getRowIndex("Inspection Template Name", inspectionTemplateName);

		// Log into the application as an Admin in the correct division
		// Must log in as an Admin or Division Admin to perform template creations.
		LoginMod login = new LoginMod("Admin");
		login.login(DIVISION);

		/*
		 * DELETE EXISTING TEST PROJECT, IF NECESSARY
		 */
		// There can be the scenario where a test fails and the project doesn't get
		// deleted as expected. If this happens, we need to delete it before we begin
		// our test.
//		deleteExistingProject(inspectionTemplateName);
			DeleteProjectMod deleteProject = new DeleteProjectMod();
		deleteProject.deleteProject(projectName);

		/*
		 * NAVIGATE TO INSPECTION TEMPLATES
		 */
		DashboardPageFactory dashboardPage = new DashboardPageFactory();
		dashboardPage.clickInspectionTemplates();

		/*
		 * INSPECTION TEMPLATES PAGE
		 */

		// If the inspection template already exist, lets delete it
		
		//TODO - Uncomment
		deleteExistingInspectionTemplate();

		// This is a work around. We need to refresh the page when we delete because the
		// table can be slow to remove the item. However, when we force a refresh, the
		// Division changes to the default (Colorado). We must select the correct
		// division again. (a few lines below)
		driver.navigate().refresh();
		// If the last method ran successfully, we should be back on the Inspection
		// Templates Page. If it did not run at all, we need to start here anyway.
		InspectionTemplatesPageFactory inspectionTemplatesPage = new InspectionTemplatesPageFactory();
		inspectionTemplatesPage.selectDivision(DIVISION);

		// Ensure that the Template is not already in the table. If so, stop processing
		// throw an error.
		
		boolean projectExists = inspectionTemplatesPage.getInspectionTemplatesTable().isRowInTable("Name",
				inspectionTemplateName);
		
		//TODO - Uncomment
		Assert.assertEquals(projectExists, false, "Inspection Template - Project Exists Already");

		/*
		 * Add Inspection Template and populate question groups
		 */
		//TODO - uncomment
		addQuestionGroups(templateDatafile);
		
		/*
		 * Add inspection template certification data.
		 */
		//TODO - Uncomment
		addInspectionTemplateCertification();

		
		// The steps below are performed with @TEST / priorities

		/*
		 * addInspectionTemplateToDivision()
		 */
		addInspectionTemplateToDivision();
		

		/*
		 * createProject()
		 */
		createProject();

		/*
		 * createProjectInspection()
		 * Note: This performs the entire inspection / compliance
		 */
		createProjectInspection();
		
		/*
		 * deleteProject();
		 */
		
		
		

	}

	/**
	 * Utility method to:<br>
	 * <li>Start on the Inspection Templates Page
	 * <li>Add the name of the Inspection Template
	 * <li>Loop through each row in the datasheet and gather a list of Question
	 * Groups
	 * <li>For each question group, add the questions that are relevant.
	 * <li>Validate that the questions were saved correctly
	 * 
	 * @param templateDatafile
	 */
	private void addQuestionGroups(ExcelDataConfig templateDatafile) {

		InspectionTemplatesPageFactory inspectionTemplatesPage = new InspectionTemplatesPageFactory();
		// Click the Add Inspection Template button
		inspectionTemplatesPage.clickAddInspectionTemplate();

		// The Add Inspection Template popup is displayed
		inspectionTemplatesPage.getAddInspectionTemplatePopup().setNameInput(inspectionTemplateName);
		inspectionTemplatesPage.getAddInspectionTemplatePopup().clickOKOnPopup();

		/*
		 * INSPECTION TEMPLATES QUESTIONS PAGE
		 */

		// Here, we must loop through all of the question groups and the questions for
		// each one.

		// This list will hold all of the question groups that are in the datasheet
		List<String> questionGroupNames = new ArrayList<String>();

		// Loop through the Templates work sheet on the current Inspection Template
		// Name.
		// Store all of the UNIQUE question Group Names.
		int questionGroupRowCount = templateDatafile.getRowCount();

		for (int i = 1; i <= questionGroupRowCount; i++) {
			// Add all question group names to the list
			questionGroupNames.add(templateDatafile.getData(i, "Question Group Name"));
		}

		// Remove the duplicates in the list, if any.
		questionGroupNames.stream().distinct().collect(Collectors.toList());

		// Now, for each question group, add the questions that pertain to that group
		for (String currentQuestionGroup : questionGroupNames) {

			InspectionTemplatesQuestionsPageFactory inspectionQuestionsPage = new InspectionTemplatesQuestionsPageFactory();

			// This is the blank box in which we start the addition of the form.
			inspectionQuestionsPage.clickAddQuestionGroup();

			/*
			 * ADD A QUESTION GROUP MODAL DISPLAYED
			 */

			// Assign variables
			formLayout = Boolean
					.valueOf(templateDatafile.getData(rowIndex, templateDatafile.getColumnIndex("Form Layout")));
			tableLayout = Boolean
					.valueOf(templateDatafile.getData(rowIndex, templateDatafile.getColumnIndex("Table Layout")));
			inspectionQuestionsPage.getAddAQuestionGroupModal().setNameInput(currentQuestionGroup);
			inspectionQuestionsPage.getAddAQuestionGroupModal().setFormLayoutRadiobutton(formLayout);
			inspectionQuestionsPage.getAddAQuestionGroupModal().setTableLayoutRadiobutton(tableLayout);

			inspectionQuestionsPage.getAddAQuestionGroupModal().clickOKOnModal();

			// Back to Template Questions Page

			// Populate the template questions with data
			populateTemplateQuestions(currentQuestionGroup);

			// Validate that the data saved by re-opening the modal and checking.
			validateTemplateData(currentQuestionGroup);

			// Go back to the Inspection Template for future processing.
			inspectionTemplatesPage = new InspectionTemplatesPageFactory();
			inspectionTemplatesPage.getInspectionTemplatesTable().clickRow("Name", inspectionTemplateName);

		}

		// Go back to the Dashboard
		inspectionTemplatesPage.clickDashboard();
	}

	/**
	 * Method to populate the Template Questions.
	 * 
	 * @param questionGroupName
	 */
	private void populateTemplateQuestions(String questionGroupName) {

		InspectionTemplatesQuestionsPageFactory page = new InspectionTemplatesQuestionsPageFactory();

		ExcelDataConfig file = getExcelFile("Inspection Template - Certification.xlsx", "Questions");

		// Get an array of questions for the passed in questionGroupName

		int rowCount = file.getRowCount();

		List<Integer> questionListRowIndex = new ArrayList<Integer>();

		for (int i = 1; i <= rowCount; i++) {

			String currentQuestionGroupName = file.getData(i, "Question Group Name");
			if (questionGroupName.equalsIgnoreCase(currentQuestionGroupName)) {

				questionListRowIndex.add(i);
			}
		}

		// Click on the Add Questions + icon
		page.clickAddQuestionGroup(questionGroupName);

		// for (int i = 1; i <= rowCount; i++) {
		int loopCount = 0;
		for (int currentRowIndex : questionListRowIndex) {

			// Create Variables
			String questionType = file.getData(currentRowIndex, file.getColumnIndex("Question Type"));
			String questionLabel = file.getData(currentRowIndex, file.getColumnIndex("Question Label"));
			String description = file.getData(currentRowIndex, file.getColumnIndex("Description"));
			String questionWidth = file.getData(currentRowIndex, file.getColumnIndex("Question Width"));

			boolean required = Boolean.valueOf(file.getData(currentRowIndex, file.getColumnIndex("Required")));
			boolean carryOver = Boolean.valueOf(file.getData(currentRowIndex, file.getColumnIndex("Carry Over")));
			boolean enableCommentsNotes = Boolean
					.valueOf(file.getData(currentRowIndex, file.getColumnIndex("Enable Comments/Notes")));
			boolean defaultToDateOfCurrentInspection = Boolean.valueOf(
					file.getData(currentRowIndex, file.getColumnIndex("Default To Date of Current Inspection")));
			boolean defaultToDateOfPreviousInspection = Boolean.valueOf(
					file.getData(currentRowIndex, file.getColumnIndex("Default To Date of Previous Inspection")));
			boolean defaultToInspectorUserName = Boolean
					.valueOf(file.getData(currentRowIndex, file.getColumnIndex("Default To Inspector User Name")));
			boolean defaultToProjectName = Boolean
					.valueOf(file.getData(currentRowIndex, file.getColumnIndex("Default To Project Name")));
			boolean defaultToClientName = Boolean
					.valueOf(file.getData(currentRowIndex, file.getColumnIndex("Default To Client Name")));
			boolean featurePostStormEvent = Boolean
					.valueOf(file.getData(currentRowIndex, file.getColumnIndex("Feature: Post-storm Event")));
			boolean featureInspectionDateFeature = Boolean
					.valueOf(file.getData(currentRowIndex, file.getColumnIndex("Feature: Inspection Date Feature")));
			boolean featureInspectionType = Boolean
					.valueOf(file.getData(currentRowIndex, file.getColumnIndex("Feature: Inspection Type")));

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

			loopCount++;

			// If it's the last record, don't click add again.
			if (!(loopCount == questionListRowIndex.size())) {
				page.clickAddQuestionGroup(questionGroupName);
			}

		}

		page.clickInspectionTemplates();
	}

	/**
	 * Method to validate the template with data.
	 * 
	 * @param questionGroupName
	 */
	private void validateTemplateData(String questionGroupName) {

		// This gives an opportunity for the table to refresh, as it is super
		// slow sometimes. Next code is funny, but works.

		// Obtain a reference to the main Inspection Templates page factory.
		InspectionTemplatesPageFactory inspectionTemplatesMainPage = new InspectionTemplatesPageFactory();

		inspectionTemplatesMainPage.clickUserManuals();
		UserManualsPageFactory userManualPage = new UserManualsPageFactory();
		userManualPage.clickInspectionTemplates();

		inspectionTemplatesMainPage = new InspectionTemplatesPageFactory();

		// Click the project to open up the Inspection Templates Questions page
		inspectionTemplatesMainPage.getInspectionTemplatesTable().clickRow("Name", inspectionTemplateName);

		// Ensure that you are selecting the right template group.
		InspectionTemplatesQuestionsPageFactory questionsPage = new InspectionTemplatesQuestionsPageFactory();

		// Get the datasheet with the questions on it.
		ExcelDataConfig questionsFile = getExcelFile("Inspection Template - Certification.xlsx", "Questions");

		// Grab the row count so that we can ensure that our loop runs correctly.
		int rowCount = questionsFile.getRowCount();

		List<Integer> questionListRowIndex = new ArrayList<Integer>();

		for (int i = 1; i <= rowCount; i++) {

			String currentQuestionGroupName = questionsFile.getData(i, "Question Group Name");
			if (questionGroupName.equalsIgnoreCase(currentQuestionGroupName)) {

				questionListRowIndex.add(i);
			}
		}

		for (int currentRowIndex : questionListRowIndex) {
			// Loop through each row and grab the values. Then assert them for accuracy.
			// Create Variables
			String questionType = questionsFile.getData(currentRowIndex, questionsFile.getColumnIndex("Question Type"));
			String questionLabel = questionsFile.getData(currentRowIndex,
					questionsFile.getColumnIndex("Question Label"));
			String description = questionsFile.getData(currentRowIndex, questionsFile.getColumnIndex("Description"));
			String questionWidth = questionsFile.getData(currentRowIndex,
					questionsFile.getColumnIndex("Question Width"));

			boolean required = Boolean
					.valueOf(questionsFile.getData(currentRowIndex, questionsFile.getColumnIndex("Required")));
			boolean carryOver = Boolean
					.valueOf(questionsFile.getData(currentRowIndex, questionsFile.getColumnIndex("Carry Over")));
			boolean enableCommentsNotes = Boolean.valueOf(
					questionsFile.getData(currentRowIndex, questionsFile.getColumnIndex("Enable Comments/Notes")));
			boolean defaultToDateOfCurrentInspection = Boolean.valueOf(questionsFile.getData(currentRowIndex,
					questionsFile.getColumnIndex("Default To Date of Current Inspection")));
			boolean defaultToDateOfPreviousInspection = Boolean.valueOf(questionsFile.getData(currentRowIndex,
					questionsFile.getColumnIndex("Default To Date of Previous Inspection")));
			boolean defaultToInspectorUserName = Boolean.valueOf(questionsFile.getData(currentRowIndex,
					questionsFile.getColumnIndex("Default To Inspector User Name")));
			boolean defaultToProjectName = Boolean.valueOf(
					questionsFile.getData(currentRowIndex, questionsFile.getColumnIndex("Default To Project Name")));
			boolean defaultToClientName = Boolean.valueOf(
					questionsFile.getData(currentRowIndex, questionsFile.getColumnIndex("Default To Client Name")));
			boolean featurePostStormEvent = Boolean.valueOf(
					questionsFile.getData(currentRowIndex, questionsFile.getColumnIndex("Feature: Post-storm Event")));
			boolean featureInspectionDateFeature = Boolean.valueOf(questionsFile.getData(currentRowIndex,
					questionsFile.getColumnIndex("Feature: Inspection Date Feature")));
			boolean featureInspectionType = Boolean.valueOf(
					questionsFile.getData(currentRowIndex, questionsFile.getColumnIndex("Feature: Inspection Type")));

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
	 * Utility method to perform deletes when an existing Inspection Template is
	 * already on the page.
	 * 
	 * @param inspectionTemplateName
	 */
	private void deleteExistingInspectionTemplate() {

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

//	/**
//	 * Utility method to delete a project that exists. This can happen because a
//	 * test errors out and doesn't get cleaned up.
//	 */
//	private void deleteExistingProject(String projectName) {
//
//		// Assume a start from the dashboard
//		DashboardPageFactory dashboardPage = new DashboardPageFactory();
//
//		dashboardPage.clickProject();
//
//		ProjectPageFactory projectPage = new ProjectPageFactory();
//
//		projectPage.setSearchProjects(projectName);
//
//		// If there is a project card with this, open it and delete it.
//
//		if (projectPage.isProjectCardPresent(projectName)) {
//			projectPage.clickProjectCard(projectName);
//
//			// Project Inspection page is displayed
//			ProjectInspectionsPageFactory projectInspectionsPage = new ProjectInspectionsPageFactory();
//
//			projectInspectionsPage.clickDeleteMenu();
//
//			// Modal is displayed
//			projectInspectionsPage.getDeleteProjectModal().clickYesIUnderstand();
//
//		}
//
//		// After the delete, were back on the project page.
//		projectPage = new ProjectPageFactory();
//		projectPage.clickDashboard();
//
//	}

	/**
	 * This test will add an inspection template to a division. <br>
	 * Note: this calls a modular script.
	 */
//	@Test(priority = 1)
	public void addInspectionTemplateToDivision() {
		// Call the modular script for adding an inspection template to a Division
		AddInspectionTemplateToDivisionMod addInspectionTemplateToDivision = new AddInspectionTemplateToDivisionMod();
		addInspectionTemplateToDivision.addInspectionTemplateToDivision(inspectionTemplateName);
	}

//	@Test(priority = 2)
	public void createProject() {

		AddNewProjectMod createNewProject = new AddNewProjectMod();

		// Note: The addNewProject method needs a parameter of the Project Name. For
		// this test, it will be the same name as the Inspection Template. This will be
		// found in the Projects.xlsx / ProjectsForAddition work sheet.

		createNewProject.addNewProject(inspectionTemplateName);

	}

//	@Test(priority = 3)
	public void createProjectInspection() {

		ExcelDataConfig projectsFile = getExcelFile("Projects.xlsx", "ProjectsForAddition");

		CreateNewInspectionMod createNewInspection = new CreateNewInspectionMod();

		// Hard coded variables here.
		String projectName = "Temp Project - Inspection Template Cert";
		String inspectionFormType = "SQA Test Template for Certification";

		//This method creates a new inspection, looks at the maps, and even performs compliance.
		createNewInspection.createNewInspectionMod(projectName, inspectionFormType, "Post-Storm", "Admin", projectsFile);

	}

//	@Test(priority = 4)
	public void deleteProject() {

		ExcelDataConfig projectDataFile = getExcelFile("Projects.xlsx", "ProjectsForAddition");

		// Find the project name, which will be associated with the Inspection Form Type
		int rowIndex = projectDataFile.getRowIndex("Inspection Form Type", inspectionTemplateName);
		String projectName = projectDataFile.getData(rowIndex, projectDataFile.getColumnIndex("Project Name"));

		DeleteProjectMod deleteProject = new DeleteProjectMod();
		deleteProject.deleteProject(projectName);
	}

	@AfterMethod
	public void afterMethod(ITestResult result) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH.mm.ss");
		if (ITestResult.FAILURE == result.getStatus()) {
			ScreenshotOnFailure screenshot = new ScreenshotOnFailure();
			screenshot.captureScreenshot(driver,
					result.getName() + " - " + formatter.format(java.time.LocalDateTime.now()));
		}

		// TODO - Commented this out as adding multiple methods in a test would fail
		// cause driver was null.
		// driver.quit();
	}

	/**
	 * Data provider for the main method.
	 * 
	 * @return String []
	 */
	@DataProvider(name = "InspectionTemplate")
	private String[] inspectionTemplatesDataProvider() {
		ExcelDataConfig templateWorksheet = getExcelFile("Inspection Template - Certification.xlsx", "Templates");

		// Get the row count
		int rowCount = templateWorksheet.getRowCount();

		List<String> templateList = new ArrayList<String>();

		if (rowCount >= 1) {

			for (int i = 1; i <= rowCount; i++) {

				// get each individual line of the datasheet
				templateList.add(templateWorksheet.getData(i, "Inspection Template Name"));
			}

			// This removes any duplicates in the list.
			templateList = templateList.stream().distinct().collect(Collectors.toList());

			if ((templateList.size() < 1)) {

				Reporter.log(
						"There are no values in the Inspection Template - Certification.xlsx / Templates worksheet.",
						true);
				System.exit(1);
			}
		}
		return templateList.stream().toArray(String[]::new);
	}

	/**
	 * Utility method to add data to an Inspection Template Certification page.
	 */
	private void addInspectionTemplateCertification() {

		// Assume we start on the Dashboards page.
		DashboardPageFactory dashboardPage = new DashboardPageFactory();
		dashboardPage.clickInspectionTemplates();
		
		//Inspection Template Page selected. Select the template.
		InspectionTemplatesPageFactory inspectionTemplatesPage = new InspectionTemplatesPageFactory();
		inspectionTemplatesPage.getInspectionTemplatesTable().clickRow("Name", inspectionTemplateName);
		
		InspectionTemplatesQuestionsPageFactory inspectionQuestionsPage = new InspectionTemplatesQuestionsPageFactory();
		inspectionQuestionsPage.clickCertificationTab();
		
		//Now we start from the Certification tab
		InspectionTemplatesCertificationPageFactory certPage = new InspectionTemplatesCertificationPageFactory();

		certPage.clickEditSignatures();

		// Get data from the datasheet.
		ExcelDataConfig certDataSheet = getExcelFile("Inspection Certification.xlsx",
				"SQA Test Template For Certifica");
		int rowIndex = 1;

		// Inspection Certification Data
		String inspectorLabel = certDataSheet.getData(rowIndex, certDataSheet.getColumnIndex("Inspector_Label"));
		String inspectorUserFieldLabel = certDataSheet.getData(rowIndex,
				certDataSheet.getColumnIndex("Inspector_User Field Label"));
		String inspectorRoleFieldLabel = certDataSheet.getData(rowIndex,
				certDataSheet.getColumnIndex("Inspector_Role Field Label"));

		// Compliance Certification Data
		String complianceLabel = certDataSheet.getData(rowIndex, certDataSheet.getColumnIndex("Compliance_Label"));
		String complianceUserFieldLabel = certDataSheet.getData(rowIndex,
				certDataSheet.getColumnIndex("Compliance_User Field Label"));
		String complianceRoleFieldLabel = certDataSheet.getData(rowIndex,
				certDataSheet.getColumnIndex("Compliance_Role Field Label"));

		/*
		 * Set fields for INSPECTION CERTIFICATION in the modal.
		 */
		// Edit buttons are enabled for processing now.
		certPage.clickEditInspectionCertificationPencil();

		// The modal is displayed
		// Edit signature line is displayed.
		// Set fields
		certPage.getEditSignatureLineModal().setLabel(inspectorLabel);
		certPage.getEditSignatureLineModal().setUserFieldLabel(inspectorUserFieldLabel);
		certPage.getEditSignatureLineModal().setRoleFieldLabel(inspectorRoleFieldLabel);
		certPage.getEditSignatureLineModal().clickOKOnModal();

		// Check the fields on the main Certification page
		certPage = new InspectionTemplatesCertificationPageFactory();
		Assert.assertEquals(certPage.readInspectionCertificationLabel(), inspectorLabel,
				"Certification Page - Inspection Certification - Label");
		Assert.assertEquals(certPage.readInspectionCertificationRole(), inspectorRoleFieldLabel,
				"Certification Page - Inspection Certification - Role");
		Assert.assertEquals(certPage.readInspectionCertificationName(), inspectorUserFieldLabel,
				"Certification Page - Inspection Certification - User");

		/*
		 * Set fields for COMPLIANCE CERTIFICATION in the modal.
		 */
		// Edit buttons are enabled for processing now.
		certPage.clickEditComplianceCertificationPencil();

		// The modal is displayed
		// Edit signature line is displayed.
		// Set fields
		certPage.getEditSignatureLineModal().setLabel(complianceLabel);
		certPage.getEditSignatureLineModal().setUserFieldLabel(complianceUserFieldLabel);
		certPage.getEditSignatureLineModal().setRoleFieldLabel(complianceRoleFieldLabel);
		certPage.getEditSignatureLineModal().clickOKOnModal();

		// Check the fields on the main Certification page
		certPage = new InspectionTemplatesCertificationPageFactory();
		Assert.assertEquals(certPage.readComplianceCertificationLabel(), complianceLabel,
				"Certification Page - Compliance Certification - Label");
		Assert.assertEquals(certPage.readComplianceCertificationRole(), complianceRoleFieldLabel,
				"Certification Page - Compliance Certification - Role");
		Assert.assertEquals(certPage.readComplianceCertificationName(), complianceUserFieldLabel,
				"Certification Page - Compliance Certification - User");

		// After the asserts check out, go back to the Dashboards page for future
		// processing
		certPage.clickSave();
		certPage.clickDashboard();

	}

}