package testCases.projectInspections;

import org.openqa.selenium.ElementNotInteractableException;
import org.testng.annotations.Test;

import pageFactories.DashboardPageFactory;
import pageFactories.Project.ProjectPageFactory;
import pageFactories.Project.Inspections.ProjectInspectionsPageFactory;
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
 * Test to add a <b>Routine Inspection<b> for a given project.
 * 
 * @author Jesse Childress
 *
 */
public class Project_Inspection_CreateRoutineInspection extends BaseTestScriptConfig {
	
	public static String INITIAL_ROLE;

	@Test
	public void createRoutineInspection() {

		/*
		 * Login to the system
		 */
		LoginMod login = new LoginMod("Inspector");
		login.login(DIVISION);
		INITIAL_ROLE = login.getUserRole();

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
//		int rowIndexRandom = projectsFile.getRandomRowIndex();
		int rowIndex = projectsFile.getRowIndex("Project Name", "SQA Four Seasons Hotel");

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
		 *  INSPECTIONS - INSPECTION QUESTIONS
		 */
		InspectionQuestionsMod inspectionQuestionsModular = new InspectionQuestionsMod("SQA Inspection Template Alpha",
				"Routine");
		inspectionQuestionsModular.setAndValidateInspectionQuestions();

		/*
		 *  INSPECTION - FINDINGS
		 */
		// FINDINGS - ACHIEVEMENT
		InspectionQuestionsPageFactory inspectionQuestionsPage = new InspectionQuestionsPageFactory();
		inspectionQuestionsPage.clickFindingsTab();

		InspectionFindingsMod inspectionFindingsModular = new InspectionFindingsMod("Maintenance Item");
		inspectionFindingsModular.createAndValidateFinding();

		// FINDINGS - CORRECTIVE ACTION
		InspectionFindingsDetailsPageFactory inspectionFindingsDetailsPage = new InspectionFindingsDetailsPageFactory();
		inspectionFindingsDetailsPage.clickFindingsTab();

		inspectionFindingsModular = new InspectionFindingsMod("Corrective Action");
		inspectionFindingsModular.createAndValidateFinding();

		// FINDINGS - MAINTENANCE ITEM
		inspectionFindingsDetailsPage = new InspectionFindingsDetailsPageFactory();
		inspectionFindingsDetailsPage.clickFindingsTab();

		inspectionFindingsModular = new InspectionFindingsMod("Achievement");
		inspectionFindingsModular.createAndValidateFinding();

		/*
		 *  INSPECTION - MAP
		 */
		// For now, lets just navigate here, check the title, and move on.
		inspectionFindingsDetailsPage = new InspectionFindingsDetailsPageFactory();
		inspectionFindingsDetailsPage.clickMapTab();

		InspectionMapPageFactory inspectionMapPage = new InspectionMapPageFactory();
		inspectionMapPage.clickCertificationTab();

		/*
		 *  INSPECTION - CERTIFICATION
		 */
		InspectionCertificationMod inspectionCertificationMod = new InspectionCertificationMod("Client User");
		inspectionCertificationMod.performCertOnTemplate("SQA Inspection Template Alpha");
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

}