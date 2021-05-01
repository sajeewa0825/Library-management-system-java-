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
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;
import code.lms.DBconnect;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sajeewa
 */
public final class setting extends javax.swing.JFrame {

    /**
     * Creates new form setting
     */
    Connection con = DBconnect.connect();
    PreparedStatement prt;
    Resultset rs = null;

    public setting() {
        initComponents();
        firstpannel();
        person_table_load();
        con = DBconnect.connect();

    }

    public void person_table_load() {
        try {
            String sql = "SELECT * FROM person";
            prt = con.prepareStatement(sql);
            rs = (Resultset) prt.executeQuery();
            table1.setModel(DbUtils.resultSetToTableModel((ResultSet) rs));
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    public void p_table_data() {
        int pdata = table1.getSelectedRow();

        String id = table1.getValueAt(pdata, 0).toString();
        String f_names = table1.getValueAt(pdata, 1).toString();
        String l_names = table1.getValueAt(pdata, 2).toString();
        String emaill = table1.getValueAt(pdata, 3).toString();
        String p_word;
        p_word = table1.getValueAt(pdata, 4).toString();

        p_id.setText(id);
        this.f_name.setText(f_names);
        this.l_name.setText(l_names);
        email.setText(emaill);
        password.setText(p_word);

    }

    public void p_update() {
        String idd = p_id.getText();
        String l_name_update = l_name.getText();
        String f_name_update = f_name.getText();
        String email_update = email.getText();
        String password_update = password.getText();

        try {
            String sql = "UPDATE person SET first_name='" + f_name_update + "', last_name='" + l_name_update + "', email='" + email_update + "', password='" + password_update + "' WHERE id='" + idd + "'";
            prt = con.prepareStatement(sql);
            prt.execute();
            JOptionPane.showMessageDialog(null, "updated");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void p_delete() {
        int check = JOptionPane.showConfirmDialog(null, "confirm delete");

        try {
            String id = p_id.getText();
            if (check == 0) {
                String sql = "DELETE FROM person WHERE id='" + id + "'";
                prt = con.prepareStatement(sql);
                prt.execute();
                JOptionPane.showMessageDialog(null, "deleted");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void P_search() {
        String searchdata = p_serachbox.getText();

        try {
            String sql = "SELECT * FROM person WHERE first_name LIKE '%" + searchdata + "%' or id LIKE '%" + searchdata + "%' or last_name LIKE '%" + searchdata + "%' or email LIKE '%" + searchdata + "%'  ";
            prt = con.prepareStatement(sql);
            rs = (Resultset) prt.executeQuery();
            table1.setModel(DbUtils.resultSetToTableModel((ResultSet) rs));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void book_table_load() {
        try {
            String sql = "SELECT * FROM bookstore";
            prt = con.prepareStatement(sql);
            rs = (Resultset) prt.executeQuery();
            table_book_store.setModel(DbUtils.resultSetToTableModel((ResultSet) rs));
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void book_table_data() {
        int bdata = table_book_store.getSelectedRow();

        String bid = table_book_store.getValueAt(bdata, 0).toString();
        String b_names = table_book_store.getValueAt(bdata, 1).toString();
        String b_authors = table_book_store.getValueAt(bdata, 2).toString();

        b_id.setText(bid);
        b_author.setText(b_authors);
        b_name.setText(b_names);

    }

    public void b_update() {
        String bid = b_id.getText();
        String b_name_update = b_name.getText();
        String b_author_update = b_author.getText();

        try {
            String sql = "UPDATE bookstore SET book_name='" + b_name_update + "', book_author='" + b_author_update + "' WHERE book_id='" + bid + "'";
            prt = con.prepareStatement(sql);
            prt.execute();
            JOptionPane.showMessageDialog(null, "updated");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void b_delete() {
        int check = JOptionPane.showConfirmDialog(null, "confirm delete");

        try {
            String id = b_id.getText();
            if (check == 0) {
                String sql = "DELETE FROM bookstore WHERE book_id='" + id + "'";
                prt = con.prepareStatement(sql);
                prt.execute();
                JOptionPane.showMessageDialog(null, "deleted");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void b_search() {
        String searchdata = book_search.getText();

        try {
            String sql = "SELECT * FROM bookstore WHERE book_name LIKE '%" + searchdata + "%' or book_id LIKE '%" + searchdata + "%' or book_author LIKE '%" + searchdata + "%' ";
            prt = con.prepareStatement(sql);
            rs = (Resultset) prt.executeQuery();
            table_book_store.setModel(DbUtils.resultSetToTableModel((ResultSet) rs));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void get_book() {
        try {
            String sql = "SELECT * FROM `getbook`";
            prt = con.prepareStatement(sql);
            rs = (Resultset) prt.executeQuery();
            get_book_table.setModel(DbUtils.resultSetToTableModel((ResultSet) rs));
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void book_hand_over() {
        try {
            String sql = "SELECT * FROM `hand_over`";
            prt = con.prepareStatement(sql);
            rs = (Resultset) prt.executeQuery();
            hand_over_table.setModel(DbUtils.resultSetToTableModel((ResultSet) rs));
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void book_hand_over_search() {
        String searchdata = h_serch.getText();

        try {
            String sql = "SELECT * FROM hand_over WHERE return_id LIKE '%" + searchdata + "%' or get_id LIKE '%" + searchdata + "%'";
            prt = con.prepareStatement(sql);
            rs = (Resultset) prt.executeQuery();
            hand_over_table.setModel(DbUtils.resultSetToTableModel((ResultSet) rs));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void get_book_search() {
        String searchdata = get_serch.getText();

        try {
            String sql = "SELECT * FROM getbook WHERE getbook_id LIKE '%" + searchdata + "%' or book_id LIKE '%" + searchdata + "%' or person_id LIKE '%" + searchdata + "%'";
            prt = con.prepareStatement(sql);
            rs = (Resultset) prt.executeQuery();
            get_book_table.setModel(DbUtils.resultSetToTableModel((ResultSet) rs));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void get_book_update() {
        String b_id = book_id_text.getText();
        String g_id = get_book_id.getText();
        String p_id = person_id_text.getText();

        try {
            String sql = "UPDATE getbook SET book_id='" + b_id + "', person_id='" + p_id + "' WHERE getbook_id='" + g_id + "'";
            prt = con.prepareStatement(sql);
            prt.execute();
            JOptionPane.showMessageDialog(null, "updated");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void get_book_select() {
        int bdata = get_book_table.getSelectedRow();

        String get = get_book_table.getValueAt(bdata, 0).toString();
        String book = get_book_table.getValueAt(bdata, 1).toString();
        String person = get_book_table.getValueAt(bdata, 2).toString();

        get_book_id.setText(get);
        book_id_text.setText(book);
        person_id_text.setText(person);
    }

    public void get_book_delete() {
        int check = JOptionPane.showConfirmDialog(null, "confirm delete");

        try {
            String id = get_book_id.getText();
            if (check == 0) {
                String sql = "DELETE FROM getbook WHERE book_id='" + id + "'";
                prt = con.prepareStatement(sql);
                prt.execute();
                JOptionPane.showMessageDialog(null, "deleted");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void hand_over_select() {
        int bdata = hand_over_table.getSelectedRow();

        String r_id = hand_over_table.getValueAt(bdata, 0).toString();
        String g_id = hand_over_table.getValueAt(bdata, 1).toString();

        this.r_id.setText(r_id);
        get_id_text.setText(g_id);

    }

    public void hand_over_update() {
        String rid = r_id.getText();
        String gid = get_id_text.getText();

        try {
            String sql = "UPDATE hand_over SET get_id='" + gid + "' WHERE return_id='" + rid + "'";
            prt = con.prepareStatement(sql);
            prt.execute();
            JOptionPane.showMessageDialog(null, "updated");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void hand_over_delete() {
        int check = JOptionPane.showConfirmDialog(null, "confirm delete");

        try {
            String id = r_id.getText();
            if (check == 0) {
                String sql = "DELETE FROM hand_over WHERE return_id='" + id + "'";
                prt = con.prepareStatement(sql);
                prt.execute();
                JOptionPane.showMessageDialog(null, "deleted");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void password_get() {
        try {
            prt = con.prepareStatement("SELECT * FROM password WHERE 1");
            ResultSet result = prt.executeQuery();
            while (result.next()) {
                String ldata = result.getString(1);
                String lname = result.getString(2);
                password_check(ldata, lname);
            }
        } catch (SQLException ex) {
            Logger.getLogger(check.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void password_check(String n1, String n2) {
        String u_name = n1;
        String p_word = n2;
        String u = this.u_name.getText();
        String p = this.p_word.getText();

        String password1 = this.password1.getText();
        String password2 = this.password2.getText();

        if (u.equals(u_name) && p.equals(p_word)) {

            if (password1.equals(password2)) {
                update_password();
            } else {
                JOptionPane.showMessageDialog(null, "Password not equals");
            }
        } else {
            this.u_name.setText("");
            this.p_word.setText("");
            JOptionPane.showMessageDialog(null, "User name or Password incorrect");
        }
    }

    public void update_password() {
        String nuser = new_user.getText();
        String password1 = this.password1.getText();

        try {
            String sql = "UPDATE password SET user_name='" + nuser + "', password='" + password1 + "' WHERE 1";
            prt = con.prepareStatement(sql);
            prt.execute();
            JOptionPane.showMessageDialog(null, "updated");
            this.password1.setText("");
            password2.setText("");
            u_name.setText("");
            new_user.setText("");
            p_word.setText("");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    void firstpannel() {
        persondata.setVisible(true);
        bookhandover.setVisible(false);
        bookstore.setVisible(false);
        get_book.setVisible(false);
        security.setVisible(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        account = new javax.swing.JLabel();
        book_store = new javax.swing.JLabel();
        handover = new javax.swing.JLabel();
        get__book = new javax.swing.JLabel();
        secue = new javax.swing.JLabel();
        main_pannel = new javax.swing.JPanel();
        persondata = new javax.swing.JPanel();
        p_serachbox = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        p = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        f_name = new javax.swing.JTextField();
        l_name = new javax.swing.JTextField();
        email = new javax.swing.JTextField();
        password = new javax.swing.JTextField();
        p_clear = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        p_id = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        table1 = new javax.swing.JTable();
        bookstore = new javax.swing.JPanel();
        book_search = new javax.swing.JTextField();
        jButton5 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        table_book_store = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        b_id = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        b_name = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        b_author = new javax.swing.JTextField();
        clear = new javax.swing.JButton();
        b_update = new javax.swing.JButton();
        b_delete = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        bookhandover = new javax.swing.JPanel();
        get_book_serch = new javax.swing.JButton();
        h_serch = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        hand_over_table = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        r_id = new javax.swing.JLabel();
        get_id_text = new javax.swing.JTextField();
        get_book = new javax.swing.JPanel();
        jButton10 = new javax.swing.JButton();
        get_serch = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        get_book_table = new javax.swing.JTable();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        book_id_text = new javax.swing.JTextField();
        person_id_text = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        get_book_id = new javax.swing.JLabel();
        security = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        new_user = new javax.swing.JTextField();
        u_name = new javax.swing.JTextField();
        jButton11 = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        password1 = new javax.swing.JPasswordField();
        password2 = new javax.swing.JPasswordField();
        p_word = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel3.setBackground(new java.awt.Color(204, 204, 204));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setIcon(new javax.swing.ImageIcon("E:\\java_test\\LMS\\src\\main\\java\\imsges\\home1.png")); // NOI18N
        jLabel1.setText("HOME");
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });

        account.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        account.setIcon(new javax.swing.ImageIcon("E:\\java_test\\LMS\\src\\main\\java\\imsges\\sperson.jpg")); // NOI18N
        account.setText("ACCOUNT");
        account.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                accountMouseClicked(evt);
            }
        });

        book_store.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        book_store.setIcon(new javax.swing.ImageIcon("E:\\java_test\\LMS\\src\\main\\java\\imsges\\sbook.jpg")); // NOI18N
        book_store.setText("BOOK STORE");
        book_store.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                book_storeMouseClicked(evt);
            }
        });

        handover.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        handover.setIcon(new javax.swing.ImageIcon("E:\\java_test\\LMS\\src\\main\\java\\imsges\\shandover.jpg")); // NOI18N
        handover.setText("HAND OVER");
        handover.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                handoverMouseClicked(evt);
            }
        });

        get__book.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        get__book.setIcon(new javax.swing.ImageIcon("E:\\java_test\\LMS\\src\\main\\java\\imsges\\sgetbook.jpg")); // NOI18N
        get__book.setText("GET BOOK");
        get__book.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                get__bookMouseClicked(evt);
            }
        });

        secue.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        secue.setIcon(new javax.swing.ImageIcon("E:\\java_test\\LMS\\src\\main\\java\\imsges\\ssecurity.png")); // NOI18N
        secue.setText("PASSWORD");
        secue.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                secueMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(account, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(book_store, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(handover, javax.swing.GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE)
                    .addComponent(get__book, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(secue, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(account, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(book_store, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(handover, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(get__book)
                .addGap(18, 18, 18)
                .addComponent(secue)
                .addContainerGap(81, Short.MAX_VALUE))
        );

        jPanel3.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 190, 560));

        main_pannel.setBackground(new java.awt.Color(255, 255, 255));

        persondata.setBackground(new java.awt.Color(165, 186, 206));

        p_serachbox.setBackground(new java.awt.Color(255, 255, 255));
        p_serachbox.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        p_serachbox.setForeground(new java.awt.Color(0, 0, 0));
        p_serachbox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                p_serachboxActionPerformed(evt);
            }
        });

        jButton1.setBackground(new java.awt.Color(0, 0, 255));
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Search ID OR NAME");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        p.setBackground(new java.awt.Color(244, 247, 250));

        jLabel7.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("Frist Name");

        jLabel8.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setText("Last Name");

        jLabel9.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 0));
        jLabel9.setText("Email");

        jLabel10.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 0, 0));
        jLabel10.setText("Password");

        f_name.setBackground(new java.awt.Color(255, 255, 255));
        f_name.setForeground(new java.awt.Color(0, 0, 0));

        l_name.setBackground(new java.awt.Color(255, 255, 255));
        l_name.setForeground(new java.awt.Color(0, 0, 0));

        email.setBackground(new java.awt.Color(255, 255, 255));
        email.setForeground(new java.awt.Color(0, 0, 0));

        password.setBackground(new java.awt.Color(255, 255, 255));
        password.setForeground(new java.awt.Color(0, 0, 0));
        password.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                passwordActionPerformed(evt);
            }
        });

        p_clear.setBackground(new java.awt.Color(0, 0, 255));
        p_clear.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        p_clear.setForeground(new java.awt.Color(255, 255, 255));
        p_clear.setText("Clear");
        p_clear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                p_clearActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(0, 0, 255));
        jButton3.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("Delete");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setBackground(new java.awt.Color(0, 0, 255));
        jButton4.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("Update");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        p_id.setFont(new java.awt.Font("Dialog", 3, 18)); // NOI18N
        p_id.setForeground(new java.awt.Color(0, 0, 0));
        p_id.setText("id");

        jLabel11.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 0, 0));
        jLabel11.setText("ID");

        javax.swing.GroupLayout pLayout = new javax.swing.GroupLayout(p);
        p.setLayout(pLayout);
        pLayout.setHorizontalGroup(
            pLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pLayout.createSequentialGroup()
                .addGroup(pLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pLayout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(pLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(82, 82, 82)
                        .addGroup(pLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(email, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(password, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(l_name, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(f_name, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(p_id, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pLayout.createSequentialGroup()
                        .addGap(57, 57, 57)
                        .addComponent(p_clear, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(82, 82, 82)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(62, 62, 62)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(181, Short.MAX_VALUE))
        );
        pLayout.setVerticalGroup(
            pLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(pLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(p_id, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(f_name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(l_name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(password, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(pLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(p_clear)
                    .addComponent(jButton3)
                    .addComponent(jButton4))
                .addContainerGap(26, Short.MAX_VALUE))
        );

        table1.setBackground(new java.awt.Color(255, 255, 255));
        table1.setForeground(new java.awt.Color(0, 0, 0));
        table1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        table1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table1MouseClicked(evt);
            }
        });
        table1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                table1KeyReleased(evt);
            }
        });
        jScrollPane4.setViewportView(table1);

        javax.swing.GroupLayout persondataLayout = new javax.swing.GroupLayout(persondata);
        persondata.setLayout(persondataLayout);
        persondataLayout.setHorizontalGroup(
            persondataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(persondataLayout.createSequentialGroup()
                .addGap(89, 89, 89)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(p_serachbox, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, persondataLayout.createSequentialGroup()
                .addContainerGap(70, Short.MAX_VALUE)
                .addGroup(persondataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane4)
                    .addComponent(p, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(24, 24, 24))
        );
        persondataLayout.setVerticalGroup(
            persondataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(persondataLayout.createSequentialGroup()
                .addContainerGap(48, Short.MAX_VALUE)
                .addGroup(persondataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(p_serachbox, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(p, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31))
        );

        bookstore.setBackground(new java.awt.Color(165, 186, 206));

        book_search.setBackground(new java.awt.Color(255, 255, 255));
        book_search.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        book_search.setForeground(new java.awt.Color(0, 0, 0));

        jButton5.setBackground(new java.awt.Color(0, 0, 255));
        jButton5.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setText("Search");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        table_book_store.setBackground(new java.awt.Color(255, 255, 255));
        table_book_store.setForeground(new java.awt.Color(0, 0, 0));
        table_book_store.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        table_book_store.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_book_storeMouseClicked(evt);
            }
        });
        table_book_store.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                table_book_storeKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(table_book_store);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel12.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 0, 0));
        jLabel12.setText("BOOK ID");

        b_id.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        b_id.setForeground(new java.awt.Color(0, 0, 0));
        b_id.setText("ID");

        jLabel14.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(0, 0, 0));
        jLabel14.setText("BOOK NAME");

        b_name.setBackground(new java.awt.Color(255, 255, 255));
        b_name.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        b_name.setForeground(new java.awt.Color(0, 0, 0));

        jLabel15.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(0, 0, 0));
        jLabel15.setText("AUTHOUR");

        b_author.setBackground(new java.awt.Color(255, 255, 255));
        b_author.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        b_author.setForeground(new java.awt.Color(0, 0, 0));

        clear.setBackground(new java.awt.Color(0, 0, 255));
        clear.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        clear.setForeground(new java.awt.Color(255, 255, 255));
        clear.setText("Clear");
        clear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearActionPerformed(evt);
            }
        });

        b_update.setBackground(new java.awt.Color(0, 0, 255));
        b_update.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        b_update.setForeground(new java.awt.Color(255, 255, 255));
        b_update.setText("Update");
        b_update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_updateActionPerformed(evt);
            }
        });

        b_delete.setBackground(new java.awt.Color(0, 0, 255));
        b_delete.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        b_delete.setForeground(new java.awt.Color(255, 255, 255));
        b_delete.setText("Delete");
        b_delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_deleteActionPerformed(evt);
            }
        });

        jLabel16.setText("PHOTO");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(81, 81, 81)
                .addComponent(clear, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(b_update, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41)
                .addComponent(b_delete, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(71, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(43, 43, 43)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(b_id, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(b_name, javax.swing.GroupLayout.DEFAULT_SIZE, 227, Short.MAX_VALUE)
                    .addComponent(b_author))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(b_id))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(b_name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15)
                            .addComponent(b_author, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(clear)
                    .addComponent(b_update)
                    .addComponent(b_delete))
                .addGap(38, 38, 38))
        );

        javax.swing.GroupLayout bookstoreLayout = new javax.swing.GroupLayout(bookstore);
        bookstore.setLayout(bookstoreLayout);
        bookstoreLayout.setHorizontalGroup(
            bookstoreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bookstoreLayout.createSequentialGroup()
                .addGroup(bookstoreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(bookstoreLayout.createSequentialGroup()
                        .addGap(162, 162, 162)
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(book_search, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(bookstoreLayout.createSequentialGroup()
                        .addGap(98, 98, 98)
                        .addGroup(bookstoreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane1)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(62, Short.MAX_VALUE))
        );
        bookstoreLayout.setVerticalGroup(
            bookstoreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bookstoreLayout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addGroup(bookstoreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(book_search, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5))
                .addGap(26, 26, 26)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(85, Short.MAX_VALUE))
        );

        bookhandover.setBackground(new java.awt.Color(165, 186, 206));

        get_book_serch.setBackground(new java.awt.Color(0, 0, 255));
        get_book_serch.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        get_book_serch.setForeground(new java.awt.Color(255, 255, 255));
        get_book_serch.setText("Search");
        get_book_serch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                get_book_serchActionPerformed(evt);
            }
        });

        h_serch.setBackground(new java.awt.Color(255, 255, 255));
        h_serch.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        h_serch.setForeground(new java.awt.Color(0, 0, 0));
        h_serch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                h_serchActionPerformed(evt);
            }
        });

        jScrollPane2.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane2.setForeground(new java.awt.Color(0, 0, 0));

        hand_over_table.setBackground(new java.awt.Color(255, 255, 255));
        hand_over_table.setForeground(new java.awt.Color(0, 0, 0));
        hand_over_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        hand_over_table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                hand_over_tableMouseClicked(evt);
            }
        });
        hand_over_table.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                hand_over_tableKeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(hand_over_table);

        jButton2.setBackground(new java.awt.Color(0, 0, 255));
        jButton2.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("UPDATE");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton6.setBackground(new java.awt.Color(0, 0, 255));
        jButton6.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jButton6.setForeground(new java.awt.Color(255, 255, 255));
        jButton6.setText("DELETE");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setBackground(new java.awt.Color(0, 0, 255));
        jButton7.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jButton7.setForeground(new java.awt.Color(255, 255, 255));
        jButton7.setText("CLEAR");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jLabel18.setBackground(new java.awt.Color(255, 255, 255));
        jLabel18.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(0, 0, 0));
        jLabel18.setText("GET ID");

        jLabel17.setBackground(new java.awt.Color(255, 255, 255));
        jLabel17.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(0, 0, 0));
        jLabel17.setText("RETURN ID");

        r_id.setBackground(new java.awt.Color(255, 255, 255));
        r_id.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        r_id.setForeground(new java.awt.Color(0, 0, 0));
        r_id.setText("GET ID");

        get_id_text.setBackground(new java.awt.Color(255, 255, 255));
        get_id_text.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        get_id_text.setForeground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout bookhandoverLayout = new javax.swing.GroupLayout(bookhandover);
        bookhandover.setLayout(bookhandoverLayout);
        bookhandoverLayout.setHorizontalGroup(
            bookhandoverLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, bookhandoverLayout.createSequentialGroup()
                .addGap(136, 136, 136)
                .addGroup(bookhandoverLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(bookhandoverLayout.createSequentialGroup()
                        .addComponent(get_book_serch, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(h_serch, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(bookhandoverLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(bookhandoverLayout.createSequentialGroup()
                            .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(75, 75, 75)
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(70, 70, 70)
                            .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 496, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(bookhandoverLayout.createSequentialGroup()
                            .addGap(10, 10, 10)
                            .addGroup(bookhandoverLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(18, 18, 18)
                            .addGroup(bookhandoverLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(r_id, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(get_id_text, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(132, 132, 132))
        );
        bookhandoverLayout.setVerticalGroup(
            bookhandoverLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bookhandoverLayout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addGroup(bookhandoverLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(h_serch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(get_book_serch))
                .addGap(38, 38, 38)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(bookhandoverLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(r_id))
                .addGap(9, 9, 9)
                .addGroup(bookhandoverLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(get_id_text, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(bookhandoverLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(77, Short.MAX_VALUE))
        );

        get_book.setBackground(new java.awt.Color(165, 186, 206));

        jButton10.setBackground(new java.awt.Color(0, 0, 255));
        jButton10.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jButton10.setForeground(new java.awt.Color(255, 255, 255));
        jButton10.setText("Search");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        get_serch.setBackground(new java.awt.Color(255, 255, 255));
        get_serch.setForeground(new java.awt.Color(0, 0, 0));
        get_serch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                get_serchActionPerformed(evt);
            }
        });

        get_book_table.setBackground(new java.awt.Color(255, 255, 255));
        get_book_table.setForeground(new java.awt.Color(0, 0, 0));
        get_book_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        get_book_table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                get_book_tableMouseClicked(evt);
            }
        });
        get_book_table.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                get_book_tableKeyReleased(evt);
            }
        });
        jScrollPane3.setViewportView(get_book_table);

        jButton8.setBackground(new java.awt.Color(0, 0, 255));
        jButton8.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jButton8.setForeground(new java.awt.Color(255, 255, 255));
        jButton8.setText("UPDATE");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jButton9.setBackground(new java.awt.Color(0, 0, 255));
        jButton9.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jButton9.setForeground(new java.awt.Color(255, 255, 255));
        jButton9.setText("DELETE");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jButton12.setBackground(new java.awt.Color(0, 0, 255));
        jButton12.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jButton12.setForeground(new java.awt.Color(255, 255, 255));
        jButton12.setText("CLEAR");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("BOOK ID");

        jLabel6.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("PERSON ID");

        book_id_text.setBackground(new java.awt.Color(255, 255, 255));
        book_id_text.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        book_id_text.setForeground(new java.awt.Color(0, 0, 0));

        person_id_text.setBackground(new java.awt.Color(255, 255, 255));
        person_id_text.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        person_id_text.setForeground(new java.awt.Color(0, 0, 0));

        jLabel13.setBackground(new java.awt.Color(255, 255, 255));
        jLabel13.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 0, 0));
        jLabel13.setText("GET BOOK ID");

        get_book_id.setBackground(new java.awt.Color(255, 255, 255));
        get_book_id.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        get_book_id.setForeground(new java.awt.Color(0, 0, 0));
        get_book_id.setText("GET BOOK ID");

        javax.swing.GroupLayout get_bookLayout = new javax.swing.GroupLayout(get_book);
        get_book.setLayout(get_bookLayout);
        get_bookLayout.setHorizontalGroup(
            get_bookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(get_bookLayout.createSequentialGroup()
                .addGap(142, 142, 142)
                .addGroup(get_bookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(get_bookLayout.createSequentialGroup()
                        .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(get_serch, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(get_bookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(get_bookLayout.createSequentialGroup()
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(person_id_text))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, get_bookLayout.createSequentialGroup()
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(book_id_text, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(get_bookLayout.createSequentialGroup()
                        .addGroup(get_bookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton12, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE))
                        .addGroup(get_bookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(get_bookLayout.createSequentialGroup()
                                .addGap(58, 58, 58)
                                .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(61, 61, 61)
                                .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(get_bookLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(get_book_id, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addContainerGap(157, Short.MAX_VALUE))
        );
        get_bookLayout.setVerticalGroup(
            get_bookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(get_bookLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(get_bookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton10)
                    .addComponent(get_serch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addGroup(get_bookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(book_id_text, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(get_bookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(person_id_text, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                .addGroup(get_bookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(get_book_id))
                .addGap(18, 18, 18)
                .addGroup(get_bookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30))
        );

        security.setBackground(new java.awt.Color(165, 186, 206));

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Password");

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("New Password");

        jLabel5.setBackground(new java.awt.Color(255, 255, 255));
        jLabel5.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("New User Name");

        new_user.setBackground(new java.awt.Color(255, 255, 255));
        new_user.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        new_user.setForeground(new java.awt.Color(0, 0, 0));

        u_name.setBackground(new java.awt.Color(255, 255, 255));
        u_name.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        u_name.setForeground(new java.awt.Color(0, 0, 0));

        jButton11.setBackground(new java.awt.Color(0, 0, 255));
        jButton11.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jButton11.setForeground(new java.awt.Color(255, 255, 255));
        jButton11.setText("SUBMIT");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        jLabel19.setBackground(new java.awt.Color(255, 255, 255));
        jLabel19.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(0, 0, 0));
        jLabel19.setText("User Name");

        jLabel20.setBackground(new java.awt.Color(255, 255, 255));
        jLabel20.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(0, 0, 0));
        jLabel20.setText("Confirm Password");

        password1.setBackground(new java.awt.Color(255, 255, 255));
        password1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        password1.setForeground(new java.awt.Color(0, 0, 0));

        password2.setBackground(new java.awt.Color(255, 255, 255));
        password2.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        password2.setForeground(new java.awt.Color(0, 0, 0));

        p_word.setBackground(new java.awt.Color(255, 255, 255));
        p_word.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        p_word.setForeground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout securityLayout = new javax.swing.GroupLayout(security);
        security.setLayout(securityLayout);
        securityLayout.setHorizontalGroup(
            securityLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(securityLayout.createSequentialGroup()
                .addGroup(securityLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(securityLayout.createSequentialGroup()
                        .addGap(343, 343, 343)
                        .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(securityLayout.createSequentialGroup()
                        .addGap(97, 97, 97)
                        .addGroup(securityLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(securityLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(u_name, javax.swing.GroupLayout.DEFAULT_SIZE, 301, Short.MAX_VALUE)
                            .addComponent(new_user, javax.swing.GroupLayout.DEFAULT_SIZE, 301, Short.MAX_VALUE)
                            .addComponent(password1)
                            .addComponent(password2)
                            .addComponent(p_word))))
                .addContainerGap(165, Short.MAX_VALUE))
        );
        securityLayout.setVerticalGroup(
            securityLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(securityLayout.createSequentialGroup()
                .addGap(126, 126, 126)
                .addGroup(securityLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(u_name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(securityLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(p_word, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(securityLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(securityLayout.createSequentialGroup()
                        .addGroup(securityLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(new_user, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(password1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(securityLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(password2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17)
                .addComponent(jButton11)
                .addContainerGap(186, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout main_pannelLayout = new javax.swing.GroupLayout(main_pannel);
        main_pannel.setLayout(main_pannelLayout);
        main_pannelLayout.setHorizontalGroup(
            main_pannelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bookhandover, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(main_pannelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(main_pannelLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(bookstore, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
            .addGroup(main_pannelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(main_pannelLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(persondata, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
            .addGroup(main_pannelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(main_pannelLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(security, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
            .addGroup(main_pannelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(main_pannelLayout.createSequentialGroup()
                    .addGap(6, 6, 6)
                    .addComponent(get_book, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGap(6, 6, 6)))
        );
        main_pannelLayout.setVerticalGroup(
            main_pannelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bookhandover, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(main_pannelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(main_pannelLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(bookstore, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
            .addGroup(main_pannelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(main_pannelLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(persondata, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
            .addGroup(main_pannelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, main_pannelLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(security, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
            .addGroup(main_pannelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(main_pannelLayout.createSequentialGroup()
                    .addGap(6, 6, 6)
                    .addComponent(get_book, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGap(6, 6, 6)))
        );

        jPanel3.add(main_pannel, new org.netbeans.lib.awtextra.AbsoluteConstraints(186, 0, 760, 560));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setSize(new java.awt.Dimension(960, 597));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void passwordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_passwordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_passwordActionPerformed

    private void accountMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_accountMouseClicked
        persondata.setVisible(true);
        bookhandover.setVisible(false);
        bookstore.setVisible(false);
        get_book.setVisible(false);
        security.setVisible(false);

    }//GEN-LAST:event_accountMouseClicked

    private void book_storeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_book_storeMouseClicked
        persondata.setVisible(false);
        bookhandover.setVisible(false);
        bookstore.setVisible(true);
        get_book.setVisible(false);
        security.setVisible(false);
        book_table_load();
    }//GEN-LAST:event_book_storeMouseClicked

    private void get_book_serchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_get_book_serchActionPerformed
        book_hand_over_search();
    }//GEN-LAST:event_get_book_serchActionPerformed

    private void handoverMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_handoverMouseClicked
        persondata.setVisible(false);
        bookhandover.setVisible(true);
        bookstore.setVisible(false);
        get_book.setVisible(false);
        security.setVisible(false);
        book_hand_over();
    }//GEN-LAST:event_handoverMouseClicked

    private void secueMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_secueMouseClicked
        persondata.setVisible(false);
        bookhandover.setVisible(false);
        bookstore.setVisible(false);
        get_book.setVisible(false);
        security.setVisible(true);
    }//GEN-LAST:event_secueMouseClicked

    private void get__bookMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_get__bookMouseClicked
        persondata.setVisible(false);
        bookhandover.setVisible(false);
        bookstore.setVisible(false);
        get_book.setVisible(true);
        security.setVisible(false);
        get_book();
    }//GEN-LAST:event_get__bookMouseClicked

    private void get_serchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_get_serchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_get_serchActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        p_delete();
        person_table_load();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        p_update();
        person_table_load();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void p_clearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_p_clearActionPerformed
        p_id.setText("");
        f_name.setText("");
        l_name.setText("");
        email.setText("");
        password.setText("");
    }//GEN-LAST:event_p_clearActionPerformed

    private void p_serachboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_p_serachboxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_p_serachboxActionPerformed

    private void table1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table1MouseClicked
        p_table_data();
    }//GEN-LAST:event_table1MouseClicked

    private void table1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_table1KeyReleased
        p_table_data();
    }//GEN-LAST:event_table1KeyReleased

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        P_search();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void table_book_storeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_table_book_storeKeyReleased
        book_table_data();
    }//GEN-LAST:event_table_book_storeKeyReleased

    private void table_book_storeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_book_storeMouseClicked
        book_table_data();
    }//GEN-LAST:event_table_book_storeMouseClicked

    private void b_updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_updateActionPerformed
        b_update();
        book_table_load();
    }//GEN-LAST:event_b_updateActionPerformed

    private void clearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearActionPerformed
        b_author.setText("");
        b_id.setText("");
        b_name.setText("");
    }//GEN-LAST:event_clearActionPerformed

    private void b_deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_deleteActionPerformed
        b_delete();
        book_table_load();
    }//GEN-LAST:event_b_deleteActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        b_search();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        new main_menu().setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_jLabel1MouseClicked

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        get_book_search();
    }//GEN-LAST:event_jButton10ActionPerformed

    private void h_serchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_h_serchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_h_serchActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        get_serch.setText("");
        get_book_id.setText("");
        person_id_text.setText("");
        book_id_text.setText("");
        get_book();
    }//GEN-LAST:event_jButton12ActionPerformed

    private void get_book_tableKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_get_book_tableKeyReleased
        get_book_select();
    }//GEN-LAST:event_get_book_tableKeyReleased

    private void get_book_tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_get_book_tableMouseClicked
        get_book_select();
    }//GEN-LAST:event_get_book_tableMouseClicked

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        get_book_update();
        get_book();
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        get_book_delete();
        get_book();
    }//GEN-LAST:event_jButton9ActionPerformed

    private void hand_over_tableKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_hand_over_tableKeyReleased
        hand_over_select();
    }//GEN-LAST:event_hand_over_tableKeyReleased

    private void hand_over_tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_hand_over_tableMouseClicked
        hand_over_select();
    }//GEN-LAST:event_hand_over_tableMouseClicked

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        r_id.setText("");
        get_id_text.setText("");
        h_serch.setText("");
        book_hand_over();
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        hand_over_update();
        book_hand_over();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        hand_over_delete();
        book_hand_over();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        password_get();
    }//GEN-LAST:event_jButton11ActionPerformed

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
            java.util.logging.Logger.getLogger(setting.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(setting.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(setting.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(setting.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new setting().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel account;
    private javax.swing.JTextField b_author;
    private javax.swing.JButton b_delete;
    private javax.swing.JLabel b_id;
    private javax.swing.JTextField b_name;
    private javax.swing.JButton b_update;
    private javax.swing.JTextField book_id_text;
    private javax.swing.JTextField book_search;
    private javax.swing.JLabel book_store;
    private javax.swing.JPanel bookhandover;
    private javax.swing.JPanel bookstore;
    private javax.swing.JButton clear;
    private javax.swing.JTextField email;
    private javax.swing.JTextField f_name;
    private javax.swing.JLabel get__book;
    private javax.swing.JPanel get_book;
    private javax.swing.JLabel get_book_id;
    private javax.swing.JButton get_book_serch;
    private javax.swing.JTable get_book_table;
    private javax.swing.JTextField get_id_text;
    private javax.swing.JTextField get_serch;
    private javax.swing.JTextField h_serch;
    private javax.swing.JTable hand_over_table;
    private javax.swing.JLabel handover;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTextField l_name;
    private javax.swing.JPanel main_pannel;
    private javax.swing.JTextField new_user;
    private javax.swing.JPanel p;
    private javax.swing.JButton p_clear;
    private javax.swing.JLabel p_id;
    private javax.swing.JTextField p_serachbox;
    private javax.swing.JPasswordField p_word;
    private javax.swing.JTextField password;
    private javax.swing.JPasswordField password1;
    private javax.swing.JPasswordField password2;
    private javax.swing.JTextField person_id_text;
    private javax.swing.JPanel persondata;
    private javax.swing.JLabel r_id;
    private javax.swing.JLabel secue;
    private javax.swing.JPanel security;
    private javax.swing.JTable table1;
    private javax.swing.JTable table_book_store;
    private javax.swing.JTextField u_name;
    // End of variables declaration//GEN-END:variables
}
