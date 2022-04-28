package pageFactories;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import utilities.AutomationHelper;

public class SW2Popups extends SW2Base {
	public static WebElement POPUP;

	public SW2Popups() {
		
		PageFactory.initElements(driver, this);

		// Instantiating this here prevents stale reference when multiple popup calls
		// are made during the same test.
		POPUP = driver.findElement(By.xpath("//section[@class='modal-main modal-small']"));
	}

	/**
	 * Clicks the Delete Document button on a popup.
	 */
	public void clickDeleteDocumentOnPopup() {
		AutomationHelper.printMethodName();
		WebElement deleteDocumentButton = POPUP.findElement(By.xpath("//button[text() = 'Delete Document']"));
		deleteDocumentButton.click();

		// This gives tables time to load. It is still questionable if it works well or
		// not.
		AutomationHelper.waitSeconds(3);
		waitForPageToLoad();
	}

	/**
	 * Clicks the Cancel button on a popup.
	 */
	public void clickCancelOnPopup() {
		AutomationHelper.printMethodName();
		WebElement deleteDocumentButton = POPUP.findElement(By.xpath(".//button[text() = 'Cancel']"));
		deleteDocumentButton.click();
	}

	/**
	 * Clicks the Delete button on a popup.
	 */
	public void clickDeleteOnPopup() {
		AutomationHelper.printMethodName();
		WebElement deleteDocumentButton = POPUP.findElement(By.xpath(".//button[text() = 'Delete']"));
		deleteDocumentButton.click();
	}
	
	/**
	 * Clicks the Ok button on a popup.
	 */
	public void clickOKOnPopup() {
		AutomationHelper.printMethodName();
		WebElement deleteDocumentButton = POPUP.findElement(By.xpath(".//button[text() = 'Ok']"));
		deleteDocumentButton.click();
	}

	/**
	 * Method to read the header in a given popup modal.
	 * 
	 * @return String
	 */
	public String readPopupHeader() {
		AutomationHelper.printMethodName();
		WebElement popupHeader = POPUP.findElement(By.xpath(".//h3[@class='modal-title']"));
		return popupHeader.getText();
	}

	/**
	 * Clicks the Close "X" in the top right hand corner of a given popup.
	 */
	public void clickXOnPopup() {
		AutomationHelper.printMethodName();
		WebElement xButton = POPUP.findElement(By.xpath(".//span[@class = 'close pointer'][@title='Close modal']"));
		xButton.click();
	}

	/**
	 * Method to read the popup body text in a given popup modal.
	 * 
	 * @return String
	 */
	public String readPopupBodyText() {
		AutomationHelper.printMethodName();
		WebElement popupBodyText = POPUP.findElement(By.xpath(".//div[@class='modal-body']/p"));
		return popupBodyText.getText();
	}
	
	/**
	 * Method to read the title from a popup.
	 * 
	 * @return String
	 */
	public String readPopupTitle() {
		AutomationHelper.printMethodName();
		WebElement modalTitle = POPUP.findElement(By.xpath(".//h3[@class='modal-title']"));
		return modalTitle.getText().trim();
	}

}
