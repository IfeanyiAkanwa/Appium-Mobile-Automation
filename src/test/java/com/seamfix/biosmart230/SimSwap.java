package com.seamfix.biosmart230;

import utils.TestBase;

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

import utils.TestUtils;

public class SimSwap extends TestBase {

	@Test
	public static void navigateToCaptureMenuTest() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), 30);
		// Try to navigate to Registration Type
		String regType = "Navigate to Registration Type";
		Markup r = MarkupHelper.createLabel(regType, ExtentColor.BLUE);
		testInfo.get().info(r);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Home']")));
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/button_start_capture")).click();
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/reg_type_placeholder")));
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity:id/reg_type_placeholder",
				"Registration Type");
	}
	
	@Parameters({ "dataEnv"})
    @Test
    public void simSwapTest(String dataEnv) throws Exception {

        WebDriverWait wait = new WebDriverWait(getDriver(), 30);
        
        JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resource/" + dataEnv + "/data.conf.json"));
		JSONObject envs = (JSONObject) config.get("SIMSwap");
		
		String invalid_Msisdn= (String) envs.get("invalid_Msisdn");
		String valid_Msisdn = (String) envs.get("valid_Msisdn");
		String lga = (String) envs.get("lga");

		// Select LGA of Registration
		String lgaa = "Select LGA of Registration: " + lga;
		Markup m = MarkupHelper.createLabel(lgaa, ExtentColor.BLUE);
		testInfo.get().info(m);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/lga_of_reg")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/alertTitle", "LGA of Registration*");
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='" + lga + "']")).click();
		Thread.sleep(500);
		
		// Select Sim Swap
		String newReg = "Select SIM Swap";
		Markup d = MarkupHelper.createLabel(newReg, ExtentColor.BLUE);
		testInfo.get().info(d);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/typeofreg")));
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/typeofreg")).click();
		Thread.sleep(500);
		TestUtils.assertSearchText("ID", "android:id/alertTitle", "Select Item");
		TestUtils.assertSearchText("ID", "android:id/text1", "[Select Registration Type]");
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='SIM Swap']")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/next_button")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/page_title")));
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity:id/page_title", "SIM Swap");
		
		// Submit without supplying Msisdn and leaving the plan at default
		String msisdn = "Submit without supplying Msisdn and leaving the plan at default";
		Markup i = MarkupHelper.createLabel(msisdn, ExtentColor.BLUE);
		testInfo.get().info(i);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/sim_swap_edit_text")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/sim_swap_edit_text")).sendKeys("");
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/btn_sim_swap_search_msisdn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/alertTitle")));
		Thread.sleep(500);
		TestUtils.assertSearchText("ID", "android:id/message", "Please Select a plan Type");
		getDriver().findElement(By.id("android:id/button1")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/sim_swap_edit_text")));
		Thread.sleep(500);
		
		// Submit without supplying Msisdn and selecting one of the plan types
		String msis = "Submit without supplying Msisdn and selecting one of the plan types";
		Markup h = MarkupHelper.createLabel(msis, ExtentColor.BLUE);
		testInfo.get().info(h);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/sim_swap_edit_text")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/sim_swap_edit_text")).sendKeys("");
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/plan_type")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.CheckedTextView[@text='PREPAID']")));
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='PREPAID']")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/btn_sim_swap_search_msisdn")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/message", "Required Input Field: Phone Number");
		getDriver().findElement(By.id("android:id/button1")).click();
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/sim_swap_edit_text")));
		Thread.sleep(500);
		
		// Submit when Msisdn field is completed and the plan type is left on default
		String msis2 = "Submit when Msisdn field is completed: " + valid_Msisdn + " and the plan type is left on default";
		Markup f = MarkupHelper.createLabel(msis2, ExtentColor.BLUE);
		testInfo.get().info(f);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/sim_swap_edit_text")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/sim_swap_edit_text")).sendKeys(valid_Msisdn);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/plan_type")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.CheckedTextView[@text='[Select Subscriber Type]']")));
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='[Select Subscriber Type]']")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/btn_sim_swap_search_msisdn")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/alertTitle")));
		Thread.sleep(500);
		TestUtils.assertSearchText("ID", "android:id/message", "Please Select a plan Type");
		getDriver().findElement(By.id("android:id/button1")).click();
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/sim_swap_edit_text")));
		Thread.sleep(500);
	
		// Submit when an invalid Msisdn is entered and selecting one of the plan types
		String invalidMsisdn = "Submit when an invalid Msisdn is entered: " + invalid_Msisdn + " and selecting one of the plan types";
		Markup g = MarkupHelper.createLabel(invalidMsisdn, ExtentColor.BLUE);
		testInfo.get().info(g);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/sim_swap_edit_text")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/sim_swap_edit_text")).sendKeys(invalid_Msisdn);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/plan_type")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.CheckedTextView[@text='PREPAID']")));
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='PREPAID']")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/btn_sim_swap_search_msisdn")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/message", "Could not retrieve bio data for the specified msisdn.");
		getDriver().findElement(By.id("android:id/button1")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/sim_swap_edit_text")));
		Thread.sleep(500);
		
		// Submit when a valid Msisdn is entered and selecting one of the plan types
		String validMsisdn = "Submit when a valid Msisdn is entered: " + valid_Msisdn + " and selecting one of the plan types";
		Markup v = MarkupHelper.createLabel(validMsisdn, ExtentColor.BLUE);
		testInfo.get().info(v);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/sim_swap_edit_text")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/sim_swap_edit_text")).sendKeys(valid_Msisdn);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/plan_type")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.CheckedTextView[@text='PREPAID']")));
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='PREPAID']")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/btn_sim_swap_search_msisdn")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/btn_verify_fingerprint")));
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/btn_verify_fingerprint")).click();
		Thread.sleep(500);
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/alertTitle")));
			Thread.sleep(500);
			TestUtils.assertSearchText("ID", "com.sf.biocapture.activity:id/alertTitle", "Scanner not found");
			getDriver().findElement(By.id("android:id/button1")).click();
			Thread.sleep(500);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/fingerType_text")));
			Thread.sleep(500);
			TestUtils.assertSearchText("ID", "com.sf.biocapture.activity:id/fingerType_text", "VERIFICATION");
			TestUtils.assertSearchText("ID", "com.sf.biocapture.activity:id/btnStop", "Capture");
			Thread.sleep(500);
		} catch (Exception e) {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/fingerType_text")));
			Thread.sleep(500);
			TestUtils.assertSearchText("ID", "com.sf.biocapture.activity:id/fingerType_text", "VERIFICATION");
			TestUtils.assertSearchText("ID", "com.sf.biocapture.activity:id/btnStop", "Capture");
			Thread.sleep(500);
		}
    
	}
      
}
