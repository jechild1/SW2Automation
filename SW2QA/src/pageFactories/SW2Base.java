package pageFactories;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import configuration.SW2Config;
import utilities.AutomationHelper;

/**
 * Base abstract Page factory for SW2 that contains common wrapper methods
 * by:<br>
 * (1) Finds a reference to objects on the page <br>
 * (2) Defines methods to interact with objects on the page.<br>
 * 
 * @author Jesse Childress
 *
 */
public abstract class SW2Base extends SW2Config {

	public void initializeElements() {
		PageFactory.initElements(driver, this);
	}

	// Worked with Andrew to find this xpath. Discovered that there are areas in
	// which Loading BMP Details or something would pop up. Modified xpath to
	// Contains to account for more.
//	@FindBy(xpath = "//h3[text() = 'Loading']")
//	List<WebElement> pageLoadingModal;
	@FindBy(xpath = "//h3[contains(text(), 'Loading')]")
	List<WebElement> pageLoadingModal;

	@FindBy(xpath = "//div[@class= 'successNotification show']")
	List<WebElement> successNotification;

	// Duplicated the one above.
	@FindBy(xpath = "//div[@class= 'successNotification show']")
	List<WebElement> checkImageNotification;

	@FindBy(xpath = "//div[@class = 'Toastify__toast-container Toastify__toast-container--top-right']")
	List<WebElement> savingInspectionNotification;

	@FindBy(xpath = "//div[@class='Toastify']/div")
	List<WebElement> updatingFindingNotification;

	/**
	 * Is the Loading spinner displaying on the page
	 * 
	 * @return boolean - true = yes | false = no
	 */
	protected boolean isPageLoading() {
		boolean isPageLoading = false;

		AutomationHelper.adjustWaitTimeToVeryShort();
		isPageLoading = AutomationHelper.isWebElementOnPage(pageLoadingModal);
		AutomationHelper.adjustWaitTimeToNormal();

		return isPageLoading;
	}

	/**
	 * Method to check for the presence of the Success Notification. This is
	 * sometimes the green check that can display. It can be found when editing /
	 * creating a new project and saving (among other places).
	 * 
	 * @return boolean
	 */
	protected boolean isSuccessNotificationPresent() {
		boolean isSuccessNotificationVisible = false;

		AutomationHelper.adjustWaitTimeToVeryShort();
		isSuccessNotificationVisible = AutomationHelper.isWebElementOnPage(successNotification);
		AutomationHelper.adjustWaitTimeToNormal();

		return isSuccessNotificationVisible;
	}

	/**
	 * Method to check for the presence of the Inspection Saving Notification. This
	 * is present when navigating away from the Inspection Questions page, or
	 * clicking save on that tab.
	 * 
	 * @return boolean
	 */
	protected boolean isSavingNotificationPresent() {
		boolean isSavingNotificationVisible = false;

		AutomationHelper.adjustWaitTimeToShort();
		isSavingNotificationVisible = AutomationHelper.isWebElementOnPage(savingInspectionNotification);
		AutomationHelper.adjustWaitTimeToNormal();

		return isSavingNotificationVisible;
	}

	/**
	 * Method to check for the presence of the Updating Finding notice (mini-popup).
	 * This is present when clicking save on on the Inspection Findings page.
	 * 
	 * @return boolean
	 */
	protected boolean isUpdatingFindingPresent() {
		boolean isUpdatingFindingNotificationVisible = false;

		AutomationHelper.adjustWaitTimeToShort();
		isUpdatingFindingNotificationVisible = AutomationHelper.isWebElementOnPage(updatingFindingNotification);
		AutomationHelper.adjustWaitTimeToNormal();

		return isUpdatingFindingNotificationVisible;
	}

	/**
	 * Method to check for the presence of the Updating Finding notice (mini-popup).
	 * This is present when clicking save on on the Inspection Findings page.
	 * 
	 * @return boolean
	 */
	protected boolean isCheckImageNotificationPresent() {
		boolean isCheckImageNotificationVisible = false;

		AutomationHelper.adjustWaitTimeToShort();
		isCheckImageNotificationVisible = AutomationHelper.isWebElementOnPage(checkImageNotification);
		AutomationHelper.adjustWaitTimeToNormal();

		return isCheckImageNotificationVisible;
	}

	@FindBy(xpath = "//div[@class = 'upload-control dropzone upload-control--row upload-control--loading'] | //div[@class = 'upload-control dropzone upload-control--loading']")
	List<WebElement> fileUploadingSpinner;

