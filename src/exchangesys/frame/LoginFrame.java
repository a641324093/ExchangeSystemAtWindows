package exchangesys.frame;

import exchange.db.DBAcount;
import exchange.db.DBConnect;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import tansfer.TransferClient;
import tansfer.TransferServer;
import utils.StringUtils;

/**
 *
 * @author myy
 */
public class LoginFrame extends javax.swing.JFrame {

    /**
     * Creates new form LoginFrame
     */
    public LoginFrame() {
        initComponents();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        but_log = new javax.swing.JButton();
        tf_acount = new javax.swing.JTextField();
        pf_pw = new javax.swing.JPasswordField();
        but_register = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("���ﱦ��¼");
        setResizable(false);

        jLabel2.setText("�û�����");

        jLabel3.setText("���룺");

        but_log.setText("��¼");
        but_log.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                but_logActionPerformed(evt);
            }
        });
        but_log.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                but_logKeyPressed(evt);
            }
        });

        but_register.setText("ע��");
        but_register.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                but_registerActionPerformed(evt);
            }
        });

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/title_��ͼ��.jpg"))); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(118, 118, 118)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(but_log)
                        .addGap(42, 42, 42)
                        .addComponent(but_register))
                    .addComponent(pf_pw, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tf_acount, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(tf_acount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(pf_pw, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(but_log)
                    .addComponent(but_register))
                .addGap(48, 48, 48))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void but_logActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_but_logActionPerformed
        Connection conn=null;
        try {
            String acount = tf_acount.getText().trim();
            String pw = pf_pw.getText().trim();
            if(StringUtils.isEmptyOrNull(pw)||StringUtils.isEmptyOrNull(acount))
            {
                 JOptionPane.showMessageDialog(this,"�˺Ż����벻��Ϊ�գ�");
                 return;
            }
            conn = DBConnect.connectDB();
            int id =DBAcount.checkAcount(conn,acount,pw);
            if(id!=-1)
            {
            ListFrame lf = new ListFrame(id);
            lf.setVisible(true);
            this.dispose();
            }
            else 
            {
                JOptionPane.showMessageDialog(this,"�˺Ż��������");
                return;
            }
            DBConnect.closeDB(conn);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LoginFrame.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this,"���������Ӽ���ʧ�ܣ�");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,"���������ӳ�����");
            Logger.getLogger(LoginFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_but_logActionPerformed

    private void but_registerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_but_registerActionPerformed
        RegisterFrame rf = new RegisterFrame();
        rf.setVisible(true);
    }//GEN-LAST:event_but_registerActionPerformed

    private void but_logKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_but_logKeyPressed
        int key_code = evt.getKeyCode();
       if(key_code==KeyEvent.VK_ENTER)
       {
           this.but_logActionPerformed(null);
       }
    }//GEN-LAST:event_but_logKeyPressed

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(LoginFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LoginFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LoginFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LoginFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LoginFrame().setVisible(true);
            }
        });
        //your code
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton but_log;
    private javax.swing.JButton but_register;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPasswordField pf_pw;
    private javax.swing.JTextField tf_acount;
    // End of variables declaration//GEN-END:variables
}