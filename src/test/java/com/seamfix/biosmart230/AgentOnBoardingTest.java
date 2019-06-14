package com.seamfix.biosmart230;

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

import java.io.FileReader;
import java.sql.SQLException;

public class AgentOnBoardingTest extends TestBase {
    
    @Parameters({ "dataEnv"})
   	@Test
   	public void onboardedAgentLoginTest(String dataEnv) throws Exception {
   		JSONParser parser = new JSONParser();
   		JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resource/" + dataEnv + "/data.conf.json"));
   		JSONObject envs = (JSONObject) config.get("AgentOnboarding");
   		
   		String valid_username = (String) envs.get("valid_username");
   		String valid_password = (String) envs.get("valid_password");
   	
   		TestBase.Login1(dataEnv, valid_username, valid_password);
   		Thread.sleep(500);
    }

   	@Test
   	public void navigateToAgentOnBoardingTest( ) throws Exception {
   		WebDriverWait wait = new WebDriverWait(getDriver(), 30);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Home']")));
        getDriver().findElement(By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']")).click();
        Thread.sleep(500);
        getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Agent OnBoarding']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/page_title")));
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity:id/page_title", "Agent Onboarding");
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity:id/page_guide_title", "Get started in 3 easy steps");
        Thread.sleep(500);
    }
    
    @Parameters({ "dataEnv"})
    @Test
    public static void agentOnBoardingTest(String dataEnv) throws Exception {

        WebDriverWait wait = new WebDriverWait(getDriver(), 30);
        JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resource/" + dataEnv + "/data.conf.json"));
		JSONObject envs = (JSONObject) config.get("AgentOnboarding");
		
		String agent_email = (String) envs.get("agent_email");
		String agent_phoNum = (String) envs.get("agent_phoNum");
		String onboardedAgent = (String) envs.get("onboardedAgent");
		String invalid_email = (String) envs.get("invalid_email");
		String invalid_OTP  = (String) envs.get("invalid_OTP");
		String used_OTP = (String) envs.get("expired_OTP");
		
		// To On-board an already existing agent
		String email1 = "To Onboard an already existing agent: " + onboardedAgent;
		Markup g = MarkupHelper.createLabel(email1, ExtentColor.BLUE);
		testInfo.get().info(g);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/email")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/email")).sendKeys(onboardedAgent);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/submit")).click();
        Thread.sleep(500);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/alertTitle")));
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity:id/alertTitle", "Error");
        TestUtils.assertSearchText("ID", "android:id/message", "Agent already onboarded");
        getDriver().findElement(By.id("android:id/button1")).click();
	    Thread.sleep(500);
	    
		// To On-board agent without supplying email address
		String email2 = " To On-board agent without supplying email address";
		Markup v = MarkupHelper.createLabel(email2, ExtentColor.BLUE);
		testInfo.get().info(v);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/email")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/email")).sendKeys();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/submit")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/alertTitle")));
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity:id/alertTitle", "Error");
		TestUtils.assertSearchText("ID", "android:id/message", "Please enter a valid email address");
		getDriver().findElement(By.id("android:id/button1")).click();
		Thread.sleep(500);
        
		// To On-board user with an invalid email address
		String email3 = "To On-board user with an invalid email address: " + invalid_email;
		Markup b = MarkupHelper.createLabel(email3, ExtentColor.BLUE);
		testInfo.get().info(b);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/email")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/email")).sendKeys(invalid_email);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/submit")).click();
        Thread.sleep(500);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/alertTitle")));
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity:id/alertTitle", "Error");
        TestUtils.assertSearchText("ID", "android:id/message", "Invalid email address was entered.");
        getDriver().findElement(By.id("android:id/button1")).click();
	    Thread.sleep(500);
	    
		// To On-board new agent with an invalid OTP
		String email4 = "To On-board new agent " + agent_email + " with an invalid OTP: " + invalid_OTP;
		Markup h = MarkupHelper.createLabel(email4, ExtentColor.BLUE);
		testInfo.get().info(h);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/email")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/email")).sendKeys(agent_email);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/submit")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/otp")));
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity:id/alertTitle", "OTP verification");
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/otp")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/otp")).sendKeys(invalid_OTP);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/confirm_otp")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/alertTitle")));
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity:id/alertTitle", "Error");
		TestUtils.assertSearchText("ID", "android:id/message", "No match was found for the specified OTP.");
		getDriver().findElement(By.id("android:id/button1")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/cancel")));
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/cancel")).click();
		Thread.sleep(1000);
		
		String email6 = "To On-board new agent " + agent_email + " with an used OTP: " + used_OTP;
		Markup u = MarkupHelper.createLabel(email6, ExtentColor.BLUE);
		testInfo.get().info(u);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/email")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/email")).sendKeys(agent_email);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/submit")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/otp")));
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity:id/alertTitle", "OTP verification");
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/otp")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/otp")).sendKeys(used_OTP);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/confirm_otp")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/alertTitle")));
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity:id/alertTitle", "Error");
		TestUtils.assertSearchText("ID", "android:id/message", "No match was found for the specified OTP.");
		getDriver().findElement(By.id("android:id/button1")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/cancel")));
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/cancel")).click();
		Thread.sleep(1000);
		
        // Email Validation
		String email = "To On-board new agent with a valid OTP: " + agent_email;
		Markup d = MarkupHelper.createLabel(email, ExtentColor.BLUE);
		testInfo.get().info(d);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/email")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/email")).sendKeys(agent_email);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/submit")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/alertTitle")));
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity:id/alertTitle", "OTP verification");
        
        // DB Connection for OTP
    	String valid_OTP = ConnectDB.getOTP(agent_phoNum);

		String ValidOTP = "Enter valid OTP : " + valid_OTP;
		Markup o = MarkupHelper.createLabel(ValidOTP, ExtentColor.BLUE);
		testInfo.get().info(o);
        if(valid_OTP == null){
        	testInfo.get().log(Status.INFO, "Can't get otp.");
            getDriver().quit();
        }
        
        // OTP Validation
        Thread.sleep(2000);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/otp")));
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/otp")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/otp")).sendKeys(valid_OTP);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/confirm_otp")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/on_boarding_camera_title")));
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity:id/on_boarding_camera_title", "Camera");
        TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Capture Passport']", "Capture Passport");
        Thread.sleep(1000);
   }
}
