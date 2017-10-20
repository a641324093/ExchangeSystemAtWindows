/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exchange.db;

import exchangesys.frame.FormUpdateFrame;
import exchangesys.frame.ItemShowFrame;
import exchangesys.frame.ListFrame;
import exchangesys.panel.ItemPanel;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import javax.swing.ImageIcon;
import utils.ImageUtils;
import utils.StringUtils;

/**
 *
 * @author myy
 */
public class DBForm {

    public static final String ERROR_IMG_PATH = "/img/error.png";
    
    private static final String ORDER_SQL = " ORDER BY sub_date DESC ,hot DESC";

    public static int addForm(Connection conn, String title, String item_name, String form_desc, String item_desc, int user_id, int cat_id, String img_path, String sub_time) throws SQLException {
        // statement����ִ��SQL���
        Statement statement = conn.createStatement();
        int item_id = -1;
        item_id = DBItem.addItem(conn, item_name, item_desc, img_path, cat_id);
        if (item_id == -1) {
            return -1;
        }
        // Ҫִ�е�SQL���
        String sql = "INSERT INTO form (`desc`,item_id,title,user_id,sub_date) VALUES ('" + title + "'," + item_id + ",'" + form_desc + "'," + user_id + ",'" + sub_time + "')";
        statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
        //���ز����ļ�¼��������idֵ
        ResultSet rs = statement.getGeneratedKeys();
        int num = -1;
        if (rs.next()) {
            num = rs.getInt(1);
        }
        return num;
    }

    public static boolean showFormInPanel(Connection conn, ItemPanel pan, int form_id) throws SQLException {
        // statement����ִ��SQL���
        Statement statement = conn.createStatement();
        // Ҫִ�е�SQL���
        String sql = "SELECT * FROM form,USER WHERE user.`id`=form.`user_id` AND form_id=" + form_id;
        ResultSet rs = statement.executeQuery(sql);
        //��������
        while (rs.next()) {
            String title = rs.getString("title");
            int item_id = rs.getInt("item_id");
            int hot = rs.getInt("hot");
            Timestamp time = rs.getTimestamp("sub_date");
            SimpleDateFormat sdf = new SimpleDateFormat(ItemPanel.DATE_FORMAT);
            String str_time = sdf.format(time);
            String sub_man = rs.getString("name");
            //�õ���ƷͼƬ 
            ResultSet item_rs = DBItem.getItemById(conn, item_id);
            String img_path = null;
            while (item_rs.next()) {
                img_path = item_rs.getString("img");
            }
            item_rs.close();
            if (img_path == null) {
                return false;
            }
            File file_img = new File(img_path);
            ImageIcon img = null;
            if (file_img.exists() == false) {
                img_path = ERROR_IMG_PATH;
                img = new ImageIcon(ImageUtils.getImageInJar(img_path));
            } else {
                file_img = new File(img_path);
                img = new ImageIcon(img_path);
            }
            Image re_img = ImageUtils.resizeImg(img, pan.lab_img);
            //ƥ���С
            img.setImage(re_img);
            pan.lab_img.setIcon(img);
            title = StringUtils.fitString(title, 7);
            pan.lab_title.setText("����:" + title);
            pan.lab_peo.setText("����ʱ��:" + str_time);
            pan.lab_hot.setText("�ȶȣ�" + hot);
            pan.lab_sub_man.setText("�����ˣ�"+sub_man);
        }
        rs.close();
        return true;
    }

