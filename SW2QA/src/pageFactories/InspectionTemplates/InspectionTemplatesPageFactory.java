package pageFactories.InspectionTemplates;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import pageFactories.SW2MenusPageFactory;
import pageFactories.SW2Popups;
import pageFactories.SW2Tables;
import utilities.AutomationHelper;

/**
 * Page factory for the Inspection Templates page for SW2. This is the main landing page<br>
 * (1) Finds a reference to objects on the page <br>
 * (2) Defines methods to interact with objects on the page.<br>
 * 
 * @author Jesse Childress
 *
 */
public class InspectionTemplatesPageFactory extends SW2MenusPageFactory {

	public static String regexURL = BASE_URL + ".*" + "inspection-templates";

	/**
	 * Page Constructor - Calls constructor in MenusPageFactory.
	 */
	public InspectionTemplatesPageFactory() {
		super(regexURL);
		waitForPageToLoad();
	}

	@FindBy(xpath = "//input[@class='search-control']")
	WebElement searchTextField;

	/**
	 * Reads the current value in the Search field.
	 * 
	 * @return String
	 */
	public String readSearchField() {
		AutomationHelper.printMethodName();
		return AutomationHelper.getText(searchTextField);
	}

	/**
	 * Sets the search field with the passed in search text.
	 * 
	 * @param searchText
	 */
	public void setSearchField(String searchText) {
		AutomationHelper.printMethodName();
		AutomationHelper.setTextField(searchTextField, searchText);
	}

	/**
	 * Clicks the Add Inspection Template button
	 */
	public void clickAddInspectionTemplate() {
		AutomationHelper.printMethodName();
		WebElement button = driver.findElement(By.xpath("//button[text()='Add Inspection Template']"));
		button.click();
	}

	/**
	 * Returns a reference to the Inspection Templates Table <br>
	 * Note: This is the main Regulations table, listing the regulations from
	 * different sites / counties / states.
	 * 
	 * @return Tables
	 */
	public SW2Tables getInspectionTemplatesTable() {
		WebElement inspectionTemplatesTable = driver.findElement(By.xpath("//table[@class = 'list-table pure-table']"));
		return new SW2Tables(inspectionTemplatesTable);
	}

	/**
	 * Method to return a reference to the "Add an Inspection Template".
	 * 
	 * @return AddInspectionTemplatePopup
	 */
	public AddInspectionTemplatePopup getAddInspectionTemplatePopup() {
		return new AddInspectionTemplatePopup();
	}

	/**
	 * Class to contain objects to work with the "Add an Inspection Template" popup
	 * modal.
	 * 
	 * @author Jesse Childress
	 *
	 */
	public class AddInspectionTemplatePopup extends SW2Popups {

		/**
		 * Returns the value of the <b>Name</b> text field.
		 * 
		 * @return String
		 */
		public String readNameInput() {
			AutomationHelper.printMethodName();
			WebElement nameInput = driver.findElement(By.id("formGroup-name"));
			return AutomationHelper.getText(nameInput);
		}

		/**
		 * Sets the value of the <b>Name</b> text field.
		 * 
		 * @param name
		 */
		public void setNameInput(String name) {
			AutomationHelper.printMethodName();
			WebElement nameInput = driver.findElement(By.id("formGroup-name"));
			AutomationHelper.setTextField(nameInput, name);
		}

	}

}