	/**
	 * Method to check to see if the file is uploading. It'll look for the spinner
	 * object.
	 * 
	 * @return boolean
	 */
	protected boolean isFileUploading() {
		boolean isFileUploadingSpinnerVisible = false;

		AutomationHelper.adjustWaitTimeToShort();
		isFileUploadingSpinnerVisible = AutomationHelper.isWebElementOnPage(fileUploadingSpinner);
		AutomationHelper.adjustWaitTimeToNormal();

		return isFileUploadingSpinnerVisible;
	}

	@FindBy(xpath = "//div[@class = 'loading-div']")
	List<WebElement> imageUploadingSpinner;

	/**
	 * Method to check to see if an image is uploading. WHen you click Save on an
	 * inspection, the image will often turn into a spinning icon. Currently unclear
	 * about how this is different than FileUploadingSpinner above.
	 * 
	 * @return boolean
	 */
	protected boolean isImageUploading() {
		boolean isImageUploadingSpinnerVisible = false;

		AutomationHelper.adjustWaitTimeToShort();
		isImageUploadingSpinnerVisible = AutomationHelper.isWebElementOnPage(imageUploadingSpinner);
		AutomationHelper.adjustWaitTimeToNormal();

		return isImageUploadingSpinnerVisible;
	}

	@FindBy(xpath = "//*[local-name()='svg' and @class='svg-loading']")
	List<WebElement> buttonSpinner;

	/**
	 * Method to check to see if a button is still spinning after being clicked.
	 * When you click a button, it will often turn into a spinning icon.
	 * 
	 * @return boolean
	 */
	protected boolean isButtonSpinning() {
		boolean isButtonSpinnerVisible = false;

		AutomationHelper.adjustWaitTimeToShort();
		isButtonSpinnerVisible = AutomationHelper.isWebElementOnPage(buttonSpinner);
		AutomationHelper.adjustWaitTimeToNormal();

		return isButtonSpinnerVisible;
	}

	/**
	 * Utility method to wait for a button to stop spinning. When a button is
	 * clicked, sometimes it turns into a spinner and the page doesn't refresh until
	 * it is finished.
	 */
	public void waitForButtonSpinner() {

		int loopCount = 0;
		long start = System.currentTimeMillis();

		// Loop for 5 min
		while (isButtonSpinning() && loopCount <= 300) {
			AutomationHelper.waitSeconds(1);
			loopCount++;
		}

		long finish = System.currentTimeMillis();
		long totalTime = finish - start;
		System.out.println("Wait for button spinner to finish took " + (totalTime / 1000) + " seconds.");
	}

	/**
	 * Bootstrap elements are sometimes disabled through the class name where
	 * javascript takes care of making it disabled instead of HTML.
	 * 
	 * This method checks for instances where a element is disabled by bootstrap
	 * rather than HTML
	 * 
	 * @param element
	 * @return boolean - returns true if enabled; false if not enabled.
	 */
	private boolean isBootstrapElementEnabled(WebElement element) {
		String className = element.getAttribute("class").toLowerCase();

		return !className.contains("disabled");
	}

	@FindBy(xpath = "//a[@rel='prev']")
	WebElement tablePreviousButton;

	@FindBy(className = "PagedList-skipToPrevious")
	WebElement tablePreviousButtonParent;

	/**
	 * Cycles through back to first paginated table page if not on it already
	 */
	public void gotoFirstTablePage() {
		AutomationHelper.printMethodName();

		while (isBootstrapElementEnabled(tablePreviousButtonParent)) {
			tablePreviousButton.click();

			waitForPageToLoad();
		}
	}

	@FindBy(xpath = "//a[@rel='next']")
	WebElement tableNextButton;

	@FindBy(className = "PagedList-skipToNext")
	WebElement tableNextButtonParent;

	/**
	 * Cycles through paginated table until a specified row can be found
	 * 
	 * @param table
	 * @param primaryColumnHeader
	 * @param primaryColumnValue
	 * 
	 * @throws NotFoundException - if specified row is not found
	 */
	public void gotoTablePageWithRow(SW2Tables table, String primaryColumnHeader, String primaryColumnValue) {
		AutomationHelper.printMethodName();

		// first - make sure already on first paginated page
		gotoFirstTablePage();

		while (!table.isRowInTableByValue(primaryColumnHeader, primaryColumnValue)) {
			if (isBootstrapElementEnabled(tableNextButtonParent)) {
				tableNextButton.click();

				waitForPageToLoad();

			} else {
				throw new NotFoundException(String.format(
						"'%s' could not be found in column '%s' of table.  Attempted to look at all pages.  Please check the value and try again",
						primaryColumnValue, primaryColumnHeader));
			}
		}
	}

