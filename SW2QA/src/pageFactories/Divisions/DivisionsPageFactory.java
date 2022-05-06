package pageFactories.Divisions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import pageFactories.SW2MenusPageFactory;
import pageFactories.SW2Tables;

/**
 * Page factory for the Divisions page for SW2.<br>
 * (1) Finds a reference to objects on the page <br>
 * (2) Defines methods to interact with objects on the page.<br>
 * 
 * @author Jesse Childress
 *
 */
public class DivisionsPageFactory extends SW2MenusPageFactory{
	
	public static String regexURL = BASE_URL + ".*" + "divisions";

	/**
	 * Page Constructor - Calls constructor in MenusPageFactory.
	 */
	public DivisionsPageFactory() {
		super(regexURL);
		waitForPageToLoad();
	}
	
	/**
	 * Returns a reference to the Divisions Table <br>
	 * 
	 * @return SW2Tables
	 */
	public SW2Tables getDivisionsTable() {
		WebElement divisionsTable = driver.findElement(By.xpath("//table[@class = 'list-table pure-table']"));
		return new SW2Tables(divisionsTable);
	}
	
	
	
	

}
