package testCases.projectInspections;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageFactories.DashboardPageFactory;
import pageFactories.Project.ProjectPageFactory;
import pageFactories.Project.Inspections.ProjectInspectionsPageFactory;
import testCases.BaseTestScriptConfig;
import testCases.modularTests.LoginMod;
import utilities.ExcelDataConfig;

/**
 * Test to assert that the inspection card data will both render and match what
 * is in a data sheet. Specifically, this script will pick a random project from
 * the Projects.xlsx data sheet. It will navigate to the projects page, and then
 * ensure that the project / client / address information is correct.
 * 
 * @author Jesse Childress
 *
 */
public class Project_Inspection_AssertInspectionCardData extends BaseTestScriptConfig {

	@Test(priority = 0)
	public void AssertInspectionCardData() {

		// Log into the system
		LoginMod login = new LoginMod("Inspector");
		login.login(DIVISION);

		// The Dashboards page is displayed
		DashboardPageFactory dashboardPage = new DashboardPageFactory();
		dashboardPage.clickProject();

		ProjectPageFactory projectPage = new ProjectPageFactory();

		/*
		 * Search for a project (dynamically, a random one from the datasheet)
		 */
		// Get a reference to the datasheet for projects
		ExcelDataConfig projectsFile = getExcelFile("Projects.xlsx", "Projects");

		// Find a project. This can be changed.
		int rowIndex = projectsFile.getRowIndex("Project Name", "SQA Four Seasons Hotel");

		// Obtain a the data from the datasheet.
		String client = projectsFile.getData(rowIndex, projectsFile.getColumnIndex("Client"));
		String permittee = projectsFile.getData(rowIndex, projectsFile.getColumnIndex("Permittee"));
		String inspectionFormType = projectsFile.getData(rowIndex,
				projectsFile.getColumnIndex("Inspection Form Type"));
		String projectName = projectsFile.getData(rowIndex, projectsFile.getColumnIndex("Project Name"));
		String address = projectsFile.getData(rowIndex, projectsFile.getColumnIndex("Address"));
		String city = projectsFile.getData(rowIndex, projectsFile.getColumnIndex("City"));
		String state = projectsFile.getData(rowIndex, projectsFile.getColumnIndex("State"));
		String zip = projectsFile.getData(rowIndex, projectsFile.getColumnIndex("Zip"));
		String latitude = projectsFile.getData(rowIndex, projectsFile.getColumnIndex("Latitude"));
		String longitude = projectsFile.getData(rowIndex, projectsFile.getColumnIndex("Longitude"));
		String timeZone = projectsFile.getData(rowIndex, projectsFile.getColumnIndex("Time Zone"));
		String inspector = projectsFile.getData(rowIndex, projectsFile.getColumnIndex("Inspector"));
		String anticipateProjectTimelineBegin = projectsFile.getData(rowIndex,
				projectsFile.getColumnIndex("Anticipated Project Timeline Begin"));
		String anticipateProjectTimelineEnd = projectsFile.getData(rowIndex,
				projectsFile.getColumnIndex("Anticipated Project Timeline End"));
		String nextRoutineInspectionDate = projectsFile.getData(rowIndex,
				projectsFile.getColumnIndex("Next Routine Inspection Date"));
		String routineInspectionInterval = projectsFile.getData(rowIndex,
				projectsFile.getColumnIndex("Routine Inspection Interval"));
		String regulations = projectsFile.getData(rowIndex, projectsFile.getColumnIndex("Regulations"));

		boolean activeSiteMaps = Boolean
				.valueOf(projectsFile.getData(rowIndex, projectsFile.getColumnIndex("Active Site Maps")));
		boolean constructionDocsCivilDrawings = Boolean.valueOf(
				projectsFile.getData(rowIndex, projectsFile.getColumnIndex("Construction Docs Civil Drawings")));
		boolean soilReports = Boolean
				.valueOf(projectsFile.getData(rowIndex, projectsFile.getColumnIndex("Soil Reports")));
		boolean delegationLetters = Boolean
				.valueOf(projectsFile.getData(rowIndex, projectsFile.getColumnIndex("Delegation Letters")));
		boolean endangeredSpecies = Boolean
				.valueOf(projectsFile.getData(rowIndex, projectsFile.getColumnIndex("Endangered Species")));
		boolean historicalProperties = Boolean
				.valueOf(projectsFile.getData(rowIndex, projectsFile.getColumnIndex("Historical Properties")));
		boolean permits = Boolean.valueOf(projectsFile.getData(rowIndex, projectsFile.getColumnIndex("Permits")));
		boolean qualificationCertifications = Boolean.valueOf(
				projectsFile.getData(rowIndex, projectsFile.getColumnIndex("Qualifications Certifications")));
		boolean localStormwaterRegulations = Boolean.valueOf(
				projectsFile.getData(rowIndex, projectsFile.getColumnIndex("Local Stormwater Regulations")));
		boolean misc = Boolean.valueOf(projectsFile.getData(rowIndex, projectsFile.getColumnIndex("MISC")));
		boolean swpNarrativeMaps = Boolean
				.valueOf(projectsFile.getData(rowIndex, projectsFile.getColumnIndex("SWP Narrative Maps")));

		String completeAddress = address + ", " + city + ", " + state + " " + zip;

		// First, search for the project on the Project Main Page
		projectPage.setSearchProjects(projectName);

		/*
		 * Read the CARD information on the Project Main Page
		 */
		Assert.assertEquals(projectPage.readProjectCardProjectName(projectName), projectName,
				"Projects - Project Name");
		Assert.assertEquals(projectPage.readProjectCardClient(projectName), client, "Projects - Client Name");
		Assert.assertEquals(projectPage.readProjectCardAddress(projectName), completeAddress, "Projects - Address");

		// Click on the project
		projectPage.clickProjectCard(projectName);

		// ProjectInspections page is shown
		ProjectInspectionsPageFactory projectInspectionsPage = new ProjectInspectionsPageFactory();

		/*
		 * Read the project inspection header information (client, project, address)
		 */

		Assert.assertEquals(projectInspectionsPage.readProjectName(), projectName,
				"Project Inspections - Project Name");
		Assert.assertEquals(projectInspectionsPage.readClientName(), client, "Project Inspections - Client Name");
		Assert.assertEquals(projectInspectionsPage.readAddress(), completeAddress, "Project Inspections - Address");

		/*
		 * Open the project up in an edit to ensure that the data for the project is
		 * correct
		 */
		// TODO - Methods are not yet created for this.

		projectInspectionsPage.clickLogOut();

	}

}
