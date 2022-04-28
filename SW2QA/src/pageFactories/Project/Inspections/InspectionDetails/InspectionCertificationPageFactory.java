package pageFactories.Project.Inspections.InspectionDetails;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.WebElement;

import pageFactories.SW2Modals;
import utilities.AutomationHelper;

/**
 * This class handles creating Inspection Certifications. There are nested
 * classes that are specific to each Inspection Template. It is the inspection
 * template that will outline the objects on the Inspection Questions page.
 * 
 * @author Jesse Childress
 *
 */
public class InspectionCertificationPageFactory extends InspectionDetailsMain {

	public static String regexURL = BASE_URL + ".*" + "inspection" + ".*" + "certification";

	/**
	 * Page Constructor - Calls constructor in MenusPageFactory.
	 */
	public InspectionCertificationPageFactory() {
		super(regexURL);
		AutomationHelper.waitForPageToLoad(90);
	}

	String INSPECTION_CERTIFICATION = "//h3[text()='Inspection Certification']//ancestor::section[1]";
	String COMPLIANCE_CERTIFICATION = "//h3[text()='Compliance Certification']//ancestor::section[1]";

	WebElement inspectionCertDateTextbox = driver.findElement(By.xpath(INSPECTION_CERTIFICATION
			+ "//span[text()='Date']//ancestor::div[@class='form-group form-group--date']//input"));

	/**
	 * Reads the value of the <b>Inspection Certification</b> - Date field.
	 * 
	 * @category Inspection Certification
	 * @return String
	 */
	public String readInspectionCertificationDate() {
		AutomationHelper.printMethodName();
		return AutomationHelper.getText(inspectionCertDateTextbox);
	}

	/**
	 * Sets the Inspection Certification date with the passed in date. <br>
	 * Note: Pass in date in MM-dd-yyyy format to prevent exception.
	 * 
	 * @category Inspection Certification
	 * @param date
	 */
	public void setInspectionCertificationDate(String date) {
		AutomationHelper.printMethodName();

		date = AutomationHelper.generateCalendarDate(date);
		AutomationHelper.setCalendarDate(inspectionCertDateTextbox, date);
	}

	WebElement complianceCertDateTextbox = driver.findElement(By.xpath(COMPLIANCE_CERTIFICATION
			+ "//span[text()='Date']//ancestor::div[@class='form-group form-group--date']//input"));

	/**
	 * Reads the value of the <b>Compliance Certification</b> - Date field.
	 * 
	 * @category Compliance Certification
	 * @return String
	 */
	public String readComplianceCertificationDate() {
		AutomationHelper.printMethodName();
		return AutomationHelper.getText(complianceCertDateTextbox);
	}

	/**
	 * Sets the Compliance Certification date with the passed in date. <br>
	 * Note: Pass in date in MM-dd-yyyy format to prevent exception.
	 * 
	 * @category Compliance Certification
	 * @param date
	 */
	public void setComplianceCertificationDate(String date) {
		AutomationHelper.printMethodName();

		date = AutomationHelper.generateCalendarDate(date);
		AutomationHelper.setCalendarDate(complianceCertDateTextbox, date);
	}

	// This will find the span regardless if it is checked or not.
	WebElement inspectionCertAuthSignatureCheckbox = driver
			.findElement(By.xpath(INSPECTION_CERTIFICATION + "//span[contains(@class,'pseudo-checkbox')]"));

	/**
	 * Method to read the checked value of the <b>Inspection Certification</b>
	 * Electronic Authorization Signature checkbox.
	 * 
	 * @category Inspection Certification
	 * @return boolean - true if checked; false if not;
	 */
	public boolean readInspectionCertAuthSignatureCheckbox() {
		AutomationHelper.printMethodName();

		boolean checked;
		if (inspectionCertAuthSignatureCheckbox.getAttribute("class").equals("pseudo-checkbox")) {
			checked = false;
		} else if (inspectionCertAuthSignatureCheckbox.getAttribute("class").equals("pseudo-checkbox disabled")) {
			checked = true;
		} else {
			throw new ElementNotInteractableException("The expected class name is not on the checkbox");
		}

		return checked;
	}

