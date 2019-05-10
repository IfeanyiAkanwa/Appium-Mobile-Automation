package com.seamfix.biosmart230;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

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
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/btn_back_home")).click();
        Thread.sleep(1000);
    }

    @Test
    public static void searchParameters() throws InterruptedException {
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/btn_server_reports")).click();

        //Start TimeStamp
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/start_date")).click();
        Thread.sleep(500);
        //Year
        getDriver().findElement(By.id("android:id/date_picker_header_year")).click();
        Thread.sleep(500);
        getDriver().findElement(By.xpath("//android.widget.TextView[@text='2017']")).click();
        //Month - Day
        getDriver().findElement(By.xpath("//android.view.View[@text='1']")).click();
        Thread.sleep(500);
        //OK
        getDriver().findElement(By.xpath("//android.widget.Button[@text='OK']")).click();
        //Verify

    }
}
