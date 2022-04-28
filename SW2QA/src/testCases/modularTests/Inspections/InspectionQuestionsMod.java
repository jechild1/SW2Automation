package testCases.modularTests.Inspections;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.Assert;

import pageFactories.Project.Inspections.InspectionQuestions.InspectionQuestionsPageFactory;
import testCases.BaseTestScriptConfig;
import utilities.AutomationHelper;
import utilities.ExcelDataConfig;

/**
 * MODULAR SCRIPT:<br>
 * This class performs the process of populating the Inspection Questions for a
 * given Inspection Type. The constructor will require an inspection questions
 * form type and an inspection type.
 * 
 * NOTE: This assumes that we are on the Inspection Questions page.
 * 
 * @author Jesse Childress
 *
 */
public class InspectionQuestionsMod extends BaseTestScriptConfig {

	String inspectionQuestionsForm;
	String inspectionType;

	/**
	 * The class constructor will accept which type of Inspection Template and the
	 * Inspection Type. This is needed in order to know which fields to execute.
	 * This class can handle the following types of templates:<br>
	 * <li>SQA Inspection Template Alpha
	 * <li>TBD
	 * 
	 * @param inspectionQuestionsForm
	 * @param inspectionType
	 */
	public InspectionQuestionsMod(String inspectionQuestionsForm, String inspectionType) {
		this.inspectionQuestionsForm = inspectionQuestionsForm;
		this.inspectionType = inspectionType;
	}

	/**
	 * Sets and validates the Inspection Questions field(s).
	 */
	public void setAndValidateInspectionQuestions() {

		switch (inspectionQuestionsForm) {
		case "SQA Inspection Template Alpha":
			setAndValidateSQATemplateAlpha(inspectionType);
			break;
		case "Colorado":
			setAndValidateColoradoTemplate(inspectionType);
			break;
		case "SQA Test Template for Certification":
			setAndValidateSQATestTemplateForCertificationQuestionGroup1(inspectionType);
			setAndValidateSQATestTemplateForCertificationQuestionGroup2(inspectionType);
			break;
		default:
			System.out
					.println("The passed in inspection questions form " + inspectionQuestionsForm + " does not exist.");
		}

	}

