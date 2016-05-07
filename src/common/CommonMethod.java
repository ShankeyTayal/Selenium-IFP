package common;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.NoSuchElementException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;

import jxl.Sheet;
import jxl.Workbook;


public class CommonMethod extends SeleniumGenericLib{
	
/**
 * 	 Method to login "www.inforcepro.com/login" website
 */
	
	public void loginToIFP( String userName_, String pwd_ ) throws Exception
	{
		navigateToUrl();
		waitUntilElementPresent(By.className(Locators.Link.LOGO_LINK));
		login( userName_, pwd_ );
		waitUntilElementPresent(By.linkText(Locators.Link.LOGOUT));
		assertElementPresent(By.linkText(Locators.Link.LOGOUT));
	}
	
/**
 * 
 * Capture Screen shot at successful login and save file to given location
 */
		
	public void CaptureLoginScreenShot(String username )
	{

		try
		{
				File theDir = new File("/Users/shankey/Documents/Selenium/Selenium/Test-Report/LoginScreenshot");
				if (!theDir.exists())
				{
					System.out.println( "creating directory: " + theDir );
					boolean result = theDir.mkdir();
					if( result )
					{
						System.out.println( "DIR created" );
					}
				}
				File scrFile = ((TakesScreenshot)driver).getScreenshotAs( OutputType.FILE );
				File imageFile = new File( theDir, getClass().getName() +username+".png" );

				FileUtils.copyFile( scrFile, imageFile );
				System.out.println( "screenshot taken in file: " + imageFile.getAbsolutePath() );
		}
		catch( Exception e )
		{
			System.out.println("screenshot not taken");
		}
		
	}
/**
 *  Quit the driver after every class	
 */
	
	@AfterClass(alwaysRun=true)
	public void closeWebDriver(){
		if(driver!=null)
			driver.quit();
	}
	
/**
 * 	
 * To check whether element is present by using different locators
 */
	protected void assertElementPresent(By by ) throws Exception
	{

		boolean elementExist = false;
		waitUntilElementPresent(by);
		try {
			driver.findElement(by);
			elementExist = true;

		} catch (NoSuchElementException e)
		{
			Assert.fail("Element does not exist on the page with title "+driver.getTitle()+" \n"+e);
		}
		Assert.assertTrue(elementExist, "Element should be present in the current page '"+driver.getTitle()+
				"' but that does not exist \n");
	}	
	
/**
 * To get particular cell value from dashboard
 * 
 */
	public String getCellValue( int rowNum, int colNum ) throws Exception
	{
		try
		{
		  waitUntilElementPresent(By.xpath("//tr[starts-with(@id,'row')]"));
		  List<WebElement> elements = driver.findElements(By.xpath(Locators.GenericXpath.ROW_XPATH));	
		  if(rowNum>elements.size())
		  {
			  Assert.fail("Enter a valid row no.");
			  return null;
		  }
		  List<WebElement> subElements =  elements.get(rowNum).findElements(By.className(Locators.ClassName.COLUMN));
		  if(colNum>subElements.size())
		  {
			  Assert.fail("Enter a valid cell no.");
			  return null;
		  }
		  String cellValue = subElements.get(colNum).getText();
		  Assert.assertNotNull(cellValue, "Unable to get cell value");
		  return cellValue;
	}
		catch(Exception e)
		{
			Assert.fail("failed to get cell value"+e );
			return null;
		}	
	}	
	
/**
 * To type in Enrty field 
 * 
 */
	public void typeInInputField(By by ,String text) throws Exception
	{
		try
		{
		waitUntilElementPresent(by);
		WebElement entryField = driver.findElement(by);
		entryField.clear();
		entryField.sendKeys(text);
		}
		catch (NoSuchElementException e)
		{
			Assert.fail("Can't find element by name: "+e);
		}
		
	}
/**
 *  To click on a element	
 */
	public void clickOnElement(By by) throws Exception {

		try{
			waitUntilElementPresent(by);
			WebElement Element = this.driver.findElement(by);
			if ((Element != null) && (Element.isEnabled())) {
				Element.click();
			}
		}
		catch(WebDriverException ex) {

			Assert.fail("Element with className is not clicked");

		}
	}
/**
 * 	 to download file and verify the file name
 */
	public void downloadPDF(int rowNum) throws Exception
	{
		 waitUntilElementPresent(By.xpath(Locators.GenericXpath.ROW_XPATH));
		  List<WebElement> elements = driver.findElements(By.xpath(Locators.GenericXpath.ROW_XPATH));
		  List<WebElement> subElements =  elements.get(rowNum).findElements(By.className(Locators.ClassName.COLUMN));
		  String ClientName = subElements.get(3).getText();
		  String[] name = ClientName.split(" ");
		  subElements.get(8).click(); 
		  clickOnElement(By.xpath(Locators.GenericXpath.DOWNLOAD_REPORT));
		  WebDriverWait wait = new WebDriverWait(driver, 20);	 
		  wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loadingZone")));
		  Assert.assertTrue(isFileDownloaded("/Users/shankey/Documents/Selenium/Selenium/Downloads", name[0]+".pdf"), "Failed to download Expected document");
	}
	
/**
 * 	 to verify if file is present in directory
 */
	public boolean isFileDownloaded(String downloadPath, String fileName) {
		boolean flag = false;
	    File dir = new File(downloadPath);
	    File[] dir_contents = dir.listFiles();
	  	    
	    for (int i = 0; i < dir_contents.length; i++) {
	        if (dir_contents[i].getName().equals(fileName))
	            return flag=true;
	            }

	    return flag;
	}
/**
 *  To read excel file and it will return multidimensional array
 */
	public String[][] getExcelData(String fileName, String sheetName) {
		String[][] arrayExcelData = null;
		try {
			FileInputStream fs = new FileInputStream(fileName);
			Workbook wb = Workbook.getWorkbook(fs);
			Sheet sh = wb.getSheet(sheetName);

			int totalNoOfCols = sh.getColumns();
			int totalNoOfRows = sh.getRows();
			
			arrayExcelData = new String[totalNoOfRows-1][totalNoOfCols];
			
			for (int i= 1 ; i < totalNoOfRows; i++) {

				for (int j=0; j < totalNoOfCols; j++) {
					arrayExcelData[i-1][j] = sh.getCell(j, i).getContents();
				}

			}
		} catch (Exception e) {
			System.out.println("exception in reading excel"+e);
		}
		return arrayExcelData;
	}
}
