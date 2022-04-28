package unitTests;

import org.testng.annotations.Test;

import pageFactories.DashboardPageFactory;
import pageFactories.LoginPageFactory;
import pageFactories.Clients.ClientPageFactory;
import testCases.BaseTestScriptConfig;
import utilities.AutomationHelper;

public class UnitTestClient extends BaseTestScriptConfig {

	@Test
	public void unitTest() {

		LoginPageFactory loginPage = new LoginPageFactory();

		loginPage.loadPage();
		
		loginPage.setEmail("jesse_admin@gmail.com");
		loginPage.setPassword("password");
		loginPage.clickLogin();
		
		DashboardPageFactory dashboardPage = new DashboardPageFactory();
		
		dashboardPage.clickClient();
			
	
		
		//Click Clients
		ClientPageFactory clientPage = new  ClientPageFactory();
		
//		clientPage.clickAddClient();
//		clientPage.getCreateNewClientModal().setClientName("Frank");
////		AutomationHelper.wait(5);
//
//		clientPage.getCreateNewClientModal().clickCancel();
		
		String clientNameForSearch = "JRC";
			
//		clientPage.setSearchClient(clientNameForSearch);
		
		
		clientPage.getClientsTable().clickRow("Name", "All Pro Capital");
		
		AutomationHelper.waitSeconds(10);
		
		
		
//		clientPage.clickEdit();
//		
//		ClientContactsPageFactory clientContactsPage = new ClientContactsPageFactory();
//		
//		
//		clientContactsPage.getEditClientModal().setClientName("Jolene");
//		AutomationHelper.wait(5);
//		clientContactsPage.getEditClientModal().clickCancel();
//		AutomationHelper.wait(5);
//		
//		clientContactsPage.clickAddContact();
//		
//		//Add Contact modal displayed
//		clientContactsPage.getAddAContactModal().setEmail("jesse_admin@gmail.com");
//		AutomationHelper.wait(5);

		
		
		

		

		
		
		
		

		
		

		
		
		

	}

}
