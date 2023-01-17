/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.espinola.infos3.Interface.vues;

import fr.insa.espinola.infos3.Interface.VuePrincipale;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

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
        this.setSpacing(100);
        this.setPadding(new javafx.geometry.Insets(150,200,15,330));
        
        Label titre = new Label("                     Bienvenue sur Enchères2000");
        Font font = Font.font("Baskerville",FontWeight.BOLD,  40);
        titre.setTextFill(Color.rgb(255, 255, 255, 0.87));
        titre.setFont(font);
        titre.setAlignment(Pos.BASELINE_CENTER);
        
        Label slogan = new Label("           \"Achetez-le pas cher, vendez-le aux enchères\" ");
        Font font2 = Font.font("Baskerville",FontPosture.ITALIC,  40);
        slogan.setTextFill(Color.web("#999999"));
        slogan.setFont(font2);
        slogan.setAlignment(Pos.CENTER);
        
        Label label = new Label("Veuillez vous connecter si vous possédez déjà un compte ou vous inscrire si vous êtes un nouvel utilisateur");
        Font font1 = Font.font("Baskerville",FontWeight.BOLD,  25);
        label.setTextFill(Color.rgb(255, 255, 255, 0.87));
        label.setFont(font1);
        label.setAlignment(Pos.CENTER);
        this.getChildren().addAll(titre, slogan,label,this.boutons, this.logo);
    }
    
    
}
