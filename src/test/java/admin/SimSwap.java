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
		Features.msisdnValidationOnline(dataEnv, "SS");
		
	}
	
	@Parameters({ "dataEnv"})
	@Test
	public void validateProxy(String dataEnv) throws Exception {
		Features.validateProxySimSwap(dataEnv);
		rroverridePortrait(dataEnv);
		overrideHand(dataEnv);
		Features.validateOtp(proxy_phone);
		
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
		Features.captureOverridenHand(dataEnv, "SS");
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

		Features.simSwapSubmit(dataEnv);
	}
	

  
    @Test
    public static int verifySubscriberNINTest(String nin, String ninVerificationMode) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(getDriver(), 20);
        //ninStatus is set to available automatically
        int ninStatus=1;
        //Proceed to NIN Verification View
        TestUtils.testTitle("Select NIN Verification Mode: "+ninVerificationMode);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/alertTitle", "Subscriber's NIN Verification");

        //Select NIN Verification Type
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/verification_modes")).click();
        //wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/alertTitle")));

        getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='"+ninVerificationMode+"']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/proceed")).click();

        //Search by NIN Modal
        TestUtils.testTitle("Click on Search without supplying NIN");
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/nin")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/capture_button")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/error_text")));
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/error_text", "Only numbers and minimum of 11 characters are allowed");

        TestUtils.testTitle("Search NIN with less than 11 digits: 11111");
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/nin")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/nin")).sendKeys("11111");
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/capture_button")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/error_text")));
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/error_text", "Only numbers and minimum of 11 characters are allowed");

        TestUtils.testTitle("Search by NIN: "+nin);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/title")));
        TestUtils.assertSearchText("ID", "android:id/title", "Subscriber's NIN Verification - Search By NIN");
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/nin")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/nin")).sendKeys(nin);
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/capture_button")).click();

        try{
            //NIN Details View
            TestUtils.testTitle("Confirm the searched NIN is returned: "+nin);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/title")));
            TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/nin_verification_title", "Subscriber's NIN Verification");
            TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/tv_compare_text", "Please compare user data before proceeding");
            TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/tv_portrait_image", "Portrait Image");

            TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/tv_sim_reg", "SIM REG");
            TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/tv_nimc", "NIMC");
            String userdata=getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/tv_user_data")).getText();
            if(userdata.contains("User data")){
                TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/tv_user_data", "User data");
            }else{
                TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/tv_user_data", "User Data");
            }

            TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/tv_nimc_data", "NIMC Data");

            String firstName = getDriver().findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/androidx.recyclerview.widget.RecyclerView/android.view.ViewGroup[1]/android.widget.TextView[2]")).getText();
            String Surname = getDriver().findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/androidx.recyclerview.widget.RecyclerView/android.view.ViewGroup[2]/android.widget.TextView[2]")).getText();
            String dob = getDriver().findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/androidx.recyclerview.widget.RecyclerView/android.view.ViewGroup[3]/android.widget.TextView[2]")).getText();

            //Confirm the NIMC Data
            TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Firstname']", "Firstname");
            TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Surname']", "Surname");
            TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Date of birth']", "Date of birth");
            String empty = "";
            Map<String, String> fields = new HashMap<>();
            fields.put("Firstname", firstName);
            fields.put("Surname", Surname);
            fields.put("Date of birth", dob);

            for (Map.Entry<String, String> entry : fields.entrySet()) {
                try {
                    Assert.assertNotEquals(entry.getValue(), empty);
                    Assert.assertNotEquals(entry.getValue(), null);
                    testInfo.get().log(Status.INFO, "<b>" + entry.getKey() + " : </b>" + entry.getValue());
                } catch (Error ee) {
                    testInfo.get().error("<b>" + entry.getKey() + " : </b>" + entry.getValue());
                }

            }

            //Proceed
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/accept_button")));
            Thread.sleep(2000);
            getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/accept_button")).click();

        }catch (Exception e){
            //Nin is not available
            ninStatus=0;
            Thread.sleep(1000);
            try {
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/button1")));
                getDriver().findElement(By.id("android:id/button1")).click();
                getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/continue_btn")).click();

                Thread.sleep(1000);
                getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/nin")).sendKeys(nin);
                Thread.sleep(1000);
                getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/proceed_button")).click();
            }catch (Exception e1){
                System.out.println(e1);
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/continue_btn")));
                getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/continue_btn")).click();

                Thread.sleep(1000);
                getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/nin")).sendKeys(nin);
                Thread.sleep(1000);
                getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/proceed_button")).click();
            }

        }

        return ninStatus;
    }

 
  
  

       

}
