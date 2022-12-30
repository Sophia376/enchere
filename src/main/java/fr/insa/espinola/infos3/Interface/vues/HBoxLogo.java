/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.espinola.infos3.Interface.vues;

import fr.insa.espinola.infos3.Interface.VuePrincipale;
import java.io.InputStream;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 *
 * @author nicol
 */
public class HBoxLogo extends HBox{
    
    private VuePrincipale vuePrincipale;
    
    public HBoxLogo(VuePrincipale vuePrincipale) {
        this.vuePrincipale = vuePrincipale;
        InputStream complet = this.getClass().getResourceAsStream("LogoINSA.png");
        ImageView imageView = new ImageView(new Image(complet));
        this.getChildren().add(imageView);
    }
    
}
