package automation.TestComponents;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import automation.pageobjects.LandingPage;
import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {
	
	public WebDriver driver;
	public LandingPage landingPage;

	public WebDriver initializeDriver() throws IOException
	{
		Properties prop=new Properties();
		FileInputStream fis=new FileInputStream(System.getProperty("user.dir")+"\\src\\main\\java\\automation\\resources\\GlobalData.properties");
		prop.load(fis);
		//if mvn cmd (mvn test -D browsername=firefox)sending browsername then use that otherwise get browsername from global data.properties file
		String browserName=System.getProperty("browser")!=null?System.getProperty("browser"):prop.getProperty("browser");
		
		
		if(browserName.contains("chrome"))
		{
			//to run in headless mode
			ChromeOptions options=new ChromeOptions();
			if(browserName.contains("headless"))
			{
			options.addArguments("headless");
			}
		WebDriverManager.chromedriver().setup();
		driver=new ChromeDriver(options);
		driver.manage().window().setSize(new Dimension(1440,900));//to run in full screen mode
		}
		
		else if(browserName.equalsIgnoreCase("firefox"))
		{
		WebDriverManager.firefoxdriver().setup();
		driver=new FirefoxDriver();
		}
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
		return driver;
	}
	
	public List<HashMap<String,String>> getJasonDataToMap(String FilePath) throws IOException
	{
		//read json to string
		String jsonContent = FileUtils.readFileToString(new File(FilePath), StandardCharsets.UTF_8);
	
		//String to Hashmap Jackson databind
		
		ObjectMapper mapper=new ObjectMapper();
		
		List<HashMap<String,String>> data=mapper.readValue(jsonContent, new TypeReference<List<HashMap<String,String>>>(){});
		
		return data;
	}
	
	public String getScreenshot(String testcaseName,WebDriver driver) throws IOException
	{
		TakesScreenshot ts=(TakesScreenshot)driver;
		File source=ts.getScreenshotAs(OutputType.FILE);
		File des=new File(System.getProperty("user.dir")+"//reports//"+testcaseName+".png");
		FileUtils.copyFile(source, des);
		return System.getProperty("user.dir")+"//reports//"+testcaseName+".png";
	}
	
	@BeforeMethod(alwaysRun=true)
	public LandingPage launchApplication() throws IOException
	{
		driver=initializeDriver();
		landingPage=new LandingPage(driver);
		landingPage.goTo();
		return landingPage;
	}
	
	@AfterMethod(alwaysRun=true)
	public void tearDown()
	{
		driver.close();
	}
	
}
