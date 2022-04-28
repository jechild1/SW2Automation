package testCases.modularTests.Inspections;

import org.openqa.selenium.ElementNotInteractableException;

import pageFactories.DashboardPageFactory;
import pageFactories.Project.ProjectPageFactory;
import pageFactories.Project.Inspections.ProjectInspectionsPageFactory;
import pageFactories.Project.Inspections.InspectionDetails.InspectionFindingsDetailsPageFactory;
import pageFactories.Project.Inspections.InspectionDetails.InspectionMapPageFactory;
import pageFactories.Project.Inspections.InspectionQuestions.InspectionQuestionsPageFactory;
import testCases.BaseTestScriptConfig;
import testCases.modularTests.LogoutAndLoginAsDifferentUserMod;
import utilities.ExcelDataConfig;

/**
 * MODULAR SCRIPT:<br>
 * This class performs the process of populating a Finding for a given
 * Inspection Type. The constructor will require a finding type of: <br>
 * <li>Achievement
 * <li>Maintenance Item
 * <li>Corrective Action
 * 
 * NOTE: This assumes that we are on the Inspection Findings page.
 * 
 * @author Jesse Childress
 *
 */
public class CreateNewInspectionMod extends BaseTestScriptConfig {

	String projectName;
	String findingType;
	String complianceSignatureRole;
	static String INITIAL_ROLE;

	/**
	 * The class constructor will accept a Finding Type. This is limited to
	 * Achievement, Maintenance Item, or Corrective Action. If one of these values
	 * are not passed in, the constructor will throw an error.<br>
	 * Note: This class expects that we are on the Dashboards page. 
	 * <li>Achievement
	 * <li>Maintenance Item
	 * <li>Corrective Action
	 * 
	 * @param projectName - e.g. SQA Paper Processing Facility
	 * @param inspectionQuestionsForm = e.g. SQA Inspection Template Alpha
	 * @param findingType - e.g. Maintenance Item
	 * @param complianceSignatureRole - e.g. "Client User"
	 * @param projectFile - This is necessary because we need to be able to specify
	 *                    the work sheet we want to use on the Project.xlsx file.
	 */
	public void createNewInspectionMod(String projectName, String inspectionQuestionsForm, String findingType, String complianceSignatureRole,
			ExcelDataConfig projectsFile) {

		this.projectName = projectName;
		this.findingType = findingType;
		this.complianceSignatureRole = complianceSignatureRole;
		// Search for the project

		// Click New Routine Inspection

		/*
		 * Dashboards Page
		 */
		DashboardPageFactory dashboardPage = new DashboardPageFactory();

		INITIAL_ROLE = dashboardPage.getUserRole();

		dashboardPage.clickProject();

		/*
		 * Project Page
		 * 
		 */
		ProjectPageFactory projectPage = new ProjectPageFactory();

		// Find a random row for projects where the data used is true.
//		int rowIndex = projectsFile.getRowIndex("Project Name", projectName);

		// Obtain a the data from the datasheet.
//			String projectName = projectsFile.getData(rowIndex, projectsFile.getColumnIndex("Project Name"));

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

		/*
		 * DELETE ANY EXISTING INSPECTIONS
		 */
		ProjectInspectionsPageFactory projectInspectionsMainPage = new ProjectInspectionsPageFactory();
		if (projectInspectionsMainPage.areInspectionsPresentOnPage()) {
			performInspectionDeletes();
		}

		// ALL INSPECTIONS PAGE
		projectInspectionsMainPage = new ProjectInspectionsPageFactory();
		projectInspectionsMainPage.clickNewRoutineInspection();

		/*
		 * INSPECTIONS - INSPECTION QUESTIONS
		 */
		InspectionQuestionsMod inspectionQuestionsModular = new InspectionQuestionsMod(inspectionQuestionsForm,
				findingType);
		inspectionQuestionsModular.setAndValidateInspectionQuestions();

		/*
		 * INSPECTION - FINDINGS
		 */
		// FINDINGS - ACHIEVEMENT
		InspectionQuestionsPageFactory inspectionQuestionsPage = new InspectionQuestionsPageFactory();
		inspectionQuestionsPage.clickFindingsTab();

		InspectionFindingsMod inspectionFindingsModular = new InspectionFindingsMod("Achievement");
		inspectionFindingsModular.createAndValidateFinding();

		// FINDINGS - CORRECTIVE ACTION
		InspectionFindingsDetailsPageFactory inspectionFindingsDetailsPage = new InspectionFindingsDetailsPageFactory();
		inspectionFindingsDetailsPage.clickFindingsTab();

		inspectionFindingsModular = new InspectionFindingsMod("Corrective Action");
		inspectionFindingsModular.createAndValidateFinding();

		// FINDINGS - MAINTENANCE ITEM
		inspectionFindingsDetailsPage = new InspectionFindingsDetailsPageFactory();
		inspectionFindingsDetailsPage.clickFindingsTab();

		inspectionFindingsModular = new InspectionFindingsMod("Maintenance Item");
		inspectionFindingsModular.createAndValidateFinding();

		/*
		 * INSPECTION - MAP
		 */
		// For now, lets just navigate here, check the title, and move on.
		inspectionFindingsDetailsPage = new InspectionFindingsDetailsPageFactory();
		inspectionFindingsDetailsPage.clickMapTab();

		InspectionMapPageFactory inspectionMapPage = new InspectionMapPageFactory();
		inspectionMapPage.clickCertificationTab();

		/*
		 * INSPECTION - CERTIFICATION
		 */
		InspectionCertificationMod inspectionCertificationMod = new InspectionCertificationMod(complianceSignatureRole);
		inspectionCertificationMod.performCertOnTemplate(inspectionQuestionsForm);

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
