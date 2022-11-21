package testscripts;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import base.PredefinedActions;
import pages.LoginPage;
import utility.PropertyFileOperations;

public class ScreenShotDemo extends PredefinedActions{
	
	@BeforeMethod
	public void setUp() throws IOException {
		PropertyFileOperations fileOperations = new PropertyFileOperations(".//config//settings.properties");
		String url = fileOperations.getValue("url");
		PredefinedActions.start(url);

		LoginPage loginPage = LoginPage.getObject();
		loginPage.login(fileOperations.getValue("username"), fileOperations.getValue("password"));
	}
	
	@Test
	public void m1() throws IOException {
		TakesScreenshot ts = (TakesScreenshot)driver; // type casted because driver need capabilities to take snippet
		File scrfile = ts.getScreenshotAs(OutputType.FILE); // Returns the file
		FileUtils.copyFile(scrfile, new File("./failedTestCases/1.jpg"));// where to copy file
	}
	
	@AfterMethod
	public void quitBrowser() {
		driver.quit();
	}
}
