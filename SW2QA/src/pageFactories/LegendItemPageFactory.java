package pageFactories;
/**
 * Page factory for the Legend Item page for SW2.<br>
 * (1) Finds a reference to objects on the page <br>
 * (2) Defines methods to interact with objects on the page.<br>
 * 
 * @author Jesse Childress
 *
 */
public class LegendItemPageFactory extends MenusPageFactory{
	
	public static String regexURL = BASE_URL + ".*" + "map-legend";

	/**
	 * Page Constructor - Calls constructor in MenusPageFactory.
	 */
	public LegendItemPageFactory() {
		super(regexURL);
		waitForPageToLoad();
	}

}
