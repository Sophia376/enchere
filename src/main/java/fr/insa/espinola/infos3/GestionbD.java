/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.espinola.infos3;

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

    public static void creeUtilisateur(Connection con)
            throws SQLException {
        // je veux que le schema soit entierement cree ou pas du tout
        // je vais donc gerer explicitement une transaction
        con.setAutoCommit(false);
        try ( Statement st = con.createStatement()) {
            // creation des tables
            st.executeUpdate(
                    """
                    create table clients (
                        id integer not null primary key
                        generated always as identity,
                        nom varchar(30) not null,
                        prenom varchar(30) not null,
                        email varchar(30) not null unique,
                        codepostal varchar(30) not null,
                        pass varchar(30) not null
                    )
                    """);

            con.commit(); // je retourne dans le mode par defaut de gestion des transaction : chaque ordre au SGBD sera considere comme une transaction independante
            con.setAutoCommit(true);
        } catch (SQLException ex) { // quelque chose s'est mal passe j'annule la transaction
            con.rollback(); // puis je renvoie l'exeption pour qu'elle puisse eventuellement etre geree (message a  l'utilisateur...)
            throw ex;
        } finally { // je reviens a  la gestion par defaut : une transaction pour chaque ordre SQL
            con.setAutoCommit(true);
        }
    }

    public static void supprimeUtilisateur(Connection con)
            throws SQLException {
        con.setAutoCommit(false);
        try ( Statement st = con.createStatement()) { // elimination des tables
            st.executeUpdate(
                    """
                    drop table Clients 
                    """);

            con.commit();
            con.setAutoCommit(true);
        } catch (SQLException ex) {
            con.rollback();
            throw ex;
        } finally {
            con.setAutoCommit(true);
        }
    }

    public static void createUser(Connection con) throws SQLException {
        try ( PreparedStatement pst = con.prepareStatement(
                """
                insert into Clients (nom,prenom, email, codepostal,pass)
                values (?,?,?,?,?)
                    """)) {

            System.out.println("Nom");
            String nom = Lire.S();
            System.out.println("Prenom");
            String prenom = Lire.S();
            System.out.println("Email");
            String email = Lire.S();
            System.out.println("Code postal");
            String codepostal = Lire.S();
            System.out.println("Pass");
            String pass = Lire.S();

            pst.setString(1, nom);
            pst.setString(2, prenom);
            pst.setString(3, email);
            pst.setString(4, codepostal);
            pst.setString(5, pass);
            pst.executeUpdate();
            con.setAutoCommit(false);
            con.commit();
            con.setAutoCommit(true);
        }catch (SQLException ex) {
            con.rollback();
            throw ex;
        } finally {
            con.setAutoCommit(true);
        }
    }

    public static void main(String[] args) {
        try {
            Connection con = defautConnect();
            System.out.println("connection OK");
            supprimeUtilisateur(con);
            System.out.println("Table eliminee");
            creeUtilisateur(con);
            System.out.println("creation OK");
            createUser(con);
            System.out.println("creation2 OK");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GestionbD.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(GestionbD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
