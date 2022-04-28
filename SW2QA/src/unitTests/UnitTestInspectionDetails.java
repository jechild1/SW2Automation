package unitTests;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageFactories.DashboardPageFactory;
import pageFactories.LoginPageFactory;
import pageFactories.Project.ProjectPageFactory;
import pageFactories.Project.Inspections.ProjectInspectionsPageFactory;
import pageFactories.Project.Inspections.InspectionDetails.InspectionCertificationPageFactory;
import pageFactories.Project.Inspections.InspectionDetails.InspectionFindingsPageFactory;
import pageFactories.Project.Inspections.InspectionDetails.InspectionMapPageFactory;
import pageFactories.Project.Inspections.InspectionQuestions.InspectionQuestionsPageFactory;
import testCases.BaseTestScriptConfig;
import utilities.AutomationHelper;

public class UnitTestInspectionDetails extends BaseTestScriptConfig {

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
		System.out.println("Project Inspections > All Inspections Page > Inspection Number: " + projectInspections.readInspectionNumber(inspectionDate));
		System.out.println("Project Inspections > All Inspections Page > Inspection Date: " + projectInspections.readInspectionDate(inspectionDate));
		System.out.println("Project Inspections > All Inspections Page > Inspection Type: " + projectInspections.readInspectionType(inspectionDate));
		System.out.println("Project Inspections > All Inspections Page > Inspection Question Status: " + projectInspections.readInspectionQuestionStatus(inspectionDate));
		System.out.println("Project Inspections > All Inspections Page > Certification Status: " + projectInspections.readCertificationStatus(inspectionDate));
		System.out.println("Project Inspections > All Inspections Page > Compliance Status: " + projectInspections.readComplianceStatus(inspectionDate));

		projectInspections.clickInspectionCard(inspectionDate);
		
		
		
		
		
		
		
		
		
		
		//INSPECTION QUESTIONS - Landed here by default
		InspectionQuestionsPageFactory inspectionQuestionsPage = new InspectionQuestionsPageFactory();
		
		inspectionQuestionsPage.clickFindingsTab();
		
		InspectionFindingsPageFactory inspectionFindings = new InspectionFindingsPageFactory();
		inspectionFindings.clickMapTab();
		
		InspectionMapPageFactory inspectionMapPage = new InspectionMapPageFactory();
		inspectionMapPage.clickCertificationTab();
		
		InspectionCertificationPageFactory inspectionCertPage = new InspectionCertificationPageFactory();
		inspectionCertPage.clickInspectionQuestionsTab();
		
		inspectionQuestionsPage = new InspectionQuestionsPageFactory();
		
		System.out.println("Inspection Details > Inspection Date: " + inspectionQuestionsPage.readInspectionDate());
		System.out.println("Inspection Details > Inspection Number: " + inspectionQuestionsPage.readInspectionNumber());
		System.out.println("Inspection Details > Inspection ID: " + inspectionQuestionsPage.readInspectionID());
		System.out.println("Inspection Details > Inspection Project: " + inspectionQuestionsPage.readInspectionProject());
		System.out.println("Inspection Details > Inspection Client: " + inspectionQuestionsPage.readInspectionClient());
		
		//Click Back to project
		inspectionQuestionsPage.clickBackToProject();
		
		projectInspections = new ProjectInspectionsPageFactory();
		
		projectInspections.clickInspectionCard(inspectionDate);
		
		//Now back on project
		inspectionQuestionsPage = new InspectionQuestionsPageFactory();
		
		//Delete
		inspectionQuestionsPage.clickDeleteInspection();
		inspectionQuestionsPage.getDeleteInspectionPopup().clickCancelOnPopup();
		
		//Print
		inspectionQuestionsPage.clickPrintInspection();
		inspectionQuestionsPage.getPrintInspectionPopup().clickPrint();
		
		AutomationHelper.waitSeconds(10);
		
		inspectionQuestionsPage.getPrintInspectionPopup().clickXOnPopup();
		
		
		
		
		
		
		

		
		
		
		
		
		
		

		
		
		
		
		

		
		

		
		
		

	}

}
