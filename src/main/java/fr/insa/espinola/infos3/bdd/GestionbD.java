/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.espinola.infos3.bdd;



import fr.insa.espinola.infos3.tables.Categories;
import fr.insa.espinola.infos3.tables.Clients;
import fr.insa.espinola.infos3.tables.Encheres;
import fr.insa.espinola.infos3.tables.Objets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sespinolabenit01
 */
public class GestionbD {
    
    public static class SGBD {

        private String name;
        private String autoGenerateKeys;
        private String defineDefautCharsetToUTF8;

        public SGBD(String name,String autoGenerateKeys, String defineDefautCharsetToUTF8) {
            this.name = name;
            this.autoGenerateKeys = autoGenerateKeys;
            this.defineDefautCharsetToUTF8 = defineDefautCharsetToUTF8;
        }
        
        public SGBD(String name,String autoGenerateKeys){
            this(name,autoGenerateKeys,null);
        }

        @Override
        public String toString() {
            return "SGBD{" + name + '}';
        }

        
        private String getSyntaxForAutogeneratedKeys() {
            return this.autoGenerateKeys;
        }

        private Optional<String> sqlOrderChangeCharsetToUTF8InCurrentDatabase() {
            if (this.defineDefautCharsetToUTF8 != null) {
                return Optional.of(this.defineDefautCharsetToUTF8);
            } else {
                return Optional.empty();
            }
        }
    }

    public static final SGBD MySQLSGBD = new SGBD("MySQL","AUTO_INCREMENT",
            "ALTER DATABASE CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci");
    public static final SGBD PostgresqlSGBD = new SGBD("PostgresQL","generated always as identity");

