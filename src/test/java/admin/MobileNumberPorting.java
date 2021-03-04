package admin;

import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.WebDriverException;
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
    public void noneMNPTest(String dataEnv) throws Exception {
        WebDriverWait wait = new WebDriverWait(getDriver(), 30);
        JSONParser parser = new JSONParser();
        JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resource/" + dataEnv + "/data.conf.json"));
        JSONObject envs = (JSONObject) config.get("MNP");

        String valid_username = (String) envs.get("valid_username");
        String valid_password = (String) envs.get("valid_password");
        TestBase.Login1( valid_username, valid_password);
        Thread.sleep(500);
        TestUtils.testTitle("To confirm that a user without MOBILE NUMBER PORTABILITY privilege cannot perform port-in-request");
        getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/button_start_capture")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity." + Id + ":id/reg_type_placeholder")));
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity." + Id + ":id/reg_type_placeholder", "Registration Type");
        Thread.sleep(500);

        // Check that Additional Registration is not selectable
        TestUtils.testTitle("Check that additional registration is not selectable");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity." + Id + ":id/typeofreg")));
        getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/typeofreg")).click();
        Thread.sleep(500);
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity." + Id + ":id/alertTitle", "Select Registration Type");
        getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Mobile Number Porting']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity." + Id + ":id/alertTitle")));
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity." + Id + ":id/alertTitle", "No Privilege");
        TestUtils.assertSearchText("ID", "android:id/message", "User does not have privilege to perform mnp");
        Thread.sleep(500);
        getDriver().findElement(By.id("android:id/button1")).click();
        Thread.sleep(500);
        getDriver().findElement(By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/alertTitle")));
        TestUtils.assertSearchText("ID", "android:id/message", "Capture session is currently active, Exit will cause loss of currently captured data! do you wish to cancel current capture session?");
        getDriver().findElement(By.id("android:id/button2")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Home']")));
        Thread.sleep(500);

        //Log out
        getDriver().findElement(By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/design_menu_item_text")));
        TestUtils.scrollDown();
        getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Logout']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/message")));
        TestUtils.assertSearchText("ID", "android:id/message", "   Log out?");
        getDriver().findElement(By.id("android:id/button3")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity.glo:id/otp_login")));
    }


    @Parameters({ "dataEnv"})
    @Test
    public void mobileNumberPortability(String dataEnv) throws Exception {

        WebDriverWait wait = new WebDriverWait(getDriver(), 60);
        JSONParser parser = new JSONParser();
        JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resource/" + dataEnv + "/data.conf.json"));
        JSONObject envs = (JSONObject) config.get("MNP");

        String pri_valid_Msisdn = (String) envs.get("pri_valid_Msisdn");
        String pri_valid_simSerial = (String) envs.get("pri_valid_simSerial");
        String invalid_Msisdn = (String) envs.get("invalid_Msisdn");
        String invalid_simSerial = (String) envs.get("invalid_simSerial");
        String invalid_number = (String) envs.get("invalid_number");
        String valid_simSerial = (String) envs.get("valid_simSerial");

        //To confirm that there is a Registration type called Mobile Number Porting
        TestUtils.testTitle("To confirm that there is a Registration type called Mobile Number Porting");
        getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/button_start_capture")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity." + Id + ":id/typeofreg")));
        getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/typeofreg")).click();
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity." + Id + ":id/alertTitle", "Select Registration Type");
        TestUtils.assertSearchText("XPATH", "//android.widget.CheckedTextView[@text='Mobile Number Porting']", "Mobile Number Porting");

        //To confirm that only  a user with MOBILE NUMBER PORTABILITY privilege can perform port-in-request
        TestUtils.testTitle("To confirm that only  a user with MOBILE NUMBER PORTABILITY privilege can perform port-in-request");
        getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Mobile Number Porting']")).click();
        getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/next_button")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Mobile Number Porting']")));
        TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Mobile Number Porting']", "Mobile Number Porting");
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity." + Id + ":id/dya_check_box", "Vanity Number");

        //To confirm that the SIM SERIAL text field is enabled and does not allow more than 20 characters
        String moreCharacter=pri_valid_simSerial+"1234";
        TestUtils.testTitle("To confirm that the SIM SERIAL text field is enabled and does not allow more than 20 characters ("+moreCharacter+")");
        getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/sim_serial")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/sim_serial")).sendKeys(pri_valid_simSerial);
        getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/sim_serial")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/sim_serial")).sendKeys(moreCharacter);
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity." + Id + ":id/sim_serial", pri_valid_simSerial);

        //To confirm that the MSISDN text field is enabled and does not allow more than 11 digits
        String moreCharacters=pri_valid_Msisdn+"1234";
        TestUtils.testTitle("To confirm that the MSISDN text field is enabled and does not allow more than 11 digits ("+moreCharacters+")");
        getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/primary_msisdn_field")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/primary_msisdn_field")).sendKeys(pri_valid_Msisdn);
        getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/primary_msisdn_field")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/primary_msisdn_field")).sendKeys(moreCharacters);
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity." + Id + ":id/primary_msisdn_field", pri_valid_Msisdn);

        //To verify that if the msisdn entered fails validation, an error message should be displayed and user should not be allowed to proceed
        /*TestUtils.testTitle("To verify that if the msisdn entered fails validation, an error message should be displayed ("+invalid_number+")");
        getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/primary_msisdn_field")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/primary_msisdn_field")).sendKeys(invalid_number);
        getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/sim_serial")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/sim_serial")).sendKeys(valid_simSerial);
        getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/validate_serial_button")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/button1")));
        TestUtils.assertSearchText("ID", "android:id/message", "Record not found");
        getDriver().findElement(By.id("android:id/button1")).click();*/

        //To verify that if the sim serial entered fails validation, an error message should be displayed and user should not be allowed to proceed
        TestUtils.testTitle("To verify that if the sim serial entered fails validation, an error message should be displayed ("+invalid_simSerial+")");
        getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/primary_msisdn_field")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/primary_msisdn_field")).sendKeys(pri_valid_Msisdn);
        getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/sim_serial")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/sim_serial")).sendKeys(invalid_simSerial);
        getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/validate_serial_button")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/button1")));
        TestUtils.assertSearchText("ID", "android:id/message", "Sim Serial format is invalid");
        getDriver().findElement(By.id("android:id/button1")).click();

        //To confirm that an empty demographic form is displayed when the NEXT button is clicked
        TestUtils.testTitle("To confirm that an empty demographic form is displayed when the NEXT button is clicked ("+pri_valid_Msisdn+")");
        getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/primary_msisdn_field")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/primary_msisdn_field")).sendKeys(pri_valid_Msisdn);
        getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/sim_serial")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/sim_serial")).sendKeys(pri_valid_simSerial);
        getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/validate_serial_button")).click();
        Thread.sleep(1000);
        TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Personal Details']", "Personal Details");

        //To verify that the button to Validate Sim Serial is enabled and functional .
        TestUtils.testTitle("To verify that the button to Validate Sim Serial is enabled and functional.");
        TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Personal Details']", "Personal Details");

        //To confirm that there is Client Acivity Log for  port-in requests
        TestUtils.testTitle("To confirm that there is Client Acivity Log for  port-in requests");




    }

    @Parameters({ "dataEnv"})
    @Test
    public void vanityNumberReg(String dataEnv) throws Exception {

        WebDriverWait wait = new WebDriverWait(getDriver(), 60);
        JSONParser parser = new JSONParser();
        JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resource/" + dataEnv + "/data.conf.json"));
        JSONObject envs = (JSONObject) config.get("MNP");

        String pri_valid_Msisdn = (String) envs.get("pri_valid_Msisdn");
        String pri_valid_simSerial = (String) envs.get("pri_valid_simSerial");
        String invalid_Msisdn = (String) envs.get("invalid_Msisdn");
        String invalid_simSerial = (String) envs.get("invalid_simSerial");
        String middle_name = (String) envs.get("middle_name");
        String surname = (String) envs.get("surname");
        String first_name = (String) envs.get("first_name");
        String mothers_maiden_name = (String) envs.get("mothers_maiden_name");

        //To confirm that only users with the VNR privilege is able to register vanity numbers
        TestUtils.testTitle("To confirm that only users with the VNR privilege is able to register vanity numbers");
        getDriver().findElement(By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity." + Id + ":id/typeofreg")));
        getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/typeofreg")).click();
        Thread.sleep(1000);
        getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Mobile Number Porting']")).click();
        getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/next_button")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Mobile Number Porting']")));
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity." + Id + ":id/dya_check_box", "Vanity Number");

        try {
            boolean check = getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/dya_check_box")).isEnabled();
            if (check==false){
                //To ensure the checkbox option that allows a user with VNR privilege to select vanity number option is available
                TestUtils.testTitle("To ensure the checkbox option that allows a user with VNR privilege to select vanity number option is available");
                TestUtils.assertSearchText("ID", "com.sf.biocapture.activity." + Id + ":id/dya_check_box", "false");
            }else{
                //To ensure the checkbox option that allows a user with VNR privilege to select vanity number option is available
                TestUtils.testTitle("To ensure the checkbox option that allows a user with VNR privilege to select vanity number option is available");
                TestUtils.assertSearchText("ID", "com.sf.biocapture.activity." + Id + ":id/dya_check_box", "true");
            }

        }catch (WebDriverException e){

        }

        //To confirm that the Email Address field is not mandatory
        TestUtils.testTitle("To confirm that the Email Address field is not mandatory");
        getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/primary_msisdn_field")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/primary_msisdn_field")).sendKeys(pri_valid_Msisdn);
        getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/sim_serial")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/sim_serial")).sendKeys(pri_valid_simSerial);
        getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/validate_serial_button")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity." + Id + ":id/surNameTXT")));
        getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/surNameTXT")).sendKeys(surname);
        getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/firstNameTXT")).sendKeys(first_name);
        TestUtils.scrollDown();
        getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/middleNameTXT")).sendKeys(middle_name);
        getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/momsMaidenNameTXT")).sendKeys(mothers_maiden_name);
        getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/maleRadioButton")).click();
        getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/selectDateButton")).click();
        getDriver().findElement(By.xpath("//android.view.View[@index='0']")).click();
        getDriver().findElement(By.id("android:id/button1")).click();
        TestUtils.scrollDown();
        getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/btnContinueReg")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity." + Id + ":id/stateOfOriginSpinner")));
        TestUtils.assertSearchText("XPATH", "com.sf.biocapture.activity." + Id + ":id/nationality",
                "Country of Origin*");
        Thread.sleep(50000);
        //Address Basic Info
        /*getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/stateOfOriginSpinner")).click();
        getDriver().findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.ListView/android.widget.TextView[2]")).click();
        getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/lgaOfOriginSpinner")).click();
        getDriver().findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.ListView/android.widget.TextView[2]")).click();
        String nin =TestUtils.generatePhoneNumber();
        getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/ninEditText")).sendKeys(nin);
        TestUtils.scrollDown();
        getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/stateOfResidenceSpinner")).click();
        getDriver().findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.ListView/android.widget.TextView[2]")).click();
        getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/lgaOfResidenceSpinner")).click();
        getDriver().findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.ListView/android.widget.TextView[2]")).click();
        getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/areaOfResidenceSpinner")).click();
        getDriver().findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.ListView/android.widget.TextView[2]")).click();
        getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/houseNumberEditText")).sendKeys("40");
        getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/streetEditText")).sendKeys("Abule Egba");
        TestUtils.scrollDown();
        getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/citySpinner")).click();
        getDriver().findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.ListView/android.widget.TextView[2]")).click();
        getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/postalCodeSpinner")).click();
        getDriver().findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.ListView/android.widget.TextView[2]")).click();
        getDriver().findElement(By.id("com.sf.biocapture.activity." + Id + ":id/btnNext")).click();

        Thread.sleep(50000);*/
    }
    public String generateString() {

        int length = 10;
        boolean useLetters = true;
        boolean useNumbers = false;
        String generatedString = RandomStringUtils.random(length, useLetters, useNumbers);

        return generatedString;
    }
}
