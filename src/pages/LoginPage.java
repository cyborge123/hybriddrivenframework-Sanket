package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import base.PredefinedActions;

public class LoginPage extends PredefinedActions {
	
	private static LoginPage loginPage;

	@FindBy(id = "txtUsername")
	private WebElement userNameElement;

	@FindBy(id = "txtPassword")
	private WebElement passwordElement;

	@FindBy(xpath = "//button[@type='submit']")
	private WebElement submitBtn;
	
	@FindBy(css = "#txtUsername-error")
	private WebElement usernameErrorElement;
	
	@FindBy(css = "#txtPassword-error")
	private WebElement passwordErrorElement;
	
	@FindBy(css = "div.organization-logo.shadow>img")
	private WebElement logo;
	
	//*** To give driver access to initialize elements and  find elements in constructor
	private LoginPage() {
		
	}
	
	public static LoginPage getObject() {
		if(loginPage == null)
			loginPage = new LoginPage();
		PageFactory.initElements(driver, loginPage);
		return loginPage;
	}

	public void login(String username, String password) {
		enterUsername(username);
		enterPassword(password);
		clickOnLoginBtn();
	}

	public void enterUsername(String username) {
		// driver.findElement(By.id("txtUsername")).sendKeys(username);

		// WebElement e = getElement("id", "txtUsername", false);
		// setText(e, username);

		// setText("id", "txtUsername", false, username);

		setText(userNameElement, username);
	}

	public void enterPassword(String password) {
		// driver.findElement(By.id("txtPassword")).sendKeys(password);
		setText(passwordElement, password);
	}

	public void clickOnLoginBtn() {
		// driver.findElement(By.xpath("//button[@type='submit']")).click();
		clickOnElement(submitBtn, false);

	}

	public boolean isUsernameErrorMessageDisplayed() {
//		WebElement usernameErrorMessage = driver.findElement(By.cssSelector("#txtUsername-error"));
//		return usernameErrorMessage.isDisplayed();
		
		return isElementDisplayed(usernameErrorElement);
	}

	public boolean isPasswordErrorMessageDisplayed() {
//		WebElement passwordErrorMessage = driver.findElement(By.cssSelector("#txtPassword-error"));
//		return passwordErrorMessage.isDisplayed();
		
		return isElementDisplayed(passwordErrorElement);
	}

	public boolean isLogoDisplayed() {
//		return driver.findElement(By.cssSelector("div.organization-logo.shadow>img")).isDisplayed();
		return isElementDisplayed(logo);
	}
}