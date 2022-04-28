package unitTests;

import org.testng.annotations.Test;

import pageFactories.DashboardPageFactory;
import pageFactories.LoginPageFactory;
import pageFactories.Project.ProjectPageFactory;
import pageFactories.Project.Inspections.ProjectInspectionsPageFactory;
import pageFactories.Project.ProjectDocuments.ProjectDocumentsPageFactory;
import testCases.BaseTestScriptConfig;

public class UnitTestFileUploadProjectDocuments extends BaseTestScriptConfig {
	
	@Test
	public void unitTest() {

		LoginPageFactory loginPage = new LoginPageFactory();

		loginPage.loadPage();
		
		loginPage.setEmail("jesse_admin@gmail.com");
		loginPage.setPassword("password");
		loginPage.clickLogin();
		
		/*
		 * Dashboard page
		 */
		DashboardPageFactory dashboardPage = new DashboardPageFactory();
		
		dashboardPage.clickProject();
		
		/*
		 * Project Page
		 */
		ProjectPageFactory projectPage = new ProjectPageFactory();
		projectPage.setSearchProjects("J");
		
		projectPage.clickProjectCard("JRC Concert Arena");
		
		/*
		 * Project Inspections Page
		 */
		ProjectInspectionsPageFactory inspectionsMainPage = new ProjectInspectionsPageFactory();
		inspectionsMainPage.clickProjectDocumentsTab();
		
		/*
		 * Project Documents
		 */
		
		String file1 = "example-finding-images//images2.jpg";
		String file2 = "example-finding-images//images3.jpg";
		String file3 = "example-finding-images//Hole.jfif";
				
		
		ProjectDocumentsPageFactory projectDocsPage = new ProjectDocumentsPageFactory();
		projectDocsPage.uploadFile("Local Stormwater Regulations", file1 + "," + file2 + "," + file3);
		
		projectDocsPage.uploadFile("MISC", file1 + "," + file2 + "," + file3);

//		bmpPage.uploadFile("Cant Find Me", file1 + "," + file2 + "," + file3);

		
		projectDocsPage.getTable("Local Stormwater Regulations").selectMenuInCell("Name", "images2.jpg", "Destroy");
		projectDocsPage.getDeleteDocumentPopup().clickDeleteDocumentOnPopup();
		
		projectDocsPage.getTable("Local Stormwater Regulations").selectMenuInCell("Name", "images3.jpg", "Destroy");
		projectDocsPage.getDeleteDocumentPopup().clickDeleteDocumentOnPopup();

		projectDocsPage.getTable("Local Stormwater Regulations").selectMenuInCell("Name", "Hole.jfif", "Destroy");
		projectDocsPage.getDeleteDocumentPopup().clickDeleteDocumentOnPopup();

		
		projectDocsPage.getTable("MISC").selectMenuInCell("Name", "images2.jpg", "Destroy");
		projectDocsPage.getDeleteDocumentPopup().clickDeleteDocumentOnPopup();

		projectDocsPage.getTable("MISC").selectMenuInCell("Name", "images3.jpg", "Destroy");
		projectDocsPage.getDeleteDocumentPopup().clickDeleteDocumentOnPopup();

		projectDocsPage.getTable("MISC").selectMenuInCell("Name", "Hole.jfif", "Destroy");
		projectDocsPage.getDeleteDocumentPopup().clickDeleteDocumentOnPopup();
		
		//ONLY TEST ONE FILE
		projectDocsPage.uploadFile("Endangered Species", "example-finding-images//images2.jpg");
		projectDocsPage.getTable("Endangered Species").selectMenuInCell("Name", "images2.jpg", "Destroy");
		projectDocsPage.getDeleteDocumentPopup().clickDeleteDocumentOnPopup();

		

}
	
}
