package db;

import java.sql.*;

public class ConnectDB {

    private static final String DB_DRIVER = "org.postgresql.Driver";
    private static final String DB_CONNECTION = "jdbc:postgresql://13.56.69.207:5445/biocapture";
    private static final String DB_USER = "biocapture";
    private static final String DB_PASSWORD = "3I0K@7t#Ur5Q=";
    private static String otp;
    private static String transactionID;
    
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

}
