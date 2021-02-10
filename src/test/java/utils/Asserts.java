package utils;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class Asserts extends TestBase {

	public static void AssertIndividualForm() throws Exception {
		WebDriverWait wait = new WebDriverWait(getDriver(), 60);
		String assertDetails = "Assert individual form of registered subscriber";
		Markup ad = MarkupHelper.createLabel(assertDetails, ExtentColor.GREEN);
		testInfo.get().info(ad);

		String typeOfRegistration = getDriver().findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/"
				+ "android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/"
				+ "android.widget.FrameLayout/android.widget.FrameLayout/android.view.View/android.widget.FrameLayout/android.widget.LinearLayout/"
				+ "android.widget.FrameLayout/android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.Spinner/android.widget.TextView")).getText();
		String surName = getDriver().findElement(By.id("com.sf.biocapture.activity:id/surNameTXT")).getText();
		String firstName = getDriver().findElement(By.id("com.sf.biocapture.activity:id/firstNameTXT")).getText();
		String middleName = getDriver().findElement(By.id("com.sf.biocapture.activity:id/middleNameTXT")).getText();
		String mothersMaidenName = getDriver().findElement(By.id("com.sf.biocapture.activity:id/momsMaidenNameTXT"))
				.getText();
		String sex;
		if (getDriver().findElement(By.id("com.sf.biocapture.activity:id/maleRadioButton")).isSelected()) {
			sex = "Male";
		} else {
			sex = "Female";
		}
		String dateOfBirth = getDriver().findElement(By.id("com.sf.biocapture.activity:id/dateOfBirth")).getText();
		String altPhoneNumber = getDriver().findElement(By.id("com.sf.biocapture.activity:id/alternatePhone"))
				.getText();
		String email = getDriver().findElement(By.id("com.sf.biocapture.activity:id/alternateEmail")).getText();
		TestUtils.scrollDown();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/spinnerOccupation")));
		Thread.sleep(500);
		String occupation = getDriver().findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/"
				+ "android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.FrameLayout/android.widget.FrameLayout/"
				+ "android.view.View/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/"
				+ "android.widget.LinearLayout[9]/android.widget.Spinner/android.widget.CheckedTextView")).getText();

		String empty = "";
		Map<String, String> fields = new HashMap<>();
		fields.put("Type of registration", typeOfRegistration);
		fields.put("Surname", surName);
		fields.put("First Name", firstName);
		fields.put("Middle name", middleName);
		fields.put("Mother's maiden name", mothersMaidenName);
		fields.put("Sex", sex);
		fields.put("Date of Birth", dateOfBirth);
		fields.put("Alternate phone Number", altPhoneNumber);
		fields.put("Email", email);
		fields.put("Occupation", occupation);

		for (Map.Entry<String, String> entry : fields.entrySet()) {
			try {
				Assert.assertNotEquals(entry.getValue(), empty);
				Assert.assertNotEquals(entry.getValue(), null);
				testInfo.get().log(Status.INFO, "<b>" +  entry.getKey() + "</b> : " + entry.getValue());
			} catch (Error e) {
				testInfo.get().error("<b>" + entry.getKey() + " </b> : " + entry.getValue());
			}

		}
	}

	public static void AssertAddresstDetails() throws Exception {
		WebDriverWait wait = new WebDriverWait(getDriver(), 60);
		Map<String, String> fields = new HashMap<>();
		String assertDetails = "Asserting address Details of registered subscriber";
		Markup ad = MarkupHelper.createLabel(assertDetails, ExtentColor.GREEN);
		testInfo.get().info(ad);
		String countryOfOrigin = getDriver().findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/"
				+ "android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.FrameLayout/android.widget.FrameLayout/"
				+ "android.view.View/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.Spinner[1]/"
				+ "android.widget.CheckedTextView")).getText();
		String stateOfOrigin = getDriver().findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/"
				+ "android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.FrameLayout/android.widget.FrameLayout/"
				+ "android.view.View/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.LinearLayout/"
				+ "android.widget.Spinner[1]/android.widget.CheckedTextView")).getText();
		if (stateOfOrigin.equals("[Select State]*")) {
			fields.put("State of origin", stateOfOrigin);
		}
		//Assert.assertNotEquals(stateOfOrigin, "[Select State]*");
		String lgaOfOrigin = getDriver().findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/"
				+ "android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.FrameLayout/android.widget.FrameLayout/"
				+ "android.view.View/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.LinearLayout/"
				+ "android.widget.Spinner[2]/android.widget.CheckedTextView")).getText();
		if (lgaOfOrigin.equals("[Select LGA]*")) {
			fields.put("LGA of Origin", lgaOfOrigin);
		}
		//Assert.assertNotEquals(lgaOfOrigin, "[Select LGA]*");
		String stateOfResidence = getDriver().findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/"
				+ "android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.FrameLayout/android.widget.FrameLayout/"
				+ "android.view.View/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.Spinner[2]/"
				+ "android.widget.CheckedTextView")).getText();
		if (stateOfResidence.equals("[Select State]*")) {
			fields.put("State of Residence", stateOfResidence);
		}
		//Assert.assertNotEquals(stateOfResidence, "[Select State]*");
		String lgaOfResidence = getDriver().findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/"
				+ "android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.FrameLayout/android.widget.FrameLayout/"
				+ "android.view.View/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.Spinner[3]/"
				+ "android.widget.CheckedTextView")).getText();
		if (lgaOfResidence.equals("[Select State]*")) {
			fields.put("LGA of Residence", lgaOfResidence);
		}
		//Assert.assertNotEquals(lgaOfResidence, "[Select LGA]*");
		String areaOfResidence = getDriver().findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/"
				+ "android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.FrameLayout/android.widget.FrameLayout/"
				+ "android.view.View/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.Spinner[4]/"
				+ "android.widget.CheckedTextView")).getText();
		if (areaOfResidence.equals("[Select Area]*")) {
			fields.put("Area of Residence", areaOfResidence);
		}
		Thread.sleep(500);
		TestUtils.scrollDown();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/houseNumberEditText")));
		Thread.sleep(500);
		String houseNum = getDriver().findElement(By.id("com.sf.biocapture.activity:id/houseNumberEditText")).getText();
		String street = getDriver().findElement(By.id("com.sf.biocapture.activity:id/streetEditText")).getText();
		String city = getDriver().findElement(By.id("com.sf.biocapture.activity:id/cityEditText")).getText();
		String postalCode = getDriver().findElement(By.id("com.sf.biocapture.activity:id/postalCodeTXT")).getText();

		String empty = "";

		fields.put("Country of Origin", countryOfOrigin);
		fields.put("State of origin", stateOfOrigin);
		fields.put("LGA of Origin", lgaOfOrigin);
		fields.put("State of Residence", stateOfResidence);
		fields.put("LGA of Residence", lgaOfResidence);
		fields.put("Area of Residence", areaOfResidence);
		fields.put("House number", houseNum);
		fields.put("Street", street);
		fields.put("City", city);
		fields.put("Postal code", postalCode);

		for (Map.Entry<String, String> entry : fields.entrySet()) {
			try {
				Assert.assertNotEquals(entry.getValue(), empty);
				Assert.assertNotEquals(entry.getValue(), null);
				testInfo.get().log(Status.INFO, "<b>" +  entry.getKey() + "</b> : " + entry.getValue());
			} catch (Error e) {
				testInfo.get().error("<b>" + entry.getKey() + " </b> : " + entry.getValue());
			}

		}
	}

	public static void AssertPassportDetails() throws Exception {

		// Passport Details
		String issuingCountry = getDriver().findElement(By.id("com.sf.biocapture.activity:id/passport_issuing_country"))
				.getText();
		String passportNumber = getDriver().findElement(By.id("com.sf.biocapture.activity:id/passport_number"))
				.getText();
		String expiryDate = getDriver().findElement(By.id("")).getText();

		String NA = "";

		String[] toList = { "Issuing Country:" + issuingCountry, "Passport Number:" + passportNumber,
				"Expiry Date:" + expiryDate };
		for (String field : toList) {
			String name = "";
			String val = NA;
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
	}

	public static void AssertCompanyDetails() throws Exception {

		String assertDetails = "Asserting Company Details of registered subscriber";
		Markup ad = MarkupHelper.createLabel(assertDetails, ExtentColor.GREEN);
		testInfo.get().info(ad);

		// Company Details
		String companyName = getDriver().findElement(By.id("com.sf.biocapture.activity:id/company_name_descrptn"))
				.getText();
		String registrationNumber = getDriver().findElement(By.id("com.sf.biocapture.activity:id/company_regno"))
				.getText();
		String houseNumber = getDriver().findElement(By.id("com.sf.biocapture.activity:id/house_or_flat_no")).getText();
		String companyAddressStreet = getDriver().findElement(By.id("com.sf.biocapture.activity:id/street")).getText();
		String companyAddressCity = getDriver().findElement(By.id("com.sf.biocapture.activity:id/city")).getText();
		String companyState = getDriver().findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.ScrollView/android.widget.TableLayout/android.widget.Spinner[1]/android.widget.TextView"))
				.getText();
		String companyLga = getDriver().findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.ScrollView/android.widget.TableLayout/android.widget.Spinner[2]/android.widget.TextView"))
				.getText();
		String companyPostalCode = getDriver().findElement(By.id("com.sf.biocapture.activity:id/company_postalcode"))
				.getText();

		String empty = "";
		Map<String, String> fields = new HashMap<>();
		fields.put("Name /Description", companyName);
		fields.put("Registration Number", registrationNumber);
		fields.put("House No.", houseNumber);
		fields.put("Street", companyAddressStreet);
		fields.put("City", companyAddressCity);
		fields.put("Company Address State", companyState);
		fields.put("Company Address LGA", companyLga);
		fields.put("Postal Code", companyPostalCode);

		for (Map.Entry<String, String> entry : fields.entrySet()) {
			try {
				Assert.assertNotEquals(entry.getValue(), empty);
				Assert.assertNotEquals(entry.getValue(), null);
				testInfo.get().log(Status.INFO, "<b>" +  entry.getKey() + "</b> : " + entry.getValue());
			} catch (Error e) {
				testInfo.get().error("<b>" + entry.getKey() + " </b> : " + entry.getValue());
			}

		}
	}

	public static void AssertReportSummary() throws Exception {

		TestUtils.testTitle("Report Summary of Registrations");
		String totalRegistrations = getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/reg_subscribers"))
				.getText();
		String totalSyncSent = getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/total_sync_sent"))
				.getText();
		String totalSyncConfirmed = getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/sync_confirmed"))
				.getText();
		String totalSyncPending = getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/total_pending"))
				.getText();
		String totalRejected = getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/total_rejected")).getText();
		String totalActivated = getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/total_activated"))
				.getText();
		String totalReactivated = getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/total_reactivated"))
				.getText();
		String totalSimSwap = getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/total_swaps")).getText();


		//TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity." + Id + ":id/reg_sims");
		Thread.sleep(300);

		/*String registeredSIMs = getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/reg_sims")).getText();
		String confirmedSIMs = getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/sims_confirmed")).getText();
		String duplicateSIMs = getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/duplicate_sims")).getText();*/

		String NA = "";

		String[] toList = {"Total Registrations: " + totalRegistrations, "Total Sync Sent: " + totalSyncSent,
				"Total Sync Confirmed: " + totalSyncConfirmed, "Total Sync Pending:" + totalSyncPending,
				"Total Rejected: " + totalRejected, "Total Activated: " + totalActivated,
				"Total Reactivated: " + totalReactivated, "Total SIM Swap: " + totalSimSwap
				//"Registered SIMS: " + registeredSIMs, "Confirmed SIMS: " + confirmedSIMs,
				//"Duplicate SIMS: " + duplicateSIMs };
		};
		for (String field : toList) {
			String name = "";
			String val = NA;
			try {
				String[] fields = field.split(":");
				name = fields[0];
				val = fields[1];
				Assert.assertNotEquals(val, NA);
				testInfo.get().log(Status.INFO, "<b>" + name + " : </b>" + val);
			} catch (Error e) {
				testInfo.get().error("<b>" + name + " : </b>" + val);
			}
		}

	}

	public static void AssertSubscriberInfo230() throws Exception {

		String assertDetails = "Asserting returned Subscriber's Details";
		Markup ad = MarkupHelper.createLabel(assertDetails, ExtentColor.BLUE);
		testInfo.get().info(ad);
		String msisdn = getDriver().findElement(By.id("com.sf.biocapture.activity:id/msisdn")).getText();
		String simSerial = getDriver().findElement(By.id("com.sf.biocapture.activity:id/sim_serial")).getText();
		String activationStatus = getDriver().findElement(By.id("com.sf.biocapture.activity:id/activation_status"))
				.getText();
		String failureReason = getDriver().findElement(By.id("com.sf.biocapture.activity:id/failure_reason")).getText();
		String Agent = getDriver().findElement(By.id("com.sf.biocapture.activity:id/agent")).getText();
		String DOB = getDriver().findElement(By.id("com.sf.biocapture.activity:id/reg_date")).getText();

		String NA = "";

		String[] toList = { "MSISDN: " + msisdn, "SIM Serial: " + simSerial, "Activation Status: " + activationStatus,
				"Failure Reason: " + failureReason, "Agent: " + Agent, "Date of Birth: " + DOB };
		for (String field : toList) {
			String name = "";
			String val = NA;
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
	}

	public static void assertBasicInfoAddReg230() throws Exception {
		WebDriverWait wait = new WebDriverWait(getDriver(), 60);

		TestUtils.testTitle("Assert returned Basic Info of Additional Registration");
		String surname = getDriver().findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.ListView/android.widget.LinearLayout[1]/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.TextView[2]")).getText();
		String firstName = getDriver().findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.ListView/android.widget.LinearLayout[2]/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.TextView[2]")).getText();
		String mothersMaidenName = getDriver().findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.ListView/android.widget.LinearLayout[3]/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.TextView[2]")).getText();
		String gender = getDriver().findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.ListView/android.widget.LinearLayout[4]/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.TextView[2]")).getText();
		String street = getDriver().findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.ListView/android.widget.LinearLayout[5]/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.TextView[2]")).getText();
		String city = getDriver().findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.ListView/android.widget.LinearLayout[6]/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.TextView[2]")).getText();
		String countryOfOrigin = getDriver().findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.ListView/android.widget.LinearLayout[7]/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.TextView[2]")).getText();
		String stateOfOrigin = getDriver().findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.ListView/android.widget.LinearLayout[8]/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.TextView[2]")).getText();
		TestUtils.scrollDown();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='LGA of Origin']")));
		Thread.sleep(500);
		String lgaOfOrigin = getDriver().findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.ListView/android.widget.LinearLayout[4]/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.TextView[2]")).getText();
		String areaOfResidence = getDriver().findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.ListView/android.widget.LinearLayout[5]/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.TextView[2]")).getText();
		String typeOfIdentification = getDriver().findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.ListView/android.widget.LinearLayout[6]/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.TextView[2]")).getText();
		String stateOfResidence = getDriver().findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.ListView/android.widget.LinearLayout[7]/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.TextView[2]")).getText();
		String lgaOfResidence = getDriver().findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.ListView/android.widget.LinearLayout[8]/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.TextView[2]")).getText();
		String lgaOfRegistration = getDriver().findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.ListView/android.widget.LinearLayout[9]/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.TextView[2]")).getText();
		String NA = "";

		String[] toList = { "Surname: " + surname, "First Name: " + firstName, "Mother's maiden name: " + mothersMaidenName,"Gender: " + gender,
				"Street: " + street, "City: " + city , "Country of Origin: " + countryOfOrigin, "State of Origin: " + stateOfOrigin,
				"LGA of Origin: " + lgaOfOrigin, "Area of residence: " + areaOfResidence,	"Type of identification: " + typeOfIdentification, "State of Residence: "
				+ stateOfResidence, "LGA of Residence: " + lgaOfResidence, "LGA of Registration: " + lgaOfRegistration,};
		for (String field : toList) {
			String name = "";
			String val = NA;
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
	}

	public static void assertTransactionIdRecords(WebElement webElement) throws Exception {

		String assertDetails = "Asserting returned Subscriber's Details";
		Markup ad = MarkupHelper.createLabel(assertDetails, ExtentColor.BLUE);
		testInfo.get().info(ad);
		String firstName = webElement.findElement(By.id("com.sf.biocapture.activity:id/first_name_value")).getText();
		String surName = webElement.findElement(By.id("com.sf.biocapture.activity:id/surname_value")).getText();
		String gender = webElement.findElement(By.id("com.sf.biocapture.activity:id/gender_value")).getText();
		String dateOfBirth = webElement.findElement(By.id("com.sf.biocapture.activity:id/transaction_id_date_of_birth_value")).getText();
		String dateOfReg = webElement.findElement(By.id("com.sf.biocapture.activity:id/date_of_registration_value")).getText();
		String transactionID = webElement.findElement(By.id("com.sf.biocapture.activity:id/transaction_id_value")).getText();

		String NA = "";

		String[] toList = { "First Name: " + firstName, "Surname: " + surName, "Gender: " + gender,
				"Date of Birth: " + dateOfBirth, "Date of Registration: " + dateOfReg, "Transaction ID: " + transactionID };
		for (String field : toList) {
			String name = "";
			String val = NA;
			try {
				String[] fields = field.split(":");
				name = fields[0];
				val = fields[1];
				Assert.assertNotEquals(val, NA);
				testInfo.get().log(Status.INFO, "<b>" +name + " : </b>" + val);
			} catch (Error e) {
				testInfo.get().error("<b>" + name + " : </b>" + val);
			}
		}
	}

	public static void assertAddedNumbers() throws Exception {

		String assertDetails = "Assert Valid Numbers";
		Markup ad = MarkupHelper.createLabel(assertDetails, ExtentColor.BLUE);
		testInfo.get().info(ad);
		String msisdn = getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/phone_no_view")).getText();
		String simSerial = getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/tv_sim_serial")).getText();

		String NA = "";

		String[] toList = { "Msisdn: " + msisdn, "Sim Serial: " + simSerial};
		for (String field : toList) {
			String name = "";
			String val = NA;
			try {
				String[] fields = field.split(":");
				name = fields[0];
				val = fields[1];
				Assert.assertNotEquals(val, NA);
				testInfo.get().log(Status.INFO, "<b>" +name + " : </b>" + val);
			} catch (Error e) {
				testInfo.get().error("<b>" + name + " : </b>" + val);
			}
		}
	}

	public static void assertSubscriberFullNameAddReg() throws Exception {

		String assertDetails = "Assert Returned Subscriber Full name after Number Validation";
		Markup ad = MarkupHelper.createLabel(assertDetails, ExtentColor.BLUE);
		testInfo.get().info(ad);
		String firstName = getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/tv_first_name")).getText();
		String Surname = getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/tv_surname")).getText();

		String NA = "";

		String[] toList = { "First Name: " + firstName, "Surname: " + Surname};
		for (String field : toList) {
			String name = "";
			String val = NA;
			try {
				String[] fields = field.split(":");
				name = fields[0];
				val = fields[1];
				Assert.assertNotEquals(val, NA);
				testInfo.get().log(Status.INFO, "<b>" +name + " : </b>" + val);
			} catch (Error e) {
				testInfo.get().error("<b>" + name + " : </b>" + val);
			}
		}
	}
}
