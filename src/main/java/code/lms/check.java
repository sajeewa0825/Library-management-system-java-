/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package code.lms;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sajeewa
 */
public class check {
        Connection con = DBconnect.connect();
        PreparedStatement pr;

    public check() {
            try {
                this.pr = con.prepareStatement("SELECT * FROM bookstore");
                ResultSet result = pr.executeQuery();
                while(result.next()) {
                    System.out.println("id: "+result.getString(0));
                }
            } catch (SQLException ex) {
                Logger.getLogger(check.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
}
