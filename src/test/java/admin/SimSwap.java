package admin;

import db.ConnectDB;
import io.appium.java_client.android.AndroidKeyCode;
import utils.TestBase;

import java.io.FileReader;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

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

import utils.TestUtils;

public class SimSwap extends TestBase {

	@Parameters({ "dataEnv"})
	@Test
	public void noneSIMSwapPrivilegeTest(String dataEnv) throws Exception {
		WebDriverWait wait = new WebDriverWait(getDriver(), 30);
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resource/" + dataEnv + "/data.conf.json"));
		JSONObject envs = (JSONObject) config.get("SIMSwap");

		String valid_username = (String) envs.get("valid_username");
		String valid_password = (String) envs.get("valid_password");

		TestBase.Login1( valid_username, valid_password);
		Thread.sleep(500);
		TestUtils.testTitle("To confirm that a user without SIM Swap privilege can't access the module");
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/button_start_capture")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity." + Id + ":id/reg_type_placeholder")));
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity." + Id + ":id/reg_type_placeholder", "Registration Type");
		Thread.sleep(500);

		// Select LGA of Registration
		/*TestUtils.testTitle("Select LGA of Registration: " + lga);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/lga_of_reg")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/alertTitle", "LGA of Registration*");
		//getDriver().findElement(By.xpath("//android.widget.TextView[@text='" + lga + "']")).click();
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='[Select LGA]*']")).click();
		Thread.sleep(500);*/

		// Select SIM Swap
		TestUtils.testTitle("Select SIM Swap");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity." + Id + ":id/typeofreg")));
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/typeofreg")).click();
		Thread.sleep(500);
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity." + Id + ":id/alertTitle", "Select Registration Type");
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='SIM Swap']")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity." + Id + ":id/alertTitle")));
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity." + Id + ":id/alertTitle", "No Privilege");
		TestUtils.assertSearchText("ID", "android:id/message", "You are not allowed to access SIM Swap because you do not have the SIM Swap privilege");
		Thread.sleep(500);
		getDriver().findElement(By.id("android:id/button1")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/message", "Capture session is currently active, Exit will cause loss of currently captured data! do you wish to cancel current capture session?");
		getDriver().findElement(By.id("android:id/button2")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Home']")));
		Thread.sleep(500);

		//Log out
		getDriver().findElement(By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/design_menu_item_text")));
		TestUtils.scrollDown();
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Logout']")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/message")));
		TestUtils.assertSearchText("ID", "android:id/message", "   Log out?");
		getDriver().findElement(By.id("android:id/button3")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/otp_login")));
	}

	@Test
	public static void navigateToCaptureMenuTest() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), 30);
		// Navigate to Registration Type
		TestUtils.testTitle("Navigate to Registration Type");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Home']")));
		getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/button_start_capture")).click();
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/reg_type_placeholder")));
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity.glo:id/reg_type_placeholder",
				"Registration Type");
	}

	@Parameters({ "dataEnv"})
	@Test
	public void navigateToSimSwapViewTest(String dataEnv) throws Exception {

		WebDriverWait wait = new WebDriverWait(getDriver(), 30);

		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resource/" + dataEnv + "/data.conf.json"));
		JSONObject envs = (JSONObject) config.get("SIMSwap");

		String lga = (String) envs.get("lga");
		String invalid_otp = (String) envs.get("invalid_otp");
		String expired_otp = (String) envs.get("expired_otp");
		String otp_phone_number = (String) envs.get("otp_phone_number");


		// Select LGA of Registration
//		String lgaa = "Select LGA of Registration: " + lga;
//		Markup m = MarkupHelper.createLabel(lgaa, ExtentColor.BLUE);
//		testInfo.get().info(m);
//		getDriver().findElement(By.id("com.sf.biocapture.activity:id/lga_of_reg")).click();
//		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/alertTitle")));
//		TestUtils.assertSearchText("ID", "android:id/alertTitle", "LGA of Registration*");
//		getDriver().findElement(By.xpath("//android.widget.TextView[@text='" + lga + "']")).click();
//		Thread.sleep(500);

		// Select Sim Swap
		TestUtils.testTitle("Select SIM Swap");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/typeofreg")));
		getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/typeofreg")).click();
		Thread.sleep(500);
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity.glo:id/alertTitle", "Select Registration Type");
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='SIM Swap']")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/next_button")).click();

		//Validate OTP if Setting is turend ON for SS
		try{
			TestUtils.testTitle("Validate OTP if it's turend ON in settings");
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/dialog_title")));
			TestUtils.assertSearchText("ID", "com.sf.biocapture.activity.glo:id/dialog_title", "OTP verification");
			TestUtils.assertSearchText("ID", "com.sf.biocapture.activity.glo:id/dialog_message", "Enter One Time Password sent to : 080*****430");

			//proceed with invalid OTP
			TestUtils.testTitle("Proceed with invalid OTP: "+invalid_otp);
			getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/user_input_dialog")).clear();
			getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/user_input_dialog")).sendKeys(invalid_otp);

			//Submit
			getDriver().findElement(By.id("android:id/button1")).click();

			//Assert Error Message
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/error_message")));
			TestUtils.assertSearchText("ID", "com.sf.biocapture.activity.glo:id/error_message", "There is no record with the otp, msisdn combination.");

//			//proceed with expired OTP
//			TestUtils.testTitle("Proceed with invalid OTP: "+expired_otp);
//			getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/user_input_dialog")).clear();
//			getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/user_input_dialog")).sendKeys(expired_otp);
//
//			//Submit
//			getDriver().findElement(By.id("android:id/button1")).click();

			//Assert Error Message
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/error_message")));
			TestUtils.assertSearchText("ID", "com.sf.biocapture.activity.glo:id/error_message", "There is no record with the otp, msisdn combination.");

			//proceed with valid OTP
			String valid_otp = ConnectDB.getOTP(otp_phone_number);
			TestUtils.testTitle("Proceed with valid OTP: "+valid_otp);
			getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/user_input_dialog")).clear();
			getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/user_input_dialog")).sendKeys(valid_otp);

			//Submit
			getDriver().findElement(By.id("android:id/button1")).click();

		}catch (Exception e){
			TestUtils.testTitle("OTP Validation is turned OFF for SIM Swap");
		}

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/title_header")));
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity.glo:id/title_header", "SIM Swap");

	}

	@Parameters({ "dataEnv"})
	@Test
	public void simSwapViewValidationTest(String dataEnv) throws Exception {

		WebDriverWait wait = new WebDriverWait(getDriver(), 30);
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resource/" + dataEnv + "/data.conf.json"));
		JSONObject envs = (JSONObject) config.get("SIMSwap");

		String invalid_Msisdn_Format = (String) envs.get("invalid_Msisdn_Format");
		String msisdn_greater_than_11_digits = (String) envs.get("msisdn_greater_than_11_digits");
		String msisdn_less_than_11_digits = (String) envs.get("msisdn_less_than_11_digits");
		String unrecognizedMsisdn = (String) envs.get("unrecognizedMsisdn");
		String valid_Msisdn = (String) envs.get("valid_Msisdn");
        String invalid_Msisdn = (String) envs.get("invalid_Msisdn");
        String invalid_new_Msisdn  = (String) envs.get("invalid_new_Msisdn");
		String new_msisdn = (String) envs.get("new_msisdn");
		String valid_sim_serial = (String) envs.get("valid_sim_serial");
		String approver_username = (String) envs.get("approver_username");
        String approver_password = (String) envs.get("approver_password");

		TestUtils.testTitle("Validate SIM Swap View Details");

		//Confirm that SIM Swap type field exists
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity.glo:id/sim_swap_typeTXT","SIM Swap Type*");
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity.glo:id/radio_item_self","Self");
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity.glo:id/radio_item_proxl","Proxy");

		//Click Validate without selecting SIM Swap type
		TestUtils.testTitle("Verify that User Can't proceed without selecting SIM Swap type");
		getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/btnValidate")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/message","Please Select Sim Swap Type");
		getDriver().findElement(By.id("android:id/button1")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/title_header")));

		//Select SIM Swap type
		getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/radio_item_self")).click();

		//Select Subscriber Type
		TestUtils.testTitle("Verify that User Can't proceed without selecting Subscriber type");
		getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/btnValidate")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/message","Please Select a Subscriber Type to proceed");
		getDriver().findElement(By.id("android:id/button1")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/subscriberType")));

		getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/subscriberType")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.CheckedTextView[@text='Prepaid']")));
		TestUtils.assertSearchText("XPATH", "//android.widget.CheckedTextView[@text='Prepaid']","Prepaid");
		TestUtils.assertSearchText("XPATH", "//android.widget.CheckedTextView[@text='Postpaid']","Postpaid");
		TestUtils.assertSearchText("XPATH", "//android.widget.CheckedTextView[@text='Data']","Data");
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Prepaid']")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Prepaid']")));

		//Select Swap Category
		TestUtils.testTitle("Verify that User Can't proceed without selecting Swap Category");
		getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/btnValidate")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/message","Please select a Swap Category to proceed");
		getDriver().findElement(By.id("android:id/button1")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/swapCategory")));

		getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/swapCategory")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='SIM UPGRADE']")));
		TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='SIM UPGRADE']","SIM UPGRADE");
		TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='SIM SWAP']","SIM SWAP");
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='SIM SWAP']")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='SIM SWAP']")));

		//Confirm Existing MSISDN field
		TestUtils.testTitle("Confirm that Existing MSISDN field exists");
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity.glo:id/existingMsisdnField","Enter Existing MSISDN*");
		TestUtils.testTitle("Verify that User Can't proceed without Supplying Existing MSISDN");
		getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/btnValidate")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/message","Please Enter Existing Msisdn");
		getDriver().findElement(By.id("android:id/button1")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/existingMsisdnField")));

		//Enter NON digits
//		TestUtils.testTitle("Enter Invalid Existing MSISDN formart: "+invalid_Msisdn_Format);
//		getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/existingMsisdnField")).clear();
//		getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/existingMsisdnField")).sendKeys(invalid_Msisdn_Format);
//		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity.glo:id/existingMsisdnField","Enter Existing MSISDN*");

		//Enter Existing MSISDN greater than 11 digits
		TestUtils.testTitle("Enter Existing MSISDN greater than 11 digits: "+msisdn_greater_than_11_digits);
		getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/existingMsisdnField")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/existingMsisdnField")).sendKeys(msisdn_greater_than_11_digits);
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity.glo:id/existingMsisdnField",msisdn_greater_than_11_digits.substring(0, 11));

		//Enter Existing MSISDN less than 11 digits
		TestUtils.testTitle("Enter Existing MSISDN less than 11 digits: "+msisdn_less_than_11_digits);
		getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/existingMsisdnField")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/existingMsisdnField")).sendKeys(msisdn_less_than_11_digits);
		getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/btnValidate")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/message","Entered Existing MSISDN is invalid. Entered value should not be less than 6 or more than 11 digits.");
		getDriver().findElement(By.id("android:id/button1")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/existingMsisdnField")));

		//Enter unrecognized Existing MSISDN
		TestUtils.testTitle("Enter unrecognized Existing MSISDN: "+unrecognizedMsisdn);
		getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/existingMsisdnField")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/existingMsisdnField")).sendKeys(unrecognizedMsisdn);
		getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/btnValidate")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/message","Existing MSISDN: The MSISDN has an unrecognized National Destination Code. Please ensure the MSISDN starts with any of the following NDCs: 0701,0708,0802,0808,0812,0901,0902,0904,0907,0809,0817,0818,0908,0909,0705,0805,0807,0811,0815,0905,0915,0703,0706,0803,0806,0810,0813,0814,0816,0903,0906 and must be 11 digits");
		getDriver().findElement(By.id("android:id/button1")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/existingMsisdnField")));

		//Enter Valid existing MSISDN
		getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/existingMsisdnField")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/existingMsisdnField")).sendKeys(valid_Msisdn);


		//Confirm New MSISDN field
		TestUtils.testTitle("Confirm that New MSISDN field exists");
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity.glo:id/newMsisdnField","Enter New MSISDN");
		TestUtils.testTitle("Verify that User Can't proceed without Supplying New MSISDN");
		getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/btnValidate")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/message","Required Input Field: New MSISDN");
		getDriver().findElement(By.id("android:id/button1")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/newMsisdnField")));

		//Enter NON digits
//		TestUtils.testTitle("Enter Invalid New MSISDN formart: "+invalid_Msisdn_Format);
//		getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/newMsisdnField")).clear();
//		getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/newMsisdnField")).sendKeys(invalid_Msisdn_Format);
//		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity.glo:id/newMsisdnField","Enter New MSISDN");

		//Enter New MSISDN greater than 11 digits
		TestUtils.testTitle("Enter New MSISDN greater than 11 digits: "+msisdn_greater_than_11_digits);
		getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/newMsisdnField")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/newMsisdnField")).sendKeys(msisdn_greater_than_11_digits);
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity.glo:id/newMsisdnField",msisdn_greater_than_11_digits.substring(0, 11));

		//Enter unrecognized MSISDN
