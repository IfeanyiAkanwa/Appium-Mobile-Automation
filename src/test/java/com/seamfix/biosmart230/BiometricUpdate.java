package com.seamfix.biosmart230;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
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
import DemographicForm.Form;
import utils.TestUtils;

import java.io.FileReader;
import java.io.IOException;

public class BiometricUpdate extends TestBase {
	
	 @Test
	public static void navigateToCaptureMenuTest() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), 30);
		// Navigate to Registration Type
		String regType = "Navigate to Registration Type";
		Markup r = MarkupHelper.createLabel(regType, ExtentColor.BLUE);
		testInfo.get().info(r);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Home']")));
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/button_start_capture")).click();
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/reg_type_placeholder")));
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity:id/reg_type_placeholder", "Registration Type");
	}

	@Parameters({ "dataEnv" })
	@Test
    public void captureBiometricUpdate(String dataEnv) throws Exception {

        WebDriverWait wait = new WebDriverWait(getDriver(), 30);
        JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resource/" + dataEnv + "/data.conf.json"));
		JSONObject envs = (JSONObject) config.get("BiometricUpdate");
		
		String invalid_msisdn = (String) envs.get("invalid_msisdn");
		String valid_msisdn = (String) envs.get("valid_msisdn");
		String lga = (String) envs.get("lga");
		String area = (String) envs.get("area");
		
		// Select LGA of Registration
		String lgaa = "Select LGA of Registration: " + lga;
		Markup m = MarkupHelper.createLabel(lgaa, ExtentColor.BLUE);
		testInfo.get().info(m);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/lga_of_reg")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/alertTitle", "LGA of Registration*");
		getDriver().findElement(By.id("android:id/search_src_text")).sendKeys(lga);
		getDriver().findElement(By.id("android:id/button1")).click();
		Thread.sleep(500);

		// Select Bio-metric Update
		String biometric = "Select New Registration MSISDN";
		Markup d = MarkupHelper.createLabel(biometric, ExtentColor.BLUE);
		testInfo.get().info(d);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/typeofreg")));
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/typeofreg")).click();
		Thread.sleep(500);
		TestUtils.assertSearchText("ID", "android:id/text1", "[Select Registration Type]");
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='Biometric Update']")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/next_button")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Biometric Update']")));
		TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Biometric Update']", "Biometric Update");
		Thread.sleep(500);
		
		// Enter agent MSISDN without biometric update privilege
		String invalidMsisdn = "Enter agent MSISDN without biometric update privilege: " + invalid_msisdn;
		Markup i = MarkupHelper.createLabel(invalidMsisdn, ExtentColor.BLUE);
		testInfo.get().info(i);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/primary_msisdn_field")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/primary_msisdn_field")).sendKeys(invalid_msisdn);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/submit_button")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/message", "Could not retrieve bio data for the specified msisdn.");
		getDriver().findElement(By.id("android:id/button1")).click();
		Thread.sleep(500);
        
		// Enter valid MSISDN for biometric update 
		String validMsisdn = "Enter valid MSISDN for biometric update : " + valid_msisdn;
		Markup j = MarkupHelper.createLabel(validMsisdn, ExtentColor.BLUE);
		testInfo.get().info(j);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/primary_msisdn_field")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/primary_msisdn_field")).sendKeys(valid_msisdn);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/submit_button")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Personal Details']")));
	    Thread.sleep(1000);
	    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/surname")));
	    Thread.sleep(500);
	    Asserts.AssertIndividualForm230();
	    Thread.sleep(1000);
	    TestUtils.scrollDown();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/btn_continue_reg")));
        Thread.sleep(500);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/btn_continue_reg")).click();
        Thread.sleep(500);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/nationality")));
        Thread.sleep(1000);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/country_spinner")));
        Thread.sleep(1000);
        Asserts.assertBasicInfoDetails230(dataEnv);
        Thread.sleep(1000);
        
		// Area of residence
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='[Select Area]*']")).click();
		Thread.sleep(500);
		TestUtils.assertSearchText("ID", "android:id/text1", "[Select Area]*");
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='" + area + "']")).click();
		Thread.sleep(500);
	
		// Next button
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/save_continue")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Capture Data']")));
		TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Capture Data']", "Capture Data");
		TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Identification Type']",
				"Identification Type");
		Thread.sleep(500);

		// Capture Data
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='[Select Identification Type]']")).click();
		Thread.sleep(500);
		TestUtils.assertSearchText("XPATH", "//android.widget.CheckedTextView[@text='[Select Identification Type]']",
				"[Select Identification Type]");
		Thread.sleep(500);

		// Capture ID CARD
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='International Passport']")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/capture_id_button")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/button_camera_capture")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/ok"))).click();
		Thread.sleep(500);

		// View Captured ID
		String capturedID = "Try to view captured ID";
		Markup c = MarkupHelper.createLabel(capturedID, ExtentColor.BLUE);
		testInfo.get().info(c);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/view_id_button")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/title")));
		TestUtils.assertSearchText("ID", "android:id/title", "Preview");
		if (getDriver().findElement(By.id("com.sf.biocapture.activity:id/image")).isDisplayed()) {
			testInfo.get().info("Captured ID is displayed");
		} else {
			testInfo.get().info("Captured ID is not displayed");
			testInfo.get().addScreenCaptureFromPath(TestUtils.addScreenshot());
		}
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/done_button")).click();
		Thread.sleep(500);

		// Try to proceed with registration without completing the capture process
		String emptyField = "Try to proceed with registration without completing the capture process";
		Markup f = MarkupHelper.createLabel(emptyField, ExtentColor.BLUE);
		testInfo.get().info(f);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/next_button")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/alertTitle")));
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity:id/alertTitle", "Error");
		TestUtils.assertSearchText("ID", "android:id/message",
				"\n" + "KYC Form: Please Capture KYC/REGISTRATION Form \n"
						+ "Face Capture: Please capture valid portrait \n" + "");
		getDriver().findElement(By.id("android:id/button1")).click();
		Thread.sleep(1000);

		// Capture KYC FORM
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/capture_kyc_form_button")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/button_camera_capture")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/ok"))).click();
		Thread.sleep(500);

		// View Captured ID
		String capturedKycForm = "Try to view captured KYC Form";
		Markup b = MarkupHelper.createLabel(capturedKycForm, ExtentColor.BLUE);
		testInfo.get().info(b);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/view_kyc_form_button")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/title")));
		TestUtils.assertSearchText("ID", "android:id/title", "Preview");
		if (getDriver().findElement(By.id("com.sf.biocapture.activity:id/image")).isDisplayed()) {
			testInfo.get().info("Captured ID is displayed");
		} else {
			testInfo.get().info("Captured ID is not displayed");
			testInfo.get().addScreenCaptureFromPath(TestUtils.addScreenshot());
		}
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/done_button")).click();
		Thread.sleep(500);

		// Face Capture
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/face_capture_button")).click();
		Thread.sleep(500);
    }
}
