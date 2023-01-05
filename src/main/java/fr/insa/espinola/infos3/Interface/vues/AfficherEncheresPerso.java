/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.espinola.infos3.Interface.vues;

import fr.insa.espinola.infos3.Interface.JavaFXUtils;
import fr.insa.espinola.infos3.Interface.VuePrincipale;
import fr.insa.espinola.infos3.tables.Encheres;
import fr.insa.espinola.infos3.tables.Objets;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 *
 * @author nicol
 */
public class AfficherEncheresPerso extends GridPane {

    private VuePrincipale main;
    private Encheres enchere;
    private ToggleButton encherir;
    private VBoxEncheres vboxencheres;
    
    public AfficherEncheresPerso(VuePrincipale main, Encheres enchere, VBoxEncheres vboxencheres) {
        this.setHgap(200);
        this.setVgap(50);
        this.main = main;
        this.vboxencheres = vboxencheres;
        this.enchere = enchere;
        this.encherir = new ToggleButton("Enchérir");

        this.encherir.setOnAction((event) -> {
            try{
                AfficherObjet.Encherir(this.main.getUtilisateurs().getConBdD(),Encheres.ConversionIdObjet(this.main.getUtilisateurs().getConBdD(),enchere.getSur()),this.main.getUtilisateurs().getUtilisateurID(),this.vboxencheres);
                vboxencheres.getPersoEncheres().setContent(new VBoxMesEncheres(this.main, this.vboxencheres));
            }catch (SQLException ex){
                
            }
        });
            
        //try {
            int lig = 0;
                
            lig++;
            /*
            this.add(new Label("Nom de l'objet : " + this.objet.getDescription()), 0, lig);
            lig++;
            this.add(new Label("Date de début : " + this.objet.getDebut()), 0, lig);
            lig++;
            this.add(new Label("Date de fin : " + this.objet.getFin()), 0, lig);
            lig++;
            this.add(new Label("Prix de base : " + this.objet.getPrixbase() + "€"), 0, lig);
            lig++;
            this.add(new Label("Proposé par : " + Clients.ConversionNomPrenomClient(this.main.getUtilisateurs().getConBdD(), this.objet.getProposer())), 0, lig);
            lig++;
            this.add(new Label("Catégorie : " + Categories.ConversionIdCategorie(this.main.getUtilisateurs().getConBdD(), this.objet.getCategorie())), 0, lig);
            lig++;
            this.add(new Label("Montant actuel de l'enchère : " + this.objet.getPrix() + "€"), 0, lig);
            lig++;
            this.add(new Label("Dernier à avoir enchéri : " + Clients.ConversionEmailNPClient(this.main.getUtilisateurs().getConBdD(), Clients.DernierEncherisseur(this.main.getUtilisateurs().getConBdD(), this.objet.getId()))), 0, lig);
            this.add(this.supprimer, 10, lig / 2);
            */
        //} catch (SQLException ex) {
          //  this.getChildren().add(new Label("Problème BdD : " + ex.getLocalizedMessage()));
        //}

    }
    
}
