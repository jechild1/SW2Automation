package unitTests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pageFactories.AdminPageFactory;
import pageFactories.DashboardPageFactory;
import pageFactories.InspectionTemplatesPageFactory;
import pageFactories.LegendItemPageFactory;
import pageFactories.LoginPageFactory;
import pageFactories.Clients.ClientPageFactory;
import pageFactories.Divisions.DivisionsPageFactory;
import pageFactories.Project.ProjectPageFactory;
import pageFactories.Project.Inspections.InspectionQuestions.InspectionQuestionsPageFactory;
import pageFactories.Regulations.RegulationsPageFactory;
import testCases.BaseTestScriptConfig;
import utilities.AutomationHelper;

public class DemoNavigation extends BaseTestScriptConfig {

	@Test(dataProvider = "userAccount")
	public void unitTest(String emailAddress, String password) {

		LoginPageFactory loginPage = new LoginPageFactory();

		loginPage.loadPage();
		
		loginPage.setEmail(emailAddress);
		loginPage.setPassword(password);
		loginPage.clickLogin();
		
		/*
		 * Dashboards Page Loaded
		 */
		DashboardPageFactory dashboardsPage = new DashboardPageFactory();
		dashboardsPage.clickClient();
		//There is no breadcrumb to check here for the Dashboards page
		
		/*
		 * Clients Page Loaded. 
		 */
		ClientPageFactory clientPage = new ClientPageFactory();
		Assert.assertEquals(clientPage.readPageTitleFromBreadcrumb(), "Clients");
		
		clientPage.clickDashboard();
		dashboardsPage.clickProject();
		
		
		
		/*
		 * Projects Page Loaded
		 */
		ProjectPageFactory projectPage = new ProjectPageFactory();
		Assert.assertEquals(projectPage.readPageTitleFromBreadcrumb(), "Projects");
		
		projectPage.clickDashboard();
		dashboardsPage.clickRegulations();
		
		/*
		 * Regulations Page Loaded
		 */
		RegulationsPageFactory regulationsPage = new RegulationsPageFactory();
		Assert.assertEquals(regulationsPage.readPageTitleFromBreadcrumb(), "Regulations");
		
		regulationsPage.clickDashboard();
		dashboardsPage.clickLegendItem();
		
		/*
		 * Legend Items Page Loaded
		 */
		LegendItemPageFactory legendItemPage = new LegendItemPageFactory();
		Assert.assertEquals(legendItemPage.readPageTitleFromBreadcrumb(), "Legend Items");
		
		legendItemPage.clickDashboard();
		dashboardsPage.clickAdmin();
		
		/*
		 * Admin Page Loaded
		 */
		AdminPageFactory adminPage = new AdminPageFactory();
		Assert.assertEquals(adminPage.readPageTitleFromBreadcrumb(), "Users");
		
		adminPage.clickDashboard();
		dashboardsPage.clickDivisions();
		
		/*
		 * Divisions Page Loaded
		 */
		DivisionsPageFactory divisionsPage = new DivisionsPageFactory();
		Assert.assertEquals(divisionsPage.readPageTitleFromBreadcrumb(), "Divisions");
		
		divisionsPage.clickDashboard();
		dashboardsPage.clickInspectionTemplates();
		
		/*
		 * Inspection Templates Page Loaded
		 */
		InspectionTemplatesPageFactory inspectionTemplatesPage = new InspectionTemplatesPageFactory();
		Assert.assertEquals(inspectionTemplatesPage.readPageTitleFromBreadcrumb(),  "Inspection Templates");;
		
		inspectionTemplatesPage.clickDashboard();
		
		//Show the Flex Selectors
		
		AutomationHelper.waitSeconds(3);
		
		inspectionTemplatesPage.selectDivision("SWQA Alpha");
		
		AutomationHelper.waitSeconds(3);
		
		inspectionTemplatesPage.selectDivision("Colorado");
		
		AutomationHelper.waitSeconds(3);
		
		inspectionTemplatesPage.selectNotification("Missed Inspection", "JRC Pizza Party Place", "2021-11-08");
		
		//This takes us to the projects page
		InspectionQuestionsPageFactory projectInspectionQuestions = new InspectionQuestionsPageFactory();
		
		projectInspectionQuestions.clickLogOut();
		
		
		
		
		
		
		
		
		
		
		
		
		
	}
	
	/**
	 * Returns a String[][] of the passed in role type.
	 * 
	 * @return dataProvider
	 */
	@DataProvider(name = "userAccount")
	private String[][] getUser() {
		return getUserAccount("Admin");
	}
}
