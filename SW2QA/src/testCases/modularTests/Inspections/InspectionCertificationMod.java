package testCases.modularTests.Inspections;

import org.openqa.selenium.ElementNotInteractableException;
import org.testng.Assert;

import pageFactories.Project.Inspections.InspectionDetails.InspectionCertificationPageFactory;
import testCases.BaseTestScriptConfig;
import testCases.modularTests.LogoutAndLoginAsDifferentUserMod;
import utilities.AutomationHelper;
import utilities.ExcelDataConfig;

/**
 * MODULAR SCRIPT:<br>
 * This class performs the process of populating the Certification tab of an
 * inspection. The methods inside this class will execute the process for a
 * given Inspection Form type. given Inspection Type. This class assumes that we
 * are on the Certification tab.
 * 
 * @author Jesse Childress
 *
 */
public class InspectionCertificationMod extends BaseTestScriptConfig {

	String complianceSignatureRole;

	/**
	 * The available roles are:<br>
	 * <li>Admin
	 * <li>Area / Assistant Manager
	 * <li>BMP Contractor
	 * <li>CDOT PE/Designee
	 * <li>Client Inspector
	 * <li>Client User
	 * <li>Divisions Admin
	 * <li>Engineering Admin
	 * <li>Inspector
	 * <li>Regional Manager (External)
	 * <li>Regional Manager
	 * <li>Regulator
	 * <li>Supervising Engineer
	 * <li>SWMP Admin </br>
	 * 
	 * @param complianceSignautreRole
	 */
	public InspectionCertificationMod(String complianceSignautreRole) {
		this.complianceSignatureRole = complianceSignautreRole;

	}

	/**
	 * Performs a certification on the passed in inspectionTemplateName, e.g. SQA
	 * Inspection Template Alpha. This handles signing out and back in to complete
	 * the process. It will stay logged in as the second user.
	 * 
	 * @param inspectionTemplateName
	 */
	public void performCertOnTemplate(String inspectionTemplateName) {

		switch (inspectionTemplateName) {
		case "SQA Inspection Template Alpha":
			performCertForSQAInspectionTemplateAlpha();
			break;

		case "Colorado":
			performCertColoradoTemplate();
			break;

		case "SQA Test Template for Certification":
			performCertSQATestTemplateForCertification();
			break;

		default:
			throw new ElementNotInteractableException(
					"There are no cert templates with the passed in name of " + inspectionTemplateName);

		}

	}

