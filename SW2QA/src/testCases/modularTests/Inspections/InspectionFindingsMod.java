package testCases.modularTests.Inspections;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.ElementNotInteractableException;
import org.testng.Assert;

import pageFactories.Project.Inspections.InspectionDetails.InspectionFindingsDetailsPageFactory;
import pageFactories.Project.Inspections.InspectionDetails.InspectionFindingsPageFactory;
import pageFactories.Project.Inspections.InspectionQuestions.InspectionQuestionsPageFactory;
import testCases.BaseTestScriptConfig;
import utilities.AutomationHelper;
import utilities.ExcelDataConfig;

/**
 * MODULAR SCRIPT:<br>
 * This class performs the process of populating a Finding for a given
 * Inspection Type. The constructor will require a finding type of: <br>
 * <li>Achievement
 * <li>Maintenance Item
 * <li>Corrective Action
 * 
 * NOTE: This assumes that we are on the Inspection Findings page.
 * 
 * @author Jesse Childress
 *
 */
public class InspectionFindingsMod extends BaseTestScriptConfig {

	String findingType;

	/**
	 * The class constructor will accept a Finding Type. This is limited to
	 * Achievement, Maintenance Item, or Corrective Action. If one of these values
	 * are not passed in, the constructor will throw an error.<br>
	 * <li>Achievement
	 * <li>Maintenance Item
	 * <li>Corrective Action
	 * 
	 * @param findingType
	 */
	public InspectionFindingsMod(String findingType) {
		this.findingType = findingType;

		// Lets ensure that the finding type is relevant before we proceed. Doing this
		// in the constructor will guarantee that our data is correct.
		
		switch (this.findingType) {
		case "Achievement":
			break;
		case "Maintenance Item":
			break;
		case "Corrective Action":
			break;
		default:
			throw new ElementNotInteractableException(
					"The Finding type is not Achievement, Maintenance Item, or Corrective Action. Please check your data and case.");

		}

	}

	/**
	 * Creates and validates a finding.
	 */
	public void createAndValidateFinding() {

		/*
		 * Inspection Findings page
		 */

		// Instantiate the page so that we can interact with objects on it.
		InspectionFindingsPageFactory inspectionFindingsPage = new InspectionFindingsPageFactory();

		// Get a reference to the datasheet
		ExcelDataConfig inspectionFindingsFile = getExcelFile("Findings.xlsx", "Findings");

		// Find out which data record we want to use, i.e. Finding type. In the
		// parameters here, "Finding Type" is the column header and findingType is, say
		// "Achievement".
		int rowIndex = inspectionFindingsFile.getRandomRowWithValue("Finding Type", findingType);
		
		
		//Assign data from datasheet.		
		String findingType = this.findingType;
		String location = inspectionFindingsFile.getData(rowIndex, inspectionFindingsFile.getColumnIndex("Location"));
		String dateInitiated = AutomationHelper.generateCalendarDate(inspectionFindingsFile.getData(rowIndex, inspectionFindingsFile.getColumnIndex("Date Initiated")));
		String dateCompleted = AutomationHelper.generateCalendarDate(inspectionFindingsFile.getData(rowIndex, inspectionFindingsFile.getColumnIndex("Date Completed")));
		String observations = inspectionFindingsFile.getData(rowIndex, inspectionFindingsFile.getColumnIndex("Observations"));
		String imagePath = inspectionFindingsFile.getData(rowIndex, inspectionFindingsFile.getColumnIndex("Image Path"));
		
		/*
		 * Set values on the Findings Page
		 */
		inspectionFindingsPage.clickAddFinding();
		
		//Set the fields - This is from ADD modal - not EDIT
		inspectionFindingsPage.getAddAFindingModal().clickFindingType(findingType);
		inspectionFindingsPage.getAddAFindingModal().setLocationTextbox(location);
		inspectionFindingsPage.getAddAFindingModal().setDateInitiatedTextbox(dateInitiated);
		inspectionFindingsPage.getAddAFindingModal().setDateCompletedTextbox(dateCompleted);
		inspectionFindingsPage.getAddAFindingModal().setObservationsTextArea(observations);
		inspectionFindingsPage.getAddAFindingModal().uploadFile(imagePath);
		
		
		//Inspection Finding Details Page
		InspectionFindingsDetailsPageFactory inspectionFindingDetailsPage = new InspectionFindingsDetailsPageFactory();
		
		//Assert values
		Assert.assertEquals(inspectionFindingDetailsPage.readSelectedFindingType(), findingType, "Inspection Details - Finding Type");
		Assert.assertEquals(inspectionFindingDetailsPage.readLocationTextbox(), location, "Inspection Details - Location");
		Assert.assertEquals(inspectionFindingDetailsPage.readDateInitiatedTextbox(), dateInitiated, "Inspection Details - Date Initiated");
		Assert.assertEquals(inspectionFindingDetailsPage.readDateCompletedTextbox(), dateCompleted, "Inspection Details - Date Completed");
		
//		Assert.assertEquals(inspectionFindingDetailsPage.readObervationsTextArea(), observations);
		
		Assert.assertEquals(inspectionFindingDetailsPage.isImagePresent(imagePath), true, "Inspection Details - Image(s) Present");
		


		
		
		
		
		




		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		

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
		ExcelDataConfig inspectionQuestionsFile = getExcelFile("Inspection Questions.xlsx", findingType);

		// FIRST: We need to know which type of inspection we want to add. The second
		// parameter in the class constructor tells us the type. From this type, we can
		// find the row index in the datasheet.
		int rowIndex = inspectionQuestionsFile.getRowIndex("Inspection Type", inspectionType);

		// SECOND: Set the string / boolean values with data from the datasheet.
//		String inspectionType = inspectionQuestionsFile.getData(rowIndex,
//				inspectionQuestionsFile.getColumnIndex("Inspection Type"));
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
		dateAndTimeFinished = tempFormatDate(dateAndTimeFinished);

		Assert.assertEquals(inspectionQuestionsPage.getSQAInspectionTemplateAlpha().readDateAndTimeFinished(),
				dateAndTimeFinished, "Inspection Questions - Date and Time Finished");

		Assert.assertEquals(inspectionQuestionsPage.getSQAInspectionTemplateAlpha().readAreOtherPeoplePresent(),
				areOtherPeoplePresent, "Inspection Questions - Are other people present");
		Assert.assertEquals(inspectionQuestionsPage.getSQAInspectionTemplateAlpha().readAreYouHavingANiceDay(),
				areYouHavingANiceDay, "Inspection Questions - Are you having a nice day");

	}

	/**
	 * Method to change date from "MM-dd-yyyy h:mm a" to MMMMM dd, yyyy h:mm a
	 * format, while a bug is being fixed. That bug is an inconsistent date format.
	 * 
	 * @param dateAndTimeFinished
	 * @return String date
	 */
	private String tempFormatDate(String dateAndTimeFinished) {
		Date dateValue;
		try {
			SimpleDateFormat input = new SimpleDateFormat("MM-dd-yyyy h:mm a");
			dateValue = input.parse(dateAndTimeFinished);
			SimpleDateFormat output = new SimpleDateFormat("MMMMM dd, yyyy h:mm a");
			dateAndTimeFinished = output.format(dateValue);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return dateAndTimeFinished;

	}

}
