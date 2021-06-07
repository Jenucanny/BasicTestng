package Testng;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class Login {

	public WebDriver driver;
	public void testInitialize(String browser) throws Exception {
		if (browser.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver", "E:\\JavaC\\chromedriver_win32 (1)\\chromedriver.exe");
			driver = new ChromeDriver();
		}
		else if (browser.equalsIgnoreCase("firefox")) {
			System.setProperty("webdriver.gecko.driver", "E:\\GeckoFiles\\geckodriver-v0.29.1-win64\\geckodriver.exe");
			driver = new FirefoxDriver();
		}
		else if (browser.equalsIgnoreCase("Edge")) {
			System.setProperty("webdriver.edge.driver", "E:\\EdgeFiles\\edgedriver_win64\\msedgedriver.exe");
			driver = new EdgeDriver();
		} 
		else {
			throw new Exception("Invalid browser");
		}
		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		//driver.get("http://demo.guru99.com/test/newtours/");
		//driver.manage().timeouts().pageLoadTimeout(10,TimeUnit.SECONDS);
	}

	@BeforeMethod
	@Parameters({"url"})
	public void setUp(String urlvalue) throws Exception {
		testInitialize("firefox");
		driver.get(urlvalue);
	}

	@AfterMethod
	public void tearDown(ITestResult result) throws IOException {
		if(ITestResult.FAILURE==result.getStatus())
		{
			TakesScreenshot takescreenshot=(TakesScreenshot)driver;
			File screenshot=takescreenshot.getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(screenshot, new File("./Screenshots/"+result.getName()+".png"));
		}
		driver.close();
		//driver.quit();
	}
	@Test(enabled=false)
	public void verifyTitle()
	{
		
		
    	String expectedPageTiltle="Welcome: Mercury Tours";
    	String actualTitle=driver.getTitle();
    	Assert.assertEquals(actualTitle, expectedPageTiltle,"PageTitleNotFound");
    
	}
    @Test(enabled=true,dataProvider="userCredentials")
	public void verifyLogin(String uname,String pass) {
		
		WebElement userName = driver.findElement(By.name("userName"));
		userName.sendKeys(uname);
		WebElement password = driver.findElement(By.name("password"));
		password.sendKeys(pass);
		WebElement submit = driver.findElement(By.name("submit"));
		submit.click();
	}
    @DataProvider(name="userCredentials")
    public Object[][] userCredentials()
    {
		Object[][] data=new Object[2][2];
		data[0][0]="test123";
		data[0][1]="12345";
		data[1][0]="invaliduser";
		data[1][1]="invalidpassword";
    	return data;
		}
}
