package pageFactories;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import utilities.AutomationHelper;

/**
 * Page factory for the Admin page for SW2.<br>
 * (1) Finds a reference to objects on the page <br>
 * (2) Defines methods to interact with objects on the page.<br>
 * 
 * @author Jesse Childress
 *
 */
public class AdminPageFactory extends MenusPageFactory {

	public static String regexURL = BASE_URL + ".*" + "admin";

	/**
	 * Page Constructor - Calls constructor in MenusPageFactory.
	 */
	public AdminPageFactory() {
		super(regexURL);
		waitForPageToLoad();
	}

	/**
	 * Sets the search field with the passed in user. This reduces the scope of
	 * records that we need to search for.
	 * 
	 * @param user
	 */
	public void setSearch(String user) {
		AutomationHelper.printMethodName();
		WebElement searchField = driver.findElement(By.xpath("//input[@type='search']"));
		AutomationHelper.setTextField(searchField, user);
		waitForPageToLoad();
	}

	/**
	 * Method to click the Add Users button
	 */
	public void clickAddUsers() {
		AutomationHelper.printMethodName();
		WebElement addUsersButton = driver
				.findElement(By.xpath("//button[@class='primary' and contains(text(),'Add Users')]"));

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.elementToBeClickable(addUsersButton));

		addUsersButton.click();

	}

	/**
	 * Returns a reference to the Users Table <br>
	 * 
	 * @return SW2Tables
	 */
	public SW2Tables getUsersTable() {
		//By using contains, we can account for the word "empty" on the end of the table where no results are present.
		WebElement usersTable = driver.findElement(By.xpath("//table[contains(@class , 'list-table pure-table')]"));
		return new SW2Tables(usersTable);
	}

	/**
	 * Returns a reference to the class Add Users
	 * 
	 * @return AddUsers modal
	 */
	public AddUsersModal getAddUsersModal() {
		return new AddUsersModal();
	}

	/**
	 * Nested class that contains methods to interact with objects on the Add Users
	 * modal.
	 * 
	 * @author Jesse Childress
	 *
	 */
	public class AddUsersModal {

		/**
		 * Method to click the Back button
		 */
		public void clickBack() {
			AutomationHelper.printMethodName();
			clickButtonByText("Back");
		}

		/**
		 * Method to click the Create button
		 */
		public void clickCreate() {
			AutomationHelper.printMethodName();
			clickButtonByText("Create");
			waitForButtonSpinner();
		}

		/**
		 * Method to click the Delete button
		 */
		public void clickDelete() {
			AutomationHelper.printMethodName();
			clickButtonByText("Delete");
		}

		/**
		 * Method to click the Next button
		 */
		public void clickNext() {
			AutomationHelper.printMethodName();
			clickButtonByText("Next");
			waitForButtonSpinner();
		}

		/**
		 * Method to click the clear button
		 */
		public void clickClear() {
			AutomationHelper.printMethodName();
			clickButtonByText("clear");
		}

		/**
		 * Method to read the Email Address
		 * 
		 * @return String
		 */
		public String readEmail() {
			AutomationHelper.printMethodName();
			WebElement emailInput = driver.findElement(By.xpath("//input[@id='formGroup-tags']"));
			return emailInput.getText();
		}

		/**
		 * Method to set the Email Address field.
		 * 
		 * @param emailAddress
		 */
		public void setEmail(String emailAddress) {
			AutomationHelper.printMethodName();
			WebElement emailInput = driver.findElement(By.xpath("//input[@id='formGroup-tags']"));
			AutomationHelper.setTextField(emailInput, emailAddress);
		}

		/**
		 * Method to read the Role drop down field
		 * 
		 * @return String
		 */
		public String readRole() {
			AutomationHelper.printMethodName();
			WebElement roleDropdown = driver.findElement(By.xpath("//select[@id='formGroup-roleId']"));
			return AutomationHelper.readSelectedSubItem(roleDropdown);
		}

		/**
		 * Method to select a role from the drop down object
		 * 
		 * @param role
		 */
		public void selectRole(String role) {
			AutomationHelper.printMethodName();
			WebElement roleDropdown = driver.findElement(By.xpath("//select[@id='formGroup-roleId']"));
			AutomationHelper.selectDropdownItem(roleDropdown, role);
		}

		public String readDivision() {
			AutomationHelper.printMethodName();
			WebElement containingDiv = driver.findElement(By.xpath("TEST"));
			return readDropdownItemsFromMultiSelect(containingDiv);
		}

		/**
		 * Method to select a Division, corresponding with the passed in Division Name.
		 * 
		 * Note: This method is smart enough to take in a string as separated by a PIPE
		 * | format and add a list of divisions.
		 * 
		 * @param divisionName
		 */
		public void selectDivision(String divisionName) {
			AutomationHelper.printMethodName();

			// If we have nothing in the String, lets bypass everything
			if (!divisionName.equals("")) {
				// Containing Div
				String xpath = "//div[contains(@class,'form-control--no-theme form-control form-control--customTagSelect')]";
				WebElement containingDiv = driver.findElement(By.xpath(xpath));

				// Because this is a Multi-Select, we can parse out the CSV format into a list
				// and cycle through and select over and over until we exhaust the list.

				List<String> divisions = Arrays.asList(divisionName.split("\\s*,\\s*"));

				for (String currentDivision : divisions) {
					selectDropdownItemFromMultiSelect(containingDiv, currentDivision);
				}
			}
		}

		/**
		 * Method to select a Client, corresponding with the passed in Client name.
		 * 
		 * Note: This method is smart enough to take in a string in format separated by
		 * a PIPE | and add a list of clients.
		 * 
		 * @param client
		 */
		public void selectClients(String client) {
			AutomationHelper.printMethodName();

			// If we have nothing in the String, lets bypass everything
			if (!client.equals("")) {

				// Containing Div
				String xpath = "//label[@for ='formGroup-clients']/following-sibling::div[contains(@class,'form-control--no-theme form-control form-control--customTagSelect')]";
				WebElement containingDiv = driver.findElement(By.xpath(xpath));

				// Because this is a Multi-Select, we can parse out the CSV format into a list
				// and cycle through and select over and over until we exhaust the list.

				List<String> clients = Arrays.asList(client.split("\\|"));

				for (String currentClient : clients) {
					currentClient = currentClient.trim();
					selectDropdownItemFromMultiSelect(containingDiv, currentClient);
				}
			}
		}

		/**
		 * Method to select a Project, corresponding with the passed in Client name.
		 * 
		 * Note: This method is smart enough to take in a string as separated by a PIPE
		 * | and add a list of project.
		 * 
		 * @param project
		 */
		public void selectProjects(String project) {
			AutomationHelper.printMethodName();

			// If we have nothing in the String, lets bypass everything
			if (!project.equals("")) {

				// Containing Div
				String xpath = "//label[@for ='formGroup-projects']/following-sibling::div[contains(@class,'form-control--no-theme form-control form-control--customTagSelect')]";
				WebElement containingDiv = driver.findElement(By.xpath(xpath));

				// Because this is a Multi-Select, we can parse out the CSV format into a list
				// and cycle through and select over and over until we exhaust the list.

				List<String> projects = Arrays.asList(project.split("\\|"));

				for (String currentProject : projects) {
					currentProject = currentProject.trim();
					selectDropdownItemFromMultiSelect(containingDiv, currentProject);
				}
			}

		}

		/**
		 * Method to set the <b>Assign to Area / Assistant Manager</b> field. <br>
		 * Note: This method looks for the presence of the field on the form. If it does
		 * not find it, it will simply bypass it.<br>
		 * Note: This field is required when you have a role that requires the
		 * connection, e.g. Inspector.
		 * 
		 * @param assignToAreaAssistantManager
		 */
		public void selectAssignToAreaAssistantManager(String assignToAreaAssistantManager) {
			AutomationHelper.printMethodName();

			// This only shows up in certain circumstances, e.g. when you select "Inspector"
			// role.
			AutomationHelper.adjustWaitTimeToShort();
			List<WebElement> assignToAreaAssistantManagerList = driver.findElements(By.id("formGroup-managerUserId"));
			AutomationHelper.adjustWaitTimeToNormal();

			if (assignToAreaAssistantManagerList.size() > 0) {

				AutomationHelper.selectDropdownItem(assignToAreaAssistantManagerList.get(0),
						assignToAreaAssistantManager);
			}

		}

		/**
		 * Method to read the errors on a modal.
		 * 
		 * @return String
		 */
		public String readErrors() {

			AutomationHelper.printMethodName();
			AutomationHelper.adjustWaitTimeToShort();
			List<WebElement> errors = driver.findElements(By.xpath("//p[@class='form-text form-text-error center']"));
			String errorText = "";
			if (errors.size() > 0) {
				errorText = AutomationHelper.getText(errors.get(0));
			}
			AutomationHelper.adjustWaitTimeToNormal();
			return errorText;
		}

	}

}
