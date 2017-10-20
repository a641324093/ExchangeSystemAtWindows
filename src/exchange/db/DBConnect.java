/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exchange.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.*;
/**
 *
 * @author myy
 */
public class DBConnect {

    // MySQL配置时的用户名
    public static final String  USER="root";
    // Java连接MySQL配置时的密码
    public static final String  PASSWORD="123456";
    //驱动程序名 
    public static final String  DRIVER="com.mysql.jdbc.Driver";
    // URL指向要访问的数据库名scutcs
    public static final String  URL="jdbc:mysql://localhost/exsystem?"
            + "user="+USER+"&password="+PASSWORD+"&useUnicode=true&characterEncoding=GBK";
    
    
    
    public static Connection connectDB() throws ClassNotFoundException, SQLException {
            // 加载驱动程序
            Class.forName(DRIVER);
            // 连续数据库,这种连接方式设置编码也无法有效，必须要下一种才能解决编码问题，（含中文查询语句无效的问题）
             Connection conn = DriverManager.getConnection(URL);
            return conn;
    }
    
    public static void closeDB(Connection conn) throws SQLException
    {
        if(conn.isClosed()==false)
        {
            conn.close();
        }
    }
}
