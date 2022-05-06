package pageFactories.Clients;

import pageFactories.SW2MenusPageFactory;

/**
 * This is an abstract class that will provide access to common methods for the
 * Clients pages.
 * 
 * @author Jesse Childress
 *
 */
public abstract class SW2ClientsMain extends SW2MenusPageFactory {


	/**
	 * Page Constructor - Calls constructor in MenusPageFactory.
	 */
	public SW2ClientsMain(String regexURL) {
		super(regexURL);

	}

}
