package admin;

import com.aventstack.extentreports.Status;
import demographics.Form;
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
import utils.Asserts;
import utils.TestBase;
import utils.TestUtils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ReportsTest extends TestBase {

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

    @Test
    public static void navigateToReportsPage() throws InterruptedException {

        WebDriverWait wait = new WebDriverWait(getDriver(), 30);

        // Navigate to Registration Type
        TestUtils.testTitle("Navigate to Reports Page");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Home']")));
        getDriver().findElement(By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']")).click();
        getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Reports']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/report_title")));
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/report_title", "Report Summary");
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/btn_server_reports", "REPORT SUMMARY");

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

    @Test
    public static void reportSummary() throws Exception {

        Asserts.AssertReportSummary();

    }

    @Parameters({"dataEnv"})
    @Test
    public void reportAfterCapture(String dataEnv) throws Exception {

        WebDriverWait wait = new WebDriverWait(getDriver(), 30);
        getDriver().findElement(By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']")).click();
        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/tv_camera_la");

        TestUtils.scrollDown();
        Thread.sleep(1000);
        getDriver().findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/androidx.drawerlayout.widget.DrawerLayout/android.widget.FrameLayout/androidx.recyclerview.widget.RecyclerView/androidx.appcompat.widget.LinearLayoutCompat[10]/android.widget.CheckedTextView")).click();

        getDriver().findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.ScrollView/android.widget.LinearLayout/android.widget.Button[1]")).click();

        JSONParser parser = new JSONParser();
        JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resource/" + dataEnv + "/data.conf.json"));
        JSONObject envs = (JSONObject) config.get("Login2");

        String valid_username = (String) envs.get("valid_username");
        String valid_password = (String) envs.get("valid_password");
        String lga = "Anambra East";
        TestBase.Login1(valid_username, valid_password);
        navigateToCaptureMenuTest();
        newRegUseCaseTest();
        parseJson(dataEnv);
        captureForeignRegTest(dataEnv);
        reportHomepage( totalSubVal,  totalSyncsentVal,  totalSyncpendingVal,  totalSynConfVal,  totalRejectVal);
    }

    @Test
    public static void newRegUseCaseTest() throws Exception {
        WebDriverWait wait = new WebDriverWait(getDriver(), 60);

        // To confirm that a user can perform New regiatration when it is  in the list of available use case settings and user has privilege
        TestUtils.testTitle("To confirm that a user can perform New regiatration when it is  in the list of available use case settings and user has privilege" );

        // Select LGA of Registration
        TestUtils.testTitle("Select LGA of Registration: " + lga);
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/lga_of_reg")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/alertTitle")));
        TestUtils.assertSearchText("ID", "android:id/alertTitle", "LGA of Registration*");
        Thread.sleep(1000);
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


    }

    @Parameters({ "dataEnv"})
    @Test
    public static void captureForeignRegTest(String dataEnv) throws Exception {
        WebDriverWait wait = new WebDriverWait(getDriver(), 30);

        try {
            //Proceed to Capture page
            navigateToCaptureMenuTest();

            //Proceed to new reg
            newRegUseCaseTest();
        }catch(Exception e){

        }
        // Select Msisdn Category
        TestUtils.testTitle("Select Msisdn Category");
        TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='MSISDN Category']", "MSISDN Category");
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnCategorySpinner")).click();
        Thread.sleep(500);
        getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Mobile']")).click();
        Thread.sleep(500);

        // Proceed after supplying valid msisdns and sim serial
        TestUtils.testTitle("Proceed after supplying valid msisdns: (" + valid_msisdn2 + ") and (" + valid_simSerial2 + ") for validation");

        // Add another Number
        TestUtils.testTitle("Add another Number");
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).sendKeys(valid_msisdn2);
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/simSerialField")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/simSerialField")).sendKeys(valid_simSerial2);
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/addMsisdnSimSerialButton")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
        TestUtils.assertSearchText("ID", "android:id/message", "Msisdn is valid");
        getDriver().findElement(By.id("android:id/button1")).click();

        TestUtils.scrollDown();

        TestUtils.testTitle("Assert Second Number");
        TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='" + valid_msisdn2 + "']", valid_msisdn2);
        TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='" + valid_simSerial2 + "']", valid_simSerial2);
        Thread.sleep(500);

        try {
            getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/btnCapturePortrait")).click();
            getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/captureButton")).click();

        }catch (Exception e){

        }
        //Select country
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/countryOfOriginSpinner")).click();
        Thread.sleep(500);
        getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='AFGHANISTAN']")).click();
        Thread.sleep(1000);

        //Fill the foreigners form here
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/title", "Foreigner Registration");
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/edPassportNumber")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/edPassportNumber")).sendKeys(TestUtils.generatePhoneNumber());
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/spForeignerTypes")).click();
        Thread.sleep(1000);
        TestUtils.assertSearchText("XPATH", "//android.widget.CheckedTextView[@text='Short Stay']", "Short Stay");
        TestUtils.assertSearchText("XPATH", "//android.widget.CheckedTextView[@text='ECOWAS']", "ECOWAS");
        TestUtils.assertSearchText("XPATH", "//android.widget.CheckedTextView[@text='Diplomat']", "Diplomat");
        TestUtils.assertSearchText("XPATH", "//android.widget.CheckedTextView[@text='Legal Resident(With NIN)']", "Legal Resident(With NIN)");
        getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='ECOWAS']")).click();

        try{
            getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/spForeignerTypes")).click();
            getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Short Stay']")).click();

            getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/edStartDate")).click();
            getDriver().findElement(By.id("android:id/button1")).click();
            getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/edEndDate")).click();
            getDriver().findElement(By.xpath("//android.view.View[@text='30']")).click();
            getDriver().findElement(By.id("android:id/button1")).click();
            getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/spForeignerDocs")).click();
            getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Visa Page']")).click();
            getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/uploadButton")).click();
            TestUtils.scrollUp();
            TestUtils.scrollUntilElementIsVisible("XPATH", "//android.widget.TextView[@text='picture.jpg']");

            getDriver().findElement(By.xpath("//android.widget.TextView[@text='picture.jpg']")).click();
            Thread.sleep(500);

        }catch (Exception e){
            getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/spForeignerTypes")).click();
            getDriver().findElement(By.id("//android.widget.CheckedTextView[@text='ECOWAS']")).click();

        }
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/btnProceed")).click();

        //BioMetrics Verification
        Thread.sleep(1000);
        TestBase.verifyBioMetricsTest();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/nextButton")));
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/nextButton")).click();

        //NIN Verification
        int ninStatus=0;


        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Personal Details']")));
        TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Personal Details']", "Personal Details");

        if (ninStatus==0){
            //Use Form that populate data itself
            Form.individualForeignerForm(dataEnv);
        }else{
            //Use autoPopulated Form
            Form.individualForeignerFormAutoPopulate(dataEnv);
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

    @Test
    public static void reportHomepage(int totalSubVal,  int totalSyncsentVal,  int totalSyncpendingVal,  int totalSynConfVal, int totalRejectVal) throws Exception {

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

    @Test
    public static void searchParameters() throws Exception {

        WebDriverWait wait = new WebDriverWait(getDriver(), 50);
        TestUtils.scrollDown();

        TestUtils.testTitle("Search by Date and Time");

        //Start TimeStamp
        // Set date
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/start_date")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/button1")));
        //TestUtils.assertSearchText("ID", "android:id/alertTitle", "Set date");
        getDriver().findElement(By.id("android:id/button1")).click();
        String sDate = getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/start_date")).getText();
        testInfo.get().info("<b> Start Date: </b>" + sDate);

        // Set time
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/start_time")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/button1")));
        //TestUtils.assertSearchText("ID", "android:id/alertTitle", "Set time");
        getDriver().findElement(By.id("android:id/button1")).click();
        String sTime = getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/start_time")).getText();
        testInfo.get().info("<b> Start Time: </b>" + sTime);

        //End TimeStamp
        // Set date
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/end_date")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/button1")));
        //TestUtils.assertSearchText("ID", "android:id/alertTitle", "Set date");
        getDriver().findElement(By.id("android:id/button1")).click();
        String eDate = getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/end_date")).getText();
        testInfo.get().info("<b> End Date: </b>" + eDate);

        // Set time
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/end_time")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/button1")));
        //TestUtils.assertSearchText("ID", "android:id/alertTitle", "Set time");
        getDriver().findElement(By.id("android:id/button1")).click();
        String eTime = getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/end_time")).getText();
        testInfo.get().info("<b> End Date: </b>" + eTime);

        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/query_agent")).click();

        reportSummary();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/btn_back_home")).click();
    }
}
