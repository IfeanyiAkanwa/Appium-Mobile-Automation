package com.seamfix.biosmart210;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import db.ConnectDB;
import utils.TestBase;
import utils.TestUtils;

import java.sql.SQLException;

public class AgentOnBoardingTest extends TestBase {
	static String phoneNumber = "09060000000";
    static String otp = null;
    
    @Test
    public static void AgentOnBoarding() throws InterruptedException, SQLException {

        WebDriverWait wait = new WebDriverWait(getDriver(), 30);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Home']")));
        getDriver().findElement(By.xpath("//android.widget.ImageButton[@content-desc=\"Navigate up\"]")).click();
        getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Agent OnBoarding']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/page_title")));
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity:id/page_title", "Agent Onboarding");
        testInfo.get().info("Successful landing to Agent Onboarding Page");
        
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/email")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/email")).sendKeys("yetunde@test.com");
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/submit")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/alertTitle")));
        otp = ConnectDB.getOTP(phoneNumber);
        if(otp == null){
        	testInfo.get().log(Status.INFO, "Can't get otp.");
            getDriver().quit();
        }
        Thread.sleep(2000);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/otp")));
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/otp")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/otp")).sendKeys(otp);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/confirm_otp")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/on_boarding_camera_title")));
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity:id/on_boarding_camera_title", "Camera");
        
    }

    /*@Test
    public static void navigateToDashboard() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(getDriver(), 30);
        getDriver().findElementByAccessibilityId("Navigate up").click();
        Thread.sleep(2000);
        if (getDriver().findElement(By.id("android:id/message")).getText().contains("Do you wish to exit Agent OnBoarding")) {
            try{
                getDriver().findElement(By.xpath("//android.widget.Button[@text='Yes']")).click();
            }catch (Exception e){
                getDriver().findElement(By.xpath("//android.widget.Button[@text='YES']")).click();
            }
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Home']")));
    }*/
}