    public static boolean showFormInFrame(Connection conn, ItemShowFrame frame, int form_id) throws SQLException {
        // statement����ִ��SQL���
        Statement statement = conn.createStatement();
        // Ҫִ�е�SQL���
        String sql = "SELECT * FROM form,USER WHERE user.`id`=form.`user_id` AND form_id=" + form_id;
        ResultSet rs = statement.executeQuery(sql);
        //��������
        while (rs.next()) {
            String title = rs.getString("title");
            String form_desc = rs.getString("desc");
            int user_id = rs.getInt("user.id");
            String contact = DBContact.getMsgByUserId(conn, user_id);
            int item_id = rs.getInt("item_id");
            SimpleDateFormat format = new SimpleDateFormat(ItemShowFrame.DATE_FORMAT);
            Timestamp sub_time = rs.getTimestamp("sub_date");
            String str_time = format.format(sub_time);
            ResultSet item_rs = DBItem.getItemById(conn, item_id);
            String img_path = null;
            String item_desc = null;
            String item_name=null;
            while (item_rs.next()) {
                img_path = item_rs.getString("img");
                item_desc = item_rs.getString("desc");
                item_name = item_rs.getString("name");
            }
            item_rs.close();
            if (img_path == null || item_desc == null) {
                System.out.println("showFormInFrame��ͼƬ·������Ʒ����Ϊ��!");
                return false;
            }
            frame.lab_title.setText(title);
            frame.lab_item_name.setText(item_name);
            frame.lab_time.setText(str_time);
            frame.ta_form_desc.setText(form_desc);
            frame.ta_item_desc.setText(item_desc);
            frame.lab_contract.setText(contact);
            File file_img = new File(img_path);
            ImageIcon img = null;
            if (file_img.exists() == false) {
                img_path = ERROR_IMG_PATH;
                img = new ImageIcon(ImageUtils.getImageInJar(img_path));
            } else {
                file_img = new File(img_path);
                img = new ImageIcon(img_path);
            }
            Image re_img = ImageUtils.resizeImg(img, frame.lab_img);
            //ƥ���С
            img.setImage(re_img);
            frame.lab_img.setIcon(img);
        }
        rs.close();
        return true;
    }

    public static boolean showFormInFrame(Connection conn, FormUpdateFrame frame, int form_id) throws SQLException {
        // statement����ִ��SQL���
        Statement statement = conn.createStatement();
        // Ҫִ�е�SQL���
        String sql = "SELECT * FROM form WHERE form_id=" + form_id;
        ResultSet rs = statement.executeQuery(sql);
        //��������
        while (rs.next()) {
            String title = rs.getString("title");
            String form_desc = rs.getString("desc");

            int item_id = rs.getInt("item_id");
            ResultSet item_rs = DBItem.getItemById(conn, item_id);
            String img_path = null;
            String item_desc = null;
            String item_name = null;
            while (item_rs.next()) {
                img_path = item_rs.getString("img");
                item_desc = item_rs.getString("desc");
                item_name = item_rs.getString("name");
            }
            item_rs.close();
            if (img_path == null || item_desc == null) {
                return false;
            }
            frame.tf_title.setText(title);
            frame.ta_formdesc.setText(form_desc);
            frame.ta_itemdesc.setText(item_desc);
            frame.tf_item_name.setText(item_name);
            frame.img_path = img_path;
            ImageIcon img = new ImageIcon(img_path);
            Image re_img = ImageUtils.resizeImg(img, frame.lab_img);
            //ƥ���С
            img.setImage(re_img);
            frame.lab_img.setIcon(img);
        }
        rs.close();
        return true;
    }

    public static ResultSet getAllForm(Connection conn) throws SQLException {
        // statement����ִ��SQL���
        Statement statement = conn.createStatement();
        // Ҫִ�е�SQL���
        String sql = "select * from form";
        ResultSet rs = statement.executeQuery(sql);
//        statement.close();
        return rs;
    }

    public static void delFormById(Connection conn, int form_id) throws SQLException, IOException {
        // statement����ִ��SQL���
        Statement statement = conn.createStatement();
        //��ɾ��item
        ResultSet rs_form = DBForm.getFormById(conn, form_id);
        int item_id = -1;
        while (rs_form.next()) {
            item_id = rs_form.getInt("item_id");
        }
        rs_form.close();
        DBItem.delectItem(conn, item_id);
        //��ɾ��form
        String sql = "DELETE FROM form WHERE form_id=" + form_id;
        statement.executeUpdate(sql);
        statement.close();
    }

    public static int updateForm(Connection conn, String title, String form_desc, int form_id) throws SQLException {
        // statement����ִ��SQL���
        Statement statement = conn.createStatement();
        // Ҫִ�е�SQL���
        String sql = "UPDATE form SET `desc`='" + form_desc + "',title='" + title + "' WHERE form_id=" + form_id;
        int num = statement.executeUpdate(sql);
        if (num == 0) {
            num = -1;
        }
        return num;
    }

