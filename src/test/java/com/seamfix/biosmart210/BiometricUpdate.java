package com.seamfix.biosmart210;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import DemographicForm.Form;
import utils.Asserts;
import utils.TestBase;

import utils.TestUtils;
import java.io.FileReader;


public class BiometricUpdate extends TestBase {
	
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

	@Parameters({ "dataEnv" })
	@Test
    public void captureBiometricUpdate(String dataEnv) throws Exception {

        WebDriverWait wait = new WebDriverWait(getDriver(), 30);
        JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resource/" + dataEnv + "/data.conf.json"));
		JSONObject envs = (JSONObject) config.get("BiometricUpdate");
		
		String invalid_msisdn = (String) envs.get("invalid_msisdn");
		String valid_msisdn = (String) envs.get("valid_msisdn");
		String surname = (String) envs.get("surname");
		String firstname = (String) envs.get("firstname");
		String middlename = (String) envs.get("middlename");
		String maiden_name = (String) envs.get("maiden_name");
		String social_media_type = (String) envs.get("social_media_type");
		String social_media_username = (String) envs.get("social_media_username");
		String street = (String) envs.get("street");
		String city = (String) envs.get("city");
		String nationality = (String) envs.get("nationality");
		String state = (String) envs.get("state");
		String LGA = (String) envs.get("LGA");
		String email = (String) envs.get("email");
		String alt_phone_number = (String) envs.get("alt_phone_number");
		String occupation = (String) envs.get("occupation");
		String postalcode = (String) envs.get("postalcode");
		String areaOfResidence = (String) envs.get("areaOfResidence");
		String passport_ID_number  = (String) envs.get("passport_ID_number");
		String house_or_flat_no = (String) envs.get("house_or_flat_no");
		String lga_of_reg = (String) envs.get("lga_of_reg");
		
		// Select Bio-metric Update
		String biometric = "Select Biometric Update";
		Markup d = MarkupHelper.createLabel(biometric, ExtentColor.BLUE);
		testInfo.get().info(d);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='[Select Registration Type]']")));
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='[Select Registration Type]']")).click();
		Thread.sleep(500);
		TestUtils.assertSearchText("ID", "android:id/alertTitle", "Select Registration Type");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Biometric Update']")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/next_button")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Biometric Update']")));
		TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Biometric Update']", "Biometric Update");
		Thread.sleep(500);
		
		// Enter agent MSISDN without biometric update privilege
		String invalidMsisdn = "Enter agent MSISDN without biometric update privilege: " + invalid_msisdn;
		Markup i = MarkupHelper.createLabel(invalidMsisdn, ExtentColor.BLUE);
		testInfo.get().info(i);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/primary_msisdn_field")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/primary_msisdn_field")).sendKeys(invalid_msisdn);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/submit_button")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/message", "Could not retrieve bio data for the specified msisdn.");
		getDriver().findElement(By.id("android:id/button1")).click();
		Thread.sleep(500);
        
		// Enter valid MSISDN for biometric update 
		String validMsisdn = "Enter valid MSISDN for biometric update : " + valid_msisdn;
		Markup j = MarkupHelper.createLabel(validMsisdn, ExtentColor.BLUE);
		testInfo.get().info(j);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/primary_msisdn_field")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/primary_msisdn_field")).sendKeys(valid_msisdn);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/submit_button")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Personal Details']")));
	    Thread.sleep(1000);
	    Asserts.assertIndividualForm210();
        Thread.sleep(1000);
        getDriver().findElement(By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']")).click();
        Thread.sleep(1000);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Personal Details']")));
	    Thread.sleep(1000);
	    TestUtils.scrollDown();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/btn_continue_reg")));
        Thread.sleep(500);
        if (TestUtils.isElementPresent("ID", "com.sf.biocapture.activity:id/add_social_media_button")) {
			Thread.sleep(1000);
			if (TestUtils.isElementPresent("ID", "com.sf.biocapture.activity:id/delete_button")) {
				getDriver().findElement(By.id("com.sf.biocapture.activity:id/delete_button")).click();
			}
			getDriver().findElement(By.id("com.sf.biocapture.activity:id/delete_button")).click();
			Thread.sleep(500);
			getDriver().findElement(By.id("com.sf.biocapture.activity:id/delete_button")).click();
			Thread.sleep(500);
			getDriver().findElement(By.id("com.sf.biocapture.activity:id/delete_button")).click();
			Thread.sleep(500);
			getDriver().findElement(By.id("com.sf.biocapture.activity:id/delete_button")).click();
			Thread.sleep(500);
		}
        
    	String fillingForm = "Filling Demographics form after asserting existing details";
		Markup c = MarkupHelper.createLabel(fillingForm, ExtentColor.PURPLE);
		testInfo.get().info(c);
		TestUtils.scrollUp();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Personal Details']")));
		TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Personal Details']", "Personal Details");
		Thread.sleep(2000);
		
		// personal details
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/reg_type")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Individual']")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/surnname")));
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/surnname")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/surnname")).sendKeys(surname);
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/firstname")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/firstname")).sendKeys(firstname);
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/middlename")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/middlename")).sendKeys(middlename);
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/moms_maidenname")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/moms_maidenname")).sendKeys(maiden_name);
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/male_radio_button")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/select_date_button")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/key_left")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/key_left")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/key_left")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/key_left")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//android.widget.Button[@text='9']")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//android.widget.Button[@text='9']")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/key_middle")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/done_button")).click();
		Thread.sleep(500);

		TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity:id/btn_continue_reg");
		Thread.sleep(1000);
		
		// Social Media
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/add_social_media_button")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/delete_button")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/add_social_media_button")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/type_spinner")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='" + social_media_type + "']")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/username_edit_text")).sendKeys(social_media_username);
		Thread.sleep(500);
		TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity:id/house_or_flat_no");
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/house_or_flat_no")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/house_or_flat_no")).sendKeys(house_or_flat_no);
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/street")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/street")).sendKeys(street);
		TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity:id/city");
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/city")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/city")).sendKeys(city);
		Thread.sleep(500);

		// Nationality
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/country")).click();
		Thread.sleep(500);
		getDriver().findElementByAndroidUIAutomator(
				"new UiScrollable(new UiSelector()).scrollIntoView(new UiSelector().text(\"INDIA\"));");
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='" + nationality + "']")).click();
		Thread.sleep(1000);
		
		// Next button
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/btn_continue_reg")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/page_title")));
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity:id/page_title", "Registration Details");
		Thread.sleep(500);
		
		// Registration Details
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/typeofid"))).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='National ID']")).click();
		Thread.sleep(500);
		
		// Filling passport details
		String passport = "Fill passport details of nationality: " + nationality + " and passport number: "	+ passport_ID_number;
		Markup g = MarkupHelper.createLabel(passport, ExtentColor.BLUE);
		testInfo.get().info(g);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/edit_passport_details")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/passport_details_title")));
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity:id/passport_details_title", "Passport/ID Details");
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/passport_issuing_country")).click();
		Thread.sleep(1000);
		getDriver().findElementByAndroidUIAutomator(
				"new UiScrollable(new UiSelector()).scrollIntoView(new UiSelector().text(\"INDIA\"));");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='" + nationality + "']")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/passport_number")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/passport_number")).sendKeys(passport_ID_number);
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/passport_expiry_date")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//android.widget.Button[@text='2016']")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//android.widget.Button[@text='2017']")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//android.widget.Button[@text='2018']")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//android.widget.Button[@text='2019']")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//android.widget.Button[@text='2020']")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("android:id/button1")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/passport_nigerian_resident")).click();
		Thread.sleep(500);

		// capture passport image
		TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity:id/capture_passport_image");
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/capture_passport_image")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/button_camera_capture")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/ok")));
		Thread.sleep(1000);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/ok")).click();
		Thread.sleep(500);
		wait.until(	ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/passport_ok")));
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/passport_ok")).click();
		Thread.sleep(500);
		
		// Email
		TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity:id/email");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/email")));
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/email")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/email")).sendKeys(email);
		Thread.sleep(500);
		TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity:id/postalcode");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/alt_phone_number")));
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/alt_phone_number")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/alt_phone_number")).sendKeys(alt_phone_number);
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/postalcode")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/postalcode")).sendKeys(postalcode);
		Thread.sleep(500);
		TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity:id/capture_kyc_form");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/occupation")));
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/occupation")).click();
		Thread.sleep(500);
		getDriver().findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(new UiSelector().text(\"Aeronautical Engineer\"));");
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='" + occupation + "']")).click();
		Thread.sleep(500);

		// State of Residence
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/states_residence")).click();
		Thread.sleep(500);
		getDriver().findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(new UiSelector().text(\"BENUE\"));");
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='" + state + "']")).click();
		Thread.sleep(500);

		// LGA of Residence
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/lga_residence")).click();
		Thread.sleep(500);
		getDriver().findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(new UiSelector().text(\"Ado\"));");
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='" + LGA + "']")).click();
		Thread.sleep(500);

		// Area of residence
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/area")).click();
		Thread.sleep(500);
		getDriver().findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(new UiSelector().text(\"AGILA\"));");
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='" + areaOfResidence + "']")).click();
		Thread.sleep(500);

		// LGA of Registration
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/lga_of_reg")).click();
		Thread.sleep(500);
		getDriver().findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(new UiSelector().text(\"Agege\"));");
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='" + lga_of_reg + "']")).click();
		Thread.sleep(500);

		// capture kyc form
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/capture_kyc_form")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/bt_basic_info_title")));
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/capture_button")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/button_camera_capture")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/ok")));
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/ok")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/capture_button")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/save_continue")).click();
		Thread.sleep(1000);

		Form.override();

    }
}
