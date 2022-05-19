package demographics;

import db.ConnectDB;
import freemarker.core.Environment;
import io.appium.java_client.android.AndroidKeyCode;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.interactions.touch.TouchActions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import org.testng.annotations.Test;
import utils.Asserts;
import utils.TestBase;
import utils.TestUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class Features extends TestBase {
	
	private static StringBuffer verificationErrors = new StringBuffer();

	private static String valid_msisdn;
	private static String invalid_msisdn;
	private static String valid_simSerial;
	private static String lga;
	private static String valid_fixed_msisdn;
	private static String valid_fixed_serial;
	private static String invalidPrefixMsisdn;
	private static String invalidPrefixSimSerial;

	private static String valid_msisdn2;
	private static String valid_simSerial2;
	private static String nin;
	private static String ninVerificationMode;
	private static String non_valid_msisdn;
	private static String invalid_simSerial;
	

	static int totalSubVal=0;static int totalSyncsentVal = 0;static int totalSyncpendingVal = 0;static int totalSynConfVal = 0;static int totalRejectVal = 0;

	@Parameters({ "dataEnv"})
	@Test
	public static void logOutUser(String dataEnv, String valid_username) throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), 60);
		// Log out
		TestUtils.testTitle("Logout username: "  + valid_username);
		getDriver().findElement(By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
		getDriver().findElement(By.id("android:id/button2")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']")).click();
		Thread.sleep(500);
		TestUtils.scrollDown();
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Logout']")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/message")));
		TestUtils.assertSearchText("ID", "android:id/message", "   Log out?");
		getDriver().findElement(By.id("android:id/button3")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/otp_login")));
		Thread.sleep(500);
	}
	
	@Test
	public static void captureReportRecords() throws InterruptedException {

		WebDriverWait wait = new WebDriverWait(getDriver(), 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Total Subscribers']")));
		String totalSub = getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/kpi_report_value")).getText();
		totalSubVal = TestUtils.convertToInt(totalSub);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Total Sync Sent']")));

		String totalSyncsent = getDriver().findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/androidx.drawerlayout.widget.DrawerLayout/android.widget.RelativeLayout/android.widget.FrameLayout[1]/androidx.viewpager.widget.ViewPager/android.widget.RelativeLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.TextView")).getText();
		totalSyncsentVal = TestUtils.convertToInt(totalSyncsent);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Total Sync Pending']")));

		String totalSyncpending = getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/kpi_report_value")).getText();
		totalSyncpendingVal = TestUtils.convertToInt(totalSyncpending);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Total Sync Confirmed']")));

		String totalSynConf = getDriver().findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/androidx.drawerlayout.widget.DrawerLayout/android.widget.RelativeLayout/android.widget.FrameLayout[1]/androidx.viewpager.widget.ViewPager/android.widget.RelativeLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.TextView")).getText();
		totalSynConfVal = TestUtils.convertToInt(totalSynConf);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Total Rejected']")));

		String totalReject = getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/kpi_report_value")).getText();
		totalRejectVal = TestUtils.convertToInt(totalReject);
		Thread.sleep(3000);

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

		@Parameters({ "dataEnv"})
		@Test
		public static void msisdnValidationOnline(String dataEnv, String regModule) throws Exception {
			WebDriverWait wait = new WebDriverWait(getDriver(), 60);
			JSONParser parser = new JSONParser();
			JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resource/" + dataEnv + "/data.conf.json"));
			JSONObject envs = (JSONObject) config.get("NewRegistration");
    
			valid_msisdn = (String) envs.get("valid_msisdn");
			invalid_msisdn = (String) envs.get("invalid_msisdn");
			valid_simSerial = (String) envs.get("valid_simSerial");
			invalid_simSerial = (String) envs.get("invalid_simSerial");
			lga = (String) envs.get("lga");
			valid_fixed_msisdn = (String) envs.get("valid_fixed_msisdn");
			valid_fixed_serial = (String) envs.get("valid_fixed_serial");
			invalidPrefixMsisdn = (String) envs.get("invalidPrefixMsisdn");
			invalidPrefixSimSerial = (String) envs.get("invalidPrefixSimSerial");

			valid_msisdn2 = (String) envs.get("valid_msisdn2");
			valid_simSerial2 = (String) envs.get("valid_simSerial2");
			nin = (String) envs.get("nin");
			ninVerificationMode = (String) envs.get("ninVerificationMode");
			non_valid_msisdn = (String) envs.get("non_valid_msisdn");


		if (regModule.equals("NMS")) {
			// Select Msisdn Category
			TestUtils.testTitle("To confirm that the default category for registration is MOBILE");
			TestUtils.assertSearchText("XPATH", "//android.widget.CheckedTextView[@text='Mobile']", "Mobile");
			Thread.sleep(1000);

			TestUtils.testTitle("Select Msisdn Category");
			
			// Select Msisdn Category
			TestUtils.testTitle("Select Mobile Msisdn Category");
			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnCategorySpinner")).click();
			Thread.sleep(1000);
			getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Fixed']")).click();
			Thread.sleep(1000);
			TestUtils.assertSearchText("XPATH", "//android.widget.CheckedTextView[@text='Fixed']", "Fixed");
			Thread.sleep(1000);

			//To confirm that the FIXED category of registration can only accept 9 digits for msisdn
			String priCharacters=valid_fixed_msisdn+"1234";
			TestUtils.testTitle("To confirm that the FIXED category of registration can only accept 9 digits for msisdn  ("+priCharacters+")");
			getDriver().findElement(By.id("android:id/text1")).click();
			getDriver().findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.ListView/android.widget.CheckedTextView[2]")).click();
			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).clear();
			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).sendKeys(valid_fixed_msisdn);
			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).clear();
			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).sendKeys(priCharacters);
			TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/msisdnField", valid_fixed_msisdn);

			
			TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='MSISDN Category']", "MSISDN Category");
			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnCategorySpinner")).click();
			Thread.sleep(500);
			getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Mobile']")).click();
			Thread.sleep(500);
			
			//To confirm that the MOBILE category of registration can only accept 11 digits for msisdn
			String moreCharacters=valid_msisdn+"1234";
			TestUtils.testTitle("To confirm that the MOBILE category of registration can only accept 11 digits for msisdn  ("+moreCharacters+")");
			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).clear();
			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).sendKeys(valid_msisdn);
			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).clear();
			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).sendKeys(moreCharacters);
			TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/msisdnField", valid_msisdn);
			
			// To confirm error message for invalid combination of MSISDN AND SIM SERIAL
			TestUtils.testTitle("To confirm error message for invalid combination of MSISDN AND SIM SERIAL:"+invalidPrefixSimSerial+ " and Valid MSISDN: " + valid_msisdn);
			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).clear();
			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).sendKeys(valid_msisdn);
			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/simSerialField")).clear();
			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/simSerialField")).sendKeys(invalidPrefixSimSerial);
			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/addMsisdnSimSerialButton")).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/button1")));
			TestUtils.assertSearchText("ID", "android:id/message", "Msisdn and Sim serial combination is invalid");
			getDriver().findElement(By.id("android:id/button1")).click();
			Thread.sleep(1000);

			// To confirm error message when invalid SIMSERIAL  is inputed by user
			TestUtils.testTitle("To confirm error message when invalid SIMSERIAL  is inputed by user:"+invalidPrefixSimSerial+ " and Valid MSISDN: " + valid_msisdn);
			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).clear();
			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).sendKeys(invalidPrefixMsisdn);
			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/simSerialField")).clear();
			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/simSerialField")).sendKeys(invalidPrefixSimSerial);
			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/addMsisdnSimSerialButton")).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
			TestUtils.assertSearchText("ID", "android:id/message", "The MSISDN has an unrecognized National Destination Code. "
					+ "Please ensure the MSISDN starts with any of the following NDCs: "
					+ "0701,0708,0802,0808,0812,0901,0902,0904,0907,0809,0817,0818,0908,"
					+ "0909,0705,0805,0807,0811,0815,0905,0915,0703,0706,0803,0806,0810,"
					+ "0813,0814,0816,0903,0906 and must be 11 digits");
			getDriver().findElement(By.id("android:id/button1")).click();
			Thread.sleep(1000);

			//Check that the user is not allowed to exceed the specified max SIMSERIAL length
			moreCharacters=valid_simSerial+"1234";
			TestUtils.testTitle("Check that the user is not allowed to exceed the specified max SIMSERIAL length  ("+moreCharacters+")");
			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/simSerialField")).clear();
			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/simSerialField")).sendKeys(moreCharacters);
			TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/simSerialField", valid_simSerial);

			// Check that the user is not allowed to go below the specified min SIMSERIAL length
			TestUtils.testTitle("Check that the user is not allowed to go below the specified min SIMSERIAL length:"+invalid_simSerial+ " and Valid MSISDN: " + valid_msisdn);
			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).clear();
			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).sendKeys(valid_msisdn);
			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/simSerialField")).clear();
			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/simSerialField")).sendKeys(invalid_simSerial);
			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/addMsisdnSimSerialButton")).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
			TestUtils.assertSearchText("ID", "android:id/message", "Sim Serial format is invalid. SIM Serial should be 19 numbers with 'F' at the end.");
			getDriver().findElement(By.id("android:id/button1")).click();
			Thread.sleep(1000);

	

			// Proceed after supplying single msisdns and sim serial
			TestUtils.testTitle("Proceed after supplying single msisdn: (" + valid_msisdn + ") and (" + valid_simSerial + ") for validation");
			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).clear();
			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).sendKeys(valid_msisdn);
			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/simSerialField")).clear();
			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/simSerialField")).sendKeys(valid_simSerial);
			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/addMsisdnSimSerialButton")).click();
			Thread.sleep(2000);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
			Thread.sleep(2000);
			TestUtils.assertSearchText("ID", "android:id/message", "Msisdn is valid");
			Thread.sleep(2000);
			getDriver().findElement(By.id("android:id/button1")).click();
	
			TestUtils.testTitle("Assert Number");
			TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/phone_no_view", valid_msisdn);
			TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/tv_sim_serial", valid_simSerial);
			}


	}
		@Parameters({ "dataEnv"})
		@Test
		public static void selectCountry(String dataEnv, String country) throws Exception {
			WebDriverWait wait = new WebDriverWait(getDriver(), 30);
			//Select country
			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/countryOfOriginSpinner")).click();
			Thread.sleep(500);
			getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='"+country+"']")).click();
			Thread.sleep(500);
			
