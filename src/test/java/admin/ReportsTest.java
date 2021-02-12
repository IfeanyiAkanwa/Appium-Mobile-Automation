package admin;

import io.appium.java_client.MobileDriver;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.interactions.internal.TouchAction;
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

import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;

import static utils.TestUtils.scroll;

public class ReportsTest extends TestBase {

    private static StringBuffer verificationErrors = new StringBuffer();

    @Test
    public static void navigateToReportsPage() throws InterruptedException {

        WebDriverWait wait = new WebDriverWait(getDriver(), 30);

        // Navigate to Registration Type
        TestUtils.testTitle("Navigate to Reports Page");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Home']")));
        getDriver().findElement(By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']")).click();
        getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Reports']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity." + Id + ":id/report_title")));
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity." + Id + ":id/report_title", "Report Summary");
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity." + Id + ":id/btn_server_reports", "SERVER/CLIENT REPORTS");

    }

    @Test
    public static void reportSummary() throws Exception {

        Asserts.AssertReportSummary();

    }
    @Parameters({"dataEnv"})
    @Test
    public static void reportLoginUser2(String dataEnv) throws Exception {

        getDriver().findElement(By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']")).click();
        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity." + Id + ":id/tv_camera_la");

        TestUtils.scrollDown();

        getDriver().findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/androidx.drawerlayout.widget.DrawerLayout/android.widget.FrameLayout/androidx.recyclerview.widget.RecyclerView/androidx.appcompat.widget.LinearLayoutCompat[9]/android.widget.CheckedTextView")).click();

        getDriver().findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.ScrollView/android.widget.LinearLayout/android.widget.Button[1]")).click();

        JSONParser parser = new JSONParser();
        JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resource/" + dataEnv + "/data.conf.json"));
        JSONObject envs = (JSONObject) config.get("Login2");

        String valid_username = (String) envs.get("valid_username");
        String valid_password = (String) envs.get("valid_password");

        TestBase.Login1(valid_username, valid_password);

        navigateToReportsPage();
        reportSummary();
    }

    @Test
    public static void reportHomepage() throws Exception {

        WebDriverWait wait = new WebDriverWait(getDriver(), 30);
        getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/btn_back_home")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Total Subscribers']")));
        String totalSub = getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/kpi_report_value")).getText();
        int totalSubVal = TestUtils.convertToInt(totalSub);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Total Sync Sent']")));

        String totalSyncsent = getDriver().findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/androidx.drawerlayout.widget.DrawerLayout/android.widget.RelativeLayout/android.widget.FrameLayout[1]/androidx.viewpager.widget.ViewPager/android.widget.RelativeLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.TextView")).getText();
        int totalSyncsentVal = TestUtils.convertToInt(totalSyncsent);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Total Sync Pending']")));

        String totalSyncpending = getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/kpi_report_value")).getText();
        int totalSyncpendingVal = TestUtils.convertToInt(totalSyncpending);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Total Sync Confirmed']")));

        String totalSynConf = getDriver().findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/androidx.drawerlayout.widget.DrawerLayout/android.widget.RelativeLayout/android.widget.FrameLayout[1]/androidx.viewpager.widget.ViewPager/android.widget.RelativeLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.TextView")).getText();
        int totalSynConfVal = TestUtils.convertToInt(totalSynConf);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Total Rejected']")));

        String totalReject = getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/kpi_report_value")).getText();
        int totalRejectVal = TestUtils.convertToInt(totalReject);
        //wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Total Rejected']")));

        navigateToReportsPage();

        String totalRegistrationsValString = getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/reg_subscribers")).getText();
        String totalSyncSentValString = getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/total_sync_sent")).getText();
        String totalSyncPendingValString = getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/total_pending")).getText();
        String totalSyncConfirmedValString = getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/sync_confirmed")).getText();
        String total_rejectedValString = getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/total_rejected")).getText();

