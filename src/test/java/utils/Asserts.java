package utils;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class Asserts extends TestBase {

	public static void AssertIndividualForm() throws InterruptedException {

		WebDriverWait wait = new WebDriverWait(getDriver(), 30);

		// Page 1
		String typeOfRegistration = getDriver().findElement(By.id("com.sf.biocapture.activity:id/reg_type")).getText();
		String surName = getDriver().findElement(By.id("com.sf.biocapture.activity:id/surnname")).getText();
		String firstName = getDriver().findElement(By.id("com.sf.biocapture.activity:id/firstname")).getText();
		String middleName = getDriver().findElement(By.id("com.sf.biocapture.activity:id/firstname")).getText();
		String mothersMaidenName = getDriver().findElement(By.id("com.sf.biocapture.activity:id/moms_maidenname"))
				.getText();
		String sex;
		if (getDriver().findElement(By.id("com.sf.biocapture.activity:id/male_radio_button")).isSelected()) {
			sex = "Male";
		} else {
			sex = "Female";
		}
		String dateOfBirth = getDriver().findElement(By.id("com.sf.biocapture.activity:id/date_of_birth")).getText();
		TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity:id/house_or_flat_no");
		String houseNo = getDriver().findElement(By.id("com.sf.biocapture.activity:id/house_or_flat_no")).getText();
		TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity:id/street");
		String street = getDriver().findElement(By.id("com.sf.biocapture.activity:id/street")).getText();
		TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity:id/city");
		String city = getDriver().findElement(By.id("com.sf.biocapture.activity:id/city")).getText();
		TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity:id/country");
		String countryOfOrigin = getDriver().findElement(By.id("com.sf.biocapture.activity:id/country")).getText();
		TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity:id/states");
		String stateOfOrigin = getDriver().findElement(By.id("com.sf.biocapture.activity:id/states")).getText();
		TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity:id/lga");
		String lgaOfOrigin = getDriver().findElement(By.id("com.sf.biocapture.activity:id/lga")).getText();
		TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity:id/btn_continue_reg");
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/btn_continue_reg")).click();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/page_title")));

		// page 2
		String identificationType = getDriver().findElement(By.id("com.sf.biocapture.activity:id/typeofid")).getText();
		TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity:id/email");
		String email = getDriver().findElement(By.id("com.sf.biocapture.activity:id/email")).getText();
		TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity:id/alt_phone_number");
		String alternatePhoneNumber = getDriver().findElement(By.id("com.sf.biocapture.activity:id/alt_phone_number"))
				.getText();
		TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity:id/postalcode");
		String postalCode = getDriver().findElement(By.id("com.sf.biocapture.activity:id/postalcode")).getText();
		TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity:id/occupation");
		String occupation = getDriver().findElement(By.id("com.sf.biocapture.activity:id/occupation")).getText();
		TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity:id/states_residence");
		String stateOfResidence = getDriver().findElement(By.id("com.sf.biocapture.activity:id/states_residence"))
				.getText();
		TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity:id/lga_residence");
		String lgaAreaOfResidence = getDriver().findElement(By.id("com.sf.biocapture.activity:id/lga_residence"))
				.getText();
		TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity:id/area");
		String areaOfResidence = getDriver().findElement(By.id("com.sf.biocapture.activity:id/area")).getText();
		TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity:id/lga_of_reg");
		String lgaOfRegistration = getDriver().findElement(By.id("com.sf.biocapture.activity:id/lga_of_reg")).getText();

		String NA = "No Data Found";

		String[] toList = { "Type of Registration:" + typeOfRegistration, "Surname:" + surName,
				"First Name:" + firstName, "Middle Name:" + middleName, "Mother's Maiden Name:" + mothersMaidenName,
				"Sex:" + sex, "Date of Birth:" + dateOfBirth, "House No:" + houseNo, "Street:" + street, "City:" + city,
				"Country of Origin:" + countryOfOrigin, "State of Origin:" + stateOfOrigin,
				"LGA of Origin:" + lgaOfOrigin, "Identification Type:" + identificationType, "Email Address:" + email,
				"Alternate Phone No:" + alternatePhoneNumber, "Postal Code:" + postalCode, "Occupation:" + occupation,
				"State of Residence:" + stateOfResidence, "LGA of Residence:" + lgaAreaOfResidence,
				"Area Of Residence" + areaOfResidence, "LGA of Registration:" + lgaOfRegistration };
		for (String field : toList) {
			String name = "";
			String val = NA;
			try {
				String[] fields = field.split(":");
				name = fields[0];
				try {
					val = fields[1];
				} catch (Exception e) {
					val = NA;
				}
				Assert.assertNotEquals(val, NA);
				testInfo.get().log(Status.INFO, name + " : " + val);
			} catch (Error e) {
				testInfo.get().error(name + " : " + val);
			}
		}
	}

	public static void AssertIndividualForm230() throws Exception {
		WebDriverWait wait = new WebDriverWait(getDriver(), 60);
		String assertDetails = "Asserting individual form of registered subscriber";
		Markup ad = MarkupHelper.createLabel(assertDetails, ExtentColor.BLUE);
		testInfo.get().info(ad);

		String typeOfRegistration = getDriver().findElement(By.id("com.sf.biocapture.activity:id/reg_type")).getText();
		String surName = getDriver().findElement(By.id("com.sf.biocapture.activity:id/surname")).getText();
		String firstName = getDriver().findElement(By.id("com.sf.biocapture.activity:id/firstname")).getText();
		String middleName = getDriver().findElement(By.id("com.sf.biocapture.activity:id/middlename")).getText();
		String mothersMaidenName = getDriver().findElement(By.id("com.sf.biocapture.activity:id/moms_maidenname"))
				.getText();
		String sex;
		if (getDriver().findElement(By.id("com.sf.biocapture.activity:id/male_radio_button")).isSelected()) {
			sex = "Male";
		} else {
			sex = "Female";
		}
		String dateOfBirth = getDriver().findElement(By.id("com.sf.biocapture.activity:id/date_of_birth")).getText();
		String altPhoneNumber = getDriver().findElement(By.id("com.sf.biocapture.activity:id/alternate_phone"))
				.getText();
		String email = getDriver().findElement(By.id("com.sf.biocapture.activity:id/alternate_email")).getText();
		TestUtils.scrollDown();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/occupation")));
		Thread.sleep(500);
		String occupation = getDriver().findElement(By.id("com.sf.biocapture.activity:id/occupation")).getText();

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
				testInfo.get().log(Status.INFO, entry.getKey() + " : " + entry.getValue());
			} catch (Error e) {
				testInfo.get().error(entry.getKey() + " : " + entry.getValue());
			}

		}
	}

	public static void AssertAddresstDetails230() throws Exception {
		WebDriverWait wait = new WebDriverWait(getDriver(), 60);

		String assertDetails = "Asserting address Details of registered subscriber";
		Markup ad = MarkupHelper.createLabel(assertDetails, ExtentColor.BLUE);
		testInfo.get().info(ad);
		String countryOfOrigin = getDriver().findElement(By.xpath(
				"/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.View/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.Spinner[1]/android.widget.CheckedTextView"))
				.getText();
		String stateOfOrigin = getDriver().findElement(By.xpath(
				"/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.View/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.LinearLayout/android.widget.Spinner[1]/android.widget.CheckedTextView"))
				.getText();
		Assert.assertNotEquals(stateOfOrigin, "[Select State]*");
		String lgaOfOrigin = getDriver().findElement(By.xpath(
				"/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.View/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.LinearLayout/android.widget.Spinner[2]/android.widget.TextView"))
				.getText();
		Assert.assertNotEquals(lgaOfOrigin, "[Select LGA]*");
		String stateOfResidence = getDriver().findElement(By.xpath(
				"/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.View/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.Spinner[2]/android.widget.CheckedTextView"))
				.getText();
		Assert.assertNotEquals(stateOfResidence, "[Select State]*");
		String lgaOfResidence = getDriver().findElement(By.xpath(
				"/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.View/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.Spinner[3]/android.widget.TextView"))
				.getText();
		Assert.assertNotEquals(lgaOfResidence, "[Select LGA]*");
		String areaOfResidence = getDriver().findElement(By.xpath(
				"/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.View/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.Spinner[4]/android.widget.CheckedTextView"))
				.getText();
		if (areaOfResidence.equals("[Select Area]*")) {
			testInfo.get().error("[Select Area]*");
		}
		Thread.sleep(500);
		TestUtils.scrollDown();
		wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/house_or_flat_no")));
		Thread.sleep(500);
		String houseNum = getDriver().findElement(By.id("com.sf.biocapture.activity:id/house_or_flat_no")).getText();
		String street = getDriver().findElement(By.id("com.sf.biocapture.activity:id/street")).getText();
		String city = getDriver().findElement(By.id("com.sf.biocapture.activity:id/city")).getText();
		String postalCode = getDriver().findElement(By.id("com.sf.biocapture.activity:id/postal_code")).getText();

		String empty = "";
		Map<String, String> fields = new HashMap<>();
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
				testInfo.get().log(Status.INFO, entry.getKey() + " : " + entry.getValue());
			} catch (Error e) {
				testInfo.get().error(entry.getKey() + " : " + entry.getValue());
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

		// Company Details
		String companyName = getDriver().findElement(By.id("com.sf.biocapture.activity:id/company_name_descrptn"))
				.getText();
		String registrationNumber = getDriver().findElement(By.id("com.sf.biocapture.activity:id/company_regno"))
				.getText();
		String houseNumber = getDriver().findElement(By.id("com.sf.biocapture.activity:id/house_or_flat_no")).getText();
		String companyAddressStreet = getDriver().findElement(By.id("com.sf.biocapture.activity:id/street")).getText();
		String companyAddressCity = getDriver().findElement(By.id("com.sf.biocapture.activity:id/city")).getText();
		String companyState = getDriver().findElement(By.id("com.sf.biocapture.activity:id/company_state_address"))
				.getText();
		String companyLga = getDriver().findElement(By.id("com.sf.biocapture.activity:id/company_lga_address"))
				.getText();
		String companyPostalCode = getDriver().findElement(By.id("com.sf.biocapture.activity:id/company_postalcode"))
				.getText();

		String NA = "";

		String[] toList = { "Name /Description:" + companyName, "Registration Number:" + registrationNumber,
				"House No.:" + houseNumber, "Street:" + companyAddressStreet, "City:" + companyAddressCity,
				"Company Address State:" + companyState, "Company Address LGA:" + companyLga,
				"Postal Code:" + companyPostalCode };
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

	public static void AssertReportSummary() throws Exception {

		String totalRegistrations = getDriver().findElement(By.id("com.sf.biocapture.activity:id/reg_subscribers"))
				.getText();
		String totalSyncSent = getDriver().findElement(By.id("com.sf.biocapture.activity:id/total_sync_sent"))
				.getText();
		String totalSyncConfirmed = getDriver().findElement(By.id("com.sf.biocapture.activity:id/sync_confirmed"))
				.getText();
		String totalSyncPending = getDriver().findElement(By.id("com.sf.biocapture.activity:id/total_pending"))
				.getText();
		String totalRejected = getDriver().findElement(By.id("com.sf.biocapture.activity:id/total_rejected")).getText();
		String totalActivated = getDriver().findElement(By.id("com.sf.biocapture.activity:id/total_activated"))
				.getText();
		String totalReactivated = getDriver().findElement(By.id("com.sf.biocapture.activity:id/total_reactivated"))
				.getText();
		String totalSimSwap = getDriver().findElement(By.id("com.sf.biocapture.activity:id/total_swaps")).getText();
		String registeredSIMs = getDriver().findElement(By.id("com.sf.biocapture.activity:id/reg_sims")).getText();
		String confirmedSIMs = getDriver().findElement(By.id("com.sf.biocapture.activity:id/sims_confirmed")).getText();
		String duplicateSIMs = getDriver().findElement(By.id("com.sf.biocapture.activity:id/duplicate_sims")).getText();

		String NA = "";

		String[] toList = { "Total Registrations: " + totalRegistrations, "Total Sync Sent: " + totalSyncSent,
				"Total Sync Confirmed: " + totalSyncConfirmed, "Total Sync Pending:" + totalSyncPending,
				"Total Rejected: " + totalRejected, "Total Activated: " + totalActivated,
				"Total Reactivated: " + totalReactivated, "Total SIM Swap: " + totalSimSwap,
				"Registered SIMS: " + registeredSIMs, "Confirmed SIMS: " + confirmedSIMs,
				"Duplicate SIMS: " + duplicateSIMs };
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
		
		String assertDetails = "Asserting returned Basic Info of Additional Registration";
		Markup ad = MarkupHelper.createLabel(assertDetails, ExtentColor.BLUE);
		testInfo.get().info(ad);
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
}
