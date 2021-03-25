package admin;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.Asserts;
import utils.TestBase;
import utils.TestUtils;

import java.io.FileReader;

public class AdditionalRegistration extends TestBase {

	@Parameters({ "dataEnv"})
	@Test
	public void noneAdditionalPrivilegeTest(String dataEnv) throws Exception {
		WebDriverWait wait = new WebDriverWait(getDriver(), 30);
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resource/" + dataEnv + "/data.conf.json"));
		JSONObject envs = (JSONObject) config.get("AdditionalRegistration");

		String valid_username = (String) envs.get("valid_username");
		String valid_password = (String) envs.get("valid_password");
		TestBase.Login1( valid_username, valid_password);
		Thread.sleep(500);
		TestUtils.testTitle("To confirm that a user without additional registration privilege can't access the module");
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/button_start_capture")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/reg_type_placeholder")));
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/reg_type_placeholder", "Registration Type");
		Thread.sleep(500);

		// Check that Additional Registration is not selectable
		TestUtils.testTitle("Check that additional registration is not selectable");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/typeofreg")));
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/typeofreg")).click();
		Thread.sleep(500);
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/alertTitle", "Select Registration Type");
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Additional Registration']")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/alertTitle", "No Privilege");
		TestUtils.assertSearchText("ID", "android:id/message", "You are not allowed to access Additional Registration because you do not have the Additional Registration privilege");
		Thread.sleep(500);
		getDriver().findElement(By.id("android:id/button1")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/message", "Capture session is currently active, Exit will cause loss of currently captured data! do you wish to cancel current capture session?");
		getDriver().findElement(By.id("android:id/button2")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Home']")));
		Thread.sleep(500);

		//Log out
		getDriver().findElement(By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/design_menu_item_text")));
		TestUtils.scrollDown();
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Logout']")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/message")));
		TestUtils.assertSearchText("ID", "android:id/message", "   Log out?");
		getDriver().findElement(By.id("android:id/button3")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/otp_login")));
	}

	@Parameters({ "dataEnv"})
    @Test
    public void navigateToAddReg(String dataEnv) throws Exception {

        WebDriverWait wait = new WebDriverWait(getDriver(), 60);
        
        JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resource/" + dataEnv + "/data.conf.json"));
		JSONObject envs = (JSONObject) config.get("AdditionalRegistration");
		
		String lga = (String) envs.get("lga");

		// Select LGA of Registration
		TestUtils.testTitle("Select LGA of Registration: " + lga);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/lga_of_reg")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/alertTitle", "LGA of Registration*");
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='" + lga + "']")).click();

		// Check that Additional Registration is selectable
		TestUtils.testTitle("Check that additional registration is selectable");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/typeofreg")));
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/typeofreg")).click();
		Thread.sleep(500);
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/alertTitle", "Select Registration Type");
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Additional Registration']")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/next_button")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Additional Registration']")));
		TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Additional Registration']", "Additional Registration");
		Thread.sleep(500);

	}

	@Parameters({ "dataEnv"})
	@Test
	public void msisdnCategory(String dataEnv) throws Exception {

		WebDriverWait wait = new WebDriverWait(getDriver(), 60);

		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resource/" + dataEnv + "/data.conf.json"));
		JSONObject envs = (JSONObject) config.get("AdditionalRegistration");

		String pri_valid_Msisdn = (String) envs.get("pri_valid_Msisdn");
		String pri_valid_simSerial = (String) envs.get("pri_valid_simSerial");
		String valid_Msisdn = (String) envs.get("valid_Msisdn");
		String valid_simSerial = (String) envs.get("valid_simSerial");
		String fixed_Msisdn = (String) envs.get("fixed_Msisdn");

		//Assert MSISDN Category Dropdown
		TestUtils.testTitle("Assert MSISDN Category Dropdown");
		//Asset the categories on primary form
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnCategorySpinner")).click();
		TestUtils.assertSearchText("XPATH", "/hierarchy/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.ListView/android.widget.CheckedTextView[1]", "Mobile");
		TestUtils.assertSearchText("XPATH", "/hierarchy/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.ListView/android.widget.CheckedTextView[2]", "Fixed");
		getDriver().findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.ListView/android.widget.CheckedTextView[1]")).click();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/primary_msisdn_field")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/primary_msisdn_field")).sendKeys(pri_valid_Msisdn);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/primary_serial_field")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/primary_serial_field")).sendKeys(pri_valid_simSerial);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/submit_button")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/summary_ok_button")));
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/summary_ok_button")).click();

		//Confirm that first item on MSISDN dropdown is mobile
		TestUtils.testTitle("Confirm that first item on MSISDN dropdown is mobile");
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/next_button")).click();
		getDriver().findElement(By.id("android:id/text1")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.ListView/android.widget.CheckedTextView[1]") ));
		TestUtils.assertSearchText("XPATH", "/hierarchy/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.ListView/android.widget.CheckedTextView[1]", "Mobile");
		getDriver().findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.ListView/android.widget.CheckedTextView[1]")).click();

		//Confirm MSISDN(Mobile) does not get more than 11 digits for verification
		String moreCharacters=valid_Msisdn+"1234";
		TestUtils.testTitle("Confirm MSISDN(Mobile) does not get more than 11 digits ("+moreCharacters+")");
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).sendKeys(valid_Msisdn);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).sendKeys(moreCharacters);
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/msisdnField", valid_Msisdn);

		//Validating a valid Msisdn on new registration
		TestUtils.testTitle("Validate a valid MSISDN on new registration ("+valid_Msisdn+")");
		getDriver().findElement(By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']")).click();
		getDriver().findElement(By.id("android:id/button1")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/typeofreg")));
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/typeofreg")).click();
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='New Registration']")).click();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/next_button")).click();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).sendKeys(valid_Msisdn);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/simSerialField")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/simSerialField")).sendKeys(valid_simSerial);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/addMsisdnSimSerialButton")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/button1")));
		TestUtils.assertSearchText("ID", "android:id/message", "Msisdn is valid");
		getDriver().findElement(By.id("android:id/button1")).click();

		//Confirm MSISDN(Fixed) does not get more than 9 digits for new registration
		String priCharacters=fixed_Msisdn+"1234";
		TestUtils.testTitle("Confirm MSISDN(Fixed) does not get more than 9 digits ("+priCharacters+")");
		getDriver().findElement(By.id("android:id/text1")).click();
		getDriver().findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.ListView/android.widget.CheckedTextView[2]")).click();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).sendKeys(fixed_Msisdn);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).sendKeys(priCharacters);
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/msisdnField", fixed_Msisdn);

		// Check Database after supplying valid msisdn and Sim serial
		//TestUtils.testTitle("Check Database after supplying valid msisdn: (" + pri_valid_Msisdn + ") and valid Sim serial: (" + pri_valid_simSerial + ")");
		getDriver().findElement(By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/typeofreg")));
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/typeofreg")).click();
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Additional Registration']")).click();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/next_button")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/primary_msisdn_field")));
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/primary_msisdn_field")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/primary_msisdn_field")).sendKeys(pri_valid_Msisdn);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/primary_serial_field")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/primary_serial_field")).sendKeys(pri_valid_simSerial);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/submit_button")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/summary_title")));
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/summary_ok_button")).click();
		Thread.sleep(1000);
		//Method To check from DB goes here


	}
	@Parameters({ "dataEnv"})
	@Test
	public void additionalRegistration(String dataEnv) throws Exception {

		WebDriverWait wait = new WebDriverWait(getDriver(), 60);

		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resource/" + dataEnv + "/data.conf.json"));
		JSONObject envs = (JSONObject) config.get("AdditionalRegistration");
		String invalid_Msisdn = (String) envs.get("invalid_Msisdn");
		String pri_valid_Msisdn = (String) envs.get("pri_valid_Msisdn");
		String invalid_simSerial = (String) envs.get("invalid_simSerial");
		String pri_valid_simSerial = (String) envs.get("pri_valid_simSerial");
		String valid_Msisdn = (String) envs.get("valid_Msisdn");
		String valid_simSerial = (String) envs.get("valid_simSerial");
		String new_valid_simSerial = (String) envs.get("new_valid_simSerial");
		String invalid_number = (String) envs.get("invalid_number");
		String nin = (String) envs.get("nin");
		String ninVerificationMode = (String) envs.get("ninVerificationMode");


		// Confirming wrong combination of invalid serial
		String WrongInvalid_simSerial=valid_simSerial+"G";
		TestUtils.testTitle("Confirm wrong combination of invalid serial: (" + WrongInvalid_simSerial + ") ");
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/primary_msisdn_field")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/primary_msisdn_field")).sendKeys(valid_Msisdn);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/primary_serial_field")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/primary_serial_field")).sendKeys(valid_simSerial);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/submit_button")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/button1")));
		TestUtils.assertSearchText("ID", "android:id/message", "Record not found");
		getDriver().findElement(By.id("android:id/button1")).click();


		//Confirm MSISDN does not get more than 11 digits for Additional registration
		String moreCharacters=pri_valid_Msisdn+"1234";
		TestUtils.testTitle("Confirm MSISDN does not get more than 11 digits ("+moreCharacters+")");
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/primary_msisdn_field")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/primary_msisdn_field")).sendKeys(pri_valid_Msisdn);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/primary_msisdn_field")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/primary_msisdn_field")).sendKeys(moreCharacters);
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/primary_msisdn_field", pri_valid_Msisdn);


		// Proceed after supplying invalid msisdn and sim serial for validation
		TestUtils.testTitle("Proceed after supplying invalid msisdn (" + invalid_Msisdn + ") and valid sim serial (" + pri_valid_simSerial + ")");
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/primary_msisdn_field")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/primary_msisdn_field")).sendKeys(invalid_Msisdn);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/primary_serial_field")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/primary_serial_field")).sendKeys(pri_valid_simSerial);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/submit_button")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/button1")));
		TestUtils.assertSearchText("ID", "android:id/message", "Record not found");
		getDriver().findElement(By.id("android:id/button1")).click();


		// Proceed after supplying valid msisdn and sim serial
		TestUtils.testTitle("Proceed after supplying valid msisdn (" + pri_valid_Msisdn + ") and valid sim serial (" + pri_valid_simSerial + ")");
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/primary_msisdn_field")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/primary_msisdn_field")).sendKeys(pri_valid_Msisdn);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/primary_serial_field")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/primary_serial_field")).sendKeys(pri_valid_simSerial);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/submit_button")).click();
		Thread.sleep(3000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/summary_ok_button")));
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/summary_ok_button")).click();
		Thread.sleep(1500);
		Asserts.assertSubscriberFullNameAddReg();

		// NEXT after supplying invalid msisdn and sim serial
		TestUtils.testTitle("Proceed to next after supplying invalid msisdn (" + invalid_Msisdn + ") and invalid sim serial (" + invalid_simSerial + ")");
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/primary_msisdn_field")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/primary_msisdn_field")).sendKeys(pri_valid_Msisdn);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/primary_serial_field")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/primary_serial_field")).sendKeys(pri_valid_simSerial);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/submit_button")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/summary_title")));
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/summary_title", "Basic Info");
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/summary_ok_button")).click();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/next_button")).click();
		Thread.sleep(2000);

		//NIN Verification
		TestBase.verifyNINTest(nin, ninVerificationMode);


		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).sendKeys(invalid_Msisdn);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/simSerialField")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/simSerialField")).sendKeys(invalid_simSerial);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/addSerialButton")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/message")));
		TestUtils.assertSearchText("ID", "android:id/message", "Sim Serial format is invalid");
		getDriver().findElement(By.id("android:id/button1")).click();


		// Proceed after supplying valid msisdn and sim serial
		TestUtils.testTitle("Proceed after supplying valid msisdn (" + valid_Msisdn + ") and valid sim serial (" + valid_simSerial + ")");
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).sendKeys(valid_Msisdn);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/simSerialField")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/simSerialField")).sendKeys(valid_simSerial);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/addSerialButton")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/button1")));
		TestUtils.assertSearchText("ID", "android:id/message", "Msisdn is valid");
		getDriver().findElement(By.id("android:id/button1")).click();


		// To check user is unable to Add record Successfully after Maximum Validation
		String noValid=getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/serialCounter")).getText();
		System.out.println("Receiving counts...."+noValid);
		if(noValid!="+0"){
			TestUtils.testTitle("To check user is unable to add record successfully after maximum value(" + noValid + ")");
			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/viewSerialButton")).getText();

			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).clear();
			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).sendKeys(valid_Msisdn);
			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/simSerialField")).clear();
			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/simSerialField")).sendKeys(valid_simSerial);
			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/addSerialButton")).click();

			Thread.sleep(2000);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/message")));
			Thread.sleep(2000);
			TestUtils.assertSearchText("ID", "android:id/message", "Subscriber is not allowed to register more than 1 additional SIMS");
			getDriver().findElement(By.id("android:id/button1")).click();

			//Remove the Added MSISDN
			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/viewSerialButton")).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/text1")));
			getDriver().findElement(By.id("android:id/text1")).click();
			getDriver().findElement(By.id("android:id/button1")).click();
		}

		// To check user is able to Add record Successfully below maximum validation
		String noValid2=getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/serialCounter")).getText();
		System.out.println("Receiving counts...."+noValid2);
		TestUtils.testTitle("To check user is able to add record successfully before maximum value(" + noValid2 + ")");
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/viewSerialButton")).getText();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).sendKeys(valid_Msisdn);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/simSerialField")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/simSerialField")).sendKeys(valid_simSerial);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/addSerialButton")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/button1")));
		Thread.sleep(1000);
		TestUtils.assertSearchText("ID", "android:id/message", "Msisdn is valid");
		getDriver().findElement(By.id("android:id/button1")).click();
		TestUtils.testTitle("Complete additional registration");
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/nextButton")).click();
		TestUtils.assertSearchText("ID", "android:id/message", "Captured record was saved successfully");
		getDriver().findElement(By.id("android:id/button1")).click();
		getDriver().findElement(By.id("android:id/button3")).click();
		System.out.println("Add reg is completed");




	}

	@Parameters({ "dataEnv"})
	@Test
	public void additionalRegTypePrivilege(String dataEnv) throws Exception {

		WebDriverWait wait = new WebDriverWait(getDriver(), 60);

		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resource/" + dataEnv + "/data.conf.json"));
		JSONObject envs = (JSONObject) config.get("AdditionalRegistration");

		String company_Msisdn = (String) envs.get("company_Msisdn");
		String company_serial = (String) envs.get("company_serial");
		String individual_Msisdn = (String) envs.get("individual_Msisdn");
		String individual_serial = (String) envs.get("individual_serial");
		String incomplete_Msisdn = (String) envs.get("incomplete_Msisdn");
		String incomplete_serial = (String) envs.get("incomplete_serial");

		//Navigate to Additional Registration
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/button_start_capture")).click();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/typeofreg")).click();
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Additional Registration']")).click();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/next_button")).click();

		//To confirm response for valid MSISDN () and SIM Serial () with incomplete biometrics
		TestUtils.testTitle("To confirm response for valid MSISDN (" + incomplete_Msisdn + ") and SIM Serial (" + incomplete_serial + ") with incomplete biometrics");
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/primary_msisdn_field")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/primary_msisdn_field")).sendKeys(incomplete_Msisdn);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/primary_serial_field")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/primary_serial_field")).sendKeys(incomplete_serial);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/submit_button")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/button1")));
		TestUtils.assertSearchText("ID", "android:id/message", "No Biometric Data found for this phone Number, Please Select another type of registration");
		getDriver().findElement(By.id("android:id/button1")).click();

		//Navigate to Additional Registration
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/typeofreg")).click();
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Additional Registration']")).click();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/next_button")).click();

		// To confirm that user with individual registration privilege can perform Individual Additional Registration
		TestUtils.testTitle("To confirm that user with individual registration privilege can perform Individual Additional Registration ");
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/primary_msisdn_field")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/primary_msisdn_field")).sendKeys(individual_Msisdn);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/primary_serial_field")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/primary_serial_field")).sendKeys(individual_serial);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/submit_button")).click();
		Thread.sleep(3000);
		TestUtils.scrollUntilElementIsVisible("XPATH", "//android.widget.TextView[@text='Registration Type']");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/summary_ok_button")));
		TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='INDIVIDUAL']", "INDIVIDUAL");
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/summary_ok_button")).click();

		// Proceed after supplying valid msisdn and sim serial with company reg type
		TestUtils.testTitle("To confirm that user with company registration privilege can perform Company Additional Registration");
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/primary_msisdn_field")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/primary_msisdn_field")).sendKeys(company_Msisdn);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/primary_serial_field")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/primary_serial_field")).sendKeys(company_serial);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/submit_button")).click();
		Thread.sleep(5000);
		TestUtils.scrollUntilElementIsVisible("XPATH", "//android.widget.TextView[@text='Registration Type']");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/summary_ok_button")));
		TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='INDIVIDUAL']", "COMPANY");
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/summary_ok_button")).click();

	}

	@Parameters({ "dataEnv"})
	@Test
	public void ndcRule(String dataEnv) throws Exception {

		WebDriverWait wait = new WebDriverWait(getDriver(), 60);

		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resource/" + dataEnv + "/data.conf.json"));
		JSONObject envs = (JSONObject) config.get("AdditionalRegistration");

		String invalid_Msisdn = (String) envs.get("invalid_Msisdn");
		String pri_valid_Msisdn = (String) envs.get("pri_valid_Msisdn");
		String invalid_simSerial = (String) envs.get("invalid_simSerial");
		String pri_valid_simSerial = (String) envs.get("pri_valid_simSerial");
		String valid_Msisdn = (String) envs.get("valid_Msisdn");
		String valid_simSerial = (String) envs.get("valid_simSerial");
		String invalid_number = (String) envs.get("invalid_number");

		//To confirm that msisdn fails validation if the prefix is not allowed by NDC
		TestUtils.testTitle("To confirm that msisdn fails validation if the prefix is not allowed by NDC");
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/primary_msisdn_field")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/primary_msisdn_field")).sendKeys(invalid_number);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/primary_serial_field")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/primary_serial_field")).sendKeys(valid_simSerial);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/submit_button")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/button1")));
		TestUtils.assertSearchText("ID", "android:id/message", "The MSISDN has an unrecognized National Destination Code. Please ensure the MSISDN starts with any of the following NDCs: 0701,0708,0802,0808,0812,0901,0902,0904,0907,0809,0817,0818,0908,0909,0705,0805,0807,0811,0815,0905,0915,0703,0706,0803,0806,0810,0813,0814,0816,0903,0906 and must be 11 digits");
		getDriver().findElement(By.id("android:id/button1")).click();

		//To confirm that msisdn should proceed  to backend validation if the prefix is  allowed by NDC
		TestUtils.testTitle("To confirm that msisdn should proceed  to backend validation if the prefix is  allowed by NDC");
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/primary_msisdn_field")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/primary_msisdn_field")).sendKeys(pri_valid_Msisdn);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/primary_serial_field")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/primary_serial_field")).sendKeys(pri_valid_simSerial);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/submit_button")).click();
		Thread.sleep(3000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id +
				":id/summary_title")));
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/summary_title", "Basic Info");

		//Go back
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/summary_ok_button")));
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/summary_ok_button")).click();

		// To check user gets error after supplying invalid number and sim serial then valid number with sim serial
		TestUtils.testTitle("To confirm that the check is applied on Additional Registrations  msisdn input fields");
		TestUtils.testTitle("To check user gets error after supplying invalid number (" + invalid_number + ") and valid sim serial (" + valid_simSerial + ") ");
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/primary_msisdn_field")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/primary_msisdn_field")).sendKeys(invalid_number);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/primary_serial_field")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/primary_serial_field")).sendKeys(valid_simSerial);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/submit_button")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/button1")));
		TestUtils.assertSearchText("ID", "android:id/message", "The MSISDN has an unrecognized National Destination Code. Please ensure the MSISDN starts with any of the following NDCs: 0701,0708,0802,0808,0812,0901,0902,0904,0907,0809,0817,0818,0908,0909,0705,0805,0807,0811,0815,0905,0915,0703,0706,0803,0806,0810,0813,0814,0816,0903,0906 and must be 11 digits");
		getDriver().findElement(By.id("android:id/button1")).click();
		TestUtils.testTitle("To confirm number is validated after supplying valid number (" + pri_valid_Msisdn + ") and invalid sim serial (" + pri_valid_simSerial + ")");
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/primary_msisdn_field")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/primary_msisdn_field")).sendKeys(pri_valid_Msisdn);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/primary_serial_field")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/primary_serial_field")).sendKeys(pri_valid_simSerial);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/submit_button")).click();
		Thread.sleep(2000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/summary_title")));
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/summary_title", "Basic Info");

		//Repeat valid number and confirm that subsequent msisdns are checked for NDC rule
		TestUtils.testTitle("Repeat valid number and confirm that subsequent MSISDNs are checked for NDC rule");
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/summary_ok_button")).click();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/primary_msisdn_field")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/primary_msisdn_field")).sendKeys(pri_valid_Msisdn);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/primary_serial_field")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/primary_serial_field")).sendKeys(pri_valid_simSerial);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/submit_button")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id +
				":id/summary_title")));
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/summary_title", "Basic Info");


	}

}
