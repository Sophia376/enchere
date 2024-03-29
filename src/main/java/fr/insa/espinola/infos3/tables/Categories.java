package fr.insa.espinola.infos3.tables;

import fr.insa.espinola.infos3.bdd.GestionbD;
import fr.insa.espinola.infos3.utils.ConsoleFdB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Sophia
 */
public class Categories {

    private String nom;
    private int id;

    public Categories(String nom, int id) {
        this.nom = nom;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Categories(String nom) {
        this.nom = nom;
    }

    @Override
    public String toString() {
        return id + " : " + nom;
    }

    public static void creerTableCategories(Connection con)
            throws SQLException {
        con.setAutoCommit(false);
        try ( Statement st = con.createStatement()) {
            st.executeUpdate(
                    """
                    create table Categories (
                        id integer not null primary key
                        generated always as identity,
                        nom varchar(30) not null unique
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

    public static void SupprimerTableCategories(Connection con)
            throws SQLException {
        con.setAutoCommit(false);
        try ( Statement st = con.createStatement()) { // elimination des tables
            st.executeUpdate(
                    """
                    drop table Categories 
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

    public static int CreerCategorie(Connection con, String nom)
            throws SQLException {
        con.setAutoCommit(false);

        try ( PreparedStatement pst = con.prepareStatement(
                """
            insert into categories (nom) values (?)
            """, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pst.setString(1, nom);
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

    public static void DemandeNouvelleCategorie(Connection con) throws SQLException {

        System.out.println("--- Création d'une nouvelle catégorie :");
        String nom = ConsoleFdB.entreeString("Nom :");

        CreerCategorie(con, nom);
    }

    public static void AfficherCategories(Connection con) throws SQLException {
        try ( Statement st = con.createStatement()) {
            try ( ResultSet tlu = st.executeQuery("select * from categories")) {
                System.out.println("liste des categories :");
                System.out.println("------------------------");
                while (tlu.next()) {
                    int id = tlu.getInt("id");
                    String nom = tlu.getString(2);
                    String mess = id + " NOM : " + nom;
                    System.out.println(mess);
                }
            }
        }
    }

    public static boolean idCategorieExiste(Connection con, int id) throws SQLException {
        try ( PreparedStatement pst = con.prepareStatement(
                "select id from categories where id = ?")) {
            pst.setInt(1, id);
            ResultSet res = pst.executeQuery();

            return res.next();
        }
    }

    public static List<Objets> ChoisirCategorie(Connection con, int id1) throws SQLException {

        List<Objets> res = new ArrayList<>();
        try ( PreparedStatement pst = con.prepareStatement(
                """
                select * 
                    from Objets
                        join Categories on Objets.categorie = Categories.id
                    where categories.id = ?
                
                """)) {
            pst.setInt(1, id1);
            try ( ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Timestamp fin = rs.getTimestamp("fin");
                    if(!GestionbD.EnchereTerminee(fin, Timestamp.valueOf(LocalDateTime.now()))){
                        res.add(new Objets(rs.getInt("id"),
                                rs.getString("titre"),
                                rs.getString("description"),
                                rs.getTimestamp("debut"),
                                fin,
                                rs.getInt("prixbase"),
                                rs.getInt("proposepar"),
                                rs.getInt("categorie"),
                                rs.getInt("prix"),
                                rs.getBytes("image")
                        ));
                    }
                }
                // System.out.println(res);
                return res;
            }

        }

    }

    public static int ChoisiCategorie(Connection con) throws SQLException {
        boolean ok = false;
        int id = -1;
        while (!ok) {
            System.out.println("----------- ----choix d'une categorie");
            AfficherCategories(con);
            id = ConsoleFdB.entreeEntier("donnez l'identificateur de la categorie :");
            ok = idCategorieExiste(con, id);
            if (!ok) {
                System.out.println("id inexistant");
            }
        }
        return id;
    }

    public static ArrayList<Categories> ToutesLesCategories(Connection con) throws SQLException {
        ArrayList<Categories> res = new ArrayList<>();
        try ( PreparedStatement pst = con.prepareStatement(
                "select * from categories order by id asc")) {

            try ( ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    res.add(new Categories(rs.getString("nom"),
                            rs.getInt("id")
                    ));
                }
                return res;
            }
        }
    }

    public static String ConversionIdCategorie(Connection con, int id) throws SQLException {
        String nom = "Meubles";
        try ( PreparedStatement pst = con.prepareStatement(
                "select nom from categories where id = ? ")) {
            pst.setInt(1, id);
            ResultSet res = pst.executeQuery();
            while (res.next()) {
                nom = res.getString("nom");
            }

        }
        return nom;
    }
    
    
        public static int ConversionIdCategorie2(Connection con, String nom) throws SQLException {
        int id = -1;
        try ( PreparedStatement pst = con.prepareStatement(
                "select id from categories where nom ILIKE '%?%' ")) {
            pst.setString(1, nom);
            ResultSet res = pst.executeQuery();
            while (res.next()) {
                id = res.getInt("id");
            }

        } System.out.println(id);
        return id;
    }
    
    
    
    public static int nbreCategories(Connection con) throws SQLException {
        int res = 0;
        try ( PreparedStatement pst = con.prepareStatement(
                "select * from categories order by id asc")) {

            try ( ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    res= res+1;                   
                }
                System.out.println(res);
                return res;
            }
        }
    }
    

    
    
    
    
    
    

    /*public static void searchCategory(Connection con) throws SQLException {                    // creo que me tendria que devolver todos los objetos con la categoria seleccionada
        try ( PreparedStatement pst = con.prepareStatement(
                """
                insert into Category (nom)
                values (?)
                    """)) {

            System.out.println("Nom");
            String nom = Lire.S();

            pst.setString(1, nom);
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
    }*/
}
