package pageFactories.InspectionTemplates;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import pageFactories.MenusPageFactory;
import utilities.AutomationHelper;

/**
 * Abstract page class that contains common methods for interacting with
 * Inspection Templates.
 * 
 * @author Jesse Childress
 *
 */
public abstract class SW2InspectionTemplatesMainPageFactory extends MenusPageFactory {

	/**
	 * Page Constructor - Calls constructor in MenusPageFactory.
	 */
	public SW2InspectionTemplatesMainPageFactory(String regexURL) {
		super(regexURL);
	}


	/**
	 * Clicks the <b>Questions</b> tab on the Inspection Templates > specific
	 * template page.
	 */
	public void clickQuestionsTab() {
		AutomationHelper.printMethodName();
		String xpath = "//nav[@class='tabs react-tabs__tab-list']/a[text()='Questions']";
		WebElement link = driver.findElement(By.xpath(xpath));

		link.click();
	}

	/**
	 * Clicks the <b>Certification</b> tab on the Inspection Templates > specific
	 * template page.
	 */
	public void clickCertificationTab() {
		AutomationHelper.printMethodName();
		String xpath = "//nav[@class='tabs react-tabs__tab-list']/a[text()='Certification']";
		WebElement link = driver.findElement(By.xpath(xpath));
		link.click();
	}

}
