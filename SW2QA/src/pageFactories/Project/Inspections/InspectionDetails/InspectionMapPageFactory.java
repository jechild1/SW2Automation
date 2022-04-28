package pageFactories.Project.Inspections.InspectionDetails;

public class InspectionMapPageFactory extends InspectionDetailsMain {

	public static String regexURL = BASE_URL + ".*" + "inspection" + ".*" + "map";

	/**
	 * Page Constructor - Calls constructor in MenusPageFactory.
	 */
	public InspectionMapPageFactory() {
		super(regexURL);
	}



}