	/**
	 * Performs a certification on the <b>Colorado</b> inspection template. This
	 * handles signing out and back in to complete the process. It will stay logged
	 * in as the second user.
	 */
	private void performCertColoradoTemplate() {

		// Get Data For Certification
		ExcelDataConfig inspectionCertFile = getExcelFile("Inspection Certification.xlsx", "Colorado");

		// We only have one record now. This is fine for the moment.
		int rowIndex = 1;

		/*
		 * Set up the data
		 */
		String inspectionCert_InspectorName = inspectionCertFile.getData(rowIndex,
				inspectionCertFile.getColumnIndex("InspectionCert_InspectorName"));

		String inspectionCert_Qualifications = inspectionCertFile.getData(rowIndex,
				inspectionCertFile.getColumnIndex("InspectionCert_Qualifications"));
		String inspectionCert_Date = AutomationHelper.generateCalendarDate(
				inspectionCertFile.getData(rowIndex, inspectionCertFile.getColumnIndex("InspectionCert_Date")));
		boolean inspectionCert_Signature = Boolean.valueOf(
				inspectionCertFile.getData(rowIndex, inspectionCertFile.getColumnIndex("InspectionCert_Signature")));
		String complianceCert_Name = inspectionCertFile.getData(rowIndex,
				inspectionCertFile.getColumnIndex("ComplianceCert_Name"));
		String complianceCert_Position = inspectionCertFile.getData(rowIndex,
				inspectionCertFile.getColumnIndex("ComplianceCert_Position"));
		String complianceCert_Date = AutomationHelper.generateCalendarDate(
				inspectionCertFile.getData(rowIndex, inspectionCertFile.getColumnIndex("ComplianceCert_Date")));
		boolean complianceCert_Signature = Boolean.valueOf(
				inspectionCertFile.getData(rowIndex, inspectionCertFile.getColumnIndex("ComplianceCert_Signature")));
		String customMessage = inspectionCertFile.getData(rowIndex,
				inspectionCertFile.getColumnIndex("Custom Message"));
		boolean saveMessage = Boolean
				.valueOf(inspectionCertFile.getData(rowIndex, inspectionCertFile.getColumnIndex("Save Note")));

		// Instantiate the page
		InspectionCertificationPageFactory inspectionCertPage = new InspectionCertificationPageFactory();

		// INSPECTION CERTIFICATION - SETS
		// Set the date and signature for the Inspection Certification role
		inspectionCertPage.getColoradoTemplate().setInspectionCertificationDate(inspectionCert_Date);

		// CUSTOM MESSAGE
		inspectionCertPage.getColoradoTemplate().clickCustomMessageButton();
		// MOdal is displayed
		inspectionCertPage.getColoradoTemplate().getCustomMessagePopup().setMessageTextArea(customMessage);
		if (saveMessage) {
			inspectionCertPage.getColoradoTemplate().getCustomMessagePopup().clickSaveOnModal();
		}

		inspectionCertPage.getColoradoTemplate().setInspectionCertAuthSignatureCheckbox(inspectionCert_Signature);

		// Log out and back in as an role which can certify.
		LogoutAndLoginAsDifferentUserMod logOutAndIn = new LogoutAndLoginAsDifferentUserMod(complianceSignatureRole,
				true);
		logOutAndIn.logOutAndLogin();

		// COMPLIANCE CERTIFICATION
		// Get a new reference to the page as we logged out and back in.
		inspectionCertPage = new InspectionCertificationPageFactory();
		inspectionCertPage.getColoradoTemplate().setComplianceCertificationDate(complianceCert_Date);
		inspectionCertPage.getColoradoTemplate().setComplianceCertAuthSignatureCheckbox(complianceCert_Signature);

		// Perform asserts

		// INSPECTION CERTIFICATION

		Assert.assertEquals(inspectionCertPage.getColoradoTemplate().readInspectionCertificationName(),
				inspectionCert_InspectorName);
		Assert.assertEquals(inspectionCertPage.getColoradoTemplate().readInspectionCertificationPosition(),
				inspectionCert_Qualifications);
		Assert.assertEquals(inspectionCertPage.getColoradoTemplate().readInspectionCertificationDate(),
				inspectionCert_Date);
		Assert.assertEquals(inspectionCertPage.getColoradoTemplate().readInspectionCertAuthSignatureCheckbox(),
				inspectionCert_Signature);

		// COMPLIANCE CERTIFICATION
		Assert.assertEquals(inspectionCertPage.getColoradoTemplate().readComplianceCertificationName(),
				complianceCert_Name);
		Assert.assertEquals(inspectionCertPage.getColoradoTemplate().readComplianceCertificationPosition(),
				complianceCert_Position);
		Assert.assertEquals(inspectionCertPage.getColoradoTemplate().readComplianceCertificationDate(),
				complianceCert_Date);
		Assert.assertEquals(inspectionCertPage.getColoradoTemplate().readComplianceCertAuthSignatureCheckbox(),
				complianceCert_Signature);

	}

