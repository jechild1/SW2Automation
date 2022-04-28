package pageFactories.Project.Inspections;

import java.time.DateTimeException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import utilities.AutomationHelper;

/**
 * Page factory for the Project page > Inspections (All Inspections) for SW2.
 * This is the page that you'll find all the inspections listed on.<br>
 * (1) Finds a reference to objects on the page <br>
 * (2) Defines methods to interact with objects on the page.<br>
 * 
 * @author Jesse Childress
 *
 */
public class ProjectInspectionsPageFactory extends ProjectInspectionsMainPageFactory {

	public static String regexURL = BASE_URL + ".*" + "projects" + ".*" + "inspections";

	/**
	 * Page Constructor - Calls constructor in MenusPageFactory.
	 */
	public ProjectInspectionsPageFactory() {
		super(regexURL);
		//Super eventually calls waitForPageToLoad
//		waitForPageToLoad();
	}

	/**
	 * Method to read the Inspection Due Date from the Project > Inspections page.
	 * This method will handing throwing an exception if the object is not found on
	 * the page.
	 * 
	 * @return String Inspection Due Date
	 */
	public String readInspectionDueDate() {
		AutomationHelper.printMethodName();
		String xpath = "//div[@class='action-edit-date inspection-date']/span[contains(text(),'inspection due on:')]/following-sibling::span/b";

		List<WebElement> inspectionDueDate = driver.findElements(By.xpath(xpath));

		if (!AutomationHelper.isWebElementOnPage(inspectionDueDate)) {
			throw new NoSuchElementException(
					"There is not currently an Inspection Due Date listed on the Project > Inspections page");
		}

		return inspectionDueDate.get(0).getText();
	}

	/**
	 * Clicks the New Inspection Post Storm button.
	 */
	public void clickNewInspectionPostStorm() {
		AutomationHelper.printMethodName();
		long methodStartTime = System.currentTimeMillis();

		String xpath = "//div[@class='tab-pane-actions']//button[contains(text(),'New')]";
		List<WebElement>button = driver.findElements(By.xpath(xpath));
		
		int timer = 0;
		while(button.size() < 1 && timer < 120) {
			//Wait a second
			AutomationHelper.waitSeconds(1);
			
			//Attempt a new reference to the button
			button = driver.findElements(By.xpath(xpath));
			
			timer++;
		}
		
		if(button.size() > 0) {
			button.get(1).click();
						
			long methodEndTime = System.currentTimeMillis();
			System.out.println("Method clickNewInspectionPostStorm took "
					+ ((methodEndTime - methodStartTime) / 1000) + " seconds to complete.");
		}else {
			throw new ElementNotVisibleException("There is no button for New Inspeciton for Post Storm.");
		}
	}

	/**
	 * Clicks the New Routine Inspection button.
	 */
	public void clickNewRoutineInspection() {
		AutomationHelper.printMethodName();
		WebElement newRoutineInspectionButton = driver
				.findElement(By.xpath("//div[@class='tab-pane-actions']//button[contains(text(),'New')]"));
		newRoutineInspectionButton.click();
	}

	@FindBy(id = "formGroup-inspectionFilter")
	WebElement allInspectionsDropDown;

	/**
	 * Method to select a value from the All Inspections select object. <br>
	 * Valid options are:<br>
	 * <li>All Inspections</li>
	 * <li>Routine</li>
	 * <li>Routine + Post Storm</li>
	 * <li>Post Storm</li>
	 * 
	 * @param selection
	 */
	public void selectAllInspectionsValue(String selection) {
		AutomationHelper.printMethodName();
		AutomationHelper.selectDropdownItem(allInspectionsDropDown, selection);
	}

	/**
	 * Method to read the currently selected value of the All Inspections select
	 * object.
	 * 
	 * @return String
	 */
	public String readAllInspectionsValue() {
		AutomationHelper.printMethodName();
		return AutomationHelper.readSelectedSubItem(allInspectionsDropDown);
	}

