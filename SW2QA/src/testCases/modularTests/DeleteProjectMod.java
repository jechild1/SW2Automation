package testCases.modularTests;

import org.testng.Assert;

import pageFactories.DashboardPageFactory;
import pageFactories.Project.ProjectPageFactory;
import pageFactories.Project.Inspections.ProjectInspectionsPageFactory;
import testCases.BaseTestScriptConfig;

/**
 * MODULAR SCRIPT:<br>
 * Modular script that deletes a project.
 * 
 * NOTE: Assume that we start on the dashboard page.
 * 
 * @author Jesse Childress
 *
 */
public class DeleteProjectMod extends BaseTestScriptConfig {
	
	/**
	 * Handles the deletion of a project, based on the passed in project name.
	 * <br>
	 * Note: It is assumed that we are using the Project.xlsx file on the
	 * ProjectsForAddition work sheet.
	 * 
	 * @param projectName
	 */
	public void deleteProject(String projectName) {
		
		//Start at the Dashboards page
		DashboardPageFactory dashboardsPage = new DashboardPageFactory();
		dashboardsPage.clickProject();
		
		//Projects page displayed
		ProjectPageFactory projectPage = new ProjectPageFactory();
		
		
		projectPage.setSearchProjects(projectName);
		
//		Assert.assertEquals(projectPage.isProjectCardPresent(projectName), true, "Project Card Exist on project page");
//		
//		projectPage.clickProjectCard(projectName);
		
		//If the project exist, delete it.
		
		if(projectPage.isProjectCardPresent(projectName)) {
			
			projectPage.clickProjectCard(projectName);
			
			//Inspection Main Page is displayed
			ProjectInspectionsPageFactory projectInspectionsPage = new ProjectInspectionsPageFactory();
			
			projectInspectionsPage.clickDeleteMenu();
			
			//Popup is displayed
			projectInspectionsPage.getDeleteProjectModal().clickYesIUnderstand();
			
			//Projects page is displayed again
			projectPage = new ProjectPageFactory();
			
			//Assert that the project is deleted
			Assert.assertEquals(projectPage.isProjectCardPresent(projectName), false, "Project Page - Expected project to be deleted");
			
			
			
		}
		
		//Regardless if the project was deleted or not, the user is back on the Projects page
		ProjectPageFactory projectPageFactory = new ProjectPageFactory();
		
		//Go back to the dashboard
		projectPage.clickDashboard();
		
		
		
		
		
		
		
		
		
	}
		
	}