    public static ResultSet getFormById(Connection conn, int form_id) throws SQLException {
        // statement����ִ��SQL���
        Statement statement = conn.createStatement();
        // Ҫִ�е�SQL���
        String sql = "select * from form where form_id=" + form_id;
        ResultSet rs = statement.executeQuery(sql);
        return rs;
    }

    public static ResultSet selectFormByCatAndArea(Connection conn, int cat_id, int area_id) throws SQLException {
        // statement����ִ��SQL���
        Statement statement = conn.createStatement();
        // Ҫִ�е�SQL���
        String sql = "SELECT form_id FROM form,USER,cat,item \n"
                + "WHERE form.user_id=user.`id` AND form.`item_id`=item.`id` AND  cat.`cat_id`=item.`cat_id`\n"
                + "AND item.cat_id=" + cat_id + " AND user.`area_id`=" + area_id + ORDER_SQL + ";";;
        ResultSet rs = statement.executeQuery(sql);
        return rs;
    }

    public static ResultSet selectFormByCatID(Connection conn, int cat_id) throws SQLException {
        // statement����ִ��SQL���
        Statement statement = conn.createStatement();
        // Ҫִ�е�SQL���
        String sql = "SELECT form_id FROM form,cat,item \n"
                + "WHERE form.`item_id`=item.`id` AND  cat.`cat_id`=item.`cat_id`\n"
                + "AND item.cat_id=" + cat_id + ORDER_SQL + ";";
        ResultSet rs = statement.executeQuery(sql);
        return rs;
    }

    public static ResultSet selectFormByAreaID(Connection conn, int area_id) throws SQLException {
        // statement����ִ��SQL���
        Statement statement = conn.createStatement();
        // Ҫִ�е�SQL���
        String sql = "SELECT form_id FROM form,USER,item \n"
                + "WHERE form.user_id=user.`id` AND form.`item_id`=item.`id`\n"
                + "AND user.`area_id`=" + area_id + ORDER_SQL + ";";
        ResultSet rs = statement.executeQuery(sql);
        return rs;
    }

    public static ResultSet selectFormByUserId(Connection conn, int user_id) throws SQLException {
        // statement����ִ��SQL���
        Statement statement = conn.createStatement();
        // Ҫִ�е�SQL���
        String sql = "SELECT * FROM form,USER\n"
                + "WHERE form.user_id=user.`id`\n"
                + "AND user.`id`=" + user_id + ORDER_SQL + ";";
        ResultSet rs = statement.executeQuery(sql);
        return rs;
    }

     public static ResultSet selectFormByDimSeek(Connection conn,String key) throws SQLException {
        // statement����ִ��SQL���
        Statement statement = conn.createStatement();
        // Ҫִ�е�SQL���
        String sql = "SELECT * FROM form,item\n" +
"WHERE form.`item_id`=item.`id` AND \n" +
"(item.`name` LIKE '%"+key+"%' OR item.`desc` LIKE '%"+key+"%' OR form.`title` LIKE '%"+key+"%' OR form.`desc` LIKE '%"+key+"%');";
        ResultSet rs = statement.executeQuery(sql);
        return rs;
    }
     
    public static ResultSet getFormByIDs(Connection conn,LinkedList<Integer> id_list) throws SQLException {
        // statement����ִ��SQL���
        Statement statement = conn.createStatement();
        // Ҫִ�е�SQL���
        String sql = "select * from form where";
        if(id_list.size()==0)
        {
            return null;
        }
        int i=0;
        for(Integer id:id_list)
        {
            if(i==0)
            {
                sql+=" form_id="+id;
            }
            else
            {
                sql+=" or form_id="+id;
            }
            i++;
        }
        sql+=ORDER_SQL;
        ResultSet rs = statement.executeQuery(sql);
//        statement.close();
        return rs;
    }
     
    public static int addFormHot(Connection conn, int form_id) throws SQLException {
        // statement����ִ��SQL���
        Statement statement = conn.createStatement();
        // Ҫִ�е�SQL���
        String sql = "UPDATE form SET hot=hot+1 WHERE form_id=" + form_id;
        int num = statement.executeUpdate(sql);
        if (num == 0) {
            num = -1;
        }
        return num;
    }
}
