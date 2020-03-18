package admin;

import utils.Asserts;
import utils.TestBase;

import java.io.FileReader;
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
import utils.TestUtils;

public class AdditionalRegistration extends TestBase {

	@Parameters({ "dataEnv"})
    @Test
    public void additionalRegistrationTest(String dataEnv) throws Exception {

        WebDriverWait wait = new WebDriverWait(getDriver(), 60);
        
        JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resource/" + dataEnv + "/data.conf.json"));
		JSONObject envs = (JSONObject) config.get("AdditionalRegistration");
		
		String invalid_Msisdn= (String) envs.get("invalid_Msisdn");
		String pri_valid_Msisdn = (String) envs.get("pri_valid_Msisdn");
		String invalid_simSerial= (String) envs.get("invalid_simSerial");
		String pri_valid_simSerial = (String) envs.get("pri_valid_simSerial");
		String new_valid_Msisdn= TestUtils.generatePhoneNumber();
		String new_valid_simSerial = TestUtils.generateSimSrial();
		String lga = (String) envs.get("lga");

		// Select LGA of Registration
		TestUtils.testTitle("Select LGA of Registration: " + lga);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/lga_of_reg")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/alertTitle", "LGA of Registration*");
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='" + lga + "']")).click();
		Thread.sleep(500);
		
		// Select Additional Registration
		TestUtils.testTitle("Select Additional Registration");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity." + Id + ":id/typeofreg")));
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/typeofreg")).click();
		Thread.sleep(500);
		TestUtils.assertSearchText("ID", "com.sf.biocapture.activity." + Id + ":id/alertTitle", "Select Registration Type");
		getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Additional Registration']")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/next_button")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Additional Registration']")));
		TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Additional Registration']", "Additional Registration");
		Thread.sleep(500);
		
		// Proceed after supplying empty details
		TestUtils.testTitle("Proceed after supplying empty details");
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/primary_msisdn_field")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/primary_msisdn_field")).sendKeys();
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/primary_serial_field")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/primary_serial_field")).sendKeys();
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/submit_button")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/message")));
		TestUtils.assertSearchText("ID", "android:id/message", "Required Input Field: Phone Number");
		getDriver().findElement(By.id("android:id/button1")).click();
		Thread.sleep(1000);
		
		// Proceed after supplying only Msisdn
		TestUtils.testTitle("Proceed after supplying only Msisdn: " + pri_valid_Msisdn);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/primary_msisdn_field")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/primary_msisdn_field")).sendKeys(pri_valid_Msisdn);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/primary_serial_field")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/primary_serial_field")).sendKeys();
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/submit_button")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/message")));
		TestUtils.assertSearchText("ID", "android:id/message", "Sim Serial format is invalid");
		getDriver().findElement(By.id("android:id/button1")).click();
		Thread.sleep(1000);
				
		// Proceed after supplying only Sim Serial
		TestUtils.testTitle("Proceed after supplying only Sim Serial: " + pri_valid_simSerial);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/primary_msisdn_field")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/primary_msisdn_field")).sendKeys();
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/primary_serial_field")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/primary_serial_field")).sendKeys(pri_valid_simSerial);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/submit_button")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/message")));
		TestUtils.assertSearchText("ID", "android:id/message", "Required Input Field: Phone Number");
		getDriver().findElement(By.id("android:id/button1")).click();
		Thread.sleep(1000);
				
		// Proceed after supplying invalid msisdn and sim serial
		TestUtils.testTitle("Proceed after supplying invalid msisdn: (" + invalid_Msisdn + ") and invalid sim serial: (" + invalid_simSerial + ") for validation");
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/primary_msisdn_field")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/primary_msisdn_field")).sendKeys(invalid_Msisdn);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/primary_serial_field")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/primary_serial_field")).sendKeys(invalid_simSerial);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/submit_button")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/message")));
		TestUtils.assertSearchText("ID", "android:id/message", "Sim Serial format is invalid");
		getDriver().findElement(By.id("android:id/button1")).click();
		Thread.sleep(1000);

		// Proceed after supplying valid msisdn and Sim serial
		TestUtils.testTitle("Proceed after supplying valid msisdn: (" + pri_valid_Msisdn + ") and valid Sim serial: (" + pri_valid_simSerial + ")");
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/primary_msisdn_field")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/primary_msisdn_field")).sendKeys(pri_valid_Msisdn);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/primary_serial_field")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/primary_serial_field")).sendKeys(pri_valid_simSerial);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/submit_button")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity." + Id + ":id/summary_title")));		
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/summary_ok_button")).click();
		Thread.sleep(1000);
		Asserts.assertSubscriberFullNameAddReg();
		
		// Next Button
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/next_button")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity." + Id + ":id/addRegPageTitle")));	
		
		// Enter New Numbers for Additional Registration
		TestUtils.testTitle("Enter New Msisdn: (" + new_valid_Msisdn + ") and New Sim serial: (" + new_valid_simSerial + ") for Additional Registration");
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/msisdnField")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/msisdnField")).sendKeys(new_valid_Msisdn);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/simSerialField")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/simSerialField")).sendKeys(new_valid_simSerial);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/addSerialButton")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity." + Id + ":id/nextButton")));		
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/nextButton")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Home']")));
		TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Home']", "Home");
		Thread.sleep(500);
	}
      
}
