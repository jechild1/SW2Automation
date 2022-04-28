package unitTests;

import org.testng.annotations.Test;

import pageFactories.AdminPageFactory;
import pageFactories.DashboardPageFactory;
import pageFactories.LoginPageFactory;
import testCases.BaseTestScriptConfig;

public class UnitTestAdmin extends BaseTestScriptConfig{

	@Test
	public void unitTest() {

		LoginPageFactory loginPage = new LoginPageFactory();

		loginPage.loadPage();
		
		loginPage.setEmail("jesse_admin@gmail.com");
		loginPage.setPassword("password");
		loginPage.clickLogin();
		
		DashboardPageFactory dashboardPage = new DashboardPageFactory();
		
		dashboardPage.clickAdmin();
		
		//Admin page is displayed
		AdminPageFactory adminPage = new AdminPageFactory();
		
		adminPage.clickAddUsers();
		
		adminPage.getAddUsersModal().selectDivision("SWQA");
		
		
		
	}
}
