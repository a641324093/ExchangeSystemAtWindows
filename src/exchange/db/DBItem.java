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
        // statement����ִ��SQL���
        Statement statement = conn.createStatement();
        // Ҫִ�е�SQL���
        String sql = "INSERT INTO item (`name`,`desc`,img,cat_id) VALUES ('"+name+"','"+desc+"','"+img_path+"',"+cat_id+")";
        statement.executeUpdate(sql,Statement.RETURN_GENERATED_KEYS);
        //���ز����ļ�¼��������idֵ
        ResultSet rs = statement.getGeneratedKeys();
        int num = -1;
        if (rs.next()) {
            num = rs.getInt(1);
        }
        return num;
    }
        
    public static ResultSet getItemById(Connection conn,int id)throws SQLException
    {
        // statement����ִ��SQL���
        Statement statement = conn.createStatement();
        // Ҫִ�е�SQL���
        String sql = "SELECT * FROM item WHERE id="+id;
        ResultSet rs = statement.executeQuery(sql);
        return rs;
    }
    
    /**
     * ����ItemͬʱҲ���·������е�ͼƬ
     * @param conn
     * @param name
     * @param desc
     * @param img_path
     * @param item_id
     * @return 0Ϊִ�д���
     * @throws SQLException 
     */
    public static int updateItem(Connection conn, String name,String desc,String img_path,int item_id,int cat_id) throws SQLException, IOException {
        // statement����ִ��SQL���
        Statement statement = conn.createStatement();
        // Ҫִ�е�SQL���
        ResultSet rs = getItemById(conn, cat_id);
        String img="";
        while(rs.next())
        {
            img = rs.getString("img");
        }
        //ͼƬҲ������
        if(img.equals(img_path)==false)
        {
            //ɾ��ԭͼƬ
            FileUtils.clearFile(img);
        }
        String sql = "UPDATE item SET NAME='"+name+"',`desc`='"+desc+"',img='"+img_path+"',cat_id="+cat_id+" WHERE id="+item_id;
        int num=statement.executeUpdate(sql);
        return num;
    }
    
    /**
     * ɾ��item��¼ʱ����Ҫ���Ĵ���
     * @param item_id
     * @return 
     */
    public static void delectItem(Connection conn,int item_id) throws SQLException, IOException
    {
        Statement statement = conn.createStatement();
        // Ҫִ�е�SQL���
        String sql = "SELECT	img FROM form,item\n" +
"WHERE form.`item_id`=item.`id`\n" +
"AND item.`id`="+item_id;
        ResultSet rs =statement.executeQuery(sql);
        String img_path=null;
        while(rs.next())
        {
            img_path=rs.getString("img");
        }
        //ɾ���ļ�
        FileUtils.clearFile(img_path);
        //ɾ����¼
        sql="DELETE FROM item WHERE id="+item_id+";";
        statement.executeUpdate(sql);
        statement.close();
        rs.close();
        
    }
}
