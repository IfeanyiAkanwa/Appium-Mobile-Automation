package demographics;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Parameters;

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
		String documentNumber = (String) envs.get("documentNumber");

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

		// Proceed with registration without supplying all mandatory fields
		TestUtils.testTitle("Proceed with registration without supplying all mandatory fields and fill form");
		TestUtils.scrollUp();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Personal Details']")));
		TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Personal Details']", "Personal Details");
		Thread.sleep(2000);
		
		// Company details
		wait.until(ExpectedConditions.visibilityOfElementLocated((By.id("com.sf.biocapture.activity." + Id + ":id/typeOfRegSpinner"))));
		Thread.sleep(1000);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/typeOfRegSpinner")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.CheckedTextView[@text='Company']")));
		Thread.sleep(1000);
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Company']")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated((By.id("com.sf.biocapture.activity." + Id + ":id/company_details_title"))));
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity." + Id + ":id/company_details_title", "Company Details");
		Thread.sleep(500);
		
		// Name/ Description
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/company_ok")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated((By.id("com.sf.biocapture.activity." + Id + ":id/alertTitle"))));
		TestUtils.assertSearchText("ID", "android:id/message", "Name/Description\n" + 
				"Empty field");
		getDriver().findElement(By.id("android:id/button1")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/company_name_descrptn")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/company_name_descrptn")).sendKeys(company_description);
		Thread.sleep(500);
		
		// Registration Number
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/company_ok")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated((By.id("com.sf.biocapture.activity." + Id + ":id/alertTitle"))));
		TestUtils.assertSearchText("ID", "android:id/message", "Company Registration Number\n" + "Empty field");
		getDriver().findElement(By.id("android:id/button1")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/company_regno")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/company_regno")).sendKeys(company_regno);
		Thread.sleep(500);
		
		// House/ Flat Number
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/company_ok")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated((By.id("com.sf.biocapture.activity." + Id + ":id/alertTitle"))));
		TestUtils.assertSearchText("ID", "android:id/message", "House/Flat Number\n" + 
				"Empty field");
		getDriver().findElement(By.id("android:id/button1")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/house_or_flat_no")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/house_or_flat_no")).sendKeys(house_or_flat_no);
		Thread.sleep(500);
		
		// Street
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/company_ok")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated((By.id("com.sf.biocapture.activity." + Id + ":id/alertTitle"))));
		TestUtils.assertSearchText("ID", "android:id/message", "Street\n" + 
				"Empty field");
		getDriver().findElement(By.id("android:id/button1")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/street")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/street")).sendKeys(company_street);
		Thread.sleep(500);
		
		// City
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/company_ok")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated((By.id("com.sf.biocapture.activity." + Id + ":id/alertTitle"))));
		TestUtils.assertSearchText("ID", "android:id/message", "City\n" + 
				"Empty field");
		getDriver().findElement(By.id("android:id/button1")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/city")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/city")).sendKeys(company_city);
		Thread.sleep(500);
		
		// Company Address State
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/company_ok")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated((By.id("com.sf.biocapture.activity." + Id + ":id/alertTitle"))));
		TestUtils.assertSearchText("ID", "android:id/message", "Please select State");
		getDriver().findElement(By.id("android:id/button1")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/company_state_address")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='" + company_state_address + "']")).click();
		Thread.sleep(500);
		
		// Company Address LGA
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/company_ok")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated((By.id("com.sf.biocapture.activity." + Id + ":id/alertTitle"))));
		TestUtils.assertSearchText("ID", "android:id/message", "Please select LGA");
		getDriver().findElement(By.id("android:id/button1")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/company_lga_address")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='" + company_lga_address + "']")).click();
		Thread.sleep(500);
		
		// Postal code
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/company_postalcode")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/company_postalcode")).sendKeys(company_postalcode);
		Thread.sleep(500);
		
		// Certificate of Incorporation
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/company_ok")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated((By.id("com.sf.biocapture.activity." + Id + ":id/alertTitle"))));
		TestUtils.assertSearchText("ID", "android:id/message", "Certificate of Incorporation is compulsory");
		getDriver().findElement(By.id("android:id/button1")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/document_type_spinner")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='Certificate of Incorporation *']")).click();
		Thread.sleep(500);

		// Push File
		File pic = new File(System.getProperty("user.dir") + "/files/idCard.jpg");
		getDriver().pushFile("/mnt/sdcard/picture.jpg", pic);

		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/btn_load_document")).click();
		Thread.sleep(500);
		TestUtils.scrollUntilElementIsVisible("XPATH", "//android.widget.TextView[@text='picture.jpg']");
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='picture.jpg']")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/btn_remove_document")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/btn_load_document")).click();
		Thread.sleep(1000);
		TestUtils.scrollUntilElementIsVisible("XPATH", "//android.widget.TextView[@text='picture.jpg']");
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='picture.jpg']")).click();
		Thread.sleep(500);

		// contact person form
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/document_type_spinner")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='Contact Person Form *']")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/btn_remove_document")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/btn_load_document")).click();
		Thread.sleep(1000);
		TestUtils.scrollUntilElementIsVisible("XPATH", "//android.widget.TextView[@text='picture.jpg']");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='picture.jpg']")).click();
		Thread.sleep(500);

		// save
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/company_ok")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Personal Details']")));
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/surNameTXT")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/surNameTXT")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/surNameTXT")).sendKeys(surname);
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/firstNameTXT")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/firstNameTXT")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/firstNameTXT")).sendKeys(firstname);
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/middleNameTXT")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/middleNameTXT")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/middleNameTXT")).sendKeys(middlename);
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/momsMaidenNameTXT")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/momsMaidenNameTXT")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/momsMaidenNameTXT")).sendKeys(maiden_name);
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/femaleRadioButton")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/selectDateButton")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("android:id/button1")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/alternatePhone")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/alternatePhone")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/alternatePhone")).sendKeys(alt_phone_number);
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/alternateEmail")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/alternateEmail")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/alternateEmail")).sendKeys(email);
		Thread.sleep(500);
		TestUtils.scrollDown();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity." + Id + ":id/spinnerOccupation")));
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/spinnerOccupation")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='[Select an Occupation]']")));
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='" + occupation + "']")).click();
		Thread.sleep(500);
		
		// Social Media
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/socialMediaUsername")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/socialMediaUsername")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/socialMediaUsername")).sendKeys(social_media_username);
		Thread.sleep(500);
		
		// Next button
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/btnContinueReg")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity." + Id + ":id/countrySpinner")));
		Thread.sleep(500);
		
		// Nationality
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='" + nationality + "']")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='" + nationality + "']")).click();
		Thread.sleep(500);
		
		//State of Origin
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/stateOfOriginSpinner")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='" + state + "']")).click();
		Thread.sleep(500);
		
		// LGA of Origin
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/lgaOfOriginSpinner")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='" + LGA + "']")).click();
		Thread.sleep(500);
		
		//  State Residence
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/stateOfResidenceSpinner")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='" + states_residence + "']")).click();
		Thread.sleep(500);
		
		// LGA Residence
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/lgaOfResidenceSpinner")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='" + lga_residence + "']")).click();
		Thread.sleep(500);
		
		// Area of residence
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/areaOfResidenceSpinner")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='" + area + "']")).click();
		Thread.sleep(500);
		
		// House/ Flat Number
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/houseNumberEditText")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/houseNumberEditText")).sendKeys(house_or_flat_no);
		Thread.sleep(500);
		
		// Street
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/streetEditText")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/streetEditText")).sendKeys(street);
		Thread.sleep(500);
		
		// City
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/cityEditText")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/cityEditText")).sendKeys(city);
		Thread.sleep(500);
		
		// Postal Code
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/postalCodeTXT")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/postalCodeTXT")).sendKeys(postalcode);
		Thread.sleep(500);
		
		// Next button
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/btnNext")).click();
		Thread.sleep(500);

		// Proceed with registration without completing the capture process
		TestUtils.testTitle("Proceed with registration without completing the capture process");
		TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Capture Data']", "Capture Data");
		Thread.sleep(1000);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/nextButton")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity." + Id + ":id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/message",
				"\n" + "KYC Form: Please Capture KYC/REGISTRATION Form \n"+ 
						"Identification Type: Please capture means of identification \n"
						+"Face Capture: Please capture valid portrait \n" + "");
		getDriver().findElement(By.id("android:id/button1")).click();
		Thread.sleep(1000);

		// Capture Identification Type
		TestUtils.testTitle("Capture Identification Type");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Capture Data']")));
		TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Identification Type']", "Identification Type");
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/identificationTypeSpinner")).click();
		Thread.sleep(500);

		// Capture ID CARD
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='National ID card']")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/captureIdButton")).click();
		Thread.sleep(1500);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/captureButton")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/ok")).click();
		Thread.sleep(500);
		
		// Document Nuumber
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/documentNumber")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/documentNumber")).sendKeys(documentNumber);
		Thread.sleep(500);
		
		// Document Expiry Date
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/selectDateButton")).click();
		Thread.sleep(1000);
		try {
			try {
				getDriver().findElement(By.xpath("//android.widget.Button[@text='2021']")).click();
				getDriver().findElement(By.id("android:id/button1")).click();
			} catch (NoSuchElementException e1) {
				getDriver().findElement(By.xpath("//android.widget.EditText[@text='2021']")).click();
				getDriver().findElement(By.id("android:id/button1")).click();
			}
		} catch (NoSuchElementException ex) {
			getDriver().findElement(By.id("android:id/date_picker_header_year")).click();
			Thread.sleep(500);
			getDriver().findElement(By.xpath("//android.widget.TextView[@text='2022']")).click();
		}

		// View Captured ID
		TestUtils.testTitle("Preview captured ID");
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/viewCaptureIdButton")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/title")));
		TestUtils.assertSearchText("ID", "android:id/title", "Preview");
		if (getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/image")).isDisplayed()) {
			testInfo.get().info("Captured ID is displayed");
		} else {
			testInfo.get().info("Captured ID is not displayed");
			testInfo.get().addScreenCaptureFromPath(TestUtils.addScreenshot());
		}
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/done_button")).click();
		Thread.sleep(500);

		// Capture KYC FORM
		TestUtils.testTitle("Capture KYC FORM");
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity." + Id + ":id/captureKycFormButton", "Capture KYC Form *");
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/captureKycFormButton")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/captureButton")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/ok")).click();
		Thread.sleep(500);

		// Preview
		TestUtils.testTitle("Preview KYC FORM");
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/viewKycFormButton")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/title")));
		TestUtils.assertSearchText("ID", "android:id/title", "Preview");
		if (getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/image")).isDisplayed()) {
			testInfo.get().info("Captured KYC FORM is displayed");
		} else {
			testInfo.get().info("Captured KYC FORM is not displayed");
			testInfo.get().addScreenCaptureFromPath(TestUtils.addScreenshot());
		}
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/done_button")).click();
		Thread.sleep(500);

		// Face Capture
		TestUtils.testTitle("Face Capture");
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity." + Id + ":id/faceCaptureButton", "Face Capture *");
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/faceCaptureButton")).click();
		Thread.sleep(500);
		try {
			if (getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/alertTitle")).isDisplayed()) {
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
		String documentNumber = (String) envs.get("documentNumber");

		TestUtils.scrollUp();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Personal Details']")));
		TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Personal Details']", "Personal Details");
		Thread.sleep(2000);
		
		// personal details
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/typeOfRegSpinner")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//android.widget.CheckedTextView[@index='0']")));
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@index='0']")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/surNameTXT")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/surNameTXT")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/surNameTXT")).sendKeys(surname);
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/firstNameTXT")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/firstNameTXT")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/firstNameTXT")).sendKeys(firstname);
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/middleNameTXT")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/middleNameTXT")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/middleNameTXT")).sendKeys(middlename);
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/momsMaidenNameTXT")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/momsMaidenNameTXT")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/momsMaidenNameTXT")).sendKeys(maiden_name);
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/maleRadioButton")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/selectDateButton")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("android:id/button1")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/alternatePhone")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/alternatePhone")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/alternatePhone")).sendKeys(alt_phone_number);
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/alternateEmail")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/alternateEmail")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/alternateEmail")).sendKeys(email);
		Thread.sleep(500);
		TestUtils.scrollDown();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity." + Id + ":id/spinnerOccupation")));
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/spinnerOccupation")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Select Item']")));
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='" + occupation + "']")).click();
		Thread.sleep(500);
	
		// Social Media
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/socialMediaUsername")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/socialMediaUsername")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/socialMediaUsername")).sendKeys(social_media_username);
		Thread.sleep(500);
		
		// Next button
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/btnContinueReg")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity." + Id + ":id/countrySpinner")));
		Thread.sleep(500);
		
		// Nationality
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/countrySpinner")).click();
		Thread.sleep(500);
		getDriver().findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(new UiSelector().text(\"INDIA\"));");
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='" + nationality + "']")).click();
		Thread.sleep(1000);
		
		// Proceed with registration without supplying all mandatory Passport fields and fill form
		TestUtils.testTitle("Proceed with registration without supplying all mandatory Passport fields and fill form");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity." + Id + ":id/passport_details_title")));
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity." + Id + ":id/passport_details_title", "Passport/ID Details");
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/passport_issuing_country")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='[Select Issuing Country]']")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/passport_ok")).click();
		Thread.sleep(500);
		TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='[Select Issuing Country]']", "[Select Issuing Country]");
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/passport_issuing_country")).click();
		Thread.sleep(1000);
		getDriver().findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(new UiSelector().text(\"INDIA\"));");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='" + nationality + "']")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/passport_number")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/passport_ok")).click();
		Thread.sleep(500);
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity." + Id + ":id/field_passport_number", "Passport/ID Number*");
		
		// Passport/ ID Number
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/passport_number")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/passport_number")).sendKeys(passport_ID_number);
		Thread.sleep(500);
		
		// Expiry date
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/passport_expiry_date")).click();
		Thread.sleep(500);
		try {
			try {
				getDriver().findElement(By.xpath("//android.widget.Button[@text='2021']")).click();
				getDriver().findElement(By.id("android:id/button1")).click();
			} catch (NoSuchElementException e1) {
				getDriver().findElement(By.xpath("//android.widget.EditText[@text='2021']")).click();
				getDriver().findElement(By.id("android:id/button1")).click();
			}
		} catch (NoSuchElementException ex) {
			getDriver().findElement(By.id("android:id/date_picker_header_year")).click();
			Thread.sleep(500);
			getDriver().findElement(By.xpath("//android.widget.TextView[@text='2022']")).click();
		}
		getDriver().findElement(By.id("android:id/button1")).click();
		Thread.sleep(1000);
		
		// Nigerian Resident Box
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/passport_nigerian_resident")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/passport_ok")).click();
		TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Passport Image*']", "Passport Image*");
		Thread.sleep(500);
				
		// capture passport image
		TestUtils.testTitle("Capture passport Image and Preview");
		TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity." + Id + ":id/capture_passport_image");
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/capture_passport_image")).click();
		Thread.sleep(1500);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/captureButton")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity." + Id + ":id/ok")));
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/ok")).click();
		Thread.sleep(1000);
		
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/view_passport")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/title")));
		TestUtils.assertSearchText("ID", "android:id/title", "Preview");
		if (getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/image")).isDisplayed()) {
			testInfo.get().info("Captured ID is displayed");
		} else {
			testInfo.get().info("Captured ID is not displayed");
			testInfo.get().addScreenCaptureFromPath(TestUtils.addScreenshot());
		}
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/done_button")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/passport_ok")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity." + Id +":id/stateOfResidenceSpinner")));
		Thread.sleep(1000);
		
		// State Residence
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/stateOfResidenceSpinner")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='" + state + "']")).click();
		Thread.sleep(500);

		// LGA Residence
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/lgaOfResidenceSpinner")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='" + LGA + "']")).click();
		Thread.sleep(500);

		// Area of residence
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/areaOfResidenceSpinner")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='" + area + "']")).click();
		Thread.sleep(500);

		// House/Flat Number
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/houseNumberEditText")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/houseNumberEditText")).sendKeys(house_or_flat_no);
		Thread.sleep(500);

		// Street
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/streetEditText")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/streetEditText")).sendKeys(street);
		Thread.sleep(500);

		// City
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/cityEditText")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/cityEditText")).sendKeys(city);
		Thread.sleep(500);

		// Postal Code
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/postalCodeTXT")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/postalCodeTXT")).sendKeys(postalcode);
		Thread.sleep(500);

		// Next button
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/btnNext")).click();
		Thread.sleep(500);
		
		// Proceed with registration without completing the capture process
		TestUtils.testTitle("Proceed with registration without completing the capture process");
		TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Capture Data']", "Capture Data");
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/nextButton")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity." + Id + ":id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/message", "\n" + 
				"KYC Form: Please Capture KYC/REGISTRATION Form \n" + 
				"Face Capture: Please capture valid portrait \n" + 
				"");
		getDriver().findElement(By.id("android:id/button1")).click();
		Thread.sleep(1000);
		
		// Capture Identification Type
		TestUtils.testTitle("Capture Identification Type");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Capture Data']")));
		TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Identification Type']", "Identification Type");
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/identificationTypeSpinner")).click();
		Thread.sleep(500);

		// Capture ID CARD
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='National ID card']")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/captureIdButton")).click();
		Thread.sleep(1500);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/captureButton")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/ok")).click();
		Thread.sleep(500);

		// Document Number
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/documentNumber")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/documentNumber")).sendKeys(documentNumber);
		Thread.sleep(500);
				
		// Document Expiry Date
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/selectDateButton")).click();
		Thread.sleep(1000);
		try {
			try {
				getDriver().findElement(By.xpath("//android.widget.Button[@text='2021']")).click();
				getDriver().findElement(By.id("android:id/button1")).click();
			} catch (NoSuchElementException e1) {
				getDriver().findElement(By.xpath("//android.widget.EditText[@text='2021']")).click();
				getDriver().findElement(By.id("android:id/button1")).click();
			}
		} catch (NoSuchElementException ex) {
			getDriver().findElement(By.id("android:id/date_picker_header_year")).click();
			Thread.sleep(500);
			getDriver().findElement(By.xpath("//android.widget.TextView[@text='2022']")).click();
		}
		
		// View Captured ID
		TestUtils.testTitle("Preview captured ID");
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/viewCaptureIdButton")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/title")));
		TestUtils.assertSearchText("ID", "android:id/title", "Preview");
		if (getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/image")).isDisplayed()) {
			testInfo.get().info("Captured ID is displayed");
		} else {
			testInfo.get().info("Captured ID is not displayed");
			testInfo.get().addScreenCaptureFromPath(TestUtils.addScreenshot());
		}
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/done_button")).click();
		Thread.sleep(500);

		// Capture KYC FORM
		TestUtils.testTitle("Capture KYC FORM");
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity." + Id + ":id/captureKycFormButton", "Capture KYC Form *");
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/captureKycFormButton")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/captureButton")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/ok")).click();
		Thread.sleep(500);

		// Preview
		TestUtils.testTitle("Preview KYC FORM"); 
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/viewKycFormButton")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/title")));
		TestUtils.assertSearchText("ID", "android:id/title", "Preview");
		if (getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/image")).isDisplayed()) {
			testInfo.get().info("Captured KYC FORM is displayed");
		} else {
			testInfo.get().info("Captured KYC FORM is not displayed");
			testInfo.get().addScreenCaptureFromPath(TestUtils.addScreenshot());
		}
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/done_button")).click();
		Thread.sleep(500);

		// Face Capture
		TestUtils.testTitle("Face Capture");
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity." + Id + ":id/faceCaptureButton", "Face Capture *");
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/faceCaptureButton")).click();
		Thread.sleep(500);
		try {
			if (getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/alertTitle")).isDisplayed()) {
				getDriver().findElement(By.xpath("//android.widget.Button[@text='Start Capture ']")).click();
					}
		} catch (Exception e2) {
		}
	}

}
