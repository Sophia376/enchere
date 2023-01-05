/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.espinola.infos3.Interface.vues;

import fr.insa.espinola.infos3.Interface.VuePrincipale;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 *
 * @author nicol
 */
public class HBoxTitres extends HBox{
    private VuePrincipale main;

    public HBoxTitres(VuePrincipale main) {
        this.main = main;
        this.setSpacing(200);
        Label titre1 = new Label("Enchères que vous gagnez");
        Label titre2 = new Label("Enchères que vous perdez");
        Label titre3 = new Label("Enchères terminées");
        Font font = Font.font("Arial", FontWeight.BOLD, 16);
        titre1.setFont(font);
        titre2.setFont(font);
        titre3.setFont(font);
        this.getChildren().addAll(titre1, titre2, titre3);
    }
    
}
