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
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author nicol
 */
public class VuePrincipale extends BorderPane {
    
    private Utilisateurs utilisateurs;
    private ScrollPane pageAccueil;

    
    public void setPageAccueil(Node c) {
        this.pageAccueil.setContent(c);
    }
    
    public VuePrincipale() {
        
        this.utilisateurs = new Utilisateurs();
        this.pageAccueil = new ScrollPane();
        this.setCenter(this.pageAccueil);
        JavaFXUtils.addSimpleBorder(this.pageAccueil);
         try {
            this.utilisateurs.setConBdD(GestionbD.defautConnect());
            this.setPageAccueil(new AffichageBienvenue(this));
            
        } catch (ClassNotFoundException | SQLException ex) {
            this.setPageAccueil(new BugBdD(this));
        }
        
    }

    public Utilisateurs getUtilisateurs() {
        return utilisateurs;
    }
}
