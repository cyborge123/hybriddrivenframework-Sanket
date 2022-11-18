package testscripts;

import org.testng.Assert;
import org.testng.annotations.Test;

import listeners.RetryTestCases;

public class RetryDemo {
	
	@Test(retryAnalyzer = RetryTestCases.class) 
	public void m1() {
		Assert.fail();
	}
}
