package sw2QA;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class HelloWorld {

	public static void main(String[] args) {
		
		//Site URL
		String siteURL = "https://dev.sw2.net/";
		String userName = "jesse_inspector@gmail.com";
		String password = "password";
		
		System.setProperty("webdriver.chrome.driver", "G:\\My Drive\\Work Related\\SW2\\WebDrivers & Jars\\chromedriver_win32\\chromedriver.exe");
		
		WebDriver driver = new ChromeDriver();
		
		driver.get(siteURL);
		driver.manage().window().maximize();
		
		WebElement userNameField = driver.findElement(By.id("formGroup-email"));
		WebElement passwordField = driver.findElement(By.name("password"));
		WebElement loginButton = driver.findElement(By.xpath("//button[normalize-space()='Login']"));
		
		userNameField.sendKeys(userName);
		passwordField.sendKeys(password);
		loginButton.click();
		
		driver.close();
	
		
	
	}

}
