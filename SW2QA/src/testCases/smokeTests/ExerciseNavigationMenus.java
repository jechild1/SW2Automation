package testCases.smokeTests;

import org.openqa.selenium.ElementNotInteractableException;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pageFactories.AdminPageFactory;
import pageFactories.DashboardPageFactory;
import pageFactories.InspectionTemplatesPageFactory;
import pageFactories.LegendItemPageFactory;
import pageFactories.Clients.ClientPageFactory;
import pageFactories.Divisions.DivisionsPageFactory;
import pageFactories.Project.ProjectPageFactory;
import pageFactories.Regulations.RegulationsPageFactory;
import pageFactories.UserManuals.UserManualsPageFactory;
import testCases.BaseTestScriptConfig;
import testCases.modularTests.LoginMod;

/**
 * This test will log in as ALL of the different user types within the
 * users.xlsx data sheet. Within each user type, it will assert that the pages
 * exist for that specific role. More specifically, it will assert that certain
 * pages are NOT present when they shouldn't be.
 * 
 * @author Jesse Childress
 *
 */
public class ExerciseNavigationMenus extends BaseTestScriptConfig {
	public static String USER_ROLE;

	@Test(dataProvider = "loginAccounts")
	public void validateMenuNavigation(String emailAddress, String password) {

		// Login as a role that can create a project
		LoginMod login = new LoginMod(emailAddress, password);
		login.loginUserWithAccountCredentials();
		USER_ROLE = login.getUserRole();

		// 14 roles

		switch (USER_ROLE) {

		case "Admin":
			adminMenuSelections();
			break;
		case "Area / Assistant Manager":
			areaAssistantManagerMenuSelections();
			break;
		case "BMP Contractor":
			bmpContractorMenuSelections();
			break;
		case "CDOT PE/Designee":
			cdotPEDesigneeMenuSelections();
			break;
		case "Client Inspector":
			clientInspectorMenuSelections();
			break;
		case "Client User":
			clientUserMenuSelections();
			break;
		case "Divisions Admin":
			divisionsAdminMenuSelections();
			break;
		case "Engineering Admin":
			engineeringAdminMenuSelections();
			break;
		case "Inspector":
			inspectorMenuSelections();
			break;
		case "Regional Manager":
			regionalManagerMenuSelections();
			break;
		case "Regional Manager (External)":
			regionalManagerExternalMenuSelections();
			break;
		case "Regulator":
			regulatorMenuSelections();
			break;
		case "Supervising Engineer":
			supervisingEngineerMenuSelections();
			break;
		case "SWMP Admin":
			swmpAdminMenuSelections();
			break;

		default:
			throw new ElementNotInteractableException("There are no roles that match " + USER_ROLE);

		}
	}

	/**
	 * Utility method to handle validating all menus that an <b>Admin</b> role would
	 * see.
	 */
	private void adminMenuSelections() {
		// Calling the methods that perform the clicks and navigation.
		Page_Dashboard();
		Page_Client();
		Page_Project();
		Page_Regulations();
		Page_LegendItem();
		Page_Admin();
		Page_Divisions();
		Page_InspectionTemplates();
		Page_UserManuals();
	}

	/**
	 * Utility method to handle validating all menus that an <b>Area / Assistant
	 * Manager</b> role would see.
	 */
	private void areaAssistantManagerMenuSelections() {
		// Calling the methods that perform the clicks and navigation.
		Page_Dashboard();
		Page_Client();
		Page_Project();
		Page_Regulations();
		Page_LegendItem();
		Page_Admin();
		Page_Divisions();
		Page_InspectionTemplates();
		Page_UserManuals();

	}

	/**
	 * Utility method to handle validating all menus that an <b>BMP Contractor</b>
	 * role would see.
	 */
	private void bmpContractorMenuSelections() {
		// Calling the methods that perform the clicks and navigation.
		Page_Dashboard();
		Page_Client();
		Page_Project();
		Page_Regulations();
		// Legend Item
		Assert.assertEquals(isPageLinkPresent("Legend Item"), false, "BMP Contractor - Legend Item Not Present");

		// Admin
		Assert.assertEquals(isPageLinkPresent("Admin"), false, "BMP Contractor - Admin Not Present");

		Page_Divisions();
		Page_InspectionTemplates();
		Page_UserManuals();

	}

	/**
	 * Utility method to handle validating all menus that an <b>CDOT PE/Designee</b>
	 * role would see.
	 */
	private void cdotPEDesigneeMenuSelections() {
		// Calling the methods that perform the clicks and navigation.
		Page_Dashboard();
		Page_Client();
		Page_Project();
		Page_Regulations();
		Page_LegendItem();

		// Admin
		Assert.assertEquals(isPageLinkPresent("Admin"), false, "BMP Contractor - CDOT/PE Designee Not Present");

		Page_Divisions();
		Page_InspectionTemplates();
		Page_UserManuals();

	}

