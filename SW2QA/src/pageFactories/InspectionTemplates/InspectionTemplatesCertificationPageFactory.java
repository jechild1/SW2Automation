package pageFactories.InspectionTemplates;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import pageFactories.SW2Modals;
import utilities.AutomationHelper;

/**
 * Page factory for the Inspection Templates > Certification page for SW2.<br>
 * (1) Finds a reference to objects on the page <br>
 * (2) Defines methods to interact with objects on the page.<br>
 * 
 * @author Jesse Childress
 *
 */
public class InspectionTemplatesCertificationPageFactory extends SW2InspectionTemplatesMainPageFactory {

	public static String regexURL = BASE_URL + ".*" + "inspection-templates" + ".*" + "certification";

	/**
	 * Page Constructor - Calls constructor in
	 * SW2InspectionTemplatesMainPageFactory.
	 */
	public InspectionTemplatesCertificationPageFactory() {
		super(regexURL);
		waitForPageToLoad();
	}

	/**
	 * Clicks the Edit Signatures button. <br>
	 * Note: This makes the "pencil" icons appear in the sections: <br>
	 * <li>Inspection Certification
	 * <li>Compliance Certification
	 */
	public void clickEditSignatures() {
		AutomationHelper.printMethodName();
		clickButtonByText("Edit Signatures");
	}
	
	/**
	 * Clicks the Save button
	 */
	public void clickSave() {
		AutomationHelper.printMethodName();
		clickButtonByText("Save");
	}
	
	/**
	 * Clicks the Cancel button
	 */
	public void clickCancel() {
		AutomationHelper.printMethodName();
		clickButtonByText("Cancel");
	}

	/**
	 * Clicks the <b>Inspection Certification</b> pencil icon.
	 */
	public void clickEditInspectionCertificationPencil() {
		AutomationHelper.printMethodName();
		WebElement pencilIcon = driver.findElement(
				By.xpath("//h3[text()='Inspection Certification']/parent::section//button[@title='Edit line']"));
		pencilIcon.click();
	}

	/**
	 * Clicks the <b>Compliance Certification</b> pencil icon.
	 */
	public void clickEditComplianceCertificationPencil() {
		AutomationHelper.printMethodName();
		WebElement pencilIcon = driver.findElement(
				By.xpath("//h3[text()='Compliance Certification']/parent::section//button[@title='Edit line']"));
		pencilIcon.click();
	}

	/**
	 * Method to return a reference to the <b>Edit Signature Line</b> modal. <br>
	 * 
	 * @return AddAQuestionModal
	 */
	public EditSignatureLineModal getEditSignatureLineModal() {
		return new EditSignatureLineModal();

	}

	/**
	 * Returns the Label of the Inspection Certification section, if it exists.
	 * 
	 * @return String
	 */
	public String readInspectionCertificationLabel() {
		AutomationHelper.printMethodName();

		String xpath = "//h3[text()='Inspection Certification']/parent::section//h4";

		// This object may or may not exist

		List<WebElement> label = new ArrayList<WebElement>();

		AutomationHelper.adjustWaitTimeToVeryShort();
		label = driver.findElements(By.xpath(xpath));
		AutomationHelper.adjustWaitTimeToNormal();

		String labelText = "";

		if (label.size() > 0) {
			labelText = label.get(0).getText();
		}

		return labelText;
	}

	/**
	 * Reads the "Inspection Certification" / <b>User Name</b> text on the
	 * Certification page.
	 * 
	 * @return String
	 */
	public String readInspectionCertificationName() {
		AutomationHelper.printMethodName();

		String xpath = "//h3[text()='Inspection Certification']/parent::section//input[contains(@name,'userName')]//preceding-sibling::label//span";

		WebElement label = driver.findElement(By.xpath(xpath));
		return AutomationHelper.getText(label);
	}

	/**
	 * Reads the "Inspection Certification" / <b>User Role</b> text on the
	 * Certification page.
	 * 
	 * @return String
	 */
	public String readInspectionCertificationRole() {
		AutomationHelper.printMethodName();

		String xpath = "//h3[text()='Inspection Certification']/parent::section//input[contains(@name,'userRole')]//preceding-sibling::label//span";

		WebElement label = driver.findElement(By.xpath(xpath));
		return AutomationHelper.getText(label);
	}
	
