package admin;

import db.ConnectDB;
import org.json.simple.JSONArray;
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
    public static void verifyByNinTest(String dataEnv) throws Exception {

        WebDriverWait wait = new WebDriverWait(getDriver(), 30);
        JSONParser parser = new JSONParser();
        JSONObject config = (JSONObject) parser
                .parse(new FileReader("src/test/resource/" + dataEnv + "/data.conf.json"));
        JSONObject envs = (JSONObject) config.get("VerifyNinStandAlone");

        String nin = (String) envs.get("nin");
        String nin_with_long_char = (String) envs.get("nin_with_long_char");
        String first_name = (String) envs.get("first_name");
        String last_name = (String) envs.get("last_name");
        String less_char_nin = (String) envs.get("less_char_nin");
        String invalid_nin = (String) envs.get("invalid_nin");
        //Verify NIN
        TestUtils.testTitle("Verify NIN");
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/sp_verification_types")).click();
        TestUtils.assertSearchText("XPATH", "//android.widget.CheckedTextView[@text='Search By NIN']", "Search By NIN");
        getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Search By NIN']")).click();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/search_field")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/search_field")).sendKeys(nin);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/btn_search")));
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/btn_search")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
        TestUtils.assertSearchText("ID", "android:id/message", "NIN Verification successful");
        getDriver().findElement(By.id("android:id/button1")).click();

        //NIMC Details View
        TestUtils.testTitle("Confirm the searched NIMC Data is returned: "+"NIMC Data");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/nin_verification_title")));
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/nin_verification_title", "NIN Verification");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/tv_user_data")));
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/tv_user_data","User Data");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/tv_nimc_data")));
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/tv_nimc_data","NIMC Data");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Firstname']")));
        TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='"+first_name+"']", first_name);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Surname']")));
        TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='"+last_name+"']", last_name);

        try {
            //Next Button
            Thread.sleep(1000);
            getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/reject_button")).click();
        }catch (Exception e) {
            //Next Button
            Thread.sleep(1000);
            getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/ok_button")).click();
        }

        //Search NIN with more than 11 digits
        nin_with_long_char=nin+"2383";
        TestUtils.testTitle("Search NIN with more than 11 digits: "+nin_with_long_char);
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/search_field")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/search_field")).sendKeys(nin_with_long_char);
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/search_field", nin);


        //Search NIN less than 11 digits
        TestUtils.testTitle("Search NIN with less than 11 digits: "+less_char_nin);
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/search_field")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/search_field")).sendKeys(less_char_nin);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/btn_search")));
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/btn_search")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/error_text")));
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/error_text", "Only numbers and minimum of 11 characters are allowed");
        String enabled=getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/btn_search")).getAttribute("enabled");
        TestUtils.assertTwoValues(enabled, "false");

        //Search NIN with wrong 11 digit details
        TestUtils.testTitle("Search NIN with wrong 11 digit details: "+invalid_nin);
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/search_field")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/search_field")).sendKeys(invalid_nin);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/btn_search")));
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/btn_search")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
        TestUtils.assertSearchText("ID", "android:id/message", "Unable to reach NIMC Service");
        getDriver().findElement(By.id("android:id/button1")).click();

        try{
            try {
                //Next Button
                Thread.sleep(1000);
                getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/reject_button")).click();
            }catch (Exception e) {
                //Next Button
                Thread.sleep(1000);
                getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/ok_button")).click();
            }
        }catch (Exception e){

        }
    }
    @Parameters ({"dataEnv"})
    @Test
    public static void verifyByNinAndFingerprintTest(String dataEnv) throws Exception {

        WebDriverWait wait = new WebDriverWait(getDriver(), 45);
        JSONParser parser = new JSONParser();
        JSONObject config = (JSONObject) parser
                .parse(new FileReader("src/test/resource/" + dataEnv + "/data.conf.json"));
        JSONObject envs = (JSONObject) config.get("VerifyNinStandAlone");

        String nin = (String) envs.get("nin");
        String nin_with_long_char = (String) envs.get("nin_with_long_char");
        String first_name = (String) envs.get("first_name");
        String last_name = (String) envs.get("last_name");
        String less_char_nin = (String) envs.get("less_char_nin");
        String invalid_nin = (String) envs.get("invalid_nin");
        //Verify NIN
        TestUtils.testTitle("Verify NIN");
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/sp_verification_types")).click();
        TestUtils.assertSearchText("XPATH", "//android.widget.CheckedTextView[@text='NIN and Fingerprint']", "NIN and Fingerprint");
        getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='NIN and Fingerprint']")).click();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/search_field")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/search_field")).sendKeys(nin);
        TestUtils.scrollDown();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/btn_search")));
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/btn_search")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
        TestUtils.assertSearchText("ID", "android:id/message", "No finger was captured");
        getDriver().findElement(By.id("android:id/button1")).click();

        //Search NIN with more than 11 digits
        nin_with_long_char=nin+"2383";
        TestUtils.testTitle("Search NIN with more than 11 digits: "+nin_with_long_char);
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/search_field")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/search_field")).sendKeys(nin_with_long_char);
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/search_field", nin);


        //Search NIN less than 11 digits
        TestUtils.testTitle("Search NIN with less than 11 digits: "+less_char_nin);
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/search_field")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/search_field")).sendKeys(less_char_nin);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/btn_search")));
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/btn_search")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/error_text")));
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/error_text", "Only numbers and minimum of 11 characters are allowed");
        String enabled=getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/btn_search")).getAttribute("enabled");
        TestUtils.assertTwoValues(enabled, "false");

        //Search NIN with wrong 11 digit details
        TestUtils.testTitle("Search NIN with wrong 11 digit details: "+invalid_nin);
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/search_field")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/search_field")).sendKeys(invalid_nin);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/btn_search")));
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/btn_search")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
        TestUtils.assertSearchText("ID", "android:id/message", "No finger was captured");
        getDriver().findElement(By.id("android:id/button1")).click();

        try{
            try {
                //Next Button
                Thread.sleep(1000);
                getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/reject_button")).click();
            }catch (Exception e) {
                //Next Button
                Thread.sleep(1000);
                getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/ok_button")).click();
            }
        }catch (Exception e){

        }

        TestUtils.testTitle("Verify that left hand is display");
        enabled=getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/left_hand")).getAttribute("enabled");
        TestUtils.assertTwoValues(enabled, "true");

        TestUtils.testTitle("Verify that right hand is display");
        enabled=getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/right_hand")).getAttribute("enabled");
        TestUtils.assertTwoValues(enabled, "true");


        TestUtils.testTitle("Confirm user can capture prints");
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/right_thumb")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
        TestUtils.assertSearchText("ID", "android:id/message", "Fingerprint scanner device not detected. Ensure your scanner device is connected and try again.");
        getDriver().findElement(By.id("android:id/button1")).click();
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/btnStop", "CAPTURE");
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/btnStop")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
        getDriver().findElement(By.id("android:id/button1")).click();

    }

    @Parameters ({"dataEnv"})
    @Test
    public static void verifyByPhoneTest(String dataEnv) throws Exception {

        WebDriverWait wait = new WebDriverWait(getDriver(), 30);
        JSONParser parser = new JSONParser();
        JSONObject config = (JSONObject) parser
                .parse(new FileReader("src/test/resource/" + dataEnv + "/data.conf.json"));
        JSONObject envs = (JSONObject) config.get("VerifyNinStandAlone");

        String phone = (String) envs.get("phone");
        String phone_with_long_char = "";
        String first_name = (String) envs.get("first_name");
        String last_name = (String) envs.get("last_name");
        String less_char_phone = (String) envs.get("less_char_phone");
        String invalid_phone = (String) envs.get("invalid_phone");
        //Verify NIN by Phone
        TestUtils.testTitle("Verify NIN by Phone");
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/sp_verification_types")).click();
        TestUtils.assertSearchText("XPATH", "//android.widget.CheckedTextView[@text='Search By Phone Number']", "Search By Phone Number");
        getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Search By Phone Number']")).click();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/search_field")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/search_field")).sendKeys(phone);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/btn_search")));
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/btn_search")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
        TestUtils.assertSearchText("ID", "android:id/message", "NIN Verification successful");
        getDriver().findElement(By.id("android:id/button1")).click();

        //NIMC Details View
        TestUtils.testTitle("Confirm the searched NIMC Data is returned: "+"NIMC Data");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/nin_verification_title")));
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/nin_verification_title", "NIN Verification");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/tv_user_data")));
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/tv_user_data","User Data");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/tv_nimc_data")));
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/tv_nimc_data","NIMC Data");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Firstname']")));
        TestUtils.assertSearchText("XPATH", "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/androidx.recyclerview.widget.RecyclerView/android.view.ViewGroup[2]/android.widget.TextView[2]", first_name);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Surname']")));
        TestUtils.assertSearchText("XPATH", "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/androidx.recyclerview.widget.RecyclerView/android.view.ViewGroup[1]/android.widget.TextView[2]", last_name);

        try {
            //Next Button
            Thread.sleep(1000);
            getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/reject_button")).click();
        }catch (Exception e) {
            //Next Button
            Thread.sleep(1000);
            getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/ok_button")).click();
        }

        //Search Phone with more than 11 digits
        phone_with_long_char=phone+"2383";
        TestUtils.testTitle("Search Phone with more than 11 digits: "+phone_with_long_char);
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/search_field")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/search_field")).sendKeys(phone_with_long_char);
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/search_field", phone);


        //Search Phone less more than 11 digits
        TestUtils.testTitle("Search Phone with more than 11 digits: "+less_char_phone);
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/search_field")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/search_field")).sendKeys(less_char_phone);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/btn_search")));
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/btn_search")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/error_text")));
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/error_text", "Only numbers and minimum of 11 characters are allowed");
        String enabled=getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/btn_search")).getAttribute("enabled");
        TestUtils.assertTwoValues(enabled, "false");

        //Search Phone with wrong 11 digit details
        TestUtils.testTitle("Search Phone with wrong 11 digit details: "+invalid_phone);
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/search_field")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/search_field")).sendKeys(invalid_phone);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/btn_search")));
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/btn_search")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
        TestUtils.assertSearchText("ID", "android:id/message", "Unable to reach NIMC Service");
        getDriver().findElement(By.id("android:id/button1")).click();

        try{
            try {
                //Next Button
                Thread.sleep(1000);
                getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/reject_button")).click();
            }catch (Exception e) {
                //Next Button
                Thread.sleep(1000);
                getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/ok_button")).click();
            }
        }catch (Exception e){

        }
    }

    @Parameters ({"dataEnv"})
    @Test
    public static void verifyByDemographicsTest(String dataEnv) throws Exception {

        WebDriverWait wait = new WebDriverWait(getDriver(), 30);
        JSONParser parser = new JSONParser();
        JSONObject config = (JSONObject) parser
                .parse(new FileReader("src/test/resource/" + dataEnv + "/data.conf.json"));
        JSONObject envs = (JSONObject) config.get("VerifyNinStandAlone");

        String first_name = (String) envs.get("first_name");
        String last_name = (String) envs.get("last_name");
        String dob = (String) envs.get("dob");
        String gender = (String) envs.get("gender");

        //Select Demographics
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/sp_verification_types")).click();
        TestUtils.assertSearchText("XPATH", "//android.widget.CheckedTextView[@text='Search By Demographics']", "Search By Demographics");
        getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Search By Demographics']")).click();

        //verify NIN with empty demographics information
        TestUtils.testTitle("Verify NIN with empty demographics information");
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/btn_search")).click();
        String firstNameErr=getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/error_text_firstname")).getText();
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/error_text_firstname", firstNameErr);
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/error_text_lastname", "Last Name must contain only characters and have a minimum of 2 characters");
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/error_text_dob", "Date of Birth is required");
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/error_text_gender", "Gender is required");

        //Verify NIN
        TestUtils.testTitle("verify NIN with valid demographics information");
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/firstname")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/firstname")).sendKeys(first_name);
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/lastname")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/lastname")).sendKeys(last_name);
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/dateofBirthTXT")).sendKeys(dob);
        if (gender.equals("male")){
            getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/maleRadioButton")).click();
        }else{
            getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/femaleRadioButton")).click();
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/btn_search")));
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/btn_search")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
        TestUtils.assertSearchText("ID", "android:id/message", "NIN Verification successful");
        getDriver().findElement(By.id("android:id/button1")).click();

        //NIMC Details View
        TestUtils.testTitle("Confirm the searched NIMC Data is returned: "+"NIMC Data");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/nin_verification_title")));
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/nin_verification_title", "NIN Verification");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/tv_user_data")));
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/tv_user_data","User Data");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/tv_nimc_data")));
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/tv_nimc_data","NIMC Data");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Firstname']")));
        TestUtils.assertSearchText("XPATH", "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/androidx.recyclerview.widget.RecyclerView/android.view.ViewGroup[2]/android.widget.TextView[2]", first_name);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Surname']")));
        TestUtils.assertSearchText("XPATH", "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/androidx.recyclerview.widget.RecyclerView/android.view.ViewGroup[1]/android.widget.TextView[2]", last_name);

        try {
            //Next Button
            Thread.sleep(1000);
            getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/reject_button")).click();
        }catch (Exception e) {
            //Next Button
            Thread.sleep(1000);
            getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/ok_button")).click();
        }

    }

    @Parameters ({"dataEnv"})
    @Test
    public static void verifyByDocumentNoTest(String dataEnv) throws Exception {

        WebDriverWait wait = new WebDriverWait(getDriver(), 30);
        JSONParser parser = new JSONParser();
        JSONObject config = (JSONObject) parser
                .parse(new FileReader("src/test/resource/" + dataEnv + "/data.conf.json"));
        JSONObject envs = (JSONObject) config.get("VerifyNinStandAlone");

        String document_no = (String) envs.get("document_no");
        String phone_with_long_char = "";
        String first_name = (String) envs.get("first_name");
        String last_name = (String) envs.get("last_name");
        String less_char_phone = (String) envs.get("less_char_phone");
        String invalid_document_no = (String) envs.get("invalid_document_no");
        //Verify NIN by Document No
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/sp_verification_types")).click();
        TestUtils.assertSearchText("XPATH", "//android.widget.CheckedTextView[@text='Search By Document Number']", "Search By Document Number");
        getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Search By Document Number']")).click();


        //Search Document number with more than 11 digits
        String doc_with_long_char=document_no+"2383";
        TestUtils.testTitle("Search Document number with more than 11 digits: "+doc_with_long_char);
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/search_field")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/search_field")).sendKeys(doc_with_long_char);
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/search_field", document_no);


        //Search Document no less than 10 digits
        TestUtils.testTitle("Search Document number with less than 10 digits: "+less_char_phone);
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/search_field")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/search_field")).sendKeys(less_char_phone);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/btn_search")));
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/btn_search")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/error_text")));
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/error_text", "Only numbers and minimum of 10 characters are allowed");
        String enabled=getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/btn_search")).getAttribute("enabled");
        TestUtils.assertTwoValues(enabled, "false");

        //Search Document number with wrong 11 digit details
        TestUtils.testTitle("Search Document number with wrong 11 digit details: "+document_no);
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/search_field")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/search_field")).sendKeys(document_no);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/btn_search")));
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/btn_search")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
        TestUtils.assertSearchText("ID", "android:id/message", "System Error: Unable to reach NIMC API");
        getDriver().findElement(By.id("android:id/button1")).click();

        try{
            try {
                //Next Button
                Thread.sleep(1000);
                getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/reject_button")).click();
            }catch (Exception e) {
                //Next Button
                Thread.sleep(1000);
                getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/ok_button")).click();
            }
        }catch (Exception e){

        }


        TestUtils.testTitle("Confirm with invalid document number:"+invalid_document_no);
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/search_field")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/search_field")).sendKeys(invalid_document_no);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/btn_search")));
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/btn_search")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
        TestUtils.assertSearchText("ID", "android:id/message", "NIN Verification successful");
        getDriver().findElement(By.id("android:id/button1")).click();

        TestUtils.testTitle("Confirm with Valid document number:"+document_no);
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/search_field")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/search_field")).sendKeys(document_no);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/btn_search")));
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/btn_search")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
        TestUtils.assertSearchText("ID", "android:id/message", "NIN Verification successful");
        getDriver().findElement(By.id("android:id/button1")).click();

        try {
            //NIMC Details View
            TestUtils.testTitle("Confirm the searched NIMC Data is returned: "+"NIMC Data");
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/nin_verification_title")));
            TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/nin_verification_title", "NIN Verification");
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/tv_user_data")));
            TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/tv_user_data","User Data");
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/tv_nimc_data")));
            TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/tv_nimc_data","NIMC Data");
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Firstname']")));
            TestUtils.assertSearchText("XPATH", "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/androidx.recyclerview.widget.RecyclerView/android.view.ViewGroup[2]/android.widget.TextView[2]", first_name);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Surname']")));
            TestUtils.assertSearchText("XPATH", "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/androidx.recyclerview.widget.RecyclerView/android.view.ViewGroup[1]/android.widget.TextView[2]", last_name);

            try {
                //Next Button
                Thread.sleep(1000);
                getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/reject_button")).click();
            }catch (Exception e) {
                //Next Button
                Thread.sleep(1000);
                getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/ok_button")).click();
            }
        }catch (Exception e){
            testInfo.get().error("Could not proceed to NIMC detail page("+e+")");
        }

    }

    @Parameters ({"dataEnv"})
    @Test
    public static void verifyByTransactionIdTest(String dataEnv) throws Exception {

        WebDriverWait wait = new WebDriverWait(getDriver(), 30);
        JSONParser parser = new JSONParser();
        JSONObject config = (JSONObject) parser
                .parse(new FileReader("src/test/resource/" + dataEnv + "/data.conf.json"));
        JSONObject envs = (JSONObject) config.get("VerifyNinStandAlone");

        String transaction_id = (String) envs.get("transaction_id");
        String phone_with_long_char = "";
        String first_name = (String) envs.get("first_name");
        String last_name = (String) envs.get("last_name");
        String less_char_phone = (String) envs.get("less_char_phone");
        String invalid_transaction_id = (String) envs.get("invalid_document_no");
        //Verify NIN by Transaction ID
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/sp_verification_types")).click();
        TestUtils.assertSearchText("XPATH", "//android.widget.CheckedTextView[@text='Search By Transaction ID']", "Search By Transaction ID");
        getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Search By Transaction ID']")).click();

        //Search Document number with wrong digit details
        TestUtils.testTitle("Search Document number with wrong digit details: "+invalid_transaction_id);
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/search_field")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/search_field")).sendKeys(invalid_transaction_id);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/btn_search")));
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/btn_search")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
        TestUtils.assertSearchText("ID", "android:id/message", "Transaction ID not found");
        getDriver().findElement(By.id("android:id/button1")).click();

        TestUtils.testTitle("Confirm with Valid Transaction ID");
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/search_field")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/search_field")).sendKeys(transaction_id);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/btn_search")));
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/btn_search")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
        TestUtils.assertSearchText("ID", "android:id/message", "NIN Verification successful");
        getDriver().findElement(By.id("android:id/button1")).click();

        try {
            //NIMC Details View
            TestUtils.testTitle("Confirm the searched NIMC Data is returned: "+"NIMC Data");
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/nin_verification_title")));
            TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/nin_verification_title", "NIN Verification");
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/tv_user_data")));
            TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/tv_user_data","User Data");
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/tv_nimc_data")));
            TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/tv_nimc_data","NIMC Data");
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Firstname']")));
            TestUtils.assertSearchText("XPATH", "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/androidx.recyclerview.widget.RecyclerView/android.view.ViewGroup[2]/android.widget.TextView[2]", first_name);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Surname']")));
            TestUtils.assertSearchText("XPATH", "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/androidx.recyclerview.widget.RecyclerView/android.view.ViewGroup[1]/android.widget.TextView[2]", last_name);

            try {
                //Next Button
                Thread.sleep(1000);
                getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/reject_button")).click();
            }catch (Exception e) {
                //Next Button
                Thread.sleep(1000);
                getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/ok_button")).click();
            }
        }catch (Exception e){
            testInfo.get().error("Could not proceed to NIMC detail page("+e+")");
        }
    }
}