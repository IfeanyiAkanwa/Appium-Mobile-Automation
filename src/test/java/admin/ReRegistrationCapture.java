package admin;

import db.ConnectDB;
import demographics.Form;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

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

public class ReRegistrationCapture extends TestBase {
  
    @Test
	public static void navigateToReRegistration() {
    	WebDriverWait wait = new WebDriverWait(getDriver(), 60);

		// Navigate to Re-Registration Use Case
		TestUtils.testTitle("Navigate to Re-Registration Use Case");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Home']")));
		getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/button_start_capture")).click();
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/reg_type_placeholder")));
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity.glo:id/reg_type_placeholder", "Registration Type");

		//Select Reg LG
//		getDriver().findElement(By.xpath("//android.widget.TextView[@text='Agege']")).click();
//		wait.until(ExpectedConditions
//				.visibilityOfElementLocated(By.id("android:id/alertTitle")));
//		TestUtils.assertSearchText("ID", "android:id/alertTitle", "LGA of Registration*");
//		getDriver().findElement(By.id("android:id/search_src_text")).clear();
//		getDriver().findElement(By.id("android:id/search_src_text")).sendKeys("Eti Osa");
//		getDriver().findElement(By.xpath("//android.widget.TextView[@text='Eti Osa']")).click();
//		wait.until(ExpectedConditions
//				.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/reg_type_placeholder")));

		//Select Re-Reg
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='[Select Registration Type]']")).click();
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/alertTitle")));
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity.glo:id/alertTitle", "Select Registration Type");
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Re-Registration']")).click();
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/reg_type_placeholder")));

		//click Next Button
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/next_button")));
		getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/next_button")).click();

