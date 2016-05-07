package common;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.openqa.selenium.TakesScreenshot;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
public class SeleniumGenericLib  {

	protected static HashMap<String, String> _namespace = new HashMap<String, String>();
	protected String _userName = null;
	protected String _password = null;
	protected String url_ = "https://inforcepro.com";
	protected WebDriver driver = null;
	
/**
 *  set driver preferences and initialize the driver 
 */
	public void initializeDriver()
	{
		System.setProperty("webdriver.chrome.driver","/Users/shankey/Documents/Selenium/chromedriver");
		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		ChromeOptions options = new ChromeOptions();
		Map<String, Object> prefs = new HashMap<String, Object>();
		prefs.put("download.default_directory","/Users/shankey/Documents/Selenium/Selenium/Downloads");
		options.setExperimentalOption("prefs", prefs);
		capabilities.setCapability(ChromeOptions.CAPABILITY, options);			
		driver = new ChromeDriver(capabilities);
			System.out.println( "********************************" );
			System.out.println( "Browser Launched-->"  );
			System.out.println("********************************");
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

/**
 * 	 enter username and password and click on submit button
 */
	public void login(String userName_, String pwd_) throws Exception
	{
		driver.findElement(By.id(Locators.TextField.LOGIN_USERNAME)).sendKeys( userName_ );
		driver.findElement(By.id(Locators.TextField.LOGIN_PASSWORD)).sendKeys( pwd_ );
		driver.findElement(By.xpath(Locators.Button.LOGIN_BTN)).submit();
		waitUntilElementPresent(By.linkText(Locators.Link.LOGOUT));
	}
/**
 *  Navigate to login page URL
 */
	public void navigateToUrl() throws Exception
	{
		initializeDriver();
		driver.navigate().to( url_ +"/login" );
		waitUntilElementPresent(By.id(Locators.TextField.LOGIN_USERNAME));
		Assert.assertTrue( driver.getCurrentUrl().contains( url_ ), "Page not found after Navigatng to url: " + url_ );
	}
/**
 *  Explicit wait , to wait for an element
 */
	public void waitUntilElementPresent(By by)throws Exception
	{
		try
		{
			WebDriverWait wait = new WebDriverWait(driver, 20);
			 
			wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(by));
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
/**
 * capture  screenshot of where the last action left us	
 */
	@AfterMethod(alwaysRun=true)
	public void CaptureScreenShot()
	{
		try
		{
				File theDir = new File("/Users/shankey/Documents/Selenium/Selenium/Test-Report/Screenshot");
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
				File imageFile = new File( theDir, getClass().getName() + ".png" );

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
	
}