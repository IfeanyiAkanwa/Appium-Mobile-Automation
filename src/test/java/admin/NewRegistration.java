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

public class NewRegistration extends TestBase {


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
		Features.msisdnValidationOnline(dataEnv, "NMS");
		
	}
	
	@Parameters({ "dataEnv"})
	@Test
	public void multipleMsisdnValidation(String dataEnv) throws Exception {
		
		Features.msisdnMultipleValidationTest(dataEnv, "NMS");
	}
	
	@Parameters({ "dataEnv"})
	@Test
	public void selectCountry(String dataEnv) throws Exception {
		
		//Select country
		Features.selectCountry(dataEnv,"NIGERIA");
	}


	@Parameters({ "dataEnv"})
	@Test
	public void overridePortrait(String dataEnv) throws Exception {

		Features.portraitCaptureOverride();
	}
	
	@Parameters({ "dataEnv"})
	@Test
	public void overrideHand(String dataEnv) throws Exception {
		Features.captureOverridenHand(dataEnv, "NMS");
	}
	
	
	
	@Parameters({ "dataEnv"})
	@Test
	public void vninVerificationOnline(String dataEnv) throws Exception {

		//NIN Verification
		Features.vNinVerificationOnline(dataEnv);
	}
	
	@Parameters({ "dataEnv"})
	@Test
	public void ninVerificationOnline(String dataEnv) throws Exception {

		//NIN Verification
		Features.ninVerificationOnline(dataEnv);
	}
	
	
	
	@Parameters({ "dataEnv"})
	@Test
	public void eyeBalling(String dataEnv) throws Exception {

		
		Features.nimcEyeBalling(dataEnv);
	}
	
	@Parameters({ "dataEnv"})
	@Test
	public void demographicsCapture(String dataEnv) throws Exception {

		Form.personalDetails(dataEnv);
		Form.addressDetails(dataEnv);
	}
	
	@Parameters({ "dataEnv"})
	@Test
	public void captureUploadDoc(String dataEnv) throws Exception {

		Features.captureUploadDocument(dataEnv);
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
	

}
