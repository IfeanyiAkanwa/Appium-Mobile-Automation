package admin;

import java.io.IOException;
import java.sql.SQLException;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import db.ConnectDB;
import utils.TestBase;
import utils.TestUtils;

public class ReactivationTest extends TestBase {
	
	String phoneNumber = "09062058526";
    String otp = null;
    
	@Test
    public static void NavigateToCaptureMenuTest() throws InterruptedException {
        Thread.sleep(500);
        WebDriverWait wait = new WebDriverWait(getDriver(), 30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Home']")));
        Thread.sleep(500);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/button_start_capture")).click();
        Thread.sleep(500);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Registration Type']")));
        TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Registration Type']", "Registration Type");
        Thread.sleep(500);

    }
	
	@Test
    public void msisdnReactivateTest() throws InterruptedException, IOException, SQLException {

        WebDriverWait wait = new WebDriverWait(getDriver(), 30);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/linear_layout_username"))).click();
        Thread.sleep(1000);
        getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='MSISDN Re-Activation']")).click();
        Thread.sleep(500);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/next_button")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='MSISDN Reactivation']")));
        TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='MSISDN Reactivation']", "MSISDN Reactivation");
        Thread.sleep(1000);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/primary_msisdn_field")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/primary_msisdn_field")).sendKeys(phoneNumber);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/submit_button")).click();
        
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/otp_panel_view")));
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/otp_request_button")).click();
        if(TestUtils.isElementPresent("ID", "android:id/body")){
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("android:id/body")));
        }
        otp = ConnectDB.getOTP(phoneNumber);
        if(otp == null){
        	testInfo.get().log(Status.INFO, "Can't get otp.");
            getDriver().quit();
        }
        Thread.sleep(2000);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/otp_field")));
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/otp_field")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/otp_field")).sendKeys(otp);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/otp_confirm_button")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/reactivate_button")));
        String screenshotPath = TestUtils.addScreenshot();
        testInfo.get().addScreenCaptureFromPath(screenshotPath);
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity:id/reactivate_button", "Reactivate Subscriber");
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/reactivate_button")).click();
        if(TestUtils.isElementPresent("ID", "android:id/body")){
        	testInfo.get().log(Status.INFO, "Message: "+ getDriver().findElement(By.id("android:id/message")).getText());
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("android:id/body")));
        }
        Thread.sleep(1000);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/message")));
        TestUtils.assertSearchText("ID", "android:id/message", "MSISDN has been reactivated successfully.");
        getDriver().findElement(By.id("android:id/button1")).click();
        Thread.sleep(500);
        
    }
}
