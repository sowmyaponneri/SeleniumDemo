package automation.TestComponents;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import automation.resources.ExtentReporterNG;

public class Listeners extends BaseTest implements ITestListener {
	
	ExtentReports extent=ExtentReporterNG.getReportObject();
	ExtentTest test;
	ThreadLocal<ExtentTest> extentTest=new ThreadLocal<ExtentTest>();
	@Override		
    public void onTestStart(ITestResult result) {					
        // TODO Auto-generated method stub				
		test=extent.createTest(result.getMethod().getMethodName());	
		extentTest.set(test);//unique thread id -> test, store it as map
    }		

    @Override		
    public void onTestSuccess(ITestResult result) {					
        // TODO Auto-generated method stub				
    	extentTest.get().log(Status.PASS, "Test Passed");	
    }		
    @Override		
    public void onTestFailure(ITestResult result) {					
        // TODO Auto-generated method stub
    	//test.log(Status.FAIL, "Test Failed");
    	//to get error log
    	extentTest.get().fail(result.getThrowable());
        //Take Screenshot and attach screenshot
    	
    	try {
			driver=(WebDriver) result.getTestClass().getRealClass().getField("driver").get(result.getInstance());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    	
    	
    	String filePath = null;
		try {
			filePath = getScreenshot(result.getMethod().getMethodName(),driver);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		extentTest.get().addScreenCaptureFromPath(filePath,result.getMethod().getMethodName());
    }		

    @Override		
    public void onTestSkipped(ITestResult result) {					
        // TODO Auto-generated method stub				
        		
    }		
    @Override		
    public void onFinish(ITestContext context) {					
        // TODO Auto-generated method stub				
        
    	extent.flush();
    }		
	
}
