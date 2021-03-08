package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import com.aventstack.extentreports.reporter.ExtentSparkReporter;
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
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class TestBase {
	
	String devices;
	static String[] udid;

	@SuppressWarnings("rawtypes")
	public static ThreadLocal<AndroidDriver> driver = new ThreadLocal<>();
	public static ExtentReports reports;
	public static ExtentSparkReporter htmlReporter;
	private static ThreadLocal<ExtentTest> parentTest = new ThreadLocal<ExtentTest>();
	public static ThreadLocal<ExtentTest> testInfo = new ThreadLocal<ExtentTest>();
	public static String gridUrl = System.getProperty("grid-url", "https:simregtest.gloworld.com");
	public static String toAddress;

	public static String userName = "USERNAME";
	public static String accessKey = "ACCESS_KEY";
	public String local = "local";
	public String remoteJenkins = "remote-jenkins";
	public String remoteBrowserStack = "remote-browserStack";
	public static String Id = "glo";

	@SuppressWarnings("rawtypes")
	public static AndroidDriver getDriver() {
		return driver.get();
	}

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

		htmlReporter = new ExtentSparkReporter(new File(System.getProperty("user.dir") + groupReport));
		//htmlReporter.loadXMLConfig(new File(System.getProperty("user.dir") + "/resources/extent-config.xml"));
		reports = new ExtentReports();
		reports.setSystemInfo("Test Environment", myUrl(dataEnv));
		reports.attachReporter(htmlReporter);

	}

	@BeforeMethod(description = "fetch test cases name")
	public void register(Method method) {

		getDriver().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		ExtentTest child = parentTest.get().createNode(method.getName());
		testInfo.set(child);
		testInfo.get().assignCategory("Sanity");
	}

	@AfterMethod(description = "to display the result after each test method")
	public void captureStatus(ITestResult result) throws IOException {

		if (result.getStatus() == ITestResult.FAILURE) {
			String screenshotPath = TestUtils.addScreenshot();
            testInfo.get().addScreenCaptureFromBase64String(screenshotPath);
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
		SendMail.ComposeGmail("BioSmart Android Glo Report <seamfix.test.report@gmail.com>", toAddress);
		getDriver().quit();
	}

	@AfterClass
	public void closeApp() {
		getDriver().quit();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@BeforeClass
	@Parameters({ "systemPort", "deviceNo", "server","deviceName", "testConfig" })
	public void startApp(String systemPort, int deviceNo, String server, String deviceName, String testConfig) throws Exception {

		if (server.equals(remoteBrowserStack)) {
			File path = null;
			File classpathRoot = new File(System.getProperty("user.dir"));
			path = new File(classpathRoot, "resources/conf/"+testConfig); 
			System.out.println(path);
			JSONParser parser = new JSONParser();
	        JSONObject config = (JSONObject) parser.parse(new FileReader(path));
	        JSONObject envs = (JSONObject) config.get("environments");

	        DesiredCapabilities capabilities = new DesiredCapabilities();

			Map<String, String> envCapabilities = (Map<String, String>) envs.get(deviceName);
	        Iterator it = envCapabilities.entrySet().iterator();
	        while (it.hasNext()) {
	            Map.Entry pair = (Map.Entry)it.next();
	            capabilities.setCapability(pair.getKey().toString(), pair.getValue().toString());
	        }
	        
	        Map<String, String> commonCapabilities = (Map<String, String>) config.get("capabilities");
	        it = commonCapabilities.entrySet().iterator();
	        while (it.hasNext()) {
	            Map.Entry pair = (Map.Entry)it.next();
	            if(capabilities.getCapability(pair.getKey().toString()) == null){
	                capabilities.setCapability(pair.getKey().toString(), pair.getValue().toString());
	            }
	        }

	        String username = System.getenv("BROWSERSTACK_USERNAME");
	        if(username == null) {
	            username = (String) config.get("user");
	        }

	        String accessKey = System.getenv("BROWSERSTACK_ACCESS_KEY");
	        if(accessKey == null) {
	            accessKey = (String) config.get("key");
	        }

	        String app = System.getenv("BROWSERSTACK_APP_ID");
	        if(app != null && !app.isEmpty()) {
	          capabilities.setCapability("app", app);
	        }

	        if(capabilities.getCapability("browserstack.local") != null && capabilities.getCapability("browserstack.local") == "true"){
	        	
	        }

	        
			driver.set(new AndroidDriver<AndroidElement>(
					new URL("https://" + userName + ":" + accessKey + "@hub-cloud.browserstack.com/wd/hub"), capabilities));

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
				capabilities.setCapability("noReset", true);
				capabilities.setCapability("fullReset", false);

				capabilities.setCapability(AndroidMobileCapabilityType.SYSTEM_PORT, systemPort);
				capabilities.setCapability(MobileCapabilityType.UDID, udid[deviceNo].trim());
				capabilities.setCapability("deviceName", "SeamfixTab");
				capabilities.setCapability("platformName", "Android");
				capabilities.setCapability("appPackage", "com.sf.biocapture.activity." +Id);
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
				capabilities.setCapability("noReset", true);
				capabilities.setCapability("fullReset", false);

				capabilities.setCapability("deviceName", "SeamfixTab");
				capabilities.setCapability("platformName", "Android");
				capabilities.setCapability("appPackage", "com.sf.biocapture.activity." +Id);
				capabilities.setCapability("appActivity", "com.sf.biocapture.activity.SplashScreenActivity");

				driver.set(new AndroidDriver(new URL("http://0.0.0.0:4723/wd/hub"), capabilities));
				System.out.println("++++++++++UIAUTOMATOR DRIVER INSTANCE RUNNING++++++++++++");

			}
		}
		ExtentTest parent = reports.createTest(getClass().getName());
		parentTest.set(parent);
	}

	@Parameters ({"dataEnv"})
	public static void LoginLogic(String dataEnv, String jValue) throws FileNotFoundException, IOException, ParseException, InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), 60);
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resource/" + dataEnv + "/data.conf.json"));
		JSONObject envs = (JSONObject) config.get(jValue);

		String valid_username = (String) envs.get("valid_username");
		String valid_password = (String) envs.get("valid_password");

		TestUtils.testTitle("Login with a valid username: ( " + valid_username 	+ " ) and valid password: ( "+ valid_password + " )");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity." + Id + ":id/otp_login")));
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/otp_login")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity." + Id + ":id/login_username")));
		
		// Select Login mode
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/login_mode_types_spinner")).click();
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Biosmart']")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/login_username")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/login_username")).sendKeys(valid_username);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/login_password")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/login_password")).sendKeys(valid_password);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/submit")).click();
		
	}
	
	@Parameters ({"dataEnv"})
	@Test
	public static void Login(String dataEnv) throws InterruptedException, FileNotFoundException, IOException, ParseException {
		WebDriverWait wait = new WebDriverWait(getDriver(), 50);
		LoginLogic(dataEnv, "Login");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Home']")));
		TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Home']", "Home");
	}

	@Test
	public static void Login1(String valid_username, String valid_password) throws Exception {
		WebDriverWait wait = new WebDriverWait(getDriver(), 50);
	
		TestUtils.testTitle("Login with a valid username: ( " + valid_username + " ) and valid password: ( "  + valid_password + " )");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity." + Id + ":id/otp_login")));
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/otp_login")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity." + Id + ":id/login_username")));
		
		// Select Login mode
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/login_mode_types_spinner")).click();
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Biosmart']")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/login_username")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/login_username")).sendKeys(valid_username);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/login_password")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/login_password")).sendKeys(valid_password);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/submit")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Home']")));
		TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Home']", "Home");
	}
	
	@Test
	public static void navigateToCaptureMenuTest() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), 30);
		
		// Navigate to Registration Type
		TestUtils.testTitle("Navigate to Registration Type");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Home']")));
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/button_start_capture")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity." + Id + ":id/reg_type_placeholder")));
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity." + Id + ":id/reg_type_placeholder","Registration Type");
	}

	@Test
	public static void verifyNINTest(String nin, String ninVerificationMode) throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), 30);

		//Proceed to NIN Verification View
		TestUtils.testTitle("Select NIN Verification Mode: "+ninVerificationMode);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/alertTitle")));
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity.glo:id/alertTitle", "NIN Verification");

		//Select NIN Verification Type
		getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/verification_modes")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/alertTitle")));
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='"+ninVerificationMode+"']")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/alertTitle")));
		getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/proceed")).click();

		//Search by NIN Modal
		TestUtils.testTitle("Click on Search without supplying NIN");
		getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/nin")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/capture_button")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/error_text")));
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity.glo:id/error_text", "Only numbers and minimum of 11 characters are allowed");

		TestUtils.testTitle("Search NIN with less than 11 digits: 11111");
		getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/nin")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/nin")).sendKeys("11111");
		getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/capture_button")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/error_text")));
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity.glo:id/error_text", "Only numbers and minimum of 11 characters are allowed");

		TestUtils.testTitle("Search by NIN: "+nin);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/title")));
		TestUtils.assertSearchText("ID", "android:id/title", "Search By Nin");
		getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/nin")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/nin")).sendKeys(nin);
		getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/capture_button")).click();

		//NIN Details View
		TestUtils.testTitle("Confirm the searched NIN is returned: "+nin);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/title")));
		TestUtils.assertSearchText("ID", "android:id/title", "NIN Details");
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity.glo:id/nin", nin);

		//Proceed
		getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/proceed_button")).click();

	}
}