	/**
	 * Method to set and validate the inspection questions for SQA Inspection
	 * Template Alpha
	 * 
	 * @param inspecitionType
	 */
	private void setAndValidateSQATemplateAlpha(String inspectionType) {

		/*
		 * Inspection Questions page
		 */
		// Instantiate the page so that we can interact with objects on it.
		InspectionQuestionsPageFactory inspectionQuestionsPage = new InspectionQuestionsPageFactory();

		// Get a reference to the datasheet
		ExcelDataConfig inspectionQuestionsFile = getExcelFile("Inspection Questions.xlsx", inspectionQuestionsForm);

		// FIRST: We need to know which type of inspection we want to add. The second
		// parameter in the class constructor tells us the type. From this type, we can
		// find the row index in the datasheet.
		int rowIndex = inspectionQuestionsFile.getRowIndex("Inspection Type", inspectionType);

		// SECOND: Set the string / boolean values with data from the datasheet.
		inspectionType = inspectionQuestionsFile.getData(rowIndex,
				inspectionQuestionsFile.getColumnIndex("Inspection Type"));
		String dateOfInspection = inspectionQuestionsFile.getData(rowIndex,
				inspectionQuestionsFile.getColumnIndex("Date of Inspection"));

		dateOfInspection = AutomationHelper.generateCalendarDate(dateOfInspection);

		boolean areYouAtTheSite = Boolean.valueOf(inspectionQuestionsFile.getData(rowIndex,
				inspectionQuestionsFile.getColumnIndex("Are you at the site")));

		String dateStarted = inspectionQuestionsFile.getData(rowIndex,
				inspectionQuestionsFile.getColumnIndex("Date Started"));

		dateStarted = AutomationHelper.generateCalendarDate(dateStarted);

		String dateAndTimeFinished = inspectionQuestionsFile.getData(rowIndex,
				inspectionQuestionsFile.getColumnIndex("Date and Time Finished"));

		dateAndTimeFinished = AutomationHelper.generateCalendarDate(dateAndTimeFinished);

		String areOtherPeoplePresent = inspectionQuestionsFile.getData(rowIndex,
				inspectionQuestionsFile.getColumnIndex("Are other people present"));
		boolean areYouHavingANiceDay = Boolean.valueOf(inspectionQuestionsFile.getData(rowIndex,
				inspectionQuestionsFile.getColumnIndex("Are you having a nice day")));

		// THIRD: Set the data in the fields
		inspectionQuestionsPage.getSQAInspectionTemplateAlpha().selectInspectionType(inspectionType);
		inspectionQuestionsPage.getSQAInspectionTemplateAlpha().setDateOfInspection(dateOfInspection);
		inspectionQuestionsPage.getSQAInspectionTemplateAlpha().setAreYouAtTheSite(areYouAtTheSite);
		inspectionQuestionsPage.getSQAInspectionTemplateAlpha().setDateStarted(dateStarted);
		inspectionQuestionsPage.getSQAInspectionTemplateAlpha().setDateAndTimeFinished(dateAndTimeFinished);
		inspectionQuestionsPage.getSQAInspectionTemplateAlpha().selectAreOtherPeoplePresent(areOtherPeoplePresent);
		inspectionQuestionsPage.getSQAInspectionTemplateAlpha().setAreYouHavingANiceDay(areYouHavingANiceDay);

		// Save
		inspectionQuestionsPage.clickSave();

		// FOURTH: Perform validations after save
		Assert.assertEquals(inspectionQuestionsPage.getSQAInspectionTemplateAlpha().readInspectionType(),
				inspectionType, "Inspection Questions - Inspection Type");

		Assert.assertEquals(inspectionQuestionsPage.getSQAInspectionTemplateAlpha().readDateOfInspection(),
				dateOfInspection, "Inspection Questions - Date of Inspection");

		Assert.assertEquals(inspectionQuestionsPage.getSQAInspectionTemplateAlpha().readAreYouAtTheSite(),
				areYouAtTheSite, "Inspection Questions - Are you at the site");

		Assert.assertEquals(inspectionQuestionsPage.getSQAInspectionTemplateAlpha().readDateStarted(), dateStarted,
				"Inspection Questions - Date Started");

		// TODO - This will need to be removed. Currently, the date will be in the
		// application be in format March 31, 2022 12:00 PM. We must take date that is
		// stored in the variable "dateAndTimeFinished" and convert it from 01-13-2022
		// 12:15 PM format to the aforementioned.
		dateAndTimeFinished = tempFormatDate("MMMMM d, yyyy h:mm a", "MM-dd-yyyy h:mm a", dateAndTimeFinished);

		Assert.assertEquals(inspectionQuestionsPage.getSQAInspectionTemplateAlpha().readDateAndTimeFinished(),
				dateAndTimeFinished, "Inspection Questions - Date and Time Finished");

		Assert.assertEquals(inspectionQuestionsPage.getSQAInspectionTemplateAlpha().readAreOtherPeoplePresent(),
				areOtherPeoplePresent, "Inspection Questions - Are other people present");
		Assert.assertEquals(inspectionQuestionsPage.getSQAInspectionTemplateAlpha().readAreYouHavingANiceDay(),
				areYouHavingANiceDay, "Inspection Questions - Are you having a nice day");

	}

