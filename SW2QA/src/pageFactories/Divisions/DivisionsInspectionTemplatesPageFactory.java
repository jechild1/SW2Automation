package pageFactories.Divisions;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import pageFactories.SW2Modals;
import pageFactories.SW2Tables;
import utilities.AutomationHelper;

/**
 * Page factory for the Divisions > Inspection Templates Tab page for SW2.<br>
 * (1) Finds a reference to objects on the page <br>
 * (2) Defines methods to interact with objects on the page.<br>
 * 
 * @author Jesse Childress
 *
 */
public class DivisionsInspectionTemplatesPageFactory extends DivisionsMainPageFactory {

	public static String regexURL = BASE_URL + ".*" + "divisions" + ".*" + "resources/inspection-templates";

	/**
	 * Page Constructor - Calls constructor in MenusPageFactory.
	 */
	public DivisionsInspectionTemplatesPageFactory() {
		super(regexURL);
		waitForPageToLoad();
	}

	/**
	 * Returns a reference to the <b>Inspction Templates</b> table
	 * 
	 * @return SW2Tables
	 */
	public SW2Tables getInspectionTemplatesTable() {
		WebElement divisionsInspectionTemplatesTable = driver
				.findElement(By.xpath("//table[@class = 'list-table pure-table']"));
		return new SW2Tables(divisionsInspectionTemplatesTable);
	}

	/**
	 * Clicks the <b>Add Templates</b> button.
	 */
	public void clickAddTemplates() {
		AutomationHelper.printMethodName();
		clickButtonByText("Add Templates");
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
	 * Returns a reference for the <b>Add Inspection Templates</b> modal
	 * 
	 * @return AddRegulationsModal
	 */
	public AddInspectionTemplatesModal getAddInspectionTemplatesModal() {
		return new AddInspectionTemplatesModal();
	}

	/**
	 * Class to contain objects for the <b>Add Inspection Templates</b> modal
	 * 
	 * @author Jesse Childress
	 *
	 */
	public class AddInspectionTemplatesModal extends SW2Modals {

		/**
		 * Method to select a Inspection Templates from the <b>Inspection Templates</b>
		 * drop down.
		 * 
		 * @param inspectionTemplatesName
		 */
		public void selectInspectionTemplates(String inspectionTemplatesName) {
			AutomationHelper.printMethodName();

			// Containing Div
//			String xpath = "//label[@for ='formGroup-inspectionTemplates']/following-sibling::div[contains(@class,'form-control--no-theme form-control form-control--customTagSelect')]";
//			WebElement containingDiv = driver.findElement(By.xpath(xpath));

			String xpath = "//div[contains(@class , 'tag-select__indicator tag-select__dropdown-indicator')]";
			WebElement containingDiv = driver.findElement(By.xpath(xpath));

			selectDropdownItemFromMultiSelect(containingDiv, inspectionTemplatesName);

		}

		/**
		 * Method to select drop down items from a multi select div (Flex type)
		 * 
		 * @param containingDiv - This is the main DIV panel that holds all the child
		 *                      select type objects
		 * @param listItemText
		 */
		protected void selectDropdownItemFromMultiSelect(WebElement containingDiv, String listItemText) {

			// Click the containing div panel that holds the objects for the "select"
			containingDiv.click();

			// Sometimes there is a "flicker" and the form is repositioned. Lets move the
			// mouse slightly and see if we can modify focus.
			Robot robot = null;
			try {
				robot = new Robot();
				robot.keyPress(KeyEvent.VK_PAGE_DOWN);
				robot.keyRelease(KeyEvent.VK_PAGE_DOWN);
			} catch (AWTException e) {

				e.printStackTrace();
			}

			// To wait for element visible
			// Note - if Item isn't in the list, our exception will be an expected condition
			// here.
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

			//NOTE::: This is the reason that the class is an override. The DIV is different.
//			wait.until(ExpectedConditions.elementToBeClickable(
//					By.xpath("//div[contains(@class, 'tag-select__menu-list tag-select__menu-list')]")));
			wait.until(
					ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(@class,'tag-select__input')]")));

			// When the element is finally visible, grab all the DIVs (list items) that are
			// on the page

			List<WebElement> divListItems = driver.findElements(By.xpath("//div[contains(@id, 'react-select')]"));

			// Perform error check
			if (divListItems.size() == 0) {
				throw new ElementNotVisibleException("There are no div list items to select.");
			}

			// Cycle through each of the div list items and click the one that contains the
			// text that we passed in.
			boolean liItemFound = false;
			for (WebElement currentDiv : divListItems) {
				String currentDivText = currentDiv.getText();
				if (currentDivText.equalsIgnoreCase(listItemText)) {
					currentDiv.click();

					liItemFound = true;
					break;// If no break, you'll get a stale reference error.
				}
			}

			if (!liItemFound) {
				Reporter.log("There is no drop down option with the name '" + listItemText + "' available.", true);
				throw new ElementNotVisibleException(
						"There is no drop down option with the name '" + listItemText + "' available.");
			}
		}

	}

}
