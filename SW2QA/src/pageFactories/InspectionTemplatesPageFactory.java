package pageFactories;
/**
 * Page factory for the Inspection Templates page for SW2.<br>
 * (1) Finds a reference to objects on the page <br>
 * (2) Defines methods to interact with objects on the page.<br>
 * 
 * @author Jesse Childress
 *
 */
public class InspectionTemplatesPageFactory extends SW2MenusPageFactory{
	
	public static String regexURL = BASE_URL + ".*" + "inspection-templates";

	/**
	 * Page Constructor - Calls constructor in MenusPageFactory.
	 */
	public InspectionTemplatesPageFactory() {
		super(regexURL);
		waitForPageToLoad();
	}

}