	/**
	 * Method to set and validate the inspection questions for Colorado template
	 * (System)
	 * 
	 * @param inspecitionType
	 */
	private void setAndValidateColoradoTemplate(String inspectionType) {

		/*
		 * Inspection Questions page
		 */
		// Instantiate the page so that we can interact with objects on it.
		InspectionQuestionsPageFactory inspectionQuestionsPage = new InspectionQuestionsPageFactory();

		// Get a reference to the datasheet
		ExcelDataConfig inspectionQuestionsFile = getExcelFile("Inspection Questions.xlsx", inspectionQuestionsForm);

		/*
		 * FIRST: We need to know which type of inspection we want to add. The second
		 * parameter in the class constructor tells us the type. From this type, we can
		 * find the row index in the datasheet.
		 * 
		 */
		int rowIndex = inspectionQuestionsFile.getRowIndex("Reason for Inspection", inspectionType);

		/*
		 * SECOND: Set the string / boolean values with data from the datasheet.
		 */

		// Inspection Summary
		String facilityName = inspectionQuestionsFile.getData(rowIndex,
				inspectionQuestionsFile.getColumnIndex("Facility Name"));

		String clientName = inspectionQuestionsFile.getData(rowIndex,
				inspectionQuestionsFile.getColumnIndex("Client Name"));

		String inspectorName = inspectionQuestionsFile.getData(rowIndex,
				inspectionQuestionsFile.getColumnIndex("Inspector Name"));

		String phaseOfConstruction = inspectionQuestionsFile.getData(rowIndex,
				inspectionQuestionsFile.getColumnIndex("Phase of Construction"));

		String municipality = inspectionQuestionsFile.getData(rowIndex,
				inspectionQuestionsFile.getColumnIndex("Municipality"));

		String dateOfInspection = AutomationHelper.generateCalendarDate(inspectionQuestionsFile.getData(rowIndex,
				inspectionQuestionsFile.getColumnIndex("Date of Inspection")));

		String reasonForInspection = inspectionQuestionsFile.getData(rowIndex,
				inspectionQuestionsFile.getColumnIndex("Reason for Inspection"));

		String existingWeatherConditions = inspectionQuestionsFile.getData(rowIndex,
				inspectionQuestionsFile.getColumnIndex("Existing Weather Conditions"));

		String estimatedAcerageOfDisturbance = inspectionQuestionsFile.getData(rowIndex,
				inspectionQuestionsFile.getColumnIndex("Estimated Acerage of Disturbance"));

		String stormEvent = inspectionQuestionsFile.getData(rowIndex,
				inspectionQuestionsFile.getColumnIndex("Storm Event"));

		String dateBegan = AutomationHelper.generateCalendarDate(
				inspectionQuestionsFile.getData(rowIndex, inspectionQuestionsFile.getColumnIndex("Date Began")));

		String duration = inspectionQuestionsFile.getData(rowIndex, inspectionQuestionsFile.getColumnIndex("Duration"));

		String amount = inspectionQuestionsFile.getData(rowIndex, inspectionQuestionsFile.getColumnIndex("Amount"));

		// SWMP Information
		String sedimentAndErosion = inspectionQuestionsFile.getData(rowIndex,
				inspectionQuestionsFile.getColumnIndex("SWMP_Sediment and Erosion"));

		String dischargePermit = inspectionQuestionsFile.getData(rowIndex,
				inspectionQuestionsFile.getColumnIndex("SWMP_Discharge Permit"));

		String drawingsUpdated = inspectionQuestionsFile.getData(rowIndex,
				inspectionQuestionsFile.getColumnIndex("SWMP_Drawings Updated"));

		String inspectionRecordExists = inspectionQuestionsFile.getData(rowIndex,
				inspectionQuestionsFile.getColumnIndex("SWMP_Inspection Record Exist"));

		String deviations = inspectionQuestionsFile.getData(rowIndex,
				inspectionQuestionsFile.getColumnIndex("SWMP_Deviations"));

		// Inspection Scope
		String areas = inspectionQuestionsFile.getData(rowIndex, inspectionQuestionsFile.getColumnIndex("SCOPE_Areas"));

		String perimeter = inspectionQuestionsFile.getData(rowIndex,
				inspectionQuestionsFile.getColumnIndex("SCOPE_Perimeter"));

		String disturbedAreas = inspectionQuestionsFile.getData(rowIndex,
				inspectionQuestionsFile.getColumnIndex("SCOPE_Disturbed Areas"));

		String sediment = inspectionQuestionsFile.getData(rowIndex,
				inspectionQuestionsFile.getColumnIndex("SCOPE_Sediment"));

		String haulRoutes = inspectionQuestionsFile.getData(rowIndex,
				inspectionQuestionsFile.getColumnIndex("SCOPE_Haul Routes"));

		String materials = inspectionQuestionsFile.getData(rowIndex,
				inspectionQuestionsFile.getColumnIndex("SCOPE_Materials"));

		String locationsDischarge = inspectionQuestionsFile.getData(rowIndex,
				inspectionQuestionsFile.getColumnIndex("SCOPE_Locations Discharge"));

		String locationsVehicles = inspectionQuestionsFile.getData(rowIndex,
				inspectionQuestionsFile.getColumnIndex("SCOPE_Locations Vehicles"));

		String permit = inspectionQuestionsFile.getData(rowIndex,
				inspectionQuestionsFile.getColumnIndex("SCOPE_Permit"));

		// Inspection Reports
		String floatingMaterials = inspectionQuestionsFile.getData(rowIndex,
				inspectionQuestionsFile.getColumnIndex("REPORTS_Floating Materials"));

		String illicitDischarge = inspectionQuestionsFile.getData(rowIndex,
				inspectionQuestionsFile.getColumnIndex("REPORTS_Illicit Discharge"));

		String needsMaintenance = inspectionQuestionsFile.getData(rowIndex,
				inspectionQuestionsFile.getColumnIndex("REPORTS_Need Maintenance"));

		String inadequate = inspectionQuestionsFile.getData(rowIndex,
				inspectionQuestionsFile.getColumnIndex("REPORTS_Inadequate"));

		String locations = inspectionQuestionsFile.getData(rowIndex,
				inspectionQuestionsFile.getColumnIndex("REPORTS_Locations"));

		/*
		 * THIRD: Perform Sets
		 */

		// Inspection Summary
		inspectionQuestionsPage.getColoradoTemplate().setFacilityName(facilityName);
		inspectionQuestionsPage.getColoradoTemplate().setClientName(clientName);
		inspectionQuestionsPage.getColoradoTemplate().setInspectorName(inspectorName);
		inspectionQuestionsPage.getColoradoTemplate().setPhaseOfConstruction(phaseOfConstruction);
		inspectionQuestionsPage.getColoradoTemplate().setMunicipality(municipality);
		inspectionQuestionsPage.getColoradoTemplate().setDateOfInspection(dateOfInspection);
		inspectionQuestionsPage.getColoradoTemplate().selectReasonForInspection(reasonForInspection);
		inspectionQuestionsPage.getColoradoTemplate().setExistingWeatherConditions(existingWeatherConditions);
		inspectionQuestionsPage.getColoradoTemplate().setEstimatedAcerageOfDisturbance(estimatedAcerageOfDisturbance);
		inspectionQuestionsPage.getColoradoTemplate().setStormEvent(stormEvent);
		inspectionQuestionsPage.getColoradoTemplate().setDateBegan(dateBegan);
		inspectionQuestionsPage.getColoradoTemplate().setDuration(duration);
		inspectionQuestionsPage.getColoradoTemplate().setAmount(amount);

		// SWMP Information
		inspectionQuestionsPage.getColoradoTemplate().getSWMPInformation()
				.clickDoesCopyOfSWMPExistRadioButton(sedimentAndErosion);

		inspectionQuestionsPage.getColoradoTemplate().getSWMPInformation()
				.clickDischargePermitAndAckRadioButton(dischargePermit);

		inspectionQuestionsPage.getColoradoTemplate().getSWMPInformation()
				.clickAreSWMPDrawingsUpdatedRadioButton(drawingsUpdated);

		inspectionQuestionsPage.getColoradoTemplate().getSWMPInformation()
				.clickDoInspectionRecordsExistAtFacilityRadioButton(inspectionRecordExists);

		inspectionQuestionsPage.getColoradoTemplate().getSWMPInformation()
				.clickHaveThereBeenDeviationsInSWMPRadioButton(deviations);

		// Inspection Scope
		inspectionQuestionsPage.getColoradoTemplate().getInspectionScope().clickAreasToBeInspectedRadioButton(areas);

		inspectionQuestionsPage.getColoradoTemplate().getInspectionScope()
				.clickConstructionSitePerimeterRadioButton(perimeter);

		inspectionQuestionsPage.getColoradoTemplate().getInspectionScope()
				.clickAllDisturbedAreasRadioButton(disturbedAreas);

		inspectionQuestionsPage.getColoradoTemplate().getInspectionScope()
				.clickAllSedimentAndErosionPracticesRadioButton(sediment);

		inspectionQuestionsPage.getColoradoTemplate().getInspectionScope()
				.clickDesignatedHaulRoutesRadioButton(haulRoutes);

		inspectionQuestionsPage.getColoradoTemplate().getInspectionScope()
				.clickMaterialsAndWasteStorageAreasRadioButton(materials);

		inspectionQuestionsPage.getColoradoTemplate().getInspectionScope()
				.clickLocationsWhereStormwaterDischargeRadioButton(locationsDischarge);

		inspectionQuestionsPage.getColoradoTemplate().getInspectionScope()
				.clickLocationsWhereVehiclesExitRadioButton(locationsVehicles);

		inspectionQuestionsPage.getColoradoTemplate().getInspectionScope()
				.clickAllInspectionsFollowedPermitRadioButton(permit);

		// Inspection Reports
		inspectionQuestionsPage.getColoradoTemplate().getInspectionReports()
				.clickEvidenceOfFloatingMaterialsRadioButton(floatingMaterials);

		inspectionQuestionsPage.getColoradoTemplate().getInspectionReports()
				.clickEvidenceOfIllicitDischargesRadioButton(illicitDischarge);

		inspectionQuestionsPage.getColoradoTemplate().getInspectionReports()
				.clickLocationsOfControlMeasuresMaintenanceRadioButton(needsMaintenance);

		inspectionQuestionsPage.getColoradoTemplate().getInspectionReports()
				.clickLocationsOfControlMeasuresInadequateRadioButton(inadequate);

		inspectionQuestionsPage.getColoradoTemplate().getInspectionReports()
				.clickLocationsAdditionalControlMeasuresNeededRadioButton(locations);

		// Save
		inspectionQuestionsPage.clickSave();

		/*
		 * FOURTH: Perform validations after save
		 */

		// Inspection Summary
		Assert.assertEquals(inspectionQuestionsPage.getColoradoTemplate().readFaciltiyName(), facilityName);

		Assert.assertEquals(inspectionQuestionsPage.getColoradoTemplate().readClientName(), clientName);

		Assert.assertEquals(inspectionQuestionsPage.getColoradoTemplate().readInspectorName(), inspectorName);

		Assert.assertEquals(inspectionQuestionsPage.getColoradoTemplate().readPhaseOfConstruction(),
				phaseOfConstruction);

		Assert.assertEquals(inspectionQuestionsPage.getColoradoTemplate().readMunicipality(), municipality);

		dateOfInspection = tempFormatDate("MM/dd/yyyy", "MM-dd-yyyy", dateOfInspection);
		Assert.assertEquals(inspectionQuestionsPage.getColoradoTemplate().readDateOfInspection(), dateOfInspection);

		Assert.assertEquals(inspectionQuestionsPage.getColoradoTemplate().readReasonForInspection(),
				reasonForInspection);

		Assert.assertEquals(inspectionQuestionsPage.getColoradoTemplate().readExistingWeatherConditions(),
				existingWeatherConditions);

		Assert.assertEquals(inspectionQuestionsPage.getColoradoTemplate().readEstimatedAcerageOfDisturbance(),
				estimatedAcerageOfDisturbance);

		Assert.assertEquals(inspectionQuestionsPage.getColoradoTemplate().readStormEvent(), stormEvent);

		dateBegan = tempFormatDate("MM/dd/yyyy", "MM-dd-yyyy", dateBegan);
		Assert.assertEquals(inspectionQuestionsPage.getColoradoTemplate().readDateBegan(), dateBegan);

		Assert.assertEquals(inspectionQuestionsPage.getColoradoTemplate().readDuration(), duration);

		Assert.assertEquals(inspectionQuestionsPage.getColoradoTemplate().readAmount(), amount);

		// Radio Buttons
		// SWMP Information
		Assert.assertEquals(inspectionQuestionsPage.getColoradoTemplate().getSWMPInformation()
				.isDoesCopyOfSWMPExistRadioButtonClicked(sedimentAndErosion), true);

		Assert.assertEquals(inspectionQuestionsPage.getColoradoTemplate().getSWMPInformation()
				.isDischargePermitAndAckRadioButtonClicked(dischargePermit), true);

		Assert.assertEquals(inspectionQuestionsPage.getColoradoTemplate().getSWMPInformation()
				.isAreSWMPDrawingsUpdatedRadioButtonClicked(drawingsUpdated), true);

		Assert.assertEquals(inspectionQuestionsPage.getColoradoTemplate().getSWMPInformation()
				.isDoInspectionRecordsExistAtFacilityRadioButtonClicked(inspectionRecordExists), true);

		Assert.assertEquals(inspectionQuestionsPage.getColoradoTemplate().getSWMPInformation()
				.isHaveThereBeenDeviationsInSWMPRadioButtonClicked(deviations), true);

		// Inspection Scope
		Assert.assertEquals(inspectionQuestionsPage.getColoradoTemplate().getInspectionScope()
				.isAreasToBeInspectedRadioButtonClicked(areas), true);

		Assert.assertEquals(inspectionQuestionsPage.getColoradoTemplate().getInspectionScope()
				.isConstructionSitePerimeterRadioButtonClicked(perimeter), true);

		Assert.assertEquals(inspectionQuestionsPage.getColoradoTemplate().getInspectionScope()
				.isAllDisturbedAreasRadioButtonClicked(disturbedAreas), true);

		Assert.assertEquals(inspectionQuestionsPage.getColoradoTemplate().getInspectionScope()
				.isAllSedimentAndErosionPracticesRadioButtonClicked(sediment), true);

		Assert.assertEquals(inspectionQuestionsPage.getColoradoTemplate().getInspectionScope()
				.isDesignatedHaulRoutesRadioButtonClicked(haulRoutes), true);

		Assert.assertEquals(inspectionQuestionsPage.getColoradoTemplate().getInspectionScope()
				.isMaterialsAndWasteStorageAreasRadioButtonClicked(materials), true);

		Assert.assertEquals(inspectionQuestionsPage.getColoradoTemplate().getInspectionScope()
				.isLocationsWhereStormwaterDischargeRadioButtonClicked(locationsDischarge), true);

		Assert.assertEquals(inspectionQuestionsPage.getColoradoTemplate().getInspectionScope()
				.isLocationsWhereVehiclesExitRadioButtonClicked(locationsVehicles), true);

		Assert.assertEquals(inspectionQuestionsPage.getColoradoTemplate().getInspectionScope()
				.isAllInspectionsFollowedPermitRadioButtonClicked(permit), true);

		// Inspection Reports
		Assert.assertEquals(inspectionQuestionsPage.getColoradoTemplate().getInspectionReports()
				.isEvidenceOfFloatingMaterialsRadioButtonClicked(floatingMaterials), true);

		Assert.assertEquals(inspectionQuestionsPage.getColoradoTemplate().getInspectionReports()
				.isEvidenceOfIllicitDischargesRadioButtonClicked(illicitDischarge), true);

		Assert.assertEquals(inspectionQuestionsPage.getColoradoTemplate().getInspectionReports()
				.isLocationsOfControlMeasuresMaintenanceRadioButtonClicked(needsMaintenance), true);

		Assert.assertEquals(inspectionQuestionsPage.getColoradoTemplate().getInspectionReports()
				.isLocationsOfControlMeasuresInadequateRadioButtonClicked(inadequate), true);

		Assert.assertEquals(inspectionQuestionsPage.getColoradoTemplate().getInspectionReports()
				.isLocationsAdditionalControlMeasuresNeededRadioButtonClicked(locations), true);

	}

