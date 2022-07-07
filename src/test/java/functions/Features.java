package functions;

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
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

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
			JSONObject envs1 = (JSONObject) config.get("ReRegistration");
			JSONObject envs2 = (JSONObject) config.get("CorporateRegistration");
			JSONObject envs3 = (JSONObject) config.get("CorporateReRegistration");
    
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

	
			//Re-reg

			String invalid_msisdn = (String) envs1.get("invalid_msisdn");
			String valid_msisdn0 = (String) envs1.get("valid_msisdn");
			String invalid_OTP = (String) envs1.get("invalid_OTP");
			String msisdnLessThanSix = (String) envs1.get("msisdnLessThanSix");
			String unrecognizedMsisdn = (String) envs1.get("unrecognizedMsisdn");
			String msisdnWithFingerprint = (String) envs1.get("msisdnWithFingerprint");
			String ninVerificationMode = (String) envs1.get("ninVerificationMode");
			String nin = (String) envs1.get("nin");
			
			//corporate Reg 
			String unrecognizedFixedMsisdn = (String) envs2.get("unrecognizedFixedMsisdn");
			String valid_username = (String) envs2.get("valid_username");
			String valid_username2 = (String) envs2.get("valid_username2");
			String primary_tm = (String) envs2.get("primary_tm");
			String secondary_tm = (String) envs2.get("secondary_tm");
			String noIndividualReg = (String) envs2.get("noIndividualReg");
			String noCompanyReg = (String) envs2.get("noCompanyReg");
			String noPrivilegeUser = (String) envs2.get("noPrivilegeUser");
			String noMultiRegUser = (String) envs2.get("noMultiRegUser");
			String generalUserPassword = (String) envs2.get("generalUserPassword");
		    valid_msisdn = (String) envs2.get("valid_msisdn");
		    invalid_msisdn = (String) envs2.get("invalid_msisdn");
		    valid_simSerial = (String) envs2.get("valid_simSerial");
		    invalid_simSerial = (String) envs2.get("invalid_simSerial");
		    lga = (String) envs2.get("lga");
		    valid_fixed_msisdn = (String) envs2.get("valid_fixed_msisdn");
		    valid_fixed_serial = (String) envs2.get("valid_fixed_serial");
		    invalidPrefixMsisdn = (String) envs2.get("invalidPrefixMsisdn");
		    invalidPrefixSimSerial = (String) envs2.get("invalidPrefixSimSerial");
		    msisdnLessThanSix = (String) envs2.get("msisdnLessThanSix");
		    unrecognizedMsisdn = (String) envs2.get("unrecognizedMsisdn");

		    valid_msisdn2 = (String) envs2.get("valid_msisdn2");
		    valid_simSerial2 = (String) envs2.get("valid_simSerial2");
		    nin = (String) envs2.get("nin");
		    String nin2 = (String) envs2.get("nin2");
		    ninVerificationMode = (String) envs2.get("ninVerificationMode");
		    non_valid_msisdn = (String) envs2.get("non_valid_msisdn");
		    String totalNo;
		    String validNo;
		    String invalidNo;
		    
		    //corporate-Re-reg
		    
			String unrecognizedFixedMsisdn1 = (String) envs2.get("unrecognizedFixedMsisdn");
			
			String primary_tm1 = (String) envs3.get("primary_tm");
			String secondary_tm1 = (String) envs3.get("secondary_tm");
			String noIndividualReg1 = (String) envs3.get("noIndividualReg");
			String noCompanyReg1 = (String) envs3.get("noCompanyReg");
			String noPrivilegeUser1 = (String) envs3.get("noPrivilegeUser");
			String noMultiRegUser1 = (String) envs3.get("noMultiRegUser");
			String generalUserPassword1 = (String) envs3.get("generalUserPassword");
			String valid_msisdn1 = (String) envs3.get("valid_msisdn");
		    String invalid_msisdn1 = (String) envs3.get("invalid_msisdn");
		    valid_simSerial = (String) envs3.get("valid_simSerial");
		    invalid_simSerial = (String) envs3.get("invalid_simSerial");
		    lga = (String) envs3.get("lga");
		    valid_fixed_msisdn = (String) envs3.get("valid_fixed_msisdn");
		    valid_fixed_serial = (String) envs3.get("valid_fixed_serial");
		    invalidPrefixMsisdn = (String) envs3.get("invalidPrefixMsisdn");
		    invalidPrefixSimSerial = (String) envs3.get("invalidPrefixSimSerial");
		    msisdnLessThanSix = (String) envs3.get("msisdnLessThanSix");
		    unrecognizedMsisdn = (String) envs3.get("unrecognizedMsisdn");

		    valid_msisdn2 = (String) envs3.get("valid_msisdn2");
		    valid_simSerial2 = (String) envs3.get("valid_simSerial2");
		    nin = (String) envs3.get("nin");
		 
		    ninVerificationMode = (String) envs3.get("ninVerificationMode");
		    non_valid_msisdn = (String) envs3.get("non_valid_msisdn");


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
		    getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/btnCaptureBiometrics")).click();
			}
		
		else if (regModule.equals("RR")){

			// Proceed without supplying msisdn
			TestUtils.testTitle("Proceed without supplying msisdn");
			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/primary_msisdn_field")).clear();
			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/primary_msisdn_field")).sendKeys();
			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/submit_button")).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
			TestUtils.assertSearchText("ID", "android:id/message", "Required Input Field: Phone Number");
			getDriver().findElement(By.id("android:id/button1")).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Re Registration']")));

			//Enter MSISDN Less than 6 digits
			TestUtils.testTitle("Proceed with MSISDN Less than 6 digits");
			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/primary_msisdn_field")).clear();
			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/primary_msisdn_field")).sendKeys(msisdnLessThanSix);
			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/submit_button")).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
			TestUtils.assertSearchText("ID", "android:id/message", "Entered Phone Number is invalid. It must be either 7 digits for fixed or 11 digits for mobile.");
			getDriver().findElement(By.id("android:id/button1")).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Re Registration']")));

			//Enter Unrecognized MSISDN
			TestUtils.testTitle("Proceed with Unrecognized MSISDN: ("+ unrecognizedMsisdn +" )");
			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/primary_msisdn_field")).clear();
			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/primary_msisdn_field")).sendKeys(unrecognizedMsisdn);
			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/submit_button")).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
			TestUtils.assertSearchText("ID", "android:id/message", "The MSISDN has an unrecognized National Destination Code. Please ensure the MSISDN starts with any of the following NDCs: 0701,0708,0802,0808,0812,0901,0902,0904,0907,0809,0817,0818,0908,0909,0705,0805,0807,0811,0815,0905,0915,0703,0706,0803,0806,0810,0813,0814,0816,0903,0906");
			getDriver().findElement(By.id("android:id/button1")).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Re Registration']")));

			// Enter invalid msisdn
			TestUtils.testTitle("Enter invalid MSISDN: " + invalid_msisdn + " for Re-Registration");
			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/primary_msisdn_field")).clear();
			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/primary_msisdn_field")).sendKeys(invalid_msisdn);
			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/submit_button")).click();
			Thread.sleep(3000);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
			TestUtils.assertSearchText("ID", "android:id/message", "Record not found. ");
			getDriver().findElement(By.id("android:id/button1")).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Re Registration']")));

			// Enter valid msisdn 
			TestUtils.testTitle("Enter valid MSISDN: " + valid_msisdn0 + " for Re-Registration");
			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/primary_msisdn_field")).clear();
			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/primary_msisdn_field")).sendKeys(valid_msisdn0);
			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/submit_button")).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/capture_image_button")));
		    TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/capture_image_button", "Verify Biometrics");
			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/capture_image_button")).click();
		}
		else if(regModule.equals("CN")) {
			//	 Capture Corporate Registration
		        TestUtils.testTitle("Capture Corporate Registration");
		        Thread.sleep(500);
		        //Add Primary TM
		        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/corporateUserTypeSpinner")).click();
		        getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='PRIMARY_TM']")).click();
		        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnCategorySpinner")).click();
		        getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Mobile']")).click();
		        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/categorySpinner")).click();
		        try{
		            getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='CORPORATE']")).click();
		        }catch (Exception e){
		            getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Corporate']")).click();
		        }
		        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).clear();
		        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).sendKeys(primary_tm);
		        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/addMsisdnButton")).click();
		        TestUtils.assertSearchText("ID", "android:id/message", "PRIMARY_TM MSISDN is Valid");
		        getDriver().findElement(By.id("android:id/button1")).click();
		
