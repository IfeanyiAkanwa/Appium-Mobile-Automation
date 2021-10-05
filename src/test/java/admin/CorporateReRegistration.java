package admin;

import db.ConnectDB;
import demographics.Form;
import io.appium.java_client.android.AndroidKeyCode;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.TestBase;
import utils.TestUtils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;

public class CorporateReRegistration extends TestBase {
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
    private static String nin2;
    private static String ninVerificationMode;
    private static String non_valid_msisdn;
    private static String invalid_simSerial;

    private static String valid_username;
    private static String valid_username2;
    private static String generalUserPassword;
    private static String msisdnLessThanSix;
    private static String unrecognizedMsisdn;
    private static String fixed_Msisdn;
    private static String unrecognizedFixedMsisdn;
    private static String expired_OTP;
    private static String invalid_OTP;

    private static String noIndividualReg;
    private static String noCompanyReg;
    private static String noPrivilegeUser;
    private static String noMultiRegUser;
    private static String primary_tm;
    private static String secondary_tm;

    String totalNo;
    String validNo;
    String invalidNo;

    int totalSubVal=0;int totalSyncsentVal = 0;int totalSyncpendingVal = 0;int totalSynConfVal = 0;int totalRejectVal = 0;
    private static String moduleName="Corporate Re-Registration";
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

        JSONObject envs = (JSONObject) config.get("CorporateReRegistration");

        valid_username = (String) envs.get("valid_username");
        valid_username2 = (String) envs.get("valid_username2");
        unrecognizedFixedMsisdn = (String) envs.get("unrecognizedFixedMsisdn");
        fixed_Msisdn = (String) envs.get("fixed_Msisdn");
        primary_tm = (String) envs.get("primary_tm");
        secondary_tm = (String) envs.get("secondary_tm");
        invalid_OTP = (String) envs.get("invalid_OTP");
        expired_OTP = (String) envs.get("expired_OTP");
        noIndividualReg = (String) envs.get("noIndividualReg");
        noCompanyReg = (String) envs.get("noCompanyReg");
        noPrivilegeUser = (String) envs.get("noPrivilegeUser");
        noMultiRegUser = (String) envs.get("noMultiRegUser");
        generalUserPassword = (String) envs.get("generalUserPassword");
        valid_msisdn = (String) envs.get("valid_msisdn");
        invalid_msisdn = (String) envs.get("invalid_msisdn");
        valid_simSerial = (String) envs.get("valid_simSerial");
        invalid_simSerial = (String) envs.get("invalid_simSerial");
        lga = (String) envs.get("lga");
        valid_fixed_msisdn = (String) envs.get("valid_fixed_msisdn");
        valid_fixed_serial = (String) envs.get("valid_fixed_serial");
        invalidPrefixMsisdn = (String) envs.get("invalidPrefixMsisdn");
        invalidPrefixSimSerial = (String) envs.get("invalidPrefixSimSerial");
        msisdnLessThanSix = (String) envs.get("msisdnLessThanSix");
        unrecognizedMsisdn = (String) envs.get("unrecognizedMsisdn");

