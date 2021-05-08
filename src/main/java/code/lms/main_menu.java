/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package code.lms;

import com.mysql.cj.protocol.Resultset;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;

/**
 *
 * @author sajeewa
 */
public class main_menu extends javax.swing.JFrame {

    /**
     * Creates new form main_menu
     */
    Connection con;
    PreparedStatement prt;
    Resultset rs = null;
    ZoneId z = ZoneId.of("Asia/Colombo");
    LocalDate today = LocalDate.now(z);
    String current_date = today.toString();
    SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");

    public main_menu() {
        con = DBconnect.connect();
        initComponents();
        return_date_check();
    }

    public void return_date_check() {
        int y = 0;

        try {
            prt = con.prepareStatement("SELECT  book_id, date FROM getbook WHERE 1");
            ResultSet result = prt.executeQuery();
            while (result.next()) {
                y = y + 1;
            }

        } catch (SQLException ex) {

            JOptionPane.showMessageDialog(null, ex);
        }

        String bookid = null, date = null, person_id = null;
        String[] arrydata = new String[y];
        String[] bookdata = new String[y];
        String[] persondata = new String[y];
        int x = 0;

        try {
            prt = con.prepareStatement("SELECT  book_id, date, person_id FROM getbook WHERE 1");
            ResultSet result = prt.executeQuery();
            while (result.next()) {
                bookid = result.getString(1);
                date = result.getString(2);
                person_id = result.getString(3);
                bookdata[x] = bookid;
                arrydata[x] = date;
                persondata[x] = person_id;
                x = x + 1;
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }

        for (int i = 0; i < arrydata.length; i++) {

            String get_date = arrydata[i];
            String get_person = persondata[i];
            String book_id = bookdata[i];

            try {
                Date date1 = myFormat.parse(get_date);
                Date date2 = myFormat.parse(current_date);

                long dif = date2.getTime() - date1.getTime();
                int daybetweendate = (int) (dif / (1000 * 60 * 60 * 24));

                if (daybetweendate > 7) {
                    System.out.println("return_date_check() book id " + book_id);
                    send_email_checker(book_id);

                }

            } catch (ParseException e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }

    }

    public void send_email(String b_id, String email) {
        String book_name = null;
        System.out.println("email    " + email);
        System.out.println("book id send email method " + b_id);
        try {
            prt = con.prepareStatement("SELECT book_name FROM bookstore WHERE book_id LIKE '" + b_id + "'");
            ResultSet result = prt.executeQuery();

            while (result.next()) {
                book_name = result.getString(1);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }

        System.out.println("start run ");
        final String username = "your@email";
        final String password = "******";
        String email2 = email;

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(email2));
            message.setSubject("Book withdrawal notice");
            message.setText("Please bring back the " + book_name + " book and submit it. Available dates are over.");

            Transport.send(message);
            System.out.println("send email addres " + email);
            insert_email_send_data(b_id, email);
            System.out.println("Done");

        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }

    public void send_email_checker(String b_id) {
        System.out.println("send_email_checker()  book Id" + b_id);
        int runcheck = 0;

        String date = null, email = null, person_i = null;

        try {
            prt = con.prepareStatement("SELECT date,email FROM email WHERE book_id LIKE '" + b_id + "'");
            ResultSet result = prt.executeQuery();
            while (result.next()) {
                runcheck = 1;
                date = result.getString(1);
                email = result.getString(2);

                try {
                    Date date1 = myFormat.parse(date);
                    Date date2 = myFormat.parse(current_date);

                    long dif = date2.getTime() - date1.getTime();
                    int daybetweendate = (int) (dif / (1000 * 60 * 60 * 24));
                    if (daybetweendate > 3) {
                        send_email(b_id, email);
                    }

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e);
                }
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }

        if (runcheck == 0) {
            try {
                prt = con.prepareStatement("SELECT person_id FROM getbook WHERE book_id LIKE '" + b_id + "'");
                ResultSet result = prt.executeQuery();

                while (result.next()) {
                    String person_idd = result.getString(1);
                    System.out.println("check person Id " + person_idd);
                    try {
                        prt = con.prepareStatement("SELECT email FROM person WHERE id LIKE '" + person_idd + "'");
                        ResultSet resul = prt.executeQuery();

                        while (resul.next()) {
                            email = resul.getString(1);
                            System.out.println("email checker " + email);
                            send_email(b_id, email);
                        }

                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(null, e);
                    }
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }

    public void insert_email_send_data(String b_id, String email) {

        email_send_table_delete(b_id);

        System.out.println("come data insert " + b_id + "  and  " + email);
        try {
            String sql = "INSERT INTO email(email,book_id) VALUES('" + email + "','" + b_id + "')";
            prt = con.prepareStatement(sql);
            prt.execute();
            System.out.println("insert succesful ");
        } catch (SQLException ex) {
            Logger.getLogger(user_acces.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void email_send_table_delete(String b_id) {
        try {
            String sql = "DELETE FROM email WHERE book_id='" + b_id + "'";
            prt = con.prepareStatement(sql);
            prt.execute();
            System.out.println("delete succesfuly");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
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
        jPanel2 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        btn_update = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        btn_add_book = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        btn_find_book = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        btn_logout = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        btn_register = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        btn_hand_over = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel6.setIcon(new javax.swing.ImageIcon("E:\\java_test\\LMS\\src\\main\\java\\imsges\\update1.jpg")); // NOI18N
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, 140, 100));

        btn_update.setBackground(new java.awt.Color(0, 0, 255));
        btn_update.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btn_update.setForeground(new java.awt.Color(255, 255, 255));
        btn_update.setText("SETTING");
        btn_update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_updateActionPerformed(evt);
            }
        });
        jPanel2.add(btn_update, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, 160, 40));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 240, 200, 170));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setIcon(new javax.swing.ImageIcon("E:\\java_test\\LMS\\src\\main\\java\\imsges\\addbook.png")); // NOI18N
        jPanel3.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, 120, 100));

