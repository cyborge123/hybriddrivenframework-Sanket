package testscripts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import pages.DashboardPage;

public class DashboardTest extends TestBase {

	@Test
	public void verifyWidgetsCountAndText() {

		DashboardPage dashboardPage = new DashboardPage();

		System.out.println("VERIFY - Number of Widgets on Dashboard Page");
		int totalWidgets = dashboardPage.getNumberOfWidgets();
		Assert.assertEquals(totalWidgets, 9,
				"totalWidegets was not displayed as expected, expected was 9, actual widgets displayed "
						+ totalWidgets);

		System.out.println("VERIFY - Text of All Widgets on Dashboard Page");
		List<String> expectedWidgetsText = new ArrayList<String>(
				Arrays.asList("Quick Access", "Buzz Latest Posts", "My Actions", "Latest Documents", "Latest News",
						"Employees on Leave Today", "Time At Work", "Headcount by Location", "COVID-19 Report"));

		System.out.println("STEP - Gell Test Of All Widgest Text");
		List<String> listOfActualWidgetsText = dashboardPage.getAllWidgetsText();

		System.out.println("VERIFY - Text of all Widgets");
		Assert.assertEquals(listOfActualWidgetsText, expectedWidgetsText);
	}

	@Test
	public void verfiyProfileAboutContentTest() {
		DashboardPage dashboardPage = new DashboardPage();

		System.out.println("STEP - Mouse hover on Profile and Click on Settings");
		List<String> expectedProfileSettingOptions = new ArrayList<String>(Arrays.asList("Change Password", "About"));
		List<String> profileSettingOptions = dashboardPage.getSettingProfileOptions();

		System.out.println("VERIFY - Available setting options");
		Assert.assertEquals(profileSettingOptions, expectedProfileSettingOptions);

		System.out.println("STEP - Click on About link");
		dashboardPage.clickOnProfileAbout();

		SoftAssert softAssert = new SoftAssert();

		System.out.println("VERIFY - Company Name");
		String companyName = "OrangeHRM (Pvt) Ltd(Parallel Demo)";
		softAssert.assertEquals(dashboardPage.getCompanyName(), companyName);

		System.out.println("VERIFY - Version");
		String version = "OrangeHRM 7.7.178472";
		softAssert.assertEquals(dashboardPage.getVersion(), version,
				"Expected version was " + version + " but displayed version was " + dashboardPage.getVersion());

		System.out.println("VERIFY - Employee");
		String employees = "97 (103 more allowed)";
		softAssert.assertEquals(dashboardPage.getEmployee(), employees);

		System.out.println("VERIFY - Users");
		String users = "83 (417 more allowed)";
		softAssert.assertEquals(dashboardPage.getUsers(), users);

		System.out.println("STEP - Click on Ok Button");
		dashboardPage.clickOnAboutPopupBtn("Ok");

		softAssert.assertAll(); // hard assert
	}
}
