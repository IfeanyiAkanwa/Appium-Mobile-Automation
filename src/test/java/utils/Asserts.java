package utils;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Parameters;

public class Asserts extends TestBase {
    private static StringBuffer verificationErrors = new StringBuffer();

    public static void AssertIndividualForm() throws Exception {
        WebDriverWait wait = new WebDriverWait(getDriver(), 60);
        String assertDetails = "Assert individual form of registered subscriber";
        Markup ad = MarkupHelper.createLabel(assertDetails, ExtentColor.GREEN);
        testInfo.get().info(ad);

        String typeOfRegistration = getDriver().findElement(By.xpath("//android.widget.TextView[@text='Individual']")).getText();
        String surName = getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/surNameTXT")).getText();
        String firstName = getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/firstNameTXT")).getText();

        String middleName = getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/middleNameTXT")).getText();
        String mothersMaidenName = getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/momsMaidenNameTXT"))
                .getText();
        String sex;
        if (getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/maleRadioButton")).isSelected()) {
            sex = "Male";
        } else {
            sex = "Female";
        }
        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/dateOfBirth");
        String dateOfBirth = getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/dateOfBirth")).getText();
        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/alternatePhone");
        String altPhoneNumber = getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/alternatePhone")).getText();
        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/alternateEmail");
        String email = getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/alternateEmail")).getText();
        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/spinnerOccupation");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/spinnerOccupation")));
        Thread.sleep(1500);
        String occupation = getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/occupationTXT")).getText();

        String empty = "";
        Map<String, String> fields = new HashMap<>();
        fields.put("Type of registration", typeOfRegistration);
        fields.put("Surname", surName);
        fields.put("First Name", firstName);
        fields.put("Middle name", middleName);
        fields.put("Mother's maiden name", mothersMaidenName);
        fields.put("Sex", sex);
        fields.put("Date of Birth", dateOfBirth);
        fields.put("Alternate phone Number", altPhoneNumber);
        fields.put("Email", email);
        fields.put("Occupation", occupation);

        for (Map.Entry<String, String> entry : fields.entrySet()) {
            try {
                Assert.assertNotEquals(entry.getValue(), empty);
                Assert.assertNotEquals(entry.getValue(), null);
                testInfo.get().log(Status.INFO, "<b>" + entry.getKey() + "</b> : " + entry.getValue());
            } catch (Error e) {
                testInfo.get().error("<b>" + entry.getKey() + " </b> : " + entry.getValue());
            }

        }
    }

    @Parameters({"dataEnv"})
    public static void AssertAddresstDetails(String dataEnv) throws Exception {
        WebDriverWait wait = new WebDriverWait(getDriver(), 60);
        Map<String, String> fields = new HashMap<>();
        String assertDetails = "Asserting address Details of registered subscriber";
        Markup ad = MarkupHelper.createLabel(assertDetails, ExtentColor.GREEN);
        testInfo.get().info(ad);
        JSONParser parser = new JSONParser();
        JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resource/" + dataEnv + "/data.conf.json"));
        JSONObject envs = (JSONObject) config.get("IndividualForeignerDetails");

        String city = (String) envs.get("city");
        String state = (String) envs.get("state");
        String LGA = (String) envs.get("LGA");
        String area = (String) envs.get("area");
        String stateOfOrigin="";
        String lgaOfOrigin="";
        String countryOfOrigin="";
        String stateOfResidence="";
        String lgaOfResidence="";
        Thread.sleep(1500);
        try{
             countryOfOrigin = getDriver().findElement(By.xpath("//android.widget.TextView[@text='Nigeria']")).getText();
        }catch (Exception e){
             countryOfOrigin = getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='NIGERIA']")).getText();
        }
        try {
            stateOfOrigin = getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='"+state+"']")).getText();
            if (stateOfOrigin.equals("[Select State]*")) {
                fields.put("State of origin", stateOfOrigin);
            }
            //Assert.assertNotEquals(stateOfOrigin, "[Select State]*");
            lgaOfOrigin = getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='"+LGA+"']")).getText();
            if (lgaOfOrigin.equals("[Select LGA]*")) {
                fields.put("LGA of Origin", lgaOfOrigin);
            }
        }catch (Exception e){
        }
        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/ninEditText");
        String nin = getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/ninEditText")).getText();
        if (nin.equals("NIN")) {
            fields.put("NIN", nin);
        }
        try{


            //Assert.assertNotEquals(lgaOfOrigin, "[Select LGA]*");
            TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/stateOfResidenceSpinner");
            stateOfResidence = getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='"+state+"']")).getText();
            if (stateOfResidence.equals("[Select State]*")) {
                fields.put("State of Residence", stateOfResidence);
            }

            //Assert.assertNotEquals(stateOfResidence, "[Select State]*");
            TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/lgaOfResidenceSpinner");
            lgaOfResidence = getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='"+LGA+"']")).getText();
            if (lgaOfResidence.equals("[Select State]*")) {
                fields.put("LGA of Residence", lgaOfResidence);
            }
        }catch (Exception e){
            //Select State
            getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/stateOfResidenceSpinner")).click();
            Thread.sleep(500);
            getDriver().findElement(By.xpath("//android.widget.TextView[@text='"+state+"']")).click();
            Thread.sleep(500);


            //Select LGA
            getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/lgaOfResidenceSpinner")).click();
            Thread.sleep(500);
            getDriver().findElement(By.xpath("//android.widget.TextView[@text='"+LGA+"']")).click();
            Thread.sleep(500);

            //Assert.assertNotEquals(lgaOfOrigin, "[Select LGA]*");
            TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/stateOfResidenceSpinner");
            stateOfResidence = getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='"+state+"']")).getText();
            if (stateOfResidence.equals("[Select State]*")) {
                fields.put("State of Residence", stateOfResidence);
            }

            //Assert.assertNotEquals(stateOfResidence, "[Select State]*");
            TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/lgaOfResidenceSpinner");
            lgaOfResidence = getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='"+LGA+"']")).getText();
            if (lgaOfResidence.equals("[Select State]*")) {
                fields.put("LGA of Residence", lgaOfResidence);
            }
        }

        //Assert.assertNotEquals(lgaOfResidence, "[Select LGA]*");
        TestUtils.scrollDown();
        String areaOfResidence="";
        try{
             areaOfResidence = getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='"+area+"']")).getText();
        }catch (Exception e){
            areaOfResidence = "";
        }
        if (areaOfResidence.equals("[Select Area]*")) {
            fields.put("Area of Residence", areaOfResidence);
        }
        Thread.sleep(500);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/houseNumberEditText")));
        Thread.sleep(500);
        String houseNum = getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/houseNumberEditText")).getText();
        String street = getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/streetEditText")).getText();
        try{
            city = getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='"+city+"']")).getText();
        }catch (Exception e){
            city = "";
        }
        String postalCode = getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/postalText")).getText();

        String empty = "";

        fields.put("Country of Origin", countryOfOrigin);
        fields.put("State of origin", stateOfOrigin);
        fields.put("LGA of Origin", lgaOfOrigin);
        fields.put("State of Residence", stateOfResidence);
        fields.put("LGA of Residence", lgaOfResidence);
        fields.put("Area of Residence", areaOfResidence);
        fields.put("House number", houseNum);
        fields.put("Street", street);
        fields.put("City", city);
        fields.put("Postal code", postalCode);
        fields.put("NIN", nin);

        for (Map.Entry<String, String> entry : fields.entrySet()) {
            try {
                Assert.assertNotEquals(entry.getValue(), empty);
                Assert.assertNotEquals(entry.getValue(), null);
                testInfo.get().log(Status.INFO, "<b>" + entry.getKey() + "</b> : " + entry.getValue());
            } catch (Error e) {
                testInfo.get().error("<b>" + entry.getKey() + " </b> : " + entry.getValue());
            }

        }
    }

    public static void AssertTMDetails() throws Exception {
        WebDriverWait wait = new WebDriverWait(getDriver(), 60);
        Map<String, String> fields = new HashMap<>();
        String assertDetails = "Asserting TM Details of registered subscriber";
        TestUtils.testTitle(assertDetails);
        String surName = getDriver().findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/androidx.viewpager.widget.ViewPager/android.widget.ScrollView/android.view.ViewGroup/android.widget.FrameLayout/android.view.ViewGroup/androidx.recyclerview.widget.RecyclerView/android.view.ViewGroup[1]/android.widget.TextView[2]")).getText();
        String firstName = getDriver().findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/androidx.viewpager.widget.ViewPager/android.widget.ScrollView/android.view.ViewGroup/android.widget.FrameLayout/android.view.ViewGroup/androidx.recyclerview.widget.RecyclerView/android.view.ViewGroup[2]/android.widget.TextView[2]")).getText();
        String otherName = getDriver().findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/androidx.viewpager.widget.ViewPager/android.widget.ScrollView/android.view.ViewGroup/android.widget.FrameLayout/android.view.ViewGroup/androidx.recyclerview.widget.RecyclerView/android.view.ViewGroup[3]/android.widget.TextView[2]")).getText();
        String mothersName = getDriver().findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/androidx.viewpager.widget.ViewPager/android.widget.ScrollView/android.view.ViewGroup/android.widget.FrameLayout/android.view.ViewGroup/androidx.recyclerview.widget.RecyclerView/android.view.ViewGroup[4]/android.widget.TextView[2]")).getText();

        String gender = getDriver().findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/androidx.viewpager.widget.ViewPager/android.widget.ScrollView/android.view.ViewGroup/android.widget.FrameLayout/android.view.ViewGroup/androidx.recyclerview.widget.RecyclerView/android.view.ViewGroup[5]/android.widget.TextView[2]")).getText();
        String dob = getDriver().findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/androidx.viewpager.widget.ViewPager/android.widget.ScrollView/android.view.ViewGroup/android.widget.FrameLayout/android.view.ViewGroup/androidx.recyclerview.widget.RecyclerView/android.view.ViewGroup[6]/android.widget.TextView[2]")).getText();
        String phoneNumber = getDriver().findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/androidx.viewpager.widget.ViewPager/android.widget.ScrollView/android.view.ViewGroup/android.widget.FrameLayout/android.view.ViewGroup/androidx.recyclerview.widget.RecyclerView/android.view.ViewGroup[7]/android.widget.TextView[2]")).getText();
        String sim_serial = getDriver().findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/androidx.viewpager.widget.ViewPager/android.widget.ScrollView/android.view.ViewGroup/android.widget.FrameLayout/android.view.ViewGroup/androidx.recyclerview.widget.RecyclerView/android.view.ViewGroup[8]/android.widget.TextView[2]")).getText();

        String alt_phone = getDriver().findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/androidx.viewpager.widget.ViewPager/android.widget.ScrollView/android.view.ViewGroup/android.widget.FrameLayout/android.view.ViewGroup/androidx.recyclerview.widget.RecyclerView/android.view.ViewGroup[9]/android.widget.TextView[2]")).getText();
        String nin = getDriver().findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/androidx.viewpager.widget.ViewPager/android.widget.ScrollView/android.view.ViewGroup/android.widget.FrameLayout/android.view.ViewGroup/androidx.recyclerview.widget.RecyclerView/android.view.ViewGroup[10]/android.widget.TextView[2]")).getText();
        String email = getDriver().findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/androidx.viewpager.widget.ViewPager/android.widget.ScrollView/android.view.ViewGroup/android.widget.FrameLayout/android.view.ViewGroup/androidx.recyclerview.widget.RecyclerView/android.view.ViewGroup[11]/android.widget.TextView[2]")).getText();
        String houseNum = getDriver().findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/androidx.viewpager.widget.ViewPager/android.widget.ScrollView/android.view.ViewGroup/android.widget.FrameLayout/android.view.ViewGroup/androidx.recyclerview.widget.RecyclerView/android.view.ViewGroup[12]/android.widget.TextView[2]")).getText();

        String street = getDriver().findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/androidx.viewpager.widget.ViewPager/android.widget.ScrollView/android.view.ViewGroup/android.widget.FrameLayout/android.view.ViewGroup/androidx.recyclerview.widget.RecyclerView/android.view.ViewGroup[13]/android.widget.TextView[2]")).getText();
        String city = getDriver().findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/androidx.viewpager.widget.ViewPager/android.widget.ScrollView/android.view.ViewGroup/android.widget.FrameLayout/android.view.ViewGroup/androidx.recyclerview.widget.RecyclerView/android.view.ViewGroup[14]/android.widget.TextView[2]")).getText();
        String postalCode = getDriver().findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/androidx.viewpager.widget.ViewPager/android.widget.ScrollView/android.view.ViewGroup/android.widget.FrameLayout/android.view.ViewGroup/androidx.recyclerview.widget.RecyclerView/android.view.ViewGroup[15]/android.widget.TextView[2]")).getText();
        String occupation = getDriver().findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/androidx.viewpager.widget.ViewPager/android.widget.ScrollView/android.view.ViewGroup/android.widget.FrameLayout/android.view.ViewGroup/androidx.recyclerview.widget.RecyclerView/android.view.ViewGroup[16]/android.widget.TextView[2]")).getText();
        TestUtils.scrollDown();
        TestUtils.scrollDown();
        String countryOfOrigin = getDriver().findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/androidx.viewpager.widget.ViewPager/android.widget.ScrollView/android.view.ViewGroup/android.widget.FrameLayout[1]/android.view.ViewGroup/androidx.recyclerview.widget.RecyclerView/android.view.ViewGroup[2]/android.widget.TextView[2]")).getText();
        String stateOfOrigin = getDriver().findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/androidx.viewpager.widget.ViewPager/android.widget.ScrollView/android.view.ViewGroup/android.widget.FrameLayout[1]/android.view.ViewGroup/androidx.recyclerview.widget.RecyclerView/android.view.ViewGroup[3]/android.widget.TextView[2]")).getText();
        String lgaOfOrigin = getDriver().findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/androidx.viewpager.widget.ViewPager/android.widget.ScrollView/android.view.ViewGroup/android.widget.FrameLayout[1]/android.view.ViewGroup/androidx.recyclerview.widget.RecyclerView/android.view.ViewGroup[4]/android.widget.TextView[2]")).getText();
        String countryOfResidence = getDriver().findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/androidx.viewpager.widget.ViewPager/android.widget.ScrollView/android.view.ViewGroup/android.widget.FrameLayout[1]/android.view.ViewGroup/androidx.recyclerview.widget.RecyclerView/android.view.ViewGroup[5]/android.widget.TextView[2]")).getText();

        String stateOfResidence = getDriver().findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/androidx.viewpager.widget.ViewPager/android.widget.ScrollView/android.view.ViewGroup/android.widget.FrameLayout/android.view.ViewGroup/androidx.recyclerview.widget.RecyclerView/android.view.ViewGroup[6]/android.widget.TextView[2]")).getText();
        String lgaOfResidence = getDriver().findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/androidx.viewpager.widget.ViewPager/android.widget.ScrollView/android.view.ViewGroup/android.widget.FrameLayout/android.view.ViewGroup/androidx.recyclerview.widget.RecyclerView/android.view.ViewGroup[7]/android.widget.TextView[2]")).getText();
        String areaOfResidence = getDriver().findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/androidx.viewpager.widget.ViewPager/android.widget.ScrollView/android.view.ViewGroup/android.widget.FrameLayout/android.view.ViewGroup/androidx.recyclerview.widget.RecyclerView/android.view.ViewGroup[8]/android.widget.TextView[2]")).getText();
        String stateOfRegistration = getDriver().findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/androidx.viewpager.widget.ViewPager/android.widget.ScrollView/android.view.ViewGroup/android.widget.FrameLayout/android.view.ViewGroup/androidx.recyclerview.widget.RecyclerView/android.view.ViewGroup[9]/android.widget.TextView[2]")).getText();
        String lgaOfRegistration = getDriver().findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/androidx.viewpager.widget.ViewPager/android.widget.ScrollView/android.view.ViewGroup/android.widget.FrameLayout/android.view.ViewGroup/androidx.recyclerview.widget.RecyclerView/android.view.ViewGroup[10]/android.widget.TextView[2]")).getText();

        String empty = "";

        fields.put("Surname", surName);
        fields.put("Firstname", firstName);
        fields.put("Othername", otherName);
        fields.put("Mother's maiden name", mothersName);
        fields.put("Gender", gender);
        fields.put("DOB", dob);
        fields.put("Phone Number", phoneNumber);
        fields.put("SIM Serial", sim_serial);
        fields.put("Alternate Phone", alt_phone);
        fields.put("NIN", nin);
        fields.put("Email", email);
        fields.put("House number", houseNum);
        fields.put("Street", street);
        fields.put("City", city);
        fields.put("Postal code", postalCode);
        fields.put("NIN", nin);
        fields.put("Occupation", occupation);

        fields.put("Birth Country", countryOfOrigin);
        fields.put("State of origin", stateOfOrigin);
        fields.put("LGA of Origin", lgaOfOrigin);
        fields.put("State of Residence", stateOfResidence);
        fields.put("LGA of Residence", lgaOfResidence);
        fields.put("Area of Residence", areaOfResidence);
        fields.put("Country of Residence", countryOfResidence);
        fields.put("State of Registration", stateOfRegistration);
        fields.put("LGA of Registration", lgaOfRegistration);

        for (Map.Entry<String, String> entry : fields.entrySet()) {
            try {
                Assert.assertNotEquals(entry.getValue(), empty);
                Assert.assertNotEquals(entry.getValue(), null);
                testInfo.get().log(Status.INFO, "<b>" + entry.getKey() + "</b> : " + entry.getValue());
            } catch (Error e) {
                testInfo.get().error("<b>" + entry.getKey() + " </b> : " + entry.getValue());
            }

        }
    }

    public static void AssertPassportDetails() throws Exception {

        // Passport Details
        String issuingCountry = getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/passport_issuing_country"))
                .getText();
        String passportNumber = getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/passport_number"))
                .getText();
        String expiryDate = getDriver().findElement(By.id("")).getText();

        String NA = "";

        String[] toList = {"Issuing Country:" + issuingCountry, "Passport Number:" + passportNumber,
                "Expiry Date:" + expiryDate};
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

        String assertDetails = "Asserting Company Details of registered subscriber";
        Markup ad = MarkupHelper.createLabel(assertDetails, ExtentColor.GREEN);
        testInfo.get().info(ad);

        // Company Details
        String companyName = getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/company_name_descrptn"))
                .getText();
        String registrationNumber = getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/company_regno"))
                .getText();
        String houseNumber = getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/house_or_flat_no")).getText();
        String companyAddressStreet = getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/street")).getText();
        String companyAddressCity = getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/city")).getText();
        String companyState = getDriver().findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.ScrollView/android.widget.TableLayout/android.widget.Spinner[1]/android.widget.TextView"))
                .getText();
        String companyLga = getDriver().findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.ScrollView/android.widget.TableLayout/android.widget.Spinner[2]/android.widget.TextView"))
                .getText();
        String companyPostalCode = getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/company_postalcode"))
                .getText();

        String empty = "";
        Map<String, String> fields = new HashMap<>();
        fields.put("Name /Description", companyName);
        fields.put("Registration Number", registrationNumber);
        fields.put("House No.", houseNumber);
        fields.put("Street", companyAddressStreet);
        fields.put("City", companyAddressCity);
        fields.put("Company Address State", companyState);
        fields.put("Company Address LGA", companyLga);
        fields.put("Postal Code", companyPostalCode);

        for (Map.Entry<String, String> entry : fields.entrySet()) {
            try {
                Assert.assertNotEquals(entry.getValue(), empty);
                Assert.assertNotEquals(entry.getValue(), null);
                testInfo.get().log(Status.INFO, "<b>" + entry.getKey() + "</b> : " + entry.getValue());
            } catch (Error e) {
                testInfo.get().error("<b>" + entry.getKey() + " </b> : " + entry.getValue());
            }

        }
    }

    public static void AssertReportSummary() throws Exception {

        TestUtils.testTitle("Report Summary of Registrations");
        String totalRegistrations = getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/reg_subscribers"))
                .getText();
        String totalSyncSent = getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/total_sync_sent"))
                .getText();
        String totalSyncConfirmed = getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/sync_confirmed"))
                .getText();
        String totalSyncPending = getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/total_pending"))
                .getText();
        String totalRejected = getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/total_rejected")).getText();
        String totalActivated = getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/total_activated"))
                .getText();
        String totalReactivated = getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/total_reactivated"))
                .getText();
        String totalSimSwap = getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/total_swaps")).getText();


        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/reg_sims");

        String registeredSIMs = getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/reg_sims")).getText();
        String confirmedSIMs = getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/sims_confirmed")).getText();
        String duplicateSIMs = getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/duplicate_sims")).getText();

        String NA = "";

        String[] toList = {"Total Registrations: " + totalRegistrations, "Total Sync Sent: " + totalSyncSent,
                "Total Sync Confirmed: " + totalSyncConfirmed, "Total Sync Pending:" + totalSyncPending,
                "Total Rejected: " + totalRejected, "Total Activated: " + totalActivated,
                "Total Reactivated: " + totalReactivated, "Total SIM Swap: " + totalSimSwap,
                "Registered SIMS: " + registeredSIMs, "Confirmed SIMS: " + confirmedSIMs,
                "Duplicate SIMS: " + duplicateSIMs};

        for (String field : toList) {
            String name = "";
            String val = NA;
            try {
                String[] fields = field.split(":");
                name = fields[0];
                val = fields[1];
                Assert.assertNotEquals(val, NA);
                testInfo.get().log(Status.INFO, "<b>" + name + " : </b>" + val);
            } catch (Error e) {
                testInfo.get().error("<b>" + name + " : </b>" + val);
            }
        }

        int actualTotalRegistrationsVal = TestUtils.convertToInt(totalRegistrations);
        int actualTotalSyncSentVal = TestUtils.convertToInt(totalSyncSent);
        int actualTotalSyncPendingVal = TestUtils.convertToInt(totalSyncPending);
        int actualTotalSyncConfirmedVal = TestUtils.convertToInt(totalSyncConfirmed);

        int expectedTotalRegistrationsVal = actualTotalSyncSentVal + actualTotalSyncPendingVal;

        try {
            Assert.assertEquals(expectedTotalRegistrationsVal, actualTotalRegistrationsVal);
            testInfo.get().log(Status.INFO, "Total Registrations (" + expectedTotalRegistrationsVal + ") is equal to summation of total Sync sent (" + actualTotalSyncSentVal + ") + total Sync pending (" + actualTotalSyncPendingVal + ") which is also equal to  total Sync confirmed (" + actualTotalSyncConfirmedVal + ")");
        } catch (Error e) {
            verificationErrors.append(e.toString());
            String verificationErrorString = verificationErrors.toString();
            testInfo.get().error("Summation not equal");
            testInfo.get().error(verificationErrorString);
        }
        //Refresh
        TestUtils.scrollUntilElementIsVisible("ID", "com.sf.biocapture.activity" + Id + ":id/refresh_button");
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/refresh_button")).click();


    }

    public static void AssertSubscriberInfo230() throws Exception {

        String assertDetails = "Asserting returned Subscriber's Details";
        Markup ad = MarkupHelper.createLabel(assertDetails, ExtentColor.BLUE);
        testInfo.get().info(ad);
        String msisdn = getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/msisdn")).getText();
        String simSerial = getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/sim_serial")).getText();
        String activationStatus = getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/activation_status"))
                .getText();
        String failureReason = getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/failure_reason")).getText();
        String Agent = getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/agent")).getText();
        String DOB = getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/reg_date")).getText();

        String NA = "";

        String[] toList = {"MSISDN: " + msisdn, "SIM Serial: " + simSerial, "Activation Status: " + activationStatus,
                "Failure Reason: " + failureReason, "Agent: " + Agent, "Date of Birth: " + DOB};
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

    public static void assertBasicInfoAddReg230() throws Exception {
        WebDriverWait wait = new WebDriverWait(getDriver(), 60);

        TestUtils.testTitle("Assert returned Basic Info of Additional Registration");
        String surname = getDriver().findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.ListView/android.widget.LinearLayout[1]/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.TextView[2]")).getText();
        String firstName = getDriver().findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.ListView/android.widget.LinearLayout[2]/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.TextView[2]")).getText();
        String mothersMaidenName = getDriver().findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.ListView/android.widget.LinearLayout[3]/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.TextView[2]")).getText();
        String gender = getDriver().findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.ListView/android.widget.LinearLayout[4]/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.TextView[2]")).getText();
        String street = getDriver().findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.ListView/android.widget.LinearLayout[5]/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.TextView[2]")).getText();
        String city = getDriver().findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.ListView/android.widget.LinearLayout[6]/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.TextView[2]")).getText();
        String countryOfOrigin = getDriver().findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.ListView/android.widget.LinearLayout[7]/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.TextView[2]")).getText();
        String stateOfOrigin = getDriver().findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.ListView/android.widget.LinearLayout[8]/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.TextView[2]")).getText();
        TestUtils.scrollDown();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='LGA of Origin']")));
        Thread.sleep(500);
        String lgaOfOrigin = getDriver().findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.ListView/android.widget.LinearLayout[4]/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.TextView[2]")).getText();
        String areaOfResidence = getDriver().findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.ListView/android.widget.LinearLayout[5]/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.TextView[2]")).getText();
        String typeOfIdentification = getDriver().findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.ListView/android.widget.LinearLayout[6]/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.TextView[2]")).getText();
        String stateOfResidence = getDriver().findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.ListView/android.widget.LinearLayout[7]/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.TextView[2]")).getText();
        String lgaOfResidence = getDriver().findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.ListView/android.widget.LinearLayout[8]/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.TextView[2]")).getText();
        String lgaOfRegistration = getDriver().findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.ListView/android.widget.LinearLayout[9]/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.TextView[2]")).getText();
        String NA = "";

        String[] toList = {"Surname: " + surname, "First Name: " + firstName, "Mother's maiden name: " + mothersMaidenName, "Gender: " + gender,
                "Street: " + street, "City: " + city, "Country of Origin: " + countryOfOrigin, "State of Origin: " + stateOfOrigin,
                "LGA of Origin: " + lgaOfOrigin, "Area of residence: " + areaOfResidence, "Type of identification: " + typeOfIdentification, "State of Residence: "
                + stateOfResidence, "LGA of Residence: " + lgaOfResidence, "LGA of Registration: " + lgaOfRegistration,};
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

    public static void assertTransactionIdRecords(WebElement webElement) throws Exception {

        String assertDetails = "Asserting returned Subscriber's Details";
        Markup ad = MarkupHelper.createLabel(assertDetails, ExtentColor.BLUE);
        testInfo.get().info(ad);
        String firstName = webElement.findElement(By.id("com.sf.biocapture.activity:id/first_name_value")).getText();
        String surName = webElement.findElement(By.id("com.sf.biocapture.activity:id/surname_value")).getText();
        String gender = webElement.findElement(By.id("com.sf.biocapture.activity:id/gender_value")).getText();
        String dateOfBirth = webElement.findElement(By.id("com.sf.biocapture.activity:id/transaction_id_date_of_birth_value")).getText();
        String dateOfReg = webElement.findElement(By.id("com.sf.biocapture.activity:id/date_of_registration_value")).getText();
        String transactionID = webElement.findElement(By.id("com.sf.biocapture.activity:id/transaction_id_value")).getText();

        String NA = "";

        String[] toList = {"First Name: " + firstName, "Surname: " + surName, "Gender: " + gender,
                "Date of Birth: " + dateOfBirth, "Date of Registration: " + dateOfReg, "Transaction ID: " + transactionID};
        for (String field : toList) {
            String name = "";
            String val = NA;
            try {
                String[] fields = field.split(":");
                name = fields[0];
                val = fields[1];
                Assert.assertNotEquals(val, NA);
                testInfo.get().log(Status.INFO, "<b>" + name + " : </b>" + val);
            } catch (Error e) {
                testInfo.get().error("<b>" + name + " : </b>" + val);
            }
        }
    }

    public static void assertAddedNumbers() throws Exception {

        String assertDetails = "Assert Valid Numbers";
        Markup ad = MarkupHelper.createLabel(assertDetails, ExtentColor.BLUE);
        testInfo.get().info(ad);
        String msisdn = getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/phone_no_view")).getText();
        String simSerial = getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/tv_sim_serial")).getText();

        String NA = "";

        String[] toList = {"Msisdn: " + msisdn, "Sim Serial: " + simSerial};
        for (String field : toList) {
            String name = "";
            String val = NA;
            try {
                String[] fields = field.split(":");
                name = fields[0];
                val = fields[1];
                Assert.assertNotEquals(val, NA);
                testInfo.get().log(Status.INFO, "<b>" + name + " : </b>" + val);
            } catch (Error e) {
                testInfo.get().error("<b>" + name + " : </b>" + val);
            }
        }
    }

    public static void assertSubscriberFullNameAddReg() throws Exception {

        String assertDetails = "Assert Returned Subscriber Full name after Number Validation";
        Markup ad = MarkupHelper.createLabel(assertDetails, ExtentColor.BLUE);
        testInfo.get().info(ad);
        String firstName = getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/tv_first_name")).getText();
        String Surname = getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/tv_surname")).getText();

        String NA = "";

        String[] toList = {"First Name: " + firstName, "Surname: " + Surname};
        for (String field : toList) {
            String name = "";
            String val = NA;
            try {
                String[] fields = field.split(":");
                name = fields[0];
                val = fields[1];
                Assert.assertNotEquals(val, NA);
                testInfo.get().log(Status.INFO, "<b>" + name + " : </b>" + val);
            } catch (Error e) {
                testInfo.get().error("<b>" + name + " : </b>" + val);
            }
        }
    }

    public static void AssertSearchDetails() {

        String issueID = getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/logIssueId"))
                .getText();
        String status = getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/logIssueStatus"))
                .getText();
        String dateLogged = getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/dateIssueLogged")).getText();
        String dateResolved = getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/dateIssueResolved")).getText();
        String username = getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/userName")).getText();
        String issueSummary = getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/logIssueSummary")).getText();
        String description = getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/logIssueDescription")).getText();
        String resolutionMessage = getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/resolutionIssueMsg")).getText();

        String empty = "";
        Map<String, String> fields = new HashMap<>();
        fields.put("Issue ID", issueID);
        fields.put("Status", status);
        fields.put("Date Logged", dateLogged);
        fields.put("Date Resolved", dateResolved);
        fields.put("Username", username);
        fields.put("Issue Summary", issueSummary);
        fields.put("Description", description);
        fields.put("Resolution Message", resolutionMessage);

        for (Map.Entry<String, String> entry : fields.entrySet()) {
            try {
                Assert.assertNotEquals(entry.getValue(), empty);
                Assert.assertNotEquals(entry.getValue(), null);
                testInfo.get().log(Status.INFO, "<b>" + entry.getKey() + "</b> : " + entry.getValue());
            } catch (Error e) {
                testInfo.get().error("<b>" + entry.getKey() + " </b> : " + entry.getValue());
            }

        }
    }


    public static void AssertSwapSummary() {

        String mandatoryChecks = getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/mandatory_checks_status")).getText();
        String optionalParameter = getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/optional_params_status")).getText();
        String authParameter = getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/auth_parameter_status")).getText();
        String comment = getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/comment")).getText();

        String empty = "";
        Map<String, String> fields = new HashMap<>();

        fields.put("Mandatory Checks", mandatoryChecks);
        fields.put("Optional Parameter (Minimum 2)", optionalParameter);
        fields.put("Authentication Parameter Passed:", authParameter);
        fields.put("Comment", comment);

        for (Map.Entry<String, String> entry : fields.entrySet()) {
            try {
                Assert.assertNotEquals(entry.getValue(), empty);
                Assert.assertNotEquals(entry.getValue(), null);
                testInfo.get().log(Status.INFO, "<b>" + entry.getKey() + "</b> : " + entry.getValue());
            } catch (Error e) {
                testInfo.get().error("<b>" + entry.getKey() + " </b> : " + entry.getValue());
            }

        }
    }

    public static void AssertSwapDataPreview(String simSwapType, String simSwapCategory, String subscriberType, String fName, String lName, String mmn, String gender, String dob, String alternate_phone, String serial, String activation_year, String valid_Msisdn, String new_msisdn, String valid_sim_serial, String puk, String nin, String email, String address, String occupation, String nationality, String state) throws InterruptedException {

        simSwapType = getDriver().findElement(By.xpath("//android.widget.TextView[@text='"+simSwapType+"']")).getText();
        simSwapCategory = getDriver().findElement(By.xpath("//android.widget.TextView[@text='"+simSwapCategory+"']")).getText();
        subscriberType = getDriver().findElement(By.xpath("//android.widget.TextView[@text='"+subscriberType+"']")).getText();
        fName = getDriver().findElement(By.xpath("//android.widget.TextView[@text='"+fName+"']")).getText();
        lName = getDriver().findElement(By.xpath("//android.widget.TextView[@text='"+lName+"']")).getText();
        mmn = getDriver().findElement(By.xpath("//android.widget.TextView[@text='"+mmn+"']")).getText();
        gender = getDriver().findElement(By.xpath("//android.widget.TextView[@text='"+gender+"']")).getText();
        dob = getDriver().findElement(By.xpath("//android.widget.TextView[@text='"+dob+"']")).getText();
        valid_Msisdn = getDriver().findElement(By.xpath("//android.widget.TextView[@text='"+valid_Msisdn+"']")).getText();
        serial = getDriver().findElement(By.xpath("//android.widget.TextView[@text='"+serial+"']")).getText();

        TestUtils.scrollUntilElementIsVisible("XPATH", "//android.widget.TextView[@text='"+activation_year+"']");
        activation_year = getDriver().findElement(By.xpath("//android.widget.TextView[@text='"+activation_year+"']")).getText();
        new_msisdn = getDriver().findElement(By.xpath("//android.widget.TextView[@text='"+new_msisdn+"']")).getText();
        valid_sim_serial = getDriver().findElement(By.xpath("//android.widget.TextView[@text='"+valid_sim_serial+"']")).getText();
        puk = getDriver().findElement(By.xpath("//android.widget.TextView[@text='"+puk+"']")).getText();
        alternate_phone = getDriver().findElement(By.xpath("//android.widget.TextView[@text='"+alternate_phone+"']")).getText();

        TestUtils.scrollUntilElementIsVisible("XPATH", "//android.widget.TextView[@text='Update Type:']");
        try{
            nin = getDriver().findElement(By.xpath("//android.widget.TextView[@text='"+nin+"']")).getText();
        }catch (Exception e){
            testInfo.get().error("NIN comparison failed");
        }

        String ninStatus = getDriver().findElement(By.xpath("//android.widget.TextView[@text='NIN_VERIFIED']")).getText();
        email = getDriver().findElement(By.xpath("//android.widget.TextView[@text='"+email+"']")).getText();
        address = getDriver().findElement(By.xpath("//android.widget.TextView[@text='"+address+"']")).getText();

        occupation = getDriver().findElement(By.xpath("//android.widget.TextView[@text='"+occupation+"']")).getText();
        nationality = getDriver().findElement(By.xpath("//android.widget.TextView[@text='"+nationality+"']")).getText();
        state = getDriver().findElement(By.xpath("//android.widget.TextView[@text='"+state+"']")).getText();

        String empty = "";
        Map<String, String> fields = new HashMap<>();

        fields.put("SIM Swap Type:", simSwapType);
        fields.put("SIM Swap Category:", simSwapCategory);
        fields.put("Subscriber Type:", subscriberType);
        fields.put("First Name:", fName);
        fields.put("Last Name:", lName);
        fields.put("Mother's Maiden Name:", mmn);
        fields.put("Gender:", gender);
        fields.put("Date of Birth:", dob);
        fields.put("Existing Phone Number:", valid_Msisdn);
        fields.put("Existing SIM Serial:", serial);
        fields.put("Activation Year:", activation_year);
        fields.put("Target Phone Number:", new_msisdn);
        fields.put("Target SIM Serial:", valid_sim_serial);
        fields.put("Existing PUK:", puk);
        fields.put("Alternate Phone Number:", alternate_phone);
        fields.put("NIN:", nin);
        fields.put("NIN Status:", ninStatus);
        fields.put("Agent Email:", email);
        fields.put("Residential Address:", address);
        fields.put("Occupation:", occupation);
        fields.put("Nationality:", nationality);
        fields.put("State of Origin:", state);

        for (Map.Entry<String, String> entry : fields.entrySet()) {
            try {
                Assert.assertNotEquals(entry.getValue(), empty);
                Assert.assertNotEquals(entry.getValue(), null);
                testInfo.get().log(Status.INFO, "<b>" + entry.getKey() + "</b> : " + entry.getValue());
            } catch (Error e) {
                testInfo.get().error("<b>" + entry.getKey() + " </b> : " + entry.getValue());
            }

        }
    }
}
