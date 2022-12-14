/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.espinola.infos3.Interface.vues;

import fr.insa.espinola.infos3.Interface.VuePrincipale;
import fr.insa.espinola.infos3.tables.Categories;
import fr.insa.espinola.infos3.tables.Clients;
import fr.insa.espinola.infos3.tables.Objets;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 *
 * @author Sophia
 */
public class AfficherObjetPerso extends GridPane {

    private VuePrincipale main;
    private Objets objet;
    private ToggleButton supprimer;
    private VBoxEncheres vboxencheres;
    
    public AfficherObjetPerso(VuePrincipale main, Objets objet, VBoxEncheres vboxencheres) {

        this.main = main;
        this.vboxencheres = vboxencheres;
        this.objet = objet;

        this.supprimer = new ToggleButton("Supprimer");
        this.supprimer.setOnAction((event) -> {
            try {                
                Objets.SupprimerObjets(this.main.getUtilisateurs().getConBdD(), this.objet.getId());
                vboxencheres.getPersoObjets().setContent(new MesObjets(this.main, this.vboxencheres));
            } catch (SQLException ex) {
                this.getChildren().add(new Label("Problème BdD : " + ex.getLocalizedMessage()));
            }            

        });

        try {
            int lig = 0;
            Label titre = new Label(this.objet.getTitre());
            Font font = Font.font("Arial", FontWeight.BOLD, 11);
            titre.setFont(font);
            this.add(titre, 0, lig);
            lig++;
            
            if(Timestamp.valueOf(LocalDateTime.now()).before(this.objet.getFin())){
                this.add(new Label("Description : " + this.objet.getDescription()), 0, lig);
                lig++;
                this.add(new Label("Date de début : " + this.objet.getDebut()), 0, lig);
                lig++;
                this.add(new Label("Date de fin : " + this.objet.getFin()), 0, lig);
                lig++;
                this.add(new Label("Catégorie : " + Categories.ConversionIdCategorie(this.main.getUtilisateurs().getConBdD(), this.objet.getCategorie())), 0, lig);
                lig++;
                this.add(new Label("Prix de base : " + this.objet.getPrixbase() + "€"), 0, lig);
                lig++;
                this.add(new Label("Montant actuel de l'enchère : " + this.objet.getPrix() + "€"), 0, lig);
                lig++;
                
            }else{
                this.add(new Label("Enchère terminée"), 0, lig);
                lig++;
                this.add(new Label("Prix de base : " + this.objet.getPrixbase() + "€"), 0, lig);
                lig++;
                this.add(new Label("Prix Final : " + this.objet.getPrix() + "€"), 0, lig);
                lig++;
            }
            

            
            this.add(new Label("Dernier à avoir enchéri : " + Clients.ConversionEmailNPClient(this.main.getUtilisateurs().getConBdD(), Clients.DernierEncherisseur(this.main.getUtilisateurs().getConBdD(), this.objet.getId()))), 0, lig);
            this.add(this.supprimer, 10, lig / 2);
        } catch (SQLException ex) {
            this.getChildren().add(new Label("Problème BdD : " + ex.getLocalizedMessage()));
        }

    }
}
