package admin;

import DemographicForm.Form;
import db.ConnectDB;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import utils.Asserts;
import utils.TestBase;
import utils.TestUtils;

import java.sql.SQLException;

public class ReRegistrationCapture extends TestBase {
    String phoneNumber = "08142050914";
    String otp = "12345";

    @Test
    public void NavigateToCaptureMenuTest() throws InterruptedException {
        Thread.sleep(500);
        WebDriverWait wait = new WebDriverWait(getDriver(), 30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Home']")));
        Thread.sleep(500);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/button_start_capture")).click();
        Thread.sleep(500);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Registration Type']")));
        TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Registration Type']", "Registration Type");
        Thread.sleep(500);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/linear_layout_username")).click();
        getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Re-Registration']")).click();
        Thread.sleep(500);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/next_button")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Re Registration']")));
        TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Re Registration']", "Re Registration");

    }

    @Test
    public void ReRegistertionTest() throws InterruptedException, SQLException {

        WebDriverWait wait = new WebDriverWait(getDriver(), 30);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/primary_msisdn_field")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/primary_msisdn_field")).sendKeys(phoneNumber);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/submit_button")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/otp_panel_view")));
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/otp_request_button")).click();
        if(TestUtils.isElementPresent("ID", "android:id/body")){
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("android:id/body")));
        }
        if(!ConnectDB.changeOTP(phoneNumber, otp)){
            TestUtils.assertSearchText("ID", "me", "Fail here");
        }
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/otp_field")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/otp_field")).sendKeys(otp);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/otp_confirm_button")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/user_details_panel")));
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/next_button")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/collapsing_toolbar")));
        Asserts.AssertIndividualForm();
        getDriver().findElement(By.className("android.widget.ImageButton")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/collapsing_toolbar")));
        Form.IndividualForeignerForm();

    }
}