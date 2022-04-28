package testCases.modularTests;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.Reporter;

import pageFactories.DashboardPageFactory;
import pageFactories.Project.ProjectPageFactory;
import pageFactories.Project.Inspections.ProjectInspectionsPageFactory;
import testCases.BaseTestScriptConfig;
import utilities.AutomationHelper;
import utilities.ExcelDataConfig;

/**
 * MODULAR SCRIPT:<br>
 * 
 * This modular script adds a new project to a division. It takes in a Project
 * Name and then uses the "Projects.xlsx" / "ProjectsForAddition" work sheet.
 * 
 * NOTE: Assume that we start on the dashboard page.
 * 
 * @author Jesse Childress
 *
 */
public class AddNewProjectMod extends BaseTestScriptConfig {
	/*
	 * Class level variables. By assigning these here, we can use them in multiple
	 * methods.
	 */
	String client;
	String permittee;
	String inspectionFormType;
	String projectName;
	String address;
	String city;
	String state;
	String zip;
	String latitude;
	String longitude;
	String timeZone;
	String inspector;
	String anticipateProjectTimelineBegin;
	String anticipateProjectTimelineEnd;
	String nextRoutineInspectionDate;
	String routineInspectionInterval;
	String regulations;
	String additionalNotes;

	boolean activeSiteMaps;
	boolean constructionDocsCivilDrawings;
	boolean soilReports;
	boolean delegationLetters;
	boolean endangeredSpecies;
	boolean historicalProperties;
	boolean permits;
	boolean qualificationCertifications;
	boolean localStormwaterRegulations;
	boolean misc;
	boolean swpNarrativeMaps;

	String completeAddress;

	/**
	 * Method to add a new project to the currently logged in division. <br>
	 * Note: It is assumed that we are using the "Projects.xlsx" /
	 * "ProjectsForAddition" work sheet.
	 * 
	 * @param projectName
	 */
	public void addNewProject(String projectName) {

		inspectionFormType = projectName;

		AutomationHelper.printMethodName();

		// Note: First test left us on the Dashboards page
		DashboardPageFactory dashboardPage = new DashboardPageFactory();

		// Navigate to Projects page.
		dashboardPage.clickProject();

		/*
		 * PROJECTS PAGE
		 */
		// Project Page
		ProjectPageFactory projectPage = new ProjectPageFactory();

		/*
		 * Obtain the data and set the variables
		 */

		// Gather all of the information for the project to add. Ensure that it is not
		// currently added.
		ExcelDataConfig projectsFile = getExcelFile("Projects.xlsx", "ProjectsForAddition");

		/*
		 * Assign the data values to the global variables
		 */
		assignDataValues(projectsFile);

		/*
		 * Delete project if it currently exists
		 */
		// Currently on the Project page
		projectPage.setSearchProjects(projectName);
		Reporter.log("Beginning check for existing project on the page. Will delete if present.", true);

		deleteExistingProject();

		// Get a new reference to the Projects Page after potential deletion
		projectPage = new ProjectPageFactory();

		// Ensure that the current project page does not have a card with the value in
		// it.
		Assert.assertEquals(projectPage.isProjectCardPresent(projectName), false,
				"Project - Card not present assertion");

		/*
		 * Create the new project using the utility method
		 */
		// Click the Add Project button
		projectPage.clickCreateNewProjectButton();

		// The Create New Project modal is displayed.
		// Call method to set fields
		setCreateNewProjectFields(projectPage);

		// Save the temporary project
		projectPage.getCreateNewProjectModal().clickOKOnModal();

		// The Project Inspections Main Page is displayed
		ProjectInspectionsPageFactory projectInspectionsMainPage = new ProjectInspectionsPageFactory();

		/*
		 * Assert that the fields exist using the utility method
		 */
		assertProjectFields(projectInspectionsMainPage);

		// Navigate back to the dashboards page to ensure that things are ready to go
		// for the next method
		projectInspectionsMainPage.clickDashboard();

	}

