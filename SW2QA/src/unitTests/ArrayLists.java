package unitTests;

public class ArrayLists {

	public static void main(String[] args) {
		
		String dataSheetValues = "folder1//test1.jpg, folder2//test2.pdf,folder3//test3.xml";
//		String dataSheetValues = "folder1//test1.jpg";
		
		String[] files = dataSheetValues.split(",");
		
		for(int i = 0; i<files.length; i++) {
			files[i] = files[i].trim();
		}
		

		
		System.out.println("TEST");

		
		
	}

}
