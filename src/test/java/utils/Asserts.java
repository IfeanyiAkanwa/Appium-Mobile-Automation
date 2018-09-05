package utils;

import com.aventstack.extentreports.Status;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class Asserts extends TestBase {

    public static void AssertIndividualForm() {

        WebDriverWait wait = new WebDriverWait(getDriver(), 30);

        //Page 1
        String typeOfRegistration = getDriver().findElement(By.id("com.sf.biocapture.activity:id/reg_type")).getText();
        String surName = getDriver().findElement(By.id("com.sf.biocapture.activity:id/surnname")).getText();
        String firstName = getDriver().findElement(By.id("com.sf.biocapture.activity:id/firstname")).getText();
        String middleName = getDriver().findElement(By.id("com.sf.biocapture.activity:id/firstname")).getText();
        String mothersMaidenName = getDriver().findElement(By.id("com.sf.biocapture.activity:id/moms_maidenname")).getText();
        String sex = getDriver().findElement(By.id("com.sf.biocapture.activity:id/male_radio_button")).getText();
        String dateOfBirth = getDriver().findElement(By.id("com.sf.biocapture.activity:id/date_of_birth")).getText();
        TestUtils.scrollDown();
        String houseNo = getDriver().findElement(By.id("com.sf.biocapture.activity:id/house_or_flat_no")).getText();
        String street = getDriver().findElement(By.id("com.sf.biocapture.activity:id/street")).getText();
        String city = getDriver().findElement(By.id("com.sf.biocapture.activity:id/city")).getText();
        String countryOfOrigin = getDriver().findElement(By.id("com.sf.biocapture.activity:id/country")).getText();
        String stateOfOrigin = getDriver().findElement(By.id("com.sf.biocapture.activity:id/states")).getText();
        String lgaOfOrigin = getDriver().findElement(By.id("com.sf.biocapture.activity:id/lga")).getText();
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/btn_continue_reg")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/page_title")));

        //page 2
        String identificationType = getDriver().findElement(By.id("com.sf.biocapture.activity:id/typeofid")).getText();
        TestUtils.scrollDown();
        String email = getDriver().findElement(By.id("com.sf.biocapture.activity:id/email")).getText();
        String alternatePhoneNumber = getDriver().findElement(By.id("com.sf.biocapture.activity:id/alt_phone_number")).getText();
        String postalCode = getDriver().findElement(By.id("com.sf.biocapture.activity:id/postalcode")).getText();
        String occupation = getDriver().findElement(By.id("com.sf.biocapture.activity:id/occupation")).getText();
        String stateOfResidence = getDriver().findElement(By.id("com.sf.biocapture.activity:id/states_residence")).getText();
        String lgaAreaOfResidence = getDriver().findElement(By.id("com.sf.biocapture.activity:id/lga_residence")).getText();
        String areaOfResidence = getDriver().findElement(By.id("com.sf.biocapture.activity:id/area")).getText();
        String lgaOfRegistration = getDriver().findElement(By.id("com.sf.biocapture.activity:id/lga_of_reg")).getText();

        String NA = "";

        String[] toList = {"Type of Registration:" + typeOfRegistration, "Surname:" + surName, "First Name:" + firstName, "Middle Name:" + middleName,
                "Mother's Maiden Name:" + mothersMaidenName, "Sex:" + sex, "Date of Birth:" + dateOfBirth, "House No:" + houseNo, "Street:" + street, "City:" + city,
                "Country of Origin:" + countryOfOrigin, "State of Origin:" + stateOfOrigin, "LGA of Origin:" + lgaOfOrigin, "Identification Type:" + identificationType,
                "Email Address:" + email, "Alternate Phone No:" + alternatePhoneNumber, "Postal Code:" + postalCode, "Occupation:" + occupation,
                "State of Residence:" + stateOfResidence, "LGA of Residence:" + lgaAreaOfResidence, "Area Of Residence" + areaOfResidence,
                "LGA of Registration:" + lgaOfRegistration
                ,
        };
        for (String field : toList) {
            String name = "";
            String val = NA;
            try {
                String[] fields = field.split(":");
                name = fields[0];
                val = fields[1];
                Assert.assertNotEquals(val, NA);
                testInfo.get().log(Status.INFO, name + " : " + val);
            } catch (Error e) {
                testInfo.get().error(name + " : " + val);
            }
        }
    }

    public static void AssertPassportDetails() throws Exception {

        //Passport Details
        String issuingCountry = getDriver().findElement(By.id("com.sf.biocapture.activity:id/passport_issuing_country")).getText();
        String passportNumber = getDriver().findElement(By.id("com.sf.biocapture.activity:id/passport_number")).getText();
        String expiryDate = getDriver().findElement(By.id("")).getText();

        String NA = "";

        String[] toList = {"Issuing Country:" + issuingCountry, "Passport Number:" + passportNumber, "Expiry Date:" + expiryDate};
        for (String field : toList) {
            String name = "";
            String val = NA;
            try {
                String[] fields = field.split(":");
                name = fields[0];
                val = fields[1];
                Assert.assertNotEquals(val, NA);
                testInfo.get().log(Status.INFO, name + " : " + val);
            } catch (Error e) {
                testInfo.get().error(name + " : " + val);
            }
        }
    }

    public static void AssertCompanyDetails() throws Exception {

        //Company Details
        String companyName = getDriver().findElement(By.id("com.sf.biocapture.activity:id/company_name_descrptn")).getText();
        String registrationNumber = getDriver().findElement(By.id("com.sf.biocapture.activity:id/company_regno")).getText();
        String houseNumber = getDriver().findElement(By.id("com.sf.biocapture.activity:id/house_or_flat_no")).getText();
        String companyAddressStreet = getDriver().findElement(By.id("com.sf.biocapture.activity:id/street")).getText();
        String companyAddressCity = getDriver().findElement(By.id("com.sf.biocapture.activity:id/city")).getText();
        String companyState = getDriver().findElement(By.id("com.sf.biocapture.activity:id/company_state_address")).getText();
        String companyLga = getDriver().findElement(By.id("com.sf.biocapture.activity:id/company_lga_address")).getText();
        String companyPostalCode = getDriver().findElement(By.id("com.sf.biocapture.activity:id/company_postalcode")).getText();

        String NA = "";

        String[] toList = {"Name /Description:" + companyName, "Registration Number:" + registrationNumber, "House No.:" + houseNumber, "Street:" + companyAddressStreet,
                "City:" + companyAddressCity, "Company Address State:" + companyState, "Company Address LGA:" + companyLga, "Postal Code:" + companyPostalCode};
        for (String field : toList) {
            String name = "";
            String val = NA;
            try {
                String[] fields = field.split(":");
                name = fields[0];
                val = fields[1];
                Assert.assertNotEquals(val, NA);
                testInfo.get().log(Status.INFO, name + " : " + val);
            } catch (Error e) {
                testInfo.get().error(name + " : " + val);
            }
        }
    }
}
