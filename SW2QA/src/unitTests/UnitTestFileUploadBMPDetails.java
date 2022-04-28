package unitTests;

import org.testng.annotations.Test;

import pageFactories.DashboardPageFactory;
import pageFactories.LoginPageFactory;
import pageFactories.Project.ProjectPageFactory;
import pageFactories.Project.BMPDetails.BMPDetailsPageFactory;
import pageFactories.Project.Inspections.ProjectInspectionsPageFactory;
import testCases.BaseTestScriptConfig;

public class UnitTestFileUploadBMPDetails extends BaseTestScriptConfig {
	
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
		inspectionsMainPage.clickBMPDetailsTab();
		
		/*
		 * BMP Details
		 */
		
		String file1 = "example-finding-images//images2.jpg";
		String file2 = "example-finding-images//images3.jpg";
		String file3 = "example-finding-images//Hole.jfif";
				
		
		BMPDetailsPageFactory bmpPage = new BMPDetailsPageFactory();
		bmpPage.uploadFile("Check Dam", file1 + "," + file2 + "," + file3);
		
		bmpPage.uploadFile("Concrete Washout Area", file1 + "," + file2 + "," + file3);

//		bmpPage.uploadFile("Cant Find Me", file1 + "," + file2 + "," + file3);

		
		bmpPage.getTable("Check Dam").selectMenuInCell("Name", "images2.jpg", "Destroy");
		bmpPage.getDeleteDocumentPopup().clickDeleteDocumentOnPopup();
		
		bmpPage.getTable("Check Dam").selectMenuInCell("Name", "images3.jpg", "Destroy");
		bmpPage.getDeleteDocumentPopup().clickDeleteDocumentOnPopup();

		bmpPage.getTable("Check Dam").selectMenuInCell("Name", "Hole.jfif", "Destroy");
		bmpPage.getDeleteDocumentPopup().clickDeleteDocumentOnPopup();

		
		bmpPage.getTable("Concrete Washout Area").selectMenuInCell("Name", "images2.jpg", "Destroy");
		bmpPage.getDeleteDocumentPopup().clickDeleteDocumentOnPopup();

		bmpPage.getTable("Concrete Washout Area").selectMenuInCell("Name", "images3.jpg", "Destroy");
		bmpPage.getDeleteDocumentPopup().clickDeleteDocumentOnPopup();

		bmpPage.getTable("Concrete Washout Area").selectMenuInCell("Name", "Hole.jfif", "Destroy");
		bmpPage.getDeleteDocumentPopup().clickDeleteDocumentOnPopup();


		

}
	
}
