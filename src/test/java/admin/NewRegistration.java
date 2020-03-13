package admin;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import demographics.Form;
import utils.Asserts;
import utils.TestBase;
import utils.TestUtils;

import java.io.FileReader;

public class NewRegistration extends TestBase {

	@Test
	public static void navigateToCaptureMenuTest() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), 30);
		// Navigate to Registration Type
		String regType = "Navigate to Registration Type";
		Markup r = MarkupHelper.createLabel(regType, ExtentColor.BLUE);
		testInfo.get().info(r);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Home']")));
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/button_start_capture")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity." + Id + ":id/reg_type_placeholder")));
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity." + Id + ":id/reg_type_placeholder","Registration Type");
	}

	@Parameters({ "dataEnv"})
	@Test
	public void captureNewSimTest(String dataEnv) throws Exception {

		WebDriverWait wait = new WebDriverWait(getDriver(), 60);
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resource/" + dataEnv + "/data.conf.json"));
		JSONObject envs = (JSONObject) config.get("NewRegistration");
		
		String invalid_msisdn = (String) envs.get("invalid_msisdn");
		String valid_msisdn = (String) envs.get("valid_msisdn");
		String invalid_simSerial = (String) envs.get("invalid_msisdn");
		String valid_simSerial = (String) envs.get("valid_msisdn");
		String lga = (String) envs.get("lga");

		// Select LGA of Registration
		TestUtils.testTitle("Select LGA of Registration: " + lga);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/lga_of_reg")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/alertTitle", "LGA of Registration*");
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='" + lga + "']")).click();
		Thread.sleep(500);

		// Select New Registration 
		TestUtils.testTitle("Select New Registration");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity." + Id + ":id/typeofreg")));
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/typeofreg")).click();
		Thread.sleep(500);
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity." + Id + ":id/alertTitle", "Select Registration Type");
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='New Registration']")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/next_button")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity." + Id + ":id/page_title")));
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity." + Id + ":id/page_title", "New Registration");

		// Proceed after supplying empty details
		TestUtils.testTitle("Proceed after supplying empty details");
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/msisdn")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/msisdn")).sendKeys("");
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/sim_serial")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/sim_serial")).sendKeys("");
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/add_msisdn_sim_serial_button")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity." + Id + ":id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/message", "Required Input Field: Phone Number");
		getDriver().findElement(By.id("android:id/button1")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity." + Id + ":id/msisdn")));
		Thread.sleep(500);
		
		// Proceed after supplying only Msisdn
		TestUtils.testTitle("Proceed after supplying only Msisdn: " + valid_msisdn);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/msisdn")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/msisdn")).sendKeys(valid_msisdn);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/sim_serial")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/sim_serial")).sendKeys("");
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/add_msisdn_sim_serial_button")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity." + Id + ":id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/message", "Sim Serial format is invalid");
		getDriver().findElement(By.id("android:id/button1")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity." + Id + ":id/msisdn")));
		Thread.sleep(500);
		
		// Proceed after supplying only Sim Serial
		TestUtils.testTitle("Proceed after supplying only Sim Serial: " + valid_simSerial);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/msisdn")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/msisdn")).sendKeys(" ");
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/sim_serial")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/sim_serial")).sendKeys(valid_simSerial);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/add_msisdn_sim_serial_button")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity." + Id + ":id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/message", "Required Input Field: Phone Number");
		getDriver().findElement(By.id("android:id/button1")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity." + Id + ":id/msisdn")));
		Thread.sleep(500);
		
		// Proceed after supplying invalid msisdn and sim serial
		TestUtils.testTitle("Proceed after supplying: (" + invalid_msisdn + ") and invalid sim serial: (" + invalid_simSerial + "for validation");
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/msisdn")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/msisdn")).sendKeys(invalid_msisdn);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/sim_serial")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/sim_serial")).sendKeys(invalid_msisdn);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/add_msisdn_sim_serial_button")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity." + Id + ":id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/message", "Entered Phone Number is invalid. Entered value should not be less than 6 or more than 11 digits.");
		getDriver().findElement(By.id("android:id/button1")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity." + Id + ":id/msisdn")));
		Thread.sleep(500);

		// Proceed after supplying valid msisdn and Sim serial
		TestUtils.testTitle("Proceed after supplying valid msisdn: (" + valid_msisdn + ") and valid Sim serial: (" + valid_simSerial + ")");
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/msisdn")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/msisdn")).sendKeys(valid_msisdn);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/sim_serial")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/sim_serial")).sendKeys(valid_simSerial);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/add_msisdn_sim_serial_button")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity." + Id + ":id/next_button")));
		TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Added Numbers']", "Added Numbers");
		Asserts.assertAddedNumbers();
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/next_button")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Personal Details']")));
		Thread.sleep(500);
		//Form.NigerianCompanyForm(dataEnv);
	}
}
