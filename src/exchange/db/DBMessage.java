/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exchange.db;

import exchangesys.panel.MessagePanel;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.LinkedList;

/**
 *
 * @author myy
 */
public class DBMessage {

    private static final String ORDER_SQL = " ORDER BY sub_time ASC";

    public static int addMessage(Connection conn, String msg, int form_id, int user_id, String sub_time) throws SQLException {
        // statement用来执行SQL语句
        Statement statement = conn.createStatement();
        // 要执行的SQL语句
        String sql = "INSERT INTO message (`msg`,form_id,user_id,sub_time) "
                + "VALUES ('" + msg + "'," + form_id + "," + user_id + ",'" + sub_time + "');";
        statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
        //返回操作的记录的自增长id值
        ResultSet rs = statement.getGeneratedKeys();
        int num = -1;
        if (rs.next()) {
            num = rs.getInt(1);
        }
        return num;
    }

    public static ResultSet selectMsgByFormIdAndUserId(Connection conn, int form_id, int user_id) throws SQLException {
        // statement用来执行SQL语句
        Statement statement = conn.createStatement();
        // 要执行的SQL语句
        String sql = "SELECT * FROM message,user\n"
                + "WHERE user.id=message.user_id and form_id=" + form_id + " and user_id=" + user_id + ORDER_SQL;
        ResultSet rs = statement.executeQuery(sql);
        return rs;
    }

    public static LinkedList getMsgerIdByFormId(Connection conn, int form_id) throws SQLException {
        // statement用来执行SQL语句
        Statement statement = conn.createStatement();
        // 要执行的SQL语句
        String sql = "SELECT * FROM message\n"
                + "WHERE form_id=" + form_id;
        ResultSet rs = statement.executeQuery(sql);
        LinkedList<Integer> list = new LinkedList<Integer>();
        while (rs.next()) {
            Integer user_id = rs.getInt("user_id");
            if (list.contains(user_id) == false) {
                list.add(user_id);
            }
        }
        return list;
    }

    public static boolean showMsgInPanel(Connection conn, MessagePanel pan, int msg_id) throws SQLException {
        // statement用来执行SQL语句
        Statement statement = conn.createStatement();
        // 要执行的SQL语句
        String sql = "SELECT * FROM message,`user`\n"
                + "WHERE message.`user_id`=`user`.`id` \n"
                + "AND msg_id=" + msg_id;
        ResultSet rs = statement.executeQuery(sql);
        //处理结果集
        while (rs.next()) {
            String msg = rs.getString("msg");
            String user_name = rs.getString("name");
            int user_id = rs.getInt("id");
            Timestamp time = rs.getTimestamp("sub_time");
            SimpleDateFormat sdf = new SimpleDateFormat(MessagePanel.DATE_FORMAT);
            String str_time = sdf.format(time);
            String contact = DBContact.getContactByUserId(conn, user_id);
            //设置控件值
            pan.lan_peo.setText(user_name);
            pan.lan_contact.setText(contact);
            String content = str_time + "\n" + user_name + ": " + msg + "\n"
                    +"-----------------------------------------\n";
            pan.ta_content.append(content);
        }
        statement.close();
        rs.close();
        return true;
    }

    public static void showMsgsInPanel(Connection conn, MessagePanel pan, ResultSet rs_msg) throws SQLException {
        //处理结果集
        while (rs_msg.next()) {
            String msg = rs_msg.getString("msg");
            String user_name = rs_msg.getString("name");
            int user_id = rs_msg.getInt("id");
            Timestamp time = rs_msg.getTimestamp("sub_time");
            SimpleDateFormat sdf = new SimpleDateFormat(MessagePanel.DATE_FORMAT);
            String str_time = sdf.format(time);
            String contact = DBContact.getContactByUserId(conn, user_id);
            //设置控件值
            pan.lan_peo.setText(user_name);
            pan.lan_contact.setText(contact);
            String content = pan.ta_content.getText();
            content += str_time + "\n" + user_name + ":" + msg + "\n";
            pan.ta_content.setText(content);
        }
    }

    public static void delMsgById(Connection conn, int msg_id) throws SQLException, IOException {
        // statement用来执行SQL语句
        Statement statement = conn.createStatement();
        String sql = "DELETE FROM message WHERE msg_id=" + msg_id;
        statement.executeUpdate(sql);
        statement.close();
    }

    /**
     * 通过结果集删除留言
     * @param conn
     * @param rs_msg
     * @throws SQLException
     * @throws IOException 
     */
    public static void delMsgByResult(Connection conn, ResultSet rs_msg) throws SQLException, IOException {
        // statement用来执行SQL语句
        rs_msg.beforeFirst();
        Statement statement = conn.createStatement();
        while (rs_msg.next()) 
        {
            int msg_id =rs_msg.getInt("msg_id");
            String sql = "DELETE FROM message WHERE msg_id=" + msg_id;
            statement.executeUpdate(sql);
        }
//        rs_msg.close();
        statement.close();
    }
    
}
