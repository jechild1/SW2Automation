package unitTests;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageFactories.DashboardPageFactory;
import pageFactories.LoginPageFactory;
import pageFactories.Project.ProjectPageFactory;
import pageFactories.Project.Inspections.ProjectInspectionsPageFactory;
import pageFactories.Project.Inspections.InspectionQuestions.InspectionQuestionsPageFactory;
import testCases.BaseTestScriptConfig;
import utilities.AutomationHelper;

public class UnitTestInspection extends BaseTestScriptConfig {

	@Test
	public void unitTest() {

		LoginPageFactory loginPage = new LoginPageFactory();

		loginPage.loadPage();
		
		loginPage.setEmail("jesse_admin@gmail.com");
		loginPage.setPassword("password");
		loginPage.clickLogin();
		
		DashboardPageFactory dashboardPage = new DashboardPageFactory();
		
	
		
		//Click Projects
		dashboardPage.clickProject();
		
		ProjectPageFactory projectsPage = new ProjectPageFactory();
		
		projectsPage.setSearchProjects("jr");
		String projectName = "JRC Concert Arena";

		
		System.out.println("Projects Page Project Name: " + projectsPage.readProjectCardProjectName(projectName));
		System.out.println("Projects Page Client: " + projectsPage.readProjectCardClient(projectName));
		System.out.println("Projects Page Address: " + projectsPage.readProjectCardAddress(projectName));
		System.out.println("Project Page Number of Inspections: " + projectsPage.readProjectCardNumberOfInspections(projectName));
		
		projectsPage.clickProjectCard(projectName);
		
		//Project Inspections Page loaded
		ProjectInspectionsPageFactory projectInspections = new ProjectInspectionsPageFactory();
		System.out.println("Project Inspections - Project Name: " + projectInspections.readProjectName());
		System.out.println("Project Inspections - Project Client: " + projectInspections.readClientName());
		System.out.println("Project Inspections - Project Address: " + projectInspections.readAddress());
		
		//Cycle through tabs
		projectInspections.clickAllInspectionsTab();
		projectInspections.clickProjectDocumentsTab();
		projectInspections.clickBMPDetailsTab();
		projectInspections.clickActiveSiteMapTab();
		projectInspections.clickContactsTab();
		projectInspections.clickCorrectiveActionsTab();
		projectInspections.clickLogTab();
		projectInspections.clickAllInspectionsTab();
		
		System.out.println("Project Inspections > Next Inspection Due: " + projectInspections.readInspectionDueDate());
		
//		projectInspections.clickNewInspectionPostStorm();
		
		projectInspections.selectAllInspectionsValue("Routine + Post Storm");
		System.out.println("Project Inspections > All Inspections Select: " + projectInspections.readAllInspectionsValue());
		projectInspections.clickResetFilter();
		
		String inspectionDate = "09-28-2021";
		System.out.println("Project Inspections > All Inspections Page > Inspection Type: " + projectInspections.readInspectionType(inspectionDate));
		System.out.println("Project Inspections > All Inspections Page > Inspection Question Status: " + projectInspections.readInspectionQuestionStatus(inspectionDate));
		System.out.println("Project Inspections > All Inspections Page > Certification Status: " + projectInspections.readCertificationStatus(inspectionDate));
		System.out.println("Project Inspections > All Inspections Page > Compliance Status: " + projectInspections.readComplianceStatus(inspectionDate));
		System.out.println("Project Inspections > All Inspections Page > Inspection Number: " + projectInspections.readInspectionNumber(inspectionDate));

		projectInspections.clickInspectionCard(inspectionDate);
		
		//INSPECTION QUESTIONS - Landed here by default
		InspectionQuestionsPageFactory inspectionQuestions = new InspectionQuestionsPageFactory();
		
		//Variables:
		String inspectionType = "Routine";
		String dateOfInspection = "09-08-2021";
		boolean areYouAtTheSite = true;
		String dateStarted = "09-28-2021";
		String dateAndTimeFinished = "September 28, 2021 2:00 AM";
		String areOtherPeoplePresent = "No";
		boolean areYouHavingANiceDay = false;
		
		AutomationHelper.waitSeconds(5);
		
		Assert.assertEquals(inspectionQuestions.getJRCInspectionTemplate().readInspectionType(), inspectionType);
		Assert.assertEquals(inspectionQuestions.getJRCInspectionTemplate().readDateOfInspection(), dateOfInspection);
		Assert.assertEquals(inspectionQuestions.getJRCInspectionTemplate().readAreYouAtTheSite(), areYouAtTheSite);
		Assert.assertEquals(inspectionQuestions.getJRCInspectionTemplate().readDateStarted(), dateStarted);
		Assert.assertEquals(inspectionQuestions.getJRCInspectionTemplate().readDateAndTimeFinished(), dateAndTimeFinished);
		Assert.assertEquals(inspectionQuestions.getJRCInspectionTemplate().readAreOtherPeoplePresent(), areOtherPeoplePresent);
		Assert.assertEquals(inspectionQuestions.getJRCInspectionTemplate().readAreYouHavingANiceDay(), areYouHavingANiceDay);
		
		inspectionType = "Post Storm";
		dateOfInspection = "09-10-2021";
		areYouAtTheSite = false;
		dateStarted = "09-30-2021";
		dateAndTimeFinished = "September 30, 2021 2:00 AM";
		areOtherPeoplePresent = "No";
		areYouHavingANiceDay = true;
		
		inspectionQuestions.getJRCInspectionTemplate().selectInspectionType(inspectionType);
		inspectionQuestions.getJRCInspectionTemplate().setDateOfInspection(dateOfInspection);
		inspectionQuestions.getJRCInspectionTemplate().setAreYouAtTheSite(areYouAtTheSite);
		inspectionQuestions.getJRCInspectionTemplate().setDateStarted(dateStarted);
		inspectionQuestions.getJRCInspectionTemplate().setDateAndTimeFinished(dateAndTimeFinished);
		inspectionQuestions.getJRCInspectionTemplate().selectAreOtherPeoplePresent(areOtherPeoplePresent);
		inspectionQuestions.getJRCInspectionTemplate().setAreYouHavingANiceDay(areYouHavingANiceDay);
		
		Assert.assertEquals(inspectionQuestions.getJRCInspectionTemplate().readInspectionType(), inspectionType);
		Assert.assertEquals(inspectionQuestions.getJRCInspectionTemplate().readDateOfInspection(), dateOfInspection);
		Assert.assertEquals(inspectionQuestions.getJRCInspectionTemplate().readAreYouAtTheSite(), areYouAtTheSite);
		Assert.assertEquals(inspectionQuestions.getJRCInspectionTemplate().readDateStarted(), dateStarted);
		Assert.assertEquals(inspectionQuestions.getJRCInspectionTemplate().readDateAndTimeFinished(), dateAndTimeFinished);
		Assert.assertEquals(inspectionQuestions.getJRCInspectionTemplate().readAreOtherPeoplePresent(), areOtherPeoplePresent);
		Assert.assertEquals(inspectionQuestions.getJRCInspectionTemplate().readAreYouHavingANiceDay(), areYouHavingANiceDay);
		

AutomationHelper.waitSeconds(20);
		
		
		
		
		

		
		

		
		
		

	}

}
