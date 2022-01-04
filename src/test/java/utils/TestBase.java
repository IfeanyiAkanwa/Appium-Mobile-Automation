package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
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
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.HashMap;
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
	public static String serviceUrl = "https://kycphase2test.seamfix.com:8195";
	public static String Id = ".glo";
	public static int waitTime = 60;
	public static boolean releaseRegItem=false;

	@SuppressWarnings("rawtypes")
	public static AndroidDriver getDriver() {
		return driver.get();
	}

	 @Parameters ("dataEnv")
		public static String myUrl(String dataEnv) throws IOException, ParseException {
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
	public void setUp(String groupReport, String dataEnv) throws IOException, ParseException {

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

//		htmlReporter = new ExtentHtmlReporter(new File(System.getProperty("user.dir") + groupReport));  //Old extent reporter
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
		//getDriver().quit();
	}

	@AfterClass
	public void closeApp() {
		getDriver().quit();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@BeforeClass
	@Parameters({ "systemPort", "deviceNo", "server","deviceName", "testConfig", "settings"})
	public void startApp(String systemPort, int deviceNo, String server, String deviceName, String testConfig, boolean settings) throws Exception {

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
				capabilities.setCapability("appPackage", "com.sf.biocapture.activity" +Id);
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
				capabilities.setCapability("appPackage", "com.sf.biocapture.activity" +Id);
				capabilities.setCapability("appActivity", "com.sf.biocapture.activity.SplashScreenActivity");

				driver.set(new AndroidDriver(new URL("http://0.0.0.0:4723/wd/hub"), capabilities));
				System.out.println("++++++++++UIAUTOMATOR DRIVER INSTANCE RUNNING++++++++++++");

			}
		}

		if (settings==true){

		}else {
			ExtentTest parent = reports.createTest(getClass().getName() + "\n" + TestUtils.getDeviceInfo(udid[deviceNo].trim()));
			parentTest.set(parent);
		}
	}

	@Parameters ({"dataEnv"})
	public static void LoginLogic(String dataEnv, String jValue) throws IOException, ParseException, InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), 60);
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resource/" + dataEnv + "/data.conf.json"));
		JSONObject envs = (JSONObject) config.get(jValue);

		String valid_username = (String) envs.get("valid_username");
		String valid_password = (String) envs.get("valid_password");

		TestUtils.testTitle("Login with a valid username: ( " + valid_username 	+ " ) and valid password: ( "+ valid_password + " )");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/otp_login")));
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/otp_login")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/login_username")));
		
		// Select Login mode
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/login_mode_types_spinner")).click();
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Biosmart']")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/login_username")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/login_username")).sendKeys(valid_username);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/login_password")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/login_password")).sendKeys(valid_password);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/submit")).click();
		
	}
	
	@Parameters ({"dataEnv"})
	@Test
	public static void Login(String dataEnv) throws InterruptedException, IOException, ParseException {
		WebDriverWait wait = new WebDriverWait(getDriver(), 60);
		LoginLogic(dataEnv, "Login");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Home']")));
		TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Home']", "Home");
	}

	@Test
	public static void Login1(String valid_username, String valid_password) throws Exception {
		WebDriverWait wait = new WebDriverWait(getDriver(), 40);
	
		TestUtils.testTitle("Login with a valid username: ( " + valid_username + " ) and valid password: ( "  + valid_password + " )");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/otp_login")));
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/otp_login")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/login_username")));
		
		// Select Login mode
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/login_mode_types_spinner")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Biosmart']")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/login_username")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/login_username")).sendKeys(valid_username);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/login_password")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/login_password")).sendKeys(valid_password);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/submit")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Home']")));
		TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Home']", "Home");
	}

	@Test
	public static void logOut(String valid_username){
		WebDriverWait wait = new WebDriverWait(getDriver(), 30);
		//Logs out
		String logOut = "Logout" + "(" + valid_username + ")";
		Markup o = MarkupHelper.createLabel(logOut, ExtentColor.BLUE);
		testInfo.get().info(o);
		getDriver().findElement(By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']")).click();
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/design_menu_item_text")));
		TestUtils.scrollDown();
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Logout']")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/message")));
		TestUtils.assertSearchText("ID", "android:id/message", "   Log out?");
		getDriver().findElement(By.id("android:id/button3")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/otp_login")));
	}
	
	@Test
	public static void navigateToCaptureMenuTest() {
		WebDriverWait wait = new WebDriverWait(getDriver(), 5);
		
		// Navigate to Registration Type
		TestUtils.testTitle("Navigate to Registration Type");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Home']")));
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/button_start_capture")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/reg_type_placeholder")));
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/reg_type_placeholder","Registration Type");
	}

	@Test
	public static int verifyNINTest(String nin, String ninVerificationMode) throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), 25);
		//ninStatus is set to available automatically
		int ninStatus=1;
		//Proceed to NIN Verification View
		TestUtils.testTitle("Select NIN Verification Mode: "+ninVerificationMode);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/alertTitle", "NIN Verification");

		//Select NIN Verification Type
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/verification_modes")).click();
		//wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/alertTitle")));

		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='"+ninVerificationMode+"']")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/proceed")).click();

		//Search by NIN Modal
		TestUtils.testTitle("Click on Search without supplying NIN");
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/nin")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/capture_button")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/error_text")));
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/error_text", "Only numbers and minimum of 11 characters are allowed");

		TestUtils.testTitle("Search NIN with less than 11 digits: 11111");
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/nin")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/nin")).sendKeys("11111");
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/capture_button")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/error_text")));
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/error_text", "Only numbers and minimum of 11 characters are allowed");

		TestUtils.testTitle("Search by NIN: "+nin);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/title")));
		TestUtils.assertSearchText("ID", "android:id/title", "Search By NIN");
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/nin")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/nin")).sendKeys(nin);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/capture_button")).click();

		try{
			//NIN Details View
			TestUtils.testTitle("Confirm the searched NIN is returned: "+nin);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/title")));
			TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/nin_verification_title", "NIN Verification");
			TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/tv_compare_text", "Please compare user data before proceeding");
			TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/tv_portrait_image", "Portrait Image");

			TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/tv_live_image", "LIVE IMAGE");
			TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/tv_nimc", "NIMC");
			String userdata=getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/tv_user_data")).getText();
			if(userdata.contains("User data")){
				TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/tv_user_data", "User data");
			}else{
				TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/tv_user_data", "User Data");
			}

			TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/tv_nimc_data", "NIMC Data");

			try {
				String firstName = getDriver().findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/androidx.recyclerview.widget.RecyclerView/android.view.ViewGroup[1]/android.widget.TextView[2]")).getText();
				String Surname = getDriver().findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/androidx.recyclerview.widget.RecyclerView/android.view.ViewGroup[2]/android.widget.TextView[2]")).getText();
				String dob = getDriver().findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/androidx.recyclerview.widget.RecyclerView/android.view.ViewGroup[3]/android.widget.TextView[2]")).getText();

				//Confirm the NIMC Data
				TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Firstname']", "Firstname");
				TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Surname']", "Surname");
				TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Date of birth']", "Date of birth");
				String empty = "";
				Map<String, String> fields = new HashMap<>();
				fields.put("Firstname", firstName);
				fields.put("Surname", Surname);
				fields.put("Date of birth", dob);

				for (Map.Entry<String, String> entry : fields.entrySet()) {
					try {
						Assert.assertNotEquals(entry.getValue(), empty);
						Assert.assertNotEquals(entry.getValue(), null);
						testInfo.get().log(Status.INFO, "<b>" + entry.getKey() + " : </b>" + entry.getValue());
					} catch (Error ee) {
						testInfo.get().error("<b>" + entry.getKey() + " : </b>" + entry.getValue());
					}

				}
			}catch (Exception e){
				testInfo.get().error("NIMC AND SIM REG MISMACTH");
				TestUtils.addScreenshot();
			}

			//Proceed
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/accept_button")));
			Thread.sleep(2500);
			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/accept_button")).click();

		}catch (Exception e){
            System.out.println(e);
			//Nin is not available
			ninStatus=0;
			Thread.sleep(1000);
			try {
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/button1")));
				getDriver().findElement(By.id("android:id/button1")).click();
				getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/continue_btn")).click();

				Thread.sleep(1000);
				getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/nin")).sendKeys(nin);
				Thread.sleep(1000);
				getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/proceed_button")).click();
			}catch (Exception e1){
				System.out.println(e1);
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/continue_btn")));
				getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/continue_btn")).click();

				Thread.sleep(1000);
				getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/nin")).sendKeys(nin);
				Thread.sleep(1000);
				getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/proceed_button")).click();
			}

		}

		return ninStatus;
	}

	@Test
	public static void verifyBioMetricsTest() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), 10);
		try{
			TestUtils.scrollDown();
		}catch (Exception e){

		}
		//Proceed
		try{
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/btnCaptureBiometrics")));
			Thread.sleep(2000);
			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/btnCaptureBiometrics")).click();

		}catch (Exception e){

			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/verifyBiometricsBtn")).click();

		}

		try{
			//Select country
			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/countryOfOriginSpinner")).click();
			Thread.sleep(500);
			getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='NIGERIA']")).click();
			Thread.sleep(500);
		}catch (Exception e){

		}

		try{
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/captureButton")));

			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/captureButton")).click();
		}catch (Exception e){

			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/buttonCapturePicture")));

			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/buttonCapturePicture")).click();
		}
		Thread.sleep(3000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/message", "Subscriber's face was successfully captured");
		getDriver().findElement(By.id("android:id/button1")).click();
		Thread.sleep(500);

		//Fingerprint capture/

		//Submit without overriding fingerprint
		TestUtils.testTitle("Save Enrollment without overriding fingerprint");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/btn_multi_capture")));
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/btn_multi_capture", "Multi Capture");
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/fp_save_enrolment")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/message", "No finger was captured");
		getDriver().findElement(By.id("android:id/button1")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/fp_save_enrolment")));


		//Override left hand
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/btn_override_left")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/message")));
		TestUtils.assertSearchText("ID", "android:id/message", "Are you sure? Note that you have to provide a reason");
		getDriver().findElement(By.id("android:id/button1")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/captureButton")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/ok")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Ageing']")));
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='Ageing']")).click();

		//Submit without overriding right hand
		TestUtils.testTitle("Save Enrollment without capturing Right Hand");
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/fp_save_enrolment")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/message", "RIGHT HAND wasn't overridden, and all selected RIGHT HAND fingers were not captured.");
		getDriver().findElement(By.id("android:id/button1")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/fp_save_enrolment")));

		//Override right hand
		TestUtils.scrollDown();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/btn_override_right")));
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/btn_override_right")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/message")));
		TestUtils.assertSearchText("ID", "android:id/message", "Are you sure? Note that you have to provide a reason");
		getDriver().findElement(By.id("android:id/button1")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/captureButton")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/ok")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Ageing']")));
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='Ageing']")).click();

		//Save enrollment
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/fp_save_enrolment")));
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/fp_save_enrolment")).click();

	}

}
