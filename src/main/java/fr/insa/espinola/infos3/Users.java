/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.espinola.infos3;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Sophia
 */
public class Users {

    private String nom;
    private String prenom;
    private String email;
    private String pass;
    private String codepostal;

    public Users(String nom, String prenom, String email, String pass, String codepostal) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.pass = pass;
        this.codepostal = codepostal;
    }

    public static void createTableUser(Connection con)
            throws SQLException {
        con.setAutoCommit(false);
        try ( Statement st = con.createStatement()) {
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

    public static void deleteTableUser(Connection con)
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
        } catch (SQLException ex) {
            con.rollback();
            throw ex;
        } finally {
            con.setAutoCommit(true);
        }
    }

    /*public static void deleteUser(Connection con)
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
    }*/

    public static void ShowUsers(Connection con) throws SQLException {
        try ( Statement st = con.createStatement()) {
            try ( ResultSet tlu = st.executeQuery("select id,nom,prenom,email, codepostal,pass from clients")) {
                System.out.println("liste des utilisateurs :");
                System.out.println("------------------------");
                while (tlu.next()) {
                    int id = tlu.getInt("id");
                    String nom = tlu.getString(2);
                    String prenom = tlu.getString(3);
                    String email = tlu.getString(4);
                    String codepostal = tlu.getString(5);
                    String pass = tlu.getString("pass");
                    String mess = id + " NOM : " + nom + " PRENOM: " + prenom + " EMAIL:  " + email + " CODE POSTAL: " + codepostal + " PASS: " + pass;
                    System.out.println(mess);
                }
            }
        }
    }

    public static boolean VerifyConnection(Connection con, String pass1, String mail1) throws SQLException {
        try ( PreparedStatement pst = con.prepareStatement(
                "select pass from Clients where Email = ?  and pass=? ")) {
            pst.setString(1, mail1);
            pst.setString(2, pass1);

            ResultSet res = pst.executeQuery();

            return res.next();
        }
    }

}
