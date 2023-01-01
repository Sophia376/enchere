/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.espinola.infos3.Interface.vues;

import fr.insa.espinola.infos3.Interface.JavaFXUtils;
import fr.insa.espinola.infos3.Interface.VuePrincipale;
import fr.insa.espinola.infos3.tables.Objets;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Optional;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.GridPane;

/**
 *
 * @author Sophia
 */
public class PublierObjet extends GridPane {

    private VuePrincipale main;

    private TextField titre;
    private TextField description;
    private Timestamp debut;
    private Timestamp fin;
    private String c_debut;
    private String c_fin;
    private TextField prixbase;
    private TextField categorie;
    private ToggleButton valider;
    private ToggleButton retour;
    private Objets objet;

    public PublierObjet(VuePrincipale main) {
        this.main = main;
        this.titre = new TextField("titre");
        this.description = new TextField("description");
        this.prixbase = new TextField("prixbase");
        this.categorie = new TextField("categorie");
        this.valider = new ToggleButton("Valider");
        this.retour = new ToggleButton("Retour");

        this.retour.setOnAction((event) -> {
            this.main.setPagePrincipale(new VBoxEncheres(this.main));
        });
        
        
        this.valider.setOnAction((event) -> {
            int id = -1;
            Connection con = this.main.getUtilisateurs().getConBdD();
            String titre = this.titre.getText();
            String description = this.description.getText();
            this.c_debut = "0000-01-01 00:00:00";
            this.c_fin = "0000-01-01 00:00:00";
            while ((this.c_debut.equals("0000-01-01 00:00:00")) || (this.c_fin.equals("0000-01-01 00:00:00"))){
                this.EntreeDateDebut();
                this.EntreeDateFin();
            }
            int prixbase = 0;
            int categorie = -1;
            try{
                prixbase = Integer.parseInt(this.prixbase.getText());
                categorie = Integer.parseInt(this.categorie.getText());
            }catch (Exception e){
                JavaFXUtils.showErrorInAlert("Vous n'avez pas saisi un entier pour le prix de base");
                
            }

            int prix1 = prixbase;
            try {
                if((categorie != -1) && (prixbase != 0) ){
                    id = Objets.CreerObjet(con, titre, description, this.debut, this.fin, prixbase, this.main.getUtilisateurs().getUtilisateurID(), categorie, prix1);
                    this.objet = new Objets(id, titre, description, this.debut, this.fin, prixbase, this.main.getUtilisateurs().getUtilisateurID(), categorie, prix1);
                    JavaFXUtils.showInfoInAlert("Objet  " + titre + " créé");
                    this.main.setPagePrincipale(new VBoxEncheres(this.main));   
                    this.main.setGauche(new VBoxGauche(this.main));
                    this.main.setDroite(new VBoxDroite(this.main));
                }else{
                    JavaFXUtils.showErrorInAlert("Vous avez fait une ou plusieurs erreurs de saisie");
                }
            } catch (SQLException ex) {
                JavaFXUtils.showErrorInAlert("Problème BdD : " + ex.getLocalizedMessage());
            }
        });
        int lig = 0;
        this.add(this.retour, 0, lig);
        lig++;
        this.add(new Label("Titre : "), 0, lig);
        this.add(this.titre, 1, lig);
        lig++;
        this.add(new Label("Description : "), 0, lig);
        this.add(this.description, 1, lig);
        lig++;
        this.add(new Label("Prix de base : "), 0, lig);
        this.add(this.prixbase, 1, lig);
        lig++;
        this.add(new Label("Catégorie : "), 0, lig);
        this.add(this.categorie, 1, lig);
        lig++;
        this.add(this.valider, 0, lig);
    }
    
