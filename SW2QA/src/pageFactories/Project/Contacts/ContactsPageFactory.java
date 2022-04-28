package pageFactories.Project.Contacts;

import pageFactories.Project.Inspections.ProjectInspectionsMainPageFactory;

/**
 * Page factory for the Project page > Inspections > Contacts for SW2.<br>
 * (1) Finds a reference to objects on the page <br>
 * (2) Defines methods to interact with objects on the page.<br>
 * 
 * @author Jesse Childress
 *
 */
public class ContactsPageFactory extends ProjectInspectionsMainPageFactory{

	public static String regexURL = BASE_URL + "divisions" + ".*" + "projects" + ".*" + "contacts";

	/**
	 * Page Constructor - Calls constructor in MenusPageFactory.
	 */
	public ContactsPageFactory() {
		super(regexURL);
	}
}
