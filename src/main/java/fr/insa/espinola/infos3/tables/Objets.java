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
public class Objets {
    
    private int id;
    private String titre;
    private String description;
    private Timestamp debut;
    private Timestamp fin;
    private int prixbase;
    private int proposer;

    public Objets(int id, String titre, String description, Timestamp debut, Timestamp fin, int prixbase, int proposer) {
        this.id = id;
        this.titre = titre;
        this.description = description;
        this.debut = debut;
        this.fin = fin;
        this.prixbase = prixbase;
        this.proposer = proposer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getDebut() {
        return debut;
    }

    public void setDebut(Timestamp debut) {
        this.debut = debut;
    }

    public Timestamp getFin() {
        return fin;
    }

    public void setFin(Timestamp fin) {
        this.fin = fin;
    }

    public int getPrixbase() {
        return prixbase;
    }

    public void setPrixbase(int prixbase) {
        this.prixbase = prixbase;
    }

    public int getProposer() {
        return proposer;
    }

    public void setProposer(int proposer) {
        this.proposer = proposer;
    }

    @Override
    public String toString() {
        return "Objects{" + "id=" + id + ", titre=" + titre + ", description=" + description + ", debut=" + debut + ", fin=" + fin + ", prixbase=" + prixbase + ", proposer=" + proposer + '}';
    }
    
    public static void CreerTableObjets(Connection con)
            throws SQLException {
        // je veux que le schema soit entierement cree ou pas du tout
        // je vais donc gerer explicitement une transaction
        con.setAutoCommit(false);
        try ( Statement st = con.createStatement()) {
            // creation des tables
            st.executeUpdate(
                    """
                    create table Object (
                        id integer not null primary key
                        generated always as identity,
                        titre varchar(100) not null,
                        description varchar(200) not null,
                        prixbase integer not null,
                        categorie integer not null,
                        proposepar integer not null,
                        debut timestamp without time zone not null,
                        fin timestamp without time zone not null,
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

    public static void SupprimerTableObjets(Connection con)
            throws SQLException {
        con.setAutoCommit(false);
        try ( Statement st = con.createStatement()) {
            st.executeUpdate(
                    """
                    drop table Object 
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

    public static void CreerObjet(Connection con) throws SQLException {
        try ( PreparedStatement pst = con.prepareStatement(
                """
                insert into Object (titre,description,categorie, debut, fin,prixbase)
                values (?,?,?,?,?,?)
                    """)) {

            System.out.println("titre");
            String titre = Lire.S();
            System.out.println("description");
            String description = Lire.S();
            System.out.println("categorie");
            Integer categorie = Lire.i();
            System.out.println("debut");
            Timestamp debut = Timestamp.from(Instant.now());
            System.out.println("fin");  /////////////////////////////////////////////////////////////////////////////// lo tengo que corregir
            String fin = Lire.S();
            System.out.println("prixbase");
            Integer prixbase = Lire.i();

            pst.setString(1, titre);
            pst.setString(2, description);
            pst.setInt(3, categorie);
            pst.setTimestamp(4, debut);
            pst.setString(5, fin);
            pst.setInt(6, prixbase);
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

    public static void AfficherObjets(Connection con) throws SQLException {
        try ( Statement st = con.createStatement()) {
            try ( ResultSet tlu = st.executeQuery("select id,titre,description,categorie, debut,fin,prixbas from clients")) {
                System.out.println("liste des utilisateurs :");
                System.out.println("------------------------");
                while (tlu.next()) {
                    int id = tlu.getInt("id");
                    String titre = tlu.getString(2);
                    String description = tlu.getString(3);
                    Integer categorie = tlu.getInt(4);
                    Timestamp debut = tlu.getTimestamp(5);
                    Timestamp fin = tlu.getTimestamp("pass");
                    Integer prixbase = tlu.getInt(7);
                    String mess = id + " TITRE : " + titre + " DESCRIPTION: " + description + " CATEGORIE:  " + categorie + " CODE DEBUT: " + debut + " FIN: " + fin + " PRIXBASE: " + prixbase;
                    System.out.println(mess);
                }
            }
        }
    }

}
