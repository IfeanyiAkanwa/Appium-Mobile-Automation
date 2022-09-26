package db;

import java.io.File;
import java.io.FileReader;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

import io.github.cdimascio.dotenv.Dotenv;
import org.apache.commons.codec.binary.Base64;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import utils.TestBase;
import utils.TestUtils;

import static utils.TestBase.testInfo;

public class ConnectDB extends TestBase{
	
	public static Map<String, String> dbValues = new HashMap<>();

    static Dotenv dotenv = Dotenv.load();

    private static final String DB_DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String DB_CONNECTION = dotenv.get("DB_CONNECTION");
    private static final String DB_USER = dotenv.get("DB_USER");
    private static final String DB_PASSWORD = dotenv.get("DB_PASSWORD");
    private static String otp;
    private static String transactionID;

    /*public static void main(String[] args) throws Exception {

    }*/
    public static String getOTP(String phoneNumber) throws SQLException {

        Connection dbConnection = null;
        Statement statement = null;

        String getOTPSql = "SELECT OTP FROM OTP_STATUS_RECORD WHERE MSISDN = '" + phoneNumber + "' AND OTP_USED = 0 AND DELETED = 0 AND EXPIRATION_TIME >= SYSDATE ORDER BY CREATE_DATE DESC";

        try {
            dbConnection = getDBConnection();
            if (dbConnection != null) {
                System.out.println("Connected to db");
            } else {
                System.out .println("Not able to connect to db");
            }
            statement = dbConnection.createStatement();
            ResultSet rs = statement.executeQuery(getOTPSql);
            if (rs.next()) {
                otp = rs.getString("otp");
            }
            return otp;

        } catch (SQLException e) {

            System.out.println(e.getMessage());
            return null;

        } finally {

            if (statement != null) {
                statement.close();
            }

            if (dbConnection != null) {
                dbConnection.close();
            }

        }

    }

    public static String getOTPWithoutPhoneNumber() throws SQLException {

        Connection dbConnection = null;
        Statement statement = null;

        String getOTPSql = "SELECT OTP FROM OTP_STATUS_RECORD ORDER BY CREATE_DATE DESC LIMIT 1";

        try {
            dbConnection = getDBConnection();
            if (dbConnection != null) {
                System.out.println("Connected to db");
            } else {
                System.out .println("Not able to connect to db");
            }
            statement = dbConnection.createStatement();
            ResultSet rs = statement.executeQuery(getOTPSql);
            if (rs.next()) {
                otp = rs.getString("OTP");
            }
            return otp;

        } catch (SQLException e) {

            System.out.println(e.getMessage());
            return null;

        } finally {

            if (statement != null) {
                statement.close();
            }

            if (dbConnection != null) {
                dbConnection.close();
            }

        }

    }

    public static String getSwapID(String msisdn) throws SQLException {

        Connection dbConnection = null;
        Statement statement = null;

        String getSwapIDSql = "select * from sim_swap_demographic ssd where msisdn = '" + msisdn + "' order by create_date desc";
        String swapID="";
        try {
            dbConnection = getDBConnection();
            if (dbConnection != null) {
                System.out.println("Connected to db");
            } else {
                System.out .println("Not able to connect to db");
            }
            statement = dbConnection.createStatement();
            ResultSet rs = statement.executeQuery(getSwapIDSql);
            if (rs.next()) {
                 swapID = rs.getString("pk");
            }
            return swapID;

        } catch (SQLException e) {

            System.out.println(e.getMessage());
            return null;

        } finally {

            if (statement != null) {
                statement.close();
            }

            if (dbConnection != null) {
                dbConnection.close();
            }

        }

    }
    
    public static String getTransactionID() throws SQLException {

        Connection dbConnection = null;
        Statement statement = null;

        String getTranIDSql = "SELECT TRANSACTION_ID FROM SUBSCRIBER_DETAIL WHERE SUBSCRIBER_TYPE = 'NEW' AND BIOMETRICS_UPDATED = 0 AND TXN_ID_EXP_DATE_TIME > SYSDATE ORDER BY CREATE_DATE DESC";

        try {
            dbConnection = getDBConnection();
            if (dbConnection != null) {
                System.out.println("Connected to db");
            } else {
                System.out .println("Not able to connect to db");
            }
            statement = dbConnection.createStatement();
            ResultSet rs = statement.executeQuery(getTranIDSql);
            if (rs.next()) {
            	transactionID = rs.getString("TRANSACTION_ID");
            }
            return transactionID;

        } catch (SQLException e) {

            System.out.println(e.getMessage());
            return null;

        } finally {

            if (statement != null) {
                statement.close();
            }

            if (dbConnection != null) {
                dbConnection.close();
            }

        }

    }

