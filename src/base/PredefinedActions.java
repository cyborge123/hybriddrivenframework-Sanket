package base;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import constant.ConstantValue;
import customexceptions.ElementNotEnabledException;

public class PredefinedActions {

	protected static WebDriver driver;
	static WebDriverWait wait;
	private static Actions actions;

	// *** To restrict the user for creating object
	protected PredefinedActions() {

	}

	public static void start(String url) {
		System.setProperty(ConstantValue.CHROMEDRIVERKEY, ConstantValue.CHROMEDRIVER);
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get(url);
		// driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		wait = new WebDriverWait(driver, ConstantValue.EXPLICITWAITTIME);
		actions = new Actions(driver);
	}

	// *** To find the locator
	protected WebElement getElement(String locatorType, String locatorValue, boolean isWaitRequired) {
		WebElement element = null;
		switch (locatorType) {
		case "id":
			if (isWaitRequired) {
				element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(locatorValue)));
			} else
				element = driver.findElement(By.id(locatorValue));
			break;

		case "xpath":
			if (isWaitRequired) {
				element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locatorValue)));
			} else
				element = driver.findElement(By.xpath(locatorValue));
			break;

		case "cssselector":
			if (isWaitRequired) {
				element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(locatorValue)));
			} else
				element = driver.findElement(By.cssSelector(locatorValue));
			break;

		case "name":
			if (isWaitRequired) {
				element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name(locatorValue)));
			} else
				element = driver.findElement(By.name(locatorValue));
			break;

		case "linktext":
			if (isWaitRequired) {
				element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(locatorValue)));
			} else
				element = driver.findElement(By.linkText(locatorValue));
			break;

		case "partiallinktext":
			if (isWaitRequired) {
				element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText(locatorValue)));
			} else
				element = driver.findElement(By.partialLinkText(locatorValue));
			break;

		case "classname":
			if (isWaitRequired) {
				element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className(locatorValue)));
			} else
				element = driver.findElement(By.className(locatorValue));
			break;

		case "tagname":
			if (isWaitRequired) {
				element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName(locatorValue)));
			} else
				element = driver.findElement(By.tagName(locatorValue));
			break;
		}
		return element;

	}

	// *** Wait
	protected boolean waitForVisibilityOfElement(WebElement e) {
		try {
			wait.until(ExpectedConditions.visibilityOf(e));
		} catch (Exception exception) {
			return false;
		}
		return true;
	}

	// *** Send Keys method
	protected void setText(WebElement e, String text) {
		scrollToElement(e);
		if (e.isEnabled())
			e.sendKeys(text);
		else
			throw new ElementNotEnabledException(text + " can't be entered as ele,ent is not enabled");
	}

	// *** Overloaded Method
	protected void setText(String locatorType, String locatorValue, boolean isWaitRequired, String text) {
		WebElement e = getElement(locatorType, locatorValue, isWaitRequired);
		if (e.isEnabled())
			e.sendKeys(text);
	}

	// *** For Click method
	protected void clickOnElement(WebElement e, boolean isWaitRequiredBeforeClick) {
		if (isWaitRequiredBeforeClick) {
			wait.until(ExpectedConditions.elementToBeClickable(e));
			e.click();
		}
		e.click();
	}

	protected void scrollToElement(WebElement e) {
		if (!e.isDisplayed()) {
			JavascriptExecutor je = (JavascriptExecutor) driver;
			je.executeScript("arguments[0].scrollIntoView(treu)", e);
		}
	}

	protected boolean isElementDisplayed(WebElement e) {
		scrollToElement(e);
		return e.isDisplayed();
	}

	protected void mouseHoverOnElement(WebElement e) {
		actions.moveToElement(e).build().perform();
	}

	protected List<String> getListOfWebElemetsText(List<WebElement> list) {
		List<String> listOfElementText = new ArrayList<String>();
		for (WebElement e : list) {
			listOfElementText.add(e.getText());
		}
		return listOfElementText;
	}

	protected String getElementText(WebElement e, boolean isWaitRequired) {
		if (isWaitRequired)
			waitForVisibilityOfElement(e);
		String value = e.getText();

		if (value.equals("")) {
			value = e.getAttribute("value");
		}
		return value;
	}

	// *** Javascript mechanism for alternative methods to Java-Selenium

	protected void clickUsingJS(WebElement ele) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeAsyncScript("arguments[0].click()", ele);
	}

	protected void sendKeyUsingJS(WebElement ele, String text) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeAsyncScript("arguments[0].value='" + text + "'", ele);
	}

	protected void markCheckBox(WebElement ele, boolean checkedOrUnchecked) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeAsyncScript("arguments[0].checked='" + checkedOrUnchecked + "'", ele);
	}

	public static void takesScreenshot(String testCaseScreenShotName) {
		TakesScreenshot ts = (TakesScreenshot) driver; // type casted because driver need capabilities to take snippet
		File scrfile = ts.getScreenshotAs(OutputType.FILE); // Returns the file
		try {
			FileUtils.copyFile(scrfile,
					new File(ConstantValue.SCREENSHOTLOCATION + testCaseScreenShotName + ConstantValue.SCREENSHOTEXT)); // where
																														// to
																														// copy
																														// file
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getPageTitle() {
		return driver.getTitle();
	}

	public String getPageURL() {
		return driver.getCurrentUrl();
	}

	public static void closeBrowser() {
		driver.close();
	}
}