package com.seamfix.biosmart210;

import java.io.FileReader;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import utils.Asserts;
import utils.TestBase;
import utils.TestUtils;

public class SearchTest extends TestBase {
	
    @Test
    public static void navigateToSearchTest() throws InterruptedException {
    	
    	WebDriverWait wait = new WebDriverWait(getDriver(), 30);
    	String PasswordPage = "Navigate to Search page";
   		Markup m = MarkupHelper.createLabel(PasswordPage, ExtentColor.BLUE);
   		testInfo.get().info(m);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Home']")));
        getDriver().findElement(By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']")).click();
        Thread.sleep(500);
        getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Search']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Search']")));
        TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Search']", "Search");
        Thread.sleep(500);
    }

    @Parameters({ "dataEnv"})
    @Test
    public static void searchByMsisdnTest(String dataEnv) throws Exception {
    	WebDriverWait wait = new WebDriverWait(getDriver(), 60);
    	
    	JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resource/" + dataEnv + "/data.conf.json"));
		JSONObject envs = (JSONObject) config.get("Search");
		
		String valid_msisdn = (String) envs.get("valid_msisdn");
		
		String valMsis = "Search for valid Msisdn: " + valid_msisdn;
		Markup d = MarkupHelper.createLabel(valMsis, ExtentColor.BLUE);
		testInfo.get().info(d);
    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/phone_no")));
    	 getDriver().findElement(By.id("com.sf.biocapture.activity:id/phone_no")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/phone_no")).sendKeys(valid_msisdn);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/search")).click();
        Thread.sleep(500);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/search_list")));
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/msisdn")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Subscriber Information']")));
        Asserts.AssertSubscriberInfo230();
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/ok")).click();
        Thread.sleep(500);
    }

    @Parameters({ "dataEnv"})
    @Test
    public static void searchBySimSerialTest(String dataEnv) throws Exception {
    	WebDriverWait wait = new WebDriverWait(getDriver(), 60);
    	
    	JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resource/" + dataEnv + "/data.conf.json"));
		JSONObject envs = (JSONObject) config.get("Search");
		
		String valid_sim_serial_no = (String) envs.get("valid_sim_serial_no");
		
		String valSimserial = "Search for valid SIM Serial number: " + valid_sim_serial_no;
		Markup d = MarkupHelper.createLabel(valSimserial, ExtentColor.BLUE);
		testInfo.get().info(d);
    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/phone_no")));
    	 getDriver().findElement(By.id("com.sf.biocapture.activity:id/phone_no")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/phone_no")).sendKeys(valid_sim_serial_no);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/search")).click();
        Thread.sleep(500);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/search_list")));
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/msisdn")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Subscriber Information']")));
        TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Subscriber Information']", "Subscriber Information");
        Thread.sleep(500);
        Asserts.AssertSubscriberInfo230();
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/ok")).click();
        Thread.sleep(500);
    }
}