	/**
	 * Utility method to assign the global level variables for use throughout the
	 * class.
	 * 
	 * @param projectsFile
	 */
	private void assignDataValues(ExcelDataConfig projectsFile) {

		int rowIndex = projectsFile.getRowIndex("Inspection Form Type", inspectionFormType);

		// Obtain a the data from the data sheet.
		client = projectsFile.getData(rowIndex, projectsFile.getColumnIndex("Client"));
		permittee = projectsFile.getData(rowIndex, projectsFile.getColumnIndex("Permittee"));
		inspectionFormType = projectsFile.getData(rowIndex, projectsFile.getColumnIndex("Inspection Form Type"));
		projectName = projectsFile.getData(rowIndex, projectsFile.getColumnIndex("Project Name"));
		address = projectsFile.getData(rowIndex, projectsFile.getColumnIndex("Address"));
		city = projectsFile.getData(rowIndex, projectsFile.getColumnIndex("City"));
		state = projectsFile.getData(rowIndex, projectsFile.getColumnIndex("State"));
		zip = projectsFile.getData(rowIndex, projectsFile.getColumnIndex("Zip"));
		latitude = projectsFile.getData(rowIndex, projectsFile.getColumnIndex("Latitude"));
		longitude = projectsFile.getData(rowIndex, projectsFile.getColumnIndex("Longitude"));
		timeZone = projectsFile.getData(rowIndex, projectsFile.getColumnIndex("Time Zone"));
		inspector = projectsFile.getData(rowIndex, projectsFile.getColumnIndex("Inspector"));
		anticipateProjectTimelineBegin = AutomationHelper.generateCalendarDate(
				projectsFile.getData(rowIndex, projectsFile.getColumnIndex("Anticipated Project Timeline Begin")));
		anticipateProjectTimelineEnd = AutomationHelper.generateCalendarDate(
				projectsFile.getData(rowIndex, projectsFile.getColumnIndex("Anticipated Project Timeline End")));
		nextRoutineInspectionDate = AutomationHelper.generateCalendarDate(
				projectsFile.getData(rowIndex, projectsFile.getColumnIndex("Next Routine Inspection Date")));
		routineInspectionInterval = projectsFile.getData(rowIndex,
				projectsFile.getColumnIndex("Routine Inspection Interval"));
		regulations = projectsFile.getData(rowIndex, projectsFile.getColumnIndex("Regulations"));
		additionalNotes = projectsFile.getData(rowIndex, projectsFile.getColumnIndex("Additional Notes"));

		activeSiteMaps = Boolean
				.valueOf(projectsFile.getData(rowIndex, projectsFile.getColumnIndex("Active Site Maps")));
		constructionDocsCivilDrawings = Boolean.valueOf(
				projectsFile.getData(rowIndex, projectsFile.getColumnIndex("Construction Docs Civil Drawings")));
		soilReports = Boolean.valueOf(projectsFile.getData(rowIndex, projectsFile.getColumnIndex("Soil Reports")));
		delegationLetters = Boolean
				.valueOf(projectsFile.getData(rowIndex, projectsFile.getColumnIndex("Delegation Letters")));
		endangeredSpecies = Boolean
				.valueOf(projectsFile.getData(rowIndex, projectsFile.getColumnIndex("Endangered Species")));
		historicalProperties = Boolean
				.valueOf(projectsFile.getData(rowIndex, projectsFile.getColumnIndex("Historical Properties")));
		permits = Boolean.valueOf(projectsFile.getData(rowIndex, projectsFile.getColumnIndex("Permits")));
		qualificationCertifications = Boolean
				.valueOf(projectsFile.getData(rowIndex, projectsFile.getColumnIndex("Qualifications Certifications")));
		localStormwaterRegulations = Boolean
				.valueOf(projectsFile.getData(rowIndex, projectsFile.getColumnIndex("Local Stormwater Regulations")));
		misc = Boolean.valueOf(projectsFile.getData(rowIndex, projectsFile.getColumnIndex("MISC")));
		swpNarrativeMaps = Boolean
				.valueOf(projectsFile.getData(rowIndex, projectsFile.getColumnIndex("SWP Narrative Maps")));

		completeAddress = address + ", " + city + ", " + state + " " + zip;

	}

