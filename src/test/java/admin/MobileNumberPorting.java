package admin;

import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
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

import utils.TestUtils;


public class MobileNumberPorting extends TestBase {

    @Parameters({ "dataEnv"})
    @Test
    public void mobileNumberPortability(String dataEnv) throws Exception {

        WebDriverWait wait = new WebDriverWait(getDriver(), 60);
        JSONParser parser = new JSONParser();
        JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resource/" + dataEnv + "/data.conf.json"));
        JSONObject envs = (JSONObject) config.get("MNP");

        String valid_username = (String) envs.get("valid_username");
        String valid_password = (String) envs.get("valid_password");

        //To confirm that there is a Registration type called Mobile Number Porting
        TestUtils.testTitle("To confirm that there is a Registration type called Mobile Number Porting");
        getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/button_start_capture")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity." + Id + ":id/typeofreg")));
        getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/typeofreg")).click();

        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity." + Id + ":id/alertTitle", "Select Registration Type");
        getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Mobile Number Porting']")).click();

        getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/next_button")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Mobile Number Porting']")));
        TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Mobile Number Porting']", "Mobile Number Porting");

        getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/primary_msisdn_field")).sendKeys();
        Thread.sleep(50000);
    }
}
