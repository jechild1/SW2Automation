package pageFactories.Project.Log;

import pageFactories.Project.Inspections.ProjectInspectionsMainPageFactory;

/**
 * Page factory for the Project page > Inspections > Log for SW2.<br>
 * (1) Finds a reference to objects on the page <br>
 * (2) Defines methods to interact with objects on the page.<br>
 * 
 * @author Jesse Childress
 *
 */
public class LogPageFactory extends ProjectInspectionsMainPageFactory{

	public static String regexURL = BASE_URL + "divisions" + ".*" + "projects" + ".*" + "log";

	/**
	 * Page Constructor - Calls constructor in MenusPageFactory.
	 */
	public LogPageFactory() {
		super(regexURL);
	}
}
