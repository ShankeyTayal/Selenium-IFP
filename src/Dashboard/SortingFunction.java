package Dashboard;

import org.openqa.selenium.By;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import common.CommonMethod;
import common.Locators;
import org.testng.Assert;


public class SortingFunction extends CommonMethod{
	
  @BeforeClass(alwaysRun = true)
  public void setup() throws Exception{
	  loginToIFP("mohitgupta","PAssword@1234"); 
  }
  @Test( groups = {"Dashboard", "AUTO_010"})
  public void test_dashboard_sorting() throws Exception {
	  waitUntilElementPresent(By.linkText(Locators.Link.POLICY_NUMBER));
	  clickOnElement(By.linkText(Locators.Link.POLICY_NUMBER));
	  int i =1;
	  String policyNo1 = getCellValue(0,0);
	  while(true)
	  {
		  String policyNo2 = getCellValue(i,0);
		  if(policyNo2==null)
		  break;
		  int j = policyNo1.compareTo(policyNo2);
		  if(j>=0)
		  {
			  Assert.fail("Policies no. are Not Sorted");
		  }
		  else
		  {
			  policyNo1 = policyNo2;
		  } 
		  i++;
	  }
	 System.out.println("Policies no. are Sorted");
  }
}