    private static Connection getDBConnection() {

        Connection dbConnection = null;

        try {

            Class.forName(DB_DRIVER);

        } catch (ClassNotFoundException e) {

            System.out.println(e.getMessage());

        }

        try {

            dbConnection = DriverManager.getConnection(
                    DB_CONNECTION, DB_USER, DB_PASSWORD);
            return dbConnection;

        } catch (SQLException e) {

            System.out.println(e.getMessage());

        }

        return dbConnection;

    }

    public static String selectQueryOnTable (String table, String column, String value, String returnColumn) throws SQLException {

        Connection dbConnection = null;
        Statement statement = null;

        String getOTPSql = "select * from "+table+" where "+column+" = '" + value + "' ORDER BY CREATE_DATE DESC";
        String returnColumnValue=null;
        try {

            dbConnection = getDBConnection();
            if (dbConnection != null) {
                System.out.println("Connected to db");
            } else {
                System.out.println("Not able to connect to db");
            }
            statement = dbConnection.createStatement();
            ResultSet rs = statement.executeQuery(getOTPSql);
            if (rs.next()) {
                returnColumnValue = rs.getString(returnColumn);
            }
            return returnColumnValue;

        } catch (SQLException e) {

            System.out.println(e.getMessage());
            return null;

        } finally {

            if (statement != null) {
                statement.close();
            }

            if (dbConnection != null) {
                dbConnection.close();
            }

        }

    }

    public static JSONArray QueryBulkTable (String msisdn) throws SQLException {

        Connection dbConnection = null;
        Statement statement = null;

        String getOTPSql = "select *\n" +
                "from bfp_sync_log bsl  \n" +
                "left join user_Id ui on ui.unique_id = bsl.unique_id \n" +
                "left join basic_data bd on bd.user_id_fk = ui.id \n" +
                "left join dynamic_data dy on dy.basic_data_fk = bd.id \n" +
                "left join special_data sd  on sd.basic_data_fk = bd.id \n" +
                "left join signature si on si.basic_data_fk = bd.id \n" +
                "left join passport_detail pd on pd.signature_fk = si.id \n" +
                "left join MSISDN_PROVISION_STATUS mps on (mps.msisdn = bsl.msisdn and mps.SIM_SERIAL = bsl.SIM_SERIAL) \n" +
                "where mps.msisdn = '" + msisdn + "' ";
        String returnColumnValue=null;
        try {

            dbConnection = getDBConnection();
            if (dbConnection != null) {
                System.out.println("Connected to db");
            } else {
                System.out.println("Not able to connect to db");
            }
            statement = dbConnection.createStatement();
            ResultSet rs = statement.executeQuery(getOTPSql);

            JSONArray rs1= TestUtils.convertResultSetToJSON(rs);

            return rs1;

        } catch (SQLException e) {

            System.out.println(e.getMessage());
            return null;

        } finally {

            if (statement != null) {
                statement.close();
            }

            if (dbConnection != null) {
                dbConnection.close();
            }

        }

    }

    public static JSONArray QueryNinTable () throws SQLException {

        Connection dbConnection = null;
        Statement statement = null;

        String getOTPSql = "select * from nimc_verification_log nvl order by last_modified DESC;";
        String returnColumnValue=null;
        try {

            dbConnection = getDBConnection();
            if (dbConnection != null) {
                System.out.println("Connected to db");
            } else {
                System.out.println("Not able to connect to db");
            }
            statement = dbConnection.createStatement();
            ResultSet rs = statement.executeQuery(getOTPSql);

            JSONArray rs1= TestUtils.convertResultSetToJSON(rs);

            return rs1;

        } catch (SQLException e) {

            System.out.println(e.getMessage());
            return null;

        } finally {

            if (statement != null) {
                statement.close();
            }

            if (dbConnection != null) {
                dbConnection.close();
            }

        }

    }