	/**
	 * Method to set and validate the inspection questions for <b>SQA Test Template
	 * for Certification Question Group 1</b>
	 * 
	 * @param inspecitionType
	 */
	private void setAndValidateSQATestTemplateForCertificationQuestionGroup1(String inspectionType) {

		/*
		 * Inspection Questions page
		 */
		// Instantiate the page so that we can interact with objects on it.
		InspectionQuestionsPageFactory inspectionQuestionsPage = new InspectionQuestionsPageFactory();

		// A worksheet can only be 30 characters long. If it is more, the worksheet
		// truncates it.
		if (inspectionQuestionsForm.length() > 30) {
			inspectionQuestionsForm = inspectionQuestionsForm.substring(0, 30 + 1);
		}

		ExcelDataConfig inspectionQuestionsGroup1File = getExcelFile("Inspection Questions.xlsx",
				"SQA Question Group 1");

		// FIRST - Hardcoding a row index here.
		int rowIndex = 1;

		// SECOND: Set the string / boolean values with data from the datasheet.
		String myDate = AutomationHelper.generateCalendarDate(inspectionQuestionsGroup1File.getData(rowIndex,
				inspectionQuestionsGroup1File.getColumnIndex("My Date")));

		String mySelectOne = inspectionQuestionsGroup1File.getData(rowIndex,
				inspectionQuestionsGroup1File.getColumnIndex("My Select One"));

		// THIRD: Set the data in the fields
		inspectionQuestionsPage.getSQAQuestionGroup1().setMyDate(myDate);
		inspectionQuestionsPage.getSQAQuestionGroup1().selectMySelectOne(mySelectOne);

		// Save
		inspectionQuestionsPage.clickSave();

		// FOURTH: Perform validations after save

		Assert.assertEquals(inspectionQuestionsPage.getSQAQuestionGroup1().readMyDate(), myDate,
				"SQA Question Group 1 - My Date");
		Assert.assertEquals(inspectionQuestionsPage.getSQAQuestionGroup1().readMySelectOne(), mySelectOne,
				"SQA Question Group 1 - My Select One");
	}

