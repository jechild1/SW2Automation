package testCases.project;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import pageFactories.DashboardPageFactory;
import pageFactories.Project.ProjectPageFactory;
import pageFactories.Project.Inspections.ProjectInspectionsPageFactory;
import testCases.BaseTestScriptConfig;
import testCases.modularTests.LoginMod;
import utilities.AutomationHelper;
import utilities.ExcelDataConfig;

/**
 * Test to add a new project to a client. Then to Edit that project, and
 * finally, delete that same project. <br>
 * This test will exercise the Add Project modal. It will:<br>
 * <li>Ensure that the project is not currently created
 * <li>Ensure that a project can be added successfully
 * <li>Ensure that a newly created project can be found (has a project card)
 * <li>Ensure that the newly created project saved the values correctly via Edit
 * functionality
 * <li>Ensure that a project can be deleted
 * <li>Ensure that a deleted project can no longer be seen as a project card.
 * 
 * @author Jesse Childress
 *
 */
public class Project_AddAndDeleteNewProject extends BaseTestScriptConfig {

	@Test
	public void addNewProject() {

		/*
		 * Login
		 */
		// Login as a role that can create a project
		LoginMod login = new LoginMod("Area / Assistant Manager");
		login.login(DIVISION);

		// Dashboards Page
		DashboardPageFactory dashboardPage = new DashboardPageFactory();
		dashboardPage.clickProject();

		// Project Page
		ProjectPageFactory projectPage = new ProjectPageFactory();

		/*
		 * Obtain the data and set the variables
		 */

		// Gather all of the information for the project to add. Ensure that it is not
		// currently added.
		ExcelDataConfig projectsFile = getExcelFile("Projects.xlsx", "ProjectsForDeletion");

		// Utility method to Assign data values to variables.
		assignDataValues(projectsFile);

		/*
		 * Delete project if it currently exists
		 */
		// Currently on the Project page
		projectPage.setSearchProjects(projectName);
		Reporter.log("Beginning check for existing project on the page. Will delete if present.", true);

		deleteExistingProject();

		// Get a new reference to the Projects Page
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
		AssertProjectFields(projectInspectionsMainPage);

		/*
		 * Delete the project to keep a clean database
		 */

		// Clicks Delete in the menu
		projectInspectionsMainPage.clickDeleteMenu();

		// The Delete Project menu is displayed
		// Check the title for accuracy
		Assert.assertEquals(projectInspectionsMainPage.getDeleteProjectModal().readModalTitle(),
				"Are you sure you want to delete this project?", "Delete Modal - Modal Title");

		// Check the modal message for accuracy
		String modalMessage = "All documents and contacts associated with " + projectName + " will be deleted.";
		Assert.assertEquals(projectInspectionsMainPage.getDeleteProjectModal().readModalBodyText(), modalMessage,
				"Delete Modal - Modal Message");

		projectInspectionsMainPage.getDeleteProjectModal().clickYesIUnderstand();

		// Back on the Projects page. Ensure that it is gone
		projectPage = new ProjectPageFactory();

		Assert.assertEquals(projectPage.isProjectCardPresent(projectName), false,
				"Project Page - Newly created project has been deleted");
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

	/**
	 * Utility method that performs asserts on the Project page to ensure that the
	 * changes stick. It ensures a commit by clicking "Edit" on the project. This
	 * loads the modal with the data in it.
	 * 
	 * @param projectInspectionsMainPage
	 */
	private void AssertProjectFields(ProjectInspectionsPageFactory projectInspectionsMainPage) {
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

	private void assignDataValues(ExcelDataConfig projectsFile) {

		// Find a random row for projects where the data used is true.
		int rowIndex = projectsFile.getNextUnusedDataRowIndex();

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

}