	/**
	 * Sets the <b>Inspection Certification</b> checkbox with the desired status.
	 * 
	 * @category Inspection Certification
	 * 
	 * @param desiredStatus
	 */
	public void setInspectionCertAuthSignatureCheckbox(boolean desiredStatus) {
		AutomationHelper.printMethodName();

		// This is not a normal checkbox. The checkbox is a hidden object and a span
		// exists that has a class of "pseudo-checkbox" when it is unchecked or
		// "pseudo-checkbox disabled" when it is checked.

		// Find the element using a generic xpath that CONTAINS pseudo-checkbox. This
		// will return it either way

		// Get the class of the object and check it for the checked (disabled) status.
		boolean checked;
		if (inspectionCertAuthSignatureCheckbox.getAttribute("class").equals("pseudo-checkbox")) {
			checked = false;
		} else if (inspectionCertAuthSignatureCheckbox.getAttribute("class").equals("pseudo-checkbox disabled")) {
			checked = true;
		} else {
			throw new ElementNotInteractableException("The expected class name is not on the checkbox");
		}

		// If it is unchecked and we desire to check it, then check it
		// if it is checked and we desire to uncheck it, we must throw an exception
		// because this is not permissible in the application
		if ((desiredStatus == true) && (checked == false)) {
			inspectionCertAuthSignatureCheckbox.click();
			waitForPageToLoad();
		} else if ((desiredStatus == false) && (checked == true)) {
			throw new ElementNotInteractableException("Once a signature has been placed, it cannot be removed.");
		}

	}

	WebElement complianceCertAuthSignatureCheckbox = driver
			.findElement(By.xpath(COMPLIANCE_CERTIFICATION + "//span[contains(@class,'pseudo-checkbox')]"));

	/**
	 * Method to read the checked value of the <b>Compliance Certification</b>
	 * Electronic Authorization Signature checkbox.
	 * 
	 * @category Compliance Certification
	 * @return boolean - true if checked; false if not;
	 */
	public boolean readComplianceCertAuthSignatureCheckbox() {
		AutomationHelper.printMethodName();

		boolean checked;
		if (complianceCertAuthSignatureCheckbox.getAttribute("class").equals("pseudo-checkbox")) {
			checked = false;
		} else if (complianceCertAuthSignatureCheckbox.getAttribute("class").equals("pseudo-checkbox disabled")) {
			checked = true;
		} else {
			throw new ElementNotInteractableException("The expected class name is not on the checkbox");
		}

		return checked;
	}

	/**
	 * Sets the <b>Compliance Certification</b> checkbox with the desired status.
	 * 
	 * @category Compliance Certification
	 * @param desiredStatus
	 */
	public void setComplianceCertAuthSignatureCheckbox(boolean desiredStatus) {
		AutomationHelper.printMethodName();

		// This is not a normal checkbox. The checkbox is a hidden object and a span
		// exists that has a class of "pseudo-checkbox" when it is unchecked or
		// "pseudo-checkbox disabled" when it is checked.

		// Find the element using a generic xpath that CONTAINS pseudo-checkbox. This
		// will return it either way

		// Get the class of the object and check it for the checked (disabled) status.
		boolean checked;
		if (complianceCertAuthSignatureCheckbox.getAttribute("class").equals("pseudo-checkbox")) {
			checked = false;
		} else if (complianceCertAuthSignatureCheckbox.getAttribute("class").equals("pseudo-checkbox disabled")) {
			checked = true;
		} else {
			throw new ElementNotInteractableException("The expected class name is not on the checkbox");
		}

		// If it is unchecked and we desire to check it, then check it
		// if it is checked and we desire to uncheck it, we must throw an exception
		// because this is not permissible in the application
		if ((desiredStatus == true) && (checked == false)) {
			complianceCertAuthSignatureCheckbox.click();
			waitForPageToLoad();
		} else if ((desiredStatus == false) && (checked == true)) {
			throw new ElementNotInteractableException("Once a signature has been placed, it cannot be removed.");
		}

	}

	/**
	 * Method to return a nested class of objects that work with the <b>SQA
	 * Inspection Template Alpha</b> Inspection Certification page.
	 * 
	 * @return SQA Inspection Template Alpha
	 */
	public SQAInspectionTemplateAlpha getSQAInspectionTemplateAlpha() {
		return new SQAInspectionTemplateAlpha();
	}

