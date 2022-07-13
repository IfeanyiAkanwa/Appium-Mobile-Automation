package admin;

import db.ConnectDB;
import demographics.Form;
import functions.Features;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import io.appium.java_client.android.AndroidKeyCode;
import org.json.simple.JSONArray;
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

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import utils.Asserts;
import utils.TestBase;
import utils.TestUtils;

import static admin.ReportsTest.navigateToReportsPage;

public class ReRegistrationCapture extends TestBase {

	private static StringBuffer verificationErrors = new StringBuffer();
	int totalSubVal=0;int totalSyncsentVal = 0;int totalSyncpendingVal = 0;int totalSynConfVal = 0;int totalRejectVal = 0;
	static int fingerPrintCount=0;
	
	private static String noIndividualReg, noCompanyReg, generalUserPassword, foreign_msisdn,lga,noForeignRegUser,valid_msisdn;
	
	@BeforeMethod
	@Parameters({ "dataEnv"})
	@Test
	public void init(String dataEnv) throws FileNotFoundException, IOException, ParseException {
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resource/" + dataEnv + "/data.conf.json"));
		JSONObject envs = (JSONObject) config.get("ReRegistration");

		 noIndividualReg = (String) envs.get("noIndividualReg");
		 noCompanyReg = (String) envs.get("noCompanyReg");
		 generalUserPassword = (String) envs.get("generalUserPassword");
		 foreign_msisdn = (String) envs.get("foreign_msisdn");
		 lga = (String) envs.get("lga");
		 noForeignRegUser = (String) envs.get("noForeignRegUser");
		 valid_msisdn = (String) envs.get("valid_msisdn");

	}
	
	
	@Parameters({ "dataEnv"})
	@Test
	public void noneReRegPrivilegeTest(String dataEnv) throws Exception {
		Features feature = new Features();
		feature.noneUsecasePrivilegeTest(dataEnv, "Re-Registration");
		Features.logOutUser();

	}

	@Parameters({ "dataEnv"})
	@Test
	public void reRegCOOPrivilegesTest(String dataEnv) throws Exception {
		Features.reRegCOOPrivilegesTest(dataEnv, noIndividualReg, generalUserPassword );
		Features.logOutUser();
		
	}
	@Parameters({ "dataEnv"})
	@Test
	public void reRegBypassOtpPrivilege(String dataEnv) throws Exception {
		
		Features.reRegBypassOtpPrivilege(dataEnv, noCompanyReg, generalUserPassword, valid_msisdn);
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
		Features.selectRegistration(dataEnv, "Re-Registration");
		
	}

	@Parameters({ "dataEnv"})
	@Test
	public void msisdnValidationOnline(String dataEnv) throws Exception {
		Features.msisdnValidationOnline(dataEnv,"RR");
	}
	
	@Parameters({ "dataEnv"})
	@Test
	public void rroverridePortrait(String dataEnv) throws Exception {
		
	//	Select override
		Features.RRportraitCaptureOverride();
	}
	
	@Parameters({ "dataEnv"})
	@Test
	public void overrideHand(String dataEnv) throws Exception {
	//	Select override
		Features.captureOverridenHand(dataEnv, "RR");
	}
	
	@Parameters({ "dataEnv"})
	@Test
	public void requestOTP(String dataEnv) throws Exception {
	//	Select override
		Features.requestOTP(dataEnv);
	}
	
	@Parameters({ "dataEnv"})
	@Test
	public void byrequestOTP(String dataEnv) throws Exception {
	//	Select override
		Features.byPassrequestOTP(dataEnv);
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
