package testCases.modularTests;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.DataProvider;

import pageFactories.DashboardPageFactory;
import pageFactories.LoginPageFactory;
import testCases.BaseTestScriptConfig;
import utilities.AutomationHelper;

/**
 * MODULAR SCRIPT:<br>
 * This class accepts a String of role for the constructor. It uses the role to
 * find the user in the user.xlsx datasheet. It signs in as a user as passed in
 * and asserts that the user is on the Dashboards page successfully.
 * 
 * @author Jesse Childress
 *
 */
public class LoginMod extends BaseTestScriptConfig {

	String role;
	String email;
	String password;

	public LoginMod(String role) {
		this.role = role;
	}
	
	public LoginMod(String email, String password) {
		this.email = email;
		this.password = password;
		this.role = getUserRole(email);
	}
	
	public void loginUserWithAccountCredentials() {
		AutomationHelper.printClassName();
		AutomationHelper.printMethodName();
		
		LoginPageFactory loginPage = new LoginPageFactory();

		loginPage.loadPage();

		loginPage.setEmail(email);
		loginPage.setPassword(password);

		loginPage.clickLogin();

		// Assert we made it to the Dashboard page.

		/*
		 * Dashboards Page Loaded
		 */
		DashboardPageFactory dashboardsPage = new DashboardPageFactory();
//		Assert.assertEquals(dashboardsPage.readUserNameInNavigationHeading(), firstName + " " + lastName,
//				"Dashboard - User Name In Heading");
//
//		// Print the login name to the console:
//		Reporter.log("Login email: " + email, true);

		/*
		 * Ensure the Division is moved to SWQA ALpha instead of the default for
		 * Colorado NOTE: https://sw2.atlassian.net/browse/SWB-170 should default this
		 * in the future.
		 */
		dashboardsPage.selectDivision(DIVISION);
		Assert.assertEquals(dashboardsPage.readDivision(), DIVISION);
	}

	/**
	 * Login method to sign the user in and to assert that the correct user is
	 * loaded in the dashboard.
	 * @param division - e.g Colorado, SWQA Alpha
	 */
	//TODO - rename
	public void login(String division) {

		AutomationHelper.printClassName();
		AutomationHelper.printMethodName();

		String[][] accountCredentials = getUserAccount(this.role);

		String email = accountCredentials[0][0];
		String password = accountCredentials[0][1];
		String firstName = accountCredentials[0][2];
		String lastName = accountCredentials[0][3];

		LoginPageFactory loginPage = new LoginPageFactory();

		loginPage.loadPage();

		loginPage.setEmail(email);
		loginPage.setPassword(password);

		loginPage.clickLogin();

		// Assert we made it to the Dashboard page.

		/*
		 * Dashboards Page Loaded
		 */
		DashboardPageFactory dashboardsPage = new DashboardPageFactory();
		Assert.assertEquals(dashboardsPage.readUserNameInNavigationHeading(), firstName + " " + lastName,
				"Dashboard - User Name In Heading");

		// Print the login name to the console:
		Reporter.log("Login email: " + email, true);

		/*
		 * Ensure the Division is moved to whatever the user passes in instead of the default for
		 * Colorado NOTE: https://sw2.atlassian.net/browse/SWB-170 should default this
		 * in the future.
		 */
		
		dashboardsPage.selectDivision(division);
		Assert.assertEquals(dashboardsPage.readDivision(), division);

	}

	/**
	 * Returns the role of the currently logged in user. If login() has not been
	 * executed, this will return null.
	 * 
	 * @return String
	 */
	public String getUserRole() {
		return role;
	}

	/**
	 * Returns a String[][] of the passed in role type.
	 * 
	 * @return dataProvider
	 */
	@DataProvider(name = "userAccount")
	private String[][] getUser() {
		return getUserAccount(this.role);
	}

}