	/**
	 * Method to return a nested class of objects that work with the <b>SQA Test
	 * Template For Certification</b> Inspection Certification page.
	 * 
	 * @return SQATestTemplateForCertification class
	 */
	public SQATestTemplateForCertification getSQATestTemplateForCertification() {
		return new SQATestTemplateForCertification();
	}

	/**
	 * Contains methods to interact with objects on the SQA Inspection Template
	 * Alpha certification page. <br>
	 * Note: All Inspection Templates have their own Certification templates.
	 * 
	 * @author Jesse Childress
	 *
	 */
	public class SQAInspectionTemplateAlpha extends InspectionCertificationPageFactory {

		/**
		 * Reads the value of the <b>Inspection Certification</b> - Inspector Name
		 * field.
		 * 
		 * @category Inspection Certification
		 * @return String
		 */
		public String readInspectionCertificationName() {
			AutomationHelper.printMethodName();
			WebElement inspectorName = driver.findElement(By.xpath(INSPECTION_CERTIFICATION
					+ "//span[text()='Inspector Name']//ancestor::div[@class='form-group form-group--text']//input"));

			return AutomationHelper.getText(inspectorName);
		}

		/**
		 * Reads the value of the <b>Inspection Certification</b> - Inspector Position
		 * field.
		 * 
		 * @category Inspection Certification
		 * @return String
		 */
		public String readInspectionCertificationPosition() {
			AutomationHelper.printMethodName();
			WebElement inspectorPosition = driver.findElement(By.xpath(INSPECTION_CERTIFICATION
					+ "//span[text()='Position']//ancestor::div[@class='form-group form-group--text']//input"));

			return AutomationHelper.getText(inspectorPosition).trim();
		}

		WebElement inspectionCertDateTextbox = driver.findElement(By.xpath(INSPECTION_CERTIFICATION
				+ "//span[text()='Date']//ancestor::div[@class='form-group form-group--date']//input"));

		/**
		 * Reads the value of the <b>Inspection Certification</b> - Date field.
		 * 
		 * @category Inspection Certification
		 * @return String
		 */
		public String readInspectionCertificationDate() {
			AutomationHelper.printMethodName();
			return AutomationHelper.getText(inspectionCertDateTextbox);
		}

		/**
		 * Sets the Inspection Certification date with the passed in date. <br>
		 * Note: Pass in date in MM-dd-yyyy format to prevent exception.
		 * 
		 * @category Inspection Certification
		 * @param date
		 */
		public void setInspectionCertificationDate(String date) {
			AutomationHelper.printMethodName();

			date = AutomationHelper.generateCalendarDate(date);
			AutomationHelper.setCalendarDate(inspectionCertDateTextbox, date);
		}

		/**
		 * Reads the value of the <b>Compliance Certification</b> - Name field.
		 * 
		 * @category Compliance Certification
		 * @return String
		 */
		public String readComplianceCertificationName() {
			AutomationHelper.printMethodName();
			WebElement inspectorName = driver.findElement(By.xpath(COMPLIANCE_CERTIFICATION
					+ "//span[text()='Name']//ancestor::div[@class='form-group form-group--text']//input"));

			return AutomationHelper.getText(inspectorName);
		}

		/**
		 * Reads the value of the <b>Compliance Certification</b> - Position field.
		 * 
		 * @category Compliance Certification
		 * @return String
		 */
		public String readComplianceCertificationPosition() {
			AutomationHelper.printMethodName();
			WebElement positionName = driver.findElement(By.xpath(COMPLIANCE_CERTIFICATION
					+ "//span[text()='Position']//ancestor::div[@class='form-group form-group--text']//input"));

			return AutomationHelper.getText(positionName);
		}

		WebElement complianceCertDateTextbox = driver.findElement(By.xpath(COMPLIANCE_CERTIFICATION
				+ "//span[text()='Date']//ancestor::div[@class='form-group form-group--date']//input"));

		/**
		 * Reads the value of the <b>Compliance Certification</b> - Date field.
		 * 
		 * @category Compliance Certification
		 * @return String
		 */
		public String readComplianceCertificationDate() {
			AutomationHelper.printMethodName();
			return AutomationHelper.getText(complianceCertDateTextbox);
		}

