package com.seamfix.biosmart230;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

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

import utils.TestBase;
import utils.TestUtils;

public class Login extends TestBase {

	@Parameters({ "dataEnv"})
	@Test
	public void loginWithFingerprint(String dataEnv) throws FileNotFoundException, IOException, ParseException {
		WebDriverWait wait = new WebDriverWait(getDriver(), 50);
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resource/" + dataEnv + "/data.conf.json"));
		JSONObject envs = (JSONObject) config.get("Login");
		String onboarded_username = (String) envs.get("onboarded_username");
		String invalid_username = (String) envs.get("invalid_username");
		String not_onboarded_username = (String) envs.get("not_onboarded_username");
		String deactivated_username = (String) envs.get("deactivated_username");
		// Try to login with fingerprint
		String fpLogin = "Try to login with fingerprint";
		Markup m = MarkupHelper.createLabel(fpLogin, ExtentColor.BLUE);
		testInfo.get().info(m);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/finger_print")));
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/finger_print")).click();
		wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/page_sub_title")));
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity:id/page_sub_title", "Fingerprint");

		// Try to login with a user that does not exist
		String wrongUsername = "Try to login with username that does not exist" + "(" + invalid_username + ")";
		Markup n = MarkupHelper.createLabel(wrongUsername, ExtentColor.BLUE);
		testInfo.get().info(n);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/email")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/email"))
				.sendKeys(invalid_username);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/verify")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/message", "No agent was found with entered email address");
		getDriver().findElement(By.id("android:id/button1")).click();
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/email")));

		// Try to login with user that has not been onboarded
		String notOnboardedUsername = "Try to login with username that is not onboarded" + "(" + not_onboarded_username
				+ ")";
		Markup b = MarkupHelper.createLabel(notOnboardedUsername, ExtentColor.BLUE);
		testInfo.get().info(b);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/email")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/email"))
				.sendKeys(not_onboarded_username);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/verify")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/message", "Agent has not been onboarded yet");
		getDriver().findElement(By.id("android:id/button1")).click();
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/email")));

		// Try to login with user that has been blacklisted
		String deactivatedUsername = "Try to login with username that is blacklisted" + "(" + deactivated_username
				+ ")";
		Markup d = MarkupHelper.createLabel(deactivatedUsername, ExtentColor.BLUE);
		testInfo.get().info(d);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/email")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/email"))
				.sendKeys(deactivated_username);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/verify")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/message", "Your account is blacklisted. Please contact support");
		getDriver().findElement(By.id("android:id/button1")).click();
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/email")));

		// Try to login with an onboarded username
		String onboardedUsername = "Try to login with an onboarded username" + "(" + onboarded_username + ")";
		Markup u = MarkupHelper.createLabel(onboardedUsername, ExtentColor.BLUE);
		testInfo.get().info(u);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/email")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/email"))
				.sendKeys(onboarded_username);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/verify")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/finger_image")));
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity:id/fingerType_text", "VERIFICATION");
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity:id/btnStop", "Capture");
	}

	@Parameters({ "dataEnv"})
	@Test
	public void usernamePasswordTest(String dataEnv) throws InterruptedException, FileNotFoundException, IOException, ParseException {
		WebDriverWait wait = new WebDriverWait(getDriver(), 50);
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resource/" + dataEnv + "/data.conf.json"));
		JSONObject envs = (JSONObject) config.get("Login");

		String valid_username = (String) envs.get("valid_username");
		String invalid_password = (String) envs.get("invalid_password");
		String invalid_username = (String) envs.get("invalid_username");
		String valid_password = (String) envs.get("valid_password");
		String deactivated_username = (String) envs.get("deactivated_username");

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/otp_login")));
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/otp_login")).click();
		wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/login_username")));

		// Try to login with valid username and invalid password
		String validUsernameInvalidPassword = "Try to login with a valid username" + "(" + valid_username + ")"
				+ " and invalid password" + "(" + invalid_password + ")";
		Markup u = MarkupHelper.createLabel(validUsernameInvalidPassword, ExtentColor.BLUE);
		testInfo.get().info(u);

		getDriver().findElement(By.id("com.sf.biocapture.activity:id/login_username")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/login_username")).sendKeys(valid_username);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/login_password")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/login_password")).sendKeys(invalid_password);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/submit")).click();

		wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/title_template")));
		TestUtils.assertSearchText("ID", "android:id/message", "Invalid username or password entered.");
		getDriver().findElement(By.id("android:id/button1")).click();
		wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/login_username")));

		// Try to login with invalid username and valid password
		String invalidUsernameValidPassword = "Try to login with invalid username" + "(" + invalid_username + ")"
				+ "and valid password" + "(" + valid_password + ")";
		Markup p = MarkupHelper.createLabel(invalidUsernameValidPassword, ExtentColor.BLUE);
		testInfo.get().info(p);

		getDriver().findElement(By.id("com.sf.biocapture.activity:id/login_username")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/login_username")).sendKeys(invalid_username);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/login_password")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/login_password")).sendKeys(valid_password);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/submit")).click();

		wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/title_template")));
		TestUtils.assertSearchText("ID", "android:id/message", "Invalid username or password entered.");
		getDriver().findElement(By.id("android:id/button1")).click();
		wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/login_username")));

		// Try to login with empty username and empty password
		String emptyUsernameAndPassword = "Try to login with an empty username" + "()" + " and empty password" + "()";
		Markup e = MarkupHelper.createLabel(emptyUsernameAndPassword, ExtentColor.BLUE);
		testInfo.get().info(e);

		getDriver().findElement(By.id("com.sf.biocapture.activity:id/login_username")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/login_username")).sendKeys("");
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/login_password")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/login_password")).sendKeys("");
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/submit")).click();

		wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/title_template")));
		TestUtils.assertSearchText("ID", "android:id/message", "Required Input Field: email");
		getDriver().findElement(By.id("android:id/button1")).click();
		wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/login_username")));

		// Try to login with deactivated username and valid password
		String deactivatedUsernameAndValidPassword = "Try to login with deactivated username" + "("
				+ deactivated_username + ")" + " and valid password" + "(" + valid_password + ")";
		Markup d = MarkupHelper.createLabel(deactivatedUsernameAndValidPassword, ExtentColor.BLUE);
		testInfo.get().info(d);

		getDriver().findElement(By.id("com.sf.biocapture.activity:id/login_username")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/login_username")).sendKeys(deactivated_username);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/login_password")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/login_password")).sendKeys(valid_password);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/submit")).click();

		wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/title_template")));
		TestUtils.assertSearchText("ID", "android:id/message", "Your account was deactivated. Please contact support");
		getDriver().findElement(By.id("android:id/button1")).click();
		wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/login_username")));

		// Try to login with valid username and valid password
		String validUsernameValidPassword = "Try to login with a valid username" + "(" + valid_username + ")"
				+ "and valid password" + "(" + valid_password + ")";
		Markup v = MarkupHelper.createLabel(validUsernameValidPassword, ExtentColor.BLUE);
		testInfo.get().info(v);

		getDriver().findElement(By.id("com.sf.biocapture.activity:id/login_username")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/login_username")).sendKeys(valid_username);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/login_password")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/login_password")).sendKeys(valid_password);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/submit")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Home']")));
		TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Home']", "Home");

		// Try to log out
		String logOut = "Try to logout" + "(" + valid_username + ")";
		Markup o = MarkupHelper.createLabel(logOut, ExtentColor.BLUE);
		testInfo.get().info(o);
		getDriver().findElement(By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']")).click();
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/design_menu_item_text")));
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Logout']")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/message")));
		TestUtils.assertSearchText("ID", "android:id/message", "   Log out?");
		getDriver().findElement(By.id("android:id/button3")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/otp_login")));

	}

}
