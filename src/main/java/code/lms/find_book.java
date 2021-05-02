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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author sajeewa
 */
public class find_book extends javax.swing.JFrame {

    /**
     * Creates new form find_book
     */
    Connection con = DBconnect.connect();
    PreparedStatement prt;
    Resultset rs = null;

    public find_book() {
        initComponents();
        con = DBconnect.connect();
        book_table_load();
        person_table_load();
    }

    public void book_table_load() {
        try {
            String sql = "SELECT `book_id`, `book_name`, `book_author` FROM `bookstore`";
            prt = con.prepareStatement(sql);
            rs = (Resultset) prt.executeQuery();
            booktable.setModel(DbUtils.resultSetToTableModel((ResultSet) rs));
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void person_table_load() {
        try {
            String sql = "SELECT id, first_name, last_name FROM person WHERE 1";
            prt = con.prepareStatement(sql);
            rs = (Resultset) prt.executeQuery();
            persontable.setModel(DbUtils.resultSetToTableModel((ResultSet) rs));
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void p_table_select() {
        int pdata = persontable.getSelectedRow();

        String id = persontable.getValueAt(pdata, 0).toString();;
        String f_names = persontable.getValueAt(pdata, 1).toString();

        p_name.setText(f_names);
        p_id.setText(id);
    }

    public void book_select() {
        int bdata = booktable.getSelectedRow();

        String bid = booktable.getValueAt(bdata, 0).toString();
        String b_names = booktable.getValueAt(bdata, 1).toString();
        String b_authors = booktable.getValueAt(bdata, 2).toString();

        b_id.setText(bid);
        b_author2.setText(b_authors);
        b_name1.setText(b_names);
    }

    public void book_serch() {
        String searchdata = b_serch.getText();

        try {
            String sql = "SELECT * FROM bookstore WHERE book_name LIKE '%" + searchdata + "%' or book_id LIKE '%" + searchdata + "%' or book_author LIKE '%" + searchdata + "%' ";
            prt = con.prepareStatement(sql);
            rs = (Resultset) prt.executeQuery();
            booktable.setModel(DbUtils.resultSetToTableModel((ResultSet) rs));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void person_serch() {
        String searchdata = p_search.getText();
        try {
            String sql = "SELECT id, first_name, last_name FROM person WHERE first_name LIKE '%" + searchdata + "%' or id LIKE '%" + searchdata + "%' or last_name LIKE '%" + searchdata + "%' or email LIKE '%" + searchdata + "%'  ";
            prt = con.prepareStatement(sql);
            rs = (Resultset) prt.executeQuery();
            persontable.setModel(DbUtils.resultSetToTableModel((ResultSet) rs));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void getbook() {
        String book_id, person_id;
        book_id = b_id.getText();
        person_id = p_id.getText();

        try {
            String sql = "INSERT INTO `getbook`( `book_id`, `person_id`) VALUES ('" + book_id + "','" + person_id + "')";
            prt = con.prepareStatement(sql);
            prt.execute();
            JOptionPane.showMessageDialog(null, "Get Book succesfully");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void get_table_data() {
        int bdata = booktable.getSelectedRow();
        String bid = booktable.getValueAt(bdata, 0).toString();
        String data2="0";
        try {
            prt = con.prepareStatement("SELECT * FROM getbook WHERE book_id LIKE '%" + bid + "%' ");
            ResultSet result = prt.executeQuery();
            while (result.next()) {
                data2 = result.getString(2);
            }
            check_book(data2);
        } catch (SQLException ex) {
            Logger.getLogger(check.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void check_book(String data1) {
        int bdata = booktable.getSelectedRow();
        String bid = booktable.getValueAt(bdata, 0).toString();

        System.out.println("data " + bid);
        System.out.println("data " + data1);

        if (bid.equals(data1)) {
            JOptionPane.showMessageDialog(null, "book allredy get ");
        } else {
            getbook();
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
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        persontable = new javax.swing.JTable();
        b_id = new javax.swing.JLabel();
        b_name1 = new javax.swing.JLabel();
        p_id = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        booktable = new javax.swing.JTable();
        b_author2 = new javax.swing.JLabel();
        p_name = new javax.swing.JLabel();
        p_search = new javax.swing.JTextField();
        b_serchbtn = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        b_serch = new javax.swing.JTextField();
        p_serchbtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(11, 116, 191));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(11, 116, 191));
        jPanel2.setForeground(new java.awt.Color(40, 84, 108));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setIcon(new javax.swing.ImageIcon("E:\\java_test\\LMS\\src\\main\\java\\imsges\\book line1.jpg")); // NOI18N
        jLabel1.setText("jLabel1");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 4, -1, 146));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 410, 1010, 150));

        jPanel3.setBackground(new java.awt.Color(150, 197, 230));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton1.setBackground(new java.awt.Color(170, 57, 57));
        jButton1.setFont(new java.awt.Font("Dialog", 3, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 170, 170));
        jButton1.setText("GET BOOK");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 210, -1, -1));

        persontable.setBackground(new java.awt.Color(255, 255, 255));
        persontable.setForeground(new java.awt.Color(0, 0, 0));
        persontable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Title 1", "Title 2"
            }
        ));
        persontable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                persontableMouseClicked(evt);
            }
        });
        persontable.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                persontableKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                persontableKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(persontable);

        jPanel3.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 10, 270, 170));

        b_id.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        b_id.setForeground(new java.awt.Color(0, 0, 0));
        b_id.setText("id");
        jPanel3.add(b_id, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 200, 90, 40));

        b_name1.setBackground(new java.awt.Color(0, 0, 0));
        b_name1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        b_name1.setForeground(new java.awt.Color(0, 0, 0));
        b_name1.setText("b_name");
        jPanel3.add(b_name1, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 200, 200, 40));

        p_id.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        p_id.setForeground(new java.awt.Color(0, 0, 0));
        p_id.setText("p_id");
        jPanel3.add(p_id, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 200, 140, 40));

        booktable.setBackground(new java.awt.Color(255, 255, 255));
        booktable.setForeground(new java.awt.Color(0, 0, 0));
        booktable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "null"
            }
        ));
        booktable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                booktableMouseClicked(evt);
            }
        });
        booktable.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                booktableKeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(booktable);

        jPanel3.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, 510, 170));

        b_author2.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        b_author2.setForeground(new java.awt.Color(0, 0, 0));
        b_author2.setText("b_author");
        jPanel3.add(b_author2, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 200, 140, 40));

        p_name.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        p_name.setForeground(new java.awt.Color(0, 0, 0));
        p_name.setText("p_name");
        jPanel3.add(p_name, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 200, 140, 40));

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 140, 1010, 250));

        p_search.setBackground(new java.awt.Color(150, 197, 230));
        p_search.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        p_search.setForeground(new java.awt.Color(0, 0, 0));
        jPanel1.add(p_search, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 10, 330, -1));

        b_serchbtn.setIcon(new javax.swing.ImageIcon("E:\\java_test\\LMS\\src\\main\\java\\imsges\\searchbtn.png.jpg")); // NOI18N
        b_serchbtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                b_serchbtnMouseClicked(evt);
            }
        });
        jPanel1.add(b_serchbtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 60, -1, 60));

        jLabel3.setIcon(new javax.swing.ImageIcon("E:\\java_test\\LMS\\src\\main\\java\\imsges\\home1.png")); // NOI18N
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 50, 60));

        jLabel4.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("HOME");
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel4MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 46, 50, 20));

        b_serch.setBackground(new java.awt.Color(150, 197, 230));
        b_serch.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        b_serch.setForeground(new java.awt.Color(0, 0, 0));
        jPanel1.add(b_serch, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 70, 330, 40));

        p_serchbtn.setText("Person Search");
        p_serchbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                p_serchbtnActionPerformed(evt);
            }
        });
        jPanel1.add(p_serchbtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 10, -1, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1010, 560));

        setSize(new java.awt.Dimension(1024, 597));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseClicked
        new main_menu().setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_jLabel4MouseClicked

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
        new main_menu().setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_jLabel3MouseClicked

    private void persontableKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_persontableKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_persontableKeyPressed

    private void persontableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_persontableMouseClicked
        p_table_select();
    }//GEN-LAST:event_persontableMouseClicked

    private void persontableKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_persontableKeyReleased
        p_table_select();
    }//GEN-LAST:event_persontableKeyReleased

    private void booktableKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_booktableKeyReleased
        book_select();
    }//GEN-LAST:event_booktableKeyReleased

    private void booktableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_booktableMouseClicked
        book_select();
    }//GEN-LAST:event_booktableMouseClicked

    private void b_serchbtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_b_serchbtnMouseClicked
        book_serch();
    }//GEN-LAST:event_b_serchbtnMouseClicked

    private void p_serchbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_p_serchbtnActionPerformed
        person_serch();
    }//GEN-LAST:event_p_serchbtnActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        get_table_data();
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
            java.util.logging.Logger.getLogger(find_book.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(find_book.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(find_book.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(find_book.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new find_book().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel b_author2;
    private javax.swing.JLabel b_id;
    private javax.swing.JLabel b_name1;
    private javax.swing.JTextField b_serch;
    private javax.swing.JLabel b_serchbtn;
    private javax.swing.JTable booktable;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel p_id;
    private javax.swing.JLabel p_name;
    private javax.swing.JTextField p_search;
    private javax.swing.JButton p_serchbtn;
    private javax.swing.JTable persontable;
    // End of variables declaration//GEN-END:variables
}
