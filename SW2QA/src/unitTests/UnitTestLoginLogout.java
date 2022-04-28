package unitTests;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageFactories.DashboardPageFactory;
import pageFactories.LoginPageFactory;
import pageFactories.Clients.ClientPageFactory;
import testCases.BaseTestScriptConfig;
import utilities.AutomationHelper;

public class UnitTestLoginLogout extends BaseTestScriptConfig {

	@Test()
	public void test() {
		
		LoginPageFactory loginPage = new LoginPageFactory();

		loginPage.loadPage();
		
		loginPage.setEmail("jesse_admin@gmail.com");
		loginPage.setPassword("password");
		loginPage.clickLogin();
		
		DashboardPageFactory dashboardPage = new DashboardPageFactory();
		
		Assert.assertEquals(dashboardPage.readPageTitleFromBreadcrumb(), "", "Dashboard page doesn't have a breadcrumb");
		
		dashboardPage.clickClient();
		
		AutomationHelper.waitSeconds(3);
		
		ClientPageFactory clientPage = new ClientPageFactory();
		Assert.assertEquals(clientPage.readPageTitleFromBreadcrumb(), "Clients", "Clients Breadcrumb");
		
		AutomationHelper.waitSeconds(3);

		clientPage.clickLogOut();
		
		AutomationHelper.waitSeconds(3);
	}
}