		//Confirm re-reg view is displayed
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Re Registration']")));
		TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Re Registration']", "Re Registration");


	}

    @Parameters({ "dataEnv"})
    @Test
    public void reRegisteration(String dataEnv) throws Exception {

    	WebDriverWait wait = new WebDriverWait(getDriver(), 60);
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resource/" + dataEnv + "/data.conf.json"));
		JSONObject envs = (JSONObject) config.get("ReRegistration");
		
		String invalid_msisdn = (String) envs.get("invalid_msisdn");
		String valid_msisdn = (String) envs.get("valid_msisdn");
		String invalid_OTP = (String) envs.get("invalid_OTP");
		String msisdnLessThanSix = (String) envs.get("msisdnLessThanSix");
		String unrecognizedMsisdn = (String) envs.get("unrecognizedMsisdn");
		String msisdnWithFingerprint = (String) envs.get("msisdnWithFingerprint");
		String ninVerificationMode = (String) envs.get("ninVerificationMode");
		String nin = (String) envs.get("nin");

		// Proceed without supplying msisdn
		TestUtils.testTitle("Proceed without supplying msisdn");
		getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/primary_msisdn_field")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/primary_msisdn_field")).sendKeys();
		getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/submit_button")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/message", "Required Input Field: Phone Number");
		getDriver().findElement(By.id("android:id/button1")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Re Registration']")));

		//Enter MSISDN Less than 6 digits
		TestUtils.testTitle("Proceed with MSISDN Less than 6 digits");
		getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/primary_msisdn_field")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/primary_msisdn_field")).sendKeys(msisdnLessThanSix);
		getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/submit_button")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/message", "Entered Phone Number is invalid. Entered value should not be less than 6 or more than 11 digits.");
		getDriver().findElement(By.id("android:id/button1")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Re Registration']")));

		//Enter Unrecognized MSISDN
		TestUtils.testTitle("Proceed with Unrecognized MSISDN: ("+ unrecognizedMsisdn +" )");
		getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/primary_msisdn_field")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/primary_msisdn_field")).sendKeys(unrecognizedMsisdn);
		getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/submit_button")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/message", "The MSISDN has an unrecognized National Destination Code. Please ensure the MSISDN starts with any of the following NDCs: 0701,0708,0802,0808,0812,0901,0902,0904,0907,0809,0817,0818,0908,0909,0705,0805,0807,0811,0815,0905,0915,0703,0706,0803,0806,0810,0813,0814,0816,0903,0906");
		getDriver().findElement(By.id("android:id/button1")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Re Registration']")));
		
		// Enter invalid msisdn 
		TestUtils.testTitle("Enter invalid MSISDN: " + invalid_msisdn + " for Re-Registration");
		getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/primary_msisdn_field")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/primary_msisdn_field")).sendKeys(invalid_msisdn);
		getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/submit_button")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/message", "Record not found");
		getDriver().findElement(By.id("android:id/button1")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Re Registration']")));


		// Enter valid msisdn with invalid OTP
		TestUtils.testTitle("Enter valid MSISDN: " + valid_msisdn + " for Re-Registration with invalid OTP: " + invalid_OTP);

		getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/primary_msisdn_field")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/primary_msisdn_field")).sendKeys(valid_msisdn);
		getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/submit_button")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/otp_field")));
		getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/otp_field")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/otp_field")).sendKeys(invalid_OTP);
		getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/otp_confirm_button")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/message", "There is no record with the otp, msisdn combination.");
		getDriver().findElement(By.id("android:id/button1")).click();

		//OTP Buttons
		TestUtils.testTitle("Confirm Request OTP, Confirm OTP and Bypass OTP buttons are displayed");
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity.glo:id/otp_confirm_button", "CONFIRM OTP");
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity.glo:id/otp_bypass_button", "BYPASS OTP");
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity.glo:id/otp_request_button", "REQUEST OTP");

		//Confirm that BYPASS OTP Button is functional
		TestUtils.testTitle("BYPASS OTP Test");
		getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/otp_bypass_button")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/next_button")));
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity.glo:id/next_button", "NEXT");

		//Confirm COO checkbox
		TestUtils.testTitle("Change of Ownership (COO) Checkbox Test");
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity.glo:id/change_sim_ownership", "Change of Ownership");
		getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/change_sim_ownership")).click();
		getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/change_sim_ownership")).click();
		getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/change_sim_ownership")).click();

		//MSISDN With Fingerprint
		TestUtils.testTitle("Verify that fingerprint match option is the default option available to the user if user fingerprint is returned (WSQL is retreived): "+msisdnWithFingerprint);
		getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/primary_msisdn_field")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/primary_msisdn_field")).sendKeys(msisdnWithFingerprint);
		getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/submit_button")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/verify_finger_print_button")));
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity.glo:id/verify_finger_print_button", "VERIFY FINGERPRINT");

		// Enter valid msisdn with valid OTP
		TestUtils.testTitle("Enter valid MSISDN with valid OTP: " + valid_msisdn + " for Re-Registration");

		getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/primary_msisdn_field")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/primary_msisdn_field")).sendKeys(valid_msisdn);
		getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/submit_button")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/otp_field")));

		//Test Cancel Button on Request OTP Modal
		getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/otp_request_button")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/otpHintMessage")));
		getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/cancel_otp")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/otp_request_button")));

		//Request OTP Modal
		TestUtils.testTitle("Request OTP Modal");
		getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/otp_request_button")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/otpHintMessage")));
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity.glo:id/otpHintMessage", "Tick options for OTP Request");
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity.glo:id/primary_phone_number", "Primary Phone Number");
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity.glo:id/alt_phone_number", "Alternate Phone Number");
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity.glo:id/email_address", "Email Address");
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity.glo:id/cancel_otp", "CANCEL");
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity.glo:id/request_otp", "REQUEST");
		getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/alt_phone_number")).click();
		getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/request_otp")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/otp_field")));

		// DB Connection for OTP
    	String valid_OTP = ConnectDB.getOTP(valid_msisdn);
		String ValidOTP = "Enter valid OTP : " + valid_OTP;
		Markup o = MarkupHelper.createLabel(ValidOTP, ExtentColor.BLUE);
		testInfo.get().info(o);

		String assertDetails = "Assert user's full name";
		Markup ad = MarkupHelper.createLabel(assertDetails, ExtentColor.BLUE);
		testInfo.get().info(ad);
		String NA = "N/A";
		Thread.sleep(1000);
		String surname = getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/tv_surname")).getText();
		String firstname = getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/tv_first_name")).getText();

		String[] toList = { "Surname: " + surname, "First name: " + firstname };
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

        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/otp_field")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/otp_field")).sendKeys(valid_OTP);
        getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/otp_confirm_button")).click();

        // Next button
		try{
			if(getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/next_button")).isDisplayed()){
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/next_button")));
				getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/next_button")).click();
			}

		}catch (Exception e){

		}

        try{
			if(getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/alertTitle")).isDisplayed()){

				//NIN Verification
				TestBase.verifyNINTest(nin, ninVerificationMode);

				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Personal Details']")));
				TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Personal Details']", "Personal Details");
			}
        } catch(Exception e){
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Personal Details']")));
			TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Personal Details']", "Personal Details");
		}


        //Confirm that user can do both Individual and Company Registration
		TestUtils.testTitle("Confirm that user can do both Individual and Company Registration");
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='Individual']")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.CheckedTextView[@text='Company']")));
		TestUtils.assertSearchText("XPATH", "//android.widget.CheckedTextView[@text='Company']", "Company");
		TestUtils.assertSearchText("XPATH", "//android.widget.CheckedTextView[@text='Individual']", "Individual");
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Company']")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/company_details_title")));
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity.glo:id/company_details_title", "Company Details");
		getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/company_cancel")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Company']")));
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='Company']")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.CheckedTextView[@text='Individual']")));
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Individual']")).click();

		//Assert Invividual Form
		Asserts.AssertIndividualForm();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/btnContinueReg")));
		getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/btnContinueReg")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/countrySpinner")));

		Asserts.AssertAddresstDetails();

		TestUtils.scrollUp();
		TestUtils.scrollUp();
		TestUtils.scrollUp();
		//Confirm User can do Foreigner registration
		TestUtils.testTitle("Confirm User can do Foreigner registration");
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='NIGERIA']")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/alertTitle", "Select Item");
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='AFGHANISTAN']")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/field_issuing_country")));
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity.glo:id/field_issuing_country", "Passport Issuing Country*");
		getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/passport_cancel")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.CheckedTextView[@text='AFGHANISTAN']")));
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='AFGHANISTAN']")).click();
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='NIGERIA']")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.CheckedTextView[@text='NIGERIA']")));

		getDriver().findElement(By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']")).click();

	    Form.individualForeignerForm(dataEnv);
    }
}
