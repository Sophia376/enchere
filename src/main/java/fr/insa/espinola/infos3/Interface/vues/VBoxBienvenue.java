/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.espinola.infos3.Interface.vues;

import fr.insa.espinola.infos3.Interface.VuePrincipale;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

/**
 *
 * @author nicol
 */
public class VBoxBienvenue extends VBox {
    private VuePrincipale main;
    private HBoxBoutons boutons; 
    private HBoxLogo logo; 

    public VBoxBienvenue(VuePrincipale main) {
        this.main = main;
        this.boutons = new HBoxBoutons(this.main);
        this.logo = new HBoxLogo(this.main);
        this.setSpacing(80);
        this.setPadding(new javafx.geometry.Insets(70,200,47,290));
        
        Label titre = new Label("                  Bienvenue sur le site : Enchères2000");
        Font font = Font.font("Baskerville",FontWeight.BOLD,  35);
        titre.setTextFill(Color.web("#000000"));
        titre.setFont(font);
        
        TextArea taMoche = new TextArea("Cette interface en javaFX est la traduction quasi à l'identique\n"
                + "d'une interface web faite en vaadin\n"
                + "==> elle n'était déjà pas très belle en vaadin,\n"
                + "==> on pourrait faire moins moche et plus adapté à JavaFX");
        taMoche.setEditable(false);
        taMoche.setMaxWidth(200);
        taMoche.setPadding(new javafx.geometry.Insets(0,50,0,0));
        Label label = new Label("       Veuillez vous connecter si vous possédez déjà un compte ou vous inscrire si vous êtes un nouvel utilisateur");
        Font font1 = Font.font("Baskerville",FontWeight.BOLD,  20);
        label.setTextFill(Color.web("#000000"));
        label.setFont(font1);
        this.getChildren().addAll(titre, taMoche,label,this.boutons, this.logo);
    }
    
    
}
