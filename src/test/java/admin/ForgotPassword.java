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
import org.testng.annotations.Test;

import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import db.ConnectDB;
import utils.TestBase;
import utils.TestUtils;

public class ForgotPassword extends TestBase {

	@Test

	public static void navigateToForgotPassword() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), 30);
		String forgotPasswordPage = "Try to navigate to forgot password page";
		Markup m = MarkupHelper.createLabel(forgotPasswordPage, ExtentColor.BLUE);
		testInfo.get().info(m);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/otp_login")));

		getDriver().findElement(By.id("com.sf.biocapture.activity:id/otp_login")).click();

		wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/password_reset")));

		getDriver().findElement(By.id("com.sf.biocapture.activity:id/password_reset")).click();
		testInfo.get().info("Successful landing to Forgot Password page");
	}

	@Test
	public static void changePasswordWithInvalidUsername()
			throws InterruptedException, SQLException, FileNotFoundException, IOException, ParseException {
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser
				.parse(new FileReader("src/test/resource/config/data.config.json"));

		String invalid_username = (String) config.get("invalid_username");
		WebDriverWait wait = new WebDriverWait(getDriver(), 50);

		// Trying to change password with invalid username
		String invalidUsername = "Try to change password with invalid username: " + invalid_username;
		Markup m = MarkupHelper.createLabel(invalidUsername, ExtentColor.BLUE);
		testInfo.get().info(m);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/email")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/email")).sendKeys(invalid_username);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/send")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("android:id/body")));

		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity:id/alertTitle", "Error");
		TestUtils.assertSearchText("ID", "android:id/message", "No agent found");
		TestUtils.assertSearchText("ID", "android:id/button1", "Ok");
		getDriver().findElement(By.id("android:id/button1")).click();
		wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/password_reset")));
	}

	@Test
	public static void changePasswordWithValidUsername()
			throws InterruptedException, SQLException, FileNotFoundException, IOException, ParseException {
		WebDriverWait wait = new WebDriverWait(getDriver(), 50);
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser
				.parse(new FileReader("src/test/resource/config/data.config.json"));

		String valid_username = (String) config.get("valid_username");
		String new_password = (String) config.get("new_password");
		String confirm_password = (String) config.get("confirm_password");
		String confirm_password_not_matching = (String) config.get("confirm_password_not_matching");
		String invalid_password_policy = (String) config.get("invalid_password_policy");

		// Trying to change password with valid username
		String validUsername = "Try to change password with valid username: " + valid_username;
		Markup m = MarkupHelper.createLabel(validUsername, ExtentColor.BLUE);
		testInfo.get().info(m);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/password_reset")).click();

		getDriver().findElement(By.id("com.sf.biocapture.activity:id/email")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/email")).sendKeys(valid_username);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/send")).click();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/dialog_title")));
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity:id/dialog_title", "OTP verification");
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity:id/dialog_message",
				"Enter One Time Password sent to : 07034154347");

		// Trying to test with invalid OTP
		String invalid_OTP = (String) config.get("invalid_OTP");
		;
		String inValidOTP = "Try to enter OTP that does not exist: " + invalid_OTP;
		Markup otp = MarkupHelper.createLabel(inValidOTP, ExtentColor.BLUE);
		testInfo.get().info(otp);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/user_input_dialog")).clear();

		getDriver().findElement(By.id("com.sf.biocapture.activity:id/user_input_dialog")).sendKeys(invalid_OTP);
		getDriver().findElement(By.id("android:id/button1")).click();
		TestUtils.assertSearchText("ID", "android:id/message", "No match was found for the specified OTP.");
		getDriver().findElement(By.id("android:id/button1")).click();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/dialog_title")));

		// Try to test with valid OTP
		String valid_OTP = ConnectDB.getOTP("07034154347");

		String ValidOTP = "Try to enter valid OTP : " + valid_OTP;
		Markup o = MarkupHelper.createLabel(ValidOTP, ExtentColor.BLUE);
		testInfo.get().info(o);

		getDriver().findElement(By.id("com.sf.biocapture.activity:id/user_input_dialog")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/user_input_dialog")).sendKeys(valid_OTP);
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity:id/user_input_dialog", valid_OTP);
		getDriver().findElement(By.id("android:id/button1")).click();

		// Trying to assert the ChangePassword modal
		String assertChangeModal = "Try to assert the Change Password modal";
		Markup mark = MarkupHelper.createLabel(assertChangeModal, ExtentColor.BLUE);
		testInfo.get().info(mark);

		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity:id/title", "CHANGE PASSWORD");
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity:id/change_password_title", "Change Password");
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity:id/change_password_guide",
				"Password must contain at least 10 Characters with at least 1 LowerCase, 1 UpperCase, 1 Number, and 1 Symbol");
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity:id/agent_full_name", "Lizzy Ikhile");
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity:id/textView3", "New password");
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity:id/textView4", "Confirm New password");
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity:id/cancel", "Cancel");
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity:id/update_pwd", "Change");

		// Trying to change password with an invalid password policy
		String invalidPasswordPolicy = "Try to change password with invalid password policy: "
				+ invalid_password_policy;
		Markup ip = MarkupHelper.createLabel(invalidPasswordPolicy, ExtentColor.BLUE);
		testInfo.get().info(ip);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/new_password")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/new_password")).sendKeys(invalid_password_policy);

		getDriver().findElement(By.id("com.sf.biocapture.activity:id/confirm_password")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/confirm_password"))
				.sendKeys(invalid_password_policy);

		getDriver().findElement(By.id("com.sf.biocapture.activity:id/update_pwd")).click();

		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity:id/alertTitle", "Error");
		TestUtils.assertSearchText("ID", "android:id/message",
				"Entered password does not match the password policy, please try again.");
		TestUtils.assertSearchText("ID", "android:id/button1", "Ok");
		getDriver().findElement(By.id("android:id/button1")).click();

		// Trying to change password when the new password doesnt match confirm new
		// password

		String notMatchingConfirmPassword = "Try to change password with confirm password" + "("
				+ confirm_password_not_matching + " )" + "not matching new password (" + new_password + ")";
		Markup pa = MarkupHelper.createLabel(notMatchingConfirmPassword, ExtentColor.BLUE);
		testInfo.get().info(pa);
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/change_password_title")));
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/new_password")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/new_password")).sendKeys(new_password);

		getDriver().findElement(By.id("com.sf.biocapture.activity:id/confirm_password")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/confirm_password"))
				.sendKeys(confirm_password_not_matching);

		getDriver().findElement(By.id("com.sf.biocapture.activity:id/update_pwd")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/message")));

		TestUtils.assertSearchText("ID", "android:id/message", "Entered passwords do not match");
		TestUtils.assertSearchText("ID", "android:id/button1", "Ok");
		getDriver().findElement(By.id("android:id/button1")).click();

		// Trying to change password with a valid password policy
		String validPassword = "Try to change password with valid Password Policy: " + new_password;
		Markup pass = MarkupHelper.createLabel(validPassword, ExtentColor.BLUE);
		testInfo.get().info(pass);
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/change_password_title")));

		getDriver().findElement(By.id("com.sf.biocapture.activity:id/new_password")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/new_password")).sendKeys(new_password);

		getDriver().findElement(By.id("com.sf.biocapture.activity:id/confirm_password")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/confirm_password")).sendKeys(confirm_password);

		getDriver().findElement(By.id("com.sf.biocapture.activity:id/update_pwd")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/alertTitle", "Password Update");
		TestUtils.assertSearchText("ID", "android:id/message", "Password change successful");
		getDriver().findElement(By.id("android:id/button1")).click();

	}

	@Test
	public static void loginWithNewPassword()
			throws InterruptedException, FileNotFoundException, IOException, ParseException {
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser
				.parse(new FileReader("src/test/resource/config/data.config.json"));

		String valid_username = (String) config.get("valid_username");
		String new_password = (String) config.get("new_password");
		// Trying to login in with the new password
		String login = "Trying to login with the newly changed password: " + new_password;
		Markup pass = MarkupHelper.createLabel(login, ExtentColor.BLUE);
		testInfo.get().info(pass);
		WebDriverWait wait = new WebDriverWait(getDriver(), 50);

		wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/login_username")));
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/login_username")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/login_username")).sendKeys(valid_username);

		getDriver().findElement(By.id("com.sf.biocapture.activity:id/linear_layout_password")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/linear_layout_password")).sendKeys(new_password);

		getDriver().findElement(By.id("com.sf.biocapture.activity:id/submit")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Home']")));

	}

}
