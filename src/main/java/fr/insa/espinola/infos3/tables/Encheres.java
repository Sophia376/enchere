/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.espinola.infos3.tables;

import fr.insa.espinola.infos3.utils.Lire;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.Instant;

/**
 *
 * @author Sophia
 */
public class Encheres {
    private int id;
    private Timestamp quand;
    private int montant;
    private int sur;
    private int de;

    public Encheres(int id, Timestamp quand, int montant, int sur, int de) {
        this.id = id;
        this.quand = quand;
        this.montant = montant;
        this.sur = sur;
        this.de = de;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getQuand() {
        return quand;
    }

    public void setQuand(Timestamp quand) {
        this.quand = quand;
    }

    public int getMontant() {
        return montant;
    }

    public void setMontant(int montant) {
        this.montant = montant;
    }

    public int getSur() {
        return sur;
    }

    public void setSur(int sur) {
        this.sur = sur;
    }

    public int getDe() {
        return de;
    }

    public void setDe(int de) {
        this.de = de;
    }
    
    public static void creerTableEncheres(Connection con)
            throws SQLException {
        con.setAutoCommit(false);
        try ( Statement st = con.createStatement()) {
            st.executeUpdate(
                    """
                    create table Encheres (
                        id integer not null primary key
                        generated always as identity,
                        quand timestamp without time zone not null,
                        montant integer not null,
                        sur integer not null,
                        de integer not null,
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
    
    public static void SupprimerTableEncheres(Connection con)
            throws SQLException {
        con.setAutoCommit(false);
        try ( Statement st = con.createStatement()) { // elimination des tables
            st.executeUpdate(
                    """
                    drop table Encheres 
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
    
    public static void CreerEnchere(Connection con) throws SQLException {
        try ( PreparedStatement pst = con.prepareStatement(
                """
                insert into Encheres (quand, montant, sur, de )
                values (?,?,?,?)
                    """)) {

            System.out.println("Quand");
            Timestamp quand = Timestamp.from(Instant.now());
            System.out.println("Montant");
            int montant = Lire.i();
            System.out.println("sur");
            int sur = Lire.i();
            System.out.println("de");
            int de = Lire.i();
                       
            pst.setTimestamp(1, quand);
            pst.setInt(2, montant);
            pst.setInt(3, sur);
            pst.setInt(4, de);
                      
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
    
    public static void AfficherEncheres(Connection con) throws SQLException {
        try ( Statement st = con.createStatement()) {
            try ( ResultSet tlu = st.executeQuery("select id,quand,montant,sur, de from encheres")) {
                System.out.println("liste des encheres :");
                System.out.println("------------------------");
                while (tlu.next()) {
                    int id = tlu.getInt("id");
                    Timestamp quand = tlu.getTimestamp(2);
                    int montant = tlu.getInt(3);
                    int sur = tlu.getInt(4);
                    int de = tlu.getInt(5);
                    String mess = id + " Quadn : " + quand + " MONTANT: " + montant + " SUR:  " + sur + " DE " + de;
                    System.out.println(mess);
                }
            }
        }
    }
}
