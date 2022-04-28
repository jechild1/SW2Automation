package unitTests;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageFactories.DashboardPageFactory;
import pageFactories.LoginPageFactory;
import pageFactories.Project.ProjectPageFactory;
import pageFactories.Project.Inspections.ProjectInspectionsPageFactory;
import pageFactories.Project.ProjectDocuments.ProjectDocumentsPageFactory;
import testCases.BaseTestScriptConfig;
import utilities.AutomationHelper;

public class UnitTestProject extends BaseTestScriptConfig {

	@Test
	public void unitTest() {

		LoginPageFactory loginPage = new LoginPageFactory();

		loginPage.loadPage();
		
		loginPage.setEmail("jesse_admin@gmail.com");
		loginPage.setPassword("password");
		loginPage.clickLogin();
		
		DashboardPageFactory dashboardPage = new DashboardPageFactory();
		
	
		
		//Click Projects
		dashboardPage.clickProject();
		
		ProjectPageFactory projectsPage = new ProjectPageFactory();
		
		
		projectsPage.setSearchProjects("jr");
		String projectName = "JRC Concert Arena";
		
		projectsPage.clickProjectCard(projectName);
		
		ProjectInspectionsPageFactory projectInspections = new ProjectInspectionsPageFactory();
		projectInspections.clickPrintSWMPMenu();
		
		//Ensure the modal title is correct.
		Assert.assertEquals(projectInspections.getPrintSWMPModal().readModalTitle(), "Print SWMP", "Modal Title Test");
		
		projectInspections.getPrintSWMPModal().clickPrint();
		projectInspections.getPrintSWMPModal().clickClose();

		
		//Now, Print Project Inspections
		projectInspections.clickPrintInspectionsMenu();
		Assert.assertEquals(projectInspections.getPrintInspectionsModal().readModalTitle(),  "Print Inspections", "Inspections Modal Title Test");
		projectInspections.getPrintInspectionsModal().clickPrint();
		projectInspections.getPrintInspectionsModal().clickXOnModal();
		AutomationHelper.waitSeconds(3);
		
		
		
		

		

		

	}

}