	/**
	 * Waits for loading icon to disable - indicating page is loaded
	 */
	public void waitForPageToLoad() {

		long start = System.currentTimeMillis();

		while (isPageLoading()) {
			AutomationHelper.waitMillis(100);
		}

		// This can check a button icon, but also the spinner in general. This spinner
		// can happen when were waiting on a page to load, and it says something like
		// "Loading Inspection Details"
		while (isButtonSpinning()) {
			AutomationHelper.waitMillis(100);
		}

		//This method waits for the green checkbox to disappear. 
		while (isSuccessNotificationPresent()) {
			AutomationHelper.waitMillis(100);
		}

		long finish = System.currentTimeMillis();

		long totalTime = finish - start;
		System.out.println("Wait for page load took " + Duration.ofMillis(totalTime)
				+ " seconds.");

	}

	/**
	 * Waits for the loading icon to disable.
	 */
	public void waitForModalToLoad() {

		long start = System.currentTimeMillis();

		while (isPageLoading()) {
			AutomationHelper.waitMillis(100);
		}

		long finish = System.currentTimeMillis();

		long totalTime = finish - start;
		System.out.println("Waiting for the modal to load took " + Duration.ofMillis(totalTime)
				+ " seconds. Checks isPageLoading()");

	}

	/**
	 * Utility method to wait for a file to upload. This looks at the spinner that
	 * displays while a file is being uploaded.
	 */
	public void waitForFileToLoad() {

		int loopCount = 0;

		long start = System.currentTimeMillis();
		// Loop for 5 min
		while (isFileUploading() && loopCount <= 300) {
			AutomationHelper.waitSeconds(1);
			loopCount++;
		}
		long finish = System.currentTimeMillis();
		long totalTime = finish - start;
		System.out.println("Wait for file load took " + (totalTime / 1000) + " seconds.");
	}

	/**
	 * Utility method to wait for an image to upload. This looks at the spinner that
	 * displays while a image is being uploaded. This can exist on the Findings page
	 * when saving a finding.
	 */
	public void waitForImageToLoad() {

		int loopCount = 0;
		long start = System.currentTimeMillis();
		// Loop for 5 min
		while (isImageUploading() && loopCount <= 300) {
			AutomationHelper.waitSeconds(1);
			loopCount++;
		}
		long finish = System.currentTimeMillis();
		long totalTime = finish - start;
		System.out.println("Wait for image load took " + (totalTime / 1000) + " seconds.");
	}

	/**
	 * Utility method to wait for an Inspection to save. It looks for the
	 * "Inspection Saved" or "Saving Inspection" item on the page. It will not
	 * proceed until this is gone away.
	 */
	public void waitForInspectionToSave() {

		int loopCount = 0;
		long start = System.currentTimeMillis();
		// Loop for 5 min
		while (isSavingNotificationPresent() && loopCount <= 300) {
			AutomationHelper.waitSeconds(1);
			loopCount++;
		}

		long finish = System.currentTimeMillis();
		long totalTime = finish - start;
		System.out.println("Wait for Inspection to Save load took " + (totalTime / 1000) + " seconds.");
	}

	/**
	 * Utility method to wait for a finding to save. It looks for the "Updating
	 * finding" mini-popup item on the page. It will not proceed until this is gone
	 * away.
	 */
	public void waitForUpdatingFindingToSave() {

		int loopCount = 0;
		long start = System.currentTimeMillis();

		// Loop for 5 min
		while (isUpdatingFindingPresent() && loopCount <= 300) {
			AutomationHelper.waitSeconds(1);
			loopCount++;
		}

		long finish = System.currentTimeMillis();
		long totalTime = finish - start;
		System.out.println("Wait for Inspection to Save load took " + (totalTime / 1000) + " seconds.");
	}