    public static SGBD curSGBD = PostgresqlSGBD;
    
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
    

    
    public static void CreerSchema(Connection con)
            throws SQLException {
        con.setAutoCommit(false);
        try ( Statement st = con.createStatement()) {
            Optional<String> ordrePourCodage = curSGBD.sqlOrderChangeCharsetToUTF8InCurrentDatabase();
            if (ordrePourCodage.isPresent()) {
                st.executeUpdate(ordrePourCodage.get());
            }
            
            st.executeUpdate(
                    """
                    create table Clients (
                        id integer not null primary key
                        generated always as identity,
                        nom varchar(30) not null,
                        prenom varchar(50) not null,
                        email varchar(100) not null unique,
                        codepostal varchar(30) not null,
                        pass varchar(30) not null
                    )
                    """);
            
            st.executeUpdate(
                    """
                    create table Categories (
                        id integer not null primary key
                        generated always as identity,
                        nom varchar(30) not null unique
                    )
                    """);
           
            
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
                        debut timestamp without time zone not null,
                        fin timestamp without time zone not null,
                        prix integer not null
                    )
                    """);
            
            st.executeUpdate(
                    """
                    alter table encheres
                        add constraint fk_encheres_de
                        foreign key (de) references clients(id)
                    """);
            
            st.executeUpdate(
                    """
                    alter table encheres
                        add constraint fk_encheres_sur
                        foreign key (sur) references objets(id)
                    """);
            
            st.executeUpdate(
                    """
                    alter table objets
                        add constraint fk_objets_proposepar
                        foreign key (proposepar) references clients(id)
                    """);
            
            st.executeUpdate(
                    """
                    alter table objets
                        add constraint fk_objets_categorie
                        foreign key (categorie) references categories(id)
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
    
    public static void SupprimerSchema(Connection con) throws SQLException {
        try ( Statement st = con.createStatement()) {
            try {
                st.executeUpdate(
                        """
                    alter table encheres
                        drop constraint fk_encheres_de
                    """);
                System.out.println("contrainte fk_enchere_de supp");
            } catch (SQLException ex) {
            }try {
                st.executeUpdate(
                        """
                    alter table encheres
                        drop constraint fk_encheres_sur
                    """);
                System.out.println("contrainte fk_encheres_sur supp");
            } catch (SQLException ex) {
            }try {
                st.executeUpdate(
                     """
                    alter table objets
                        drop constraint fk_objets_proposepar
                    """);
                System.out.println("contrainte fk_objets_proposepar supp");
            } catch (SQLException ex) {
            }try {
                st.executeUpdate(
                        """
                    alter table objets
                        drop constraint fk_objets_categorie
                    """);
                System.out.println("contrainte fk_objets_categorie supp");
            } catch (SQLException ex) {
            }try {
                st.executeUpdate(
                        """
                    drop table clients
                    """);
                System.out.println("table clients supp");
            } catch (SQLException ex) {
            }try {
                st.executeUpdate(
                        """
                    drop table encheres
                    """);
                System.out.println("table encheres supp");
            } catch (SQLException ex) {
            }try {
                st.executeUpdate(
                        """
                    drop table categories
                    """);
                System.out.println("table categories supp");
            } catch (SQLException ex) {
            }try {
                st.executeUpdate(
                        """
                    drop table objets
                    """);
                System.out.println("table objets supp");
            } catch (SQLException ex) {
            }
        }
    }
    
    
    
    
    
        
    
    
    
    
    
    public static void SchemaDeBase(Connection con) throws SQLException {
        // j'essaye d'abord de tout supprimer
        try {
            SupprimerSchema(con);
        } catch (SQLException ex) {
        }
        CreerSchema(con);
        
        try {
            Clients.CreerClient(con, "Auvray", "Nicolas", "nicolas.auvray50@gmail.com", "67270", "pass");
            Clients.CreerClient(con, "Espinola", "Sophia", "sophia.espinola@insa-strasbourg.fr", "67000", "pass");
            Clients.CreerClient(con, "Lareyre", "Jean-Laurent", "jean-laurent.lareyre@insa-strasbourg.fr", "67000", "pass");
        } catch (Clients.EmailExisteDejaException ex) {
            throw new Error(ex);
        }
        Categories.CreerCategorie(con, "Multimédia");
        Categories.CreerCategorie(con, "Meuble");
               
        
        Objets.CreerObjet(con, "PS5", "console de jeu nouvelle génération", Timestamp.valueOf(LocalDateTime.now()), Timestamp.valueOf("2022-12-28 10:00:00"), 550, 1, 1, 550);
        Objets.CreerObjet(con, "Etagere", "Petite étagère 3 tiroirs", Timestamp.valueOf(LocalDateTime.now()), Timestamp.valueOf("2022-12-28 11:00:00"), 50, 1, 2, 50);
        Objets.CreerObjet(con, "Ordinateur", "Carte Graphique de ouf", Timestamp.valueOf(LocalDateTime.now()), Timestamp.valueOf("2022-12-28 12:00:00"), 1200, 3, 1, 1200);
        
        
        System.out.println("Schéma de base créé");
    }
    
    /*
    public static void menu(Connection con) {
        int rep = -1;
        while (rep != 0) {
            System.out.println("Menu BdD Encheres");
            System.out.println("=============");
            System.out.println("1) créer/recréer la BdD initiale");
            System.out.println("2) liste des utilisateurs");
            System.out.println("3) liste des liens 'Aime'");
            System.out.println("4) ajouter un utilisateur");
            System.out.println("5) ajouter un lien 'Aime'");
            System.out.println("6) ajouter n utilisateurs aléatoires");
            System.out.println("0) quitter");
            rep = ConsoleFdB.entreeEntier("Votre choix : ");
            try {
                if (rep == 1) {
                    recreeTout(con);
                } else if (rep == 2) {
                    afficheTousLesUtilisateur(con);
                } else if (rep == 3) {
                    afficheAmours(con);
                } else if (rep == 4) {
                    demandeNouvelUtilisateur(con);
                } else if (rep == 5) {
                    demandeNouvelAime(con);
                } else if (rep == 6) {
                    System.out.println("création d'utilisateurs 'aléatoires'");
                    int combien = ConsoleFdB.entreeEntier("combien d'utilisateur : ");
                    for (int i = 0; i < combien; i++) {
                        boolean exist = true;
                        while (exist) {
                            String nom = "U" + ((int) (Math.random() * 10000));
                            try {
                                createUtilisateur(con, nom, "P" + ((int) (Math.random() * 10000)), 2);
                                exist = false;
                            } catch (NomExisteDejaException ex) {
                            }
                        }

                    }
                }
            } catch (SQLException ex) {
                throw new Error(ex);
            }
        }
    }
    */
    
    public static void main(String[] args) {
        try {
            
            Connection con = defautConnect();
            SchemaDeBase(con);
            Encheres.AjouterEnchere(con);
            Encheres.AjouterEnchere(con);
            Encheres.AjouterEnchere(con);
            //Clients.DernierEncherisseur(con, 1);
            Clients.BilanClient(con, 1);
            //Clients.SupprimerTableClients(con);
            
            /*AfficherClients(con);
            int i=0;
            while ( i<=5){
                CreerCategorie(con);
                i=i+1;
            }
            System.out.println("avez vous un compte?   oui:1, non:0");
            int c = Lire.i();
            if (c == 0) {
                CreerClient(con);
            }
            if (c == 1) {
                System.out.println("MAIL : ");
                String mail1 = Lire.S();
                System.out.println("PASS : ");
                String pass1 = Lire.S();

                if (VerifyConnection(con, pass1, mail1) == true) {
                    System.out.println("Bien");
                } else {
                    System.out.println("Le pass ou le mail est incorrect");
                }
             

            }
            */
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GestionbD.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(GestionbD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
