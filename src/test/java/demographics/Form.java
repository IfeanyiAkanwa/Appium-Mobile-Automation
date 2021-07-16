package demographics;

import db.ConnectDB;
import freemarker.core.Environment;
import io.appium.java_client.android.AndroidKeyCode;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.interactions.touch.TouchActions;
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
	public static void individualForeignerFormOld(String dataEnv) throws Exception {

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
		String nin = (String) envs.get("nin");

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Personal Details']")));
		Thread.sleep(2000);

		// personal details
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/typeOfRegSpinner")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//android.widget.CheckedTextView[@index='0']")));
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@index='0']")).click();

		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/surNameTXT")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/surNameTXT")).sendKeys(surname);

		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/firstNameTXT")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/firstNameTXT")).sendKeys(firstname);

		TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/middleNameTXT");

		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/middleNameTXT")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/middleNameTXT")).sendKeys(middlename);

		TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/momsMaidenNameTXT");

		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/momsMaidenNameTXT")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/momsMaidenNameTXT")).sendKeys(maiden_name);

		TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/maleRadioButton");

		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/maleRadioButton")).click();

		TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/selectDateButton");

		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/selectDateButton")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/date_picker_header_year")));
		getDriver().findElement(By.id("android:id/date_picker_header_year")).click();

		TestUtils.scrollUntilElementIsVisible("XPATH", "//android.widget.TextView[@text='2000']");

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='2000']")));
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='2000']")).click();
		getDriver().findElement(By.id("android:id/button1")).click();

		TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/alternatePhone");

		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/alternatePhone")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/alternatePhone")).sendKeys(alt_phone_number);

		TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/alternateEmail");

		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/alternateEmail")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/alternateEmail")).sendKeys(email);

		TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/spinnerOccupation");

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/spinnerOccupation")));
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/spinnerOccupation")).click();

		TestUtils.scrollUntilElementIsVisible("XPATH", "//android.widget.TextView[@text='Select Item']");
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Select Item']")));

		TestUtils.scrollUntilElementIsVisible("XPATH", "//android.widget.TextView[@text='" + occupation + "']");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='" + occupation + "']")).click();
		Thread.sleep(500);

		// Social Media
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/socialMediaUsername")));
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/socialMediaUsername")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/socialMediaUsername")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/socialMediaUsername")).sendKeys(social_media_username);
		Thread.sleep(500);

		// Next button
		TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/btnContinueReg");
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/btnContinueReg")).click();
		Thread.sleep(3000);
		//wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/countrySpinner")));

		try{
			TestUtils.assertSearchText("ID", "android:id/title", "Edit Reason");
			getDriver().findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/androidx.recyclerview.widget.RecyclerView/android.view.ViewGroup[1]/android.widget.LinearLayout/android.widget.LinearLayout")).click();
			Thread.sleep(1000);
			getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Descriptions']")).click();

			getDriver().findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/androidx.recyclerview.widget.RecyclerView/android.view.ViewGroup[2]/android.widget.LinearLayout/android.widget.LinearLayout")).click();
			Thread.sleep(1000);
			getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Descriptions']")).click();

			getDriver().findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/androidx.recyclerview.widget.RecyclerView/android.view.ViewGroup[3]/android.widget.LinearLayout/android.widget.LinearLayout")).click();
			Thread.sleep(1000);
			getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Descriptions']")).click();

			getDriver().findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/androidx.recyclerview.widget.RecyclerView/android.view.ViewGroup[4]/android.widget.LinearLayout/android.widget.LinearLayout")).click();
			Thread.sleep(1000);
			getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Descriptions']")).click();

			TestUtils.scrollUntilElementIsVisible("XPATH", "//android.widget.CheckedTextView[@text='Descriptions']");

			getDriver().findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/androidx.recyclerview.widget.RecyclerView/android.view.ViewGroup[5]/android.widget.LinearLayout/android.widget.LinearLayout")).click();
			Thread.sleep(1000);
			getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Descriptions']")).click();

			getDriver().findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/androidx.recyclerview.widget.RecyclerView/android.view.ViewGroup[6]/android.widget.LinearLayout/android.widget.LinearLayout")).click();
			Thread.sleep(1000);
			getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Descriptions']")).click();
			// Go back
			getDriver().pressKeyCode(AndroidKeyCode.BACK);
		}catch(Exception e){

		}

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/countrySpinner")));
		Thread.sleep(500);

		try {
			// Nationality
			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/countrySpinner")).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='" + nationality + "']")));
			getDriver().findElement(By.xpath("//android.widget.TextView[@text='" + nationality + "']")).click();
		}catch (Exception e){
			//Nationality has been selected from the beginning of the registration proceed
		}

		// Proceed with registration without supplying all mandatory Passport fields and fill form
		TestUtils.testTitle("Proceed with registration without supplying all mandatory Passport fields and fill form");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/passport_details_title")));
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/passport_details_title", "Passport/ID Details");

		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/passport_issuing_country")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='[Select Issuing Country]']")));
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='[Select Issuing Country]']")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/passport_issuing_country")));
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/passport_ok")).click();

		TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='[Select Issuing Country]']", "[Select Issuing Country]");

		//|Select Passport Issuing Country
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/passport_issuing_country")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='[Select Issuing Country]']")));
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='" + nationality + "']")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/passport_issuing_country")));


		//Submit Without filling Passport Number
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/passport_number")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/passport_ok")).click();
		Thread.sleep(500);
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/field_passport_number", "Passport/ID Number*");

		// Passport/ ID Number
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/passport_number")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/passport_number")).sendKeys(passport_ID_number);

		// Expiry date
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/passport_expiry_date")).click();
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
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='2022']")));
			getDriver().findElement(By.xpath("//android.widget.TextView[@text='2022']")).click();
		}
		getDriver().findElement(By.id("android:id/button1")).click();

		// Nigerian Resident Box
		TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/passport_nigerian_resident");
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/passport_nigerian_resident")).click();

		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/passport_ok")).click();

		TestUtils.scrollUntilElementIsVisible("XPATH", "//android.widget.TextView[@text='Passport Image*']");
		TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Passport Image*']", "Passport Image*");

		// capture passport image
		TestUtils.testTitle("Capture passport Image and Preview");
		TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/capture_passport_image");
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/capture_passport_image")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/captureButton")));
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/captureButton")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/ok")));
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/ok")).click();

		TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/view_passport");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/view_passport")));
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/view_passport")).click();
		TestUtils.scrollUntilElementIsVisible("ID", "android:id/title");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/title")));
		TestUtils.assertSearchText("ID", "android:id/title", "Preview");
		if (getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/image")).isDisplayed()) {
			testInfo.get().info("Captured ID is displayed");
		} else {
			testInfo.get().info("Captured ID is not displayed");
			testInfo.get().addScreenCaptureFromPath(TestUtils.addScreenshot());
		}
		TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/done_button");
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/done_button")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/passport_ok")));
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/passport_ok")).click();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/ninEditText")));
		if(getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/ninEditText")).getText().equalsIgnoreCase("NIN")){
			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/ninEditText")).clear();
			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/ninEditText")).sendKeys(nin);
		}
		// State Residence
		TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/stateOfResidenceSpinner");
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/stateOfResidenceSpinner")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='" + state + "']")).click();
		Thread.sleep(500);


		TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/lgaOfResidenceSpinner");
		// LGA Residence
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/lgaOfResidenceSpinner")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='" + LGA + "']")).click();
		Thread.sleep(500);


		// Area of residence
		TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/areaOfResidenceSpinner");
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/areaOfResidenceSpinner")).click();
		Thread.sleep(500);
		TestUtils.scrollUntilElementIsVisible("XPATH", "//android.widget.TextView[@text='" + area + "']");
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='" + area + "']")).click();
		Thread.sleep(500);

		// House/Flat Number
		TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/houseNumberEditText");
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/houseNumberEditText")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/houseNumberEditText")).sendKeys(house_or_flat_no);
		Thread.sleep(500);

		// Street
		TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/streetEditText");
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/streetEditText")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/streetEditText")).sendKeys(street);
		Thread.sleep(500);

		// City
		TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/citySpinner");
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/citySpinner")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='"+city+"']")));
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='"+city+"']")).click();
		Thread.sleep(500);

		// Postal Code
		TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/postalCodeSpinner");
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/postalCodeSpinner")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='"+postalcode+"']")));
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='"+postalcode+"']")).click();
		Thread.sleep(500);

		// Next button
		TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/btnNext");
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/btnNext")).click();
		Thread.sleep(500);

		// Proceed with registration without completing the capture process
		TestUtils.testTitle("Proceed with registration without completing the capture process");
		TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Capture Data']", "Capture Data");
