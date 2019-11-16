package com.seamfix.biosmart230;

import db.ConnectDB;

import java.io.File;
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

import DemographicForm.Form;
import utils.Asserts;
import utils.TestBase;
import utils.TestUtils;

public class ReRegistrationCapture extends TestBase {
  
    @Test
	public static void navigateToCaptureMenuTest() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), 30);
		// Navigate to Registration Type
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
    public void reRegisterationTest(String dataEnv) throws Exception {

    	WebDriverWait wait = new WebDriverWait(getDriver(), 60);
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resource/" + dataEnv + "/data.conf.json"));
		JSONObject envs = (JSONObject) config.get("ReRegistration");
		JSONObject envs2 = (JSONObject) config.get("CompanyDetails");
		
		String invalid_msisdn = (String) envs.get("invalid_msisdn");
		String valid_msisdn = (String) envs.get("valid_msisdn");
		String lga = (String) envs.get("lga");
		String alternate_phone = (String) envs.get("alternate_phone");
		String invalid_OTP = (String) envs.get("invalid_OTP");
		String state = (String) envs.get("state");
		String LGA = (String) envs.get("LGA");
		
		String company_description = (String) envs2.get("company_description");
		String company_regno = (String) envs2.get("company_regno");
		String house_or_flat_no = (String) envs2.get("house_or_flat_no");
		String company_street = (String) envs2.get("company_street");
		String company_city = (String) envs2.get("company_city");
		String company_state_address = (String) envs2.get("company_state_address");
		String company_lga_address = (String) envs2.get("company_lga_address");
		String company_postalcode = (String) envs2.get("company_postalcode");
		
		// Select LGA of Registration
		String lgaa = "Select LGA of Registration: " + lga;
		Markup m = MarkupHelper.createLabel(lgaa, ExtentColor.BLUE);
		testInfo.get().info(m);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/lga_of_reg")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/alertTitle", "LGA of Registration*");
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='" + lga + "']")).click();
		Thread.sleep(500);
		
		//Select Re-Registration
		String newReg = "Select Re-Registration";
		Markup d = MarkupHelper.createLabel(newReg, ExtentColor.BLUE);
		testInfo.get().info(d);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/typeofreg")));
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/typeofreg")).click();
		Thread.sleep(500);
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity:id/alertTitle", "Select Registration Type");
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Re-Registration']")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/next_button")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Re Registration']")));
		TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Re Registration']", "Re Registration");
	
		// Enter invalid msisdn 
		String invalidMsisdn = "Enter invalid MSISDN: " + invalid_msisdn + " for Re-Registration";
		Markup i = MarkupHelper.createLabel(invalidMsisdn, ExtentColor.BLUE);
		testInfo.get().info(i);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/primary_msisdn_field")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/primary_msisdn_field")).sendKeys(invalid_msisdn);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/submit_button")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/alertTitle")));
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity:id/alertTitle", "Error");
		TestUtils.assertSearchText("ID", "android:id/message", "Could not retrieve bio data for the specified msisdn.");
		getDriver().findElement(By.id("android:id/button1")).click();
		Thread.sleep(500);
		
		// Enter valid msisdn with invalid OTP
		String validMsisdnn = "Enter valid MSISDN: " + valid_msisdn + " for Re-Registration with invalid OTP: " + invalid_OTP;
		Markup k = MarkupHelper.createLabel(validMsisdnn, ExtentColor.BLUE);
		testInfo.get().info(k);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/primary_msisdn_field")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/primary_msisdn_field")).sendKeys(valid_msisdn);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/submit_button")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/otp_field")));
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/otp_field")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/otp_field")).sendKeys(invalid_OTP);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/otp_confirm_button")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/alertTitle")));
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity:id/alertTitle", "Error");
		TestUtils.assertSearchText("ID", "android:id/message", "There is no record with the otp, msisdn combination.");
		getDriver().findElement(By.id("android:id/button1")).click();
		Thread.sleep(1000);
		
		// Enter valid msisdn with valid OTP
		String validMsisdn = "Enter valid MSISDN with valid OTP: " + valid_msisdn + " for Re-Registration";
		Markup j = MarkupHelper.createLabel(validMsisdn, ExtentColor.BLUE);
		testInfo.get().info(j);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/primary_msisdn_field")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/primary_msisdn_field")).sendKeys(valid_msisdn);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/submit_button")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/otp_field")));
		Thread.sleep(1000);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/otp_request_button")).click();
		Thread.sleep(500);
		
		  // DB Connection for OTP
    	String valid_OTP = ConnectDB.getOTP(valid_msisdn);

		String ValidOTP = "Enter valid OTP : " + valid_OTP;
		Markup o = MarkupHelper.createLabel(ValidOTP, ExtentColor.BLUE);
		testInfo.get().info(o);
        if(valid_OTP == null){
        	testInfo.get().log(Status.INFO, "Can't get otp.");
            getDriver().quit();
        }
        Thread.sleep(1000);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/otp_field")));
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/otp_field")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/otp_field")).sendKeys(valid_OTP);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/otp_confirm_button")).click();
        Thread.sleep(1000);
        String assertDetails = "Assert user's full name";
		Markup ad = MarkupHelper.createLabel(assertDetails, ExtentColor.BLUE);
		testInfo.get().info(ad);
		String NA = "N/A";
		Thread.sleep(1000);
		String surname = getDriver().findElement(By.id("com.sf.biocapture.activity:id/tv_surname")).getText();
		String firstname = getDriver().findElement(By.id("com.sf.biocapture.activity:id/tv_first_name")).getText();
		
		String[] toList = { "Surname: " + surname, "First name: " + firstname};
		for (String field : toList) {
			String name = "";
			String val = NA;
			if (field.endsWith(":")) {
				field = field + val;
			}
			try {
				String[] fields = field.split(":");
				name = fields[0];
				val = fields[1];
				Assert.assertNotEquals(val, NA);
				testInfo.get().log(Status.INFO, name + " : " + val);
			} catch (Error e) {
				testInfo.get().error(name + " : " + val);

			}
		}
		Thread.sleep(500);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/next_button")));
        Thread.sleep(500);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/next_button")).click();
        
		// Company details
		wait.until(ExpectedConditions.visibilityOfElementLocated((By.id("com.sf.biocapture.activity:id/reg_type"))));
		Thread.sleep(1000);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/reg_type")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.CheckedTextView[@text='Company']")));
		Thread.sleep(1000);
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Company']")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated((By.id("com.sf.biocapture.activity:id/company_details_title"))));
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity:id/company_details_title", "Company Details");
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/company_name_descrptn")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/company_name_descrptn")).sendKeys(company_description);
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/company_regno")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/company_regno")).sendKeys(company_regno);
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/house_or_flat_no")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/house_or_flat_no")).sendKeys(house_or_flat_no);
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/street")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/street")).sendKeys(company_street);
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/city")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/city")).sendKeys(company_city);
		Thread.sleep(500);

		// Company Address State
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/company_state_address")).click();
		Thread.sleep(500);
		TestUtils.assertSearchText("ID", "android:id/text1", "[Select State]*");
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='" + company_state_address + "']")).click();
		Thread.sleep(500);

		// Company Address LGA
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/company_lga_address")).click();
		Thread.sleep(500);
		TestUtils.assertSearchText("ID", "android:id/text1", "[Select LGA]*");
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='" + company_lga_address + "']")).click();
		Thread.sleep(500);

		getDriver().findElement(By.id("com.sf.biocapture.activity:id/company_postalcode")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/company_postalcode")).sendKeys(company_postalcode);
		Thread.sleep(500);

		// Certificate of Incorporation
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/document_type_spinner")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='Certificate of Incorporation *']")).click();
		Thread.sleep(500);

		// Push File
		File pic = new File(System.getProperty("user.dir") + "/files/idCard.jpg");
		getDriver().pushFile("/mnt/sdcard/picture.jpg", pic);

		getDriver().findElement(By.id("com.sf.biocapture.activity:id/btn_load_document")).click();
		Thread.sleep(500);
		TestUtils.scrollUntilElementIsVisible("XPATH", "//android.widget.TextView[@text='picture.jpg']");
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='picture.jpg']")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/btn_remove_document")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/btn_load_document")).click();
		Thread.sleep(1000);
		TestUtils.scrollUntilElementIsVisible("XPATH", "//android.widget.TextView[@text='picture.jpg']");
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='picture.jpg']")).click();
		Thread.sleep(500);

		// contact person form
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/document_type_spinner")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='Contact Person Form *']")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/btn_remove_document")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/btn_load_document")).click();
		Thread.sleep(1000);
		TestUtils.scrollUntilElementIsVisible("XPATH", "//android.widget.TextView[@text='picture.jpg']");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='picture.jpg']")).click();
		Thread.sleep(500);

		// save
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/company_ok")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Personal Details']")));
		TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Personal Details']", "Personal Details");
		
        Thread.sleep(2000);
        Asserts.AssertIndividualForm230();
        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity:id/btn_continue_reg");
        Thread.sleep(1000);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/btn_continue_reg")));
        Thread.sleep(500);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/btn_continue_reg")).click();
        Thread.sleep(500);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/nationality")));
        Thread.sleep(1000);
        Asserts.AssertAddresstDetails230();
        Thread.sleep(1000);
      
		// Next button
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/save_continue")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Capture Data']")));
		TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Capture Data']", "Capture Data");
		TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Identification Type']","Identification Type");
		Thread.sleep(500);

		// Capture Data
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='[Select Identification Type]']")).click();
		Thread.sleep(500);
		TestUtils.assertSearchText("XPATH", "//android.widget.CheckedTextView[@text='[Select Identification Type]']",
				"[Select Identification Type]");
		Thread.sleep(500);

		// Capture ID CARD
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='International Passport']")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/capture_id_button")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/button_camera_capture")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/ok"))).click();
		Thread.sleep(500);

		// View Captured ID
		String capturedID = "View captured ID";
		Markup c = MarkupHelper.createLabel(capturedID, ExtentColor.BLUE);
		testInfo.get().info(c);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/view_id_button")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/title")));
		TestUtils.assertSearchText("ID", "android:id/title", "Preview");
		if (getDriver().findElement(By.id("com.sf.biocapture.activity:id/image")).isDisplayed()) {
			testInfo.get().info("Captured ID is displayed");
		} else {
			testInfo.get().info("Captured ID is not displayed");
			testInfo.get().addScreenCaptureFromPath(TestUtils.addScreenshot());
		}
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/done_button")).click();
		Thread.sleep(500);

		// Proceed with registration without completing the capture process
		String emptyField = "Proceed with registration without completing the capture process";
		Markup f = MarkupHelper.createLabel(emptyField, ExtentColor.BLUE);
		testInfo.get().info(f);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/next_button")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/alertTitle")));
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity:id/alertTitle", "Error");
		TestUtils.assertSearchText("ID", "android:id/message",
				"\n" + "KYC Form: Please Capture KYC/REGISTRATION Form \n"
						+ "Face Capture: Please capture valid portrait \n" + "");
		getDriver().findElement(By.id("android:id/button1")).click();
		Thread.sleep(1000);

		// Capture KYC FORM
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/capture_kyc_form_button")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/button_camera_capture")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/ok"))).click();
		Thread.sleep(500);

		// View captured KYC Form
		String capturedKycForm = "View captured KYC Form";
		Markup b = MarkupHelper.createLabel(capturedKycForm, ExtentColor.BLUE);
		testInfo.get().info(b);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/view_kyc_form_button")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/title")));
		TestUtils.assertSearchText("ID", "android:id/title", "Preview");
		if (getDriver().findElement(By.id("com.sf.biocapture.activity:id/image")).isDisplayed()) {
			testInfo.get().info("Captured ID is displayed");
		} else {
			testInfo.get().info("Captured ID is not displayed");
			testInfo.get().addScreenCaptureFromPath(TestUtils.addScreenshot());
		}
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/done_button")).click();
		Thread.sleep(500);

		// Face Capture
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/face_capture_button")).click();
		Thread.sleep(500);
    }
}
