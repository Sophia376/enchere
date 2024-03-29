
package fr.insa.espinola.infos3.tables;

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
    
    public static Objets ConversionIdObjet(Connection con, int idObjet ) throws SQLException{
        int id = -1;
        String titre = "";
        String description= "";
        Timestamp debut = Timestamp.valueOf(LocalDateTime.MIN);;
        Timestamp fin = Timestamp.valueOf(LocalDateTime.MIN);
        int prixbase = -1;
        int proposepar = -1;
        int categorie = -1;
        int prix = -1;
        byte[] image = null;
        try ( PreparedStatement pst = con.prepareStatement(
                "select * from Objets where id = ? ")) {
            
            pst.setInt(1, idObjet);
            ResultSet res = pst.executeQuery();
            while (res.next()) {
                id = res.getInt("id");
                titre = res.getString("titre");
                description = res.getString("description");
                debut = res.getTimestamp("debut");
                fin = res.getTimestamp("fin");
                prixbase = res.getInt("prixbase");
                proposepar = res.getInt("proposepar");
                categorie = res.getInt("categorie");
                prix = res.getInt("prix");
                image = res.getBytes("image");
           
            }

        }
        return new Objets(id,titre,description,debut,fin,prixbase,proposepar,categorie,prix, image );
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
                        de integer not null
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
    
    public static void CreerEnchere(Connection con, Timestamp quand, int montant, int sur, int de) throws SQLException {
        try ( PreparedStatement pst = con.prepareStatement(
                """
                insert into Encheres (quand, montant, sur, de )
                values (?,?,?,?)
                    """ , PreparedStatement.RETURN_GENERATED_KEYS)) {
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
    
    public static void AjouterEnchere(Connection con) throws SQLException {

        System.out.println("--- ajout d'une enchère");
        int idUtilisateur = Clients.ChoisiClient(con);
        int idObjet = Objets.ChoisiObjet(con);
        int prix = 0;
        try ( PreparedStatement pst = con.prepareStatement("select prix from objets where id = ?")) {
            
            pst.setInt(1, idObjet);
            ResultSet res = pst.executeQuery();
            while(res.next()){
                prix = res.getInt("prix");
            }
            
        }
        boolean ok = false;
        while(!ok){
            System.out.println("Le prix actuel de l'objet est à "+prix);
            int montantSaisi = ConsoleFdB.entreeInt("Quel montant voulez-vous mettre sur cette enchère ( -1 pour annuler )");
            if(montantSaisi == -1){
                System.out.println("annulation");
                ok = true;
            }   
            else if(montantSaisi > prix){
                ok = true;
                CreerEnchere(con, Timestamp.valueOf(LocalDateTime.now()),montantSaisi, idObjet, idUtilisateur);
                con.setAutoCommit(false);
                try ( PreparedStatement pst1 = con.prepareStatement("update objets set prix = ? where id = ?")) {
                    pst1.setInt(1, montantSaisi);
                    pst1.setInt(2, idObjet);
                    pst1.executeUpdate();
            
            
            
                    con.commit(); // je retourne dans le mode par defaut de gestion des transaction : chaque ordre au SGBD sera considere comme une transaction independante
                    con.setAutoCommit(true);
                } catch (SQLException ex) { // quelque chose s'est mal passe j'annule la transaction
                    con.rollback(); // puis je renvoie l'exeption pour qu'elle puisse eventuellement etre geree (message a  l'utilisateur...)
                    throw ex;
                } finally { // je reviens a  la gestion par defaut : une transaction pour chaque ordre SQL
                    con.setAutoCommit(true);
                }
            }else{
                System.out.println("Le montant saisi n'est pas assez élevé");
            }   
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
                    String mess = id + " QUAND : " + quand + " MONTANT: " + montant + " SUR:  " + Objets.ConversionIdObjet(con, sur) + " DE " + Clients.ConversionIdClient(con, de);
                    System.out.println(mess);
                }
            }
        }
    }
    
    public static List<Encheres> encheresUtilisateur(Connection con, int idUtilisateur) throws SQLException {

        List<Encheres> res = new ArrayList<>();
        /*  System.out.println("Objets publiés par le Client " + ConversionIdClient(con, id1));
        System.out.println("-----------------------");
         */
        try ( PreparedStatement pst = con.prepareStatement(
                """
                select * 
                    from Encheres
                        join Clients on clients.id = encheres.de
                    where clients.id = ?
                
                """)) {
            pst.setInt(1, idUtilisateur);
            try ( ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    res.add(new Encheres(rs.getInt("id"),
                            rs.getTimestamp("quand"),
                            rs.getInt("montant"),
                            rs.getInt("sur"),
                            rs.getInt("de")
                    ));
                }
                return res;
            }

        }
    }

    @Override
    public String toString() {
        return "Encheres{" + "id=" + id + ", quand=" + quand + ", montant=" + montant + ", sur=" + sur + ", de=" + de + '}';
    }
    

        
    public static Encheres DerniereEnchere(Connection con, int idObjet) throws SQLException {

        int id = -1;
        Timestamp quand = Timestamp.valueOf(LocalDateTime.MIN);
        int montant = -1;
        int sur = -1;
        int de = -1;
        try ( PreparedStatement pst = con.prepareStatement(
                """
                select * 
                    from Encheres
                        join Objets on objets.id = encheres.sur
                    where (montant = prix and objets.id = ?) 
                
                """)) {
            pst.setInt(1, idObjet);
            try ( ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    id = rs.getInt("id");
                    quand = rs.getTimestamp("quand");
                    montant = rs.getInt("montant");
                    sur = rs.getInt("sur");
                    de = rs.getInt("de");
                }
                
            }
            

        }
        return new Encheres(id, quand, montant, sur, de);
    }
    
    
    
    
    
    
}
