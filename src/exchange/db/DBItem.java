/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exchange.db;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import utils.FileUtils;

/**
 *
 * @author myy
 */
public class DBItem {
        public static int addItem(Connection conn, String name,String desc,String img_path,int cat_id) throws SQLException {
        // statement用来执行SQL语句
        Statement statement = conn.createStatement();
        // 要执行的SQL语句
        String sql = "INSERT INTO item (`name`,`desc`,img,cat_id) VALUES ('"+name+"','"+desc+"','"+img_path+"',"+cat_id+")";
        statement.executeUpdate(sql,Statement.RETURN_GENERATED_KEYS);
        //返回操作的记录的自增长id值
        ResultSet rs = statement.getGeneratedKeys();
        int num = -1;
        if (rs.next()) {
            num = rs.getInt(1);
        }
        return num;
    }
        
    public static ResultSet getItemById(Connection conn,int id)throws SQLException
    {
        // statement用来执行SQL语句
        Statement statement = conn.createStatement();
        // 要执行的SQL语句
        String sql = "SELECT * FROM item WHERE id="+id;
        ResultSet rs = statement.executeQuery(sql);
        return rs;
    }
    
    /**
     * 更新Item同时也更新服务器中的图片
     * @param conn
     * @param name
     * @param desc
     * @param img_path
     * @param item_id
     * @return 0为执行错误
     * @throws SQLException 
     */
    public static int updateItem(Connection conn, String name,String desc,String img_path,int item_id,int cat_id) throws SQLException, IOException {
        // statement用来执行SQL语句
        Statement statement = conn.createStatement();
        // 要执行的SQL语句
        ResultSet rs = getItemById(conn, cat_id);
        String img="";
        while(rs.next())
        {
            img = rs.getString("img");
        }
        //图片也更新了
        if(img.equals(img_path)==false)
        {
            //删除原图片
            FileUtils.clearFile(img);
        }
        String sql = "UPDATE item SET NAME='"+name+"',`desc`='"+desc+"',img='"+img_path+"',cat_id="+cat_id+" WHERE id="+item_id;
        int num=statement.executeUpdate(sql);
        return num;
    }
    
    /**
     * 删除item记录时所需要做的处理
     * @param item_id
     * @return 
     */
    public static void delectItem(Connection conn,int item_id) throws SQLException, IOException
    {
        Statement statement = conn.createStatement();
        // 要执行的SQL语句
        String sql = "SELECT	img FROM form,item\n" +
"WHERE form.`item_id`=item.`id`\n" +
"AND item.`id`="+item_id;
        ResultSet rs =statement.executeQuery(sql);
        String img_path=null;
        while(rs.next())
        {
            img_path=rs.getString("img");
        }
        //删除文件
        FileUtils.clearFile(img_path);
        //删除记录
        sql="DELETE FROM item WHERE id="+item_id+";";
        statement.executeUpdate(sql);
        statement.close();
        rs.close();
        
    }
}
