/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package code.lms;

import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import static javax.swing.text.html.HTML.Tag.P;

/**
 *
 * @author sajeewa
 */
public class Login extends javax.swing.JFrame {

    /**
     * Creates new form Login
     */
    Connection con;
    PreparedStatement prt;

    public Login() {
        initComponents();
        con = DBconnect.connect();
        login.setVisible(true);
        foraget.setVisible(false);
        secue_q.setVisible(false);

    }

    public void login(String use, String pass) {
        String u_name = use;
        String p_word = pass;
        String u = username.getText();
        String p = password.getText();

        if (u.equals(u_name) && p.equals(p_word)) {
            new main_menu().setVisible(true);
            this.setVisible(false);
        } else {
            user_password();
        }
    }

    public void logindata() {
        Connection con = DBconnect.connect();
        PreparedStatement pr;

        try {
            pr = con.prepareStatement("SELECT * FROM password WHERE 1");
            ResultSet result = pr.executeQuery();
            while (result.next()) {
                String ldata = result.getString(1);
                String lname = result.getString(2);
                login(ldata, lname);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    public void security_questions() {
        String s1 = "diddeniya";
        String s2 = "09:40";
        String s3 = "sri lanka";

        String sq1 = this.sq1.getText();
        String sq2 = this.sq2.getText();
        String sq3 = this.sq3.getText();

        if (sq1.equals(s1) && sq2.equals(s2) && sq3.equals(s3)) {
            foraget.setVisible(true);
            secue_q.setVisible(false);
            login.setVisible(false);

        } else {
            JOptionPane.showMessageDialog(null, "Answer Incorrect");
        }
    }

    public void change_password() {
        String password1 = n_pword1.getText();
        String password2 = n_pword3.getText();

        if (password1.equals(password2)) {
            password_update();
            foraget.setVisible(false);
            login.setVisible(true);
            secue_q.setVisible(false);
        } else {
            JOptionPane.showMessageDialog(null, "Password not equals");
        }

    }

    public void password_update() {

        String nuser = this.n_user.getText();
        String password1 = n_pword1.getText();

        try {
            String sql = "UPDATE password SET user_name='" + nuser + "', password='" + password1 + "' WHERE 1";
            prt = con.prepareStatement(sql);
            prt.execute();
            JOptionPane.showMessageDialog(null, "updated");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    String f_name = null;
    public void user_password() {

        String user_data = username.getText();
        System.out.println("get text");
        String email = "0";
        String f_name = null;
        String id = null;

        try {
            prt = con.prepareStatement("SELECT email, first_name, id  FROM person WHERE email LIKE '" + user_data + "'");
            ResultSet result = prt.executeQuery();
            while (result.next()) {
                email = result.getString(1);
                f_name = result.getString(2);
                id = result.getString(3);
                
                System.out.println(email + " " + f_name + " " + id);
                password_username_check(email);
                sendid(id);
                //sendname(f_name);
                
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }

        if (email == "0") {
            JOptionPane.showMessageDialog(null, "User Name or password incorrect");
        }
    }

    public void password_username_check(String pass_email) {
        String p_word = null;
        String p_word2 = password.getText();
        try {
            prt = con.prepareStatement("SELECT password  FROM person WHERE email LIKE '" + pass_email + "'");
            ResultSet result = prt.executeQuery();
            while (result.next()) {
                p_word = result.getString(1);
                System.out.println("password" + p_word);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }

        if (p_word2.equals(p_word)) {
            new user_acces(f_name).setVisible(true);
            this.setVisible(false);
        } else {
            JOptionPane.showMessageDialog(null, "Your Email or Password Incoorect");
        }

    }

    
    
//    public String sendname(){
//        String name = f_name;
//        return name;
//        
//    }
    
    public String sendid( String id){
        return id;
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        secue_q = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        sq2 = new javax.swing.JTextField();
        sq1 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        sq3 = new javax.swing.JTextField();
        sq_login = new javax.swing.JButton();
        sq_submit1 = new javax.swing.JButton();
        foraget = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        n_pword = new javax.swing.JLabel();
        n_user = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        n_pword1 = new javax.swing.JPasswordField();
        n_pword3 = new javax.swing.JPasswordField();
        login = new javax.swing.JPanel();
        username = new javax.swing.JTextField();
        password = new javax.swing.JPasswordField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        btnlogin = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        label_Forget = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        secue_q.setBackground(new java.awt.Color(255, 255, 255));
        secue_q.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel8.setBackground(new java.awt.Color(255, 255, 255));
        jLabel8.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setText("What primary school did you attend?");
        secue_q.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, 330, -1));

        jLabel9.setBackground(new java.awt.Color(255, 255, 255));
        jLabel9.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 0));
        jLabel9.setText("What time of the day were you born? (hh:mm)");
        secue_q.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 130, -1, -1));

        sq2.setBackground(new java.awt.Color(255, 255, 255));
        sq2.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        sq2.setForeground(new java.awt.Color(0, 0, 0));
        sq2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sq2ActionPerformed(evt);
            }
        });
        secue_q.add(sq2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 160, 300, -1));

        sq1.setBackground(new java.awt.Color(255, 255, 255));
        sq1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        sq1.setForeground(new java.awt.Color(0, 0, 0));
        sq1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sq1ActionPerformed(evt);
            }
        });
        secue_q.add(sq1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, 290, -1));

        jLabel10.setBackground(new java.awt.Color(255, 255, 255));
        jLabel10.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 0, 0));
        jLabel10.setText("In what town or city did you meet your spouse or partner?");
        secue_q.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 200, -1, 30));

        sq3.setBackground(new java.awt.Color(255, 255, 255));
        sq3.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        sq3.setForeground(new java.awt.Color(0, 0, 0));
        sq3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sq3ActionPerformed(evt);
            }
        });
        secue_q.add(sq3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 240, 300, -1));

        sq_login.setBackground(new java.awt.Color(0, 0, 255));
        sq_login.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        sq_login.setForeground(new java.awt.Color(255, 255, 255));
        sq_login.setText("LOGIN");
        sq_login.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sq_loginActionPerformed(evt);
            }
        });
        secue_q.add(sq_login, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 290, 142, -1));

        sq_submit1.setBackground(new java.awt.Color(0, 0, 255));
        sq_submit1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        sq_submit1.setForeground(new java.awt.Color(255, 255, 255));
        sq_submit1.setText("SUBMIT");
        sq_submit1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sq_submit1ActionPerformed(evt);
            }
        });
        secue_q.add(sq_submit1, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 290, 142, -1));

        jPanel1.add(secue_q, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 40, 570, 380));

        foraget.setBackground(new java.awt.Color(255, 255, 255));
        foraget.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setBackground(new java.awt.Color(255, 255, 255));
        jLabel5.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("New User Name");
        foraget.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(63, 54, -1, -1));

        n_pword.setBackground(new java.awt.Color(255, 255, 255));
        n_pword.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        n_pword.setForeground(new java.awt.Color(0, 0, 0));
        n_pword.setText("New Password");
        foraget.add(n_pword, new org.netbeans.lib.awtextra.AbsoluteConstraints(63, 129, -1, -1));

        n_user.setBackground(new java.awt.Color(255, 255, 255));
        n_user.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        n_user.setForeground(new java.awt.Color(0, 0, 0));
        n_user.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                n_userActionPerformed(evt);
            }
        });
        foraget.add(n_user, new org.netbeans.lib.awtextra.AbsoluteConstraints(63, 84, 233, -1));

        jLabel7.setBackground(new java.awt.Color(255, 255, 255));
        jLabel7.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("Confirm Password");
        foraget.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(63, 204, -1, -1));

        jButton1.setBackground(new java.awt.Color(0, 0, 255));
        jButton1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("SUBMIT");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        foraget.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(154, 279, 142, -1));

        n_pword1.setBackground(new java.awt.Color(255, 255, 255));
        n_pword1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        n_pword1.setForeground(new java.awt.Color(0, 0, 0));
        foraget.add(n_pword1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 230, 240, -1));

        n_pword3.setBackground(new java.awt.Color(255, 255, 255));
        n_pword3.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        n_pword3.setForeground(new java.awt.Color(0, 0, 0));
        foraget.add(n_pword3, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 160, 240, -1));

        jPanel1.add(foraget, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 40, 380, 380));

        login.setBackground(new java.awt.Color(255, 255, 255));
        login.setForeground(new java.awt.Color(255, 255, 255));
        login.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        username.setBackground(new java.awt.Color(204, 204, 204));
        username.setForeground(new java.awt.Color(0, 0, 0));
        login.add(username, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 130, 270, 40));

        password.setBackground(new java.awt.Color(204, 204, 204));
        password.setForeground(new java.awt.Color(0, 0, 0));
        login.add(password, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 200, 270, 40));

        jLabel2.setForeground(new java.awt.Color(153, 153, 153));
        jLabel2.setText("Password");
        login.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 180, 80, -1));

        jLabel3.setForeground(new java.awt.Color(153, 153, 153));
        jLabel3.setText("User name");
        login.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 110, 80, -1));

        btnlogin.setBackground(new java.awt.Color(153, 0, 0));
        btnlogin.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnlogin.setForeground(new java.awt.Color(255, 255, 255));
        btnlogin.setText("LOGIN TO ACCOUNT");
        btnlogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnloginActionPerformed(evt);
            }
        });
        login.add(btnlogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 260, 270, -1));

        jLabel4.setIcon(new javax.swing.ImageIcon("E:\\java_test\\LMS\\src\\main\\java\\imsges\\login7.png")); // NOI18N
        login.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, 260, 100));

        label_Forget.setForeground(new java.awt.Color(0, 204, 0));
        label_Forget.setText("FORGET PASSWORD");
        label_Forget.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                label_ForgetMouseClicked(evt);
            }
        });
        login.add(label_Forget, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 290, 130, -1));

        jPanel1.add(login, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 50, 360, 360));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setIcon(new javax.swing.ImageIcon("E:\\java_test\\LMS\\src\\main\\java\\imsges\\wallpepar.jpg")); // NOI18N
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 820, 460));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 820, 460));

        setSize(new java.awt.Dimension(834, 498));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnloginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnloginActionPerformed
        logindata();
        // user_password();
    }//GEN-LAST:event_btnloginActionPerformed

    private void n_userActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_n_userActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_n_userActionPerformed

    private void label_ForgetMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_label_ForgetMouseClicked
        login.setVisible(false);
        foraget.setVisible(false);
        secue_q.setVisible(true);
    }//GEN-LAST:event_label_ForgetMouseClicked

    private void sq2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sq2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_sq2ActionPerformed

    private void sq1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sq1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_sq1ActionPerformed

    private void sq3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sq3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_sq3ActionPerformed

    private void sq_loginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sq_loginActionPerformed
        login.setVisible(true);
        foraget.setVisible(false);
        secue_q.setVisible(false);
    }//GEN-LAST:event_sq_loginActionPerformed

    private void sq_submit1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sq_submit1ActionPerformed
        security_questions();

    }//GEN-LAST:event_sq_submit1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        change_password();
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnlogin;
    private javax.swing.JPanel foraget;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel label_Forget;
    private javax.swing.JPanel login;
    private javax.swing.JLabel n_pword;
    private javax.swing.JPasswordField n_pword1;
    private javax.swing.JPasswordField n_pword3;
    private javax.swing.JTextField n_user;
    private javax.swing.JPasswordField password;
    private javax.swing.JPanel secue_q;
    private javax.swing.JTextField sq1;
    private javax.swing.JTextField sq2;
    private javax.swing.JTextField sq3;
    private javax.swing.JButton sq_login;
    private javax.swing.JButton sq_submit1;
    private javax.swing.JTextField username;
    // End of variables declaration//GEN-END:variables
}
