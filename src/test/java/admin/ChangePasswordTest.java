package admin;

import io.appium.java_client.android.Activity;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import utils.TestBase;
import utils.TestUtils;

public class ChangePasswordTest extends TestBase {
    static final String newPassword = "bankole1!!!";
    static final String password = "bankole1!!";
    @Test
    public static void navigateToChangePasswordPage() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(getDriver(), 30);

//        getDriver().startActivity(new Activity("com.sf.biocapture.activity", "com.sf.biocapture.activity.NewHomeActivity"));

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Home']")));

        getDriver().findElementByAccessibilityId("Navigate up").click();
        Thread.sleep(500);
        getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Change Password']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/change_password_title")));
        testInfo.get().info("Successful landing to Change Password page");
    }

    @Test
    public static void changeToNewPassword() throws InterruptedException {

        WebDriverWait wait = new WebDriverWait(getDriver(), 30);

        //Tries to change password with invalid Password Policy
        testInfo.get().info("Trying to change password to one that unmatches the password policy");
        //Current Password
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/current_password")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/current_password")).sendKeys(password);
        TestUtils.hideKeyboard();
        Thread.sleep(300);

        //New Password
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/new_password")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/new_password")).sendKeys("password");
        TestUtils.hideKeyboard();
        Thread.sleep(300);

        //Confirm New Password
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/confirm_password")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/confirm_password")).sendKeys("password");
        TestUtils.hideKeyboard();
        Thread.sleep(300);

        //Clicks on Change Button
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/update_pwd")).click();
        Thread.sleep(1000);

        TestUtils.assertSearchText("ID", "android:id/message", "Entered password does not match the password policy, please try again.");

        getDriver().findElement(By.id("android:id/button1")).click();

        //Successful Change
        //Current Password
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/current_password")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/current_password")).sendKeys(password);
        TestUtils.hideKeyboard();
        Thread.sleep(300);

        //New Password
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/new_password")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/new_password")).sendKeys(newPassword);
        TestUtils.hideKeyboard();
        Thread.sleep(300);

        //Confirm New Password
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/confirm_password")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/confirm_password")).sendKeys(newPassword);
        TestUtils.hideKeyboard();
        Thread.sleep(300);

        //Clicks on Change Button
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/update_pwd")).click();

        wait.until(ExpectedConditions.textToBe(By.id("android:id/message"), "Password change successful"));
        TestUtils.assertSearchText("ID", "android:id/message", "Password change successful");
        getDriver().findElement(By.id("android:id/button1")).click();
        Thread.sleep(1000);
    }

    @Test
    public static void loginWithNewPassword() throws InterruptedException {

        WebDriverWait wait = new WebDriverWait(getDriver(), 30);

        //Logs out
        getDriver().findElementByAccessibilityId("Navigate up").click();
        Thread.sleep(500);
        getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Logout']")).click();
        Thread.sleep(300);
        getDriver().findElement(By.xpath("//android.widget.Button[@text='OK']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/otp_login")));

        //Login
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/otp_login")).click();
        Thread.sleep(1000);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/login_username")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/login_username")).sendKeys("bestify@seamfix.com");
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/login_password")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/login_password")).sendKeys(newPassword);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/submit")).click();
        Thread.sleep(1000);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Home']")));

        testInfo.get().info("Log in successful with new password.");
    }

    @Test
    public static void changeBackToOldPassword() throws InterruptedException {

        WebDriverWait wait = new WebDriverWait(getDriver(), 30);
        navigateToChangePasswordPage();

        //Current Password
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/current_password")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/current_password")).sendKeys(newPassword);
        TestUtils.hideKeyboard();
        Thread.sleep(300);

        //New Password
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/new_password")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/new_password")).sendKeys(password);
        TestUtils.hideKeyboard();
        Thread.sleep(300);

        //Confirm New Password
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/confirm_password")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/confirm_password")).sendKeys(password);
        TestUtils.hideKeyboard();
        Thread.sleep(300);

        //Clicks on Change Button
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/update_pwd")).click();

        wait.until(ExpectedConditions.textToBe(By.id("android:id/message"), "Password change successful"));
        TestUtils.assertSearchText("ID", "android:id/message", "Password change successful");
        getDriver().findElement(By.id("android:id/button1")).click();
        testInfo.get().info("Password reverted Successfully");
        Thread.sleep(1000);
    }
}
