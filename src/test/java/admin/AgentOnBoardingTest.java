package admin;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import db.ConnectDB;
import utils.TestBase;
import utils.TestUtils;

import java.io.FileReader;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class AgentOnBoardingTest extends TestBase {

	@Parameters({ "dataEnv"})
	@Test
	public void onboardedAgentLoginTest(String dataEnv) throws Exception {
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resource/" + dataEnv + "/data.conf.json"));
		JSONObject envs = (JSONObject) config.get("AgentOnboarding");

		String valid_username = (String) envs.get("valid_username");
		String valid_password = (String) envs.get("valid_password");

		TestBase.Login1( valid_username, valid_password);
		//Thread.sleep(500);
	}

	@Test
	public void navigateToAgentOnBoardingTest( ) throws Exception {
		WebDriverWait wait = new WebDriverWait(getDriver(), 30);

		String title = "Navigate to Agent Onboarding page";
		TestUtils.testTitle(title);

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Home']")));
		getDriver().findElement(By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']")).click();
		//Thread.sleep(500);
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Agent OnBoarding']")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/page_title")));
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/page_title", "Agent Onboarding");
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/page_guide_title", "Get started in 3 easy steps");
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/step_one", "1. Email validation");
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/step_two", "2. OTP validation");
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/step_three", "3. Biometric capture");
		//Thread.sleep(500);
	}

	@Test
	public void navigateToDealerOnBoardingTest( ) throws Exception {
		WebDriverWait wait = new WebDriverWait(getDriver(), 30);

		String title = "Navigate to Dealer Onboarding page";
		TestUtils.testTitle(title);

		getDriver().findElement(By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']")).click();
		getDriver().findElement(By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']")).click();
		getDriver().findElement(By.id("android:id/button1")).click();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Home']")));
		getDriver().findElement(By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']")).click();
		//Thread.sleep(500);
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Dealer OnBoarding']")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/page_title")));
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/page_title", "Dealer Onboarding");
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/page_guide_title", "Get started in 3 easy steps");
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/step_one", "1. Dealer Code validation");
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/step_two", "2. OTP validation");
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/step_three", "3. Biometric capture");
		//Thread.sleep(500);
	}

	@Parameters({ "dataEnv"})
	@Test
	public static void agentOnBoardingTest(String dataEnv) throws Exception {

		WebDriverWait wait = new WebDriverWait(getDriver(), 30);
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resource/" + dataEnv + "/data.conf.json"));
		JSONObject envs = (JSONObject) config.get("AgentOnboarding");

		String agent_email = (String) envs.get("agent_email");
		String agent1_email = (String) envs.get("agent1_email");
		String agent_phoNum = (String) envs.get("agent_phoNum");
		String onboardedAgent = (String) envs.get("onboardedAgent");
		String invalid_email = (String) envs.get("invalid_email");
		String invalid_OTP  = (String) envs.get("invalid_OTP");
		String used_OTP = (String) envs.get("used_OTP");
		String nin = (String) envs.get("nin");
		String ninVerificationMode = (String) envs.get("ninVerificationMode");

		// To On-board an already existing agent
		String email1 = "To Onboard an already existing agent: ( " + onboardedAgent + " )";
		TestUtils.testTitle(email1);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/email")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/email")).sendKeys(onboardedAgent);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/submit")).click();
		//Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
		//Pass OTP
		//OTP(dataEnv);
		/*String valid_OTP = ConnectDB.getOTPWithoutPhoneNumber();
		if(valid_OTP == null){
			testInfo.get().log(Status.INFO, "Can't get otp.");
			getDriver().quit();
		}
		Thread.sleep(1000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/alertTitle")));
		getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/otp")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/otp")).sendKeys(valid_OTP);
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity.glo:id/otp", valid_OTP);
		getDriver().findElement(By.id("com.sf.biocapture.activity.glo:id/confirm_otp")).click();

		TestUtils.verifyNINTest(nin, ninVerificationMode);*/

		TestUtils.assertSearchText("ID", "android:id/message", "Agent already onboarded");
		getDriver().findElement(By.id("android:id/button1")).click();
		Thread.sleep(500);


		// To On-board agent without supplying email address
		String email2 = "To On-board agent without supplying email address";
		TestUtils.testTitle(email2);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/email")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/email")).sendKeys();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/submit")).click();
		//Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/message", "Required Input Field: ID");
		getDriver().findElement(By.id("android:id/button1")).click();
		//Thread.sleep(500);

		// To On-board user with an invalid email address
		String email3 = "To On-board agent with an invalid email address: ( " + invalid_email + " )";
		TestUtils.testTitle(email3);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/email")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/email")).sendKeys(invalid_email);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/submit")).click();
		//Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/message", "Invalid email address was entered.");
		getDriver().findElement(By.id("android:id/button1")).click();
		//Thread.sleep(500);

		// To On-board new agent with an invalid OTP
		String email4 = "To On-board new agent: ( " + agent_email + " ) with an invalid OTP: " + invalid_OTP;
		TestUtils.testTitle(email4);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/email")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/email")).sendKeys(agent_email);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/submit")).click();
		//Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/otp")));
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/alertTitle", "OTP Verification");
		//Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/otp")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/otp")).sendKeys(invalid_OTP);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/confirm_otp")).click();
		//Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/message", "There is no record with the otp, msisdn combination.");
		getDriver().findElement(By.id("android:id/button1")).click();
		//Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/cancel")));
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/cancel")).click();
		//Thread.sleep(1000);

		// To On-board new agent with a Used OTP
		String email6 = "To On-board new agent: ( " + agent_email + " ) with used OTP: " + used_OTP;
		TestUtils.testTitle(email6);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/email")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/email")).sendKeys(agent_email);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/submit")).click();
		//Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/otp")));
		Thread.sleep(1000);
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/alertTitle", "OTP Verification");
		//Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/otp")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/otp")).sendKeys(used_OTP);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/confirm_otp")).click();
		//Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/message", "There is no record with the otp, msisdn combination.");
		getDriver().findElement(By.id("android:id/button1")).click();
		//Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/cancel")));
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/cancel")).click();
		//Thread.sleep(1000);

		// Email Validation
		String email = "To On-board new agent with a valid OTP: ( " + agent_email + " )";
		TestUtils.testTitle(email);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/email")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/email")).sendKeys(agent_email);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/submit")).click();

		/*//Call OTP
		ConfirmOTPDialogue(dataEnv);
		try {
			//NIN Verification
			TestBase.verifyNINTest(nin, ninVerificationMode);
		}catch (Exception e){

		}*/

		// DB Connection for OTP
		String valid_OTP = ConnectDB.getOTP(agent_phoNum);
		//String valid_OTP = ConnectDB.getOTPWithoutPhoneNumber();
		String ValidOTP = "Enter valid OTP : " + valid_OTP;
		TestUtils.testTitle(ValidOTP);
		if(valid_OTP == null){
			testInfo.get().log(Status.INFO, "Can't get otp.");
			getDriver().quit();
		}
		Thread.sleep(1000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/otp")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/otp")).sendKeys(valid_OTP);
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/otp", valid_OTP);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/confirm_otp")).click();

		Thread.sleep(2000);

		//NIN Verification
		verifyNINTest(nin, ninVerificationMode);

		//Verify Biometric
		try{
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/captureButton")));

			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/captureButton")).click();
		}catch (Exception e){

			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/buttonCapturePicture")));

			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/buttonCapturePicture")).click();
		}
		Thread.sleep(3000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/message", "Subscriber's face was successfully captured");
		getDriver().findElement(By.id("android:id/button1")).click();
		Thread.sleep(500);

		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/button1")));
			getDriver().findElement(By.id("android:id/button1")).click();
			//TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/on_boarding_camera_title", "Camera");
			TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Fingerprint Capture']","Fingerprint Capture");
			getDriver().findElement(By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"));
			Thread.sleep(1000);
			getDriver().findElement(By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"));
			getDriver().findElement(By.xpath("android:id/button1"));
		}catch(Exception e){

		}

	}

	@Parameters({ "dataEnv"})
	@Test
	public static void dealerOnBoardingTest(String dataEnv) throws Exception {

		WebDriverWait wait = new WebDriverWait(getDriver(), 30);
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resource/" + dataEnv + "/data.conf.json"));
		JSONObject envs = (JSONObject) config.get("AgentOnboarding");

		String agent1_email = (String) envs.get("agent1_email");
		String dealer_phoNum = (String) envs.get("dealer_phoNum");
		String onboardedDealer = (String) envs.get("onboardedDealer");
		String invalid_OTP  = (String) envs.get("invalid_OTP");
		String used_DealerOtp = (String) envs.get("used_DealerOtp");
		String nin = (String) envs.get("nin");
		String ninVerificationMode = (String) envs.get("ninVerificationMode");
		String dealerCode = (String) envs.get("dealerCode");
		String invalid_dealerCode = (String) envs.get("invalid_dealerCode");

		//Navi
		// To On-board an already existing agent
		String email1 = "To Onboard an already existing agent: ( " + onboardedDealer + " )";
		TestUtils.testTitle(email1);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/email")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/email")).sendKeys(onboardedDealer);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/submit")).click();
		//Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));


		TestUtils.assertSearchText("ID", "android:id/message", "Onboarding has been completed for this account. No further actions are required.");
		getDriver().findElement(By.id("android:id/button1")).click();
		Thread.sleep(500);


		// To On-board dealer without supplying dealerCode
		String email2 = "To On-board dealer without supplying dealerCode";
		TestUtils.testTitle(email2);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/email")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/email")).sendKeys();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/submit")).click();
		//Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/message", "Required Input Field: ID");
		getDriver().findElement(By.id("android:id/button1")).click();
		//Thread.sleep(500);

		// To On-board user with an invalid invalid_dealerCode
		String email3 = "To On-board dealer with an invalid dealerCode: ( " + invalid_dealerCode + " )";
		TestUtils.testTitle(email3);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/email")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/email")).sendKeys(invalid_dealerCode);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/submit")).click();
		//Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/message", "Invalid Dealer code was entered.");
		getDriver().findElement(By.id("android:id/button1")).click();
		//Thread.sleep(500);

		// To On-board new dealer with an invalid OTP
		String email4 = "To On-board new dealer: ( " + dealerCode + " ) with an invalid OTP: " + invalid_OTP;
		TestUtils.testTitle(email4);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/email")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/email")).sendKeys(dealerCode);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/submit")).click();
		//Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/otp")));
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/alertTitle", "OTP Verification");
		//Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/otp")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/otp")).sendKeys(invalid_OTP);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/confirm_otp")).click();
		//Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/message", "There is no record with the otp, msisdn combination.");
		getDriver().findElement(By.id("android:id/button1")).click();
		//Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/cancel")));
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/cancel")).click();
		//Thread.sleep(1000);

		// To On-board new dealer with a Used OTP
		String email6 = "To On-board new dealer: ( " + dealerCode + " ) with used OTP: " + used_DealerOtp;
		TestUtils.testTitle(email6);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/email")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/email")).sendKeys(dealerCode);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/submit")).click();
		//Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/otp")));
		Thread.sleep(1000);
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/alertTitle", "OTP Verification");
		//Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/otp")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/otp")).sendKeys(used_DealerOtp);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/confirm_otp")).click();
		//Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/message", "There is no record with the otp, msisdn combination.");
		getDriver().findElement(By.id("android:id/button1")).click();
		//Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/cancel")));
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/cancel")).click();
		//Thread.sleep(1000);

		// Email Validation
		String email = "To On-board new dealer with a valid OTP: ( " + dealerCode + " )";
		TestUtils.testTitle(email);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/email")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/email")).sendKeys(dealerCode);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/submit")).click();
		Thread.sleep(2000);
		/*//Call OTP
		ConfirmOTPDialogue(dataEnv);
		try {
			//NIN Verification
			TestBase.verifyNINTest(nin, ninVerificationMode);
		}catch (Exception e){

		}*/

		// DB Connection for OTP
		String valid_OTP = ConnectDB.getOTP(dealer_phoNum);
		//String valid_OTP = ConnectDB.getOTPWithoutPhoneNumber();
		String ValidOTP = "Enter valid OTP : " + valid_OTP;
		TestUtils.testTitle(ValidOTP);
		if(valid_OTP == null){
			testInfo.get().log(Status.INFO, "Can't get otp.");
			getDriver().quit();
		}
		Thread.sleep(1000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/otp")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/otp")).sendKeys(valid_OTP);
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/otp", valid_OTP);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/confirm_otp")).click();

		Thread.sleep(2000);

		//NIN Verification
		verifyNINTest(nin, ninVerificationMode);

		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/button1")));
			getDriver().findElement(By.id("android:id/button1")).click();
			//TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/on_boarding_camera_title", "Camera");
			TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Fingerprint Capture']","Fingerprint Capture");

		}catch(Exception e){

		}

	}

	@Parameters({ "dataEnv"})
	@Test
	public static void ConfirmOTPDialogue(String dataEnv) throws Exception {

		WebDriverWait wait = new WebDriverWait(getDriver(), 30);
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resource/" + dataEnv + "/data.conf.json"));
		JSONObject envs = (JSONObject) config.get("AgentOnboarding");

		String agent_phoNum = (String) envs.get("agent_phoNum");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/alertTitle", "OTP Verification");

		// DB Connection for OTP
		String valid_OTP = ConnectDB.getOTP(agent_phoNum);

		String ValidOTP = "Enter valid OTP : " + valid_OTP;
		TestUtils.testTitle(ValidOTP);

		// OTP Validation
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/otp")));
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/otp")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/otp")).sendKeys(valid_OTP);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/confirm_otp")).click();

	}

	public static int verifyNINTest(String nin, String ninVerificationMode) throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), 20);
		//ninStatus is set to available automatically
		int ninStatus=1;
		//Proceed to NIN Verification View
		TestUtils.testTitle("Select NIN Verification Mode: "+ninVerificationMode);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/alertTitle", "NIN Verification");

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
		TestUtils.assertSearchText("ID", "android:id/title", "Search By NIN");
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/nin")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/nin")).sendKeys(nin);
		getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/capture_button")).click();

		try{
			//NIN Details View
			TestUtils.testTitle("Confirm the searched NIN is returned: "+nin);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/title")));
			TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/nin_verification_title", "NIN Verification");
			TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/tv_compare_text", "Please compare user data before proceeding");
			TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/tv_portrait_image", "Portrait Image");

			TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/tv_live_image", "LIVE IMAGE");
			TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/tv_nimc", "NIMC");
			String userdata=getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/tv_user_data")).getText();
			if(userdata.contains("User data")){
				TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/tv_user_data", "User data");
			}else{
				TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/tv_user_data", "User Data");
			}

			TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/tv_nimc_data", "NIMC Data");

			try {
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
			}catch (Exception e){
				testInfo.get().error("NIMC AND SIM REG MISMACTH");
				TestUtils.addScreenshot();
			}

			//Proceed
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/accept_button")));
			Thread.sleep(2500);
			getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/accept_button")).click();

		}catch (Exception e){
			System.out.println(e);
			//Nin is not available

		}

		return ninStatus;
	}


}