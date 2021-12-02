package admin;

import com.aventstack.extentreports.Status;
import db.ConnectDB;
import io.appium.java_client.android.AndroidKeyCode;
import org.apache.maven.surefire.shade.org.apache.commons.lang3.ObjectUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.Asserts;
import utils.TestBase;
import utils.TestUtils;

import java.io.File;
import java.io.FileReader;
import java.util.HashMap;

public class CorporateSimSwap extends TestBase {

    @Parameters({"dataEnv"})
    @Test
    public void noneCorporateSwapPrivilegeTest(String dataEnv) throws Exception {
        WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
        JSONParser parser = new JSONParser();
        JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resource/" + dataEnv + "/data.conf.json"));
        JSONObject envs = (JSONObject) config.get("CorporateSIMSwap");

        String valid_username = (String) envs.get("valid_username");
        String valid_password = (String) envs.get("valid_password");

        TestBase.Login1(valid_username, valid_password);
        Thread.sleep(1000);
        TestUtils.testTitle("To confirm that a user without Corporate SIM Swap privilege can't access the module");
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/button_start_capture")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/reg_type_placeholder")));
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/reg_type_placeholder", "Registration Type");
        Thread.sleep(500);

        // Select SIM Swap
        TestUtils.testTitle("Check that Corporate SIM Swap is not selectable");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/typeofreg")));
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/typeofreg")).click();
        Thread.sleep(500);
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/alertTitle", "Select Registration Type");
        getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Corporate SIM Swap']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/alertTitle", "No Privilege");
        TestUtils.assertSearchText("ID", "android:id/message", "You are not allowed to access Corporate SIM Swap because you do not have the Corporate SIM Swap privilege");
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

    @Parameters({"dataEnv"})
    @Test
    public void navigateToCorporateSimSwap(String dataEnv) throws Exception {

        WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);

        JSONParser parser = new JSONParser();
        JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resource/" + dataEnv + "/data.conf.json"));
        JSONObject envs = (JSONObject) config.get("CorporateSIMSwap");

        String lga = (String) envs.get("lga");

        // Select LGA of Registration
        TestUtils.testTitle("Select LGA of Registration: " + lga);
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/lga_of_reg")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/alertTitle")));
        TestUtils.assertSearchText("ID", "android:id/alertTitle", "LGA of Registration*");
        getDriver().findElement(By.xpath("//android.widget.TextView[@text='" + lga + "']")).click();

        // Check that Corporate Sim Swap is selectable
        TestUtils.testTitle("Check that Corporate SIM Swap is selectable");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/typeofreg")));
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/typeofreg")).click();
        Thread.sleep(500);
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/alertTitle", "Select Registration Type");
        getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Corporate SIM Swap']")).click();
        Thread.sleep(500);
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/next_button")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/pageTitle")));
        TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Corporate SIM Swap']", "Corporate SIM Swap");
        Thread.sleep(500);

    }

    @Parameters({"dataEnv"})
    @Test
    public void corporateSwapMsisdnValidation(String dataEnv) throws Exception {

        WebDriverWait wait = new WebDriverWait(getDriver(), 90);
        JSONParser parser = new JSONParser();
        JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resource/" + dataEnv + "/data.conf.json"));
        JSONObject envs = (JSONObject) config.get("CorporateSIMSwap");

        String validMsisdn = (String) envs.get("validMsisdn");
        String moreMsisdn = (String) envs.get("moreMsisdn");
        String lessMsisdn1 = (String) envs.get("lessMsisdn1");
        String lessMsisdn2 = (String) envs.get("lessMsisdn2");
        String invalidMsisdn = (String) envs.get("invalidMsisdn");
        String newMsisdn = (String) envs.get("newMsisdn");
        String newSerial = (String) envs.get("newSerial");
        String invalidSerial = (String) envs.get("invalidSerial");
        String moreSerial = (String) envs.get("moreSerial");
        String lessSerial = (String) envs.get("lessSerial");
        String invalidFormatSerial = (String) envs.get("invalidFormatSerial");

        //Select sim type
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/simType")));
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/simType")).click();
        Thread.sleep(500);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.CheckedTextView[@text='MOBILE']")));
        TestUtils.assertSearchText("XPATH", "//android.widget.CheckedTextView[@text='MOBILE']", "MOBILE");
        TestUtils.assertSearchText("XPATH", "//android.widget.CheckedTextView[@text='DATA']", "DATA");
        getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='MOBILE']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='MOBILE']")));

        // To ensure that there is a field called Swap Type
        TestUtils.testTitle("To ensure that there is a field called 'SIM Swap Type*'");
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/sim_swap_typeTXT", "SIM Swap Type*");

        // To confirm that the Swap Type field has the following list of items [TM, CHILD]
        TestUtils.testTitle("Confirm that the Swap Type field has the following list of items [TM, CHILD]");
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/radio_item_tm", "TM");
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/radio_item_child", "CHILD");

        //Proceed without selecting SIM Swap type
        TestUtils.testTitle("To ensure the Swap Type field is a compulsory field");
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/btnValidate")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
        TestUtils.assertSearchText("ID", "android:id/message", "Please Select SIM Swap Type");
        getDriver().findElement(By.id("android:id/button1")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/radio_item_tm")));
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/radio_item_tm")).click();
        Thread.sleep(500);

        // To ensure there is a dropdown field called Swap Category
        TestUtils.testTitle("To ensure there is a dropdown field called Swap Category");
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/swapCategory", "");

        // To ensure the Swap Category has the following items [CORPORATE SWAP, SIM UPGRADE]
        TestUtils.testTitle("To ensure the Swap Category has the following items [CORPORATE SWAP, SIM UPGRADE]");
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/swapCategory")).click();
        Thread.sleep(500);
        TestUtils.assertSearchText("XPATH", "//android.widget.CheckedTextView[@text='SIM UPGRADE']", "SIM UPGRADE");
        TestUtils.assertSearchText("XPATH", "//android.widget.CheckedTextView[@text='SIM SWAP']", "SIM SWAP");
        getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='SIM SWAP']")).click();
        Thread.sleep(500);

        // Confirm there is a field called Existing TM MSISDN
        TestUtils.testTitle("Confirm there is a field called Existing TM MSISDN");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/existingMsisdnField")));
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/existingMsisdnField", "Enter Existing MSISDN*");

        // To ensure the Existing TM MSISDN field is a mandatory field
        TestUtils.testTitle("To ensure the Existing TM MSISDN field is a mandatory field");
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/btnValidate")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
        TestUtils.assertSearchText("ID", "android:id/message", "MSISDN cannot be blank");
        getDriver().findElement(By.id("android:id/button1")).click();
        Thread.sleep(500);

        // Confirm the Existing TM MSISDN field accepts only digits
        String invalidFormat = lessMsisdn2 + "a$%";
        TestUtils.testTitle("Confirm the Existing TM MSISDN field accepts only digits and no letters or special characters (" + invalidFormat + ")");
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/existingMsisdnField")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/existingMsisdnField")).sendKeys(invalidFormat);
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/existingMsisdnField", lessMsisdn2);
        Thread.sleep(500);

        // Confirm Existing TM MSISDN field has validation for Glo
        TestUtils.testTitle("Confirm invalid MSISDN cannot be validated in Existing MSISDN field (" + invalidMsisdn + ")");
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/existingMsisdnField")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/existingMsisdnField")).sendKeys(invalidMsisdn);
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/newMsisdnField")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/newMsisdnField")).sendKeys(newMsisdn);
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/simSerialField")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/simSerialField")).sendKeys(newSerial);
        TestUtils.scrollDown();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/btnValidate")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
        TestUtils.assertSearchText("ID", "android:id/message", invalidMsisdn + " is not available for SIM Swap. Please Contact Support");
        getDriver().findElement(By.id("android:id/button1")).click();
        Thread.sleep(500);
        TestUtils.testTitle("Confirm Existing MSISDN cannot take more than 11 digits (" + moreMsisdn + ")");
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/existingMsisdnField")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/existingMsisdnField")).sendKeys(moreMsisdn);
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/existingMsisdnField", moreMsisdn.substring(0,11));
        Thread.sleep(500);
        TestUtils.testTitle("Confirm Existing MSISDN cannot take less than 11 digits (" + lessMsisdn1 + " and " + lessMsisdn2 + ")");
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/existingMsisdnField")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/existingMsisdnField")).sendKeys(lessMsisdn1);
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/btnValidate")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
        TestUtils.assertSearchText("ID", "android:id/message", "Entered Existing MSISDN is invalid. It must be either 7 digits for fixed or 11 digits for mobile.");
        getDriver().findElement(By.id("android:id/button1")).click();
        Thread.sleep(500);
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/existingMsisdnField")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/existingMsisdnField")).sendKeys(lessMsisdn2);
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/btnValidate")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
        TestUtils.assertSearchText("ID", "android:id/message", "Existing MSISDN: The MSISDN has an unrecognized National Destination Code. Please ensure the MSISDN starts with any of the following NDCs: 0701,0708,0802,0808,0812,0901,0902,0904,0907,0809,0817,0818,0908,0909,0705,0805,0807,0811,0815,0905,0915,0703,0706,0803,0806,0810,0813,0814,0816,0903,0906 and must be 11 digits");
        getDriver().findElement(By.id("android:id/button1")).click();
        Thread.sleep(500);

        // Confirm there is a field called New MSISDN
        TestUtils.testTitle("Confirm there is a field called New MSISDN*");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/newMsisdnField")));
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/newMsisdnField")).clear();
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/newMsisdnField", "Enter New MSISDN*");

        // Confirm that New MSISDN field accepts only digits
        TestUtils.testTitle("Confirm the New MSISDN field accepts only digits and no letters or special characters (" + invalidFormat + ")");
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/existingMsisdnField")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/existingMsisdnField")).sendKeys(validMsisdn);
        Thread.sleep(500);
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/newMsisdnField")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/newMsisdnField")).sendKeys(invalidFormat);
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/newMsisdnField", lessMsisdn2);
        Thread.sleep(500);

        // Ensure New MSISDN field has MSISDN validation for Glo
        TestUtils.testTitle("Confirm invalid MSISDN cannot be validated in New MSISDN field (" + invalidMsisdn + ")");
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/newMsisdnField")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/newMsisdnField")).sendKeys(invalidMsisdn);
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/btnValidate")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
        TestUtils.assertSearchText("ID", "android:id/message", "New Msisdn : MSISDN Platform not allowed for Corporate Swap");
        getDriver().findElement(By.id("android:id/button1")).click();
        Thread.sleep(500);
        TestUtils.testTitle("Confirm New MSISDN field cannot take more than 11 digits (" + moreMsisdn + ")");
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/newMsisdnField")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/newMsisdnField")).sendKeys(moreMsisdn);
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/newMsisdnField", moreMsisdn.substring(0,11));
        Thread.sleep(500);
        TestUtils.testTitle("Confirm New MSISDN cannot take less than 11 digits (" + lessMsisdn1 + " and " + lessMsisdn2 + ")");
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/newMsisdnField")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/newMsisdnField")).sendKeys(lessMsisdn1);
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/btnValidate")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
        TestUtils.assertSearchText("ID", "android:id/message", "New MSISDN : Invalid MSISDN. It must be either 7 digits for fixed or 11 digit(s) for mobile ");
        getDriver().findElement(By.id("android:id/button1")).click();
        Thread.sleep(500);
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/newMsisdnField")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/newMsisdnField")).sendKeys(lessMsisdn2);
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/btnValidate")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
        TestUtils.assertSearchText("ID", "android:id/message", "Existing MSISDN: The MSISDN has an unrecognized National Destination Code. Please ensure the MSISDN starts with any of the following NDCs: 0701,0708,0802,0808,0812,0901,0902,0904,0907,0809,0817,0818,0908,0909,0705,0805,0807,0811,0815,0905,0915,0703,0706,0803,0806,0810,0813,0814,0816,0903,0906 and must be 11 digits");
        getDriver().findElement(By.id("android:id/button1")).click();
        Thread.sleep(500);

        // To ensure the New MSISDN field is a mandatory field
        TestUtils.testTitle("To ensure the New MSISDN field is a mandatory field");
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/newMsisdnField")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/btnValidate")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
        TestUtils.assertSearchText("ID", "android:id/message", "Required Input Field: New MSISDN");
        getDriver().findElement(By.id("android:id/button1")).click();
        Thread.sleep(500);

        // Confirm there is a field called New SIM Serial
        TestUtils.testTitle("Confirm there is a field called New SIM Serial");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/simSerialField")));
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/simSerialField")).clear();
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/simSerialField", "Enter New SIM Serial*");

        // Ensure New SIM Serial field has SIM Serial validation for Glo
        TestUtils.testTitle("Confirm New SIM Serial cannot take more than 20 digits (" + moreSerial + ")");
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/newMsisdnField")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/newMsisdnField")).sendKeys(newMsisdn);
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/simSerialField")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/simSerialField")).sendKeys(moreSerial);
        TestUtils.assertSearchText("ID","com.sf.biocapture.activity" + Id + ":id/simSerialField",moreSerial.substring(0,20));
        Thread.sleep(500);
        TestUtils.testTitle("Confirm Existing MSISDN cannot take less than 20 digits (" + lessSerial + ")");
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/simSerialField")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/simSerialField")).sendKeys(lessSerial);
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/btnValidate")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
        TestUtils.assertSearchText("ID","android:id/message","New SIM Serial: SIM Serial format is invalid. SIM Serial should be 19 numbers with 'F' at the end");
        getDriver().findElement(By.id("android:id/button1")).click();
        Thread.sleep(500);
        TestUtils.testTitle("Confirm SIM Serial must end with 'F' (" + invalidFormatSerial + ")");
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/simSerialField")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/simSerialField")).sendKeys(invalidFormatSerial);
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/btnValidate")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
        TestUtils.assertSearchText("ID","android:id/message","New SIM Serial: SIM Serial format is invalid. SIM Serial should be 19 numbers with 'F' at the end");
        getDriver().findElement(By.id("android:id/button1")).click();
        Thread.sleep(500);

        // Confirm fields displayed if Swap type is TM
        TestUtils.testTitle("Confirm fields displayed if Swap type is TM");
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/simSerialField")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/simSerialField")).sendKeys(newSerial);
        Thread.sleep(500);
        TestUtils.assertSearchText("ID","com.sf.biocapture.activity" + Id + ":id/radio_item_tm","TM");
        TestUtils.assertSearchText("ID","com.sf.biocapture.activity" + Id + ":id/sim_swap_typeTXT","SIM Swap Type*");
        TestUtils.assertSearchText("ID","com.sf.biocapture.activity" + Id + ":id/swapCategory", "");
        TestUtils.assertSearchText("ID","com.sf.biocapture.activity" + Id + ":id/existingMsisdnField",validMsisdn);
        TestUtils.assertSearchText("ID","com.sf.biocapture.activity" + Id + ":id/newMsisdnField",newMsisdn);
        TestUtils.assertSearchText("ID","com.sf.biocapture.activity" + Id + ":id/simSerialField",newSerial);
        TestUtils.assertSearchText("ID","com.sf.biocapture.activity" + Id + ":id/rawSimCheckBox","Raw SIM");


        // Confirm fields displayed if Swap type is Child
        TestUtils.testTitle("Confirm fields displayed if Swap type is Child");
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/radio_item_child")).click();
        Thread.sleep(500);
        TestUtils.assertSearchText("ID","com.sf.biocapture.activity" + Id + ":id/radio_item_child","CHILD");
        TestUtils.assertSearchText("ID","com.sf.biocapture.activity" + Id + ":id/sim_swap_typeTXT","SIM Swap Type*");
        TestUtils.assertSearchText("ID","com.sf.biocapture.activity" + Id + ":id/swapCategory", "");
        TestUtils.assertSearchText("ID","com.sf.biocapture.activity" + Id + ":id/existingMsisdnField",validMsisdn);
        TestUtils.assertSearchText("ID","com.sf.biocapture.activity" + Id + ":id/newMsisdnField",newMsisdn);
        TestUtils.assertSearchText("ID","com.sf.biocapture.activity" + Id + ":id/simSerialField",newSerial);
        TestUtils.assertSearchText("ID","com.sf.biocapture.activity" + Id + ":id/childMsisdnField","Enter Child MSISDN*");
        TestUtils.assertSearchText("ID","com.sf.biocapture.activity" + Id + ":id/childFirstNameField","Enter Child First Name*");
        TestUtils.assertSearchText("ID","com.sf.biocapture.activity" + Id + ":id/childLastNameField","Enter Child Last Name*");
        TestUtils.assertSearchText("ID","com.sf.biocapture.activity" + Id + ":id/rawSimCheckBox","Raw SIM");


        //Check for Raw SIM
        TestUtils.testTitle("Check for Raw SIM checkbox");
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/radio_item_tm")).click();
        Thread.sleep(500);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/rawSimCheckBox")));
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/rawSimCheckBox", "Raw SIM");

        // To confirm once the RAW checkbox is checked, the New MSISDN field is disabled
        TestUtils.testTitle("To confirm once the RAW checkbox is checked, the New MSISDN field is disabled");
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/rawSimCheckBox")).click();
        Thread.sleep(500);
        if (!(getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/newMsisdnField")).isEnabled())) {
            testInfo.get().log(Status.INFO,"Raw SIM Checkbox is disabled");
        } else {
            testInfo.get().log(Status.INFO,"Raw SIM Checkbox is not disabled");
        }

        // To ensure the inputted MSISDN is cleared once a user checks the RAW checkbox
        TestUtils.testTitle("To ensure the inputted MSISDN is cleared once a user checks the RAW checkbox");
        TestUtils.assertSearchText("ID","com.sf.biocapture.activity" + Id + ":id/newMsisdnField","Enter New MSISDN*");

        // To ensure a Validate Button exist to validate the Subscriber after entering the required fields
        TestUtils.testTitle("To ensure a Validate Button exists to validate the Subscriber after entering the required fields");
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/rawSimCheckBox")).click(); // To disable Raw SIM Checkbox
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/newMsisdnField")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/newMsisdnField")).sendKeys(newMsisdn);

        Thread.sleep(500);
        TestUtils.assertSearchText("ID","com.sf.biocapture.activity" + Id + ":id/btnValidate","Validate");
        if (getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/newMsisdnField")).isEnabled()) {
            testInfo.get().log(Status.INFO,"Validate Button is enabled");
        } else {
            testInfo.get().log(Status.INFO,"Validate Button is not enabled");
        }

    }

    @Parameters({"dataEnv"})
    @Test
    public void verifyBiometrics(String dataEnv) throws Exception {

        WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
        JSONParser parser = new JSONParser();
        JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resource/" + dataEnv + "/data.conf.json"));
        JSONObject envs = (JSONObject) config.get("CorporateSIMSwap");

        String nin = (String) envs.get("nin");

        // To test "Verify Biometrics" button is clickable after MSISDN validation
        TestUtils.testTitle("To test \"Verify Biometrics\" button is clickable after MSISDN validation");
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/btnValidate")).click();
        Thread.sleep(1000);
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
            getDriver().findElement(By.id("android:id/button1")).click();
            Thread.sleep(500);
        }catch(Exception e){

        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/btn_capture")));
        TestUtils.assertSearchText("ID","com.sf.biocapture.activity" + Id + ":id/btn_capture","Verify Biometrics");
        if (getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/btn_capture")).isEnabled()) {
            testInfo.get().log(Status.INFO,"Verify Biometrics Button is enabled");
        } else {
            testInfo.get().log(Status.INFO,"Verify Biometrics Button is not enabled");
        }

        // To test that the maker would be required to capture the subscriber’s fingerprints when no fingerprint is returned by BioData during capture
        TestUtils.testTitle("To test that the maker would be required to capture the subscriber’s fingerprints when no fingerprint is returned by BioData during capture");
        verifyBioMetricsTest();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
        TestUtils.assertSearchText("ID","android:id/message","Subscriber does not have fingerprint saved. You will be allowed to proceed");
        getDriver().findElement(By.id("android:id/button1")).click();
        Thread.sleep(500);

        // To test that user is returned to MSISDN validation view upon disposal of the capture modal
        TestUtils.testTitle("To test that user is returned to MSISDN validation view upon disposal of the capture modal");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/btnNext")));
        TestUtils.assertSearchText("ID","com.sf.biocapture.activity" + Id + ":id/pageTitle","Corporate SIM Swap");
        TestUtils.assertSearchText("ID","com.sf.biocapture.activity" + Id + ":id/btnValidate","Validate");

        // To test that user is able to proceed to NIN verification after matching for SIM SWAP
        TestUtils.testTitle("To test that user is able to proceed to NIN verification after matching for SIM SWAP");
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/btnNext")).click();
        Thread.sleep(500);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
        TestUtils.assertSearchText("ID","com.sf.biocapture.activity" + Id + ":id/alertTitle","NIN Verification");

        // To test that user can select required NIN verification option
        TestUtils.testTitle("To test that user can select required NIN verification option");
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/verification_modes")).click();
        Thread.sleep(500);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.CheckedTextView[@text='Search By NIN']")));
        getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Search By NIN']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/proceed")));
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/proceed")).click();
        Thread.sleep(500);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/title")));
        TestUtils.assertSearchText("ID","android:id/title","Search By NIN");
        TestUtils.assertSearchText("ID","com.sf.biocapture.activity" + Id + ":id/nin","Supply NIN");

        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/nin")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/nin")).sendKeys(nin);
        Thread.sleep(500);
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/capture_button")).click();
        Thread.sleep(500);

        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
            getDriver().findElement(By.id("android:id/button1")).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/continue_btn")));
            getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/continue_btn")).click();

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/nin")));
            getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/nin")).clear();
            getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/nin")).sendKeys(nin);
            Thread.sleep(500);
            getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/proceed_button")).click();

        } catch (Exception e) {

        }

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/nin_verification_title")));
        TestUtils.assertSearchText("ID","com.sf.biocapture.activity" + Id + ":id/nin_verification_title","NIN Verification");
        TestUtils.assertSearchText("ID","com.sf.biocapture.activity" + Id + ":id/tv_portrait_image","Portrait Image");
        TestUtils.assertSearchText("ID","com.sf.biocapture.activity" + Id + ":id/tv_user_data","User Data");
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/accept_button")).click();
        Thread.sleep(500);



    }

    @Parameters({"dataEnv"})
    @Test
    public void fdnCheck(String dataEnv) throws Exception {

        WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
        JSONParser parser = new JSONParser();
        JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resource/" + dataEnv + "/data.conf.json"));
        JSONObject envs = (JSONObject) config.get("CorporateSIMSwap");

        String fName = (String) envs.get("fName");
        String lName = (String) envs.get("lName");
        String mmn = (String) envs.get("mmn");
        String dob = (String) envs.get("dob");
        String address = (String) envs.get("address");
        String activation_year = (String) envs.get("activation_year");
        String lastInvoiceAmount = (String) envs.get("lastInvoiceAmount");
        String lastInvoiceDate = (String) envs.get("lastInvoiceDate");


        // To test that user is required to capture subscriber's demographics
        TestUtils.testTitle("To test that user is required to capture subscriber's demographics");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/title_header")));

        // First Name
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/firstnameTXT")).clear();
        TestUtils.scrollUntilElementIsVisible("ID","com.sf.biocapture.activity" + Id + ":id/submit");
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/submit")).click();
        Thread.sleep(500);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
        TestUtils.assertSearchText("XPATH","//android.widget.TextView[@text='First Name is empty']","First Name is empty");
        getDriver().findElement(By.id("android:id/button1")).click();
        Thread.sleep(500);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/submit")));
        TestUtils.scrollUp2();
        TestUtils.scrollUp2();
        TestUtils.scrollUp2();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/firstnameTXT")).sendKeys(fName);

        // Surname
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/surname")).clear();
        TestUtils.scrollUntilElementIsVisible("ID","com.sf.biocapture.activity" + Id + ":id/submit");
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/submit")).click();
        Thread.sleep(500);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
        TestUtils.assertSearchText("XPATH","//android.widget.TextView[@text='Surname is empty']","Surname is empty");
        getDriver().findElement(By.id("android:id/button1")).click();
        Thread.sleep(500);
        TestUtils.scrollUp2();
        TestUtils.scrollUp2();
        TestUtils.scrollUp2();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/surname")).sendKeys(lName);

        // Mother's Maiden name
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/maidenNameTxt")).clear();
        TestUtils.scrollUntilElementIsVisible("ID","com.sf.biocapture.activity" + Id + ":id/submit");
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/submit")).click();
        Thread.sleep(500);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
        TestUtils.assertSearchText("XPATH","/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/androidx.appcompat.widget.LinearLayoutCompat/android.widget.FrameLayout/android.widget.ListView/android.widget.TextView[1]","Mother's Maiden Name is empty");
        getDriver().findElement(By.id("android:id/button1")).click();
        Thread.sleep(500);
        TestUtils.scrollUp2();
        TestUtils.scrollUp2();
        TestUtils.scrollUp2();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/maidenNameTxt")).sendKeys(mmn);

        // Gender
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/female")).click();
        Thread.sleep(500);

        // Date of birth
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/dateofBirthTXT")).clear();
        TestUtils.scrollUntilElementIsVisible("ID","com.sf.biocapture.activity" + Id + ":id/submit");
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/submit")).click();
        Thread.sleep(500);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
        TestUtils.assertSearchText("XPATH","//android.widget.TextView[@text='Date of Birth is empty']","Date of Birth is empty");
        getDriver().findElement(By.id("android:id/button1")).click();
        Thread.sleep(500);
        TestUtils.scrollUp2();
        TestUtils.scrollUp2();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/dateofBirthTXT")).sendKeys(dob);

        // Residential Address
        TestUtils.scrollUntilElementIsVisible("ID","com.sf.biocapture.activity" + Id + ":id/residentialAddress");
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/residentialAddress")).clear();
        TestUtils.scrollUntilElementIsVisible("ID","com.sf.biocapture.activity" + Id + ":id/submit");
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/submit")).click();
        Thread.sleep(500);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
        TestUtils.assertSearchText("XPATH","//android.widget.TextView[@text='Residential Address is empty']","Residential Address is empty");
        getDriver().findElement(By.id("android:id/button1")).click();
        Thread.sleep(500);
        TestUtils.scrollUp2();
        TestUtils.scrollUp2();
        TestUtils.scrollUntilElementIsVisible("ID","com.sf.biocapture.activity" + Id + ":id/residentialAddress");
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/residentialAddress")).sendKeys(address);

        // Country
        TestUtils.scrollUntilElementIsVisible("ID","com.sf.biocapture.activity" + Id + ":id/countrySpinner");
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/countrySpinner")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/alertTitle")));
        getDriver().findElement(By.xpath("//android.widget.TextView[@text='[Select Nationality]*']")).click();
        Thread.sleep(500);
        TestUtils.scrollUntilElementIsVisible("ID","com.sf.biocapture.activity" + Id + ":id/submit");
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/submit")).click();
        Thread.sleep(500);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
        TestUtils.assertSearchText("XPATH","//android.widget.TextView[@text='Nationality is empty']","Nationality is empty");
        getDriver().findElement(By.id("android:id/button1")).click();
        Thread.sleep(500);
        TestUtils.scrollUp2();
        TestUtils.scrollUp2();
        TestUtils.scrollUntilElementIsVisible("ID","com.sf.biocapture.activity" + Id + ":id/countrySpinner");
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/countrySpinner")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='NIGERIA']")));
        getDriver().findElement(By.xpath("//android.widget.TextView[@text='NIGERIA']")).click();

        // Last Invoice Amount
        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/lastRechargeAmt");
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/lastRechargeAmt")).clear();
        TestUtils.scrollUntilElementIsVisible("ID","com.sf.biocapture.activity" + Id + ":id/submit");
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/submit")).click();
        Thread.sleep(500);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
        TestUtils.assertSearchText("XPATH","//android.widget.TextView[@text='Last Recharge Amount is empty']","Last Recharge Amount is empty");
        getDriver().findElement(By.id("android:id/button1")).click();
        Thread.sleep(500);
        TestUtils.scrollUp2();
        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/lastRechargeAmt");
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/lastRechargeAmt")).sendKeys(lastInvoiceAmount);

        // Last Invoice Date
        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/lastRechargeDate");
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/lastRechargeDate")).clear();
        TestUtils.scrollUntilElementIsVisible("ID","com.sf.biocapture.activity" + Id + ":id/submit");
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/submit")).click();
        Thread.sleep(500);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
        TestUtils.assertSearchText("XPATH","//android.widget.TextView[@text='Last Recharge Date is empty']","Last Recharge Date is empty");
        getDriver().findElement(By.id("android:id/button1")).click();
        Thread.sleep(500);
        TestUtils.scrollUp2();
        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/lastRechargeDate");
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/lastRechargeDate")).sendKeys(lastInvoiceDate);

        // Activation Year
        TestUtils.scrollUntilElementIsVisible("ID","com.sf.biocapture.activity" + Id + ":id/activationYear");
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/activationYear")).clear();
        TestUtils.scrollUntilElementIsVisible("ID","com.sf.biocapture.activity" + Id + ":id/submit");
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/submit")).click();
        Thread.sleep(500);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
        TestUtils.assertSearchText("XPATH","//android.widget.TextView[@text='Activation Year is empty']","Activation year is empty");
        getDriver().findElement(By.id("android:id/button1")).click();
        Thread.sleep(500);
        TestUtils.scrollUp2();
        TestUtils.scrollUntilElementIsVisible("ID","com.sf.biocapture.activity" + Id + ":id/activationYear");
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/activationYear")).sendKeys(activation_year);

        // To test that affidavit check box exists on Swap demographics page when FDN is not returned
        TestUtils.testTitle("To test that affidavit check box exists on Swap demographics page when FDN is not returned");
        TestUtils.scrollUntilElementIsVisible("ID","com.sf.biocapture.activity" + Id + ":id/check_box_affidavit");
        TestUtils.assertSearchText("ID","com.sf.biocapture.activity" + Id + ":id/check_box_affidavit","Upload Affidavit");

        // To test that user is required to check a box for upload of subscriber's affidavit when FDN is not returned
        TestUtils.testTitle("To test that user is required to check a box for upload of subscriber's affidavit when FDN is not returned");
        TestUtils.scrollUntilElementIsVisible("ID","com.sf.biocapture.activity" + Id + ":id/submit");
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/submit")).click();
        Thread.sleep(500);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
        TestUtils.assertSearchText("ID","android:id/text1","Ensure that the Affidavit check box is checked");
        getDriver().findElement(By.id("android:id/button1")).click();
        Thread.sleep(500);

        // To test that user is directed to the score card after completion of the form
        TestUtils.testTitle("To test that user is directed to the score card after completion of the form");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/check_box_affidavit")));
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/check_box_affidavit")).click();
        Thread.sleep(500);
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/submit")).click();
        Thread.sleep(500);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Authentication Score Card']")));
        TestUtils.assertSearchText("XPATH","//android.widget.TextView[@text='Authentication Score Card']","Authentication Score Card");
        TestUtils.assertSearchText("XPATH","//android.widget.TextView[@text='Mandatory Parameter']","Mandatory Parameter");
        TestUtils.assertSearchText("XPATH","//android.widget.TextView[@text='Optional Parameter - At least 4 must pass']","Optional Parameter - At least 4 must pass");
        TestUtils.scrollDown();
        TestUtils.assertSearchText("XPATH","//android.widget.TextView[@text='Summary']","Summary");
        Thread.sleep(500);

        // To test that view for document capture has a document upload component with the existing business rules
        TestUtils.testTitle("To test that view for document capture has a document upload component with the existing business rules");
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/btnSwapOk")).click();
        Thread.sleep(500);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/title_header")));
        TestUtils.assertSearchText("ID","com.sf.biocapture.activity" + Id + ":id/title_header","Document Upload");
        TestUtils.assertSearchText("ID","com.sf.biocapture.activity" + Id + ":id/summary","Upload all necessary Documents.");
        TestUtils.assertSearchText("ID","com.sf.biocapture.activity" + Id + ":id/finger_name","Add Documents");

        // To test that an affidavit option must be on the dropdown for upload when FDN was not returned and the subscriber checked the Affidavit checkbox
        TestUtils.testTitle("To test that an affidavit option must be on the dropdown for upload when FDN was not returned and the subscriber checked the Affidavit checkbox");
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/finger_name")).click();
        Thread.sleep(500);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/summary")));
        TestUtils.assertSearchText("XPATH","//android.widget.TextView[@text='Valid ID Card']","Valid ID Card");
        TestUtils.assertSearchText("XPATH","//android.widget.TextView[@text='Sworn FDN Affidavit']","Sworn FDN Affidavit");
        Thread.sleep(500);


        // Adding Valid ID Card
        getDriver().findElement(By.xpath("//android.widget.TextView[@text='Valid ID Card']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/doc_capture_btn")).click();
        Thread.sleep(500);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/captureButton")));
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/captureButton")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/ok")));
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/ok")).click();
        Thread.sleep(500);

        // Adding Sworn FDN Affidavit
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/continue_btn")));
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/finger_name")).click();
        Thread.sleep(500);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/summary")));
        getDriver().findElement(By.xpath("//android.widget.TextView[@text='Sworn FDN Affidavit']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/doc_capture_btn")).click();
        Thread.sleep(500);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/captureButton")));
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/captureButton")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/ok")));
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/ok")).click();
        Thread.sleep(500);

        // Adding SIM Certificate or Affidavit
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/document_image")));
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/document_image")).click();
        getDriver().findElement(By.xpath("//android.widget.TextView[@text='SIM Certificate or Affidavit']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/doc_capture_btn")).click();
        Thread.sleep(500);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/captureButton")));
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/captureButton")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/ok")));
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/ok")).click();
        Thread.sleep(500);

    }

    @Parameters({"dataEnv"})
    @Test
    public void swapRequest(String dataEnv) throws Exception {

        WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
        JSONParser parser = new JSONParser();
        JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resource/" + dataEnv + "/data.conf.json"));
        JSONObject envs = (JSONObject) config.get("CorporateSIMSwap");
        String valid_Msisdn = (String) envs.get("validMsisdn");
        String kmUserId = (String) envs.get("kmUserId");


        // Proceeding after Document Uploads
        TestUtils.testTitle("SWAP Request");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/continue_btn")));
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/continue_btn")).click();
        Thread.sleep(500);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/message")));
        getDriver().findElement(By.id("android:id/button1")).click();
        Thread.sleep(500);
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
        Thread.sleep(500);

    }

    @Test
    public static void verifyBioMetricsTest() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);

        // TestUtils.scrollDown();
        //Proceed
        Thread.sleep(2000);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/btn_capture")));
        Thread.sleep(2000);
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/btn_capture")).click();


        try{
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/captureButton")));
            getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/captureButton")).click();
            Thread.sleep(1000);
            getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/captureButton")).click();
        }catch (Exception e){

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/buttonCapturePicture")));

            getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/buttonCapturePicture")).click();
        }

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
        TestUtils.assertSearchText("ID", "android:id/message", "Subscriber's face was successfully captured");
        getDriver().findElement(By.id("android:id/button1")).click();
        Thread.sleep(500);
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/nextButton")).click();

        //Fingerprint capture/

        //Submit without overriding fingerprint
        TestUtils.testTitle("Save fingerprint without overriding fingerprint");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/btn_multi_capture")));
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/btn_multi_capture", "MULTI CAPTURE");
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/fp_save_enrolment")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
        TestUtils.assertSearchText("ID", "android:id/message", "No finger was captured");
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



}
