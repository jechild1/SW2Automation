package unitTests;

import org.testng.annotations.Test;

import pageFactories.DashboardPageFactory;
import pageFactories.LoginPageFactory;
import pageFactories.Regulations.RegulationsPageFactory;
import testCases.BaseTestScriptConfig;

public class UnitTestRegulation extends BaseTestScriptConfig {

	@Test
	public void unitTest() {

		LoginPageFactory loginPage = new LoginPageFactory();

		loginPage.loadPage();
		
		loginPage.setEmail("jesse_admin@gmail.com");
		loginPage.setPassword("password");
		loginPage.clickLogin();
		
		DashboardPageFactory dashboardPage = new DashboardPageFactory();
		
		dashboardPage.clickRegulations();
		
	
		RegulationsPageFactory regulationsPage = new RegulationsPageFactory();
		
//		regulationsPage.getRegulationsTable().clickCell("Colorado - Aurora");
		regulationsPage.getRegulationsTable().clickRow("Name", "SQA Regulation Charlie");
		
		
//		RegulationDetailsPageFactory regulationDetailsPage = new RegulationDetailsPageFactory();
//		
//		
//		regulationDetailsPage.getRegulationDetailsTable().selectMenuInCell("Name", "infinite-digital-logo.jpg", "Rename");
//		
//		AutomationHelper.wait(5);
		
		
		
		
		

		
		
		

	}

}