        valid_msisdn2 = (String) envs.get("valid_msisdn2");
        valid_simSerial2 = (String) envs.get("valid_simSerial2");
        nin = (String) envs.get("nin");
        nin2 = (String) envs.get("nin2");
        ninVerificationMode = (String) envs.get("ninVerificationMode");
        non_valid_msisdn = (String) envs.get("non_valid_msisdn");
    }
    @Test
    public void navigateToCorporateReRegistration() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(getDriver(), 30);
        try{
            navigateToCaptureMenuTest();
        }catch (Exception e){

        }

        // Select LGA of Registration
        TestUtils.testTitle("Select LGA of Registration: " + lga);
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/lga_of_reg")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/alertTitle")));
        TestUtils.assertSearchText("ID", "android:id/alertTitle", "LGA of Registration*");
        getDriver().findElement(By.xpath("//android.widget.TextView[@text='" + lga + "']")).click();
        Thread.sleep(500);

        // Select Module
        TestUtils.testTitle("Select "+moduleName);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/typeofreg")));
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/typeofreg")).click();
        Thread.sleep(500);
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/alertTitle", "Select Registration Type");
        getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='"+moduleName+"']")).click();

        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/next_button")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/pageTitle")));
    }

    @Parameters({ "systemPort", "deviceNo", "server","deviceName", "testConfig", "dataEnv" })
    @Test
    public void removeCorporateReRegTest(String systemPort, int deviceNo, String server, String deviceName, String testConfig, String dataEnv) throws Exception {

        //initial setting
        String initialSetting= TestUtils.retrieveSettingsApiCall(dataEnv, "RETRIEVE_AVAILABLE_USECASES");
        String settingCode="CR";
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

    @Parameters({ "dataEnv"})
    @Test
    public void noneCorporateReRegPrivilegeTest(String dataEnv) throws Exception {
        WebDriverWait wait = new WebDriverWait(getDriver(), 30);

        TestBase.Login1( valid_username, generalUserPassword);

        Thread.sleep(500);
        TestUtils.testTitle("To ensure agents without Cooperate registration privilege can't perform the action when Cooperate registration  is in the list of available  use cases");
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

        // Select Corporate New Registration
        TestUtils.testTitle("Select Corporate New Registration");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/typeofreg")));
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/typeofreg")).click();
        Thread.sleep(500);
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/alertTitle", "Select Registration Type");
        getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='"+moduleName+"']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/alertTitle", "No Privilege");
        TestUtils.assertSearchText("ID", "android:id/message", "You are not allowed to access "+moduleName+" because you do not have the "+moduleName+" privilege");
        Thread.sleep(500);
        getDriver().findElement(By.id("android:id/button1")).click();
        Thread.sleep(500);

        // Log out
        logOutUser(valid_username);

    }

    @Test
    public void CorporatePrivilegesTest() throws Exception {
        WebDriverWait wait = new WebDriverWait(getDriver(), 30);

        //To ensure that the user can click on the Corporate New Registration modal
        TestUtils.testTitle("To ensure that the user can click on the Corporate New Registration modal(" + valid_username2 + ")");
        TestBase.Login1(valid_username2, generalUserPassword);
        Thread.sleep(500);

        navigateToCorporateReRegistration();

        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/pageTitle", "Corporate Re-Registration");

        getDriver().findElement(By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']")).click();
        //Log Out
        logOutUser(valid_username2);

    }

    @Parameters({ "dataEnv"})
    @Test
    public void validateMsisdnTest(String dataEnv) throws Exception {
        WebDriverWait wait = new WebDriverWait(getDriver(), 60);

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
        TestUtils.assertSearchText("XPATH", "//android.widget.CheckedTextView[@text='IOT']", "IOT");
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
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='"+moduleName+"']")));

        //Enter Unrecognized MSISDN
        TestUtils.testTitle("Proceed with Unrecognized MSISDN: ("+ unrecognizedMsisdn +" )");
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).sendKeys(unrecognizedMsisdn);
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/checkBtn")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
        TestUtils.assertSearchText("ID", "android:id/message", "Mobile MSISDN: The MSISDN has an unrecognized National Destination Code. Please ensure the MSISDN starts with any of the following NDCs: 0701,0708,0802,0808,0812,0901,0902,0904,0907,0809,0817,0818,0908,0909,0705,0805,0807,0811,0815,0905,0915,0703,0706,0803,0806,0810,0813,0814,0816,0903,0906");
        getDriver().findElement(By.id("android:id/button1")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='"+moduleName+"']")));

        //Enter MSISDN with special character
        String valid_msisdn_char=valid_msisdn+"jSJHs";
        TestUtils.testTitle("Proceed with MSISDN with special character: ("+ valid_msisdn_char +" )");
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).sendKeys(valid_msisdn_char);
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/checkBtn")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
        TestUtils.assertSearchText("ID", "android:id/message", "Entered Mobile MSISDN is invalid. Entered value should be 11 digits");
        getDriver().findElement(By.id("android:id/button1")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='"+moduleName+"']")));

        //Enter MSISDN with longer Number
        String valid_msisdn_Long=valid_msisdn+"2782";
        TestUtils.testTitle("Proceed with MSISDN with longer Number: ("+ valid_msisdn_Long +" )");
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).sendKeys(valid_msisdn_Long);
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/checkBtn")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
        TestUtils.assertSearchText("ID", "android:id/message", "Entered Mobile MSISDN is invalid. Entered value should be 11 digits");
        getDriver().findElement(By.id("android:id/button1")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='"+moduleName+"']")));

        //Select Fixed category
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnCategorySpinner")).click();
        getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Fixed']")).click();
        Thread.sleep(500);

        //Enter Unrecognized fixed MSISDN
        TestUtils.testTitle("Proceed with Unrecognized Fixed MSISDN: ("+ unrecognizedFixedMsisdn +" )");
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).sendKeys(unrecognizedFixedMsisdn);
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/checkBtn")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
        TestUtils.assertSearchText("ID", "android:id/message", "Fixed MSISDN: Field must have more than 11 characters;The MSISDN has an unrecognized National Destination Code. Please ensure the MSISDN starts with any of the following NDCs: 0701,0708,0802,0808,0812,0901,0902,0904,0907,0809,0817,0818,0908,0909,0705,0805,0807,0811,0815,0905,0915,0703,0706,0803,0806,0810,0813,0814,0816,0903,0906");
        getDriver().findElement(By.id("android:id/button1")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='"+moduleName+"']")));

        //Enter MSISDN Less than 6 digits for FIXED
        TestUtils.testTitle("Proceed with MSISDN Less than 6 digits for FIXED");
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).sendKeys(msisdnLessThanSix);
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/checkBtn")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
        TestUtils.assertSearchText("ID", "android:id/message", "Entered Fixed MSISDN is invalid. Entered value should be 7 digits");
        getDriver().findElement(By.id("android:id/button1")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='"+moduleName+"']")));

    }

    @Test
    @Parameters({"dataEnv"})
    public void corporateSubscriberValidationTest(String dataEnv) throws Exception {
        WebDriverWait wait = new WebDriverWait(getDriver(), 60);

        //Select PRIMARY_TM
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/corporateUserTypeSpinner")).click();
        Thread.sleep(1000);
        getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='PRIMARY_TM']")).click();
        Thread.sleep(500);

        //Select Mobile category
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnCategorySpinner")).click();
        getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Mobile']")).click();
        Thread.sleep(500);

        //To ensure that Only one TM is required for validation (Primary TM MSISDN or Secondary MSISDN)
        TestUtils.testTitle("To ensure that Only one TM is required for validation (Primary TM MSISDN or Secondary MSISDN): ("+ valid_msisdn +" )");
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).sendKeys(valid_msisdn2);
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/checkBtn")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/message")));
        TestUtils.assertSearchText("ID", "android:id/message", "Getting Data...");
        Thread.sleep(3000);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/verifyBiometricsBtn")));
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/verifyBiometricsBtn", "Verify Biometrics");


    }

    @Test
    @Parameters({"dataEnv"})
    public static void captureCorporateReReg(String dataEnv) throws Exception {
        WebDriverWait wait = new WebDriverWait(getDriver(), 30);

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
        TestUtils.testTitle("To ensure that Only one TM is required for validation (Primary TM MSISDN or Secondary MSISDN): ("+ valid_msisdn +" )");
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).sendKeys(valid_msisdn);
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/checkBtn")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/message")));
        TestUtils.assertSearchText("ID", "android:id/message", "Getting Data...");
        Thread.sleep(3000);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/verifyBiometricsBtn")));
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/verifyBiometricsBtn", "Verify Biometrics");

        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/verifyBiometricsBtn")).click();
        Thread.sleep(1000);

        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/uploadButton")).click();
        Thread.sleep(1000);
        TestUtils.scrollUntilElementIsVisible("XPATH", "//android.widget.TextView[@text='picture.jpg']");
        getDriver().findElement(By.xpath("//android.widget.TextView[@text='picture.jpg']")).click();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/nextButton")).click();
        Thread.sleep(1000);

        captureFingerPrint();

        TestUtils.assertSearchText("ID", "android:id/message", "Fingerprint matching not applicable. You will be allowed to proceed to the next verification option.");
        getDriver().findElement(By.id("android:id/button1")).click();

        Thread.sleep(2000);
        TestUtils.scrollDown();
        TestUtils.testTitle("Test invalid OTP:"+invalid_OTP);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/otp_field")));
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/otp_field")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/otp_field")).sendKeys(invalid_OTP);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/otp_confirm_btn")));
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/otp_confirm_btn")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
        TestUtils.assertSearchText("ID", "android:id/message", "There is no record with the otp, msisdn combination.");
        getDriver().findElement(By.id("android:id/button1")).click();

        TestUtils.testTitle("Test Expired OTP:"+expired_OTP);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/otp_field")));
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/otp_field")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/otp_field")).sendKeys(expired_OTP);
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/otp_confirm_btn")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
        TestUtils.assertSearchText("ID", "android:id/message", "There is no record with the otp, msisdn combination.");
        getDriver().findElement(By.id("android:id/button1")).click();

        //OTP Buttons
        TestUtils.testTitle("Confirm Request OTP, Confirm OTP and Bypass OTP buttons are displayed");
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/otp_confirm_btn", "Confirm OTP");
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/otp_bypass_btn", "Bypass OTP");
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/otp_request_btn", "Request OTP");

        //Test Cancel Button on Request OTP Modal
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/otp_request_btn")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/otpHintMessage")));
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/cancel_otp")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/otp_request_btn")));

        //Request OTP Modal
        TestUtils.testTitle("Request OTP Modal");
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/otp_request_btn")).click();
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
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/otp_confirm_btn")).click();
        Thread.sleep(1000);

        //Proceed
        try{
            Thread.sleep(2000);
            getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/otp_bypass_btn")).click();
            getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/nextBtn")).click();
        }catch (Exception e){
            Thread.sleep(2000);
            getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/nextBtn")).click();
        }
        //NIN verification
        TestUtils.verifyNINTest(nin2, ninVerificationMode);

        Form.CorporateRegFormAutoPopulate(dataEnv);

        Form.corporateDocsForm(dataEnv);
        TestUtils.assertCNDetailsTables(valid_msisdn);
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
        }catch (Exception e){
            try{
                getDriver().findElement(By.id("android:id/button3")).click();
                getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/experience_type")).click();
                getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Network Speed']")).click();
                getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/rating_ratingBar")).click();
                getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/rating_btn_dialog_positive")).click();
                TestUtils.assertSearchText("ID", "android:id/alertTitle", "Feedback sent");

                getDriver().pressKeyCode(AndroidKeyCode.BACK);
            }catch (Exception e1){

            }
        }

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
