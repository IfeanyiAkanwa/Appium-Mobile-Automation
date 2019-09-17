package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.MobileCapabilityType;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class TestBase {

	public static ThreadLocal<AndroidDriver> driver = new ThreadLocal<>();
	public static ExtentReports reports;
	public static ExtentHtmlReporter htmlReporter;
	private static ThreadLocal<ExtentTest> parentTest = new ThreadLocal<ExtentTest>();
	public static ThreadLocal<ExtentTest> testInfo = new ThreadLocal<ExtentTest>();
	public static String gridUrl = System.getProperty("grid-url", "http:simregpoc.mtnnigeria.net");
	public static String toAddress;

	public static String userName = "USERNAME";
	public static String accessKey = "ACCESS_KEY";
	public String local = "local";
	public String remoteJenkins = "remote-jenkins";
	public String remoteBrowserStack = "remote-browserStack";

	public static AndroidDriver getDriver() {
		return driver.get();
	}

	String devices;
	static String[] udid;
	
	 @Parameters ("dataEnv")
		public static String myUrl(String dataEnv) throws FileNotFoundException, IOException, ParseException {
	    	JSONParser parser = new JSONParser();
			JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resource/" + dataEnv + "/data.conf.json"));
			JSONObject envs = (JSONObject) config.get("LandingPage_Url");

			String stagingUrl = (String) envs.get("stagingUrl");
			String prodUrl = (String) envs.get("prodUrl");
			
			String myUrl = null;
			if(dataEnv.equalsIgnoreCase("stagingData")) {
				myUrl = System.getProperty("instance-url", stagingUrl);
			} else
			{
				myUrl = System.getProperty("instance-url", prodUrl);
			}
			return myUrl;
		}

	@Parameters({"groupReport", "dataEnv"})
	@BeforeSuite
	public void setUp(String groupReport, String dataEnv) throws FileNotFoundException, IOException, ParseException {

		{
			try {
				devices = TestUtils.executeAdbCommand("adb devices");
				devices = devices.replaceAll("List of devices attached", " ");
				devices = devices.replaceAll("device", " ").trim();
				udid = devices.split(" ");
			} catch (IOException e) {
				System.out.println("No devices found: " + e.toString());

			}
		}

		htmlReporter = new ExtentHtmlReporter(new File(System.getProperty("user.dir") + groupReport));
		//htmlReporter.loadXMLConfig(new File(System.getProperty("user.dir") + "/resources/extent-config.xml"));
		reports = new ExtentReports();
		reports.setSystemInfo("Test Environment", myUrl(dataEnv));
		reports.attachReporter(htmlReporter);

	}

	@BeforeMethod(description = "fetch test cases name")
	public void register(Method method) {

		getDriver().manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
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
		} else if (result.getStatus() == ITestResult.SKIP)
			testInfo.get().skip(result.getThrowable());
		else
			testInfo.get().pass(result.getName() + " Test passed");

		reports.flush();
	}

	@AfterSuite
	@Parameters("toMails")
	public void cleanup(String toMails) {
		toAddress = toMails;
		SendMail.ComposeGmail("BioSmart Report <seamfix.test.report@gmail.com>", toAddress);

		getDriver().quit();
	}

	@AfterClass
	public void closeApp() {
		getDriver().quit();
	}

	@BeforeClass
	@Parameters({ "systemPort", "deviceNo", "server" })
	public void startApp(String systemPort, int deviceNo, String server) throws IOException {

		if (server.equals(remoteBrowserStack)) {
			DesiredCapabilities caps = new DesiredCapabilities();
			caps.setCapability("device", "Samsung Galaxy S8 Plus");
			caps.setCapability("app", "bs://<hashed app-id>");
			driver.set(new AndroidDriver<AndroidElement>(
					new URL("https://" + userName + ":" + accessKey + "@hub-cloud.browserstack.com/wd/hub"), caps));

		} else if (server.equals(remoteJenkins)) {

		} else if (server.equals(local)) {

			deviceNo = deviceNo - 1;
			while (deviceNo >= udid.length) {
				deviceNo = deviceNo - 1;
			}
			try {
				DesiredCapabilities capabilities = new DesiredCapabilities();
				capabilities.setCapability("autoGrantPermissions", true);
				capabilities.setCapability("unicodeKeyboard", true);
				capabilities.setCapability("resetKeyboard", true);
				capabilities.setCapability("noReset", false);

				capabilities.setCapability(AndroidMobileCapabilityType.SYSTEM_PORT, systemPort);
				capabilities.setCapability(MobileCapabilityType.UDID, udid[deviceNo].trim());
				capabilities.setCapability("deviceName", "SeamfixTab");
				capabilities.setCapability("platformName", "Android");
				capabilities.setCapability("appPackage", "com.sf.biocapture.activity");
				capabilities.setCapability("appActivity", "com.sf.biocapture.activity.SplashScreenActivity");
				capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.ANDROID_UIAUTOMATOR2);

				driver.set(new AndroidDriver(new URL("http://0.0.0.0:4723/wd/hub"), capabilities));
				System.out.println("++++++++++UIAUTOMATOR 2 DRIVER INSTANCE RUNNING++++++++++++");

			} catch (WebDriverException e) {
				DesiredCapabilities capabilities = new DesiredCapabilities();
				capabilities.setCapability(MobileCapabilityType.UDID, udid[deviceNo].trim());
				capabilities.setCapability("autoGrantPermissions", true);
				capabilities.setCapability(AndroidMobileCapabilityType.SYSTEM_PORT, systemPort);
				capabilities.setCapability("unicodeKeyboard", true);
				capabilities.setCapability("resetKeyboard", true);
				capabilities.setCapability("noReset", false);

				capabilities.setCapability("deviceName", "SeamfixTab");
				capabilities.setCapability("platformName", "Android");
				capabilities.setCapability("appPackage", "com.sf.biocapture.activity");
				capabilities.setCapability("appActivity", "com.sf.biocapture.activity.SplashScreenActivity");

				driver.set(new AndroidDriver(new URL("http://0.0.0.0:4723/wd/hub"), capabilities));
				System.out.println("++++++++++UIAUTOMATOR DRIVER INSTANCE RUNNING++++++++++++");

			}
		}
		ExtentTest parent = reports.createTest(getClass().getName());
		parentTest.set(parent);
	}

	@Parameters ({"dataEnv"})
	@Test
	public static void Login(String dataEnv) throws InterruptedException, FileNotFoundException, IOException, ParseException {
		WebDriverWait wait = new WebDriverWait(getDriver(), 50);
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resource/" + dataEnv + "/data.conf.json"));
		JSONObject envs = (JSONObject) config.get("Login");

		String valid_username = (String) envs.get("valid_username");
		String valid_password = (String) envs.get("valid_password");

		String validUsernameValidPassword = "Login with a valid username: " + valid_username 	+ " and valid password: "+ valid_password;
		Markup v = MarkupHelper.createLabel(validUsernameValidPassword, ExtentColor.BLUE);
		testInfo.get().info(v);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/otp_login")));
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/otp_login")).click();
		wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/login_username")));
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/login_username")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/login_username")).sendKeys(valid_username);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/login_password")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/login_password")).sendKeys(valid_password);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/submit")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Home']")));
		TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Home']", "Home");
	}
	@Parameters ({"dataEnv"})
	@Test
	public static void Login1(String dataEnv, String valid_username, String valid_password) throws Exception {
		WebDriverWait wait = new WebDriverWait(getDriver(), 50);
	
		String validUsernameValidPassword = "Login with a valid username: " + valid_username + " and valid password: "  + valid_password;
		Markup v = MarkupHelper.createLabel(validUsernameValidPassword, ExtentColor.BLUE);
		testInfo.get().info(v);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/otp_login")));
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/otp_login")).click();
		wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/login_username")));
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/login_username")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/login_username")).sendKeys(valid_username);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/login_password")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/login_password")).sendKeys(valid_password);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/submit")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Home']")));
		TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Home']", "Home");
	}
}
