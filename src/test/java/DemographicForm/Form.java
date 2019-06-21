package DemographicForm;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import utils.TestBase;
import utils.TestUtils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Form extends TestBase {
	
	@Parameters({ "dataEnv"})
	@Test
	public static void NigerianCompanyForm(String dataEnv) throws InterruptedException, IOException, ParseException {
		WebDriverWait wait = new WebDriverWait(getDriver(), 30);
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resource/" + dataEnv + "/data.conf.json"));
		JSONObject envs = (JSONObject) config.get("UserDetails");
		JSONObject envs2 = (JSONObject) config.get("CompanyDetails");
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

		String company_description = (String) envs2.get("company_description");
		String company_regno = (String) envs2.get("company_regno");
		String house_or_flat_no = (String) envs2.get("house_or_flat_no");
		String company_street = (String) envs2.get("company_street");
		String company_city = (String) envs2.get("company_city");
		String company_state_address = (String) envs2.get("company_state_address");
		String company_lga_address = (String) envs2.get("company_lga_address");
		String company_postalcode = (String) envs2.get("company_postalcode");
		String email = (String) envs2.get("email");
		String alt_phone_number = (String) envs2.get("alt_phone_number");
		String postalcode = (String) envs2.get("postalcode");
		String occupation = (String) envs2.get("occupation");
		String states_residence = (String) envs2.get("states_residence");
		String lga_residence = (String) envs2.get("lga_residence");
		String area = (String) envs2.get("area");
		String lga_of_reg = (String) envs2.get("lga_of_reg");

		TestUtils.scrollUp();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Personal Details']")));
		TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Personal Details']", "Personal Details");
		Thread.sleep(2000);
		// Proceed with registration after supplying all mandatory fields
		String completeField = "Proceed with registration after supplying all mandatory fields";
		Markup e = MarkupHelper.createLabel(completeField, ExtentColor.BLUE);
		testInfo.get().info(e);
		// personal details
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/reg_type")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Company']")).click();
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
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/female_radio_button")).click();
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
		if (TestUtils.isElementPresent("ID", "com.sf.biocapture.activity:id/add_social_media_button")) {
			Thread.sleep(1000);
			if (TestUtils.isElementPresent("ID", "com.sf.biocapture.activity:id/delete_button")) {
				getDriver().findElement(By.id("com.sf.biocapture.activity:id/delete_button")).click();
			}
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
		}

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
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='[Select Nationality]*']")).click();
		Thread.sleep(1000);
		TestUtils.assertSearchText("ID", "android:id/text1", "[Select Nationality]*");
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='" + nationality + "']")).click();
		Thread.sleep(500);
		
		// State 
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='[Select State]*']")).click();
		Thread.sleep(500);
		TestUtils.assertSearchText("ID", "android:id/text1", "[Select State]*");
		getDriver().findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(new UiSelector().text(\"BENUE\"));");
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='" + state + "']")).click();
		Thread.sleep(500);
		
		// LGA
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/lga")).click();
		Thread.sleep(500);
		TestUtils.assertSearchText("ID", "android:id/text1", "[Select LGA]*");
		getDriver().findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(new UiSelector().text(\"Ado\"));");
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='" + LGA + "']")).click();
		Thread.sleep(500);
		
		// Next button
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/btn_continue_reg")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/page_title")));
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity:id/page_title", "Registration Details");
		Thread.sleep(500);
		
		// Registration Details
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/typeofid")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='International Passport']")).click();
		Thread.sleep(500);
		
		// capture
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/capture_id_button")).click();
		Thread.sleep(1000);
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
		
		// View Captured ID
		String capturedID = "View captured ID";
		Markup c = MarkupHelper.createLabel(capturedID, ExtentColor.BLUE);
		testInfo.get().info(c);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/view_captured_id_button")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/title")));
		TestUtils.assertSearchText("ID", "android:id/title", "View KYC Form");
		Thread.sleep(1000);
		if (getDriver().findElement(By.id("com.sf.biocapture.activity:id/image")).isDisplayed()) {
			testInfo.get().info("Captured ID is displayed");
		} else {
			testInfo.get().info("Captured ID is not displayed");
			testInfo.get().addScreenCaptureFromPath(TestUtils.addScreenshot());
		}
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/done_button")).click();
		Thread.sleep(500);

		// Company Details
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/edit_company_details")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/company_details_title")));
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity:id/company_details_title", "Company Details");
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/company_name_descrptn")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/company_name_descrptn")).sendKeys(company_description);
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/company_regno")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/company_regno")).sendKeys(company_regno);
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/house_or_flat_no")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/house_or_flat_no")).sendKeys(surname);
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
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='" + company_state_address + "']")).click();
		Thread.sleep(500);
		
		// Company Address LGA
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/company_lga_address")).click();
		Thread.sleep(500);
		TestUtils.assertSearchText("ID", "android:id/text1", "[Select LGA]*");
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='" + company_lga_address + "']")).click();
		Thread.sleep(500);
		
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/company_postalcode")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/company_postalcode")).sendKeys(company_postalcode);
		Thread.sleep(500);
		
		// Certificate of Incorporation
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/document_type_spinner")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Certificate of Incorporation *']")).click();
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
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Contact Person Form *']")).click();
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
		Thread.sleep(500);

		// Email
		TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity:id/email");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/email")));
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/email")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/email")).sendKeys(email);
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
		
		//  State  of Residence
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/states_residence")).click();
		Thread.sleep(500);
		TestUtils.assertSearchText("ID", "android:id/text1", "[Select State]*");
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='" + states_residence + "']")).click();
		Thread.sleep(500);
		
		// LGA Residence
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/lga_residence")).click();
		Thread.sleep(500);
		TestUtils.assertSearchText("ID", "android:id/text1", "[Select LGA]*");
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='" + lga_residence + "']")).click();
		Thread.sleep(500);
		
		// Area of residence
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/area")).click();
		Thread.sleep(500);
		TestUtils.assertSearchText("ID", "android:id/text1", "[Select Area]*");
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='" + area + "']")).click();
		Thread.sleep(500);
		
		// LGA Residence
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/lga_of_reg")).click();
		Thread.sleep(500);
		TestUtils.assertSearchText("ID", "android:id/text1", "[Select LGA]*");
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
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/ok"))).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/ok")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/capture_button")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/save_continue")).click();
		Thread.sleep(1000);

		override();

	}

	public static void IndividualForeignerForm() throws InterruptedException {

		WebDriverWait wait = new WebDriverWait(getDriver(), 30);
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Personal Details']")));
		TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Personal Details']", "Personal Details");
		Thread.sleep(2000);
		// personal details
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/reg_type")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Individual']")).click();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/surnname")));
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/surnname")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/surnname")).sendKeys("Demetrice");
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/firstname")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/firstname")).sendKeys("Joubert");
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/middlename")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/middlename")).sendKeys("Barbara");
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/moms_maidenname")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/moms_maidenname")).sendKeys("Minor");
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/male_radio_button")).click();
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

		TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity:id/add_social_media_button");

		// Social Media
		if (TestUtils.isElementPresent("ID", "com.sf.biocapture.activity:id/add_social_media_button")) {
			Thread.sleep(1000);
			boolean elementPresent = TestUtils.isElementPresent("ID", "com.sf.biocapture.activity:id/delete_button");
			while (elementPresent) {
				getDriver().findElement(By.id("com.sf.biocapture.activity:id/delete_button")).click();
				elementPresent = TestUtils.isElementPresent("ID", "com.sf.biocapture.activity:id/delete_button");
			}
			getDriver().findElement(By.id("com.sf.biocapture.activity:id/add_social_media_button")).click();
			Thread.sleep(500);
			getDriver().findElement(By.id("com.sf.biocapture.activity:id/type_spinner")).click();
			Thread.sleep(500);
			getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='INSTAGRAM']")).click();
			Thread.sleep(500);
			getDriver().findElement(By.id("com.sf.biocapture.activity:id/username_edit_text")).sendKeys("@testuser");
		}

		Thread.sleep(500);
		TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity:id/house_or_flat_no");
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/house_or_flat_no")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/house_or_flat_no")).sendKeys("455");
		TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity:id/street");
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/street")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/street")).sendKeys("Neville Street");
		TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity:id/city");
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/city")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/city")).sendKeys("Lagos");
		TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity:id/country");
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/country")).click();
		Thread.sleep(500);
		getDriver().findElementByAndroidUIAutomator(
				"new UiScrollable(new UiSelector()).scrollIntoView(new UiSelector().text(\"ANGOLA\"));");
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='ANGOLA']")).click();
		Thread.sleep(1000);

		try {
			getDriver().findElement(By.id("com.sf.biocapture.activity:id/btn_continue_reg")).click();
		} catch (NoSuchElementException exc) {

			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/passport_details_title")));
			TestUtils.assertSearchText("ID", "com.sf.biocapture.activity:id/passport_details_title",
					"Passport/ID Details");
			Thread.sleep(1000);
			getDriver().findElement(By.id("com.sf.biocapture.activity:id/passport_issuing_country")).click();
			Thread.sleep(1000);
			getDriver().findElement(By.xpath("//android.widget.TextView[@text='ANGOLA']")).click();
			Thread.sleep(500);
			getDriver().findElement(By.id("com.sf.biocapture.activity:id/passport_number")).clear();
			getDriver().findElement(By.id("com.sf.biocapture.activity:id/passport_number")).sendKeys("686163746BC");
			Thread.sleep(500);
			getDriver().findElement(By.id("com.sf.biocapture.activity:id/passport_expiry_date")).click();
			Thread.sleep(500);
			try {
				try {
					getDriver().findElement(By.xpath("//android.widget.Button[@text='2019']")).click();
				} catch (NoSuchElementException e) {
					getDriver().findElement(By.xpath("//android.widget.EditText[@text='2019']")).click();
				}
			} catch (NoSuchElementException ex) {
				getDriver().findElement(By.id("android:id/date_picker_header_year")).click();
				Thread.sleep(500);
				getDriver().findElement(By.xpath("//android.widget.TextView[@text='2020']")).click();
			}
			getDriver().findElement(By.id("android:id/button1")).click();
			Thread.sleep(1000);
			getDriver().findElement(By.id("com.sf.biocapture.activity:id/passport_nigerian_resident")).click();
			Thread.sleep(2000);

			// capture passport image
			TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity:id/capture_passport_image");
			getDriver().findElement(By.id("com.sf.biocapture.activity:id/capture_passport_image")).click();
			Thread.sleep(1000);
			getDriver().findElement(By.id("com.sf.biocapture.activity:id/button_camera_capture")).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/ok")))
					.click();
			Thread.sleep(1000);
			getDriver().findElement(By.id("com.sf.biocapture.activity:id/passport_ok")).click();
			Thread.sleep(1000);
			getDriver().findElement(By.id("com.sf.biocapture.activity:id/btn_continue_reg")).click();
		}

		// Registration Details
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/typeofid")))
				.click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='National ID']")).click();
		Thread.sleep(500);

		// Edit passport details
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/edit_passport_details")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/passport_details_title")));
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity:id/passport_details_title", "Passport/ID Details");
		Thread.sleep(1000);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/passport_issuing_country")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='ANGOLA']")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/passport_number")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/passport_number")).sendKeys("686163746BC");
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/passport_expiry_date")).click();
		Thread.sleep(500);
		try {
			try {
				getDriver().findElement(By.xpath("//android.widget.Button[@text='2019']")).click();
			} catch (NoSuchElementException e) {
				getDriver().findElement(By.xpath("//android.widget.EditText[@text='2019']")).click();
			}
		} catch (NoSuchElementException ex) {
			Thread.sleep(2000);
			getDriver().findElement(By.id("android:id/date_picker_header_year")).click();
			Thread.sleep(500);
			try {
				getDriver().findElement(By.xpath("//android.widget.TextView[@text='2020']")).click();
			} catch (Exception e) {
				getDriver().findElement(By.xpath("//android.widget.EditText[@text='2020']")).click();
			}
		}
		getDriver().findElement(By.id("android:id/button1")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/passport_nigerian_resident")).click();
		Thread.sleep(2000);

		// capture passport image
		TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity:id/capture_passport_image");
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/capture_passport_image")).click();
		Thread.sleep(2000);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/button_camera_capture")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/ok"))).click();
		Thread.sleep(1000);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/passport_ok")).click();
		Thread.sleep(1000);

		// Other details starting from email
		TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity:id/email");
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/email")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/email")).sendKeys("testIndividual@yopmail.com");
		Thread.sleep(500);
		TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity:id/alt_phone_number");
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/alt_phone_number")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/alt_phone_number")).sendKeys("08000000000");
		Thread.sleep(500);
		TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity:id/postalcode");
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/postalcode")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/postalcode")).sendKeys("126583");
		Thread.sleep(500);
		TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity:id/occupation");
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/occupation")).click();
		Thread.sleep(500);
		getDriver().findElementByAndroidUIAutomator(
				"new UiScrollable(new UiSelector()).scrollIntoView(new UiSelector().text(\"Pilot\"));");
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Pilot']")).click();
		Thread.sleep(500);
		TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity:id/states_residence");
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/states_residence")).click();
		Thread.sleep(500);
		getDriver().findElementByAndroidUIAutomator(
				"new UiScrollable(new UiSelector()).scrollIntoView(new UiSelector().text(\"ABIA\"));");
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='ABIA']")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/lga_residence")).click();
		Thread.sleep(500);
		getDriver().findElementByAndroidUIAutomator(
				"new UiScrollable(new UiSelector()).scrollIntoView(new UiSelector().text(\"Aba North\"));");
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Aba North']")).click();
		Thread.sleep(500);
		TestUtils.scrollDown();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/area")).click();
		Thread.sleep(500);
		getDriver().findElementByAndroidUIAutomator(
				"new UiScrollable(new UiSelector()).scrollIntoView(new UiSelector().text(\"ARIARIA\"));");
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='ARIARIA']")).click();
		Thread.sleep(500);
		TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity:id/lga_of_reg");
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/lga_of_reg")).click();
		Thread.sleep(500);
		getDriver().findElementByAndroidUIAutomator(
				"new UiScrollable(new UiSelector()).scrollIntoView(new UiSelector().text(\"Agege\"));");
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Agege']")).click();
		Thread.sleep(500);

		// capture kyc form
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/capture_kyc_form")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/capture_button")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/button_camera_capture")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/ok"))).click();
		Thread.sleep(1000);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/capture_button")).click();
		Thread.sleep(1000);

		// save
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/save_continue")).click();
		Thread.sleep(1000);
