package com.sf.utils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import io.appium.java_client.android.AndroidDriver;
import com.sf.utils.testUtils;

public class testBase {
	
	public static WebDriverWait wait;
	public static ThreadLocal<WebDriver> driver = new ThreadLocal<WebDriver>();
	public static ExtentReports reports;
	public static ExtentHtmlReporter htmlReporter;
	private static ThreadLocal<ExtentTest> parentTest = new ThreadLocal<ExtentTest>();
	public static ThreadLocal<ExtentTest> testInfo = new ThreadLocal<ExtentTest>();
	public static String URLbase = "http:simreg.mtnnigeria.net";
	
    @SuppressWarnings("rawtypes")
	protected void prepareAndroidForAppium() throws MalformedURLException {
    	
    	DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability(CapabilityType.BROWSER_NAME, "Android");
		capabilities.setCapability("deviceName", "cc175d07");
		capabilities.setCapability("platformVersion", "4.4.4");
		capabilities.setCapability("platformName", "Android");
		capabilities.setCapability("appPackage", "com.sf.biocapture.activity");
        capabilities.setCapability("appActivity", "com.sf.biocapture.activity.SplashScreenActivity");
        
        driver.set( new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities));
        
    }
    
    public static  WebDriver getDriver(){
		return driver.get();
	}
    
    @Parameters( "groupReport")
    @BeforeSuite
    public void setUp( String groupReport) throws MalformedURLException {
        prepareAndroidForAppium();
        htmlReporter = new ExtentHtmlReporter(new File(System.getProperty("user.dir") + groupReport));
		htmlReporter.loadXMLConfig(new File(System.getProperty("user.dir") + "/resources/extent-config.xml"));
		reports = new ExtentReports();
		reports.setSystemInfo("POC", URLbase);
		reports.attachReporter(htmlReporter);

        
    }
    
    @BeforeMethod(description = "fetch test cases name")
	public void register(Method method) {
    	
    	ExtentTest parent = reports.createTest(getClass().getName());
        parentTest.set(parent);
		ExtentTest child = parentTest.get().createNode(method.getName());
		testInfo.set(child);
		testInfo.get().assignCategory("Sanity");

	}

	@AfterMethod(description = "to display the result after each test method")
	public void captureStatus(ITestResult result) throws IOException {
		
		if (result.getStatus() == ITestResult.FAILURE) {
			String screenshotPath = testUtils.addScreenshot();
			testInfo.get().addScreenCaptureFromPath(screenshotPath);
			testInfo.get().fail(result.getThrowable());
		}			
        else if (result.getStatus() == ITestResult.SKIP)
        	testInfo.get().skip(result.getThrowable());
        else
        	testInfo.get().pass(result.getName() +" Test passed");

		reports.flush();
	}

    @AfterSuite
    public void closeApp() {
    	getDriver().quit();
    }
}
