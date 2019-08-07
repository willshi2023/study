package org.interview.mysql.forupdate;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 在dbConnection.commit地方打断点，测试for update语句需要占用锁
 * @author Administrator
 *
 */
public class LocalTranJdbcApplication {
	private static Connection getDBConnection() throws SQLException {
        String DB_DRIVER = "com.mysql.jdbc.Driver";
        String DB_CONNECTION = "jdbc:mysql://localhost:3306/dist_tran_course";
        String DB_USER = "root";
        String DB_PASSWORD = "";
        try {
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            LOG.error(e.getMessage());
        }
        return DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
    }
	
	private static final Logger LOG = LoggerFactory.getLogger(LocalTranJdbcApplication.class);

    public static void main(String[] args) throws SQLException {


        String plusAmountSQL = "UPDATE T_USER SET amount = amount + 100 WHERE username = ?";
        String minusAmountSQL = "UPDATE T_USER SET amount = amount - 100 WHERE username = ?";

        Connection dbConnection = getDBConnection();
        LOG.debug("Begin");
        dbConnection.setAutoCommit(false);

        PreparedStatement plusAmountPS = dbConnection.prepareStatement(plusAmountSQL);
        plusAmountPS.setString(1, "SuperMan");
        plusAmountPS.executeUpdate();


        PreparedStatement minusAmountPS = dbConnection.prepareStatement(minusAmountSQL);
        minusAmountPS.setString(1, "BatMan");
        minusAmountPS.executeUpdate();

        dbConnection.commit();
        LOG.debug("Done!");

        plusAmountPS.close();
        minusAmountPS.close();
        dbConnection.close();
    }
}