    public void EntreeDateDebut(){
        Dialog<ButtonType> date = new Dialog<ButtonType>();
        date.setTitle("Saisie d'une date de début");
        date.setHeaderText("Saisir la date que vous souhaitez");
        Label annee = new Label("Quelle Année ? ");
        TextField ann = new TextField("aaaa");
        Label mois = new Label("Quel Mois ? ");
        TextField moi = new TextField("mm");
        Label jour = new Label("Quel Jour ? ");
        TextField jou = new TextField("jj");
        Label heures = new Label("A quelle Heure ?");
        TextField heu = new TextField("hh");
        Label minutes = new Label("A combien de Minutes ?");
        TextField min = new TextField("mm");
        Label secondes = new Label("A combien de Secondes ?");
        TextField sec = new TextField("ss");
        GridPane infos = new GridPane();
        infos.add(annee, 1, 1);
        infos.add(ann, 2, 1);
        infos.add(mois, 1, 2);
        infos.add(moi, 2, 2);
        infos.add(jour, 1, 3);
        infos.add(jou, 2, 3);
        infos.add(heures, 1, 4);
        infos.add(heu, 2, 4);
        infos.add(minutes, 1, 5);
        infos.add(min, 2, 5);
        infos.add(secondes, 1, 6);
        infos.add(sec, 2, 6);
        date.getDialogPane().setContent(infos);
        
        ButtonType saisie = new ButtonType("Valider", ButtonBar.ButtonData.OK_DONE);
        date.getDialogPane().getButtonTypes().add(saisie);
        Optional<ButtonType> p = date.showAndWait();
        p.ifPresent(r -> {
            try{
                this.c_debut = ann.getText() + "-" + moi.getText() + "-" + jou.getText() + " " + heu.getText() + ":" + min.getText() + ":" +sec.getText();
                this.debut = Timestamp.valueOf(c_debut) ;
            }catch (Exception e){
                JavaFXUtils.showErrorInAlert("Saisir des entiers valides :\n"
                        + "aaaa pour l'année\n"
                        + "mm pour le mois de 01 à 12\n"
                        + "jj pour le jour de 01 à 31\n"
                        + "hh pour l'heure de 00 à 24\n"
                        + "mm pour les minutes de 00 à 59\n"
                        + "ss pour les secondes de 00 à 59");
                
            }
            
            
            
        });
        
    }
    
    public void EntreeDateFin(){
        Dialog<ButtonType> date = new Dialog<ButtonType>();
        date.setTitle("Saisie d'une date de fin");
        date.setHeaderText("Saisir la date que vous souhaitez");
        Label annee = new Label("Quelle Année ? ");
        TextField ann = new TextField();
        Label mois = new Label("Quel Mois ? ");
        TextField moi = new TextField();
        Label jour = new Label("Quel Jour ? ");
        TextField jou = new TextField();
        Label heures = new Label("A quelle Heure ?");
        TextField heu = new TextField();
        Label minutes = new Label("A combien de Minutes ?");
        TextField min = new TextField();
        Label secondes = new Label("A combien de Secondes ?");
        TextField sec = new TextField();
        GridPane infos = new GridPane();
        infos.add(annee, 1, 1);
        infos.add(ann, 2, 1);
        infos.add(mois, 1, 2);
        infos.add(moi, 2, 2);
        infos.add(jour, 1, 3);
        infos.add(jou, 2, 3);
        infos.add(heures, 1, 4);
        infos.add(heu, 2, 4);
        infos.add(minutes, 1, 5);
        infos.add(min, 2, 5);
        infos.add(secondes, 1, 6);
        infos.add(sec, 2, 6);
        date.getDialogPane().setContent(infos);
        
        ButtonType saisie = new ButtonType("Valider", ButtonBar.ButtonData.OK_DONE);
        date.getDialogPane().getButtonTypes().add(saisie);
        Optional<ButtonType> p = date.showAndWait();
        p.ifPresent(r -> {
            try{
                this.c_fin = ann.getText() + "-" + moi.getText() + "-" + jou.getText() + " " + heu.getText() + ":" + min.getText() + ":" +sec.getText();
                this.fin = Timestamp.valueOf(this.c_fin) ;
             
            }catch (Exception e){
                JavaFXUtils.showErrorInAlert("Saisir des entiers valides :\n"
                        + "aaaa pour l'année\n"
                        + "mm pour le mois de 01 à 12\n"
                        + "jj pour le jour de 01 à 31\n"
                        + "hh pour l'heure de 00 à 24\n"
                        + "mm pour les minutes de 00 à 59\n"
                        + "ss pour les secondes de 00 à 59");
                
            }
            
            
            
        });
        
    }
}


