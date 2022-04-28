package unitTests;

import org.testng.annotations.Test;

import pageFactories.DashboardPageFactory;
import pageFactories.LoginPageFactory;
import pageFactories.Project.ProjectPageFactory;
import pageFactories.Project.Inspections.ProjectInspectionsPageFactory;
import pageFactories.Project.Inspections.InspectionQuestions.InspectionQuestionsPageFactory;
import pageFactories.Project.Inspections.InspectionQuestions.InspectionQuestionsPageFactory.JRCInspectionTemplate;
import testCases.BaseTestScriptConfig;
import utilities.AutomationHelper;

public class UnitTest2 extends BaseTestScriptConfig {

	@Test
	public void unitTest() {

		LoginPageFactory loginPage = new LoginPageFactory();

		loginPage.loadPage();
		
		loginPage.setEmail("jesse_admin@gmail.com");
		loginPage.setPassword("password");
		loginPage.clickLogin();
		
		DashboardPageFactory dashboardPage = new DashboardPageFactory();
		
		//Test to throw exception
//		dashboardPage.paginationClickFirstPage();
		
		
		//Click Projects
		dashboardPage.clickProject();
		
		ProjectPageFactory projectsPage = new ProjectPageFactory();
		
//		projectsPage.setSearchProjects("J");
		
//		for(int i = 0; i < 15; i++) {
//			projectsPage.paginationGoToNextPage();
//		}
//		
//		for(int i = 0; i < 20; i++) {
//			projectsPage.paginationGoToPreviousPage();
//		}
		
		projectsPage.setSearchProjects("jr");
		String projectName = "JRC Aquatic Center";
//		projectsPage.clickProject(projectName);
//		projectsPage.clickProject("Project doesn't exist");
		
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
		
		String inspectionDate = "09/20/2021";
//		projectInspections.clickInspectionCard("09/20/2021");
		System.out.println("Project Inspections > All Inspections Page > Inspection Type: " + projectInspections.readInspectionType(inspectionDate));
		System.out.println("Project Inspections > All Inspections Page > Inspection Question Status: " + projectInspections.readInspectionQuestionStatus(inspectionDate));
		System.out.println("Project Inspections > All Inspections Page > Certification Status: " + projectInspections.readCertificationStatus(inspectionDate));
		System.out.println("Project Inspections > All Inspections Page > Compliance Status: " + projectInspections.readComplianceStatus(inspectionDate));
		System.out.println("Project Inspections > All Inspections Page > Inspection Number: " + projectInspections.readInspectionNumber(inspectionDate));

		AutomationHelper.waitSeconds(5);
		
		//INSPECTION QUESTIONS
		InspectionQuestionsPageFactory inspectionQuestions = new InspectionQuestionsPageFactory();
		
		
		

		
		
		
		
		

		
		

		
		
		

	}

}
