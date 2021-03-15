package admin;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import demographics.Form;
import utils.Asserts;
import utils.TestBase;
import utils.TestUtils;
import java.io.FileReader;

public class NewRegistration extends TestBase {

	@Parameters({ "dataEnv"})
	@Test
	public void msisdnCategoryAndNDCValidationTest(String dataEnv) throws Exception {
		WebDriverWait wait = new WebDriverWait(getDriver(), 10);
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resource/" + dataEnv + "/data.conf.json"));
		JSONObject envs = (JSONObject) config.get("NewRegistration");
		
		String valid_msisdn = (String) envs.get("valid_msisdn");
		String valid_simSerial = (String) envs.get("valid_simSerial");
		String lga = (String) envs.get("lga");
		String valid_fixed_msisdn = (String) envs.get("valid_fixed_msisdn");
		String valid_fixed_serial = (String) envs.get("valid_fixed_serial");
		String invalidPrefixMsisdn = (String) envs.get("invalidPrefixMsisdn");
		String invalidPrefixSimSerial = (String) envs.get("invalidPrefixSimSerial");

		// Select LGA of Registration
		TestUtils.testTitle("Select LGA of Registration: " + lga);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/lga_of_reg")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/alertTitle", "LGA of Registration*");
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='" + lga + "']")).click();
		Thread.sleep(500);

		// Select New Registration 
		TestUtils.testTitle("Select New Registration");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/typeofreg")));
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/typeofreg")).click();
		Thread.sleep(500);
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/alertTitle", "Select Registration Type");
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='New Registration']")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/next_button")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/pageTitle")));
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/pageTitle", "New Registration");
		
		// Select Msisdn Category
		TestUtils.testTitle("Select Mobile Msisdn Category");
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnCategorySpinner")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Mobile']")).click();
		Thread.sleep(500);
		TestUtils.assertSearchText("XPATH", "//android.widget.CheckedTextView[@text='Mobile']", "Mobile");
		Thread.sleep(500);
		
		// Enter msisdn less than 11 digits
		TestUtils.testTitle("Proceed after supplying msisdn less than 11 digits: " + valid_fixed_msisdn + " and SIM Serial: " + valid_fixed_serial);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).sendKeys(valid_fixed_msisdn);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/simSerialField")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/simSerialField")).sendKeys(valid_fixed_serial);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/addMsisdnSimSerialButton")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/message", "The MSISDN has an unrecognized National Destination Code. "
				+ "Please ensure the MSISDN starts with any of the following NDCs: "
				+ "0701,0708,0802,0808,0812,0901,0902,0904,0907,0809,0817,0818,"
				+ "0908,0909,0705,0805,0807,0811,0815,0905,0915,0703,0706,0803,"
				+ "0806,0810,0813,0814,0816,0903,0906 and must be 11 digits");
		getDriver().findElement(By.id("android:id/button1")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")));
		Thread.sleep(500);
		
		// Select Msisdn Category
		TestUtils.testTitle("Select Fixed Msisdn Category");
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnCategorySpinner")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Fixed']")).click();
		Thread.sleep(500);
		TestUtils.assertSearchText("XPATH", "//android.widget.CheckedTextView[@text='Fixed']", "Fixed");
		Thread.sleep(500);
		
		// Enter msisdn more than 9 digits
		TestUtils.testTitle("Proceed after supplying msisdn more than 9 digits: " + valid_msisdn + " and SIM Serial: " + valid_simSerial);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).sendKeys(valid_msisdn);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/simSerialField")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/simSerialField")).sendKeys(valid_simSerial);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/addMsisdnSimSerialButton")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/message", "The MSISDN has an unrecognized National Destination Code. "
				+ "Please ensure the MSISDN starts with any of the following NDCs: "
				+ "0701,0708,0802,0808,0812,0901,0902,0904,0907,0809,0817,0818,0908,"
				+ "0909,0705,0805,0807,0811,0815,0905,0915,0703,0706,0803,0806,0810,"
				+ "0813,0814,0816,0903,0906 and must be 9 digits");
		getDriver().findElement(By.id("android:id/button1")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")));
		Thread.sleep(500);
		
		// Select Msisdn Category
		TestUtils.testTitle("Select Fixed Msisdn Category");
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnCategorySpinner")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Fixed']")).click();
		Thread.sleep(500);
		TestUtils.assertSearchText("XPATH", "//android.widget.CheckedTextView[@text='Fixed']", "Fixed");
		Thread.sleep(500);

		// Enter Msisdn with prefix not allowed by NDC
		TestUtils.testTitle("Proceed after supplying msisdn with prefix not allowed by NDC: " + invalidPrefixMsisdn	+ " and SIM Serial: " + invalidPrefixSimSerial);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).sendKeys(invalidPrefixMsisdn);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/simSerialField")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/simSerialField")).sendKeys(invalidPrefixSimSerial);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/addMsisdnSimSerialButton")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/message", "The MSISDN has an unrecognized National Destination Code. "
				+ "Please ensure the MSISDN starts with any of the following NDCs: "
				+ "0701,0708,0802,0808,0812,0901,0902,0904,0907,0809,0817,0818,0908,"
				+ "0909,0705,0805,0807,0811,0815,0905,0915,0703,0706,0803,0806,0810,"
				+ "0813,0814,0816,0903,0906 and must be 9 digits");
		getDriver().findElement(By.id("android:id/button1")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")));
		Thread.sleep(500);
		
		// Select Msisdn Category
		TestUtils.testTitle("Select Mobile Msisdn Category");
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnCategorySpinner")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Mobile']")).click();
		Thread.sleep(500);
		TestUtils.assertSearchText("XPATH", "//android.widget.CheckedTextView[@text='Mobile']", "Mobile");
		Thread.sleep(500);

		// Enter Msisdn that does not exist on MPS table and Siebel
		TestUtils.testTitle("Proceed after supplying msisdn that does not exist on MPS table and Siebel: " + invalidPrefixMsisdn + " and SIM Serial: " + invalidPrefixSimSerial);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).sendKeys(invalidPrefixMsisdn);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/simSerialField")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/simSerialField")).sendKeys(invalidPrefixSimSerial);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/addMsisdnSimSerialButton")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Confirming...']")));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/message")));
		TestUtils.assertSearchText("ID", "android:id/message", "The requested SIM Card does not exist, Please check it again");
		getDriver().findElement(By.id("android:id/button1")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")));
		Thread.sleep(500);
	}
	
	@Parameters({ "dataEnv"})
	@Test
	public void captureIndividualSimTest(String dataEnv) throws Exception {
		WebDriverWait wait = new WebDriverWait(getDriver(), 10);
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resource/" + dataEnv + "/data.conf.json"));
		JSONObject envs = (JSONObject) config.get("NewRegistration");
		
		String invalid_msisdn = (String) envs.get("invalid_msisdn");
		String valid_msisdn = (String) envs.get("valid_msisdn");
		String invalid_simSerial = (String) envs.get("invalid_simSerial");
		String valid_simSerial = (String) envs.get("valid_simSerial");
		String valid_msisdn2 = (String) envs.get("valid_msisdn2");
		String valid_simSerial2 = (String) envs.get("valid_simSerial2");
		String nin = (String) envs.get("nin");
		String ninVerificationMode = (String) envs.get("ninVerificationMode");
		
		// Select Msisdn Category
		TestUtils.testTitle("Select Msisdn Category");
		TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='MSISDN Category']", "MSISDN Category");
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnCategorySpinner")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Mobile']")).click();
		Thread.sleep(500);
		
		// Proceed after supplying empty details
		TestUtils.testTitle("Proceed after supplying empty details");
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).sendKeys("");
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/simSerialField")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/simSerialField")).sendKeys("");
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/addMsisdnSimSerialButton")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/message", "Required Input Field: Phone Number");
		getDriver().findElement(By.id("android:id/button1")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")));
		Thread.sleep(500);
		
		// Proceed after supplying only Msisdn
		TestUtils.testTitle("Proceed after supplying only Msisdn: " + valid_msisdn);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).sendKeys(valid_msisdn);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/simSerialField")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/simSerialField")).sendKeys("");
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/addMsisdnSimSerialButton")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/message", "Required Input Field: Sim Serial");
		getDriver().findElement(By.id("android:id/button1")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")));
		Thread.sleep(500);
		
		// Proceed after supplying only Sim Serial
		TestUtils.testTitle("Proceed after supplying only Sim Serial: " + valid_simSerial);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).sendKeys(" ");
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/simSerialField")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/simSerialField")).sendKeys(valid_simSerial);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/addMsisdnSimSerialButton")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/message", "Required Input Field: Phone Number");
		getDriver().findElement(By.id("android:id/button1")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")));
		Thread.sleep(500);
		
		// Proceed after supplying invalid msisdn and sim serial
		TestUtils.testTitle("Proceed after supplying invalid msisdn: (" + invalid_msisdn + ") and invalid sim serial: (" + invalid_simSerial + ") for validation");
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).sendKeys(invalid_msisdn);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/simSerialField")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/simSerialField")).sendKeys(invalid_simSerial);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/addMsisdnSimSerialButton")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/message", "The MSISDN has an unrecognized National Destination Code. "
				+ "Please ensure the MSISDN starts with any of the following NDCs: "
				+ "0701,0708,0802,0808,0812,0901,0902,0904,0907,0809,0817,0818,0908,"
				+ "0909,0705,0805,0807,0811,0815,0905,0915,0703,0706,0803,0806,0810,"
				+ "0813,0814,0816,0903,0906 and must be 11 digits");
		getDriver().findElement(By.id("android:id/button1")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")));
		Thread.sleep(500);
		
		// Proceed after supplying multiple msisdns and sim serial
		TestUtils.testTitle("Proceed after supplying Multiple msisdns: (" + valid_msisdn + ") and (" + valid_msisdn2 + ") for validation");
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).sendKeys(valid_msisdn);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/simSerialField")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/simSerialField")).sendKeys(valid_simSerial);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/addMsisdnSimSerialButton")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/message", "Msisdn is valid");
		getDriver().findElement(By.id("android:id/button1")).click();
		
		// Add another Number
		TestUtils.testTitle("Add another Number");
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).sendKeys(valid_msisdn2);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/simSerialField")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/simSerialField")).sendKeys(valid_simSerial2);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/addMsisdnSimSerialButton")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/message", "Msisdn is valid");
		getDriver().findElement(By.id("android:id/button1")).click();

		TestUtils.scrollDown();

		TestUtils.testTitle("Assert First Number");
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/phone_no_view", valid_msisdn);
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/tv_sim_serial", valid_simSerial);

		TestUtils.testTitle("Assert Second Number");
		TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='" + valid_msisdn2 + "']", valid_msisdn2);
		TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='" + valid_simSerial2 + "']", valid_simSerial2);
		Thread.sleep(500);

		//Remove Second MSISDN
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/delete_button")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/nextButton")).click();

		//NIN Verification
		TestBase.verifyNINTest(nin, ninVerificationMode);

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Personal Details']")));
		TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Personal Details']", "Personal Details");

		Form.individualForeignerForm(dataEnv);
	}

	@Parameters({ "dataEnv"})
	@Test
	public void captureCompanyNewSimTest(String dataEnv) throws Exception {

		WebDriverWait wait = new WebDriverWait(getDriver(), 10);
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resource/" + dataEnv + "/data.conf.json"));
		JSONObject envs = (JSONObject) config.get("NewRegistration");

		String valid_msisdn = (String) envs.get("valid_msisdn");
		String valid_simSerial = (String) envs.get("valid_simSerial");
		String lga = (String) envs.get("lga");
		String nin = (String) envs.get("nin");
		String ninVerificationMode = (String) envs.get("ninVerificationMode");

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Total Subscribers']")));
		String totalSubBeforeCapture = getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/kpi_report_value")).getText();
		int totalSubVal = TestUtils.convertToInt(totalSubBeforeCapture);

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Total Sync Confirmed']")));
		String totalSynConfBeforeCapture = getDriver().findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/androidx.drawerlayout.widget.DrawerLayout/android.widget.RelativeLayout/android.widget.FrameLayout[1]/androidx.viewpager.widget.ViewPager/android.widget.RelativeLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.TextView")).getText();
		int totalSynConfVal = TestUtils.convertToInt(totalSynConfBeforeCapture);


		navigateToCaptureMenuTest();

		// Select LGA of Registration
		try{
			TestUtils.testTitle("Select LGA of Registration: " + lga);
			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/lga_of_reg")).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/alertTitle")));
			TestUtils.assertSearchText("ID", "android:id/alertTitle", "LGA of Registration*");
			getDriver().findElement(By.xpath("//android.widget.TextView[@text='" + lga + "']")).click();
			Thread.sleep(500);
		}catch (Exception e){

		}


		// Select New Registration
		TestUtils.testTitle("Select New Registration");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/typeofreg")));
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/typeofreg")).click();
		Thread.sleep(500);
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/alertTitle", "Select Registration Type");
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='New Registration']")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/next_button")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/pageTitle")));
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/pageTitle", "New Registration");

		// Select Msisdn Category
		TestUtils.testTitle("Select Mobile Msisdn Category");
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnCategorySpinner")).click();
		Thread.sleep(500);
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Mobile']")).click();
		Thread.sleep(500);
		TestUtils.assertSearchText("XPATH", "//android.widget.CheckedTextView[@text='Mobile']", "Mobile");
		Thread.sleep(500);

		// Proceed after supplying valid msisdn and Sim serial
		TestUtils.testTitle("Proceed after supplying valid msisdn: (" + valid_msisdn + ") and valid Sim serial: (" + valid_simSerial + ")");
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).sendKeys(valid_msisdn);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/simSerialField")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/simSerialField")).sendKeys(valid_simSerial);

		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/addMsisdnSimSerialButton")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/message", "Msisdn is valid");
		getDriver().findElement(By.id("android:id/button1")).click();

		TestUtils.scrollDown();

		Asserts.assertAddedNumbers();


		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/nextButton")).click();

		//NIN Verification
		TestBase.verifyNINTest(nin, ninVerificationMode);

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Personal Details']")));
		Thread.sleep(500);
		Form.NigerianCompanyForm(dataEnv);

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Total Subscribers']")));
		String totalSubAfterCapture = getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/kpi_report_value")).getText();
		int totalSubValAf = TestUtils.convertToInt(totalSubAfterCapture);

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Total Sync Confirmed']")));
		String totalSynConfAfterCapture = getDriver().findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/androidx.drawerlayout.widget.DrawerLayout/android.widget.RelativeLayout/android.widget.FrameLayout[1]/androidx.viewpager.widget.ViewPager/android.widget.RelativeLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.TextView")).getText();
		int totalSynConfValAf = TestUtils.convertToInt(totalSynConfAfterCapture);

		testInfo.get().info("Total Subscribers Before Capture: "+totalSubBeforeCapture);
		testInfo.get().info("Total Sync Confirmed Before Capture: "+totalSynConfBeforeCapture);

		if(totalSubValAf == (totalSubVal+1)){
			testInfo.get().info("Total Subscribers After Capture: "+totalSubValAf);
		}else {
			testInfo.get().error("Total Subscribers After Capture: "+totalSubValAf);
		}

		if(totalSynConfValAf == (totalSynConfVal+1)){
			testInfo.get().info("Total Sync Confirmed After Capture: "+totalSynConfValAf);
		}else {
			testInfo.get().error("Total Sync Confirmed After Capture: "+totalSynConfValAf);
		}
	}

}
