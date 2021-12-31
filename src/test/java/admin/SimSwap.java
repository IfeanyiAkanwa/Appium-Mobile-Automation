package admin;

import com.aventstack.extentreports.Status;
import db.ConnectDB;
import io.appium.java_client.android.AndroidKeyCode;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.Asserts;
import utils.TestBase;
import utils.TestUtils;

import java.io.File;
import java.io.FileReader;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static admin.ReportsTest.navigateToReportsPage;

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
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/button_start_capture")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/reg_type_placeholder")));
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/reg_type_placeholder", "Registration Type");
        Thread.sleep(500);


        // Select SIM Swap
        TestUtils.testTitle("Check that SIM Swap is not selectable");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/typeofreg")));
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/typeofreg")).click();
        Thread.sleep(500);
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/alertTitle", "Select Registration Type");
        getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='SIM Swap']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/alertTitle", "No Privilege");
        TestUtils.assertSearchText("ID", "android:id/message", "You are not allowed to access SIM Swap because you do not have the SIM Swap privilege");
        Thread.sleep(500);
        getDriver().findElement(By.id("android:id/button1")).click();
        Thread.sleep(500);
        getDriver().findElement(By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
        TestUtils.assertSearchText("ID", "android:id/message", "Capture session is currently active, Exit will cause loss of currently captured data! do you wish to cancel current capture session?");
        getDriver().findElement(By.id("android:id/button2")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Home']")));
        Thread.sleep(500);

        //Log out
        getDriver().findElement(By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/design_menu_item_text")));
        TestUtils.scrollDown();
        getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Logout']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/message")));
        TestUtils.assertSearchText("ID", "android:id/message", "   Log out?");
        getDriver().findElement(By.id("android:id/button3")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/otp_login")));
    }

    @Test
    public static void navigateToCaptureMenuTest() {
        WebDriverWait wait = new WebDriverWait(getDriver(), 30);
        // Navigate to Registration Type
        TestUtils.testTitle("Navigate to Registration Type");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Home']")));
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/button_start_capture")).click();
        wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/reg_type_placeholder")));
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/reg_type_placeholder",
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
        //try {
            TestUtils.testTitle("Select LGA of Registration: " + lga);
            getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/lga_of_reg")).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/alertTitle")));
            TestUtils.assertSearchText("ID", "android:id/alertTitle", "LGA of Registration*");
            getDriver().findElement(By.xpath("//android.widget.TextView[@text='" + lga + "']")).click();
            Thread.sleep(500);
        //} catch (Exception e) {
            testInfo.get().info("LGA already selected");
        //}

        // Select Sim Swap
        TestUtils.testTitle("Select SIM Swap");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/typeofreg")));
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/typeofreg")).click();
        Thread.sleep(500);
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/alertTitle", "Select Registration Type");
        getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='SIM Swap']")).click();
        Thread.sleep(500);
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/next_button")).click();

        //Validate OTP if Setting is turend ON for SS
        try {
            TestUtils.testTitle("Validate OTP if it's turned ON in settings");
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/dialog_title")));
            TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/dialog_title", "OTP verification");
            TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/dialog_message", "Enter One Time Password sent to : 080*****430");

            //proceed with invalid OTP
            TestUtils.testTitle("Proceed with invalid OTP: " + invalid_otp);
            getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/user_input_dialog")).clear();
            getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/user_input_dialog")).sendKeys(invalid_otp);

            //Submit
            getDriver().findElement(By.id("android:id/button1")).click();

            //Assert Error Message
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/error_message")));
            TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/error_message", "There is no record with the otp, msisdn combination.");

//			//proceed with expired OTP (Commented out because OTP limit is reached while checking out all conditions)
//			TestUtils.testTitle("Proceed with invalid OTP: "+expired_otp);
//			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/user_input_dialog")).clear();
//			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/user_input_dialog")).sendKeys(expired_otp);
//
//			//Submit
//			getDriver().findElement(By.id("android:id/button1")).click();

            //Assert Error Message
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/error_message")));
            TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/error_message", "There is no record with the otp, msisdn combination.");

            //proceed with valid OTP
            String valid_otp = ConnectDB.getOTP(otp_phone_number);
            TestUtils.testTitle("Proceed with valid OTP: " + valid_otp);
            getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/user_input_dialog")).clear();
            getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/user_input_dialog")).sendKeys(valid_otp);

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
        getDriver().findElement(By.xpath("//android.widget.Button")).click();
        Thread.sleep(500);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
        TestUtils.assertSearchText("ID", "android:id/message", "Please Select SIM Swap Type");
        getDriver().findElement(By.id("android:id/button1")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/title_header")));

        //Select SIM Swap type
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/radio_item_self")).click();

        //Select Subscriber Type
        TestUtils.testTitle("Verify that User Can't proceed without selecting Subscriber type");
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/btnValidate")).click();
        Thread.sleep(500);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
        TestUtils.assertSearchText("ID", "android:id/message", "Please Select a Subscriber Type to proceed");
        getDriver().findElement(By.id("android:id/button1")).click();

        /*
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/subscriberType")));
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/subscriberType")).click();
        Thread.sleep(500);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.CheckedTextView[@text='Prepaid']")));
        TestUtils.assertSearchText("XPATH", "//android.widget.CheckedTextView[@text='Prepaid']", "Prepaid");
        //TestUtils.assertSearchText("XPATH", "//android.widget.CheckedTextView[@text='Postpaid']", "Postpaid");
        TestUtils.assertSearchText("XPATH", "//android.widget.CheckedTextView[@text='Data']", "Data");
        getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Prepaid']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Prepaid']")));*/

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/simType")));
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/simType")).click();
        Thread.sleep(500);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.CheckedTextView[@text='MOBILE']")));
        TestUtils.assertSearchText("XPATH", "//android.widget.CheckedTextView[@text='MOBILE']", "MOBILE");
        TestUtils.assertSearchText("XPATH", "//android.widget.CheckedTextView[@text='DATA']", "DATA");
        getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='MOBILE']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='MOBILE']")));

        //Select Swap Category
        TestUtils.testTitle("Verify that User Can't proceed without selecting Swap Category");
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/btnValidate")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
        TestUtils.assertSearchText("ID", "android:id/message", "Please select a Swap Category to proceed");
        getDriver().findElement(By.id("android:id/button1")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/swapCategory")));

        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/swapCategory")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.CheckedTextView[@text='SIM UPGRADE']")));
        TestUtils.assertSearchText("XPATH", "//android.widget.CheckedTextView[@text='SIM UPGRADE']", "SIM UPGRADE");
        TestUtils.assertSearchText("XPATH", "//android.widget.CheckedTextView[@text='SIM SWAP']", "SIM SWAP");
        getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='SIM SWAP']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='SIM SWAP']")));

        //Confirm Existing MSISDN field
        TestUtils.testTitle("Confirm that Existing MSISDN field exists");
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/existingMsisdnField", "Enter Existing MSISDN*");
        TestUtils.testTitle("Verify that User Can't proceed without Supplying Existing MSISDN");
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/btnValidate")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
        TestUtils.assertSearchText("ID", "android:id/message", "MSISDN cannot be blank");
        getDriver().findElement(By.id("android:id/button1")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/existingMsisdnField")));

        //Enter NON digits
//		TestUtils.testTitle("Enter Invalid Existing MSISDN formart: "+invalid_Msisdn_Format);
//		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/existingMsisdnField")).clear();
//		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/existingMsisdnField")).sendKeys(invalid_Msisdn_Format);
//		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/existingMsisdnField","Enter Existing MSISDN*");

        //Enter Existing MSISDN greater than 11 digits
        TestUtils.testTitle("Enter Existing MSISDN greater than 11 digits: " + msisdn_greater_than_11_digits);
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/existingMsisdnField")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/existingMsisdnField")).sendKeys(msisdn_greater_than_11_digits);
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/existingMsisdnField", msisdn_greater_than_11_digits.substring(0, 11));

        //Enter Existing MSISDN less than 11 digits
        TestUtils.testTitle("Enter Existing MSISDN less than 11 digits: " + msisdn_less_than_11_digits);
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/existingMsisdnField")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/existingMsisdnField")).sendKeys(msisdn_less_than_11_digits);
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/btnValidate")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
        TestUtils.assertSearchText("ID", "android:id/message", "Entered Existing MSISDN is invalid. It must be either 7 digits for fixed or 11 digits for mobile.");
        getDriver().findElement(By.id("android:id/button1")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/existingMsisdnField")));

        //Enter unrecognized Existing MSISDN
        TestUtils.testTitle("Enter unrecognized Existing MSISDN: " + unrecognizedMsisdn);
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/existingMsisdnField")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/existingMsisdnField")).sendKeys(unrecognizedMsisdn);
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/btnValidate")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
        TestUtils.assertSearchText("ID", "android:id/message", "Existing MSISDN: The MSISDN has an unrecognized National Destination Code. Please ensure the MSISDN starts with any of the following NDCs: 0701,0708,0802,0808,0812,0901,0902,0904,0907,0809,0817,0818,0908,0909,0705,0805,0807,0811,0815,0905,0915,0703,0706,0803,0806,0810,0813,0814,0816,0903,0906 and must be 11 digits");
        getDriver().findElement(By.id("android:id/button1")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/existingMsisdnField")));

        //Enter Valid existing MSISDN
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/existingMsisdnField")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/existingMsisdnField")).sendKeys(valid_Msisdn);


        //Confirm New MSISDN field
        TestUtils.testTitle("Confirm that New MSISDN field exists");
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/newMsisdnField", "Enter New MSISDN*");
        TestUtils.testTitle("Verify that User Can't proceed without Supplying New MSISDN");
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/btnValidate")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
        TestUtils.assertSearchText("ID", "android:id/message", "Required Input Field: New MSISDN");
        getDriver().findElement(By.id("android:id/button1")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/newMsisdnField")));

        //Enter NON digits
//		TestUtils.testTitle("Enter Invalid New MSISDN formart: "+invalid_Msisdn_Format);
//		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/newMsisdnField")).clear();
//		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/newMsisdnField")).sendKeys(invalid_Msisdn_Format);
//		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/newMsisdnField","Enter New MSISDN");

        //Enter New MSISDN greater than 11 digits
        TestUtils.testTitle("Enter New MSISDN greater than 11 digits: " + msisdn_greater_than_11_digits);
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/newMsisdnField")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/newMsisdnField")).sendKeys(msisdn_greater_than_11_digits);
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/newMsisdnField", msisdn_greater_than_11_digits.substring(0, 11));

        //Enter unrecognized MSISDN
