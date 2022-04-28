package testCases.projectInspections;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageFactories.DashboardPageFactory;
import pageFactories.Project.ProjectPageFactory;
import pageFactories.Project.ActiveSiteMap.ActiveSiteMapPageFactory;
import pageFactories.Project.BMPDetails.BMPDetailsPageFactory;
import pageFactories.Project.Contacts.ContactsPageFactory;
import pageFactories.Project.CorrectiveActions.CorrectiveActionsPageFactory;
import pageFactories.Project.Inspections.ProjectInspectionsPageFactory;
import pageFactories.Project.Log.LogPageFactory;
import pageFactories.Project.ProjectDocuments.ProjectDocumentsPageFactory;
import testCases.BaseTestScriptConfig;
import testCases.modularTests.LoginMod;
import utilities.ExcelDataConfig;

/**
 * Test to assert that the inspection menus (tabs) render correctly, and that the correct page loads when exercised.
 * 
 * @author Jesse Childress
 *
 */
public class Project_Inspection_AssertInspectionMenus extends BaseTestScriptConfig {

	@Test
	public void AssertInspectionMenus() {

		// Log into the system
		LoginMod login = new LoginMod(generateRandomUserRole());
		login.login(DIVISION);

		/*
		 * Dashboards page is displayed.
		 */
		DashboardPageFactory dashboardPage = new DashboardPageFactory();
		dashboardPage.clickProject();
		
		/*
		 * Projects page is displayed
		 */
		ProjectPageFactory projectPage = new ProjectPageFactory();

		//Search for a random project that has been used.
		
		// Get a reference to the datasheet for projects
		ExcelDataConfig projectsFile = getExcelFile("Projects.xlsx", "Projects");

		// Find a random row for projects where the data used is true.
//		int rowIndexRandom = projectsFile.getRandomRowIndex();
		int rowIndex = projectsFile.getRowIndex("Project Name", "SQA Four Seasons Hotel");

		// Obtain a the data from the datasheet.
		String projectName = projectsFile.getData(rowIndex, projectsFile.getColumnIndex("Project Name"));
		String client = projectsFile.getData(rowIndex, projectsFile.getColumnIndex("Client"));
		String permittee = projectsFile.getData(rowIndex, projectsFile.getColumnIndex("Permittee"));
		String inspectionFormType = projectsFile.getData(rowIndex, projectsFile.getColumnIndex("Inspection Form Type"));
		
		String address = projectsFile.getData(rowIndex, projectsFile.getColumnIndex("Address"));
		String city = projectsFile.getData(rowIndex, projectsFile.getColumnIndex("City"));
		String state = projectsFile.getData(rowIndex, projectsFile.getColumnIndex("State"));
		String zip = projectsFile.getData(rowIndex, projectsFile.getColumnIndex("Zip"));
		String completeAddress = address + ", " + city + ", " + state + " " + zip;

		
		
		
		//Search for the porject
		projectPage.setSearchProjects(projectName);
		//Click on the project card
		projectPage.clickProjectCard(projectName);
		//This will take you to All Inspections tab
		
		/*
		 * All Inspections
		 */
		
		ProjectInspectionsPageFactory allInspectionsPage = new ProjectInspectionsPageFactory();
		
		//Check Header
		Assert.assertEquals(allInspectionsPage.readProjectName(), projectName,	"Project Inspections - Project Name");
		Assert.assertEquals(allInspectionsPage.readClientName(), client, "Project Inspections - Client Name");
		Assert.assertEquals(allInspectionsPage.readAddress(), completeAddress, "Project Inspections - Address");
		
		//Check Breadcrumb
		Assert.assertEquals(allInspectionsPage.readPageTitleFromBreadcrumb(), "Projects" + " " + projectName, "Project Inspections - Breadcrumb");
		
		allInspectionsPage.clickProjectDocumentsTab();
		
		/*
		 * Project Documents
		 */
		
		ProjectDocumentsPageFactory projectDocumentsPage = new ProjectDocumentsPageFactory();
		//Check Header
		Assert.assertEquals(projectDocumentsPage.readProjectName(), projectName,	"Project Documents - Project Name");
		Assert.assertEquals(projectDocumentsPage.readClientName(), client, "Project Documents - Client Name");
		Assert.assertEquals(projectDocumentsPage.readAddress(), completeAddress, "Project Documents - Address");
		
		//Check Breadcrumb
		Assert.assertEquals(projectDocumentsPage.readPageTitleFromBreadcrumb(), "Projects" + " " + projectName, "Project Inspections - Breadcrumb");
		
		projectDocumentsPage.clickBMPDetailsTab();
		
		/*
		 * BMP Details
		 */
		
		BMPDetailsPageFactory bmpDetailsPage = new BMPDetailsPageFactory();
		//Check Header
		Assert.assertEquals(bmpDetailsPage.readProjectName(), projectName,	"BMP Details - Project Name");
		Assert.assertEquals(bmpDetailsPage.readClientName(), client, "BMP Details - Client Name");
		Assert.assertEquals(bmpDetailsPage.readAddress(), completeAddress, "BMP Details - Address");
		
		//Check Breadcrumb
		Assert.assertEquals(bmpDetailsPage.readPageTitleFromBreadcrumb(), "Projects" + " " + projectName, "BMP Details - Breadcrumb");
		
		bmpDetailsPage.clickActiveSiteMapTab();
		
		/*
		 * Active Site Map
		 */
		
		ActiveSiteMapPageFactory activeSiteMapPage = new ActiveSiteMapPageFactory();
		//Check Header
		Assert.assertEquals(activeSiteMapPage.readProjectName(), projectName,	"Active Site Maps - Project Name");
		Assert.assertEquals(activeSiteMapPage.readClientName(), client, "Active Site Maps - Client Name");
		Assert.assertEquals(activeSiteMapPage.readAddress(), completeAddress, "Active Site Maps - Address");
		
		//Check Breadcrumb
		Assert.assertEquals(activeSiteMapPage.readPageTitleFromBreadcrumb(), "Projects" + " " + projectName, "Active Site Maps - Breadcrumb");
		
		activeSiteMapPage.clickContactsTab();
		
		/*
		 * Contacts
		 */
		
		ContactsPageFactory contactsPage = new ContactsPageFactory();
		//Check Header
		Assert.assertEquals(contactsPage.readProjectName(), projectName,	"Contacts - Project Name");
		Assert.assertEquals(contactsPage.readClientName(), client, "Contacts - Client Name");
		Assert.assertEquals(contactsPage.readAddress(), completeAddress, "Contacts - Address");
		
		//Check Breadcrumb
		Assert.assertEquals(contactsPage.readPageTitleFromBreadcrumb(), "Projects" + " " + projectName, "Contacts - Breadcrumb");
		
		contactsPage.clickCorrectiveActionsTab();
		
		
		/*
		 * Corrective Actions
		 */
		
		CorrectiveActionsPageFactory correctiveActionsPage = new CorrectiveActionsPageFactory();
		//Check Header
		Assert.assertEquals(correctiveActionsPage.readProjectName(), projectName,	"Corrective Actions - Project Name");
		Assert.assertEquals(correctiveActionsPage.readClientName(), client, "Corrective Actions - Client Name");
		Assert.assertEquals(correctiveActionsPage.readAddress(), completeAddress, "Corrective Actions - Address");
		
		//Check Breadcrumb
		Assert.assertEquals(correctiveActionsPage.readPageTitleFromBreadcrumb(), "Projects" + " " + projectName, "Corrective Actions - Breadcrumb");
		
		correctiveActionsPage.clickLogTab();
		
		/*
		 * Log
		 */
		
		LogPageFactory logPage = new LogPageFactory();
		//Check Header
		Assert.assertEquals(logPage.readProjectName(), projectName,	"Log - Project Name");
		Assert.assertEquals(logPage.readClientName(), client, "Log - Client Name");
		Assert.assertEquals(logPage.readAddress(), completeAddress, "Log - Address");
		
		//Check Breadcrumb
		Assert.assertEquals(logPage.readPageTitleFromBreadcrumb(), "Projects" + " " + projectName, "Log - Breadcrumb");
		
		logPage.clickAllInspectionsTab();
		
		
		
		
		//Finally, log out of the application.
		allInspectionsPage = new ProjectInspectionsPageFactory();
		allInspectionsPage.clickLogOut();
		
		
		
		
		
		
		




	}

}
