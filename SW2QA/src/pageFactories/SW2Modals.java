package pageFactories;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import utilities.AutomationHelper;

public class SW2Modals extends SW2Base {
	
	public static WebElement MODAL;

	public SW2Modals() {

		PageFactory.initElements(driver, this);
		// Instantiating this here prevents stale reference when multiple modal calls
		// are made during the same test.
		MODAL = driver.findElement(By.xpath("//div[@id = 'modal']"));
		waitForModalToLoad();
	}
	
	@FindBy(xpath = "//div[@id = 'modal']")
	List<WebElement> modal;
	
	/**
	 * Method to check to see if a modal is displayed.
	 * 
	 * @return boolean
	 */
	protected boolean isModalVisible() {
		boolean isModalVisible = false;

		AutomationHelper.adjustWaitTimeToVeryShort();
		isModalVisible = modal.get(0).isDisplayed();
		AutomationHelper.adjustWaitTimeToNormal();

		return isModalVisible;
	}

	
	/**
	 * Utility method to wait for a Modal to Disappear to upload. This looks at the spinner that
	 * displays while a file is being uploaded.
	 */
	public void waitForModalToDisappear() {

		int loopCount = 0;

		long start = System.currentTimeMillis();
		// Loop for 5 min
		while (isModalVisible() && loopCount <= 300) {
			AutomationHelper.waitMillis(500);
			loopCount++;
		}
		long finish = System.currentTimeMillis();
		long totalTime = finish - start;
		System.out.println("Modal took " + Duration.ofMillis(totalTime) + " to disappear.");
	}


	/**
	 * Clicks the OK button on a modal.
	 */
	public void clickOKOnModal() {
		AutomationHelper.printMethodName();
		WebElement okButton = MODAL.findElement(By.xpath(".//button[text() = 'Ok']"));
		okButton.click();
		waitForFileToLoad();
		waitForImageToLoad();
		waitForModalToDisappear();
		waitForPageToLoad();
		
	}

	/**
	 * Clicks the Cancel button on a modal.
	 */
	public void clickCancelOnModal() {
		AutomationHelper.printMethodName();
		WebElement cancelButton = MODAL.findElement(By.xpath(".//button[text() = 'Cancel']"));

		cancelButton.click();
		waitForModalToDisappear();
		waitForPageToLoad();
	}
	
	/**
	 * Clicks the Save button on a modal.
	 */
	public void clickSaveOnModal() {
		AutomationHelper.printMethodName();
		WebElement saveButton = MODAL.findElement(By.xpath(".//button[text() = 'Save']"));

		saveButton.click();
	}
		
	/**
	 * Method to read the title from a modal.
	 * 
	 * @return String
	 */
	public String readModalTitle() {
		AutomationHelper.printMethodName();
		WebElement modalTitle = MODAL.findElement(By.xpath(".//h3[@class='modal-title']"));
		return modalTitle.getText().trim();
	}
	
	/**
	 * Returns the modal body text (the paragraph) within a given modal.
	 * 
	 * @return String
	 */
	public String readModalBodyText() {
		AutomationHelper.printMethodName();
		WebElement modalTitle = MODAL.findElement(By.xpath(".//div[@class='modal-body']"));
//		return modalTitle.getText().trim();	
		return AutomationHelper.getText(modalTitle).trim();	

	}
	
	/**
	 * Clicks the Close "X" in the top right hand corner of a given modal.
	 */
	public void clickXOnModal() {
		AutomationHelper.printMethodName();
		WebElement xButton = MODAL.findElement(By.xpath(".//span[@class = 'close pointer'][@title='Close modal']"));
		xButton.click();
	}
	
	/**
	 * Method to click the Close button on a modal.
	 */
	public void clickClose() {
		AutomationHelper.printMethodName();
		WebElement closeButton = MODAL.findElement(By.xpath(".//span[@class = 'close pointer'][@title='Close modal']"));
		closeButton.click();
	}
	
	/**
	 * Method to click the Print button on a modal.
	 */
	public void clickPrint() {
		AutomationHelper.printMethodName();
		WebElement printButton = MODAL.findElement(By.xpath(".//button[text() = 'Print']"));
		printButton.click();
	}
	
	/**
	 * Method to click the Delete button on a modal.
	 */
	public void clickDelete() {
		AutomationHelper.printMethodName();
		WebElement printButton = MODAL.findElement(By.xpath(".//button[text() = 'Delete']"));
		printButton.click();
		waitForModalToDisappear();
		
		//This line of code is here because after deleting, the table can be slow in refreshing with the deletion.
		//ALthough putting the line below works, it causes the division to change to a default division.
//		driver.navigate().refresh();
	}
	

}
