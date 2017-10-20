/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exchange.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author myy 操作数据库中的账户表
 */
public class DBAcount {

    /**
     * 登录检测
     * @param conn
     * @param acount
     * @param password
     * @return 账户id号 为-1为未找到
     * @throws SQLException 
     */
    public static int checkAcount(Connection conn, String acount, String password) throws SQLException {
        // statement用来执行SQL语句
        Statement statement = conn.createStatement();
        // 要执行的SQL语句
        String sql = "select * from user";
        ResultSet rs = statement.executeQuery(sql);
        //处理结果集
        while (rs.next()) {
            String name = rs.getString("acount");
            if (name.equals(acount)) {
                String pw = rs.getString("password");
                if (pw.equals(password)) {
                    String id = rs.getString("id");
                    return Integer.valueOf(id);
                }
            }
        }
        return -1;
    }

}
