package com.seamfix.biosmart210;

import DemographicForm.Form;
import io.appium.java_client.android.Connection;
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

public class CaptureNewSimSerialRegistration extends TestBase {

    @Test
    public void navigateToCaptureMenuTest() throws InterruptedException {
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
    public void registerNewSimSerialTest(String dataEnv) throws Exception {

    	 WebDriverWait wait = new WebDriverWait(getDriver(), 30);
         
         JSONParser parser = new JSONParser();
 		JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resource/" + dataEnv + "/data.conf.json"));
 		JSONObject envs = (JSONObject) config.get("NewSimSerialRegistration");
 		
 		String invalid_simSerial= (String) envs.get("invalid_simSerial");
 		String valid_simSerial = (String) envs.get("valid_simSerial");

		// Select new Registration SIM Serial
		String newRegSimSerial = "Select New Registration SIM Serial Registration Type";
		Markup m = MarkupHelper.createLabel(newRegSimSerial, ExtentColor.PURPLE);
		testInfo.get().info(m);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='[Select Registration Type]']")));
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='[Select Registration Type]']")).click();
		Thread.sleep(500);
		TestUtils.assertSearchText("ID", "android:id/alertTitle", "Select Registration Type");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='New Registration (SIM Serial)']")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/next_button")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='New Registration (SIM Serial)']")));
		TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='New Registration (SIM Serial)']",	"New Registration (SIM Serial)");
		Thread.sleep(500);
		
 		// Enter invalid sim serial
 		String invalidSimSerial = "Enter invalid sim serial: "  + invalid_simSerial + " for registration";
 		Markup i = MarkupHelper.createLabel(invalidSimSerial, ExtentColor.BLUE);
 		testInfo.get().info(i);
 		getDriver().findElement(By.id("com.sf.biocapture.activity:id/sim_serial_field")).clear();
 		getDriver().findElement(By.id("com.sf.biocapture.activity:id/sim_serial_field")).sendKeys(invalid_simSerial);
 		getDriver().findElement(By.id("com.sf.biocapture.activity:id/add_sim_serial")).click();
 		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/alertTitle")));
 		TestUtils.assertSearchText("ID", "android:id/message", "Entered Sim Serial is invalid. Entered value should be 20 digits");
 		getDriver().findElement(By.id("android:id/button1")).click();
 		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/sim_serial_field")));
 		Thread.sleep(500);
 		
 		// Turn off all (data and wi-fi)
 		getDriver().setConnection(Connection.NONE);
 		
 		// Enter valid sim serial
 		String validSimSerial = "Enter valid sim serial: " + valid_simSerial + " for registration";
 		Markup v = MarkupHelper.createLabel(validSimSerial, ExtentColor.BLUE);
 		testInfo.get().info(v);
 		getDriver().findElement(By.id("com.sf.biocapture.activity:id/sim_serial_field")).clear();
 		getDriver().findElement(By.id("com.sf.biocapture.activity:id/sim_serial_field")).sendKeys(valid_simSerial);
 		getDriver().findElement(By.id("com.sf.biocapture.activity:id/add_sim_serial")).click();
 		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/alertTitle")));
 		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity:id/alertTitle", "Verification");
 		TestUtils.assertSearchText("ID", "android:id/message", "Do you still wish to proceed with unverified Sim Serial");
 		Thread.sleep(500);
 		getDriver().findElement(By.id("android:id/button1")).click();
 		Thread.sleep(500);
 		
 		// Turn on network
 		getDriver().setConnection(Connection.ALL);
 		
         // Next button
 		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/next_button")));
 		Thread.sleep(500);
 		getDriver().findElement(By.id("com.sf.biocapture.activity:id/next_button")).click();
 		Thread.sleep(500);
 		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Personal Details']")));
 		Thread.sleep(500);
 		TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Personal Details']", "Personal Details");
 		Thread.sleep(500);
         
 		// Proceed with registration without supplying all mandatory fields
 		String emptyField ="Proceed with registration without supplying all mandatory fields on demographics form";
 		Markup e = MarkupHelper.createLabel(emptyField, ExtentColor.BLUE);
 		testInfo.get().info(e);
 		TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity:id/btn_continue_reg");
 		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("com.sf.biocapture.activity:id/btn_continue_reg")));
 		Thread.sleep(500);
 		getDriver().findElement(By.id("com.sf.biocapture.activity:id/btn_continue_reg")).click();
 		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/message", "Registration Type\n" + "Please select Registration Type");
		Thread.sleep(500);
		getDriver().findElement(By.id("android:id/button1")).click();
		Thread.sleep(500);
 		
 		Form.individualForeignerForm(dataEnv);
 	}
       
 }
