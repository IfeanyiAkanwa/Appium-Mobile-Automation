package admin;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.Asserts;
import utils.TestBase;
import utils.TestUtils;

import java.io.FileReader;

public class AgentSupport extends TestBase {

    @Parameters({"dataEnv"})
    @Test
    public void navigateToAgentSupport() {
        WebDriverWait wait = new WebDriverWait(getDriver(), 30);

        //Check that Home Page is displayed
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Home']")));

        //Click the Menu Button
        getDriver().findElement(By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/design_menu_item_text")));

        //Click Agent Support
        getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Agent Support']")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Agent Support']")));

        //Confirm Agent Support View is displayed
        TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Agent Support']", "Agent Support");
    }

    @Parameters({"dataEnv"})
    @Test
    public void searchTest(String dataEnv) throws Exception {
        WebDriverWait wait = new WebDriverWait(getDriver(), 30);
        JSONParser parser = new JSONParser();
        JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resource/" + dataEnv + "/data.conf.json"));
        JSONObject envs = (JSONObject) config.get("AgentSupport");

        String issueID = (String) envs.get("issueID");
        String pending = (String) envs.get("pending");
        String resolved = (String) envs.get("resolved");
        String user = (String) envs.get("user");
        String kit = (String) envs.get("kit");
        String startDate = (String) envs.get("startDate");
        String endDate = (String) envs.get("endDate");
        String last_two_digits_of_yr = (String) envs.get("last_two_digits_of_yr");

        //Search By issue ID
        TestUtils.testTitle("Search By Issue ID (" + issueID + ")");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/issueId")));
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/issueId")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/issueId")).sendKeys(issueID);
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/searchLogBtn")).click();
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/issueLogview")));
            TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/issueIdTXT", issueID);
        } catch (Exception e) {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/contentPanel")));
            TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Record was not found']", "Record was not found");
            getDriver().findElement(By.xpath("//android.widget.Button[@text='OK']")).click();
        }


        //Search by Status (Pending)
        TestUtils.testTitle("Search By Status: (" + pending + ")");
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/issueId")).clear();
        getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Select Status']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='PENDING']")));
        getDriver().findElement(By.xpath("//android.widget.TextView[@text='PENDING']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/searchLogBtn")));
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/searchLogBtn")).click();

        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/issueLogview")));
            TestUtils.scrollDown();
            TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/statusTXT", pending);
        } catch (Exception e) {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/contentPanel")));
            TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Record was not found']", "Record was not found");
            getDriver().findElement(By.xpath("//android.widget.Button[@text='OK']")).click();
        }

        //Search by Status (Resolved)
        TestUtils.testTitle("Search By Status (" + resolved + ")");
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/issueId")).clear();
        getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='PENDING']")).clear();
        getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='PENDING']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='RESOLVED']")));
        getDriver().findElement(By.xpath("//android.widget.TextView[@text='RESOLVED']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/searchLogBtn")));
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/searchLogBtn")).click();
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/issueLogview")));
            TestUtils.scrollDown();
            TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/statusTXT", resolved);
        } catch (Exception e) {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/contentPanel")));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Record was not found']")));
            TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Record was not found']", "Record was not found");
            getDriver().findElement(By.xpath("//android.widget.Button[@text='OK']")).click();
        }

        //Reset Search by Status
        getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='RESOLVED']")).click();
        getDriver().findElement(By.xpath("//android.widget.TextView[@text='Select Status']")).click();

        //Search By Issue Type User
        TestUtils.testTitle("Search By Issue Type (" + user + ")");
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/issueId")).clear();
        getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Select Status']")).clear();
        getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Select Issue Type']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='USER']")));
        getDriver().findElement(By.xpath("//android.widget.TextView[@text='USER']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/searchLogBtn")));
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/searchLogBtn")).click();
        Thread.sleep(2000);
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/issueLogview")));
            TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/issueTypeTXT", user);
        } catch (Exception e) {
            Thread.sleep(3000);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/contentPanel")));
            Thread.sleep(1000);
            TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Record was not found']", "Record was not found");
            getDriver().findElement(By.xpath("//android.widget.Button[@text='OK']")).click();
        }

        //Search By Issue Type Kit
        TestUtils.testTitle("Search By Issue Type (" + kit + ")");
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/issueId")).clear();
        getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Select Status']")).clear();
        getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='USER']")).clear();
        getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='USER']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='KIT']")));
        getDriver().findElement(By.xpath("//android.widget.TextView[@text='KIT']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/searchLogBtn")));
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/searchLogBtn")).click();
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/issueLogview")));
            TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/issueTypeTXT", kit);
        } catch (Exception e) {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/contentPanel")));
            TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Record was not found']", "Record was not found");
            getDriver().findElement(By.xpath("//android.widget.Button[@text='OK']")).click();
        }

        //Reset issue type
        getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='KIT']")).click();
        getDriver().findElement(By.xpath("//android.widget.TextView[@text='Select Issue Type']")).click();


        //Search by Start Date
        TestUtils.testTitle("Search By Start Date (" + startDate + ")");
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/issueId")).clear();

        //Confirm Date picker is displayed
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/selectStartDate")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/date_picker_header_date")));
        getDriver().findElement(By.xpath("//android.widget.Button[@text='OK']")).click();

        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/startDate")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/startDate")).sendKeys(startDate);
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/searchLogBtn")).click();
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/issueLogview")));
            String table_Date = getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/dateLogged")).getText() + last_two_digits_of_yr;
            TestUtils.convertDate(table_Date);
        } catch (Exception e) {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/contentPanel")));
            TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Record was not found']", "Record was not found");
            getDriver().findElement(By.xpath("//android.widget.Button[@text='OK']")).click();
        }

        //Search End Date
        TestUtils.testTitle("Search By End Date (" + endDate + ")");

        //Confirm Date picker is displayed
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/selectEndDateBtn")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/date_picker_header_date")));
        getDriver().findElement(By.xpath("//android.widget.Button[@text='OK']")).click();

        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/startDate")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/selectedEndDate")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/selectedEndDate")).sendKeys(endDate);

        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/searchLogBtn")).click();
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/issueLogview")));
            String table_Date = getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/dateLogged")).getText() + last_two_digits_of_yr;
            TestUtils.convertDate(table_Date);
        } catch (Exception e) {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/contentPanel")));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Record was not found']")));
            TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Record was not found']", "Record was not found");
            getDriver().findElement(By.xpath("//android.widget.Button[@text='OK']")).click();
        }

        //Search By start date and end date
        TestUtils.testTitle("Search by Start Date ( " + startDate + ") and End Date (" + endDate + ")");
        Thread.sleep(5000);

        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/startDate")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/startDate")).sendKeys(endDate);

        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/searchLogBtn")).click();

        Thread.sleep(4000);
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/issueLogview")));
            String table_Date = getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/dateLogged")).getText() + last_two_digits_of_yr;
            String newDate = TestUtils.convertDate(table_Date);
            TestUtils.checkDateBoundary(startDate, endDate, newDate);
        } catch (Exception e) {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/contentPanel")));
            Thread.sleep(1000);
            TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Record was not found']", "Record was not found");
            getDriver().findElement(By.xpath("//android.widget.Button[@text='OK']")).click();
        }
        //Reset The Start Date and End Date
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/startDate")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/selectedEndDate")).clear();

        //Search By Issue Id and Status Pending
        TestUtils.testTitle("Search By Issue ID (" + issueID + "and Status (" + pending + ")");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/issueId")));
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/issueId")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/issueId")).sendKeys(issueID);
        getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='Select Status']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='PENDING']")));
        Thread.sleep(500);
        getDriver().findElement(By.xpath("//android.widget.TextView[@text='PENDING']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/searchLogBtn")));
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/searchLogBtn")).click();
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/issueLogview")));
            TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/issueIdTXT", issueID);
            TestUtils.scrollDown();
            TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/statusTXT", pending);
        } catch (Exception e) {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/contentPanel")));
            TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Record was not found']", "Record was not found");
            getDriver().findElement(By.xpath("//android.widget.Button[@text='OK']")).click();
        }

        //Reset Issue ID and Search by Status
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/issueId")).clear();
        getDriver().findElement(By.xpath("//android.widget.CheckedTextView[@text='PENDING']")).click();
        getDriver().findElement(By.xpath("//android.widget.TextView[@text='Select Status']")).click();

    }

    @Parameters({"dataEnv"})
    @Test
    public void assertSearchDetailsTest() {
        WebDriverWait wait = new WebDriverWait(getDriver(), 30);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/searchLogBtn")));
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/searchLogBtn")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/issueLogview")));
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/viewIssue")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/closeDetailsBtn")));
        Asserts.AssertSearchDetails();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/closeDetailsBtn")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/logIssuebtn")));


    }


    @Parameters({"dataEnv"})
    @Test
    public void logIssueByUser(String dataEnv) throws Exception {
        WebDriverWait wait = new WebDriverWait(getDriver(), 60);
        JSONParser parser = new JSONParser();
        JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resource/" + dataEnv + "/data.conf.json"));
        JSONObject envs = (JSONObject) config.get("AgentSupport");

        String user = (String) envs.get("user");
        String kit = (String) envs.get("kit");
        String password = (String) envs.get("password");
        String unlock = (String) envs.get("unlock");
        String activate = (String) envs.get("activate");
        String deactivate = (String) envs.get("deactivate");
        String details = (String) envs.get("details");
        String invalid = (String) envs.get("invalid");
        String peripherals = (String) envs.get("peripherals");
        String others = (String) envs.get("others");
        String description = (String) envs.get("description");

        //Log issue Type User
        TestUtils.testTitle("Log Issue Type (" + user + ")");
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/logIssuebtn")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Create Support Request']")));
        TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Create Support Request']", "Create Support Request");

        //Proceed without Selecting issue type
        TestUtils.testTitle("Log issue without selecting Issue Type");
        //Click the Log Issue Button
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/BtnlogIssue")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/alertTitle", "Empty Field");
        TestUtils.assertSearchText("ID", "android:id/message", "Please Select an Issue Type");
        getDriver().findElement(By.id("android:id/button1")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Select Issue Type']")));

        //Select Issue Type (User)
        TestUtils.testTitle("Select Issue Type (" + user + ")");
        getDriver().findElement(By.xpath("//android.widget.TextView[@text='Select Issue Type']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='" + user + "']")));
        TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='" + user + "']", user);
        TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='" + kit + "']", kit);
        getDriver().findElement(By.xpath("//android.widget.TextView[@text='" + user + "']")).click();
        Thread.sleep(500);

        //Proceed without Selecting issue Summary
        TestUtils.testTitle("Log issue without selecting Issue Summary");
        //Click the Log Issue Button
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/BtnlogIssue")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/alertTitle", "Empty Field");
        TestUtils.assertSearchText("ID", "android:id/message", "Please Select an Issue Summary");
        getDriver().findElement(By.id("android:id/button1")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Select Issue Summary']")));

        //Select Issue Summary
        TestUtils.testTitle("Select Issue Summary (" + password + ")");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Select Issue Summary']")));
        getDriver().findElement(By.xpath("//android.widget.TextView[@text='Select Issue Summary']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='" + password + "']")));
        TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='" + password + "']", password);
        TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='" + unlock + "']", unlock);
        TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='" + activate + "']", activate);
        TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='" + deactivate + "']", deactivate);
        TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='" + details + "']", details);
        TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='" + invalid + "']", invalid);
        TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='" + peripherals + "']", peripherals);
        TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='" + others + "']", others);
        getDriver().findElement(By.xpath("//android.widget.TextView[@text='" + password + "']")).click();

        //Proceed without Entering Description
        TestUtils.testTitle("Log issue without Entering Description");
        //Click the Log Issue Button
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/BtnlogIssue")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/alertTitle", "Empty Field");
        TestUtils.assertSearchText("ID", "android:id/message", "Please enter a description");
        getDriver().findElement(By.id("android:id/button1")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/issueDescription")));

        //Enter the Description
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/issueDescription")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/issueDescription")).sendKeys(description);

        //Click the Log Issue Button
        Thread.sleep(3000);
        TestUtils.testTitle("Log Issue");
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/BtnlogIssue")).click();

        //Assert Toast Message
        Thread.sleep(3000);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/hierarchy/android.widget.Toast")));
        TestUtils.assertSearchText("XPATH", "/hierarchy/android.widget.Toast", "Logged Issue Successful");

        // Confirm that You're redirected to the Home Page
        TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Home']", "Home");


    }


    @Parameters({"dataEnv"})
    @Test
    public void logIssueByKit(String dataEnv) throws Exception {
        WebDriverWait wait = new WebDriverWait(getDriver(), 60);
        JSONParser parser = new JSONParser();
        JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resource/" + dataEnv + "/data.conf.json"));
        JSONObject envs = (JSONObject) config.get("AgentSupport");

        String user = (String) envs.get("user");
        String kit = (String) envs.get("kit");
        String whitelist = (String) envs.get("whitelist");
        String blacklist = (String) envs.get("blacklist");
        String network = (String) envs.get("network");
        String login = (String) envs.get("login");
        String portrait = (String) envs.get("portrait");
        String fingerprint = (String) envs.get("fingerprint");
        String java = (String) envs.get("java");
        String device = (String) envs.get("device");
        String synchronizing = (String) envs.get("synchronizing");
        String failed = (String) envs.get("failed");
        String lines = (String) envs.get("lines");
        String otp = (String) envs.get("otp");
        String description = (String) envs.get("description");

        //Navigate to agent support
        navigateToAgentSupport();

        //Log issue Type User
        TestUtils.testTitle("Log Issue Type (" + kit + ")");
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/logIssuebtn")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Create Support Request']")));
        TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Create Support Request']", "Create Support Request");

        //Proceed without Selecting issue type
        TestUtils.testTitle("Log issue without selecting Issue Type");
        //Click the Log Issue Button
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/BtnlogIssue")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/alertTitle", "Empty Field");
        TestUtils.assertSearchText("ID", "android:id/message", "Please Select an Issue Type");
        getDriver().findElement(By.id("android:id/button1")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Select Issue Type']")));

        //Select Issue Type (Kit)
        TestUtils.testTitle("Select Issue Type (" + kit + ")");
        getDriver().findElement(By.xpath("//android.widget.TextView[@text='Select Issue Type']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='" + kit + "']")));
        TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='" + user + "']", user);
        TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='" + kit + "']", kit);
        getDriver().findElement(By.xpath("//android.widget.TextView[@text='" + kit + "']")).click();
        Thread.sleep(500);

        //Proceed without Selecting issue Summary
        TestUtils.testTitle("Log issue without selecting Issue Summary");
        //Click the Log Issue Button
        Thread.sleep(2000);
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/BtnlogIssue")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/alertTitle", "Empty Field");
        TestUtils.assertSearchText("ID", "android:id/message", "Please Select an Issue Summary");
        getDriver().findElement(By.id("android:id/button1")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Select Issue Summary']")));

        //Select Issue Summary
        TestUtils.testTitle("Select Issue Summary (" + whitelist + ")");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Select Issue Summary']")));
        getDriver().findElement(By.xpath("//android.widget.TextView[@text='Select Issue Summary']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='" + whitelist + "']")));
        TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='" + whitelist + "']", whitelist);
        TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='" + blacklist + "']", blacklist);
        TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='" + network + "']", network);
        TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='" + login + "']", login);
