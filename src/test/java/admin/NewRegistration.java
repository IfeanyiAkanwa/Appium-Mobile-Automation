package admin;

import com.aventstack.extentreports.Status;
import db.ConnectDB;
import io.appium.java_client.android.AndroidKeyCode;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import demographics.Features;
import demographics.Form;
import utils.Asserts;
import utils.TestBase;
import utils.TestUtils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import static admin.ReportsTest.navigateToReportsPage;

public class NewRegistration extends TestBase {

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

	int totalSubVal=0;int totalSyncsentVal = 0;int totalSyncpendingVal = 0;int totalSynConfVal = 0;int totalRejectVal = 0;

	@Parameters({ "dataEnv" })
	@BeforeMethod
	public static void parseJson(String dataEnv) throws IOException, ParseException {
		File path = null;
		File classpathRoot = new File(System.getProperty("user.dir"));
		if (dataEnv.equalsIgnoreCase("stagingData")) {
			path = new File(classpathRoot, "src/test/resource/" + dataEnv + "/data.conf.json");
		} else {
			path = new File(classpathRoot, "src/test/resource/" + dataEnv + "/data.conf.json");
		}
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader(path));

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
	}

	@Parameters({ "systemPort", "deviceNo", "server","deviceName", "testConfig", "dataEnv" })
	@Test
	public void removeNewRegPrivilegeTest(String systemPort, int deviceNo, String server, String deviceName, String testConfig, String dataEnv) throws Exception {

		//initial setting
		String initialSetting=TestUtils.retrieveSettingsApiCall(dataEnv, "RETRIEVE_AVAILABLE_USECASES");

		if(initialSetting.contains("NMS")){
			//Continue the setting is available already, remove NMS
			//*********UPDATING SETTING FOR AVAILABLE USE CASE************
			TestUtils.testTitle("UPDATING SETTING FOR AVAILABLE USE CASE(NMS)");
			String SettinVal= initialSetting.replace(",NMS", "");
			SettinVal = SettinVal.replace("NMS,","");
			if (SettinVal.contains("NMS")){
				SettinVal = SettinVal.replace("NMS","");
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
		JSONObject envs = (JSONObject) config.get("NewRegistration");

		String valid_username = (String) envs.get("valid_username");
		String generalUserPassword = (String) envs.get("generalUserPassword");
		String lga = (String) envs.get("lga");

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

		// Select New Registration
		TestUtils.testTitle("Select New Registration");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/typeofreg")));
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/typeofreg")).click();
		Thread.sleep(500);
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/alertTitle", "Select Registration Type");
		try{
			getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='New Registration']")).click();
			testInfo.get().error("NEW REGISTRATION is not removed from the AVAILABLE-USE-CASE");
		}catch (Exception e){
			testInfo.get().info("NEW REGISTRATION is not found, hence successfully removed from AVAILABLE-USE-CASE");
		}

		Thread.sleep(1000);
		if(initialSetting.contains("NMS")) {
			//Returning initial setting value
			TestUtils.testTitle("Returning initial setting value(" + initialSetting + ")");
			JSONObject getSettingParams = TestUtils.createSettingObject("PILOT-AVAILABLE-USE-CASE", initialSetting, "All available registration use case(SS,AR,CR,CN,NMS,RR,MP,MR)");
			TestUtils.updateSettingsApiCall(dataEnv, getSettingParams);
		}else{
			//Add NMS and update data
			//Returning initial setting value
			initialSetting+=",NMS";
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
	public void addBarCodePrivilegeTest(String systemPort, int deviceNo, String server, String deviceName, String testConfig, String dataEnv) throws Exception {
		String initialSetting=TestUtils.retrieveSettingsApiCall(dataEnv, "GET_DETAILS_FROM_BARCODE_USECASES");

		if(!initialSetting.contains("NMS")){
			//Continue the setting is available already, remove NMS
			//*********UPDATING SETTING FOR AVAILABLE USE CASE************
			TestUtils.testTitle("UPDATING SETTING FOR GET_DETAILS_FROM_BARCODE_USECASES(NMS)");
			/*String SettinVal= initialSetting.replace(",NMS", "");
			SettinVal = SettinVal.replace("NMS,","");
			if (SettinVal.contains("NMS")){
				SettinVal = SettinVal.replace("NMS","");
			}*/
			String SettinVal= initialSetting+",NMS";

			JSONObject getSettingParams=TestUtils.createSettingObject("PILOT-GET-DETAILS-FROM-BARCODE-USECASES", SettinVal,"This determines the use case where details is retrieved using barcode technology");
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
		JSONObject envs = (JSONObject) config.get("NewRegistration");

		String valid_username = (String) envs.get("valid_username2");
		String generalUserPassword = (String) envs.get("generalUserPassword");
		String lga = (String) envs.get("lga");

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

		// Select New Registration
		TestUtils.testTitle("Select New Registration");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/typeofreg")));
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/typeofreg")).click();
		Thread.sleep(500);
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/alertTitle", "Select Registration Type");
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='New Registration']")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/next_button")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/pageTitle")));
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/pageTitle", "New Registration");

		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/scanBarCode", "Scan Bar Code");

		Thread.sleep(1000);
		if(!initialSetting.contains("NMS")) {
			//Returning initial setting value
			String SettinVal= initialSetting.replace(",NMS", "");
			SettinVal = SettinVal.replace("NMS,","");
			if (SettinVal.contains("NMS")){
				SettinVal = SettinVal.replace("NMS","");
			}
			TestUtils.testTitle("Returning initial setting value(" + initialSetting + ")");
			JSONObject getSettingParams=TestUtils.createSettingObject("PILOT-GET-DETAILS-FROM-BARCODE-USECASES", SettinVal,"This determines the use case where details is retrieved using barcode technology");
			TestUtils.updateSettingsApiCall(dataEnv, getSettingParams);
		}else{
			//Remove NMS and update data
			String SettinVal= initialSetting.replace(",NMS", "");
			SettinVal = SettinVal.replace("NMS,","");
			if (SettinVal.contains("NMS")){
				SettinVal = SettinVal.replace("NMS","");
			}
			//Returning initial setting value
			TestUtils.testTitle("Returning initial setting value(" + initialSetting + ")");
			JSONObject getSettingParams=TestUtils.createSettingObject("PILOT-GET-DETAILS-FROM-BARCODE-USECASES", SettinVal,"This determines the use case where details is retrieved using barcode technology");
			TestUtils.updateSettingsApiCall(dataEnv, getSettingParams);
		}


	}

	@Parameters({ "dataEnv"})
	@Test
	public void noneNewRegPrivilegeTest(String dataEnv) throws Exception {
		WebDriverWait wait = new WebDriverWait(getDriver(), 30);
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resource/" + dataEnv + "/data.conf.json"));
		JSONObject envs = (JSONObject) config.get("NewRegistration");

		String valid_username = (String) envs.get("valid_username");
		String generalUserPassword = (String) envs.get("generalUserPassword");
		String lga = (String) envs.get("lga");

		TestBase.Login1( valid_username, generalUserPassword);
		Thread.sleep(500);
		TestUtils.testTitle("To ensure agents without New registration privilege can't perform the action when New registration  is in the list of available  use cases");
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

		// Select New Registration
		TestUtils.testTitle("Select New Registration");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/typeofreg")));
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/typeofreg")).click();
		Thread.sleep(500);
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/alertTitle", "Select Registration Type");
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='New Registration']")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/alertTitle", "No Privilege");
		TestUtils.assertSearchText("ID", "android:id/message", "You are not allowed to access New Registration because you do not have the New Registration privilege");
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
	public void NewRegPrivilegesTest(String dataEnv) throws Exception {
		WebDriverWait wait = new WebDriverWait(getDriver(), 30);
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resource/" + dataEnv + "/data.conf.json"));
		JSONObject envs = (JSONObject) config.get("NewRegistration");

		String noIndividualReg = (String) envs.get("noIndividualReg");
		String noCompanyReg = (String) envs.get("noCompanyReg");
		String noPrivilegeUser = (String) envs.get("noPrivilegeUser");
		String generalUserPassword = (String) envs.get("generalUserPassword");
		String noMultiRegUser = (String) envs.get("noMultiRegUser");
		String lga = (String) envs.get("lga");

		//To confirm that only  users with the individual registration privilege can perform individual registration
		TestUtils.testTitle("To confirm that only  users with the individual registration privilege can perform individual registration("+noCompanyReg+")");
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

		// Select New Registration
		TestUtils.testTitle("Select New Registration");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/typeofreg")));
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/typeofreg")).click();
		Thread.sleep(500);
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/alertTitle", "Select Registration Type");
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='New Registration']")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/next_button")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")));

		//To confirm that a user is unable to use the barcode scanner to get the SIM Serial of a SIM card on New Registration SIM Serial
		TestUtils.testTitle("To confirm that a user is unable to use the barcode scanner to get the SIM Serial of a SIM card on New Registration SIM Serial");
		String result= TestUtils.retrieveSettingsApiCall( dataEnv, "GET_DETAILS_FROM_BARCODE_USECASES");
		try{
			TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/scanBarCode", "Scan Bar Code");
			testInfo.get().error("Scan bar code found, Setting name GET_DETAILS_FROM_BARCODE_USECASES["+result+"] failed");
		}catch(Exception e){
			if(result.contains("NMS")){
                testInfo.get().error("NMS is found on the settings but scan bar code is not displaying, Check Setting name GET_DETAILS_FROM_BARCODE_USECASES["+result+"]");
			}else{
				testInfo.get().info("Scan bar code is not available[Setting name GET_DETAILS_FROM_BARCODE_USECASES["+result+"]]");
			}
		}

		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).sendKeys(valid_msisdn);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/simSerialField")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/simSerialField")).sendKeys(valid_simSerial);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/addMsisdnSimSerialButton")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/message", "Msisdn is valid");
		getDriver().findElement(By.id("android:id/button1")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")));
		getDriver().findElement(By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']")).click();
		//Log Out
	//	logOutUser(noCompanyReg);

		//To confirm that users without the individual registration privileges  cannot perform Individual registration
		TestUtils.testTitle("To confirm that users without the individual registration privileges  cannot perform Individual registration("+noIndividualReg+")");
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

		// Select New Registration
		TestUtils.testTitle("Select New Registration");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/typeofreg")));
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/typeofreg")).click();
		Thread.sleep(500);
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/alertTitle", "Select Registration Type");
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='New Registration']")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/next_button")).click();

		Thread.sleep(1000);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).sendKeys(valid_msisdn);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/simSerialField")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/simSerialField")).sendKeys(valid_simSerial);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/addMsisdnSimSerialButton")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/message", "Msisdn is valid");
		getDriver().findElement(By.id("android:id/button1")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")));
		getDriver().findElement(By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']")).click();
		//Log Out
	//	logOutUser(noIndividualReg);

		//To confirm that users without both the company  and individual  registration privileges  cannot perform New registration
		TestBase.Login1( noPrivilegeUser, generalUserPassword);
		Thread.sleep(500);
		TestUtils.testTitle("To confirm that users without both the company  and individual  registration privileges  cannot perform New registration("+noPrivilegeUser+")");
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

		// Select New Registration
		TestUtils.testTitle("Select New Registration");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/typeofreg")));
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/typeofreg")).click();
		Thread.sleep(500);
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/alertTitle", "Select Registration Type");
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='New Registration']")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/alertTitle", "Error");
		TestUtils.assertSearchText("ID", "android:id/message", "You need to have either Individual Registration privilege or Corporate Registration to do New Registration registration");
		Thread.sleep(500);
		getDriver().findElement(By.id("android:id/button1")).click();
		Thread.sleep(500);
		//Log Out
	//	logOutUser(noPrivilegeUser);

		//To confirm that users without the individual registration privileges  cannot perform Individual registration
		TestUtils.testTitle("To confirm that users without the individual registration privileges  cannot perform Individual registration("+noMultiRegUser+")");
		TestBase.Login1( noMultiRegUser, generalUserPassword);
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

		// Select New Registration
		TestUtils.testTitle("Select New Registration");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/typeofreg")));
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/typeofreg")).click();
		Thread.sleep(500);
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/alertTitle", "Select Registration Type");
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='New Registration']")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/next_button")).click();

		Thread.sleep(1000);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).sendKeys(valid_msisdn);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/simSerialField")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/simSerialField")).sendKeys(valid_simSerial);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/addMsisdnSimSerialButton")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/message", "Msisdn is valid");
		getDriver().findElement(By.id("android:id/button1")).click();

		Thread.sleep(1000);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).sendKeys(valid_msisdn2);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/simSerialField")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/simSerialField")).sendKeys(valid_simSerial2);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/addMsisdnSimSerialButton")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/message", "You are not allowed to capture more than 1 MSISDN and SIM Serial in one registration");
		getDriver().findElement(By.id("android:id/button1")).click();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")));
		getDriver().findElement(By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']")).click();
		//Log Out
		//logOutUser(noIndividualReg);

	}

	
	
	@Parameters({ "dataEnv"})
	@Test
	public void NPSValidationTest(String dataEnv) throws Exception {
		WebDriverWait wait = new WebDriverWait(getDriver(), 60);

		// Select Msisdn Category
		TestUtils.testTitle("Select Mobile Msisdn Category");
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnCategorySpinner")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Mobile']")).click();
		Thread.sleep(1000);
		TestUtils.assertSearchText("XPATH", "//android.widget.CheckedTextView[@text='Mobile']", "Mobile");
		Thread.sleep(1000);

		// To ensure that NPS successfully Validates a number that is AVAILABLE on msisdn_provision_status
		TestUtils.testTitle("To ensure that NPS successfully Validates a number that is AVAILABLE on msisdn_provision_status : " + valid_msisdn	+ " and SIM Serial: " + valid_simSerial);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).sendKeys(valid_msisdn);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/simSerialField")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/simSerialField")).sendKeys(valid_simSerial);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/addMsisdnSimSerialButton")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/message", "Msisdn is valid");
		getDriver().findElement(By.id("android:id/button1")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")));
		Thread.sleep(1000);
		//Remove MSISDN
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/delete_button")).click();

		//To verify that NPS logs Valid Sibel verified numbers on MSISDN_PROVISION_STATUS table
		String status="Available";
		TestUtils.testTitle("To verify that NPS logs Valid Sibel verified numbers on MSISDN_PROVISION_STATUS table("+status+")");
		TestUtils.assertTableValue("msisdn_provision_status", "msisdn", valid_msisdn, "status", status);

		// To ensure that NPS fails Validation for a number that is ACTIVE on msisdn_provision_status
		TestUtils.testTitle("To ensure that NPS fails Validation for a number that is ACTIVE on msisdn_provision_status : " + invalid_msisdn	+ " and SIM Serial: " + invalid_simSerial);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).sendKeys(invalid_msisdn);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/simSerialField")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/simSerialField")).sendKeys(invalid_simSerial);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/addMsisdnSimSerialButton")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/message", "The MSISDN has an unrecognized National Destination Code. "
				+ "Please ensure the MSISDN starts with any of the following NDCs: "
				+ "0701,0708,0802,0808,0812,0901,0902,0904,0907,0809,0817,0818,0908,"
				+ "0909,0705,0805,0807,0811,0815,0905,0915,0703,0706,0803,0806,0810,"
				+ "0813,0814,0816,0903,0906 and must be 11 digits");
		getDriver().findElement(By.id("android:id/button1")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")));
		Thread.sleep(500);



	}

	@Parameters({ "dataEnv"})
	@Test
	public void NDCValidationTest(String dataEnv) throws Exception {
		WebDriverWait wait = new WebDriverWait(getDriver(), 60);

		// Select Msisdn Category
		TestUtils.testTitle("Select Mobile Msisdn Category");
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnCategorySpinner")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Mobile']")).click();
		Thread.sleep(1000);
		TestUtils.assertSearchText("XPATH", "//android.widget.CheckedTextView[@text='Mobile']", "Mobile");
		Thread.sleep(1000);

		// To confirm that msisdn fails validation if the prefix is not allowed by NDC
		TestUtils.testTitle("To confirm that msisdn fails validation if the prefix is not allowed by NDC : " + invalidPrefixMsisdn	+ " and SIM Serial: " + invalidPrefixSimSerial);
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
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")));
		Thread.sleep(500);

		// To confirm error message when user tries to validate a number that doesn't conform to NDCs rule
		TestUtils.testTitle("To confirm error message when user tries to validate a number that doesn't conform to NDCs rule : " + invalidPrefixMsisdn	+ " and SIM Serial: " + invalidPrefixSimSerial);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).sendKeys(invalid_msisdn);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/simSerialField")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/simSerialField")).sendKeys(valid_simSerial);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/addMsisdnSimSerialButton")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/message", "The MSISDN has an unrecognized National Destination Code. "
				+ "Please ensure the MSISDN starts with any of the following NDCs: "
				+ "0701,0708,0802,0808,0812,0901,0902,0904,0907,0809,0817,0818,0908,"
				+ "0909,0705,0805,0807,0811,0815,0905,0915,0703,0706,0803,0806,0810,"
				+ "0813,0814,0816,0903,0906 and must be 11 digits");
		getDriver().findElement(By.id("android:id/button1")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")));
		Thread.sleep(500);

		// To confirm that the check is applied on NEW REG  msisdn  input fields
		TestUtils.testTitle("To confirm that the check is applied on NEW REG  msisdn  input fields:"+invalid_msisdn+"|"+ valid_msisdn + " and SIM Serial: " + valid_simSerial);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).sendKeys(invalid_msisdn);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/simSerialField")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/simSerialField")).sendKeys(valid_simSerial);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/addMsisdnSimSerialButton")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/message", "The MSISDN has an unrecognized National Destination Code. "
				+ "Please ensure the MSISDN starts with any of the following NDCs: "
				+ "0701,0708,0802,0808,0812,0901,0902,0904,0907,0809,0817,0818,0908,"
				+ "0909,0705,0805,0807,0811,0815,0905,0915,0703,0706,0803,0806,0810,"
				+ "0813,0814,0816,0903,0906 and must be 11 digits");
		getDriver().findElement(By.id("android:id/button1")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/simSerialField")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).sendKeys(valid_msisdn);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/simSerialField")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/simSerialField")).sendKeys(valid_simSerial);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/addMsisdnSimSerialButton")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/message", "Msisdn is valid");
		getDriver().findElement(By.id("android:id/button1")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")));
		Thread.sleep(500);
		//Remove MSISDN
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/delete_button")).click();


		// To confirm that the check is applied on Multiple registration input fields on New Reg
		TestUtils.testTitle("To confirm that the check is applied on Multiple registration input fields on New Reg:"+invalid_msisdn+"|"+ valid_msisdn + " and SIM Serial: " + valid_simSerial);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).sendKeys(invalid_msisdn);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/simSerialField")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/simSerialField")).sendKeys(valid_simSerial);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/addMsisdnSimSerialButton")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/message", "The MSISDN has an unrecognized National Destination Code. "
				+ "Please ensure the MSISDN starts with any of the following NDCs: "
				+ "0701,0708,0802,0808,0812,0901,0902,0904,0907,0809,0817,0818,0908,"
				+ "0909,0705,0805,0807,0811,0815,0905,0915,0703,0706,0803,0806,0810,"
				+ "0813,0814,0816,0903,0906 and must be 11 digits");
		getDriver().findElement(By.id("android:id/button1")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/simSerialField")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).sendKeys(valid_msisdn);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/simSerialField")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/simSerialField")).sendKeys(valid_simSerial);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/addMsisdnSimSerialButton")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/message", "Msisdn is valid");
		getDriver().findElement(By.id("android:id/button1")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")));
		//Remove MSISDN
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/delete_button")).click();

		//Repeat valid MSISDN
		Thread.sleep(1000);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).sendKeys(valid_msisdn);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/simSerialField")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/simSerialField")).sendKeys(valid_simSerial);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/addMsisdnSimSerialButton")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/message", "Msisdn is valid");
		getDriver().findElement(By.id("android:id/button1")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")));
		Thread.sleep(500);

		// To confirm that msisdn should proceed  to backend validation if the prefix is  allowed by NDC
		TestUtils.testTitle("To confirm that msisdn should proceed  to backend validation if the prefix is  allowed by NDC : " + valid_msisdn + " and SIM Serial: " + valid_simSerial);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).sendKeys(valid_msisdn2);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/simSerialField")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/simSerialField")).sendKeys(valid_simSerial2);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/addMsisdnSimSerialButton")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/message", "Msisdn is valid");
		getDriver().findElement(By.id("android:id/button1")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")));
		Thread.sleep(500);

		// Confirm user cannot add more than MAX(2) msisdn per registration
		/*TestUtils.testTitle("Confirm user cannot add more than MAX(2) msisdn per registration: " + valid_fixed_msisdn + " and SIM Serial: " + valid_fixed_serial);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).sendKeys(valid_fixed_msisdn);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/simSerialField")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/simSerialField")).sendKeys(valid_fixed_serial);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/addMsisdnSimSerialButton")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/message", "You are not allowed to capture more than 2 MSISDN and SIM Serial in one registration");
		getDriver().findElement(By.id("android:id/button1")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")));
		Thread.sleep(500);
		//Remove MSISDN
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/delete_button")).click();
		//Remove MSISDN
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/delete_button")).click();


		// Enter msisdn less than 11 digits
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).sendKeys(valid_fixed_msisdn);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/simSerialField")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/simSerialField")).sendKeys(valid_fixed_serial);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/addMsisdnSimSerialButton")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/message", "The MSISDN has an unrecognized National Destination Code. "
				+ "Please ensure the MSISDN starts with any of the following NDCs: "
				+ "0701,0708,0802,0808,0812,0901,0902,0904,0907,0809,0817,0818,"
				+ "0908,0909,0705,0805,0807,0811,0815,0905,0915,0703,0706,0803,"
				+ "0806,0810,0813,0814,0816,0903,0906 and must be 11 digits");
		getDriver().findElement(By.id("android:id/button1")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")));*/
		Thread.sleep(500);

		// To confirm that the check is applied on Aternative Phone number input fields on Demographics
		TestUtils.testTitle("To confirm that the check is applied on Alternative Phone number input fields on Demographics[ Valid:"+valid_msisdn+"|Alternate:"+ invalid_msisdn + "] and SIM Serial: " + valid_simSerial);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/simSerialField")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).sendKeys(valid_msisdn);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/simSerialField")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/simSerialField")).sendKeys(valid_simSerial);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/addMsisdnSimSerialButton")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/message", "Msisdn is valid");
		getDriver().findElement(By.id("android:id/button1")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")));
		//Remove MSISDN
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/delete_button")).click();

		//Repeat for Alternate MSISDN
		Thread.sleep(1000);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).sendKeys(invalid_msisdn);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/simSerialField")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/simSerialField")).sendKeys(invalid_simSerial);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/addMsisdnSimSerialButton")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/message", "The MSISDN has an unrecognized National Destination Code. "
				+ "Please ensure the MSISDN starts with any of the following NDCs: "
				+ "0701,0708,0802,0808,0812,0901,0902,0904,0907,0809,0817,0818,0908,"
				+ "0909,0705,0805,0807,0811,0815,0905,0915,0703,0706,0803,0806,0810,"
				+ "0813,0814,0816,0903,0906 and must be 11 digits");
		getDriver().findElement(By.id("android:id/button1")).click();
		Thread.sleep(1000);

	}

	@Test
	public void newRegistrationFormTest() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), 30);
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
		String moreCharacters=valid_simSerial+"1234";
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

		// To check that an agent with REG MULTI MSISDN privilege can register more than specified MSISDN at a time
		TestUtils.testTitle("To check that an agent with REG MULTI MSISDN privilege can register more than specified MSISDN at a time: " + valid_msisdn + " and SIM Serial: " + valid_simSerial);
		//First MSISDN
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).sendKeys(valid_msisdn);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/simSerialField")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/simSerialField")).sendKeys(valid_simSerial);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/addMsisdnSimSerialButton")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/message", "Msisdn is valid");
		getDriver().findElement(By.id("android:id/button1")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")));
		Thread.sleep(500);
		//Second MSISDN
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).sendKeys(valid_msisdn2);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/simSerialField")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/simSerialField")).sendKeys(valid_simSerial2);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/addMsisdnSimSerialButton")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/message", "Msisdn is valid");
		getDriver().findElement(By.id("android:id/button1")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")));
		Thread.sleep(500);
		//Third MSISDN
		/*getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).sendKeys(valid_fixed_msisdn);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/simSerialField")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/simSerialField")).sendKeys(valid_fixed_serial);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/addMsisdnSimSerialButton")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/message", "You are not allowed to capture more than 2 MSISDN and SIM Serial in one registration");
		getDriver().findElement(By.id("android:id/button1")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")));
		Thread.sleep(500);*/

		//Remove First MSISDN
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/delete_button")).click();
		Thread.sleep(500);
		//Remove Second MSISDN
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/delete_button")).click();
		Thread.sleep(500);

		// Proceed after supplying empty details
		TestUtils.testTitle("Proceed after supplying empty details");
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).sendKeys("");
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/simSerialField")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/simSerialField")).sendKeys("");
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/addMsisdnSimSerialButton")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/message", "Required Input Field: Phone Number");
		getDriver().findElement(By.id("android:id/button1")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")));
		Thread.sleep(500);

		// Proceed after supplying only Msisdn
		TestUtils.testTitle("Proceed after supplying only Msisdn: " + valid_msisdn);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).sendKeys(valid_msisdn);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/simSerialField")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/simSerialField")).sendKeys("");
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/addMsisdnSimSerialButton")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/message", "Required Input Field: Sim Serial");
		getDriver().findElement(By.id("android:id/button1")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")));
		Thread.sleep(500);

		// Proceed after supplying only Sim Serial
		TestUtils.testTitle("Proceed after supplying only Sim Serial: " + valid_simSerial);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).sendKeys(" ");
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/simSerialField")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/simSerialField")).sendKeys(valid_simSerial);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/addMsisdnSimSerialButton")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/message", "Required Input Field: Phone Number");
		getDriver().findElement(By.id("android:id/button1")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")));
		Thread.sleep(500);

		// Proceed after supplying invalid msisdn and sim serial
		TestUtils.testTitle("Proceed after supplying invalid msisdn: (" + invalid_msisdn + ") and invalid sim serial: (" + invalid_simSerial + ") for validation");
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).sendKeys(invalid_msisdn);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/simSerialField")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/simSerialField")).sendKeys(invalid_simSerial);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/addMsisdnSimSerialButton")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/message", "The MSISDN has an unrecognized National Destination Code. "
				+ "Please ensure the MSISDN starts with any of the following NDCs: "
				+ "0701,0708,0802,0808,0812,0901,0902,0904,0907,0809,0817,0818,0908,"
				+ "0909,0705,0805,0807,0811,0815,0905,0915,0703,0706,0803,0806,0810,"
				+ "0813,0814,0816,0903,0906 and must be 11 digits");
		getDriver().findElement(By.id("android:id/button1")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")));
		Thread.sleep(500);

	}
	
	@Parameters({ "dataEnv"})
	@Test
	public void navigateToCapture(String dataEnv) throws Exception {
		Features.navigateToCaptureMenuTest();
		
	}
	
	@Test
	public void captureReportRecords() throws InterruptedException {
		Features.captureReportRecords();
	}
	
	@Parameters({ "dataEnv"})
	@Test
	public void selectRegistrationType(String dataEnv) throws Exception {
		Features.selectRegistration(dataEnv, "New Registration");
		
	}


	@Parameters({ "dataEnv"})
	@Test
	public void singleMsidnValidationTest(String dataEnv) throws Exception {
		Features.msisdnValidationOnline(dataEnv, "NMS");
		
	}


	

		

		
