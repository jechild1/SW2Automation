package pageFactories.Clients;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import pageFactories.SW2Modals;
import pageFactories.SW2Tables;
import utilities.AutomationHelper;

/**
 * Page factory for the Client page for SW2.<br>
 * (1) Finds a reference to objects on the page <br>
 * (2) Defines methods to interact with objects on the page.<br>
 * 
 * @author Jesse Childress
 *
 */
public class ClientPageFactory extends SW2ClientsMain {

	public static String regexURL = BASE_URL + "divisions" + ".*" + "clients" + ".*";

	/**
	 * Page Constructor - Calls constructor in MenusPageFactory.
	 */
	public ClientPageFactory() {
		super(regexURL);
		waitForPageToLoad();
	}


	/**
	 * Sets the Client Name text field with the passed in text.
	 * 
	 * @param clientName
	 */
	public void setSearchClient(String clientName) {
		AutomationHelper.printMethodName();
		WebElement searchClientInput = driver.findElement(By.xpath("//input[@class='search-control']"));
		
		AutomationHelper.setTextField(searchClientInput, clientName);
		
		//TODO - Check on why this is here.
		AutomationHelper.waitSeconds(1);
		
		//After the page loads, we have to give the table a moment to load.
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//table[@class='list-table pure-table']")));
	}
	
	/**
	 * Clicks the Add Client button
	 */
	public void clickAddClient() {
		AutomationHelper.printMethodName();
		WebElement addClientButton = driver.findElement(By.xpath("//button[contains(text(),'Add Client')]"));
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.elementToBeClickable(addClientButton));
		
