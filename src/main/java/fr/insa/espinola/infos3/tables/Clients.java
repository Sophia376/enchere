/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.espinola.infos3.tables;

import fr.insa.espinola.infos3.utils.ConsoleFdB;
import fr.insa.espinola.infos3.utils.Lire;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Sophia
 */
public class Clients {

    private String nom;
    private String prenom;
    private String email;
    private String pass;
    private String codepostal;

    public Clients(String nom, String prenom, String email, String pass, String codepostal) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.pass = pass;
        this.codepostal = codepostal;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getEmail() {
        return email;
    }

    public String getPass() {
        return pass;
    }

    public String getCodepostal() {
        return codepostal;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public void setCodepostal(String codepostal) {
        this.codepostal = codepostal;
    }

    @Override
    public String toString() {
        return "Users{" + "nom=" + nom + ", prenom=" + prenom + ", email=" + email + ", pass=" + pass + ", codepostal=" + codepostal + '}';
    }
    
    public static void CreerTableClients(Connection con)
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
    
    public static void SupprimerTableClients(Connection con)
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
    
    public static class EmailExisteDejaException extends Exception {
    }
    
    public static void DemandeNouveauClient(Connection con) throws SQLException {
        boolean existe = true;
        while (existe) {
            System.out.println("--- creation nouvel utilisateur");
            String nom = ConsoleFdB.entreeString("Nom :");
            String prenom = ConsoleFdB.entreeString("Prenom :");
            String email = ConsoleFdB.entreeString("Email :");
            String code_postal = ConsoleFdB.entreeString("Code postal :");
            String pass = ConsoleFdB.entreeString("Pass :");
            try {
                CreerClient(con, nom, prenom, email, code_postal, pass);
                existe = false;
            } catch (EmailExisteDejaException ex) {
                System.out.println("cet email est déjà utilisé");
            }
        }
    }
    
    public static int CreerClient(Connection con, String nom, String prenom, String email, String code_postal, String pass)
            throws SQLException, EmailExisteDejaException {
        con.setAutoCommit(false);
        try ( PreparedStatement chercheEmail = con.prepareStatement(
                "select id from clients where email = ?")) {
            chercheEmail.setString(1, email);
            ResultSet testEmail = chercheEmail.executeQuery();
            if (testEmail.next()) {
                throw new EmailExisteDejaException();
            }
            try ( PreparedStatement pst = con.prepareStatement(
                    """
                insert into clients (nom,prenom,email,code_postal,pass) values (?,?,?,?,?)
                """, PreparedStatement.RETURN_GENERATED_KEYS)) {
                pst.setString(1, nom);
                pst.setString(2, prenom);
                pst.setString(3, email);
                pst.setString(4, code_postal);
                pst.setString(5, pass);
                pst.executeUpdate();
                con.commit();

                // je peux alors récupérer les clés créées comme un result set :
                try ( ResultSet rid = pst.getGeneratedKeys()) {
                    // et comme ici je suis sur qu'il y a une et une seule clé, je
                    // fait un simple next 
                    rid.next();
                    // puis je récupère la valeur de la clé créé qui est dans la
                    // première colonne du ResultSet
                    int id = rid.getInt(1);
                    return id;
                }
            }
        } catch (Exception ex) {
            con.rollback();
            throw ex;
        } finally {
            con.setAutoCommit(true);
        }
    }

    public static void AfficherClients(Connection con) throws SQLException {
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