//        TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='" + portrait + "']", portrait);
//        TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='" + fingerprint + "']", fingerprint);
        TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='" + java + "']", java);
        TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='" + device + "']", device);
        TestUtils.scrollDown();
        TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='" + synchronizing + "']", synchronizing);
        TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='" + failed + "']", failed);
        TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='" + lines + "']", lines);
//        TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='" + otp + "']", otp);

        getDriver().findElement(By.id("android:id/search_src_text")).sendKeys(whitelist);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='" + whitelist + "']")));
        getDriver().findElement(By.xpath("//android.widget.TextView[@text='" + whitelist + "']")).click();

        //Proceed without Entering Description
        TestUtils.testTitle("Log issue without Entering Description");
        //Click the Log Issue Button
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/BtnlogIssue")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/alertTitle")));
        TestUtils.assertSearchText("ID", "com.sf.biocapture.activity" + Id + ":id/alertTitle", "Empty Field");
        TestUtils.assertSearchText("ID", "android:id/message", "Please enter a description");
        getDriver().findElement(By.id("android:id/button1")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sf.biocapture.activity" + Id + ":id/issueDescription")));

        //Enter the Description
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/issueDescription")).clear();
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/issueDescription")).sendKeys(description);

        TestUtils.testTitle("Log Issue");
        //Click the Log Issue Button
        getDriver().findElement(By.id("com.sf.biocapture.activity" + Id + ":id/BtnlogIssue")).click();

        //Assert Toast Message
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/hierarchy/android.widget.Toast")));
        TestUtils.assertSearchText("XPATH", "/hierarchy/android.widget.Toast", "Logged Issue Successful");

        // Confirm that You're redirected to the Home Page
        TestUtils.assertSearchText("XPATH", "//android.widget.TextView[@text='Home']", "Home");
    }

}