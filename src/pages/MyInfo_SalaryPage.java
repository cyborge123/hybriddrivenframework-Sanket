package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import base.PredefinedActions;

public class MyInfo_SalaryPage extends PredefinedActions {
	
	public MyInfo_SalaryPage() {
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath = "//div[@traslate='Cost to the Company']/following-sibling::div")
	private WebElement costToCmp;
	
	public String getCostToCompany() {
		return costToCmp.getText();
	}

}
