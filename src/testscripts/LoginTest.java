package testscripts;

import org.testng.Assert;
import org.testng.annotations.Test;

import base.PredefinedActions;
import pages.LoginPage;

public class LoginTest {

	@Test
	public void tc1() {
		System.out.println("STEP - Launch Chrome Browser & Hit url");
		PredefinedActions.start("https://srambhad-trials77.orangehrmlive.com");

		System.out.println("STEP - Enter valid login credentials");
		LoginPage loginPage = new LoginPage();
		loginPage.login("Admin", "z6FRB7Ure@");

		System.out.println("VERIFY - Home page is displayed");
		String expetedTitle = "Employee Management";
		String actualTitle = loginPage.getPageTitle();

		Assert.assertEquals(actualTitle, expetedTitle,
				"Expected title was " + expetedTitle + " but actual title was " + actualTitle);

		PredefinedActions.closeBrowser();
	}

	@Test
	public void tc1_Negative_Secenario() {
		System.out.println("STEP - Launch Chrome Browser & Hit url");
		PredefinedActions.start("https://srambhad-trials77.orangehrmlive.com");

		System.out.println("STEP - Enter valid login credentials");
		LoginPage loginPage = new LoginPage();
		loginPage.login("Admin", "z6FRB7Ure@123");

		System.out.println("VERIFY - Home page is displayed");
		String expetedTitle = "OrangeHRM";
		String actualTitle = loginPage.getPageTitle();

		Assert.assertEquals(actualTitle, expetedTitle,
				"Expected title was " + expetedTitle + " but actual title was " + actualTitle);

		PredefinedActions.closeBrowser();
	}
}
