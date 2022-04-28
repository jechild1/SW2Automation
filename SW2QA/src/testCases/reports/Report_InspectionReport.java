package testCases.reports;

import org.openqa.selenium.ElementNotInteractableException;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pageFactories.DashboardPageFactory;
import pageFactories.Project.ProjectPageFactory;
import pageFactories.Project.Inspections.ProjectInspectionsPageFactory;
import pageFactories.Project.Inspections.InspectionDetails.InspectionCertificationPageFactory;
import pageFactories.Project.Inspections.InspectionDetails.InspectionFindingsDetailsPageFactory;
import pageFactories.Project.Inspections.InspectionDetails.InspectionMapPageFactory;
import pageFactories.Project.Inspections.InspectionQuestions.InspectionQuestionsPageFactory;
import testCases.BaseTestScriptConfig;
import testCases.modularTests.LoginMod;
import testCases.modularTests.LogoutAndLoginAsDifferentUserMod;
import testCases.modularTests.Inspections.InspectionCertificationMod;
import testCases.modularTests.Inspections.InspectionFindingsMod;
import testCases.modularTests.Inspections.InspectionQuestionsMod;
import testCases.modularTests.Inspections.InspectionsDeleteAllInspectionsMod;
import utilities.ExcelDataConfig;

/**
 * Test to produce an <b>Inspection Report<b> for a given project.
 * 
 * @author Jesse Childress
 *
 */
public class Report_InspectionReport extends BaseTestScriptConfig {

	public static String INITIAL_ROLE;

	@Test (dataProvider = "printConfigurations")
	public void createInspectionReport(String printConfiguration) {

		/*
		 * Login to the system
		 */
		LoginMod login = new LoginMod("Inspector");
		login.login(DIVISION);
		INITIAL_ROLE = login.getUserRole();
		
		Reporter.log("Current Print Configuration: " + printConfiguration, true);

		/*
		 * Dashboards Page
		 */
		DashboardPageFactory dashboardPage = new DashboardPageFactory();
		dashboardPage.clickProject();

		/*
		 * Project Page
		 * 
		 */
		ProjectPageFactory projectPage = new ProjectPageFactory();

		// Search for a random project that has been used.
		// Get a reference to the datasheet for projects
		ExcelDataConfig projectsFile = getExcelFile("Projects.xlsx", "Projects");

		// Find a random row for projects where the data used is true.
		int rowIndex = projectsFile.getRowIndex("Project Name", "SQA Paper Processing Facility");

		// Obtain a the data from the datasheet.
		String projectName = projectsFile.getData(rowIndex, projectsFile.getColumnIndex("Project Name"));

		// Search for the project
		projectPage.setSearchProjects(projectName);
		// Click on the project card. This will take you to All Inspections tab
		projectPage.clickProjectCard(projectName);

		/*
		 * INSPECTION FINDINGS
		 */

		// First, check to see if there are existing inspection for the project. If so,
		// we need to delete them and start fresh. This is because you can't start an
		// inspection if one already is open and in progress. This can happen often if
		// we have a failed test.

		// See if inspections exist to be deleted
		ProjectInspectionsPageFactory projectInspectionsMainPage = new ProjectInspectionsPageFactory();
		if (projectInspectionsMainPage.areInspectionsPresentOnPage()) {
			performInspectionDeletes();
		}

		// ALL INSPECTIONS
		projectInspectionsMainPage = new ProjectInspectionsPageFactory();
		projectInspectionsMainPage.clickNewRoutineInspection();

		/*
		 * INSPECTIONS - INSPECTION QUESTIONS
		 */
		InspectionQuestionsMod inspectionQuestionsModular = new InspectionQuestionsMod("SQA Inspection Template Alpha",
				"Routine");
		inspectionQuestionsModular.setAndValidateInspectionQuestions();

		/*
		 * INSPECTION - FINDINGS
		 */
		// FINDINGS - ACHIEVEMENT
		InspectionQuestionsPageFactory inspectionQuestionsPage = new InspectionQuestionsPageFactory();
		inspectionQuestionsPage.clickFindingsTab();

//		InspectionFindingsMod inspectionFindingsModular = new InspectionFindingsMod("Maintenance Item");
//		inspectionFindingsModular.createAndValidateFinding();
//
//		// FINDINGS - CORRECTIVE ACTION
//		InspectionFindingsDetailsPageFactory inspectionFindingsDetailsPage = new InspectionFindingsDetailsPageFactory();
//		inspectionFindingsDetailsPage.clickFindingsTab();
//
//		inspectionFindingsModular = new InspectionFindingsMod("Corrective Action");
//		inspectionFindingsModular.createAndValidateFinding();

		// FINDINGS - MAINTENANCE ITEM
//		inspectionFindingsDetailsPage = new InspectionFindingsDetailsPageFactory();
//		inspectionFindingsDetailsPage.clickFindingsTab();

		InspectionFindingsMod inspectionFindingsModular = new InspectionFindingsMod("Achievement");
		inspectionFindingsModular.createAndValidateFinding();

		/*
		 * INSPECTION - MAP
		 */
		// For now, lets just navigate here, check the title, and move on.
		InspectionFindingsDetailsPageFactory inspectionFindingsDetailsPage = new InspectionFindingsDetailsPageFactory();

		inspectionFindingsDetailsPage.clickMapTab();

		InspectionMapPageFactory inspectionMapPage = new InspectionMapPageFactory();
		inspectionMapPage.clickCertificationTab();

		/*
		 * INSPECTION - CERTIFICATION
		 */
		InspectionCertificationMod inspectionCertificationMod = new InspectionCertificationMod("Client User");
		inspectionCertificationMod.performCertOnTemplate("SQA Inspection Template Alpha");

		/*
		 * PRINT INSPECTION
		 */

		performPrintInspection(printConfiguration);

	}

