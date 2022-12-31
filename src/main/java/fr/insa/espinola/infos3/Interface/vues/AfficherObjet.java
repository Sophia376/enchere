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
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 *
 * @author nicol
 */
public class AfficherObjet extends GridPane{
    private VuePrincipale main;
    private Objets objet;
    private ToggleButton encherir;
    
    public AfficherObjet(VuePrincipale main, Objets objet) {
        this.main = main;
        this.objet = objet;
        
        this.encherir = new ToggleButton("Enchérir");       
        this.encherir.setOnAction((event) -> {
        });
        try{
            int lig = 0;
            Label titre = new Label(this.objet.getTitre());
            Font font = Font.font("Arial",FontWeight.BOLD,11);
            titre.setFont(font);
            this.add(titre,0,lig);
            lig++;
            this.add(new Label("Description : " + this.objet.getDescription()), 0, lig);
            lig ++;
            this.add(new Label("Date de début : " + this.objet.getDebut()), 0, lig);
            lig ++;
            this.add(new Label("Date de fin : " + this.objet.getFin()), 0, lig);
            lig ++;
            this.add(new Label("Prix de base : " + this.objet.getPrixbase() + "€") , 0, lig);
            lig ++;
            this.add(new Label("Proposé par : " + Clients.ConversionNomPrenomClient(this.main.getUtilisateurs().getConBdD(),this.objet.getProposer())), 0, lig);
            lig ++;
            this.add(new Label("Catégorie : " + Categories.ConversionIdCategorie(this.main.getUtilisateurs().getConBdD(),this.objet.getCategorie())), 0, lig);
            lig ++;
            this.add(new Label("Montant actuel de l'enchère : " + this.objet.getPrix() + "€") , 0, lig);
            lig ++;
            this.add(new Label("Dernier à avoir enchéri : " + Clients.ConversionEmailNPClient(this.main.getUtilisateurs().getConBdD(), Clients.DernierEncherisseur(this.main.getUtilisateurs().getConBdD(), this.objet.getId()))) , 0, lig);
            this.add(this.encherir, 10, lig/2);
        } catch (SQLException ex) {
            this.getChildren().add(new Label("Problème BdD : " + ex.getLocalizedMessage()));
        }
        
    }
    
    
}
