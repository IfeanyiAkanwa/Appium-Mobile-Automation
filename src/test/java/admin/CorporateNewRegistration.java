package admin;

import db.ConnectDB;
import demographics.Form;
import functions.Features;
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

public class CorporateNewRegistration extends TestBase {
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

    private static String noIndividualReg;
    private static String noCompanyReg;
    private static String noPrivilegeUser;
    private static String noMultiRegUser;
    private static String primary_tm;
    private static String secondary_tm;
    private static String unrecognizedFixedMsisdn;
    String totalNo;
    String validNo;
    String invalidNo;

    int totalSubVal=0;int totalSyncsentVal = 0;int totalSyncpendingVal = 0;int totalSynConfVal = 0;int totalRejectVal = 0;
    private static String moduleName="Corporate New Registration";
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

        JSONObject envs = (JSONObject) config.get("CorporateRegistration");

        unrecognizedFixedMsisdn = (String) envs.get("unrecognizedFixedMsisdn");
        valid_username = (String) envs.get("valid_username");
        valid_username2 = (String) envs.get("valid_username2");
        primary_tm = (String) envs.get("primary_tm");
        secondary_tm = (String) envs.get("secondary_tm");
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
  

    

    @Parameters({ "dataEnv"})
    @Test
    public void noneCorporatePrivilegeTest(String dataEnv) throws Exception {
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
		Features.selectRegistration(dataEnv, "Corporate New Registration");
		
	}
	
	@Parameters({ "dataEnv"})
	@Test
	public void msisdnValidationOnline(String dataEnv) throws Exception {
		Features.msisdnValidationOnline(dataEnv, "CN");
		
	}
	
	@Parameters({ "dataEnv"})
	@Test
	public void overrideHand(String dataEnv) throws Exception {
	//	Select override
		Features.captureOverridenHand(dataEnv, "CR");
	}
	
	@Parameters({ "dataEnv"})
	@Test
	public void ninVerificationOnline(String dataEnv) throws Exception {

		//NIN Verification
		Features.ninVerificationOnline(dataEnv);
	}
	
	@Parameters({ "dataEnv"})
	@Test
	public void portraitFileUpload(String dataEnv) throws Exception {

		Features.portraitFileUpload(dataEnv, "picture.jpg");
	}
	
	@Parameters({ "dataEnv"})
	@Test
	public void corporateOverridePortrait(String dataEnv) throws Exception {

		//corporate Portrait Override
		Features.corporateOverridePortrait(dataEnv);
	}
	
	
	@Parameters({ "dataEnv"})
	@Test
	public void eyeBalling(String dataEnv) throws Exception {

		
		Features.nimcEyeBalling(dataEnv);
	}
	
	@Parameters({ "dataEnv"})
	@Test
	public void demographicsCapture(String dataEnv) throws Exception {

		Form.CorporateRegFormAutoPopulate(dataEnv);
		Form.addressDetails(dataEnv);
	}
	
	@Parameters({ "dataEnv"})
	@Test
	public void captureUploadDoc(String dataEnv) throws Exception {
		Features.corporatecaptureUploadDocument(dataEnv);
		
	}
	
	@Parameters({ "dataEnv"})
	@Test
	public void capturePreview(String dataEnv) throws Exception {

		Features.captureKYCDocument(dataEnv);
	}
	
	@Parameters({ "dataEnv"})
	@Test
	public void saveCapture(String dataEnv) throws Exception {

		Features.saveEnrollment(dataEnv);
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