//		        //Add Secondary TM
//		        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/corporateUserTypeSpinner")).click();
//		        getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='SECONDARY_TM']")).click();
//		        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).clear();
//		        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).sendKeys(secondary_tm);
//		        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/addMsisdnButton")).click();
//		        Thread.sleep(500);
//		        getDriver().findElement(By.id("android:id/button1")).click();
//		        Thread.sleep(500);
//		        getDriver().findElement(By.id("android:id/button1")).click();
		
		        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/addChildMsisdnButton")).click();
		        String info=getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/tv_info")).getText();
		        if (info.contains("Click the download button to get the template file. It will be saved in Download folder. NOTE: The following fields are compulsory; MSISDN, SIM Serial, NIN, MSISDN Category and User Category (Possible values are PRIMARY_TM, SECONDARY_TM, CHILD).")){
		            testInfo.get().info(info);
		        }
		        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/downloadCsvTempBtn", "Download Bulk Template");
		        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/downloadCsvTempBtn")).click();
		        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/uploadButton")).click();
		        TestUtils.scrollUntilElementIsVisible("XPATH", "//android.widget.TextView[@text='Download']");
		        getDriver().findElement(By.xpath("//android.widget.TextView[@text='Download']")).click();
		        Thread.sleep(1000);
		        getDriver().findElement(By.xpath("//android.widget.TextView[@text='CorporateRegistration.csv']")).click();
		        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
		        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/alertTitle", "First Level Check Complete");
		        totalNo=getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/tv_total")).getText();
		        testInfo.get().info(totalNo);
		        validNo=getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/tv_valid")).getText();
		        testInfo.get().info(validNo);
		        invalidNo=getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/tv_invalid")).getText();
		        testInfo.get().info(invalidNo);
		       // TestUtils.assertSearchText("ID", "android:id/button1", "DOWNLOAD");
		        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity.glo:id/bt_proceed", "Proceed");
		        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/bt_proceed")).click();
		       
		        Thread.sleep(10000);
		
		      
		        
		        //Child MSISDN check
		        
		        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/alertTitle", "Corporate New Registration");
		        String validChild=getDriver().findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/androidx.appcompat.widget.LinearLayoutCompat/android.widget.FrameLayout/android.widget.ListView/android.widget.TextView[1]")).getText();
		        testInfo.get().info(validChild);
		        String inValidChild=getDriver().findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/androidx.appcompat.widget.LinearLayoutCompat/android.widget.FrameLayout/android.widget.ListView/android.widget.TextView[2]")).getText();
		        testInfo.get().info(inValidChild);
		        getDriver().findElement(By.id("android:id/button1")).click();
		        

				
		        //Second Level Check
		        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
		        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/alertTitle", "Second Level Check Complete");
		        try {
		        	getDriver().findElement(By.id("android:id/button1")).click();
		        }
		        catch(Exception e) {
		        	TestUtils.assertSearchText("ID", "com.sf.biocapture.activity.glo:id/bt_proceed", "Proceed");
		        	getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/bt_proceed")).click();
		        }
		
		        //To confirm that the Child Msisdn button is greyed out after successful upload
		        TestUtils.testTitle("To confirm that the Child Msisdn button is greyed out after successful upload");
		        try{
		            getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/addChildMsisdnButton")).click();
		            testInfo.get().error("addChil0dMsisdnButton found after validation");
		            Thread.sleep(1000);
		            getDriver().pressKeyCode(AndroidKeyCode.BACK);
		        }catch (Exception e){
		            testInfo.get().info("addChildMsisdnButton is removed");
		        }
		
		
		        //Proceed to next
		        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/corporateUserTypeSpinner")).click();
		        Thread.sleep(500);
		        getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='PRIMARY_TM']")).click();
		        Thread.sleep(500);
		        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/nextButton")).click();
		        Thread.sleep(500);
		   
		}	
		else if(regModule.equals("CR")) {
	        TestUtils.testTitle("To confirm that there is an MSISDN Category dropdown");
	        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/corporateUserTypeSpinner")).click();
	        TestUtils.assertSearchText("XPATH", "//android.widget.CheckedTextView[@text='PRIMARY_TM']", "PRIMARY_TM");
	        TestUtils.assertSearchText("XPATH", "//android.widget.CheckedTextView[@text='SECONDARY_TM']", "SECONDARY_TM");
	        getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='PRIMARY_TM']")).click();
	        Thread.sleep(500);
	
	        //To confirm that the MSISDN category is a dropdown containing Mobile and Fixed
	        TestUtils.testTitle("To confirm that the MSISDN category is a dropdown containing Mobile and Fixed");
	        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnCategorySpinner")).click();
	        TestUtils.assertSearchText("XPATH", "//android.widget.CheckedTextView[@text='Mobile']", "Mobile");
	        TestUtils.assertSearchText("XPATH", "//android.widget.CheckedTextView[@text='Fixed']", "Fixed");
	        getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Mobile']")).click();
	        Thread.sleep(500);
	
	        //To confirm that the corporate category is a dropdown containing Internet of things(IOT) and Corporate
	        TestUtils.testTitle("To confirm that the corporate category is a dropdown containing Internet of things(IOT) and Corporate");
	        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/corporateCategorySpinner")).click();
	        Thread.sleep(800);
	        TestUtils.assertSearchText("XPATH", "//android.widget.CheckedTextView[@text='CORPORATE']", "CORPORATE");
	        // TestUtils.assertSearchText("XPATH", "//android.widget.CheckedTextView[@text='IOT']", "IOT");
	        getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='CORPORATE']")).click();
	        Thread.sleep(500);
	
	
	        //To confirm that the MSISDN field is not a dropdown
	        TestUtils.testTitle("To confirm that the MSISDN field is not a dropdown ");
	        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/msisdnField", "Enter Phone Number");
	
	        //Enter MSISDN Less than 6 digits
	        TestUtils.testTitle("Proceed with MSISDN Less than 6 digits");
	        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).clear();
	        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).sendKeys(msisdnLessThanSix);
	        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/checkBtn")).click();
	        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
	        TestUtils.assertSearchText("ID", "android:id/message", "Entered Mobile MSISDN is invalid. Entered value should be 11 digits");
	        getDriver().findElement(By.id("android:id/button1")).click();
	        
	
	        //Enter Unrecognized MSISDN
	        TestUtils.testTitle("Proceed with Unrecognized MSISDN: (" + unrecognizedMsisdn + " )");
	        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).clear();
	        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).sendKeys(unrecognizedMsisdn);
	        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/checkBtn")).click();
	        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
	        TestUtils.assertSearchText("ID", "android:id/message", "Mobile MSISDN: The MSISDN has an unrecognized National Destination Code. Please ensure the MSISDN starts with any of the following NDCs: 0701,0708,0802,0808,0812,0901,0902,0904,0907,0809,0817,0818,0908,0909,0705,0805,0807,0811,0815,0905,0915,0703,0706,0803,0806,0810,0813,0814,0816,0903,0906,0913");
	        getDriver().findElement(By.id("android:id/button1")).click();
	       
	
	        //Enter MSISDN with special character
	        String valid_msisdn_char = valid_msisdn + "jSJHs";
	        TestUtils.testTitle("Proceed with MSISDN with special character: (" + valid_msisdn_char + " )");
	        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).clear();
	        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).sendKeys(valid_msisdn_char);
	        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/checkBtn")).click();
	        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
	        TestUtils.assertSearchText("ID", "android:id/message", "Entered Mobile MSISDN is invalid. Entered value should be 11 digits");
	        getDriver().findElement(By.id("android:id/button1")).click();
	        Thread.sleep(1000);
	
	        //Enter MSISDN with longer Number
	        String valid_msisdn_Long = valid_msisdn + "2782";
	        TestUtils.testTitle("Proceed with MSISDN with longer Number: (" + valid_msisdn_Long + " )");
	        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).clear();
	        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).sendKeys(valid_msisdn_Long);
	        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/checkBtn")).click();
	        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
	        TestUtils.assertSearchText("ID", "android:id/message", "Entered Mobile MSISDN is invalid. Entered value should be 11 digits");
	        getDriver().findElement(By.id("android:id/button1")).click();
	        Thread.sleep(1000);
	       
	
	        //Select Fixed category
	        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnCategorySpinner")).click();
	        getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Fixed']")).click();
	        Thread.sleep(500);
	
	        //Enter Unrecognized fixed MSISDN
	        TestUtils.testTitle("Proceed with Unrecognized Fixed MSISDN: (" + unrecognizedFixedMsisdn1 + " )");
	        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).clear();
	        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).sendKeys(unrecognizedFixedMsisdn1);
	        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/checkBtn")).click();
	        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
	        TestUtils.assertSearchText("ID", "android:id/message", "Fixed MSISDN: Field must have more than 11 characters;The MSISDN has an unrecognized National Destination Code. Please ensure the MSISDN starts with any of the following NDCs: 0701,0708,0802,0808,0812,0901,0902,0904,0907,0809,0817,0818,0908,0909,0705,0805,0807,0811,0815,0905,0915,0703,0706,0803,0806,0810,0813,0814,0816,0903,0906,0913");
	        getDriver().findElement(By.id("android:id/button1")).click();
	        Thread.sleep(1000);
	
	        //Enter MSISDN Less than 6 digits for FIXED
	        TestUtils.testTitle("Proceed with MSISDN Less than 6 digits for FIXED");
	        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).clear();
	        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).sendKeys(msisdnLessThanSix);
	        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/checkBtn")).click();
	        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
	        TestUtils.assertSearchText("ID", "android:id/message", "Entered Fixed MSISDN is invalid. Entered value should be 7 digits");
	        getDriver().findElement(By.id("android:id/button1")).click();
	        Thread.sleep(1000);
	        
	        //Select PRIMARY_TM
	        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/corporateUserTypeSpinner")).click();
	        Thread.sleep(1500);
	        getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='PRIMARY_TM']")).click();
	        Thread.sleep(500);
	
	        //Select Mobile category
	        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnCategorySpinner")).click();
	        Thread.sleep(1000);
	        getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Mobile']")).click();
	        Thread.sleep(500);
	
	        //To ensure that Only one TM is required for validation (Primary TM MSISDN or Secondary MSISDN)
	        TestUtils.testTitle("To ensure that Only one TM is required for validation (Primary TM MSISDN or Secondary MSISDN): (" + valid_msisdn1 + " )");
	        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).clear();
	        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).sendKeys(valid_msisdn1);
	        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/checkBtn")).click();
	        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/message")));
	        TestUtils.assertSearchText("ID", "android:id/message", "Getting Data...");
	        Thread.sleep(3000);
	
	        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/verifyBiometricsBtn")));
	        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/verifyBiometricsBtn", "Verify Biometrics");
	
	        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/verifyBiometricsBtn")).click();
	        Thread.sleep(1000);
	      
		}		
	
	}
		@Parameters({ "dataEnv"})
		@Test
		public static void portraitFileUpload(String dataEnv, String file) throws Exception {
      //  Upload Primary TM image
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/uploadButton")).click();
        Thread.sleep(1000);
        TestUtils.scrollUntilElementIsVisible("XPATH", "//android.widget.TextView[@text='"+file+"']");
        getDriver().findElement(By.xpath("//android.widget.TextView[@text='"+file+"']")).click();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/nextButton")).click();
        Thread.sleep(1000);
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

		}
		
		public static void selectRegistration(String dataEnv, String regModule) throws InterruptedException, FileNotFoundException, IOException, ParseException {
	   		WebDriverWait wait = new WebDriverWait(getDriver(), 60);

	   		JSONParser parser = new JSONParser();
	   		JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resource/" + dataEnv + "/data.conf.json"));
	   		JSONObject envs = (JSONObject) config.get("NewRegistration");
	   		JSONObject envs1 = (JSONObject) config.get("ReRegistration");
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
	   		Thread.sleep(500);
	   		if(regModule.equals("Re-Registration")) {
	   			TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Re Registration']", "Re Registration");
	   		}else {
	   			TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/pageTitle", regModule);
	   		}
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
		
		  @Parameters ({"dataEnv"})
			@Test
			public static void portraitCaptureOverride() throws Exception {
			   WebDriverWait wait = new WebDriverWait(getDriver(), 30);
			   //image capture
			    //getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/captureButton")).click();
						
			    getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/switchButton")).click();
			    Thread.sleep(5000);
			    getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/captureButton")).click();
				Thread.sleep(1000);
				getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/overrideBtn")).click();  
				Thread.sleep(1000);
				getDriver().findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/"
						+ "android.widget.ListView/android.widget.TextView[4]")).click();
				
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
			    TestUtils.assertSearchText("ID", "android:id/message", "Subscriber's face was successfully captured");
				getDriver().findElement(By.id("android:id/button1")).click();
		

			  }
		  
		  @Parameters ({"dataEnv"})
				@Test
				public static void RRportraitCaptureOverride() throws Exception {
				   WebDriverWait wait = new WebDriverWait(getDriver(), 30);
				   //image capture
				    //getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/captureButton")).click();
							
				    getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/switchButton")).click();
				    Thread.sleep(5000);
				    getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/captureButton")).click();
					Thread.sleep(1000);
					
					try{
						wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/alertTitle")));
						TestUtils.assertSearchText("ID","android:id/message","Cropped image did not pass validation Do you want to proceed with original image?");
						getDriver().findElement(By.id("android:id/button2")).click();
					}catch (Exception e) {

						wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
						TestUtils.assertSearchText("ID", "android:id/message", "Subscriber's face was successfully captured");
						getDriver().findElement(By.id("android:id/button1")).click();
					}
			

				  }
		  
		    @Parameters ({"dataEnv"})
	  		@Test
	  		public static void captureOverridenHand(String dataEnv, String regModule) throws Exception {
	  		   WebDriverWait wait = new WebDriverWait(getDriver(), 30);
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
	  			getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/switchButton")).click();
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
	  			getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/switchButton")).click();
	  			Thread.sleep(1000);
	  			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/captureButton")).click();
	  			Thread.sleep(1000);
	  			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/ok")).click();
	  			Thread.sleep(1000);
	  			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Ageing']")));
	  			getDriver().findElement(By.xpath("//android.widget.TextView[@text='Ageing']")).click();

	  			//Save enrollment
	  		    TestUtils.testTitle("Proceed after overriding both hands on fingerprint");
	  		    TestUtils.scrollDown();
	  			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/fp_save_enrolment")));
	  		//	TestUtils.assertSearchText("ID", "com.sf.biocapture.activity:id/fp_save_enrolment", "Save Fingerprint");
	  			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/fp_save_enrolment")).click();
	  			Thread.sleep(1000);
	  			
	  			if (regModule.equals("NMS")) {
	  				getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/nextButton")).click();
	  			}else if(regModule.equals("RR") || regModule.equals("CR") ) {
	  				Thread.sleep(500);
	  				getDriver().findElement(By.id("android:id/button1")).click();
	  				wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/message")));
	  				getDriver().findElement(By.id("android:id/button1")).click();
	  			}
	  			
		  }
		  
		    @Parameters ({"dataEnv"})
		  	@Test
		    public static void requestOTP(String dataEnv) throws Exception {

		        WebDriverWait wait = new WebDriverWait(getDriver(), 60);
		        JSONParser parser = new JSONParser();
		        JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resource/" + dataEnv + "/data.conf.json"));
		        JSONObject envs2 = (JSONObject) config.get("ReRegistration");
		        String nin = (String) envs2.get("nin");
		        String ninVerificationMode = (String) envs2.get("ninVerificationMode");
		        String valid_msisdn = (String) envs2.get("valid_msisdn");
		        
		        String invalid_OTP = (String) envs2.get("invalid_OTP");
		        String expired_OTP = (String) envs2.get("expired_OTP");
		        
		    	//OTP Buttons
				TestUtils.testTitle("Confirm Request OTP, Confirm OTP and Bypass OTP buttons are displayed");
				
				try {
					TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/otp_confirm_button", "Confirm OTP");
				}catch(Exception e) {
					TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/otp_confirm_btn", "Confirm OTP");
				}
				
				try {
					TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/otp_bypass_button", "Bypass OTP");
				}catch(Exception e) {
					TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/otp_bypass_btn", "Bypass OTP");
				}
				
				try {
					TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/otp_request_button", "Request OTP");
				}catch(Exception e) {
					TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/otp_request_btn", "Request OTP");
				}
			
		        // Enter valid msisdn with invalid OTP
		        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/otp_field")));
				getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/otp_field")).clear();
				getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/otp_field")).sendKeys(invalid_OTP);
				
				try {
					getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/otp_confirm_button")).click();	
				}catch(Exception e) {
					getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/otp_confirm_btn")).click();
				}
				
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
				TestUtils.assertSearchText("ID", "android:id/message", "There is no record with the otp, msisdn combination.");
				getDriver().findElement(By.id("android:id/button1")).click();
				
				//Enter valid msisdn with Expired OTP
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/otp_field")));
				getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/otp_field")).clear();
				getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/otp_field")).sendKeys(expired_OTP);
				
				try {
					getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/otp_confirm_button")).click();	
				}catch(Exception e) {
					getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/otp_confirm_btn")).click();
				}
				
				
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
				TestUtils.assertSearchText("ID", "android:id/message", "There is no record with the otp, msisdn combination.");
				getDriver().findElement(By.id("android:id/button1")).click();
				
				TestUtils.testTitle("Request OTP Modal");
				
				try {
					getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/otp_request_button")).click();	
				}catch(Exception e) {
					getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/otp_request_btn")).click();
				}
			
			
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/otpHintMessage")));
				TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/otpHintMessage", "Tick options for OTP Request");
				TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/alt_phone_number", "Alternate Phone Number");
				TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/email_address", "Email Address");
//				TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/cancel_otp", "CANCEL");
//				TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/request_otp", "REQUEST");
//				getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/alt_phone_number")).click();
				getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/request_otp")).click();
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/otp_field")));

				// DB Connection for OTP
		    	String valid_OTP = ConnectDB.getOTPWithoutPhoneNumber();
				String ValidOTP = "Enter valid OTP : " + valid_OTP;
				TestUtils.testTitle(ValidOTP);

		        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/otp_field")).clear();
		        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/otp_field")).sendKeys(valid_OTP);
		        
		        try {
					getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/otp_confirm_button")).click();	
				}catch(Exception e) {
					getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/otp_confirm_btn")).click();
				}
		    

		        // Next button
		        try {
		        	TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/next_button", "NEXT");
					getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/next_button")).click();
				}catch(Exception e) {
					TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/nextBtn", "Next");
					getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/nextBtn")).click();
				}

				Thread.sleep(1000);
		    }

		    @Parameters ({"dataEnv"})
		  	@Test
		    public static void confirmCOOButton() throws Exception {
		  //Confirm COO checkbox
			TestUtils.testTitle("Change of Ownership (COO) Checkbox Test");
			TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/change_sim_ownership", "Change of Ownership");
			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/change_sim_ownership")).click();
			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/change_sim_ownership")).click();
		    }
		    
		    @Parameters ({"dataEnv"})
		  	@Test
		    public static void byPassrequestOTP(String dataEnv) throws Exception {

		        WebDriverWait wait = new WebDriverWait(getDriver(), 60);
		        JSONParser parser = new JSONParser();
		        JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resource/" + dataEnv + "/data.conf.json"));
		        JSONObject envs2 = (JSONObject) config.get("ReRegistration");
		        String nin = (String) envs2.get("nin");
		        String ninVerificationMode = (String) envs2.get("ninVerificationMode");
		        String valid_msisdn = (String) envs2.get("valid_msisdn");
		        
		        String invalid_OTP = (String) envs2.get("invalid_OTP");
		        String expired_OTP = (String) envs2.get("expired_OTP");
		        
		    	//OTP Buttons
				TestUtils.testTitle("Confirm Request OTP, Confirm OTP and Bypass OTP buttons are displayed");
				
				try {
					TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/otp_confirm_button", "Confirm OTP");
				}catch(Exception e) {
					TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/otp_confirm_btn", "Confirm OTP");
				}
				
				try {
					TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/otp_bypass_button", "Bypass OTP");
				}catch(Exception e) {
					TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/otp_bypass_btn", "Bypass OTP");
				}
				
				try {
					TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/otp_request_button", "Request OTP");
				}catch(Exception e) {
					TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/otp_request_btn", "Request OTP");
				}
				TestUtils.testTitle("BYPASS OTP Test");
				
				try {
					getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/otp_bypass_button")).click();
				}catch(Exception e) {
					getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/otp_bypass_btn")).click();
				}
				
				try {
					TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/next_button", "NEXT");
				}catch(Exception e) {
					TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/nextBtn", "Next");
				}
		
				String assertDetails = "Assert user's full name";
				TestUtils.testTitle(assertDetails);
				String NA = "N/A";
				Thread.sleep(1000);
				String surname = getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/tv_surname")).getText();
				String firstname = getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/tv_first_name")).getText();

				String[] toList = { "Surname: " + surname, "First name: " + firstname };
				for (String field : toList) {
					String name = "";
					String val = NA;
					if (field.endsWith(":")) {
						field = field + val;
					}
					try {
						String[] fields = field.split(":");
						name = fields[0];
						val = fields[1];
						Assert.assertNotEquals(val, NA);
						testInfo.get().log(Status.INFO, name + " : " + val);
					} catch (Error e) {
						testInfo.get().error(name + " : " + val);

					}
				}
				
				// Next button
				try {
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/next_button")));
					getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/next_button")).click();
				
				}catch(Exception e) {
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/nextBtn")));
					getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/nextBtn")).click();
					
				}
				
		    }
	  
		  @Test
		    public static void ninAssertion(String dataEnv) throws Exception {

		        WebDriverWait wait = new WebDriverWait(getDriver(), 60);
		        JSONParser parser = new JSONParser();
		        JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resource/" + dataEnv + "/data.conf.json"));
		        JSONObject envs = (JSONObject) config.get("NewRegistration");

		        String nin = (String) envs.get("nin");
		        String ninVerificationMode = (String) envs.get("ninVerificationMode");

		        //Select NIN Verification Type
		        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/verification_modes")).click();
		        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
		        getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Search By NIN']")).click();
		        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
		        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/proceed")).click();

		        //Search by NIN Modal
		        TestUtils.testTitle("Click on Search without supplying NIN");
		        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/nin")).clear();
		        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/capture_button")).click();
		        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/error_text")));
		        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/error_text", "Only numbers and minimum of 11 characters are allowed");

		        TestUtils.testTitle("Search NIN with less than 11 numeric characters: 1111111");
		        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/nin")).clear();
		        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/nin")).sendKeys("1111111");
		        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/capture_button")).click();
		        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/error_text")));
		        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/error_text", "Only numbers and minimum of 11 characters are allowed");

		        TestUtils.testTitle("Search by NIN: " + nin);
		        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/title")));
		        TestUtils.assertSearchText("ID", "android:id/title", "Search By NIN");
		        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/nin")).clear();
		        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/nin")).sendKeys(nin);
		        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/capture_button")).click();
		        
		        if( getDriver().findElement(By.id("android:id/message")).isDisplayed()){
		        	Thread.sleep(500);
		        	try {
		        		getDriver().findElement(By.id("android:id/button1")).click();
		        	}catch(Exception e) {
		        		
		        	}
	  				
		        }
		        else if(getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/contentPanel")).isDisplayed()) {
		        	String warningMsg = getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/contentPanel")).getText();
		        	testInfo.get().info(warningMsg);
		        	Thread.sleep(500);
		        	getDriver().findElement(By.id("android:id/button1")).click();
		        }
		    }
		  
		  @Test
		    public static void vNinAssertion(String dataEnv) throws Exception {


		        WebDriverWait wait = new WebDriverWait(getDriver(), 60);
		        JSONParser parser = new JSONParser();
		        JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resource/" + dataEnv + "/data.conf.json"));
		        JSONObject envs = (JSONObject) config.get("NewRegistration");

		        String nin = (String) envs.get("nin");
		        String vnin = (String) envs.get("vnin");
		        String ninVerificationMode = (String) envs.get("ninVerificationMode");

		        //Select VNIN Verification Type
		        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/verification_modes")).click();
		        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
		        getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Search By VNIN']")).click();
		        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
		        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/proceed")).click();

		        //Search by VNIN Modal
		        TestUtils.testTitle("Click on Search without supplying VNIN");
		        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/nin")).clear();
		        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/capture_button")).click();
		        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/error_text")));
		        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/error_text", "VNIN must contain 16 alphanumeric characters in the format 2 alphabets, 12 digits, 2 alphabets");

		        TestUtils.testTitle("Search vNIN with less than 16 alphanumeric characters: AA11111AA");
		        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/nin")).clear();
		        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/nin")).sendKeys("AA11111AA");
		        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/capture_button")).click();
		        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/error_text")));
		        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/error_text", "VNIN must contain 16 alphanumeric characters in the format 2 alphabets, 12 digits, 2 alphabets");

		        TestUtils.testTitle("Search by VNIN: " + vnin);
		        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/title")));
		        TestUtils.assertSearchText("ID", "android:id/title", "Search By vNIN");
		        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/nin")).clear();
		        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/nin")).sendKeys(vnin);
		        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/capture_button")).click();
		        
		        if( getDriver().findElement(By.id("android:id/message")).isDisplayed()){
		        	try {
		        		getDriver().findElement(By.id("android:id/button1")).click();
		        	}catch(Exception e) {
		        		
		        	}
		        }
		        else if(getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/contentPanel")).isDisplayed()) {
		        	String warningMsg = getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/contentPanel")).getText();
		        	testInfo.get().info(warningMsg);
		        	Thread.sleep(500);
		        	getDriver().findElement(By.id("android:id/button1")).click();
		        }
		    }
		  
		  @Test
		    public static void vNinVerificationOnline(String dataEnv) throws Exception {


		        WebDriverWait wait = new WebDriverWait(getDriver(), 60);
		        JSONParser parser = new JSONParser();
		        JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resource/" + dataEnv + "/data.conf.json"));
		        JSONObject envs = (JSONObject) config.get("NewRegistration");

		        String nin = (String) envs.get("nin");
		        String vnin = (String) envs.get("vnin");
		        String ninVerificationMode = (String) envs.get("ninVerificationMode");

		        if(getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")).isDisplayed()) {

		            //Proceed to NIN Verification View
		            TestUtils.testTitle("Select NIN Verification Mode");
		            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
		            TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/alertTitle", "NIN Verification");

		            vNinAssertion(dataEnv);
		        //    getDriver().findElement(By.id("android:id/button1")).click();
		           
		            Thread.sleep(1000);
		        }
		    }
		  
		  @Test
		    public static void ninVerificationOnline(String dataEnv) throws Exception {

		        WebDriverWait wait = new WebDriverWait(getDriver(), 60);
		        JSONParser parser = new JSONParser();
		        JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resource/" + dataEnv + "/data.conf.json"));
		        JSONObject envs = (JSONObject) config.get("NewRegistration");

		        String nin = (String) envs.get("nin");
		        String vnin = (String) envs.get("vnin");
		        String ninVerificationMode = (String) envs.get("ninVerificationMode");

		        if(getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")).isDisplayed()) {

		            //Proceed to NIN Verification View
		            TestUtils.testTitle("Select NIN Verification Mode");
		            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
		            TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/alertTitle", "NIN Verification");

		            ninAssertion(dataEnv);

		       //     getDriver().findElement(By.id("android:id/button1")).click();
		            Thread.sleep(1000);
		        }
		    }
		  
		    @Test
		    public static void nimcEyeBalling(String dataEnv) throws Exception {

		        WebDriverWait wait = new WebDriverWait(getDriver(), 60);
		        JSONParser parser = new JSONParser();
		        JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resource/" + dataEnv + "/data.conf.json"));
		        JSONObject envs = (JSONObject) config.get("NewRegistration");

		        String nin = (String) envs.get("nin");
		        String vnin = (String) envs.get("vnin");
		        String ninVerificationMode = (String) envs.get("ninVerificationMode");

		        //NIMC Details View
		        TestUtils.testTitle("Confirm the searched NIMC Data is returned: " + "NIMC Data");
		        Thread.sleep(2000);
		        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/nin_verification_title")));
		        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/nin_verification_title", "NIN Verification");
		        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/tv_portrait_image")));
		        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/tv_portrait_image", "Portrait Image");
		        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/tv_user_data")));
		        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/tv_user_data", "User Data");
		        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/tv_nimc_data")));
		        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/tv_nimc_data", "NIMC Data");
//		        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[1][@text='Firstname']")));
//		        TestUtils.assertSearchText("XPATH", "//android.widget.TextView[1][@text='Anthony']", "Anthony");
//		        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[1][@text='Surname']")));
//		        TestUtils.assertSearchText("XPATH", "//android.widget.TextView[1][@text='Uzoh']", "Uzoh");

//		        Thread.sleep(5000);
//		        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/proceed_button")));
		        Thread.sleep(2000);
		        if(getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/accept_button")).isDisplayed()){
		        	String acceptBtn = getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/accept_button")).getText();
		        	testInfo.get().info("<b> Accept Button is Visible </b>");
		        }
		        if(getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/reject_button")).isDisplayed()){
		        	String rejectBtn = getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/reject_button")).getText();
		        	testInfo.get().info("<b> Reject Button is Visible </b>");
		        }
//		        if(getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/recapture_button")).isDisplayed()){
//		        	String recaptureBtn = getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/recapture_button")).getText();
//		        	testInfo.get().info("<b> Recapture Button is Visible </b>");
//		        }
		        Thread.sleep(1000);
		        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id +":id/tv_message", "Live Image and NIMC Image matching failed. You are allowed to proceed or perform a recapture");
		        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/accept_button")).click();
		        
		    }
		    
		    public static void captureUploadDocument(String dataEnv) throws Exception{
				
				WebDriverWait wait = new WebDriverWait(getDriver(), 30);
				JSONParser parser = new JSONParser();
				JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resource/" + dataEnv + "/data.conf.json"));
				JSONObject envs = (JSONObject) config.get("IndividualNigerianDetails");

				String documentNumber = (String) envs.get("documentNumber");


				TestUtils.testTitle("Capture Identification Type");
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Capture Data']")));
				TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='[Select Identification Type]']", "[Select Identification Type]");
				Thread.sleep(500);
				getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/identificationTypeSpinner")).click();
				Thread.sleep(500);

				// Capture ID CARD
				TestUtils.scrollUntilElementIsVisible("XPATH", "//android.widget.CheckedTextView[@text='Passport']");
				getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Passport']")).click();
				Thread.sleep(500);
				TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/captureIdButton");
				getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/captureIdButton")).click();
				Thread.sleep(1500);
				getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/switchButton")).click();
				Thread.sleep(500);
				TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/captureButton");
				getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/captureButton")).click();
				Thread.sleep(1000);
				getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/ok")).click();
				Thread.sleep(500);

				// Document Number
				TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/documentNumber");
				getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/documentNumber")).clear();
				getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/documentNumber")).sendKeys(documentNumber);
				Thread.sleep(500);

				// Document Expiry Date
				TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/selectDateButton");
				getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/selectDateButton")).click();
				Thread.sleep(1000);
				try {
					try {
						getDriver().findElement(By.xpath("//android.widget.Button[@text='2021']")).click();
						getDriver().findElement(By.id("android:id/button1")).click();
					} catch (NoSuchElementException e1) {
						getDriver().findElement(By.xpath("//android.widget.EditText[@text='2021']")).click();
						getDriver().findElement(By.id("android:id/button1")).click();
					}
				} catch (NoSuchElementException ex) {
					getDriver().findElement(By.id("android:id/date_picker_header_year")).click();
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='2022']")));
					getDriver().findElement(By.xpath("//android.widget.TextView[@text='2022']")).click();
					getDriver().findElement(By.id("android:id/button1")).click();
				}

				// View Captured ID
				TestUtils.testTitle("Preview captured ID");
				TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/viewCaptureIdButton");
				getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/viewCaptureIdButton")).click();
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/title")));
				TestUtils.assertSearchText("ID", "android:id/title", "Preview");
				if (getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/image")).isDisplayed()) {
					testInfo.get().info("Captured ID is displayed");
				} else {
					testInfo.get().info("Captured ID is not displayed");
					testInfo.get().addScreenCaptureFromPath(TestUtils.addScreenshot());
				}
				getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/done_button")).click();
				Thread.sleep(500);

				TestUtils.scrollDown();

				// Capture KYC FORM
				TestUtils.testTitle("Capture KYC FORM");
				TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/captureKycFormButton", "CAPTURE KYC FORM *");
				getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/captureKycFormButton")).click();
				Thread.sleep(500);
				getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/switchButton")).click();
				Thread.sleep(500);
				getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/captureButton")).click();
				Thread.sleep(1000);
				getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/ok")).click();
				Thread.sleep(500);

			}
		    
		    public static void capturePreview(String dataEnv) throws Exception{
				WebDriverWait wait = new WebDriverWait(getDriver(), 30);
				// Preview
				TestUtils.testTitle("Preview KYC FORM");
				getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/viewKycFormButton")).click();
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/title")));
				TestUtils.assertSearchText("ID", "android:id/title", "Preview");
				if (getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/image")).isDisplayed()) {
					testInfo.get().info("Captured KYC FORM is displayed");
				} else {
					testInfo.get().info("Captured KYC FORM is not displayed");
					testInfo.get().addScreenCaptureFromPath(TestUtils.addScreenshot());
				}
				getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/done_button")).click();
				Thread.sleep(500);

				TestUtils.scrollDown();
			}
			
			public static void saveCapture(String dataEnv) throws Exception{
				WebDriverWait wait = new WebDriverWait(getDriver(), 30);

				wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/nextButton")));
				getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/nextButton")).click();
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/tv_message")));
				TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/tv_message", "Captured record was saved successfully");
				getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/btn_okay")).click();
			
			}
			
			@Parameters({ "dataEnv"})
			@Test
			public static void corporateOverridePortrait(String dataEnv) throws Exception { 
	        //Picture Capture
	        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/captureButton")).click();
	        
	        RRportraitCaptureOverride();
	        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/nextButton")).click();
			}
			
			
			@Test
			@Parameters({"dataEvn"})
			public static void corporatecaptureUploadDocument(String dataEnv) throws Exception {

				JSONParser parser = new JSONParser();
				JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resource/" + dataEnv + "/data.conf.json"));
			
				WebDriverWait wait = new WebDriverWait(getDriver(), 30);
				TestUtils.testTitle("Capture Identification Type");
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/corporateDocSpinner")));

				// Capture Company Documents
				String docType;
				docType = getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/tv_sub_sub_heading")).getText();
				TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/tv_sub_sub_heading", "Other Company Documents");
				getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/corporateDocSpinner")).click();
				getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Certificate Of Incorporation']")).click();

				//Submit with Empty files
				Thread.sleep(500);
				getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/bt_next")).click();
				TestUtils.assertSearchText("ID", "android:id/message",
						"Please ensure you capture all mandatory documents");
				getDriver().findElement(By.id("android:id/button1")).click();
				Thread.sleep(500);

				Thread.sleep(500);
				getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/bt_capture_doc")).click();
				Thread.sleep(500);
				getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/switchButton")).click();
				Thread.sleep(500);
				TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/captureButton");
				getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/captureButton")).click();
				Thread.sleep(1000);
				getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/ok")).click();
				Thread.sleep(500);
				getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/bt_next")).click();

				// Capture MOD Primary TM
				docType = getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/tv_sub_sub_heading")).getText();
				TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/tv_sub_sub_heading", "MOD Primary TM");
				getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/corporateDocSpinner")).click();
				Thread.sleep(500);
				getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Passport']")).click();

				if (docType.contains("MOD Primary TM")) {
					//Submit with Empty files
					/*Thread.sleep(500);
					getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/bt_next")).click();
					TestUtils.assertSearchText("ID", "android:id/message","Please ensure you capture all mandatory documents");
					getDriver().findElement(By.id("android:id/button1")).click();*/
					Thread.sleep(500);

					getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/bt_capture_doc")).click();
					Thread.sleep(500);
					getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/switchButton")).click();
					Thread.sleep(500);
					TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/captureButton");
					getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/captureButton")).click();
					Thread.sleep(1000);
					getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/ok")).click();
					Thread.sleep(500);
					getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/bt_next")).click();

				}


				// Capture MOD Secondary TM
				docType = getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/tv_sub_sub_heading")).getText();
				if (docType.contains("MOD Secondary TM")) {
					TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/tv_sub_sub_heading", "MOD Secondary TM");
					getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/corporateDocSpinner")).click();
					Thread.sleep(500);
					getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Passport']")).click();

					//Submit with Empty files
					/*Thread.sleep(1000);
					getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/bt_next")).click();
					Thread.sleep(1000);
					TestUtils.assertSearchText("ID", "android:id/message","Please ensure you capture all mandatory documents");
					getDriver().findElement(By.id("android:id/button1")).click();
					Thread.sleep(500);*/

					getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/bt_capture_doc")).click();
					Thread.sleep(500);
					getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/switchButton")).click();
					Thread.sleep(500);
					TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/captureButton");
					getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/captureButton")).click();
					Thread.sleep(1000);
					getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/ok")).click();
					Thread.sleep(500);
					getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/bt_next")).click();
				}

			}
			
			@Test
			@Parameters({"dataEvn"})
			public static void captureKYCDocument(String dataEnv) throws Exception {
			WebDriverWait wait = new WebDriverWait(getDriver(), 30);
			
			JSONParser parser = new JSONParser();
			JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resource/" + dataEnv + "/data.conf.json"));
			JSONObject envs = (JSONObject) config.get("IndividualForeignerDetails");
			String documentNumber = (String) envs.get("documentNumber");

			JSONObject envs2 = (JSONObject) config.get("CompanyDetails");
			String company_description = (String) envs2.get("company_description");
			String company_regno = (String) envs2.get("company_regno");
			String house_or_flat_no = (String) envs2.get("house_or_flat_no");
			String company_street = (String) envs2.get("company_street");
			String company_city = (String) envs2.get("company_city");
			String company_state_address = (String) envs2.get("company_state_address");
			String company_lga_address = (String) envs2.get("company_lga_address");
			String company_postalcode = (String) envs2.get("company_postalcode");
			String email = (String) envs2.get("email");
			String alt_phone_number = (String) envs2.get("alt_phone_number");

			String docType;
			// Capture KYC Form
			docType = getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/tv_sub_sub_heading")).getText();
			TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/tv_sub_sub_heading", "KYC Capture Form");
			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/corporateDocSpinner")).click();
			Thread.sleep(500);
			getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Registration Form']")).click();

			//Submit with Empty files
			/*Thread.sleep(500);
			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/bt_next")).click();
			TestUtils.assertSearchText("ID", "android:id/message","Please ensure you capture all mandatory documents");
			getDriver().findElement(By.id("android:id/button1")).click();*/
			Thread.sleep(500);

			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/bt_capture_doc")).click();
			Thread.sleep(500);
			getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/switchButton")).click();
			Thread.sleep(500);
			TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/captureButton");
			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/captureButton")).click();
			Thread.sleep(1000);
			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/ok")).click();
			Thread.sleep(500);
			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/bt_next")).click();

			try {
				//Capture Company Details
				Thread.sleep(1000);
				Asserts.AssertCompanyDetails();
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/company_name_descrptn")));
				getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/company_name_descrptn")).clear();
				getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/company_name_descrptn")).sendKeys(company_description);
				Thread.sleep(500);

				// Registration Number
				getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/company_ok")).click();
				wait.until(ExpectedConditions.visibilityOfElementLocated((By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle"))));
				TestUtils.assertSearchText("ID", "android:id/message", "Company Registration Number\n" + "Empty field");
				getDriver().findElement(By.id("android:id/button1")).click();
				Thread.sleep(500);
				getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/company_regno")).clear();
				getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/company_regno")).sendKeys(company_regno);
				Thread.sleep(500);

				// House/ Flat Number
				getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/company_ok")).click();
				wait.until(ExpectedConditions.visibilityOfElementLocated((By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle"))));
				TestUtils.assertSearchText("ID", "android:id/message", "House/Flat Number\n" +
						"Empty field");
				getDriver().findElement(By.id("android:id/button1")).click();
				Thread.sleep(500);
				getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/house_or_flat_no")).clear();
				getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/house_or_flat_no")).sendKeys(house_or_flat_no);
				Thread.sleep(500);

				// Street
				getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/company_ok")).click();
				wait.until(ExpectedConditions.visibilityOfElementLocated((By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle"))));
				TestUtils.assertSearchText("ID", "android:id/message", "Street\n" +
						"Empty field");
				getDriver().findElement(By.id("android:id/button1")).click();
				Thread.sleep(500);
				getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/street")).clear();
				getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/street")).sendKeys(company_street);
				Thread.sleep(500);


				TestUtils.scrollDown();

				// City
				getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/company_ok")).click();
				wait.until(ExpectedConditions.visibilityOfElementLocated((By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle"))));
				TestUtils.assertSearchText("ID", "android:id/message", "City\n" +
						"Empty field");
				getDriver().findElement(By.id("android:id/button1")).click();
				Thread.sleep(500);
				getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/city")).clear();
				getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/city")).sendKeys(company_city);
				Thread.sleep(500);

				// Company Address State
				getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/company_ok")).click();
				wait.until(ExpectedConditions.visibilityOfElementLocated((By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle"))));
				TestUtils.assertSearchText("ID", "android:id/message", "Please select State");
				getDriver().findElement(By.id("android:id/button1")).click();
				Thread.sleep(500);
				getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/company_state_address")).click();
				Thread.sleep(500);
				getDriver().findElement(By.xpath("//android.widget.TextView[@text='" + company_state_address + "']")).click();
				Thread.sleep(500);

				// Company Address LGA
				getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/company_ok")).click();
				wait.until(ExpectedConditions.visibilityOfElementLocated((By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle"))));
				TestUtils.assertSearchText("ID", "android:id/message", "Please select LGA");
				getDriver().findElement(By.id("android:id/button1")).click();
				Thread.sleep(500);
				getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/company_lga_address")).click();
				Thread.sleep(500);
				getDriver().findElement(By.xpath("//android.widget.TextView[@text='" + company_lga_address + "']")).click();
				Thread.sleep(500);

				TestUtils.scrollDown();
				// Postal code
				getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/company_postalcode")).clear();
				getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/company_postalcode")).sendKeys(company_postalcode);
				Thread.sleep(500);

				getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/company_ok")).click();
			} catch (Exception e) {
				testInfo.get().error("Company details not found");
			}
			//Assert Primary TM details
			TestUtils.testTitle("Assert Primary TM details");
			Asserts.AssertTMDetails();

			try {
				//Assert Secondary TM details
				getDriver().findElement(By.xpath("//androidx.appcompat.app.ActionBar.Tab[@content-desc='SECONDARY TM Details']")).click();
				Thread.sleep(500);
				TestUtils.testTitle("Assert Secondary TM details");
				Asserts.AssertTMDetails();
			} catch (Exception e) {

			}

			}
			
			@Test
			@Parameters({"dataEvn"})
			public static void saveEnrollment(String dataEnv) throws Exception {
			WebDriverWait wait = new WebDriverWait(getDriver(), 30);
			//Save enrollment
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/btn_submit")));
			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/btn_submit")).click();
			try {
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/tv_message")));
				TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/tv_message", "Captured record was saved successfully");
				getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/btn_okay")).click();
			} catch (Exception e) {
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/message")));
				TestUtils.assertSearchText("ID", "android:id/message", "Captured record was saved successfully");
				getDriver().findElement(By.id("android:id/button1")).click();
			}
		}
		
		  
}
	
		
	
	
	
	

