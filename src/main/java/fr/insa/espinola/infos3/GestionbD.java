/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.espinola.infos3;

import static fr.insa.espinola.infos3.Users.afficheTousLesUtilisateur;
import static fr.insa.espinola.infos3.Users.createTableUser;
import static fr.insa.espinola.infos3.Users.createUser;
import static fr.insa.espinola.infos3.Users.deleteTableUser;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sespinolabenit01
 */
public class GestionbD {

    public static Connection connectGeneralPostGres(String host,
            int port, String database,
            String user, String pass)
            throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        Connection con = DriverManager.getConnection(
                "jdbc:postgresql://" + host + ":" + port
                + "/" + database,
                user, pass);
        con.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
        return con;
    }

    public static Connection defautConnect()
            throws ClassNotFoundException, SQLException {
        return connectGeneralPostGres("localhost", 5439, "postgres", "postgres", "pass");
    }

    public static void main(String[] args) {
        try {
            Connection con = defautConnect();
            afficheTousLesUtilisateur(con);
            System.out.println("avez vous un compte?   oui:1, non:0");
            int c= Lire.i();
            if (c==0){
               createUser(con); 
            }
            if (c==1){
                System.out.println("MAIL : ");
                String nom1= Lire.S();
                
                
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GestionbD.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(GestionbD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
