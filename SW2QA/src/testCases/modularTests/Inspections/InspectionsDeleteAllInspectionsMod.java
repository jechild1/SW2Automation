package testCases.modularTests.Inspections;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;

import pageFactories.Project.Inspections.ProjectInspectionsPageFactory;
import pageFactories.Project.Inspections.InspectionQuestions.InspectionQuestionsPageFactory;
import testCases.BaseTestScriptConfig;
import utilities.AutomationHelper;

/**
 * MODULAR SCRIPT:<br>
 * This class performs the process of deleting ALL INSPECTIONS for a given
 * process. It simply loops through until it is finished.
 * 
 * NOTE: This assumes that we are on the Inspections main page (listing all of the inspections)
 * 
 * @author Jesse Childress
 *
 */
public class InspectionsDeleteAllInspectionsMod extends BaseTestScriptConfig {


	/**
	 * Method to set and validate the inspection questions for SQA Inspection
	 * Template Alpha
	 * 
	 * @param inspecitionType
	 */
	public void deleteAllInspections() {
		
		AutomationHelper.printMethodName();
		
		Reporter.log("Beginning deletion for all inspections on the page.", true);
		
		ProjectInspectionsPageFactory projectInspectionsPage = new ProjectInspectionsPageFactory();
		
		
		while(projectInspectionsPage.countInspections() > 0){
			
			//Grab all of the inspection cards
			AutomationHelper.adjustWaitTimeToShort();
			List<WebElement> inspectionCards = driver.findElements(By.xpath("//div[@class = 'inspection flex-item']"));
			AutomationHelper.adjustWaitTimeToNormal();
			
			//Get the inspection card at the 1 position, which will always be the first one.
			inspectionCards.get(0).click();
			
			//The Inspection Details page is displayed. Get a reference to it
			InspectionQuestionsPageFactory inspectionQuestionsPage = new InspectionQuestionsPageFactory();
			inspectionQuestionsPage.clickDeleteInspection();
			
			//The "Are you sure you want to delete this inspection?" modal is displayed.
			inspectionQuestionsPage.getDeleteInspectionPopup().clickDeleteOnPopup();
			
			//The main inspections card is loaded again, which will be executed all over again. Grab a new reference to it
			projectInspectionsPage = new ProjectInspectionsPageFactory();
			
		}
	}
}
