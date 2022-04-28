package testCases;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pageFactories.AdminPageFactory;
import pageFactories.DashboardPageFactory;
import pageFactories.LoginPageFactory;
import utilities.ExcelDataConfig;

/**
 * This test case: <br>
 * <li>Grabs a complete list of users from the users.xlsx datasheet
 * <li>Cycles through those users to see if there are any with a status of "NOT
 * CREATED"
 * 
 * 
 * <li>Updates the datasheet column for the user to be "Active"
 * 
 * @author Jesse Childress
 *
 */
public class AddNewUser extends BaseTestScriptConfig {
	String emailAddress;

	/**
	 * Method for the main test - Login
	 * 
	 * @param loginEmailAddress
	 * @param password
	 */
	@Test(dataProvider = "AdminAccount")
	public void execute(String loginEmailAddress, String password) {

		/*
		 * Log in to the system
		 */

		// Log into the system
		LoginPageFactory loginPage = new LoginPageFactory();
		loginPage.loadPage();

		// Set the email / password
		loginPage.setEmail(loginEmailAddress);
		loginPage.setPassword(password);
		loginPage.clickLogin();

		/*
		 * After user logs in, ensure that the Dashboard page populates with the correct
		 * user
		 */
		DashboardPageFactory dashboardPage = new DashboardPageFactory();

		// From the existing email, we can find the user name.
		ExcelDataConfig dataFile = getExcelFile("users.xlsx", "users");
		String firstName = dataFile.getData(dataFile.getRowIndex("Email", loginEmailAddress), "First Name");
		String lastName = dataFile.getData(dataFile.getRowIndex("Email", loginEmailAddress), "Last Name");
		String name = firstName + " " + lastName;

		Assert.assertEquals(dashboardPage.readUserNameInNavigationHeading(), name);
		Reporter.log("The User Name is: " + dashboardPage.readUserNameInNavigationHeading());
		
		/*
		 * Navigate to the Admin page
		 */
		dashboardPage.clickAdmin();
		
		//The Admin page is displayed. Click "Add Users"
		AdminPageFactory adminPage = new AdminPageFactory();
		adminPage.clickAddUsers();
		
		//Gather data that we will need to add a new user
		int rowIndex = dataFile.getNextUnusedDataRowIndex();
		
		String email = dataFile.getData(rowIndex, dataFile.getColumnIndex("Email"));
		String role = dataFile.getData(rowIndex, dataFile.getColumnIndex("Role"));
		String division = dataFile.getData(rowIndex, dataFile.getColumnIndex("Division"));
		
		//Add Users Modal is displayed
		//Page 1 of 2 on modal
		adminPage.getAddUsersModal().setEmail(email);
		adminPage.getAddUsersModal().selectRole(role);
		adminPage.getAddUsersModal().selectDivision(division);
		
		adminPage.getAddUsersModal().clickNext();	
		
		
		//Page 2 of 2 on modal
		String clients = dataFile.getData(rowIndex, dataFile.getColumnIndex("Clients"));
		String projects = dataFile.getData(rowIndex, dataFile.getColumnIndex("Projects"));
		
		adminPage.getAddUsersModal().selectClients(clients);
		adminPage.getAddUsersModal().selectProjects(projects);
		
		/*
		 * Perform validations
		 */
		//Now that the fields have been set, lets navigate backward to ensure that they are indeed set.
		adminPage.getAddUsersModal().clickBack();
		
		Assert.assertEquals(adminPage.getAddUsersModal().readEmail(), email);
		Assert.assertEquals(adminPage.getAddUsersModal().readRole(), role);
		Assert.assertEquals(adminPage.getAddUsersModal().readDivision(), division);
		

		
		
		
		
		
		
		
		
		
		

	}

	@DataProvider(name = "AdminAccount")
	private String[][] getAdminAccount() {
		return getUserAccount("Admin");
	}
}