	/**
	 * Utility method to handle validating all menus that an <b>Client Inspector</b>
	 * role would see.
	 */
	private void clientInspectorMenuSelections() {
		// Calling the methods that perform the clicks and navigation.
		Page_Dashboard();
		Page_Client();
		Page_Project();
		Page_Regulations();
		Page_LegendItem();

		// Admin
		Assert.assertEquals(isPageLinkPresent("Admin"), false, "Client Inspector - Admin Not Present");

		Page_Divisions();
		Page_InspectionTemplates();
		Page_UserManuals();

	}

	/**
	 * Utility method to handle validating all menus that an <b>Client User</b> role
	 * would see.
	 */
	private void clientUserMenuSelections() {
		// Calling the methods that perform the clicks and navigation.
		Page_Dashboard();
		Page_Client();
		Page_Project();
		Page_Regulations();

		// Legend Item
		Assert.assertEquals(isPageLinkPresent("Legend Item"), false, "Client User - Legend Item Not Present");

		// Admin
		Assert.assertEquals(isPageLinkPresent("Admin"), false, "Client User - Admin Not Present");

		Page_Divisions();
		Page_InspectionTemplates();
		Page_UserManuals();

	}

	/**
	 * Utility method to handle validating all menus that an <b>Divisions Admin</b>
	 * role would see.
	 */
	private void divisionsAdminMenuSelections() {
		// Calling the methods that perform the clicks and navigation.
		Page_Dashboard();
		Page_Client();
		Page_Project();
		Page_Regulations();
		Page_LegendItem();
		Page_Admin();
		Page_Divisions();
		Page_InspectionTemplates();
		Page_UserManuals();

	}

	/**
	 * Utility method to handle validating all menus that an <b>Engineering
	 * Admin</b> role would see.
	 */
	private void engineeringAdminMenuSelections() {
		// Calling the methods that perform the clicks and navigation.
		Page_Dashboard();
		Page_Client();
		Page_Project();
		Page_Regulations();
		Page_LegendItem();
		Page_Admin();
		Page_Divisions();
		Page_InspectionTemplates();
		Page_UserManuals();

	}

	/**
	 * Utility method to handle validating all menus that an <b>Inspector</b> role
	 * would see.
	 */
	private void inspectorMenuSelections() {
		// Calling the methods that perform the clicks and navigation.
		Page_Dashboard();
		Page_Client();
		Page_Project();
		Page_Regulations();
		Page_LegendItem();
		Page_Admin();
		Page_Divisions();
		Page_InspectionTemplates();
		Page_UserManuals();

	}

	/**
	 * Utility method to handle validating all menus that an <b>Regional Manager</b>
	 * role would see.
	 */
	private void regionalManagerMenuSelections() {
		// Calling the methods that perform the clicks and navigation.
		Page_Dashboard();
		Page_Client();
		Page_Project();
		Page_Regulations();
		Page_LegendItem();
		Page_Admin();
		Page_Divisions();
		Page_InspectionTemplates();
		Page_UserManuals();

	}

	/**
	 * Utility method to handle validating all menus that an <b>Regional Manager
	 * (External)</b> role would see.
	 */
	private void regionalManagerExternalMenuSelections() {
		// Calling the methods that perform the clicks and navigation.
		Page_Dashboard();
		Page_Client();
		Page_Project();
		Page_Regulations();
		Page_LegendItem();

		// Admin
		Assert.assertEquals(isPageLinkPresent("Admin"), false, "Regional Manager - Admin Not Present");

		Page_Divisions();
		Page_InspectionTemplates();
		Page_UserManuals();

	}

	/**
	 * Utility method to handle validating all menus that an <b>Regulator</b> role
	 * would see.
	 */
	private void regulatorMenuSelections() {
		// Calling the methods that perform the clicks and navigation.
		Page_Dashboard();
		Page_Client();
		Page_Project();
		Page_Regulations();
		Page_LegendItem();

		// Admin
		Assert.assertEquals(isPageLinkPresent("Admin"), false, "Regional Manager - Admin Not Present");
		Page_Divisions();

		Page_InspectionTemplates();
		Page_UserManuals();

	}

	/**
	 * Utility method to handle validating all menus that an <b>Supervising
	 * Engineer</b> role would see.
	 */
	private void supervisingEngineerMenuSelections() {
		// Calling the methods that perform the clicks and navigation.
		Page_Dashboard();
		Page_Client();
		Page_Project();
		Page_Regulations();
		Page_LegendItem();
		Page_Admin();
		Page_Divisions();
		Page_InspectionTemplates();
		Page_UserManuals();

	}

