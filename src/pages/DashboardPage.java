package pages;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import base.PredefinedActions;
import constant.ConstantValue;
import utility.PropertyFileOperations;

public class DashboardPage extends PredefinedActions {

	private static DashboardPage dashboardPage;

	@FindBy(css = "div.row>div[class*='oxd dashboard-widget-shell']:not([class*='ng-hide']) div.widget-header>span:last-child")
	private List<WebElement> listOfWidgets;

	@FindBy(css = "div[id='sidebar-profile-picture']")
	private WebElement profile;

	@FindBy(css = "a#aboutDisplayLink")
	private WebElement profileAboutLink;

	@FindBy(xpath = "//div[@class='sub-menu-container-php profile-context-menu-handler opened']/div/div")
	private List<WebElement> profileOptions;

	@FindBy(css = "a[class*='xt-menu-handler']")
	private WebElement profileSetting;

	@FindBy(css = "div#companyInfo p")
	private List<WebElement> profileAboutDetails;

	@FindBy(css = "div#companyInfo>div>div:nth-child(1)>p")
	private WebElement aboutContentFirstP;

	private PropertyFileOperations prop;

	private DashboardPage() {
		try {
			prop = new PropertyFileOperations(ConstantValue.DASHBOARDPAGELOCATOR);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static DashboardPage getObject() {
		if (dashboardPage == null)
			dashboardPage = new DashboardPage();
		PageFactory.initElements(driver, dashboardPage);
		return dashboardPage;
	}

	public int getNumberOfWidgets() {
		return listOfWidgets.size();
	}

	public List<String> getAllWidgetsText() {
		return getListOfWebElemetsText(listOfWidgets);
	}

	public boolean isProfileDisplayed() {
		return isElementDisplayed(profile);
	}

	private void expandProfileOptions() {
		mouseHoverOnElement(profile);
		clickOnElement(profileSetting, false);
	}

	public List<String> getSettingProfileOptions() {
		expandProfileOptions();
		return getListOfWebElemetsText(profileOptions);
	}

	public void clickOnProfileAbout() {
		if (!isElementDisplayed(profileAboutLink)) {
			expandProfileOptions();
		}
		clickOnElement(profileAboutLink, false);
	}

	public Map<String, String> getAboutText() {
		boolean flag = waitForVisibilityOfElement(aboutContentFirstP);
		if (!flag)
			throw new NoSuchElementException("About content not being loaded in given time out");

		List<String> aboutDetailsList = getListOfWebElemetsText(profileAboutDetails);
		Map<String, String> aboutDetailsMap = new LinkedHashMap<>();
		
		for (String text : aboutDetailsList) {
			String[] arr = text.split(":");
			aboutDetailsMap.put(arr[0].trim(), arr[1].trim());
		}
		return aboutDetailsMap;
	}

	public String getCompanyName() {
		return getAboutText().get("Company Name");
	}

	public String getVersion() {
		return getAboutText().get("Version");
	}

	public String getEmployee() {
		return getAboutText().get("Employees");
	}

	public String getUsers() {
		return getAboutText().get("Users");
	}

	public String renewalOn() {
		return getAboutText().get("Renewal on");
	}

	public void clickOnAboutPopupBtn(String btnName) {
		String locatorValue = String.format(prop.getValue("aboutBtnLocator"), btnName);
		WebElement e = getElement("xpath", locatorValue, false);
		clickOnElement(e, false);
	}

	public enum Menu {

		EMPLOYEELIST("Employee List"), MYINFO("My Info"), DIRECTORY("Directory");

		public String menuItem;

		private Menu(String menuTitle) {
			this.menuItem = menuTitle;
		}
	}

	public void gotoMenu(Menu menuName) {
		String menuText = menuName.menuItem;
		String locatorValue = String.format(prop.getValue("menuLocator"), menuText);
		clickOnElement(getElement("xpath", locatorValue, true), false);
	}

}