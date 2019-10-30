package com.seamfix.biosmart230;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import io.appium.java_client.android.AndroidKeyCode;
import utils.TestBase;
import utils.TestUtils;

public class Login extends TestBase {

	@Parameters({ "dataEnv"})
	@Test(groups = { "Regression", ""})
	public void loginWithFingerprint(String dataEnv) throws Exception {
		WebDriverWait wait = new WebDriverWait(getDriver(), 50);
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resource/" + dataEnv + "/data.conf.json"));
		JSONObject envs = (JSONObject) config.get("Login");
		String onboarded_username = (String) envs.get("onboarded_username");
		String invalid_username = (String) envs.get("invalid_username");
		String not_onboarded_username = (String) envs.get("not_onboarded_username");
		String blacklisted_username = (String) envs.get("blacklisted_username");
		String deactivated_username = (String) envs.get("deactivated_username");

		// Login with fingerprint
		String fpLogin = "Login with fingerprint";
		Markup m = MarkupHelper.createLabel(fpLogin, ExtentColor.BLUE);
		testInfo.get().info(m);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/finger_print")));
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/finger_print")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/page_sub_title")));
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity:id/page_sub_title", "Fingerprint");

		// Login with a user that does not exist
		String wrongUsername = "Login with non existing user " + "(" + invalid_username + ")";
		Markup n = MarkupHelper.createLabel(wrongUsername, ExtentColor.BLUE);
		testInfo.get().info(n);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/email")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/email")).sendKeys(invalid_username);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/verify")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/message", "No agent was found with entered email address");
		getDriver().findElement(By.id("android:id/button1")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/email")));

		// Login with user that has not been onboarded
		String notOnboardedUsername = "Login with username that is not onboarded" + "(" + not_onboarded_username + ")";
		Markup b = MarkupHelper.createLabel(notOnboardedUsername, ExtentColor.BLUE);
		testInfo.get().info(b);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/email")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/email")).sendKeys(not_onboarded_username);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/verify")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/message", "Agent has not been onboarded yet");
		getDriver().findElement(By.id("android:id/button1")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/email")));

		// Login with user that has been blacklisted
		String blacklistedUsername = "Login with blacklisted user " + "(" + blacklisted_username + ")";
		Markup d = MarkupHelper.createLabel(blacklistedUsername, ExtentColor.BLUE);
		testInfo.get().info(d);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/email")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/email")).sendKeys(blacklisted_username);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/verify")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/message", "Your account is blacklisted. Please contact support");
		getDriver().findElement(By.id("android:id/button1")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/email")));

		// Login with user that has been deactivated
		String deactivatedUsername = "Login with deactivated user " + "(" + deactivated_username + ")";
		Markup s = MarkupHelper.createLabel(deactivatedUsername, ExtentColor.BLUE);
		testInfo.get().info(s);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/email")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/email")).sendKeys(deactivated_username);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/verify")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/message", "Your Account has been deactivated");
		getDriver().findElement(By.id("android:id/button1")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/email")));
		
		// Login with an onboarded username
		String onboardedUsername = "Login with an onboarded user " + "(" + onboarded_username + ")";
		Markup u = MarkupHelper.createLabel(onboardedUsername, ExtentColor.BLUE);
		testInfo.get().info(u);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/email")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/email")).sendKeys(onboarded_username);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/verify")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/alertTitle")));
		Thread.sleep(500);
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity:id/alertTitle", "Scanner not found");
		getDriver().findElement(By.id("android:id/button1")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/finger_image")));
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity:id/fingerType_text", "VERIFICATION");
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity:id/btnStop", "Capture");
	}

	@Parameters({ "dataEnv"})
	@Test (groups = {"Regression"})
	public void usernamePasswordTest(String dataEnv) throws InterruptedException, FileNotFoundException, IOException, ParseException {
		WebDriverWait wait = new WebDriverWait(getDriver(), 50);
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resource/" + dataEnv + "/data.conf.json"));
		JSONObject envs = (JSONObject) config.get("Login");

		String valid_username = (String) envs.get("valid_username");
		String invalid_password = (String) envs.get("invalid_password");
		String invalid_username = (String) envs.get("invalid_username");
		String valid_password = (String) envs.get("valid_password");
		String blacklisted_username = (String) envs.get("blacklisted_username");
		String deactivated_username = (String) envs.get("deactivated_username");
		String invalid_email_format = (String) envs.get("invalid_email_format");
		String AD_UserName = (String) envs.get("AD_UserName");
		String valid_AD_pw = (String) envs.get("valid_AD_pw");
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/otp_login")));
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/otp_login")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/login_username")));

		// Select Login mode
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/login_mode_types_spinner")).click();
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Biosmart']")).click();
		Thread.sleep(500);
		
		// Login with valid username and invalid password
		String validUsernameInvalidPassword = "Login with a valid username " + "(" + valid_username + ")" + " and invalid password " + "(" + invalid_password + ")";
		Markup u = MarkupHelper.createLabel(validUsernameInvalidPassword, ExtentColor.BLUE);
		testInfo.get().info(u);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/login_username")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/login_username")).sendKeys(valid_username);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/login_password")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/login_password")).sendKeys(invalid_password);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/submit")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/title_template")));
		TestUtils.assertSearchText("ID", "android:id/message", "Invalid username or password entered.");
		getDriver().findElement(By.id("android:id/button1")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/login_username")));

		// Login with invalid username and valid password
		String invalidUsernameValidPassword = "Login with invalid username " + "(" + invalid_username + ")" + " and valid password " + "(" + valid_password + ")";
		Markup p = MarkupHelper.createLabel(invalidUsernameValidPassword, ExtentColor.BLUE);
		testInfo.get().info(p);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/login_username")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/login_username")).sendKeys(invalid_username);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/login_password")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/login_password")).sendKeys(valid_password);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/submit")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/title_template")));
		TestUtils.assertSearchText("ID", "android:id/message", "Invalid username or password entered.");
		getDriver().findElement(By.id("android:id/button1")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/login_username")));
		
		// Login with invalid Email Format and valid password
		String invalidEmailFormat = "Login with invalid username " + "(" + invalid_email_format + ")" + " and valid password " + "(" + valid_password + ")";
		Markup b = MarkupHelper.createLabel(invalidEmailFormat, ExtentColor.BLUE);
		testInfo.get().info(b);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/login_username")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/login_username")).sendKeys(invalid_email_format);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/login_password")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/login_password")).sendKeys(valid_password);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/submit")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/title_template")));
		TestUtils.assertSearchText("ID", "android:id/message", "Please enter a valid Username format (AD Username or email)");
		getDriver().findElement(By.id("android:id/button1")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/login_username")));

		// Login with empty username and empty password
		String emptyUsernameAndPassword = "Login with an empty username " + "()" + " and empty password " + "()";
		Markup e = MarkupHelper.createLabel(emptyUsernameAndPassword, ExtentColor.BLUE);
		testInfo.get().info(e);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/login_username")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/login_username")).sendKeys("");
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/login_password")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/login_password")).sendKeys("");
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/submit")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/title_template")));
		TestUtils.assertSearchText("ID", "android:id/message", "Required Input Field: email");
		getDriver().findElement(By.id("android:id/button1")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/login_username")));

		// Login with deactivated username and valid password
		String deactivatedUsernameAndValidPassword = "Login with deactivated username " + "(" + deactivated_username + ")" + " and valid password " + "(" + valid_password + ")";
		Markup d = MarkupHelper.createLabel(deactivatedUsernameAndValidPassword, ExtentColor.BLUE);
		testInfo.get().info(d);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/login_username")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/login_username")).sendKeys(deactivated_username);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/login_password")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/login_password")).sendKeys(valid_password);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/submit")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/title_template")));
		TestUtils.assertSearchText("ID", "android:id/message", "Your account was deactivated. Please contact support");
		getDriver().findElement(By.id("android:id/button1")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/login_username")));
		
		// Login with blacklisted username and valid password
		String blacklistedUsernameAndValidPassword = "Login with blacklisted username " + "(" + blacklisted_username	+ ")" + " and valid password " + "(" + valid_password + ")";
		Markup y = MarkupHelper.createLabel(blacklistedUsernameAndValidPassword, ExtentColor.BLUE);
		testInfo.get().info(y);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/login_username")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/login_username")).sendKeys(blacklisted_username);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/login_password")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/login_password")).sendKeys(valid_password);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/submit")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/title_template")));
		TestUtils.assertSearchText("ID", "android:id/message", "Your account is blacklisted. Please contact support");
		getDriver().findElement(By.id("android:id/button1")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/login_username")));

		// Login with valid username and valid password
		String validUsernameValidPassword = "Login with a valid username " + "(" + valid_username + ")" + " and valid password " + "(" + valid_password + ")";
		Markup v = MarkupHelper.createLabel(validUsernameValidPassword, ExtentColor.BLUE);
		testInfo.get().info(v);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/login_username")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/login_username")).sendKeys(valid_username);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/login_password")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/login_password")).sendKeys(valid_password);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/submit")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Home']")));
		TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Home']", "Home");

		// Log out
		String logOut = "Logout " + "(" + valid_username + ")";
		Markup o = MarkupHelper.createLabel(logOut, ExtentColor.BLUE);
		testInfo.get().info(o);
		getDriver().findElement(By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/design_menu_item_text")));
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Logout']")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/message")));
		TestUtils.assertSearchText("ID", "android:id/message", "   Log out?");
		getDriver().findElement(By.id("android:id/button3")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/otp_login")));
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/otp_login")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/login_username")));
		Thread.sleep(500);
		
		// Login with valid username and valid password
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/login_mode_types_spinner")).click();
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Active Directory']")).click();
		Thread.sleep(500);
		String activeDirectoryValidLogin = "Login with a valid Active Directory username " + "(" + AD_UserName + ")" + " and valid password " + "(" + valid_AD_pw + ")";
		Markup x = MarkupHelper.createLabel(activeDirectoryValidLogin, ExtentColor.BLUE);
		testInfo.get().info(x);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/login_username")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/login_username")).sendKeys(AD_UserName);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/login_password")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/login_password")).sendKeys(valid_AD_pw);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/submit")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Home']")));
		TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Home']", "Home");
		Thread.sleep(500);
		
		// Log out
		String logOut1 = "Logout " + "(" + AD_UserName + ")";
		Markup r = MarkupHelper.createLabel(logOut1, ExtentColor.BLUE);
		testInfo.get().info(r);
		getDriver().findElement(By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/design_menu_item_text")));
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Logout']")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/message")));
		TestUtils.assertSearchText("ID", "android:id/message", "   Log out?");
		getDriver().findElement(By.id("android:id/button3")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/otp_login")));
		Thread.sleep(500);
	}
	
	@Parameters ({"dataEnv"})
	@Test (groups = { "Regression" })
	public void erorrMessagesValidationTest(String dataEnv) throws InterruptedException, FileNotFoundException, IOException, ParseException {
		WebDriverWait wait = new WebDriverWait(getDriver(), 60);
		
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resource/" + dataEnv + "/data.conf.json"));
		JSONObject envs = (JSONObject) config.get("Login");

		String AD_UserName = (String) envs.get("AD_UserName");
		String valid_AD_pw = (String) envs.get("valid_AD_pw");
		String AD_UserName1 = (String) envs.get("AD_UserName1");
		String BS_email = (String) envs.get("BS_email");
		String BS_email1 = (String) envs.get("BS_email1");
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/otp_login")));
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/otp_login")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/login_username")));
		Thread.sleep(500);
		
		// Login with Active Directory user but select Biosmart as Auth mode and biosmart email
		String log = "Login with Active Directory user but select Biosmart as Auth mode and biosmart email: " + BS_email;
		Markup v = MarkupHelper.createLabel(log, ExtentColor.BLUE);
		testInfo.get().info(v);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/login_mode_types_spinner")).click();
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Biosmart']")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/login_username")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/login_username")).sendKeys(BS_email);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/login_password")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/login_password")).sendKeys(valid_AD_pw);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/submit")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/title_template")));
		TestUtils.assertSearchText("ID", "android:id/message", "Your allowed Login Mode is Active Directory. Your AD Username is; " + AD_UserName);
		getDriver().findElement(By.id("android:id/button1")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/login_username")));
		
		// Login with Active Directory user but select Biosmart as Auth mode and Active Directory user name
		String log1 = "Login with Active Directory user but select Biosmart as Auth mode and Active Directory user name: " + AD_UserName;
		Markup w = MarkupHelper.createLabel(log1, ExtentColor.BLUE);
		testInfo.get().info(w);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/login_mode_types_spinner")).click();
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Biosmart']")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/login_username")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/login_username")).sendKeys(AD_UserName);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/login_password")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/login_password")).sendKeys(valid_AD_pw);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/submit")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/title_template")));
		TestUtils.assertSearchText("ID", "android:id/message", "Your allowed Login Mode is Active Directory. Your AD Username is; " + AD_UserName);
		getDriver().findElement(By.id("android:id/button1")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/login_username")));
		
		// Login with Active Directory user but select Active Directory as Auth mode and Biosmart email
		String log2 = "Login with Active Directory user but select Active Directory as Auth mode and Biosmart email: " + BS_email;
		Markup d = MarkupHelper.createLabel(log2, ExtentColor.BLUE);
		testInfo.get().info(d);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/login_mode_types_spinner")).click();
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Active Directory']")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/login_username")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/login_username")).sendKeys(BS_email);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/login_password")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/login_password")).sendKeys(valid_AD_pw);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/submit")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/title_template")));
		TestUtils.assertSearchText("ID", "android:id/message", "Your allowed Login Mode is Active Directory. Your AD Username is; " + AD_UserName);
		getDriver().findElement(By.id("android:id/button1")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/login_username")));
		
		// Login with Biosmart user but select Active Directory as Auth mode and Active Directory username
		String log3 = "Login with Biosmart user but select Active Directory as Auth mode and Active Directory username: " + AD_UserName1;
		Markup g = MarkupHelper.createLabel(log3, ExtentColor.BLUE);
		testInfo.get().info(g);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/login_mode_types_spinner")).click();
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Active Directory']")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/login_username")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/login_username")).sendKeys(AD_UserName1);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/login_password")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/login_password")).sendKeys(valid_AD_pw);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/submit")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/title_template")));
		TestUtils.assertSearchText("ID", "android:id/message", "Your allowed Login Mode is Biosmart. Your email address is; " + BS_email1);
		getDriver().findElement(By.id("android:id/button1")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/login_username")));
		
		// Login with Biosmart user but select Active Directory as Auth mode and Biosmart email
		String log4 = "Login with Biosmart user but select Active Directory as Auth mode and Biosmart email: " + BS_email1;
		Markup s = MarkupHelper.createLabel(log4, ExtentColor.BLUE);
		testInfo.get().info(s);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/login_mode_types_spinner")).click();
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Active Directory']")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/login_username")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/login_username")).sendKeys(BS_email1);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/login_password")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/login_password")).sendKeys(valid_AD_pw);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/submit")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/title_template")));
		TestUtils.assertSearchText("ID", "android:id/message", "Your allowed Login Mode is Biosmart. Your email address is; " + BS_email1);
		getDriver().findElement(By.id("android:id/button1")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/login_username")));
		
		// Login with Biosmart user but select Biosmart as Auth mode and Active Directory username
		String log5 = "Login with Biosmart user but select Biosmart as Auth mode and Active Directory username: " + AD_UserName1;
		Markup a = MarkupHelper.createLabel(log5, ExtentColor.BLUE);
		testInfo.get().info(a);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/login_mode_types_spinner")).click();
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Biosmart']")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/login_username")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/login_username")).sendKeys(AD_UserName1);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/login_password")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/login_password")).sendKeys(valid_AD_pw);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/submit")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/title_template")));
		TestUtils.assertSearchText("ID", "android:id/message", "Your allowed Login Mode is Biosmart. Your email address is; " + BS_email1);
		getDriver().findElement(By.id("android:id/button1")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/login_username")));
		
		// Go back
		getDriver().pressKeyCode(AndroidKeyCode.BACK);
	}

}