//			try {
//				getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/btnCapturePortrait")).click();
//				getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/captureButton")).click();
//
//				}catch (Exception e){
//
//			}

		}

	        
	        
		public static void selectRegistration(String dataEnv, String regModule) throws InterruptedException, FileNotFoundException, IOException, ParseException {
	   		WebDriverWait wait = new WebDriverWait(getDriver(), 60);

	   		JSONParser parser = new JSONParser();
	   		JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resource/" + dataEnv + "/data.conf.json"));
	   		JSONObject envs = (JSONObject) config.get("NewRegistration");
	   		String lga = (String) envs.get("lga");


	   	      
	   		TestUtils.testTitle("To confirm that a user can perform "+regModule+" when it is  in the list of available use case settings and user has privilege" );

	   	// Select LGA of Registration
	   		TestUtils.testTitle("Select LGA of Registration: " + lga);
	   		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/lga_of_reg")).click();
	   		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/alertTitle")));
	   		TestUtils.assertSearchText("ID", "android:id/alertTitle", "LGA of Registration*");
	   		Thread.sleep(1000);
	   		getDriver().findElement(By.xpath("//android.widget.TextView[@text='" + lga + "']")).click();
	   		Thread.sleep(500);

	   		// Select Registration Type
	   		TestUtils.testTitle("Select "+regModule);
	   		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/typeofreg")));
	   		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/typeofreg")).click();
	   		Thread.sleep(500);
	   		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/alertTitle", "Select Registration Type");
	   		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='"+regModule+"']")).click();
	   		Thread.sleep(500);
	   		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/next_button")).click();
	   		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/pageTitle")));
	   		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/pageTitle", regModule);

		}
		
		@Parameters({ "dataEnv"})
		@Test
		public static void msisdnMultipleValidationTest(String dataEnv, String RegModule) throws Exception {
			WebDriverWait wait = new WebDriverWait(getDriver(), 30);
			
			
			if(getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/delete_button")).isDisplayed()){
				getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/delete_button")).click();
			}
			Thread.sleep(500);
			//Add First Number
			TestUtils.testTitle("Add First Number : (" + valid_msisdn + ") and (" + valid_simSerial + ") for validation");
			msisdnValidationOnline(dataEnv,RegModule);	
			TestUtils.testTitle("Add Second Number : (" + valid_msisdn2 + ") and (" + valid_simSerial2 + ") for validation");
						getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).clear();
			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).sendKeys(valid_msisdn2);
			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/simSerialField")).clear();
			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/simSerialField")).sendKeys(valid_simSerial2);
			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/addMsisdnSimSerialButton")).click();
				Thread.sleep(1000);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
			TestUtils.assertSearchText("ID", "android:id/message", "Msisdn is valid");
			getDriver().findElement(By.id("android:id/button1")).click();

			TestUtils.scrollDown();

			TestUtils.testTitle("Assert First Number");
			TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/phone_no_view", valid_msisdn);
			TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/tv_sim_serial", valid_simSerial);

			TestUtils.testTitle("Assert Second Number");
			TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='" + valid_msisdn2 + "']", valid_msisdn2);
			TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='" + valid_simSerial2 + "']", valid_simSerial2);
			Thread.sleep(500);
		
			
		}
		
	 }
	
		
	
	
	
	

