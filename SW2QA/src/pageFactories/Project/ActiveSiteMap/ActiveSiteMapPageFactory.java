package pageFactories.Project.ActiveSiteMap;

import pageFactories.Project.Inspections.ProjectInspectionsMainPageFactory;

/**
 * Page factory for the Project page > Inspections > Active Site Map for
 * SW2.<br>
 * (1) Finds a reference to objects on the page <br>
 * (2) Defines methods to interact with objects on the page.<br>
 * 
 * @author Jesse Childress
 *
 */
public class ActiveSiteMapPageFactory extends ProjectInspectionsMainPageFactory {

	public static String regexURL = BASE_URL + "divisions" + ".*" + "projects" + ".*" + "active-site-map";

	/**
	 * Page Constructor - Calls constructor in MenusPageFactory.
	 */
	public ActiveSiteMapPageFactory() {
		super(regexURL);
	}
}
