package com.seamfix.biosmart230;

import java.io.FileReader;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import utils.Asserts;
import utils.TestBase;
import utils.TestUtils;

public class ReportsTest extends TestBase {
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
