package admin;

import com.aventstack.extentreports.Status;
import db.ConnectDB;
import io.appium.java_client.android.AndroidKeyCode;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import functions.Features;
import demographics.Form;
import utils.Asserts;
import utils.TestBase;
import utils.TestUtils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import static admin.ReportsTest.navigateToReportsPage;

public class ForeignerNewReg extends TestBase {


	int totalSubVal=0;int totalSyncsentVal = 0;int totalSyncpendingVal = 0;int totalSynConfVal = 0;int totalRejectVal = 0;

	
	@Parameters({ "dataEnv"})
	@Test
	public void noneNewRegPrivilegeTest(String dataEnv) throws Exception {
		Features feature = new Features();
		feature.noneUsecasePrivilegeTest(dataEnv, "New Registration");
		Features.logOutUser();
	}
	
	@Parameters({ "dataEnv"})
	@Test
	public void navigateToCapture(String dataEnv) throws Exception {
		Features.navigateToCaptureMenuTest();
	
	}
	
	@Test
	public void captureReportRecords() throws InterruptedException {
		Features.captureReportRecords();
	}
	
	@Parameters({ "dataEnv"})
	@Test
	public void selectRegistrationType(String dataEnv) throws Exception {
		Features.selectRegistration(dataEnv, "New Registration");
		
	}


	@Parameters({ "dataEnv"})
	@Test
	public void singleMsidnValidationTest(String dataEnv) throws Exception {
		Features.msisdnValidationOnline(dataEnv, "FR");
		
	}
	
	@Parameters({ "dataEnv"})
	@Test
	public void foreignerRegForm(String dataEnv) throws Exception {
		
		Features.foreignerRegForm(dataEnv);
	}
	
	@Parameters({ "dataEnv"})
	@Test
	public void selectCountry(String dataEnv) throws Exception {
		
		//Select country
		Features.selectCountry(dataEnv,"AFGHANISTAN");
	}


	@Parameters({ "dataEnv"})
	@Test
	public void overridePortrait(String dataEnv) throws Exception {

		Features.portraitCaptureOverride();
	}
	
	@Parameters({ "dataEnv"})
	@Test
	public void overrideHand(String dataEnv) throws Exception {
		Features.captureOverridenHand(dataEnv, "FR");
	}
	
	
	
	@Parameters({ "dataEnv"})
	@Test
	public void vninVerificationOnline(String dataEnv) throws Exception {

		//NIN Verification
		Features.vNinVerificationOnline(dataEnv, "Search By NIN");
	}
	
	@Parameters({ "dataEnv"})
	@Test
	public void ninVerificationOnline(String dataEnv) throws Exception {

		//NIN Verification
		Features.ninVerificationOnline(dataEnv, "Search By NIN");
	}
	
	
	
	@Parameters({ "dataEnv"})
	@Test
	public void eyeBalling(String dataEnv) throws Exception {

		
		Features.foreignerEyeBalling(dataEnv);
	}
	
	@Parameters({ "dataEnv"})
	@Test
	public void demographicsCapture(String dataEnv) throws Exception {

		Form.foreignerPersonalDetails(dataEnv);
		Form.foreignerAddressDetails(dataEnv);
	}
	
	@Parameters({ "dataEnv"})
	@Test
	public void captureUploadDoc(String dataEnv) throws Exception {

		Features.foreignercaptureUploadDocument(dataEnv);
	}
	
	@Parameters({ "dataEnv"})
	@Test
	public void capturePreview(String dataEnv) throws Exception {

		Features.capturePreview(dataEnv);
	}
	
