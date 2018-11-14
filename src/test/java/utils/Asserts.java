package utils;

import com.aventstack.extentreports.Status;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class Asserts extends TestBase {

    public static void AssertIndividualForm() throws InterruptedException {

        WebDriverWait wait = new WebDriverWait(getDriver(), 30);

        //Page 1
        String typeOfRegistration = getDriver().findElement(By.id("com.sf.biocapture.activity:id/reg_type")).getText();
        String surName = getDriver().findElement(By.id("com.sf.biocapture.activity:id/surnname")).getText();
        String firstName = getDriver().findElement(By.id("com.sf.biocapture.activity:id/firstname")).getText();
        String middleName = getDriver().findElement(By.id("com.sf.biocapture.activity:id/firstname")).getText();
        String mothersMaidenName = getDriver().findElement(By.id("com.sf.biocapture.activity:id/moms_maidenname")).getText();
        String sex;
        if(getDriver().findElement(By.id("com.sf.biocapture.activity:id/male_radio_button")).isSelected()){
            sex  = "Male";
        }else {
            sex = "Female";
        }
        String dateOfBirth = getDriver().findElement(By.id("com.sf.biocapture.activity:id/date_of_birth")).getText();
        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity:id/house_or_flat_no");
        String houseNo = getDriver().findElement(By.id("com.sf.biocapture.activity:id/house_or_flat_no")).getText();
        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity:id/street");
        String street = getDriver().findElement(By.id("com.sf.biocapture.activity:id/street")).getText();
        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity:id/city");
        String city = getDriver().findElement(By.id("com.sf.biocapture.activity:id/city")).getText();
        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity:id/country");
        String countryOfOrigin = getDriver().findElement(By.id("com.sf.biocapture.activity:id/country")).getText();
        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity:id/states");
        String stateOfOrigin = getDriver().findElement(By.id("com.sf.biocapture.activity:id/states")).getText();
        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity:id/lga");
        String lgaOfOrigin = getDriver().findElement(By.id("com.sf.biocapture.activity:id/lga")).getText();
        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity:id/btn_continue_reg");
        getDriver().findElement(By.id("com.sf.biocapture.activity:id/btn_continue_reg")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity:id/page_title")));

        //page 2
        String identificationType = getDriver().findElement(By.id("com.sf.biocapture.activity:id/typeofid")).getText();
        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity:id/email");
        String email = getDriver().findElement(By.id("com.sf.biocapture.activity:id/email")).getText();
        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity:id/alt_phone_number");
        String alternatePhoneNumber = getDriver().findElement(By.id("com.sf.biocapture.activity:id/alt_phone_number")).getText();
        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity:id/postalcode");
        String postalCode = getDriver().findElement(By.id("com.sf.biocapture.activity:id/postalcode")).getText();
        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity:id/occupation");
        String occupation = getDriver().findElement(By.id("com.sf.biocapture.activity:id/occupation")).getText();
        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity:id/states_residence");
        String stateOfResidence = getDriver().findElement(By.id("com.sf.biocapture.activity:id/states_residence")).getText();
        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity:id/lga_residence");
        String lgaAreaOfResidence = getDriver().findElement(By.id("com.sf.biocapture.activity:id/lga_residence")).getText();
        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity:id/area");
        String areaOfResidence = getDriver().findElement(By.id("com.sf.biocapture.activity:id/area")).getText();
        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity:id/lga_of_reg");
        String lgaOfRegistration = getDriver().findElement(By.id("com.sf.biocapture.activity:id/lga_of_reg")).getText();

        String NA = "No Data Found";

        String[] toList = {"Type of Registration:" + typeOfRegistration, "Surname:" + surName, "First Name:" + firstName, "Middle Name:" + middleName,
                "Mother's Maiden Name:" + mothersMaidenName, "Sex:" + sex, "Date of Birth:" + dateOfBirth, "House No:" + houseNo, "Street:" + street, "City:" + city,
                "Country of Origin:" + countryOfOrigin, "State of Origin:" + stateOfOrigin, "LGA of Origin:" + lgaOfOrigin, "Identification Type:" + identificationType,
                "Email Address:" + email, "Alternate Phone No:" + alternatePhoneNumber, "Postal Code:" + postalCode, "Occupation:" + occupation,
                "State of Residence:" + stateOfResidence, "LGA of Residence:" + lgaAreaOfResidence, "Area Of Residence" + areaOfResidence,
                "LGA of Registration:" + lgaOfRegistration
        };
        for (String field : toList) {
            String name = "";
            String val = NA;
            try {
                String[] fields = field.split(":");
                name = fields[0];
                try {
                    val = fields[1];
                }catch(Exception e){
                    val = NA;
                }
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
    
    public static void AssertReportSummary() throws Exception {

        String totalRegistrations = getDriver().findElement(By.id("com.sf.biocapture.activity:id/reg_subscribers")).getText();
        String totalSyncSent = getDriver().findElement(By.id("com.sf.biocapture.activity:id/total_sync_sent")).getText();
        String totalSyncConfirmed = getDriver().findElement(By.id("com.sf.biocapture.activity:id/sync_confirmed")).getText();
        String totalSyncPending = getDriver().findElement(By.id("com.sf.biocapture.activity:id/total_pending")).getText();
        String totalRejected = getDriver().findElement(By.id("com.sf.biocapture.activity:id/total_rejected")).getText();
        String totalActivated = getDriver().findElement(By.id("com.sf.biocapture.activity:id/total_activated")).getText();
        String totalReactivated = getDriver().findElement(By.id("com.sf.biocapture.activity:id/total_reactivated")).getText();
        String totalSimSwap = getDriver().findElement(By.id("com.sf.biocapture.activity:id/total_swaps")).getText();
        String registeredSIMs = getDriver().findElement(By.id("com.sf.biocapture.activity:id/reg_sims")).getText();
        String confirmedSIMs = getDriver().findElement(By.id("com.sf.biocapture.activity:id/sims_confirmed")).getText();
        String duplicateSIMs = getDriver().findElement(By.id("com.sf.biocapture.activity:id/duplicate_sims")).getText();
        
        String NA = "";

        String[] toList = {"Total Registrations: " + totalRegistrations, "Total Sync Sent: " + totalSyncSent, "Total Sync Confirmed: " + totalSyncConfirmed, "Total Sync Pending:" + totalSyncPending,
                "Total Rejected: " + totalRejected, "Total Activated: " + totalActivated, "Total Reactivated: " + totalReactivated, "Total SIM Swap: " + totalSimSwap
                , "Registered SIMS: " + registeredSIMs, "Confirmed SIMS: " + confirmedSIMs, "Duplicate SIMS: " + duplicateSIMs};
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
