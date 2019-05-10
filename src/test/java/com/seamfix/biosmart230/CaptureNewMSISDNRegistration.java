package com.seamfix.biosmart230;

import DemographicForm.Form;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
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
	public static void NavigateToCaptureMenuTest() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), 30);
		// Try to navigate to Registration Type
		String regType = "Try to navigate to Registration Type";
		Markup r = MarkupHelper.createLabel(regType, ExtentColor.BLUE);
		testInfo.get().info(r);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Home']")));
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/button_start_capture")).click();
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Registration Type']")));
		TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Registration Type']",
				"Registration Type");
	}

	@Test
	public void CaptureNewMSISDNTest() throws InterruptedException, IOException, ParseException {

		WebDriverWait wait = new WebDriverWait(getDriver(), 50);
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resource/config/data.config.json"));
		JSONObject envs = (JSONObject) config.get("NewRegistration");
		String invalid_msisdn = (String) envs.get("invalid_msisdn");
		String valid_msisdn = (String) envs.get("valid_msisdn");

		// Try to select new Registration MSISDN
		String newRegMsisdn = "Try to select New Registration MSISDN Registration Type";
		Markup m = MarkupHelper.createLabel(newRegMsisdn, ExtentColor.BLUE);
		testInfo.get().info(m);

		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/linear_layout_username"))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/alertTitle", "Select Registration Type");
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='New Registration (MSISDN)']"))
				.click();
		TestUtils.assertSearchText("ID", "android:id/text1", "New Registration (MSISDN)");
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/next_button")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/page_title")));
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity:id/page_title", "New Registration (MSISDN)");
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity:id/dya_check_box", "Request for Yellow Account");

		// Try to enter invalid msisdn
		String invalidMsisdn = "Try to enter invalid MSISDN " + "(" + invalid_msisdn + ") " + "for validation";
		Markup i = MarkupHelper.createLabel(invalidMsisdn, ExtentColor.BLUE);
		testInfo.get().info(i);

		getDriver().findElement(By.id("com.sf.biocapture.activity:id/msisdn")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/msisdn")).sendKeys(invalid_msisdn);

		getDriver().findElement(By.id("com.sf.biocapture.activity:id/add_msisdn_button")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/button1")));
		TestUtils.assertSearchText("ID", "android:id/message", "Failure");
		getDriver().findElement(By.id("android:id/button1")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/msisdn")));

		// Try to enter valid msisdn

		String validMsisdn = "Try to enter valid MSISDN" + "(" + valid_msisdn + ") " + "for validation";
		Markup v = MarkupHelper.createLabel(validMsisdn, ExtentColor.BLUE);
		testInfo.get().info(v);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/msisdn")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/msisdn")).sendKeys(valid_msisdn);

		getDriver().findElement(By.id("com.sf.biocapture.activity:id/add_msisdn_button")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/next_button")));
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/next_button")).click();
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Personal Details']")));
		TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Personal Details']", "Personal Details");

		// Try to proceed with registration without supplying all mandatory fields
		String emptyField = "Try to proceed with registration without supplying all mandatory fields";
		Markup e = MarkupHelper.createLabel(emptyField, ExtentColor.BLUE);
		testInfo.get().info(e);
		TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity:id/btn_continue_reg");
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("com.sf.biocapture.activity:id/btn_continue_reg")));
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/btn_continue_reg")).click();
		//getDriver().findElement(By.id("com.sf.biocapture.activity:id/btn_continue_reg")).click();
		wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/message",
				"Registration Type\n" + "Please select Registration Type");
		Thread.sleep(1000);
		getDriver().findElement(By.id("android:id/button1")).click();

		Thread.sleep(1000);

		Form.NigerianCompanyForm();
		if (TestUtils.isElementPresent("XPATH", "//android.widget.TextView[@text='Sell Airtime/Data']")) {
			getDriver().findElement(By.className("android.widget.ImageButton")).click();
		}

		wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/kpi_report_name")));

	}
}
