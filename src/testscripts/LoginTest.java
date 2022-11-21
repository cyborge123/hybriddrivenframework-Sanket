package testscripts;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import base.PredefinedActions;
import constant.ConstantValue;
import pages.LoginPage;
import utility.ExcelOperations;

public class LoginTest {

	@Test(dataProvider = "LoginDataProvider")
	public void tc1(String url, String uname, String password, boolean isLoginSuccessful) {

		System.out.println("STEP - Launch Chrome Browser & Hit url");
		PredefinedActions.start(url);

		System.out.println("STEP - Enter valid login credentials");
		LoginPage loginPage = LoginPage.getObject();

		loginPage.login(uname, password);

		if (isLoginSuccessful) {
			System.out.println("VERIFY - Home page is displayed");
			String expetedTitle = "Employee Management";
			String actualTitle = loginPage.getPageTitle();
			Assert.assertEquals(actualTitle, expetedTitle,
					"Expected title was " + expetedTitle + " but actual title was " + actualTitle);
		} else {
			System.out.println("VERIFY - Home page is displayed");
			String expetedTitle = "OrangeHRM";
			String actualTitle = loginPage.getPageTitle();
			Assert.assertEquals(actualTitle, expetedTitle,
					"Expected title was " + expetedTitle + " but actual title was " + actualTitle);

			// ***Second check for negative scenario URL check
			System.out.println("VERIFY - Retry Page is loaded.");
			String expetedURLContent = "retryLogin";
			String actualCurrentURL = loginPage.getPageURL();
			Assert.assertTrue(actualCurrentURL.endsWith(expetedURLContent));
		}
	}

	// *** Test Data is provided thought Excel Sheet
	@DataProvider(name = "LoginDataProvider")
	public Object[][] getLoginData() throws IOException {
		Object[][] data;
		try {
			data = ExcelOperations.readExcelData(ConstantValue.LOGINDATA, "Data");
		} catch (IOException e) {
			data = ExcelOperations.readExcelData(".//testdata1//LoginData.xlsx", "Data");
		}
		return data;
	}

	@AfterMethod
	public void tearDown() {
		PredefinedActions.closeBrowser();
	}

	// *** To provide the all data to tc1 (Test Case 1) we need annotation
	// DataProvider of TestNG
	@DataProvider(name = "LoginDataProvider1")
	public Object[][] getLoginData1() {
		Object[][] data = new Object[2][4];

		data[0][0] = "https://srambhad-trials77.orangehrmlive.com";
		data[0][1] = "Admin";
		data[0][2] = "z6FRB7Ure@";
		data[0][3] = true;

		// *** Data for negative scenario
		data[1][0] = "https://srambhad-trials77.orangehrmlive.com";
		data[1][1] = "Admin";
		data[1][2] = "z6FRB7Ure@123";
		data[1][3] = false;

		return data;
	}
}