	/**
	 * Performs a certification on the <b>SQA Inspection Template Alpha</b>
	 * template. Thsi handles signing out and back in to complete the process. It
	 * will stay logged in as the second user.
	 */
	private void performCertForSQAInspectionTemplateAlpha() {

		// Get Data For Certification
		ExcelDataConfig inspectionCertFile = getExcelFile("Inspection Certification.xlsx",
				"SQA Inspection Template Alpha");

		// We only have one record now. This is fine for the moment.
		int rowIndex = 1;

		/*
		 * Set up the data
		 */
		String inspectionCert_InspectorName = inspectionCertFile.getData(rowIndex,
				inspectionCertFile.getColumnIndex("InspectionCert_InspectorName"));
		String inspectionCert_Position = inspectionCertFile.getData(rowIndex,
				inspectionCertFile.getColumnIndex("InspectionCert_Position"));
		String inspectionCert_Date = AutomationHelper.generateCalendarDate(
				inspectionCertFile.getData(rowIndex, inspectionCertFile.getColumnIndex("InspectionCert_Date")));
		boolean inspectionCert_Signature = Boolean.valueOf(
				inspectionCertFile.getData(rowIndex, inspectionCertFile.getColumnIndex("InspectionCert_Signature")));
		String complianceCert_Name = inspectionCertFile.getData(rowIndex,
				inspectionCertFile.getColumnIndex("ComplianceCert_Name"));
		String complianceCert_Position = inspectionCertFile.getData(rowIndex,
				inspectionCertFile.getColumnIndex("ComplianceCert_Position"));
		String complianceCert_Date = AutomationHelper.generateCalendarDate(
				inspectionCertFile.getData(rowIndex, inspectionCertFile.getColumnIndex("ComplianceCert_Date")));
		boolean complianceCert_Signature = Boolean.valueOf(
				inspectionCertFile.getData(rowIndex, inspectionCertFile.getColumnIndex("ComplianceCert_Signature")));
//		String additionalSiteNote = inspectionCertFile.getData(rowIndex, inspectionCertFile.getColumnIndex("ComplianceCert_InspectorName"));
//		boolean saveNote = Boolean.valueOf(inspectionCertFile.getData(rowIndex, inspectionCertFile.getColumnIndex("ComplianceCert_Save Note")));

		// Instantiate the page
		InspectionCertificationPageFactory inspectionCertPage = new InspectionCertificationPageFactory();

		// INSPECTION CERTIFICATION - SETS
		// Set the date and signature for the Inspection Certification role
		inspectionCertPage.getSQAInspectionTemplateAlpha().setInspectionCertificationDate(inspectionCert_Date);
		inspectionCertPage.getSQAInspectionTemplateAlpha()
				.setInspectionCertAuthSignatureCheckbox(inspectionCert_Signature);

		// Log out and back in as an role which can certify.
		LogoutAndLoginAsDifferentUserMod logOutAndIn = new LogoutAndLoginAsDifferentUserMod(complianceSignatureRole,
				true);
		logOutAndIn.logOutAndLogin();

		// COMPLIANCE CERTIFICATION
		// Get a new reference to the page as we logged out and back in.
		inspectionCertPage = new InspectionCertificationPageFactory();
		inspectionCertPage.getSQAInspectionTemplateAlpha().setComplianceCertificationDate(complianceCert_Date);
		inspectionCertPage.getSQAInspectionTemplateAlpha()
				.setComplianceCertAuthSignatureCheckbox(complianceCert_Signature);

		// Perform asserts

		// INSPECTION CERTIFICATION

		Assert.assertEquals(inspectionCertPage.getSQAInspectionTemplateAlpha().readInspectionCertificationName(),
				inspectionCert_InspectorName);
		Assert.assertEquals(inspectionCertPage.getSQAInspectionTemplateAlpha().readInspectionCertificationPosition(),
				inspectionCert_Position);
		Assert.assertEquals(inspectionCertPage.getSQAInspectionTemplateAlpha().readInspectionCertificationDate(),
				inspectionCert_Date);
		Assert.assertEquals(
				inspectionCertPage.getSQAInspectionTemplateAlpha().readInspectionCertAuthSignatureCheckbox(),
				inspectionCert_Signature);

		// COMPLIANCE CERTIFICATION
		Assert.assertEquals(inspectionCertPage.getSQAInspectionTemplateAlpha().readComplianceCertificationName(),
				complianceCert_Name);
		Assert.assertEquals(inspectionCertPage.getSQAInspectionTemplateAlpha().readComplianceCertificationPosition(),
				complianceCert_Position);
		Assert.assertEquals(inspectionCertPage.getSQAInspectionTemplateAlpha().readComplianceCertificationDate(),
				complianceCert_Date);
		Assert.assertEquals(
				inspectionCertPage.getSQAInspectionTemplateAlpha().readComplianceCertAuthSignatureCheckbox(),
				complianceCert_Signature);
	}