//		TestUtils.scrollDown();
//		TestUtils.scrollDown();
//		TestUtils.scrollDown();
//		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/nextButton")).click();
//		Thread.sleep(500);
//		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
//		TestUtils.assertSearchText("ID", "android:id/message", "\n" +
//				"KYC Form: Please Capture KYC/REGISTRATION Form \n" +
//				"Face Capture: Please capture valid portrait \n" +
//				"");
//		getDriver().findElement(By.id("android:id/button1")).click();
//		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/nextButton")));

		// Capture Identification Type
//		TestUtils.scrollUp();
		TestUtils.testTitle("Capture Identification Type");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Capture Data']")));
		TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='[Select Identification Type]']", "[Select Identification Type]");
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/identificationTypeSpinner")).click();
		Thread.sleep(500);

		// Capture ID CARD
		TestUtils.scrollUntilElementIsVisible("XPATH", "//android.widget.CheckedTextView[@text='Passport']");
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Passport']")).click();
		Thread.sleep(500);
		TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/captureIdButton");
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/captureIdButton")).click();
		Thread.sleep(1500);
		TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/captureButton");
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/captureButton")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/ok")).click();
		Thread.sleep(500);

		// Document Number
		TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/documentNumber");
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/documentNumber")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/documentNumber")).sendKeys(documentNumber);
		Thread.sleep(500);

		// Document Expiry Date
		TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/selectDateButton");
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/selectDateButton")).click();
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
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='2022']")));
			getDriver().findElement(By.xpath("//android.widget.TextView[@text='2022']")).click();
			getDriver().findElement(By.id("android:id/button1")).click();
		}

		// View Captured ID
		TestUtils.testTitle("Preview captured ID");
		TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/viewCaptureIdButton");
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/viewCaptureIdButton")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/title")));
		TestUtils.assertSearchText("ID", "android:id/title", "Preview");
		if (getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/image")).isDisplayed()) {
			testInfo.get().info("Captured ID is displayed");
		} else {
			testInfo.get().info("Captured ID is not displayed");
			testInfo.get().addScreenCaptureFromPath(TestUtils.addScreenshot());
		}
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/done_button")).click();
		Thread.sleep(500);

		//COO Form
		TestUtils.testTitle("Upload COO Form");
		try{
			TestUtils.scrollDown();
			// Push File
			File pic = new File(System.getProperty("user.dir") + "/files/idCard.jpg");

			getDriver().pushFile("/storage/emulated/0/picture.jpg", pic);

			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/documentUploadSpinner")).click();
			Thread.sleep(500);
			TestUtils.assertSearchText("XPATH", "//android.widget.CheckedTextView[@text='Affidavit']", "Affidavit");
			TestUtils.assertSearchText("XPATH", "//android.widget.CheckedTextView[@text='Letter of Authorization']", "Letter of Authorization");
			TestUtils.assertSearchText("XPATH", "//android.widget.CheckedTextView[@text='Newspaper Record']", "Newspaper Record");
			TestUtils.assertSearchText("XPATH", "//android.widget.CheckedTextView[@text='WAEC Result']", "WAEC Result");
			getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Affidavit']")).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/uploadDocumentBtn")));
			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/uploadDocumentBtn")).click();

			TestUtils.scrollDown();

			TestUtils.scrollUntilElementIsVisible("XPATH", "//android.widget.TextView[@text='picture.jpg']");


			getDriver().findElement(By.xpath("//android.widget.TextView[@text='picture.jpg']")).click();
			Thread.sleep(500);
			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/btn_remove_document")).click();
			Thread.sleep(500);
			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/uploadDocumentBtn")).click();
			Thread.sleep(1000);
			TestUtils.scrollUntilElementIsVisible("XPATH", "//android.widget.TextView[@text='picture.jpg']");
			getDriver().findElement(By.xpath("//android.widget.TextView[@text='picture.jpg']")).click();
			Thread.sleep(500);
		}catch (Exception e){

		}

		TestUtils.scrollDown();

		// Capture KYC FORM
		TestUtils.testTitle("Capture KYC FORM");
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/captureKycFormButton", "CAPTURE KYC FORM *");
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/captureKycFormButton")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/captureButton")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/ok")).click();
		Thread.sleep(500);

		// Preview
		TestUtils.testTitle("Preview KYC FORM");
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/viewKycFormButton")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/title")));
		TestUtils.assertSearchText("ID", "android:id/title", "Preview");
		if (getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/image")).isDisplayed()) {
			testInfo.get().info("Captured KYC FORM is displayed");
		} else {
			testInfo.get().info("Captured KYC FORM is not displayed");
			testInfo.get().addScreenCaptureFromPath(TestUtils.addScreenshot());
		}
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/done_button")).click();
		Thread.sleep(500);

		TestUtils.scrollDown();

		// Face Capture
		TestUtils.testTitle("Face Capture");
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/faceCaptureButton", "FACE CAPTURE *");
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/faceCaptureButton")).click();

		try{
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/captureButton")));

			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/captureButton")).click();
		}catch (Exception e){

			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/buttonCapturePicture")));

			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/buttonCapturePicture")).click();
		}
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/message", "Subscriber's face was successfully captured");
		getDriver().findElement(By.id("android:id/button1")).click();
		Thread.sleep(500);

		//Click next button
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/nextButton")).click();

		//Fingerprint capture/

		//Submit without overriding fingerprint
		TestUtils.testTitle("Save Enrollment without overriding fingerprint");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/btn_multi_capture")));
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/btn_multi_capture", "MULTI CAPTURE");
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/fp_save_enrolment")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/message", "No finger was captured");
		getDriver().findElement(By.id("android:id/button1")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/fp_save_enrolment")));


		//Override left hand
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/btn_override_left")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/message")));
		TestUtils.assertSearchText("ID", "android:id/message", "Are you sure? Note that you have to provide a reason");
		getDriver().findElement(By.id("android:id/button1")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/captureButton")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/ok")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Ageing']")));
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='Ageing']")).click();

		//Submit without overriding right hand
		TestUtils.testTitle("Save Enrollment without capturing Right Hand");
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/fp_save_enrolment")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/message", "RIGHT HAND wasn't overridden, and all selected RIGHT HAND fingers were not captured.");
		getDriver().findElement(By.id("android:id/button1")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/fp_save_enrolment")));

		//Override right hand
		TestUtils.scrollDown();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/btn_override_right")));
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/btn_override_right")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/message")));
		TestUtils.assertSearchText("ID", "android:id/message", "Are you sure? Note that you have to provide a reason");
		getDriver().findElement(By.id("android:id/button1")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/captureButton")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/ok")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Ageing']")));
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='Ageing']")).click();

		//Save enrollment
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/fp_save_enrolment")));
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/fp_save_enrolment")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/message")));
		TestUtils.assertSearchText("ID", "android:id/message", "Captured record was saved successfully");
		getDriver().findElement(By.id("android:id/button1")).click();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/message")));
		TestUtils.assertSearchText("ID", "android:id/message", "Do you wish to Top Up the registered subscriber ?");
		getDriver().findElement(By.id("android:id/button3")).click();

	}

	@Parameters({ "dataEnv"})
	public static void individualForeignerForm(String dataEnv) throws Exception {

		WebDriverWait wait = new WebDriverWait(getDriver(), 60);
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resource/" + dataEnv + "/data.conf.json"));
		JSONObject envs = (JSONObject) config.get("IndividualForeignerDetails");
		JSONObject envs2 = (JSONObject) config.get("NewRegistration");

		String valid_msisdn = (String) envs.get("valid_msisdn");

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
		String nin = (String) envs.get("nin");

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Personal Details']")));

		TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/alternatePhone");

		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/alternatePhone")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/alternatePhone")).sendKeys(alt_phone_number);

		// Next button
		TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/btnContinueReg");
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/btnContinueReg")).click();

		//Edit Reasons
		TestUtils.assertSearchText("ID", "android:id/title", "Edit Reason");
		getDriver().findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/androidx.recyclerview.widget.RecyclerView/android.view.ViewGroup[1]/android.widget.LinearLayout/android.widget.LinearLayout")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Descriptions']")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/submit_button")).click();

		// Next button
		TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/btnContinueReg");
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/btnContinueReg")).click();
		Thread.sleep(500);

		// State Residence
		TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/stateOfResidenceSpinner");
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/stateOfResidenceSpinner")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='" + state + "']")).click();
		Thread.sleep(500);


		TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/lgaOfResidenceSpinner");
		// LGA Residence
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/lgaOfResidenceSpinner")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='" + LGA + "']")).click();
		Thread.sleep(500);


		// Area of residence
		TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/areaOfResidenceSpinner");
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/areaOfResidenceSpinner")).click();
		Thread.sleep(500);
		TestUtils.scrollUntilElementIsVisible("XPATH", "//android.widget.TextView[@text='" + area + "']");
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='" + area + "']")).click();
		Thread.sleep(500);

		// House/Flat Number
		TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/houseNumberEditText");
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/houseNumberEditText")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/houseNumberEditText")).sendKeys(house_or_flat_no);
		Thread.sleep(500);

		// Street
		TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/streetEditText");
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/streetEditText")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/streetEditText")).sendKeys(street);
		Thread.sleep(500);

		// City
		TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/citySpinner");
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/citySpinner")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='"+city+"']")));
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='"+city+"']")).click();
		Thread.sleep(500);

		// Postal Code
		TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/postalCodeSpinner");
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/postalCodeSpinner")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='"+postalcode+"']")));
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='"+postalcode+"']")).click();
		Thread.sleep(500);

		// Next button
		TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/btnNext");
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/btnNext")).click();
		Thread.sleep(500);


		TestUtils.testTitle("Capture Identification Type");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Capture Data']")));
		TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='[Select Identification Type]']", "[Select Identification Type]");
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/identificationTypeSpinner")).click();
		Thread.sleep(500);

		// Capture ID CARD
		TestUtils.scrollUntilElementIsVisible("XPATH", "//android.widget.CheckedTextView[@text='Passport']");
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Passport']")).click();
		Thread.sleep(500);
		TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/captureIdButton");
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/captureIdButton")).click();
		Thread.sleep(1500);
		TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/captureButton");
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/captureButton")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/ok")).click();
		Thread.sleep(500);

		// Document Number
		TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/documentNumber");
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/documentNumber")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/documentNumber")).sendKeys(documentNumber);
		Thread.sleep(500);

		// Document Expiry Date
		TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/selectDateButton");
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/selectDateButton")).click();
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
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='2022']")));
			getDriver().findElement(By.xpath("//android.widget.TextView[@text='2022']")).click();
			getDriver().findElement(By.id("android:id/button1")).click();
		}

		// View Captured ID
		TestUtils.testTitle("Preview captured ID");
		TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/viewCaptureIdButton");
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/viewCaptureIdButton")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/title")));
		TestUtils.assertSearchText("ID", "android:id/title", "Preview");
		if (getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/image")).isDisplayed()) {
			testInfo.get().info("Captured ID is displayed");
		} else {
			testInfo.get().info("Captured ID is not displayed");
			testInfo.get().addScreenCaptureFromPath(TestUtils.addScreenshot());
		}
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/done_button")).click();
		Thread.sleep(500);

		//COO Form
		TestUtils.testTitle("Upload COO Form");
		try{
			TestUtils.scrollDown();
			// Push File
			File pic = new File(System.getProperty("user.dir") + "/files/idCard.jpg");

			getDriver().pushFile("/storage/emulated/0/picture.jpg", pic);

			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/documentUploadSpinner")).click();
			Thread.sleep(500);
			TestUtils.assertSearchText("XPATH", "//android.widget.CheckedTextView[@text='Affidavit']", "Affidavit");
			TestUtils.assertSearchText("XPATH", "//android.widget.CheckedTextView[@text='Letter of Authorization']", "Letter of Authorization");
			TestUtils.assertSearchText("XPATH", "//android.widget.CheckedTextView[@text='Newspaper Record']", "Newspaper Record");
			TestUtils.assertSearchText("XPATH", "//android.widget.CheckedTextView[@text='WAEC Result']", "WAEC Result");
			getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Affidavit']")).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/uploadDocumentBtn")));
			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/uploadDocumentBtn")).click();

			TestUtils.scrollDown();

			TestUtils.scrollUntilElementIsVisible("XPATH", "//android.widget.TextView[@text='picture.jpg']");


			getDriver().findElement(By.xpath("//android.widget.TextView[@text='picture.jpg']")).click();
			Thread.sleep(500);
			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/btn_remove_document")).click();
			Thread.sleep(500);
			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/uploadDocumentBtn")).click();
			Thread.sleep(1000);
			TestUtils.scrollUntilElementIsVisible("XPATH", "//android.widget.TextView[@text='picture.jpg']");
			getDriver().findElement(By.xpath("//android.widget.TextView[@text='picture.jpg']")).click();
			Thread.sleep(500);
		}catch (Exception e){

		}

		TestUtils.scrollDown();

		// Capture KYC FORM
		TestUtils.testTitle("Capture KYC FORM");
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/captureKycFormButton", "CAPTURE KYC FORM *");
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/captureKycFormButton")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/captureButton")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/ok")).click();
		Thread.sleep(500);

		// Preview
		TestUtils.testTitle("Preview KYC FORM");
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/viewKycFormButton")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/title")));
		TestUtils.assertSearchText("ID", "android:id/title", "Preview");
		if (getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/image")).isDisplayed()) {
			testInfo.get().info("Captured KYC FORM is displayed");
		} else {
			testInfo.get().info("Captured KYC FORM is not displayed");
			testInfo.get().addScreenCaptureFromPath(TestUtils.addScreenshot());
		}
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/done_button")).click();
		Thread.sleep(500);

		TestUtils.scrollDown();

		//Save enrollment
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/nextButton")));
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/nextButton")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/message")));
		TestUtils.assertSearchText("ID", "android:id/message", "Captured record was saved successfully");
		getDriver().findElement(By.id("android:id/button1")).click();


	}

	@Parameters({ "dataEnv"})
	public static void individualForeignerFormNew(String dataEnv) throws Exception{
		WebDriverWait wait = new WebDriverWait(getDriver(), 30);
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
		String nin = (String) envs.get("nin");

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Personal Details']")));
		Thread.sleep(2000);

		// personal details
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/typeOfRegSpinner")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//android.widget.CheckedTextView[@index='0']")));
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@index='0']")).click();

		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/surNameTXT")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/surNameTXT")).sendKeys(surname);

		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/firstNameTXT")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/firstNameTXT")).sendKeys(firstname);

		TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/middleNameTXT");

		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/middleNameTXT")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/middleNameTXT")).sendKeys(middlename);

		TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/momsMaidenNameTXT");

		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/momsMaidenNameTXT")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/momsMaidenNameTXT")).sendKeys(maiden_name);

		TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/maleRadioButton");

		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/maleRadioButton")).click();

		TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/selectDateButton");

		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/selectDateButton")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/date_picker_header_year")));
		getDriver().findElement(By.id("android:id/date_picker_header_year")).click();

		TestUtils.scrollUntilElementIsVisible("XPATH", "//android.widget.TextView[@text='2000']");

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='2000']")));
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='2000']")).click();
		getDriver().findElement(By.id("android:id/button1")).click();

		TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/alternatePhone");

		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/alternatePhone")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/alternatePhone")).sendKeys(alt_phone_number);

		TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/alternateEmail");

		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/alternateEmail")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/alternateEmail")).sendKeys(email);

		TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/spinnerOccupation");

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/spinnerOccupation")));
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/spinnerOccupation")).click();

		TestUtils.scrollUntilElementIsVisible("XPATH", "//android.widget.TextView[@text='Select Item']");
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Select Item']")));

		TestUtils.scrollUntilElementIsVisible("XPATH", "//android.widget.TextView[@text='" + occupation + "']");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='" + occupation + "']")).click();
		Thread.sleep(500);

		// Social Media
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/socialMediaUsername")));
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/socialMediaUsername")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/socialMediaUsername")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/socialMediaUsername")).sendKeys(social_media_username);
		Thread.sleep(500);

		// Next button
		TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/btnContinueReg");
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/btnContinueReg")).click();
		Thread.sleep(3000);
		//wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/countrySpinner")));

		try{
			TestUtils.assertSearchText("ID", "android:id/title", "Edit Reason");
			getDriver().findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/androidx.recyclerview.widget.RecyclerView/android.view.ViewGroup[1]/android.widget.LinearLayout/android.widget.LinearLayout")).click();
			Thread.sleep(1000);
			getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Descriptions']")).click();

			getDriver().findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/androidx.recyclerview.widget.RecyclerView/android.view.ViewGroup[2]/android.widget.LinearLayout/android.widget.LinearLayout")).click();
			Thread.sleep(1000);
			getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Descriptions']")).click();

			getDriver().findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/androidx.recyclerview.widget.RecyclerView/android.view.ViewGroup[3]/android.widget.LinearLayout/android.widget.LinearLayout")).click();
			Thread.sleep(1000);
			getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Descriptions']")).click();

			getDriver().findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/androidx.recyclerview.widget.RecyclerView/android.view.ViewGroup[4]/android.widget.LinearLayout/android.widget.LinearLayout")).click();
			Thread.sleep(1000);
			getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Descriptions']")).click();

			TestUtils.scrollUntilElementIsVisible("XPATH", "//android.widget.CheckedTextView[@text='Descriptions']");

			getDriver().findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/androidx.recyclerview.widget.RecyclerView/android.view.ViewGroup[5]/android.widget.LinearLayout/android.widget.LinearLayout")).click();
			Thread.sleep(1000);
			getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Descriptions']")).click();

			getDriver().findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/androidx.recyclerview.widget.RecyclerView/android.view.ViewGroup[6]/android.widget.LinearLayout/android.widget.LinearLayout")).click();
			Thread.sleep(1000);
			getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Descriptions']")).click();
			// Go back
			getDriver().pressKeyCode(AndroidKeyCode.BACK);

			Thread.sleep(2000);
			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/btnContinueReg")).click();
		}catch(Exception e){

		}

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/countrySpinner")));
		Thread.sleep(500);

		try {
			// Nationality
			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/countrySpinner")).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='" + nationality + "']")));
			getDriver().findElement(By.xpath("//android.widget.TextView[@text='" + nationality + "']")).click();
		}catch (Exception e){
			//Nationality has been selected from the beginning of the registration proceed
		}

		//State of Origin
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/stateOfOriginSpinner")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='" + state + "']")).click();
		Thread.sleep(500);

		// LGA of Origin
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/lgaOfOriginSpinner")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='" + LGA + "']")).click();
		Thread.sleep(500);

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/ninEditText")));
		if(getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/ninEditText")).getText().equalsIgnoreCase("NIN")){
			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/ninEditText")).clear();
			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/ninEditText")).sendKeys(nin);
		}

		// State Residence
		TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/stateOfResidenceSpinner");
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/stateOfResidenceSpinner")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='" + state + "']")).click();
		Thread.sleep(500);


		TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/lgaOfResidenceSpinner");
		// LGA Residence
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/lgaOfResidenceSpinner")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='" + LGA + "']")).click();
		Thread.sleep(500);


		// Area of residence
		TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/areaOfResidenceSpinner");
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/areaOfResidenceSpinner")).click();
		Thread.sleep(500);
		TestUtils.scrollUntilElementIsVisible("XPATH", "//android.widget.TextView[@text='" + area + "']");
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='" + area + "']")).click();
		Thread.sleep(500);

		// House/Flat Number
		TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/houseNumberEditText");
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/houseNumberEditText")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/houseNumberEditText")).sendKeys(house_or_flat_no);
		Thread.sleep(500);

		// Street
		TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/streetEditText");
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/streetEditText")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/streetEditText")).sendKeys(street);
		Thread.sleep(500);

		// City
		TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/citySpinner");
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/citySpinner")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='"+city+"']")));
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='"+city+"']")).click();
		Thread.sleep(500);

		// Postal Code
		TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/postalCodeSpinner");
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/postalCodeSpinner")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='"+postalcode+"']")));
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='"+postalcode+"']")).click();
		Thread.sleep(500);

		// Next button
		TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/btnNext");
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/btnNext")).click();
		Thread.sleep(500);

		TestUtils.testTitle("Capture Identification Type");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Capture Data']")));
		TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='[Select Identification Type]']", "[Select Identification Type]");
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/identificationTypeSpinner")).click();
		Thread.sleep(500);

		// Capture ID CARD
		TestUtils.scrollUntilElementIsVisible("XPATH", "//android.widget.CheckedTextView[@text='Passport']");
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Passport']")).click();
		Thread.sleep(500);
		TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/captureIdButton");
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/captureIdButton")).click();
		Thread.sleep(1500);
		TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/captureButton");
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/captureButton")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/ok")).click();
		Thread.sleep(500);

		// Document Number
		TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/documentNumber");
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/documentNumber")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/documentNumber")).sendKeys(documentNumber);
		Thread.sleep(500);

		// Document Expiry Date
		TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/selectDateButton");
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/selectDateButton")).click();
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
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='2022']")));
			getDriver().findElement(By.xpath("//android.widget.TextView[@text='2022']")).click();
			getDriver().findElement(By.id("android:id/button1")).click();
		}

		// View Captured ID
		TestUtils.testTitle("Preview captured ID");
		TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/viewCaptureIdButton");
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/viewCaptureIdButton")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/title")));
		TestUtils.assertSearchText("ID", "android:id/title", "Preview");
		if (getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/image")).isDisplayed()) {
			testInfo.get().info("Captured ID is displayed");
		} else {
			testInfo.get().info("Captured ID is not displayed");
			testInfo.get().addScreenCaptureFromPath(TestUtils.addScreenshot());
		}
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/done_button")).click();
		Thread.sleep(500);

		//COO Form
		TestUtils.testTitle("Upload COO Form");
		try{
			TestUtils.scrollDown();
			// Push File
			File pic = new File(System.getProperty("user.dir") + "/files/idCard.jpg");

			getDriver().pushFile("/storage/emulated/0/picture.jpg", pic);

			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/documentUploadSpinner")).click();
			Thread.sleep(500);
			TestUtils.assertSearchText("XPATH", "//android.widget.CheckedTextView[@text='Affidavit']", "Affidavit");
			TestUtils.assertSearchText("XPATH", "//android.widget.CheckedTextView[@text='Letter of Authorization']", "Letter of Authorization");
			TestUtils.assertSearchText("XPATH", "//android.widget.CheckedTextView[@text='Newspaper Record']", "Newspaper Record");
			TestUtils.assertSearchText("XPATH", "//android.widget.CheckedTextView[@text='WAEC Result']", "WAEC Result");
			getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Affidavit']")).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/uploadDocumentBtn")));
			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/uploadDocumentBtn")).click();

			TestUtils.scrollDown();

			TestUtils.scrollUntilElementIsVisible("XPATH", "//android.widget.TextView[@text='picture.jpg']");


			getDriver().findElement(By.xpath("//android.widget.TextView[@text='picture.jpg']")).click();
			Thread.sleep(500);
			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/btn_remove_document")).click();
			Thread.sleep(500);
			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/uploadDocumentBtn")).click();
			Thread.sleep(1000);
			TestUtils.scrollUntilElementIsVisible("XPATH", "//android.widget.TextView[@text='picture.jpg']");
			getDriver().findElement(By.xpath("//android.widget.TextView[@text='picture.jpg']")).click();
			Thread.sleep(500);
		}catch (Exception e){

		}

		TestUtils.scrollDown();

		// Capture KYC FORM
		TestUtils.testTitle("Capture KYC FORM");
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/captureKycFormButton", "CAPTURE KYC FORM *");
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/captureKycFormButton")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/captureButton")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/ok")).click();
		Thread.sleep(500);

		// Preview
		TestUtils.testTitle("Preview KYC FORM");
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/viewKycFormButton")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/title")));
		TestUtils.assertSearchText("ID", "android:id/title", "Preview");
		if (getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/image")).isDisplayed()) {
			testInfo.get().info("Captured KYC FORM is displayed");
		} else {
			testInfo.get().info("Captured KYC FORM is not displayed");
			testInfo.get().addScreenCaptureFromPath(TestUtils.addScreenshot());
		}
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/done_button")).click();
		Thread.sleep(500);

		TestUtils.scrollDown();

		//Save enrollment
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/nextButton")));
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/nextButton")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/message")));
		TestUtils.assertSearchText("ID", "android:id/message", "Captured record was saved successfully");
		getDriver().findElement(By.id("android:id/button1")).click();


	}

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
		String nin = (String) envs2.get("nin");

		// Proceed with registration without supplying all mandatory fields
		TestUtils.testTitle("Proceed with registration without supplying all mandatory fields and fill form");
		TestUtils.scrollUp();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Personal Details']")));
		TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Personal Details']", "Personal Details");
		Thread.sleep(2000);

		// Company details
		wait.until(ExpectedConditions.visibilityOfElementLocated((By.id("com.sf.biocapture.activity" + Id + ":id/typeOfRegSpinner"))));
		Thread.sleep(1000);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/typeOfRegSpinner")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.CheckedTextView[@text='Company']")));
		Thread.sleep(1000);
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Company']")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated((By.id("com.sf.biocapture.activity" + Id + ":id/company_details_title"))));
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/company_details_title", "Company Details");
		Thread.sleep(500);

		// Name/ Description
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/company_ok")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated((By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle"))));
		TestUtils.assertSearchText("ID", "android:id/message", "Name/Description\n" +
				"Empty field");
		getDriver().findElement(By.id("android:id/button1")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/company_name_descrptn")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/company_name_descrptn")).sendKeys(company_description);
		Thread.sleep(500);

		// Registration Number
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/company_ok")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated((By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle"))));
		TestUtils.assertSearchText("ID", "android:id/message", "Company Registration Number\n" + "Empty field");
		getDriver().findElement(By.id("android:id/button1")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/company_regno")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/company_regno")).sendKeys(company_regno);
		Thread.sleep(500);

		// House/ Flat Number
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/company_ok")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated((By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle"))));
		TestUtils.assertSearchText("ID", "android:id/message", "House/Flat Number\n" +
				"Empty field");
		getDriver().findElement(By.id("android:id/button1")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/house_or_flat_no")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/house_or_flat_no")).sendKeys(house_or_flat_no);
		Thread.sleep(500);

		// Street
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/company_ok")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated((By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle"))));
		TestUtils.assertSearchText("ID", "android:id/message", "Street\n" +
				"Empty field");
		getDriver().findElement(By.id("android:id/button1")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/street")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/street")).sendKeys(company_street);
		Thread.sleep(500);


		TestUtils.scrollDown();

		// City
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/company_ok")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated((By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle"))));
		TestUtils.assertSearchText("ID", "android:id/message", "City\n" +
				"Empty field");
		getDriver().findElement(By.id("android:id/button1")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/city")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/city")).sendKeys(company_city);
		Thread.sleep(500);

		// Company Address State
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/company_ok")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated((By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle"))));
		TestUtils.assertSearchText("ID", "android:id/message", "Please select State");
		getDriver().findElement(By.id("android:id/button1")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/company_state_address")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='" + company_state_address + "']")).click();
		Thread.sleep(500);

		// Company Address LGA
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/company_ok")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated((By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle"))));
		TestUtils.assertSearchText("ID", "android:id/message", "Please select LGA");
		getDriver().findElement(By.id("android:id/button1")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/company_lga_address")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='" + company_lga_address + "']")).click();
		Thread.sleep(500);

		TestUtils.scrollDown();
		// Postal code
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/company_postalcode")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/company_postalcode")).sendKeys(company_postalcode);
		Thread.sleep(500);

		// Certificate of Incorporation
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/company_ok")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated((By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle"))));
		TestUtils.assertSearchText("ID", "android:id/message", "Certificate of Incorporation is compulsory");
		getDriver().findElement(By.id("android:id/button1")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/document_type_spinner")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='Certificate of Incorporation *']")).click();
		Thread.sleep(500);

		// Push File
		File pic = new File(System.getProperty("user.dir") + "/files/idCard.jpg");
		getDriver().pushFile("/storage/emulated/0/picture.jpg", pic);

		TestUtils.scrollDown();

		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/btn_load_document")).click();
		Thread.sleep(500);
		TestUtils.scrollUntilElementIsVisible("XPATH", "//android.widget.TextView[@text='picture.jpg']");
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='picture.jpg']")).click();
		Thread.sleep(500);
		TestUtils.scrollDown();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/btn_remove_document")).click();
		Thread.sleep(500);
		TestUtils.scrollDown();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/btn_load_document")).click();
		Thread.sleep(1000);
		TestUtils.scrollUntilElementIsVisible("XPATH", "//android.widget.TextView[@text='picture.jpg']");
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='picture.jpg']")).click();
		Thread.sleep(500);

		// contact person form
		TestUtils.scrollDown();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/document_type_spinner")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='Contact Person Form *']")).click();
		Thread.sleep(500);
		TestUtils.scrollDown();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/btn_remove_document")).click();
		Thread.sleep(500);
		TestUtils.scrollDown();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/btn_load_document")).click();
		Thread.sleep(1000);
		TestUtils.scrollUntilElementIsVisible("XPATH", "//android.widget.TextView[@text='picture.jpg']");
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='picture.jpg']")).click();
		Thread.sleep(500);

		// save
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/company_ok")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Personal Details']")));
		Thread.sleep(500);

		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/surNameTXT")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/surNameTXT")).sendKeys(surname);

		TestUtils.scrollDown();

		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/firstNameTXT")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/firstNameTXT")).sendKeys(firstname);



		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/middleNameTXT")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/middleNameTXT")).sendKeys(middlename);

		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/momsMaidenNameTXT")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/momsMaidenNameTXT")).sendKeys(maiden_name);

		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/maleRadioButton")).click();

		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/selectDateButton")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/date_picker_header_year")));
		getDriver().findElement(By.id("android:id/date_picker_header_year")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='2000']")));
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='2000']")).click();
		getDriver().findElement(By.id("android:id/button1")).click();

		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/alternatePhone")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/alternatePhone")).sendKeys(alt_phone_number);

		TestUtils.scrollDown();

		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/alternateEmail")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/alternateEmail")).sendKeys(email);

		TestUtils.scrollDown();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/spinnerOccupation")));
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/spinnerOccupation")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Select Item']")));
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='" + occupation + "']")).click();
		Thread.sleep(500);
		TestUtils.scrollDown();


		// Social Media
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/socialMediaUsername")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/socialMediaUsername")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/socialMediaUsername")).sendKeys(social_media_username);
		Thread.sleep(500);


		// Next button
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/btnContinueReg")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/countrySpinner")));
		Thread.sleep(500);

		// Nationality
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='" + nationality + "']")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='" + nationality + "']")).click();
		Thread.sleep(500);

		//State of Origin
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/stateOfOriginSpinner")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='" + state + "']")).click();
		Thread.sleep(500);

		// LGA of Origin
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/lgaOfOriginSpinner")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='" + LGA + "']")).click();
		Thread.sleep(500);

		TestUtils.scrollDown();

		if (getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/ninEditText")).getText().equalsIgnoreCase("NIN")) {
			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/ninEditText")).clear();
			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/ninEditText")).sendKeys(nin);
		}

		//  State Residence
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/stateOfResidenceSpinner")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='" + states_residence + "']")).click();
		Thread.sleep(500);

		// LGA Residence
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/lgaOfResidenceSpinner")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='" + lga_residence + "']")).click();
		Thread.sleep(500);

		// Area of residence
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/areaOfResidenceSpinner")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='" + area + "']")).click();
		Thread.sleep(500);

		// House/ Flat Number
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/houseNumberEditText")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/houseNumberEditText")).sendKeys(house_or_flat_no);
		Thread.sleep(500);

		TestUtils.scrollDown();

		// Street
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/streetEditText")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/streetEditText")).sendKeys(street);
		Thread.sleep(500);


		// City
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/citySpinner")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='" + city + "']")));
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='" + city + "']")).click();
		Thread.sleep(500);

		// Postal Code
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/postalCodeSpinner")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='" + postalcode + "']")));
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='" + postalcode + "']")).click();
		Thread.sleep(500);

		// Next button
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/btnNext")).click();
		Thread.sleep(500);

		TestUtils.testTitle("Capture Identification Type");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Capture Data']")));
		TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='[Select Identification Type]']", "[Select Identification Type]");
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/identificationTypeSpinner")).click();
		Thread.sleep(500);

		// Capture ID CARD
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Passport']")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/captureIdButton")).click();
		Thread.sleep(1500);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/captureButton")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/ok")).click();
		Thread.sleep(500);

		// Document Number
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/documentNumber")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/documentNumber")).sendKeys(documentNumber);
		Thread.sleep(500);

		// Document Expiry Date
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/selectDateButton")).click();
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
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='2022']")));
			getDriver().findElement(By.xpath("//android.widget.TextView[@text='2022']")).click();
			getDriver().findElement(By.id("android:id/button1")).click();
		}

		// View Captured ID
		TestUtils.testTitle("Preview captured ID");
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/viewCaptureIdButton")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/title")));
		TestUtils.assertSearchText("ID", "android:id/title", "Preview");
		if (getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/image")).isDisplayed()) {
			testInfo.get().info("Captured ID is displayed");
		} else {
			testInfo.get().info("Captured ID is not displayed");
			testInfo.get().addScreenCaptureFromPath(TestUtils.addScreenshot());
		}
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/done_button")).click();
		Thread.sleep(500);

		//COO Form
		TestUtils.testTitle("Upload COO Form");
		try{
			TestUtils.scrollDown();
			// Push File
			File pic2 = new File(System.getProperty("user.dir") + "/files/idCard.jpg");
			getDriver().pushFile("/storage/emulated/0/picture.jpg", pic2);

			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/documentUploadSpinner")).click();
			Thread.sleep(500);
			TestUtils.assertSearchText("XPATH", "//android.widget.CheckedTextView[@text='Affidavit']", "Affidavit");
			TestUtils.assertSearchText("XPATH", "//android.widget.CheckedTextView[@text='Letter of Authorization']", "Letter of Authorization");
			TestUtils.assertSearchText("XPATH", "//android.widget.CheckedTextView[@text='Newspaper Record']", "Newspaper Record");
			TestUtils.assertSearchText("XPATH", "//android.widget.CheckedTextView[@text='WAEC Result']", "WAEC Result");
			getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Affidavit']")).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/uploadDocumentBtn")));
			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/uploadDocumentBtn")).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='picture.jpg']")));

			TestUtils.scrollUntilElementIsVisible("XPATH", "//android.widget.TextView[@text='picture.jpg']");
			getDriver().findElement(By.xpath("//android.widget.TextView[@text='picture.jpg']")).click();
			Thread.sleep(500);
			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/btn_remove_document")).click();
			Thread.sleep(500);
			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/uploadDocumentBtn")).click();
			Thread.sleep(1000);
			TestUtils.scrollDown();
			getDriver().findElement(By.xpath("//android.widget.TextView[@text='picture.jpg']")).click();
			Thread.sleep(500);
		}catch (Exception e){

		}

		TestUtils.scrollDown();


		// Capture KYC FORM
		TestUtils.testTitle("Capture KYC FORM");
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/captureKycFormButton", "CAPTURE KYC FORM *");
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/captureKycFormButton")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/captureButton")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/ok")).click();
		Thread.sleep(500);

		// Preview
		TestUtils.testTitle("Preview KYC FORM");
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/viewKycFormButton")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/title")));
		TestUtils.assertSearchText("ID", "android:id/title", "Preview");
		if (getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/image")).isDisplayed()) {
			testInfo.get().info("Captured KYC FORM is displayed");
		} else {
			testInfo.get().info("Captured KYC FORM is not displayed");
			testInfo.get().addScreenCaptureFromPath(TestUtils.addScreenshot());
		}
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/done_button")).click();
		Thread.sleep(500);

		TestUtils.scrollDown();
		// Face Capture
		TestUtils.testTitle("Face Capture");
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/faceCaptureButton", "FACE CAPTURE *");
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/faceCaptureButton")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/captureButton")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/message", "Subscriber's face was successfully captured");
		getDriver().findElement(By.id("android:id/button1")).click();
		Thread.sleep(500);

		//Click next button
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/nextButton")).click();

		//Fingerprint capture

		//Submit without overriding fingerprint
		TestUtils.testTitle("Save Enrollment without overriding fingerprint");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/btn_multi_capture")));
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/btn_multi_capture", "MULTI CAPTURE");
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/fp_save_enrolment")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/message", "No finger was captured");
		getDriver().findElement(By.id("android:id/button1")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/fp_save_enrolment")));


		//Override left hand
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/btn_override_left")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/message")));
		TestUtils.assertSearchText("ID", "android:id/message", "Are you sure? Note that you have to provide a reason");
		getDriver().findElement(By.id("android:id/button1")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/captureButton")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/ok")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Ageing']")));
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='Ageing']")).click();

		//Submit without overriding right hand
		TestUtils.testTitle("Save Enrollment without capturing Right Hand");
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/fp_save_enrolment")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/message", "RIGHT HAND wasn't overridden, and all selected RIGHT HAND fingers were not captured.");
		getDriver().findElement(By.id("android:id/button1")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/fp_save_enrolment")));

		//Override right hand
		TestUtils.scrollDown();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/btn_override_right")));
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/btn_override_right")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/message")));
		TestUtils.assertSearchText("ID", "android:id/message", "Are you sure? Note that you have to provide a reason");
		getDriver().findElement(By.id("android:id/button1")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/captureButton")).click();
		Thread.sleep(1000);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/ok")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Ageing']")));
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='Ageing']")).click();

		//Save enrollment
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/fp_save_enrolment")));
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/fp_save_enrolment")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/message")));
		TestUtils.assertSearchText("ID", "android:id/message", "Captured record was saved successfully");
		getDriver().findElement(By.id("android:id/button1")).click();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/message")));
		TestUtils.assertSearchText("ID", "android:id/message", "Do you wish to Top Up the registered subscriber ?");
		getDriver().findElement(By.id("android:id/button3")).click();
	}
}
