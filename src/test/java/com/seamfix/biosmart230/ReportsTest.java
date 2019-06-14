package com.seamfix.biosmart230;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import utils.Asserts;
import utils.TestBase;
import utils.TestUtils;

public class ReportsTest extends TestBase {
	
	private static StringBuffer verificationErrors = new StringBuffer();
	
    @Test
    public static void navigateToReportsPage() throws InterruptedException {
    	
    	WebDriverWait wait = new WebDriverWait(getDriver(), 30);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Home']")));
        getDriver().findElement(By.xpath("//android.widget.ImageButton[@content-desc=\"Navigate up\"]")).click();
        getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Reports']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/report_title")));
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity:id/report_title", "Report Summary");
        testInfo.get().info("Successful landing to Report Summary Page");
    	
    }

    @Test
    public static void reportSummary() throws Exception {
    	
    	Asserts.AssertReportSummary();
    	String totalRegistrationsValString = getDriver().findElement(By.id("com.sf.biocapture.activity:id/reg_subscribers")).getText();
		String totalSyncSentValString = getDriver().findElement(By.id("com.sf.biocapture.activity:id/total_sync_sent")).getText();
		String totalSyncPendingValString = getDriver().findElement(By.id("com.sf.biocapture.activity:id/total_pending")).getText();
		String totalSyncConfirmedValString = getDriver().findElement(By.id("com.sf.biocapture.activity:id/sync_confirmed")).getText();
		
		int actualTotalRegistrationsVal = TestUtils.convertToInt(totalRegistrationsValString);
		int actualTotalSyncSentVal = TestUtils.convertToInt(totalSyncSentValString);
		int actualTotalSyncPendingVal = TestUtils.convertToInt(totalSyncPendingValString);
		int actualTotalSyncConfirmedVal = TestUtils.convertToInt(totalSyncConfirmedValString);
				
		int expectedTotalRegistrationsVal = actualTotalSyncSentVal + actualTotalSyncPendingVal;

		try {
			Assert.assertEquals(expectedTotalRegistrationsVal, actualTotalRegistrationsVal);
			testInfo.get().log(Status.INFO, "Total Registrations (" + expectedTotalRegistrationsVal + ") is equal to summation of total Sync sent (" + actualTotalSyncSentVal + ") + total Sync pending (" + actualTotalSyncPendingVal + ") which is also equal to  total Sync confirmed (" + actualTotalSyncConfirmedVal + ")");
		} catch (Error e) {
			verificationErrors.append(e.toString());
			String verificationErrorString = verificationErrors.toString();
			testInfo.get().error("Summation not equal");
			testInfo.get().error(verificationErrorString);
		}
    	
        //Refresh
    	TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity:id/refresh_button");
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/refresh_button")).click();
        Thread.sleep(2000);
    }

    @Test
    public static void searchParameters() throws Exception {
    	WebDriverWait wait = new WebDriverWait(getDriver(), 50);
    	TestUtils.scrollDown();
    	
        //Start TimeStamp
		// Set date
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/start_date")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/alertTitle")));
        Thread.sleep(1000);
        TestUtils.assertSearchText("ID", "android:id/alertTitle", "Set date");
        getDriver().findElement(By.xpath("//android.widget.Button[@text='2018']")).click();
        Thread.sleep(1000);
        getDriver().findElement(By.id("android:id/button1")).click();
        String sDate = getDriver().findElement(By.id("com.sf.biocapture.activity:id/start_date")).getText();
        
        // Set time
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/start_time")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/alertTitle")));
        TestUtils.assertSearchText("ID", "android:id/alertTitle", "Set time");
        getDriver().findElement(By.id("android:id/button1")).click();
        String sTime = getDriver().findElement(By.id("com.sf.biocapture.activity:id/start_time")).getText();
        
		String date = "Try to set start date: " + sDate+ " and start time " + sTime;
		Markup m = MarkupHelper.createLabel(date, ExtentColor.BLUE);
		testInfo.get().info(m);
		
        //End TimeStamp
		// Set date
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/end_date")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/alertTitle")));
        TestUtils.assertSearchText("ID", "android:id/alertTitle", "Set date");
        getDriver().findElement(By.id("android:id/button1")).click();
        String eDate = getDriver().findElement(By.id("com.sf.biocapture.activity:id/end_date")).getText();
        
        // Set time
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/end_time")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/alertTitle")));
        TestUtils.assertSearchText("ID", "android:id/alertTitle", "Set time");
        getDriver().findElement(By.id("android:id/button1")).click();
        String eTime = getDriver().findElement(By.id("com.sf.biocapture.activity:id/end_time")).getText();
        
        String datee = "Try to set end date: " + eDate+ " and end time " + eTime;
		Markup d = MarkupHelper.createLabel(datee, ExtentColor.BLUE);
		testInfo.get().info(d);
		
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/query_agent")).click();
		Thread.sleep(2000);
		reportSummary();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/btn_back_home")).click();
    }
}
