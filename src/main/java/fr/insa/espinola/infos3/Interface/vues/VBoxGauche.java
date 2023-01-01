/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.espinola.infos3.Interface.vues;

import fr.insa.espinola.infos3.Interface.VuePrincipale;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.VBox;

/**
 *
 * @author nicol
 */
public class VBoxGauche extends VBox{ 
    private VuePrincipale main;
    private ToggleButton choixcategorie;

    public VBoxGauche(VuePrincipale main) {
        this.main = main;
        this.choixcategorie = new ToggleButton("Choisir une catégorie");   //faire un menu button
        this.choixcategorie.setOnAction((event) -> {
            this.choixCategorie();
        });
        this.setSpacing(20);
        this.setPadding(new javafx.geometry.Insets(20, 20, 20, 20));
        this.getChildren().add(this.choixcategorie);
    }
    public void choixCategorie() {
       // this.main.getUtilisateurs().setUtilisateur(Optional.empty());
        
        this.main.setPagePrincipale(new AffichageBienvenue(this.main));
        this.main.setGauche(new Vide(main));
        this.main.setDroite(new Vide(main));
    }
}
