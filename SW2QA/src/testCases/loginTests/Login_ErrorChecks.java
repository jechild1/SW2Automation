package testCases.loginTests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pageFactories.LoginPageFactory;
import testCases.BaseTestScriptConfig;
import utilities.ExcelDataConfig;

/**
 * This test case: Logs in as a random user and performs error checks on the
 * Login Page. It ensures that the correct errors fire.
 * 
 * @author Jesse Childress
 *
 */
public class Login_ErrorChecks extends BaseTestScriptConfig {

	@Test(dataProvider = "randomUserAccount")
	public void validateErrorsOnLogin(String emailAddress, String password) {

		LoginPageFactory lp_factory = new LoginPageFactory();

		lp_factory.loadPage();

		// Open the datasheet that contains the errors that we will be checking
		ExcelDataConfig errorsFile = getExcelFile("SW2ContentMatrix.xlsx", "StaticTextValidations");

		String noUserName = errorsFile.getData("LP_No_Email", "Content");
		String noPassword = errorsFile.getData("LP_No_Password", "Content");
		String mainError = errorsFile.getData("LP_Main_Error", "Content");
		String invalidCombination = errorsFile.getData("LP_Invalid_Combination", "Content");

		// Click Login to fire the errors with NO DATA
		lp_factory.clickLogin();

		/*
		 * FIRST TEST: Click submit with NO data and ensure that the correct errors are returned.
		 */
		// This field is required
		Assert.assertEquals(noUserName, lp_factory.readEmailError());
		// This field is required
		Assert.assertEquals(noPassword, lp_factory.readPasswordError());
		// Invalid Form Data
		Assert.assertEquals(mainError, lp_factory.readMainErrorMessageText());

		
		/*
		 * SECOND TEST: Enter in bogus email address without password. Entering without a password
		 */
		
		// leaves text "This field is required"
		lp_factory.setEmail("wrongformat.com");
		Assert.assertEquals("", lp_factory.readEmailError());
		Assert.assertEquals(noPassword, lp_factory.readPasswordError());
		
		//Clear the email field
		lp_factory.setEmail("");
		
		/*
		 * THIRD TEST: Enter a password, but not a user name
		 */
			
		//Enter in a password, but not a user name.
		lp_factory.setPassword(password);
		
		Assert.assertEquals(noUserName, lp_factory.readEmailError());
		Assert.assertEquals("", lp_factory.readPasswordError());
		
		//Clear the password field.
		lp_factory.setPassword("");
		
		/*
		 * FOURTH TEST: Enter a bogus user name / password combination
		 */
		lp_factory.setEmail("bogusEmail@test.com");
		lp_factory.setPassword("pass_will_not_work");
		lp_factory.clickLogin();
		
		
		Assert.assertEquals(invalidCombination, lp_factory.readMainErrorMessageText());
		Assert.assertEquals("", lp_factory.readEmailError());
		Assert.assertEquals("", lp_factory.readPasswordError());

		
	}

	@DataProvider(name = "randomUserAccount")
	private String[][] randomUserAccount() {
		return getRandomUserAccount();
	}

}
