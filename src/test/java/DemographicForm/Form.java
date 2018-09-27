package DemographicForm;


import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.TestBase;
import utils.TestUtils;

import java.io.IOException;

public class Form extends TestBase {

    public static void NigerianCompanyForm() throws InterruptedException, IOException {

        WebDriverWait wait = new WebDriverWait(getDriver(), 30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Personal Details']")));
        TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Personal Details']", "Personal Details");
        Thread.sleep(2000);
        //personal details
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/reg_type")).click();
        Thread.sleep(500);
        getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Company']")).click();
        Thread.sleep(500);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/surnname")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/surnname")).sendKeys("Andrea");
        Thread.sleep(500);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/firstname")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/firstname")).sendKeys("Banks");
        Thread.sleep(500);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/middlename")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/middlename")).sendKeys("Landscape");
        Thread.sleep(500);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/moms_maidenname")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/moms_maidenname")).sendKeys("Peters");
        Thread.sleep(500);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/male_radio_button")).click();
        Thread.sleep(500);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/select_date_button")).click();
        Thread.sleep(500);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/key_left")).click();
        Thread.sleep(500);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/key_left")).click();
        Thread.sleep(500);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/key_left")).click();
        Thread.sleep(500);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/key_left")).click();
        Thread.sleep(500);
        getDriver().findElement(By.xpath("//android.widget.Button[@text='9']")).click();
        Thread.sleep(500);
        getDriver().findElement(By.xpath("//android.widget.Button[@text='9']")).click();
        Thread.sleep(500);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/key_middle")).click();
        Thread.sleep(500);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/done_button")).click();
        Thread.sleep(500);

        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity:id/house_or_flat_no");

        //Social Media
        if (TestUtils.isElementPresent("ID", "com.sf.biocapture.activity:id/add_social_media_button")) {
            Thread.sleep(1000);
            getDriver().findElement(By.id("com.sf.biocapture.activity:id/add_social_media_button")).click();
            Thread.sleep(500);
            getDriver().findElement(By.id("com.sf.biocapture.activity:id/delete_button")).click();
            Thread.sleep(500);
            getDriver().findElement(By.id("com.sf.biocapture.activity:id/add_social_media_button")).click();
            Thread.sleep(500);
            getDriver().findElement(By.id("com.sf.biocapture.activity:id/type_spinner")).click();
            Thread.sleep(500);
            getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='INSTAGRAM']")).click();
            Thread.sleep(500);
            getDriver().findElement(By.id("com.sf.biocapture.activity:id/username_edit_text")).sendKeys("@testuser");
        }

        Thread.sleep(500);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/house_or_flat_no")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/house_or_flat_no")).sendKeys("23");
        Thread.sleep(500);
        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity:id/street");
        Thread.sleep(500);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/street")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/street")).sendKeys("Lewis Lane");
        Thread.sleep(500);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/city")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/city")).sendKeys("Lagos");
        Thread.sleep(500);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/country")).click();
        Thread.sleep(500);
        getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='NIGERIA']")).click();
        Thread.sleep(500);
        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity:id/states");
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/states")).click();
        Thread.sleep(500);
        getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='ABIA']")).click();
        Thread.sleep(500);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/lga")).click();
        Thread.sleep(500);
        getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Aba North']")).click();
        Thread.sleep(500);
        //Next
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/btn_continue_reg")).click();
        Thread.sleep(2000);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/page_title")));
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity:id/page_title", "Registration Details");

        //Registration Details
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/typeofid")).click();
        Thread.sleep(500);
        getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='International Passport']")).click();
        Thread.sleep(500);
        //capture
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/capture_id_button")).click();
        Thread.sleep(500);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/capture_button")).click();
        Thread.sleep(500);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/button_camera_capture")).click();
        Thread.sleep(500);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/ok"))).click();
        Thread.sleep(500);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/capture_button")).click();
        Thread.sleep(500);
        //View Captured ID
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/view_captured_id_button")).click();
        Thread.sleep(3000);
        if (getDriver().findElement(By.id("com.sf.biocapture.activity:id/image")).isDisplayed()) {
            testInfo.get().info("Captured ID is displayed");
        } else {
            testInfo.get().info("Captured ID is not displayed");
            testInfo.get().addScreenCaptureFromPath(TestUtils.addScreenshot());
        }
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/done_button")).click();
        Thread.sleep(500);

        //company Details
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/edit_company_details")).click();
        Thread.sleep(2000);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/company_details_title")));
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity:id/company_details_title", "Company Details");

        Thread.sleep(1000);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/company_name_descrptn")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/company_name_descrptn")).sendKeys("Seamfix Nigeria QA test");
        Thread.sleep(500);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/company_regno")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/company_regno")).sendKeys("NIG/LAG/16253");
        Thread.sleep(500);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/house_or_flat_no")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/house_or_flat_no")).sendKeys("45C");
        Thread.sleep(500);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/street")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/street")).sendKeys("Lekki Leasing House");
        Thread.sleep(500);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/city")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/city")).sendKeys("Lekki");
        Thread.sleep(500);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/company_state_address")).click();
        Thread.sleep(500);
        getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='ABIA']")).click();
        Thread.sleep(500);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/company_lga_address")).click();
        Thread.sleep(500);
        getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Aba North']")).click();
        Thread.sleep(500);

        getDriver().findElement(By.id("com.sf.biocapture.activity:id/company_postalcode")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/company_postalcode")).sendKeys("5200");
        Thread.sleep(500);

        //Certificate of Incorporation
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/document_type_spinner")).click();
        Thread.sleep(500);
        getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Certificate of Incorporation *']")).click();
        Thread.sleep(500);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/btn_load_document")).click();
        Thread.sleep(500);
        getDriver().findElement(By.xpath("//android.widget.TextView[@text='_001']")).click();
        getDriver().findElement(By.xpath("//android.widget.TextView[@text='profile_picture.jpg']")).click();
        Thread.sleep(500);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/btn_remove_document")).click();
        Thread.sleep(500);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/btn_load_document")).click();
        Thread.sleep(1000);
        getDriver().findElement(By.xpath("//android.widget.TextView[@text='_001']")).click();
        Thread.sleep(1000);
        getDriver().findElement(By.xpath("//android.widget.TextView[@text='profile_picture.jpg']")).click();
        Thread.sleep(500);

        //contact person form
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/document_type_spinner")).click();
        Thread.sleep(500);
        getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Contact Person Form *']")).click();
        Thread.sleep(500);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/btn_remove_document")).click();
        Thread.sleep(500);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/btn_load_document")).click();
        Thread.sleep(1000);
        getDriver().findElement(By.xpath("//android.widget.TextView[@text='_001']")).click();
        Thread.sleep(1000);
        getDriver().findElement(By.xpath("//android.widget.TextView[@text='profile_picture.jpg']")).click();
        Thread.sleep(500);

        //save
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/company_ok")).click();
        Thread.sleep(2000);

        //wait to see email
        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity:id/email");
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/email")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/email")).sendKeys("test@yopmail.com");
        Thread.sleep(500);
        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity:id/alt_phone_number");
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/alt_phone_number")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/alt_phone_number")).sendKeys("08000000000");
        Thread.sleep(500);
        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity:id/postalcode");
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/postalcode")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/postalcode")).sendKeys("746583");

        Thread.sleep(500);
        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity:id/occupation");
        Thread.sleep(500);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/occupation")).click();
        Thread.sleep(500);
        getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Aeronautical Engineer']")).click();
        Thread.sleep(500);
        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity:id/states_residence");
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/states_residence")).click();
        Thread.sleep(500);
        getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='ABIA']")).click();
        Thread.sleep(500);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/lga_residence")).click();
        Thread.sleep(500);
        getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Aba North']")).click();
        Thread.sleep(500);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/area")).click();
        Thread.sleep(500);
        getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='ARIARIA']")).click();
        Thread.sleep(500);
        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity:id/lga_of_reg");
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/lga_of_reg")).click();
        Thread.sleep(500);
        getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Agege']")).click();
        Thread.sleep(500);

        //capture kyc form
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/capture_kyc_form")).click();
        Thread.sleep(500);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/capture_button")).click();
        Thread.sleep(500);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/button_camera_capture")).click();
        Thread.sleep(500);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/ok"))).click();
        Thread.sleep(500);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/capture_button")).click();
        Thread.sleep(500);

        getDriver().findElement(By.id("com.sf.biocapture.activity:id/save_continue")).click();
        Thread.sleep(1000);
        getDriver().findElement(By.id("android:id/button2")).click();

        override();

    }


    public static void IndividualForeignerForm() throws InterruptedException {


        WebDriverWait wait = new WebDriverWait(getDriver(), 30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Personal Details']")));
        TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Personal Details']", "Personal Details");
        Thread.sleep(2000);
        //personal details
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/reg_type")).click();
        Thread.sleep(500);
        getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Individual']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/surnname")));
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/surnname")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/surnname")).sendKeys("Demetrice");
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/firstname")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/firstname")).sendKeys("Joubert");
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/middlename")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/middlename")).sendKeys("Barbara");
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/moms_maidenname")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/moms_maidenname")).sendKeys("Minor");
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/male_radio_button")).click();
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/select_date_button")).click();
        Thread.sleep(500);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/key_left")).click();
        Thread.sleep(500);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/key_left")).click();
        Thread.sleep(500);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/key_left")).click();
        Thread.sleep(500);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/key_left")).click();
        Thread.sleep(500);
        getDriver().findElement(By.xpath("//android.widget.Button[@text='9']")).click();
        Thread.sleep(500);
        getDriver().findElement(By.xpath("//android.widget.Button[@text='9']")).click();
        Thread.sleep(500);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/key_middle")).click();
        Thread.sleep(500);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/done_button")).click();
        Thread.sleep(500);

        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity:id/house_or_flat_no");

        //Social Media
        if (TestUtils.isElementPresent("ID", "com.sf.biocapture.activity:id/add_social_media_button")) {
            Thread.sleep(1000);
            if (TestUtils.isElementPresent("ID", "com.sf.biocapture.activity:id/delete_button")) {
                getDriver().findElement(By.id("com.sf.biocapture.activity:id/delete_button")).click();
            }
            getDriver().findElement(By.id("com.sf.biocapture.activity:id/add_social_media_button")).click();
            Thread.sleep(500);
            getDriver().findElement(By.id("com.sf.biocapture.activity:id/delete_button")).click();
            Thread.sleep(500);
            getDriver().findElement(By.id("com.sf.biocapture.activity:id/add_social_media_button")).click();
            Thread.sleep(500);
            getDriver().findElement(By.id("com.sf.biocapture.activity:id/type_spinner")).click();
            Thread.sleep(500);
            getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='INSTAGRAM']")).click();
            Thread.sleep(500);
            getDriver().findElement(By.id("com.sf.biocapture.activity:id/username_edit_text")).sendKeys("@testuser");
        }

        Thread.sleep(500);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/house_or_flat_no")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/house_or_flat_no")).sendKeys("455");
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/street")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/street")).sendKeys("Neville Street");
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/city")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/city")).sendKeys("Lagos");
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/country")).click();
        Thread.sleep(500);
        getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='ANGOLA']")).click();
        Thread.sleep(1000);

        try {
            getDriver().findElement(By.id("com.sf.biocapture.activity:id/btn_continue_reg")).click();
        } catch (NoSuchElementException exc) {

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/passport_details_title")));
            TestUtils.assertSearchText("ID", "com.sf.biocapture.activity:id/passport_details_title", "Passport/ID Details");
            Thread.sleep(1000);
            getDriver().findElement(By.id("com.sf.biocapture.activity:id/passport_issuing_country")).click();
            Thread.sleep(1000);
            getDriver().findElement(By.xpath("//android.widget.TextView[@text='ANGOLA']")).click();
            Thread.sleep(500);
            getDriver().findElement(By.id("com.sf.biocapture.activity:id/passport_number")).clear();
            getDriver().findElement(By.id("com.sf.biocapture.activity:id/passport_number")).sendKeys("686163746BC");
            Thread.sleep(500);
            getDriver().findElement(By.id("com.sf.biocapture.activity:id/passport_expiry_date")).click();
            Thread.sleep(500);
            try {
                try {
                    getDriver().findElement(By.xpath("//android.widget.Button[@text='2019']")).click();
                } catch (NoSuchElementException e) {
                    getDriver().findElement(By.xpath("//android.widget.EditText[@text='2019']")).click();
                }
            } catch (NoSuchElementException ex) {
                getDriver().findElement(By.id("android:id/date_picker_header_year")).click();
                Thread.sleep(500);
                getDriver().findElement(By.xpath("//android.widget.TextView[@text='2020']")).click();
            }
            getDriver().findElement(By.id("android:id/button1")).click();
            Thread.sleep(1000);
            getDriver().findElement(By.id("com.sf.biocapture.activity:id/passport_nigerian_resident")).click();
            Thread.sleep(2000);

            //capture passport image
            TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity:id/capture_passport_image");
            getDriver().findElement(By.id("com.sf.biocapture.activity:id/capture_passport_image")).click();
            Thread.sleep(500);
            getDriver().findElement(By.id("com.sf.biocapture.activity:id/button_camera_capture")).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/ok"))).click();
            Thread.sleep(1000);
            getDriver().findElement(By.id("com.sf.biocapture.activity:id/passport_ok")).click();
            Thread.sleep(1000);
            getDriver().findElement(By.id("com.sf.biocapture.activity:id/btn_continue_reg")).click();
        }


        //Registration Details
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/typeofid"))).click();
        Thread.sleep(500);
        getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='National ID']")).click();
        Thread.sleep(500);

        //Edit passport details
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/edit_passport_details")).click();
        Thread.sleep(500);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/passport_details_title")));
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity:id/passport_details_title", "Passport/ID Details");
        Thread.sleep(1000);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/passport_issuing_country")).click();
        Thread.sleep(1000);
        getDriver().findElement(By.xpath("//android.widget.TextView[@text='ANGOLA']")).click();
        Thread.sleep(500);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/passport_number")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/passport_number")).sendKeys("686163746BC");
        Thread.sleep(500);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/passport_expiry_date")).click();
        Thread.sleep(500);
        try {
            try {
                getDriver().findElement(By.xpath("//android.widget.Button[@text='2019']")).click();
            } catch (NoSuchElementException e) {
                getDriver().findElement(By.xpath("//android.widget.EditText[@text='2019']")).click();
            }
        } catch (NoSuchElementException ex) {
            Thread.sleep(2000);
            getDriver().findElement(By.id("android:id/date_picker_header_year")).click();
            Thread.sleep(500);
            try {
                getDriver().findElement(By.xpath("//android.widget.TextView[@text='2020']")).click();
            } catch (Exception e) {
                getDriver().findElement(By.xpath("//android.widget.EditText[@text='2020']")).click();
            }
        }
        getDriver().findElement(By.id("android:id/button1")).click();
        Thread.sleep(1000);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/passport_nigerian_resident")).click();
        Thread.sleep(2000);

        //capture passport image
        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity:id/capture_passport_image");
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/capture_passport_image")).click();
        Thread.sleep(2000);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/button_camera_capture")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/ok"))).click();
        Thread.sleep(1000);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/passport_ok")).click();
        Thread.sleep(1000);


        //Other details starting from email
        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity:id/email");
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/email")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/email")).sendKeys("testIndividual@yopmail.com");
        Thread.sleep(500);
        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity:id/alt_phone_number");
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/alt_phone_number")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/alt_phone_number")).sendKeys("08000000000");
        Thread.sleep(500);
        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity:id/postalcode");
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/postalcode")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/postalcode")).sendKeys("126583");
        Thread.sleep(500);
        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity:id/occupation");
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/occupation")).click();
        Thread.sleep(500);
        try {
            getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Aeronautical Engineer']")).click();
        } catch (NoSuchElementException e) {
            getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Petroleum Engineer']")).click();
        }
        Thread.sleep(500);
        TestUtils.scrollDown();
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/states_residence")).click();
        Thread.sleep(500);
        try {
            getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='BENUE']")).click();
        } catch (NoSuchElementException e) {
            getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='LAGOS']")).click();
        }
        Thread.sleep(500);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/lga_residence")).click();
        Thread.sleep(500);
        try {
            getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Ado']")).click();
        } catch (NoSuchElementException e) {
            getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Apapa']")).click();
        }
        Thread.sleep(500);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/area")).click();
        Thread.sleep(500);
        try {
            getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='AGILA']")).click();
        } catch (NoSuchElementException e) {
            getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='AMUKOKO']")).click();
        }
        Thread.sleep(500);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/lga_of_reg")).click();
        Thread.sleep(500);
        getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Eti Osa']")).click();
        Thread.sleep(500);

        //capture kyc form
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/capture_kyc_form")).click();
        Thread.sleep(500);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/capture_button")).click();
        Thread.sleep(500);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/button_camera_capture")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/ok"))).click();
        Thread.sleep(1000);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/capture_button")).click();
        Thread.sleep(1000);

        //save
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/save_continue")).click();
        Thread.sleep(1000);
        getDriver().findElement(By.id("android:id/button2")).click();
        override();


    }

    public static void override() throws InterruptedException {

        WebDriverWait wait = new WebDriverWait(getDriver(), 30);

        //override
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/override_poor_portrait_capture_button"))).click();
        Thread.sleep(2000);
        getDriver().findElement(By.id("android:id/button1")).click();
        Thread.sleep(2000);

        //capture override
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/capture_button")).click();
        Thread.sleep(2000);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/button_camera_capture")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/ok"))).click();
        Thread.sleep(1000);
        getDriver().findElement(By.xpath("//android.widget.TextView[@text='Scarred']")).click();
        Thread.sleep(1000);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/capture_button")).click();
        Thread.sleep(1000);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/buttonImageInfo")).click();
        Thread.sleep(1000);

        //finger print override
        //left hand
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/override_finger_print_capture_button")).click();
        Thread.sleep(1000);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/capture_button")).click();
        Thread.sleep(1000);
        getDriver().findElement(By.xpath("//android.widget.TextView[@text='MISSING RIGHT HAND']")).click();
        Thread.sleep(1000);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/button_camera_capture")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/ok"))).click();
        Thread.sleep(1000);
        getDriver().findElement(By.xpath("//android.widget.TextView[@text='No prints']")).click();
        Thread.sleep(1000);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/capture_button")).click();
        Thread.sleep(1000);

        //finger print override
        //left hand
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/override_finger_print_capture_button")).click();
        Thread.sleep(1000);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/capture_button")).click();
        Thread.sleep(1000);
        getDriver().findElement(By.xpath("//android.widget.TextView[@text='MISSING LEFT HAND']")).click();
        Thread.sleep(1000);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/button_camera_capture")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/ok"))).click();
        Thread.sleep(1000);
        getDriver().findElement(By.xpath("//android.widget.TextView[@text='No prints']")).click();
        Thread.sleep(1000);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/capture_button")).click();

        //save enrollment
        Thread.sleep(1000);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/fp_save_enrolment")).click();

        //popup
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/content")));
        TestUtils.assertSearchText("ID", "android:id/message", "Captured record was saved successfully");

        //ok button
        getDriver().findElement(By.id("android:id/button1")).click();
        Thread.sleep(1000);

    }

}
