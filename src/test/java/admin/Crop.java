package admin;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import db.ConnectDB;
import demographics.Form;
import utils.Asserts;
import utils.TestBase;
import utils.TestUtils;

public class Crop extends TestBase {
	
	private static final String String = null;

	@Test
	public static void navigateToCaptureMenuTest() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), 60);
		// Navigate to Registration Type
		String regType = "Navigate to Registration Type";
		Markup r = MarkupHelper.createLabel(regType, ExtentColor.BLUE);
		testInfo.get().info(r);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Home']")));
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/button_start_capture")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/reg_type_placeholder")));
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity:id/reg_type_placeholder","Registration Type");
	}
	
	@Parameters({ "dataEnv"})
	@Test
	public static void forgotTransactionIdTest(String dataEnv) throws Exception {
		
		WebDriverWait wait = new WebDriverWait(getDriver(), 60);
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resource/" + dataEnv + "/data.conf.json"));
		JSONObject envs = (JSONObject) config.get("Crop");
		
		String email = (String) envs.get("email");
		String valid_msisdn = (String) envs.get("valid_msisdn");
		String lga = (String) envs.get("lga");
		String invalid_email = (String) envs.get("invalid_email");
		
		// Select LGA of Registration
		String lgaa = "Select LGA of Registration: " + lga;
		Markup m = MarkupHelper.createLabel(lgaa, ExtentColor.BLUE);
		testInfo.get().info(m);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/lga_of_reg")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/alertTitle", "LGA of Registration*");
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='" + lga + "']")).click();
		Thread.sleep(500);

		// Select New Registration MSISDN
		String newReg = "Select New Registration MSISDN";
		Markup d = MarkupHelper.createLabel(newReg, ExtentColor.BLUE);
		testInfo.get().info(d);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/typeofreg")));
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/typeofreg")).click();
		Thread.sleep(500);
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity:id/alertTitle", "Select Registration Type");
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='New Registration (MSISDN)']")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/next_button")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/page_title")));
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity:id/page_title", "New Registration (MSISDN)");
		
		// Click on Forgot Transaction ID link
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/new_msisdn_forgot_transaction_id_text_view")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/forgot_transaction_id_label")));
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity:id/forgot_transaction_id_label", "Forgot Transaction ID?");
		
		// Proceed without supplying any details
        String fgot = "Proceed without supplying any details";
		Markup c = MarkupHelper.createLabel(fgot, ExtentColor.BLUE);
		testInfo.get().info(c);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/phone_no_text_field")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/phone_no_text_field")).sendKeys();
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/transaction_email_address_text_field")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/transaction_email_address_text_field")).sendKeys();
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/retrieve_id_button")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/message", "An Email Address or Alternate Phone Number must be provided to retrieve subscriber's Transaction ID");
		getDriver().findElement(By.id("android:id/button1")).click();
		Thread.sleep(500);
		
		// Proceed after supplying invalid email
        String inv = "Proceed after supplying invalid email: " + invalid_email;
		Markup u = MarkupHelper.createLabel(inv, ExtentColor.BLUE);
		testInfo.get().info(u);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/phone_no_text_field")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/transaction_email_address_text_field")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/transaction_email_address_text_field")).sendKeys(invalid_email);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/retrieve_id_button")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/message", "No record was found for this subscriber");
		getDriver().findElement(By.id("android:id/button1")).click();
		Thread.sleep(500);
		
		// Proceed after supplying valid email
        String fgot1 = "Proceed after supplying valid email: " + email;
		Markup t = MarkupHelper.createLabel(fgot1, ExtentColor.BLUE);
		testInfo.get().info(t);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/phone_no_text_field")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/transaction_email_address_text_field")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/transaction_email_address_text_field")).sendKeys(email);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/retrieve_id_button")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/result_message")));
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity:id/result_message", "Multiple records were found for " + email);
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity:id/instruction_message", "Please select the Transaction ID to be used for this registration");
		Thread.sleep(1000);
		
		// Get Count of Records found
		int count = getDriver().findElements(By.id("com.sf.biocapture.activity:id/transaction_id_result_item")).size();
		String count1 = Integer.toString(count);
		String num = "Number of records returned";
		Markup h = MarkupHelper.createLabel(num, ExtentColor.GREEN);
		testInfo.get().info(h);
		testInfo.get().info(count1);
		
		for (int i = 0; i < count; i++) {
			Asserts.assertTransactionIdRecords((WebElement) getDriver().findElements(By.id("com.sf.biocapture.activity:id/transaction_id_result_item")).get(i));
			Thread.sleep(1000);
		}
		
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/date_of_registration_value")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/new_msisdn_transaction_id")));
		Thread.sleep(500);
		
		// Assert Transaction ID after Retrieving records
		String tranID = getDriver().findElement(By.id("com.sf.biocapture.activity:id/new_msisdn_transaction_id")).getText();
		String tid = "Assert Transaction ID after Retrieving records";
		Markup r = MarkupHelper.createLabel(tid, ExtentColor.GREEN);
		testInfo.get().info(r);
		testInfo.get().info(tranID);
		
		// PSB Account
		String psb = "Click on PSB checkbox";
		Markup w = MarkupHelper.createLabel(psb, ExtentColor.BLUE);
		testInfo.get().info(w);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/dya_check_box")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/alertTitle", "[Select PSB Type]");
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='PSB']")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("android:id/button1")).click();
		Thread.sleep(500);
		
		// Enter valid Msisdn
		String demo = "Proceed to Demographics form";
		Markup v = MarkupHelper.createLabel(demo, ExtentColor.BLUE);
		testInfo.get().info(v);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/msisdn")).clear();
	    getDriver().findElement(By.id("com.sf.biocapture.activity:id/msisdn")).sendKeys(valid_msisdn);
	    getDriver().findElement(By.id("com.sf.biocapture.activity:id/add_msisdn_button")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/message", "Valid MSISDN. Valid Transaction ID.");
		getDriver().findElement(By.id("android:id/button1")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/next_button")));
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/next_button")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Personal Details']")));
		Thread.sleep(1500);
		try {
			if (getDriver().findElement(By.id("com.sf.biocapture.activity:id/company_details_title")).isDisplayed()) {
				TestUtils.assertSearchText("ID", "com.sf.biocapture.activity:id/company_details_title", "Company Details");
				getDriver().findElement(By.id("com.sf.biocapture.activity:id/company_cancel")).click();
				Thread.sleep(500);
				
				// Back button
				getDriver().findElement(By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']")).click();
				Thread.sleep(500);
			}
		} catch (Exception e) {
			TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Personal Details']", "Personal Details");
			Thread.sleep(500);
			
			// Back button
			getDriver().findElement(By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']")).click();
			Thread.sleep(500);
		}
		
	}
	
	@Parameters({ "dataEnv"})
	@Test
	public void cropTest(String dataEnv) throws Exception {

		WebDriverWait wait = new WebDriverWait(getDriver(), 50);
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resource/" + dataEnv + "/data.conf.json"));
		JSONObject envs = (JSONObject) config.get("Crop");
		
		String invalid_tranID = (String) envs.get("invalid_tranID");
		String valid_msisdn = (String) envs.get("valid_msisdn");
		String lga = (String) envs.get("lga");
		String used_tranID = (String) envs.get("used_tranID");

		// Select LGA of Registration
		String lgaa = "Select LGA of Registration: " + lga;
		Markup m = MarkupHelper.createLabel(lgaa, ExtentColor.BLUE);
		testInfo.get().info(m);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/lga_of_reg")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/alertTitle", "LGA of Registration*");
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='" + lga + "']")).click();
		Thread.sleep(500);

		// Select New Registration MSISDN
		String newReg = "Select New Registration MSISDN";
		Markup d = MarkupHelper.createLabel(newReg, ExtentColor.BLUE);
		testInfo.get().info(d);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/typeofreg")));
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/typeofreg")).click();
		Thread.sleep(500);
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity:id/alertTitle", "Select Registration Type");
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='New Registration (MSISDN)']")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/next_button")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/page_title")));
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity:id/page_title", "New Registration (MSISDN)");
		
		// DB Connection for Transaction ID
    	String transactionID = ConnectDB.getTransactionID();

		String tranID = "Valid Transaction ID";
		Markup o = MarkupHelper.createLabel(tranID, ExtentColor.GREEN);
		testInfo.get().info(o);
		testInfo.get().info(transactionID);
        if(transactionID == null){
        	testInfo.get().log(Status.INFO, "Can't get transaction ID.");
            getDriver().quit();
        }
        
        // Proceed without supplying any details
        String newReg1 = "Proceed without supplying any details";
		Markup c = MarkupHelper.createLabel(newReg1, ExtentColor.BLUE);
		testInfo.get().info(c);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/msisdn")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/msisdn")).sendKeys();
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/new_msisdn_transaction_id")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/new_msisdn_transaction_id")).sendKeys();
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/add_msisdn_button")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/message", "Required Input Field: Phone Number");
		getDriver().findElement(By.id("android:id/button1")).click();
		Thread.sleep(500);
		
		// Proceed without supplying msisdn
        String newReg2 = "Proceed after supplying valid transaction ID: (" + transactionID + ") and empty msisdn";
		Markup a = MarkupHelper.createLabel(newReg2, ExtentColor.BLUE);
		testInfo.get().info(a);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/msisdn")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/msisdn")).sendKeys();
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/new_msisdn_transaction_id")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/new_msisdn_transaction_id")).sendKeys(transactionID);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/add_msisdn_button")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/message", "Required Input Field: Phone Number");
		getDriver().findElement(By.id("android:id/button1")).click();
		Thread.sleep(500);
		
		// Proceed after supplying valid msisdn and invalid transaction Id
        String newReg3 = "Proceed after supplying valid msisdn: (" + valid_msisdn + ") and invalid transaction Id: (" + invalid_tranID + ")";
		Markup f = MarkupHelper.createLabel(newReg3, ExtentColor.BLUE);
		testInfo.get().info(f);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/msisdn")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/msisdn")).sendKeys(valid_msisdn);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/new_msisdn_transaction_id")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/new_msisdn_transaction_id")).sendKeys(invalid_tranID);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/add_msisdn_button")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/message", "Valid MSISDN. Transaction ID is invalid");
		getDriver().findElement(By.id("android:id/button1")).click();
		Thread.sleep(500);
		
		// Delete button
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/delete_button")).click();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/new_msisdn_delete_transaction_id")).click();
		Thread.sleep(500);
		
		// Proceed after supplying valid msisdn and Old transaction Id
        String newReg5 = "Proceed after supplying valid msisdn: (" + valid_msisdn + ") and Used transaction Id: (" + used_tranID + ")";
		Markup b = MarkupHelper.createLabel(newReg5, ExtentColor.BLUE);
		testInfo.get().info(b);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/msisdn")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/msisdn")).sendKeys(valid_msisdn);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/new_msisdn_transaction_id")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/new_msisdn_transaction_id")).sendKeys(used_tranID);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/add_msisdn_button")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/message", "Valid MSISDN. Transaction ID is invalid");
		getDriver().findElement(By.id("android:id/button1")).click();
		Thread.sleep(500);
		
		// Delete button
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/delete_button")).click();
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/new_msisdn_delete_transaction_id")).click();
		Thread.sleep(500);
		
		// Proceed after supplying valid msisdn and valid transaction Id
        String newReg4 = "Proceed after supplying valid msisdn: (" + valid_msisdn + ") and valid transaction Id: (" + transactionID + ")";
		Markup h = MarkupHelper.createLabel(newReg4, ExtentColor.BLUE);
		testInfo.get().info(h);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/msisdn")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/msisdn")).sendKeys(valid_msisdn);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/new_msisdn_transaction_id")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/new_msisdn_transaction_id")).sendKeys(transactionID);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/add_msisdn_button")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/message", "Valid MSISDN. Valid Transaction ID.");
		getDriver().findElement(By.id("android:id/button1")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/next_button")));
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/next_button")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Personal Details']")));
		Thread.sleep(1500);

		try {
			if (getDriver().findElement(By.id("com.sf.biocapture.activity:id/company_details_title")).isDisplayed()) {
				Asserts.AssertCompanyDetails();
				Thread.sleep(1000);
				
				// Certificate of Incorporation
				getDriver().findElement(By.id("com.sf.biocapture.activity:id/company_ok")).click();
				wait.until(ExpectedConditions.visibilityOfElementLocated((By.id("com.sf.biocapture.activity:id/alertTitle"))));
				TestUtils.assertSearchText("ID", "android:id/message", "Certificate of Incorporation is compulsory");
				getDriver().findElement(By.id("android:id/button1")).click();
				Thread.sleep(500);
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
				
				// Save
				getDriver().findElement(By.id("com.sf.biocapture.activity:id/company_ok")).click();
				Thread.sleep(500);
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/surNameTXT"))); 
				Thread.sleep(500);
				Asserts.AssertIndividualForm(); Thread.sleep(1000);
				TestUtils.scrollDown();
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/btnContinueReg"))); 
				Thread.sleep(500);
				getDriver().findElement(By.id("com.sf.biocapture.activity:id/btnContinueReg")).click(); 
				Thread.sleep(500);
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/countrySpinner")));
				Thread.sleep(1000);
				Asserts.AssertAddresstDetails(); Thread.sleep(1000);
				getDriver().findElement(By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']")).click();
				Thread.sleep(1000);
				Form.NigerianCompanyForm(dataEnv);
			} 
		} catch (Exception e) {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/surNameTXT"))); 
			Thread.sleep(500);
			Asserts.AssertIndividualForm(); Thread.sleep(1000);
			TestUtils.scrollDown();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/btnContinueReg"))); 
			Thread.sleep(500);
			getDriver().findElement(By.id("com.sf.biocapture.activity:id/btnContinueReg")).click(); 
			Thread.sleep(500);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/countrySpinner")));
			Thread.sleep(1000);
			Asserts.AssertAddresstDetails(); Thread.sleep(1000);
			getDriver().findElement(By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']")).click();
			Thread.sleep(1000);
			Form.individualForeignerForm(dataEnv);
		}
		
	}
}
