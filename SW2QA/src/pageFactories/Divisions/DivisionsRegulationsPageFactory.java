package pageFactories.Divisions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import pageFactories.SW2Modals;
import pageFactories.SW2Tables;
import utilities.AutomationHelper;

/**
 * Page factory for the Divisions > Regulations Tab page for SW2.<br>
 * (1) Finds a reference to objects on the page <br>
 * (2) Defines methods to interact with objects on the page.<br>
 * 
 * @author Jesse Childress
 *
 */
public class DivisionsRegulationsPageFactory extends SW2DivisionsMainPageFactory {

	public static String regexURL = BASE_URL + ".*" + "divisions" + ".*" + "resources/regulations";

	/**
	 * Page Constructor - Calls constructor in MenusPageFactory.
	 */
	public DivisionsRegulationsPageFactory() {
		super(regexURL);
		waitForPageToLoad();
	}

	/**
	 * Returns a reference to the Divisions Table <br>
	 * 
	 * @return SW2Tables
	 */
	public SW2Tables getDivisionsRegulationsTable() {
		WebElement divisionsRegulationsTable = driver
				.findElement(By.xpath("//table[@class = 'list-table pure-table']"));
		return new SW2Tables(divisionsRegulationsTable);
	}

	/**
	 * Clicks the Add Regulations button.
	 */
	public void clickAddRegulations() {
		AutomationHelper.printMethodName();
		clickButtonByText("Add Regulations");
	}

	/**
	 * Sets the <b>Search</b> field on the Divisions > Regulations page.
	 * 
	 * @param searchText
	 */
	public void setSearchField(String searchText) {
		AutomationHelper.printMethodName();
		WebElement searchField = driver.findElement(By.xpath("//input[@class = 'search-control']"));
		AutomationHelper.setTextField(searchField, searchText);
	}

	/**
	 * Returns a reference for the Add Regulations Modal
	 * 
	 * @return AddRegulationsModal
	 */
	public AddRegulationsModal getAddRegulationsModal() {
		return new AddRegulationsModal();
	}

	/**
	 * Class to contain objects for the Add Regulations modal
	 * 
	 * @author Jesse Childress
	 *
	 */
	public class AddRegulationsModal extends SW2Modals {

		/**
		 * Method to select a regulation from the <b>Regulation</b> drop down.
		 * 
		 * @param regulation
		 */
		public void selectRegulation(String regulation) {
			AutomationHelper.printMethodName();

			// Containing Div
			String xpath = "//label[@for ='formGroup-regulations']/following-sibling::div[contains(@class,'form-control--no-theme form-control form-control--customTagSelect')]";
			WebElement containingDiv = driver.findElement(By.xpath(xpath));

			selectDropdownItemFromMultiSelect(containingDiv, regulation);
		}

	}

}
