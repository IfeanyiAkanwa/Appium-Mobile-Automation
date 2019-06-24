package com.seamfix.biosmart210;

import DemographicForm.Form;
import io.appium.java_client.android.Connection;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import utils.TestBase;
import utils.TestUtils;

import java.io.FileReader;
import java.io.IOException;

public class CaptureNewMSISDNRegistration extends TestBase {

	@Test
	public static void navigateToCaptureMenuTest() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), 30);
		// Navigate to Registration Type
		String regType = "Navigate to Registration Type";
		Markup r = MarkupHelper.createLabel(regType, ExtentColor.BLUE);
		testInfo.get().info(r);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Home']")));
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/button_start_capture")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Registration Type']")));
		TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Registration Type']",	"Registration Type");
		Thread.sleep(500);
	}

	@Parameters({ "dataEnv"})
	@Test
	public void captureNewMSISDNTest(String dataEnv) throws InterruptedException, IOException, ParseException {

		WebDriverWait wait = new WebDriverWait(getDriver(), 50);
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resource/" + dataEnv + "/data.conf.json"));
		JSONObject envs = (JSONObject) config.get("NewRegistrationMsisdn");
		
		String invalid_msisdn = (String) envs.get("invalid_msisdn");
		String valid_msisdn = (String) envs.get("valid_msisdn");

		// Select new Registration MSISDN
		String newRegMsisdn = "Select New Registration MSISDN Registration Type";
		Markup m = MarkupHelper.createLabel(newRegMsisdn, ExtentColor.BLUE);
		testInfo.get().info(m);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='[Select Registration Type]']")));
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='[Select Registration Type]']")).click();
		Thread.sleep(500);
		TestUtils.assertSearchText("ID", "android:id/alertTitle", "Select Registration Type");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='New Registration (MSISDN)']")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/next_button")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='New Registration (MSISDN)']")));
		TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='New Registration (MSISDN)']", "New Registration (MSISDN)");
		Thread.sleep(500);
		
		// Enter invalid msisdn
		String invalidMsisdn = "Enter invalid MSISDN: "  + invalid_msisdn + " for validation";
		Markup i = MarkupHelper.createLabel(invalidMsisdn, ExtentColor.BLUE);
		testInfo.get().info(i);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/msisdn")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/msisdn")).sendKeys(invalid_msisdn);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/add_msisdn_button")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/button1")));
		TestUtils.assertSearchText("ID", "android:id/message", "Failure");
		getDriver().findElement(By.id("android:id/button1")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/msisdn")));
		Thread.sleep(500);
		
		// Enter valid msisdn
		String validMsisdn = "Enter valid MSISDN: " + valid_msisdn + " for validation";
		Markup v = MarkupHelper.createLabel(validMsisdn, ExtentColor.BLUE);
		testInfo.get().info(v);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/msisdn")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/msisdn")).sendKeys(valid_msisdn);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/add_msisdn_button")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/next_button")));
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/next_button")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Personal Details']")));
		Thread.sleep(500);
		TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Personal Details']", "Personal Details");
		Thread.sleep(500);

		// Proceed with registration without supplying all mandatory fields
		String emptyField = "Proceed with registration without supplying all mandatory fields";
		Markup e = MarkupHelper.createLabel(emptyField, ExtentColor.BLUE);
		testInfo.get().info(e);
		TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity:id/btn_continue_reg");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/btn_continue_reg")));
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/btn_continue_reg")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/message", "Registration Type\n" + "Please select Registration Type");
		Thread.sleep(500);
		getDriver().findElement(By.id("android:id/button1")).click();
		Thread.sleep(500);

		Form.NigerianCompanyForm(dataEnv);
		if (TestUtils.isElementPresent("XPATH", "//android.widget.TextView[@text='Sell Airtime/Data']")) {
			getDriver().findElement(By.className("android.widget.ImageButton")).click();
		}

		wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/kpi_report_name")));

	}
}
