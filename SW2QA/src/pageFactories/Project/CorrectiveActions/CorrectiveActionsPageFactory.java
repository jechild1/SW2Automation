package pageFactories.Project.CorrectiveActions;

import pageFactories.Project.Inspections.SW2ProjectInspectionsMainPageFactory;

/**
 * Page factory for the Project page > Inspections > Corrective Actions
 * (findings) for SW2.<br>
 * (1) Finds a reference to objects on the page <br>
 * (2) Defines methods to interact with objects on the page.<br>
 * 
 * @author Jesse Childress
 *
 */
public class CorrectiveActionsPageFactory extends SW2ProjectInspectionsMainPageFactory {

	public static String regexURL = BASE_URL + "divisions" + ".*" + "projects" + ".*" + "findings";

	/**
	 * Page Constructor - Calls constructor in MenusPageFactory.
	 */
	public CorrectiveActionsPageFactory() {
		super(regexURL);
	}
}
