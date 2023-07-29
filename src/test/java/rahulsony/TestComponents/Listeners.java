package rahulsony.TestComponents;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import rahulsony.resources.ExtentReporterNG;

public class Listeners extends BaseTest implements ITestListener {
	
	ExtentTest test;
	ExtentReports extent = ExtentReporterNG.getReportObject();
	ThreadLocal<ExtentTest> extentTest = new ThreadLocal<ExtentTest>(); //Thread Safe (In Parallel we ran into a problem of test failure)
	
	@Override
	public void onTestStart(ITestResult result) {
		test = extent.createTest(result.getMethod().getMethodName());
		extentTest.set(test); //Unique thread id (ErrorValidationTest)->test
	}
	
	@Override
	public void onTestSuccess(ITestResult result) {
		extentTest.get().log(Status.PASS, "Test Passed");
	}
	
	@Override
	public void onTestFailure(ITestResult result) {
		
		//test.log(Status.PASS, "Test Failed");
		extentTest.get().fail(result.getThrowable());
		
		//we are implementing here driver using ErrorValidation knowledge
		
		try {
			driver = (WebDriver) result.getTestClass().getRealClass().getField("driver").get(result.getInstance());
		} catch(Exception e1) {
			e1.printStackTrace();
		}
	}
		
	@Override
	public void onStart(ITestContext result) {
		
	}
	
	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		
	}
	
	@Override
	public void onTestSkipped(ITestResult result) {
		
	}
	@Override
	public void onFinish(ITestContext result) {
		extent.flush();
	}

}
