package testCases.projectInspections.projectInspectionsInspectionFindings;

import org.testng.annotations.Test;

import pageFactories.DashboardPageFactory;
import pageFactories.Project.ProjectPageFactory;
import testCases.BaseTestScriptConfig;
import testCases.modularTests.LoginMod;
import utilities.ExcelDataConfig;

/**
 * Test to add a <b>Routine Inspection<b> for a given project.
 * 
 * @author Jesse Childress
 *
 */
public class Project_Inspection_InspectionFindings_SQA_Alpha extends BaseTestScriptConfig {

	@Test
	public void CreateRoutineInspection() {

		/*
		 * Login to the system
		 */
		LoginMod login = new LoginMod("Inspector");
		login.login(DIVISION);

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
		
		//Search for a random project that has been used.
		// Get a reference to the datasheet for projects
		ExcelDataConfig projectsFile = getExcelFile("Projects.xlsx", "Projects");

		// Find a random row for projects where the data used is true.
		int rowIndexRandom = projectsFile.getRandomRowIndex();

		// Obtain a the data from the datasheet.
		String projectName = projectsFile.getData(rowIndexRandom, projectsFile.getColumnIndex("Project Name"));
		String client = projectsFile.getData(rowIndexRandom, projectsFile.getColumnIndex("Client"));
		String permittee = projectsFile.getData(rowIndexRandom, projectsFile.getColumnIndex("Permittee"));
		String inspectionFormType = projectsFile.getData(rowIndexRandom, projectsFile.getColumnIndex("Inspection Form Type"));
		
		String address = projectsFile.getData(rowIndexRandom, projectsFile.getColumnIndex("Address"));
		String city = projectsFile.getData(rowIndexRandom, projectsFile.getColumnIndex("City"));
		String state = projectsFile.getData(rowIndexRandom, projectsFile.getColumnIndex("State"));
		String zip = projectsFile.getData(rowIndexRandom, projectsFile.getColumnIndex("Zip"));
		String completeAddress = address + ", " + city + ", " + state + " " + zip;

		
		//Search for the project
		projectPage.setSearchProjects(projectName);
		//Click on the project card
		projectPage.clickProjectCard(projectName);
		//This will take you to All Inspections tab

	}

}