package org.interview.mysql.forupdate;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

/**
 * 测试for update语句需要占用锁
 * 使用for update语句将表锁起来，锁起来的时候其他的地方不能访问表数据，或者当其他事务未提交占用锁的时候，必须等待其他事务提交释放锁才可以继续往后运行。
如果for update加上where 条件，则会锁住where条件里面的数据，其他地方不会锁，for update的sql用法
select * from user for update 或者select * from user where username=’SuperMan’ for update

 * @author Administrator
 *
 */
public class LocalTranJdbcApplication2 {

    private static final Logger LOG = LoggerFactory.getLogger(LocalTranJdbcApplication2.class);

    public static void main(String[] args) throws SQLException {

        String sql = "SELECT * FROM T_USER FOR UPDATE";
        String plusAmountSQL = "UPDATE T_USER SET amount = ? WHERE username = ?";

        Connection dbConnection = getDBConnection();
        LOG.debug("Begin session2");

        PreparedStatement queryPS = dbConnection.prepareStatement(sql);
        ResultSet rs = queryPS.executeQuery();
        Long superManAmount = 0L;
        while (rs.next()) {
            String name = rs.getString(2);
            Long amount = rs.getLong(3);
            LOG.info("{} has amount:{}", name, amount);
            if (name.equals("SuperMan")) {
                superManAmount = amount;
            }
        }

        PreparedStatement updatePS = dbConnection.prepareStatement(plusAmountSQL);
        updatePS.setLong(1, superManAmount + 100);
        updatePS.setString(2, "SuperMan");
        updatePS.executeUpdate();

        LOG.debug("Done session2!");
        queryPS.close();
        updatePS.close();
        dbConnection.close();
    }

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
}
