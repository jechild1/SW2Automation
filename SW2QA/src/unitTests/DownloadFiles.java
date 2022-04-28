package unitTests;

import static java.util.concurrent.TimeUnit.MINUTES;
import static org.awaitility.Awaitility.await;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DownloadFiles {

	static final String filePathForSave = "C:\\Users\\jesse\\git\\sw2-qa-automation\\sw2QA\\Application Report Files\\";

	public static void main(String[] args) {

		/*
		 * CHROME
		 */

		WebDriverManager.chromedriver().setup();
		ChromeOptions options = new ChromeOptions();

		HashMap<String, Object> prefs = new HashMap<>();
		prefs.put("plugins.always_open_pdf_externally", true);
		//To specify download fold
		prefs.put("download.default_directory", filePathForSave);
		options.setExperimentalOption("prefs", prefs);
		

		ChromeDriver driver = new ChromeDriver(options);
		driver.manage().window().maximize();

		/*
		 * EDGE
		 */
//		WebDriverManager.edgedriver().setup();
//		EdgeOptions options = new EdgeOptions();
//
//		HashMap<String, Object> prefs = new HashMap<>();
//		prefs.put("plugins.always_open_pdf_externally", true);
//		prefs.put("download.default_directory", filePathForSave);
//		// To specify download fold
//		options.setExperimentalOption("prefs", prefs);
//
//		WebDriver driver = new EdgeDriver(options);
//		driver.manage().window().maximize();

		/*
		 * FIREFOX
		 */
//		WebDriverManager.firefoxdriver().setup();
//		
//		FirefoxOptions options = new FirefoxOptions();
//		FirefoxProfile profile = new FirefoxProfile();
//		
//
//		
//		profile.setPreference("browser.download.folderList",2); //Use for the default download directory the last folder specified for a download
//		profile.setPreference("browser.download.dir", filePathForSave); //Set the last directory used for saving a file from the "What should (browser) do with this file?" dialog.
//		profile.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/pdf"); //list of MIME types to save to disk without asking what to use to open the file
//		profile.setPreference("pdfjs.disabled", true);  // disable the built-in PDF viewer
//
//		
//		options.setProfile(profile);
//		
//		WebDriver driver = new FirefoxDriver(options);
//		driver.manage().window().maximize();

		// _______________________

		driver.get("https://webbrowsertools.com/test-download-with/");

		driver.findElement(By.xpath("//button[text()='Got it!']")).click();

		WebElement docType = driver.findElement(By.xpath("//td[text()='sample.pdf']/following-sibling::td[3]/a"));
		
		String fileName = docType.getAttribute("href");
		fileName = fileName.substring(fileName.lastIndexOf("/")+1, fileName.length());


		docType.click();
		
		Path filePath = Paths.get(filePathForSave, fileName);
		await().atMost(2, MINUTES)
		        .ignoreExceptions()
		        .until(() -> filePath.toFile().exists());
		
		

		driver.quit();

	}
	
	

}
