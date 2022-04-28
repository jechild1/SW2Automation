package unitTests;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RemoveDuplicatesFromList {
	public static void main(String[] args) {
		
		 List<String> arrList = Arrays.asList("A", "B", "C", "D" , "A" , "C");

		    arrList.stream().distinct().collect(Collectors.toList()).forEach(System.out::println);
	}
}
