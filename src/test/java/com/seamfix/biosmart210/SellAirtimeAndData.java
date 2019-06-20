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

public class SellAirtimeAndData extends TestBase {

	@Parameters({ "dataEnv"})
	@Test
	public void sellAirtimeLoginTest(String dataEnv) throws Exception {
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resource/" + dataEnv + "/data.conf.json"));
		JSONObject envs = (JSONObject) config.get("SellAirtimeAndData");
		
		String valid_username = (String) envs.get("valid_username");
		String valid_password = (String) envs.get("valid_password");
	
		TestBase.Login1(dataEnv, valid_username, valid_password);
	}
	
	@Test
	public static void navigateToSellAirtimeAndData() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), 30);
		String PasswordPage = "Navigate to Sell Airtime/Data page";
		Markup m = MarkupHelper.createLabel(PasswordPage, ExtentColor.BLUE);
		testInfo.get().info(m);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Home']")));
		getDriver().findElementByAccessibilityId("Navigate up").click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Sell Airtime/Data']")).click();
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Sell Airtime/Data']")));
		TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Sell Airtime/Data']",
				"Sell Airtime/Data");
		TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Airtime']", "Airtime");
		TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Data']", "Data");
		Thread.sleep(500);
	    }
	
	@Parameters({ "dataEnv"})
	@Test
	public void sellAirtimeTest(String dataEnv) throws Exception {
		WebDriverWait wait = new WebDriverWait(getDriver(), 60);
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resource/" + dataEnv + "/data.conf.json"));
		JSONObject envs = (JSONObject) config.get("SellAirtimeAndData");
		
		String agent_vtu = (String) envs.get("agent_vtu");
		String sub_msisdn = (String) envs.get("sub_msisdn");
		String amount = (String) envs.get("amount");
		String invalid_agent_vtu = (String) envs.get("invalid_agent_vtu");
		String invalid_OTP = (String) envs.get("invalid_OTP");
		
		// Sell airtime with invalid Agent VTU number
		String air = "Sell airtime with invalid Agent VTU number: " + invalid_agent_vtu;
		Markup m = MarkupHelper.createLabel(air, ExtentColor.BLUE);
		testInfo.get().info(m);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/agent_vtu")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/agent_vtu")).sendKeys(invalid_agent_vtu);
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/sub_msisdn")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/sub_msisdn")).sendKeys(sub_msisdn);
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/amount")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/amount")).sendKeys(amount);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/buy_airtime")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/message")));
		TestUtils.assertSearchText("ID", "android:id/message", "Agent's VTU number is invalid or inactive\n" + 
				"Do you want to retry?");
		getDriver().findElement(By.id("android:id/button1")).click();
		Thread.sleep(1000);
		
		// Sell airtime with valid Agent VTU number and invalid OTP
		String validMs = "Sell airtime with valid Agent VTU number: "  + agent_vtu + "  and invalid OTP: " + invalid_OTP;
		Markup r = MarkupHelper.createLabel(validMs, ExtentColor.BLUE);
		testInfo.get().info(r);
		
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/agent_vtu")).sendKeys(agent_vtu);
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/sub_msisdn")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/sub_msisdn")).sendKeys(sub_msisdn);
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/amount")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/amount")).sendKeys(amount);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/buy_airtime")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/alertTitle", "OTP Verification");
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/otp")));
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/otp")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/otp")).sendKeys(invalid_OTP);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/confirm_otp")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/message")));
		TestUtils.assertSearchText("ID", "android:id/message", "OTP is invalid.");
		getDriver().findElement(By.id("android:id/button1")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/alertTitle")));
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/cancel")).click();
		Thread.sleep(500);
		
		// Sell airtime with valid Agent VTU number and valid OTP
		String airtime = "Sell airtime with valid Agent VTU number and valid OTP: " + agent_vtu;
		Markup e = MarkupHelper.createLabel(airtime, ExtentColor.BLUE);
		testInfo.get().info(e);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Airtime']")));
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/agent_vtu")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/agent_vtu")).sendKeys(agent_vtu);
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/sub_msisdn")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/sub_msisdn")).sendKeys(sub_msisdn);
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/amount")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/amount")).sendKeys(amount);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/buy_airtime")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/alertTitle", "OTP Verification");
		
		  // DB Connection for OTP
    	String valid_OTP = ConnectDB.getOTP(agent_vtu);

		String ValidOTP = "Enter valid OTP : " + valid_OTP;
		Markup o = MarkupHelper.createLabel(ValidOTP, ExtentColor.BLUE);
		testInfo.get().info(o);
        if(valid_OTP == null){
        	testInfo.get().log(Status.INFO, "Can't get otp.");
            getDriver().quit();
        }
		Thread.sleep(1000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/otp")));
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/otp")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/otp")).sendKeys(valid_OTP);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/confirm_otp")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/message")));
		TestUtils.assertSearchText("ID", "android:id/message", "OTP is invalid.");
		getDriver().findElement(By.id("android:id/button1")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/alertTitle")));
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/cancel")).click();
		Thread.sleep(500);

	}
	
	@Parameters({ "dataEnv"})
	@Test
	public void sellDataTest(String dataEnv) throws Exception {
		WebDriverWait wait = new WebDriverWait(getDriver(), 60);
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resource/" + dataEnv + "/data.conf.json"));
		JSONObject envs = (JSONObject) config.get("SellAirtimeAndData");
		
		String agent_vtu = (String) envs.get("agent_vtu");
		String sub_msisdn = (String) envs.get("sub_msisdn");
		String invalid_agent_vtu = (String) envs.get("invalid_agent_vtu");
		String invalid_OTP = (String) envs.get("invalid_OTP");
		
		// Vend data with invalid Agent VTU number
		String data = "Vend data with invalid Agent VTU number: " + invalid_agent_vtu;
		Markup a = MarkupHelper.createLabel(data, ExtentColor.BLUE);
		testInfo.get().info(a);
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='Data']")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/name")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/name")).sendKeys(invalid_agent_vtu);
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/sub_msisdn")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/sub_msisdn")).sendKeys(sub_msisdn);
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/bundle_type")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/name")));
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity:id/name", "Bundle Type");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='100.0']")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/vend_data")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/message")));
		TestUtils.assertSearchText("ID", "android:id/message", "Agent's VTU number is invalid or inactive\n" + 
				"Do you want to retry?");
		getDriver().findElement(By.id("android:id/button1")).click();
		Thread.sleep(500);
		
		// Vend data with valid Agent VTU number and invalid OTP
		String validMs = "Vend data with valid Agent VTU number: "  + agent_vtu + "  and invalid OTP: " + invalid_OTP;
		Markup r = MarkupHelper.createLabel(validMs, ExtentColor.BLUE);
		testInfo.get().info(r);
		
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/name")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/name")).sendKeys(agent_vtu);
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/sub_msisdn")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/sub_msisdn")).sendKeys(sub_msisdn);
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/bundle_type")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/name")));
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity:id/name", "Bundle Type");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='100.0']")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/vend_data")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/alertTitle", "OTP Verification");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/otp")));
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/otp")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/otp")).sendKeys(invalid_OTP);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/confirm_otp")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/message")));
		TestUtils.assertSearchText("ID", "android:id/message", "OTP is invalid.");
		getDriver().findElement(By.id("android:id/button1")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/alertTitle")));
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/cancel")).click();
		Thread.sleep(500);

		// Vend data with valid Agent VTU number and valid OTP
		String dataa = "Vend data with valid Agent VTU number and valid OTP: " + agent_vtu;
		Markup t = MarkupHelper.createLabel(dataa, ExtentColor.BLUE);
		testInfo.get().info(t);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Data']")));
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/name")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/name")).sendKeys(agent_vtu);
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/sub_msisdn")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/sub_msisdn")).sendKeys(sub_msisdn);
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/bundle_type")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/name")));
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity:id/name", "Bundle Type");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='100.0']")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/vend_data")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/alertTitle", "OTP Verification");
		
		  // DB Connection for OTP
    	String valid_OTP = ConnectDB.getOTP(agent_vtu);

		String ValidOTP = "Enter valid OTP : " + valid_OTP;
		Markup o = MarkupHelper.createLabel(ValidOTP, ExtentColor.BLUE);
		testInfo.get().info(o);
        if(valid_OTP == null){
        	testInfo.get().log(Status.INFO, "Can't get otp.");
            getDriver().quit();
        }
        Thread.sleep(1000);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/otp")));
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/otp")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/otp")).sendKeys(valid_OTP);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/confirm_otp")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/message")));
        TestUtils.assertSearchText("ID", "android:id/message", "OTP is invalid.");
        getDriver().findElement(By.id("android:id/button1")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/alertTitle")));
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/cancel")).click();
		Thread.sleep(500);
	}

}
