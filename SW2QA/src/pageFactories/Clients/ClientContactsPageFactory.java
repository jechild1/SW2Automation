package pageFactories.Clients;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import pageFactories.SW2Modals;
import pageFactories.SW2Popups;
import utilities.AutomationHelper;

/**
 * Page factory for the Client page for SW2.<br>
 * (1) Finds a reference to objects on the page <br>
 * (2) Defines methods to interact with objects on the page.<br>
 * 
 * @author Jesse Childress
 *
 */
public class ClientContactsPageFactory extends SW2ClientsShared {

	public static String regexURL = BASE_URL + ".*" + "clients" + ".*" + "contacts";

	/**
	 * Page Constructor - Calls constructor in MenusPageFactory.
	 */
	public ClientContactsPageFactory() {
		super(regexURL);
	}

	/**
	 * Method to click Add Contact.
	 */
	public void clickAddContact() {

		AutomationHelper.printMethodName();
		WebElement addContactButton = driver.findElement(By.xpath("//button[contains(text(),'Add Contact')]"));

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.elementToBeClickable(addContactButton));

		addContactButton.click();

	}

	/**
	 * Clicks the "Edit" button on the Clients Page (header) behind the three dot
	 * ellipsis button.
	 */
	public void clickEdit() {
		AutomationHelper.printMethodName();
		clickButtonBehindThreeDotEllipsis("Edit");
	}

	/**
	 * Clicks the "Delete" button on the Clients Page (header) behind the three dot
	 * ellipsis button.
	 */
	public void clickDelete() {
		AutomationHelper.printMethodName();
		clickButtonBehindThreeDotEllipsis("Delete");
	}

	/**
	 * Method to return a reference to the Add A Contact Modal.
	 * 
	 * @return AddAContactModal
	 */
	public AddAContactModal getAddAContactModal() {
		return new AddAContactModal();
	}

	/**
	 * Class to interact with objects on the Add A Contact Modal.
	 * 
	 * @author Jesse Childress
	 *
	 */
	public class AddAContactModal extends SW2Modals {

		public void setEmail(String emailAddress) {
			AutomationHelper.printMethodName();
		}

	}

	/**
	 * Method to return a reference to the Remove Contact popup.
	 * 
	 * @return RemoveContactPopup
	 */
	public RemoveContactPopup getRemoveContactPopup() {
		return new RemoveContactPopup();
	}

	/**
	 * Class to Contain objects to work with the "Remove Contact" popup. This modal
	 * is displayed when clicking the trashcan icon on a client contact.
	 * 
	 * @author Jesse Childress
	 *
	 */
	public class RemoveContactPopup extends SW2Popups {

		/**
		 * Clicks the Remove Contact button on the "Remove Contact" popup.
		 */
		public void clickRemoveContact() {
			AutomationHelper.printMethodName();
			POPUP.findElement(By.xpath("//button[text()='Remove contact']")).click();
		}
	}

	/**
	 * Method to return a reference to the Delete Client Popup.
	 * 
	 * @return DeleteClientPopup
	 */
	public DeleteClientPopup getDeleteClientPopup() {
		return new DeleteClientPopup();
	}

	/**
	 * Class to contain objects to work with the "Are you sure you want to delete
	 * xyz Client" popup.
	 * 
	 * @author Jesse Childress
	 *
	 */
	public class DeleteClientPopup extends SW2Popups {

		public void clickDeleteClient() {
			AutomationHelper.printMethodName();
			POPUP.findElement(By.xpath("//button[text()='Delete Client']")).click();
			
		}

	}

}
