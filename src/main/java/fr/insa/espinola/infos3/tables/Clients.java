
package fr.insa.espinola.infos3.tables;

import fr.insa.espinola.infos3.utils.ConsoleFdB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

/**
 *
 * @author Sophia
 */
public class Clients {
    private int id;
    private String nom;
    private String prenom;
    private String email;
    private String pass;
    private String codepostal;

    public Clients(int id, String nom, String prenom, String email, String pass, String codepostal) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.pass = pass;
        this.codepostal = codepostal;
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
                        prenom varchar(50) not null,
                        email varchar(100) not null unique,
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
            String codepostal = ConsoleFdB.entreeString("Code postal :");
            String pass = ConsoleFdB.entreeString("Pass :");
            try {
                CreerClient(con, nom, prenom, email, codepostal, pass);
                existe = false;
            } catch (EmailExisteDejaException ex) {
                System.out.println("cet email est déjà utilisé");
            }
        }
    }
    
    public static int CreerClient(Connection con, String nom, String prenom, String email, String codepostal, String pass)
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
                insert into clients (nom,prenom,email,codepostal,pass) values (?,?,?,?,?)
                """, PreparedStatement.RETURN_GENERATED_KEYS)) {
                pst.setString(1, nom);
                pst.setString(2, prenom);
                pst.setString(3, email);
                pst.setString(4, codepostal);
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
            try ( ResultSet tlu = st.executeQuery("select id,nom,prenom,email, codepostal,pass from clients order by id")) {
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
    
    public static boolean idClientExiste(Connection con, int id) throws SQLException {
        try ( PreparedStatement pst = con.prepareStatement(
                "select id from clients where id = ?")) {
            pst.setInt(1, id);
            ResultSet res = pst.executeQuery();

            return res.next();
        }
    }
    
    public static boolean EmailClientExiste(Connection con, String email) throws SQLException {
        try ( PreparedStatement pst = con.prepareStatement(
                "select email from clients where email = ?")) {
            pst.setString(1, email);
            ResultSet res = pst.executeQuery();

            return res.next();
        }
    }
    
    public static int ChoisiClient(Connection con) throws SQLException{
        boolean ok = false;
        int id = -1;
        while(!ok){
            System.out.println("---------------choix d'un utilisateur");
            Clients.AfficherClients(con);
            id = ConsoleFdB.entreeEntier("donnez l'identificateur de l'utilisateur :");
            ok = idClientExiste(con, id);
            if (!ok) {
                System.out.println("id inexistant");
            }
        }
        return id;
    }
    
    public static int ConversionEmailClient(Connection con, String email) throws SQLException{
        int id = -1;
        try ( PreparedStatement pst = con.prepareStatement(
                "select id from Clients where email = ? ")) {
            pst.setString(1, email);
            ResultSet res = pst.executeQuery();
            while(res.next()){
                id = res.getInt("email");
            }
            
        }
        return id;
    }
    
    public static String ConversionEmailNPClient(Connection con, String email) throws SQLException{
        String nomprenom = "Personne";
        try ( PreparedStatement pst = con.prepareStatement(
                "select nom, prenom from Clients where email = ? ")) {
            pst.setString(1, email);
            ResultSet res = pst.executeQuery();
            while(res.next()){
                String nom = res.getString("nom");
                String prenom = res.getString("prenom");
                nomprenom = prenom + " " + nom;
            }
            
        }
        return nomprenom;
    }
    public static String ConversionIdClient(Connection con, int id) throws SQLException{
        String email = "Personne";
        try ( PreparedStatement pst = con.prepareStatement(
                "select email from Clients where id = ? ")) {
            pst.setInt(1, id);
            ResultSet res = pst.executeQuery();
            while(res.next()){
                email = res.getString("email");
            }
            
        }
        return email;
    }
    
    public static String ConversionNomPrenomClient(Connection con, int id) throws SQLException{
        String nomprenom = "Michel";
        try ( PreparedStatement pst = con.prepareStatement(
                "select nom, prenom from Clients where id = ? ")) {
            pst.setInt(1, id);
            ResultSet res = pst.executeQuery();
            while(res.next()){
                String nom = res.getString("nom");
                String prenom = res.getString("prenom");
                nomprenom = prenom + " " + nom;
            }
            
        }
        return nomprenom;
    }
    
    public static String DernierEncherisseur(Connection con, int idObjet) throws SQLException{
        String email = "toto@google.com";
        try ( PreparedStatement pst = con.prepareStatement(
                """
                select email 
                    from clients
                        join encheres on clients.id = encheres.de
                        join objets on encheres.sur = objets.id
                where(select prix from objets where objets.id = ?) = encheres.montant
                """)){
            pst.setInt(1, idObjet);
            ResultSet res = pst.executeQuery();
            while(res.next()){
                email = res.getString("email");
                // System.out.println(email);
            }
            
        }
        return email;
    }
    
    public static void BilanClient(Connection con) throws SQLException{
        int idClient = ChoisiClient(con);
        System.out.println("Bilan du Client " + ConversionIdClient(con, idClient));
        System.out.println("-----------------------");
        try ( PreparedStatement pst = con.prepareStatement(
                """
                select objets.id, titre, categorie,prixbase,debut,fin 
                    from Objets
                        join Encheres on encheres.sur = objets.id
                        join Clients on encheres.de = clients.id
                    where clients.id = ?
                
                """)) {
            pst.setInt(1, idClient);
            ResultSet res = pst.executeQuery();
            while(res.next()){
                int idObjet = res.getInt("id");
                String dernier = DernierEncherisseur(con , idObjet);
                String titre = res.getString("titre");
                String categorie = Categories.ConversionIdCategorie(con, res.getInt("categorie"));
                int prixbase = res.getInt("prixbase");
                Timestamp debut = res.getTimestamp("debut");
                Timestamp fin = res.getTimestamp("fin");
                if(dernier.equals(ConversionIdClient(con, idClient))){
                    System.out.println("Vous êtes le dernier à avoir enchéri sur :");
                    System.out.println("Titre :" + titre + ", Catégorie :" + categorie + ", Prix de base : " + prixbase + ", Début :" + debut + ", Fin :" + fin);
                }else{
                    System.out.println(dernier + " est le dernier à avoir enchéri sur :");
                    System.out.println("Titre :" + titre + ", Catégorie :" + categorie + ", Prix de base : " + prixbase + ", Début :" + debut + ", Fin :" + fin);
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
