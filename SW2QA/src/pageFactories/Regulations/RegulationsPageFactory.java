package pageFactories.Regulations;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import pageFactories.MenusPageFactory;
import pageFactories.SW2Tables;

/**
 * Page factory for the Regulations page for SW2.<br>
 * (1) Finds a reference to objects on the page <br>
 * (2) Defines methods to interact with objects on the page.<br>
 * 
 * @author Jesse Childress
 *
 */
public class RegulationsPageFactory extends MenusPageFactory {

	public static String regexURL = BASE_URL + ".*" + "groups";

	/**
	 * Page Constructor - Calls constructor in MenusPageFactory.
	 */
	public RegulationsPageFactory() {
		super(regexURL);
		waitForPageToLoad();
	}

	/**
	 * Returns a reference to the Regulations Table <br>
	 * Note: This is the main Regulations table, listing the regulations from
	 * different sites / counties / states.
	 * 
	 * @return Tables
	 */
	public SW2Tables getRegulationsTable() {
		WebElement regulationsTable = driver.findElement(By.xpath("//table[@class = 'list-table pure-table']"));
		return new SW2Tables(regulationsTable);
	}
	

}