	/**
	 * Utility method to ensure that the perform sets and asserts for the Print
	 * Inspection modal.
	 */
	private void performPrintInspection(String printConfiguration) {

		// Last step left us on the Certification tab.
		InspectionCertificationPageFactory inspectionCertPage = new InspectionCertificationPageFactory();
		inspectionCertPage.clickPrintInspection();

		// Pull data from the Print datasheet
		ExcelDataConfig printFile = getExcelFile("Print.xlsx", "PrintInspection");
//		int rowIndex = printFile.getRandomRowIndex();
		int rowIndex = printFile.getRowIndex("TestType", printConfiguration);

		boolean entireInspection = Boolean
				.valueOf(printFile.getData(rowIndex, printFile.getColumnIndex("ReportType_EntireInspection")));
		boolean correctiveActionLogOnly = Boolean
				.valueOf(printFile.getData(rowIndex, printFile.getColumnIndex("ReportType_CorrectiveActionLogOnly")));
		boolean signaturePageOnly = Boolean
				.valueOf(printFile.getData(rowIndex, printFile.getColumnIndex("ReportType_SignaturePageOnly")));
		boolean findingLocations = Boolean
				.valueOf(printFile.getData(rowIndex, printFile.getColumnIndex("MapOptions_FindingLocations")));
		boolean controlMeasures = Boolean
				.valueOf(printFile.getData(rowIndex, printFile.getColumnIndex("MapOptions_ControlMeasures")));
		boolean includeChangelog = Boolean
				.valueOf(printFile.getData(rowIndex, printFile.getColumnIndex("MapOptions_IncludeChangelog")));
		boolean findingImages = Boolean
				.valueOf(printFile.getData(rowIndex, printFile.getColumnIndex("PDFOptions_FindingImages")));

		// The Print Inspection modal is displayed.
		Assert.assertEquals(inspectionCertPage.getPrintInspectionPopup().readPopupHeader(), "Print Inspection",
				"Print Inspection - Page Title");

		// Perform sets

		inspectionCertPage.getPrintInspectionPopup().setEntireInspection(entireInspection);
		inspectionCertPage.getPrintInspectionPopup().setCorrectiveActionLogOnly(correctiveActionLogOnly);
		inspectionCertPage.getPrintInspectionPopup().setSignaturePageOnly(signaturePageOnly);

		// If the Signature Page Only option is selected, all other fields that are NOT
		// of type "Report" will not be present on the modal.
		if (!signaturePageOnly) {
			inspectionCertPage.getPrintInspectionPopup().setFindingLocations(findingLocations);
			inspectionCertPage.getPrintInspectionPopup().setControlMeasures(controlMeasures);
			inspectionCertPage.getPrintInspectionPopup().setIncludeChangelog(includeChangelog);
			inspectionCertPage.getPrintInspectionPopup().setFindingImages(findingImages);
		}

		// Perform Reads - Note: This is kind of a cheat, since we are doing NOTHING to
		// commit the data. We can't save the settings or launch the modal again to see
		// that our selections stay.
		Assert.assertEquals(inspectionCertPage.getPrintInspectionPopup().readEntireInspection(), entireInspection,
				"Print Inspection - Entire Inspection");
		Assert.assertEquals(inspectionCertPage.getPrintInspectionPopup().readCorrectiveActionLogOnly(),
				correctiveActionLogOnly, "Print Inspection - Corrective Action Log Only");
		Assert.assertEquals(inspectionCertPage.getPrintInspectionPopup().readSignaturePageOnly(), signaturePageOnly,
				"Print Inspection - Signature Page Only");

		// If the Signature Page Only option is selected, all other fields that are NOT
		// of type "Report" will not be present on the modal.
		if (!signaturePageOnly) {
			Assert.assertEquals(inspectionCertPage.getPrintInspectionPopup().readFindingLocations(), findingLocations,
					"Print Inspection - Finding Locations");
			Assert.assertEquals(inspectionCertPage.getPrintInspectionPopup().readControlMeasures(), controlMeasures,
					"Print Inspection - Control Measures");
			Assert.assertEquals(inspectionCertPage.getPrintInspectionPopup().readIncludeChangelog(), includeChangelog,
					"Print Inspection - Include Changelog");
			Assert.assertEquals(inspectionCertPage.getPrintInspectionPopup().readFindingImages(), findingImages,
					"Print Inspection - Finding Images");
		}

		// Click the Print button. This can take a moment.
		inspectionCertPage.getPrintInspectionPopup().clickPrint();

		Assert.assertEquals(inspectionCertPage.getPrintInspectionPopup().isDownloadLinkPresent(), true,
				"Print Inspection - Download link present");
		
		//Download the file. Ensure that it saves in the correct folder.
		inspectionCertPage.getPrintInspectionPopup().clickDownloadLink();
			
	}

