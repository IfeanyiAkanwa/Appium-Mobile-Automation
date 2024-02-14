package admin;

import com.aventstack.extentreports.Status;
import db.ConnectDB;
import demographics.Form;
import io.appium.java_client.android.AndroidKeyCode;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.TestBase;
import utils.TestUtils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;


public class MobileNumberPorting extends TestBase {
    private static StringBuffer verificationErrors = new StringBuffer();
    private static String valid_msisdn;
    private static String valid_msisdn2;
    private static String valid_username;
    private static String valid_username2;
    private static String lga;
    private static String generalUserPassword;

    int ninValidate = 0;

    int totalSubVal=0;int totalSyncsentVal = 0;int totalSyncpendingVal = 0;int totalSynConfVal = 0;int totalRejectVal = 0;
    private static String moduleName="Mobile Number Porting";
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

        JSONObject envs = (JSONObject) config.get("MNP");

        valid_username = (String) envs.get("valid_username");
        generalUserPassword = (String) envs.get("generalUserPassword");
        lga = (String) envs.get("lga");
    }

    @Parameters({ "dataEnv"})
    @Test
    public void noneMobileNumberPortPrivilegeTest(String dataEnv) throws Exception {
        WebDriverWait wait = new WebDriverWait(getDriver(), 30);
        JSONParser parser = new JSONParser();
        JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resource/" + dataEnv + "/data.conf.json"));
        JSONObject envs = (JSONObject) config.get("MNP");

        String valid_username = (String) envs.get("valid_username2");
        String valid_password = (String) envs.get("valid_password");
        TestBase.Login1( valid_username, valid_password);
        Thread.sleep(500);


        // To confirm that only  a user with MOBILE NUMBER PORTABILITY privilege can perform port-in-request
        TestUtils.testTitle("To confirm that a user without MOBILE NUMBER PORTABILITY privilege cannot perform port-in-request");
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/button_start_capture")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/reg_type_placeholder")));
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/reg_type_placeholder", "Registration Type");
        Thread.sleep(500);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/typeofreg")));
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/typeofreg")).click();
        Thread.sleep(500);
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/alertTitle", "Select Registration Type");
        getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='"+moduleName+"']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/alertTitle", "No Privilege");
        TestUtils.assertSearchText("ID", "android:id/message", "You are not allowed to access Mobile Number Porting because you do not have the Mobile Number Porting privilege");
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


    @Parameters({ "dataEnv"})
    @Test
    public void mobileNumberPortingValidationTest(String dataEnv) throws Exception {

        WebDriverWait wait = new WebDriverWait(getDriver(), 30);
        JSONParser parser = new JSONParser();
        JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resource/" + dataEnv + "/data.conf.json"));
        JSONObject envs = (JSONObject) config.get("MNP");

        String pri_valid_Msisdn = (String) envs.get("pri_valid_Msisdn");
        String pri_valid_simSerial = (String) envs.get("pri_valid_simSerial");
        String invalid_Msisdn = (String) envs.get("invalid_Msisdn");
        String invalid_simSerial = (String) envs.get("invalid_simSerial");
        String nin = (String) envs.get("nin");

        //To confirm that there is a Registration type called Mobile Number Porting
        TestUtils.testTitle("To confirm that there is a Registration type called Mobile Number Porting");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/button_start_capture")));
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/button_start_capture")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/typeofreg")));
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/typeofreg")).click();
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/alertTitle", "Select Registration Type");
        TestUtils.assertSearchText("XPATH", "//android.widget.CheckedTextView[@text='Mobile Number Porting']", "Mobile Number Porting");

        //To confirm that only  a user with MOBILE NUMBER PORTABILITY privilege can perform port-in-request
        TestUtils.testTitle("To confirm that only  a user with MOBILE NUMBER PORTABILITY privilege can perform port-in-request");
        getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Mobile Number Porting']")).click();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/next_button")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Mobile Number Porting']")));
        TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Mobile Number Porting']", "Mobile Number Porting");
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/dya_check_box", "Vanity Number");

        //To confirm that the SIM SERIAL text field is enabled and does not allow more than 20 characters
        String moreCharacter=pri_valid_simSerial+"1234";
        TestUtils.testTitle("To confirm that the SIM SERIAL text field is enabled and does not allow more than 20 characters ("+moreCharacter+")");
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/sim_serial")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/sim_serial")).sendKeys(pri_valid_simSerial);
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/sim_serial")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/sim_serial")).sendKeys(moreCharacter);
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/sim_serial", pri_valid_simSerial);

        //To confirm that the MSISDN text field is enabled and does not allow more than 11 digits
        String moreCharacters=pri_valid_Msisdn+"1234";
        TestUtils.testTitle("To confirm that the MSISDN text field is enabled and does not allow more than 11 digits ("+moreCharacters+")");
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/primary_msisdn_field")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/primary_msisdn_field")).sendKeys(pri_valid_Msisdn);
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/primary_msisdn_field")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/primary_msisdn_field")).sendKeys(moreCharacters);
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/primary_msisdn_field", pri_valid_Msisdn);

        //To verify that if the sim serial entered fails validation, an error message should be displayed and user should not be allowed to proceed
        TestUtils.testTitle("To verify that if the sim serial entered fails validation, an error message should be displayed ("+invalid_simSerial+")");
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/primary_msisdn_field")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/primary_msisdn_field")).sendKeys(pri_valid_Msisdn);
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/sim_serial")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/sim_serial")).sendKeys(invalid_simSerial);
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/validate_serial_button")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/button1")));
        TestUtils.assertSearchText("ID", "android:id/message", "Sim Serial format is invalid. SIM Serial should be 19 numbers with 'F' at the end.");
        getDriver().findElement(By.id("android:id/button1")).click();

        //To confirm that an empty demographic form is displayed when the NEXT button is clicked
        TestUtils.testTitle("To confirm that an empty demographic form is displayed when the NEXT button is clicked ("+pri_valid_Msisdn+")");
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/primary_msisdn_field")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/primary_msisdn_field")).sendKeys(pri_valid_Msisdn);
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/sim_serial")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/sim_serial")).sendKeys(pri_valid_simSerial);
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/validate_serial_button")).click();
        Thread.sleep(1000);


        //Capture
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/captureButton")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/button1")));
        getDriver().findElement(By.id("android:id/button1")).click();

        //Capture Print
        captureFingerPrint();

        //proceed
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/validate_serial_button")).click();


        //Verify NIN
        ninValidate =TestBase.verifyNINTest(nin, "Search By NIN");

        TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Personal Details']", "Personal Details");

        //To verify that the button to Validate Sim Serial is enabled and functional .
        TestUtils.testTitle("To verify that the button to Validate Sim Serial is enabled and functional.");
        TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Personal Details']", "Personal Details");

    }

    @Parameters({ "dataEnv"})
    @Test
    public void captureMobileNumberPortingTest(String dataEnv) throws Exception{
        JSONParser parser = new JSONParser();
        JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resource/" + dataEnv + "/data.conf.json"));
        JSONObject envs = (JSONObject) config.get("MNP");

        String pri_valid_Msisdn = (String) envs.get("pri_valid_Msisdn");

        if (ninValidate==1){
            Form.individualNigerianFormAutoPopulate(dataEnv);
        }else{
            Form.individualNigerianForm(dataEnv);
        }


        if (releaseRegItem==true) {
            TestUtils.testTitle("DB Checks");
            String quarantineRegPk=ConnectDB.selectQueryOnTable("bfp_sync_log", "msisdn", pri_valid_Msisdn, "pk");
            String uniqueId=ConnectDB.selectQueryOnTable("bfp_sync_log", "msisdn", pri_valid_Msisdn, "unique_id");

            //Release quarantine item
            TestUtils.testTitle("Release the quarantined item("+quarantineRegPk+")");
            Thread.sleep(1500);
            JSONObject payload = new JSONObject();
            payload.put("quarantineRegPk", quarantineRegPk);
            payload.put("uniqueId", uniqueId);
            payload.put("feedback", "test");
            payload.put("loggedInUserId", "2067");
            TestUtils.releaseActionApiCall(dataEnv, payload);
            ConnectDB.query(uniqueId, dataEnv, "MNP");

            JSONArray dbQuery=ConnectDB.QueryBulkTable(pri_valid_Msisdn);
            Object getDDaObject = dbQuery.get(0);
            JSONObject getDDaObjectItem = (JSONObject) getDDaObject;
            TestUtils.testTitle("To ensure the type of registration done is flagged and sent to the backend with other registration data");
            TestUtils.assertTwoValues((String) getDDaObjectItem.get("DDA11"), "MPI");
        }



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
        }
    }

    @Parameters({ "dataEnv"})
    @Test
    public void vanityNumberReg(String dataEnv) throws Exception {

        WebDriverWait wait = new WebDriverWait(getDriver(), 60);
        JSONParser parser = new JSONParser();
        JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resource/" + dataEnv + "/data.conf.json"));
        JSONObject envs = (JSONObject) config.get("MNP");

        String pri_valid_Msisdn = (String) envs.get("pri_valid_Msisdn");
        String pri_valid_simSerial = (String) envs.get("pri_valid_simSerial");
        String invalid_Msisdn = (String) envs.get("invalid_Msisdn");
        String invalid_simSerial = (String) envs.get("invalid_simSerial");
        String nonGlo_Msisdn = (String) envs.get("nonGlo_Msisdn");
        String middle_name = (String) envs.get("middle_name");
        String surname = (String) envs.get("surname");
        String first_name = (String) envs.get("first_name");
        String mothers_maiden_name = (String) envs.get("mothers_maiden_name");
        String nonGloMsisdn = (String) envs.get("nonGloMsisdn");
        String nin = (String) envs.get("nin");
        String alt_phone_number = (String) envs.get("alt_phone_number");

        try {
            navigateToCaptureMenuTest();
        } catch (Exception e) {

        }

        //To ensure the ONLY registration type available on the demographic form for a VNR registraition is INDIVIDUAL
        TestUtils.testTitle("To ensure the ONLY registration type available on the demographic form for a VNR registraition is INDIVIDUAL");

        //Navigate to Mobile Number Porting
        navigateToMobileNumberPorting();

        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/primary_msisdn_field")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/primary_msisdn_field")).sendKeys(pri_valid_Msisdn);
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/sim_serial")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/sim_serial")).sendKeys(pri_valid_simSerial);
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/validate_serial_button")).click();

        //Capture
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/captureButton")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/button1")));
        TestUtils.assertSearchText("ID", "android:id/message", "Subscriber's face was successfully captured");
        getDriver().findElement(By.id("android:id/button1")).click();


        //Capture Print
        captureFingerPrint();

        //proceed
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/validate_serial_button")).click();


        //Verify NIN
        TestBase.verifyNINTest(nin, "Search By NIN");

        //Address Basic Info
        //To verify that the NEXT button directs user to the demographics capture view and contains necessary form
        TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Individual']", "Individual");

        //To confirm that the Vanity Number Registration can not be done with an MSISDN that is not a GLO MSISDN
        TestUtils.testTitle("To confirm that the Vanity Number Registration can not be done with an MSISDN that is not a GLO MSISDN(" + nonGloMsisdn + ")");
        getDriver().findElement(By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']")).click();

        //Navigate to Mobile Number Porting
        navigateToMobileNumberPorting();

        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/primary_msisdn_field")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/primary_msisdn_field")).sendKeys(nonGloMsisdn);
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/sim_serial")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/sim_serial")).sendKeys(pri_valid_simSerial);
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/validate_serial_button")).click();
        try {
            Thread.sleep(500);
            TestUtils.assertSearchText("ID", "android:id/message", "The MSISDN is not a valid GLO MSISDN");
            getDriver().findElement(By.id("android:id/button1")).click();

        } catch (Exception e) {
            testInfo.get().error("Validation failed");
            //Capture
            getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/captureButton")).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/button1")));
            getDriver().findElement(By.id("android:id/button1")).click();

            //Capture Print
            captureFingerPrint();
            Thread.sleep(1500);
            getDriver().findElement(By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']")).click();

            //Navigate to Mobile Number Porting
            navigateToMobileNumberPorting();

        }

        //To confirm that the Vanity Number Registration can only be done with a GLO MSISDN
        TestUtils.testTitle("To confirm that the Vanity Number Registration can only be done with a GLO MSISDN(" + pri_valid_Msisdn + ")");

        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/primary_msisdn_field")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/primary_msisdn_field")).sendKeys(pri_valid_Msisdn);
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/sim_serial")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/sim_serial")).sendKeys(pri_valid_simSerial);
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/validate_serial_button")).click();
        //Capture
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/captureButton")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/button1")));
        TestUtils.assertSearchText("ID", "android:id/message", "Subscriber's face was successfully captured");
        getDriver().findElement(By.id("android:id/button1")).click();
        //Capture Print
        captureFingerPrint();

        //To confirm that only users with the VNR privilege are able to register vanity numbers
        Thread.sleep(1000);
        TestUtils.testTitle("To confirm that only users with the VNR privilege are able to register vanity numbers");
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/dya_check_box", "Vanity Number");
        boolean check1 = getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/dya_check_box")).isEnabled();
        String check = Boolean.toString(check1);
        TestUtils.testTitle("Check if vanity number button is enabled");
        try {
            Assert.assertEquals(check, "true");
            testInfo.get().log(Status.INFO, check + " found");
        } catch (Error e) {
            Assert.assertEquals(check, "false");
            testInfo.get().log(Status.INFO, check + " found");
        }

        //To confirm that the Email Address field is not mandatory
        TestUtils.testTitle("To confirm that the Email Address field is not mandatory");

        getDriver().findElement(By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']")).click();
        //Navigate to Mobile Number Porting
        navigateToMobileNumberPorting();

        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/primary_msisdn_field")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/primary_msisdn_field")).sendKeys(pri_valid_Msisdn);
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/sim_serial")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/sim_serial")).sendKeys(pri_valid_simSerial);
        //getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/dya_check_box")).click();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/validate_serial_button")).click();

        //Capture
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/captureButton")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/button1")));
        getDriver().findElement(By.id("android:id/button1")).click();

        //Capture Print
        captureFingerPrint();

        //proceed
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/validate_serial_button")).click();


        //Verify NIN
        int ninReturned = TestBase.verifyNINTest(nin, "Search By NIN");

        TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Personal Details']", "Personal Details");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/surNameTXT")));
        if (ninReturned == 1) {
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

        } else{
            getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/surNameTXT")).sendKeys(surname);
            getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/firstNameTXT")).sendKeys(first_name);
            TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/middleNameTXT");
            getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/middleNameTXT")).sendKeys(middle_name);
            getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/momsMaidenNameTXT")).sendKeys(mothers_maiden_name);
            TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/maleRadioButton");
            getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/maleRadioButton")).click();
            getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/selectDateButton")).click();
            getDriver().findElement(By.xpath("//android.view.View[@index='0']")).click();
            getDriver().findElement(By.id("android:id/button1")).click();
            TestUtils.scrollDown();
        }
        TestUtils.scrollDown();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/btnContinueReg")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/stateOfOriginSpinner")));
        getDriver().findElement(By.xpath("//android.widget.FrameLayout[@content-desc='Basic Information - Address Details']")).click();
        testInfo.get().log(Status.INFO,"Basic Information - Address Details found, Email is not required");

        //To ensure the SIM Serial inputted is the SIM Serial tied to the Phone Number (MSISDN) the subscriber wants to port
        TestUtils.testTitle("To ensure the SIM Serial inputted is the SIM Serial tied to the Phone Number (MSISDN) the subscriber wants to port AND"+" Confirm that the vanity number checkbox is disabled");
        getDriver().findElement(By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']")).click();
        getDriver().findElement(By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']")).click();

        //Navigate to Mobile Number Porting
        navigateToMobileNumberPorting();

        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/primary_msisdn_field")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/primary_msisdn_field")).sendKeys(nonGlo_Msisdn);
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/sim_serial")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/sim_serial")).sendKeys(pri_valid_simSerial);
        try {
            Assert.assertEquals(check, "true");
            testInfo.get().log(Status.INFO, check + " found");
        } catch (Error e) {
            Assert.assertEquals(check, "false");
            testInfo.get().log(Status.INFO, check + " found");
        }
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/validate_serial_button")).click();
        //Capture
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/captureButton")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/button1")));
        getDriver().findElement(By.id("android:id/button1")).click();
        //Capture Print
        captureFingerPrint();

        //To ensure the Next button is enabled and the Validate button is disabled when the MNP use case is in the skippable validate settings
        TestUtils.testTitle("To ensure the Next button is enabled and the Validate button is disabled when the MNP use case is in the skippable validate settings\n");
        Thread.sleep(1500);
        getDriver().findElement(By.xpath(" //android.widget.ImageButton[@content-desc='Navigate up']")).click();

        //Navigate to Mobile Number Porting
        navigateToMobileNumberPorting();

        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/primary_msisdn_field")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/primary_msisdn_field")).sendKeys(pri_valid_Msisdn);
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/sim_serial")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/sim_serial")).sendKeys(pri_valid_simSerial);
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/validate_serial_button")).click();
        Thread.sleep(2000);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/captureButton")));
        //Capture
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/captureButton")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/button1")));
        getDriver().findElement(By.id("android:id/button1")).click();

        //Capture Print
        captureFingerPrint();

        //proceed
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/validate_serial_button")).click();

        //Verify NIN
        ninReturned=TestBase.verifyNINTest(nin, "Search By NIN");

        TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Personal Details']", "Personal Details");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/surNameTXT")));
        if (ninReturned == 1) {
            Form.individualNigerianFormAutoPopulate(dataEnv);
        } else {
            Form.individualNigerianForm(dataEnv);
        }

        TestUtils.testTitle("To confirm that there is Client Acivity Log for  port-in requests");
        String unique_id= ConnectDB.selectQueryOnTable("BFP_SYNC_LOG", "msisdn", pri_valid_Msisdn, "unique_id");
        JSONArray cal = ConnectDB.ClientActivityLogTable(unique_id);
        Object getCalObject = cal.get(0);
        JSONObject getCalObjectItem = (JSONObject) getCalObject;
        TestUtils.assertTwoValues((String) getCalObjectItem.get("unique_activity_code"), unique_id);

        JSONArray dbQuery=ConnectDB.QueryBulkTable(pri_valid_Msisdn);
        Object getDDaObject = dbQuery.get(0);
        JSONObject getDDaObjectItem = (JSONObject) getDDaObject;
        TestUtils.testTitle("To ensure the type of registration done is flagged and sent to the backend with other registration data");
        TestUtils.assertTwoValues((String) getDDaObjectItem.get("DDA11"), "VNI");

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
        }
    }

    @Parameters({ "systemPort", "deviceNo", "server","deviceName", "testConfig", "dataEnv" })
    @Test
    public void removeMobileNumberPortingTest(String systemPort, int deviceNo, String server, String deviceName, String testConfig, String dataEnv, ITestContext context) throws Exception {

        //initial setting
        String initialSetting= TestUtils.retrieveSettingsApiCall(dataEnv, "RETRIEVE_AVAILABLE_USECASES");
        String settingCode="MP";
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
            Thread.sleep(1000);
            TestUtils.updateSettingsApiCall(dataEnv, getSettingParams);
            closeApp();
            Thread.sleep(5000);
            startApp(systemPort, deviceNo, server, deviceName, testConfig, true, context);
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

        // Select Registration
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

    public static void captureFingerPrint() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(getDriver(), 30);
        //Fingerprint capture/

        //Submit without overriding fingerprint
        TestUtils.testTitle("Save Enrollment without overriding fingerprint");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/btn_multi_capture")));
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/btn_multi_capture", "MULTI CAPTURE");
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
        Thread.sleep(1000);
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

    }

    public static void navigateToMobileNumberPorting() throws InterruptedException {

        WebDriverWait wait = new WebDriverWait(getDriver(), 5);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/typeofreg")));
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/typeofreg")).click();
        Thread.sleep(1000);
        getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='"+moduleName+"']")).click();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/next_button")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='"+moduleName+"']")));

    }

}
