package admin;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
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

public class ForgotPassword extends TestBase {
    public static String new_password;
	@Test
	public static void navigateToForgotPasswordTest() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), 60);
		String forgotPasswordPage = "Navigate to forgot password page";
		Markup m = MarkupHelper.createLabel(forgotPasswordPage, ExtentColor.BLUE);
		testInfo.get().info(m);

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/otp_login")));
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/otp_login")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/password_reset")));
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/password_reset")).click();
		TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='FORGOT PASSWORD']", "FORGOT PASSWORD");
		Thread.sleep(500);
	}

	@Parameters({ "dataEnv"})
	@Test
	public static void changePasswordWithInvalidUsernameTest(String dataEnv)
			throws InterruptedException, SQLException, FileNotFoundException, IOException, ParseException {
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resource/" + dataEnv + "/data.conf.json"));
		JSONObject envs = (JSONObject) config.get("ForgotPassword");

		String invalid_username = (String) envs.get("invalid_username");
		String invalid_email_format  = (String) envs.get("invalid_email_format");
		WebDriverWait wait = new WebDriverWait(getDriver(), 60);

		// Select Login mode
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/login_mode_types_spinner")).click();
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Biosmart']")).click();
		Thread.sleep(500);

		// Change password with invalid username
		String invalidUsername = "Change password with invalid username: " + invalid_username;
		Markup m = MarkupHelper.createLabel(invalidUsername, ExtentColor.BLUE);
		testInfo.get().info(m);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/login_username")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/login_username")).sendKeys(invalid_username);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/send")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/message", "No agent found");
		getDriver().findElement(By.id("android:id/button1")).click();
		Thread.sleep(500);

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/password_reset")));
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/password_reset")).click();
		Thread.sleep(500);

		// Change password with invalid email format
		String invalidUsername1 = "Change password with invalid email format: " + invalid_email_format;
		Markup c = MarkupHelper.createLabel(invalidUsername1, ExtentColor.BLUE);
		testInfo.get().info(c);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/login_username")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/login_username")).sendKeys(invalid_email_format);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/send")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/message", "Please enter a valid User ID or Please enter a valid Email address");
		getDriver().findElement(By.id("android:id/button1")).click();
		Thread.sleep(500);
	}

	@Parameters({ "dataEnv"})
	@Test
	public static void changePasswordWithValidUsernameTest(String dataEnv)
			throws InterruptedException, SQLException, FileNotFoundException, IOException, ParseException {
		WebDriverWait wait = new WebDriverWait(getDriver(), 60);
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resource/" + dataEnv + "/data.conf.json"));
		JSONObject envs = (JSONObject) config.get("ForgotPassword");

		String valid_username = (String) envs.get("valid_username");
		/*String new_password = (String) envs.get("new_password");
		String confirm_password = (String) envs.get("confirm_password");*/
		new_password = "Bankole@"+TestUtils.generatePhoneNumber();
		String confirm_password = new_password;
		String confirm_password_not_matching = (String) envs.get("confirm_password_not_matching");
		String invalid_password_policy = (String) envs.get("invalid_password_policy");
		String user_full_name = (String) envs.get("user_full_name");
		String user_phoneNumber = (String) envs.get("user_phoneNumber");

		// Change password with valid username
		String validUsername = "Change password with valid username: " + valid_username;
		Markup m = MarkupHelper.createLabel(validUsername, ExtentColor.BLUE);
		testInfo.get().info(m);

		// Select Login mode
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/login_mode_types_spinner")).click();
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Biosmart']")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/login_username")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/login_username")).sendKeys(valid_username);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/send")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/dialog_title")));
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/dialog_title", "OTP verification");
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/dialog_message",
				"Enter One Time Password sent to : " + user_phoneNumber.substring(0,3) + "*****" + user_phoneNumber.substring(8,11));

		// Test with invalid OTP
		String invalid_OTP = (String) envs.get("invalid_OTP");
		String inValidOTP = "Enter OTP that does not exist: " + invalid_OTP;
		Markup otp = MarkupHelper.createLabel(inValidOTP, ExtentColor.BLUE);
		testInfo.get().info(otp);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/user_input_dialog")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/user_input_dialog")).sendKeys(invalid_OTP);
		getDriver().findElement(By.id("android:id/button1")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/alertTitle", "Error");
		TestUtils.assertSearchText("ID", "android:id/message", "There is no record with the otp, msisdn combination.");
		getDriver().findElement(By.id("android:id/button1")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/dialog_title")));

		// DB Connection for OTP
		String valid_OTP = ConnectDB.getOTP(user_phoneNumber);

		String ValidOTP = "Enter valid OTP : " + valid_OTP;
		Markup o = MarkupHelper.createLabel(ValidOTP, ExtentColor.BLUE);
		testInfo.get().info(o);
		if(valid_OTP == null){
			testInfo.get().log(Status.INFO, "Can't get otp.");
			getDriver().quit();
		}
		Thread.sleep(1000);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/user_input_dialog")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/user_input_dialog")).sendKeys(valid_OTP);
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/user_input_dialog", valid_OTP);
		getDriver().findElement(By.id("android:id/button1")).click();

		// Assert the ChangePassword modal
		String assertChangeModal = "Assert the Change Password modal";
		TestUtils.testTitle(assertChangeModal);

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/title")));
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/title", "CHANGE PASSWORD");
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/change_password_title", "Change Password");
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/change_password_guide",
				"Password must contain at least 8 Characters and at most 20 characters with at least 1 LowerCase, 1 UpperCase, 1 Number, and 1 Symbol");
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/textView3", "New password");
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/textView4", "Confirm New password");
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/cancel", "CANCEL");
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/update_pwd", "CHANGE");

		// Change password with an invalid password policy
		String invalidPasswordPolicy = "Change password with invalid password policy: "
				+ invalid_password_policy;
		Markup ip = MarkupHelper.createLabel(invalidPasswordPolicy, ExtentColor.BLUE);
		testInfo.get().info(ip);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/new_password")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/new_password")).sendKeys(invalid_password_policy);

		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/confirm_password")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/confirm_password"))
				.sendKeys(invalid_password_policy);

		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/update_pwd")).click();

		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/alertTitle", "Error");
		TestUtils.assertSearchText("ID", "android:id/message",
				"Entered password does not match the password policy, please try again.");
		TestUtils.assertSearchText("ID", "android:id/button1", "OK");
		getDriver().findElement(By.id("android:id/button1")).click();

		// Change password when the new password doesnt match confirm old password
		String notMatchingConfirmPassword = "Change password with confirm password" + "("
				+ confirm_password_not_matching + " )" + "not matching new password (" + new_password + ")";
		Markup pa = MarkupHelper.createLabel(notMatchingConfirmPassword, ExtentColor.BLUE);
		testInfo.get().info(pa);
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/change_password_title")));
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/new_password")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/new_password")).sendKeys(new_password);

		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/confirm_password")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/confirm_password"))
				.sendKeys(confirm_password_not_matching);

		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/update_pwd")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/message")));

		TestUtils.assertSearchText("ID", "android:id/message", "Entered passwords do not match");
		TestUtils.assertSearchText("ID", "android:id/button1", "OK");
		getDriver().findElement(By.id("android:id/button1")).click();

		// Change password with a valid password policy
		String validPassword = "Change password with valid Password Policy: " + new_password;
		Markup pass = MarkupHelper.createLabel(validPassword, ExtentColor.BLUE);
		testInfo.get().info(pass);
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/change_password_title")));
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/new_password")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/new_password")).sendKeys(new_password);

		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/confirm_password")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/confirm_password")).sendKeys(confirm_password);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/update_pwd")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/alertTitle")));
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity.glo:id/alertTitle", "Password Update");
		TestUtils.assertSearchText("ID", "android:id/message", "Password change successful");
		getDriver().findElement(By.id("android:id/button1")).click();

	}

	@Parameters({ "dataEnv"})
	@Test
	public static void loginWithNewPasswordTest(String dataEnv)
			throws InterruptedException, FileNotFoundException, IOException, ParseException, SQLException {
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resource/" + dataEnv + "/data.conf.json"));
		JSONObject envs = (JSONObject) config.get("ForgotPassword");

		String valid_username = (String) envs.get("valid_username");
		String user_phoneNumber = (String) envs.get("user_phoneNumber");

		// Login in with the new password
		String login = "Login with the newly changed password: " + new_password;
		Markup pass = MarkupHelper.createLabel(login, ExtentColor.BLUE);
		testInfo.get().info(pass);
		WebDriverWait wait = new WebDriverWait(getDriver(), 50);

		// Select Login mode
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/login_mode_types_spinner")).click();
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Biosmart']")).click();
		Thread.sleep(500);

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/login_username")));
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/login_username")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/login_username")).sendKeys(valid_username);
		getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/login_password")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/login_password")).sendKeys(new_password);
		getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/submit")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Home']")));
		TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Home']", "Home");

		// Log out
		TestUtils.testTitle("Logout " + "(" + valid_username + ")");
		getDriver().findElement(By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/design_menu_item_text")));
		TestUtils.scrollDown();
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Logout']")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/message")));
		TestUtils.assertSearchText("ID", "android:id/message", "   Log out?");
		getDriver().findElement(By.id("android:id/button3")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/otp_login")));
		Thread.sleep(500);
	}

	@Parameters ({"dataEnv"})
	@Test (groups = { "Regression" })
	public void erorrMessagesValidationTest(String dataEnv) throws InterruptedException, FileNotFoundException, IOException, ParseException {
		WebDriverWait wait = new WebDriverWait(getDriver(), 60);

		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resource/" + dataEnv + "/data.conf.json"));
		JSONObject envs = (JSONObject) config.get("ForgotPassword");

		String AD_UserName = (String) envs.get("AD_UserName");
		String AD_UserName1 = (String) envs.get("AD_UserName1");
		String BS_email = (String) envs.get("BS_email");
		String BS_email1 = (String) envs.get("BS_email1");

		// Change password for Active Directory user but select Biosmart as Auth mode and biosmart email
		String log = "Change password for Active Directory user but select Biosmart as Auth mode and biosmart email: " + BS_email;
		Markup v = MarkupHelper.createLabel(log, ExtentColor.BLUE);
		testInfo.get().info(v);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/otp_login")));
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/otp_login")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/password_reset")));
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/password_reset")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/login_mode_types_spinner")).click();
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Biosmart']")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/login_username")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/login_username")).sendKeys(BS_email);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/send")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/message", "No agent found");
		getDriver().findElement(By.id("android:id/button1")).click();
		Thread.sleep(500);

		// Change password for Active Directory user but select Biosmart as Auth mode and Active Directory user name
		String log1 = "Change password for Active Directory user but select Biosmart as Auth mode and Active Directory user name: " + AD_UserName;
		Markup w = MarkupHelper.createLabel(log1, ExtentColor.BLUE);
		testInfo.get().info(w);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/password_reset")));
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/password_reset")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/login_mode_types_spinner")).click();
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Biosmart']")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/login_username")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/login_username")).sendKeys(AD_UserName);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/send")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/message", "Please enter a valid User ID or Please enter a valid Email address");
		getDriver().findElement(By.id("android:id/button1")).click();
		Thread.sleep(500);

		// Change password for Active Directory user but select Active Directory as Auth mode and Biosmart email
		String log2 = "Change password for Active Directory user but select Active Directory as Auth mode and Biosmart email: " + BS_email;
		Markup d = MarkupHelper.createLabel(log2, ExtentColor.BLUE);
		testInfo.get().info(d);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/send")));
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/login_mode_types_spinner")).click();
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Active Directory']")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/login_username")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/login_username")).sendKeys(BS_email);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/send")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/message", "No agent found");
		getDriver().findElement(By.id("android:id/button1")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/password_reset")));
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/password_reset")).click();
		Thread.sleep(500);

		// Change password for Biosmart user but select Active Directory as Auth mode and Active Directory username
		String log3 = "Change password for Biosmart user but select Active Directory as Auth mode and Active Directory username: " + AD_UserName1;
		Markup g = MarkupHelper.createLabel(log3, ExtentColor.BLUE);
		testInfo.get().info(g);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/login_mode_types_spinner")).click();
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Active Directory']")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/login_username")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/login_username")).sendKeys(AD_UserName1);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/send")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/message", "No agent found");
		getDriver().findElement(By.id("android:id/button1")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/password_reset")));
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/password_reset")).click();
		Thread.sleep(500);

		// Change password for Biosmart user but select Active Directory as Auth mode and Biosmart email
		String log4 = "Change password for Biosmart user but select Active Directory as Auth mode and Biosmart email: " + BS_email1;
		Markup s = MarkupHelper.createLabel(log4, ExtentColor.BLUE);
		testInfo.get().info(s);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/login_mode_types_spinner")).click();
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Active Directory']")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/login_username")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/login_username")).sendKeys(BS_email1);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/send")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/message", "No agent found");
		getDriver().findElement(By.id("android:id/button1")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/password_reset")));
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/password_reset")).click();
		Thread.sleep(500);

		// Change password for Biosmart user but select Biosmart as Auth mode and Active Directory username
		String log5 = "Change password for Biosmart user but select Biosmart as Auth mode and Active Directory username: " + AD_UserName1;
		Markup a = MarkupHelper.createLabel(log5, ExtentColor.BLUE);
		testInfo.get().info(a);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/login_mode_types_spinner")).click();
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Biosmart']")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/login_username")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/login_username")).sendKeys(AD_UserName1);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/send")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/message", "Please enter a valid User ID or Please enter a valid Email address");
		getDriver().findElement(By.id("android:id/button1")).click();
		Thread.sleep(500);
	}

}