	/**
	 * Clicks the Reset Filter button after a selection is made for the All
	 * Inspections select object.
	 */
	public void clickResetFilter() {
		AutomationHelper.printMethodName();
		driver.findElement(By.xpath("//button[contains(text(),'Reset filter')]")).click();
	}

	/**
	 * Method to click an inspection card on the All Inspections page.
	 * 
	 * @param inspectionDate
	 */
	public void clickInspectionCard(String inspectionDate) {
		AutomationHelper.printMethodName();
		getInspectionCard(inspectionDate).click();
	}

	/**
	 * Method to return the Inspection Number for the passed in Inspection Date.
	 * 
	 * @param inspectionDate
	 * @return String - The Inspection Number
	 */
	/**
	 * @param inspectionDate
	 * @return
	 */
	public String readInspectionNumber(String inspectionDate) {
		AutomationHelper.printMethodName();

		// Calling getContactCard ensures that the inspections card is present, and that
		// it is NOT duplicated.
		getInspectionCard(inspectionDate);

		inspectionDate = inspectionDate.replace("/", "-");

		String xpath = "//div[@class='inspection flex-item']/a/h2[contains(text(),'" + inspectionDate + "')]";

		WebElement inspectionNumberString = driver.findElement(By.xpath(xpath));

		String text = inspectionNumberString.getText();

		// String looks like "#11 | 09-28-2021 | Post Storm"
		text = text.substring(text.indexOf("#") + 1, text.indexOf(" "));

		return text;
	}

	/**
	 * Method to return the Inspection Date for the passed in Inspection Date.
	 * 
	 * @param inspectionType
	 * @return String - the Inspection Type
	 */
	public String readInspectionDate(String inspectionDate) {
		AutomationHelper.printMethodName();

		// Calling getContactCard ensures that the inspections card is present, and that
		// it is NOT duplicated.
		getInspectionCard(inspectionDate);

		// TODO - Remove me when team formats date correctly.
		inspectionDate = inspectionDate.replace("/", "-");

		// String looks like "#11 | 09-28-2021 | Post Storm"
		String xpath = "//div[@class='inspection flex-item']/a/h2[contains(text(),'" + inspectionDate + "')]";

		WebElement inspectionDateString = driver.findElement(By.xpath(xpath));

		String text = inspectionDateString.getText();

		text = text.substring(text.indexOf("|") + 2, text.lastIndexOf("|") - 1);

		return text;
	}

	/**
	 * Method to return the Inspection Type for the passed in Inspection Date.
	 * 
	 * @param inspectionType
	 * @retu6rn String - the Inspection Type
	 */
	public String readInspectionType(String inspectionDate) {
		AutomationHelper.printMethodName();

		// Calling getContactCard ensures that the inspections card is present, and that
		// it is NOT duplicated.
		getInspectionCard(inspectionDate);

		// TODO - Remove me when team formats date correctly.
		inspectionDate = inspectionDate.replace("/", "-");

		// String looks like "#11 | 09-28-2021 | Post Storm"
		String xpath = "//div[@class='inspection flex-item']/a/h2[contains(text(),'" + inspectionDate + "')]";

		WebElement inspectionTypeString = driver.findElement(By.xpath(xpath));

		String text = inspectionTypeString.getText();

		text = text.substring(text.lastIndexOf("|") + 2, text.length());

		return text;
	}

	/**
	 * Method to return the Inspection Question Status for the passed in Inspection
	 * Date.
	 * 
	 * @param inspectionDate
	 * @return String - The Inspection Question Status
	 */
	public String readInspectionQuestionStatus(String inspectionDate) {
		AutomationHelper.printMethodName();

		// Calling getContactCard ensures that the inspections card is present, and that
		// it is NOT duplicated.
		getInspectionCard(inspectionDate);

		// TODO - Remove me when team formats date correctly.
		inspectionDate = inspectionDate.replace("/", "-");

		String xpath = "//div[@class='inspection flex-item']/a/h2[contains(text(),'" + inspectionDate
				+ "')]//parent::a/p[contains(text(), 'Inspection Questions')]";

		WebElement inspectionQuestions = driver.findElement(By.xpath(xpath));

		String objectText = inspectionQuestions.getText();

		// Split to get the right hand side of the string (escaping special character)
		String[] splitText = objectText.split("\\|");

		return (splitText[1].trim());
	}