//		//NIN Verification
//		int ninStatus=TestBase.verifyNINTest(nin, ninVerificationMode);
//		Thread.sleep(2000);
//		try{
//			getDriver().findElement(By.id("android:id/button1")).click();
//		}catch(Exception e){
//
//		}
//		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Personal Details']")));
//		TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Personal Details']", "Personal Details");
//
//		if (ninStatus==0){
//			//Use Form that populate data itself
//			Form.individualNigerianForm(dataEnv);
//		}else{
//			//Use autoPopulated Form
//			Form.individualNigerianFormAutoPopulate(dataEnv);
//		}
//
//		/*//To confirm that the registration category is saved on DB after successful registration
//		TestUtils.testTitle("To confirm that the registration category is saved on DB after successful registration:"+valid_msisdn);
//		TestUtils.assertTableValue("msisdn_detail", "msisdn", valid_msisdn, "msisdn_category", "MOBILE");
//
//		//To confirm that if ICC platform msisdn is added during a single registration on NMS, the ICC platform name should be logged on MSISDN_DETAIL table
//		TestUtils.testTitle("To confirm that if ICC platform msisdn is added during a single registration on NMS, the ICC platform name should be logged on MSISDN_DETAIL table :"+valid_msisdn);
//		TestUtils.assertTableValue("msisdn_detail", "msisdn", valid_msisdn, "platform", "ICC3");
//
//		//Do Bulk Assert for Table checking
//		//TestUtils.assertBulkTables(valid_msisdn);
//		Thread.sleep(5000);
//		TestUtils.assertBulkTables(valid_msisdn, "NIGERIA");*/
//        if (releaseRegItem==true) {
//            String quarantineRegPk=ConnectDB.selectQueryOnTable("bfp_sync_log", "msisdn", valid_msisdn, "pk");
//            String uniqueId=ConnectDB.selectQueryOnTable("bfp_sync_log", "msisdn", valid_msisdn, "unique_id");
//
//            //Release quarantine item
//            TestUtils.testTitle("Release the quarantined item("+quarantineRegPk+")");
//            Thread.sleep(1500);
//            JSONObject payload = new JSONObject();
//            payload.put("quarantineRegPk", quarantineRegPk);
//            payload.put("uniqueId", uniqueId);
//            payload.put("feedback", "test");
//            payload.put("loggedInUserId", "2067");
//            TestUtils.releaseActionApiCall(dataEnv, payload);
//            ConnectDB.query(uniqueId, dataEnv, "NMS");
//        }
//		try {
//			getDriver().pressKeyCode(AndroidKeyCode.BACK);
//			Thread.sleep(1000);
//			getDriver().findElement(By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']")).click();
//			Thread.sleep(1000);
//			getDriver().findElement(By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']")).click();
//			Thread.sleep(1000);
//			getDriver().findElement(By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']")).click();
//			getDriver().findElement(By.id("android:id/button2")).click();
//			Thread.sleep(1000);
//			getDriver().findElement(By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']")).click();
//			//Logout
//			//TestBase.logOut(valid_msisdn);
//		}catch (Exception e){
//			try{
//				getDriver().findElement(By.id("android:id/button3")).click();
//				getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/experience_type")).click();
//				getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Network Speed']")).click();
//				getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/rating_ratingBar")).click();
//				getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/rating_btn_dialog_positive")).click();
//				TestUtils.assertSearchText("ID", "android:id/alertTitle", "Feedback sent");
//
//				getDriver().pressKeyCode(AndroidKeyCode.BACK);
//			}catch (Exception e1){
//
//			}
//			reportHomepage( totalSubVal,  totalSyncsentVal,  totalSyncpendingVal,  totalSynConfVal,  totalRejectVal);
//		}
//	}
//
//	@Parameters({ "dataEnv"})
//	@Test
//	public static void captureForeignRegTest(String dataEnv) throws Exception {
//		WebDriverWait wait = new WebDriverWait(getDriver(), 30);
//
//		try {
//			//Proceed to Capture page
//			navigateToCaptureMenuTest();
//
//			//Proceed to new reg
//			newRegUseCaseTest(dataEnv);
//		}catch(Exception e){
//
//		}
//		// Select Msisdn Category
//		TestUtils.testTitle("Select Msisdn Category");
//		TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='MSISDN Category']", "MSISDN Category");
//		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnCategorySpinner")).click();
//		Thread.sleep(500);
//		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Mobile']")).click();
//		Thread.sleep(500);
//
//		// Proceed after supplying valid msisdns and sim serial
//		TestUtils.testTitle("Proceed after supplying valid msisdns: (" + valid_msisdn2 + ") and (" + valid_simSerial2 + ") for validation");
//
//		// Add another Number
//		TestUtils.testTitle("Add another Number");
//		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).clear();
//		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).sendKeys(valid_msisdn2);
//		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/simSerialField")).clear();
//		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/simSerialField")).sendKeys(valid_simSerial2);
//		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/addMsisdnSimSerialButton")).click();
//		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
//		TestUtils.assertSearchText("ID", "android:id/message", "Msisdn is valid");
//		getDriver().findElement(By.id("android:id/button1")).click();
//
//		TestUtils.scrollDown();
//
//		TestUtils.testTitle("Assert Second Number");
//		TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='" + valid_msisdn2 + "']", valid_msisdn2);
//		TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='" + valid_simSerial2 + "']", valid_simSerial2);
//		Thread.sleep(500);
//
//		try {
//			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/btnCapturePortrait")).click();
//			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/captureButton")).click();
//
//		}catch (Exception e){
//
//		}
//		//Select country
//		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/countryOfOriginSpinner")).click();
//		Thread.sleep(500);
//		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='AFGHANISTAN']")).click();
//		Thread.sleep(1000);
//
//		//Fill the foreigners form here
//		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/title", "Foreigner Registration");
//		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/edPassportNumber")).clear();
//		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/edPassportNumber")).sendKeys(TestUtils.generatePhoneNumber());
//		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/spForeignerTypes")).click();
//		Thread.sleep(1000);
//		TestUtils.assertSearchText("XPATH", "//android.widget.CheckedTextView[@text='Short Stay']", "Short Stay");
//		TestUtils.assertSearchText("XPATH", "//android.widget.CheckedTextView[@text='ECOWAS']", "ECOWAS");
//		TestUtils.assertSearchText("XPATH", "//android.widget.CheckedTextView[@text='Diplomat']", "Diplomat");
//		TestUtils.assertSearchText("XPATH", "//android.widget.CheckedTextView[@text='Legal Resident(With NIN)']", "Legal Resident(With NIN)");
//		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='ECOWAS']")).click();
//
//		try{
//			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/spForeignerTypes")).click();
//			getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Short Stay']")).click();
//
//			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/edStartDate")).click();
//			getDriver().findElement(By.id("android:id/button1")).click();
//			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/edEndDate")).click();
//			getDriver().findElement(By.xpath("//android.view.View[@text='30']")).click();
//			getDriver().findElement(By.id("android:id/button1")).click();
//			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/spForeignerDocs")).click();
//			getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Visa Page']")).click();
//			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/uploadButton")).click();
//			TestUtils.scrollUp();
//			TestUtils.scrollUntilElementIsVisible("XPATH", "//android.widget.TextView[@text='picture.jpg']");
//
//			getDriver().findElement(By.xpath("//android.widget.TextView[@text='picture.jpg']")).click();
//			Thread.sleep(500);
//
//		}catch (Exception e){
//			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/spForeignerTypes")).click();
//			getDriver().findElement(By.id("//android.widget.CheckedTextView[@text='ECOWAS']")).click();
//
//		}
//		Thread.sleep(1000);
//		TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/btnProceed");
//		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/btnProceed")).click();
//        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/button1")));
//        getDriver().findElement(By.id("android:id/button1")).click();
//
//		//BioMetrics Verification
//		Thread.sleep(1000);
//		TestBase.verifyBioMetricsTest();
//
//		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/nextButton")));
//		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/nextButton")).click();
//
//		//NIN Verification
//		int ninStatus=0;
//
//		try{
//			getDriver().findElement(By.id("android:id/button1")).click();
//		}catch(Exception e){
//
//		}
//
//		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Personal Details']")));
//		TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Personal Details']", "Personal Details");
//
//		if (ninStatus==0){
//			//Use Form that populate data itself
//			Form.individualForeignerForm(dataEnv);
//		}else{
//			//Use autoPopulated Form
//			Form.individualForeignerFormAutoPopulate(dataEnv);
//		}
//
//		/*//To confirm that the registration category is saved on DB after successful registration
//		TestUtils.testTitle("To confirm that the registration category is saved on DB after successful registration:"+valid_msisdn);
//		TestUtils.assertTableValue("msisdn_detail", "msisdn", valid_msisdn, "msisdn_category", "MOBILE");
//
//		//Do Bulk Assert for Table checking
//		//TestUtils.assertBulkTables(valid_msisdn);
//		Thread.sleep(5000);
//		TestUtils.assertBulkTables(valid_msisdn, "AFGHANISTAN");*/
//		String uniqueId=ConnectDB.selectQueryOnTable("bfp_sync_log", "msisdn", valid_msisdn, "pk");
//		ConnectDB.query( uniqueId, dataEnv, "FR");
//
//		try {
//			getDriver().pressKeyCode(AndroidKeyCode.BACK);
//			Thread.sleep(1000);
//			getDriver().findElement(By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']")).click();
//			Thread.sleep(1000);
//			getDriver().findElement(By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']")).click();
//			Thread.sleep(1000);
//			getDriver().findElement(By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']")).click();
//			getDriver().findElement(By.id("android:id/button2")).click();
//			Thread.sleep(1000);
//			getDriver().findElement(By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']")).click();
//			//Logout
//			//TestBase.logOut(valid_msisdn);
//		}catch (Exception e) {
//			try {
//				getDriver().findElement(By.id("android:id/button3")).click();
//				getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/experience_type")).click();
//				getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Network Speed']")).click();
//				getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/rating_ratingBar")).click();
//				getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/rating_btn_dialog_positive")).click();
//				TestUtils.assertSearchText("ID", "android:id/alertTitle", "Feedback sent");
//
//				getDriver().pressKeyCode(AndroidKeyCode.BACK);
//			} catch (Exception e1) {
//
//			}
//		}
//
//	}

