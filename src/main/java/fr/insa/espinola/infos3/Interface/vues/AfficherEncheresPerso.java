/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.espinola.infos3.Interface.vues;

import fr.insa.espinola.infos3.Interface.VuePrincipale;
import fr.insa.espinola.infos3.tables.Categories;
import fr.insa.espinola.infos3.tables.Clients;
import fr.insa.espinola.infos3.tables.Encheres;
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
public class AfficherEncheresPerso extends GridPane {

    private VuePrincipale main;
    private Encheres enchere;
    private ToggleButton encherir;
    private VBoxEncheres vboxencheres;
    private AfficherObjet fct_encherir;
    
    
    public AfficherEncheresPerso(VuePrincipale main, Encheres enchere, VBoxEncheres vboxencheres, AfficherObjet fct_encherir) {
        this.setHgap(50);
        this.setVgap(50);
        this.main = main;
        this.vboxencheres = vboxencheres;
        this.enchere = enchere;
        this.encherir = new ToggleButton("Enchérir");
        this.fct_encherir = fct_encherir;

        this.encherir.setOnAction((event) -> {            
            this.fct_encherir.Encherir();
            vboxencheres.getPersoEncheres().setContent(new VBoxMesEncheres(this.main, this.vboxencheres, this.fct_encherir));          
        });
            
        //try {
            int lig = 0;
            Label gagne = new Label("Enchères que vous gagnez");
            Label perd = new Label("Enchères que vous perdez");
            Label termine = new Label("Enchères terminées");
            Font font = Font.font("Arial", FontWeight.BOLD, 11);
            gagne.setFont(font);
            perd.setFont(font);
            termine.setFont(font);
            this.add(gagne, 0, lig);
            this.add(perd, 1, lig);
            this.add(termine, 2, lig);
            lig++;
            /*
            this.add(new Label("Description : " + this.objet.getDescription()), 0, lig);
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
