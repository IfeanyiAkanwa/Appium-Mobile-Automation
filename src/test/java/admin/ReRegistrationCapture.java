package admin;

import db.ConnectDB;
import demographics.Form;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import io.appium.java_client.android.AndroidKeyCode;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import utils.Asserts;
import utils.TestBase;
import utils.TestUtils;

import static admin.ReportsTest.navigateToReportsPage;

public class ReRegistrationCapture extends TestBase {
	private static StringBuffer verificationErrors = new StringBuffer();
	int totalSubVal=0;int totalSyncsentVal = 0;int totalSyncpendingVal = 0;int totalSynConfVal = 0;int totalRejectVal = 0;
	static int fingerPrintCount=0;
	@Parameters({ "systemPort", "deviceNo", "server","deviceName", "testConfig", "dataEnv" })
	@Test
	public void removeReRegPrivilegeTest(String systemPort, int deviceNo, String server, String deviceName, String testConfig, String dataEnv) throws Exception {

		//initial setting
		String settingCode="RR";
		String initialSetting=TestUtils.retrieveSettingsApiCall(dataEnv, "RETRIEVE_AVAILABLE_USECASES");

		if(initialSetting.contains(settingCode)){
			//Continue the setting is available already, remove NMS
			//*********UPDATING SETTING FOR AVAILABLE USE CASE************
			TestUtils.testTitle("UPDATING SETTING FOR AVAILABLE USE CASE(NMS)");
			String SettinVal= initialSetting.replace(","+settingCode, "");
			SettinVal = SettinVal.replace(settingCode+",","");
			if (SettinVal.contains(settingCode)){
				SettinVal = SettinVal.replace(settingCode,"");
			}

			JSONObject getSettingParams=TestUtils.createSettingObject("PILOT-AVAILABLE-USE-CASE", SettinVal,"All available registration use case(SS,AR,CR,CN,NMS,RR,MP,MR)");
			TestUtils.updateSettingsApiCall(dataEnv, getSettingParams);
			closeApp();
			Thread.sleep(5000);
			startApp(systemPort, deviceNo, server, deviceName, testConfig, true);
		}else{
			//*********NMS is not found proceed************

		}

		WebDriverWait wait = new WebDriverWait(getDriver(), 30);
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resource/" + dataEnv + "/data.conf.json"));
		JSONObject envs = (JSONObject) config.get("ReRegistration");

		String valid_username = (String) envs.get("valid_username");
		String generalUserPassword = (String) envs.get("generalUserPassword");
		String lga = (String) envs.get("lga");

		TestBase.Login1( valid_username, generalUserPassword);
		Thread.sleep(500);
		TestUtils.testTitle("To confirm that Re registration  is not available when it is removed from available use case settings and user has privilege");
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/button_start_capture")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/reg_type_placeholder")));
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/reg_type_placeholder", "Registration Type");
		Thread.sleep(500);

		// Select LGA of Registration
		TestUtils.testTitle("Select LGA of Registration: " + lga);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/lga_of_reg")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/alertTitle", "LGA of Registration*");
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='" + lga + "']")).click();
		Thread.sleep(500);

		// Select Re Registration
		TestUtils.testTitle("Select Re Registration");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/typeofreg")));
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/typeofreg")).click();
		Thread.sleep(500);
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/alertTitle", "Select Registration Type");
		try{
			getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Re-Registration']")).click();
			testInfo.get().error("RE REGISTRATION is not removed from the AVAILABLE-USE-CASE");
		}catch (Exception e){
			testInfo.get().info("RE REGISTRATION is not found, hence successfully removed from AVAILABLE-USE-CASE");
		}

		Thread.sleep(1000);
		if(initialSetting.contains(settingCode)) {
			//Returning initial setting value
			TestUtils.testTitle("Returning initial setting value(" + initialSetting + ")");
			JSONObject getSettingParams = TestUtils.createSettingObject("PILOT-AVAILABLE-USE-CASE", initialSetting, "All available registration use case(SS,AR,CR,CN,NMS,RR,MP,MR)");
			TestUtils.updateSettingsApiCall(dataEnv, getSettingParams);
		}else{
			//Add NMS and update data
			//Returning initial setting value
			initialSetting+=","+settingCode;
			TestUtils.testTitle("Returning initial setting value(" + initialSetting + ")");
			JSONObject getSettingParams = TestUtils.createSettingObject("PILOT-AVAILABLE-USE-CASE", initialSetting, "All available registration use case(SS,AR,CR,CN,NMS,RR,MP,MR)");
			TestUtils.updateSettingsApiCall(dataEnv, getSettingParams);
		}

		// Log out
		TestUtils.testTitle("Logout username: "  + valid_username);
		getDriver().pressKeyCode(AndroidKeyCode.BACK);
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

	@Parameters({ "systemPort", "deviceNo", "server","deviceName", "testConfig", "dataEnv" })
	@Test
	public void disableSubscriberBypassTest(String systemPort, int deviceNo, String server, String deviceName, String testConfig, String dataEnv) throws Exception {
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resource/" + dataEnv + "/data.conf.json"));
		JSONObject envs = (JSONObject) config.get("ReRegistration");

		JSONObject config2 = (JSONObject) parser.parse(new FileReader("src/test/resource/" + dataEnv + "/settingsApi.conf.json"));
		JSONObject envs2 = (JSONObject) config2.get("ENABLE_SUBSCRIBER_VALIDATION_BYPASS");
		JSONArray settingNames = (JSONArray) envs2.get("settingNames");
		String settingName= (String) settingNames.get(0);
		fingerPrintCount=0;

		TestUtils.testTitle("To verify that the default value for ENABLE SUBSCRIBER VALIDATION BYPASS is TRUE");
		String initialSetting=TestUtils.retrieveSettingsApiCall(dataEnv, "ENABLE_SUBSCRIBER_VALIDATION_BYPASS");
		TestUtils.assertTwoValues(initialSetting, " TRUE");

		TestUtils.testTitle("To verify that the setting name is ENABLE SUBSCRIBER VALIDATION BYPASS");
		TestUtils.assertTwoValues(settingName, "PILOT-ENABLE-SUBSCRIBER-VALIDATION-BYPASS");

		if(!initialSetting.contains("FALSE") && !initialSetting.contains("false")){
			//Continue the setting is set to TRUE, Set to FALSE
			//*********UPDATING SETTING FOR AVAILABLE USE CASE************
			TestUtils.testTitle("UPDATING SETTING FOR ENABLE_SUBSCRIBER_VALIDATION_BYPASS(FALSE)");

			String SettinVal= "TRUE";

			JSONObject getSettingParams=TestUtils.createSettingObject("PILOT-ENABLE-SUBSCRIBER-VALIDATION-BYPASS", SettinVal,"This determines the use case where details is retrieved using barcode technology");
			TestUtils.updateSettingsApiCall(dataEnv, getSettingParams);
			closeApp();
			Thread.sleep(5000);
			startApp(systemPort, deviceNo, server, deviceName, testConfig, true);
		}else{
			//*********NMS is not found proceed************

		}

		WebDriverWait wait = new WebDriverWait(getDriver(), 30);

		String valid_username = (String) envs.get("valid_username2");
		String generalUserPassword = (String) envs.get("generalUserPassword");
		String lga = (String) envs.get("lga");
		String valid_msisdn = (String) envs.get("valid_msisdn");

		TestBase.Login1( valid_username, generalUserPassword);
		Thread.sleep(500);
		TestUtils.testTitle("To confirm that New registration  is not available when it is removed from available use case settings and user has privilege");
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/button_start_capture")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/reg_type_placeholder")));
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/reg_type_placeholder", "Registration Type");
		Thread.sleep(500);

		// Select LGA of Registration
		TestUtils.testTitle("Select LGA of Registration: " + lga);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/lga_of_reg")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/alertTitle", "LGA of Registration*");
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='" + lga + "']")).click();
		Thread.sleep(500);

		// Select Re Registration
		TestUtils.testTitle("Select Re Registration");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/typeofreg")));
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/typeofreg")).click();
		Thread.sleep(500);
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/alertTitle", "Select Registration Type");
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Re-Registration']")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/next_button")).click();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/primary_msisdn_field")));
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/primary_msisdn_field")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/primary_msisdn_field")).sendKeys(valid_msisdn);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/submit_button")).click();

		verifyBioMetricsTest();

		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/otp_bypass_button")));
			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/otp_bypass_button")).click();
			TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/otp_bypass_button", "Bypass OTP");
			testInfo.get().error("OTP verification should not be found");
		}catch (Exception e){
			testInfo.get().info("OTP verification is not be found");
		}

		Thread.sleep(1000);
		if(!initialSetting.contains("FALSE") && !initialSetting.contains("false")) {
			//Returning initial setting value
			TestUtils.testTitle("Returning initial setting value(" + initialSetting + ")");
			JSONObject getSettingParams=TestUtils.createSettingObject("PILOT-GET-DETAILS-FROM-BARCODE-USECASES", initialSetting,"This determines the use case where details is retrieved using barcode technology");
			TestUtils.updateSettingsApiCall(dataEnv, getSettingParams);
		}else{
			//Returning initial setting value
			TestUtils.testTitle("Returning initial setting value(" + initialSetting + ")");
			JSONObject getSettingParams=TestUtils.createSettingObject("PILOT-GET-DETAILS-FROM-BARCODE-USECASES", initialSetting,"This determines the use case where details is retrieved using barcode technology");
			TestUtils.updateSettingsApiCall(dataEnv, getSettingParams);
		}


	}

	@Test
	public static void navigateToReRegistration() {
    	WebDriverWait wait = new WebDriverWait(getDriver(), 60);

		// Navigate to Re-Registration Use Case
		TestUtils.testTitle("Navigate to Re-Registration Use Case");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Home']")));
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/button_start_capture")).click();
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/reg_type_placeholder")));
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/reg_type_placeholder", "Registration Type");

		//Select Re-Reg
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='[Select Registration Type]']")).click();
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/alertTitle", "Select Registration Type");
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Re-Registration']")).click();
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/reg_type_placeholder")));

		//click Next Button
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/next_button")));
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/next_button")).click();

		//Confirm re-reg view is displayed
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Re Registration']")));
		TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Re Registration']", "Re Registration");


	}
	@Parameters({ "dataEnv"})
	@Test
	public void noneReRegPrivilegeTest(String dataEnv) throws Exception {
		WebDriverWait wait = new WebDriverWait(getDriver(), 30);
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resource/" + dataEnv + "/data.conf.json"));
		JSONObject envs = (JSONObject) config.get("ReRegistration");

		String valid_username = (String) envs.get("valid_username");
		String generalUserPassword = (String) envs.get("generalUserPassword");
		String lga = (String) envs.get("lga");

		TestBase.Login1( valid_username, generalUserPassword);
		Thread.sleep(500);
		TestUtils.testTitle("To ensure agents without Re registration privilege can't perform the action when Re registration  is in the list of available  use cases");
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/button_start_capture")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/reg_type_placeholder")));
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/reg_type_placeholder", "Registration Type");
		Thread.sleep(500);

		// Select LGA of Registration
		TestUtils.testTitle("Select LGA of Registration: " + lga);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/lga_of_reg")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/alertTitle", "LGA of Registration*");
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='" + lga + "']")).click();
		Thread.sleep(500);

		// Select Re Registration
		TestUtils.testTitle("Select Re Registration");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/typeofreg")));
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/typeofreg")).click();
		Thread.sleep(500);
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/alertTitle", "Select Registration Type");
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Re-Registration']")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/alertTitle", "No Privilege");
		TestUtils.assertSearchText("ID", "android:id/message", "You are not allowed to access Re-Registration because you do not have the Re-Registration privilege");
		Thread.sleep(500);
		getDriver().findElement(By.id("android:id/button1")).click();
		Thread.sleep(500);

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

	@Parameters({ "dataEnv"})
	@Test
	public void ReRegPrivilegesTest(String dataEnv) throws Exception {
		WebDriverWait wait = new WebDriverWait(getDriver(), 30);
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resource/" + dataEnv + "/data.conf.json"));
		JSONObject envs = (JSONObject) config.get("ReRegistration");

		String noIndividualReg = (String) envs.get("noIndividualReg");
		String noCompanyReg = (String) envs.get("noCompanyReg");
		String generalUserPassword = (String) envs.get("generalUserPassword");
		String foreign_msisdn = (String) envs.get("foreign_msisdn");
		String lga = (String) envs.get("lga");
		String noForeignRegUser = (String) envs.get("noForeignRegUser");
		String valid_msisdn = (String) envs.get("valid_msisdn");


		//To confirm that the COO option is  disabled for a user without the COO privilege
		TestUtils.testTitle("To confirm that the COO option is  disabled for a user without the COO privilege("+noIndividualReg+")");
		TestBase.Login1( noIndividualReg, generalUserPassword);
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/button_start_capture")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/reg_type_placeholder")));
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/reg_type_placeholder", "Registration Type");
		Thread.sleep(500);

		// Select LGA of Registration
		TestUtils.testTitle("Select LGA of Registration: " + lga);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/lga_of_reg")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/alertTitle", "LGA of Registration*");
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='" + lga + "']")).click();
		Thread.sleep(500);

		// Select Re Registration
		TestUtils.testTitle("Select Re Registration");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/typeofreg")));
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/typeofreg")).click();
		Thread.sleep(500);
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/alertTitle", "Select Registration Type");
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Re-Registration']")).click();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/next_button")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/change_of_ownership_hint")));
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/change_of_ownership_hint", "Change of Ownership privilege is required");
		getDriver().findElement(By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']")).click();

		//Log Out
		logOutUser(noIndividualReg);

		//To verify that subscriber without Portrait and without the RE-REGISTRATION BYPASS privilege is not be able to bypass OTP verification
		TestUtils.testTitle("To verify that subscriber without Portrait and without the RE-REGISTRATION BYPASS privilege is not be able to bypass OTP verification("+noCompanyReg+")");
		TestBase.Login1( noCompanyReg, generalUserPassword);
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/button_start_capture")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/reg_type_placeholder")));
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/reg_type_placeholder", "Registration Type");
		Thread.sleep(500);

		// Select LGA of Registration
		TestUtils.testTitle("Select LGA of Registration: " + lga);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/lga_of_reg")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/alertTitle", "LGA of Registration*");
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='" + lga + "']")).click();
		Thread.sleep(500);

		// Select Re Registration
		TestUtils.testTitle("Select Re Registration");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/typeofreg")));
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/typeofreg")).click();
		Thread.sleep(500);
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/alertTitle", "Select Registration Type");
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Re-Registration']")).click();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/next_button")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/primary_msisdn_field")));
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/primary_msisdn_field")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/primary_msisdn_field")).sendKeys(valid_msisdn);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/submit_button")).click();

		//BioMetrics Verification
		verifyBioMetricsTest();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/button1")));
		getDriver().findElement(By.id("android:id/button1")).click();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/otp_field")));
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/otp_bypass_button")).click();
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/otp_bypass_button", "Bypass OTP");

		getDriver().findElement(By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']")).click();


		//Log Out
		logOutUser(noCompanyReg);

	}

	@Test
	public void captureReportRecords() throws InterruptedException {

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

	@Parameters({ "dataEnv"})
	@Test
	public void validateMsisdnTest(String dataEnv) throws Exception {
		WebDriverWait wait = new WebDriverWait(getDriver(), 60);
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resource/" + dataEnv + "/data.conf.json"));
		JSONObject envs = (JSONObject) config.get("ReRegistration");

		String invalid_msisdn = (String) envs.get("invalid_msisdn");
		String valid_msisdn = (String) envs.get("valid_msisdn");
		String invalid_OTP = (String) envs.get("invalid_OTP");
		String msisdnLessThanSix = (String) envs.get("msisdnLessThanSix");
		String unrecognizedMsisdn = (String) envs.get("unrecognizedMsisdn");
		String msisdnWithFingerprint = (String) envs.get("msisdnWithFingerprint");
		String ninVerificationMode = (String) envs.get("ninVerificationMode");
		String nin = (String) envs.get("nin");

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
		TestUtils.assertSearchText("ID", "android:id/message", "No biometric data was found for the specified MSISDN.");
		getDriver().findElement(By.id("android:id/button1")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Re Registration']")));

		// Enter valid msisdn with invalid OTP
		TestUtils.testTitle("Enter valid MSISDN: " + valid_msisdn + " for Re-Registration");
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/primary_msisdn_field")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/primary_msisdn_field")).sendKeys(valid_msisdn);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/submit_button")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/capture_image_button")));
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/capture_image_button", "Verify Biometrics");

	}

	@Parameters({ "dataEnv"})
    @Test
    public void captureIndividualReRegTest(String dataEnv) throws Exception {

    	WebDriverWait wait = new WebDriverWait(getDriver(), 30);
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resource/" + dataEnv + "/data.conf.json"));
		JSONObject envs = (JSONObject) config.get("ReRegistration");
		
		String invalid_msisdn = (String) envs.get("invalid_msisdn");
		String valid_msisdn = (String) envs.get("valid_msisdn");
		String invalid_OTP = (String) envs.get("invalid_OTP");
		String expired_OTP = (String) envs.get("expired_OTP");
		String msisdnLessThanSix = (String) envs.get("msisdnLessThanSix");
		String unrecognizedMsisdn = (String) envs.get("unrecognizedMsisdn");
		String msisdnWithFingerprint = (String) envs.get("msisdnWithFingerprint");
		String ninVerificationMode = (String) envs.get("ninVerificationMode");
		String nin = (String) envs.get("nin");
		String alt_phone_number = (String) envs.get("alt_phone_number");

		// Enter valid msisdn with invalid OTP
		TestUtils.testTitle("Enter valid MSISDN: " + valid_msisdn + " for Re-Registration with invalid OTP: " + invalid_OTP);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/primary_msisdn_field")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/primary_msisdn_field")).sendKeys(valid_msisdn);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/submit_button")).click();

		//BioMetrics Verification
		fingerPrintCount=0;
		verifyBioMetricsTest();
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/button1")));
			getDriver().findElement(By.id("android:id/button1")).click();
		}catch (Exception e){

		}

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/otp_field")));
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/otp_field")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/otp_field")).sendKeys(invalid_OTP);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/otp_confirm_button")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/message", "There is no record with the otp, msisdn combination.");
		getDriver().findElement(By.id("android:id/button1")).click();

		// Enter valid msisdn with expired OTP
		TestUtils.testTitle("Enter valid MSISDN: " + valid_msisdn + " for Re-Registration with expired OTP: " + expired_OTP);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/primary_msisdn_field")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/primary_msisdn_field")).sendKeys(valid_msisdn);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/submit_button")).click();

		//BioMetrics Verification
		verifyBioMetricsTest();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/otp_field")));
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/otp_field")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/otp_field")).sendKeys(expired_OTP);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/otp_confirm_button")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/message", "There is no record with the otp, msisdn combination.");
		getDriver().findElement(By.id("android:id/button1")).click();

		//OTP Buttons
		TestUtils.testTitle("Confirm Request OTP, Confirm OTP and Bypass OTP buttons are displayed");
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/otp_confirm_button", "Confirm OTP");
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/otp_bypass_button", "Bypass OTP");
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/otp_request_button", "Request OTP");

		//Confirm that BYPASS OTP Button is functional
		TestUtils.testTitle("BYPASS OTP Test");
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/otp_bypass_button")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/next_button")));
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/next_button", "NEXT");
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

		//Confirm COO checkbox
		TestUtils.testTitle("Change of Ownership (COO) Checkbox Test");
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/change_sim_ownership", "Change of Ownership");
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/change_sim_ownership")).click();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/change_sim_ownership")).click();
		//getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/change_sim_ownership")).click();

		// Enter valid msisdn with valid OTP
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/primary_msisdn_field")));
		TestUtils.testTitle("Enter valid MSISDN with valid OTP: " + valid_msisdn + " for Re-Registration");
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/primary_msisdn_field")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/primary_msisdn_field")).sendKeys(valid_msisdn);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/submit_button")).click();

		//BioMetrics Verification
		verifyBioMetricsTest();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/otp_request_button")));

		//Test Cancel Button on Request OTP Modal
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/otp_request_button")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/otpHintMessage")));
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/cancel_otp")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/otp_request_button")));

		//Request OTP Modal
		TestUtils.testTitle("Request OTP Modal");
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/otp_request_button")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/otpHintMessage")));
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/otpHintMessage", "Tick options for OTP Request");
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/primary_phone_number", "Primary Phone Number");
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/alt_phone_number", "Alternate Phone Number");
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/email_address", "Email Address");
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/cancel_otp", "CANCEL");
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/request_otp", "REQUEST");
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/alt_phone_number")).click();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/request_otp")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/otp_field")));

		// DB Connection for OTP
    	String valid_OTP = ConnectDB.getOTPWithoutPhoneNumber();
		String ValidOTP = "Enter valid OTP : " + valid_OTP;
		TestUtils.testTitle(ValidOTP);

        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/otp_field")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/otp_field")).sendKeys(valid_OTP);
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/otp_confirm_button")).click();

        // Next button
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/next_button")));
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/next_button")).click();
		/*Thread.sleep(2000);
		if(getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/next_button")).isDisplayed()){
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/next_button")));
			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/next_button")).click();
		}*/

        try{
        	//Proceed if NIN modal comes up
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
			//NIN Verification
			TestBase.verifyNINTest(nin, ninVerificationMode);

			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Personal Details']")));
			TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Personal Details']", "Personal Details");

        } catch(Exception e){
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Personal Details']")));
			TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Personal Details']", "Personal Details");
		}


        //Confirm that user can do both Individual
		TestUtils.testTitle("Confirm that user can do both Individual");
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='Individual']")).click();
		TestUtils.assertSearchText("XPATH", "//android.widget.CheckedTextView[@text='Individual']", "Individual");
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Individual']")).click();

		//Assert Invividual Form
		Asserts.AssertIndividualForm();

		TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/alternatePhone");

		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/alternatePhone")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/alternatePhone")).sendKeys(alt_phone_number);

		// Next button
		TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/btnContinueReg");
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/btnContinueReg")).click();

		//Edit Reasons
		TestUtils.assertSearchText("ID", "android:id/title", "Edit Reason");
		getDriver().findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/androidx.recyclerview.widget.RecyclerView/android.view.ViewGroup[1]/android.widget.LinearLayout/android.widget.LinearLayout")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='ALTERNATE PHONE MISMATCH']")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/submit_button")).click();

		// Next button
		TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/btnContinueReg");
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/btnContinueReg")).click();
		Thread.sleep(500);

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/countrySpinner")));

		Asserts.AssertAddresstDetails(dataEnv);


		getDriver().findElement(By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']")).click();

		try{
			Form.individualNigerianForm(dataEnv);
		}catch (Exception e){
			Form.individualNigerianFormAutoPopulate(dataEnv);
		}

		//Do Bulk Assert for Table checking
		//TestUtils.assertBulkTables(valid_msisdn);
		Thread.sleep(5000);
		TestUtils.assertBulkTables(valid_msisdn, "NIGERIA");

		try {
			getDriver().pressKeyCode(AndroidKeyCode.BACK);
			Thread.sleep(1000);
			getDriver().findElement(By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']")).click();
			Thread.sleep(1000);
			getDriver().findElement(By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']")).click();
			Thread.sleep(1000);
			getDriver().findElement(By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']")).click();
			getDriver().findElement(By.id("android:id/button2")).click();
			Thread.sleep(1000);
			getDriver().findElement(By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']")).click();
			//Logout
			//TestBase.logOut(valid_msisdn);
		}catch (Exception e) {
			try {
				getDriver().findElement(By.id("android:id/button3")).click();
				getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/experience_type")).click();
				getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Network Speed']")).click();
				getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/rating_ratingBar")).click();
				getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/rating_btn_dialog_positive")).click();
				TestUtils.assertSearchText("ID", "android:id/alertTitle", "Feedback sent");

				getDriver().pressKeyCode(AndroidKeyCode.BACK);
			} catch (Exception e1) {

			}
			reportHomepage( totalSubVal,  totalSyncsentVal,  totalSyncpendingVal,  totalSynConfVal,  totalRejectVal);
		}
    }

	@Test
	@Parameters({"dataEnv"})
	public static void captureCooDetailsTest(String dataEnv) throws Exception {
		WebDriverWait wait = new WebDriverWait(getDriver(), 30);
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resource/" + dataEnv + "/data.conf.json"));
		JSONObject envs = (JSONObject) config.get("ReRegistration");

		String valid_msisdn = (String) envs.get("valid_msisdn");
		String ninVerificationMode = (String) envs.get("ninVerificationMode");
		String nin = (String) envs.get("nin");

		navigateToReRegistration();

		//Confirm COO checkbox
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/change_sim_ownership")));
		TestUtils.testTitle("Change of Ownership (COO) Checkbox Test");
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/change_sim_ownership", "Change of Ownership");
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/change_sim_ownership")).click();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/change_sim_ownership")).click();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/change_sim_ownership")).click();

		//To confirm that there is a settings that returns the list of COO document types
		TestUtils.testTitle("To confirm that there is a settings that returns the list of COO document types");
		String cooDocument=TestUtils.retrieveSettingsApiCall(dataEnv, "CHANGE_OF_OWNERSHIP_DOCUMENTS");

		if (!cooDocument.contains("Affidavit")){
			testInfo.get().error("Affidavit not found");
		}else{
			testInfo.get().info("Affidavit found");
		}

		if (!cooDocument.contains("Letter of Authorization")){
			testInfo.get().error("Letter of Authorization not found");
		}else{
			testInfo.get().info("Letter of Authorization found");
		}

		//To confirm that the default value for COO document format is  JPG, JPEG
		TestUtils.testTitle("To confirm that the default value for COO document format is  JPG, JPEG");
		String cooDocumentType=TestUtils.retrieveSettingsApiCall(dataEnv, "CHANGE_OF_OWNERSHIP_DOCUMENTS_FORMAT");
		TestUtils.assertTwoValues(cooDocumentType, "JPG,JPEG");

		//To confirm that the default value for COO document size is 512KB
		TestUtils.testTitle("To confirm that the default value for COO document size is  512KB");
		String cooDocumentSize=TestUtils.retrieveSettingsApiCall(dataEnv, "CHANGE_OF_OWNERSHIP_DOCUMENTS_SIZE");
		TestUtils.assertTwoValues(cooDocumentSize, " 512");

		// Enter valid msisdn with valid OTP
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/primary_msisdn_field")));
		TestUtils.testTitle("Enter valid MSISDN with valid OTP: " + valid_msisdn + " for Re-Registration");
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/primary_msisdn_field")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/primary_msisdn_field")).sendKeys(valid_msisdn);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/submit_button")).click();

		//BioMetrics Verification
		verifyBioMetricsTest();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/button1")));
		getDriver().findElement(By.id("android:id/button1")).click();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/otp_request_button")));

		//Test Cancel Button on Request OTP Modal
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/otp_request_button")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/otpHintMessage")));
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/cancel_otp")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/otp_request_button")));

		//Request OTP Modal
		TestUtils.testTitle("Request OTP Modal");
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/otp_request_button")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/otpHintMessage")));
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/otpHintMessage", "Tick options for OTP Request");
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/primary_phone_number", "Primary Phone Number");
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/alt_phone_number", "Alternate Phone Number");
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/email_address", "Email Address");
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/cancel_otp", "CANCEL");
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/request_otp", "REQUEST");
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/alt_phone_number")).click();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/request_otp")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/otp_field")));

		// DB Connection for OTP
		String valid_OTP = ConnectDB.getOTP(valid_msisdn);
		String ValidOTP = "Enter valid OTP : " + valid_OTP;
		TestUtils.testTitle(ValidOTP);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/otp_field")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/otp_field")).sendKeys(valid_OTP);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/otp_confirm_button")).click();

		// Next button
		try{
			Thread.sleep(2000);
			if(getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/next_button")).isDisplayed()){
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/next_button")));
				getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/next_button")).click();
			}

		}catch (Exception e){

		}

		try{
			//Proceed if NIN modal comes up
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
			//NIN Verification
			TestBase.verifyNINTest(nin, ninVerificationMode);

			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Personal Details']")));
			TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Personal Details']", "Personal Details");

		} catch(Exception e){
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Personal Details']")));
			TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Personal Details']", "Personal Details");
		}


		//Confirm that user can do both Individual
		TestUtils.testTitle("Confirm that user can do both Individual ");
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='Individual']")).click();
		TestUtils.assertSearchText("XPATH", "//android.widget.CheckedTextView[@text='Individual']", "Individual");
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Individual']")).click();

		Form.changeOfOwnerForm(dataEnv);

	}

	@Test
	public static void verifyBioMetricsTest() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), 60);
		try{
			TestUtils.scrollDown();
		}catch (Exception e){

		}
		//Proceed
		Thread.sleep(2000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/capture_image_button")));
		Thread.sleep(2000);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/capture_image_button")).click();

		try{
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/captureButton")));

			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/captureButton")).click();
		}catch (Exception e){

			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/buttonCapturePicture")));

			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/buttonCapturePicture")).click();
		}
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
		String fingerMatch=getDriver().findElement(By.id("android:id/message")).getText();
		if(fingerMatch.contains("No finger was captured")){
			TestUtils.assertSearchText("ID", "android:id/message", "No finger was captured");
		}else{
			TestUtils.assertSearchText("ID", "android:id/message", "Subscriber does not have fingerprint saved. Verification would proceed to the next verification option.");
		}
		getDriver().findElement(By.id("android:id/button1")).click();
		try {
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
			Thread.sleep(500);
			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/captureButton")).click();
			Thread.sleep(1000);
			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/ok")).click();
			Thread.sleep(500);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Ageing']")));
			getDriver().findElement(By.xpath("//android.widget.TextView[@text='Ageing']")).click();
			Thread.sleep(1000);
			//Save enrollment
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/fp_save_enrolment")));
			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/fp_save_enrolment")).click();
		}catch (Exception e){
			System.out.println(e);
			//Fingerprint matching unsuccessful. You will be allowed to proceed to the next verification option.
		}

	}

	public static void reportHomepage(int totalSubVal, int totalSyncsentVal, int totalSyncpendingVal, int totalSynConfVal, int totalRejectVal) throws Exception {

		WebDriverWait wait = new WebDriverWait(getDriver(), 30);

		//
		navigateToReportsPage();
		Thread.sleep(1000);
		TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/refresh_button");
		Thread.sleep(1000);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/refresh_button")).click();
		Thread.sleep(1000);
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/btn_back_home")).click();
        Thread.sleep(1000);
        navigateToReportsPage();
        Thread.sleep(1000);

		String totalRegistrationsValString = getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/reg_subscribers")).getText();
		String totalSyncSentValString = getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/total_sync_sent")).getText();
		String totalSyncPendingValString = getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/total_pending")).getText();
		String totalSyncConfirmedValString = getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/sync_confirmed")).getText();
		String total_rejectedValString = getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/total_rejected")).getText();

		int actualTotalRegistrationsVal = TestUtils.convertToInt(totalRegistrationsValString);
		int actualTotalSyncSentVal = TestUtils.convertToInt(totalSyncSentValString);
		int actualTotalSyncPendingVal = TestUtils.convertToInt(totalSyncPendingValString);
		int actualTotalSyncConfirmedVal = TestUtils.convertToInt(totalSyncConfirmedValString);
		int total_rejectedVal = TestUtils.convertToInt(total_rejectedValString);

		//int expectedTotalRegistrationsVal = actualTotalSyncSentVal + actualTotalSyncPendingVal;

		try {
			totalSubVal+=1;
			Assert.assertEquals(totalSubVal, actualTotalRegistrationsVal);
			testInfo.get().log(Status.INFO, "Total Registrations (" + totalSubVal + ") is equal to Actual Total Reg  (" + actualTotalRegistrationsVal + ") ");

			totalSyncsentVal+=1;
			Assert.assertEquals(totalSyncsentVal, actualTotalSyncSentVal);
			testInfo.get().log(Status.INFO, "Total Sync Sent (" + totalSyncsentVal + ") is equal to Actual Total Sync Sent  (" + actualTotalSyncSentVal + ") ");

			Assert.assertEquals(totalSyncpendingVal, actualTotalSyncPendingVal);
			testInfo.get().log(Status.INFO, "Total Sync Pending (" + totalSyncpendingVal + ") is equal to Actual Total Sync Pending  (" + actualTotalSyncPendingVal + ") ");

			totalSynConfVal+=1;
			Assert.assertEquals(totalSynConfVal, actualTotalSyncConfirmedVal);
			testInfo.get().log(Status.INFO, "Total Sync Confirmed (" + totalSynConfVal + ") is equal to Actual Total Sync Confirmed  (" + actualTotalSyncConfirmedVal + ") ");

			Assert.assertEquals(totalRejectVal, total_rejectedVal);
			testInfo.get().log(Status.INFO, "Total Rejected (" + total_rejectedVal + ") is equal to Actual Total Rejected (" + totalRejectVal + ") ");


		} catch (Error e) {

			verificationErrors.append(e.toString());
			String verificationErrorString = verificationErrors.toString();
			testInfo.get().error("Summation not equal");
			testInfo.get().error(verificationErrorString);
		}

		//Return to capture page
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/btn_back_home")).click();



	}

	public static void logOutUser(String valid_username) throws InterruptedException {
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


}