	/**
	 * Method to return the Certification Status for the passed in Inspection Date.
	 * 
	 * @param inspectionDate
	 * @return String - The Certification Status
	 */
	public String readCertificationStatus(String inspectionDate) {
		AutomationHelper.printMethodName();

		// Calling getContactCard ensures that the inspections card is present, and that
		// it is NOT duplicated.
		getInspectionCard(inspectionDate);

		// TODO - Remove me when team formats date correctly.
		inspectionDate = inspectionDate.replace("/", "-");

		String xpath = "//div[@class='inspection flex-item']/a/h2[contains(text(),'" + inspectionDate
				+ "')]//parent::a/p[contains(text(), 'Certification')]";

		WebElement certificationStatus = driver.findElement(By.xpath(xpath));

		String objectText = certificationStatus.getText();

		// Split to get the right hand side of the string (escaping special character)
		String[] splitText = objectText.split("\\|");

		return (splitText[1].trim());
	}

	/**
	 * Method to return the Compliance Status for the passed in Inspection Date.
	 * 
	 * @param inspectionDate
	 * @return String - The Compliance Status
	 */
	public String readComplianceStatus(String inspectionDate) {
		AutomationHelper.printMethodName();

		// Calling getInspectionCard ensures that the inspections card is present, and
		// that
		// it is NOT duplicated.
		getInspectionCard(inspectionDate);

		// TODO - Remove me when team formats date correctly.
		inspectionDate = inspectionDate.replace("/", "-");

		String xpath = "//div[@class='inspection flex-item']/a/h2[contains(text(),'" + inspectionDate
				+ "')]//parent::a/p[contains(text(), 'Compliance')]";

		WebElement complianceStatus = driver.findElement(By.xpath(xpath));

		String objectText = complianceStatus.getText();

		// Split to get the right hand side of the string (escaping special character)
		String[] splitText = objectText.split("\\|");

		return (splitText[1].trim());
	}

	/**
	 * Method to return the Corrective Actions count on a contact card matching the
	 * passed in inspection date.
	 * 
	 * @param inspectionDate
	 * @return String
	 */
	public String readCorrectiveActionsCount(String inspectionDate) {
		AutomationHelper.printMethodName();

		// Calling getInspectionCard ensures that the inspections card is present, and
		// that
		// it is NOT duplicated.
		getInspectionCard(inspectionDate);

		// TODO - Remove me when team formats date correctly.
		inspectionDate = inspectionDate.replace("/", "-");

		// Finds a DIV which is an over arching contact card container
		// Finds the next HTML.A object, which is a link to all below that container
		// Finds an H2 tag under the link that contains the text of the passed in
		// inspection date
		// Finds the parent of that item, which is an HTML.A
		// Finds the downstream span that says Corrective Actions
		// Finds the following sibling that is a span and contains the number.
		String xpath = "//div[@class='inspection flex-item']/a/h2[contains(text(),'" + inspectionDate
				+ "')]/parent::a//span[text()='Corrective Actions']/following-sibling::span";

		WebElement correctiveActionsSpan = driver.findElement(By.xpath(xpath));

		String correctiveActionCount = correctiveActionsSpan.getText();

		return correctiveActionCount;
	}