	/**
	 * Method to wait on a check to appear, then disappear after performing a save.
	 * <br>
	 * Note: This can happen when saving a finding. The images will load, and things
	 * will work to save. Eventually a check will appear and disappear, showing the
	 * user that the file has been updated.
	 */
	public void waitForCheckSuccessToAppearAndDisappear() {

		boolean found = false;

		// FIRST, wait for the object to appear. We will wait 5 minutes / 300 seconds
		// for this to happen.
		int loopCount = 0;
		long start = System.currentTimeMillis();

		// Loop for 5 min - We expect a FALSE
		found = isCheckImageNotificationPresent();
		while (!found && loopCount <= 300) {
			AutomationHelper.waitSeconds(1);
			loopCount++;
			found = isCheckImageNotificationPresent();
		}

		long finish = System.currentTimeMillis();
		long totalTime = finish - start;

		if (found) {
			Reporter.log("Waited for the Check image to appear took " + (totalTime / 1000) + " seconds.", true);
		}

		// If the object never appears, throw an exception
		if (!found) {
			throw new ElementNotVisibleException("The check image did not appear after 5 minutes of trying.");
		}

		// SECOND, wait for the object to disappear
		loopCount = 0;

		start = System.currentTimeMillis();

		while (found && loopCount < 300) {
			AutomationHelper.waitSeconds(1);
			loopCount++;
			found = isCheckImageNotificationPresent();
		}

		finish = System.currentTimeMillis();
		totalTime = finish - start;

		if (!found) {
			Reporter.log("Waited for the Check image disappear took " + (totalTime / 1000) + " seconds.", true);
		}

	}

	/**
	 * Returns a popup modal element. This is used to organize code and reduce scope
	 * of searches for objects within a modal.
	 * 
	 * @return WebElement
	 */
	public SW2Popups getPopupModal() {
		return new SW2Popups();
	}

	/**
	 * Method to upload a file(s) when an HTML.Input object is present on the page.
	 * 
	 * @param filesToUpload - only the path forward from localDataSets. E.g.
	 *                      "example-finding-images//picture1.jpeg"
	 */
	public void uploadFile(String filesToUpload) {

		// First, see if the data has multiple files, as separated by a comma.
		String[] files = filesToUpload.split(",");
		// Remove the white space from before and after each separate file name
		for (int i = 0; i < files.length; i++) {
			files[i] = files[i].trim();
		}

		// Loop through each item in the array and perform an upload

		for (String currentFile : files) {

			String xpath = "//input[@type='file'][@multiple = '']";
			WebElement fileUploadObject = driver.findElement(By.xpath(xpath));

			String filePath = generateFullFileNameAndPath(currentFile);
			// Good to print in the log
			Reporter.log("File Uploaded: " + filePath, true);

			fileUploadObject.sendKeys(filePath);
			waitForFileToLoad();

		}
	}
	
	/**
	 * Returns the User Role from any page in the application. <br>
	 * Note: this works by storing the current URL, navigating to the profile and
	 * scraping the user role, and then re-loading the original page.
	 * 
	 * @return String
	 */
	public String getUserRole() {
		// Step one - Capture current page
		String originalURL = driver.getCurrentUrl();

		clickHome();
		DashboardPageFactory dashboardPageFactory = new DashboardPageFactory();
		dashboardPageFactory.clickMyProfile();
		
		ProfilePageFactory profilePage = new ProfilePageFactory();
		String role = profilePage.readRoleType();

		driver.get(originalURL);
		return role;

	}

	/**
	 * Utility method to click a button by text. <br>
	 * Note: The text must match exactly. <br>
	 * Note: This method will wait for an object to become clickable.
	 * 
	 * @param buttonName
	 */
	protected void clickButtonByText(String buttonName) {

		String xpath = "//button[text() = '" + buttonName + "']";
		WebElement button = driver.findElement(By.xpath(xpath));

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.elementToBeClickable(button));

