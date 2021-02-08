package admin;

import java.io.FileReader;
import java.sql.SQLException;

import db.ConnectDB;
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

import utils.TestBase;
import utils.TestUtils;

public class ChangePasswordTest extends TestBase {
    private static int i = 0;
    
    @Test
    public static void navigateToChangePasswordPage() throws InterruptedException, SQLException {
        WebDriverWait wait = new WebDriverWait(getDriver(), 30);
        String forgotPasswordPage = "Navigate to Change password page";
		Markup m = MarkupHelper.createLabel(forgotPasswordPage, ExtentColor.BLUE);
		testInfo.get().info(m);
		
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Home']")));

        getDriver().findElementByAccessibilityId("Navigate up").click();
        Thread.sleep(500);
        getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Account']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/alertTitle")));

        TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Change Password']", "Change Password");
        TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Deactivate Account']", "Deactivate Account");
        getDriver().findElement(By.xpath("//android.widget.TextView[@text='Change Password']")).click();

        //OTP Verification Modal
        TestUtils.testTitle("OTP Dialogue Test");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/dialog_title")));
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity.glo:id/dialog_title", "OTP verification");

        //Ensure that cancel operation runs only once
        if(i == 0){
            //Test the cancel button
            TestUtils.testTitle("Cancel OTP Verification Modal Test");
            getDriver().findElement(By.id("android:id/button2")).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Home']")));
            TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Home']", "Home");

            //Continue to Change Password
            getDriver().findElementByAccessibilityId("Navigate up").click();
            Thread.sleep(500);
            getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Account']")).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/alertTitle")));
            getDriver().findElement(By.xpath("//android.widget.TextView[@text='Change Password']")).click();

            //Resend Otp Test
            TestUtils.testTitle("Resend OTP Test");
            testInfo.get().info("Current OTP: "+ConnectDB.getOTPWithoutPhoneNumber());
            getDriver().findElement(By.id("android:id/button3")).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/user_input_dialog")));
            testInfo.get().info("New OTP: "+ConnectDB.getOTPWithoutPhoneNumber());

            //OTP Verification Modal
            TestUtils.testTitle("OTP Verification Modal Test");
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/dialog_title")));
            TestUtils.assertSearchText("ID", "com.sf.biocapture.activity.glo:id/dialog_title", "OTP verification");

            i++;
        }

        String otp = ConnectDB.getOTPWithoutPhoneNumber();

        TestUtils.testTitle("Invalid OTP Test: u57267");
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/user_input_dialog")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/user_input_dialog")).sendKeys("u57267");
        getDriver().findElement(By.id("android:id/button1")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/alertTitle")));
        TestUtils.assertSearchText("ID", "android:id/message", "There is no record with the otp, msisdn combination.");
        getDriver().findElement(By.id("android:id/button1")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/dialog_title")));

        TestUtils.testTitle("Valid OTP Test: "+otp);
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/user_input_dialog")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/user_input_dialog")).sendKeys(otp);
        getDriver().findElement(By.id("android:id/button1")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/title")));
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity.glo:id/change_password_title", "Change Password");

        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity.glo:id/change_password_guide",
				"Password must contain at least 8 Characters and at most 20 characters with at least 1 LowerCase, 1 UpperCase, 1 Number, and 1 Symbol");
    }

    @Parameters({ "dataEnv"})
    @Test
    public static void changeToNewPassword(String dataEnv) throws Exception {

        WebDriverWait wait = new WebDriverWait(getDriver(), 30);
    	JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resource/" + dataEnv + "/data.conf.json"));
		JSONObject envs = (JSONObject) config.get("ChangePassword");
		
		String newPassword = (String) envs.get("newPassword");
        String newPassword2 = (String) envs.get("newPassword2");
        String newPassword3 = (String) envs.get("newPassword3");
		String password = (String) envs.get("password");
		String invalid_password_policy = (String) envs.get("invalid_password_policy");
		String valid_username = (String) envs.get("valid_username");
		String confirm_password_not_matching = (String) envs.get("confirm_password_not_matching");

        // Change password with invalid Password Policy
		String invalidPass = "Change password with invalid password policy: " + invalid_password_policy;
		Markup m = MarkupHelper.createLabel(invalidPass, ExtentColor.BLUE);
		testInfo.get().info(m);
      
        //Current Password
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/current_password")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/current_password")).sendKeys(password);

        //New Password
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/new_password")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/new_password")).sendKeys(invalid_password_policy);

        //Confirm New Password
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/confirm_password")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/confirm_password")).sendKeys(invalid_password_policy);

        //Clicks on Change Button
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/update_pwd")).click();
        Thread.sleep(1000);
        TestUtils.assertSearchText("ID", "android:id/message", "Entered password does not match the password policy, please try again.");
        getDriver().findElement(By.id("android:id/button1")).click();

		// Change password when the new password doesn't match confirm old password
		String notMatchingConfirmPassword = "Change password with confirm password " + "("
				+ confirm_password_not_matching + " )" + "not matching new password (" + newPassword + ")";
		Markup pa = MarkupHelper.createLabel(notMatchingConfirmPassword, ExtentColor.BLUE);
		testInfo.get().info(pa);
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/change_password_title")));
		//Current Password
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/current_password")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/current_password")).sendKeys(password);
        
        //New Password
		getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/new_password")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/new_password")).sendKeys(newPassword);

		 //Confirm New Password
		getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/confirm_password")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/confirm_password"))
				.sendKeys(confirm_password_not_matching);
		
		//Clicks on Change Button
		getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/update_pwd")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/message")));
		TestUtils.assertSearchText("ID", "android:id/message", "Entered passwords do not match");
		TestUtils.assertSearchText("ID", "android:id/button1", "OK");
		getDriver().findElement(By.id("android:id/button1")).click();

		//Password History Work Around Starts Here

        String title = "Change Password 3 times as a Work Around to Password History Implementation";
        Markup m2 = MarkupHelper.createLabel(title, ExtentColor.ORANGE);
        testInfo.get().info(m2);

        String validpass3 = "Change password with valid password policy: " + newPassword3;
        Markup e = MarkupHelper.createLabel(validpass3, ExtentColor.AMBER);
        testInfo.get().info(e);
        //Current Password
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/current_password")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/current_password")).sendKeys(password);

        //New Password
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/new_password")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/new_password")).sendKeys(newPassword3);

        //Confirm New Password
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/confirm_password")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/confirm_password")).sendKeys(newPassword3);

        //Submit
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/update_pwd")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/alertTitle")));
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity.glo:id/alertTitle", "Password Update");
        TestUtils.assertSearchText("ID", "android:id/message", "Password change successful");
        getDriver().findElement(By.id("android:id/button1")).click();

        navigateToChangePasswordPage();
    	// Change password with valid password policy
       	String validpass2= "Change password with valid password policy: " + newPassword2;
		Markup ee = MarkupHelper.createLabel(validpass2, ExtentColor.AMBER);
		testInfo.get().info(ee);
        //Current Password
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/current_password")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/current_password")).sendKeys(newPassword3);
       
        //New Password
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/new_password")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/new_password")).sendKeys(newPassword2);

        //Confirm New Password
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/confirm_password")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/confirm_password")).sendKeys(newPassword2);

        //Submit
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/update_pwd")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/alertTitle")));
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity.glo:id/alertTitle", "Password Update");
        TestUtils.assertSearchText("ID", "android:id/message", "Password change successful");
        getDriver().findElement(By.id("android:id/button1")).click();

        // Change password with valid password policy
        navigateToChangePasswordPage();
        String validpass= "Change password with valid password policy: " + newPassword;
        Markup eee = MarkupHelper.createLabel(validpass, ExtentColor.AMBER);
        testInfo.get().info(eee);
        //Current Password
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/current_password")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/current_password")).sendKeys(newPassword2);

        //New Password
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/new_password")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/new_password")).sendKeys(newPassword);

        //Confirm New Password
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/confirm_password")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/confirm_password")).sendKeys(newPassword);

        //Submit
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/update_pwd")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/alertTitle")));
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity.glo:id/alertTitle", "Password Update");
        TestUtils.assertSearchText("ID", "android:id/message", "Password change successful");
        getDriver().findElement(By.id("android:id/button1")).click();

        //Password History Work Around Ends Here

        TestUtils.testTitle("Confirm that a user is not able to change password to a password that matches any of the (x) last password used: "+newPassword2);
        navigateToChangePasswordPage();
        //Current Password
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/current_password")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/current_password")).sendKeys(newPassword);

        //New Password
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/new_password")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/new_password")).sendKeys(newPassword2);

        //Confirm New Password
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/confirm_password")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/confirm_password")).sendKeys(newPassword2);

        //Submit
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/update_pwd")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/alertTitle")));
        TestUtils.assertSearchText("ID", "android:id/message", "You cannot use the same password as your last 3 password. Try a different password.");
        getDriver().findElement(By.id("android:id/button1")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/change_password_title")));
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/cancel")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Home']")));

        //Logs out
		String logOut = "Logout" + "(" + valid_username + ")";
		Markup o = MarkupHelper.createLabel(logOut, ExtentColor.BLUE);
		testInfo.get().info(o);
		getDriver().findElement(By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']")).click();
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/design_menu_item_text")));
		TestUtils.scrollDown();
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Logout']")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/message")));
		TestUtils.assertSearchText("ID", "android:id/message", "   Log out?");
		getDriver().findElement(By.id("android:id/button3")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/otp_login")));
       
    }

    @Parameters({ "dataEnv"})
    @Test
    public static void loginWithNewPassword(String dataEnv) throws Exception {

        WebDriverWait wait = new WebDriverWait(getDriver(), 30);
        JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resource/" + dataEnv + "/data.conf.json"));
		JSONObject envs = (JSONObject) config.get("ChangePassword");
		
		String newPassword = (String) envs.get("newPassword");
		String valid_username = (String) envs.get("valid_username");
        String password = (String) envs.get("password");

		// Login in with the new password
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/otp_login")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/login_username")));
        
        // Select Login mode
     	getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/login_mode_types_spinner")).click();
     	getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Biosmart']")).click();
     	Thread.sleep(500);

     	TestUtils.testTitle("Confirm that old password: ( " + password + " ) and valid username ( " + valid_username + " ) can't used to Login");
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/login_username")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/login_username")).sendKeys(valid_username);
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/login_password")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/login_password")).sendKeys(password);
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/submit")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/alertTitle")));
        TestUtils.assertSearchText("ID", "android:id/message", "Invalid username or password entered.");
        getDriver().findElement(By.id("android:id/button1")).click();
     		
        String login = "Login with newly changed password : " + "(" + newPassword + ")"  + " and valid username: " + "(" +valid_username + ")";
		Markup g = MarkupHelper.createLabel(login, ExtentColor.BLUE);
		testInfo.get().info(g);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/login_username")));
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/login_username")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/login_username")).sendKeys(valid_username);
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/login_password")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/login_password")).sendKeys(newPassword);
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/submit")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Home']")));
        TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Home']", "Home");
    }

    @Parameters({ "dataEnv"})
    @Test
    public static void changeBackToOldPassword(String dataEnv) throws Exception {

		WebDriverWait wait = new WebDriverWait(getDriver(), 60);
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser
				.parse(new FileReader("src/test/resource/" + dataEnv + "/data.conf.json"));
		JSONObject envs = (JSONObject) config.get("ChangePassword");

		String newPassword = (String) envs.get("newPassword");
		String password = (String) envs.get("password");

        navigateToChangePasswordPage();
        
		// Change back to old password
		String pw = "Change back to old password: "+ password;
		Markup m = MarkupHelper.createLabel(pw, ExtentColor.BLUE);
		testInfo.get().info(m);
        //Current Password
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/current_password")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/current_password")).sendKeys(newPassword);

        //New Password
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/new_password")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/new_password")).sendKeys(password);
        
        //Confirm New Password
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/confirm_password")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/confirm_password")).sendKeys(password);
      
        //Clicks on Change Button
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/update_pwd")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/alertTitle")));
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity.glo:id/alertTitle", "Password Update");
        TestUtils.assertSearchText("ID", "android:id/message", "Password change successful");
        getDriver().findElement(By.id("android:id/button1")).click();

        //Logs out
        getDriver().findElement(By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']")).click();
        wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/design_menu_item_text")));
        TestUtils.scrollDown();
        getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Logout']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/message")));
        TestUtils.assertSearchText("ID", "android:id/message", "   Log out?");
        getDriver().findElement(By.id("android:id/button3")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/otp_login")));
    }


    @Parameters({ "dataEnv"})
    @Test
    public static void loginWithOldPassword(String dataEnv) throws Exception {

        WebDriverWait wait = new WebDriverWait(getDriver(), 30);
        JSONParser parser = new JSONParser();
        JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resource/" + dataEnv + "/data.conf.json"));
        JSONObject envs = (JSONObject) config.get("ChangePassword");

        String password = (String) envs.get("password");
        String valid_username = (String) envs.get("valid_username");

        // Login in with the new password
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/otp_login")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/login_username")));

        // Select Login mode
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/login_mode_types_spinner")).click();
        getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Biosmart']")).click();
        Thread.sleep(500);

        String login = "Login with old password : " + "(" + password + ")"  + " and valid username: " + "(" +valid_username + ")";
        Markup g = MarkupHelper.createLabel(login, ExtentColor.BLUE);
        testInfo.get().info(g);
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/login_username")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/login_username")).sendKeys(valid_username);
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/login_password")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/login_password")).sendKeys(password);
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/submit")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Home']")));
        TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Home']", "Home");

        //Logs out
        getDriver().findElement(By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']")).click();
        wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/design_menu_item_text")));
        TestUtils.scrollDown();
        getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Logout']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/message")));
        TestUtils.assertSearchText("ID", "android:id/message", "   Log out?");
        getDriver().findElement(By.id("android:id/button3")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/otp_login")));
    }
}