	/**
	 * Performs a certification on the <b>SQA Test Template For Certification</b>
	 * inspection template. This handles signing out and back in to complete the
	 * process. It will stay logged in as the second user.
	 */
	private void performCertSQATestTemplateForCertification() {
		// Get Data For Certification
		ExcelDataConfig certDataSheet = getExcelFile("Inspection Certification.xlsx",
				"SQA Test Template For Certifica");

		// We only have one record now. This is fine for the moment.
		int rowIndex = 1;

		// Get data from datasheet

		// Inspection Certification Data
		String inspectorLabel = certDataSheet.getData(rowIndex, certDataSheet.getColumnIndex("Inspector_Label"));
		String inspectorRoleFieldLabel = certDataSheet.getData(rowIndex,
				certDataSheet.getColumnIndex("Inspector_Role Field Label"));
		String inspectorRole = certDataSheet.getData(rowIndex, certDataSheet.getColumnIndex("Inspector_Role"));
		String inspectorUserFieldLabel = certDataSheet.getData(rowIndex,
				certDataSheet.getColumnIndex("Inspector_User Field Label"));
		String inspectorName = certDataSheet.getData(rowIndex, certDataSheet.getColumnIndex("Inspector_Name"));
		String inspectorCertDate = AutomationHelper
				.generateCalendarDate(certDataSheet.getData(rowIndex, "Inspector_Cert Date"));
		boolean inspectorCertSignature = Boolean
				.valueOf(certDataSheet.getData(rowIndex, certDataSheet.getColumnIndex("Inspector_Cert Signature")));

		// Compliance Certification Data
		String complianceLabel = certDataSheet.getData(rowIndex, certDataSheet.getColumnIndex("Compliance_Label"));
		String complianceRoleFieldLabel = certDataSheet.getData(rowIndex,
				certDataSheet.getColumnIndex("Compliance_Role Field Label"));
		String complianceRole = certDataSheet.getData(rowIndex, certDataSheet.getColumnIndex("Compliance_Role"));
		String complianceUserFieldLabel = certDataSheet.getData(rowIndex,
				certDataSheet.getColumnIndex("Compliance_User Field Label"));
		String complianceName = certDataSheet.getData(rowIndex, certDataSheet.getColumnIndex("Compliance_Name"));
		String complianceCertDate = AutomationHelper.generateCalendarDate(
				certDataSheet.getData(rowIndex, certDataSheet.getColumnIndex("Compliance_Cert Date")));
		boolean complianceCertSignature = Boolean
				.valueOf(certDataSheet.getData(rowIndex, certDataSheet.getColumnIndex("Compliance_Cert Signature")));

		// Instantiate the page
		InspectionCertificationPageFactory inspectionCertPage = new InspectionCertificationPageFactory();

		// INSPECTION CERTIFICATION - SETS
		// Set the date and signature for the Inspection Certification role
		inspectionCertPage.getSQATestTemplateForCertification().setInspectionCertificationDate(inspectorCertDate);
		inspectionCertPage.getSQATestTemplateForCertification()
				.setInspectionCertAuthSignatureCheckbox(inspectorCertSignature);

		// Log out and back in as an role which can certify.
		LogoutAndLoginAsDifferentUserMod logOutAndIn = new LogoutAndLoginAsDifferentUserMod(complianceSignatureRole,
				true);
		logOutAndIn.logOutAndLogin();

		// COMPLIANCE CERTIFICATION
		// Get a new reference to the page as we logged out and back in.
		inspectionCertPage = new InspectionCertificationPageFactory();
		inspectionCertPage.getSQATestTemplateForCertification().setComplianceCertificationDate(complianceCertDate);
		inspectionCertPage.getSQATestTemplateForCertification()
				.setComplianceCertAuthSignatureCheckbox(complianceCertSignature);

		// Perform asserts

		// INSPECTION CERTIFICATION - LABELS AND SUCH
		Assert.assertEquals(inspectionCertPage.getSQATestTemplateForCertification().readInspectionCertificationLabel(),
				inspectorLabel, "Inspection Certification - Labels - Main Label");
		Assert.assertEquals(
				inspectionCertPage.getSQATestTemplateForCertification().readInspectionCertificationNameLabel(),
				inspectorUserFieldLabel, "Inspection Certification - Labels - Inspector User / Name");
		Assert.assertEquals(
				inspectionCertPage.getSQATestTemplateForCertification().readInspectionCertificationRoleLabel(),
				inspectorRoleFieldLabel, "Inspection Certification - Labels - Role");

		// INSPECTION CERTIFICATION - INPUTS
		Assert.assertEquals(inspectionCertPage.getSQATestTemplateForCertification().readInspectionCertificationName(),
				inspectorName, "Inspection Certification - Name");
		Assert.assertEquals(inspectionCertPage.getSQATestTemplateForCertification().readInspectionCertificationRole(),
				inspectorRole, "Inspection Certification - Role");
		Assert.assertEquals(inspectionCertPage.getSQATestTemplateForCertification().readInspectionCertificationDate(),
				inspectorCertDate, "Inspection Certification - Date");
		Assert.assertEquals(
				inspectionCertPage.getSQATestTemplateForCertification().readInspectionCertAuthSignatureCheckbox(),
				inspectorCertSignature, "Inspection Certification - Signature");

		// COMPLIANCE CERTIFICATION - LABELS AND SUCH
		Assert.assertEquals(inspectionCertPage.getSQATestTemplateForCertification().readComplianceCertificationLabel(),
				complianceLabel, "Compliance Certification - Labels - Main Label");
		Assert.assertEquals(
				inspectionCertPage.getSQATestTemplateForCertification().readComplianceCertificationNameLabel(),
				complianceUserFieldLabel, "Compliance Certification - Labels - Inspector User / Name");
		Assert.assertEquals(
				inspectionCertPage.getSQATestTemplateForCertification().readComplianceCertificationRoleLabel(),
				complianceRoleFieldLabel, "Compliance Certification - Labels - Role");

		// COMPLIANCE CERTIFICATION - INPUTS
		Assert.assertEquals(inspectionCertPage.getSQATestTemplateForCertification().readComplianceCertificationName(),
				complianceName, "Compliance Certification - Name");
		Assert.assertEquals(inspectionCertPage.getSQATestTemplateForCertification().readComplianceCertificationRole(),
				complianceRole, "Compliance Certification - Role");
		Assert.assertEquals(inspectionCertPage.getSQATestTemplateForCertification().readComplianceCertificationDate(),
				complianceCertDate, "Compliance Certification - Date");
		Assert.assertEquals(
				inspectionCertPage.getSQATestTemplateForCertification().readComplianceCertAuthSignatureCheckbox(),
				complianceCertSignature, "Compliance Certification - Signature");

	}

}
