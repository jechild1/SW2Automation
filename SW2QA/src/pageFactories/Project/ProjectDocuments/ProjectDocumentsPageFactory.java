package pageFactories.Project.ProjectDocuments;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import pageFactories.Project.Inspections.SW2ProjectInspectionsMainPageFactory;
import utilities.AutomationHelper;

/**
 * Page factory for the Project page > Inspections > Project Documents for
 * SW2.<br>
 * (1) Finds a reference to objects on the page <br>
 * (2) Defines methods to interact with objects on the page.<br>
 * 
 * @author Jesse Childress
 *
 */
public class ProjectDocumentsPageFactory extends SW2ProjectInspectionsMainPageFactory {

	public static String regexURL = BASE_URL + "divisions" + ".*" + "projects" + ".*" + "project-docs";

	/**
	 * Page Constructor - Calls constructor in MenusPageFactory.
	 */
	public ProjectDocumentsPageFactory() {
		super(regexURL);
	}

	/**
	 * Returns true if the passed in table name is present on the Project Documents
	 * page. This method will poll for 5 seconds before stopping.
	 * 
	 * @param tableName
	 * @return boolean
	 */
	public boolean isTableSectionPresent(String tableName) {

		AutomationHelper.adjustWaitTimeToShort();
		List<WebElement> table = driver.findElements(By.xpath("//h3[text()= '" + tableName + "']"));
		int counter = 0;

		while (table.size() < 1 && counter < 5) {
			AutomationHelper.waitSeconds(1);
			table = driver.findElements(By.xpath("//h3[text()= '" + tableName + "']"));
			counter++;
		}
		AutomationHelper.adjustWaitTimeToNormal();

		// If the table size is > 0 return true, else false.
		return table.size() > 0 ? true : false;

	}

	/**
	 * Method to return if the Document Type, e.g. Active Site Maps, actually has a
	 * table beneath it with values in it. The section can exist, with no records.
	 * This method determines if that is the case or not.
	 * 
	 * @param documentType
	 * @return boolean
	 */
	public boolean doesDocumentTypeHaveTable(String documentType) {

		AutomationHelper.adjustWaitTimeToShort();
		String xpath = "//h3[text()= '" + documentType + "']//ancestor::section[1]//table";

		List<WebElement> tableFound = driver.findElements(By.xpath(xpath));
		int counter = 0;

		while (tableFound.size() < 1 && counter < 2) {
			AutomationHelper.waitSeconds(1);
			tableFound = driver.findElements(By.xpath(xpath));
			counter++;
		}
		AutomationHelper.adjustWaitTimeToNormal();

		// If the table size is > 0 return true, else false.
		return tableFound.size() > 0 ? true : false;

	}

}
