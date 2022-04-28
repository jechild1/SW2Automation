package testCases.BulkCreationsAndUploads;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pageFactories.DashboardPageFactory;
import pageFactories.Clients.ClientContactsPageFactory;
import pageFactories.Clients.ClientPageFactory;
import testCases.BaseTestScriptConfig;
import testCases.modularTests.LoginMod;
import utilities.ExcelDataConfig;

/**
 * Test to add a new project(s) to a client.
 * 
 * This test script is designed to populate a list of projects in a loop, until
 * they are all completed. <br>
 * Note:
 * 
 * <li>Ensure that the correct USER PROFILE adds the project. This is because
 * that user will be a part of this client.
 * <li>Ensure that you pass in a correct DIVISION - The Client will be part of
 * this division
 * <li>Ensure that the datasheet name / Worksheet are correct and that there is
 * a column for Data Used
 * <li>Comment out the DELETE ME section of the code
 * 
 * 
 * @author Jesse Childress
 *
 */
public class AddNewClients extends BaseTestScriptConfig {

	@Test(dataProvider = "clients")
	public void test(String clientName) {

		// Set Datasheet up first to get the correct Division.
		ExcelDataConfig clientDatasheet = getExcelFile("Project Set Up - Silver Leaf.xlsx", "Client Set Up");
		int row = clientDatasheet.getRowIndex("Client Name", clientName);

		String division = clientDatasheet.getData(row, "Division");

		/*
		 * LOGIN
		 */
		// Login as a role that can create a project
		LoginMod login = new LoginMod("Admin");

		if (division.contains(",") || division.equals("")) {// if there is a comma, indicating multiple divisions OR if
															// there is NO division, enter default division in CONFIG
			login.login(DIVISION);

		} else {
			login.login(division);
		}

		/*
		 * DASHBOARDS PAGE
		 */
		DashboardPageFactory dashboardPage = new DashboardPageFactory();
		dashboardPage.clickClient();

		/*
		 * CLIENTS PAGE
		 */
		ClientPageFactory clientPage = new ClientPageFactory();

		// Ensure that the client isn't already in the list
		// NOTE: If it is the first time the client has been created, there is NO table.
		Assert.assertEquals(clientPage.getClientsTable().isRowInTableByValue("Name", clientName), false,
				"Client Table - Client Exists");

		/*
		 * CLIENTS PAGE - GET DATASHEET AND SET VARIABLES.
		 */

		// Variables
		// clientName is passed in as data provider
		String phone = clientDatasheet.getData(row, "Phone");
		String address1 = clientDatasheet.getData(row, "Address 1");
		String address2 = clientDatasheet.getData(row, "Address 2");
		String city = clientDatasheet.getData(row, "City");
		String state = clientDatasheet.getData(row, "State");
		String zip = clientDatasheet.getData(row, "Zip");
		String defaultFindingObservation = clientDatasheet.getData(row, "Default Finding Observation");

		/*
		 * CLIENTS PAGE - Add the client
		 * 
		 */

		clientPage.clickAddClient(); // Create New Client Modal Displayed

		// Set the values of the modal
		clientPage.getCreateNewClientModal().setClientName(clientName);
		clientPage.getCreateNewClientModal().setPhone(phone);
		clientPage.getCreateNewClientModal().setAddress1(address1);
		clientPage.getCreateNewClientModal().setAddress2(address2);
		clientPage.getCreateNewClientModal().setCity(city);
		clientPage.getCreateNewClientModal().selectState(state);
		clientPage.getCreateNewClientModal().setZip(zip);
		clientPage.getCreateNewClientModal().setDefaultFinding(defaultFindingObservation);

		// Save the values
		clientPage.getCreateNewClientModal().clickOKOnModal();

		// The modal disappears and the client contacts page is displayed.
		ClientContactsPageFactory clientContactsPage = new ClientContactsPageFactory();

		// Lets ensure that the data file "Data Used" gets updated to reflect successful
		// addition.
		clientDatasheet.writeToWorkSheet(row, "Data Used", "TRUE");
		Reporter.log("Datasheet value 'Data Used' for " + clientName + " set to TRUE", true);

		// Assert that at least the client name is correct.
		// Other asserts can be created when
		Assert.assertEquals(clientContactsPage.readClientName(), clientName, "Client Contacts Page - Client Name");
		// TODO - Add other asserts when Ashray gets it fixed

		/*
		 * CLIENT PAGE - Ensure values saved by looking at modal after commit.
		 */
		clientContactsPage.clickEdit();

		clientPage = new ClientPageFactory();

		Assert.assertEquals(clientPage.getEditClientModal().readClientName(), clientName,
				"Edit Client Modal - Client Name");
		Assert.assertEquals(clientPage.getEditClientModal().readPhone().replace("-", "").replace("(", "")
				.replace(")", "").replace(" ", ""), phone.replace("-", ""), "Edit Client Modal - Phone");
		Assert.assertEquals(clientPage.getEditClientModal().readAddress1(), address1, "Edit Client Modal - Address 1");
		Assert.assertEquals(clientPage.getEditClientModal().readAddress2(), address2, "Edit Client Modal - Address 2");
		Assert.assertEquals(clientPage.getEditClientModal().readCity(), city, "Edit Client Modal - City");
		Assert.assertEquals(clientPage.getEditClientModal().readState(), state, "Edit Client Modal - State");
		Assert.assertEquals(clientPage.getEditClientModal().readZip(), zip, "Edit Client Modal - Zip");
		Assert.assertEquals(clientPage.getEditClientModal().readDefaultFinding(), defaultFindingObservation,
				"Edit Client Modal - Default Finding Observation");

		// Click Cancel on the edit
		clientPage.getEditClientModal().clickCancel();

		// NOTHING ELSE NEEDS TO BE DONE HERE
		// We can be done and let the data provider send in another value.

		clientPage.clickLogOut();

//		/*
//		 * TEMP DELETE
//		 */
//
//		//Back on the Client Contacts page
//		clientContactsPage = new ClientContactsPageFactory();		
//
//		// Clicks Delete in the menu
//		clientContactsPage.clickDelete();
//
//		// The Delete Project menu is displayed
//		// Check the title for accuracy
//		Assert.assertEquals(clientContactsPage.getDeleteClientPopup().readPopupTitle(),
//				"Are you sure you want to delete " + clientName + "?", "Delete Popup - Popup Title");
//
//		// Check the modal message for accuracy
//		String popupMessage = "All associated projects and contacts will also be deleted.";
//		Assert.assertEquals(clientContactsPage.getDeleteClientPopup().readPopupBodyText(), popupMessage,
//				"Delete Popup - Popup Message");
//
//		// Delete the client
//		clientContactsPage.getDeleteClientPopup().clickDeleteClient();
//
//		// Back on the Projects page. Ensure that it is gone
////		AutomationHelper.wait(4);
//		clientPage = new ClientPageFactory();
//
//		Assert.assertEquals(clientPage.getClientsTable().isRowInTableByValue("Name", clientName), false,
//				"Clients Page- Newly created client has been deleted");
//
//		// If the project was successfully removed, we need to mark it as such in the
//		// datasheet.
//		clientDatasheet.writeToWorkSheet(row, "Data Used", "FALSE");
//		Reporter.log("Datasheet value 'Data Used' for " + clientName + " set to FALSE", true);

	}

	/**
	 * Data provider to get all of the users for a given worksheet.
	 * 
	 * @return
	 */
	@DataProvider(name = "clients")
	private String[] getClientsList() {

		ExcelDataConfig clientDatasheet = getExcelFile("Project Set Up - Silver Leaf.xlsx", "Client Set Up");

		// Get the row count
		int rowCount = clientDatasheet.getRowCount();

		List<String> clientList = new ArrayList<String>();

		if (rowCount >= 1) {

			for (int i = 1; i <= rowCount; i++) {

				// get each individual line of the datasheet
				// add only if the data hasn't been used
				if (clientDatasheet.getData(i, "Data Used").equalsIgnoreCase("FALSE")) {
					clientList.add(clientDatasheet.getData(i, "Client Name"));
				}
			}

			if ((clientList.size() < 1)) {

				Reporter.log("There are no values in the Clients.xlsx worksheet that have not already been added",
						true);
				System.exit(1);
			}
		}

		return clientList.stream().toArray(String[]::new);
	}

}
