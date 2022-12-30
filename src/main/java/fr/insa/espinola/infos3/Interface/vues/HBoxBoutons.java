/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.espinola.infos3.Interface.vues;

import fr.insa.espinola.infos3.Interface.VuePrincipale;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;

/**
 *
 * @author nicol
 */
public class HBoxBoutons extends HBox{
    private VuePrincipale vuePrincipale;
    private ToggleButton inscription;
    private ToggleButton connexion;

    public HBoxBoutons(VuePrincipale vuePrincipale) {
        this.setSpacing(50);
        this.vuePrincipale = vuePrincipale;
        this.inscription = new ToggleButton("S'inscrire");
        this.inscription.setPrefSize(125,50);
        this.connexion = new ToggleButton("Se connecter");
        this.connexion.setPrefSize(125,50);
        
        ToggleGroup tg = new ToggleGroup();  
        this.inscription.setToggleGroup(tg);
        this.connexion.setToggleGroup(tg);
        
        this.getChildren().addAll(this.inscription, this.connexion);
    }

    
}
