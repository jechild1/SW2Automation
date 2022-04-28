package unitTests;

import org.testng.annotations.Test;

import pageFactories.DashboardPageFactory;
import pageFactories.LoginPageFactory;
import pageFactories.Project.ProjectPageFactory;
import pageFactories.Project.Inspections.ProjectInspectionsPageFactory;
import pageFactories.Project.Inspections.InspectionDetails.InspectionFindingsPageFactory;
import pageFactories.Project.Inspections.InspectionQuestions.InspectionQuestionsPageFactory;
import testCases.BaseTestScriptConfig;

public class UnitTestFileUploadInspectionFinding extends BaseTestScriptConfig {

	@Test
	public void unitTest() {

		LoginPageFactory loginPage = new LoginPageFactory();

		loginPage.loadPage();
		
		loginPage.setEmail("jesse_admin@gmail.com");
		loginPage.setPassword("password");
		loginPage.clickLogin();
		
//		LoginMod login = new LoginMod("Inspector");
//		login.login();
		
		DashboardPageFactory dashboardPage = new DashboardPageFactory();
		
	
		
		//Click Projects
		dashboardPage.clickProject();
		
		ProjectPageFactory projectsPage = new ProjectPageFactory();
		
		projectsPage.setSearchProjects("jr");
		String projectName = "JRC Concert Arena";

		
				
		projectsPage.clickProjectCard(projectName);
		
		//Project Inspections Page loaded
		ProjectInspectionsPageFactory projectInspections = new ProjectInspectionsPageFactory();
		System.out.println("Project Inspections - Project Name: " + projectInspections.readProjectName());
		System.out.println("Project Inspections - Project Client: " + projectInspections.readClientName());
		System.out.println("Project Inspections - Project Address: " + projectInspections.readAddress());
		
		
		String inspectionDate = "12-10-2021";
		
		projectInspections.clickInspectionCard(inspectionDate);
		
		//INSPECTION QUESTIONS - Landed here by default
		InspectionQuestionsPageFactory inspectionQuestions = new InspectionQuestionsPageFactory();
		
		//INSPECTION FINDINGS
		inspectionQuestions.clickFindingsTab();
		InspectionFindingsPageFactory inspectionFindingsPage = new InspectionFindingsPageFactory();
		
//		inspectionFindingsPage.clickFinding("Achievement", "12-10-2021", "");
		
		inspectionFindingsPage.clickAddFinding();
		
		inspectionFindingsPage.getAddAFindingModal().clickAchievementRadioButton();
		inspectionFindingsPage.getAddAFindingModal().setLocationTextbox("This is the location");
		inspectionFindingsPage.getAddAFindingModal().setDateInitiatedTextbox("12-21-2021");
		inspectionFindingsPage.getAddAFindingModal().setDateCompletedTextbox("12-23-2021");
		inspectionFindingsPage.getAddAFindingModal().setObservationsTextArea("This is an observation");
		
		inspectionFindingsPage.getAddAFindingModal().uploadFile("example-finding-images//images2.jpg, example-finding-images//images3.jpg, example-finding-images//Hole.jfif");
//		inspectionFindingsPage.getAddAFindingModal().uploadFile("example-finding-images//Hole.jfif");

		
		
		
//		inspectionFindingsPage.getAddAFindingModal().setObservationsTextArea("This is an observation");



		
		
		

	}

}
