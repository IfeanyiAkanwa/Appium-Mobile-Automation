package com.seamfix.biosmart210;

import utils.TestBase;

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

import utils.TestUtils;

public class AdditionalRegistration extends TestBase {

	@Test
	public static void navigateToCaptureMenuTest() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), 30);
		// Navigate to Registration Type
		String regType = "Navigate to Registration Type";
		Markup r = MarkupHelper.createLabel(regType, ExtentColor.BLUE);
		testInfo.get().info(r);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Home']")));
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/button_start_capture")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Registration Type']")));
		TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Registration Type']",	"Registration Type");
		Thread.sleep(500);
	}
	
	@Parameters({ "dataEnv"})
    @Test
    public void additionalRegistrationTest(String dataEnv) throws Exception {

        WebDriverWait wait = new WebDriverWait(getDriver(), 30);
        
        JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resource/" + dataEnv + "/data.conf.json"));
		JSONObject envs = (JSONObject) config.get("AdditionalRegistration");
		
		String invalid_Msisdn= (String) envs.get("invalid_Msisdn");
		String valid_Msisdn = (String) envs.get("valid_Msisdn");

		// Select Additional Registration
		String newReg = "Select Additional Registration";
		Markup d = MarkupHelper.createLabel(newReg, ExtentColor.BLUE);
		testInfo.get().info(d);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='[Select Registration Type]']")));
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='[Select Registration Type]']")).click();
		Thread.sleep(500);
		TestUtils.assertSearchText("ID", "android:id/alertTitle", "Select Registration Type");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Additional Registration']")).click();
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
		
		// Enter valid Msisdn
		String validMsisdn = "Enter valid Msisdn: " + valid_Msisdn + " for registration";
		Markup v = MarkupHelper.createLabel(validMsisdn, ExtentColor.BLUE);
		testInfo.get().info(v);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/primary_msisdn_field")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/primary_msisdn_field")).sendKeys(valid_Msisdn);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/submit_button")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/spinner_finger_type")));
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/spinner_finger_type")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='RIGHT THUMB']")));
		Thread.sleep(1000);
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='RIGHT THUMB']")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/verify_finger_print_button")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/fingerType_text")));
		Thread.sleep(500);
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity:id/fingerType_text", "VERIFICATION");
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity:id/btnStop", "Capture");
		Thread.sleep(500);
	}
      
}