		addClientButton.click();
	}
	
	/**
	 * Returns a reference to the Clients Table <br>
	 * Note: This is the main Regulations table, listing the regulations from
	 * different sites / counties / states.
	 * 
	 * @return Tables
	 */
	public SW2Tables getClientsTable() {
		WebElement clientsTable = driver.findElement(By.xpath("//table[@class = 'list-table pure-table']"));
		return new SW2Tables(clientsTable);
	}
	
	/**
	 * Returns a reference to the Edit Contact Modal
	 * 
	 * @return CreateEditClientModal
	 */
	public CreateEditClientModal getEditClientModal() {
		return new CreateEditClientModal();
	}

	/**
	 * Returns a reference to the Create A New Contact Modal
	 * 
	 * @return CreateEditClientModal
	 */
	public CreateEditClientModal getCreateNewClientModal() {
		return new CreateEditClientModal();
	}
	
	// Check on reducing the scope of this class.
		/**
		 * Class to contain methods that will interaction with objects on the Create or
		 * Edit a Client modal.
		 * 
		 * @author Jesse Childress
		 *
		 */
		public class CreateEditClientModal extends SW2Modals {

			WebElement clientNameInput = driver.findElement(By.id("formGroup-name"));

			/**
			 * Reads the value from the <b>Client Name</b> text field.
			 * 
			 * @return String
			 */
			public String readClientName() {
				AutomationHelper.printMethodName();
				return AutomationHelper.getText(clientNameInput);
			}

			/**
			 * Sets the <b>Client Name</b> field in the Create New Client modal.
			 * 
			 * @param clientName
			 */
			public void setClientName(String clientName) {
				AutomationHelper.printMethodName();
				AutomationHelper.setTextField(clientNameInput, clientName);
			}

//			@FindBy(id = "formGroup-phone")
			WebElement phoneInput = driver.findElement(By.id("formGroup-phone"));

			/**
			 * Reads the value from the <b>Phone</b> text field.
			 * 
			 * @return String
			 */
			public String readPhone() {
				AutomationHelper.printMethodName();
				return AutomationHelper.getText(phoneInput);
			}

			/**
			 * Sets the <b>Phone</b> field in the Create New Client modal.
			 * 
			 * @param phone
			 */
			public void setPhone(String phone) {
				AutomationHelper.printMethodName();
				AutomationHelper.setTextField(phoneInput, phone);
			}

//			@FindBy(id = "formGroup-street1")
			WebElement address1Input = driver.findElement(By.id("formGroup-street1"));

			/**
			 * Reads the value from the <b>Address 1</b> text field.
			 * 
			 * @return String
			 */
			public String readAddress1() {
				AutomationHelper.printMethodName();
				return AutomationHelper.getText(address1Input);
			}

			/**
			 * Sets the <b>Address 1</b> field in the Create New Client modal.
			 * 
			 * @param address1
			 */
			public void setAddress1(String address1) {
				AutomationHelper.printMethodName();
				AutomationHelper.setTextField(address1Input, address1);
			}

//			@FindBy(id = "formGroup-street2")
			WebElement address2Input = driver.findElement(By.id("formGroup-street2"));

			/**
			 * Reads the value from the <b>Address 2</b> text field.
			 * 
			 * @return String
			 */
			public String readAddress2() {
				AutomationHelper.printMethodName();
				return AutomationHelper.getText(address2Input);
			}

			/**
			 * Sets the <b>Address 2</b> field in the Create New Client modal.
			 * 
			 * @param address2
			 */
			public void setAddress2(String address2) {
				AutomationHelper.printMethodName();
				AutomationHelper.setTextField(address2Input, address2);
			}

//			@FindBy(id = "formGroup-city")
			WebElement cityInput =driver.findElement(By.id("formGroup-city"));

			/**
			 * Reads the value from the <b>City</b> text field.
			 * 
			 * @return String
			 */
			public String readCity() {
				AutomationHelper.printMethodName();
				return AutomationHelper.getText(cityInput);
			}

			/**
			 * Sets the <b>City</b> field in the Create New Client modal.
			 * 
			 * @param city
			 */
			public void setCity(String city) {
				AutomationHelper.printMethodName();
				AutomationHelper.setTextField(cityInput, city);
			}

//			@FindBy(id = "formGroup-state")
			WebElement stateSelect = driver.findElement(By.id("formGroup-state"));;

			/**
			 * Reads the value from the <b>State</b> drop down field.
			 * 
			 * @return String
			 */
			public String readState() {
				AutomationHelper.printMethodName();
				return AutomationHelper.readSelectedSubItem(stateSelect);
			}

			/**
			 * Selects the <b>State</b> field in the Create New Client modal.<br>
			 * Note: Must be in format "Virginia" or "Texas"
			 * 
			 * @param state
			 */
			public void selectState(String state) {
				AutomationHelper.printMethodName();
				AutomationHelper.selectDropdownItem(stateSelect, state);
			}
			
//			@FindBy(id = "formGroup-zip")
			WebElement zipInput = driver.findElement(By.id("formGroup-zip"));;

			/**
			 * Reads the value from the <b>Zip</b> text field.
			 * 
			 * @return String
			 */
			public String readZip() {
				AutomationHelper.printMethodName();
				return AutomationHelper.getText(zipInput);
			}

			/**
			 * Sets the <b>Zip</b> field in the Create New Client modal.
			 * 
			 * @param zip
			 */
			public void setZip(String zip) {
				AutomationHelper.printMethodName();
				AutomationHelper.setTextField(zipInput, zip);
			}
			
//			@FindBy(id = "formGroup-default_finding_observation")
			WebElement defaultFindingTextArea = driver.findElement(By.id("formGroup-default_finding_observation"));;

			/**
			 * Reads the value from the <b>Default Finding</b> text area.
			 * 
			 * @return String
			 */
			public String readDefaultFinding() {
				AutomationHelper.printMethodName();
				return AutomationHelper.getText(defaultFindingTextArea);
			}

			/**
			 * Sets the <b>Default Finding</b> field in the Create New Client modal.
			 * 
			 * @param defaultFinding
			 */
			public void setDefaultFinding(String defaultFinding) {
				AutomationHelper.printMethodName();
				AutomationHelper.setTextField(defaultFindingTextArea, defaultFinding);
			}
			
			
			
			
			
			
			
			
			
			

			/**
			 * Clicks the Cancel button on the Add / Edit Client Modal
			 */
			public void clickCancel() {
				AutomationHelper.printMethodName();
				driver.findElement(By.xpath("//button[@class = 'reversed form-action-secondary' and text()='Cancel']"))
						.click();
			}

		}
	

}
