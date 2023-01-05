/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.espinola.infos3.Interface;

import fr.insa.espinola.infos3.Interface.vues.AffichageBienvenue;
import fr.insa.espinola.infos3.Interface.vues.BugBdD;
import fr.insa.espinola.infos3.bdd.GestionbD;
import java.sql.SQLException;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;

/**
 *
 * @author nicol
 */
public class VuePrincipale extends BorderPane {
    
    private Utilisateurs utilisateurs;
    private ScrollPane pagePrincipale;

    
    public void setPagePrincipale(Node c) {
        this.pagePrincipale.setContent(c);
    }
    
    public void setDroite(Node c) {
        this.setRight(c);
    }
    
    public void setGauche(Node c) {
        this.setLeft(c);
    }
       
    public VuePrincipale() {
        
        this.utilisateurs = new Utilisateurs();
        this.pagePrincipale = new ScrollPane();
        this.setCenter(this.pagePrincipale);
        JavaFXUtils.addSimpleBorder(this.pagePrincipale);
        
        
        
         try {
            this.utilisateurs.setConBdD(GestionbD.defautConnect());
            this.setPagePrincipale(new AffichageBienvenue(this));
            
            
        } catch (ClassNotFoundException | SQLException ex) {
            this.setPagePrincipale(new BugBdD(this));
        }
         
        
        
    }

    public Utilisateurs getUtilisateurs() {
        return utilisateurs;
    }
}
