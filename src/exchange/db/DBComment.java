/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exchange.db;

import exchangesys.panel.MessagePanel;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 *
 * @author myy
 */
public class DBComment {

    private static final String ORDER_SQL = " ORDER BY comment.sub_time ASC";

    public static int addComment(Connection conn, String comment, int form_id, int user_id, String sub_time,int msg_id) throws SQLException {
        // statement用来执行SQL语句
        Statement statement = conn.createStatement();
        // 要执行的SQL语句
        String sql = "INSERT INTO `comment` (`comment`,user_id,sub_time,msg_id) \n"
                + "VALUES ('"+comment+"',"+user_id+",'"+sub_time+"',"+msg_id+");";
        statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
        //返回操作的记录的自增长id值
        ResultSet rs = statement.getGeneratedKeys();
        int num = -1;
        if (rs.next()) {
            num = rs.getInt(1);
        }
        return num;
    }
    
     public static ResultSet selectCommentByFormIdAndMsgId(Connection conn, int form_id,int msg_id) throws SQLException {
        // statement用来执行SQL语句
        Statement statement = conn.createStatement();
        // 要执行的SQL语句
        String sql = "SELECT * FROM comment,user,message,form\n"
                + "WHERE user.id=comment.user_id and comment.msg_id=message.msg_id and message.form_id=form.form_id and "
                + "form.form_id=" + form_id +" and message.msg_id="+msg_id+ ORDER_SQL;
        ResultSet rs = statement.executeQuery(sql);
        return rs;
    }
     
      public static void showComsInPanel(Connection conn, MessagePanel pan, ResultSet rs_com) throws SQLException {
        //处理结果集
        while (rs_com.next()) {
            String com = rs_com.getString("comment");
            String user_name = rs_com.getString("name");
            Timestamp time = rs_com.getTimestamp("sub_time");
            SimpleDateFormat sdf = new SimpleDateFormat(MessagePanel.DATE_FORMAT);
            String str_time = sdf.format(time);
            //留言框内增加内容
            String content = str_time + "\n" + user_name + ": " + com + "\n"
                    +"-----------------------------------------\n";
            pan.ta_content.append(content);
        }
    }
}
