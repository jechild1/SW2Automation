package pageFactories.Clients;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import utilities.AutomationHelper;

/**
 * This is an abstract class that will provide access to common methods for the
 * Clients pages.
 * 
 * @author Jesse Childress
 *
 */
public abstract class SW2ClientsShared extends SW2ClientsMain {


	/**
	 * Page Constructor - Calls constructor in MenusPageFactory.
	 */
	public SW2ClientsShared(String regexURL) {
		super(regexURL);

	}
	
	/**
	 * Method to return the Client Name on the Client > Client Contacts / Client Projects page.
	 * 
	 * @return Client Name
	 */
	public String readClientName() {
		AutomationHelper.printMethodName();
		String xpath = "//div[@class='detail-content pure-u pure-u-md-1-2 pure-u-1']/h2";
		WebElement clientName = driver.findElement(By.xpath(xpath));
		return clientName.getText();
	}
	
	
	
	
	
	



}
