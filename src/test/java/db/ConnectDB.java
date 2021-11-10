package db;

import java.sql.*;
import io.github.cdimascio.dotenv.Dotenv;
import org.json.simple.JSONArray;
import utils.TestUtils;

public class ConnectDB {

    static Dotenv dotenv = Dotenv.load();

    private static final String DB_DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String DB_CONNECTION = dotenv.get("DB_CONNECTION");
    private static final String DB_USER = dotenv.get("DB_USER");
    private static final String DB_PASSWORD = dotenv.get("DB_PASSWORD");
    private static String otp;
    private static String transactionID;

    /*public static void main(String[] args) throws SQLException {
        String otp = getOTPWithoutPhoneNumber();
        System.out.println(otp);
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

        String getOTPSql = "select * from "+table+" where "+column+" = '" + value + "' ";
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
}
