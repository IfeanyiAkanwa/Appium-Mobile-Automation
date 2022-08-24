package admin;

import com.aventstack.extentreports.Status;
import db.ConnectDB;
import demographics.Form;
import functions.Features;
import io.appium.java_client.android.AndroidKeyCode;
import org.apache.maven.surefire.shade.org.apache.commons.lang3.ObjectUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.Asserts;
import utils.TestBase;
import utils.TestUtils;

import java.io.File;
import java.io.FileReader;
import java.util.HashMap;

public class CorporateSimSwap extends TestBase {

   
    @Parameters({ "dataEnv"})
    @Test
    public void noneCorporarteSIMSwapPrivilegeTest(String dataEnv) throws Exception {
	Features feature = new Features();
	feature.noneUsecasePrivilegeTest(dataEnv, "Corporate SIM Swap");
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
	Features.selectRegistration(dataEnv, "Corporate SIM Swap");
	
}

@Parameters({ "dataEnv"})
@Test
public void msisdnValidationOnline(String dataEnv) throws Exception {
	Features.msisdnValidationOnline(dataEnv, "CSS");
	
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
public void overrideHand(String dataEnv) throws Exception {

	Features.captureOverridenHand(dataEnv, "CSS");
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
public void captureUploadDoc(String dataEnv) throws Exception {

	Features.captureSimSwapDocument(dataEnv);
}


@Parameters({ "dataEnv"})
@Test
public void saveCapture(String dataEnv) throws Exception {

	Features.simSwapSubmit2(dataEnv);
}


}