	/**
	 * Utility method that performs asserts on the Project page to ensure that the
	 * changes stick. It ensures a commit by clicking "Edit" on the project. This
	 * loads the modal with the data in it.
	 * 
	 * @param projectInspectionsMainPage
	 */
	private void assertProjectFields(ProjectInspectionsPageFactory projectInspectionsMainPage) {
		projectInspectionsMainPage.clickEditMenu();

		// Client
		Assert.assertEquals(projectInspectionsMainPage.getEditProjectModal().readClientDropdown(), client,
				"Edit Project - Client");

		// Permittee
		Assert.assertEquals(projectInspectionsMainPage.getEditProjectModal().readPermitteeTextbox(), permittee,
				"Edit Project - Permittee");

		// Inspection Form Type
		Assert.assertEquals(projectInspectionsMainPage.getEditProjectModal().readInspectionFormTypeDropdown(),
				inspectionFormType, "Edit Project - Inspection Form Type");

		// Project Name
		Assert.assertEquals(projectInspectionsMainPage.getEditProjectModal().readProjectNameTextbox(), projectName,
				"Edit Project - Project Name");

		// Project Address
		Assert.assertEquals(projectInspectionsMainPage.getEditProjectModal().readAddressTextbox(), address,
				"Edit Project - Address");

		// Project City
		Assert.assertEquals(projectInspectionsMainPage.getEditProjectModal().readCityTextbox(), city,
				"Edit Project - City");

		// Project State
		Assert.assertEquals(projectInspectionsMainPage.getEditProjectModal().readStateDropdown(), state,
				"Edit Project - State");

		// Project Zip
		Assert.assertEquals(projectInspectionsMainPage.getEditProjectModal().readZipTextbox(), zip,
				"Edit Project - Zip");

		// Latitude
		Assert.assertEquals(projectInspectionsMainPage.getEditProjectModal().readLatitudeTextbox(), latitude,
				"Edit Project - Latitude");

		// Longitude
		Assert.assertEquals(projectInspectionsMainPage.getEditProjectModal().readLongitudeTextbox(), longitude,
				"Edit Project - Longitude");

		// Inspector
		Assert.assertEquals(projectInspectionsMainPage.getEditProjectModal().readInspectorDropdown(), inspector,
				"Edit Project - Inspector");

		// Anticipated Project Timeline Begin

		Assert.assertEquals(projectInspectionsMainPage.getEditProjectModal().readTimeLineStartDateTextbox(),
				AutomationHelper.generateCalendarDate(anticipateProjectTimelineBegin),
				"Edit Project - Anticipated Project Timeline Begin");

		// Anticipated Project Timeline End

		Assert.assertEquals(projectInspectionsMainPage.getEditProjectModal().readTimeLineEndDateTextbox(),
				AutomationHelper.generateCalendarDate(anticipateProjectTimelineEnd),
				"Edit Project - Anticipated Project Timeline End");

		// Next Routine Inspection Date
		Assert.assertEquals(projectInspectionsMainPage.getEditProjectModal().readNextRoutineInspectionDateTextbox(),
				AutomationHelper.generateCalendarDate(nextRoutineInspectionDate),
				"Edit Project - Next Routine Inspection Date");

		// Routine Inspection INterval
		Assert.assertEquals(projectInspectionsMainPage.getEditProjectModal().readRoutineInspectionIntervalTextbox(),
				routineInspectionInterval, "Edit Project - Routine Inspection Interval");

		// Additional Notes
		Assert.assertEquals(projectInspectionsMainPage.getEditProjectModal().readAdditionalNotesTextbox(),
				additionalNotes, "Edit Project - Additional Notes");

		// Regulations
		Assert.assertEquals(projectInspectionsMainPage.getEditProjectModal().readRegulationsDropdown(), regulations,
				"Edit Project - Regulations");

		/*
		 * CHECKBOXES
		 */
		Assert.assertEquals(projectInspectionsMainPage.getEditProjectModal().readActiveSiteMapCheckbox(),
				activeSiteMaps, "Edit Project - Checkbox - Active Site Maps");
		Assert.assertEquals(
				projectInspectionsMainPage.getEditProjectModal().readConstructionDocsCivilDrawingsCheckbox(),
				constructionDocsCivilDrawings, "Edit Project - Checkbox - Construction Docs/Civil Drawings");
		Assert.assertEquals(projectInspectionsMainPage.getEditProjectModal().readSoilReportsCheckbox(), soilReports,
				"Edit Project - Checkbox - Soil Reports ");
		Assert.assertEquals(projectInspectionsMainPage.getEditProjectModal().readDelegationLettersCheckbox(),
				delegationLetters, "Edit Project - Checkbox - Delegation Letters");
		Assert.assertEquals(projectInspectionsMainPage.getEditProjectModal().readEndangeredSpeciesCheckbox(),
				endangeredSpecies, "Edit Project - Checkbox - Endangered Species");
		Assert.assertEquals(projectInspectionsMainPage.getEditProjectModal().readHistoricalPropertiesCheckbox(),
				historicalProperties, "Edit Project - Checkbox - Historical Properties");
		Assert.assertEquals(projectInspectionsMainPage.getEditProjectModal().readPermitsCheckbox(), permits,
				"Edit Project - Checkbox - Permits ");
		Assert.assertEquals(projectInspectionsMainPage.getEditProjectModal().readQualificationsCertificationsCheckbox(),
				qualificationCertifications, "Edit Project - Checkbox - Qualifications/Certificaitons");
		Assert.assertEquals(projectInspectionsMainPage.getEditProjectModal().readLocalStormwaterRegulationsCheckbox(),
				localStormwaterRegulations, "Edit Project - Checkbox - Local Stormwater Regulations");
		Assert.assertEquals(projectInspectionsMainPage.getEditProjectModal().readMISCCheckbox(), misc,
				"Edit Project - Checkbox - MISC");
		Assert.assertEquals(projectInspectionsMainPage.getEditProjectModal().readSWPNarrativeMapsCheckbox(),
				swpNarrativeMaps, "Edit Project - Checkbox - SWP Narrative Maps");

		projectInspectionsMainPage.getEditProjectModal().clickCancelOnModal();

	}

