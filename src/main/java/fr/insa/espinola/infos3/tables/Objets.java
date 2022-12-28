/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.espinola.infos3.tables;

import fr.insa.espinola.infos3.utils.ConsoleFdB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;

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
    private int categorie;
    private int prix;

    public Objets(int id, String titre, String description, Timestamp debut, Timestamp fin, int prixbase, int proposer, int categorie, int prix) {
        this.id = id;
        this.titre = titre;
        this.description = description;
        this.debut = debut;
        this.fin = fin;
        this.prixbase = prixbase;
        this.proposer = proposer;
        this.categorie = categorie;
        this.prix = prix;
    }

    public int getCategorie() {
        return categorie;
    }

    public void setCategorie(int categorie) {
        this.categorie = categorie;
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
        return "Objets{" + "id=" + id + ", titre=" + titre + ", description=" + description + ", debut=" + debut + ", fin=" + fin + ", prixbase=" + prixbase + ", proposer=" + proposer + ", categorie=" + categorie + '}';
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
                    create table Objets (
                        id integer not null primary key
                        generated always as identity,
                        titre varchar(100) not null,
                        description varchar(200) not null,
                        prixbase integer not null,
                        categorie integer not null,
                        proposepar integer not null,
                        prix integer not null,
                        debut timestamp without time zone not null,
                        fin timestamp without time zone not null
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
                    drop table Objets 
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
    
    public static void DemandeNouvelObjet(Connection con) throws SQLException {

        System.out.println("--- creation nouvel objet");
        String titre = ConsoleFdB.entreeString("Titre :");
        String description = ConsoleFdB.entreeString("Description :");
        Timestamp debut = Timestamp.valueOf(LocalDateTime.now());
        Timestamp fin = Timestamp.valueOf(ConsoleFdB.entreeString("Fin (aaaa-mm-jj hh:mm:ss) : "));
        int prixbase = ConsoleFdB.entreeInt("Prix de base :");
        int categorie = ConsoleFdB.entreeInt("Categorie :");
        int proposepar = ConsoleFdB.entreeInt("Propose par :");
        int prix = prixbase;

        CreerObjet(con, titre, description, debut, fin, prixbase, proposepar, categorie, prix);
    }

    public static int CreerObjet(Connection con, String titre, String description, Timestamp debut, Timestamp fin, int prixbase, int proposepar, int categorie, int prix)
            throws SQLException {
        con.setAutoCommit(false);
        
        try ( PreparedStatement pst = con.prepareStatement(
                """
            insert into objets (titre,description,debut,fin,prixbase,proposepar,categorie, prix) values (?,?,?,?,?,?,?,?)
            """, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pst.setString(1, titre);
            pst.setString(2, description);
            pst.setTimestamp(3, debut);
            pst.setTimestamp(4, fin);
            pst.setInt(5, prixbase);
            pst.setInt(6, proposepar);
            pst.setInt(7, categorie);
            pst.setInt(8, prix);
            pst.executeUpdate();
            con.commit();
            try ( ResultSet rid = pst.getGeneratedKeys()) {
                rid.next();
                int id = rid.getInt(1);
                return id;
            }
        } catch (Exception ex) {
            con.rollback();
            throw ex;
        } finally {
            con.setAutoCommit(true);
        }
    }

    public static void AfficherObjets(Connection con) throws SQLException {
        try ( Statement st = con.createStatement()) {
            try ( ResultSet tlu = st.executeQuery("select id,titre,description,categorie, debut,fin,prixbase,prix from objets")) {
                System.out.println("liste des objets :");
                System.out.println("------------------------");
                while (tlu.next()) {
                    int id = tlu.getInt("id");
                    String titre = tlu.getString(2);
                    String description = tlu.getString(3);
                    Integer categorie = tlu.getInt(4);
                    Timestamp debut = tlu.getTimestamp(5);
                    Timestamp fin = tlu.getTimestamp(6);
                    Integer prixbase = tlu.getInt(7);
                    Integer prix = tlu.getInt(8);
                    String mess = id + " TITRE : " + titre + " DESCRIPTION: " + description + " CATEGORIE:  " + categorie + " CODE DEBUT: " + debut + " FIN: " + fin + " PRIXBASE: " + prixbase + " PRIX: " + prix;
                    System.out.println(mess);
                }
            }
        }
    }
    
    public static boolean idObjetExiste(Connection con, int id) throws SQLException {
        try ( PreparedStatement pst = con.prepareStatement(
                "select id from objets where id = ?")) {
            pst.setInt(1, id);
            ResultSet res = pst.executeQuery();

            return res.next();
        }
    }
    
    public static int ChoisiObjet(Connection con) throws SQLException{
        boolean ok = false;
        int id = -1;
        while(!ok){
            System.out.println("---------------choix d'un objet");
            Objets.AfficherObjets(con);
            id = ConsoleFdB.entreeEntier("donnez l'identificateur de l'objet :");
            ok = idObjetExiste(con, id);
            if (!ok) {
                System.out.println("id inexistant");
            }
        }
        return id;
    }

}
