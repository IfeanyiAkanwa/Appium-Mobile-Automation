package DemographicForm;


import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import utils.TestBase;
import utils.TestUtils;

public class Form extends TestBase {

    public void NigerianCompanyForm() throws InterruptedException {

        String idCard = System.getProperty("user.dir")+"/files/idCard.jpg";

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
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/key_right")).click();
        Thread.sleep(500);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/key_right")).click();
        Thread.sleep(500);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/key_middle")).click();
        Thread.sleep(500);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/done_button")).click();
        Thread.sleep(500);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/house_or_flat_no")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/house_or_flat_no")).sendKeys("23");
        Thread.sleep(500);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/street")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/street")).sendKeys("Lewis Lane");
        Thread.sleep(500);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/city")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/city")).sendKeys("Lagos");
        Thread.sleep(500);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/country")).click();
        Thread.sleep(500);
        getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Nigeria']")).click();
        Thread.sleep(500);
        Thread.sleep(500);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/states")).click();
        Thread.sleep(500);
        getDriver().findElement(By.id("//android.widget.CheckedTextView[@text='ENUGU']")).click();
        Thread.sleep(500);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/lga")).click();
        Thread.sleep(500);
        getDriver().findElement(By.id("//android.widget.CheckedTextView[@text='Nsukka']")).click();
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
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/ok")).click();
        Thread.sleep(500);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/capture_button")).click();
        Thread.sleep(500);

        //company Details
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/edit_company_details")).click();
        Thread.sleep(2000);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/company_details_title")));
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity:id/company_details_title", "Company Details");

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
        getDriver().findElement(By.id("//android.widget.CheckedTextView[@text='LAGOS']")).click();
        Thread.sleep(500);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/company_lga_address")).click();
        Thread.sleep(500);
        getDriver().findElement(By.id("//android.widget.CheckedTextView[@text='Eti Osa']")).click();
        Thread.sleep(500);

        getDriver().findElement(By.id("com.sf.biocapture.activity:id/company_postalcode")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/company_postalcode")).sendKeys("5200");
        Thread.sleep(500);

        //passport form
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/document_type_spinner")).click();
        Thread.sleep(500);
        getDriver().findElement(By.id("//android.widget.CheckedTextView[@text='Passport Photographs']")).click();
        Thread.sleep(500);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/btn_remove_document")).click();
        Thread.sleep(500);
        Thread.sleep(500);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/btn_load_document")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/btn_load_document")).sendKeys(idCard);
        Thread.sleep(500);

        //contact person form
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/document_type_spinner")).click();
        Thread.sleep(500);
        getDriver().findElement(By.id("//android.widget.CheckedTextView[@text='Contact Person Form *']")).click();
        Thread.sleep(500);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/btn_remove_document")).click();
        Thread.sleep(500);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/btn_load_document")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/btn_load_document")).sendKeys(idCard);
        Thread.sleep(500);

        //save
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/company_ok")).click();
        Thread.sleep(2000);

        //wait to see email
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/email")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/email")).sendKeys("test@yopmail.com");
        Thread.sleep(500);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/alt_phone_number")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/alt_phone_number")).sendKeys("08000000000");
        Thread.sleep(500);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/postalcode")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/postalcode")).sendKeys("746583");

        Thread.sleep(500);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/occupation")).click();
        Thread.sleep(500);
        getDriver().findElement(By.id("//android.widget.CheckedTextView[@text='Aeronautical Engineer']")).click();
        Thread.sleep(500);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/states_residence")).click();
        Thread.sleep(500);
        getDriver().findElement(By.id("//android.widget.CheckedTextView[@text='LAGOS']")).click();
        Thread.sleep(500);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/lga_residence")).click();
        Thread.sleep(500);
        getDriver().findElement(By.id("//android.widget.CheckedTextView[@text='Eti Osa']")).click();
        Thread.sleep(500);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/area")).click();
        Thread.sleep(500);
        getDriver().findElement(By.id("//android.widget.CheckedTextView[@text='LEKKI PHASE 1']")).click();
        Thread.sleep(500);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/lga_of_reg")).click();
        Thread.sleep(500);
        getDriver().findElement(By.id("//android.widget.CheckedTextView[@text='Eti Osa']")).click();
        Thread.sleep(500);

        //capture kyc form
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/capture_kyc_form")).click();
        Thread.sleep(500);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/capture_button")).click();
        Thread.sleep(500);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/button_camera_capture")).click();
        Thread.sleep(500);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/ok")).click();
        Thread.sleep(500);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/capture_button")).click();
        Thread.sleep(500);

        getDriver().findElement(By.id("com.sf.biocapture.activity:id/save_continue")).click();
        Thread.sleep(1000);
        getDriver().findElement(By.id("android:id/button2")).click();

        //Override


    }


    public void IndividualForeignerForm() throws InterruptedException{

        String idCard = System.getProperty("user.dir")+"/files/idCard.jpg";

        WebDriverWait wait = new WebDriverWait(getDriver(), 30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Personal Details']")));
        TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Personal Details']", "Personal Details");
        Thread.sleep(2000);
        //personal details
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/reg_type")).click();
        Thread.sleep(500);
        getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Individual']")).click();
        Thread.sleep(500);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/surnname")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/surnname")).sendKeys("Demetrice");
        Thread.sleep(500);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/firstname")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/firstname")).sendKeys("Joubert");
        Thread.sleep(500);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/middlename")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/middlename")).sendKeys("Barbara");
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
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/key_right")).click();
        Thread.sleep(500);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/key_right")).click();
        Thread.sleep(500);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/key_middle")).click();
        Thread.sleep(500);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/done_button")).click();
        Thread.sleep(500);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/house_or_flat_no")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/house_or_flat_no")).sendKeys("23");
        Thread.sleep(500);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/street")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/street")).sendKeys("Lewis Lane");
        Thread.sleep(500);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/city")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/city")).sendKeys("Lagos");
        Thread.sleep(500);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/country")).click();
        Thread.sleep(500);
        getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Nigeria']")).click();
        Thread.sleep(500);
        Thread.sleep(500);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/states")).click();
        Thread.sleep(500);
        getDriver().findElement(By.id("//android.widget.CheckedTextView[@text='ENUGU']")).click();
        Thread.sleep(500);
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/lga")).click();
        Thread.sleep(500);
        getDriver().findElement(By.id("//android.widget.CheckedTextView[@text='Nsukka']")).click();
        Thread.sleep(500);

    }

}