        int actualTotalRegistrationsVal = TestUtils.convertToInt(totalRegistrationsValString);
        int actualTotalSyncSentVal = TestUtils.convertToInt(totalSyncSentValString);
        int actualTotalSyncPendingVal = TestUtils.convertToInt(totalSyncPendingValString);
        int actualTotalSyncConfirmedVal = TestUtils.convertToInt(totalSyncConfirmedValString);
        int total_rejectedVal = TestUtils.convertToInt(total_rejectedValString);

        //int expectedTotalRegistrationsVal = actualTotalSyncSentVal + actualTotalSyncPendingVal;

        try {
            Assert.assertEquals(totalSubVal, actualTotalRegistrationsVal);
            testInfo.get().log(Status.INFO, "Total Registrations (" + totalSubVal + ") is equal to Actual Total Reg  (" + actualTotalRegistrationsVal + ") ");

            Assert.assertEquals(totalSyncsentVal, actualTotalSyncSentVal);
            testInfo.get().log(Status.INFO, "Total Sync Sent (" + totalSyncsentVal + ") is equal to Actual Total Sync Sent  (" + actualTotalSyncSentVal + ") ");

            Assert.assertEquals(totalSyncpendingVal, actualTotalSyncPendingVal);
            testInfo.get().log(Status.INFO, "Total Sync Pending (" + totalSyncpendingVal + ") is equal to Actual Total Sync Pending  (" + actualTotalSyncPendingVal + ") ");

            Assert.assertEquals(totalSynConfVal, actualTotalSyncConfirmedVal);
            testInfo.get().log(Status.INFO, "Total Sync Confirmed (" + totalSynConfVal + ") is equal to Actual Total Sync Confirmed  (" + actualTotalSyncConfirmedVal + ") ");

            Assert.assertEquals(totalRejectVal, total_rejectedVal);
            testInfo.get().log(Status.INFO, "Total Rejected (" + total_rejectedVal + ") is equal to Actual Total Rejected (" + actualTotalSyncConfirmedVal + ") ");

        } catch (Error e) {
            verificationErrors.append(e.toString());
            String verificationErrorString = verificationErrors.toString();
            testInfo.get().error("Summation not equal");
            testInfo.get().error(verificationErrorString);
        }



    }

    @Test
    public static void searchParameters() throws Exception {

        WebDriverWait wait = new WebDriverWait(getDriver(), 50);
        TestUtils.scrollDown();

        TestUtils.testTitle("Search by Date and Time");

        //Start TimeStamp
        // Set date
        getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/start_date")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/button1")));
        //TestUtils.assertSearchText("ID", "android:id/alertTitle", "Set date");
        getDriver().findElement(By.id("android:id/button1")).click();
        String sDate = getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/start_date")).getText();
        testInfo.get().info("<b> Start Date: </b>" + sDate);

        // Set time
        getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/start_time")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/button1")));
        //TestUtils.assertSearchText("ID", "android:id/alertTitle", "Set time");
        getDriver().findElement(By.id("android:id/button1")).click();
        String sTime = getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/start_time")).getText();
        testInfo.get().info("<b> Start Time: </b>" + sTime);

        //End TimeStamp
        // Set date
        getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/end_date")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/button1")));
        //TestUtils.assertSearchText("ID", "android:id/alertTitle", "Set date");
        getDriver().findElement(By.id("android:id/button1")).click();
        String eDate = getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/end_date")).getText();
        testInfo.get().info("<b> End Date: </b>" + eDate);

        // Set time
        getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/end_time")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/button1")));
        //TestUtils.assertSearchText("ID", "android:id/alertTitle", "Set time");
        getDriver().findElement(By.id("android:id/button1")).click();
        String eTime = getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/end_time")).getText();
        testInfo.get().info("<b> End Date: </b>" + eTime);

        getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/query_agent")).click();
        Thread.sleep(2000);
        reportSummary();
        getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/btn_back_home")).click();
    }

    public void scrollUp() throws Exception {


    }

}
