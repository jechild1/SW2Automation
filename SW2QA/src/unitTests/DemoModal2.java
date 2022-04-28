package unitTests;

import org.testng.annotations.Test;

import pageFactories.DashboardPageFactory;
import pageFactories.LoginPageFactory;
import pageFactories.Clients.ClientContactsPageFactory;
import pageFactories.Clients.ClientPageFactory;
import testCases.BaseTestScriptConfig;
import utilities.AutomationHelper;

public class DemoModal2 extends BaseTestScriptConfig {

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

		clientPage.getClientsTable().clickRow("Name", "00test00");
		
		//New page
		ClientContactsPageFactory clientContactsPage = new ClientContactsPageFactory();
		
		clientContactsPage.clickDelete();
		
		AutomationHelper.waitSeconds(5);
		
		clientContactsPage.getDeleteClientPopup().clickCancelOnPopup();
		
		AutomationHelper.waitSeconds(7);
		
		
	}
}
