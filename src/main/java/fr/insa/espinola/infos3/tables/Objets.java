package fr.insa.espinola.infos3.tables;

import fr.insa.espinola.infos3.bdd.GestionbD;
import fr.insa.espinola.infos3.utils.ConsoleFdB;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;

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
    private byte[] image;

    public Objets(int id, String titre, String description, Timestamp debut, Timestamp fin, int prixbase, int proposer, int categorie, int prix, byte[] image) {
        this.id = id;
        this.titre = titre;
        this.description = description;
        this.debut = debut;
        this.fin = fin;
        this.prixbase = prixbase;
        this.proposer = proposer;
        this.categorie = categorie;
        this.prix = prix;
        this.image = image;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
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

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
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
    
    public static void SupprimerObjets(Connection con, int id1)
            throws SQLException {
        con.setAutoCommit(false);
        try ( PreparedStatement pst = con.prepareStatement("""
                    delete from Objets 
                    where id= ?;
                    """)) {
            pst.setInt(1, id1);
            pst.executeUpdate();
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
        byte[] image = null;

        CreerObjet(con, titre, description, debut, fin, prixbase, proposepar, categorie, prix, image);
    }

    public static int CreerObjet(Connection con, String titre, String description, Timestamp debut, Timestamp fin, int prixbase, int proposepar, int categorie, int prix, byte[] image)
            throws SQLException {
        con.setAutoCommit(false);

        try ( PreparedStatement pst = con.prepareStatement(
                """
            insert into objets (titre,description,debut,fin,prixbase,proposepar,categorie, prix, image) values (?,?,?,?,?,?,?,?,?)
            """, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pst.setString(1, titre);
            pst.setString(2, description);
            pst.setTimestamp(3, debut);
            pst.setTimestamp(4, fin);
            pst.setInt(5, prixbase);
            pst.setInt(6, proposepar);
            pst.setInt(7, categorie);
            pst.setInt(8, prix);
            pst.setBytes(9, image);
            
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
            try ( ResultSet tlu = st.executeQuery("select id,titre,description,categorie, debut,fin,proposepar,prixbase,prix from objets order by id")) {
                System.out.println("liste des objets :");
                System.out.println("------------------------");
                while (tlu.next()) {
                    int id = tlu.getInt("id");
                    String titre = tlu.getString(2);
                    String description = tlu.getString(3);
                    Integer categorie = tlu.getInt(4);
                    Timestamp debut = tlu.getTimestamp(5);
                    Timestamp fin = tlu.getTimestamp(6);
                    Integer proposepar = tlu.getInt(7);
                    Integer prixbase = tlu.getInt(8);
                    Integer prix = tlu.getInt(9);
                    String mess = id + " TITRE : " + titre + " DESCRIPTION: " + description + " CATEGORIE:  " + Categories.ConversionIdCategorie(con, categorie) + " CODE DEBUT: " + debut + " FIN: " + fin + " PRIXBASE: " + prixbase + "PROPOSE PAR :" + Clients.ConversionIdClient(con, proposepar) + " PRIX: " + prix;
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

    public static int ChoisiObjet(Connection con) throws SQLException {
        boolean ok = false;
        int id = -1;
        while (!ok) {
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

    public static String ConversionIdObjet(Connection con, int id) throws SQLException {
        String titre = "PS5";
        try ( PreparedStatement pst = con.prepareStatement(
                "select titre from Objets where id = ? ")) {
            pst.setInt(1, id);
            ResultSet res = pst.executeQuery();
            while (res.next()) {
                titre = res.getString("titre");
            }

        }
        return titre;
    }

    public static List<Objets> tousLesObjets(Connection con) throws SQLException {
        List<Objets> res = new ArrayList<>();
        try ( PreparedStatement pst = con.prepareStatement(
                "select * from objets order by id asc")) {

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
                return res;
            }
        }
    }

    public void NouveauPrix(Connection con, int idObjet, int montant) throws SQLException {
        try ( Statement st = con.createStatement()) {
            st.executeQuery("update Objets set prix = " + montant + " where id = " + idObjet);
        }
    }

    public static List<Objets> objetsUtilisateur(Connection con, int id1) throws SQLException {

        List<Objets> res = new ArrayList<>();
        /*  System.out.println("Objets publiés par le Client " + ConversionIdClient(con, id1));
        System.out.println("-----------------------");
         */
        try ( PreparedStatement pst = con.prepareStatement(
                """
                select * 
                    from Objets
                        join Clients on Objets.proposepar = clients.id
                    where clients.id = ?
                
                """)) {
            pst.setInt(1, id1);
            try ( ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    res.add(new Objets(rs.getInt("id"),
                            rs.getString("titre"),
                            rs.getString("description"),
                            rs.getTimestamp("debut"),
                            rs.getTimestamp("fin"),
                            rs.getInt("prixbase"),
                            rs.getInt("proposepar"),
                            rs.getInt("categorie"),
                            rs.getInt("prix"),
                            rs.getBytes("image")
                    ));
                }
                //System.out.println(res);
                return res;
            }

        }
    }
    
    public static byte[] InsererImage(Connection con) throws SQLException, FileNotFoundException, IOException {
        JFileChooser fileChooser = new JFileChooser();
        byte[] imageBytes = null;
        int returnValue = fileChooser.showOpenDialog(null);
        if(imageBytes != null){
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                imageBytes = new byte[(int) selectedFile.length()];
                FileInputStream fis = new FileInputStream(selectedFile);
                fis.read(imageBytes);
                fis.close();

            }
        }
        return imageBytes;
    }


}
