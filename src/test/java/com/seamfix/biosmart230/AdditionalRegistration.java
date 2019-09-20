package com.seamfix.biosmart230;

import utils.Asserts;
import utils.TestBase;

import java.io.FileReader;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import utils.TestUtils;

public class AdditionalRegistration extends TestBase {

	@Test
	public static void navigateToCaptureMenuTest() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), 60);
		
		String regType = "Navigate to Registration Type";
		Markup r = MarkupHelper.createLabel(regType, ExtentColor.BLUE);
		testInfo.get().info(r);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Home']")));
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/button_start_capture")).click();
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/reg_type_placeholder")));
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity:id/reg_type_placeholder",
				"Registration Type");
	}
	
	@Parameters({ "dataEnv"})
    @Test
    public void additionalRegistrationTest(String dataEnv) throws Exception {

        WebDriverWait wait = new WebDriverWait(getDriver(), 60);
        
        JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resource/" + dataEnv + "/data.conf.json"));
		JSONObject envs = (JSONObject) config.get("AdditionalRegistration");
		
		String invalid_Msisdn= (String) envs.get("invalid_Msisdn");
		String valid_Msisdn = (String) envs.get("valid_Msisdn");
		String lga = (String) envs.get("lga");

		// Select LGA of Registration
		String lgaa = "Select LGA of Registration: " + lga;
		Markup m = MarkupHelper.createLabel(lgaa, ExtentColor.BLUE);
		testInfo.get().info(m);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/lga_of_reg")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/alertTitle", "LGA of Registration*");
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='" + lga + "']")).click();
		Thread.sleep(500);
		
		// Select Additional Registration
		String newReg = "Select Additional Registration";
		Markup d = MarkupHelper.createLabel(newReg, ExtentColor.BLUE);
		testInfo.get().info(d);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/typeofreg")));
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/typeofreg")).click();
		Thread.sleep(500);
		TestUtils.assertSearchText("ID", "android:id/alertTitle", "Select Item");
		TestUtils.assertSearchText("ID", "android:id/text1", "[Select Registration Type]");
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='Additional Registration']")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/next_button")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Additional Registration']")));
		TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Additional Registration']", "Additional Registration");
	
		// Enter invalid Msisdn
		String invalidMsisdn = "Enter invalid MSISDN: "  + invalid_Msisdn + " for registration";
		Markup i = MarkupHelper.createLabel(invalidMsisdn, ExtentColor.BLUE);
		testInfo.get().info(i);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/primary_msisdn_field")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/primary_msisdn_field")).sendKeys(invalid_Msisdn);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/submit_button")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/message", "No Biometric Data found for this phone Number, Please Select another type of registration");
		getDriver().findElement(By.id("android:id/button2")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/primary_msisdn_field")));
		Thread.sleep(500);
		
		// Enter valid sim Msisdn
		String validMsisdn = "Enter valid Msisdn: " + valid_Msisdn + " for registration";
		Markup v = MarkupHelper.createLabel(validMsisdn, ExtentColor.BLUE);
		testInfo.get().info(v);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/primary_msisdn_field")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/primary_msisdn_field")).sendKeys(valid_Msisdn);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/submit_button")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/verify_finger_print_button")));
		String assertDetails = "Assert user's full name";
		Markup ad = MarkupHelper.createLabel(assertDetails, ExtentColor.BLUE);
		testInfo.get().info(ad);
		String NA = "N/A";
		String surName = getDriver().findElement(By.id("com.sf.biocapture.activity:id/tv_surname")).getText();
		String firstName = getDriver().findElement(By.id("com.sf.biocapture.activity:id/tv_first_name")).getText();
		String[] toList = {"Surname: " + surName, "First name: " + firstName};
		for (String field : toList) {
			String name = "";
			String val = NA;
			if (field.endsWith(":")) {
				field = field + val;	
			}
			try {
				String[] fields = field.split(":");
				name = fields[0];
				val = fields[1];
				Assert.assertNotEquals(val, NA);
				testInfo.get().log(Status.INFO, name + " : " + val);
			} catch (Error e) {
				testInfo.get().error(name + " : " + val);

			}
		}
		
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/spinner_finger_type")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='RIGHT THUMB']")));
		Thread.sleep(1000);
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='RIGHT THUMB']")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/verify_finger_print_button")).click();
		Thread.sleep(500);
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/alertTitle")));
			Thread.sleep(500);
			TestUtils.assertSearchText("ID", "com.sf.biocapture.activity:id/alertTitle", "Scanner not found");
			getDriver().findElement(By.id("android:id/button1")).click();
			Thread.sleep(500);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/fingerType_text")));
			Thread.sleep(500);
			TestUtils.assertSearchText("ID", "com.sf.biocapture.activity:id/fingerType_text", "VERIFICATION");
			TestUtils.assertSearchText("ID", "com.sf.biocapture.activity:id/btnStop", "Capture");
			Thread.sleep(500);
		} catch (Exception e) {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/fingerType_text")));
			Thread.sleep(500);
			TestUtils.assertSearchText("ID", "com.sf.biocapture.activity:id/fingerType_text", "VERIFICATION");
			TestUtils.assertSearchText("ID", "com.sf.biocapture.activity:id/btnStop", "Capture");
			Thread.sleep(500);
		}
		
	}
      
}
