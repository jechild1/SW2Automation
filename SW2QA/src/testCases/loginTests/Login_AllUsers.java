package testCases.loginTests;

import java.util.List;
import java.util.StringJoiner;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pageFactories.DashboardPageFactory;
import pageFactories.LoginPageFactory;
import pageFactories.Divisions.DivisionsPageFactory;
import testCases.BaseTestScriptConfig;
import utilities.ExcelDataConfig;

/**
 * This test case: Takes care of logging into the system as any random user that
 * is a part of the dataset. It ensures that the dashbaord page shows the user
 * name and then logs out.
 * 
 * @author Jesse Childress
 *
 */
public class Login_AllUsers extends BaseTestScriptConfig {
	String emailAddress;

	/**
	 * Method for the main test - Login
	 * 
	 * @param emailAddress
	 * @param password
	 */
	@Test(dataProvider = "loginAccounts")
	public void loginAllUsers(String emailAddress, String password) {

		this.emailAddress = emailAddress;

		LoginPageFactory lp_factory = new LoginPageFactory();

		lp_factory.loadPage();

		performErrorChecks(lp_factory);

		lp_factory.setEmail(emailAddress);
		lp_factory.setPassword(password);

		lp_factory.clickLogin();

		// After user logs in, ensure that the Dashboard page populates with the correct
		// user.

		DashboardPageFactory dashboard_PF = new DashboardPageFactory();

		// From the existing email, we can find the user name.

		ExcelDataConfig dataFile = getExcelFile("users.xlsx", USERS_WORKSHEET);
		String firstName = dataFile.getData(dataFile.getRowIndex("Email", emailAddress), "First Name");
		String lastName = dataFile.getData(dataFile.getRowIndex("Email", emailAddress), "Last Name");
		String name = firstName + " " + lastName;

		Assert.assertEquals(dashboard_PF.readUserNameInNavigationHeading(), name);
		Reporter.log("The User Name is: " + dashboard_PF.readUserNameInNavigationHeading(), true);

//		/*
//		 * Code below is for when we want to update our datasheet with the divisions for
//		 * a specific user
//		 */
//		populateDivisionsInUserFile(dataFile);

		dashboard_PF.clickLogOut();

	}

	/**
	 * Data provider to get all of the users for a given worksheet.
	 * 
	 * @return
	 */
	@DataProvider(name = "loginAccounts")
	private String[][] getAllAccounts() {
		return getUserAndPassDataProviderFromExcel("users.xlsx", USERS_WORKSHEET);
	}

	/**
	 * This private method clicks the login button with nothing in it and asserts
	 * that the errors fire as expected
	 * 
	 * @param lp
	 */
	private void performErrorChecks(LoginPageFactory lp) {

		// Click Login to fire the errors.
		lp.clickLogin();

		// Assert the errors
		Assert.assertEquals("This field is required", lp.readEmailError());
		Assert.assertEquals("This field is required", lp.readPasswordError());
		Assert.assertEquals("Invalid form data. Please review errors above.", lp.readMainErrorMessageText());

	}

	/**
	 * Utility method that will populate the Divisions for a specific user in the
	 * datasheet after that user navigates to the divisions page.
	 * 
	 * @param dataFile
	 */
	private void populateDivisionsInUserFile(ExcelDataConfig dataFile) {

		DashboardPageFactory dashboard_PF = new DashboardPageFactory();
		dashboard_PF.clickDivisions();

		DivisionsPageFactory divisionsPage = new DivisionsPageFactory();

		List<String> divisionsForUser = divisionsPage.getDivisionsTable().getCellValues("Name");

		// This joiner strings together the values in the array
		StringJoiner joiner = new StringJoiner(", ");
		divisionsForUser.forEach(item -> joiner.add(item.toString()));
		String divisionsCSVFormat = joiner.toString();

		dataFile.writeToWorkSheet(dataFile.getRowIndex("Email", emailAddress), "Divisions", divisionsCSVFormat);

		divisionsPage.clickHome();
	}
}
