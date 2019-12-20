package db;


import java.sql.*;

public class ConnectDB {

    private static final String DB_DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String DB_CONNECTION = "jdbc:oracle:thin:@10.1.242.245:1521:bsm_n2";
    private static final String DB_USER = "biocapture";
    private static final String DB_PASSWORD = "s3amf1xK0l0";
    private static String otp;

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
