package com.seamfix.biosmart210;

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

import utils.TestBase;
import utils.TestUtils;

public class ChangePasswordTest extends TestBase {
    
    @Test
    public static void navigateToChangePasswordPage() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(getDriver(), 30);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Home']")));

        getDriver().findElementByAccessibilityId("Navigate up").click();
        Thread.sleep(500);
        getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Change Password']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/change_password_title")));
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity:id/title", "CHANGE PASSWORD");
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity:id/change_password_guide",
				"Password must contain at least 10 Characters with at least 1 LowerCase, 1 UpperCase, 1 Number, and 1 Symbol");
    }

    @Parameters({ "dataEnv"})
    @Test
    public static void changeToNewPassword(String dataEnv) throws Exception {

        WebDriverWait wait = new WebDriverWait(getDriver(), 30);
    	JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resource/" + dataEnv + "/data.conf.json"));
		JSONObject envs = (JSONObject) config.get("ChangePassword");
		
		String newPassword = (String) envs.get("newPassword");
		String password = (String) envs.get("password");
		String invalid_password_policy = (String) envs.get("invalid_password_policy");
		String valid_username = (String) envs.get("valid_username");
		String confirm_password_not_matching = (String) envs.get("confirm_password_not_matching");

        // Change password with invalid Password Policy
		String invalidPass = "Change password with invalid password policy: " + invalid_password_policy;
		Markup m = MarkupHelper.createLabel(invalidPass, ExtentColor.BLUE);
		testInfo.get().info(m);
      
        //Current Password
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/current_password")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/current_password")).sendKeys(password);

        //New Password
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/new_password")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/new_password")).sendKeys(invalid_password_policy);

        //Confirm New Password
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/confirm_password")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/confirm_password")).sendKeys(invalid_password_policy);

        //Clicks on Change Button
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/update_pwd")).click();
        Thread.sleep(1000);
        TestUtils.assertSearchText("ID", "android:id/message", "Entered password does not match the password policy, please try again.");
        getDriver().findElement(By.id("android:id/button1")).click();

		// Change password when the new password doesn't match confirm old password
		String notMatchingConfirmPassword = "Change password with confirm password: " + confirm_password_not_matching + " not matching new password " + newPassword;
		Markup pa = MarkupHelper.createLabel(notMatchingConfirmPassword, ExtentColor.BLUE);
		testInfo.get().info(pa);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/change_password_title")));
		
		//Current Password
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/current_password")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/current_password")).sendKeys(password);
        
        //New Password
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/new_password")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/new_password")).sendKeys(newPassword);

		 //Confirm New Password
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/confirm_password")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/confirm_password")).sendKeys(confirm_password_not_matching);
		
		//Clicks on Change Button
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/update_pwd")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/message")));
		TestUtils.assertSearchText("ID", "android:id/message", "Entered passwords do not match");
		TestUtils.assertSearchText("ID", "android:id/button1", "Ok");
		getDriver().findElement(By.id("android:id/button1")).click();

    	// Change password with valid password policy
		String validpass= "Change password with valid password policy: " + newPassword;
		Markup e = MarkupHelper.createLabel(validpass, ExtentColor.BLUE);
		testInfo.get().info(e);
		
        //Current Password
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/current_password")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/current_password")).sendKeys(password);
       
        //New Password
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/new_password")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/new_password")).sendKeys(newPassword);

        //Confirm New Password
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/confirm_password")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/confirm_password")).sendKeys(newPassword);
      
        //Clicks on Change Button
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/update_pwd")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/alertTitle", "Password Update");
        TestUtils.assertSearchText("ID", "android:id/message", "Password change successful");
        getDriver().findElement(By.id("android:id/button1")).click();
        
        //Logs out
		String logOut = "Logout: " + valid_username;
		Markup o = MarkupHelper.createLabel(logOut, ExtentColor.BLUE);
		testInfo.get().info(o);
		getDriver().findElement(By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/design_menu_item_text")));
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Logout']")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/message")));
		TestUtils.assertSearchText("ID", "android:id/message", "   Log out?");
		getDriver().findElement(By.id("android:id/button3")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/otp_login")));
       
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

		// Login in with the new password
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/otp_login")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/login_username")));
        String login = "Login with newly changed password: " +  newPassword + " and valid username: " + valid_username;
		Markup g = MarkupHelper.createLabel(login, ExtentColor.BLUE);
		testInfo.get().info(g);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/login_username")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/login_username")).sendKeys(valid_username);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/login_password")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/login_password")).sendKeys(newPassword);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/submit")).click();
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
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/current_password")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/current_password")).sendKeys(newPassword);

        //New Password
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/new_password")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/new_password")).sendKeys(password);
        
        //Confirm New Password
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/confirm_password")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/confirm_password")).sendKeys(password);
      
        //Clicks on Change Button
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/update_pwd")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/alertTitle", "Password Update");
        TestUtils.assertSearchText("ID", "android:id/message", "Password change successful");
        getDriver().findElement(By.id("android:id/button1")).click();
    }
}