	/**
	 * Method to return the Maintenance Items count on a contact card matching the
	 * passed in inspection date.
	 * 
	 * @param inspectionDate
	 * @return String
	 */
	public String readMaintenanceItemsCount(String inspectionDate) {
		AutomationHelper.printMethodName();

		// Calling getInspectionCard ensures that the inspections card is present, and
		// that
		// it is NOT duplicated.
		getInspectionCard(inspectionDate);

		// TODO - Remove me when team formats date correctly.
		inspectionDate = inspectionDate.replace("/", "-");

		// Finds a DIV which is an over arching contact card container
		// Finds the next HTML.A object, which is a link to all below that container
		// Finds an H2 tag under the link that contains the text of the passed in
		// inspection date
		// Finds the parent of that item, which is an HTML.A
		// Finds the downstream span that says Corrective Actions
		// Finds the following sibling that is a span and contains the number.
		String xpath = "//div[@class='inspection flex-item']/a/h2[contains(text(),'" + inspectionDate
				+ "')]/parent::a//span[text()='Maintenance Items']/following-sibling::span";

		WebElement maintenanceItemsSpan = driver.findElement(By.xpath(xpath));

		String maintenanceItemsCount = maintenanceItemsSpan.getText();

		return maintenanceItemsCount;
	}

	/**
	 * Private method to get obtain a Inspection card which has the passed in
	 * inspection date.
	 * 
	 * @param inspectionDate
	 * @return WebElement - Inspection Card
	 */
	private WebElement getInspectionCard(String inspectionDate) {

		// Ensure the date is in the correct format. If it isn't throw an exception so
		// that the user knows why.
		if (!AutomationHelper.isDateFormatValid("MM-dd-yyyy", inspectionDate)) {
			throw new DateTimeException("Date format needs to be in dd/MM/yyyy format");
		}

		// TODO - Remove me when team formats date correctly.
		inspectionDate = inspectionDate.replace("/", "-");

		// Grab a list of inspection cards on the All Inspections page
		List<WebElement> inspectionCards = driver.findElements(By.xpath("//div[@class='inspection flex-item']"));

		// Declare a list to hold all the inspection cards that have a matching date.
		// This will be needed in order to hold duplicates, if they exist.
		List<WebElement> inspectionCardsWithText = new ArrayList<WebElement>();

		WebElement inspectionCard = null;

		// Ensure there is at least one inspection card
		if (inspectionCards.size() > 0) {

			// Cycle through each of the inspection cards and look for an "h2" that matches
			// the date
			for (WebElement currentCard : inspectionCards) {

				// Variable to hold the current text of the card.
				String cardText = currentCard.getText();

				// If the card contains the passed in date, lets add it to our List. We may end
				// up with more than one so we need to throw an exception to let the user know
				// that there is not a unique.
				if (cardText.contains(inspectionDate)) {

					inspectionCardsWithText.add(currentCard);

					// Set the Inspection Card = to the current CARD in the loop. This way we can
					// click it later, if we don't find too many.
					inspectionCard = currentCard;
				}
			}

			// If we find multiple inspection cards with the same date, we need to throw an
			// exception to guarantee that we are not clicking the wrong one.
			if (inspectionCardsWithText.size() > 1) {
				throw new ElementNotInteractableException("There are multiple inspection cards with the date '"
						+ inspectionDate + "'. Please get a grip on your data, sir.");
			}

			if (inspectionCardsWithText.size() == 0) {
				throw new NoSuchElementException("Cannot find an inspection card with date of " + inspectionDate + ".");

			}

		} else {
			throw new NoSuchElementException("There are no inspection cards on the page");
		}

		return inspectionCard;

	}

	/**
	 * Returns the number of inspection cards on the page.
	 * 
	 * @return int
	 */
	public int countInspections() {
		AutomationHelper.printMethodName();
		AutomationHelper.adjustWaitTimeToShort();
		List<WebElement> inspectionCards = driver.findElements(By.xpath("//div[@class = 'inspection flex-item']"));
		AutomationHelper.adjustWaitTimeToNormal();
		return inspectionCards.size();
	}
	
	/**
	 * Checks to see if Inspections are present on the page.
	 * @return boolean
	 */
	public boolean areInspectionsPresentOnPage() {
		AutomationHelper.printMethodName();
		return(countInspections() > 0) ? true: false;
	}

	/**
	 * Returns a reference to the Edit Project modal.
	 * 
	 * @return CreateOrEditProjectModal
	 */
	public CreateOrEditProjectModal getEditProjectModal() {
		return new CreateOrEditProjectModal();
	}

}
