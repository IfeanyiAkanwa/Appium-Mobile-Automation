package admin;

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
		String valid_Msisdn = (String) envs.get("valid_Msisdn");
		String invalid_simSerial= (String) envs.get("invalid_simSerial");
		String valid_simSerial = (String) envs.get("valid_simSerial");
		String lga = (String) envs.get("lga");

		// Select LGA of Registration
		String lgaa = "Select LGA of Registration: " + lga;
		Markup m = MarkupHelper.createLabel(lgaa, ExtentColor.BLUE);
		testInfo.get().info(m);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/lga_of_reg")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/alertTitle", "LGA of Registration*");
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='" + lga + "']")).click();
		Thread.sleep(500);
		
		// Select Additional Registration
		String newReg = "Select Additional Registration";
		Markup d = MarkupHelper.createLabel(newReg, ExtentColor.BLUE);
		testInfo.get().info(d);
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
		TestUtils.testTitle("Proceed after supplying only Msisdn: " + valid_Msisdn);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/primary_msisdn_field")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/primary_msisdn_field")).sendKeys(valid_Msisdn);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/primary_serial_field")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/primary_serial_field")).sendKeys();
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/submit_button")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity." + Id + ":id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/message", "Sim Serial format is invalid");
		getDriver().findElement(By.id("android:id/button1")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity." + Id + ":id/msisdn")));
		Thread.sleep(500);
				
		// Proceed after supplying only Sim Serial
		TestUtils.testTitle("Proceed after supplying only Sim Serial: " + valid_simSerial);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/primary_msisdn_field")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/primary_msisdn_field")).sendKeys();
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/primary_serial_field")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/primary_serial_field")).sendKeys(valid_simSerial);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/submit_button")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity." + Id + ":id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/message", "Required Input Field: Phone Number");
		getDriver().findElement(By.id("android:id/button1")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity." + Id + ":id/msisdn")));
		Thread.sleep(500);
				
		// Proceed after supplying invalid msisdn and sim serial
		TestUtils.testTitle("Proceed after supplying invalid msisdn: (" + invalid_Msisdn + ") and invalid sim serial: (" + invalid_simSerial + ") for validation");
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/primary_msisdn_field")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/primary_msisdn_field")).sendKeys(invalid_Msisdn);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/primary_serial_field")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/primary_serial_field")).sendKeys(invalid_simSerial);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/submit_button")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity." + Id + ":id/alertTitle")));
		TestUtils.assertSearchText("ID", "android:id/message", "Sim Serial format is invalid");
		getDriver().findElement(By.id("android:id/button1")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity." + Id + ":id/msisdn")));
		Thread.sleep(500);

		// Proceed after supplying valid msisdn and Sim serial
		TestUtils.testTitle("Proceed after supplying valid msisdn: (" + valid_Msisdn + ") and valid Sim serial: (" + valid_simSerial + ")");
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/primary_msisdn_field")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/primary_msisdn_field")).sendKeys(valid_Msisdn);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/primary_serial_field")).clear();
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/primary_serial_field")).sendKeys(valid_simSerial);
		getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/submit_button")).click();
				

		
		
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/spinner_finger_type")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='RIGHT THUMB']")));
		Thread.sleep(1000);
		getDriver().findElement(By.xpath("//android.widget.TextView[@text='RIGHT THUMB']")).click();
		Thread.sleep(500);
		getDriver().findElement(By.id("com.sf.biocapture.activity:id/verify_finger_print_button")).click();
		Thread.sleep(500);
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/alertTitle")));
			Thread.sleep(500);
			TestUtils.assertSearchText("ID", "com.sf.biocapture.activity:id/alertTitle", "Scanner not found");
			getDriver().findElement(By.id("android:id/button1")).click();
			Thread.sleep(500);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/fingerType_text")));
			Thread.sleep(500);
			TestUtils.assertSearchText("ID", "com.sf.biocapture.activity:id/fingerType_text", "VERIFICATION");
			TestUtils.assertSearchText("ID", "com.sf.biocapture.activity:id/btnStop", "Capture");
			Thread.sleep(500);
		} catch (Exception e) {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/fingerType_text")));
			Thread.sleep(500);
			TestUtils.assertSearchText("ID", "com.sf.biocapture.activity:id/fingerType_text", "VERIFICATION");
			TestUtils.assertSearchText("ID", "com.sf.biocapture.activity:id/btnStop", "Capture");
			Thread.sleep(500);
		}
		
	}
      
}
