package testCases;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pageFactories.DashboardPageFactory;
import pageFactories.LoginPageFactory;
import utilities.ExcelDataConfig;

/**
 * This test case: Takes care of logging into the system a user (all user in the
 * data set will be tested). It then ensures that the division in the data set
 * matches the division that is in the application.
 * 
 * @author Jesse Childress
 *
 */
public class DivisionAssertion extends BaseTestScriptConfig {
	
	String emailAddress;

	/**
	 * Method for the main test - Login
	 * 
	 * @param emailAddress
	 * @param password
	 */
	@Test(dataProvider = "loginAccounts")
	public void performDivisionAssertion(String emailAddress, String password) {

		this.emailAddress = emailAddress;

		LoginPageFactory lp_factory = new LoginPageFactory();

		lp_factory.loadPage();

		lp_factory.setEmail(emailAddress);
		lp_factory.setPassword(password);

		lp_factory.clickLogin();

		DashboardPageFactory dashboard_PF = new DashboardPageFactory();

		// From the existing email, we can find the user name.
		ExcelDataConfig dataFile = getExcelFile("users.xlsx", USERS_WORKSHEET);
		String firstName = dataFile.getData(dataFile.getRowIndex("Email", emailAddress), "First Name");
		String lastName = dataFile.getData(dataFile.getRowIndex("Email", emailAddress), "Last Name");
		String name = firstName + " " + lastName;

		//Assert that the logged in user name is found correctly
		Assert.assertEquals(dashboard_PF.readUserNameInNavigationHeading(), name);
		Reporter.log("The User Name is: " + dashboard_PF.readUserNameInNavigationHeading(), true);

		/*
		 * Code for validating divisions from values in dataset
		 */
		List<String> applicationDivisionsList = dashboard_PF.readDivisions();

		String datasheetDivisions = dataFile.getData(dataFile.getRowIndex("Email", emailAddress), "Divisions");

		String[] myArray = datasheetDivisions.split(", ");
		System.out.println("Contents of the array ::" + Arrays.toString(myArray));
		List<String> datasheetDivisionsList =  new ArrayList<String>();
		datasheetDivisionsList = Arrays.asList(myArray);
		
		Collections.sort(applicationDivisionsList);
		Collections.sort(datasheetDivisionsList);
		
		boolean appAndDataMatch = applicationDivisionsList.equals(datasheetDivisionsList);
		
		Reporter.log("The application divisions for this user: " + applicationDivisionsList.toString(), true);
		Reporter.log("The data sheet divisions for this user: " + datasheetDivisionsList.toString(), true);

		Assert.assertEquals(appAndDataMatch, true, "Divisions Match - Ensuring Application Matches Datasheet");

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

}
