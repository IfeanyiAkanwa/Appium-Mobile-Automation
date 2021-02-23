package admin;

import db.ConnectDB;
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

	@Parameters({ "dataEnv"})
	@Test
	public void noneSIMSwapPrivilegeTest(String dataEnv) throws Exception {
		WebDriverWait wait = new WebDriverWait(getDriver(), 30);
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resource/" + dataEnv + "/data.conf.json"));
		JSONObject envs = (JSONObject) config.get("SIMSwap");

		String valid_username = (String) envs.get("valid_username");
		String valid_password = (String) envs.get("valid_password");

		TestBase.Login1( valid_username, valid_password);
		Thread.sleep(500);
		TestUtils.testTitle("To confirm that a user without SIM Swap privilege can't access the module");
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/button_start_capture")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity." + Id + ":id/reg_type_placeholder")));
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity." + Id + ":id/reg_type_placeholder", "Registration Type");
		Thread.sleep(500);

		// Select LGA of Registration
		/*TestUtils.testTitle("Select LGA of Registration: " + lga);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/lga_of_reg")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/alertTitle", "LGA of Registration*");
		//getDriver().findElement(By.xpath("//android.widget.TextView[@text='" + lga + "']")).click();
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='[Select LGA]*']")).click();
		Thread.sleep(500);*/

		// Select SIM Swap
		TestUtils.testTitle("Select SIM Swap");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity." + Id + ":id/typeofreg")));
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/typeofreg")).click();
		Thread.sleep(500);
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity." + Id + ":id/alertTitle", "Select Registration Type");
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='SIM Swap']")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity." + Id + ":id/alertTitle")));
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity." + Id + ":id/alertTitle", "No Privilege");
		TestUtils.assertSearchText("ID", "android:id/message", "You are not allowed to access SIM Swap because you do not have the MSISDN SIM Swap privilege");
		Thread.sleep(500);
		getDriver().findElement(By.id("android:id/button1")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/message", "Capture session is currently active, Exit will cause loss of currently captured data! do you wish to cancel current capture session?");
		getDriver().findElement(By.id("android:id/button2")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Home']")));
		Thread.sleep(500);

		//Log out
		getDriver().findElement(By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/design_menu_item_text")));
		TestUtils.scrollDown();
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Logout']")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/message")));
		TestUtils.assertSearchText("ID", "android:id/message", "   Log out?");
		getDriver().findElement(By.id("android:id/button3")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/otp_login")));
	}

	@Test
	public static void navigateToCaptureMenuTest() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), 30);
		// Navigate to Registration Type
		TestUtils.testTitle("Navigate to Registration Type");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Home']")));
		getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/button_start_capture")).click();
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/reg_type_placeholder")));
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity.glo:id/reg_type_placeholder",
				"Registration Type");
	}

	@Parameters({ "dataEnv"})
	@Test
	public void navigateToSimSwapViewTest(String dataEnv) throws Exception {

		WebDriverWait wait = new WebDriverWait(getDriver(), 30);

		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resource/" + dataEnv + "/data.conf.json"));
		JSONObject envs = (JSONObject) config.get("SIMSwap");

		String lga = (String) envs.get("lga");
		String invalid_otp = (String) envs.get("invalid_otp");
		String expired_otp = (String) envs.get("expired_otp");
		String otp_phone_number = (String) envs.get("otp_phone_number");


		// Select LGA of Registration
//		String lgaa = "Select LGA of Registration: " + lga;
//		Markup m = MarkupHelper.createLabel(lgaa, ExtentColor.BLUE);
//		testInfo.get().info(m);
//		getDriver().findElement(By.id("com.sf.biocapture.activity:id/lga_of_reg")).click();
//		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/alertTitle")));
//		TestUtils.assertSearchText("ID", "android:id/alertTitle", "LGA of Registration*");
//		getDriver().findElement(By.xpath("//android.widget.TextView[@text='" + lga + "']")).click();
//		Thread.sleep(500);

		// Select Sim Swap
		TestUtils.testTitle("Select SIM Swap");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/typeofreg")));
		getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/typeofreg")).click();
		Thread.sleep(500);
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity.glo:id/alertTitle", "Select Registration Type");
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='SIM Swap']")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/next_button")).click();

		//Validate OTP if Setting is turend ON for SS
		try{
			TestUtils.testTitle("Validate OTP if it's turend ON in settings");
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/dialog_title")));
			TestUtils.assertSearchText("ID", "com.sf.biocapture.activity.glo:id/dialog_title", "OTP verification");
			TestUtils.assertSearchText("ID", "com.sf.biocapture.activity.glo:id/dialog_message", "Enter One Time Password sent to : 080*****430");

			//proceed with invalid OTP
			TestUtils.testTitle("Proceed with invalid OTP: "+invalid_otp);
			getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/user_input_dialog")).clear();
			getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/user_input_dialog")).sendKeys(invalid_otp);

			//Submit
			getDriver().findElement(By.id("android:id/button1")).click();

			//Assert Error Message
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/error_message")));
			TestUtils.assertSearchText("ID", "com.sf.biocapture.activity.glo:id/error_message", "There is no record with the otp, msisdn combination.");

			//proceed with expired OTP
			TestUtils.testTitle("Proceed with invalid OTP: "+expired_otp);
			getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/user_input_dialog")).clear();
			getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/user_input_dialog")).sendKeys(expired_otp);

			//Submit
			getDriver().findElement(By.id("android:id/button1")).click();

			//Assert Error Message
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/error_message")));
			TestUtils.assertSearchText("ID", "com.sf.biocapture.activity.glo:id/error_message", "There is no record with the otp, msisdn combination.");

			//proceed with valid OTP
			String valid_otp = ConnectDB.getOTP(otp_phone_number);
			TestUtils.testTitle("Proceed with invalid OTP: "+valid_otp);
			getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/user_input_dialog")).clear();
			getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/user_input_dialog")).sendKeys(valid_otp);

			//Submit
			getDriver().findElement(By.id("android:id/button1")).click();

		}catch (Exception e){
			TestUtils.testTitle("OTP Validation is turned OFF for SIM Swap");
		}

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/page_title")));
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity.glo:id/page_title", "SIM Swap");

	}
	
	@Parameters({ "dataEnv"})
    @Test
    public void simSwapTest(String dataEnv) throws Exception {

        WebDriverWait wait = new WebDriverWait(getDriver(), 30);
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resource/" + dataEnv + "/data.conf.json"));
		JSONObject envs = (JSONObject) config.get("SIMSwap");

		String lga = (String) envs.get("lga");
		String valid_Msisdn = (String) envs.get("valid_Msisdn");
		String invalid_Msisdn = (String) envs.get("invalid_Msisdn");
		
		// Submit without supplying Msisdn and leaving the plan at default
		TestUtils.testTitle("Submit without supplying Msisdn and leaving the plan at default");
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