    public static JSONArray ClientActivityLogTable (String unique_id) throws SQLException {

        Connection dbConnection = null;
        Statement statement = null;

        String getOTPSql = "SELECT * FROM CLIENT_ACTIVITY_LOG cal where unique_activity_code = '" + unique_id + "' order by create_date";
        String returnColumnValue=null;
        try {

            dbConnection = getDBConnection();
            if (dbConnection != null) {
                System.out.println("Connected to db");
            } else {
                System.out.println("Not able to connect to db");
            }
            statement = dbConnection.createStatement();
            ResultSet rs = statement.executeQuery(getOTPSql);

            JSONArray rs1= TestUtils.convertResultSetToJSON(rs);

            return rs1;

        } catch (SQLException e) {

            System.out.println(e.getMessage());
            return null;

        } finally {

            if (statement != null) {
                statement.close();
            }

            if (dbConnection != null) {
                dbConnection.close();
            }

        }

    }

    public static JSONArray QueryNINLogTable() throws SQLException {
        Connection dbConnection = null;
        Statement statement = null;

        String getOTPSql = "select * from nimc_verification_log nvl order by last_modified desc";
        String returnColumnValue=null;
        try {

            dbConnection = getDBConnection();
            if (dbConnection != null) {
                System.out.println("Connected to db");
            } else {
                System.out.println("Not able to connect to db");
            }
            statement = dbConnection.createStatement();
            ResultSet rs = statement.executeQuery(getOTPSql);

            JSONArray rs1= TestUtils.convertResultSetToJSON(rs);

            return rs1;

        } catch (SQLException e) {

            System.out.println(e.getMessage());
            return null;

        } finally {

            if (statement != null) {
                statement.close();
            }

            if (dbConnection != null) {
                dbConnection.close();
            }

        }
    }


