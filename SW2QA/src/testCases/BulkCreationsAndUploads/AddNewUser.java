package testCases.BulkCreationsAndUploads;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pageFactories.AdminPageFactory;
import pageFactories.DashboardPageFactory;
import testCases.BaseTestScriptConfig;
import testCases.modularTests.LoginMod;
import utilities.ExcelDataConfig;

/**
 * This test case: <br>
 * <li>Grabs a complete list of users from the a dataset, as outlined in the
 * global variables.
 * <li>Cycles through those users to see if there are any with "data used"
 * status of FALSE.
 * 
 * <br>
 * Note: Please ensure that you:
 * <li>Update the script to log in as the appropriate user.
 * <li>Update the Division
 * <li>Update the file name / worksheet name
 * 
 * 
 * 
 * <li>Updates the datasheet column for the user to be "Active"
 * 
 * @author Jesse Childress
 *
 */
public class AddNewUser extends BaseTestScriptConfig {
	String emailAddress;

	String fileName = "Tim - Client User Set Up.xlsx";
	String worksheetName = "Tim";

	/**
	 * Method for the main test - Login
	 * 
	 * @param loginEmailAddress
	 * @param password
	 */
	@Test(dataProvider = "uncreatedUsers")
	public void addNewUser(String email) {

		/*
		 * LOGIN
		 */
		// Login as a role that can create a new user
		LoginMod login = new LoginMod("Admin");
		login.login(DIVISION);

		/*
		 * DASHBOARD PAGE LOADS
		 */
		DashboardPageFactory dashboardPage = new DashboardPageFactory();
		dashboardPage.clickAdmin();

		/*
		 * ADMIN PAGE
		 */
		AdminPageFactory adminPage = new AdminPageFactory();
		
		// From the existing email, we can find the user name.
		ExcelDataConfig dataFile = getExcelFile(fileName, worksheetName);
		
		String role = dataFile.getData(dataFile.getRowIndex("Email", email), "Role");
		String divisions = dataFile.getData(dataFile.getRowIndex("Email", email), "Divisions");
		String clients = dataFile.getData(dataFile.getRowIndex("Email", email), "Clients");
		String projects = dataFile.getData(dataFile.getRowIndex("Email", email), "Projects");
		String assignToAreaAssistantManager = dataFile.getData(dataFile.getRowIndex("Email", email),
				"AssignToAreaAssistantManager");
		
		// Begin by searching for the user. If the user exists, we should break the
		// test.
		
//		Assert.assertEquals(adminPage.getUsersTable().isRowInTable("Email", email), false,
//				"Admin - Users Table - User should not exist, but does.");

		// Click the Add Users button, provided the last steps are fixed.
		adminPage.clickAddUsers();



		// Set the fields
		adminPage.getAddUsersModal().setEmail(email);
		adminPage.getAddUsersModal().selectRole(role);
		adminPage.getAddUsersModal().selectDivision(divisions);

		// Click next to go to next part of modal
		adminPage.getAddUsersModal().clickNext();

		adminPage.getAddUsersModal().selectClients(clients);
		adminPage.getAddUsersModal().selectProjects(projects);
		
		//Inspector - This is the only role that requires you to "Assign to Area/Assistant Manager"
		if(role.equalsIgnoreCase("Inspector")) {
			adminPage.getAddUsersModal().selectAssignToAreaAssistantManager(assignToAreaAssistantManager);
		}
		

		// Perform the save and move on to the next user.
		adminPage.getAddUsersModal().clickCreate();
		
		//Check for errors
		Assert.assertEquals(adminPage.getAddUsersModal().readErrors(), "", "Errors on Modal Prevent Saving");
		

		adminPage = new AdminPageFactory();
		adminPage.clickLogOut();
//		Assert.assertEquals(adminPage.getUsersTable().isRowInTable("Email", emailAddress), true, "Record added to the table");
		
		
		
		
		
		// Mark the data record as used
		dataFile.writeToWorkSheet(dataFile.getRowIndex("Email", email), "Data Used", "TRUE");

	}

	/**
	 * Data provider to get all of the users for a given worksheet.
	 * 
	 * @return
	 */
	@DataProvider(name = "uncreatedUsers")
	private String[] getUserList() {

		ExcelDataConfig usersDatasheet = getExcelFile(fileName, worksheetName);

		// Get the row count
		int rowCount = usersDatasheet.getRowCount();

		List<String> usersList = new ArrayList<String>();

		if (rowCount >= 1) {

			for (int i = 1; i <= rowCount; i++) {

				// get each individual line of the datasheet
				// add only if the data hasn't been used
				if (usersDatasheet.getData(i, "Data Used").equalsIgnoreCase("FALSE")) {
					usersList.add(usersDatasheet.getData(i, "Email"));
				}
			}

			if ((usersList.size() < 1)) {

				Reporter.log("There are no values in the " + fileName + " filename and " + worksheetName
						+ " worksheet that have not already been added", true);
				System.exit(1);
			}
		}

		return usersList.stream().toArray(String[]::new);
	}

}
