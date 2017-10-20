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

    // MySQL����ʱ���û���
    public static final String  USER="root";
    // Java����MySQL����ʱ������
    public static final String  PASSWORD="123456";
    //���������� 
    public static final String  DRIVER="com.mysql.jdbc.Driver";
    // URLָ��Ҫ���ʵ����ݿ���scutcs
    public static final String  URL="jdbc:mysql://localhost/exsystem?"
            + "user="+USER+"&password="+PASSWORD+"&useUnicode=true&characterEncoding=GBK";
    
    
    
    public static Connection connectDB() throws ClassNotFoundException, SQLException {
            // ������������
            Class.forName(DRIVER);
            // �������ݿ�,�������ӷ�ʽ���ñ���Ҳ�޷���Ч������Ҫ��һ�ֲ��ܽ���������⣬�������Ĳ�ѯ�����Ч�����⣩
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
