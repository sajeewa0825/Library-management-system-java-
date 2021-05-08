/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package code.lms;

import com.mysql.cj.protocol.Resultset;
import java.awt.Color;
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
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author sajeewa
 */
public class user_acces extends javax.swing.JFrame {

    /**
     * Creates new form user_acces
     */
    Connection con;
    PreparedStatement prt;
    Resultset rs = null;
    ZoneId z = ZoneId.of("Asia/Colombo");
    LocalDate today = LocalDate.now(z);
    String current_date = today.toString();
    SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");

    public user_acces() {
        // con = DBconnect.connect();
        initComponents();
//        find_book_pannel.setVisible(true);
//        return_pannel.setVisible(false);
//        profile_pannel.setVisible(false);
        //person_data();

    }

    public user_acces(String id) throws MessagingException {
        con = DBconnect.connect();
        initComponents();
        id_label.setText(id);
        find_book_pannel.setVisible(true);
        return_pannel.setVisible(false);
        profile_pannel.setVisible(false);
        person_data();
        book_table_load();
        user_return_book_table();
        return_date_check();

    }

    public void setColor(JPanel p) {
        p.setBackground(new Color(99, 125, 252));
    }

    public void resetColor(JPanel p1) {
        p1.setBackground(new Color(52, 57, 87));
    }

    public void set_toolbarname(String name, String id) {
        //String name = loginobject.sendname(null);
        name_label.setText(name);
        id_label.setText(id);
        System.out.println("toolbar name 1 " + name);

    }