	/**
	 * Returns the Label of the Compliance Certification section, if it exists.
	 * 
	 * @return String
	 */
	public String readComplianceCertificationLabel() {
		AutomationHelper.printMethodName();

		String xpath = "//h3[text()='Compliance Certification']/parent::section//h4";

		// This object may or may not exist

		List<WebElement> label = new ArrayList<WebElement>();

		AutomationHelper.adjustWaitTimeToVeryShort();
		label = driver.findElements(By.xpath(xpath));
		AutomationHelper.adjustWaitTimeToNormal();

		String labelText = "";

		if (label.size() > 0) {
			labelText = label.get(0).getText();
		}

		return labelText;
	}

	/**
	 * Reads the "Compliance Certification" / <b>User Role</b> text on the
	 * Certification page.
	 * 
	 * @return String
	 */
	public String readComplianceCertificationRole() {
		AutomationHelper.printMethodName();

		String xpath = "//h3[text()='Compliance Certification']/parent::section//input[contains(@name,'userRole')]//preceding-sibling::label//span";

		WebElement label = driver.findElement(By.xpath(xpath));
		return AutomationHelper.getText(label);
	}

	/**
	 * Reads the "Compliance Certification" / <b>User Name</b> text on the
	 * Certification page.
	 * 
	 * @return String
	 */
	public String readComplianceCertificationName() {
		AutomationHelper.printMethodName();

		String xpath = "//h3[text()='Compliance Certification']/parent::section//input[contains(@name,'userName')]//preceding-sibling::label//span";

		WebElement label = driver.findElement(By.xpath(xpath));
		return AutomationHelper.getText(label);
	}

	/**
	 * Class to contain objects to work with the <b>Edit Signature Line</b> modal.
	 * 
	 * @author Jesse Childress
	 *
	 */
	public class EditSignatureLineModal extends SW2Modals {

		public EditSignatureLineModal() {
		}

		@FindBy(id = "formGroup-label")
		WebElement labelInput;

		/**
		 * Returns the value in the <b>Label</b> field.
		 * 
		 * @return String
		 */
		public String readLabel() {
			AutomationHelper.printMethodName();
			return AutomationHelper.getText(labelInput);
		}

		/**
		 * Set the <b>Label</b> field with the passed in text.
		 * 
		 * @param label
		 */
		public void setLabel(String label) {
			AutomationHelper.printMethodName();
			AutomationHelper.setTextField(labelInput, label);
		}

		@FindBy(id = "formGroup-roleFieldLabel")
		WebElement roleFieldLabelInput;

		/**
		 * Returns the value in the <b>Role Field Label</b> field.
		 * 
		 * @return String
		 */
		public String readRoleFieldLabel() {
			AutomationHelper.printMethodName();
			return AutomationHelper.getText(roleFieldLabelInput);
		}

		/**
		 * Set the <b>Role Field Label</b> field with the passed in text.
		 * 
		 * @param roleFieldLabel
		 */
		public void setRoleFieldLabel(String roleFieldLabel) {
			AutomationHelper.printMethodName();
			AutomationHelper.setTextField(roleFieldLabelInput, roleFieldLabel);
		}

		@FindBy(id = "formGroup-userFieldLabel")
		WebElement userFieldLabelInput;

		/**
		 * Returns the value in the <b>User Field Label</b> field.
		 * 
		 * @return String
		 */
		public String readUserFieldLabel() {
			AutomationHelper.printMethodName();
			return AutomationHelper.getText(userFieldLabelInput);
		}

		/**
		 * Set the <b>User Field Label</b> field with the passed in text.
		 * 
		 * @param userFieldLabel
		 */
		public void setUserFieldLabel(String userFieldLabel) {
			AutomationHelper.printMethodName();
			AutomationHelper.setTextField(userFieldLabelInput, userFieldLabel);
		}

	}

}