		button.click();
	}

	/**
	 * Utility method to click a link (Html.a) by the link text. <br>
	 * Note: The text must match exactly
	 * 
	 * @param linkText
	 */
	protected void clickLinkByLinkText(String linkText) {
		String xpath = "//a[text() = '" + linkText + "']";
		WebElement link = driver.findElement(By.xpath(xpath));
		link.click();
	}

	/**
	 * Protected method to click a link behind the three dot ellipsis on the
	 * Projects landing page.
	 */
	protected void clickButtonBehindThreeDotEllipsis(String linkText) {

		// Containing Div (three dot ellipsis button)
		WebElement threeDotEllipsis = driver.findElement(By.xpath(
				"//button[@class = 'outline overflow'] | //button[@class = 'dropdown-menu-toggle action-buttons-secondary-toggle outline'] | //button[@class = 'outline client-kebob']"));

		WebElement popperDiv = driver.findElement(By.id("popper"));

		selectDropdownItemFromFlexSelector(threeDotEllipsis, popperDiv, linkText);
	}

	/**
	 * Method to click the "Drag and drop files here to upload" button. <br>
	 * Note: This method will cause the windows file upload modal to display.
	 */
	public void clickDragAndDropFilesHereToUpload() {
		AutomationHelper.printMethodName();

		WebElement fileUploadButton = null;

		fileUploadButton = driver.findElement(By.xpath("//span[text()='Drag and drop files here to upload.']"));

		if (fileUploadButton == null) {
			throw new ElementNotVisibleException(
					"There is no 'Drag and drop files here to upload.' button on the page.");
		} else {
			fileUploadButton.click();
		}

	}

	/**
	 * Method to return a String List of divisions that exist in the divisions drop
	 * down.
	 * 
	 * @return List<String>
	 */
	protected List<String> getDivisionsInDropdown() {

		WebElement containingDiv = driver
				.findElement(By.xpath("//span[@class = 'flex-row division-selector pointer']"));

		WebElement popperDiv = driver.findElement(By.id("popper"));

		// First, click the containing div that holds the pop out menu. This could be
		// clicking the division.
		containingDiv.click();

		// To wait for element visible
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(NORMAL_TIMEOUT));
		wait.until(ExpectedConditions.elementToBeClickable(popperDiv));

		// Create a list of all of the list items beneath the popper div
		List<WebElement> listItemsInWebElement = driver
				.findElements(By.xpath("//div[@class='popper']//li | //div[@class='popper client-menu']//li"));

		List<String> listItemsInString = new ArrayList<String>();

		for (WebElement currentElement : listItemsInWebElement) {
			listItemsInString.add(currentElement.getText().trim());
		}

		return listItemsInString;
	}

	/**
	 * Method to handle clicking drop down items in a flex selector. This method
	 * requires the div that contains the pop out element, the pop out element, and
	 * also a value for the item to select.
	 * 
	 * @param containingDiv
	 * @param popperDiv
	 * @param itemToSelect
	 */
	protected void selectDropdownItemFromFlexSelector(WebElement containingDiv, WebElement popperDiv,
			String itemToSelect) {

		// First, click the containing div that holds the pop out menu. This could be
		// clicking the division.
		containingDiv.click();

		// To wait for element visible
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(NORMAL_TIMEOUT));
		wait.until(ExpectedConditions.elementToBeClickable(popperDiv));

		// Create a list of all of the list items beneath the popper div
		List<WebElement> listItems = driver
				.findElements(By.xpath("//div[@class='popper']//li | //div[@class='popper client-menu']//li"));

		// If we do not find any list items, let the user know.
		if (listItems.size() == 0) {
			throw new ElementNotVisibleException("There are no list items avaliable");
		}

		// This boolean is required so that we can throw an exception if we do not find
		// an item to click.
		boolean listItemClicked = false;

		// Loop through all of the list items and click the one that matches the passed
		// in value by the user.
		for (WebElement currentListItem : listItems) {
			if (currentListItem.getText().equalsIgnoreCase(itemToSelect)) {
				currentListItem.click();
				listItemClicked = true;
				break;
			}
		}

		// If we didn't get to click an item, throw a meaningful error.
		if (!listItemClicked) {
			throw new ElementNotVisibleException("The item of '" + itemToSelect + "' is not visible in the list.");
		}
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
		wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//div[contains(@class, 'tag-select__menu-list tag-select__menu-list')]")));

		// When the element is finally visible, grab all the DIVs (list items) that are
		// on the page
		List<WebElement> divListItems = driver.findElements(By.xpath(
				"//div[contains(@class, 'tag-select__menu-list tag-select__menu-list')]/div[contains(@id, 'react-select')]"));

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

	/**
	 * Method to read Drop Down Items from a multi select.
	 * 
	 * @return String
	 */
	protected String readDropdownItemsFromMultiSelect(WebElement containingDiv) {

		List<WebElement> selectedDropdownItems = driver.findElements(
				By.xpath("//div[@class ='form-group form-group--customTagSelect']//span[@class='input-tag']))"));

		// String to hold the drop down items, which will later be separated by a space
		// If we do not have any items that have been selected, we will return empty
		// String.
		String dropDownItems = "";

		for (WebElement currentDropDownItem : selectedDropdownItems) {
			dropDownItems += currentDropDownItem.getText() + " ";
		}

		return dropDownItems.trim();
	}

	/**
	 * Method to return a reference to the "Are you sure you want to delete this
	 * document popup".
	 * 
	 * @return DeleteDocumentPopup
	 */
	public DeleteDocumentPopup getDeleteDocumentPopup() {
		return new DeleteDocumentPopup();
	}

	/**
	 * Class to contain objects to work with the "Are you sure you want to delete
	 * this document" popup modal.
	 * 
	 * @author Jesse Childress
	 *
	 */
	public class DeleteDocumentPopup extends SW2Popups {

	}

}