//		TestUtils.testTitle("Enter unrecognized MSISDN: "+unrecognizedMsisdn);
//		getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/newMsisdnField")).clear();
//		getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/newMsisdnField")).sendKeys(unrecognizedMsisdn);
//		getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/btnValidate")).click();
//		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/alertTitle")));
//		TestUtils.assertSearchText("ID", "android:id/message","Existing MSISDN: The MSISDN has an unrecognized National Destination Code. Please ensure the MSISDN starts with any of the following NDCs: 0701,0708,0802,0808,0812,0901,0902,0904,0907,0809,0817,0818,0908,0909,0705,0805,0807,0811,0815,0905,0915,0703,0706,0803,0806,0810,0813,0814,0816,0903,0906 and must be 11 digits");
//		getDriver().findElement(By.id("android:id/button1")).click();
//		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/newMsisdnField")));

		//Enter Valid New MSISDN
		getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/newMsisdnField")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/newMsisdnField")).sendKeys(new_msisdn);


		//Confirm Sim Serial field
		TestUtils.testTitle("Confirm that Sim Serial field exists");
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity.glo:id/simSerialField","Enter New Sim Serial*");
		TestUtils.testTitle("Verify that User Can't proceed without Supplying New Sim Serial");
		getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/simSerialField")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/btnValidate")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/message","Please Enter New Sim Serial");
		getDriver().findElement(By.id("android:id/button1")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/simSerialField")));

		//Confirm user cannot proceed with invalid SIM Serial
		TestUtils.testTitle("Confirm user cannot proceed with invalid Sim Serial: "+msisdn_greater_than_11_digits);
		getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/simSerialField")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/simSerialField")).sendKeys(msisdn_greater_than_11_digits);
		getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/btnValidate")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/message","New Sim Serial: Sim Serial format is invalid");
		getDriver().findElement(By.id("android:id/button1")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/simSerialField")));

		//RAW SIM checkbox
		TestUtils.testTitle("RAW Sim checkbox Test");
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity.glo:id/rawSimCheckBox","Raw Sim");
		testInfo.get().info("New MSISDN field before checking Raw Sim Checkbox: "+getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/newMsisdnField")).getText());
		getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/rawSimCheckBox")).click();
		testInfo.get().info("New MSISDN field after checking Raw Sim Checkbox: "+getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/newMsisdnField")).getText());
		getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/rawSimCheckBox")).click();

		//Insert Back Valid New MSISDN
		getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/newMsisdnField")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/newMsisdnField")).sendKeys(new_msisdn);

		//Enter Valid New Sim Serail
		getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/simSerialField")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/simSerialField")).sendKeys(valid_sim_serial);


		//Confirm that user can't proceed with invalid Old MSISDN
        TestUtils.testTitle("Confirm that user can't proceed with invalid Old MSISDN ("+invalid_Msisdn+")");
        //Enter invalid existing MSISDN
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/existingMsisdnField")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/existingMsisdnField")).sendKeys(invalid_Msisdn);
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/btnValidate")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/alertTitle")));
        TestUtils.assertSearchText("ID", "android:id/message", "Old Msisdn : MSISDN is not registered and cannot be used for this use case");
        getDriver().findElement(By.id("android:id/button1")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/existingMsisdnField")));

        //Enter Valid existing MSISDN
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/existingMsisdnField")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/existingMsisdnField")).sendKeys(valid_Msisdn);

        //Confirm that user can't proceed with invalid Old MSISDN
        TestUtils.testTitle("Confirm that user can't proceed with invalid New MSISDN ("+invalid_new_Msisdn+")");
        //Enter invalid existing MSISDN
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/newMsisdnField")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/newMsisdnField")).sendKeys(invalid_new_Msisdn);
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/btnValidate")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/alertTitle")));
        TestUtils.assertSearchText("ID", "android:id/message", "New Msisdn : Target MSISDN is not available for SIM Swap. Kindly use another MSISDN");
        getDriver().findElement(By.id("android:id/button1")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/newMsisdnField")));

        //Insert Back Valid New MSISDN
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/newMsisdnField")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/newMsisdnField")).sendKeys(new_msisdn);

		getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/btnValidate")).click();

		//Check for Approve Sim Swap Validation
        try{
            TestUtils.testTitle("Approve Sim Swap Validation");
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/alertTitle")));
            TestUtils.assertSearchText("ID", "com.sf.biocapture.activity.glo:id/alertTitle", "Approve Sim Swap Validation");
            getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/approve_username")).clear();
            getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/approve_username")).sendKeys(approver_username);
            getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/approve_password")).clear();
            getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/approve_password")).sendKeys(approver_password);
            getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/approve")).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/dialog_title")));
			TestUtils.assertSearchText("ID", "com.sf.biocapture.activity.glo:id/alertTitle", "Approval Status");
			TestUtils.assertSearchText("ID", "android:id/message", "Successfully approved!");
			getDriver().findElement(By.id("android:id/button1")).click();
        }catch(Exception e){

        }
		//Confirm There is no otp verification when SIM Swap is selected
        TestUtils.testTitle("Confirm There is no otp verification when SIM Swap is selected");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/alertTitle")));
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity.glo:id/alertTitle", "NIN Verification");

        //Confirm OTP Verification when Sim Upgrade is selected
        TestUtils.testTitle("Confirm OTP Verification when Sim Upgrade is selected");
        getDriver().pressKeyCode(AndroidKeyCode.BACK);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/swapCategory")));
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/swapCategory")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='SIM UPGRADE']")));
        getDriver().findElement(By.xpath("//android.widget.TextView[@text='SIM UPGRADE']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='SIM UPGRADE']")));

        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/btnValidate")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/dialog_title")));
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity.glo:id/dialog_title", "OTP verification");

        String otp = ConnectDB.getOTPWithoutPhoneNumber();

        TestUtils.testTitle("Submit without entering OTP");
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/user_input_dialog")).clear();
        getDriver().findElement(By.id("android:id/button1")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/error_message")));
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity.glo:id/error_message", "Required Input Field: OTP");

        TestUtils.testTitle("Invalid OTP Test: u57267");
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/user_input_dialog")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/user_input_dialog")).sendKeys("u57267");
        getDriver().findElement(By.id("android:id/button1")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/alertTitle")));
        TestUtils.assertSearchText("ID", "android:id/message", "There is no record with the otp, msisdn combination.");
        getDriver().findElement(By.id("android:id/button1")).click();
        //Check for Approve Sim Swap Validation
        try{
            TestUtils.testTitle("Approve Sim Swap Validation");
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/alertTitle")));
            TestUtils.assertSearchText("ID", "com.sf.biocapture.activity.glo:id/alertTitle", "Approve Sim Swap Validation");
            getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/approve_username")).clear();
            getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/approve_username")).sendKeys(approver_username);
            getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/approve_password")).clear();
            getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/approve_password")).sendKeys(approver_password);
            getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/approve")).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/dialog_title")));
			TestUtils.assertSearchText("ID", "com.sf.biocapture.activity.glo:id/alertTitle", "Approval Status");
			TestUtils.assertSearchText("ID", "android:id/message", "Successfully approved!");
			getDriver().findElement(By.id("android:id/button1")).click();
        }catch(Exception e){

        }

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/dialog_title")));

        TestUtils.testTitle("Valid OTP Test: "+otp);
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/user_input_dialog")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/user_input_dialog")).sendKeys(otp);
        getDriver().findElement(By.id("android:id/button1")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/alertTitle")));
        TestUtils.assertSearchText("ID", "android:id/message", "The specified otp and msisdn combinations are valid.");
        getDriver().findElement(By.id("android:id/button1")).click();

		//Verify NIN
		TestBase.verifyNINTest("11111111111", "Search By NIN");
	}

    @Parameters({ "dataEnv"})
    @Test
    public void assertFieldsDisplayed(String dataEnv) throws Exception {

	    TestUtils.testTitle("Assert ");



    }
	
	@Parameters({ "dataEnv"})
    @Test
    public void simSwapTest(String dataEnv) throws Exception {

        WebDriverWait wait = new WebDriverWait(getDriver(), 30);
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resource/" + dataEnv + "/data.conf.json"));
		JSONObject envs = (JSONObject) config.get("SIMSwap");

		String lga = (String) envs.get("lga");
		String valid_Msisdn = (String) envs.get("valid_Msisdn");
		String invalid_Msisdn = (String) envs.get("invalid_Msisdn");
		
		// Submit without supplying Msisdn and leaving the plan at default
		TestUtils.testTitle("Submit without supplying Msisdn and leaving the plan at default");
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/sim_swap_edit_text")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/sim_swap_edit_text")).sendKeys("");
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/btn_sim_swap_search_msisdn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/alertTitle")));
		Thread.sleep(500);
		TestUtils.assertSearchText("ID", "android:id/message", "Please Select a plan Type");
		getDriver().findElement(By.id("android:id/button1")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/sim_swap_edit_text")));
		Thread.sleep(500);
		
		// Submit without supplying Msisdn and selecting one of the plan types
		String msis = "Submit without supplying Msisdn and selecting one of the plan types";
		Markup h = MarkupHelper.createLabel(msis, ExtentColor.BLUE);
		testInfo.get().info(h);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/sim_swap_edit_text")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/sim_swap_edit_text")).sendKeys("");
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/plan_type")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.CheckedTextView[@text='PREPAID']")));
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='PREPAID']")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/btn_sim_swap_search_msisdn")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/message", "Required Input Field: Phone Number");
		getDriver().findElement(By.id("android:id/button1")).click();
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/sim_swap_edit_text")));
		Thread.sleep(500);
		
		// Submit when Msisdn field is completed and the plan type is left on default
		String msis2 = "Submit when Msisdn field is completed: " + valid_Msisdn + " and the plan type is left on default";
		Markup f = MarkupHelper.createLabel(msis2, ExtentColor.BLUE);
		testInfo.get().info(f);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/sim_swap_edit_text")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/sim_swap_edit_text")).sendKeys(valid_Msisdn);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/plan_type")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.CheckedTextView[@text='[Select Subscriber Type]']")));
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='[Select Subscriber Type]']")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/btn_sim_swap_search_msisdn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/alertTitle")));
		Thread.sleep(500);
		TestUtils.assertSearchText("ID", "android:id/message", "Please Select a plan Type");
		getDriver().findElement(By.id("android:id/button1")).click();
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/sim_swap_edit_text")));
		Thread.sleep(500);
	
		// Submit when an invalid Msisdn is entered and selecting one of the plan types
		String invalidMsisdn = "Submit when an invalid Msisdn is entered: " + invalid_Msisdn + " and selecting one of the plan types";
		Markup g = MarkupHelper.createLabel(invalidMsisdn, ExtentColor.BLUE);
		testInfo.get().info(g);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/sim_swap_edit_text")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/sim_swap_edit_text")).sendKeys(invalid_Msisdn);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/plan_type")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.CheckedTextView[@text='PREPAID']")));
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='PREPAID']")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/btn_sim_swap_search_msisdn")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/message", "Could not retrieve bio data for the specified msisdn.");
		getDriver().findElement(By.id("android:id/button1")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/sim_swap_edit_text")));
		Thread.sleep(500);
		
		// Submit when a valid Msisdn is entered and selecting one of the plan types
		String validMsisdn = "Submit when a valid Msisdn is entered: " + valid_Msisdn + " and selecting one of the plan types";
		Markup v = MarkupHelper.createLabel(validMsisdn, ExtentColor.BLUE);
		testInfo.get().info(v);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/sim_swap_edit_text")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/sim_swap_edit_text")).sendKeys(valid_Msisdn);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/plan_type")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.CheckedTextView[@text='PREPAID']")));
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='PREPAID']")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/btn_sim_swap_search_msisdn")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/btn_verify_fingerprint")));
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/btn_verify_fingerprint")).click();
		Thread.sleep(500);
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/alertTitle")));
			Thread.sleep(500);
			TestUtils.assertSearchText("ID", "com.sf.biocapture.activity:id/alertTitle", "Scanner not found");
			getDriver().findElement(By.id("android:id/button1")).click();
			Thread.sleep(500);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/fingerType_text")));
			Thread.sleep(500);
			TestUtils.assertSearchText("ID", "com.sf.biocapture.activity:id/fingerType_text", "VERIFICATION");
			TestUtils.assertSearchText("ID", "com.sf.biocapture.activity:id/btnStop", "Capture");
			Thread.sleep(500);
		} catch (Exception e) {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/fingerType_text")));
			Thread.sleep(500);
			TestUtils.assertSearchText("ID", "com.sf.biocapture.activity:id/fingerType_text", "VERIFICATION");
			TestUtils.assertSearchText("ID", "com.sf.biocapture.activity:id/btnStop", "Capture");
			Thread.sleep(500);
		}
    
	}
      
}
