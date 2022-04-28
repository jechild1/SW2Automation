package testCases;

import java.io.File;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;

import configuration.SW2Config;
import utilities.AutomationHelper;
import utilities.ExcelDataConfig;

/**
 * Base Test Script to be extended by SW2 test cases
 * 
 * @author Jesse Childress
 *
 */
public abstract class BaseTestScriptConfig extends SW2Config {

	/**
	 * After class method that will run the cleanup finish test methods at the end
	 * of all tests in a given class
	 */
	@AfterClass
	public void afterClass() {
		AutomationHelper.finishTest();
	}

	/**
	 * After method that will close the driver in-between tests in the loop
	 */
	@AfterMethod
	public void afterMethod(ITestResult result) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH.mm.ss");
		if (ITestResult.FAILURE == result.getStatus()) {
			ScreenshotOnFailure screenshot = new ScreenshotOnFailure();
			screenshot.captureScreenshot(driver,
					result.getName() + " - " + formatter.format(java.time.LocalDateTime.now()));
		}

		// Note: In some instances, we want to override this after method as to not quit
		// after each method, when doing multiple @Tests.
		driver.quit();
	}

	/**
	 * Returns a random user from the main Users data file of users.xlsx.
	 * 
	 * @return String[]
	 */
	protected String[][] getRandomUserAccount() {

		ExcelDataConfig usersFile = getExcelFile("users.xlsx", USERS_WORKSHEET);

		// Get the row count for the data file
		int rowCount = usersFile.getRowCount();

		// Get a random row number from the datasheet.
		int randomRowNumber = AutomationHelper.generateRandomInteger(1, rowCount);

		String email = usersFile.getData(randomRowNumber, "Email");
		String password = usersFile.getData(randomRowNumber, "Password");

		String[][] userNamePassArray = new String[1][2];
		userNamePassArray[0][0] = email;
		userNamePassArray[0][1] = password;

		return userNamePassArray;
	}

	/**
	 * Returns the User Role for the passed in email address
	 * 
	 * @param emailAddress
	 * @return String - User Role
	 */
	protected String getUserRole(String emailAddress) {
		ExcelDataConfig usersFile = getExcelFile("users.xlsx", USERS_WORKSHEET);

		int rowNumberForEmailAddress = usersFile.getRowIndex("Email", emailAddress);

		String role = usersFile.getData(rowNumberForEmailAddress, "Role");

		return role;

	}

	/**
	 * Returns a user account for the type of user as passed in. <br>
	 * For example, passing in "Admin" would return me a user name of SQA Admin /
	 * sqa_admin@gmail.com;
	 * 
	 * @param userAccount - The account Type, e.g. Inspector
	 * @return String[][]
	 */
	protected String[][] getUserAccount(String userAccount) {

		ExcelDataConfig usersFile = getExcelFile("users.xlsx", USERS_WORKSHEET);

		int rowNumberForUserType = usersFile.getRowIndex("Role", userAccount);

		String email = usersFile.getData(rowNumberForUserType, "Email");
		String password = usersFile.getData(rowNumberForUserType, "Password");
		String firstName = usersFile.getData(rowNumberForUserType, "First Name");
		String lastName = usersFile.getData(rowNumberForUserType, "Last Name");

		String[][] userNamePassArray = new String[1][4];
		userNamePassArray[0][0] = email;
		userNamePassArray[0][1] = password;
		userNamePassArray[0][2] = firstName;
		userNamePassArray[0][3] = lastName;

		return userNamePassArray;
	}

	/**
	 * This method takes an excel data file and puts the data in a list. Then it
	 * does size checking and converts it to a multidimensional string array to be
	 * used in the main method.
	 * 
	 * @param excelFile
	 * @param excelWorksheetName
	 * 
	 * @return String[][]
	 */
	protected String[][] getUserAndPassDataProviderFromExcel(String excelFile, String worksheet) {
		String[][] userPassCombinations = getUserAndPassDataProviderFromExcel(excelFile, worksheet, null);

		return userPassCombinations;
	}

	/**
	 * This method takes an excel data file and puts the data in a list if it
	 * matches the specified username. Then it does size checking and converts it to
	 * a multidimensional string array to be used in the main method.
	 * 
	 * @param excelFile
	 * @param excelWorksheetName
	 * @param specifiedUserName
	 * 
	 * @return String[][]
	 */
	protected String[][] getUserAndPassDataProviderFromExcel(String excelFile, String excelWorksheetName,
			String specifiedUserName) {
		// Set up datafiles
		ExcelDataConfig edc = getExcelFile(excelFile, excelWorksheetName);

		// Get the row count of the datasheet.
		int rowCount = edc.getRowCount();

		// Create an array to store username / password combinations
		List<String> userNameList = new ArrayList<String>();
		List<String> passwordList = new ArrayList<String>();

		/*
		 * Cycle through the username / password columns and store in the respective
		 * array lists. Note: Start x variable at 1 to avoid storing the datasheet title
		 * row.
		 */
		for (int i = 1; i <= rowCount; i++) {
			userNameList.add(edc.getData(i, edc.getColumnIndex("Email")));
			passwordList.add(edc.getData(i, edc.getColumnIndex("Password")));
		}

		// Ensure that the lists are the same size. Else, we have bad data.
		if (userNameList.size() != passwordList.size()) {
			throw new IllegalArgumentException("The user name and password list sizes are different. Check datasheet.");
		} else if (userNameList.size() == 0) {
			throw new NotFoundException("No username and password combinations found");
		}
		// Now that we have two String Lists, we need to convert them to arrays
		// to be later added to multi-dimensional array.

		String[] userNameArray = userNameList.toArray(new String[userNameList.size()]);
		String[] passwordArray = passwordList.toArray(new String[passwordList.size()]);

		String[][] userPassCombinations = new String[userNameArray.length][2];
		for (int i = 0; i < userNameArray.length; i++) {
			userPassCombinations[i][0] = userNameArray[i];
			userPassCombinations[i][1] = passwordArray[i];
		}
		return userPassCombinations;
	}

	/**
	 * 
	 * Class to capture screenshots when an error occurs.
	 * 
	 * @author Jesse Childress
	 *
	 */
	public class ScreenshotOnFailure {
		public void captureScreenshot(WebDriver driver, String screenshotName) {

			try {
				TakesScreenshot ts = (TakesScreenshot) driver;

				File source = ts.getScreenshotAs(OutputType.FILE);

				FileHandler.copy(source, new File(DEFAULT_FILE_PATH_FOR_SCREENSHOTS + screenshotName + ".png"));

				System.out.println("Screenshot taken");

			} catch (Exception e) {

				System.out.println("Exception while taking screenshot " + e.getMessage());
			}
		}
	}

}
