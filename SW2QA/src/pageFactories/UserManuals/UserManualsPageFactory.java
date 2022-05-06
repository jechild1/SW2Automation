package pageFactories.UserManuals;

import pageFactories.SW2MenusPageFactory;

/**
 * Page factory for the User Manuals page for SW2.<br>
 * (1) Finds a reference to objects on the page <br>
 * (2) Defines methods to interact with objects on the page.<br>
 * 
 * @author Jesse Childress
 *
 */
public class UserManualsPageFactory extends SW2MenusPageFactory {

	public static String regexURL = BASE_URL + "user-manuals";

	/**
	 * Page Constructor - Calls constructor in MenusPageFactory.
	 */
	public UserManualsPageFactory() {
		super(regexURL);
	}
	
}
