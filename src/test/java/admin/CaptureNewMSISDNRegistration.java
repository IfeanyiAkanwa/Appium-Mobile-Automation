package admin;

import DemographicForm.Form;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import utils.TestBase;
import utils.TestUtils;

import java.io.IOException;

public class CaptureNewMSISDNRegistration extends TestBase {

    @Test
    public static void NavigateToCaptureMenuTest() throws InterruptedException {
        Thread.sleep(500);
        WebDriverWait wait = new WebDriverWait(getDriver(), 30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Home']")));
        Thread.sleep(500);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/button_start_capture")).click();
        Thread.sleep(500);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Registration Type']")));
        TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Registration Type']", "Registration Type");
        Thread.sleep(500);

    }

    @Test
    public void CaptureNewMSISDNTest() throws InterruptedException, IOException {

        WebDriverWait wait = new WebDriverWait(getDriver(), 30);

        getDriver().findElement(By.id("com.sf.biocapture.activity:id/linear_layout_username")).click();
        getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='New Registration (MSISDN)']")).click();
        Thread.sleep(500);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/next_button")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/page_title")));
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity:id/page_title", "New Registration (MSISDN)");

        Thread.sleep(1000);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/msisdn")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/msisdn")).sendKeys("1111113112");
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/add_msisdn_button")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/next_button")));
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/next_button")).click();
        Thread.sleep(1000);
//        Form.IndividualForeignerForm();
        Form.NigerianCompanyForm();
        if (TestUtils.isElementPresent("XPATH", "//android.widget.TextView[@text='Sell Airtime/Data']")) {
            getDriver().findElement(By.className("android.widget.ImageButton")).click();
        }

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/kpi_report_name")));
//        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity:id/kpi_report_name", "Total Subscribers");

    }
}
