/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.espinola.infos3.Interface.vues;

import fr.insa.espinola.infos3.Interface.JavaFXUtils;
import fr.insa.espinola.infos3.Interface.VuePrincipale;
import fr.insa.espinola.infos3.tables.Categories;
import fr.insa.espinola.infos3.tables.Objets;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import javafx.geometry.Pos;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
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
    private String c_fin;
    private TextField prixbase;
    private ToggleButton categorie;
    private ToggleButton valider;
    private ToggleButton date_fin;
    private ToggleButton retour;
    private Objets objet;
    private ToggleButton choix_image;
    private int idcategorie;
    private byte[] image;
    
    public PublierObjet(VuePrincipale main) {
        this.main = main;
        this.titre = new TextField("titre");
        this.description = new TextField("description");
        this.prixbase = new TextField("0");
        this.categorie = new ToggleButton("Choisir une catégorie");
        this.date_fin = new ToggleButton("Choisir une Date de fin");
        this.choix_image = new ToggleButton("Choisir une image");
        this.valider = new ToggleButton("Valider");
        this.retour = new ToggleButton("Retour");
        this.idcategorie = -1;
        this.image = null;
        Connection con = this.main.getUtilisateurs().getConBdD();
        this.retour.setOnAction((event) -> {
            this.main.setPagePrincipale(new VBoxEncheres(this.main));
        });
        
        this.choix_image.setOnAction((event) -> {
            try{
                this.image = Objets.InsererImage(con);
            }catch(SQLException  ex){
                JavaFXUtils.showErrorInAlert("Problème BdD : " + ex.getLocalizedMessage());
            }catch(IOException e){
                
            }
        });
        
        this.categorie.setOnAction((event) -> {
            this.idcategorie = ChoisiCategorie();
        });
        
        this.fin = Timestamp.valueOf("0000-01-01 00:00:00");
        this.debut = Timestamp.valueOf(LocalDateTime.now());
        
        this.date_fin.setOnAction((event) -> {
            while(this.debut.after(this.fin)){
                this.EntreeDateFin();
                if (this.debut.after(this.fin)){
                    JavaFXUtils.showErrorInAlert("Saisir une date postérieure à la date actuelle");
                }
            }
        });
        
        
        this.setAlignment(Pos.CENTER);
        this.valider.setOnAction((event) -> {
            int id = -1;
            
            String titre = this.titre.getText();
            String description = this.description.getText();
            try {
                int prixbase = Integer.parseInt(this.prixbase.getText());
                if(this.idcategorie != -1 ){
                    id = Objets.CreerObjet(con, titre, description, this.debut, this.fin, prixbase, this.main.getUtilisateurs().getUtilisateurID(), this.idcategorie, prixbase, this.image);
                    this.objet = new Objets(id, titre, description, this.debut, this.fin, prixbase, this.main.getUtilisateurs().getUtilisateurID(), this.idcategorie, prixbase, this.image);
                    JavaFXUtils.showInfoInAlert("Objet  " + titre + " créé");
                    this.main.setPagePrincipale(new VBoxEncheres(this.main));   
                    this.main.setGauche(new VBoxGauche(this.main));
                    this.main.setDroite(new VBoxDroite(this.main));
                }else{
                    JavaFXUtils.showErrorInAlert("Vous avez fait une ou plusieurs erreurs de saisie");
                }
            }catch (SQLException ex) {
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
        this.add(new Label("Image : "), 0, lig);
        this.add(this.choix_image, 1, lig);
        lig++;
        this.add(new Label("Date de fin : "), 0, lig);
        this.add(this.date_fin, 1, lig);
        lig++;
        this.add(this.valider, 0, lig);
        
        
        
        /* var image = new Image("http://www.interinclusion.org/wp-content/uploads/2012/10/105876350-light-wave-1.1.jpg");
        var bgImage = new BackgroundImage(
        image,
        BackgroundRepeat.NO_REPEAT,
        BackgroundRepeat.NO_REPEAT,
        BackgroundPosition.DEFAULT,
        new BackgroundSize(1.0, 1.0, true, true, false, false)
        );
        this.setBackground(new Background(bgImage));*/
    }
    
    public int ChoisiCategorie(){
        int idcategorie = -1;
        ArrayList<Categories> categories = new ArrayList();
        try{
            categories = Categories.ToutesLesCategories(this.main.getUtilisateurs().getConBdD());
        }catch (SQLException ex) {
            this.getChildren().add(new Label("Problème BdD : " + ex.getLocalizedMessage()));
        }
        ChoiceDialog dialog = new ChoiceDialog(categories.get(0), categories);  
        dialog.setTitle("Choix d'une catégorie");
        dialog.setHeaderText("Choisir la catégorie de votre objet");

        Optional<Categories> saisie = dialog.showAndWait();

        if (saisie.isPresent()) {
            Categories categorieSaisie = saisie.get();
            idcategorie = categorieSaisie.getId();
        }
        return idcategorie;
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