//		TestUtils.testTitle("Enter unrecognized MSISDN: "+unrecognizedMsisdn);
//		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/newMsisdnField")).clear();
//		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/newMsisdnField")).sendKeys(unrecognizedMsisdn);
//		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/btnValidate")).click();
//		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
//		TestUtils.assertSearchText("ID", "android:id/message","Existing MSISDN: The MSISDN has an unrecognized National Destination Code. Please ensure the MSISDN starts with any of the following NDCs: 0701,0708,0802,0808,0812,0901,0902,0904,0907,0809,0817,0818,0908,0909,0705,0805,0807,0811,0815,0905,0915,0703,0706,0803,0806,0810,0813,0814,0816,0903,0906 and must be 11 digits");
//		getDriver().findElement(By.id("android:id/button1")).click();
//		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/newMsisdnField")));

        //Enter Valid New MSISDN
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/newMsisdnField")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/newMsisdnField")).sendKeys(new_msisdn);


        //Confirm Sim Serial field
        TestUtils.testTitle("Confirm that Sim Serial field exists");
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/simSerialField", "Enter New SIM Serial*");
        TestUtils.testTitle("Verify that User Can't proceed without Supplying New Sim Serial");
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/simSerialField")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/btnValidate")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
        TestUtils.assertSearchText("ID", "android:id/message", "Please Enter New SIM Serial");
        getDriver().findElement(By.id("android:id/button1")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/simSerialField")));

        //Confirm user cannot proceed with invalid SIM Serial
        TestUtils.testTitle("Confirm user cannot proceed with invalid Sim Serial: " + msisdn_greater_than_11_digits);
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/simSerialField")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/simSerialField")).sendKeys(msisdn_greater_than_11_digits);
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/btnValidate")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
        TestUtils.assertSearchText("ID", "android:id/message", "New SIM Serial: SIM Serial format is invalid. SIM Serial should be 19 numbers with 'F' at the end");
        getDriver().findElement(By.id("android:id/button1")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/simSerialField")));

        //RAW SIM checkbox
        TestUtils.testTitle("RAW Sim checkbox Test");
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/rawSimCheckBox", "Raw SIM");
        testInfo.get().info("New MSISDN field before checking Raw Sim Checkbox: " + getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/newMsisdnField")).getText());
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/rawSimCheckBox")).click();
        testInfo.get().info("New MSISDN field after checking Raw Sim Checkbox: " + getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/newMsisdnField")).getText());
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/rawSimCheckBox")).click();

        //Insert Back Valid New MSISDN
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/newMsisdnField")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/newMsisdnField")).sendKeys(new_msisdn);

        //Enter Valid New Sim Serail
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/simSerialField")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/simSerialField")).sendKeys(valid_sim_serial);


        //Confirm that user can't proceed with invalid Old MSISDN
        TestUtils.testTitle("Confirm that user can't proceed with invalid Old MSISDN (" + invalid_Msisdn + ")");
        //Enter invalid existing MSISDN
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/existingMsisdnField")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/existingMsisdnField")).sendKeys(invalid_Msisdn);
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/btnValidate")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
        TestUtils.assertSearchText("ID", "android:id/message", "Old Msisdn : MSISDN is not registered and cannot be used for this use case");
        getDriver().findElement(By.id("android:id/button1")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/existingMsisdnField")));

       /* //Confirm that user can't proceed with Unavailable Old MSISDN
        TestUtils.testTitle("Confirm that user can't proceed with unavailable Old MSISDN (" + unavailable_msisdn + ")");
        //Enter invalid existing MSISDN
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/existingMsisdnField")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/existingMsisdnField")).sendKeys(unavailable_msisdn);
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/btnValidate")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
        TestUtils.assertSearchText("ID", "android:id/message", unavailable_msisdn + " is not available for SIM Swap.");
        getDriver().findElement(By.id("android:id/button1")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/existingMsisdnField")));
        */

        //Confirm that user can't proceed with Blocked Old MSISDN
        TestUtils.testTitle("Confirm that user can't proceed with Blocked Old MSISDN (" + blocked_msisdn + ")");
        //Enter invalid existing MSISDN
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/existingMsisdnField")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/existingMsisdnField")).sendKeys(blocked_msisdn);
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/btnValidate")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
        TestUtils.assertSearchText("ID", "android:id/message", "You cannot proceed with the SIM Swap. SIM Swap request for "+ blocked_msisdn + " has been blocked due to maximum retry. Please Contact Support");
        getDriver().findElement(By.id("android:id/button1")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/existingMsisdnField")));

        //Enter Valid existing MSISDN
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/existingMsisdnField")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/existingMsisdnField")).sendKeys(valid_Msisdn);

        //Confirm that user can't proceed with invalid New MSISDN
        TestUtils.testTitle("Confirm that user can't proceed with invalid New MSISDN (" + invalid_new_Msisdn + ")");
        //Enter invalid existing MSISDN
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/newMsisdnField")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/newMsisdnField")).sendKeys(invalid_new_Msisdn);
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/btnValidate")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
        TestUtils.assertSearchText("ID", "android:id/message", "New Msisdn : Target MSISDN is not available for SIM Swap. Kindly use another MSISDN");
        getDriver().findElement(By.id("android:id/button1")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/newMsisdnField")));

        //Insert Back Valid New MSISDN
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/newMsisdnField")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/newMsisdnField")).sendKeys(new_msisdn);

        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/btnValidate")).click();

        try{
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/button1")));
            getDriver().findElement(By.id("android:id/button1")).click();
        }catch (Exception e1){

        }

        //Check for Approve Sim Swap Validation
        try {
            TestUtils.testTitle("Approve Sim Swap Validation");
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
            getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/approve_username")).clear();
            getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/approve_username")).sendKeys(approver_username);
            getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/approve_password")).clear();
            getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/approve_password")).sendKeys(approver_password);
            getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/approve")).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
            TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/alertTitle", "Approval Status");
            TestUtils.assertSearchText("ID", "android:id/message", "Successfully approved!");
            getDriver().findElement(By.xpath("//android.widget.Button[@text='OK']")).click();
        } catch (Exception e) {
            testInfo.get().info("Approve Validation view not displayed");

        }
        /*//Confirm There is no otp verification when SIM Swap is selected
        TestUtils.testTitle("Confirm There is no otp verification when SIM Swap is selected");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/alertTitle", "NIN Verification");*/

        //Confirm OTP Verification when Sim Upgrade is selected
        TestUtils.testTitle("Confirm OTP Verification when Sim Upgrade is selected");

        //Go back
        //getDriver().pressKeyCode(AndroidKeyCode.BACK);
        Thread.sleep(5000);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/swapCategory")));
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/swapCategory")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.CheckedTextView[@text='SIM UPGRADE']")));
        getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='SIM UPGRADE']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='SIM UPGRADE']")));

        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/btnValidate")).click();

        validateOtp(otp_phone_number);

        //Check for Approve Sim Swap Validation
        try {
            TestUtils.testTitle("Approve Sim Swap Validation");
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
            getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/approve_username")).clear();
            getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/approve_username")).sendKeys(approver_username);
            getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/approve_password")).clear();
            getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/approve_password")).sendKeys(approver_password);
            getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/approve")).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
            TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/alertTitle", "Approval Status");
            TestUtils.assertSearchText("ID", "android:id/message", "Successfully approved!");
            getDriver().findElement(By.id("android:id/button1")).click();
        } catch (Exception e) {
            testInfo.get().info("Approve Validation view not displayed");
        }

        //BioMetrics Verification
        verifyBioMetricsTest();

        try{
            //check for fingerprint warnings
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/button1")));
            getDriver().findElement(By.id("android:id/button1")).click();
        }catch(Exception e){

        }

        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/btnNext")).click();

        //Verify NIN
        TestBase.verifyNINTest(nin, "Search By NIN");

    }

    private void validateOtp(String otp_phone_number) throws SQLException, InterruptedException {
        WebDriverWait wait = new WebDriverWait(getDriver(), 30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/dialog_title")));

        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/dialog_title", "OTP verification");

        String otp = ConnectDB.getOTP(otp_phone_number);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/dialog_title")));
        TestUtils.testTitle("Valid OTP Test: " + otp);
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/user_input_dialog")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/user_input_dialog")).sendKeys(otp);
        getDriver().findElement(By.id("android:id/button1")).click();
        Thread.sleep(2000);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/button1")));
        TestUtils.assertSearchText("ID", "android:id/message", "The specified otp and msisdn combinations are valid.");
        getDriver().findElement(By.id("android:id/button1")).click();
        try{
            //check for warnings
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/button1")));
            getDriver().findElement(By.id("android:id/button1")).click();
        }catch(Exception e){

        }
    }

    @Parameters({"dataEnv"})
    @Test
    public void captureMobileSimUpgrade(String dataEnv) throws Exception {

        WebDriverWait wait = new WebDriverWait(getDriver(), 60);
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
        String nin = (String) envs.get("nin");
        String pp_nin = (String) envs.get("pp_nin");
        String email = (String) envs.get("agent_email");
        String serial = (String) envs.get("serial");
        String alternate_phone = (String) envs.get("alternate_phone");
        String kmUserId = (String) envs.get("kmUserId");

        String inavlidPostFix = "NO";
        String new_msisdn = (String) envs.get("new_msisdn");
        String valid_sim_serial = (String) envs.get("valid_sim_serial");

       /* //Capture Subscriber face
        TestUtils.testTitle("Capture Subscriber Face");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/title_header")));
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/title_header", "Face Capture");
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/button_next_action")).click();
        Thread.sleep(500);
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/captureButton")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/faceView")));
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/preview", "Preview");
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/button_next_action")).click();
        Thread.sleep(500);*/

        //Demographics View
        TestUtils.testTitle("Supply Basic Information");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/title_header")));
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/title_header", "Basic Information");

        //Submit with empty details
        TestUtils.testTitle("Proceed without supplying details");
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/surname")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/firstnameTXT")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/maidenNameTxt")).clear();
        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/submit");
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/submit")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
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
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/firstnameTXT")));

        //Enter invalid details
        TestUtils.testTitle("Enter Non-Matching Details");
        //first name
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/firstnameTXT")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/firstnameTXT")).sendKeys(fName+inavlidPostFix);

        //last name
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/surname")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/surname")).sendKeys(lName+inavlidPostFix);

        //Mothers  maiden name
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/maidenNameTxt")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/maidenNameTxt")).sendKeys(mmn+inavlidPostFix);

        //scroll down
        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/male");

        //gender
        if (gender.equalsIgnoreCase("Male")) {
            getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/male")).click();
        } else {
            getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/female")).click();
        }

        //scroll down
        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/dateofBirthTXT");

        //Date of birth
        String invalidDob="1920-03-06";
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/dateofBirthTXT")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/dateofBirthTXT")).sendKeys(invalidDob);

        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/occupationSpinner");
        //occupation
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/occupationSpinner")));
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/occupationSpinner")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Select Item']")));
        getDriver().findElement(By.xpath("//android.widget.TextView[@text='" + occupation + "']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.CheckedTextView[@text='" + occupation + "']")));

        //scroll down
        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/alternatePhoneTXT");

        //Alternate Phone Number
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/alternatePhoneTXT")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/alternatePhoneTXT")).sendKeys(alternate_phone);

        //scroll down
        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/residentialAddress");

        //Residential Address
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/residentialAddress")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/residentialAddress")).sendKeys(address+inavlidPostFix);

        //scroll down
        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/countrySpinner");
        //Country
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/countrySpinner")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='[Select Nationality]*']")));
        getDriver().findElement(By.xpath("//android.widget.TextView[@text='" + nationality + "']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='" + nationality + "']")));

        //scroll down
        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/stateOfOriginSpinner");

        //State of origin
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/stateOfOriginSpinner")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='[Select State of Origin]']")));
        getDriver().findElement(By.xpath("//android.widget.TextView[@text='" + state + "']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='" + state + "']")));

        //scroll down
        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/serial");
        //SIM Serial
        String invalidSerial="1234550710222181471F";
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/serial")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/serial")).sendKeys(invalidSerial);

        //scroll down
        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/puk");
        //PUK
        String invalidPuk="52653000";
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/puk")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/puk")).sendKeys(invalidPuk);

        //scroll down
        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/activationYear");

        //Activation Year
        String invalidYear="1220";
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/activationYear")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/activationYear")).sendKeys(invalidYear);

        //scroll down
        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/lastRechargeAmt");

        //Last Recharge Amount
        String invalidAmount="0";
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/lastRechargeAmt")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/lastRechargeAmt")).sendKeys(invalidAmount);

        //scroll down
        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/lastRechargeDate");

        //Last Recharge Date
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/lastRechargeDate")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/lastRechargeDate")).sendKeys(invalidDob);

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
            TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/capturePrint", "CAPTURE FINGER PRINT");
            getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/capturePrint")).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
            TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/alertTitle", "Scanner not found");
            TestUtils.assertSearchText("ID", "android:id/message", "Fingerprint scanner device not detected. Ensure your scanner device is connected and try again.");
            getDriver().findElement(By.id("android:id/button1")).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/fingerType_text")));
            //Go back
            getDriver().pressKeyCode(AndroidKeyCode.BACK);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/submit")));
        } catch (Exception e) {
            testInfo.get().info("Fingerprint validation button not found");
        }

        //scroll down
        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/submit");
        TestUtils.scrollDown();
        TestUtils.scrollDown();
        //Submit
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/submit")).click();

        //proceed without selecting affidavit box

        //TestUtils.scrollUntilElementIsVisible("ID", "android:id/text1");

        try {
            TestUtils.assertSearchText("ID", "android:id/text1", "Ensure that the Affidavit check box is checked");
            getDriver().findElement(By.id("android:id/button1")).click();

            getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/check_box_affidavit")).click();

            //Submit
            getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/submit")).click();

        }catch(Exception e){

        }



        //Assert Sim Swap Response
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='SIM Swap Response']")));
        TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Authentication Score Card']", "Authentication Score Card");

        TestUtils.testTitle("Mandatory Parameter");
        testInfo.get().info("Check Screenshot for report");
        TestUtils.logScreenshot();

        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/optionalParamsRecyclerView");

        TestUtils.testTitle("Optional Parameter");
        testInfo.get().info("Check Screenshot for report");
        TestUtils.logScreenshot();
        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/mandatory_checks_status");
        TestUtils.logScreenshot();

        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/btnSwapOk");
        TestUtils.testTitle("Summary");
        Thread.sleep(1000);
        Asserts.AssertSwapSummary();

        //click ok
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/btnSwapOk")).click();

        try {
            TestUtils.assertSearchText("ID", "android:id/message", "There is a Demographic mismatch, ensure that the Update Consent check box is checked");
            getDriver().findElement(By.id("android:id/button1")).click();
            getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/check_box_demographics")).click();
            //click ok
            getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/btnSwapOk")).click();
        }catch (Exception e){
            getDriver().pressKeyCode(AndroidKeyCode.BACK);
        }

        //Proceed with valid details
        TestUtils.testTitle("Proceed with matching details");

        TestUtils.scrollUp2();
        TestUtils.scrollUp2();
        TestUtils.scrollUp2();

        TestUtils.scrollUp2();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/firstnameTXT")));
        //first name
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/firstnameTXT")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/firstnameTXT")).sendKeys(fName);

        //last name
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/surname")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/surname")).sendKeys(lName);

        //Mothers  maiden name
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/maidenNameTxt")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/maidenNameTxt")).sendKeys(mmn);

        //scroll down
        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/male");

        //gender
        if (gender.equalsIgnoreCase("Male")) {
            getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/male")).click();
        } else {
            getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/female")).click();
        }

        //scroll down
        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/dateofBirthTXT");

        //Date of birth
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/dateofBirthTXT")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/dateofBirthTXT")).sendKeys(dob);

        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/occupationSpinner");
        //occupation
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/occupationSpinner")));
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/occupationSpinner")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Select Item']")));
        getDriver().findElement(By.xpath("//android.widget.TextView[@text='" + occupation + "']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.CheckedTextView[@text='" + occupation + "']")));

        //scroll down
        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/alternatePhoneTXT");

        //Alternate Phone Number
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/alternatePhoneTXT")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/alternatePhoneTXT")).sendKeys(alternate_phone);

        //scroll down
        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/residentialAddress");

        //Residential Address
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/residentialAddress")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/residentialAddress")).sendKeys(address);

        //scroll down
        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/countrySpinner");
        //Country
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/countrySpinner")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='[Select Nationality]*']")));
        getDriver().findElement(By.xpath("//android.widget.TextView[@text='" + nationality + "']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='" + nationality + "']")));

        //scroll down
        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/stateOfOriginSpinner");

        //State of origin
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/stateOfOriginSpinner")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='[Select State of Origin]']")));
        getDriver().findElement(By.xpath("//android.widget.TextView[@text='" + state + "']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='" + state + "']")));

        //scroll down
        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/serial");
        //SIM Serial
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/serial")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/serial")).sendKeys(serial);

        //scroll down
        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/puk");
        //PUK
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/puk")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/puk")).sendKeys(puk);

        //scroll down
        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/activationYear");

        //Activation Year
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/activationYear")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/activationYear")).sendKeys(activation_year);

        //scroll down
        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/lastRechargeAmt");

        //Last Recharge Amount
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/lastRechargeAmt")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/lastRechargeAmt")).sendKeys(last_recharge_Amount);

        //scroll down
        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/lastRechargeDate");

        //Last Recharge Date
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/lastRechargeDate")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/lastRechargeDate")).sendKeys(last_recharge_date);

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
            TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/capturePrint", "CAPTURE FINGER PRINT");
            getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/capturePrint")).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
            TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/alertTitle", "Scanner not found");
            TestUtils.assertSearchText("ID", "android:id/message", "Fingerprint scanner device not detected. Ensure your scanner device is connected and try again.");
            getDriver().findElement(By.id("android:id/button1")).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/fingerType_text")));
            //Go back
            getDriver().pressKeyCode(AndroidKeyCode.BACK);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/submit")));
        } catch (Exception e) {
            testInfo.get().info("Fingerprint validation button not found");
        }

        //scroll down
        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/submit");

        //Submit
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/submit")).click();

        try {
            //proceed without selecting affidavit box
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/button1")));
            TestUtils.assertSearchText("ID", "android:id/text1", "Ensure that the Affidavit check box is checked");
            getDriver().findElement(By.id("android:id/button1")).click();
            getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/check_box_affidavit")).click();

            //Submit
            getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/submit")).click();
        }catch (Exception e){

        }

        //Assert Sim Swap Response
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='SIM Swap Response']")));
        TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Authentication Score Card']", "Authentication Score Card");

        TestUtils.testTitle("Mandatory Parameter");
        testInfo.get().info("Check Screenshot for report");
        TestUtils.logScreenshot();

        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/optionalParamsRecyclerView");

        TestUtils.testTitle("Optional Parameter");
        testInfo.get().info("Check Screenshot for report");
        TestUtils.logScreenshot();
        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/btnSwapOk");
        TestUtils.logScreenshot();

        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/btnSwapOk");
        TestUtils.testTitle("Summary");
        Asserts.AssertSwapSummary();

        //click ok
        Thread.sleep(1000);
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/check_box_demographics")).click();
        Thread.sleep(1000);
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/btnSwapOk")).click();


        Thread.sleep(1000);
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/title_header", "Subscriber Data Preview");
        Thread.sleep(1000);
        Asserts.AssertSwapDataPreview( "SELF",  "SIM UPGRADE",  "PREPAID",  fName,  lName,  mmn,  gender,  dob,  alternate_phone,  serial,  activation_year,  valid_Msisdn,  new_msisdn,  valid_sim_serial,  puk,  pp_nin,  email,  address,  occupation,  nationality,  state);
        Thread.sleep(1000);
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/btn_proceed")).click();

        Thread.sleep(1000);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/title_header")));
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/title_header", "Document Upload");

        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/document_image")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/summary")));
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/summary", "SELECT DOCUMENT TYPE");
        TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Picture of Sim or Sim Card Holder']", "Picture of Sim or Sim Card Holder");
        TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Valid ID Card']", "Valid ID Card");

        //Picture of Sim or Sim cardholder
        TestUtils.testTitle("Picture of Sim or Sim Card Holder");
        getDriver().findElement(By.xpath("//android.widget.TextView[@text='Picture of Sim or Sim Card Holder']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/alertTitle", "SIM Swap Document Upload");
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/doc_upload_btn", "DOCUMENT UPLOAD");
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/doc_capture_btn", "DOCUMENT CAPTURE");


        //Upload invalid file
        TestUtils.testTitle("Upload invalid file");
        // Push File
        File pic = new File(System.getProperty("user.dir") + "/files/invalid.png");
        getDriver().pushFile("/storage/emulated/0/invalid.png", pic);

        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/doc_upload_btn")).click();
        Thread.sleep(500);
        TestUtils.scrollUntilElementIsVisible("XPATH", "//android.widget.TextView[@text='invalid.png']");
        getDriver().findElement(By.xpath("//android.widget.TextView[@text='invalid.png']")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
        TestUtils.assertSearchText("ID", "android:id/message", "You have selected an invalid file format, file must be in JPG,PDF format");
        getDriver().findElement(By.id("android:id/button1")).click();

        TestUtils.testTitle("Upload Valid Document");
        // Push File
        File pic2 = new File(System.getProperty("user.dir") + "/files/idCard.jpg");
        getDriver().pushFile("/storage/emulated/0/picture.jpg", pic2);
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/document_image")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/summary")));
        getDriver().findElement(By.xpath("//android.widget.TextView[@text='Picture of Sim or Sim Card Holder']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/doc_upload_btn")).click();
        Thread.sleep(500);
        TestUtils.scrollUntilElementIsVisible("XPATH", "//android.widget.TextView[@text='picture.jpg']");
        getDriver().findElement(By.xpath("//android.widget.TextView[@text='picture.jpg']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/document")));
        TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Picture of Sim or Sim Card Holder']", "Picture of Sim or Sim Card Holder");

        //Remove picture and Capture Document
        TestUtils.testTitle("Remove uploaded document and Capture Document");
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/document")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/message")));
        TestUtils.assertSearchText("ID", "android:id/message", "Do you want to delete uploaded document");
        getDriver().findElement(By.id("android:id/button1")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/document_image")));
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/document_image")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/summary")));
        getDriver().findElement(By.xpath("//android.widget.TextView[@text='Valid ID Card']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/doc_capture_btn")).click();
        Thread.sleep(500);
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/captureButton")).click();
        Thread.sleep(1000);
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/ok")).click();
        Thread.sleep(500);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/continue_btn")));
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/continue_btn")).click();

        /*wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/message")));
        TestUtils.assertSearchText("ID", "android:id/message", "I, " + valid_username + " acknowledge the submission of the swap request for "+valid_Msisdn);
        getDriver().findElement(By.id("android:id/button1")).click();*/

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/message")));
        TestUtils.assertSearchText("ID", "android:id/message", "Please confirm Submission of SIM Swap along with SIM Registration Update for MSISDN "+valid_Msisdn+".");
        getDriver().findElement(By.id("android:id/button1")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/btn_okay")));

        try {
            TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/tv_fail_heading", "Request Failed");
            TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/tv_message", "Duplicate record.");

            TestUtils.testTitle("Unblock Swap after successful swap");
            String swapID=ConnectDB.getSwapID(valid_Msisdn);
            JSONObject unBlockPayLoad = new JSONObject();
            unBlockPayLoad.put("kmUserId", kmUserId);
            unBlockPayLoad.put("swapId", swapID);
            unBlockPayLoad.put("processType", "APPROVED");
            unBlockPayLoad.put("feedback", "Unblock");
            //Level q unblock
            String response = TestUtils.blockActionApiCall(dataEnv, unBlockPayLoad);
            TestUtils.blockActionApiCall(dataEnv, unBlockPayLoad);
            testInfo.get().info(response);
            getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/btn_okay")).click();

            //Go back
            getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/exitSimSwap")).click();
            getDriver().findElement(By.id("android:id/button1")).click();

            //Go back
            getDriver().findElement(By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']")).click();
            getDriver().findElement(By.id("android:id/button2")).click();

        }catch (Exception e){

            TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/tv_message", "Subscriber information successfully saved.");
            TestUtils.testTitle("Reject Swap after successful swap submission");
            String swapID=ConnectDB.getSwapID(valid_Msisdn);
            JSONObject rejectSwapPayLoad = new JSONObject();
            rejectSwapPayLoad.put("kmUserId", kmUserId);
            rejectSwapPayLoad.put("swapId", swapID);
            rejectSwapPayLoad.put("processType", "FAILED_CHECK");
            rejectSwapPayLoad.put("feedback", "Reject");

            String response = TestUtils.rejectSwapApiCall(dataEnv, rejectSwapPayLoad);
            testInfo.get().info(response);

            getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/btn_okay")).click();

            try {
                //getDriver().findElement(By.id("android:id/button3")).click();
                getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/experience_type")).click();
                getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Network Speed']")).click();
                getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/rating_ratingBar")).click();
                getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/rating_btn_dialog_positive")).click();
                TestUtils.assertSearchText("ID", "android:id/alertTitle", "Feedback sent");
                getDriver().pressKeyCode(AndroidKeyCode.BACK);
            } catch (Exception e1) {

            }
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Home']")));
        }

    }


    @Parameters({"dataEnv"})
    @Test
    public void capturePrepaidProxySimSwap(String dataEnv) throws Exception {

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
        String kmUserId = (String) envs.get("kmUserId");
        String state = (String) envs.get("pp_state");
        String gender = (String) envs.get("pp_gender");
        String pp_last_recharge_Amount = (String) envs.get("pp_last_recharge_Amount");
        String activation_year = (String) envs.get("pp_activation_year");
        String pp_last_recharge_date = (String) envs.get("pp_last_recharge_date");
        String fdn1 = (String) envs.get("pp_fdn1");
        String fdn2 = (String) envs.get("pp_fdn2");
        String fdn3 = (String) envs.get("pp_fdn3");
        String fdn4 = (String) envs.get("pp_fdn4");
        String fdn5 = (String) envs.get("pp_fdn5");
        String nin = (String) envs.get("nin");
        String pp_nin = (String) envs.get("pp_nin");
        String proxy_name = (String) envs.get("proxy_name");
        String proxy_phone = (String) envs.get("proxy_phone");
        String last_invoice_amount = (String) envs.get("pp_last_invoice_amount");
        String last_invoice_date = (String) envs.get("pp_last_invoice_date");
        String puk = (String) envs.get("pp_puk");
        String serial = (String) envs.get("pp_serial");
        String alternate_phone = (String) envs.get("alternate_phone");
        String otp_phone_number = (String) envs.get("otp_phone_number");

        navigateToCaptureMenuTest();
        navigateToSimSwapViewTest(dataEnv);


        //Select SIM Swap type
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/radio_item_proxl")).click();

        /*//Select Subscriber Type
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/subscriberType")).click();
        getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Prepaid']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Prepaid']")));*/
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/simType")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.CheckedTextView[@text='MOBILE']")));
        getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='MOBILE']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='MOBILE']")));
        //Select Swap Category
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/swapCategory")).click();
        try{
            Thread.sleep(1500);
            getDriver().findElement(By.xpath("//android.widget.TextView[@text='SIM SWAP']")).click();
        }catch (Exception e){
            getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='SIM SWAP']")).click();
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='SIM SWAP']")));

        //Enter Valid existing MSISDN
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/existingMsisdnField")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/existingMsisdnField")).sendKeys(valid_Msisdn);


        //Enter Valid New MSISDN
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/newMsisdnField")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/newMsisdnField")).sendKeys(new_msisdn);

        //Enter Valid New Sim Serial
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/simSerialField")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/simSerialField")).sendKeys(valid_sim_serial);

        //Enter Valid Proxy MSISDN
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/proxyMsisdnField")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/proxyMsisdnField")).sendKeys(proxy_phone);


        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/btnValidate")).click();

        //Check for Approve Sim Swap Validation
        try {
            TestUtils.testTitle("Approve Sim Swap Validation");
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
            getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/approve_username")).clear();
            getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/approve_username")).sendKeys(approver_username);
            getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/approve_password")).clear();
            getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/approve_password")).sendKeys(approver_password);
            getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/approve")).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
            TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/alertTitle", "Approval Status");
            TestUtils.assertSearchText("ID", "android:id/message", "Successfully approved!");
            getDriver().findElement(By.xpath("//android.widget.Button[@text='OK']")).click();
        } catch (Exception e) {
            testInfo.get().info("SIM Swap validation view not displayed");
        }

        try{
            //check for warnings
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/button1")));
            getDriver().findElement(By.id("android:id/button1")).click();
        }catch(Exception e){

        }

        /*//Confirm There is no otp verification when SIM Swap is selected
        TestUtils.testTitle("Confirm There is no otp verification when SIM Swap is selected");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/alertTitle", "NIN Verification");*/


        //BioMetrics Verification
        verifyBioMetricsTest();

        try{
            //check for fingerprint warnings
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/button1")));
            getDriver().findElement(By.id("android:id/button1")).click();
        }catch(Exception e){

        }
        validateOtp(proxy_phone);

        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/btnNext")).click();

        //Verify Proxy NIN
        verifyProxyNINTest(nin, "Search By NIN");

        //Verify Subscriber NIN
        verifySubscriberNINTest(pp_nin, "Search By NIN");

        /*//Capture Subscriber face
        TestUtils.testTitle("Capture Subscriber Face");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/title_header")));
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/title_header", "Face Capture");
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/button_next_action")).click();
        Thread.sleep(500);
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/captureButton")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/faceView")));
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/preview", "Preview");
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/button_next_action")).click();
        Thread.sleep(500);*/

        //Demographics View
        TestUtils.testTitle("Supply Basic Information");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/title_header")));
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/title_header", "Basic Information");

        //Submit with empty details
        TestUtils.scrollByID("com.sf.biocapture.activity" + Id + ":id/submit", 0);
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/submit")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
        try {
            TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Proxy Name is empty']", "Proxy Name is empty");
            TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Proxy Phone Number is empty']", "Proxy Phone Number is empty");
            TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Proxy Phone Number should be the same as that inputted during MSISDN Validation']", "Proxy Phone Number should be the same as that inputted during MSISDN Validation");
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
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/firstnameTXT")));

        //scroll down
        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/stateOfOriginSpinner");

        //State of origin
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/stateOfOriginSpinner")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='[Select State of Origin]']")));
        getDriver().findElement(By.id("android:id/search_src_text")).clear();
        getDriver().findElement(By.id("android:id/search_src_text")).sendKeys(state);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='" + state + "']")));
        getDriver().findElement(By.xpath("//android.widget.TextView[@text='" + state + "']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='" + state + "']")));

        //scroll down
        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/serial");
        //SIM Serial
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/serial")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/serial")).sendKeys(serial);

        //scroll down
        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/puk");
        //PUK
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/puk")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/puk")).sendKeys(puk);

        //scroll down
        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/proxyNameTXT");

        //Proxy Name
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/proxyNameTXT")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/proxyNameTXT")).sendKeys(proxy_name);

        //scroll down
        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/proxyPhoneNoTXT");

        //Proxy Phone
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/proxyPhoneNoTXT")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/proxyPhoneNoTXT")).sendKeys(proxy_phone);

        try {
            //Last invoice amount
            getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/lastInvoiceAmt")).clear();
            getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/lastInvoiceAmt")).sendKeys(last_invoice_amount);

            //Last invoice date
            getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/lastInvoiceDate")).clear();
            getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/lastInvoiceDate")).sendKeys(last_invoice_date);
        }catch(Exception e){
            //scroll down
            TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/lastRechargeAmt");

            //Last Recharge Amount
            getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/lastRechargeAmt")).clear();
            getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/lastRechargeAmt")).sendKeys(pp_last_recharge_Amount);

            //scroll down
            TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/lastRechargeDate");

            //Last Recharge Date
            getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/lastRechargeDate")).clear();
            getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/lastRechargeDate")).sendKeys(pp_last_recharge_date);

        }

        //scroll down
        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/activationYear");

        //Activation Year
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/activationYear")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/activationYear")).sendKeys(activation_year);


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

        //Check for fingerprint Capture Button
        try {
            TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/capturePrint", "CAPTURE FINGER PRINT");
            getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/capturePrint")).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
            TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/alertTitle", "Scanner not found");
            TestUtils.assertSearchText("ID", "android:id/message", "Fingerprint scanner device not detected. Ensure your scanner device is connected and try again.");
            getDriver().findElement(By.id("android:id/button1")).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/fingerType_text")));
            //Go back
            getDriver().pressKeyCode(AndroidKeyCode.BACK);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/submit")));
        } catch (Exception e) {
            testInfo.get().info("Fingerprint validation button not found");
        }

        //scroll down
        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/submit");

        //Submit
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/submit")).click();

        try {
            //proceed without selecting affidavit box
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/button1")));
            TestUtils.assertSearchText("ID", "android:id/text1", "Ensure that the Affidavit check box is checked");
            getDriver().findElement(By.id("android:id/button1")).click();
            getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/check_box_affidavit")).click();

            //Submit
            getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/submit")).click();
        }catch (Exception e){

        }

        //Assert Sim Swap Response
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='SIM Swap Response']")));
        TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Authentication Score Card']", "Authentication Score Card");

        TestUtils.testTitle("Mandatory Parameter");
        testInfo.get().info("Check Screenshot for report");
        TestUtils.logScreenshot();
        Thread.sleep(1000);
        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/optionalParamsRecyclerView");

        TestUtils.testTitle("Optional Parameter");
        testInfo.get().info("Check Screenshot for report");
        TestUtils.logScreenshot();


        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/btnSwapOk");
        TestUtils.logScreenshot();

        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/btnSwapOk");
        TestUtils.scrollDown();
        TestUtils.testTitle("Summary");
        Asserts.AssertSwapSummary();

        //click ok
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/btnSwapOk")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/title_header")));
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/title_header", "Document Upload");

        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/document_image")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/summary")));
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/summary", "SELECT DOCUMENT TYPE");
        TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Proxy Affidavit']", "Proxy Affidavit");
        TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Proxy ID']", "Proxy ID");
        TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Valid ID Card']", "Valid ID Card");
        TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='SIM Certificate or Affidavit']", "SIM Certificate or Affidavit");
        //TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Court Affidavit']", "Court Affidavit");
        //TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Company Letter of Authorization']", "Company Letter of Authorization");
        TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='SIM Replacement Form']", "SIM Replacement Form");

        //Proxy ID
        TestUtils.testTitle("Proxy ID");
        getDriver().findElement(By.xpath("//android.widget.TextView[@text='Proxy ID']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/alertTitle", "SIM Swap Document Upload");
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/doc_upload_btn", "DOCUMENT UPLOAD");
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/doc_capture_btn", "DOCUMENT CAPTURE");


        //Upload invalid file
        TestUtils.testTitle("Upload invalid file");
        // Push File
        File pic = new File(System.getProperty("user.dir") + "/files/invalid.png");
        getDriver().pushFile("/storage/emulated/0/invalid.png", pic);

        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/doc_upload_btn")).click();
        Thread.sleep(500);
        TestUtils.scrollUntilElementIsVisible("XPATH", "//android.widget.TextView[@text='invalid.png']");
        getDriver().findElement(By.xpath("//android.widget.TextView[@text='invalid.png']")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/message")));
        TestUtils.assertSearchText("ID", "android:id/message", "You have selected an invalid file format, file must be in JPG,PDF format");
        getDriver().findElement(By.id("android:id/button1")).click();

        TestUtils.testTitle("Upload Valid Document");
        // Push File
        File pic2 = new File(System.getProperty("user.dir") + "/files/idCard.jpg");
        getDriver().pushFile("/storage/emulated/0/picture.jpg", pic2);
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/document_image")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/summary")));
        getDriver().findElement(By.xpath("//android.widget.TextView[@text='Proxy ID']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/doc_upload_btn")).click();
        Thread.sleep(500);
        TestUtils.scrollUntilElementIsVisible("XPATH", "//android.widget.TextView[@text='picture.jpg']");
        getDriver().findElement(By.xpath("//android.widget.TextView[@text='picture.jpg']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/document")));
        TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Proxy ID']", "Proxy ID");

        //Continue without uploading Proxy Affidavit
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/continue_btn")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/text1")));
        TestUtils.assertSearchText("ID", "android:id/text1", "Proxy Affidavit is compulsory");
        getDriver().pressKeyCode(AndroidKeyCode.BACK);

        //Capture Proxy Affidavit
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/document_image")));
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/document_image")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/summary")));
        getDriver().findElement(By.xpath("//android.widget.TextView[@text='Proxy Affidavit']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/doc_capture_btn")).click();
        Thread.sleep(500);
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/captureButton")).click();
        Thread.sleep(500);
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/ok")).click();
        Thread.sleep(500);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/continue_btn")));

        TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Proxy Affidavit']", "Proxy Affidavit");

        //Capture SIM Certificate or Affidavit
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/document_image")));
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/document_image")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/summary")));
        getDriver().findElement(By.xpath("//android.widget.TextView[@text='SIM Certificate or Affidavit']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/doc_capture_btn")).click();
        Thread.sleep(500);
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/captureButton")).click();
        Thread.sleep(500);
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/ok")).click();
        Thread.sleep(500);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/continue_btn")));

        TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='SIM Certificate or Affidavit']", "SIM Certificate or Affidavit");

        //Capture Valid ID Card
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/document_image")));
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/document_image")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/summary")));
        getDriver().findElement(By.xpath("//android.widget.TextView[@text='Valid ID Card']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/doc_capture_btn")).click();
        Thread.sleep(500);
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/captureButton")).click();
        Thread.sleep(500);
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/ok")).click();
        Thread.sleep(500);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/continue_btn")));

        TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Valid ID Card']", "Valid ID Card");

        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/continue_btn")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/message")));
        TestUtils.assertSearchText("ID", "android:id/message", "Please confirm Submission of SIM Swap for MSISDN "+valid_Msisdn+" to be verified against existing SIM Registration Details.");
        getDriver().findElement(By.id("android:id/button1")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/btn_okay")));

        try {
            TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/tv_fail_heading", "Request Failed");
            TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/tv_message", "Duplicate record.");

            TestUtils.testTitle("Unblock Swap after successful swap");
            String swapID=ConnectDB.getSwapID(valid_Msisdn);
            JSONObject unBlockPayLoad = new JSONObject();
            unBlockPayLoad.put("kmUserId", kmUserId);
            unBlockPayLoad.put("swapId", swapID);
            unBlockPayLoad.put("processType", "APPROVED");
            unBlockPayLoad.put("feedback", "Unblock");

            String response = TestUtils.blockActionApiCall(dataEnv, unBlockPayLoad);
            TestUtils.blockActionApiCall(dataEnv, unBlockPayLoad);
            testInfo.get().info(response);

            //DB checks
            String uniqueId=ConnectDB.selectQueryOnTable("bfp_sync_log", "msisdn", new_msisdn, "unique_id");
            ConnectDB.query( uniqueId, dataEnv, "RR");

            getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/btn_okay")).click();

            //Go back
            getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/exitSimSwap")).click();
            getDriver().findElement(By.id("android:id/button1")).click();

            //Go back
            getDriver().findElement(By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']")).click();
            getDriver().findElement(By.id("android:id/button2")).click();

        }catch (Exception e){
            TestUtils.testTitle("Reject Swap after successful swap submission");
            String swapID=ConnectDB.getSwapID(valid_Msisdn);
            JSONObject rejectSwapPayLoad = new JSONObject();
            rejectSwapPayLoad.put("kmUserId", kmUserId);
            rejectSwapPayLoad.put("swapId", swapID);
            rejectSwapPayLoad.put("processType", "FAILED_CHECK");
            rejectSwapPayLoad.put("feedback", "Reject");

            String response = TestUtils.rejectSwapApiCall(dataEnv, rejectSwapPayLoad);
            testInfo.get().info(response);
            TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/tv_message", "Subscriber information successfully saved.");
            getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/btn_okay")).click();

            try {
                //getDriver().findElement(By.id("android:id/button3")).click();
                getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/experience_type")).click();
                getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Network Speed']")).click();
                getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/rating_ratingBar")).click();
                getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/rating_btn_dialog_positive")).click();
                TestUtils.assertSearchText("ID", "android:id/alertTitle", "Feedback sent");
                getDriver().pressKeyCode(AndroidKeyCode.BACK);
            } catch (Exception e1) {

            }
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Home']")));
        }

    }


    @Test
    public static void verifyBioMetricsTest() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(getDriver(), 30);

        //scroll down
        TestUtils.scrollDown();

        //Proceed
        Thread.sleep(2000);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/btn_capture")));
        Thread.sleep(2000);
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/btn_capture")).click();


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

            //Save enrollment
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/fp_save_enrolment")));
            getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/fp_save_enrolment")).click();
        }catch (Exception e){
            //Fingerprint matching unsuccessful. You will be allowed to proceed to the next verification option.
        }

    }

    @Test
    public static int verifyProxyNINTest(String nin, String ninVerificationMode) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(getDriver(), 20);
        //ninStatus is set to available automatically
        int ninStatus=1;
        //Proceed to NIN Verification View
        TestUtils.testTitle("Select NIN Verification Mode: "+ninVerificationMode);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/alertTitle", "Proxy's NIN Verification");

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
        TestUtils.assertSearchText("ID", "android:id/title", "Proxy's NIN Verification - Search By NIN");
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/nin")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/nin")).sendKeys(nin);
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/capture_button")).click();

        try{
            //NIN Details View
            TestUtils.testTitle("Confirm the searched NIN is returned: "+nin);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/title")));
            TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/nin_verification_title", "Proxy's NIN Verification");
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

            //Proceed
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/accept_button")));
            Thread.sleep(2000);
            getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/accept_button")).click();

        }catch (Exception e){
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
    public static int verifySubscriberNINTest(String nin, String ninVerificationMode) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(getDriver(), 20);
        //ninStatus is set to available automatically
        int ninStatus=1;
        //Proceed to NIN Verification View
        TestUtils.testTitle("Select NIN Verification Mode: "+ninVerificationMode);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/alertTitle", "Subscriber's NIN Verification");

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
        TestUtils.assertSearchText("ID", "android:id/title", "Subscriber's NIN Verification - Search By NIN");
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/nin")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/nin")).sendKeys(nin);
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/capture_button")).click();

        try{
            //NIN Details View
            TestUtils.testTitle("Confirm the searched NIN is returned: "+nin);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/title")));
            TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/nin_verification_title", "Subscriber's NIN Verification");
            TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/tv_compare_text", "Please compare user data before proceeding");
            TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/tv_portrait_image", "Portrait Image");

            TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/tv_sim_reg", "SIM REG");
            TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/tv_nimc", "NIMC");
            String userdata=getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/tv_user_data")).getText();
            if(userdata.contains("User data")){
                TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/tv_user_data", "User data");
            }else{
                TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/tv_user_data", "User Data");
            }

            TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/tv_nimc_data", "NIMC Data");

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

            //Proceed
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/accept_button")));
            Thread.sleep(2000);
            getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/accept_button")).click();

        }catch (Exception e){
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

    @Parameters({ "systemPort", "deviceNo", "server","deviceName", "testConfig", "dataEnv" })
    @Test
    public void removeSimSwapPrivilegeTest(String systemPort, int deviceNo, String server, String deviceName, String testConfig, String dataEnv) throws Exception {

        JSONParser parser = new JSONParser();
        JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resource/" + dataEnv + "/data.conf.json"));
        JSONObject envs = (JSONObject) config.get("SIMSwap");

        String valid_username = (String) envs.get("valid_username");
        String generalUserPassword = (String) envs.get("generalUserPassword");
        String lga = (String) envs.get("lga");
        String moduleName = "SIM Swap";


        //initial setting
        String initialSetting= TestUtils.retrieveSettingsApiCall(dataEnv, "RETRIEVE_AVAILABLE_USECASES");
        String settingCode="SS";
        if(initialSetting.contains(settingCode)){
            //Continue the setting is available already, remove NMS
            //*********UPDATING SETTING FOR AVAILABLE USE CASE************
            TestUtils.testTitle("UPDATING SETTING FOR AVAILABLE USE CASE("+settingCode+")");
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

        TestBase.Login1( valid_username, generalUserPassword);
        Thread.sleep(500);
        TestUtils.testTitle("To confirm that New registration  is not available when it is removed from available use case settings and user has privilege");
        navigateToCaptureMenuTest();

        // Select LGA of Registration
        TestUtils.testTitle("Select LGA of Registration: " + lga);
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/lga_of_reg")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/alertTitle")));
        TestUtils.assertSearchText("ID", "android:id/alertTitle", "LGA of Registration*");
        getDriver().findElement(By.xpath("//android.widget.TextView[@text='" + lga + "']")).click();
        Thread.sleep(500);

        // Select Corporate Registration
        TestUtils.testTitle("Select "+moduleName);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/typeofreg")));
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/typeofreg")).click();
        Thread.sleep(500);
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/alertTitle", "Select Registration Type");
        try{
            getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='"+moduleName+"']")).click();
            testInfo.get().error(moduleName+" is not removed from the AVAILABLE-USE-CASE");
            Thread.sleep(500);
            getDriver().findElement(By.id("android:id/button1")).click();
        }catch (Exception e){
            testInfo.get().info(moduleName+" is not found, hence successfully removed from AVAILABLE-USE-CASE");
        }

        Thread.sleep(1000);
        if(initialSetting.contains(settingCode)) {
            //Returning initial setting value
            TestUtils.testTitle("Returning initial setting value(" + initialSetting + ")");
            JSONObject getSettingParams = TestUtils.createSettingObject("PILOT-AVAILABLE-USE-CASE", initialSetting, "All available registration use case(SS,AR,CR,CN,NMS,RR,MP,MR)");
            TestUtils.updateSettingsApiCall(dataEnv, getSettingParams);
        }else{
            //Add Module and update data
            //Returning initial setting value
            initialSetting+=","+settingCode;
            TestUtils.testTitle("Returning initial setting value(" + initialSetting + ")");
            JSONObject getSettingParams = TestUtils.createSettingObject("PILOT-AVAILABLE-USE-CASE", initialSetting, "All available registration use case(SS,AR,CR,CN,NMS,RR,MP,MR)");
            TestUtils.updateSettingsApiCall(dataEnv, getSettingParams);
        }

        // Log out
        getDriver().pressKeyCode(AndroidKeyCode.BACK);
        logOutUser(valid_username);

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

            //verificationErrors.append(e.toString());
            //String verificationErrorString = verificationErrors.toString();
            testInfo.get().error("Summation not equal");
            //testInfo.get().error(verificationErrorString);
        }

        //Return to capture page
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/btn_back_home")).click();



    }

}