		/**
		 * Sets the Compliance Certification date with the passed in date. <br>
		 * Note: Pass in date in MM-dd-yyyy format to prevent exception.
		 * 
		 * @category Compliance Certification
		 * @param date
		 */
		public void setComplianceCertificationDate(String date) {
			AutomationHelper.printMethodName();

			date = AutomationHelper.generateCalendarDate(date);
			AutomationHelper.setCalendarDate(complianceCertDateTextbox, date);
		}

		WebElement addNoteTextArea = driver.findElement(By.id("formGroup-newNote"));

		// TODO - Come back to this and add the ability to read the notes.
		/**
		 * Method to read the Add Note field text.
		 * 
		 * @return String
		 */
		public String readAddNote() {
			AutomationHelper.printMethodName();

			return AutomationHelper.getText(addNoteTextArea);
		}

	}

	/**
	 * Method to return a nested class of objects that work with the <b>SQA
	 * Inspection Template Alpha</b> Inspection Certification page.
	 * 
	 * @return SQA Inspection Template Alpha
	 */
	public ColoradoTemplate getColoradoTemplate() {
		return new ColoradoTemplate();
	}

	/**
	 * Contains methods to interact with objects on the <b>Colorado
	 * Template</b>certification page. <br>
	 * Note: All Inspection Templates have their own Certification templates.
	 * 
	 * @author Jesse Childress
	 *
	 */
	public class ColoradoTemplate extends InspectionCertificationPageFactory {

		/**
		 * Reads the value of the <b>Inspection Certification</b> - Inspector Name
		 * field.
		 * 
		 * @category Inspection Certification
		 * @return String
		 */
		public String readInspectionCertificationName() {
			AutomationHelper.printMethodName();
			WebElement inspectorName = driver.findElement(By.xpath(INSPECTION_CERTIFICATION
					+ "//span[text()='Inspector Name']//ancestor::div[@class='form-group form-group--text']//input"));

			return AutomationHelper.getText(inspectorName);
		}

		/**
		 * Reads the value of the <b>Inspection Certification</b> - Inspector
		 * Qualifications field.
		 * 
		 * @category Inspection Certification
		 * @return String
		 */
		public String readInspectionCertificationPosition() {
			AutomationHelper.printMethodName();
			WebElement inspectorqualifications = driver.findElement(By.xpath(INSPECTION_CERTIFICATION
					+ "//span[text()='Inspector Qualifications']//ancestor::div[@class='form-group form-group--text']//input"));

			return AutomationHelper.getText(inspectorqualifications);
		}

		/**
		 * Reads the value of the <b>Compliance Certification</b> - Name field.
		 * 
		 * @category Compliance Certification
		 * @return String
		 */
		public String readComplianceCertificationName() {
			AutomationHelper.printMethodName();
			WebElement inspectorName = driver.findElement(By.xpath(COMPLIANCE_CERTIFICATION
					+ "//span[text()='Name']//ancestor::div[@class='form-group form-group--text']//input"));

			return AutomationHelper.getText(inspectorName);
		}

		/**
		 * Reads the value of the <b>Compliance Certification</b> - Position field.
		 * 
		 * @category Compliance Certification
		 * @return String
		 */
		public String readComplianceCertificationPosition() {
			AutomationHelper.printMethodName();
			WebElement positionName = driver.findElement(By.xpath(COMPLIANCE_CERTIFICATION
					+ "//span[text()='Position']//ancestor::div[@class='form-group form-group--text']//input"));

			return AutomationHelper.getText(positionName);
		}
	}

	/**
	 * Clicks the Custom Message button
	 */
	public void clickCustomMessageButton() {

		AutomationHelper.printMethodName();
		WebElement customMessageButton = driver.findElement(By.xpath("//button//span[text()='Custom Message']"));
		customMessageButton.click();
	}

	/**
	 * Method to return a reference to the <b>Custom Message</b> popup modal.
	 * 
	 * @return CustomMessagePopup
	 */
	public CustomMessagePopup getCustomMessagePopup() {
		return new CustomMessagePopup();
	}

	/**
	 * Class to interact with objects on the <b>Custom Message</b> popup modal.
	 * 
	 * @author Jesse Childress
	 *
	 */
	public class CustomMessagePopup extends SW2Modals {

		WebElement messageTextArea = driver
				.findElement(By.xpath("//label[text()='Message']//following-sibling::textarea"));

