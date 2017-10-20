package exchangesys.frame;

import exchange.db.DBConnect;
import exchange.db.DBForm;
import exchange.db.DBMessage;
import exchange.db.DBUser;
import exchangesys.panel.MessagePanel;
import java.awt.Dimension;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;
import utils.StringUtils;

/**
 *
 * @author myy
 */
public class ItemShowFrame extends javax.swing.JFrame {

    public static final String DATE_FORMAT = "yyyy-MM-dd H:mm:ss";
    private int form_id;
    private int user_id;
    private boolean is_owner = false, is_master = false;

    public ItemShowFrame(int form_id, int user_id) {
        try {
            this.user_id = user_id;
            this.form_id = form_id;
            initComponents();
            this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            init();
        } catch (Exception ex) {
            Logger.getLogger(ItemShowFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void init() throws Exception {
        Connection conn = DBConnect.connectDB();
        //��ʾ����
        DBForm.showFormInFrame(conn, this, form_id);
        //��ʾ����
        showMessagePanel(conn);
        //�����ȶ�
        DBForm.addFormHot(conn, form_id);
        //�жϵ�¼�û��Ƿ�Ϊ����������
        boolean is_owner = DBUser.isOwnerOfForm(conn, user_id, form_id);
        this.is_owner = is_owner;
        boolean is_master = DBUser.isMasterOfForm(conn, user_id);
        this.is_master = is_master;
        if (is_owner == false) {
            but_del.setEnabled(false);
            but_update.setEnabled(false);
            if (DBUser.hasMsgedInForm(conn, user_id, form_id) == true) {
                ta_msg.setEnabled(false);
                but_message.setEnabled(false);
            }
        }
        if (is_master == true) {
            but_del.setEnabled(true);
        }
        if (is_owner == true || is_master == true) {
            but_message.setEnabled(false);
            ta_msg.setEnabled(false);
        }

        DBConnect.closeDB(conn);
    }

    private void showMessagePanel(Connection conn) throws SQLException {
        pan_msg.removeAll();
        LinkedList<Integer> list = DBMessage.getMsgerIdByFormId(conn, form_id);
        for (Integer msger_id : list) {
            ResultSet rs_msg = DBMessage.selectMsgByFormIdAndUserId(conn, form_id, msger_id);
            //ͬһ�˵���Ϣ���
            while (rs_msg.next()) {
                int msg_id = rs_msg.getInt("msg_id");
                MessagePanel mp = new MessagePanel(sp_msg, form_id, user_id, msg_id, is_owner, is_master);
                pan_msg.add(mp);
            }
        }
        int len = list.size();
        //�ظ�����ͷ
        int size = sp_msg.getWidth() / MessagePanel.width;
        int hight = MessagePanel.height;
        int width = MessagePanel.width;
        int h = (hight) * (len / size + 1);
        int w = width - 10;

        pan_msg.setPreferredSize(new Dimension(w, h));
        pan_msg.updateUI();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lab_img = new javax.swing.JLabel();
        lab_title = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        ta_form_desc = new javax.swing.JTextArea();
        lab_contract = new javax.swing.JLabel();
        but_del = new javax.swing.JButton();
        but_update = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        ta_item_desc = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lab_time = new javax.swing.JLabel();
        sp_msg = new javax.swing.JScrollPane();
        pan_msg = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        lab_time1 = new javax.swing.JLabel();
        lab_contract1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lab_item_name = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        ta_msg = new javax.swing.JTextArea();
        but_message = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("��Ʒ����");
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });
        addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                formWindowGainedFocus(evt);
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
            }
        });

        lab_img.setText("��ƷͼƬ");

        lab_title.setFont(new java.awt.Font("΢���ź�", 0, 24)); // NOI18N
        lab_title.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lab_title.setText("��������");

        ta_form_desc.setEditable(false);
        ta_form_desc.setColumns(20);
        ta_form_desc.setLineWrap(true);
        ta_form_desc.setRows(4);
        ta_form_desc.setText("��������\n");
        ta_form_desc.setWrapStyleWord(true);
        jScrollPane1.setViewportView(ta_form_desc);

        lab_contract.setFont(new java.awt.Font("΢���ź�", 0, 18)); // NOI18N
        lab_contract.setText("�绰");

        but_del.setText("ɾ������");
        but_del.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                but_delActionPerformed(evt);
            }
        });

        but_update.setText("�޸Ķ���");
        but_update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                but_updateActionPerformed(evt);
            }
        });

        ta_item_desc.setEditable(false);
        ta_item_desc.setColumns(10);
        ta_item_desc.setLineWrap(true);
        ta_item_desc.setText("��Ʒ����\n");
        ta_item_desc.setWrapStyleWord(true);
        ta_item_desc.setPreferredSize(new java.awt.Dimension(80, 104));
        jScrollPane3.setViewportView(ta_item_desc);

        jLabel1.setFont(new java.awt.Font("΢���ź� Light", 0, 14)); // NOI18N
        jLabel1.setText("��Ʒ������");

        jLabel2.setFont(new java.awt.Font("΢���ź� Light", 0, 14)); // NOI18N
        jLabel2.setText("������������");

        lab_time.setFont(new java.awt.Font("΢���ź� Light", 0, 14)); // NOI18N
        lab_time.setText("ʱ��");

        sp_msg.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        JScrollBar jsb = sp_msg.getVerticalScrollBar();
        jsb.setUnitIncrement(20);
        sp_msg.setViewportView(pan_msg);

        jLabel3.setFont(new java.awt.Font("΢���ź� Light", 0, 14)); // NOI18N
        jLabel3.setText("���Ծ�������");

        lab_time1.setFont(new java.awt.Font("΢���ź� Light", 0, 14)); // NOI18N
        lab_time1.setText("����ʱ��:");

        lab_contract1.setFont(new java.awt.Font("΢���ź�", 0, 18)); // NOI18N
        lab_contract1.setText("��ϵ��ʽ��");

        jLabel4.setFont(new java.awt.Font("΢���ź� Light", 0, 18)); // NOI18N
        jLabel4.setText("��Ʒ����");

        lab_item_name.setFont(new java.awt.Font("΢���ź� Light", 0, 18)); // NOI18N
        lab_item_name.setText("��Ʒ��");

        jLabel5.setFont(new java.awt.Font("΢���ź� Light", 0, 14)); // NOI18N
        jLabel5.setText("�������ԣ�");

        ta_msg.setLineWrap(true);
        ta_msg.setWrapStyleWord(true);
        jScrollPane4.setViewportView(ta_msg);

        but_message.setText("����");
        but_message.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                but_messageActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lab_item_name, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(lab_title, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lab_time1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lab_time))
                                    .addComponent(lab_img, javax.swing.GroupLayout.PREFERRED_SIZE, 323, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(but_update, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(but_del)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 150, Short.MAX_VALUE))
                                    .addComponent(sp_msg)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jLabel5)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lab_contract1, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lab_contract, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(96, 96, 96))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(but_message)
                                .addGap(188, 188, 188))
                            .addComponent(jScrollPane4))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lab_title, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(lab_item_name))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lab_time)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3)
                    .addComponent(lab_time1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lab_img, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sp_msg, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE)
                    .addComponent(jScrollPane4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lab_contract1)
                    .addComponent(lab_contract)
                    .addComponent(but_message))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(but_update)
                    .addComponent(but_del))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void but_delActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_but_delActionPerformed
        if (JOptionPane.showConfirmDialog(null,"ȷ��ɾ��?", "ɾ��ȷ��",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            try {
                Connection conn = DBConnect.connectDB();
                DBForm.delFormById(conn, form_id);
                DBConnect.closeDB(conn);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ItemShowFrame.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(ItemShowFrame.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ItemShowFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
            JOptionPane.showMessageDialog(this, "ɾ���ɹ���");
            this.dispose();
        }
    }//GEN-LAST:event_but_delActionPerformed

    private void but_updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_but_updateActionPerformed
        FormUpdateFrame fsf = new FormUpdateFrame(form_id, user_id);
        fsf.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_but_updateActionPerformed

    private void but_messageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_but_messageActionPerformed
        try {

            String msg = ta_msg.getText();
            if (StringUtils.isEmptyOrNull(msg) == true) {
                JOptionPane.showMessageDialog(this, "�������ݲ���Ϊ�գ�");
                return;
            }
            if (msg.length() > 100) {
                JOptionPane.showMessageDialog(this, "�������ݲ��ܳ���100���ַ���");
                return;
            }
            Date sub_date = new Date(System.currentTimeMillis());
            SimpleDateFormat sdf = new SimpleDateFormat(ItemShowFrame.DATE_FORMAT);
            String sub_time = sdf.format(sub_date);
            Connection conn = DBConnect.connectDB();

            if (DBUser.hasMsgedInForm(conn, user_id, form_id) == true) {
                JOptionPane.showMessageDialog(null, "���Ѿ����Թ���!\n�������������Ϣ���뵽��������׷�����ԣ�");
                return;
            }

            //��������
            DBMessage.addMessage(conn, msg, form_id, user_id, sub_time);
            //����������
            showMessagePanel(conn);
            DBConnect.closeDB(conn);
            this.ta_msg.setText("");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ItemShowFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ItemShowFrame.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_but_messageActionPerformed

    private void formWindowGainedFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowGainedFocus
        try {
            Connection conn = DBConnect.connectDB();
            showMessagePanel(conn);
            DBConnect.closeDB(conn);
        } catch (SQLException ex) {
            Logger.getLogger(ItemShowFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ItemShowFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_formWindowGainedFocus

    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
        formWindowGainedFocus(null);
    }//GEN-LAST:event_formComponentResized


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton but_del;
    private javax.swing.JButton but_message;
    public javax.swing.JButton but_update;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    public javax.swing.JLabel lab_contract;
    private javax.swing.JLabel lab_contract1;
    public javax.swing.JLabel lab_img;
    public javax.swing.JLabel lab_item_name;
    public javax.swing.JLabel lab_time;
    public javax.swing.JLabel lab_time1;
    public javax.swing.JLabel lab_title;
    private javax.swing.JPanel pan_msg;
    private javax.swing.JScrollPane sp_msg;
    public javax.swing.JTextArea ta_form_desc;
    public javax.swing.JTextArea ta_item_desc;
    public javax.swing.JTextArea ta_msg;
    // End of variables declaration//GEN-END:variables
}