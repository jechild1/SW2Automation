package unitTests;

import org.testng.annotations.Test;

import pageFactories.DashboardPageFactory;
import pageFactories.LoginPageFactory;
import pageFactories.Regulations.RegulationsDetailsPageFactory;
import pageFactories.Regulations.RegulationsPageFactory;
import testCases.BaseTestScriptConfig;

public class UnitTestFileUploadRegulation extends BaseTestScriptConfig {
	
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
		
		dashboardPage.clickRegulations();
		
		/*
		 * Regulations Page
		 */
		
		//Click on the Delete Me Regulation
		RegulationsPageFactory regulationsPage = new RegulationsPageFactory();
		regulationsPage.getRegulationsTable().clickRow("Name", "Delete Me");
		
		/*
		 * Regulation Details Page
		 */
		RegulationsDetailsPageFactory regulationDetailsPage = new RegulationsDetailsPageFactory();

		//Upload the file(s)
		regulationDetailsPage.uploadFile("example-finding-images//images2.jpg, example-finding-images//images3.jpg, example-finding-images//Hole.jfif");
		regulationDetailsPage.readPageTitle();
		

		//Delete the files.
		regulationDetailsPage.getRegulationDetailsTable().selectMenuInCell("Name", "images2.jpg", "Destroy");
		regulationDetailsPage.getDeleteDocumentPopup().clickDeleteDocumentOnPopup();

		regulationDetailsPage.getRegulationDetailsTable().selectMenuInCell("Name", "images3.jpg", "Destroy");
		regulationDetailsPage.getDeleteDocumentPopup().clickDeleteDocumentOnPopup();

		regulationDetailsPage.getRegulationDetailsTable().selectMenuInCell("Name", "Hole.jfif", "Destroy");
		regulationDetailsPage.getDeleteDocumentPopup().clickDeleteDocumentOnPopup();

		
		

		
		

		

		

}
	
}
