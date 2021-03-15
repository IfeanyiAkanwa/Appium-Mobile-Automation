package admin;

import db.ConnectDB;
import io.appium.java_client.android.AndroidKeyCode;
import utils.Asserts;
import utils.TestBase;

import java.io.File;
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

    @Parameters({"dataEnv"})
    @Test
    public void noneSIMSwapPrivilegeTest(String dataEnv) throws Exception {
        WebDriverWait wait = new WebDriverWait(getDriver(), 30);
        JSONParser parser = new JSONParser();
        JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resource/" + dataEnv + "/data.conf.json"));
        JSONObject envs = (JSONObject) config.get("SIMSwap");

        String valid_username = (String) envs.get("valid_username");
        String valid_password = (String) envs.get("valid_password");
        String lga = (String) envs.get("lga");

        TestBase.Login1(valid_username, valid_password);
        Thread.sleep(500);
        TestUtils.testTitle("To confirm that a user without SIM Swap privilege can't access the module");
        getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/button_start_capture")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity." + Id + ":id/reg_type_placeholder")));
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity." + Id + ":id/reg_type_placeholder", "Registration Type");
        Thread.sleep(500);

        try {
            // Select LGA of Registration
            TestUtils.testTitle("Select LGA of Registration: " + lga);
            getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/lga_of_reg")).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/alertTitle")));
            TestUtils.assertSearchText("ID", "android:id/alertTitle", "LGA of Registration*");
            //getDriver().findElement(By.xpath("//android.widget.TextView[@text='" + lga + "']")).click();
            getDriver().findElement(By.xpath("//android.widget.TextView[@text='[Select LGA]*']")).click();
            Thread.sleep(500);
        } catch (Exception e) {
            testInfo.get().info("LGA already selected");
        }


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

    @Parameters({"dataEnv"})
    @Test
    public void navigateToSimSwapViewTest(String dataEnv) throws Exception {

        WebDriverWait wait = new WebDriverWait(getDriver(), 10);

        JSONParser parser = new JSONParser();
        JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resource/" + dataEnv + "/data.conf.json"));
        JSONObject envs = (JSONObject) config.get("SIMSwap");

        String lga = (String) envs.get("lga");
        String invalid_otp = (String) envs.get("invalid_otp");
        String expired_otp = (String) envs.get("expired_otp");
        String otp_phone_number = (String) envs.get("otp_phone_number");


        // Select LGA of Registration
        try {
            TestUtils.testTitle("Select LGA of Registration: " + lga);
            getDriver().findElement(By.id("com.sf.biocapture.activity:id/lga_of_reg")).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/alertTitle")));
            TestUtils.assertSearchText("ID", "android:id/alertTitle", "LGA of Registration*");
            getDriver().findElement(By.xpath("//android.widget.TextView[@text='" + lga + "']")).click();
            Thread.sleep(500);
        } catch (Exception e) {
            testInfo.get().info("LGA already selected");
        }

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
        try {
            TestUtils.testTitle("Validate OTP if it's turend ON in settings");
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/dialog_title")));
            TestUtils.assertSearchText("ID", "com.sf.biocapture.activity.glo:id/dialog_title", "OTP verification");
            TestUtils.assertSearchText("ID", "com.sf.biocapture.activity.glo:id/dialog_message", "Enter One Time Password sent to : 080*****430");

            //proceed with invalid OTP
            TestUtils.testTitle("Proceed with invalid OTP: " + invalid_otp);
            getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/user_input_dialog")).clear();
            getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/user_input_dialog")).sendKeys(invalid_otp);

            //Submit
            getDriver().findElement(By.id("android:id/button1")).click();

            //Assert Error Message
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/error_message")));
            TestUtils.assertSearchText("ID", "com.sf.biocapture.activity.glo:id/error_message", "There is no record with the otp, msisdn combination.");

//			//proceed with expired OTP (Commented out because OTP limit is reached while checking out all conditions)
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
            TestUtils.testTitle("Proceed with valid OTP: " + valid_otp);
            getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/user_input_dialog")).clear();
            getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/user_input_dialog")).sendKeys(valid_otp);

            //Submit
            getDriver().findElement(By.id("android:id/button1")).click();

        } catch (Exception e) {
            TestUtils.testTitle("OTP Validation is turned OFF for SIM Swap");
        }

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/title_header")));
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity.glo:id/title_header", "SIM Swap");

    }

    @Parameters({"dataEnv"})
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
        String invalid_new_Msisdn = (String) envs.get("invalid_new_Msisdn");
        String new_msisdn = (String) envs.get("new_msisdn");
        String valid_sim_serial = (String) envs.get("valid_sim_serial");
        String approver_username = (String) envs.get("approver_username");
        String approver_password = (String) envs.get("approver_password");
        String blocked_msisdn = (String) envs.get("blocked_msisdn");
        String otp_phone_number = (String) envs.get("otp_phone_number");
        String nin = (String) envs.get("nin");
        String unavailable_msisdn = (String) envs.get("unavailable_msisdn");

        TestUtils.testTitle("Validate SIM Swap View Details");

        //Confirm that SIM Swap type field exists
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity.glo:id/sim_swap_typeTXT", "SIM Swap Type*");
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity.glo:id/radio_item_self", "Self");
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity.glo:id/radio_item_proxl", "Proxy");

        //Click Validate without selecting SIM Swap type
        TestUtils.testTitle("Verify that User Can't proceed without selecting SIM Swap type");
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/btnValidate")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/alertTitle")));
        TestUtils.assertSearchText("ID", "android:id/message", "Please Select Sim Swap Type");
        getDriver().findElement(By.id("android:id/button1")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/title_header")));

        //Select SIM Swap type
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/radio_item_self")).click();

        //Select Subscriber Type
        TestUtils.testTitle("Verify that User Can't proceed without selecting Subscriber type");
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/btnValidate")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/alertTitle")));
        TestUtils.assertSearchText("ID", "android:id/message", "Please Select a Subscriber Type to proceed");
        getDriver().findElement(By.id("android:id/button1")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/subscriberType")));

        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/subscriberType")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.CheckedTextView[@text='Prepaid']")));
        TestUtils.assertSearchText("XPATH", "//android.widget.CheckedTextView[@text='Prepaid']", "Prepaid");
        TestUtils.assertSearchText("XPATH", "//android.widget.CheckedTextView[@text='Postpaid']", "Postpaid");
        TestUtils.assertSearchText("XPATH", "//android.widget.CheckedTextView[@text='Data']", "Data");
        getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Prepaid']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Prepaid']")));

        //Select Swap Category
        TestUtils.testTitle("Verify that User Can't proceed without selecting Swap Category");
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/btnValidate")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/alertTitle")));
        TestUtils.assertSearchText("ID", "android:id/message", "Please select a Swap Category to proceed");
        getDriver().findElement(By.id("android:id/button1")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/swapCategory")));

        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/swapCategory")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='SIM UPGRADE']")));
        TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='SIM UPGRADE']", "SIM UPGRADE");
        TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='SIM SWAP']", "SIM SWAP");
        getDriver().findElement(By.xpath("//android.widget.TextView[@text='SIM SWAP']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='SIM SWAP']")));

        //Confirm Existing MSISDN field
        TestUtils.testTitle("Confirm that Existing MSISDN field exists");
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity.glo:id/existingMsisdnField", "Enter Existing MSISDN*");
        TestUtils.testTitle("Verify that User Can't proceed without Supplying Existing MSISDN");
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/btnValidate")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/alertTitle")));
        TestUtils.assertSearchText("ID", "android:id/message", "Please Enter Existing Msisdn");
        getDriver().findElement(By.id("android:id/button1")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/existingMsisdnField")));

        //Enter NON digits
//		TestUtils.testTitle("Enter Invalid Existing MSISDN formart: "+invalid_Msisdn_Format);
//		getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/existingMsisdnField")).clear();
//		getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/existingMsisdnField")).sendKeys(invalid_Msisdn_Format);
//		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity.glo:id/existingMsisdnField","Enter Existing MSISDN*");

        //Enter Existing MSISDN greater than 11 digits
        TestUtils.testTitle("Enter Existing MSISDN greater than 11 digits: " + msisdn_greater_than_11_digits);
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/existingMsisdnField")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/existingMsisdnField")).sendKeys(msisdn_greater_than_11_digits);
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity.glo:id/existingMsisdnField", msisdn_greater_than_11_digits.substring(0, 11));

        //Enter Existing MSISDN less than 11 digits
        TestUtils.testTitle("Enter Existing MSISDN less than 11 digits: " + msisdn_less_than_11_digits);
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/existingMsisdnField")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/existingMsisdnField")).sendKeys(msisdn_less_than_11_digits);
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/btnValidate")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/alertTitle")));
        TestUtils.assertSearchText("ID", "android:id/message", "Entered Existing MSISDN is invalid. Entered value should not be less than 6 or more than 11 digits.");
        getDriver().findElement(By.id("android:id/button1")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/existingMsisdnField")));

        //Enter unrecognized Existing MSISDN
        TestUtils.testTitle("Enter unrecognized Existing MSISDN: " + unrecognizedMsisdn);
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/existingMsisdnField")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/existingMsisdnField")).sendKeys(unrecognizedMsisdn);
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/btnValidate")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/alertTitle")));
        TestUtils.assertSearchText("ID", "android:id/message", "Existing MSISDN: The MSISDN has an unrecognized National Destination Code. Please ensure the MSISDN starts with any of the following NDCs: 0701,0708,0802,0808,0812,0901,0902,0904,0907,0809,0817,0818,0908,0909,0705,0805,0807,0811,0815,0905,0915,0703,0706,0803,0806,0810,0813,0814,0816,0903,0906 and must be 11 digits");
        getDriver().findElement(By.id("android:id/button1")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/existingMsisdnField")));

        //Enter Valid existing MSISDN
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/existingMsisdnField")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/existingMsisdnField")).sendKeys(valid_Msisdn);


        //Confirm New MSISDN field
        TestUtils.testTitle("Confirm that New MSISDN field exists");
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity.glo:id/newMsisdnField", "Enter New MSISDN");
        TestUtils.testTitle("Verify that User Can't proceed without Supplying New MSISDN");
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/btnValidate")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/alertTitle")));
        TestUtils.assertSearchText("ID", "android:id/message", "Required Input Field: New MSISDN");
        getDriver().findElement(By.id("android:id/button1")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/newMsisdnField")));

        //Enter NON digits
//		TestUtils.testTitle("Enter Invalid New MSISDN formart: "+invalid_Msisdn_Format);
//		getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/newMsisdnField")).clear();
//		getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/newMsisdnField")).sendKeys(invalid_Msisdn_Format);
//		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity.glo:id/newMsisdnField","Enter New MSISDN");

        //Enter New MSISDN greater than 11 digits
        TestUtils.testTitle("Enter New MSISDN greater than 11 digits: " + msisdn_greater_than_11_digits);
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/newMsisdnField")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/newMsisdnField")).sendKeys(msisdn_greater_than_11_digits);
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity.glo:id/newMsisdnField", msisdn_greater_than_11_digits.substring(0, 11));

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
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity.glo:id/simSerialField", "Enter New Sim Serial*");
        TestUtils.testTitle("Verify that User Can't proceed without Supplying New Sim Serial");
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/simSerialField")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/btnValidate")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/alertTitle")));
        TestUtils.assertSearchText("ID", "android:id/message", "Please Enter New Sim Serial");
        getDriver().findElement(By.id("android:id/button1")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/simSerialField")));

        //Confirm user cannot proceed with invalid SIM Serial
        TestUtils.testTitle("Confirm user cannot proceed with invalid Sim Serial: " + msisdn_greater_than_11_digits);
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/simSerialField")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/simSerialField")).sendKeys(msisdn_greater_than_11_digits);
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/btnValidate")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/alertTitle")));
        TestUtils.assertSearchText("ID", "android:id/message", "New Sim Serial: Sim Serial format is invalid");
        getDriver().findElement(By.id("android:id/button1")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/simSerialField")));

        //RAW SIM checkbox
        TestUtils.testTitle("RAW Sim checkbox Test");
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity.glo:id/rawSimCheckBox", "Raw Sim");
        testInfo.get().info("New MSISDN field before checking Raw Sim Checkbox: " + getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/newMsisdnField")).getText());
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/rawSimCheckBox")).click();
        testInfo.get().info("New MSISDN field after checking Raw Sim Checkbox: " + getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/newMsisdnField")).getText());
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/rawSimCheckBox")).click();

        //Insert Back Valid New MSISDN
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/newMsisdnField")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/newMsisdnField")).sendKeys(new_msisdn);

        //Enter Valid New Sim Serail
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/simSerialField")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/simSerialField")).sendKeys(valid_sim_serial);


        //Confirm that user can't proceed with invalid Old MSISDN
        TestUtils.testTitle("Confirm that user can't proceed with invalid Old MSISDN (" + invalid_Msisdn + ")");
        //Enter invalid existing MSISDN
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/existingMsisdnField")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/existingMsisdnField")).sendKeys(invalid_Msisdn);
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/btnValidate")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/alertTitle")));
        TestUtils.assertSearchText("ID", "android:id/message", "Old Msisdn : MSISDN is not registered and cannot be used for this use case");
        getDriver().findElement(By.id("android:id/button1")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/existingMsisdnField")));

        //Confirm that user can't proceed with Unavailable Old MSISDN
        TestUtils.testTitle("Confirm that user can't proceed with unavailable Old MSISDN (" + unavailable_msisdn + ")");
        //Enter invalid existing MSISDN
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/existingMsisdnField")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/existingMsisdnField")).sendKeys(unavailable_msisdn);
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/btnValidate")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/alertTitle")));
        TestUtils.assertSearchText("ID", "android:id/message", unavailable_msisdn + " is not available for SIM Swap.");
        getDriver().findElement(By.id("android:id/button1")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/existingMsisdnField")));

        //Confirm that user can't proceed with Blocked Old MSISDN
        TestUtils.testTitle("Confirm that user can't proceed with Blocked Old MSISDN (" + blocked_msisdn + ")");
        //Enter invalid existing MSISDN
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/existingMsisdnField")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/existingMsisdnField")).sendKeys(blocked_msisdn);
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/btnValidate")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/alertTitle")));
        TestUtils.assertSearchText("ID", "android:id/message", "You cannot proceed with the SIM Swap. Further Swap request on "+ blocked_msisdn+" has been Blocked. Contact Support");
        getDriver().findElement(By.id("android:id/button1")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/existingMsisdnField")));

        //Enter Valid existing MSISDN
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/existingMsisdnField")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/existingMsisdnField")).sendKeys(valid_Msisdn);

        //Confirm that user can't proceed with invalid New MSISDN
        TestUtils.testTitle("Confirm that user can't proceed with invalid New MSISDN (" + invalid_new_Msisdn + ")");
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
        try {
            TestUtils.testTitle("Approve Sim Swap Validation");
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/alertTitle")));
            getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/approve_username")).clear();
            getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/approve_username")).sendKeys(approver_username);
            getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/approve_password")).clear();
            getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/approve_password")).sendKeys(approver_password);
            getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/approve")).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/alertTitle")));
            TestUtils.assertSearchText("ID", "com.sf.biocapture.activity.glo:id/alertTitle", "Approval Status");
            TestUtils.assertSearchText("ID", "android:id/message", "Successfully approved!");
            getDriver().findElement(By.xpath("//android.widget.Button[@text='OK']")).click();
        } catch (Exception e) {
            testInfo.get().info("Approve Validation view not displayed");
        }
        //Confirm There is no otp verification when SIM Swap is selected
        TestUtils.testTitle("Confirm There is no otp verification when SIM Swap is selected");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/alertTitle")));
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity.glo:id/alertTitle", "NIN Verification");

        //Confirm OTP Verification when Sim Upgrade is selected
        TestUtils.testTitle("Confirm OTP Verification when Sim Upgrade is selected");

        //Go back
        getDriver().pressKeyCode(AndroidKeyCode.BACK);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/swapCategory")));
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/swapCategory")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='SIM UPGRADE']")));
        getDriver().findElement(By.xpath("//android.widget.TextView[@text='SIM UPGRADE']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='SIM UPGRADE']")));

        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/btnValidate")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/dialog_title")));
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity.glo:id/dialog_title", "OTP verification");

        String otp = ConnectDB.getOTP(otp_phone_number);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/dialog_title")));
        TestUtils.testTitle("Valid OTP Test: " + otp);
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/user_input_dialog")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/user_input_dialog")).sendKeys(otp);
        getDriver().findElement(By.id("android:id/button1")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/alertTitle")));
        TestUtils.assertSearchText("ID", "android:id/message", "The specified otp and msisdn combinations are valid.");
        getDriver().findElement(By.id("android:id/button1")).click();


        //Check for Approve Sim Swap Validation
        try {
            TestUtils.testTitle("Approve Sim Swap Validation");
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/alertTitle")));
            getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/approve_username")).clear();
            getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/approve_username")).sendKeys(approver_username);
            getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/approve_password")).clear();
            getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/approve_password")).sendKeys(approver_password);
            getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/approve")).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/alertTitle")));
            TestUtils.assertSearchText("ID", "com.sf.biocapture.activity.glo:id/alertTitle", "Approval Status");
            TestUtils.assertSearchText("ID", "android:id/message", "Successfully approved!");
            getDriver().findElement(By.id("android:id/button1")).click();
        } catch (Exception e) {
            testInfo.get().info("Approve Validation view not displayed");
        }

        //Verify NIN
        TestBase.verifyNINTest(nin, "Search By NIN");
    }

    @Parameters({"dataEnv"})
    @Test
    public void capturePrepaidSimUpgrade(String dataEnv) throws Exception {

        WebDriverWait wait = new WebDriverWait(getDriver(), 30);
        JSONParser parser = new JSONParser();
        JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resource/" + dataEnv + "/data.conf.json"));
        JSONObject envs = (JSONObject) config.get("SIMSwap");
        JSONObject envs2 = (JSONObject) config.get("Login");

        String valid_username = (String) envs2.get("valid_username");

        String valid_Msisdn = (String) envs.get("valid_Msisdn");
        String fName = (String) envs.get("fName");
        String lName = (String) envs.get("lName");
        String mmn = (String) envs.get("mmn");
        String dob = (String) envs.get("dob");
        String last_recharge_Amount = (String) envs.get("last_recharge_Amount");
        String last_recharge_date = (String) envs.get("last_recharge_date");
        String occupation = (String) envs.get("occupation");
        String state = (String) envs.get("state");
        String gender = (String) envs.get("gender");
        String nationality = (String) envs.get("nationality");
        String activation_year = (String) envs.get("activation_year");
        String address = (String) envs.get("address");
        String fdn1 = (String) envs.get("fdn1");
        String fdn2 = (String) envs.get("fdn2");
        String fdn3 = (String) envs.get("fdn3");
        String fdn4 = (String) envs.get("fdn4");
        String fdn5 = (String) envs.get("fdn5");
        String puk = (String) envs.get("puk");
        String serial = (String) envs.get("serial");
        String alternate_phone = (String) envs.get("alternate_phone");

        String inavlidPostFix = "NO";

        //Capture Subscriber face
        TestUtils.testTitle("Capture Subscriber Face");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/title_header")));
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity.glo:id/title_header", "Face Capture");
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/button_next_action")).click();
        Thread.sleep(500);
        getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/captureButton")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/faceView")));
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity.glo:id/preview", "Preview");
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/button_next_action")).click();
        Thread.sleep(500);

        //Demographics View
        TestUtils.testTitle("Supply Basic Information");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/title_header")));
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity.glo:id/title_header", "Basic Information");

        //Submit with empty details
        TestUtils.testTitle("Proceed without supplying details");
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/surname")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/firstnameTXT")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/maidenNameTxt")).clear();
        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity.glo:id/submit");
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/submit")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/alertTitle")));
        try {
            TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='First Name is empty']", "First Name is empty");
            TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Surname is empty']", "Surname is empty");
            TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Mother's Maiden Name is empty']", "Mother's Maiden Name is empty");
            TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Residential Address is empty']", "Residential Address is empty");
            TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Activation year is empty']", "Activation year is empty");
            TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Ensure no frequently dialed number field is empty']", "Ensure no frequently dialed number field is empty");
            TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Last Recharge Date is empty']", "Last Recharge Date is empty");
            TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Last Recharge Amount is empty']", "Last Recharge Amount is empty");
            TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Activation year is empty']", "Activation year is empty");
        } catch (Exception e) {

        }
        getDriver().findElement(By.id("android:id/button1")).click();
        TestUtils.scrollUp2();
        TestUtils.scrollUp2();
        TestUtils.scrollUp2();
        Thread.sleep(500);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/firstnameTXT")));

        //Enter invalid details
        TestUtils.testTitle("Enter Non-Matching Details");
        //first name
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/firstnameTXT")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/firstnameTXT")).sendKeys(fName+inavlidPostFix);

        //last name
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/surname")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/surname")).sendKeys(lName+inavlidPostFix);

        //Mothers  maiden name
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/maidenNameTxt")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/maidenNameTxt")).sendKeys(mmn+inavlidPostFix);

        //scroll down
        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity.glo:id/male");

        //gender
        if (gender.equalsIgnoreCase("Male")) {
            getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/male")).click();
        } else {
            getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/female")).click();
        }

        //scroll down
        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity.glo:id/dateofBirthTXT");

        //Date of birth
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/dateofBirthTXT")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/dateofBirthTXT")).sendKeys(dob);

        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity.glo:id/occupationSpinner");
        //occupation
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/occupationSpinner")));
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/occupationSpinner")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Select Item']")));
        getDriver().findElement(By.xpath("//android.widget.TextView[@text='" + occupation + "']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.CheckedTextView[@text='" + occupation + "']")));

        //scroll down
        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity.glo:id/alternatePhoneTXT");

        //Alternate Phone Number
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/alternatePhoneTXT")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/alternatePhoneTXT")).sendKeys(alternate_phone);

        //scroll down
        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity.glo:id/residentialAddress");

        //Residential Address
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/residentialAddress")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/residentialAddress")).sendKeys(address+inavlidPostFix);

        //scroll down
        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity.glo:id/countrySpinner");
        //Country
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/countrySpinner")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='[Select Nationality]*']")));
        getDriver().findElement(By.xpath("//android.widget.TextView[@text='" + nationality + "']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='" + nationality + "']")));

        //scroll down
        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity.glo:id/stateOfOriginSpinner");

        //State of origin
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/stateOfOriginSpinner")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='[Select State of Origin]']")));
        getDriver().findElement(By.xpath("//android.widget.TextView[@text='" + state + "']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='" + state + "']")));

        //scroll down
        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity.glo:id/serial");
        //SIM Serial
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/serial")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/serial")).sendKeys(serial);

        //scroll down
        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity.glo:id/puk");
        //PUK
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/puk")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/puk")).sendKeys(puk);

        //scroll down
        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity.glo:id/activationYear");

        //Activation Year
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/activationYear")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/activationYear")).sendKeys(activation_year);

        //scroll down
        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity.glo:id/lastRechargeAmt");

        //Last Recharge Amount
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/lastRechargeAmt")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/lastRechargeAmt")).sendKeys(last_recharge_Amount);

        //scroll down
        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity.glo:id/lastRechargeDate");

        //Last Recharge Date
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/lastRechargeDate")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/lastRechargeDate")).sendKeys(last_recharge_date);

        //Frequently Dialed Numbers
        //scroll down
        TestUtils.scrollUntilElementIsVisible("XPATH", "//android.widget.EditText[@text='Frequently Dialed Number  1']");
        //FDN 1
        getDriver().findElement(By.xpath("//android.widget.EditText[@text='Frequently Dialed Number  1']")).clear();
        getDriver().findElement(By.xpath("//android.widget.EditText[@text='Frequently Dialed Number  1']")).sendKeys(fdn1+"1234");

        //scroll down
        TestUtils.scrollUntilElementIsVisible("XPATH", "//android.widget.EditText[@text='Frequently Dialed Number  2']");
        //FDN 2
        getDriver().findElement(By.xpath("//android.widget.EditText[@text='Frequently Dialed Number  2']")).clear();
        getDriver().findElement(By.xpath("//android.widget.EditText[@text='Frequently Dialed Number  2']")).sendKeys(fdn2+"1234");

        //scroll down
        TestUtils.scrollUntilElementIsVisible("XPATH", "//android.widget.EditText[@text='Frequently Dialed Number  3']");
        //FDN 3
        getDriver().findElement(By.xpath("//android.widget.EditText[@text='Frequently Dialed Number  3']")).clear();
        getDriver().findElement(By.xpath("//android.widget.EditText[@text='Frequently Dialed Number  3']")).sendKeys(fdn3+"1234");

        //scroll down
        TestUtils.scrollUntilElementIsVisible("XPATH", "//android.widget.EditText[@text='Frequently Dialed Number  4']");
        //FDN 4
        getDriver().findElement(By.xpath("//android.widget.EditText[@text='Frequently Dialed Number  4']")).clear();
        getDriver().findElement(By.xpath("//android.widget.EditText[@text='Frequently Dialed Number  4']")).sendKeys(fdn4+"1234");

        //scroll down
        TestUtils.scrollUntilElementIsVisible("XPATH", "//android.widget.EditText[@text='Frequently Dialed Number  5']");

        //FDN 5
        getDriver().findElement(By.xpath("//android.widget.EditText[@text='Frequently Dialed Number  5']")).clear();
        getDriver().findElement(By.xpath("//android.widget.EditText[@text='Frequently Dialed Number  5']")).sendKeys(fdn5);

        //Check for fingerpprint Capture Button
        try {
            TestUtils.assertSearchText("ID", "com.sf.biocapture.activity.glo:id/capturePrint", "CAPTURE FINGER PRINT");
            getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/capturePrint")).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/alertTitle")));
            TestUtils.assertSearchText("ID", "com.sf.biocapture.activity.glo:id/alertTitle", "Scanner not found");
            TestUtils.assertSearchText("ID", "android:id/message", "Fingerprint scanner device not detected. Ensure your scanner device is connected and try again.");
            getDriver().findElement(By.id("android:id/button1")).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/fingerType_text")));
            //Go back
            getDriver().pressKeyCode(AndroidKeyCode.BACK);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/submit")));
        } catch (Exception e) {
            testInfo.get().info("Fingerprint validation button not found");
        }

        //Submit
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/submit")).click();

        //Assert Sim Swap Response
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Sim Swap Response']")));
        TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Authentication Score Card']", "Authentication Score Card");

        TestUtils.testTitle("Mandatory Parametter");
        testInfo.get().info("Check Screenshot for report");
        TestUtils.logScreenshot();

        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity.glo:id/optionalParamsRecyclerView");

        TestUtils.testTitle("Optional Parameter");
        testInfo.get().info("Check Screenshot for report");
        TestUtils.logScreenshot();
        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity.glo:id/mandatory_checks_status");
        TestUtils.logScreenshot();

        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity.glo:id/btnSwapOk");
        TestUtils.testTitle("Summary");
        Asserts.AssertSwapSummary();

        //click ok
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/btnSwapOk")).click();

        //Proceed with valid details
        TestUtils.testTitle("Procced with matching details");

        TestUtils.scrollUp2();
        TestUtils.scrollUp2();
        TestUtils.scrollUp2();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/firstnameTXT")));
        //first name
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/firstnameTXT")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/firstnameTXT")).sendKeys(fName);

        //last name
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/surname")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/surname")).sendKeys(lName);

        //Mothers  maiden name
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/maidenNameTxt")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/maidenNameTxt")).sendKeys(mmn);

        //scroll down
        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity.glo:id/male");

        //gender
        if (gender.equalsIgnoreCase("Male")) {
            getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/male")).click();
        } else {
            getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/female")).click();
        }

        //scroll down
        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity.glo:id/dateofBirthTXT");

        //Date of birth
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/dateofBirthTXT")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/dateofBirthTXT")).sendKeys(dob);

        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity.glo:id/occupationSpinner");
        //occupation
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/occupationSpinner")));
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/occupationSpinner")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Select Item']")));
        getDriver().findElement(By.xpath("//android.widget.TextView[@text='" + occupation + "']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.CheckedTextView[@text='" + occupation + "']")));

        //scroll down
        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity.glo:id/alternatePhoneTXT");

        //Alternate Phone Number
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/alternatePhoneTXT")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/alternatePhoneTXT")).sendKeys(alternate_phone);

        //scroll down
        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity.glo:id/residentialAddress");

        //Residential Address
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/residentialAddress")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/residentialAddress")).sendKeys(address);

        //scroll down
        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity.glo:id/countrySpinner");
        //Country
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/countrySpinner")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='[Select Nationality]*']")));
        getDriver().findElement(By.xpath("//android.widget.TextView[@text='" + nationality + "']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='" + nationality + "']")));

        //scroll down
        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity.glo:id/stateOfOriginSpinner");

        //State of origin
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/stateOfOriginSpinner")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='[Select State of Origin]']")));
        getDriver().findElement(By.xpath("//android.widget.TextView[@text='" + state + "']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='" + state + "']")));

        //scroll down
        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity.glo:id/serial");
        //SIM Serial
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/serial")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/serial")).sendKeys(serial);

        //scroll down
        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity.glo:id/puk");
        //PUK
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/puk")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/puk")).sendKeys(puk);

        //scroll down
        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity.glo:id/activationYear");

        //Activation Year
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/activationYear")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/activationYear")).sendKeys(activation_year);

        //scroll down
        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity.glo:id/lastRechargeAmt");

        //Last Recharge Amount
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/lastRechargeAmt")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/lastRechargeAmt")).sendKeys(last_recharge_Amount);

        //scroll down
        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity.glo:id/lastRechargeDate");

        //Last Recharge Date
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/lastRechargeDate")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/lastRechargeDate")).sendKeys(last_recharge_date);

        //Frequently Dialed Numbers
        //scroll down
        TestUtils.scrollUntilElementIsVisible("XPATH", "//android.widget.EditText[@text='"+fdn1+"1234']");
        //FDN 1
        getDriver().findElement(By.xpath("//android.widget.EditText[@text='"+fdn1+"1234']")).clear();
        getDriver().findElement(By.xpath("//android.widget.EditText[@text='Frequently Dialed Number  1']")).sendKeys(fdn1);

        //scroll down
        TestUtils.scrollUntilElementIsVisible("XPATH", "//android.widget.EditText[@text='"+fdn2+"1234']");
        //FDN 2
        getDriver().findElement(By.xpath("//android.widget.EditText[@text='"+fdn2+"1234']")).clear();
        getDriver().findElement(By.xpath("//android.widget.EditText[@text='Frequently Dialed Number  2']")).sendKeys(fdn2);

        //scroll down
        TestUtils.scrollUntilElementIsVisible("XPATH", "//android.widget.EditText[@text='"+fdn3+"1234']");
        //FDN 3
        getDriver().findElement(By.xpath("//android.widget.EditText[@text='"+fdn3+"1234']")).clear();
        getDriver().findElement(By.xpath("//android.widget.EditText[@text='Frequently Dialed Number  3']")).sendKeys(fdn3);

        //scroll down
        TestUtils.scrollUntilElementIsVisible("XPATH", "//android.widget.EditText[@text='"+fdn4+"1234']");
        //FDN 4
        getDriver().findElement(By.xpath("//android.widget.EditText[@text='"+fdn4+"1234']")).clear();
        getDriver().findElement(By.xpath("//android.widget.EditText[@text='Frequently Dialed Number  4']")).sendKeys(fdn4);

        //scroll down
        TestUtils.scrollUntilElementIsVisible("XPATH", "//android.widget.EditText[@text='"+fdn5+"']");

        //FDN 5
        getDriver().findElement(By.xpath("//android.widget.EditText[@text='"+fdn5+"']")).clear();
        getDriver().findElement(By.xpath("//android.widget.EditText[@text='Frequently Dialed Number  5']")).sendKeys(fdn5);

        //Check for fingerpprint Capture Button
        try {
            TestUtils.assertSearchText("ID", "com.sf.biocapture.activity.glo:id/capturePrint", "CAPTURE FINGER PRINT");
            getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/capturePrint")).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/alertTitle")));
            TestUtils.assertSearchText("ID", "com.sf.biocapture.activity.glo:id/alertTitle", "Scanner not found");
            TestUtils.assertSearchText("ID", "android:id/message", "Fingerprint scanner device not detected. Ensure your scanner device is connected and try again.");
            getDriver().findElement(By.id("android:id/button1")).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/fingerType_text")));
            //Go back
            getDriver().pressKeyCode(AndroidKeyCode.BACK);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/submit")));
        } catch (Exception e) {
            testInfo.get().info("Fingerprint validation button not found");
        }

        //Submit
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/submit")).click();

        //Assert Sim Swap Response
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Sim Swap Response']")));
        TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Authentication Score Card']", "Authentication Score Card");

        TestUtils.testTitle("Mandatory Parametter");
        testInfo.get().info("Check Screenshot for report");
        TestUtils.logScreenshot();

        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity.glo:id/optionalParamsRecyclerView");

        TestUtils.testTitle("Optional Parameter");
        testInfo.get().info("Check Screenshot for report");
        TestUtils.logScreenshot();
        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity.glo:id/btnSwapOk");
        TestUtils.logScreenshot();

        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity.glo:id/btnSwapOk");
        TestUtils.testTitle("Summary");
        Asserts.AssertSwapSummary();

        //click ok
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/btnSwapOk")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/title_header")));
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity.glo:id/title_header", "Document Upload");

        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/document_image")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/summary")));
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity.glo:id/summary", "SELECT DOCUMENT TYPE");
        TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Picture of Sim or Sim Card Holder']", "Picture of Sim or Sim Card Holder");
        TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Valid ID Card']", "Valid ID Card");

        //Picture of Sim or Sim Card Holder
        TestUtils.testTitle("Picture of Sim or Sim Card Holder");
        getDriver().findElement(By.xpath("//android.widget.TextView[@text='Picture of Sim or Sim Card Holder']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/alertTitle")));
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity.glo:id/alertTitle", "Sim Swap Document Upload");
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity.glo:id/doc_upload_btn", "DOCUMENT UPLOAD");
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity.glo:id/doc_capture_btn", "DOCUMENT CAPTURE");


        //Upload invalid file
        TestUtils.testTitle("Upload invalid file");
        // Push File
        File pic = new File(System.getProperty("user.dir") + "/files/invalid.png");
        getDriver().pushFile("/storage/emulated/0/invalid.png", pic);

        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/doc_upload_btn")).click();
        Thread.sleep(500);
        TestUtils.scrollUntilElementIsVisible("XPATH", "//android.widget.TextView[@text='invalid.png']");
        getDriver().findElement(By.xpath("//android.widget.TextView[@text='invalid.png']")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/alertTitle")));
        TestUtils.assertSearchText("ID", "android:id/message", "You have selected an invalid file format, file must be in JPG,PDF format");
        getDriver().findElement(By.id("android:id/button1")).click();

        TestUtils.testTitle("Upload Valid Document");
        // Push File
        File pic2 = new File(System.getProperty("user.dir") + "/files/idCard.jpg");
        getDriver().pushFile("/storage/emulated/0/picture.jpg", pic2);
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/document_image")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/summary")));
        getDriver().findElement(By.xpath("//android.widget.TextView[@text='Picture of Sim or Sim Card Holder']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/alertTitle")));
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/doc_upload_btn")).click();
        Thread.sleep(500);
        TestUtils.scrollUntilElementIsVisible("XPATH", "//android.widget.TextView[@text='picture.jpg']");
        getDriver().findElement(By.xpath("//android.widget.TextView[@text='picture.jpg']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/document")));
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity.glo:id/doc_name", "Picture of Sim or Sim Card Holder");

        //Remove picture and Capture Document
        TestUtils.testTitle("Remove uploaded document and Capture Document");
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/document")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/message")));
        TestUtils.assertSearchText("ID", "android:id/message", "Do you want to delete uploaded document");
        getDriver().findElement(By.id("android:id/button1")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/document_image")));
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/document_image")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/summary")));
        getDriver().findElement(By.xpath("//android.widget.TextView[@text='Valid ID Card']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/alertTitle")));
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/doc_capture_btn")).click();
        Thread.sleep(500);
        getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/captureButton")).click();
        Thread.sleep(1000);
        getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/ok")).click();
        Thread.sleep(500);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/continue_btn")));
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/continue_btn")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/message")));
        TestUtils.assertSearchText("ID", "android:id/message", "I, " + valid_username + " acknowledge the submission of the swap request for "+valid_Msisdn);
        getDriver().findElement(By.id("android:id/button1")).click();

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Performing Simswap request']")));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/message")));
        TestUtils.assertSearchText("ID", "android:id/message", "Subscriber information successfully saved.");
        getDriver().findElement(By.id("android:id/button1")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Home']")));
    }


    @Parameters({"dataEnv"})
    @Test
    public void capturePostpaidSimSwap(String dataEnv) throws Exception {

        WebDriverWait wait = new WebDriverWait(getDriver(), 30);
        JSONParser parser = new JSONParser();
        JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resource/" + dataEnv + "/data.conf.json"));
        JSONObject envs = (JSONObject) config.get("SIMSwap");
        JSONObject envs2 = (JSONObject) config.get("Login");

        String valid_username = (String) envs2.get("valid_username");

        String valid_sim_serial = (String) envs.get("pp_valid_sim_serial");
        String valid_Msisdn = (String) envs.get("pp_valid_Msisdn");
        String new_msisdn = (String) envs.get("pp_new_msisdn");
        String approver_username = (String) envs.get("approver_username");
        String approver_password = (String) envs.get("approver_password");
        String fName = (String) envs.get("pp_fName");
        String lName = (String) envs.get("pp_lName");
        String mmn = (String) envs.get("pp_mmn");
        String dob = (String) envs.get("pp_dob");
        String occupation = (String) envs.get("pp_occupation");
        String state = (String) envs.get("pp_state");
        String gender = (String) envs.get("pp_gender");
        String nationality = (String) envs.get("pp_nationality");
        String activation_year = (String) envs.get("pp_activation_year");
        String address = (String) envs.get("pp_address");
        String fdn1 = (String) envs.get("pp_fdn1");
        String fdn2 = (String) envs.get("pp_fdn2");
        String fdn3 = (String) envs.get("pp_fdn3");
        String fdn4 = (String) envs.get("pp_fdn4");
        String fdn5 = (String) envs.get("pp_fdn5");
        String nin = (String) envs.get("nin");
        String proxy_name = (String) envs.get("proxy_name");
        String proxy_phone = (String) envs.get("proxy_phone");
        String last_invoice_amount = (String) envs.get("pp_last_invoice_amount");
        String last_invoice_date = (String) envs.get("pp_last_invoice_date");
        String puk = (String) envs.get("pp_puk");
        String serial = (String) envs.get("pp_serial");
        String alternate_phone = (String) envs.get("alternate_phone");

        navigateToCaptureMenuTest();
        navigateToSimSwapViewTest(dataEnv);


        //Select SIM Swap type
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/radio_item_proxl")).click();

        //Select Subscriber Type
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/subscriberType")).click();
        getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Postpaid']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Postpaid']")));

        //Select Swap Category
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/swapCategory")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='SIM UPGRADE']")));
        getDriver().findElement(By.xpath("//android.widget.TextView[@text='SIM SWAP']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='SIM SWAP']")));

        //Enter Valid existing MSISDN
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/existingMsisdnField")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/existingMsisdnField")).sendKeys(valid_Msisdn);


        //Enter Valid New MSISDN
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/newMsisdnField")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/newMsisdnField")).sendKeys(new_msisdn);

        //Enter Valid New Sim Serail
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/simSerialField")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/simSerialField")).sendKeys(valid_sim_serial);

        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/btnValidate")).click();

        //Check for Approve Sim Swap Validation
        try {
            TestUtils.testTitle("Approve Sim Swap Validation");
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/alertTitle")));
            getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/approve_username")).clear();
            getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/approve_username")).sendKeys(approver_username);
            getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/approve_password")).clear();
            getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/approve_password")).sendKeys(approver_password);
            getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/approve")).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/alertTitle")));
            TestUtils.assertSearchText("ID", "com.sf.biocapture.activity.glo:id/alertTitle", "Approval Status");
            TestUtils.assertSearchText("ID", "android:id/message", "Successfully approved!");
            getDriver().findElement(By.xpath("//android.widget.Button[@text='OK']")).click();
        } catch (Exception e) {
            testInfo.get().info("SIM Swap validation view not displayed");
        }
        //Confirm There is no otp verification when SIM Swap is selected
        TestUtils.testTitle("Confirm There is no otp verification when SIM Swap is selected");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/alertTitle")));
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity.glo:id/alertTitle", "NIN Verification");

        //Verify NIN
        TestBase.verifyNINTest(nin, "Search By NIN");

        //Capture Subscriber face
        TestUtils.testTitle("Capture Subscriber Face");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/title_header")));
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity.glo:id/title_header", "Face Capture");
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/button_next_action")).click();
        Thread.sleep(500);
        getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/captureButton")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/faceView")));
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity.glo:id/preview", "Preview");
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/button_next_action")).click();
        Thread.sleep(500);

        //Demographics View
        TestUtils.testTitle("Supply Basic Information");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/title_header")));
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity.glo:id/title_header", "Basic Information");

        //Submit with empty details
        TestUtils.testTitle("Proceed without supplying details");
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/surname")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/firstnameTXT")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/maidenNameTxt")).clear();
        TestUtils.scrollByID("com.sf.biocapture.activity.glo:id/submit", 0);
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/submit")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/alertTitle")));
        try {
            TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='First Name is empty']", "First Name is empty");
            TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Surname is empty']", "Surname is empty");
            TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Mother's Maiden Name is empty']", "Mother's Maiden Name is empty");
            TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Residential Address is empty']", "Residential Address is empty");
            TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Activation year is empty']", "Activation year is empty");
            TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Ensure no frequently dialed number field is empty']", "Ensure no frequently dialed number field is empty");
            TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Last Recharge Date is empty']", "Last Recharge Date is empty");
            TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Last Recharge Amount is empty']", "Last Recharge Amount is empty");
            TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Activation year is empty']", "Activation year is empty");
        } catch (Exception e) {

        }
        getDriver().findElement(By.id("android:id/button1")).click();
        TestUtils.scrollUp2();
        TestUtils.scrollUp2();
        TestUtils.scrollUp2();
        TestUtils.scrollUp2();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/firstnameTXT")));


        //first name
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/firstnameTXT")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/firstnameTXT")).sendKeys(fName);

        //last name
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/surname")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/surname")).sendKeys(lName);

        //Mothers  maiden name
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/maidenNameTxt")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/maidenNameTxt")).sendKeys(mmn);

        //scroll down
        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity.glo:id/male");

        //gender
        if (gender.equalsIgnoreCase("Male")) {
            getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/male")).click();
        } else {
            getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/female")).click();
        }

        //scroll down
        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity.glo:id/dateofBirthTXT");

        //Date of birth
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/dateofBirthTXT")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/dateofBirthTXT")).sendKeys(dob);

        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity.glo:id/occupationSpinner");
        //occupation
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/occupationSpinner")));
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/occupationSpinner")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Select Item']")));
        getDriver().findElement(By.xpath("//android.widget.TextView[@text='" + occupation + "']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.CheckedTextView[@text='" + occupation + "']")));

        //scroll down
        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity.glo:id/alternatePhoneTXT");

        //Alternate Phone Number
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/alternatePhoneTXT")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/alternatePhoneTXT")).sendKeys(alternate_phone);

        //scroll down
        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity.glo:id/residentialAddress");

        //Residential Address
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/residentialAddress")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/residentialAddress")).sendKeys(address);

        //scroll down
        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity.glo:id/countrySpinner");
        //Country
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/countrySpinner")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='[Select Nationality]*']")));
        getDriver().findElement(By.xpath("//android.widget.TextView[@text='" + nationality + "']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='" + nationality + "']")));

        //scroll down
        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity.glo:id/stateOfOriginSpinner");

        //State of origin
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/stateOfOriginSpinner")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='[Select State of Origin]']")));
        getDriver().findElement(By.id("android:id/search_src_text")).clear();
        getDriver().findElement(By.id("android:id/search_src_text")).sendKeys(state);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='" + state + "']")));
        getDriver().findElement(By.xpath("//android.widget.TextView[@text='" + state + "']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='" + state + "']")));

        //scroll down
        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity.glo:id/serial");
        //SIM Serial
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/serial")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/serial")).sendKeys(serial);

        //scroll down
        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity.glo:id/puk");
        //PUK
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/puk")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/puk")).sendKeys(puk);

        //scroll down
        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity.glo:id/proxyNameTXT");

        //Proxy Name
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/proxyNameTXT")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/proxyNameTXT")).sendKeys(proxy_name);

        //scroll down
        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity.glo:id/proxyPhoneNoTXT");

        //Proxy Phone
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/proxyPhoneNoTXT")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/proxyPhoneNoTXT")).sendKeys(proxy_phone);

        //scroll down
        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity.glo:id/lastInvoiceAmt");

        //Last invoice amount
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/lastInvoiceAmt")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/lastInvoiceAmt")).sendKeys(last_invoice_amount);

        //scroll down
        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity.glo:id/lastInvoiceDate");

        //Last invoice date
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/lastInvoiceDate")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/lastInvoiceDate")).sendKeys(last_invoice_date);

        //scroll down
        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity.glo:id/activationYear");

        //Activation Year
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/activationYear")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/activationYear")).sendKeys(activation_year);

        //Frequently Dialed Numbers
        //scroll down
        TestUtils.scrollUntilElementIsVisible("XPATH", "//android.widget.EditText[@text='Frequently Dialed Number  1']");
        //FDN 1
        getDriver().findElement(By.xpath("//android.widget.EditText[@text='Frequently Dialed Number  1']")).clear();
        getDriver().findElement(By.xpath("//android.widget.EditText[@text='Frequently Dialed Number  1']")).sendKeys(fdn1);

        //scroll down
        TestUtils.scrollUntilElementIsVisible("XPATH", "//android.widget.EditText[@text='Frequently Dialed Number  2']");
        //FDN 2
        getDriver().findElement(By.xpath("//android.widget.EditText[@text='Frequently Dialed Number  2']")).clear();
        getDriver().findElement(By.xpath("//android.widget.EditText[@text='Frequently Dialed Number  2']")).sendKeys(fdn2);

        //scroll down
        TestUtils.scrollUntilElementIsVisible("XPATH", "//android.widget.EditText[@text='Frequently Dialed Number  3']");
        //FDN 3
        getDriver().findElement(By.xpath("//android.widget.EditText[@text='Frequently Dialed Number  3']")).clear();
        getDriver().findElement(By.xpath("//android.widget.EditText[@text='Frequently Dialed Number  3']")).sendKeys(fdn3);

        //scroll down
        TestUtils.scrollUntilElementIsVisible("XPATH", "//android.widget.EditText[@text='Frequently Dialed Number  4']");
        //FDN 4
        getDriver().findElement(By.xpath("//android.widget.EditText[@text='Frequently Dialed Number  4']")).clear();
        getDriver().findElement(By.xpath("//android.widget.EditText[@text='Frequently Dialed Number  4']")).sendKeys(fdn4);

        //scroll down
        TestUtils.scrollUntilElementIsVisible("XPATH", "//android.widget.EditText[@text='Frequently Dialed Number  5']");

        //FDN 5
        getDriver().findElement(By.xpath("//android.widget.EditText[@text='Frequently Dialed Number  5']")).clear();
        getDriver().findElement(By.xpath("//android.widget.EditText[@text='Frequently Dialed Number  5']")).sendKeys(fdn5);

        //Check for fingerpprint Capture Button
        try {
            TestUtils.assertSearchText("ID", "com.sf.biocapture.activity.glo:id/capturePrint", "CAPTURE FINGER PRINT");
            getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/capturePrint")).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/alertTitle")));
            TestUtils.assertSearchText("ID", "com.sf.biocapture.activity.glo:id/alertTitle", "Scanner not found");
            TestUtils.assertSearchText("ID", "android:id/message", "Fingerprint scanner device not detected. Ensure your scanner device is connected and try again.");
            getDriver().findElement(By.id("android:id/button1")).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/fingerType_text")));
            //Go back
            getDriver().pressKeyCode(AndroidKeyCode.BACK);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/submit")));
        } catch (Exception e) {
            testInfo.get().info("Fingerprint validation button not found");
        }

        //Submit
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/submit")).click();

        //Assert Sim Swap Response
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Sim Swap Response']")));
        TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Authentication Score Card']", "Authentication Score Card");

        TestUtils.testTitle("Mandatory Parametter");
        testInfo.get().info("Check Screenshot for report");
        TestUtils.logScreenshot();

        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity.glo:id/optionalParamsRecyclerView");

        TestUtils.testTitle("Optional Parameter");
        testInfo.get().info("Check Screenshot for report");
        TestUtils.logScreenshot();


        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity.glo:id/btnSwapOk");
        TestUtils.logScreenshot();

        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity.glo:id/btnSwapOk");
        TestUtils.scrollDown();
        TestUtils.testTitle("Summary");
        Asserts.AssertSwapSummary();

        //click ok
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/btnSwapOk")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/title_header")));
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity.glo:id/title_header", "Document Upload");

        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/document_image")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/summary")));
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity.glo:id/summary", "SELECT DOCUMENT TYPE");
        TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Proxy Affidavit']", "Proxy Affidavit");
        TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Proxy ID']", "Proxy ID");
        TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Valid ID Card']", "Valid ID Card");
        TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Court Affidavit']", "Court Affidavit");
        TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Company Letter of Authorization']", "Company Letter of Authorization");
        TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Sim Replacement Form']", "Sim Replacement Form");

        //Proxy ID
        TestUtils.testTitle("Proxy ID");
        getDriver().findElement(By.xpath("//android.widget.TextView[@text='Proxy ID']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/alertTitle")));
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity.glo:id/alertTitle", "Sim Swap Document Upload");
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity.glo:id/doc_upload_btn", "DOCUMENT UPLOAD");
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity.glo:id/doc_capture_btn", "DOCUMENT CAPTURE");


        //Upload invalid file
        TestUtils.testTitle("Upload invalid file");
        // Push File
        File pic = new File(System.getProperty("user.dir") + "/files/invalid.png");
        getDriver().pushFile("/storage/emulated/0/invalid.png", pic);

        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/doc_upload_btn")).click();
        Thread.sleep(500);
        TestUtils.scrollUntilElementIsVisible("XPATH", "//android.widget.TextView[@text='invalid.png']");
        getDriver().findElement(By.xpath("//android.widget.TextView[@text='invalid.png']")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/alertTitle")));
        TestUtils.assertSearchText("ID", "android:id/message", "You have selected an invalid file format, file must be in JPG,PDF format");
        getDriver().findElement(By.id("android:id/button1")).click();

        TestUtils.testTitle("Upload Valid Document");
        // Push File
        File pic2 = new File(System.getProperty("user.dir") + "/files/idCard.jpg");
        getDriver().pushFile("/storage/emulated/0/picture.jpg", pic2);
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/document_image")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/summary")));
        getDriver().findElement(By.xpath("//android.widget.TextView[@text='Proxy ID']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/alertTitle")));
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/doc_upload_btn")).click();
        Thread.sleep(500);
        TestUtils.scrollUntilElementIsVisible("XPATH", "//android.widget.TextView[@text='picture.jpg']");
        getDriver().findElement(By.xpath("//android.widget.TextView[@text='picture.jpg']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/document")));
        TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Proxy ID']", "Proxy ID");

        //Continue without uploading Proxy Affidavit
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/continue_btn")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/text1")));
        TestUtils.assertSearchText("ID", "android:id/text1", "Proxy Affidavit is compulsory");
        getDriver().pressKeyCode(AndroidKeyCode.BACK);

        //Capture Proxy Affidavit
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/document_image")));
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/document_image")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/summary")));
        getDriver().findElement(By.xpath("//android.widget.TextView[@text='Proxy Affidavit']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/alertTitle")));
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/doc_capture_btn")).click();
        Thread.sleep(500);
        getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/captureButton")).click();
        Thread.sleep(500);
        getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/ok")).click();
        Thread.sleep(500);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/continue_btn")));

        TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Proxy Affidavit']", "Proxy Affidavit");

        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/continue_btn")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/message")));
        TestUtils.assertSearchText("ID", "android:id/message", "I, " + valid_username + " acknowledge the submission of the swap request for "+valid_Msisdn);
        getDriver().findElement(By.id("android:id/button1")).click();

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Performing Simswap request']")));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/message")));
        TestUtils.assertSearchText("ID", "android:id/message", "Subscriber information successfully saved.");
        getDriver().findElement(By.id("android:id/button1")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Home']")));
    }

    @Parameters({"dataEnv"})
    @Test
    public void captureDataSimSwap(String dataEnv) throws Exception {

        WebDriverWait wait = new WebDriverWait(getDriver(), 30);
        JSONParser parser = new JSONParser();
        JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resource/" + dataEnv + "/data.conf.json"));
        JSONObject envs = (JSONObject) config.get("SIMSwap");
        JSONObject envs2 = (JSONObject) config.get("Login");

        String valid_username = (String) envs2.get("valid_username");

        String valid_sim_serial = (String) envs.get("valid_sim_serial");
        String valid_Msisdn = (String) envs.get("valid_Msisdn");
        String new_msisdn = (String) envs.get("new_msisdn");
        String approver_username = (String) envs.get("approver_username");
        String approver_password = (String) envs.get("approver_password");
        String fName = (String) envs.get("fName");
        String lName = (String) envs.get("lName");
        String mmn = (String) envs.get("mmn");
        String dob = (String) envs.get("dob");
        String occupation = (String) envs.get("occupation");
        String state = (String) envs.get("state");
        String gender = (String) envs.get("gender");
        String nationality = (String) envs.get("nationality");
        String activation_year = (String) envs.get("activation_year");
        String address = (String) envs.get("address");
        String fdn1 = (String) envs.get("fdn1");
        String fdn2 = (String) envs.get("fdn2");
        String fdn3 = (String) envs.get("fdn3");
        String fdn4 = (String) envs.get("fdn4");
        String fdn5 = (String) envs.get("fdn5");
        String nin = (String) envs.get("nin");
        String last_recharge_Amount = (String) envs.get("last_recharge_Amount");
        String last_recharge_date = (String) envs.get("last_recharge_date");
        String puk = (String) envs.get("puk");
        String serial = (String) envs.get("serial");
        String alternate_phone = (String) envs.get("alternate_phone");
        String data_pack = (String) envs.get("data_pack");

        navigateToCaptureMenuTest();
        navigateToSimSwapViewTest(dataEnv);


        //Select SIM Swap type
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/radio_item_proxl")).click();

        //Select Subscriber Type
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/subscriberType")).click();
        getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Data']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Data']")));

        //Select Swap Category
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/swapCategory")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='SIM UPGRADE']")));
        getDriver().findElement(By.xpath("//android.widget.TextView[@text='SIM SWAP']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='SIM SWAP']")));

        //Enter Valid existing MSISDN
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/existingMsisdnField")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/existingMsisdnField")).sendKeys(valid_Msisdn);


        //Enter Valid New MSISDN
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/newMsisdnField")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/newMsisdnField")).sendKeys(new_msisdn);

        //Enter Valid New Sim Serail
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/simSerialField")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/simSerialField")).sendKeys(valid_sim_serial);

        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/btnValidate")).click();

        //Check for Approve Sim Swap Validation
        try {
            TestUtils.testTitle("Approve Sim Swap Validation");
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/alertTitle")));
            getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/approve_username")).clear();
            getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/approve_username")).sendKeys(approver_username);
            getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/approve_password")).clear();
            getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/approve_password")).sendKeys(approver_password);
            getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/approve")).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/alertTitle")));
            TestUtils.assertSearchText("ID", "com.sf.biocapture.activity.glo:id/alertTitle", "Approval Status");
            TestUtils.assertSearchText("ID", "android:id/message", "Successfully approved!");
            getDriver().findElement(By.xpath("//android.widget.Button[@text='OK']")).click();
        } catch (Exception e) {
            testInfo.get().info("SIM Swap validation not displayed");
        }
        //Confirm There is no otp verification when SIM Swap is selected
        TestUtils.testTitle("Confirm There is no otp verification when SIM Swap is selected");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/alertTitle")));
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity.glo:id/alertTitle", "NIN Verification");

        //Verify NIN
        TestBase.verifyNINTest(nin, "Search By NIN");

        //Capture Subscriber face
        TestUtils.testTitle("Capture Subscriber Face");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/title_header")));
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity.glo:id/title_header", "Face Capture");
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/button_next_action")).click();
        Thread.sleep(500);
        getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/captureButton")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/faceView")));
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity.glo:id/preview", "Preview");
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/button_next_action")).click();
        Thread.sleep(500);

        //Demographics View
        TestUtils.testTitle("Supply Basic Information");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/title_header")));
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity.glo:id/title_header", "Basic Information");

        //Submit with empty details
        TestUtils.testTitle("Proceed without supplying details");
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/surname")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/firstnameTXT")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/maidenNameTxt")).clear();
        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity.glo:id/submit");
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/submit")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/alertTitle")));
        try {
            TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='First Name is empty']", "First Name is empty");
            TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Surname is empty']", "Surname is empty");
            TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Mother's Maiden Name is empty']", "Mother's Maiden Name is empty");
            TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Residential Address is empty']", "Residential Address is empty");
            TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Activation year is empty']", "Activation year is empty");
            TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Ensure no frequently dialed number field is empty']", "Ensure no frequently dialed number field is empty");
            TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Last Recharge Date is empty']", "Last Recharge Date is empty");
            TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Last Recharge Amount is empty']", "Last Recharge Amount is empty");
            TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Activation year is empty']", "Activation year is empty");
        } catch (Exception e) {

        }
        getDriver().findElement(By.id("android:id/button1")).click();
        TestUtils.scrollUp2();
        TestUtils.scrollUp2();
        TestUtils.scrollUp2();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/firstnameTXT")));


        //first name
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/firstnameTXT")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/firstnameTXT")).sendKeys(fName);

        //last name
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/surname")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/surname")).sendKeys(lName);

        //Mothers  maiden name
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/maidenNameTxt")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/maidenNameTxt")).sendKeys(mmn);

        //scroll down
        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity.glo:id/male");

        //gender
        if (gender.equalsIgnoreCase("Male")) {
            getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/male")).click();
        } else {
            getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/female")).click();
        }

        //scroll down
        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity.glo:id/dateofBirthTXT");

        //Date of birth
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/dateofBirthTXT")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/dateofBirthTXT")).sendKeys(dob);

        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity.glo:id/occupationSpinner");
        //occupation
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/occupationSpinner")));
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/occupationSpinner")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Select Item']")));
        getDriver().findElement(By.xpath("//android.widget.TextView[@text='" + occupation + "']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.CheckedTextView[@text='" + occupation + "']")));

        //scroll down
        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity.glo:id/alternatePhoneTXT");

        //Alternate Phone Number
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/alternatePhoneTXT")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/alternatePhoneTXT")).sendKeys(alternate_phone);

        //scroll down
        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity.glo:id/residentialAddress");

        //Residential Address
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/residentialAddress")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/residentialAddress")).sendKeys(address);

        //scroll down
        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity.glo:id/countrySpinner");
        //Country
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/countrySpinner")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='[Select Nationality]*']")));
        getDriver().findElement(By.xpath("//android.widget.TextView[@text='" + nationality + "']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='" + nationality + "']")));

        //scroll down
        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity.glo:id/stateOfOriginSpinner");

        //State of origin
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/stateOfOriginSpinner")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='[Select State of Origin]']")));
        getDriver().findElement(By.xpath("//android.widget.TextView[@text='" + state + "']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='" + state + "']")));

        //scroll down
        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity.glo:id/serial");
        //SIM Serial
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/serial")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/serial")).sendKeys(serial);

        //scroll down
        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity.glo:id/puk");
        //PUK
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/puk")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/puk")).sendKeys(puk);

        //scroll down
        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity.glo:id/proxyNameTXT");



        //scroll down
        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity.glo:id/activationYear");

        //Activation Year
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/activationYear")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/activationYear")).sendKeys(activation_year);

        //Confirm Data Pack field is displayed
        TestUtils.testTitle("Confirm Data Pack field is displayed");

        //scroll down
        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity.glo:id/datePack");
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity.glo:id/datePack", "Data Pack*");

        //Data Pack
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/datePack")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/datePack")).sendKeys(data_pack);

        //Todo complete when information is available

        /*
        //scroll down
        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity.glo:id/lastRechargeAmt");

        //Last Recharge Amount
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/lastRechargeAmt")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/lastRechargeAmt")).sendKeys(last_recharge_Amount);

        //scroll down
        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity.glo:id/lastRechargeDate");

        //Last Recharge Date
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/lastRechargeDate")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/lastRechargeDate")).sendKeys(last_recharge_date);

        //Frequently Dialed Numbers
        //scroll down
        TestUtils.scrollUntilElementIsVisible("XPATH", "//android.widget.EditText[@text='Frequently Dialed Number  1']");
        //FDN 1
        getDriver().findElement(By.xpath("//android.widget.EditText[@text='Frequently Dialed Number  1']")).clear();
        getDriver().findElement(By.xpath("//android.widget.EditText[@text='Frequently Dialed Number  1']")).sendKeys(fdn1);

        //scroll down
        TestUtils.scrollUntilElementIsVisible("XPATH", "//android.widget.EditText[@text='Frequently Dialed Number  2']");
        //FDN 2
        getDriver().findElement(By.xpath("//android.widget.EditText[@text='Frequently Dialed Number  2']")).clear();
        getDriver().findElement(By.xpath("//android.widget.EditText[@text='Frequently Dialed Number  2']")).sendKeys(fdn2);

        //scroll down
        TestUtils.scrollUntilElementIsVisible("XPATH", "//android.widget.EditText[@text='Frequently Dialed Number  3']");
        //FDN 3
        getDriver().findElement(By.xpath("//android.widget.EditText[@text='Frequently Dialed Number  3']")).clear();
        getDriver().findElement(By.xpath("//android.widget.EditText[@text='Frequently Dialed Number  3']")).sendKeys(fdn3);

        //scroll down
        TestUtils.scrollUntilElementIsVisible("XPATH", "//android.widget.EditText[@text='Frequently Dialed Number  4']");
        //FDN 4
        getDriver().findElement(By.xpath("//android.widget.EditText[@text='Frequently Dialed Number  4']")).clear();
        getDriver().findElement(By.xpath("//android.widget.EditText[@text='Frequently Dialed Number  4']")).sendKeys(fdn4);

        //scroll down
        TestUtils.scrollUntilElementIsVisible("XPATH", "//android.widget.EditText[@text='Frequently Dialed Number  5']");

        //FDN 5
        getDriver().findElement(By.xpath("//android.widget.EditText[@text='Frequently Dialed Number  5']")).clear();
        getDriver().findElement(By.xpath("//android.widget.EditText[@text='Frequently Dialed Number  5']")).sendKeys(fdn5);

        //Check for fingerpprint Capture Button
        try {
            TestUtils.assertSearchText("ID", "com.sf.biocapture.activity.glo:id/capturePrint", "CAPTURE FINGER PRINT");
            getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/capturePrint")).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/alertTitle")));
            TestUtils.assertSearchText("ID", "com.sf.biocapture.activity.glo:id/alertTitle", "Scanner not found");
            TestUtils.assertSearchText("ID", "android:id/message", "Fingerprint scanner device not detected. Ensure your scanner device is connected and try again.");
            getDriver().findElement(By.id("android:id/button1")).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/fingerType_text")));
            //Go back
            getDriver().pressKeyCode(AndroidKeyCode.BACK);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/submit")));
        } catch (Exception e) {
             testInfo.get().info("Fingerprint validation button not found");
        }

        //Submit
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/submit")).click();

        //Assert Sim Swap Response
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Sim Swap Response']")));
        TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Authentication Score Card']", "Authentication Score Card");

        TestUtils.testTitle("Mandatory Parametter");
        TestUtils.logScreenshot();

        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity.glo:id/optionalParamsRecyclerView");

        TestUtils.testTitle("Optional Parameter");
        TestUtils.logScreenshot();
        TestUtils.scrollDown();
        TestUtils.logScreenshot();

        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity.glo:id/btnSwapOk");
        TestUtils.scrollDown();
        TestUtils.testTitle("Summary");
        Asserts.AssertSwapSummary();

        //click ok
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/btnSwapOk")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/title_header")));
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity.glo:id/title_header", "Document Upload");

        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/document_image")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/summary")));
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity.glo:id/summary", "SELECT DOCUMENT TYPE");
        TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Proxy Affidavit']", "Proxy Affidavit");
        TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Proxy ID']", "Proxy ID");
        TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Valid ID Card']", "Valid ID Card");
        TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Court Affidavit']", "Court Affidavit");
        TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Company Letter of Authorization']", "Company Letter of Authorization");
        TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Sim Replacement Form']", "Sim Replacement Form");

        //Proxy ID
        TestUtils.testTitle("Proxy ID");
        getDriver().findElement(By.xpath("//android.widget.TextView[@text='Proxy ID']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/alertTitle")));
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity.glo:id/alertTitle", "Sim Swap Document Upload");
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity.glo:id/doc_upload_btn", "DOCUMENT UPLOAD");
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity.glo:id/doc_capture_btn", "DOCUMENT CAPTURE");


        //Upload invalid file
        TestUtils.testTitle("Upload invalid file");
        // Push File
        File pic = new File(System.getProperty("user.dir") + "/files/invalid.png");
        getDriver().pushFile("/storage/emulated/0/invalid.png", pic);

        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/doc_upload_btn")).click();
        Thread.sleep(500);
        TestUtils.scrollUntilElementIsVisible("XPATH", "//android.widget.TextView[@text='invalid.png']");
        getDriver().findElement(By.xpath("//android.widget.TextView[@text='invalid.png']")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/alertTitle")));
        TestUtils.assertSearchText("ID", "android:id/message", "You have selected an invalid file format, file must be in JPG,PDF format");
        getDriver().findElement(By.id("android:id/button1")).click();

        TestUtils.testTitle("Upload Valid Document");
        // Push File
        File pic2 = new File(System.getProperty("user.dir") + "/files/idCard.jpg");
        getDriver().pushFile("/storage/emulated/0/picture.jpg", pic2);
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/document_image")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/summary")));
        getDriver().findElement(By.xpath("//android.widget.TextView[@text='Picture of Sim or Sim Card Holder']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/alertTitle")));
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/doc_upload_btn")).click();
        Thread.sleep(500);
        TestUtils.scrollUntilElementIsVisible("XPATH", "//android.widget.TextView[@text='picture.jpg']");
        getDriver().findElement(By.xpath("//android.widget.TextView[@text='picture.jpg']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/document")));
        TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Proxy ID']", "Proxy ID");

        //Continue without uploading Proxy Affidavit
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/continue_btn")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/text1")));
        TestUtils.assertSearchText("ID", "android:id/text1", "Proxy Affidavit is compulsory");
        getDriver().pressKeyCode(AndroidKeyCode.BACK);

        //Capture Proxy Affidavit
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/document_image")));
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/document_image")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/summary")));
        getDriver().findElement(By.xpath("//android.widget.TextView[@text='Proxy Affidavit']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/alertTitle")));
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/doc_capture_btn")).click();
        Thread.sleep(500);
        getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/captureButton")).click();
        Thread.sleep(500);
        getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/ok")).click();
        Thread.sleep(500);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/continue_btn")));

        TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Proxy Affidavit']", "Proxy Affidavit");

        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/continue_btn")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/message")));
        TestUtils.assertSearchText("ID", "android:id/message", "I, " + valid_username + " acknowledge the submission of the swap request for "+valid_Msisdn);
        getDriver().findElement(By.id("android:id/button1")).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Performing Simswap request']")));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/message")));
        TestUtils.assertSearchText("ID", "android:id/message", "Subscriber information successfully saved.");
        getDriver().findElement(By.id("android:id/button1")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Home']")));

        */
    }
}
