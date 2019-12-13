package demographics;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Parameters;

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
	public static void NigerianCompanyForm(String dataEnv) throws InterruptedException, IOException, ParseException {
		WebDriverWait wait = new WebDriverWait(getDriver(), 60);
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resource/" + dataEnv + "/data.conf.json"));
		JSONObject envs = (JSONObject) config.get("UserDetails");
		JSONObject envs2 = (JSONObject) config.get("CompanyDetails");
		String surname = (String) envs.get("surname");
		String firstname = (String) envs.get("firstname");
		String middlename = (String) envs.get("middlename");
		String maiden_name = (String) envs.get("maiden_name");
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

		// Proceed with registration after supplying all mandatory fields
		String completeField = "Proceed with registration after supplying all mandatory fields";
		Markup e = MarkupHelper.createLabel(completeField, ExtentColor.BLUE);
		testInfo.get().info(e);
		
		TestUtils.scrollUp();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Personal Details']")));
		TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Personal Details']", "Personal Details");
		Thread.sleep(2000);
		
		// Company details
		wait.until(ExpectedConditions.visibilityOfElementLocated((By.id("com.sf.biocapture.activity:id/typeOfRegSpinner"))));
		Thread.sleep(1000);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/typeOfRegSpinner")).click();
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
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/surNameTXT")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/surNameTXT")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/surNameTXT")).sendKeys(surname);
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/firstNameTXT")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/firstNameTXT")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/firstNameTXT")).sendKeys(firstname);
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/middleNameTXT")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/middleNameTXT")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/middleNameTXT")).sendKeys(middlename);
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/momsMaidenNameTXT")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/momsMaidenNameTXT")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/momsMaidenNameTXT")).sendKeys(maiden_name);
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/femaleRadioButton")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/selectDateButton")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("android:id/button1")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/alternatePhone")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/alternatePhone")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/alternatePhone")).sendKeys(alt_phone_number);
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/alternateEmail")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/alternateEmail")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/alternateEmail")).sendKeys(email);
		Thread.sleep(500);
		TestUtils.scrollDown();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/spinnerOccupation")));
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/spinnerOccupation")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='[Select an Occupation]']")));
		Thread.sleep(500);
		TestUtils.assertSearchText("ID", "android:id/text1", "[Select an Occupation]");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='" + occupation + "']")).click();
		Thread.sleep(500);
		
		// Social Media
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/socialMediaUsername")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/socialMediaUsername")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/socialMediaUsername")).sendKeys(social_media_username);
		Thread.sleep(500);
		
		// Next button
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/btnContinueReg")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/countrySpinner")));
		Thread.sleep(500);
		
		// Nationality
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='" + nationality + "']")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='" + nationality + "']")).click();
		Thread.sleep(500);
		
		//State of Origin
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='[Select State]*']")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='" + state + "']")).click();
		Thread.sleep(500);
		
		// LGA of Origin
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='[Select LGA]*']")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='" + LGA + "']")).click();
		Thread.sleep(500);
		
		//  State Residence
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='[Select State]*']")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='" + states_residence + "']")).click();
		Thread.sleep(500);
		
		// LGA Residence
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='[Select LGA]*']")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='" + lga_residence + "']")).click();
		Thread.sleep(500);
		
		// Area of residence
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='[Select Area]*']")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='" + area + "']")).click();
		Thread.sleep(500);
		
		// House/ Flat Number
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/houseNumberEditText")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/houseNumberEditText")).sendKeys(house_or_flat_no);
		Thread.sleep(500);
		
		// Street
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/streetEditText")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/streetEditText")).sendKeys(street);
		Thread.sleep(500);
		
		// City
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/cityEditText")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/cityEditText")).sendKeys(city);
		Thread.sleep(500);
		
		// Postal Code
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/postalCodeTXT")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/postalCodeTXT")).sendKeys(postalcode);
		Thread.sleep(500);
		
		// Next button
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/btnNext")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Capture Data']")));
		TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Capture Data']", "Capture Data");
		TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Identification Type']", "Identification Type");
		Thread.sleep(500);
		
		// Capture Data
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='[Select Identification Type]']")).click();
		Thread.sleep(500);
		TestUtils.assertSearchText("XPATH", "//android.widget.CheckedTextView[@text='[Select Identification Type]']", "[Select Identification Type]");
		Thread.sleep(500);
		
		// Capture ID CARD
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='International Passport']")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/capture_id_button")).click();
		Thread.sleep(1500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/captureButton")).click();
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
		Markup d = MarkupHelper.createLabel(emptyField, ExtentColor.BLUE);
		testInfo.get().info(d);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/next_button")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/alertTitle")));
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity:id/alertTitle", "Error");
		TestUtils.assertSearchText("ID", "android:id/message", "\n" + 
				"KYC Form: Please Capture KYC/REGISTRATION Form \n" + 
				"Face Capture: Please capture valid portrait \n" + 
				"");
		getDriver().findElement(By.id("android:id/button1")).click();
		Thread.sleep(1000);
		
		// Capture KYC FORM
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/capture_kyc_form_button")).click();
		Thread.sleep(1500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/captureButton")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/ok"))).click();
		Thread.sleep(500);
		
		// View Captured ID
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
		try {
			if (getDriver().findElement(By.id("com.sf.biocapture.activity:id/alertTitle")).isDisplayed()) {
				getDriver().findElement(By.xpath("//android.widget.Button[@text='Start Capture ']")).click();
			}
		} catch (Exception e2) {
		}
		
	}

	@Parameters({ "dataEnv"})
	public static void individualForeignerForm(String dataEnv) throws Exception {

		WebDriverWait wait = new WebDriverWait(getDriver(), 60);
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resource/" + dataEnv + "/data.conf.json"));
		JSONObject envs = (JSONObject) config.get("IndividualForeignerDetails");
		
		String surname = (String) envs.get("surname");
		String firstname = (String) envs.get("firstname");
		String middlename = (String) envs.get("middlename");
		String maiden_name = (String) envs.get("maiden_name");
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
		String area = (String) envs.get("area");
		String passport_ID_number  = (String) envs.get("passport_ID_number");
		String house_or_flat_no = (String) envs.get("house_or_flat_no");

		TestUtils.scrollUp();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Personal Details']")));
		TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Personal Details']", "Personal Details");
		Thread.sleep(2000);
		
		// personal details
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/typeOfRegSpinner")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//android.widget.CheckedTextView[@index='0']")));
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@index='0']")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/surNameTXT")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/surNameTXT")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/surNameTXT")).sendKeys(surname);
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/firstNameTXT")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/firstNameTXT")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/firstNameTXT")).sendKeys(firstname);
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/middleNameTXT")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/middleNameTXT")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/middleNameTXT")).sendKeys(middlename);
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/momsMaidenNameTXT")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/momsMaidenNameTXT")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/momsMaidenNameTXT")).sendKeys(maiden_name);
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/maleRadioButton")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/selectDateButton")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("android:id/button1")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/alternatePhone")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/alternatePhone")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/alternatePhone")).sendKeys(alt_phone_number);
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/alternateEmail")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/alternateEmail")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/alternateEmail")).sendKeys(email);
		Thread.sleep(500);
		TestUtils.scrollDown();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/spinnerOccupation")));
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/spinnerOccupation")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Select Item']")));
		Thread.sleep(500);
		TestUtils.assertSearchText("ID", "android:id/text1", "[Select an Occupation]");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='" + occupation + "']")).click();
		Thread.sleep(500);
	
		// Social Media
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/socialMediaUsername")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/socialMediaUsername")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/socialMediaUsername")).sendKeys(social_media_username);
		Thread.sleep(500);
		
		// Next button
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/btnContinueReg")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/countrySpinner")));
		Thread.sleep(500);
		
		// Nationality
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/countrySpinner")).click();
		Thread.sleep(500);
		getDriver().findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(new UiSelector().text(\"INDIA\"));");
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='" + nationality + "']")).click();
		Thread.sleep(1000);
		
		// Proceed with registration without supplying all mandatory Passport fields and fill form
		String emptyField ="Proceed with registration without supplying all mandatory Passport fields and fill form";
		Markup e = MarkupHelper.createLabel(emptyField, ExtentColor.BLUE);
		testInfo.get().info(e);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/passport_details_title")));
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity:id/passport_details_title", "Passport/ID Details");
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/passport_issuing_country")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='[Select Issuing Country]']")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/passport_ok")).click();
		TestUtils.assertToast();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/passport_issuing_country")).click();
		Thread.sleep(1000);
		getDriver().findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(new UiSelector().text(\"INDIA\"));");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='" + nationality + "']")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/passport_number")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/passport_ok")).click();
		TestUtils.assertToast();
		Thread.sleep(500);
		
		// Passport/ ID Number
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/passport_number")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/passport_number")).sendKeys(passport_ID_number);
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/passport_ok")).click();
		TestUtils.assertToast();
		Thread.sleep(500);
		
		// Expiry date
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/passport_expiry_date")).click();
		Thread.sleep(500);
		try {
			try {
				getDriver().findElement(By.xpath("//android.widget.Button[@text='2020']")).click();
			} catch (NoSuchElementException e1) {
				getDriver().findElement(By.xpath("//android.widget.EditText[@text='2020']")).click();
			}
		} catch (NoSuchElementException ex) {
			getDriver().findElement(By.id("android:id/date_picker_header_year")).click();
			Thread.sleep(500);
			getDriver().findElement(By.xpath("//android.widget.TextView[@text='2021']")).click();
		}
		getDriver().findElement(By.id("android:id/button1")).click();
		Thread.sleep(1000);
		
		// Nigerian Resident Box
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/passport_nigerian_resident")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/passport_ok")).click();
		TestUtils.assertToast();
		Thread.sleep(500);
				
		// capture passport image
		TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity:id/capture_passport_image");
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/capture_passport_image")).click();
		Thread.sleep(1500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/captureButton")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/ok")));
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/ok")).click();
		Thread.sleep(1000);
		
		// View Captured ID
		String capturedPass = "View captured passport";
		Markup c = MarkupHelper.createLabel(capturedPass, ExtentColor.BLUE);
		testInfo.get().info(c);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/view_passport")).click();
		Thread.sleep(500);
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
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/passport_ok")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/stateOfResidenceSpinner")));
		Thread.sleep(1000);
		
		// State Residence
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/stateOfResidenceSpinner")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='" + state + "']")).click();
		Thread.sleep(500);

		// LGA Residence
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/lgaOfResidenceSpinner")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='" + LGA + "']")).click();
		Thread.sleep(500);

		// Area of residence
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/areaOfResidenceSpinner")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='" + area + "']")).click();
		Thread.sleep(500);

		// House/Flat Number
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/houseNumberEditText")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/houseNumberEditText")).sendKeys(house_or_flat_no);
		Thread.sleep(500);

		// Street
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/streetEditText")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/streetEditText")).sendKeys(street);
		Thread.sleep(500);

		// City
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/cityEditText")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/cityEditText")).sendKeys(city);
		Thread.sleep(500);

		// Postal Code
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/postalCodeTXT")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/postalCodeTXT")).sendKeys(postalcode);
		Thread.sleep(500);

		// Next button
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/btnNext")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Capture Data']")));
		TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Capture Data']", "Capture Data");
		TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Identification Type']", "Identification Type");
		Thread.sleep(500);

		// Capture Data
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/identification_type_spinner")).click();
		Thread.sleep(500);
		TestUtils.assertSearchText("XPATH", "//android.widget.CheckedTextView[@text='[Select Identification Type]']","[Select Identification Type]");
		Thread.sleep(500);

		// Capture ID CARD
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='National ID']")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/capture_id_button")).click();
		Thread.sleep(1500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/captureButton")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/ok")).click();
		Thread.sleep(500);

		// View Captured ID
		String capturedID = "View captured ID";
		Markup r = MarkupHelper.createLabel(capturedID, ExtentColor.BLUE);
		testInfo.get().info(r);
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
		String emptyField1 = "Proceed with registration without completing the capture process";
		Markup d = MarkupHelper.createLabel(emptyField1, ExtentColor.BLUE);
		testInfo.get().info(d);
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
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/captureButton")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/ok"))).click();
		Thread.sleep(500);

		// View Captured ID
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
		try {
			if (getDriver().findElement(By.id("com.sf.biocapture.activity:id/alertTitle")).isDisplayed()) {
				getDriver().findElement(By.xpath("//android.widget.Button[@text='Start Capture ']")).click();
					}
		} catch (Exception e2) {
		}
	}

}
