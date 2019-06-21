package com.seamfix.biosmart210;

import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import utils.Asserts;
import utils.TestBase;
import DemographicForm.Form;
import utils.TestUtils;

import java.io.IOException;

public class BiometricUpdate extends TestBase {
    @Test
    public static void navigateToCaptureSubscriber() throws InterruptedException {
        CaptureNewMSISDNRegistration.navigateToCaptureMenuTest();
        testInfo.get().info("Successful landing to Capture Subscriber page");
    }

    @Test
    public void captureBiometricUpdate() throws InterruptedException, IOException, ParseException {

        WebDriverWait wait = new WebDriverWait(getDriver(), 30);

        Thread.sleep(1000);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/typeofreg")).click();
        Thread.sleep(500);
        getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Biometric Update']")).click();
        Thread.sleep(500);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/next_button")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/primary_msisdn_field")));
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/primary_msisdn_field")).sendKeys("08142050494");
        Thread.sleep(500);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/submit_button")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Personal Details']")));

        Thread.sleep(1000);

        Asserts.AssertIndividualForm();
        getDriver().findElement(By.className("android.widget.ImageButton")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/collapsing_toolbar")));

        Form.IndividualForeignerForm();
        if (TestUtils.isElementPresent("XPATH", "//android.widget.TextView[@text='Sell Airtime/Data']")) {
            getDriver().findElement(By.className("android.widget.ImageButton")).click();
        }

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/kpi_report_name")));
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity:id/kpi_report_name", "Total Subscribers");
    }
}