	/**
	 * Utility method to delete all inspections for a given project when they exist.
	 * This can be necessary when we have a test that failed. Can't start a new
	 * inspection until a previous one has been completed.
	 */
	private void performInspectionDeletes() {

		// If logged in as a non-managerial role, we need to log out and back in as a
		// role that can perform deletions
		switch (INITIAL_ROLE) {

		case "BMP Contractor":
		case "CDOT PE/Designee":
		case "Client User":
		case "Divisions Admin":
		case "Engineering Admin":
		case "Inspector":
		case "Regulator":
			// If any of the above roles, log out and back in as an role in which can delete
			// inspections
			LogoutAndLoginAsDifferentUserMod logOutAndInMod = new LogoutAndLoginAsDifferentUserMod("Client Inspector",
					true);

			logOutAndInMod.logOutAndLogin();

			// Once logged back in, we will be on the same page per the last method
			// Perform the deletes
			InspectionsDeleteAllInspectionsMod deleteAllInspections = new InspectionsDeleteAllInspectionsMod();
			deleteAllInspections.deleteAllInspections();

			// After all inspections are deleted, return to the original role
			logOutAndInMod = new LogoutAndLoginAsDifferentUserMod(INITIAL_ROLE, true);
			logOutAndInMod.logOutAndLogin();
			break;

		case "Area / Assistant Manager":
		case "Client Inspector":
		case "Regional Manager":
		case "SWMP Admin":
		case "Regional Manager (External)":
			deleteAllInspections = new InspectionsDeleteAllInspectionsMod();
			deleteAllInspections.deleteAllInspections();
			break;
		default:
			throw new ElementNotInteractableException("There are no roles that match " + INITIAL_ROLE);

		}

	}
	
	//Data Provider
	@DataProvider(name = "printConfigurations")
	private String[] getPrinterReportConfigurations(){
		
		ExcelDataConfig usersFile = getExcelFile("Print.xlsx", "PrintInspection");
		
		int rowNumber = 1;
		int columnIndex = usersFile.getColumnIndex("TestType");
		
		String[] printConfigurations = new String[usersFile.getRowCount()];
		
		for(int i = 0; i < usersFile.getRowCount(); i++) {
			
			printConfigurations[i] = usersFile.getData(rowNumber, columnIndex);
			rowNumber++;
		}
		
		return printConfigurations;
		
		

		
		
		
		
	}

}