    public void person_data() {
        System.out.println("run person data method");
        String u_id = id_label.getText();
        // String email1 = null, f_name1 = null, l_name1 = null;

        try {
            System.out.println("run try catch " + u_id);
            prt = con.prepareStatement("SELECT  first_name, last_name, email, password,date  FROM person WHERE id LIKE '" + u_id + "'");
            ResultSet result = prt.executeQuery();
            while (result.next()) {
                String f_name1 = result.getString(1);
                String l_name1 = result.getString(2);
                String email1 = result.getString(3);
                String password1 = result.getString(4);
                // String date = result.getString(5);
                // System.out.println("date " + date);

                name_label.setText(f_name1);
                this.f_name.setText(f_name1);
                this.l_name.setText(l_name1);
                this.email.setText(email1);

            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }

    }

    public void p_password_check() {
        String id = id_label.getText();
        String password1 = current_password.getText();
        String password2 = null;

        try {
            prt = con.prepareStatement("SELECT password  FROM person WHERE id LIKE '" + id + "'");
            ResultSet result = prt.executeQuery();
            while (result.next()) {
                password2 = result.getString(1);
                if (password1.equals(password2)) {
                    p_update(id);
                } else {
                    JOptionPane.showMessageDialog(null, "password incorrect");
                }

            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }

    }

    public void p_update(String id) {
        String f_name_data = f_name.getText();
        String l_name_data = l_name.getText();
        String email_data = email.getText();
        String n1_password = new_password.getText();
        String n2_password = conform_password.getText();
        String password1 = current_password.getText();

        if (n1_password.equals("") && n2_password.equals("")) {
            n1_password = password1;
            n2_password = password1;
        }

        if (n1_password.equals(n2_password)) {

            try {
                String sql = "UPDATE person SET first_name='" + f_name_data + "', last_name='" + l_name_data + "', email='" + email_data + "', password='" + n1_password + "' WHERE id='" + id + "'";
                prt = con.prepareStatement(sql);
                prt.execute();
                JOptionPane.showMessageDialog(null, "updated");
                person_data();
                name_label.setText(f_name_data);
                current_password.setText("");
                new_password.setText("");
                conform_password.setText("");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        } else {
            JOptionPane.showMessageDialog(null, "password not equals");
        }
    }

    public void book_table_load() {
        try {
            String sql = "SELECT `book_id` as ID, `book_name` as Name, `book_author` as Autor FROM `bookstore`";
            prt = con.prepareStatement(sql);
            rs = (Resultset) prt.executeQuery();
            book_table.setModel(DbUtils.resultSetToTableModel((ResultSet) rs));
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void book_select() {
        int bdata = book_table.getSelectedRow();

        String b_id = book_table.getValueAt(bdata, 0).toString();
        String b_names = book_table.getValueAt(bdata, 1).toString();

        select_book_name.setText(b_names);
    }

    public void book_serch() {
        String searchdata = search_box.getText();

        try {
            String sql = "SELECT book_id as ID , book_name as Name, book_author as Author FROM bookstore WHERE book_name LIKE '%" + searchdata + "%' or book_id LIKE '%" + searchdata + "%' or book_author LIKE '%" + searchdata + "%' ";
            prt = con.prepareStatement(sql);
            rs = (Resultset) prt.executeQuery();
            book_table.setModel(DbUtils.resultSetToTableModel((ResultSet) rs));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void get_table_data() {
        int bdata = book_table.getSelectedRow();
        String bid = book_table.getValueAt(bdata, 0).toString();
        String data2 = "0";
        try {
            prt = con.prepareStatement("SELECT * FROM getbook WHERE book_id LIKE '%" + bid + "%' ");
            ResultSet result = prt.executeQuery();
            while (result.next()) {
                data2 = result.getString(2);
            }
            check_book(data2);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }

    }

    public void check_book(String data1) {
        int bdata = book_table.getSelectedRow();
        String bid = book_table.getValueAt(bdata, 0).toString();

        if (bid.equals(data1)) {
            JOptionPane.showMessageDialog(null, "book allredy get ");
        } else {
            getbook(bid);
        }

    }

    public void getbook(String b_id) {
        String person_id;
        person_id = id_label.getText();

        try {
            String sql = "INSERT INTO `getbook`( `book_id`, `person_id`) VALUES ('" + b_id + "','" + person_id + "')";
            prt = con.prepareStatement(sql);
            prt.execute();
            JOptionPane.showMessageDialog(null, "Get Book succesfully");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void user_return_book_table() {
        String person_id = id_label.getText();

        try {
            String sql = "SELECT getbook_id as GetBook_ID , book_id as Book_ID, date as Date FROM getbook WHERE person_id LIKE '%" + person_id + "%'";
            prt = con.prepareStatement(sql);
            rs = (Resultset) prt.executeQuery();
            return_table.setModel(DbUtils.resultSetToTableModel((ResultSet) rs));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    public void return_book_table_select() {
        int pdata = return_table.getSelectedRow();

        return_table.getValueAt(pdata, 0).toString();
        String book_id = return_table.getValueAt(pdata, 1).toString();
        String person_id = id_label.getText();

        try {
            prt = con.prepareStatement("SELECT  book_name FROM bookstore WHERE book_id LIKE '%" + book_id + "%' ");
            ResultSet result = prt.executeQuery();
            while (result.next()) {
                String data2 = result.getString(1);
                select_book_name1.setText(data2);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }

    }

    public void return_book_serch() {
        String searchdata = search_box1.getText();

        try {
            String sql = "SELECT getbook_id as GetBook_ID , book_id as Book_ID, date as Date FROM getbook WHERE getbook_id LIKE '%" + searchdata + "%' or book_id LIKE '%" + searchdata + "%' ";
            prt = con.prepareStatement(sql);
            rs = (Resultset) prt.executeQuery();
            return_table.setModel(DbUtils.resultSetToTableModel((ResultSet) rs));
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void send_return_data() {
        int pdata = return_table.getSelectedRow();

        String r_id = return_table.getValueAt(pdata, 0).toString();
        String person_id = id_label.getText();

        try {
            String sql = "INSERT INTO hand_over (get_id, p_id) VALUES ('" + r_id + "','" + person_id + "')";
            prt = con.prepareStatement(sql);
            prt.execute();
            user_return_book_table();
            select_book_name1.setText("");
            //JOptionPane.showMessageDialog(null, "Insert succesfully");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void delete_get_data() {
        int check = JOptionPane.showConfirmDialog(null, "confirm hand over");
        int pdata = return_table.getSelectedRow();
        String id = return_table.getValueAt(pdata, 0).toString();
        String b_id = return_table.getValueAt(pdata, 1).toString();
        try {

            if (check == 0) {
                String sql = "DELETE FROM getbook WHERE getbook_id='" + id + "'";
                prt = con.prepareStatement(sql);
                prt.execute();
                //JOptionPane.showMessageDialog(null, "deleted");
                email_send_table_delete(b_id);
                send_return_data();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
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

        String person_idd = id_label.getText();
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
                    String countdate = String.valueOf(daybetweendate);
                    if (get_person.equals(person_idd)) {
                        r_label.setText("Plese return Book.. late date = " + countdate + "  book id = " + bookid);
                    }
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
        final String password = "*****";
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

        dashbord = new javax.swing.JPanel();
        d_pannel3 = new javax.swing.JPanel();
        profile = new javax.swing.JLabel();
        d_pannel1 = new javax.swing.JPanel();
        find_book = new javax.swing.JLabel();
        d_pannel2 = new javax.swing.JPanel();
        return_book = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        exit_pannel = new javax.swing.JPanel();
        profile2 = new javax.swing.JLabel();
        main_pannel = new javax.swing.JPanel();
        find_book_pannel = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        search_box = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        book_table = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        select_book_name = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        return_pannel = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        search_box1 = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        return_table = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        select_book_name1 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jButton4 = new javax.swing.JButton();
        profile_pannel = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        f_name = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        l_name = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        email = new javax.swing.JTextField();
        current_password = new javax.swing.JPasswordField();
        new_password = new javax.swing.JPasswordField();
        conform_password = new javax.swing.JPasswordField();
        update_btn = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        name_label = new javax.swing.JLabel();
        id_label = new javax.swing.JLabel();
        r_label = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(235, 236, 241));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        dashbord.setBackground(new java.awt.Color(52, 57, 87));
        dashbord.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dashbordMouseClicked(evt);
            }
        });
        dashbord.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        d_pannel3.setBackground(new java.awt.Color(52, 57, 87));
        d_pannel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                d_pannel3MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                d_pannel3MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                d_pannel3MouseExited(evt);
            }
        });

        profile.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        profile.setForeground(new java.awt.Color(255, 255, 255));
        profile.setText("Profile");
        profile.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                profileMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                profileMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                profileMouseExited(evt);
            }
        });

        javax.swing.GroupLayout d_pannel3Layout = new javax.swing.GroupLayout(d_pannel3);
        d_pannel3.setLayout(d_pannel3Layout);
        d_pannel3Layout.setHorizontalGroup(
            d_pannel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(d_pannel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(profile, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(56, Short.MAX_VALUE))
        );
        d_pannel3Layout.setVerticalGroup(
            d_pannel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(d_pannel3Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(profile)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        dashbord.add(d_pannel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 210, 130, 60));

        d_pannel1.setBackground(new java.awt.Color(52, 57, 87));
        d_pannel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                d_pannel1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                d_pannel1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                d_pannel1MouseExited(evt);
            }
        });

        find_book.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        find_book.setForeground(new java.awt.Color(255, 255, 255));
        find_book.setText("FIND BOOK");
        find_book.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                find_bookMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                find_bookMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                find_bookMouseExited(evt);
            }
        });

        javax.swing.GroupLayout d_pannel1Layout = new javax.swing.GroupLayout(d_pannel1);
        d_pannel1.setLayout(d_pannel1Layout);
        d_pannel1Layout.setHorizontalGroup(
            d_pannel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(d_pannel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(find_book)
                .addContainerGap(21, Short.MAX_VALUE))
        );
        d_pannel1Layout.setVerticalGroup(
            d_pannel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(d_pannel1Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(find_book)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        dashbord.add(d_pannel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 130, 60));

        d_pannel2.setBackground(new java.awt.Color(52, 57, 87));
        d_pannel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                d_pannel2MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                d_pannel2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                d_pannel2MouseExited(evt);
            }
        });

        return_book.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        return_book.setForeground(new java.awt.Color(255, 255, 255));
        return_book.setText("Return Book");
        return_book.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                return_bookMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                return_bookMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                return_bookMouseExited(evt);
            }
        });

        javax.swing.GroupLayout d_pannel2Layout = new javax.swing.GroupLayout(d_pannel2);
        d_pannel2.setLayout(d_pannel2Layout);
        d_pannel2Layout.setHorizontalGroup(
            d_pannel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(d_pannel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(return_book)
                .addContainerGap(11, Short.MAX_VALUE))
        );
        d_pannel2Layout.setVerticalGroup(
            d_pannel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(d_pannel2Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(return_book)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        dashbord.add(d_pannel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 150, 130, 60));

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(140, 160, 182));
        jLabel3.setText("  Book Club");
        dashbord.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 110, 40));

        jLabel4.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(140, 160, 182));
        jLabel4.setText("Welcome");
        dashbord.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, -1, -1));

        exit_pannel.setBackground(new java.awt.Color(52, 57, 87));
        exit_pannel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                exit_pannelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                exit_pannelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                exit_pannelMouseExited(evt);
            }
        });

        profile2.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        profile2.setForeground(new java.awt.Color(255, 255, 255));
        profile2.setText("Exit");
        profile2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                profile2MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                profile2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                profile2MouseExited(evt);
            }
        });

        javax.swing.GroupLayout exit_pannelLayout = new javax.swing.GroupLayout(exit_pannel);
        exit_pannel.setLayout(exit_pannelLayout);
        exit_pannelLayout.setHorizontalGroup(
            exit_pannelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(exit_pannelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(profile2, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(56, Short.MAX_VALUE))
        );
        exit_pannelLayout.setVerticalGroup(
            exit_pannelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(exit_pannelLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(profile2)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        dashbord.add(exit_pannel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 270, 130, 60));

        getContentPane().add(dashbord, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 571));

        main_pannel.setBackground(new java.awt.Color(235, 236, 241));
        main_pannel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        find_book_pannel.setBackground(new java.awt.Color(235, 236, 241));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jButton1.setBackground(new java.awt.Color(0, 123, 255));
        jButton1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Book Search");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        search_box.setBackground(new java.awt.Color(255, 255, 255));
        search_box.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        search_box.setForeground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(search_box, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(search_box, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(26, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("Ebrima", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Book Table");

        book_table.setBackground(new java.awt.Color(235, 236, 241));
        book_table.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        book_table.setForeground(new java.awt.Color(0, 0, 0));
        book_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3"
            }
        ));
        book_table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                book_tableMouseClicked(evt);
            }
        });
        book_table.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                book_tableKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(book_table);

        jLabel6.setBackground(new java.awt.Color(0, 0, 0));
        jLabel6.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("Selected Book Name");

        select_book_name.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(105, 105, 105)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addGap(41, 41, 41)
                                .addComponent(select_book_name, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(114, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(select_book_name))
                .addGap(31, 31, 31))
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jButton2.setBackground(new java.awt.Color(0, 123, 255));
        jButton2.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("SUMBIT");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(33, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jButton2)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout find_book_pannelLayout = new javax.swing.GroupLayout(find_book_pannel);
        find_book_pannel.setLayout(find_book_pannelLayout);
        find_book_pannelLayout.setHorizontalGroup(
            find_book_pannelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(find_book_pannelLayout.createSequentialGroup()
                .addGroup(find_book_pannelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(find_book_pannelLayout.createSequentialGroup()
                        .addGap(143, 143, 143)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(find_book_pannelLayout.createSequentialGroup()
                        .addGap(84, 84, 84)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(44, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, find_book_pannelLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(95, 95, 95))
        );
        find_book_pannelLayout.setVerticalGroup(
            find_book_pannelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(find_book_pannelLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(50, Short.MAX_VALUE))
        );

        main_pannel.add(find_book_pannel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -9, 800, 530));

        return_pannel.setBackground(new java.awt.Color(235, 236, 241));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        jButton3.setBackground(new java.awt.Color(0, 123, 255));
        jButton3.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("Book Search");

        search_box1.setBackground(new java.awt.Color(255, 255, 255));
        search_box1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        search_box1.setForeground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(search_box1, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3)
                    .addComponent(search_box1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(26, Short.MAX_VALUE))
        );

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        jLabel7.setFont(new java.awt.Font("Ebrima", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("Book return table");

        return_table.setBackground(new java.awt.Color(235, 236, 241));
        return_table.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        return_table.setForeground(new java.awt.Color(0, 0, 0));
        return_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3"
            }
        ));
        return_table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                return_tableMouseClicked(evt);
            }
        });
        return_table.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                return_tableKeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(return_table);

        jLabel8.setBackground(new java.awt.Color(0, 0, 0));
        jLabel8.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setText("Selected Book Name");

        select_book_name1.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(105, 105, 105)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addGap(41, 41, 41)
                                .addComponent(select_book_name1, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(114, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(select_book_name1))
                .addGap(31, 31, 31))
        );

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));

        jButton4.setBackground(new java.awt.Color(0, 123, 255));
        jButton4.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("SUBMIT");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(33, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jButton4)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout return_pannelLayout = new javax.swing.GroupLayout(return_pannel);
        return_pannel.setLayout(return_pannelLayout);
        return_pannelLayout.setHorizontalGroup(
            return_pannelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(return_pannelLayout.createSequentialGroup()
                .addGap(146, 146, 146)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(153, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, return_pannelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(93, 93, 93))
            .addGroup(return_pannelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(return_pannelLayout.createSequentialGroup()
                    .addGap(64, 64, 64)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(64, Short.MAX_VALUE)))
        );
        return_pannelLayout.setVerticalGroup(
            return_pannelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(return_pannelLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 330, Short.MAX_VALUE)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29))
            .addGroup(return_pannelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(return_pannelLayout.createSequentialGroup()
                    .addGap(128, 128, 128)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(139, Short.MAX_VALUE)))
        );

        main_pannel.add(return_pannel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -10, 800, 530));

        profile_pannel.setBackground(new java.awt.Color(235, 236, 241));

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setBackground(new java.awt.Color(0, 0, 0));
        jLabel1.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("First Name");

        f_name.setBackground(new java.awt.Color(255, 255, 255));
        f_name.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        f_name.setForeground(new java.awt.Color(0, 0, 0));

        jLabel9.setBackground(new java.awt.Color(0, 0, 0));
        jLabel9.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 0));
        jLabel9.setText("Last Name");

        l_name.setBackground(new java.awt.Color(255, 255, 255));
        l_name.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        l_name.setForeground(new java.awt.Color(0, 0, 0));

        jLabel10.setBackground(new java.awt.Color(0, 0, 0));
        jLabel10.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 0, 0));
        jLabel10.setText("Email");

        jLabel11.setBackground(new java.awt.Color(0, 0, 0));
        jLabel11.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 0, 0));
        jLabel11.setText("Current Password");

        jLabel12.setBackground(new java.awt.Color(0, 0, 0));
        jLabel12.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 0, 0));
        jLabel12.setText("Confrom Password");

        jLabel13.setBackground(new java.awt.Color(0, 0, 0));
        jLabel13.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 0, 0));
        jLabel13.setText("New Password");

        email.setBackground(new java.awt.Color(255, 255, 255));
        email.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        email.setForeground(new java.awt.Color(0, 0, 0));

        current_password.setBackground(new java.awt.Color(255, 255, 255));
        current_password.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        current_password.setForeground(new java.awt.Color(0, 0, 0));

        new_password.setBackground(new java.awt.Color(255, 255, 255));
        new_password.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        new_password.setForeground(new java.awt.Color(0, 0, 0));

        conform_password.setBackground(new java.awt.Color(255, 255, 255));
        conform_password.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        conform_password.setForeground(new java.awt.Color(0, 0, 0));

        update_btn.setBackground(new java.awt.Color(0, 123, 255));
        update_btn.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        update_btn.setForeground(new java.awt.Color(255, 255, 255));
        update_btn.setText("UPDATE");
        update_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                update_btnActionPerformed(evt);
            }
        });

        jLabel14.setIcon(new javax.swing.ImageIcon("E:\\java_test\\LMS\\src\\main\\java\\imsges\\user1.jpg")); // NOI18N

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(update_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel8Layout.createSequentialGroup()
                        .addGap(55, 55, 55)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(conform_password)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(email, javax.swing.GroupLayout.PREFERRED_SIZE, 331, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(new_password, javax.swing.GroupLayout.PREFERRED_SIZE, 331, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(current_password, javax.swing.GroupLayout.PREFERRED_SIZE, 331, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(l_name, javax.swing.GroupLayout.PREFERRED_SIZE, 331, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(f_name, javax.swing.GroupLayout.PREFERRED_SIZE, 331, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addGap(102, 102, 102))
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(261, 261, 261)
                .addComponent(jLabel14)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(f_name, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(l_name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(current_password, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(new_password, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(conform_password, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addComponent(update_btn)
                .addGap(30, 30, 30))
        );

        javax.swing.GroupLayout profile_pannelLayout = new javax.swing.GroupLayout(profile_pannel);
        profile_pannel.setLayout(profile_pannelLayout);
        profile_pannelLayout.setHorizontalGroup(
            profile_pannelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, profile_pannelLayout.createSequentialGroup()
                .addContainerGap(67, Short.MAX_VALUE)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(63, 63, 63))
        );
        profile_pannelLayout.setVerticalGroup(
            profile_pannelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, profile_pannelLayout.createSequentialGroup()
                .addContainerGap(62, Short.MAX_VALUE)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        main_pannel.add(profile_pannel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -10, 800, 530));

        getContentPane().add(main_pannel, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 40, 800, 530));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel15.setIcon(new javax.swing.ImageIcon("E:\\java_test\\LMS\\src\\main\\java\\imsges\\toolbar1.jpg")); // NOI18N
        jLabel15.setText("jLabel15");

        name_label.setBackground(new java.awt.Color(255, 255, 255));
        name_label.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        name_label.setForeground(new java.awt.Color(0, 0, 0));
        name_label.setText("sajeewa");
        name_label.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        id_label.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        id_label.setForeground(new java.awt.Color(0, 0, 0));
        id_label.setText("ID");
        id_label.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        r_label.setBackground(new java.awt.Color(255, 255, 255));
        r_label.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        r_label.setForeground(new java.awt.Color(255, 0, 51));
        r_label.setText("Have a nice day");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(r_label, javax.swing.GroupLayout.DEFAULT_SIZE, 579, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(name_label, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(id_label, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                .addComponent(id_label)
                .addComponent(name_label)
                .addComponent(r_label))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 0, 800, 40));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void dashbordMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dashbordMouseClicked
        find_book_pannel.setVisible(true);
        return_pannel.setVisible(false);
        profile_pannel.setVisible(false);
    }//GEN-LAST:event_dashbordMouseClicked

    private void return_bookMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_return_bookMouseClicked
        find_book_pannel.setVisible(false);
        return_pannel.setVisible(true);
        profile_pannel.setVisible(false);
        user_return_book_table();
    }//GEN-LAST:event_return_bookMouseClicked

    private void profileMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_profileMouseClicked
        find_book_pannel.setVisible(false);
        return_pannel.setVisible(false);
        profile_pannel.setVisible(true);
    }//GEN-LAST:event_profileMouseClicked

    private void d_pannel1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_d_pannel1MouseEntered
        setColor(d_pannel1);
    }//GEN-LAST:event_d_pannel1MouseEntered

    private void d_pannel1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_d_pannel1MouseExited
        resetColor(d_pannel1);
    }//GEN-LAST:event_d_pannel1MouseExited

    private void d_pannel2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_d_pannel2MouseEntered
        setColor(d_pannel2);
    }//GEN-LAST:event_d_pannel2MouseEntered

    private void d_pannel2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_d_pannel2MouseExited
        resetColor(d_pannel2);
    }//GEN-LAST:event_d_pannel2MouseExited

    private void d_pannel3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_d_pannel3MouseEntered
        setColor(d_pannel3);
    }//GEN-LAST:event_d_pannel3MouseEntered

    private void d_pannel3MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_d_pannel3MouseExited
        resetColor(d_pannel3);
    }//GEN-LAST:event_d_pannel3MouseExited

    private void d_pannel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_d_pannel3MouseClicked
        find_book_pannel.setVisible(false);
        return_pannel.setVisible(false);
        profile_pannel.setVisible(true);
    }//GEN-LAST:event_d_pannel3MouseClicked

    private void profileMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_profileMouseEntered
        setColor(d_pannel3);
    }//GEN-LAST:event_profileMouseEntered

    private void profileMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_profileMouseExited
        resetColor(d_pannel3);
    }//GEN-LAST:event_profileMouseExited

    private void return_bookMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_return_bookMouseEntered
        setColor(d_pannel2);
    }//GEN-LAST:event_return_bookMouseEntered

    private void return_bookMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_return_bookMouseExited
        resetColor(d_pannel2);
    }//GEN-LAST:event_return_bookMouseExited

    private void find_bookMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_find_bookMouseEntered
        setColor(d_pannel1);
    }//GEN-LAST:event_find_bookMouseEntered

    private void find_bookMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_find_bookMouseExited
        resetColor(d_pannel3);
    }//GEN-LAST:event_find_bookMouseExited

    private void find_bookMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_find_bookMouseClicked
        find_book_pannel.setVisible(true);
        return_pannel.setVisible(false);
        profile_pannel.setVisible(false);
    }//GEN-LAST:event_find_bookMouseClicked

    private void d_pannel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_d_pannel1MouseClicked
        find_book_pannel.setVisible(true);
        return_pannel.setVisible(false);
        profile_pannel.setVisible(false);
    }//GEN-LAST:event_d_pannel1MouseClicked

    private void d_pannel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_d_pannel2MouseClicked
        find_book_pannel.setVisible(false);
        return_pannel.setVisible(true);
        profile_pannel.setVisible(false);
        user_return_book_table();
    }//GEN-LAST:event_d_pannel2MouseClicked

    private void update_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_update_btnActionPerformed
        p_password_check();
    }//GEN-LAST:event_update_btnActionPerformed

    private void book_tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_book_tableMouseClicked
        book_select();
    }//GEN-LAST:event_book_tableMouseClicked

    private void book_tableKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_book_tableKeyReleased
        book_select();
    }//GEN-LAST:event_book_tableKeyReleased

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        book_serch();

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        String check = r_label.getText();
        if (check == "Have a nice day") {
            get_table_data();
        } else {
            JOptionPane.showMessageDialog(null, "Please return your book");
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void return_tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_return_tableMouseClicked
        return_book_table_select();
    }//GEN-LAST:event_return_tableMouseClicked

    private void return_tableKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_return_tableKeyReleased
        return_book_table_select();
    }//GEN-LAST:event_return_tableKeyReleased

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        delete_get_data();
        
    }//GEN-LAST:event_jButton4ActionPerformed

    private void profile2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_profile2MouseClicked
        System.exit(0);
    }//GEN-LAST:event_profile2MouseClicked

    private void profile2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_profile2MouseEntered
        setColor(exit_pannel);
    }//GEN-LAST:event_profile2MouseEntered

    private void profile2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_profile2MouseExited
        resetColor(exit_pannel);
    }//GEN-LAST:event_profile2MouseExited

    private void exit_pannelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exit_pannelMouseClicked
        System.exit(0);
    }//GEN-LAST:event_exit_pannelMouseClicked

    private void exit_pannelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exit_pannelMouseEntered
        setColor(exit_pannel);
    }//GEN-LAST:event_exit_pannelMouseEntered

    private void exit_pannelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exit_pannelMouseExited
        resetColor(exit_pannel);
    }//GEN-LAST:event_exit_pannelMouseExited

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
            java.util.logging.Logger.getLogger(user_acces.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(user_acces.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(user_acces.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(user_acces.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new user_acces().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable book_table;
    private javax.swing.JPasswordField conform_password;
    private javax.swing.JPasswordField current_password;
    private javax.swing.JPanel d_pannel1;
    private javax.swing.JPanel d_pannel2;
    private javax.swing.JPanel d_pannel3;
    private javax.swing.JPanel dashbord;
    private javax.swing.JTextField email;
    private javax.swing.JPanel exit_pannel;
    private javax.swing.JTextField f_name;
    private javax.swing.JLabel find_book;
    private javax.swing.JPanel find_book_pannel;
    private javax.swing.JLabel id_label;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField l_name;
    private javax.swing.JPanel main_pannel;
    public javax.swing.JLabel name_label;
    private javax.swing.JPasswordField new_password;
    private javax.swing.JLabel profile;
    private javax.swing.JLabel profile2;
    private javax.swing.JPanel profile_pannel;
    private javax.swing.JLabel r_label;
    private javax.swing.JLabel return_book;
    private javax.swing.JPanel return_pannel;
    private javax.swing.JTable return_table;
    private javax.swing.JTextField search_box;
    private javax.swing.JTextField search_box1;
    private javax.swing.JLabel select_book_name;
    private javax.swing.JLabel select_book_name1;
    private javax.swing.JButton update_btn;
    // End of variables declaration//GEN-END:variables

}