	/**
	 * Utility method to handle validating all menus that an <b>SWMP Admin</b> role
	 * would see.
	 */
	private void swmpAdminMenuSelections() {
		// Calling the methods that perform the clicks and navigation.
		Page_Dashboard();
		Page_Client();
		Page_Project();
		Page_Regulations();

		// Legend Item
		Assert.assertEquals(isPageLinkPresent("Legend Item"), false, "SWMP Admin - Legend Item Not Present");

		// Admin
		Assert.assertEquals(isPageLinkPresent("Admin"), false, "SWMP Admin - Admin Not Present");

		Page_Divisions();
		Page_InspectionTemplates();
		Page_UserManuals();

	}

	/**
	 * Private method to assert Dashboard page is loaded
	 */
	private void Page_Dashboard() {

//		DashboardPageFactory dashboardsPage = new DashboardPageFactory();
		clickHome();
		// There is no breadcrumb to check here for the Dashboards page, so nothing ot
		// check
	}

	/**
	 * Private method to assert Client page is loaded
	 */
	private void Page_Client() {

		// Start from the dashboard
		DashboardPageFactory dashboardPage = new DashboardPageFactory();
		dashboardPage.clickClient();

		ClientPageFactory clientPage = new ClientPageFactory();
		Assert.assertEquals(clientPage.readPageTitleFromBreadcrumb(), "Clients");

		clickHome();
	}

	/**
	 * Private method to assert Project page is loaded
	 */
	private void Page_Project() {

		// Start from the dashboard
		DashboardPageFactory dashboardPage = new DashboardPageFactory();
		dashboardPage.clickProject();

		ProjectPageFactory projectPage = new ProjectPageFactory();
		Assert.assertEquals(projectPage.readPageTitleFromBreadcrumb(), "Projects");

		clickHome();
	}

	/**
	 * Private method to assert Regulations page is loaded
	 */
	private void Page_Regulations() {

		// Start from the dashboard
		DashboardPageFactory dashboardPage = new DashboardPageFactory();
		dashboardPage.clickRegulations();

		RegulationsPageFactory regulationsPage = new RegulationsPageFactory();
		Assert.assertEquals(regulationsPage.readPageTitleFromBreadcrumb(), "Regulations");

		clickHome();
	}

	/**
	 * Private method to assert Legend Item page is loaded
	 */
	private void Page_LegendItem() {

		// Start from the dashboard
		DashboardPageFactory dashboardPage = new DashboardPageFactory();
		dashboardPage.clickLegendItem();

		LegendItemPageFactory legendItemPage = new LegendItemPageFactory();
		Assert.assertEquals(legendItemPage.readPageTitleFromBreadcrumb(), "Legend Items");

		clickHome();
	}

	/**
	 * Private method to assert Admin page is loaded
	 */
	private void Page_Admin() {

		// Start from the dashboard
		DashboardPageFactory dashboardPage = new DashboardPageFactory();
		dashboardPage.clickAdmin();

		AdminPageFactory adminPage = new AdminPageFactory();
		// Note: Ticket in to change "User" below to "Admin"
		Assert.assertEquals(adminPage.readPageTitleFromBreadcrumb(), "Users");

		clickHome();
	}

	/**
	 * Private method to assert Divisions page is loaded
	 */
	private void Page_Divisions() {

		// Start from the dashboard
		DashboardPageFactory dashboardPage = new DashboardPageFactory();
		dashboardPage.clickDivisions();

		DivisionsPageFactory divisionsPage = new DivisionsPageFactory();
		Assert.assertEquals(divisionsPage.readPageTitleFromBreadcrumb(), "Divisions");

		clickHome();
	}

	/**
	 * Private method to assert Inspection Templates page is loaded
	 */
	private void Page_InspectionTemplates() {

		// Start from the dashboard
		DashboardPageFactory dashboardPage = new DashboardPageFactory();
		dashboardPage.clickInspectionTemplates();

		/*
		 * Inspection Templates Page Loaded
		 */
		InspectionTemplatesPageFactory inspectionTemplatesPage = new InspectionTemplatesPageFactory();
		Assert.assertEquals(inspectionTemplatesPage.readPageTitleFromBreadcrumb(), "Inspection Templates");

		clickHome();

	}
	
	/**
	 * Private method to assert User Manuals page is loaded
	 */
	private void Page_UserManuals() {

		// Start from the dashboard
		DashboardPageFactory dashboardPage = new DashboardPageFactory();
		dashboardPage.clickUserManuals();

		/*
		 * User Manuals Page Loaded
		 */
		//TODO - Currently does NOT have a title. - SW2-484
		UserManualsPageFactory userManualsPage = new UserManualsPageFactory();
		Assert.assertEquals(userManualsPage.readPageTitleFromBreadcrumb(), "");

		clickHome();

	}

	/**
	 * Utility method to return ALL users in a String array for the users.xlsx
	 * datasheet.
	 * 
	 * @return String[][]
	 */
	@DataProvider(name = "loginAccounts")
	private String[][] getAllAccounts() {
		return getUserAndPassDataProviderFromExcel("users.xlsx", USERS_WORKSHEET);
	}

}
