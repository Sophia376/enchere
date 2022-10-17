/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.espinola.infos3;

import java.sql.Connection;
import java.sql.DriverManager;
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
    
    public static void creeSchema(Connection con)
            throws SQLException {
        // je veux que le schema soit entierement crÃ©Ã© ou pas du tout
        // je vais donc gÃ©rer explicitement une transaction
        con.setAutoCommit(false);
        try ( Statement st = con.createStatement()) {
            // creation des tables
            st.executeUpdate(
                    """
                    create table Clients (
                        id integer not null primary key
                        generated always as identity,
                        nom varchar(30) not null unique,
                        pass varchar(30) not null
                    )
                    """);
            
            con.commit();
            // je retourne dans le mode par dÃ©faut de gestion des transaction :
            // chaque ordre au SGBD sera considÃ©rÃ© comme une transaction indÃ©pendante
            con.setAutoCommit(true);
        } catch (SQLException ex) {
            // quelque chose s'est mal passÃ©
            // j'annule la transaction
            con.rollback();
            // puis je renvoie l'exeption pour qu'elle puisse Ã©ventuellement etre geree (message Ã  l'utilisateur...)
            throw ex;
        } finally {
            // je reviens Ã  la gestion par dÃ©faut : une transaction pour chaque ordre SQL
            con.setAutoCommit(true);
        }
        
        
    }
    
    
    public static void main(String[] args) {
        try {
            Connection con = defautConnect();
            System.out.println("connection OK");
            creeSchema(con);
            System.out.println("creation OK");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GestionbD.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(GestionbD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
// salut
