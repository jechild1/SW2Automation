package pageFactories.Regulations;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import pageFactories.SW2MenusPageFactory;
import pageFactories.SW2Popups;
import pageFactories.SW2Tables;
import utilities.AutomationHelper;

/**
 * Page factory for the Regulation Details page for SW2. This is the page that
 * loads specific regulations.<br>
 * (1) Finds a reference to objects on the page <br>
 * (2) Defines methods to interact with objects on the page.<br>
 * 
 * @author Jesse Childress
 *
 */
public class RegulationsDetailsPageFactory extends SW2MenusPageFactory {

	public static String regexURL = BASE_URL + ".*" + "groups" + ".*" + "regulations";

	/**
	 * Page Constructor - Calls constructor in MenusPageFactory.
	 */
	public RegulationsDetailsPageFactory() {
		super(regexURL);
	}

	/**
	 * Returns a reference to the Regulation Details Table <br>
	 * Note: This is the table of items within a particular regulation.
	 * 
	 * @return Tables
	 */
	public SW2Tables getRegulationDetailsTable() {
		WebElement regulationsTable = driver.findElement(By.xpath("//table[@class = 'document-group__items']"));
		return new SW2Tables(regulationsTable);
	}

}
