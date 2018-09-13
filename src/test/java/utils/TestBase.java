package utils;


import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;

public class TestBase {

    public static ThreadLocal<AndroidDriver> driver = new ThreadLocal<>();
    public static ExtentReports reports;
    public static ExtentHtmlReporter htmlReporter;
    private static ThreadLocal<ExtentTest> parentTest = new ThreadLocal<ExtentTest>();
    public static ThreadLocal<ExtentTest> testInfo = new ThreadLocal<ExtentTest>();
    public static String URLbase = "http:simreg.mtnnigeria.net";
    public static String toAddress;


    public static  AndroidDriver getDriver(){
        return driver.get();
    }

    @Parameters( "groupReport")
    @BeforeSuite
    public void setUp( String groupReport) throws MalformedURLException {

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("autoGrantPermissions", true);
        capabilities.setCapability("unicodeKeyboard", true);
        capabilities.setCapability("resetKeyboard", true);
        capabilities.setCapability("noReset", false);
        capabilities.setCapability(CapabilityType.BROWSER_NAME, "Android");
        capabilities.setCapability("deviceName", "SeamfixTab");
        capabilities.setCapability("platformVersion", "4.4.2");
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("appPackage", "com.sf.biocapture.activity");
        capabilities.setCapability("appActivity", "com.sf.biocapture.activity.SplashScreenActivity");
//        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.ANDROID_UIAUTOMATOR2);

        driver.set( new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities));

        htmlReporter = new ExtentHtmlReporter(new File(System.getProperty("user.dir") + groupReport));
        htmlReporter.loadXMLConfig(new File(System.getProperty("user.dir") + "/resources/extent-config.xml"));
        reports = new ExtentReports();
        reports.setSystemInfo("POC", URLbase);
        reports.attachReporter(htmlReporter);


    }

    @BeforeMethod(description = "fetch test cases name")
    public void register(Method method) {

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
    @Parameters("toMails")
    public void cleanup(String toMails) {
        toAddress = toMails;
        SendMail.ComposeGmail("seamfix.test.report@gmail.com", toAddress);

        getDriver().quit();
    }

    @BeforeClass
    public void beforeClass() {
        ExtentTest parent = reports.createTest(getClass().getName());
        parentTest.set(parent);
    }

    @Test
    public void Login() throws InterruptedException {
        Thread.sleep(500);
        WebDriverWait wait = new WebDriverWait(getDriver(), 30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Login']")));
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/otp_login")).click();
        Thread.sleep(1000);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/login_username")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/login_username")).sendKeys("snapshot@seamfix.com");
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/login_password")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/login_password")).sendKeys("bankole1!!");
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/submit")).click();
        Thread.sleep(1000);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Home']")));
    }
}