//	public static void reportHomepage(int totalSubVal, int totalSyncsentVal, int totalSyncpendingVal, int totalSynConfVal, int totalRejectVal) throws Exception {
//
//		WebDriverWait wait = new WebDriverWait(getDriver(), 30);
//
//		//wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Total Rejected']")));
//
//		navigateToReportsPage();
//
//		String totalRegistrationsValString = getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/reg_subscribers")).getText();
//		String totalSyncSentValString = getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/total_sync_sent")).getText();
//		String totalSyncPendingValString = getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/total_pending")).getText();
//		String totalSyncConfirmedValString = getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/sync_confirmed")).getText();
//		String total_rejectedValString = getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/total_rejected")).getText();
//
//		int actualTotalRegistrationsVal = TestUtils.convertToInt(totalRegistrationsValString);
//		int actualTotalSyncSentVal = TestUtils.convertToInt(totalSyncSentValString);
//		int actualTotalSyncPendingVal = TestUtils.convertToInt(totalSyncPendingValString);
//		int actualTotalSyncConfirmedVal = TestUtils.convertToInt(totalSyncConfirmedValString);
//		int total_rejectedVal = TestUtils.convertToInt(total_rejectedValString);
//
//		//int expectedTotalRegistrationsVal = actualTotalSyncSentVal + actualTotalSyncPendingVal;
//
//		try {
//			Assert.assertEquals(totalSubVal, actualTotalRegistrationsVal);
//			testInfo.get().log(Status.INFO, "Total Registrations (" + totalSubVal + ") is equal to Actual Total Reg  (" + actualTotalRegistrationsVal + ") ");
//
//			Assert.assertEquals(totalSyncsentVal, actualTotalSyncSentVal);
//			testInfo.get().log(Status.INFO, "Total Sync Sent (" + totalSyncsentVal + ") is equal to Actual Total Sync Sent  (" + actualTotalSyncSentVal + ") ");
//
//			Assert.assertEquals(totalSyncpendingVal, actualTotalSyncPendingVal);
//			testInfo.get().log(Status.INFO, "Total Sync Pending (" + totalSyncpendingVal + ") is equal to Actual Total Sync Pending  (" + actualTotalSyncPendingVal + ") ");
//
//			Assert.assertEquals(totalSynConfVal, actualTotalSyncConfirmedVal);
//			testInfo.get().log(Status.INFO, "Total Sync Confirmed (" + totalSynConfVal + ") is equal to Actual Total Sync Confirmed  (" + actualTotalSyncConfirmedVal + ") ");
//
//			Assert.assertEquals(totalRejectVal, total_rejectedVal);
//			testInfo.get().log(Status.INFO, "Total Rejected (" + total_rejectedVal + ") is equal to Actual Total Rejected (" + actualTotalSyncConfirmedVal + ") ");
//
//
//		} catch (Error e) {
//
//			verificationErrors.append(e.toString());
//			String verificationErrorString = verificationErrors.toString();
//			testInfo.get().error("Summation not equal");
//			testInfo.get().error(verificationErrorString);
//		}
//
//		//Return to capture page
//		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/btn_back_home")).click();
//
//
//
//	}

