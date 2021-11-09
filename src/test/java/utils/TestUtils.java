package utils;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.testinium.deviceinformation.helper.ProcessHelper;
import db.ConnectDB;
import enums.TargetTypeEnum;
import io.appium.java_client.MobileBy;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.restassured.RestAssured;
import io.restassured.config.SSLConfig;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.commons.codec.binary.Base64;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.*;
import org.testng.Assert;
import org.testng.annotations.Parameters;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;

import static io.restassured.RestAssured.given;

public class TestUtils extends TestBase {

    public static String getDeviceInfo(String deviceID) throws IOException {
        String deviceVersion = "adb -s " + deviceID + " shell getprop ro.build.version.release";
        String deviceName = "adb.exe -s " +deviceID+ " shell getprop ro.product.model";
        String deviceInfo = "<b>" + executeAdbCommand(deviceName) +"</b><br/><b>Android " + executeAdbCommand(deviceVersion)+"</b>";
        System.out.println(deviceInfo);
        return deviceInfo;
    }

    public static String addScreenshot() {

        TakesScreenshot ts = getDriver();
        File scrFile = ts.getScreenshotAs(OutputType.FILE);

        String encodedBase64 = null;
        FileInputStream fileInputStreamReader;
        try {
            fileInputStreamReader = new FileInputStream(scrFile);
            byte[] bytes = new byte[(int) scrFile.length()];
            fileInputStreamReader.read(bytes);
            encodedBase64 = new String(Base64.encodeBase64(bytes));


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "data:image/png;base64," + encodedBase64;
    }


    /**
     * @description adds screenshot to the page.
     */
    public static void logScreenshot(){
        String screenshotPath = TestUtils.addScreenshot();
        testInfo.get().addScreenCaptureFromBase64String(screenshotPath);
    }


    public static void assertTableValue(String table, String column, String search, String returnColumn, String expect) throws SQLException {

        StringBuffer verificationErrors = new StringBuffer();

        String value=ConnectDB.selectQueryOnTable(table, column, search, returnColumn);
        //System.out.println(value);
        try {
            Assert.assertEquals(value, expect);
            testInfo.get().log(Status.INFO, value + " found");
        } catch (Error e) {
            verificationErrors.append(e.toString());
            String verificationErrorString = verificationErrors.toString();
            testInfo.get().error(expect + " not found");
            testInfo.get().error(verificationErrorString);
        }
    }

    /*public static void main(String[] args) throws SQLException, IOException, org.json.simple.parser.ParseException {
        //assertBulkTables("08118071446" );

        //System.out.println(Setting);
        //JSONObject getSettingParams=TestUtils.createSettingObject("PILOT-AVAILABLE-USE-CASE", "RR","All available registration use case");
        //updateSettingsApiCall( "stagingData",  getSettingParams);

        String swapID=ConnectDB.getSwapID("09054391786");
        JSONObject rejectSwapPayLoad = new JSONObject();
        rejectSwapPayLoad.put("kmUserId", "990");
        rejectSwapPayLoad.put("swapId", swapID);
        rejectSwapPayLoad.put("processType", "FAILED_CHECK");
        rejectSwapPayLoad.put("feedback", "Reject");
        rejectSwapApiCall("stagingData", rejectSwapPayLoad);
    }
*/
    public static void assertBulkTables(String msisdn, String Country) throws SQLException, IOException, org.json.simple.parser.ParseException {


        StringBuffer verificationErrors = new StringBuffer();
        JSONArray dBvalue=ConnectDB.QueryBulkTable(msisdn);
        try{
            Object getFirstObject = dBvalue.get(0);
            System.out.println(getFirstObject);
            TestUtils.testTitle("Asserting individual registration DB values");
            JSONObject jsonLineItem = (JSONObject) getFirstObject;
            TestUtils.testTitle("Country");
            String DDA19 = (String) jsonLineItem.get("dda19");
            if (Country=="NIGERIA"){
                assertTwoValues( DDA19, "NIGERIA");
            }else{
                //Handle Foreigner Registration
                assertTwoValues( DDA19, Country);
                //To confirm that Foreigner type is stored in Dynamic data table
                TestUtils.testTitle("To confirm that Foreigner type is stored in Dynamic data table");
                String dda45 = (String) jsonLineItem.get("dda45");
                checkNullValues( dda45);
                //To ensure that start date of Visit is saved in Dynamic_data
                TestUtils.testTitle("To ensure that start date of Visit is saved in Dynamic_data");
                String dda46 = (String) jsonLineItem.get("dda46");
                checkNullValues( dda46);
                //To ensure that End date of Visit is saved in Dynamic_data
                TestUtils.testTitle("To ensure that End date of Visit is saved in Dynamic_data");
                String dda47 = (String) jsonLineItem.get("dda47");
                checkNullValues( dda47);
                //To confirm that passport number is saved in Dynamic_data
                TestUtils.testTitle("To confirm that passport number is saved in Dynamic_data");
                String dda48 = (String) jsonLineItem.get("dda48");
                checkNullValues( dda48);
                //To confirm that The visa/cerpac details shall be saved in DDA is saved in Dynamic_data
                TestUtils.testTitle("To confirm that The visa/cerpac details shall be saved in DDA is saved in Dynamic_data");
                String dda54 = (String) jsonLineItem.get("dda54");
                checkNullValues( dda54);
            }

            TestUtils.testTitle("Status");
            String status = (String) jsonLineItem.get("status");
            assertTwoValues( status, "ACTIVE");
            TestUtils.testTitle("State of origin");
            String DA8 = (String) jsonLineItem.get("dda8");
            checkNullValues( DA8);
            TestUtils.testTitle("LGA of origin");
            String DA9 = (String) jsonLineItem.get("dda9");
            checkNullValues( DA9);
            TestUtils.testTitle("To confirm that the NIN capture should be saved in Dynamic Data");
            String da34 = (String) jsonLineItem.get("dda34");
            checkNullValues( da34);
            TestUtils.testTitle("To confirm that a record saved while connected to network is flagged as ONLINE on DYNAMIC_DATA table");
            String da33 = (String) jsonLineItem.get("dda33");
            assertTwoValues( da33, "ONLINE");
            TestUtils.testTitle("bfpsync table status");
            String bfpsyncstatusenum = (String) jsonLineItem.get("bfpsyncstatusenum");
            assertTwoValues( bfpsyncstatusenum, "SUCCESS");

            //Unique ID
            String unique_id = (String) jsonLineItem.get("unique_id");
            JSONArray CAL=ConnectDB.ClientActivityLogTable(unique_id);
            testInfo.get().info(String.valueOf(CAL.get(0)));
        }catch (Exception e){
            testInfo.get().error("Could not fetch data for the Registered MSISDN:"+msisdn+", please perform test manually");
        }


        try {
            //Assert.assertEquals(expect, value);
            //testInfo.get().log(Status.INFO, value + " found");
        } catch (Error e) {
            verificationErrors.append(e.toString());
            String verificationErrorString = verificationErrors.toString();
            //testInfo.get().error(value + " not found");
            testInfo.get().error(verificationErrorString);
        }
    }

    public static void assertCNDetailsTables(String primary_tm) throws SQLException {
        String msisdn=primary_tm;
        StringBuffer verificationErrors = new StringBuffer();
        JSONArray dBvalue=ConnectDB.QueryBulkTable(msisdn);
        try{
            Object getFirstObject = dBvalue.get(0);
            System.out.println(getFirstObject);
            TestUtils.testTitle("Asserting Corporate new registration DB values");
            JSONObject jsonLineItem = (JSONObject) getFirstObject;

            //Unique ID
            String unique_id = (String) jsonLineItem.get("unique_id");
            JSONArray CAL=ConnectDB.ClientActivityLogTable(unique_id);
            testInfo.get().info(String.valueOf(CAL.get(0)));

            TestUtils.testTitle("To confirm that corporate category is saved in DDA58");
            String dd58 = (String) jsonLineItem.get("dd58");
            assertTwoValues(dd58, "Primary");
            testInfo.get().info(dd58);

            TestUtils.testTitle("To confirm that the Secondary TM whose MSISDN, Demographics and biometric were validated is saved in DDA59");
            String dd59 = (String) jsonLineItem.get("dd59");
            testInfo.get().info(dd59);

        }catch (Exception e){
            testInfo.get().error("Could not fetch data for the Registered MSISDN:"+msisdn+", please perform test manually");
        }


        try {
            //Assert.assertEquals(expect, value);
            //testInfo.get().log(Status.INFO, value + " found");
        } catch (Error e) {
            verificationErrors.append(e.toString());
            String verificationErrorString = verificationErrors.toString();
            //testInfo.get().error(value + " not found");
            testInfo.get().error(verificationErrorString);
        }
    }

    public static void assertNINVerify(String nin) throws SQLException {
        JSONArray dBvalue=ConnectDB.QueryNinTable();
        try {
            Object getFirstObject = dBvalue.get(0);
            System.out.println(getFirstObject);
            JSONObject jsonLineItem = (JSONObject) getFirstObject;

            //Transaction ID
            TestUtils.testTitle("To confirm that backend generates a unique transaction ID for each request");
            String transaction_id = (String) jsonLineItem.get("transaction_id");
            TestUtils.checkNullValues(transaction_id);

            //Unique ID
            TestUtils.testTitle("To confirm that backend generates a unique transaction ID for each request");
            String db_nin = (String) jsonLineItem.get("nin");
            TestUtils.assertTwoValues(db_nin, nin);

            TestUtils.testTitle("Logged data");
            testInfo.get().info(String.valueOf(getFirstObject));
        }catch (Exception e){
            testInfo.get().error("Cannot fetch DB:"+e);
        }
    }

    public static JSONArray convertResultSetToJSON(ResultSet rs) throws SQLException {
        JSONArray json = new JSONArray();
        ResultSetMetaData rsmd = rs.getMetaData();
        while(rs.next()) {
            int numColumns = rsmd.getColumnCount();
            JSONObject obj = new JSONObject();
            for (int i=1; i<=numColumns; i++) {
                String column_name = rsmd.getColumnName(i);
                obj.put(column_name, rs.getObject(column_name));
            }
            json.add(obj);
        }
        return json;
    }

    /**
     * @param type
     * @param element
     * @param value
     * @description to check if the expected text is present in the page.
     */
    public static void assertSearchText(String type, String element, String value) {

        StringBuffer verificationErrors = new StringBuffer();
        TargetTypeEnum targetTypeEnum = TargetTypeEnum.valueOf(type);
        String ttype = null;

        switch (targetTypeEnum) {
            case ID:
                ttype = getDriver().findElement(By.id(element)).getText();
                break;
            case NAME:
                ttype = getDriver().findElement(By.name(element)).getText();
                break;
            case CSS:
                ttype = getDriver().findElement(By.cssSelector(element)).getText();
                break;

            case XPATH:
                ttype = getDriver().findElement(By.xpath(element)).getText();
                break;
            case CLASS:
                ttype = getDriver().findElement(By.className(element)).getText();
                break;

            default:
                ttype = getDriver().findElement(By.id(element)).getText();
                break;
        }

        try {
            Assert.assertEquals(ttype, value);
            testInfo.get().log(Status.INFO, value + " found");
        } catch (Error e) {
            verificationErrors.append(e.toString());
            String verificationErrorString = verificationErrors.toString();
            testInfo.get().error(value + " not found");
            testInfo.get().error(verificationErrorString);
        }
    }

    public static void assertTwoValues(String value, String expected){
        StringBuffer verificationErrors = new StringBuffer();
        try {
            Assert.assertEquals(value, expected);
            testInfo.get().log(Status.INFO, value + " found");
        } catch (Error e) {
            verificationErrors.append(e.toString());
            String verificationErrorString = verificationErrors.toString();
            testInfo.get().error(expected + " not found");
            testInfo.get().error(verificationErrorString);
        }
    }

    public static void checkNullValues(String value){
        if (value!=null &&  !value.isEmpty()){
            testInfo.get().log(Status.INFO, value + " found");
        }else{
            StringBuffer verificationErrors = new StringBuffer();
            String verificationErrorString = verificationErrors.toString();
            testInfo.get().error(value + " value found");
            testInfo.get().error(verificationErrorString);
        }
    }

    @SuppressWarnings({"rawtypes", "deprecation"})
    public static void scroll(int fromX, int fromY, int toX, int toY) {
        TouchAction touchAction = new TouchAction(getDriver());
        touchAction.longPress(fromX, fromY).moveTo(toX, toY).release().waitAction( WaitOptions.waitOptions( Duration.ofMillis( 5000 ) ) ).perform();
    }

    public static void scrollDown() {
        int pressX = getDriver().manage().window().getSize().width / 2;
        int bottomY = getDriver().manage().window().getSize().height * 4 / 5;
        int topY = getDriver().manage().window().getSize().height / 8;
        scroll(pressX, bottomY, pressX, topY);
    }

    public static void scrollUp() {
        int pressX = getDriver().manage().window().getSize().width / 2;
        int bottomY = getDriver().manage().window().getSize().height * 4 / 5;
        int topY = getDriver().manage().window().getSize().height / 8;
        scroll(pressX, topY, pressX, bottomY);
    }

    public static void scrollByID(String Id, int index) {

        try {

            getDriver().findElement(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().resourceId(\""+Id+"\").instance("+index+"));"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * This method does a swipe downwards
     * @author Bill Hileman
     * @throws Exception
     */
    public static void scrollUp2() throws Exception {

        //The viewing size of the device
        Dimension size = getDriver().manage().window().getSize();

        //Starting y location set to 20% of the height (near bottom)
        int starty = (int) (size.height * 0.20);
        //Ending y location set to 80% of the height (near top)
        int endy = (int) (size.height * 0.80);
        //x position set to mid-screen horizontally
        int startx = size.width / 2;

        scroll(startx, starty, startx, endy);
    }

    public static void hideKeyboard() throws InterruptedException {
        if (getDriver().isKeyboardShown()) {
            getDriver().hideKeyboard();
            Thread.sleep(500);
        }
    }

    public static boolean isElementPresent(String elementType, String locator) {

        WebElement elementPresent = null;

        TargetTypeEnum targetTypeEnum = TargetTypeEnum.valueOf(elementType);
        switch (targetTypeEnum) {
            case ID:
                try {
                    elementPresent = getDriver().findElement(By.id(locator));
                } catch (Exception e) {
                }
                break;
            case NAME:
                try {
                    elementPresent = getDriver().findElement(By.name(locator));
                } catch (Exception e) {
                }
                break;
            case CSS:
                try {
                    elementPresent = getDriver().findElement(By.cssSelector(locator));
                } catch (Exception e) {
                }
                break;
            case XPATH:
                try {
                    elementPresent = getDriver().findElement(By.xpath(locator));
                } catch (Exception e) {
                }
                break;
            default:
                try {
                    elementPresent = getDriver().findElement(By.id(locator));
                } catch (Exception e) {
                }
        }
        if (elementPresent != null) {
            return true;
        } else {
            return false;
        }
    }

    public static void scrollUntilElementIsVisible(String elementType, String locator) throws InterruptedException {
        Thread.sleep(500);
        while (!TestUtils.isElementPresent(elementType, locator)) {
            scrollDown();
        }
    }

    //Possible solution to interacting with Android Internal or External memory
    public static String executeAdbCommand(String command) throws IOException {
        Process process = null;
        StringBuilder builder = new StringBuilder();
        String commandString;
        commandString = String.format("%s", command);
        //System.out.print("Command is " + commandString + "\n");
        try {
            process = ProcessHelper.runTimeExec(commandString);
        } catch (IOException e) {
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.print(line + "\n");
            builder.append(line);
        }
        return builder.toString();
    }

    public static boolean validateParams(Object... params) {

        for (Object param : params) {
            if (param == null) {
                return false;
            }

            if (param instanceof String && ((String) param).isEmpty()) {
                return false;
            }

            if (param instanceof Collection<?> && ((Collection<?>) param).isEmpty()) {
                return false;
            }

            if (param instanceof Long && ((Long) param).compareTo(0L) == 0) {
                return false;
            }
            if (param instanceof Double && ((Double) param).compareTo(0D) == 0) {
                return false;
            }

            if (param instanceof Integer && ((Integer) param).compareTo(0) == 0) {
                return false;
            }

        }

        return true;
    }

    public static Integer convertToInt(String value) throws InterruptedException {
        Integer result = null;
        String convertedString = value.replaceAll("[^0-9]", "");
        if (validateParams(convertedString)) {
            try {
                return result = Integer.parseInt(convertedString);
            } catch (NumberFormatException e) {
                testInfo.get().error("convertToInt  Error converting to integer ");
                testInfo.get().error(e);
            }
        }
        return result;

    }

    public static void testTitle(String phrase) {
        String word = "<b>" + phrase + "</b>";
        Markup w = MarkupHelper.createLabel(word, ExtentColor.BLUE);
        testInfo.get().info(w);
    }

    /**
     * @return number
     * @description to generate a 11 digit number.
     */
    public static String generatePhoneNumber() {

        long y = (long) (Math.random() * 100000 + 0113330000L);
        String Surfix = "081";
        String num = Long.toString(y);
        String number = Surfix + num;
        return number;

    }

    /**
     * @return sim serial number
     * @description to generate a 19 digit number.
     */
    public static String generateSimSrial() {

        long y = (long) (Math.random() * 1000000000 + 011333000000000000L);
        String Surfix = "8923";
        String num = Long.toString(y);
        String simSerialNumber = Surfix + num + "F";
        return simSerialNumber;

    }

    public static Calendar yyyymmddToDate(String dateString) {
        Calendar dateDate = Calendar.getInstance();

        try {
            String[] dateArray = dateString.split("-");
            int year = Integer.valueOf(dateArray[0]);
            int month = Integer.valueOf(dateArray[1]) - 1;
            int day = Integer.valueOf(dateArray[2]);

            dateDate.set(year, month, day);
        } catch (NumberFormatException e) {
            String[] dateArray = dateString.split("/");
            int year = Integer.valueOf(dateArray[0]);
            int month = Integer.valueOf(dateArray[1]) - 1;
            int day = Integer.valueOf(dateArray[2]);

            dateDate.set(year, month, day);
        }

        return dateDate;
    }

    public static Calendar mmddyyyyToDate(String dateString) {
        Calendar dateDate = Calendar.getInstance();

        try {
            String[] dateArray = dateString.split("-");
            int month = Integer.valueOf(dateArray[0]) - 1;
            int day = Integer.valueOf(dateArray[1]);
            int year = Integer.valueOf(dateArray[2]);

            dateDate.set(month, day, year);
        } catch (NumberFormatException e) {
            String[] dateArray = dateString.split("/");
            int month = Integer.valueOf(dateArray[0]) - 1;
            int day = Integer.valueOf(dateArray[1]);
            int year = Integer.valueOf(dateArray[2]);

            dateDate.set(month, day, year);
        }

        return dateDate;
    }

    public static void checkDateBoundary(String start, String end, String verify) {
        Calendar startDate = mmddyyyyToDate(start);
        Calendar endDate = mmddyyyyToDate(end);
        Calendar verifyDate = yyyymmddToDate(verify);

        if (verifyDate.before(startDate) && verifyDate.after(endDate)) {
            testInfo.get().error("Record not within date range");
        } else {
            testInfo.get().info("Record within date range");
        }
    }

    public static void checkDateyYMDBoundary(String start, String end, String verify) {
        Calendar startDate = yyyymmddToDate(start);
        Calendar endDate = yyyymmddToDate(end);
        Calendar verifyDate = yyyymmddToDate(verify);

        if (verifyDate.before(startDate) && verifyDate.after(endDate)) {
            testInfo.get().error("Record not within date range");
        } else {
            testInfo.get().info("Record within date range");
        }
    }

    public static String convertDate(String returnedDate) throws ParseException {
        SimpleDateFormat sdf;
        sdf = new SimpleDateFormat("dd/MM/yy, hh:mm a");
        SimpleDateFormat sdff = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = sdff.format(sdf.parse(returnedDate));
        testInfo.get().info("Date returned: " + formattedDate);
        return formattedDate;
    }

    @Parameters({"dataEnv"})
    public static JsonObject generateJson(String dataEnv, String filename, String testCase) throws IOException {

        String filePath = "src/test/resource/"+dataEnv+"/"+filename;

        JsonObject object = new Gson().fromJson(new String(Files.readAllBytes(Paths.get(filePath))),JsonObject.class);
        JsonObject object2 = (JsonObject) object.get(testCase);

        return object2;

    }

    public static JSONObject createSettingObject(String settingName, String settingVal, String settingDesc){
        JSONObject newSetting1 = new JSONObject();
        JSONObject Setting = new JSONObject();
        newSetting1.put("name", settingName);
        newSetting1.put("value", settingVal);
        newSetting1.put("description", settingDesc);

        ArrayList<JSONObject> arlist = new ArrayList<>( );
        arlist.add(newSetting1);

        Setting.put("settings",arlist);

        return Setting;
    }

    public static String retrieveSettingsApiCall(String dataEnv,String settings) throws IOException, org.json.simple.parser.ParseException {
        try {
            TestUtils.testTitle("*********RETRIEVING SETTINGS("+settings+")**********");
        }catch (Exception e){

        }
        System.out.println("*********RETRIEVING SETTINGS("+settings+")**********");

        RestAssured.baseURI = serviceUrl;
        JSONParser parser = new JSONParser();
        JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resource/" + dataEnv + "/settingsApi.conf.json"));
        JSONObject requestBody = (JSONObject) config.get(settings);
        String endPoint = (String) config.get("retrieve_endPoint");

        Response res =	given().
                header("User-Agent", "Smart Client for KYC [Build: 1.0, Install Date: NA]").
                header("User-UUID","03764868-6f9e-41f3-ba45-45599d8c8e08").
                header("sc-auth-key","AHVZ0xiTP498n2uUgtXA2wt95nPbIvh7e6sdFgHKB5fcoIvLf6_24KKMHA9H7zG_5EQHAZ6QM221GQo6GUf-wG2QcEo2S_dkirXCgRHOq6N_0Go36DorLZs").
                header("Client-ID","smartclient").
                header("Content-Type","application/json").

                body(requestBody).config(RestAssured.config().sslConfig(new SSLConfig().allowAllHostnames())).when().post(endPoint).then().assertThat().extract().response();

        String response = res.asString();
        JsonPath jsonRes = new JsonPath(response);
        int statusCode = res.getStatusCode();
        int result=0;
        String status="";
        if (statusCode==200){
            //Successful settings API
            status = jsonRes.getString("status");
            result=1;

        }else{
            //Failed to make settings
            try {
                testInfo.get().error("API CALL FAILED("+settings+")");
            }catch (Exception e){

            }

        }

        //System.out.println(response);
        String setttings= jsonRes.getString("settings");
        String replace = setttings.replace("[","");
        String replace1 = replace.replace("]","");

        List<String> myList = new ArrayList<String>(Arrays.asList(replace1.split(":")));
        String settingsVal = myList.get(4);
        if (settingsVal.contains(", id")){
              myList = new ArrayList<String>(Arrays.asList(replace1.split(",")));
             settingsVal = myList.get(3);
             //Remove Value String
            settingsVal = settingsVal.replace("value:", "");
        }else{
            myList = new ArrayList<String>(Arrays.asList(replace1.split(",")));
            if (myList.size()==4){
                settingsVal = myList.get(3);
                settingsVal=settingsVal.replace("value:", "");
            }
        }

        try {
            testInfo.get().info(response);
        }catch (Exception e){

        }
        System.out.println("Settings Retrieved:"+settingsVal);
        return settingsVal;
    }

    public static ArrayList<String> convertStringArrayToArraylist(String[] strArr){
        ArrayList<String> stringList = new ArrayList<String>();
        for (String s : strArr) {
            stringList.add(s);
        }
        return stringList;
    }

    public static int updateSettingsApiCall(String dataEnv, JSONObject settingData) throws IOException, org.json.simple.parser.ParseException {

        RestAssured.baseURI = serviceUrl;
        JSONParser parser = new JSONParser();
        JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resource/" + dataEnv + "/settingsApi.conf.json"));
        JSONObject requestBody = settingData;
        String endPoint = (String) config.get("update_endPoint");

        //requestBody.remove(settings);

        Response res =	given().
                header("User-Agent", "Smart Client for KYC [Build: 1.0, Install Date: NA]").
                header("User-UUID","03764868-6f9e-41f3-ba45-45599d8c8e08").
                header("sc-auth-key","AHVZ0xiTP498n2uUgtXA2wt95nPbIvh7e6sdFgHKB5fcoIvLf6_24KKMHA9H7zG_5EQHAZ6QM221GQo6GUf-wG2QcEo2S_dkirXCgRHOq6N_0Go36DorLZs").
                header("Client-ID","smartclient").
                header("Content-Type","application/json").

                body(requestBody).config(RestAssured.config().sslConfig(new SSLConfig().allowAllHostnames())).when().post(endPoint).then().assertThat().extract().response();

        String response = res.asString();
        JsonPath jsonRes = new JsonPath(response);
        int statusCode = res.getStatusCode();
        int result=0;
        if (statusCode==200){
            //Successful settings API
            String status = jsonRes.getString("status");
            result=1;

        }else{
            //Failed to make settings
            testInfo.get().error("UPDATE API FAILED"+response);
        }
        ArrayList<JSONObject> getSet= (ArrayList<JSONObject>) requestBody.get("settings");
        JSONObject getData = getSet.get(0);
        //String settings = getSet['']

        System.out.println("*********UPDATING SETTINGS("+getData.get("name")+")**********");
        try {
            testInfo.get().info(response);
        }catch (Exception e){

        }
        System.out.println(response);
        return result;
    }

    public static String blockActionApiCall(String dataEnv, JSONObject settingData) throws IOException, org.json.simple.parser.ParseException {

        RestAssured.baseURI = serviceUrl;
        JSONParser parser = new JSONParser();
        JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resource/" + dataEnv + "/settingsApi.conf.json"));
        JSONObject requestBody = settingData;
        //String endPoint = (String) config.get("unblock_endPoint");
        String endPoint = "/simrop/biocapture/swap-unblock";

        //requestBody.remove(settings);

        Response res =	given().
                header("User-Agent", "Smart Client for KYC [Build: 1.0, Install Date: NA]").
                header("User-UUID","03764868-6f9e-41f3-ba45-45599d8c8e08").
                header("sc-auth-key","AHVZ0xiTP498n2uUgtXA2wt95nPbIvh7e6sdFgHKB5fcoIvLf6_24KKMHA9H7zG_5EQHAZ6QM221GQo6GUf-wG2QcEo2S_dkirXCgRHOq6N_0Go36DorLZs").
                header("Client-ID","smartclient").
                header("Content-Type","application/json").

                body(requestBody).config(RestAssured.config().sslConfig(new SSLConfig().allowAllHostnames())).when().post(endPoint).then().assertThat().extract().response();

        String response = res.asString();
        JsonPath jsonRes = new JsonPath(response);
        int statusCode = res.getStatusCode();
        int result=0;
        if (statusCode==200){
            //Successful settings API
            String status = jsonRes.getString("status");
            result=1;

        }else{
            //Failed to make settings
            testInfo.get().error("BLOCK API FAILED"+response);
        }
        /*ArrayList<JSONObject> getSet= (ArrayList<JSONObject>) requestBody.get("settings");
        JSONObject getData = getSet.get(0);*/
        //String settings = getSet['']

        System.out.println("*********UPDATING SETTINGS("+jsonRes.getString("response")+")**********");
        try {
            testInfo.get().info(response);
        }catch (Exception e){

        }
        System.out.println(response);
        return jsonRes.getString("response");
    }

    public static String rejectSwapApiCall(String dataEnv, JSONObject settingData) throws IOException, org.json.simple.parser.ParseException {

        RestAssured.baseURI = serviceUrl;
        JSONParser parser = new JSONParser();
        JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resource/" + dataEnv + "/settingsApi.conf.json"));
        JSONObject requestBody = settingData;
        //String endPoint = (String) config.get("reject_endPoint");
        String endPoint = "/simrop/biocapture/do-checking";

        //requestBody.remove(settings);
        Response res =	given().
                header("User-Agent", "Smart Client for KYC [Build: 1.0, Install Date: NA]").
                header("User-UUID","03764868-6f9e-41f3-ba45-45599d8c8e08").
                header("sc-auth-key","AHVZ0xiTP498n2uUgtXA2wt95nPbIvh7e6sdFgHKB5fcoIvLf6_24KKMHA9H7zG_5EQHAZ6QM221GQo6GUf-wG2QcEo2S_dkirXCgRHOq6N_0Go36DorLZs").
                header("Client-ID","smartclient").
                header("Content-Type","application/json").

                body(requestBody).config(RestAssured.config().sslConfig(new SSLConfig().allowAllHostnames())).when().post(endPoint).then().assertThat().extract().response();

        String response = res.asString();
        JsonPath jsonRes = new JsonPath(response);
        int statusCode = res.getStatusCode();
        int result=0;
        if (statusCode==200){
            //Successful settings API
            String status = jsonRes.getString("status");
            result=1;

        }else{
            //Failed to make settings
            testInfo.get().error("REJECT API FAILED"+response);
        }

        //String settings = getSet['']

        System.out.println("*********REJECTING SWAP("+jsonRes.getString("response")+")**********");
        try {
            testInfo.get().info(response);
        }catch (Exception e){

        }
        System.out.println(response);
        return jsonRes.getString("response");
    }



}
