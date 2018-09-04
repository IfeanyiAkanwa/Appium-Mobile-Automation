package utils;


import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;

public class TestBase {

    public static WebDriverWait wait;
    public static ThreadLocal<WebDriver> driver = new ThreadLocal<WebDriver>();
    public static ExtentReports reports;
    public static ExtentHtmlReporter htmlReporter;
    private static ThreadLocal<ExtentTest> parentTest = new ThreadLocal<ExtentTest>();
    public static ThreadLocal<ExtentTest> testInfo = new ThreadLocal<ExtentTest>();
    public static String URLbase = "http:simreg.mtnnigeria.net";


    public static  WebDriver getDriver(){
        return driver.get();
    }

    @Parameters( "groupReport")
    @BeforeSuite
    public void setUp( String groupReport) throws MalformedURLException {

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("autoGrantPermissions", "true");
//        capabilities.setCapability("unicodeKeyboard", false);
//        capabilities.setCapability("resetKeyboard", true);
        capabilities.setCapability("noReset", "false");
        capabilities.setCapability(CapabilityType.BROWSER_NAME, "Android");
        capabilities.setCapability("deviceName", "R7L4C15920003639");
        capabilities.setCapability("platformVersion", "4.4.2");
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("appPackage", "com.sf.biocapture.activity");
        capabilities.setCapability("appActivity", "com.sf.biocapture.activity.SplashScreenActivity");

        driver.set( new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities));

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
            String screenshotPath = TestUtils.addScreenshot();
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