    public static void query(String unique_Id, String dataEnv, String regModule) throws Exception{
        File path = null;
        File classpathRoot = new File(System.getProperty("user.dir"));
        if(dataEnv.equalsIgnoreCase("StagingData")) {
            path = new File(classpathRoot, "src/test/resource/stagingData/data.conf.json");
        }else {
            path = new File(classpathRoot, "src/test/resource/prodData/data.conf.json");
        }
        JSONParser parser = new JSONParser();
        JSONObject config = (JSONObject) parser.parse(new FileReader(path));
        //JSONObject envs = (JSONObject) config.get("NewRegistration");
        JSONObject envs2 = (JSONObject) config.get("IndividualForeignerDetails");
        JSONObject envs_ind = (JSONObject) config.get("IndividualNigerianDetails");
        JSONObject envs3 = (JSONObject) config.get("NewRegistration");
        //JSONObject envs4 = (JSONObject) config.get("BiometricUpdate");
        JSONObject envs5 = (JSONObject) config.get("ReRegistration");
        JSONObject envs6 = (JSONObject) config.get("AdditionalRegistration");
        JSONObject envs7 = (JSONObject) config.get("CompanyDetails");


        String firstname = (String) envs2.get("firstname");
        String surname = (String) envs2.get("surname");
        String middlename = (String) envs2.get("middlename");
        String maiden_name = (String) envs2.get("maiden_name");
        String street = (String) envs2.get("street");
        String city = (String) envs2.get("city");
        String state = (String) envs2.get("state");
        String LGA = (String) envs2.get("LGA");
        String email = (String) envs2.get("email");
        String lga_of_reg = (String) envs2.get("lga_of_reg");
        String alt_phone_number = (String) envs2.get("alt_phone_number");
        String occupation = (String) envs2.get("occupation");
        String area = (String) envs2.get("area");
        String house_or_flat_no = (String) envs2.get("house_or_flat_no");
        String postalcode = (String) envs2.get("postalcode");
        String passport_ID_number = (String)  envs2.get("passport_ID_number");
        String nationality = (String) envs2.get("nationality");
        String valid_msisdn_nm = (String) envs3.get("valid_msisdn");
        String nin_nm = (String) envs3.get("nin");
        String valid_msisdn_rr = (String) envs5.get("valid_msisdn");
        String nin_rr = (String) envs5.get("nin");
        String valid_Msisdn_ar = (String) envs6.get("valid_Msisdn");
        String nin_ar = (String) envs6.get("nin");
        String company_description = (String) envs7.get("company_description");
        String company_regno = (String) envs7.get("company_regno");
        String company_house_or_flat_no = (String) envs7.get("company_house_or_flat_no");
        String company_street = (String) envs7.get("company_street");
        String company_city = (String) envs7.get("company_city");
        String company_state_address = (String) envs7.get("company_state_address");
        String company_lga_address = (String) envs7.get("company_lga_address");
        String company_postalcode = (String) envs7.get("company_postalcode");
        String syncStatus = "SUCCESS";
        String device_type = "DROID";
        String ninEnrollement = "ONLINE";
        String ninEnrollement_fr = "null";
        String ninStatus = "NIN_VERIFIED";
        String ninStatus_fr = "NIN Pending";
        String nin_fr = "null";
        String foreignerType = "SHORT STAY (DIPLOMAT)";

        Connection dbConnection = null;
        Statement statement = null;
     

        String getRegDetsSql = "select bsl.*, bd.*, dd.*, sar.*, md.*, md2.*, p.*, s.*, pd.*, nv.* " +
                "from DYNAMIC_DATA dd " +
                "left join basic_data bd on bd.id = dd.basic_data_fk " +
                "left join user_id ui on ui.id = bd.user_id_fk " +
                "LEFT JOIN META_DATA md2 ON md2.BASIC_DATA_FK  = bd.ID " +
                "LEFT JOIN BFP_SYNC_LOG bsl ON bsl.UNIQUE_ID  = ui.UNIQUE_ID " +
                "LEFT JOIN SMS_ACTIVATION_REQUEST sar ON sar.UNIQUE_ID = ui.UNIQUE_ID " +
                "LEFT JOIN MSISDN_DETAIL md ON md.BASIC_DATA_FK  = bd.ID " +
                "LEFT JOIN PASSPORT p ON p.BASIC_DATA_FK  = bd.ID " +
                "LEFT JOIN SIGNATURE s ON s.BASIC_DATA_FK = bd.ID " +
                "LEFT JOIN PASSPORT_DETAIL pd ON pd.SIGNATURE_FK = s.ID " +
                "LEFT JOIN NIMC_VERIFICATION_LOG nv ON nv.TRANSACTION_ID = dd.DDA23 " +
                "where 1=1 " +
                "and ui.UNIQUE_ID  = '" + unique_Id + "'";

        try {
            dbConnection = getDBConnection();
            if (dbConnection != null) {
                System.out.println("Connected to db");
            } else {
                System.out.println("Not able to connect to db");
            }
            statement = dbConnection.createStatement();
            ResultSet rs = statement.executeQuery(getRegDetsSql);

            int row = 1;
			while (rs.next()) {
				ResultSetMetaData rsmd = rs.getMetaData();
				int columnCount = rsmd.getColumnCount();
				String rows = "ROW: " + row;
				row++;
				Markup u = MarkupHelper.createLabel(rows, ExtentColor.GREEN);
				testInfo.get().info(u);
				basic_data_fk  = rs.getInt("basic_data_fk");
				// The column count starts from 1
				for (int i = 1; i <= columnCount; i++) {
					String name = rsmd.getColumnName(i);
					String data = "";

					switch (rsmd.getColumnType(i)) {
					case Types.BLOB:

						Blob image = rs.getBlob(i);
						if (image == null) {
							break;
						}

						String encodedBase64 = null;
						byte[] bytes = image.getBytes(1L, (int) image.length());
						encodedBase64 = new String(Base64.encodeBase64(bytes));
						data = encodedBase64;
						break;
					case Types.VARCHAR:
						data = rs.getString(i);
						break;

					default:
						data = rs.getString(i);
						break;
					}

					if (data == null) {
						testInfo.get().error("<b>" + name + "</b><br/>" + data);
					} else if (data.length() > 100) {
						testInfo.get().info("<b>" + name + "</b><br/><img src=\"data:image/jpeg;base64," + data + "\">");
					} else {
						testInfo.get().info("<b>" + name + "</b><br/>" + data);
					}
				

                }

                TestUtils.testTitle("Database Assertions: Test Data vs Database Values");
                TestUtils.assertDbValue("BFPSYNCSTATUSENUM",syncStatus,rs.getString("BFPSYNCSTATUSENUM"));
                TestUtils.assertDbValue("UNIQUE_ID",unique_Id,rs.getString("UNIQUE_ID"));
                TestUtils.assertDbValue("DEVICE_TYPE",device_type,rs.getString("DEVICE_TYPE"));


                if (regModule.equals("NMS")){
                    TestUtils.assertDbValue("MSISDN",valid_msisdn_nm,rs.getString("MSISDN"));
                    TestUtils.assertDbValue("DDA34" + "(NIN)",nin_nm,rs.getString("DDA34"));
                    TestUtils.assertDbValue("DA33" + "(NIN Enrollment Mode)",ninEnrollement,rs.getString("DA33"));
                    TestUtils.assertDbValue("DDA36" + "(NIN Status)",ninStatus,rs.getString("DDA36"));
                    TestUtils.assertDbValue("DA8" + "(State Of Origin)",state,rs.getString("DA8"));
                    TestUtils.assertDbValue("DA9" + "(LGA of Origin)",LGA,rs.getString("DA9"));
                }else if (regModule.equals("RR")){
                    TestUtils.assertDbValue("MSISDN",valid_msisdn_rr,rs.getString("MSISDN"));
                    TestUtils.assertDbValue("DDA34" + "(NIN)",nin_rr,rs.getString("DDA34"));
                    TestUtils.assertDbValue("DA33" + "(NIN Enrollment Mode)",ninEnrollement,rs.getString("DA33"));
                    TestUtils.assertDbValue("DDA36" + "(NIN Status)",ninStatus,rs.getString("DDA36"));
                    TestUtils.assertDbValue("DA8" + "(State Of Origin)",state,rs.getString("DA8"));
                    TestUtils.assertDbValue("DA9" + "(LGA of Origin)",LGA,rs.getString("DA9"));
                }else if (regModule.equals("AR")){
                    TestUtils.assertDbValue("MSISDN",valid_Msisdn_ar,rs.getString("MSISDN"));
                    TestUtils.assertDbValue("DDA34" + "(NIN)",nin_ar,rs.getString("DDA34"));
                    TestUtils.assertDbValue("DA33" + "(NIN Enrollment Mode)",ninEnrollement,rs.getString("DA33"));
                    TestUtils.assertDbValue("DDA36" + "(NIN Status)",ninStatus,rs.getString("DDA36"));
                    TestUtils.assertDbValue("DA8" + "(State Of Origin)",state,rs.getString("DA8"));
                    TestUtils.assertDbValue("DA9" + "(LGA of Origin)",LGA,rs.getString("DA9"));
                }else if (regModule.equals("FR")){
                    //TestUtils.assertDbValue("SIM_SERIAL",valid_simSerial,rs.getString("SIM_SERIAL"));
                    TestUtils.assertDbValue("DDA34" + "(NIN)",nin_fr,rs.getString("DDA34"));
                    TestUtils.assertDbValue("FIRSTNAME",firstname,rs.getString("FIRSTNAME"));
                    TestUtils.assertDbValue("SURNAME",surname,rs.getString("SURNAME"));
                    TestUtils.assertDbValue("DDA45" + "(Foreigner Type)",foreignerType,rs.getString("DDA45"));
                    TestUtils.assertDbValue("ISSUE_COUNTRY",nationality,rs.getString("ISSUE_COUNTRY"));
                    try {
                        TestUtils.assertDbValue("PASSPORT_NUMBER",passport_ID_number,rs.getString("PASSPORT_NUMBER"));
                    }catch (Exception e){
                        TestUtils.assertDbValue("DDA48" + "(Passport Number)",passport_ID_number,rs.getString("DDA48"));
                    }
                    TestUtils.assertDbValue("DA33" + "(NIN Enrollment Mode)",ninEnrollement_fr,rs.getString("DA33"));
                    TestUtils.assertDbValue("DDA36" + "(NIN Status)",ninStatus_fr,rs.getString("DDA36"));
                }else if (regModule.equals("CS")){

                }else if (regModule.equals("SS")){

                }else if (regModule.equals("CR")){

                }

                TestUtils.assertDbValue("DA2" + "(Occupation)",occupation,rs.getString("DA2"));
                TestUtils.assertDbValue("DA6" + "(Email)",email,rs.getString("DA6"));
                TestUtils.assertDbValue("DA10" + "(Alternate Phone Numbers)",alt_phone_number,rs.getString("DA10"));
                TestUtils.assertDbValue("DDA2" + "(Area Of Residence)",area,rs.getString("DDA2"));
                TestUtils.assertDbValue("DDA5" + "(State of Residence)",state,rs.getString("DDA5"));
                TestUtils.assertDbValue("DDA6" + "(LGA of Residence)",LGA,rs.getString("DDA6"));
                TestUtils.assertDbValue("DDA7" + "(Postal Code)",postalcode,rs.getString("DDA7"));
                TestUtils.assertDbValue("DDA9" + "(Registration LGA/CITY)",lga_of_reg,rs.getString("DDA9"));
                TestUtils.assertDbValue("DA11" + "(House Number)",house_or_flat_no,rs.getString("DA11"));
                TestUtils.assertDbValue("DA12" + "(Street)",street,rs.getString("DA12"));
                TestUtils.assertDbValue("DA13" + "(City)",city,rs.getString("DA13"));
                TestUtils.assertDbValue("DDA12" + "(Mother's Maiden Name)",maiden_name,rs.getString("DDA12"));
                TestUtils.assertDbValue("OTHERNAME",middlename,rs.getString("OTHERNAME"));

                rs.close();
                statement.close();
            }

        } catch (SQLException e) {

            System.out.println(e);

        } finally {

            if (statement != null) {
                statement.close();
            }

            if (dbConnection != null) {
                dbConnection.close();
            }

        }

    }
    //Database Trial
    
