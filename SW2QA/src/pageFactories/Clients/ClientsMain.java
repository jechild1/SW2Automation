package pageFactories.Clients;

import pageFactories.MenusPageFactory;

/**
 * This is an abstract class that will provide access to common methods for the
 * Clients pages.
 * 
 * @author Jesse Childress
 *
 */
public abstract class ClientsMain extends MenusPageFactory {


	/**
	 * Page Constructor - Calls constructor in MenusPageFactory.
	 */
	public ClientsMain(String regexURL) {
		super(regexURL);

	}

}
