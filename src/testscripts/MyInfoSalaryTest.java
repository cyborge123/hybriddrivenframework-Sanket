package testscripts;

import org.testng.Assert;
import org.testng.annotations.Test;

import pages.DashboardPage;
import pages.DashboardPage.Menu;
import pages.MyInfoPage;
import pages.MyInfoPage.MyInfoMenu;
import pages.MyInfo_SalaryPage;

public class MyInfoSalaryTest extends TestBase {

	@Test
	public void verifyCtc() {
		DashboardPage dashboardPage = DashboardPage.getObject();
		dashboardPage.gotoMenu(Menu.MYINFO);
		MyInfoPage myInfoPage = MyInfoPage.getObject();
		myInfoPage.gotoMenu(MyInfoMenu.SALARY);
		MyInfo_SalaryPage salaryPage = MyInfo_SalaryPage.getObject();
		String ctc = salaryPage.getCostToCompany();
		Assert.assertTrue(ctc.startsWith("$"));
		ctc = ctc.replace("$", "").replace(",", "");
		double ctcD = Double.parseDouble(ctc);
		Assert.assertTrue(ctcD > 0, "CTC is non zero.");
		System.out.println(ctc);
	}
}
