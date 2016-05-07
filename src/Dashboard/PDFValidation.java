package Dashboard;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import common.CommonMethod;


public class PDFValidation extends CommonMethod{
	
  @BeforeClass(alwaysRun = true)
  public void setup() throws Exception{
	  loginToIFP("mohitgupta","PAssword@1234"); 
  }
  @Test( groups = {"Dashboard", "AUTO_008"})
  public void test_dashboard_PDF() throws Exception {
	// Download the pdf from row = 0 and assert for file downloaded with client first name  
	  downloadPDF(0);
  }
}