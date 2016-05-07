package login;

import org.openqa.selenium.By;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import common.CommonMethod;
import common.Locators;

public class LoginFromExcel extends CommonMethod{
	
  @Test( dataProvider="login", groups =  {"login_excel", "AUTO_0011"})
	public void test_dashboard_VerifyLogin(String userName, String password) throws Exception {
	  	navigateToUrl(); 
		driver.findElement(By.id(Locators.TextField.LOGIN_USERNAME)).sendKeys( userName );
		driver.findElement(By.id(Locators.TextField.LOGIN_PASSWORD)).sendKeys( password );
		driver.findElement(By.xpath(Locators.Button.LOGIN_BTN)).submit();
		
		
		try
		{
		if(driver.findElement(By.xpath("//h3[@id='err' and contains(text(),'Error')]")).isDisplayed())
		{
			System.out.println("login failed for user "+userName);
		}
		}
		catch (Exception e)
		{
			CaptureLoginScreenShot(userName);	
		}
			
		
	}
	@DataProvider(name="login")
	public Object[][] loginData() {
		Object[][] arrayObject = getExcelData("/Users/shankey/Documents/Selenium/Selenium/src/testData/sample.xls","Sheet1");
		return arrayObject;
	}
	/**
	 *  Quit the driver after every class	
	 */
		
		@AfterMethod(alwaysRun=true)
		public void closeWebDriver(){
			if(driver!=null)
				driver.quit();
		}
	
}
