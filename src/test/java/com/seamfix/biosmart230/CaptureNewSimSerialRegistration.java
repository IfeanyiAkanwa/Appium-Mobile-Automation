package com.seamfix.biosmart230;

import DemographicForm.Form;
import io.appium.java_client.android.Connection;
import io.appium.java_client.android.connection.ConnectionState;
import utils.TestBase;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import utils.TestUtils;

public class CaptureNewSimSerialRegistration extends TestBase {
	
	

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
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/typeofreg")).click();
        Thread.sleep(1000);
        try {
            getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='New Registration (SIM Serial)']")).click();

        } catch (Exception e) {
            getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='New Registration (Sim Serial)']")).click();

        }
        Thread.sleep(500);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/next_button")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/page_title")));
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity:id/page_title", "New Registration (SIM Serial)");


    }

    @Test
    public void RegisterNewSimSerialTest() throws InterruptedException, FileNotFoundException, IOException, ParseException {

        WebDriverWait wait = new WebDriverWait(getDriver(), 30);
        
        //turn off Network
        ConnectionState networks = getDriver().getConnection();
        getDriver().setConnection(Connection.NONE);
        
        //Enter SIM Serial
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/sim_serial_field")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/sim_serial_field")).sendKeys("111111111111111113352");
        Thread.sleep(500);

        //Request Dya
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/dya_check_box")).click();
        Thread.sleep(500);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/alertTitle")));
        TestUtils.assertSearchText("ID", "android:id/alertTitle", "[Select Yellow Account Type]");
        getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='DYA']")).click();
        getDriver().findElement(By.id("android:id/button1")).click();
        Thread.sleep(500);

        getDriver().findElement(By.id("com.sf.biocapture.activity:id/add_sim_serial")).click();
        Thread.sleep(500);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/alertTitle")));
        TestUtils.assertSearchText("ID", "android:id/message", "Do you still wish to proceed with unverified Sim Serial");
        getDriver().findElement(By.id("android:id/button1")).click(); // ok button
        Thread.sleep(1000);
        
        //turn on Network
        getDriver().setConnection(Connection.valueOf(networks.toString()));
        
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/next_button")).click(); // ok button
        Thread.sleep(1000);
        Form.IndividualForeignerForm();
        if (TestUtils.isElementPresent("XPATH", "//android.widget.TextView[@text='Sell Airtime/Data']")) {
            getDriver().findElement(By.className("android.widget.ImageButton")).click();
        }

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/kpi_report_name")));
    }
}
