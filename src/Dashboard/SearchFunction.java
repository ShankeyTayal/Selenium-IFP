package Dashboard;

import org.openqa.selenium.By;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import common.CommonMethod;
import common.Locators;
import org.testng.Assert;


public class SearchFunction extends CommonMethod{
	
  @BeforeClass(alwaysRun = true)
  public void setup() throws Exception{
	  loginToIFP("mohitgupta","PAssword@1234"); 
  }
  @Test( groups = {"Dashboard", "AUTO_008"})
  public void test_dashboard_Seach() throws Exception {
	  
	  String policyNo = getCellValue(0,0);
	  String ClientName = getCellValue(0,3);
	  System.out.println("Client "+ClientName+"policy number is "+policyNo);
	  typeInInputField(By.id(Locators.TextField.SEARCH),ClientName);
	  clickOnElement(By.name(Locators.Button.SEARCH_BUTTON));
	  assertElementPresent(By.xpath("//td[@class='table_text' and contains(text(),'"+ClientName+"')]"));	
	  String SearchedpolicyNo = getCellValue(0,0);
	  Assert.assertEquals(policyNo, SearchedpolicyNo, "Wrong Search, policies are different ");
	 
  }
}