//	@Parameters({ "dataEnv"})
//	@Test
//	public void captureCompanyNewSimTest(String dataEnv) throws Exception {
//
//		WebDriverWait wait = new WebDriverWait(getDriver(), 15);
//		JSONParser parser = new JSONParser();
//		JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resource/" + dataEnv + "/data.conf.json"));
//		JSONObject envs = (JSONObject) config.get("NewRegistration");
//
//		String valid_msisdn = (String) envs.get("valid_msisdn");
//		String valid_simSerial = (String) envs.get("valid_simSerial");
//		String lga = (String) envs.get("lga");
//		String nin = (String) envs.get("nin");
//		String ninVerificationMode = (String) envs.get("ninVerificationMode");
//
//		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Total Subscribers']")));
//		String totalSubBeforeCapture = getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/kpi_report_value")).getText();
//		int totalSubVal = TestUtils.convertToInt(totalSubBeforeCapture);
//
//		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Total Sync Confirmed']")));
//		String totalSynConfBeforeCapture = getDriver().findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/androidx.drawerlayout.widget.DrawerLayout/android.widget.RelativeLayout/android.widget.FrameLayout[1]/androidx.viewpager.widget.ViewPager/android.widget.RelativeLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.TextView")).getText();
//		int totalSynConfVal = TestUtils.convertToInt(totalSynConfBeforeCapture);
//
//		navigateToCaptureMenuTest();
//
//		// Select LGA of Registration
//		try{
//			TestUtils.testTitle("Select LGA of Registration: " + lga);
//			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/lga_of_reg")).click();
//			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/alertTitle")));
//			TestUtils.assertSearchText("ID", "android:id/alertTitle", "LGA of Registration*");
//			getDriver().findElement(By.xpath("//android.widget.TextView[@text='" + lga + "']")).click();
//			Thread.sleep(500);
//		}catch (Exception e){
//
//		}
//
//		// Select New Registration
//		TestUtils.testTitle("Select New Registration");
//		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/typeofreg")));
//		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/typeofreg")).click();
//		Thread.sleep(500);
//		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/alertTitle", "Select Registration Type");
//		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='New Registration']")).click();
//		Thread.sleep(500);
//		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/next_button")).click();
//		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/pageTitle")));
//		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/pageTitle", "New Registration");
//
//		// Select Msisdn Category
//		TestUtils.testTitle("Select Mobile Msisdn Category");
//		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnCategorySpinner")).click();
//		Thread.sleep(500);
//		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Mobile']")).click();
//		Thread.sleep(500);
//		TestUtils.assertSearchText("XPATH", "//android.widget.CheckedTextView[@text='Mobile']", "Mobile");
//		Thread.sleep(500);
//
//		// Proceed after supplying valid msisdn and Sim serial
//		TestUtils.testTitle("Proceed after supplying valid msisdn: (" + valid_msisdn + ") and valid Sim serial: (" + valid_simSerial + ")");
//		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).clear();
//		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).sendKeys(valid_msisdn);
//		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/simSerialField")).clear();
//		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/simSerialField")).sendKeys(valid_simSerial);
//
//		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/addMsisdnSimSerialButton")).click();
//		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/message")));
//		TestUtils.assertSearchText("ID", "android:id/message", "Msisdn is valid");
//		getDriver().findElement(By.id("android:id/button1")).click();
//
//		TestUtils.scrollDown();
//
//		Asserts.assertAddedNumbers();
//
//
//		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/nextButton")).click();
//
//		//NIN Verification
//		TestBase.verifyNINTest(nin, ninVerificationMode);
//
//		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Personal Details']")));
//		Thread.sleep(500);
//		Form.NigerianCompanyForm(dataEnv);
//
//		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Total Subscribers']")));
//		String totalSubAfterCapture = getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/kpi_report_value")).getText();
//		int totalSubValAf = TestUtils.convertToInt(totalSubAfterCapture);
//
//		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Total Sync Confirmed']")));
//		String totalSynConfAfterCapture = getDriver().findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/androidx.drawerlayout.widget.DrawerLayout/android.widget.RelativeLayout/android.widget.FrameLayout[1]/androidx.viewpager.widget.ViewPager/android.widget.RelativeLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.TextView")).getText();
//		int totalSynConfValAf = TestUtils.convertToInt(totalSynConfAfterCapture);
//
//		testInfo.get().info("Total Subscribers Before Capture: "+totalSubBeforeCapture);
//		testInfo.get().info("Total Sync Confirmed Before Capture: "+totalSynConfBeforeCapture);
//
//		if(totalSubValAf == (totalSubVal+1)){
//			testInfo.get().info("Total Subscribers After Capture: "+totalSubValAf);
//		}else {
//			testInfo.get().error("Total Subscribers After Capture: "+totalSubValAf);
//		}
//
//		if(totalSynConfValAf == (totalSynConfVal+1)){
//			testInfo.get().info("Total Sync Confirmed After Capture: "+totalSynConfValAf);
//		}else {
//			testInfo.get().error("Total Sync Confirmed After Capture: "+totalSynConfValAf);
//		}
//	}

	@Parameters({ "systemPort", "deviceNo", "server","deviceName", "testConfig", "dataEnv" })
	@Test
	public void captureSettingsTest(String systemPort, int deviceNo, String server, String deviceName, String testConfig, String dataEnv) throws Exception {


		closeApp();
		Thread.sleep(10);
		startApp(systemPort, deviceNo, server, deviceName, testConfig, true);

	}

}
