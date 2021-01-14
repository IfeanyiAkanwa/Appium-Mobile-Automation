package admin;

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

import io.appium.java_client.android.AndroidKeyCode;
import utils.TestBase;
import utils.TestUtils;

public class Login extends TestBase {
    @Parameters({ "dataEnv"})
	@Test (groups = {"Regression"})
	public void usernamePasswordLogin(String dataEnv) throws InterruptedException, IOException, ParseException {
		WebDriverWait wait = new WebDriverWait(getDriver(), 50);
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resource/" + dataEnv + "/data.conf.json"));
		JSONObject envs = (JSONObject) config.get("Login");

		String valid_username = (String) envs.get("valid_username");
		String invalid_password = (String) envs.get("invalid_password");
		String invalid_username = (String) envs.get("invalid_username");
		String valid_password = (String) envs.get("valid_password");
		String blacklisted_username = (String) envs.get("blacklisted_username");
		String blacklisted_password = (String) envs.get("blacklisted_password");
		String deactivated_username = (String) envs.get("deactivated_username");
		String invalid_email_format = (String) envs.get("invalid_email_format");
		String valid_pw = (String) envs.get("valid_pw");
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity." + Id + ":id/otp_login")));
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/otp_login")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity." + Id + ":id/login_username")));

		// Select Login mode
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/login_mode_types_spinner")).click();
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Biosmart']")).click();
		Thread.sleep(500);
		
		// Login with valid username and invalid password
		TestUtils.testTitle("Login with a valid username " + "(" + valid_username + ")" + " and invalid password " + "(" + invalid_password + ")");
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/login_username")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/login_username")).sendKeys(valid_username);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/login_password")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/login_password")).sendKeys(invalid_password);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/submit")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity." + Id + ":id/title_template")));
		TestUtils.assertSearchText("ID", "android:id/message", "Invalid username or password entered.");
		getDriver().findElement(By.id("android:id/button1")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity." + Id + ":id/login_username")));

		// Login with invalid username and valid password
		TestUtils.testTitle("Login with invalid username " + "(" + invalid_username + ")" + " and valid password " + "(" + valid_password + ")");
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/login_username")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/login_username")).sendKeys(invalid_username);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/login_password")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/login_password")).sendKeys(valid_password);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/submit")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity." + Id + ":id/title_template")));
		TestUtils.assertSearchText("ID", "android:id/message", "Invalid username or password entered.");
		getDriver().findElement(By.id("android:id/button1")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity." + Id + ":id/login_username")));
		
		// Login with invalid Email Format and valid password
		TestUtils.testTitle("Login with invalid Email Format " + "(" + invalid_email_format + ")" + " and valid password " + "(" + valid_password + ")");
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/login_username")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/login_username")).sendKeys(invalid_email_format);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/login_password")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/login_password")).sendKeys(valid_password);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/submit")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity." + Id + ":id/title_template")));
		TestUtils.assertSearchText("ID", "android:id/message", "Please enter a valid User ID or Please enter a valid Email address");
		getDriver().findElement(By.id("android:id/button1")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity." + Id + ":id/login_username")));

		// Login with empty username and empty password
		TestUtils.testTitle("Login with an empty username " + "()" + " and empty password " + "()");
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/login_username")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/login_username")).sendKeys("");
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/login_password")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/login_password")).sendKeys("");
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/submit")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity." + Id + ":id/title_template")));
		TestUtils.assertSearchText("ID", "android:id/message", "Required Input Field: email");
		getDriver().findElement(By.id("android:id/button1")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity." + Id + ":id/login_username")));

		// Login with deactivated username and valid password
		TestUtils.testTitle("Login with deactivated username " + "(" + deactivated_username + ")" + " and valid password " + "(" + valid_pw + ")");
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/login_username")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/login_username")).sendKeys(deactivated_username);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/login_password")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/login_password")).sendKeys(valid_pw);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/submit")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity." + Id + ":id/title_template")));
		TestUtils.assertSearchText("ID", "android:id/message", "Your account was deactivated. Please contact support");
		getDriver().findElement(By.id("android:id/button1")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity." + Id + ":id/login_username")));
		
		// Login with blacklisted username and valid password
		TestUtils.testTitle("Login with blacklisted username " + "(" + blacklisted_username	+ ")" + " and valid password " + "(" + valid_pw + ")");
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/login_username")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/login_username")).sendKeys(blacklisted_username);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/login_password")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/login_password")).sendKeys(blacklisted_password);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/submit")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity." + Id + ":id/title_template")));
		TestUtils.assertSearchText("ID", "android:id/message", "Your account is blacklisted. Please contact support");
		getDriver().findElement(By.id("android:id/button1")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity." + Id + ":id/login_username")));

		// Login with valid username and valid password
		TestUtils.testTitle("Login with a valid username " + "(" + valid_username + ")" + " and valid password " + "(" + valid_password + ")");
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/login_username")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/login_username")).sendKeys(valid_username);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/login_password")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/login_password")).sendKeys(valid_password);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/submit")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Home']")));
		TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Home']", "Home");

		// Log out
		TestUtils.testTitle("Logout " + "(" + valid_username + ")");
		getDriver().findElement(By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity." + Id + ":id/design_menu_item_text")));
		TestUtils.scrollDown();
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Logout']")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/message")));
		TestUtils.assertSearchText("ID", "android:id/message", "   Log out?");
		getDriver().findElement(By.id("android:id/button3")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity." + Id + ":id/otp_login")));
		Thread.sleep(500);
	}

    @Parameters({ "dataEnv"})
    @Test(groups = { "Regression", ""})
    public void fingerprintLogin(String dataEnv) throws Exception {
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
        TestUtils.testTitle("Login with fingerprint");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity." + Id + ":id/finger_print")));
        getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/finger_print")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity." + Id + ":id/page_sub_title")));
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity." + Id + ":id/page_sub_title", "Fingerprint");

        //Login without entering email
        TestUtils.testTitle("Login with empty email");
        getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/email")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/verify")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/message")));
        testInfo.get().info(getDriver().findElement(By.id("android:id/message")).getText());
        getDriver().findElement(By.id("android:id/button1")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity." + Id + ":id/email")));

        //Login with incomplete email
        TestUtils.testTitle("Login with incomplete email: cngwu");
        getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/email")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/email")).sendKeys("cngwu");
        getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/verify")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/message")));
        testInfo.get().info(getDriver().findElement(By.id("android:id/message")).getText());
        getDriver().findElement(By.id("android:id/button1")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity." + Id + ":id/email")));

        // Login with a user that does not exist
        TestUtils.testTitle("Login with non existing user " + "(" + invalid_username + ")");
        getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/email")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/email")).sendKeys(invalid_username);
        getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/verify")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity." + Id + ":id/alertTitle")));
        TestUtils.assertSearchText("ID", "android:id/message", "No agent was found with entered email address");
        getDriver().findElement(By.id("android:id/button1")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity." + Id + ":id/email")));

        // Login with user that has not been onboarded
        TestUtils.testTitle("Login with username that is not onboarded" + "(" + not_onboarded_username + ")");
        getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/email")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/email")).sendKeys(not_onboarded_username);
        getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/verify")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity." + Id + ":id/alertTitle")));
        TestUtils.assertSearchText("ID", "android:id/message", "No agent was found with entered email address");
        getDriver().findElement(By.id("android:id/button1")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity." + Id + ":id/email")));

        // Login with user that has been blacklisted
        TestUtils.testTitle("Login with blacklisted user " + "(" + blacklisted_username + ")");
        getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/email")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/email")).sendKeys(blacklisted_username);
        getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/verify")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity." + Id + ":id/alertTitle")));
        TestUtils.assertSearchText("ID", "android:id/message", "Your Account has been blacklisted");
        getDriver().findElement(By.id("android:id/button1")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity." + Id + ":id/email")));

        // Login with user that has been deactivated
        TestUtils.testTitle("Login with deactivated user " + "(" + deactivated_username + ")");
        getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/email")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/email")).sendKeys(deactivated_username);
        getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/verify")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity." + Id + ":id/alertTitle")));
        TestUtils.assertSearchText("ID", "android:id/message", "Your Account has been deactivated");
        getDriver().findElement(By.id("android:id/button1")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity." + Id + ":id/email")));

        // Login with an onboarded username
        TestUtils.testTitle("Login with an onboarded user " + "(" + onboarded_username + ")");
        getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/email")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/email")).sendKeys(onboarded_username);
        getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/verify")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity." + Id + ":id/alertTitle")));
        Thread.sleep(500);
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity." + Id + ":id/alertTitle", "Scanner not found");
        getDriver().findElement(By.id("android:id/button1")).click();
        Thread.sleep(500);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity." + Id + ":id/finger_image")));
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity." + Id + ":id/fingerType_text", "VERIFICATION");
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity." + Id + ":id/btnStop", "CAPTURE");

        //Return to Login page
        // Go back
        getDriver().pressKeyCode(AndroidKeyCode.BACK);
        // Go back
        getDriver().pressKeyCode(AndroidKeyCode.BACK);
    }

	@Parameters ({"dataEnv"})
	@Test (groups = { "Regression" })
	public void activeDirectoryValidLoginTest(String dataEnv) throws InterruptedException, IOException, ParseException {
		WebDriverWait wait = new WebDriverWait(getDriver(), 60);
		
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resource/" + dataEnv + "/data.conf.json"));
		JSONObject envs = (JSONObject) config.get("Login");

		String AD_UserName = (String) envs.get("AD_UserName");
		String valid_AD_pw = (String) envs.get("valid_AD_pw");
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity." + Id + ":id/otp_login")));
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/otp_login")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity." + Id + ":id/login_username")));
		
		// Select Login Mode Type
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/login_mode_types_spinner")).click();
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Active Directory']")).click();
		Thread.sleep(500);
		
		// Login with valid username and valid password
		TestUtils.testTitle("Login with a valid Active Directory username " + "(" + AD_UserName + ")" + " and valid password " + "(" + valid_AD_pw + ")");
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/login_username")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/login_username")).sendKeys(AD_UserName);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/login_password")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/login_password")).sendKeys(valid_AD_pw);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/submit")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Home']")));
		TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Home']", "Home");
		Thread.sleep(500);
				
		// Log out
		TestUtils.testTitle("Logout " + "(" + AD_UserName + ")");
		getDriver().findElement(By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity." + Id + ":id/design_menu_item_text")));
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Logout']")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/message")));
		TestUtils.assertSearchText("ID", "android:id/message", "   Log out?");
		getDriver().findElement(By.id("android:id/button3")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity." + Id + ":id/otp_login")));
		Thread.sleep(500);
	}
	
	@Parameters ({"dataEnv"})
	@Test (groups = { "Regression" })
	public void erorrMessagesBiosmartValidationTest(String dataEnv) throws InterruptedException, FileNotFoundException, IOException, ParseException {
		WebDriverWait wait = new WebDriverWait(getDriver(), 60);
		
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resource/" + dataEnv + "/data.conf.json"));
		JSONObject envs = (JSONObject) config.get("Login");

		String AD_UserName = (String) envs.get("AD_UserName");
		String valid_AD_pw = (String) envs.get("valid_AD_pw");
		String BS_email = (String) envs.get("BS_email");
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity." + Id + ":id/otp_login")));
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/otp_login")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity." + Id + ":id/login_username")));
		Thread.sleep(500);
		
		// Login with Active Directory user but select Biosmart as Auth mode and biosmart email
		TestUtils.testTitle("Login with Active Directory user but select Biosmart as Auth mode and biosmart email: " + BS_email);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/login_mode_types_spinner")).click();
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Biosmart']")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/login_username")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/login_username")).sendKeys(BS_email);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/login_password")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/login_password")).sendKeys(valid_AD_pw);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/submit")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity." + Id + ":id/title_template")));
		TestUtils.assertSearchText("ID", "android:id/message", "Your allowed Login Mode is Active Directory. Your AD Username is; " + AD_UserName);
		getDriver().findElement(By.id("android:id/button1")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity." + Id + ":id/login_username")));
		
		// Login with Active Directory user but select Biosmart as Auth mode and Active Directory user name
		TestUtils.testTitle("Login with Active Directory user but select Biosmart as Auth mode and Active Directory user name: " + AD_UserName);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/login_mode_types_spinner")).click();
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Biosmart']")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/login_username")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/login_username")).sendKeys(AD_UserName);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/login_password")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/login_password")).sendKeys(valid_AD_pw);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/submit")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity." + Id + ":id/title_template")));
		TestUtils.assertSearchText("ID", "android:id/message", "Your allowed Login Mode is Active Directory. Your AD Username is; " + AD_UserName);
		getDriver().findElement(By.id("android:id/button1")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity." + Id + ":id/login_username")));
		
		// Login with Active Directory user but select Active Directory as Auth mode and Biosmart email
		TestUtils.testTitle("Login with Active Directory user but select Active Directory as Auth mode and Biosmart email: " + BS_email);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/login_mode_types_spinner")).click();
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Active Directory']")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/login_username")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/login_username")).sendKeys(BS_email);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/login_password")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/login_password")).sendKeys(valid_AD_pw);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/submit")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity." + Id + ":id/title_template")));
		TestUtils.assertSearchText("ID", "android:id/message", "Your allowed Login Mode is Active Directory. Your AD Username is; " + AD_UserName);
		getDriver().findElement(By.id("android:id/button1")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity." + Id + ":id/login_username")));

	}
	
	@Parameters ({"dataEnv"})
	@Test (groups = { "Regression" })
	public void erorrMessagesActiveDirectoryValidationTest(String dataEnv) throws Exception {
		WebDriverWait wait = new WebDriverWait(getDriver(), 60);
		
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resource/" + dataEnv + "/data.conf.json"));
		JSONObject envs = (JSONObject) config.get("Login");
		
		String valid_AD_pw = (String) envs.get("valid_AD_pw");
		String AD_UserName1 = (String) envs.get("AD_UserName1");
		String BS_email1 = (String) envs.get("BS_email1");
		
		// Login with Biosmart user but select Biosmart as Auth mode and Active	Directory username
		TestUtils.testTitle("Login with Biosmart user but select Biosmart as Auth mode and Active Directory username: " + AD_UserName1);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/login_mode_types_spinner")).click();
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Biosmart']")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/login_username")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/login_username")).sendKeys(AD_UserName1);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/login_password")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/login_password")).sendKeys(valid_AD_pw);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/submit")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity." + Id + ":id/title_template")));
		TestUtils.assertSearchText("ID", "android:id/message", "Your allowed Login Mode is Biosmart. Your email address is; " + BS_email1);
		getDriver().findElement(By.id("android:id/button1")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity." + Id + ":id/login_username")));
		
		// Login with Biosmart user but select Active Directory as Auth mode and Biosmart email
		TestUtils.testTitle("Login with Biosmart user but select Active Directory as Auth mode and Biosmart email: " + BS_email1);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/login_mode_types_spinner")).click();
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Active Directory']")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/login_username")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/login_username")).sendKeys(BS_email1);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/login_password")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/login_password")).sendKeys(valid_AD_pw);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/submit")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity." + Id + ":id/title_template")));
		TestUtils.assertSearchText("ID", "android:id/message", "Your allowed Login Mode is Biosmart. Your email address is; " + BS_email1);
		getDriver().findElement(By.id("android:id/button1")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity." + Id + ":id/login_username")));
		
		// Login with Biosmart user but select Active Directory as Auth mode and Active Directory username
		TestUtils.testTitle("Login with Biosmart user but select Active Directory as Auth mode and Active Directory username: " + AD_UserName1);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/login_mode_types_spinner")).click();
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Active Directory']")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/login_username")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/login_username")).sendKeys(AD_UserName1);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/login_password")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/login_password")).sendKeys(valid_AD_pw);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/submit")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity." + Id + ":id/title_template")));
		TestUtils.assertSearchText("ID", "android:id/message","Your allowed Login Mode is Biosmart. Your email address is; " + BS_email1);
		getDriver().findElement(By.id("android:id/button1")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity." + Id + ":id/login_username")));
		
		// Go back
		getDriver().pressKeyCode(AndroidKeyCode.BACK);
	}

}
