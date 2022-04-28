package unitTests;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Dates {

	public static void main(String[] args) {
		
		System.out.println("Is Date Valid - 09-21-2022 1:45 PM: " + isDateFormatValid("MM-dd-yyyy h:mm a", "09-21-2022 2:52 PM" ));

		 String dateAndTimeFinished = "09-21-2022 1:45 PM";
		 
		 dateAndTimeFinished = tempFormatDate(dateAndTimeFinished);
		 
		 System.out.println(dateAndTimeFinished);
	}
	

	


	
	

//		System.out.println("isValid - dd/MM/yyyy with 20130925 = " + isDateFormatValid("dd/MM/yyyy", "20130925"));
//		System.out.println("isValid - dd/MM/yyyy with 25/09/2013 = " + isDateFormatValid("dd/MM/yyyy", "25/09/2013"));
//		System.out.println("isValid - dd/MM/yyyy with 25/09/2013 12:13:50 = "+ isDateFormatValid("dd/MM/yyyy", "25/09/2013  12:13:50"));
//		
//		System.out.println("isValid - MM/dd/yyyy with 12/15/2021 = "+ isDateFormatValid("MM/dd/yyyy", "12/15/2021"));
//		System.out.println("isValid - MM-dd-yyyy with 12-15-2021 = "+ isDateFormatValid("MM-dd-yyyy", "12-15-2021"));
//		System.out.println("isValid - MM-dd-yyyy h:mm a with 12-15-2021 02:59 PM = "+ isDateFormatValid("MM-dd-yyyy h:mm a", "12-15-2021 2:00 PM"));
//		System.out.println("isValid - MMM-dd-yyyy h:mm a with September 28, 2021 2:00 AM = "+ isDateFormatValid("MMMM dd, yyyy h:mm a", "September 28, 2021 2:00 AM"));
//
//		
//		
//
//	}
//
	/**
	 * Method to ensure that a valid date is returned in the expected format. This
	 * method can throw a Parse Exception if dates are not recognizable.
	 * 
	 * @param dateFormat - e.g. MM/dd/yyyy or MM-dd-yyyy
	 * @param dateValue  - e.g. 12/15/2021
	 * @return true or false
	 */
	public static boolean isDateFormatValid(String dateFormat, String dateValue) {
		Date date = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
			date = sdf.parse(dateValue);
			if (!dateValue.equals(sdf.format(date))) {
				date = null;
			}
		} catch (ParseException ex) {
			ex.printStackTrace();
		}
		return date != null;
	}
	
	public static String tempFormatDate(String dateAndTimeFinished) {
		SimpleDateFormat input = new SimpleDateFormat("MM-dd-yyyy h:mm a");
		Date dateValue;
		try {
			dateValue = input.parse(dateAndTimeFinished);
			SimpleDateFormat output = new SimpleDateFormat("MMMMM dd, yyyy h:mm a");
			dateAndTimeFinished = output.format(dateValue);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return dateAndTimeFinished;
		
	}

}


