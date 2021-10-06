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

public class VerifyNinStandAlone extends TestBase {

    @Parameters({ "dataEnv"})
    @Test
    public void noneVerifyNinPrivilegeTest(String dataEnv) throws Exception {
        WebDriverWait wait = new WebDriverWait(getDriver(), 60);
        JSONParser parser = new JSONParser();
        JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resource/" + dataEnv + "/data.conf.json"));
        JSONObject envs = (JSONObject) config.get("VerifyNinStandAlone");

        String valid_urn = (String) envs.get("valid_urn");
        String valid_psw = (String) envs.get("valid_psw");
        TestBase.Login1(valid_urn, valid_psw);

        // Check that Verify NIN is not selectable
        Thread.sleep(500);
        TestUtils.testTitle("To confirm that a user without Verify NIN privilege can't access the module");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Home']")));
        getDriver().findElement(By.xpath("//android.widget.ImageButton[@content-desc=\"Navigate up\"]")).click();
        Thread.sleep(500);
        TestUtils.scrollDown();
        getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Verify NIN']")).click();
        Thread.sleep(500);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/alertTitle", "No Privilege");
        TestUtils.assertSearchText("ID", "android:id/message", "You are not allowed to access Standalone NIN Verification because you do not have the Standalone NIN Verification privilege");
        Thread.sleep(500);
        getDriver().findElement(By.id("android:id/button1")).click();
        Thread.sleep(500);

        //Log out
        getDriver().findElement(By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/design_menu_item_text")));
        TestUtils.scrollDown();
        getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Logout']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/message")));
        TestUtils.assertSearchText("ID", "android:id/message", "   Log out?");
        getDriver().findElement(By.id("android:id/button3")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/otp_login")));
    }

    @Test
    public static void navigateToVerifyNin() throws InterruptedException {

        WebDriverWait wait = new WebDriverWait(getDriver(), 30);
        // Navigate to Registration Type
        String regType = "Navigate to Verify Nin Page";
        Markup r = MarkupHelper.createLabel(regType, ExtentColor.BLUE);
        testInfo.get().info(r);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Home']")));
        getDriver().findElement(By.xpath("//android.widget.ImageButton[@content-desc=\"Navigate up\"]")).click();
        Thread.sleep(500);
        TestUtils.scrollDown();
        getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Verify NIN']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='NIN Verification']")));
        TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='NIN Verification']", "NIN Verification");
    }

    @Parameters ({"dataEnv"})
    @Test
    public static void verifyNinStandAloneTest(String dataEnv) throws Exception {

        WebDriverWait wait = new WebDriverWait(getDriver(), 30);
        JSONParser parser = new JSONParser();
        JSONObject config = (JSONObject) parser
                .parse(new FileReader("src/test/resource/" + dataEnv + "/data.conf.json"));
        JSONObject envs = (JSONObject) config.get("VerifyNinStandAlone");

        String nin = (String) envs.get("nin");

        //Verify NIN
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/sp_verification_types")).click();
        TestUtils.assertSearchText("XPATH", "//android.widget.CheckedTextView[2]", "Search By NIN");
        getDriver().findElement(By.xpath("//android.widget.CheckedTextView[2]")).click();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/search_field")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/search_field")).sendKeys(nin);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/btn_search")));
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/btn_search")).click();

        //NIMC Details View
        TestUtils.testTitle("Confirm the searched NIMC Data is returned: "+"NIMC Data");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/nin_verification_title")));
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity:id/nin_verification_title", "NIN Verification");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/tv_user_data")));
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/tv_user_data","User data");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/tv_nimc_data")));
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/tv_nimc_data","NIMC Data");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Firstname']")));
        TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Anthony']", "Anthony");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Surname']")));
        TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Uzoh']", "Uzoh");

        //Next Button
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/ok_button")));
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/ok_button")).click();

        //Search NIN with more than 11 digits
        TestUtils.testTitle("Search NIN with more than 11 digits: 333333333333333");
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/search_field")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/search_field")).sendKeys("333333333333333");
        TestUtils.assertSearchText("ID", ":id/search_field", "33333333333");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/btn_search")));
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/btn_search")).click();

        //NIMC Details View
        TestUtils.testTitle("Confirm the searched NIMC Data is returned: "+"NIMC Data");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/nin_verification_title")));
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity:id/nin_verification_title", "NIN Verification");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/tv_user_data")));
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/tv_user_data","User data");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/tv_nimc_data")));
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/tv_nimc_data","NIMC Data");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Firstname']")));
        TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Amaike']", "Amaike");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Surname']")));
        TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Chima']", "Chima");

        //Next Button
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/ok_button")));
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/ok_button")).click();

        //Search NIN less more than 11 digits
        TestUtils.testTitle("Search NIN with more than 11 digits: 11111");
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/search_field")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/search_field")).sendKeys("11111");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/btn_search")));
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/btn_search")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/alertTitle")));
        TestUtils.assertSearchText("ID", "android:id/message", "NIN must be 11 digits. Please provide a valid NIN");
        getDriver().findElement(By.id("android:id/button1")).click();

        //Search NIN with wrong 11 digit details
        TestUtils.testTitle("Search NIN with wrong 11 digit details: 12345678900");
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/search_field")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/search_field")).sendKeys("12345678900");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/btn_search")));
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/btn_search")).click();
        Thread.sleep(45000);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/alertTitle")));
        TestUtils.assertSearchText("ID", "android:id/message", "Unable to reach NIMC Service");
        getDriver().findElement(By.id("android:id/button1")).click();
    }
}