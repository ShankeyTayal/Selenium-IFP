package login;

import org.testng.annotations.Test;
import common.CommonMethod;


public class CheckLoginSuccess extends CommonMethod{
  @Test( groups =  {"login", "AUTO_001"})
  public void test() throws Exception {
	   
	 loginToIFP("mohitgupta","PAssword@1234");		
	 CaptureLoginScreenShot("mohitgupta");	
  }
}
