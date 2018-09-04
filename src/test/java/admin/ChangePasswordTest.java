package admin;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
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

        //Current Password
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/current_password")).sendKeys("bankole1!!");
        TestUtils.hideKeyboard();
        Thread.sleep(300);

        //New Password
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/new_password")).sendKeys("bankole1!");
        TestUtils.hideKeyboard();
        Thread.sleep(300);

        //Confirm New Password
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/new_password")).sendKeys("bankole1!");
        TestUtils.hideKeyboard();
        Thread.sleep(300);

        //Clicks on Change Button
//        getDriver().findElement(By.id("com.sf.biocapture.activity:id/update_pwd")).click();
    }
}
