package unitTests;

import org.testng.annotations.Test;

import pageFactories.DashboardPageFactory;
import pageFactories.LoginPageFactory;
import pageFactories.Divisions.DivisionsInspectionTemplatesPageFactory;
import pageFactories.Divisions.DivisionsPageFactory;
import pageFactories.Divisions.DivisionsRegulationsPageFactory;
import pageFactories.Regulations.RegulationsPageFactory;
import testCases.BaseTestScriptConfig;

public class UnitTestDivisions extends BaseTestScriptConfig {

	@Test
	public void unitTest() {

		LoginPageFactory loginPage = new LoginPageFactory();

		loginPage.loadPage();
		
		loginPage.setEmail("jesse_admin@gmail.com");
		loginPage.setPassword("password");
		loginPage.clickLogin();
		
		DashboardPageFactory dashboardPage = new DashboardPageFactory();
		
		dashboardPage.clickDivisions();
		
		DivisionsPageFactory divisionsPage = new DivisionsPageFactory();
		
		divisionsPage.getDivisionsTable().clickRow("Name", "SWQA Alpha");
		
		//Taken to Regulations
		DivisionsRegulationsPageFactory divRegulationsPage = new DivisionsRegulationsPageFactory();
		divRegulationsPage.clickInspectionTemplatesTab();
		
		
		
		//Inspection Templates
		DivisionsInspectionTemplatesPageFactory divINspectionsPage = new DivisionsInspectionTemplatesPageFactory();
		
		divINspectionsPage.clickAddTemplates();
		
		divINspectionsPage.getAddInspectionTemplatesModal().selectInspectionTemplates("Test");
		
	

		
		
		
		
		

		
		
		

	}

}