//        getDriver().findElement(By.id("android:id/button2")).click();
		override();

	}

	public static void override() throws InterruptedException {

		WebDriverWait wait = new WebDriverWait(getDriver(), 30);

		// override
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.id("com.sf.biocapture.activity:id/override_poor_portrait_capture_button"))).click();
		Thread.sleep(2000);
		getDriver().findElement(By.id("android:id/button1")).click();
		Thread.sleep(2000);

		// capture override
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/capture_button")).click();
		Thread.sleep(2000);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/button_camera_capture")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/ok"))).click();
		Thread.sleep(1000);
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='Scarred']")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/capture_button")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/buttonImageInfo")).click();
		Thread.sleep(1000);

		// finger print override
		// left hand

		String override_fingerprint = "Try to override fingerprints";
		Markup o = MarkupHelper.createLabel(override_fingerprint, ExtentColor.BLUE);
		testInfo.get().info(o);
		TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Choose Your Preferred Capture Mode']",
				"Choose Your Preferred Capture Mode");
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/fp_left_index")).click();
		Thread.sleep(1000);

		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity:id/override_finger_print_capture_button",
				"Override Finger Print");
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/override_finger_print_capture_button")).click();
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/bt_basic_info_title")));
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/capture_button")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='MISSING RIGHT HAND']")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/button_camera_capture")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/ok"))).click();
		Thread.sleep(1000);
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='No prints']")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/capture_button")).click();
		Thread.sleep(1000);

		// finger print override
		// left hand
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity:id/override_finger_print_capture_button",
				"Override Finger Print");
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/override_finger_print_capture_button")).click();
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/bt_basic_info_title")));
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/capture_button")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='MISSING LEFT HAND']")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/button_camera_capture")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/ok"))).click();
		Thread.sleep(1000);
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='No prints']")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/capture_button")).click();

		// save enrollment
		String saveEnrollment = "Try to save enrollement";
		Markup s = MarkupHelper.createLabel(saveEnrollment, ExtentColor.BLUE);
		testInfo.get().info(s);
		Thread.sleep(1000);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/fp_save_enrolment")).click();

		// popup
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/content")));
		TestUtils.assertSearchText("ID", "android:id/message", "Captured record was saved successfully");

		// ok button
		getDriver().findElement(By.id("android:id/button1")).click();
		Thread.sleep(1000);

	}

}
