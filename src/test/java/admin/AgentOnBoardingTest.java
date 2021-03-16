package admin;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
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

		String forgotPasswordPage = "Navigate to Agent Onboarding page";
		TestUtils.testTitle(forgotPasswordPage);

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Home']")));
		getDriver().findElement(By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']")).click();
		//Thread.sleep(500);
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Agent OnBoarding']")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity." + Id + ":id/page_title")));
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity." + Id + ":id/page_title", "Agent Onboarding");
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity." + Id + ":id/page_guide_title", "Get started in 3 easy steps");
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity." + Id + ":id/step_one", "1. Agent ID validation");
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity." + Id + ":id/step_two", "2. OTP validation");
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity." + Id + ":id/step_three", "3. Biometric capture");
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
		String agent_phoNum = (String) envs.get("agent_phoNum");
		String onboardedAgent = (String) envs.get("onboardedAgent");
		String invalid_email = (String) envs.get("invalid_email");
		String invalid_OTP  = (String) envs.get("invalid_OTP");
		String used_OTP = (String) envs.get("used_OTP");

		// To On-board an already existing agent
		/*String email1 = "To Onboard an already existing agent: ( " + onboardedAgent + " )";
		TestUtils.testTitle(email1);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/email")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/email")).sendKeys(onboardedAgent);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/submit")).click();
		//Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity." + Id + ":id/alertTitle")));
		//Pass OTP
		OTP(dataEnv);
		TestUtils.assertSearchText("ID", "android:id/message", "Agent already onboarded");*/
		//getDriver().findElement(By.id("android:id/button1")).click();


		// To On-board agent without supplying email address
		String email2 = "To On-board agent without supplying email address";
		TestUtils.testTitle(email2);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/email")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/email")).sendKeys();
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/submit")).click();
		//Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity." + Id + ":id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/message", "Required Input Field: ID");
		getDriver().findElement(By.id("android:id/button1")).click();
		//Thread.sleep(500);

		// To On-board user with an invalid email address
		String email3 = "To On-board agent with an invalid email address: ( " + invalid_email + " )";
		TestUtils.testTitle(email3);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/email")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/email")).sendKeys(invalid_email);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/submit")).click();
		//Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity." + Id + ":id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/message", "Invalid email address was entered.");
		getDriver().findElement(By.id("android:id/button1")).click();
		//Thread.sleep(500);

		// To On-board new agent with an invalid OTP
		String email4 = "To On-board new agent: ( " + agent_email + " ) with an invalid OTP: " + invalid_OTP;
		TestUtils.testTitle(email4);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/email")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/email")).sendKeys(agent_email);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/submit")).click();
		//Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity." + Id + ":id/otp")));
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity." + Id + ":id/alertTitle", "OTP verification");
		//Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/otp")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/otp")).sendKeys(invalid_OTP);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/confirm_otp")).click();
		//Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity." + Id + ":id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/message", "There is no record with the otp, msisdn combination.");
		getDriver().findElement(By.id("android:id/button1")).click();
		//Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity." + Id + ":id/cancel")));
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/cancel")).click();
		//Thread.sleep(1000);

		String email6 = "To On-board new agent: ( " + agent_email + " ) with used OTP: " + used_OTP;
		TestUtils.testTitle(email6);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/email")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/email")).sendKeys(agent_email);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/submit")).click();
		//Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity." + Id + ":id/otp")));
		Thread.sleep(1000);
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity." + Id + ":id/alertTitle", "OTP verification");
		//Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/otp")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/otp")).sendKeys(used_OTP);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/confirm_otp")).click();
		//Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity." + Id + ":id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/message", "There is no record with the otp, msisdn combination.");
		getDriver().findElement(By.id("android:id/button1")).click();
		//Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity." + Id + ":id/cancel")));
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/cancel")).click();
		//Thread.sleep(1000);

		// Email Validation
		String email = "To On-board new agent with a valid OTP: ( " + agent_email + " )";
		TestUtils.testTitle(email);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/email")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/email")).sendKeys(agent_email);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/submit")).click();

		//Call OTP
		ConfirmOTPDialogue(dataEnv);

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity." + Id + ":id/on_boarding_camera_title")));
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity." + Id + ":id/on_boarding_camera_title", "Camera");
		TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Capture Passport']", "Capture Passport");

		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/button_capture_image")).click();
		//getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/buttonCapturePicture")).click();

	}

	@Parameters({ "dataEnv"})
	@Test
	public static void ConfirmOTPDialogue(String dataEnv) throws Exception {

		WebDriverWait wait = new WebDriverWait(getDriver(), 30);
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resource/" + dataEnv + "/data.conf.json"));
		JSONObject envs = (JSONObject) config.get("AgentOnboarding");

		String agent_phoNum = (String) envs.get("agent_phoNum");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity." + Id + ":id/alertTitle")));
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity." + Id + ":id/alertTitle", "OTP verification");

		// DB Connection for OTP
		String valid_OTP = ConnectDB.getOTP(agent_phoNum);

		String ValidOTP = "Enter valid OTP : " + valid_OTP;
		TestUtils.testTitle(ValidOTP);

		// OTP Validation
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity." + Id + ":id/otp")));
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/otp")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/otp")).sendKeys(valid_OTP);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/confirm_otp")).click();

	}

}