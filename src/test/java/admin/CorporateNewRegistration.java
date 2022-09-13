package admin;


import demographics.Form;
import functions.Features;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import db.ConnectDB;
import utils.TestBase;
import utils.TestUtils;


public class CorporateNewRegistration extends TestBase {
 
    @Parameters({ "dataEnv"})
    @Test
    public void noneCorporatePrivilegeTest(String dataEnv) throws Exception {
    	Features feature = new Features();
		feature.noneUsecasePrivilegeTest(dataEnv, "Corporate New Registration");
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
		Features.selectRegistration(dataEnv, "Corporate New Registration");
		
	}
	
	@Parameters({ "dataEnv"})
	@Test
	public void msisdnValidationOnline(String dataEnv) throws Exception {
		Features.msisdnValidationOnline(dataEnv, "CN");
		
	}
	
	@Parameters({ "dataEnv"})
	@Test
	public void overrideHand(String dataEnv) throws Exception {
	
		Features.captureOverridenHand(dataEnv, "CN");
	}
	
	@Parameters({ "dataEnv"})
	@Test
	public void ninVerificationOnline(String dataEnv) throws Exception {
		Features.ninVerificationOnline(dataEnv, "Search By NIN");
	}
	
	@Parameters({ "dataEnv"})
	@Test
	public void vninVerificationOnline(String dataEnv) throws Exception {
		Features.vNinVerificationOnline(dataEnv, "Search By NIN");
	}
	
	@Parameters({ "dataEnv"})
	@Test
	public void portraitFileUpload(String dataEnv) throws Exception {

		Features.portraitFileUpload(dataEnv, "picture.jpg");
	}
	
	@Parameters({ "dataEnv"})
	@Test
	public void corporateOverridePortrait(String dataEnv) throws Exception {

		Features.corporateOverridePortrait(dataEnv);
	}
	
	
	@Parameters({ "dataEnv"})
	@Test
	public void eyeBalling(String dataEnv) throws Exception {

		
		Features.nimcEyeBalling(dataEnv);
	}
	
	@Parameters({ "dataEnv"})
	@Test
	public void demographicsCapture(String dataEnv) throws Exception {

		Form.CorporateRegFormAutoPopulate2(dataEnv);
		Form.addressDetails(dataEnv);
	}
	
	@Parameters({ "dataEnv"})
	@Test
	public void captureUploadDoc(String dataEnv) throws Exception {
		Features.corporatecaptureUploadDocument(dataEnv);
		
	}
	
	@Parameters({ "dataEnv"})
	@Test
	public void capturePreview(String dataEnv) throws Exception {

		Features.captureKYCDocument(dataEnv);
	}
	
	@Parameters({ "dataEnv"})
	@Test
	public void saveCapture(String dataEnv) throws Exception {

		Features.saveEnrollment(dataEnv);
	}
	
	@Parameters({ "dataEnv"})
	@Test

    public void databaseAssertions(String dataEnv) throws Exception {

        String nmUniqueId = unique_Id;
        System.out.println(">>>>>>>>>>>" +unique_Id);

        TestUtils.testTitle("Database Checks: Basic Data, Meta Data, BFP Sync Log, User Identification, SMS Activation Request, MSISDN Details, Passport Details");

        Thread.sleep(1000);

        ConnectDB.query(nmUniqueId, dataEnv, "CN");

        ConnectDB.specialData();

    }
	
	@Parameters({ "dataEnv"})
	@Test

    public void releaseQuarantinedRecords(String dataEnv) throws Exception {
        Thread.sleep(30000);
	
        System.out.println(">>>>>>>>>>>" +unique_Id);
		Features.releaseQuarantinedRecords(dataEnv, unique_Id);
    }


  

}
