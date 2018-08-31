package com.sf.testCases;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

import com.sf.utils.testBase;

public class login extends testBase{
	
	@Test
    public void usernamePasswordTest() throws InterruptedException {
        Thread.sleep(9000);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/otp_login")).click();
        Thread.sleep(1000);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/login_username")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/login_username")).sendKeys("oosifala@seamfix.com");
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/login_password")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/login_password")).sendKeys("bankole1!!");
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/submit")).click();
        Thread.sleep(3000);
    }

}
