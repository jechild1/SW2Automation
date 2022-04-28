package unitTests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import pageFactories.DashboardPageFactory;
import pageFactories.LoginPageFactory;
import testCases.BaseTestScriptConfig;
import utilities.AutomationHelper;

public class UnitTest1 extends BaseTestScriptConfig {

	@Test()
	public void test() {
		
		LoginPageFactory loginPage = new LoginPageFactory();

		loginPage.loadPage();
		
		loginPage.setEmail("jesse_admin@gmail.com");
		loginPage.setPassword("password");
		loginPage.clickLogin();
		
		DashboardPageFactory dashboardPage = new DashboardPageFactory();
		
//		dashboardPage.selectDivision("Florida");
//		System.out.println("Dashboard Page: " + dashboardPage.readDivision());
//		
//		AutomationHelper.wait(2);
//		dashboardPage.selectDivision("Colorado");
//		System.out.println("Dashboard Page: " + dashboardPage.readDivision());
//
//		AutomationHelper.wait(2);
//		dashboardPage.selectDivision("SWQA");
//		System.out.println("Dashboard Page: " + dashboardPage.readDivision());
//
//		AutomationHelper.wait(2);
//		dashboardPage.selectDivision("US East");
//		System.out.println("Dashboard Page: " + dashboardPage.readDivision());
//
//		AutomationHelper.wait(2);
//		dashboardPage.selectDivision("I'll fail");
//		System.out.println("Dashboard Page: " + dashboardPage.readDivision());
		
//		dashboardPage.clickMyProfile();
		
		
		dashboardPage.selectNotification("Missed Inspection", "JRC State Park", "2021-11-02");
		AutomationHelper.waitSeconds(5);



	}
}
