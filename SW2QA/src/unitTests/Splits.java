package unitTests;

import java.util.ArrayList;
import java.util.List;

public class Splits {
	public static void main(String[] args) {
		
//		String text = "10-13-2021 | Routine";
//		
//		String[] splitText = text.split("\\|");
//		
//		System.out.println(splitText[1].trim());
		
//		String inspectionText = "09-20-2021 | Post Storm\r\n"
//				+ "Inspection Questions | Complete\r\n"
//				+ "Certification | Complete\r\n"
//				+ "Compliance | Complete\r\n"
//				+ "Inspection Number | 8\r\n"
//				+ "Corrective Actions\r\n"
//				+ "1\r\n"
//				+ "Maintenance Items\r\n"
//				+ "0";
//		
//		System.out.println(inspectionText.substring(inspectionText.indexOf("|") , inspectionText.indexOf("\n") +1));
		
//		String text = "#11 | 09-28-2021 | Post Storm";
//		System.out.println(text.substring(text.indexOf("#")+1, text.indexOf(" ")));
//		System.out.println(text.substring(text.indexOf("|")+2, text.lastIndexOf("|")-1));
//		System.out.println(text.substring(text.lastIndexOf("|")+2, text.length()));
		
//		String filePath = "test//123.jpg";
//		
//		System.out.println(filePath.substring(filePath.lastIndexOf("/")+1, filePath.length()));
		
//		String monthAndYearInCalHeader = "Janurary 2022";
//		
//		String[] monthYear = monthAndYearInCalHeader.split(" ");
//		
//		String calMonth = monthYear[0];
//		String calYear = monthYear[1];
//		
//		System.out.println(calMonth);
//		System.out.println(calYear);
		
//		String dateAndTime = "12-01-2025 1:14 PM";
//		
//		String date = dateAndTime.substring(0, dateAndTime.indexOf(" "));
//		String time = dateAndTime.substring(dateAndTime.indexOf(" ")+1, dateAndTime.length());
//		
//		System.out.println(date);
//		System.out.println(time);
//		
//		String minutes = time.substring(time.indexOf(":")+1, time.indexOf(":")+3);
//		
//		if (!Stream.of("00", "15", "30", "45").anyMatch(minutes::equalsIgnoreCase)) {
//		    System.out.println("No match");
//		}else {
//			System.out.println("Match");
//		}
		
		
		
		
//		String [] splitString = dateAndTime.split("\\s+");
//		String [] splitString = dateAndTime.split(dateAndTime.indexOf(":")-1);
//		
//		for(String str: splitString) {
//			System.out.println(str);
//		}

//		if(dateAndTime.endsWith("AM") || dateAndTime.endsWith("PM") || dateAndTime.endsWith("am") || dateAndTime.endsWith("pm")) {
//			date = date.substring(0, date.indexOf(" "));
//			time = date.substring(date.indexOf(" ")+1, date.length());	
//		}
		
//		String filePath = "example-finding-images//DSC_01112.jpg, example-finding-images//DSC_0137.jpg, example-finding-images//DSC_0138.jpg";
//		
//		String[] splitString = filePath.split(",");
//		
//		for(int i =0; i< splitString.length; i++) {
//			System.out.println(splitString[i]);
//		}
//		
//		List<String> fileNames = new ArrayList<String>();
//		for(String currentFilePath : splitString) {
//			fileNames.add(currentFilePath.substring(currentFilePath.indexOf("//")+2,currentFilePath.length()));
//		}
//		
//		for(String thisFileName: fileNames) {
//			System.out.println(thisFileName);
//		}
		
		String input = "SQA Test Template for Certification";
	
		
		if(input.length() > 30) {
			input = input.substring(0, 30+1);
		}
		
		System.out.println(input);
			
		
		
		
		
		
		
		
		
		
		

		

	}

}