    public static void multipleRowQeury(String qry) throws SQLException {

        Connection dbConnection = null;
        Statement statement = null;
        try {
            dbConnection = getDBConnection();
            if (dbConnection != null) {
                System.out.println("Connected to db");
            } else {
                System.out.println("Not able to connect to db");
            }
            statement = dbConnection.createStatement();
            ResultSet rs = statement.executeQuery(qry);
            int row = 1;
            while (rs.next()) {
                ResultSetMetaData rsmd = rs.getMetaData();
                int columnCount = rsmd.getColumnCount();
                String rows = "ROW: " + row;
                row++;
                Markup u = MarkupHelper.createLabel(rows, ExtentColor.GREEN);
                testInfo.get().info(u);
                // The column count starts from 1
                for (int i = 1; i <= columnCount; i++) {
                    String name = rsmd.getColumnName(i);
                    String data = "";

                    switch (rsmd.getColumnType(i)) {
                        case Types.BLOB:

                            Blob image = rs.getBlob(i);
                            if (image == null) {
                                break;
                            }

                            String encodedBase64 = null;
                            byte[] bytes = image.getBytes(1L, (int) image.length());
                            encodedBase64 = new String(Base64.encodeBase64(bytes));
                            data = encodedBase64;
                            break;
                        case Types.VARCHAR:
                            data = rs.getString(i);
                            break;

                        default:
                            data = rs.getString(i);
                            break;
                    }

                    if (data == null) {
                        testInfo.get().error("<b>" + name + "</b><br/>" + data);
                    } else if (data.length() > 100) {
                        testInfo.get().info("<b>" + name + "</b><br/><img src=\"data:image/jpeg;base64," + data + "\">");
                    } else {
                        testInfo.get().info("<b>" + name + "</b><br/>" + data);
                    }
                }
            }
            rs.close();
            statement.close();
        } catch (

                SQLException e) {

            System.out.println(e.getMessage());

        } finally {

            if (statement != null) {
                statement.close();
            }

            if (dbConnection != null) {
                dbConnection.close();
            }

        }
    }

    public static void specialData() throws SQLException {
        String userIDQuery = "Query special data table for basic_data_fk: " + basic_data_fk;
        Markup m = MarkupHelper.createLabel(userIDQuery, ExtentColor.BLUE);
        testInfo.get().info(m);
        String qry = "SELECT * FROM SPECIAL_DATA where BASIC_DATA_FK = " + basic_data_fk;
        multipleRowQeury(qry);
    }
}
