package testCases.BulkCreationsAndUploads;

import java.util.ArrayList;
import java.util.List;

import org.testng.Reporter;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pageFactories.DashboardPageFactory;
import pageFactories.SW2Tables;
import pageFactories.Project.ProjectPageFactory;
import pageFactories.Project.Inspections.ProjectInspectionsPageFactory;
import pageFactories.Project.ProjectDocuments.ProjectDocumentsPageFactory;
import testCases.BaseTestScriptConfig;
import testCases.modularTests.LoginMod;
import utilities.AutomationHelper;
import utilities.ExcelDataConfig;

/**
 * Class to perform bulk uploading of documents to the Project Documents page of
 * a particular project. <br>
 * Note: YOu must do the following:
 * 
 * <li>Change the PROJECT_NAME variable to reflect the project name we want to
 * work with.
 * <li>Change the DIVISION to be the division under which the project falls.
 * <li>Ensure that Project Documents.xlsx file is populated correctly. If
 * necessary, update the file name in this class.
 * <li>Ensure that all of the files that will be uploaded are in the Project
 * Documents folder (inside of localDataSets).
 * 
 * @author Jesse Childress
 *
 */
public class UploadProjectDocuments extends BaseTestScriptConfig {


	String fileName = "Project Documents.xlsx";
	String projectWorksheet = "Projects";
	String project;

	/**
	 * Method for the main test - Login
	 * 
	 * @param emailAddress
	 * @param password
	 */
	@Test(dataProvider = "projects")
	public void uploadProjectDocuments(String project) {

		this.project = project;

		// The first thing we need to do is to pull in the worksheet
		ExcelDataConfig projectsWorksheet = getExcelFile(fileName, projectWorksheet);
		int rowIndex = projectsWorksheet.getRowIndex("Project", this.project);
		String division = projectsWorksheet.getData(rowIndex, projectsWorksheet.getColumnIndex("Division"));

		/*
		 * LOGIN
		 */
		LoginMod login = new LoginMod("Admin");
		login.login(division);

		/*
		 * DASHBOARD
		 */

		DashboardPageFactory dashboardPage = new DashboardPageFactory();
		// Navigate to the Project page
		dashboardPage.clickProject();

		/*
		 * PROJECT PAGE
		 */
		ProjectPageFactory projectPage = new ProjectPageFactory();
		projectPage.setSearchProjects(project); // Reduce scope
		projectPage.clickProjectCard(project);

		/*
		 * PROJECT PAGE - PROJECT INSPECTIONS (main)
		 */
		ProjectInspectionsPageFactory projectInspectionsPage = new ProjectInspectionsPageFactory();
		projectInspectionsPage.clickProjectDocumentsTab();

		/*
		 * PROJECT PAGE - PROJECT DOCUMENTS
		 */
		ProjectDocumentsPageFactory projectDocumentsPage = new ProjectDocumentsPageFactory();

		// TODO - TEMP - DELETE NEXT LINE
		// Delete existing records
		deleteExistingDocuments(projectDocumentsPage);

		/*
		 * UPLOAD ALL DOCUMENTS
		 */
		uploadDocumentsForAllTypes(projectDocumentsPage);

		// Ensure that the Project value is marked TRUE as in it executed.
		projectsWorksheet.writeToWorkSheet(rowIndex, projectsWorksheet.getColumnIndex("Data Used"), "TRUE");

		projectDocumentsPage.clickLogOut();

	}

	private void deleteExistingDocuments(ProjectDocumentsPageFactory projectDocumentsPage) {

		List<String> documentTypes = new ArrayList<String>();
		documentTypes.add("Active Site Maps");
		documentTypes.add("Construction Docs/Civil Drawings");
		documentTypes.add("Soil Reports");
		documentTypes.add("Delegation Letters");
		documentTypes.add("Endangered Species");
		documentTypes.add("Historical Properties");
		documentTypes.add("Permits");
		documentTypes.add("Qualifications/Certifications");
		documentTypes.add("Local Stormwater Regulations");
		documentTypes.add("MISC");
		documentTypes.add("SWP Narrative/Maps");

		// Ensure that the document is actually in the list. Sometimes they're not.
		// Remove it if not.
		ArrayList<String> valuesToDelete = new ArrayList<String>();
		
		for (int i = 0; i < documentTypes.size(); i++) {
			
			String documentType = documentTypes.get(i);

			// If the section exists, remove it from the list.
			if (!projectDocumentsPage.isTableSectionPresent(documentType)) {
				valuesToDelete.add(documentType);
			}
			// If the section exists, but has no records beneath it, remove it
			else if (!projectDocumentsPage.doesDocumentTypeHaveTable(documentType)) {
				valuesToDelete.add(documentType);
			}

		}
		

		documentTypes.removeAll(valuesToDelete);

		// for every document type listed, go through a delete process
		for (String currentTableName : documentTypes) {

			SW2Tables currentTable = projectDocumentsPage.getTable(currentTableName);
			int numberOfItemsInTableForDeletion = currentTable.getRowCount();

			for (int x = 1; x <= numberOfItemsInTableForDeletion; x++) {
				
				currentTable = projectDocumentsPage.getTable(currentTableName);

				//Force the number 1 here. Each time we delete, we refresh and the row index changes.
				currentTable.selectMenuInCell("Name", currentTable.getCellText(1, "Name"), "Destroy");

				projectDocumentsPage.getDeleteDocumentPopup().clickDeleteDocumentOnPopup();
			}

		}

	}

