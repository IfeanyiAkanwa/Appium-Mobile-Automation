
package admin;

import java.io.FileReader;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import db.ConnectDB;
import utils.TestBase;
import utils.TestUtils;

public class ReactivationTest extends TestBase {

    @Parameters({ "dataEnv"})
	@Test
	public void noneReactivationPrivilegeTest(String dataEnv) throws Exception {
    	 WebDriverWait wait = new WebDriverWait(getDriver(), 60);
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resource/" + dataEnv + "/data.conf.json"));
		JSONObject envs = (JSONObject) config.get("Reactivation");
		
		String valid_username = (String) envs.get("valid_username");
		String valid_password = (String) envs.get("valid_password");
		String lga = (String) envs.get("lga");

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/otp_login")));
		TestBase.Login1( valid_username, valid_password);
		Thread.sleep(500);
		TestUtils.testTitle("To confirm that a user without Msisdn Reactivation privilege can't access the module");
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/button_start_capture")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/reg_type_placeholder")));
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/reg_type_placeholder", "Registration Type");
		Thread.sleep(500);
		
		// Select LGA of Registration
		TestUtils.testTitle("Select LGA of Registration: " + lga);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/lga_of_reg")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/alertTitle", "LGA of Registration*");
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='" + lga + "']")).click();
		Thread.sleep(500);
		
		// Select MSISDN Re-Activation
		TestUtils.testTitle("Select MSISDN Re-Activation");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/typeofreg")));
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/typeofreg")).click();
		Thread.sleep(500);
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/alertTitle", "Select Registration Type");
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Re-Registration']")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/alertTitle", "No Privilege");
		TestUtils.assertSearchText("ID", "android:id/message", "You are not allowed to access Re-Registration because you do not have the Re-Registration privilege");
		Thread.sleep(500);
		getDriver().findElement(By.id("android:id/button1")).click();
		Thread.sleep(500);
		
		// Log out
		TestUtils.testTitle("Logout username: "  + valid_username);
		getDriver().findElement(By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
		getDriver().findElement(By.id("android:id/button2")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']")).click();
		Thread.sleep(500);
		TestUtils.scrollDown();
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Logout']")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/message")));
		TestUtils.assertSearchText("ID", "android:id/message", "   Log out?");
		getDriver().findElement(By.id("android:id/button3")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/otp_login")));
		Thread.sleep(500);


	}


	@Parameters({ "dataEnv"})
	@Test
	public void validateMsisdn(String dataEnv) throws Exception {

		WebDriverWait wait = new WebDriverWait(getDriver(), 60);
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resource/" + dataEnv + "/data.conf.json"));
		JSONObject envs = (JSONObject) config.get("Reactivation");

		String invalid_msisdn = (String) envs.get("invalid_msisdn");
		String valid_msisdn = (String) envs.get("valid_msisdn");
		String lga = (String) envs.get("lga");
		String invalid_OTP = (String) envs.get("invalid_OTP");
		String nin = (String) envs.get("nin");

		// Select LGA of Registration
		TestUtils.testTitle("Select LGA of Registration: " + lga);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/lga_of_reg")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/alertTitle", "LGA of Registration*");
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='" + lga + "']")).click();
		Thread.sleep(500);

		// Select MSISDN Re-Activation
		TestUtils.testTitle("Select MSISDN Re-Activation");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/typeofreg")));
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/typeofreg")).click();
		Thread.sleep(500);
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/alertTitle", "Select Registration Type");
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Re-Registration']")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/next_button")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='48 hours MSISDN Reactivation']")));
		TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='48 hours MSISDN Reactivation']", "48 hours MSISDN Reactivation");
		getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/use_case_switch")).click();
		Thread.sleep(500);

		// Proceed without supplying msisdn
		TestUtils.testTitle("Proceed without supplying msisdn");
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/primary_msisdn_field")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/submit_button")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/message", "Required Input Field: Phone Number");
		getDriver().findElement(By.id("android:id/button1")).click();
		Thread.sleep(1000);

		// Enter invalid msisdn
		TestUtils.testTitle("Enter invalid MSISDN: " + invalid_msisdn + " for validation");
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/primary_msisdn_field")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/primary_msisdn_field")).sendKeys(invalid_msisdn);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/submit_button")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
		Thread.sleep(2000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/message", "MSISDN is not registered and cannot be used for this use case");
		getDriver().findElement(By.id("android:id/button1")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='MSISDN Reactivation']")));

		// Enter valid msisdn with invalid OTP
		TestUtils.testTitle("Enter valid msisdn: " + valid_msisdn + " with invalid OTP: " + invalid_OTP);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/primary_msisdn_field")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/primary_msisdn_field")).sendKeys(valid_msisdn);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/submit_button")).click();

		//wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/capture_image_button")));
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/capture_image_button", "Verify Biometrics");
    }

		/*//Capture
		try{
			//Capture
			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/btn_capture_portrait")).click();
			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/captureButton")).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/validate_serial_button")));
			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/validate_serial_button")).click();

			//Verify NIN
			TestBase.verifyNINTest(nin, "Search By NIN");
		}catch (Exception e){

		}

	}

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/otp_field")));

		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/otp_field")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/otp_field")).sendKeys(invalid_OTP);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/otp_confirm_button")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/message", "There is no record with the otp, msisdn combination.");
		getDriver().findElement(By.id("android:id/button1")).click();
		Thread.sleep(500);
		//OTP Buttons
		TestUtils.testTitle("Confirm Request OTP, Confirm OTP and Bypass OTP buttons are displayed");
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/otp_confirm_button", "CONFIRM OTP");
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/otp_request_button", "REQUEST OTP");

		// Enter valid msisdn with valid OTP
		TestUtils.testTitle("Enter valid msisdn with valid OTP: " + valid_msisdn + " for validation");
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/primary_msisdn_field")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/primary_msisdn_field")).sendKeys(valid_msisdn);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/submit_button")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/otp_field")));
		Thread.sleep(1000);


		//Test Cancel Button on Request OTP Modal
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/otp_request_button")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/otpHintMessage")));
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/cancel_otp")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/otp_request_button")));

		//Request OTP Modal
		TestUtils.testTitle("Request OTP Modal");
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/otp_request_button")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/otpHintMessage")));
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/otpHintMessage", "Tick options for OTP Request");
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/primary_phone_number", "Primary Phone Number");
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/alt_phone_number", "Alternate Phone Number");
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/email_address", "Email Address");
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/cancel_otp", "CANCEL");
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/request_otp", "REQUEST");
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/alt_phone_number")).click();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/request_otp")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/otp_field")));


		// DB Connection for OTP
		String valid_OTP = ConnectDB.getOTP(valid_msisdn);

		TestUtils.testTitle("Enter valid OTP: " + valid_OTP);

		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/otp_field")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/otp_field")).sendKeys(valid_OTP);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/otp_confirm_button")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/reactivate_button")));
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/reactivate_button", "REACTIVATE SUBSCRIBER");

	}

	@Parameters({ "dataEnv"})
	@Test
    public void msisdnReactivationTest(String dataEnv) throws Exception {

        WebDriverWait wait = new WebDriverWait(getDriver(), 60);

		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/reactivate_button")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='MSISDN has been reactivated successfully.']")));
		TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='MSISDN has been reactivated successfully.']", "MSISDN has been reactivated successfully.");
		getDriver().findElement(By.id("android:id/button1")).click();
    }*/
}
