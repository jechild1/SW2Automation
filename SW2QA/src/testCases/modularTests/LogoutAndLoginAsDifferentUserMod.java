package testCases.modularTests;

import pageFactories.DashboardPageFactory;
import pageFactories.ProfilePageFactory;
import testCases.BaseTestScriptConfig;
import utilities.AutomationHelper;

/**
 * MODULAR SCRIPT:<br>
 * This class accepts a parameter for the role for the second login and a
 * parameter for loading the page in which this method was called on. If not, it
 * will load the dashboard page. It does the following:<br>
 * <li>Captures the current page URL when this method is called
 * <li>Navigates to the Profile page, no matter where you are, and stores the
 * currently logged in role.
 * <li>Logs out of the system and closes the browser
 * <li>Logs back in as the role passed in the parameter of this constructor
 * <li>Lands on the dashboard page and ensures that page loads
 * <li>Navigates back to the place where the original method was called (this is
 * the current URL that was initially captured) <br>
 * The roles are:<br>
 * <li>Admin
 * <li>Area / Assistant Manager
 * <li>BMP Contractor
 * <li>CDOT PE/Designee
 * <li>Client Inspector
 * <li>Client User
 * <li>Divisions Admin
 * <li>Engineering Admin
 * <li>Inspector
 * <li>Regional Manager (External)
 * <li>Regional Manager
 * <li>Regulator
 * <li>Supervising Engineer
 * <li>SWMP Admin </br>
 * 
 * 
 * It uses the role to find the user in the user.xlsx datasheet. It signs in as
 * a user as passed in and asserts that the user is on the Dashboards page
 * successfully.
 * 
 * @author Jesse Childress
 *
 */
public class LogoutAndLoginAsDifferentUserMod extends BaseTestScriptConfig {

	String initialRole;
	String secondaryRole;
	String division;
	String pageURL;
	boolean loadOriginalPage;

	/**
	 * The class constructor accepts a role for the second login in. We need to know
	 * which user role to use. Also, it accepts a boolean parameter for
	 * loadOriginalPage. True will load the page the user was on when logged out.
	 * False leaves the user on the dashboard.
	 * 
	 * @param roleForSecondLogin
	 * @param loadOriginalPage
	 * @param division TODO
	 */
	public LogoutAndLoginAsDifferentUserMod(String roleForSecondLogin, boolean loadOriginalPage) {
		secondaryRole = roleForSecondLogin;
		this.loadOriginalPage = loadOriginalPage;
	}

	/**
	 * Method to log out and back in as a different user.
	 */
	public void logOutAndLogin() {
		AutomationHelper.printMethodName();
		
		// Store the current URL for login later.
		pageURL = driver.getCurrentUrl();

		// Capture the current user role.
		captureInitialUserRole();

		// Log of out of the system
		clickLogOut();

		// Once you log out, close the browser window
		driver.close();

		// Log in again as the passed in role (the secondary role)
		LoginMod login = new LoginMod(secondaryRole);
		login.login(division);

		// Load the URL
		if (loadOriginalPage) {
			driver.get(pageURL);

		}
	}
	
	/**
	 * Internal method to navigate to the home page / Dashboards. It then navigates
	 * to the Profile page, captures the role of the currently logged in user, and
	 * stores the value in a global variable.
	 * 
	 * @return String
	 */
	private String captureInitialUserRole() {
		// Clicks Home from anywhere in the application. We do NOT have to be on a
		// certain page to use this method.
		clickHome();

		// The Dashboards page will be loaded. Open it and then capture the role of the
		// currently logged in user.
		DashboardPageFactory dashboardPage = new DashboardPageFactory();
		dashboardPage.clickMyProfile();

		// Profile Page is displayed
		ProfilePageFactory profilePage = new ProfilePageFactory();
		initialRole = profilePage.readRoleType();
		division = profilePage.readDivision();
		return initialRole;

	}

}
