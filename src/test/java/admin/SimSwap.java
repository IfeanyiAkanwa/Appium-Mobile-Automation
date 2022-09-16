package admin;

import com.aventstack.extentreports.Status;
import db.ConnectDB;
import demographics.Form;
import functions.Features;
import io.appium.java_client.android.AndroidKeyCode;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.Asserts;
import utils.TestBase;
import utils.TestUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static admin.ReportsTest.navigateToReportsPage;

public class SimSwap extends TestBase {
	private static String proxy_phone;

	
	@BeforeMethod
	@Parameters({ "dataEnv"})
	@Test
	public void init(String dataEnv) throws FileNotFoundException, IOException, ParseException {
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resource/" + dataEnv + "/data.conf.json"));
		JSONObject envs = (JSONObject) config.get("SIMSwap");

		 proxy_phone = (String) envs.get("proxy_phone");
	}
	
	  @Parameters({ "dataEnv"})
	    @Test
	    public void noneSIMSwapPrivilegeTest(String dataEnv) throws Exception {
		Features feature = new Features();
		feature.noneUsecasePrivilegeTest(dataEnv, "SIM Swap");
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
		Features.selectRegistration(dataEnv, "SIM Swap");
		
	}
	
	@Parameters({ "dataEnv"})
	@Test
	public void msisdnValidationOnline(String dataEnv) throws Exception {
		Features.msisdnValidationOnline(dataEnv, "SSS");
		
	}
	
	@Parameters({ "dataEnv"})
	@Test
	public void validateProxy(String dataEnv) throws Exception {
		Features.validateProxySimSwap(dataEnv);
	
	}
	
	@Parameters({ "dataEnv"})
	@Test
	public void validateOtp(String dataEnv) throws Exception {
		Features.validateOtp(proxy_phone, "SSP");
		
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
		Features.captureOverridenHand(dataEnv, "SSS");
	}
	
	@Parameters({ "dataEnv"})
	@Test
	public void overrideHandProxy(String dataEnv) throws Exception {
	//	Select override
		Features.captureOverridenHand(dataEnv, "SSP");
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
	public void proxyNINVerificationOnline(String dataEnv) throws Exception {

		//NIN Verification
		Features.ninVerificationOnline(dataEnv, "Proxy's NIN Verification - Search By NIN");
	}
	
	@Parameters({ "dataEnv"})
	@Test
	public void subscriberNIN(String dataEnv) throws Exception {

		//NIN Verification
		Features.ninVerificationOnline(dataEnv, "Subscriber's NIN Verification - Search By NIN" );
	}
	
	
	
	
	@Parameters({ "dataEnv"})
	@Test
	public void eyeBalling(String dataEnv) throws Exception {

		
		Features.nimcEyeBalling(dataEnv);
	}
	
	@Parameters({ "dataEnv"})
	@Test
	public void subscriberEyeBalling(String dataEnv) throws Exception {

		Features.nimcEyeBalling(dataEnv);
	}
	
	@Parameters({ "dataEnv"})
	@Test
	public void demographicsCapture(String dataEnv) throws Exception {
		Form.captureSimSwapForm(dataEnv);
		Form.simSwapResponse(dataEnv);

	}
	
	@Parameters({ "dataEnv"})
	@Test
	public void ProxydemographicsCapture(String dataEnv) throws Exception {
		Form.captureSimSwapFormProxy(dataEnv);
		Form.simSwapResponse(dataEnv);

	}
	
	@Parameters({ "dataEnv"})
	@Test
	public void captureUploadDoc(String dataEnv) throws Exception {

		Features.captureSimSwapDocument(dataEnv);
	}
	
	@Parameters({ "dataEnv"})
	@Test
	public void captureUploadDocProxy(String dataEnv) throws Exception {

		Features.captureSimSwapDocumentProxy(dataEnv);
	}
	
	
	
	
	@Parameters({ "dataEnv"})
	@Test
	public void saveCapture(String dataEnv) throws Exception {

		Features.simSwapSubmit2(dataEnv);
	}
	
	
	@Parameters({ "dataEnv"})
	@Test
    public void databaseAssertions(String dataEnv) throws Exception {

        String nmUniqueId = unique_Id;
     
        TestUtils.testTitle("Database Checks: Basic Data, Meta Data, BFP Sync Log, User Identification, SMS Activation Request, MSISDN Details, Passport Details");

        Thread.sleep(1000);
        

        ConnectDB.query(nmUniqueId, dataEnv, "SSP");

        ConnectDB.specialData();

    }
	
	@Parameters({ "dataEnv"})
	@Test

    public void releaseQuarantinedRecords(String dataEnv) throws Exception {
        Thread.sleep(30000);

		Features.releaseQuarantinedRecords(dataEnv, unique_Id);
    }


  


}
 
  
  

       


