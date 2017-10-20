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
public class DBUser {

    /**
     *
     * @param conn
     * @param name
     * @param acount
     * @param password
     * @param area_id
     * @param tel
     * @return 插入的用户的id号，-1为失败
     * @throws SQLException
     */
    public static int addUser(Connection conn, String name, String acount, String password, int area_id, String tel) throws SQLException {

        Statement statement = conn.createStatement();
        String sql = "INSERT INTO USER(name,acount,password,area_id) "
                + "VALUES ('" + name + "','" + acount + "','" + password + "','" + area_id + "')";
        //添加了之后的参数才能返回插入记录的id
        statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
        ResultSet rs = statement.getGeneratedKeys();
        int num = -1;
        if (rs.next()) {
            num = rs.getInt(1);
        }
        //后添加联系方式
        if (num != -1) {
            DBContact.addContact(conn, tel, num);
        }
        return num;
    }

    public static ResultSet getUserById(Connection conn, int id) throws SQLException {
        Statement statement = conn.createStatement();
        // 要执行的SQL语句
        String sql = "SELECT * FROM USER WHERE id=" + id;
        ResultSet rs = statement.executeQuery(sql);
        return rs;
    }

    public static boolean isOwnerOfForm(Connection conn, int user_id, int form_id) throws SQLException {
        Statement statement = conn.createStatement();
        // 要执行的SQL语句
        String sql = "SELECT * FROM USER,form WHERE  user_id=id AND form_id=" + form_id;
        ResultSet rs = statement.executeQuery(sql);
        //处理结果集
        while (rs.next()) {
            int user_id1 = rs.getInt("user_id");
            if (user_id == user_id1) {
                rs.close();
                return true;
            }
        }
        rs.close();
        return false;
    }

    public static boolean isOwnerOfMessage(Connection conn, int user_id, int msg_id) throws SQLException {
        Statement statement = conn.createStatement();
        // 要执行的SQL语句
        String sql = "SELECT * FROM message WHERE msg_id=" + msg_id;
        ResultSet rs = statement.executeQuery(sql);
        //处理结果集
        while (rs.next()) {
            int user_id1 = rs.getInt("user_id");
            if (user_id == user_id1) {
                rs.close();
                return true;
            }
        }
        rs.close();
        return false;
    }

    public static boolean isMasterOfForm(Connection conn, int user_id) throws SQLException {
        Statement statement = conn.createStatement();
        // 要执行的SQL语句
        String sql = "SELECT * FROM USER WHERE user.`user_level`=0;";
        ResultSet rs = statement.executeQuery(sql);
        //处理结果集
        while (rs.next()) {
            int user_id1 = rs.getInt("id");
            if (user_id == user_id1) {
                rs.close();
                return true;
            }
        }
        rs.close();
        return false;
    }

    /**
     * 用户是否已经在该表的留言
     *
     * @param conn
     * @param user_id
     * @param form_id
     * @return
     * @throws SQLException
     */
    public static boolean hasMsgedInForm(Connection conn, int user_id, int form_id) throws SQLException {
        Statement statement = conn.createStatement();
        // 要执行的SQL语句
        String sql = "SELECT * FROM message WHERE form_id=" + form_id + " AND user_id=" + user_id + ";";
        ResultSet rs = statement.executeQuery(sql);
        //处理结果集
        rs.last();
        int row = rs.getRow();
        rs.close();
        if (row == 0) {
            return false;
        }
        return true;
    }

    public static int updateLoc(Connection conn, int user_id, int area_id) throws SQLException {
        // statement用来执行SQL语句
        Statement statement = conn.createStatement();
        // 要执行的SQL语句
        String sql = "UPDATE `user` SET area_id=" + area_id + " WHERE id=" + user_id + ";";
        int num = statement.executeUpdate(sql);
        if (num == 0) {
            num = -1;
        }
        return num;
    }

    /**
     * 该账户是否已经注册
     *
     * @param conn
     * @param acount
     * @return
     * @throws SQLException
     */
    public static boolean hasResgiter(Connection conn, String acount) throws SQLException {
        Statement statement = conn.createStatement();
        // 要执行的SQL语句
        String sql = "SELECT * FROM USER ";
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()) {
            String user_acount = rs.getString("acount");
            if (user_acount.equals(acount)) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean nameHasResgiter(Connection conn, String str_name) throws SQLException {
        Statement statement = conn.createStatement();
        // 要执行的SQL语句
        String sql = "SELECT * FROM USER ";
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()) {
            String name = rs.getString("name");
            if (name.equals(str_name)) {
                return true;
            }
        }
        return false;
    }
}
