/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.espinola.infos3.Interface.vues;

import fr.insa.espinola.infos3.tables.Categories;
import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;
import fr.insa.espinola.infos3.Interface.VuePrincipale;
import fr.insa.espinola.infos3.tables.Objets;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

/**
 *
 * @author nicol
 */
public class VBoxGauche extends VBox {

    private VuePrincipale main;
    private VBoxCategories vboxcategories;
    private Label barrerecherche;
    private Label filtreprix;
    
    public VBoxGauche(VuePrincipale main) throws SQLException {
        this.main = main;
        
        this.vboxcategories = new VBoxCategories(this.main);
        this.barrerecherche = new Label("Barre de Recherche ici");
        this.filtreprix = new Label("Filtre par prix ici");
        this.setPadding(
                new javafx.geometry.Insets(20, 20, 20, 20));
        this.setSpacing(50);
        this.getChildren().addAll(this.barrerecherche,this.vboxcategories, this.filtreprix);
    }
        

}