	/**
	 * Method to set and validate the inspection questions for <b>SQA Test Template
	 * for Certification Question Group 2</b>
	 * 
	 * @param inspecitionType
	 */
	private void setAndValidateSQATestTemplateForCertificationQuestionGroup2(String inspectionType) {

		/*
		 * Inspection Questions page
		 */
		// Instantiate the page so that we can interact with objects on it.
		InspectionQuestionsPageFactory inspectionQuestionsPage = new InspectionQuestionsPageFactory();

		// A worksheet can only be 30 characters long. If it is more, the worksheet
		// truncates it.
		if (inspectionQuestionsForm.length() > 30) {
			inspectionQuestionsForm = inspectionQuestionsForm.substring(0, 30 + 1);
		}

		ExcelDataConfig inspectionQuestionsGroup2File = getExcelFile("Inspection Questions.xlsx",
				"SQA Question Group 2");

		// FIRST - Hardcoding a row index here.
		int rowIndex = 1;

		// SECOND: Set the string / boolean values with data from the datasheet.
		boolean myCheckbox = Boolean.valueOf(inspectionQuestionsGroup2File.getData(rowIndex,
				inspectionQuestionsGroup2File.getColumnIndex("My Checkbox")));

		String myDate = AutomationHelper.generateCalendarDate(inspectionQuestionsGroup2File.getData(rowIndex,
				inspectionQuestionsGroup2File.getColumnIndex("My Date")));

		String myNumber = inspectionQuestionsGroup2File.getData(rowIndex,
				inspectionQuestionsGroup2File.getColumnIndex("My Number"));

		String myPhone = inspectionQuestionsGroup2File.getData(rowIndex,
				inspectionQuestionsGroup2File.getColumnIndex("My Phone"));

		boolean myRadioGroup = Boolean.valueOf(inspectionQuestionsGroup2File.getData(rowIndex,
				inspectionQuestionsGroup2File.getColumnIndex("My Radio Group")));

		// MySelectMany - Not developed

		String mySelectOne = inspectionQuestionsGroup2File.getData(rowIndex,
				inspectionQuestionsGroup2File.getColumnIndex("My Select One"));

		String myText = inspectionQuestionsGroup2File.getData(rowIndex,
				inspectionQuestionsGroup2File.getColumnIndex("My Text"));

		// MyYesNoNA - Not developed

		// THIRD: Set the data in the fields
		inspectionQuestionsPage.getSQAQuestionGroup2().setMyCheckbox(myCheckbox);
		inspectionQuestionsPage.getSQAQuestionGroup2().setMyDate(myDate);
		inspectionQuestionsPage.getSQAQuestionGroup2().setMyNumber(myNumber);
		inspectionQuestionsPage.getSQAQuestionGroup2().setMyPhone(myPhone);
		inspectionQuestionsPage.getSQAQuestionGroup2().setMyRadioGroup(myRadioGroup);
		// MySelectMany - Not Developed
		inspectionQuestionsPage.getSQAQuestionGroup2().selectMySelectOne(mySelectOne);
		inspectionQuestionsPage.getSQAQuestionGroup2().setMyText(myText);
		// MyYesNoNA - Not developed

		// Save
		inspectionQuestionsPage.clickSave();

		// FOURTH: Perform validations after save
		Assert.assertEquals(inspectionQuestionsPage.getSQAQuestionGroup2().readMyCheckbox(), myCheckbox,
				"SQA Question Group 2 - My Checkbox");
		Assert.assertEquals(inspectionQuestionsPage.getSQAQuestionGroup2().readMyDate(), myDate,
				"SQA Question Group 2 - My Date");
		Assert.assertEquals(inspectionQuestionsPage.getSQAQuestionGroup2().readMyNumber(), myNumber,
				"SQA Question Group 2 - My Number");
//		Assert.assertEquals(inspectionQuestionsPage.getSQAQuestionGroup2().readMyPhone().replace("(", "")
//				.replace(")", "").replace("-", "").trim(), myPhone.replace("-", ""), "SQA Question Group 2 - My Phone");
		Assert.assertEquals(inspectionQuestionsPage.getSQAQuestionGroup2().readMyRadioGroup(), myRadioGroup,
				"SQA Question Group 2 - My Radio Group");
		// MySelectMany - Not Developed
		Assert.assertEquals(inspectionQuestionsPage.getSQAQuestionGroup2().readMySelectOne(), mySelectOne,
				"SQA Question Group 2 - My Select One");
		Assert.assertEquals(inspectionQuestionsPage.getSQAQuestionGroup2().readMyText(), myText,
				"SQA Question Group 2 - My Text");
		// MyYesNoNA - Not developed

	}

	/**
	 * Method to change a date to the dateFormat passed in. This method is being
	 * used while another bug for inconsistent date format is being fixed.
	 * 
	 * @param newDateFormat              - e.g. "MMMMM d, yyyy h:mm a" or
	 *                                   "MM/dd/yyyy"
	 * @param currentDateFormat          - the date format that the data from the
	 *                                   datasheet is currently in
	 * @param currentDateAndTimeFromData - The date from the datasheet
	 * @return
	 */
	private String tempFormatDate(String newDateFormat, String currentDateFormat, String currentDateAndTimeFromData) {
		Date dateValue;
		try {
			SimpleDateFormat input = new SimpleDateFormat(currentDateFormat);
			dateValue = input.parse(currentDateAndTimeFromData);
			// d and not dd as to account for February 2 and not February 02
			SimpleDateFormat output = new SimpleDateFormat(newDateFormat);

			currentDateAndTimeFromData = output.format(dateValue);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return currentDateAndTimeFromData;

	}

}