	/**
	 * Method to loop through all of the tables and files in the datasheet and
	 * upload them.
	 * 
	 * @param projectDocumentsPage
	 */
	private void uploadDocumentsForAllTypes(ProjectDocumentsPageFactory projectDocumentsPage) {

		// REGULATIONS Note: Regulations are not uploaded here on the Project Documents
		// page. They are added through the Regulations Tab.

		// The purpose of this multi-dimensional array is to store pairs for the Table
		// name in the application and the worksheet name in the datafiles. This allows
		// us to use a loop to go through each one of them and perform uploads. They
		// can't be named the same because datafile worksheet names are not allowed to
		// have / characters.
		String[][] pageNameDatasheetName = new String[][] { { "Active Site Maps", "Active Site Maps" },
				{ "Construction Docs/Civil Drawings", "Construction Docs Civil Drawing" },
				{ "Soil Reports", "Soil Reports" }, { "Delegation Letters", "Delegation Letters" },
				{ "Endangered Species", "Endangered Species" }, { "Historical Properties", "Historical Properties" },
				{ "Permits", "Permits" }, { "Qualifications/Certifications", "Qualifications Certifications" },
				{ "Local Stormwater Regulations", "Local Stormwater Regulations" }, { "MISC", "MISC" },
				{ "SWP Narrative/Maps", "SWP Narrative Maps" } };

		/*
		 * At this level, were running the loop for each datasheet
		 */
		for (int i = 0; i < pageNameDatasheetName.length; i++) {

			String pageName = pageNameDatasheetName[i][0];
			Reporter.log("Current Loop for: " + pageName, true);

			// Get data file
			String dataWorksheetName = pageNameDatasheetName[i][1];
			ExcelDataConfig dataFile = getExcelFile("Project Documents.xlsx", dataWorksheetName);

			int rowCount = dataFile.getRowCount();// DOES NOT COUNT THE HEADER

			List<String> fileNames = new ArrayList<String>();// List to store all of the file names in the event that
																// there are multiples

			if (rowCount >= 1) {// Check that we have a least one row that we want to add. If not, this will not
								// evaluate and execute

				/*
				 * At this level, were in an individual work sheet getting the file name(s)
				 */
				List<String> worksheetValuesToUpdate = new ArrayList<String>();

				// Start X at 1 to prevent header of 0.
				for (int x = 1; x <= rowCount; x++) {

					String currentFileName = dataFile.getData(x, dataFile.getColumnIndex("Name")); // All File names are
																									// under "Name"

					boolean dataUsed = Boolean.valueOf(dataFile.getData(x, dataFile.getColumnIndex("Uploaded")));

					if (!dataUsed && dataFile.getData(x, "Project").equalsIgnoreCase(project)) {
						fileNames.add("Project Documents//" + currentFileName);// Have to add the folder that the
																				// documents
																				// live in
						worksheetValuesToUpdate.add(currentFileName);
					}

				}

				// If there are multiple files, this will append a comma between each of them.
				String fileNamesCSV = String.join(", ", fileNames);

				// The uploadFile method does a ton.
				projectDocumentsPage.uploadFile(pageName, fileNamesCSV);

				// After the file has uploaded successfully, lets update the datasheet
				for (String currentRecord : worksheetValuesToUpdate) {

					int rowIndex = dataFile.getRowIndexWithTwoValues("Project", project, "Name", currentRecord);

					dataFile.writeToWorkSheet(rowIndex, dataFile.getColumnIndex("Uploaded"), "TRUE");
					dataFile.writeToWorkSheet(rowIndex, dataFile.getColumnIndex("Created"),
							AutomationHelper.generateCalendarDate("TODAY"));
					dataFile.writeToWorkSheet(rowIndex, dataFile.getColumnIndex("Updated"),
							AutomationHelper.generateCalendarDate("TODAY"));

				}

				// For good measure, lets ensure that the datafile gets reset.
				dataFile = null;

			}

		}
	}

	@DataProvider(name = "projects")
	private String[] getProjectList() {

		ExcelDataConfig projectWorksheet = getExcelFile("Project Documents.xlsx", "Projects");

		// Get the row count
		int rowCount = projectWorksheet.getRowCount();

		List<String> projectList = new ArrayList<String>();

		if (rowCount >= 1) {

			for (int i = 1; i <= rowCount; i++) {

				// get each individual line of the datasheet
				// add only if the data hasn't been used
				if (projectWorksheet.getData(i, "Data Used").equalsIgnoreCase("FALSE")) {
					projectList.add(projectWorksheet.getData(i, "Project"));
				}
			}

			if ((projectList.size() < 1)) {

				Reporter.log(
						"There are no values in the Project Documents.xlsx / Projects worksheet that have not already been added, as indicated by a TRUE in the 'Data Used' column.",
						true);
				System.exit(1);
			}
		}
		return projectList.stream().toArray(String[]::new);
	}
}
