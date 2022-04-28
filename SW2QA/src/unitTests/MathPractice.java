package unitTests;

import java.time.Duration;

import utilities.AutomationHelper;

public class MathPractice {

	public static void main(String[] args) {
		
		
		long timeInMillis = 9999;
		
		System.out.println(timeInMillis / 1000);
		
		System.out.println(Duration.ofMillis(timeInMillis)); 
		
//		long digit = (long)timeInMillis;
		
		try {
			Thread.sleep((long) timeInMillis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		
		
//		int min = 0;
//		int max = 5;
//		
//	int i = 0;
//	
//	while(i < 100) {
//		
//		
//		int randomNumber = AutomationHelper.generateRandomInteger(min, max);
//		
//		
////			int randomNumber = (int) (Math.random() * (max - min + min) + 1);
////
////		
//		System.out.println("Random Number " + i + ": " +  randomNumber);
//		
//		i++;
//		
//	}
	
	

	}

}
