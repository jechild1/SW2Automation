package unitTests;

import org.testng.annotations.Test;

import pageFactories.DashboardPageFactory;
import pageFactories.LoginPageFactory;
import pageFactories.Clients.ClientPageFactory;
import testCases.BaseTestScriptConfig;
import utilities.AutomationHelper;

public class DemoModal1 extends BaseTestScriptConfig {

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

		clientPage.clickAddClient();

		//TODO - Show the modal code here.
		clientPage.getCreateNewClientModal().setClientName("Frank");

		AutomationHelper.waitSeconds(7);

		clientPage.getCreateNewClientModal().clickCancel();
	}
}