	/**
	 * Utility method to delete a project that exists. This can happen because a
	 * test errors out and doesn't get cleaned up.
	 */
	private void deleteExistingProject() {

		// get a list of projects that match the projectName
		AutomationHelper.adjustWaitTimeToShort();
		List<WebElement> projects = driver
				.findElements(By.xpath("//h3[text()='" + projectName + "']//parent::div[@class='project-main']"));
		AutomationHelper.adjustWaitTimeToNormal();

		// You can't have more than one project of the same name
		if (projects.size() > 0) {

			ProjectPageFactory projectPage = new ProjectPageFactory();
			projectPage.clickProjectCard(projectName);

			// Note: If we hadn't logged in as an Area / Assistant-Manager, we would have
			// had to have logged in as this or an Admin to have the ability to delete the
			// project.

			ProjectInspectionsPageFactory inspectionsPage = new ProjectInspectionsPageFactory();
			inspectionsPage.clickDeleteMenu();

			inspectionsPage.getDeleteProjectModal().clickYesIUnderstand();
		}
	}

	/**
	 * Private utility method to set the fields on the Create New Project modal.
	 * 
	 * @param projectPage
	 */
	private void setCreateNewProjectFields(ProjectPageFactory projectPage) {

		// Client
		projectPage.getCreateNewProjectModal().selectClientDropdown(client);

		// Permittee
		projectPage.getCreateNewProjectModal().setPermitteeTextbox(permittee);

		// Inspection Form Type
		projectPage.getCreateNewProjectModal().selectInspectionFormTypeDropdown(inspectionFormType);

		// Project Name
		projectPage.getCreateNewProjectModal().setProjectNameTextbox(projectName);

		// Project Address
		projectPage.getCreateNewProjectModal().setAddressTextbox(address);

		// Project City
		projectPage.getCreateNewProjectModal().setCityTextbox(city);

		// Project State
		projectPage.getCreateNewProjectModal().selectStateDropdown(state);

		// Project Zip
		projectPage.getCreateNewProjectModal().setZipTextbox(zip);

		// Latitude
		projectPage.getCreateNewProjectModal().setLatitudeTextbox(latitude);

		// Longitude
		projectPage.getCreateNewProjectModal().setLongitudeTextbox(longitude);

		// Inspector
		projectPage.getCreateNewProjectModal().selectInspectorDropdown(inspector);

		// Anticipated Project Timeline Begin
		projectPage.getCreateNewProjectModal().setTimeLineStartDateTextbox(anticipateProjectTimelineBegin);

		// Anticipated Project Timeline End
		projectPage.getCreateNewProjectModal().setTimeLineEndDateTextbox(anticipateProjectTimelineEnd);

		// Next ROutine Inspection Date
		projectPage.getCreateNewProjectModal().setNextRoutineInspectionDateTextbox(nextRoutineInspectionDate);

		// Routine Inspection INterval
		projectPage.getCreateNewProjectModal().setRoutineInspectionIntervalTextbox(routineInspectionInterval);

		// Additional Notes
		projectPage.getCreateNewProjectModal().setAdditionalNotesTextbox(additionalNotes);

		// Regulations
		projectPage.getCreateNewProjectModal().selectRegulationsDropdown(regulations);

		/*
		 * CHECKBOXES
		 */
		projectPage.getCreateNewProjectModal().setActiveSiteMapCheckbox(activeSiteMaps);
		projectPage.getCreateNewProjectModal().setConstructionDocsCivilDrawingsCheckbox(constructionDocsCivilDrawings);
		projectPage.getCreateNewProjectModal().setSoilReportsCheckbox(soilReports);
		projectPage.getCreateNewProjectModal().setDelegationLettersCheckbox(delegationLetters);
		projectPage.getCreateNewProjectModal().setEndangeredSpeciesCheckbox(endangeredSpecies);
		projectPage.getCreateNewProjectModal().setHistoricalPropertiesCheckbox(historicalProperties);
		projectPage.getCreateNewProjectModal().setPermitsCheckbox(permits);
		projectPage.getCreateNewProjectModal().setQualificationsCertificationsCheckbox(qualificationCertifications);
		projectPage.getCreateNewProjectModal().setLocalStormwaterRegulationsCheckbox(localStormwaterRegulations);
		projectPage.getCreateNewProjectModal().setMISCCheckbox(misc);
		projectPage.getCreateNewProjectModal().setSWPNarrativeMapsCheckbox(swpNarrativeMaps);
	}

}
