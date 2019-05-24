package com.seamfix.biosmart230;

import demographic230.Form;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
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
				.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/reg_type_placeholder")));
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity:id/reg_type_placeholder",
				"Registration Type");
	}

	@Parameters({ "dataEnv"})
	@Test
	public void CaptureNewMSISDNTest(String dataEnv) throws InterruptedException, IOException, ParseException {

		WebDriverWait wait = new WebDriverWait(getDriver(), 50);
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resource/" + dataEnv + "/data.conf.json"));
		JSONObject envs = (JSONObject) config.get("NewRegistration");
		
		String invalid_msisdn = (String) envs.get("invalid_msisdn");
		String valid_msisdn = (String) envs.get("valid_msisdn");
		String lga = (String) envs.get("lga");

		// Try to select LGA of Registration
		String lgaa = "Try to select LGA of Registration: " + lga;
		Markup m = MarkupHelper.createLabel(lgaa, ExtentColor.BLUE);
		testInfo.get().info(m);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/lga_of_reg")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/alertTitle", "LGA of Registration*");
		getDriver().findElement(By.id("android:id/search_src_text")).sendKeys(lga);
		getDriver().findElement(By.id("android:id/button1")).click();
		Thread.sleep(500);
		
		// Try to select New Registration MSISDN
		String newReg = "Try to select New Registration MSISDN";
		Markup d = MarkupHelper.createLabel(newReg, ExtentColor.BLUE);
		testInfo.get().info(d);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/typeofreg")));
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/typeofreg")).click();
		Thread.sleep(500);
		TestUtils.assertSearchText("ID", "android:id/alertTitle", "Select Item");
		TestUtils.assertSearchText("ID", "android:id/text1", "[Select Registration Type]");
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='New Registration (MSISDN)']")).click();
		Thread.sleep(500);
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
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Personal Details']")));
		TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Personal Details']", "Personal Details");

		// Try to proceed with registration without supplying all mandatory fields
		String emptyField = "Try to proceed with registration without supplying all mandatory fields";
		Markup e = MarkupHelper.createLabel(emptyField, ExtentColor.BLUE);
		testInfo.get().info(e);
		TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity:id/btn_continue_reg");
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("com.sf.biocapture.activity:id/btn_continue_reg")));
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/btn_continue_reg")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/alertTitle")));
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity:id/alertTitle", "Error");
		TestUtils.assertSearchText("ID", "android:id/message", "\n" + 
				"Surname: Empty field \n" + 
				"First Name: Empty field \n" + 
				"Mother's Maiden Name: Empty field \n" + 
				"Date of Birth: Empty field \n" + 
				"Gender: Empty field \n" + 
				"");
		getDriver().findElement(By.id("android:id/button1")).click();
		Thread.sleep(1000);
		
		Form.NigerianCompanyForm(dataEnv);
	}
}