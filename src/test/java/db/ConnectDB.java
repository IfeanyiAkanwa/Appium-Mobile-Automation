package db;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectDB {

    private static final String DB_DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String DB_CONNECTION = "jdbc:oracle:thin:@10.1.216.126:1521:bsm_n2";
    private static final String DB_USER = "biocapture";
    private static final String DB_PASSWORD = "s3amf1xK0l0";

    public static boolean changeOTP(String phoneNumber, String otp) throws SQLException {

        Connection dbConnection = null;
        Statement statement = null;

        String updateOTPSQL = "UPDATE OTP_STATUS_RECORD SET OTP = '" + otp + "' WHERE MSISDN = " + phoneNumber;

        try {
            dbConnection = getDBConnection();
            statement = dbConnection.createStatement();
            statement.execute(updateOTPSQL);
            return true;

        } catch (SQLException e) {

            System.out.println(e.getMessage());
            return false;

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
