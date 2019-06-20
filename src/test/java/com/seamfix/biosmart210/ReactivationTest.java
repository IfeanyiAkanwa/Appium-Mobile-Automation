package com.seamfix.biosmart210;

import java.io.FileReader;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import db.ConnectDB;
import utils.TestBase;
import utils.TestUtils;

public class ReactivationTest extends TestBase {

    @Parameters({ "dataEnv"})
	@Test
	public void noneReactivationPrivilegeLoginTest(String dataEnv) throws Exception {
    	 WebDriverWait wait = new WebDriverWait(getDriver(), 60);
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resource/" + dataEnv + "/data.conf.json"));
		JSONObject envs = (JSONObject) config.get("Reactivation");
		
		String valid_username = (String) envs.get("valid_username");
		String valid_password = (String) envs.get("valid_password");
	
		TestBase.Login1(dataEnv, valid_username, valid_password);
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/button_start_capture")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Registration Type']")));
		TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Registration Type']", "Registration Type");
		Thread.sleep(500);
		
		// Select MSISDN Re-Activation
		String reAct = "Select MSISDN Re-Activation";
		Markup d = MarkupHelper.createLabel(reAct, ExtentColor.BLUE);
		testInfo.get().info(d);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='[Select Registration Type]']")));
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='[Select Registration Type]']")).click();
		Thread.sleep(500);
		TestUtils.assertSearchText("ID", "android:id/alertTitle", "Select Registration Type");
		Thread.sleep(500);
		if (TestUtils.isElementPresent("XPATH", "//android.widget.CheckedTextView[@text='MSISDN Re-Activation']")) {
			 testInfo.get().error("Element is present");
		} else {
			testInfo.get().info("Element is not present");
		}
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='[Select Registration Type]']")).click();
		Thread.sleep(500);
		
		// Log out
		String logOut = "logout username: "  + valid_username;
		Markup o = MarkupHelper.createLabel(logOut, ExtentColor.BLUE);
		testInfo.get().info(o);
		getDriver().findElement(By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/alertTitle")));
		getDriver().findElement(By.id("android:id/button2")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Logout']")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/message")));
		TestUtils.assertSearchText("ID", "android:id/message", "   Log out?");
		getDriver().findElement(By.id("android:id/button3")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/otp_login")));
		Thread.sleep(500);
	}
    
	@Test
    public static void navigateToCaptureMenuTest() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(getDriver(), 30);
        
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Home']")));
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/button_start_capture")).click();
        Thread.sleep(500);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Registration Type']")));
		TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Registration Type']", "Registration Type");
		Thread.sleep(500);
    }
	
	@Parameters({ "dataEnv"})
	@Test
    public void msisdnReactivateTest(String dataEnv) throws Exception {

        WebDriverWait wait = new WebDriverWait(getDriver(), 60);
        JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resource/" + dataEnv + "/data.conf.json"));
		JSONObject envs = (JSONObject) config.get("Reactivation");
		
		String invalid_msisdn = (String) envs.get("invalid_msisdn");
		String valid_msisdn = (String) envs.get("valid_msisdn");
		String invalid_OTP = (String) envs.get("invalid_OTP");
		
		// Select MSISDN Re-Activation
		String reAct = "Select MSISDN Re-Activation";
		Markup d = MarkupHelper.createLabel(reAct, ExtentColor.BLUE);
		testInfo.get().info(d);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='[Select Registration Type]']")));
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='[Select Registration Type]']")).click();
		Thread.sleep(500);
		TestUtils.assertSearchText("ID", "android:id/alertTitle", "Select Registration Type");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='MSISDN Re-Activation']")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/next_button")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='MSISDN Reactivation']")));
		TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='MSISDN Reactivation']", "MSISDN Reactivation");
		TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Please provide information for the \n" + 
				"corresponding fields']", "Please provide information for the \n" + 
				"corresponding fields");
		Thread.sleep(500);
		
		// Enter invalid msisdn
		String invalidMsisdn = "Enter invalid MSISDN " + invalid_msisdn + " for validation";
		Markup i = MarkupHelper.createLabel(invalidMsisdn, ExtentColor.BLUE);
		testInfo.get().info(i);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/primary_msisdn_field")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/primary_msisdn_field")).sendKeys(invalid_msisdn);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/submit_button")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/alertTitle")));
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity:id/alertTitle", "Error");
		TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='MSISDN is not eligible for reactivation']", "MSISDN is not eligible for reactivation");
		getDriver().findElement(By.id("android:id/button1")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='MSISDN Reactivation']")));
		
		// Enter valid msisdn with invalid OTP
		String validMs = "Enter valid msisdn: "  + valid_msisdn + " with invalid OTP: " +invalid_OTP ;
		Markup r = MarkupHelper.createLabel(validMs, ExtentColor.BLUE);
		testInfo.get().info(r);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/primary_msisdn_field")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/primary_msisdn_field")).sendKeys(valid_msisdn);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/submit_button")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/otp_field")));
		Thread.sleep(1000);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/otp_field")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/otp_field")).sendKeys(invalid_OTP);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/otp_confirm_button")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/alertTitle")));
	    TestUtils.assertSearchText("ID", "com.sf.biocapture.activity:id/alertTitle", "Error");
	    TestUtils.assertSearchText("ID", "android:id/message", "There is no record with the otp, msisdn combination.");
	    getDriver().findElement(By.id("android:id/button1")).click();
	    Thread.sleep(500);
	     
		// Enter valid msisdn with valid OTP
		String validMsisdn = "Enter valid msisdn with valid OTP: " + valid_msisdn + " for validation";
		Markup v = MarkupHelper.createLabel(validMsisdn, ExtentColor.BLUE);
		testInfo.get().info(v);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/primary_msisdn_field")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/primary_msisdn_field")).sendKeys(valid_msisdn);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/submit_button")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/otp_field")));
        Thread.sleep(1000);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/otp_request_button")).click();
        Thread.sleep(1000);
        
        // DB Connection for OTP
    	String valid_OTP = ConnectDB.getOTP(valid_msisdn);

		String ValidOTP = "Enter valid OTP : " + valid_OTP;
		Markup o = MarkupHelper.createLabel(ValidOTP, ExtentColor.BLUE);
		testInfo.get().info(o);
        if(valid_OTP == null){
        	testInfo.get().log(Status.INFO, "Can't get otp.");
            getDriver().quit();
        }
        Thread.sleep(2000);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/otp_field")));
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/otp_field")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/otp_field")).sendKeys(valid_OTP);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/otp_confirm_button")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/reactivate_button")));
        String screenshotPath = TestUtils.addScreenshot();
        testInfo.get().addScreenCaptureFromPath(screenshotPath);
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity:id/reactivate_button", "Reactivate Subscriber");
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/reactivate_button")).click();
        Thread.sleep(1000);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/alertTitle")));
        TestUtils.assertSearchText("ID", "android:id/message", "MSISDN has been reactivated successfully.");
        getDriver().findElement(By.id("android:id/button1")).click();
        Thread.sleep(500);
    }
}