		/**
		 * Reads the Message text area of the <b>Custom Message</b> text area.
		 * 
		 * @return String
		 */
		public String readMessageTextArea() {
			AutomationHelper.printMethodName();
			return AutomationHelper.getText(messageTextArea);
		}

		/**
		 * Sets the Message text area with the passed in text.
		 * 
		 * @param messageText
		 */
		public void setMessageTextArea(String messageText) {
			AutomationHelper.printMethodName();
			AutomationHelper.setTextField(messageTextArea, messageText);
		}

	}

	public class SQATestTemplateForCertification extends InspectionCertificationPageFactory {

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
		public String readInspectionCertificationNameLabel() {
			AutomationHelper.printMethodName();

			String xpath = "//h3[text()='Inspection Certification']/parent::section//input[contains(@name,'userName')]//preceding-sibling::label//span";

			WebElement label = driver.findElement(By.xpath(xpath));
			return AutomationHelper.getText(label);
		}

		/**
		 * Reads the value of the <b>Inspection Certification - Name</b> text input.
		 * 
		 * @return String
		 */
		public String readInspectionCertificationName() {
			AutomationHelper.printMethodName();
			String xpath = "//h3[text()='Inspection Certification']/parent::section//input[contains(@name,'userName')]";
			WebElement inspectionCertName = driver.findElement(By.xpath(xpath));
			return AutomationHelper.getText(inspectionCertName);
		}

		/**
		 * Reads the "Inspection Certification" / <b>User Role</b> text on the
		 * Certification page.
		 * 
		 * @return String
		 */
		public String readInspectionCertificationRoleLabel() {
			AutomationHelper.printMethodName();

			String xpath = "//h3[text()='Inspection Certification']/parent::section//input[contains(@name,'userRole')]//preceding-sibling::label//span";

			WebElement label = driver.findElement(By.xpath(xpath));
			return AutomationHelper.getText(label);
		}

		/**
		 * Reads the value of the <b>Inspection Certification - Role</b> text input.
		 * 
		 * @return String
		 */
		public String readInspectionCertificationRole() {
			AutomationHelper.printMethodName();
			String xpath = "//h3[text()='Inspection Certification']/parent::section//input[contains(@name,'userRole')]";
			WebElement inspectionCertName = driver.findElement(By.xpath(xpath));
			return AutomationHelper.getText(inspectionCertName);
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
		 * Reads the "Compliance Certification" / <b>User Name</b> text on the
		 * Certification page.
		 * 
		 * @return String
		 */
		public String readComplianceCertificationNameLabel() {
			AutomationHelper.printMethodName();

			String xpath = "//h3[text()='Compliance Certification']/parent::section//input[contains(@name,'userName')]//preceding-sibling::label//span";

			WebElement label = driver.findElement(By.xpath(xpath));
			return AutomationHelper.getText(label);
		}

		/**
		 * Reads the value of the <b>Compliance Certification - Name</b> text input.
		 * 
		 * @return String
		 */
		public String readComplianceCertificationName() {
			AutomationHelper.printMethodName();
			String xpath = "//h3[text()='Compliance Certification']/parent::section//input[contains(@name,'userName')]";
			WebElement complianceCertName = driver.findElement(By.xpath(xpath));
			return AutomationHelper.getText(complianceCertName);
		}

		/**
		 * Reads the "Compliance Certification" / <b>User Role</b> text on the
		 * Certification page.
		 * 
		 * @return String
		 */
		public String readComplianceCertificationRoleLabel() {
			AutomationHelper.printMethodName();

			String xpath = "//h3[text()='Compliance Certification']/parent::section//input[contains(@name,'userRole')]//preceding-sibling::label//span";

			WebElement label = driver.findElement(By.xpath(xpath));
			return AutomationHelper.getText(label);
		}

		/**
		 * Reads the value of the <b>Compliance Certification - Role</b> text input.
		 * 
		 * @return String
		 */
		public String readComplianceCertificationRole() {
			AutomationHelper.printMethodName();
			String xpath = "//h3[text()='Compliance Certification']/parent::section//input[contains(@name,'userRole')]";
			WebElement complianceCertRole = driver.findElement(By.xpath(xpath));
			return AutomationHelper.getText(complianceCertRole);
		}
	}
}
