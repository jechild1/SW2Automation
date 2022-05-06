package pageFactories.Divisions;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import pageFactories.SW2MenusPageFactory;
import utilities.AutomationHelper;

/**
 * This is an abstract class that will provide access to common methods for the
 * Divisions pages.
 * 
 * @author Jesse Childress
 *
 */
public abstract class DivisionsMainPageFactory extends SW2MenusPageFactory {

	/**
	 * Page Constructor - Calls constructor in MenusPageFactory.
	 */
	public DivisionsMainPageFactory(String regexURL) {
		super(regexURL);
	}

	/**
	 * Method to return the Project Name on <b>Division</b> > pages.
	 * 
	 * @return String
	 */
	public String readDivisionNameInHeader() {
		AutomationHelper.printMethodName();
		String xpath = "//div[starts-with(@class, 'division-header')]//h2";
		WebElement divisionName = driver.findElement(By.xpath(xpath));
		return divisionName.getText();
	}

	/**
	 * Clicks the <b>Edit Division</b> button
	 */
	public void clickEditDivision() {
		AutomationHelper.printMethodName();
		WebElement button = driver.findElement(By.xpath("//button[contains(text(), 'Edit')]"));

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.elementToBeClickable(button));

		button.click();
	}

	/**
	 * Clicks the <b>Delete / Trash Can icon</b> button.
	 */
	public void clickDelete() {
		AutomationHelper.printMethodName();
		WebElement button = driver.findElement(By.xpath("//i[@class='fa fa-trash']"));
		button.click();
	}

	/**
	 * Sets the <b>Search</b> field on the Divisions page(s).
	 * 
	 * @param searchText
	 */
	public void setSearchField(String searchText) {
		AutomationHelper.printMethodName();
		WebElement searchField = driver.findElement(By.xpath("//input[@class = 'search-control']"));
		AutomationHelper.setTextField(searchField, searchText);
	}

	/**
	 * Clicks the <b>Regulations</b> tab.
	 */
	public void clickRegulationsTab() {
		AutomationHelper.printMethodName();
		clickLinkByLinkText("Regulations");
	}

	/**
	 * Clicks the <b>Inspection Templates</b> tab.
	 */
	public void clickInspectionTemplatesTab() {
		AutomationHelper.printMethodName();

		clickLinkByLinkText("Inspection Templates");
	}

	/**
	 * Clicks the <b>Clients</b> tab.
	 */
	public void clickClientsTab() {
		AutomationHelper.printMethodName();
		clickLinkByLinkText("Clients");
	}

	/**
	 * Clicks the <b>Users</b> tab.
	 */
	public void clickUsersTab() {
		AutomationHelper.printMethodName();
		clickLinkByLinkText("Users");
	}

	/**
	 * Method to click a link by the text. <br>
	 * Note: The override exist to reduce scope to the nav bar.
	 */
	protected void clickLinkByLinkText(String linkText) {

		WebElement parentNavBar = driver.findElement(By.xpath("//nav[@class = 'tabs react-tabs__tab-list']"));

		String xpath = ".//a[text() = '" + linkText + "']";
		WebElement link = parentNavBar.findElement(By.xpath(xpath));
		link.click();

	}

	

}