        btn_add_book.setBackground(new java.awt.Color(0, 0, 255));
        btn_add_book.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btn_add_book.setForeground(new java.awt.Color(255, 255, 255));
        btn_add_book.setText("ADD BOOK");
        btn_add_book.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_add_bookActionPerformed(evt);
            }
        });
        jPanel3.add(btn_add_book, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, 160, 40));

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 50, 200, 170));

        jPanel4.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                jPanel4AncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setIcon(new javax.swing.ImageIcon("E:\\java_test\\LMS\\src\\main\\java\\imsges\\search1.png")); // NOI18N
        jPanel4.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, 120, 110));

        btn_find_book.setBackground(new java.awt.Color(0, 0, 255));
        btn_find_book.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btn_find_book.setForeground(new java.awt.Color(255, 255, 255));
        btn_find_book.setText("FIND BOOK");
        btn_find_book.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_find_bookActionPerformed(evt);
            }
        });
        jPanel4.add(btn_find_book, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, 160, 40));

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 50, 200, 170));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setForeground(new java.awt.Color(255, 255, 255));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel7.setIcon(new javax.swing.ImageIcon("E:\\java_test\\LMS\\src\\main\\java\\imsges\\logout1.png")); // NOI18N
        jPanel5.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, 110, 100));

        btn_logout.setBackground(new java.awt.Color(0, 0, 255));
        btn_logout.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btn_logout.setForeground(new java.awt.Color(255, 255, 255));
        btn_logout.setText("LOG OUT");
        btn_logout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_logoutActionPerformed(evt);
            }
        });
        jPanel5.add(btn_logout, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, 160, 40));

        jPanel1.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 240, 200, 170));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setIcon(new javax.swing.ImageIcon("E:\\java_test\\LMS\\src\\main\\java\\imsges\\person.png")); // NOI18N
        jPanel6.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 0, 100, 110));

        btn_register.setBackground(new java.awt.Color(0, 0, 255));
        btn_register.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btn_register.setForeground(new java.awt.Color(255, 255, 255));
        btn_register.setText("REGISTER");
        btn_register.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_registerActionPerformed(evt);
            }
        });
        jPanel6.add(btn_register, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, 160, 40));

        jPanel1.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 240, 200, 170));

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setForeground(new java.awt.Color(255, 255, 255));
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setIcon(new javax.swing.ImageIcon("E:\\java_test\\LMS\\src\\main\\java\\imsges\\book_delivery.png")); // NOI18N
        jPanel7.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 0, 150, 110));

        btn_hand_over.setBackground(new java.awt.Color(0, 0, 255));
        btn_hand_over.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btn_hand_over.setForeground(new java.awt.Color(255, 255, 255));
        btn_hand_over.setText("HAND OVER");
        btn_hand_over.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_hand_overActionPerformed(evt);
            }
        });
        jPanel7.add(btn_hand_over, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, 160, 40));

        jPanel1.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 50, 200, 170));

        jLabel1.setIcon(new javax.swing.ImageIcon("E:\\java_test\\LMS\\src\\main\\java\\imsges\\background5.jpg")); // NOI18N
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 900, 450));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        setSize(new java.awt.Dimension(914, 487));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jPanel4AncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_jPanel4AncestorAdded

    }//GEN-LAST:event_jPanel4AncestorAdded

    private void btn_registerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_registerActionPerformed
        new signup().setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btn_registerActionPerformed

    private void btn_add_bookActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_add_bookActionPerformed
        new addbook().setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btn_add_bookActionPerformed

    private void btn_logoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_logoutActionPerformed
        new Login().setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btn_logoutActionPerformed

    private void btn_find_bookActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_find_bookActionPerformed
        new find_book().setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btn_find_bookActionPerformed

    private void btn_updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_updateActionPerformed
        new setting().setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btn_updateActionPerformed

    private void btn_hand_overActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_hand_overActionPerformed
        new hand_over().setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btn_hand_overActionPerformed

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
            java.util.logging.Logger.getLogger(main_menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(main_menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(main_menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(main_menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new main_menu().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_add_book;
    private javax.swing.JButton btn_find_book;
    private javax.swing.JButton btn_hand_over;
    private javax.swing.JButton btn_logout;
    private javax.swing.JButton btn_register;
    private javax.swing.JButton btn_update;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    // End of variables declaration//GEN-END:variables
}
