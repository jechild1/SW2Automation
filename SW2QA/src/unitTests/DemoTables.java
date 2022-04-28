package unitTests;

import org.testng.annotations.Test;

import pageFactories.DashboardPageFactory;
import pageFactories.LoginPageFactory;
import pageFactories.Clients.ClientContactsPageFactory;
import pageFactories.Clients.ClientPageFactory;
import testCases.BaseTestScriptConfig;
import utilities.AutomationHelper;

public class DemoTables extends BaseTestScriptConfig {

	@Test
	public void unitTest() {

		LoginPageFactory loginPage = new LoginPageFactory();

		loginPage.loadPage();

		loginPage.setEmail("jesse_admin@gmail.com");
		loginPage.setPassword("password");
		loginPage.clickLogin();

		DashboardPageFactory dashboardPage = new DashboardPageFactory();

		dashboardPage.clickClient();

		ClientPageFactory clientPage = new ClientPageFactory();
		
		//set the table with JR to reduce scope
		clientPage.setSearchClient("JR");
		
			
		//TODO - This readTableRowValue will need to be re-developed with pagination.
//		System.out.println("Table Value: " + clientPage.getClientsTable().readTableRowValue("Name", "JRC Partners, LLC", "Address", true));

		clientPage.getClientsTable().clickRow("Name", "JRC Partners, LLC");
		
		AutomationHelper.waitSeconds(3);
		

		
		
	}
}
