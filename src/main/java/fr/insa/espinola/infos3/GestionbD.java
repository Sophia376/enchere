/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.espinola.infos3;

import static fr.insa.espinola.infos3.Users.createUser;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import static fr.insa.espinola.infos3.Users.ShowUsers;
import static fr.insa.espinola.infos3.Users.VerifyConnection;

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
            ShowUsers(con);
            System.out.println("avez vous un compte?   oui:1, non:0");
            int c = Lire.i();
            if (c == 0) {
                createUser(con);
            }
            if (c == 1) {
                System.out.println("MAIL : ");
                String mail1 = Lire.S();
                System.out.println("PASS : ");
                String pass1 = Lire.S();

                if (VerifyConnection(con, pass1, mail1) == true) {
                    System.out.println("Bien");
                } else {
                    System.out.println("Le pass ou le mail est incorrect");
                }

            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GestionbD.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(GestionbD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
