package testCases.modularTests;

import org.testng.Assert;

import pageFactories.DashboardPageFactory;
import pageFactories.Divisions.DivisionsInspectionTemplatesPageFactory;
import pageFactories.Divisions.DivisionsPageFactory;
import pageFactories.Divisions.DivisionsRegulationsPageFactory;
import testCases.BaseTestScriptConfig;
import utilities.AutomationHelper;

/**
 * MODULAR SCRIPT:<br>
 * This class accepts for Inspection Template Name and will add the Inspection
 * Template Name to the Division that the user is currently logged in under.
 * 
 * NOTE: Assume that we start on the dashboard page.
 * 
 * @author Jesse Childress
 *
 */
public class AddInspectionTemplateToDivisionMod extends BaseTestScriptConfig {

	/**
	 * 
	 * Note: Assume we start on the Dashboard page
	 * 
	 * @param inspectionTemplateName
	 */
	public void addInspectionTemplateToDivision(String inspectionTemplateName) {

		AutomationHelper.printMethodName();

		// Grab the division
		DashboardPageFactory dashboardPage = new DashboardPageFactory();
		String division = dashboardPage.readDivision();

		/*
		 * STEP 1: Navigate to the Divisions tab
		 */

		dashboardPage.clickDivisions();

		// Divisions main page is displayed
		DivisionsPageFactory divisionsPage = new DivisionsPageFactory();

		// Assert that the division is actually in the table
		Assert.assertEquals(divisionsPage.getDivisionsTable().isRowInTable("Name", division), true,
				"Divisions Table - Asserting Division " + division + " is in the table.");

		divisionsPage.getDivisionsTable().clickRow("Name", division);
		
		//Regulations page is displayed
		DivisionsRegulationsPageFactory divisionRegulationsPage = new DivisionsRegulationsPageFactory();
		divisionRegulationsPage.clickInspectionTemplatesTab();

		/*
		 * STEP 2: Click Add Templates
		 */
		DivisionsInspectionTemplatesPageFactory divisionsInspectionTemplatesPage = new DivisionsInspectionTemplatesPageFactory();
		divisionsInspectionTemplatesPage.clickAddTemplates();

		/*
		 * STEP 3:
		 */
		// The Add Inspection Templates modal is displayed.
		divisionsInspectionTemplatesPage.getAddInspectionTemplatesModal()
				.selectInspectionTemplates(inspectionTemplateName);
		// Click OK on the modal
		divisionsInspectionTemplatesPage.getAddInspectionTemplatesModal().clickOKOnModal();

		/*
		 * STEP 4:
		 * 
		 */
		// Obtain a new reference to the page after adding the new inspection template
		divisionsInspectionTemplatesPage = new DivisionsInspectionTemplatesPageFactory();

		// Assert that the value is now in the table
		Assert.assertEquals(divisionsInspectionTemplatesPage.getInspectionTemplatesTable().isRowInTable("Name",
				inspectionTemplateName), true, "Inspection Templates Page - New Inspection Template Added");
		
		//Click the Dashboard to reset for other classes
		divisionsInspectionTemplatesPage.clickDashboard();

	}

}
