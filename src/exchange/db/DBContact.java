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
 * @author myy
 */
public class DBContact {

    /**
     * 
     * @param conn
     * @param tel
     * @return 返回插入记录的id，未插入为-1；
     * @throws SQLException 
     */
    public static int addContact(Connection conn, String tel,int user_id) throws SQLException {
        // statement用来执行SQL语句
        Statement statement = conn.createStatement();
        // 要执行的SQL语句
        String sql = "INSERT INTO contact (tel,user_id) VALUES ('" + tel + "',"+user_id+")";
        statement.executeUpdate(sql,Statement.RETURN_GENERATED_KEYS);
        //返回操作的记录的自增长id值
        ResultSet rs = statement.getGeneratedKeys();
        int num = -1;
        if (rs.next()) {
            num = rs.getInt(1);
        }
        return num;
    }
    
    /**
     * 得到联系信息包括用户昵称
     * @param conn
     * @param user_id
     * @return
     * @throws SQLException 
     */
    public static String getMsgByUserId(Connection conn,int user_id)throws SQLException
    {
        // statement用来执行SQL语句
        Statement statement = conn.createStatement();
        // 要执行的SQL语句
        String sql = "SELECT * FROM contact,USER WHERE  user.id=contact.`user_id` AND user.`id`="+user_id;
        ResultSet rs = statement.executeQuery(sql);
        //处理结果集
        String contact="发布人:";
        int count = 0;
        while (rs.next()) {
            String name = rs.getString("name");
            String tel = rs.getString("tel");
            contact+=name+"\n  电话:"+tel;
            count++;
        }
        if(count <=0){
            contact = "未查询到相关联系方式";
        }
        rs.close();
        return contact;
    }
    
    /**
     * 得到联系信息不包括用户昵称
     * @param conn
     * @param user_id
     * @return
     * @throws SQLException 
     */
    public static String getContactByUserId(Connection conn,int user_id)throws SQLException
    {
        // statement用来执行SQL语句
        Statement statement = conn.createStatement();
        // 要执行的SQL语句
        String sql = "SELECT * FROM contact where `user_id`="+user_id;
        ResultSet rs = statement.executeQuery(sql);
        //处理结果集
        String contact="";
        while (rs.next()) {
            String tel = rs.getString("tel");
            contact+="电话:"+tel+" ";
        }
        rs.close();
        return contact;
    }
}