	@Parameters({ "dataEnv"})
	@Test
	public void saveCapture(String dataEnv) throws Exception {

		Features.saveCapture(dataEnv);
	}
	
	
	
	
	//
//	@Parameters({ "dataEnv"})
//	@Test
//	public static void captureForeignRegTest(String dataEnv) throws Exception {
//		WebDriverWait wait = new WebDriverWait(getDriver(), 30);
//
//		try {
//			//Proceed to Capture page
//			navigateToCaptureMenuTest();
//
//			//Proceed to new reg
//			newRegUseCaseTest(dataEnv);
//		}catch(Exception e){
//
//		}
//		// Select Msisdn Category
//		TestUtils.testTitle("Select Msisdn Category");
//		TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='MSISDN Category']", "MSISDN Category");
//		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnCategorySpinner")).click();
//		Thread.sleep(500);
//		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Mobile']")).click();
//		Thread.sleep(500);
//
//		// Proceed after supplying valid msisdns and sim serial
//		TestUtils.testTitle("Proceed after supplying valid msisdns: (" + valid_msisdn2 + ") and (" + valid_simSerial2 + ") for validation");
//
//		// Add another Number
//		TestUtils.testTitle("Add another Number");
//		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).clear();
//		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdnField")).sendKeys(valid_msisdn2);
//		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/simSerialField")).clear();
//		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/simSerialField")).sendKeys(valid_simSerial2);
//		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/addMsisdnSimSerialButton")).click();
//		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
//		TestUtils.assertSearchText("ID", "android:id/message", "Msisdn is valid");
//		getDriver().findElement(By.id("android:id/button1")).click();
//
//		TestUtils.scrollDown();
//
//		TestUtils.testTitle("Assert Second Number");
//		TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='" + valid_msisdn2 + "']", valid_msisdn2);
//		TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='" + valid_simSerial2 + "']", valid_simSerial2);
//		Thread.sleep(500);
//
//		try {
//			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/btnCapturePortrait")).click();
//			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/captureButton")).click();
//
//		}catch (Exception e){
//
//		}
//		//Select country
//		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/countryOfOriginSpinner")).click();
//		Thread.sleep(500);
//		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='AFGHANISTAN']")).click();
//		Thread.sleep(1000);
//
//		//Fill the foreigners form here
//		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/title", "Foreigner Registration");
//		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/edPassportNumber")).clear();
//		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/edPassportNumber")).sendKeys(TestUtils.generatePhoneNumber());
//		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/spForeignerTypes")).click();
//		Thread.sleep(1000);
//		TestUtils.assertSearchText("XPATH", "//android.widget.CheckedTextView[@text='Short Stay']", "Short Stay");
//		TestUtils.assertSearchText("XPATH", "//android.widget.CheckedTextView[@text='ECOWAS']", "ECOWAS");
//		TestUtils.assertSearchText("XPATH", "//android.widget.CheckedTextView[@text='Diplomat']", "Diplomat");
//		TestUtils.assertSearchText("XPATH", "//android.widget.CheckedTextView[@text='Legal Resident(With NIN)']", "Legal Resident(With NIN)");
//		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='ECOWAS']")).click();
//
//		try{
//			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/spForeignerTypes")).click();
//			getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Short Stay']")).click();
//
//			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/edStartDate")).click();
//			getDriver().findElement(By.id("android:id/button1")).click();
//			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/edEndDate")).click();
//			getDriver().findElement(By.xpath("//android.view.View[@text='30']")).click();
//			getDriver().findElement(By.id("android:id/button1")).click();
//			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/spForeignerDocs")).click();
//			getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Visa Page']")).click();
//			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/uploadButton")).click();
//			TestUtils.scrollUp();
//			TestUtils.scrollUntilElementIsVisible("XPATH", "//android.widget.TextView[@text='picture.jpg']");
//
//			getDriver().findElement(By.xpath("//android.widget.TextView[@text='picture.jpg']")).click();
//			Thread.sleep(500);
//
//		}catch (Exception e){
//			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/spForeignerTypes")).click();
//			getDriver().findElement(By.id("//android.widget.CheckedTextView[@text='ECOWAS']")).click();
//
//		}
//		Thread.sleep(1000);
//		TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/btnProceed");
//		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/btnProceed")).click();
//        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/button1")));
//        getDriver().findElement(By.id("android:id/button1")).click();
//
//		//BioMetrics Verification
//		Thread.sleep(1000);
//		TestBase.verifyBioMetricsTest();
//
//		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/nextButton")));
//		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/nextButton")).click();
//
//		//NIN Verification
//		int ninStatus=0;
//
//		try{
//			getDriver().findElement(By.id("android:id/button1")).click();
//		}catch(Exception e){
//
//		}
//
//		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Personal Details']")));
//		TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Personal Details']", "Personal Details");
//
//		if (ninStatus==0){
//			//Use Form that populate data itself
//			Form.individualForeignerForm(dataEnv);
//		}else{
//			//Use autoPopulated Form
//			Form.individualForeignerFormAutoPopulate(dataEnv);
//		}
//
//		/*//To confirm that the registration category is saved on DB after successful registration
//		TestUtils.testTitle("To confirm that the registration category is saved on DB after successful registration:"+valid_msisdn);
//		TestUtils.assertTableValue("msisdn_detail", "msisdn", valid_msisdn, "msisdn_category", "MOBILE");
//
//		//Do Bulk Assert for Table checking
//		//TestUtils.assertBulkTables(valid_msisdn);
//		Thread.sleep(5000);
//		TestUtils.assertBulkTables(valid_msisdn, "AFGHANISTAN");*/
//		String uniqueId=ConnectDB.selectQueryOnTable("bfp_sync_log", "msisdn", valid_msisdn, "pk");
//		ConnectDB.query( uniqueId, dataEnv, "FR");
//
//		try {
//			getDriver().pressKeyCode(AndroidKeyCode.BACK);
//			Thread.sleep(1000);
//			getDriver().findElement(By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']")).click();
//			Thread.sleep(1000);
//			getDriver().findElement(By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']")).click();
//			Thread.sleep(1000);
//			getDriver().findElement(By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']")).click();
//			getDriver().findElement(By.id("android:id/button2")).click();
//			Thread.sleep(1000);
//			getDriver().findElement(By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']")).click();
//			//Logout
//			//TestBase.logOut(valid_msisdn);
//		}catch (Exception e) {
//			try {
//				getDriver().findElement(By.id("android:id/button3")).click();
//				getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/experience_type")).click();
//				getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Network Speed']")).click();
//				getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/rating_ratingBar")).click();
//				getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/rating_btn_dialog_positive")).click();
//				TestUtils.assertSearchText("ID", "android:id/alertTitle", "Feedback sent");
//
//				getDriver().pressKeyCode(AndroidKeyCode.BACK);
//			} catch (Exception e1) {
//
//			}
//		}
//
//	}

}
