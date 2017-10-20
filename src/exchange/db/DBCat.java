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
import javax.swing.JComboBox;

/**
 *
 * @author myy
 */
public class DBCat {
    public static void showCatInConditionCB(Connection conn,JComboBox cat) throws SQLException
    {
        // statement����ִ��SQL���
        Statement statement = conn.createStatement();
        // Ҫִ�е�SQL���
        String sql = "select * from cat";
        ResultSet rs = statement.executeQuery(sql);
        //��������
        cat.removeAllItems();
        while (rs.next()) {
            String cat_name = rs.getString("name");
            cat.addItem(cat_name);
        }
        cat.addItem("��");
        cat.setSelectedItem("��");
        rs.close();
    }
    
    public static void showCatInCB(Connection conn,JComboBox cat) throws SQLException
    {
        // statement����ִ��SQL���
        Statement statement = conn.createStatement();
        // Ҫִ�е�SQL���
        String sql = "select * from cat";
        ResultSet rs = statement.executeQuery(sql);
        //��������
        cat.removeAllItems();
        while (rs.next()) {
            String cat_name = rs.getString("name");
            cat.addItem(cat_name);
        }
        rs.close();
    }
    
    /**
     * 
     * @param conn
     * @param cat_name
     * @return cat�� nullΪ��
     * @throws SQLException 
     */
    public static int getIdByCatName(Connection conn,String cat_name) throws SQLException
    {
        // statement����ִ��SQL���
        Statement statement = conn.createStatement();
        // Ҫִ�е�SQL���
        String sql = "SELECT cat_id FROM cat WHERE name='"+cat_name+"'";
        ResultSet rs = statement.executeQuery(sql);
        //��������
        while (rs.next()) {
            int cat_id = rs.getInt("cat_id");
            return cat_id;
        }
        rs.close();
        return -1;
    }
}
