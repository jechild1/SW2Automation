package testCases.loginTests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pageFactories.DashboardPageFactory;
import pageFactories.LoginPageFactory;
import pageFactories.MailTrapPageFactory;
import testCases.BaseTestScriptConfig;
import utilities.AutomationHelper;
import utilities.ExcelDataConfig;

/**
 * This test attempts to log in as a user, and then follows the Forgot Password
 * workflow. We pick a static user name from the data sheet of "Inspector". We
 * send the email link to our Mail Trap.
 * 
 * @author Jesse Childress
 *
 */
public class Login_ForgotPassword extends BaseTestScriptConfig {
	
	// Mail Trap user name / pass
	protected static String mailTrapEmailAddress = "mmurray@sw2.net";
	protected static String mailTrapEmailPassword = "Hw8MXPskqnKLXxU";

	@Test(dataProvider = "userAccount")
	public void resetPassword(String emailAddress, String password, String x, String y) {

		// Gather data

		// Open the datasheet that contains the errors that we will be checking
		ExcelDataConfig errorsFile = getExcelFile("SW2ContentMatrix.xlsx", "StaticTextValidations");
		String userEmailDoesNotExist = errorsFile.getData("LP_User_Does_Not_Exist", "Content");

		// Once the page is logged, immediately click the Forgot Password link.
		LoginPageFactory loginPage = new LoginPageFactory();
		loginPage.loadPage();
		loginPage.clickForgotPassword();

		/*
		 * TEST 1: Test the "Cancel" functionality
		 */
		loginPage.clickCancel();

		// We should be back on the main login page, where we can click the Forgot
		// Password link.
		loginPage.clickForgotPassword();

		/*
		 * TEST 2: Set the email address with an email that isn't real and click Send.
		 * We expect an email saying that the user doesn't exist.
		 */

		loginPage.setEmail("boguseamil@fakedomain.com");
		AutomationHelper.tab();
		loginPage.clickSend();

		Assert.assertEquals(loginPage.readMainErrorMessageText(), userEmailDoesNotExist);

		/*
		 * TEST 3: Enter valid email and send password reset link
		 */
		loginPage.setEmail(emailAddress);
		AutomationHelper.tab();
		loginPage.clickSend();

		/*
		 * TEST 4: Go to the mail trap application. Find the email that was just sent,
		 * and click on the email. After this, click the "Reset" button
		 */

		// Need to go to Mail Trap and check for the email address
		String mailTrapURL = "https://mailtrap.io/signin";

		// Below code will open Tab for you as well as switch the control to new Tab
		driver.switchTo().newWindow(WindowType.TAB);

		// Below code will navigate you to your desirable Url
		driver.navigate().to(mailTrapURL);

		// Instantiate the Mail Trap Page Factory
		MailTrapPageFactory mailTrap = new MailTrapPageFactory();

		// Log into the mail trap application
		mailTrap.setEmail(mailTrapEmailAddress);
		
		//Click the Next button to make the password field appear
		mailTrap.clickNext();
		
		mailTrap.setPassword(mailTrapEmailPassword);
		mailTrap.clickLogInAfterSettingUserNameAndPass();

		// The Projects page is displayed with the Mail Trap application. We need to
		// click the "Demo inbox" url here.
		mailTrap.clickDemoInbox();

		// The Demo Inbox is loaded now. We need to search for our email here.
		mailTrap.clickEmail("SW2 - Reset Password", emailAddress);

		// The actual reset button is located within an iframe, which we can't just do
		// xpath for.
		driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@title='Message view']")));

		// Find the reset button within the iframe
		WebElement resetButton = driver.findElement(By.linkText("Reset Password"));
		String resetPasswordURL = resetButton.getAttribute("href");

		/*
		 * TEST 5: Navigate to the New Password page back in the original application
		 */
		driver.navigate().to(resetPasswordURL);

		// Now the password page is displayed
		// Setting the password to the original. Don't want to actually change it.
		loginPage.setNewPassword(password);
		loginPage.clickSubmit();

		// Modal displays here for a few moments, and then dashboard is displayed.

		/*
		 * TEST 6: Wait on the modal do disappear and be re-directed to the Dashboards
		 * page. Check the user name to ensure the correct user shows up in the
		 * dashboard.
		 */
		DashboardPageFactory dashboardsPage = new DashboardPageFactory();

		ExcelDataConfig dataFile = getExcelFile("users.xlsx", "AutomationUsers");
		String firstName = dataFile.getData(dataFile.getRowIndex("Email", emailAddress), "First Name");
		String lastName = dataFile.getData(dataFile.getRowIndex("Email", emailAddress), "Last Name");
		String name = firstName + " " + lastName;

		Assert.assertEquals(dashboardsPage.readUserNameInNavigationHeading(), name);
		Reporter.log("The User Name is: " + dashboardsPage.readUserNameInNavigationHeading());

	}

	/**
	 * Returns a String[][] of the passed in role type.
	 * 
	 * @return dataProvider
	 */
	@DataProvider(name = "userAccount")
	private String[][] getUser() {
		return getUserAccount("Inspector");
	}

}
