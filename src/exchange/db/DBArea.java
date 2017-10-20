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
import javax.swing.JCheckBox;
import javax.swing.JComboBox;

/**
 *
 * @author myy
 */
public class DBArea {

    public static void showProvInConditionCB(Connection conn, JComboBox prov) throws SQLException {
        // statement用来执行SQL语句
        Statement statement = conn.createStatement();
        // 要执行的SQL语句
        String sql = "select * from provinces";
        ResultSet rs = statement.executeQuery(sql);
        //处理结果集
        prov.removeAllItems();
        while (rs.next()) {
            String province = rs.getString("province");
            prov.addItem(province);
        }
        prov.addItem("无");
        prov.setSelectedItem("无");
        rs.close();
    }

    public static void showProvInCB(Connection conn, JComboBox prov) throws SQLException {
        // statement用来执行SQL语句
        Statement statement = conn.createStatement();
        // 要执行的SQL语句
        String sql = "select * from provinces";
        ResultSet rs = statement.executeQuery(sql);
        //处理结果集
        prov.removeAllItems();
        while (rs.next()) {
            String province = rs.getString("province");
            prov.addItem(province);
        }
        rs.close();
    }

    /**
     *
     * @param conn
     * @param name
     * @return -1出错
     * @throws SQLException
     */
    public static int getProvIdByName(Connection conn, String name) throws SQLException {
        // statement用来执行SQL语句
        Statement statement = conn.createStatement();
        // 要执行的SQL语句
        String sql = "select * from provinces where province='" + name + "'";
        ResultSet rs = statement.executeQuery(sql);
        //处理结果集
        while (rs.next()) {
            String province = rs.getString("province");
            if (province.equals(name)) {
                String id = rs.getString("provinceid");
                rs.close();
                return Integer.valueOf(id);
            }
        }
        rs.close();
        return -1;
    }

    public static void showCityInConditionCB(Connection conn, JComboBox city, int prov_id) throws SQLException {
        // statement用来执行SQL语句
        Statement statement = conn.createStatement();
        // 要执行的SQL语句
        String sql = "SELECT * FROM cities WHERE provinceid =" + prov_id;
        ResultSet rs = statement.executeQuery(sql);
        //处理结果集
        city.removeAllItems();
//         city.addItem("城市");
        while (rs.next()) {
            String city_name = rs.getString("city");
            city.addItem(city_name);
        }
        rs.last();
        if (rs.getRow() == 0) {
            city.addItem("无");
        }
        city.setSelectedIndex(0);
        rs.close();
    }

    public static void showCityInCB(Connection conn, JComboBox city, int prov_id) throws SQLException {
        // statement用来执行SQL语句
        Statement statement = conn.createStatement();
        // 要执行的SQL语句
        String sql = "SELECT * FROM cities WHERE provinceid =" + prov_id;
        ResultSet rs = statement.executeQuery(sql);
        //处理结果集
        city.removeAllItems();
//         city.addItem("城市");
        while (rs.next()) {
            String city_name = rs.getString("city");
            city.addItem(city_name);
        }
        rs.close();
    }

    /**
     *
     * @param conn
     * @param city_name
     * @return -1出错
     * @throws SQLException
     */
    public static int getCityIdByName(Connection conn, String prov_name, String city_name) throws SQLException {
        // statement用来执行SQL语句
        Statement statement = conn.createStatement();
        // 要执行的SQL语句
        String sql = "SELECT cityid FROM provinces,cities WHERE province='" + prov_name + "' AND city='" + city_name + "';";
        ResultSet rs = statement.executeQuery(sql);
        //处理结果集
        while (rs.next()) {
            String id = rs.getString("cityid");
            rs.close();
            return Integer.valueOf(id);
        }

        rs.close();
        return -1;
    }

    public static void showAreaInConditionCB(Connection conn, JComboBox area, int city_id) throws SQLException {
        // statement用来执行SQL语句
        Statement statement = conn.createStatement();
        // 要执行的SQL语句
        String sql = "SELECT * FROM areas WHERE cityid =" + city_id;
        ResultSet rs = statement.executeQuery(sql);
        //处理结果集
        area.removeAllItems();
        while (rs.next()) {
            String area_name = rs.getString("area");
            area.addItem(area_name);
        }
        rs.last();
        if (rs.getRow() == 0) {
            area.addItem("无");
        }
        area.setSelectedIndex(0);
        rs.close();
    }

    public static void showAreaInCB(Connection conn, JComboBox area, int city_id) throws SQLException {
        // statement用来执行SQL语句
        Statement statement = conn.createStatement();
        // 要执行的SQL语句
        String sql = "SELECT * FROM areas WHERE cityid =" + city_id;
        ResultSet rs = statement.executeQuery(sql);
        //处理结果集
        area.removeAllItems();
        while (rs.next()) {
            String area_name = rs.getString("area");
            area.addItem(area_name);
        }
        rs.close();
    }

    /**
     *
     * @param conn
     * @param city_name
     * @return -1出错
     * @throws SQLException
     */
    public static int getAreaIdByName(Connection conn, String prov_name, String city_name, String area_name) throws SQLException {
        // statement用来执行SQL语句
        Statement statement = conn.createStatement();
        // 要执行的SQL语句
        String sql = "SELECT areaid FROM provinces,cities,areas "
                + "WHERE areas.`cityid`=cities.`cityid` AND cities.`provinceid`= provinces.`provinceid` and "
                + "province='" + prov_name + "' AND city='" + city_name + "' AND AREA='" + area_name + "';";
        ResultSet rs = statement.executeQuery(sql);
        //处理结果集
        while (rs.next()) {
            String id = rs.getString("areaid");
            rs.close();
            return Integer.valueOf(id);
        }
        rs.close();
        return -1;
    }

    public static String[] getAreaMsgByAreaID(Connection conn, int area_id) throws SQLException {
        // statement用来执行SQL语句
        Statement statement = conn.createStatement();
        // 要执行的SQL语句
        String sql = "SELECT * FROM areas,cities,provinces\n"
                + "WHERE areas.`cityid`=cities.`cityid` AND cities.`provinceid`=provinces.`provinceid`\n"
                + "AND areas.`areaid`=" + area_id + ";";
        ResultSet rs = statement.executeQuery(sql);
        //处理结果集
        String prov_name="";
        String city_name="";
        String area_name="";
        while (rs.next()) {
            prov_name = rs.getString("province");
            city_name = rs.getString("city");
            area_name = rs.getString("area");
        }
        rs.close();
        String msg[]=new String[3];
        msg[0]=prov_name;
        msg[1]=city_name;
        msg[2]=area_name;
        return msg;
    }
}
