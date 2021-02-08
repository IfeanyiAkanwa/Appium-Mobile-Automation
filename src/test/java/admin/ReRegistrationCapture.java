package admin;

import db.ConnectDB;
import demographics.Form;

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

import utils.Asserts;
import utils.TestBase;
import utils.TestUtils;

public class ReRegistrationCapture extends TestBase {
  
    @Test
	public static void navigateToReRegistration() throws InterruptedException {
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
		TestUtils.assertSearchText("ID", "android:id/message", "Could not retrieve bio data for the specified msisdn.");
		getDriver().findElement(By.id("android:id/button1")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Re Registration']")));

//		// Enter valid msisdn with invalid OTP
//		String validMsisdnn = "Enter valid MSISDN: " + valid_msisdn + " for Re-Registration with invalid OTP: " + invalid_OTP;
//		Markup k = MarkupHelper.createLabel(validMsisdnn, ExtentColor.BLUE);
//		testInfo.get().info(k);
//		getDriver().findElement(By.id("com.sf.biocapture.activity:id/primary_msisdn_field")).clear();
//		getDriver().findElement(By.id("com.sf.biocapture.activity:id/primary_msisdn_field")).sendKeys(valid_msisdn);
//		getDriver().findElement(By.id("com.sf.biocapture.activity:id/submit_button")).click();
//		Thread.sleep(500);
//		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/otp_field")));
//		Thread.sleep(500);
//		getDriver().findElement(By.id("com.sf.biocapture.activity:id/otp_field")).clear();
//		getDriver().findElement(By.id("com.sf.biocapture.activity:id/otp_field")).sendKeys(invalid_OTP);
//		getDriver().findElement(By.id("com.sf.biocapture.activity:id/otp_confirm_button")).click();
//		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/alertTitle")));
//		TestUtils.assertSearchText("ID", "android:id/message", "There is no record with the otp, msisdn combination.");
//		getDriver().findElement(By.id("android:id/button1")).click();
//		Thread.sleep(1000);
//
//		// Enter valid msisdn with valid OTP
//		String validMsisdn = "Enter valid MSISDN with valid OTP: " + valid_msisdn + " for Re-Registration";
//		Markup j = MarkupHelper.createLabel(validMsisdn, ExtentColor.BLUE);
//		testInfo.get().info(j);
//		getDriver().findElement(By.id("com.sf.biocapture.activity:id/primary_msisdn_field")).clear();
//		getDriver().findElement(By.id("com.sf.biocapture.activity:id/primary_msisdn_field")).sendKeys(valid_msisdn);
//		getDriver().findElement(By.id("com.sf.biocapture.activity:id/submit_button")).click();
//		Thread.sleep(500);
//		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/otp_field")));
//		Thread.sleep(1000);
//		getDriver().findElement(By.id("com.sf.biocapture.activity:id/otp_request_button")).click();
//		Thread.sleep(500);
//
//		// DB Connection for OTP
//    	String valid_OTP = ConnectDB.getOTP(valid_msisdn);
//		String ValidOTP = "Enter valid OTP : " + valid_OTP;
//		Markup o = MarkupHelper.createLabel(ValidOTP, ExtentColor.BLUE);
//		testInfo.get().info(o);
//        if(valid_OTP == null){
//        	testInfo.get().log(Status.INFO, "Can't get otp.");
//            getDriver().quit();
//        }
//
//		String assertDetails = "Assert user's full name";
//		Markup ad = MarkupHelper.createLabel(assertDetails, ExtentColor.BLUE);
//		testInfo.get().info(ad);
//		String NA = "N/A";
//		Thread.sleep(1000);
//		String surname = getDriver().findElement(By.id("com.sf.biocapture.activity:id/tv_surname")).getText();
//		String firstname = getDriver().findElement(By.id("com.sf.biocapture.activity:id/tv_first_name")).getText();
//
//		String[] toList = { "Surname: " + surname, "First name: " + firstname };
//		for (String field : toList) {
//			String name = "";
//			String val = NA;
//			if (field.endsWith(":")) {
//				field = field + val;
//			}
//			try {
//				String[] fields = field.split(":");
//				name = fields[0];
//				val = fields[1];
//				Assert.assertNotEquals(val, NA);
//				testInfo.get().log(Status.INFO, name + " : " + val);
//			} catch (Error e) {
//				testInfo.get().error(name + " : " + val);
//
//			}
//		}
//
//        Thread.sleep(1000);
//        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/otp_field")));
//        getDriver().findElement(By.id("com.sf.biocapture.activity:id/otp_field")).clear();
//        getDriver().findElement(By.id("com.sf.biocapture.activity:id/otp_field")).sendKeys(valid_OTP);
//        getDriver().findElement(By.id("com.sf.biocapture.activity:id/otp_confirm_button")).click();
//        Thread.sleep(1000);
//
//        // Next button
//        try {
//            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/next_button")));
//            Thread.sleep(500);
//            getDriver().findElement(By.id("com.sf.biocapture.activity:id/next_button")).click();
//		} catch (Exception e) {
//
//		}
//
//        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Personal Details']")));
//		Thread.sleep(1000);
//		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/surNameTXT")));
//		Thread.sleep(500);
//		Asserts.AssertIndividualForm();
//		Thread.sleep(1000);
//		TestUtils.scrollDown();
//		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/btnContinueReg")));
//		Thread.sleep(500);
//		getDriver().findElement(By.id("com.sf.biocapture.activity:id/btnContinueReg")).click();
//		Thread.sleep(500);
//		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/countrySpinner")));
//		Thread.sleep(1000);
//		Asserts.AssertAddresstDetails();
//		Thread.sleep(1000);
//		getDriver().findElement(By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']")).click();
//		Thread.sleep(1000);
//	    Form.NigerianCompanyForm(dataEnv);
 
    }
}
