package pageFactories.Project;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import utilities.AutomationHelper;

/**
 * Page factory for the Project page for SW2. This is the main page that lists
 * all of the project cards.<br>
 * 
 * (1) Finds a reference to objects on the page <br>
 * (2) Defines methods to interact with objects on the page.<br>
 * 
 * @author Jesse Childress
 *
 */
public class ProjectPageFactory extends SW2ProjectPageFactory {

	public static String regexURL = BASE_URL + ".*" + "projects.*";

	/**
	 * Page Constructor - Calls constructor in MenusPageFactory.
	 */
	public ProjectPageFactory() {
		super(regexURL);
		// Super calls waitForPageToLoad.
//		waitForPageToLoad();
	}

	@FindBy(xpath = "//input[@type='search']")
	WebElement searchProjectsInput;

	/**
	 * Sets a passed in value into the Search Projects input field.
	 * 
	 * @param projectName
	 */
	public void setSearchProjects(String projectName) {
		AutomationHelper.printMethodName();
		searchProjectsInput.sendKeys(projectName);
		waitForPageToLoad();

	}

	@FindBy(xpath = "//button[text()='Create New Project']")
	WebElement createNewProjectButton;

	/**
	 * Clicks the "Create New Project" button. <br>
	 * This is only visible for certain roles.
	 */
	public void clickCreateNewProjectButton() {
		AutomationHelper.printMethodName();

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.elementToBeClickable(createNewProjectButton));
		createNewProjectButton.click();
		waitForPageToLoad();
	}

	/**
	 * Navigates to the page containing the passed in project name. If the name does
	 * not exist, an exception will be thrown.
	 * 
	 * @param projectName
	 */
	public void navigateToProject(String projectName) {
		AutomationHelper.printMethodName();

		// Begin with setting the project found to false.
		boolean projectFound = false;

		// While the project isn't found, and we actually can click on the go to next
		// page
		// Took the ! off the from of paginationIsGoTONextPageDisabled.
		while (!projectFound && paginationIsGoToNextPageDisabled()) {

			// Get a list of all of the web elements that are "project cards"
			List<WebElement> projects = driver
					.findElements(By.xpath("//section/div[@class='projects']//div[@class='project-main']/h3"));

			// Cycle through each web element and see if it equals the project name.
			for (WebElement currentProjectInLoop : projects) {

				// If the current card has the project name in it, then set the flag to true and
				// click the card, then break out of the loop
				if (currentProjectInLoop.getText().equalsIgnoreCase(projectName)) {
					projectFound = true;
					break;
				}
			}

			// If we looked on the current page and still haven't found the project click Go
			// To Next Page ">". Then,
			// perform the next loop of the while. We do NOT have to check for
			// isNextPageDisabled here because it was checked in the while before we got to
			// this.
			if (!projectFound) {
				// Click the go to next page button and search again
				paginationClickGoToNextPage();
			}
		}

		// If we still have found the project, throw an exception.
		if (!projectFound) {
			throw new ElementNotVisibleException(
					"There is not a project on the visible page with a project name of '" + projectName + "'.");
		}

	}

	/**
	 * Method to click card for the passed in project name.<br>
	 * Note: This method is capable of using pagination and exhausting ALL PAGES
	 * looking for the project before throwing an exception. <br>
	 * Note: Case does not matter, but all other text does.
	 * 
	 * @param projectName
	 */
	public void clickProjectCard(String projectName) {
		AutomationHelper.printMethodName();
		// Begin with setting the project found to false.
		boolean projectFound = false;

		// While the project isn't found, and we actually can click on the go to next
		// page
//		while (!projectFound && !paginationIsGoToNextPageDisabled()) {
		do {
			// Get a list of all of the web elements that are "project cards"
			List<WebElement> projects = driver
					.findElements(By.xpath("//section/div[@class='projects']//div[@class='project-main']/h3"));

			// Cycle through each web element and see if it equals the project name.
			for (WebElement currentProjectInLoop : projects) {

				// If the current card has the project name in it, then set the flag to true and
				// click the card, then break out of the loop
				if (currentProjectInLoop.getText().equalsIgnoreCase(projectName)) {
					projectFound = true;
					currentProjectInLoop.click();
					break;
				}
			}

			// If we looked on the current page and still haven't found the project click Go
			// To Next Page ">". Then,
			// perform the next loop of the while. We do NOT have to check for
			// isNextPageDisabled here because it was checked in the while before we got to
			// this.
			if (!projectFound) {
				// Click the go to next page button and search again
				paginationClickGoToNextPage();
			}
		} while (!projectFound && !paginationIsGoToNextPageDisabled());

		// If we still have found the project, throw an exception.
		if (!projectFound) {
			throw new ElementNotVisibleException(
					"There is not a project on the visible page with a project name of '" + projectName + "'.");
		}
	}

	/**
	 * Method to check for the presense of a project card for the passed in project
	 * name.<br>
	 * Note: This method is capable of using pagination and exhausting ALL PAGES
	 * looking for the project. <br>
	 * Note: Case does not matter, but all other text does.
	 * 
	 * @param projectName
	 */
	public boolean isProjectCardPresent(String projectName) {
		AutomationHelper.printMethodName();

		// Begin with setting the project found to false.
		boolean projectFound = false;

		// While the project isn't found, and we actually can click on the go to next
		// page
//		while ((projectFound == false) && (paginationIsGoToNextPageDisabled() == false)) {
		do {
			// Get a list of all of the web elements that are "project cards"
			AutomationHelper.adjustWaitTimeToShort();
			List<WebElement> projects = driver
					.findElements(By.xpath("//section/div[@class='projects']//div[@class='project-main']/h3"));
			AutomationHelper.adjustWaitTimeToNormal();

			// Cycle through each web element and see if it equals the project name.
			for (WebElement currentProjectInLoop : projects) {

				// If the current card has the project name in it, then set the flag to true and
				// click the card, then break out of the loop
				if (currentProjectInLoop.getText().equalsIgnoreCase(projectName)) {
					projectFound = true;
					break;
				}
			}

			// If we looked on the current page and still haven't found the project click Go
			// To Next Page ">". Then,
			// perform the next loop of the while. We do NOT have to check for
			// isNextPageDisabled here because it was checked in the while before we got to
			// this.
			if (projectFound == false && !paginationIsGoToNextPageDisabled()) {
				// Click the go to next page button and search again
				paginationClickGoToNextPage();
			}
		} while (projectFound == false && !paginationIsGoToNextPageDisabled());

		return projectFound;
	}

	/**
	 * Method to read the text of the Project Card - Project Name, according to the
	 * passed in text. <br>
	 * Note: Yes, this method does read back what you pass in.
	 * 
	 * @param projectName
	 * @return String - Project Card - Project Name - The text of the project name
	 */
	public String readProjectCardProjectName(String projectName) {
		AutomationHelper.printMethodName();
		// Attempt to find the project on the page
		navigateToProject(projectName);

		// Obtain a reference to the card
		String xpath = "//div[@class='project']/div[@class='project-main']/h3[text()='" + projectName + "']";
		WebElement projectNameObject = driver.findElement(By.xpath(xpath));

		return projectNameObject.getText();

	}

	/**
	 * Method to read the text of the Project Card - Client Name based on the passed
	 * in projectName.
	 * 
	 * @param projectName
	 * @return String - Project Card - Client Name
	 */
	public String readProjectCardClient(String projectName) {
		AutomationHelper.printMethodName();
		// Attempt to find the project on the page
		navigateToProject(projectName);

		String xpath = "//div[@class='project']/div[@class='project-main']/h3[text()='" + projectName
				+ "']/parent::div/h4";

		WebElement clientNameObject = driver.findElement(By.xpath(xpath));

		// The Client was sometimes slow to load. Put this expected condition (with NOT
		// to invert it), waits for 10 seconds until the text attribute does NOT contain
		// an empty string.
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.not(ExpectedConditions.attributeContains(clientNameObject, "text", "")));

		return clientNameObject.getText();
	}

	/**
	 * Method to read the text of the Project Card - Project Address based on the
	 * passed in projectName.
	 * 
	 * @param projectName
	 * @return String - Project Card - Project Address
	 */
	public String readProjectCardAddress(String projectName) {
		AutomationHelper.printMethodName();
		// Attempt to find the project on the page
		navigateToProject(projectName);

		String xpath = "//div[@class='project']/div[@class='project-main']/h3[text()='" + projectName
				+ "']/parent::div/h6";
		WebElement projectCardObject = driver.findElement(By.xpath(xpath));

		return projectCardObject.getText();
	}

	/**
	 * Method to read the text of Project Card - Inspections based on the passed in
	 * projectName.
	 * 
	 * @param projectName
	 * @return String - Project Card - Project number of inspections that were
	 *         found.
	 */
	public String readProjectCardNumberOfInspections(String projectName) {
		AutomationHelper.printMethodName();
		// Attempt to find the project on the page
		navigateToProject(projectName);

		String xpath = "//div[@class='project']/div[@class='project-main']/h3[text()='" + projectName
				+ "']/parent::div//i";
		WebElement numberOfInspectionsObject = driver.findElement(By.xpath(xpath));

		return numberOfInspectionsObject.getText();
	}

	/**
	 * Clicks the "Create Multiple Post-Storm Inspections" link on the Projects
	 * Page. <br>
	 * Note: This link is behind the three dot ellipsis button.
	 */
	public void clickCreateMultiplePostStormInspections() {
		AutomationHelper.printMethodName();
		clickButtonBehindThreeDotEllipsis("Create Multiple Post-Storm Inspections");
	}

	/**
	 * Clicks the "View Archived Projects" link on the Projects Page. <br>
	 * Note: This link is behind the three dot ellipsis button.
	 */
	public void clickViewArchivedProjects() {
		AutomationHelper.printMethodName();
		clickButtonBehindThreeDotEllipsis("View Archived Projects");
	}

	/**
	 * Method to return a reference to the Create New Project modal.
	 * 
	 * @return createOrEditProject
	 */
	public CreateOrEditProjectModal getCreateNewProjectModal() {
		return new CreateOrEditProjectModal();
	}

}
