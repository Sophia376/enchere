/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.espinola.infos3.Interface.vues;

import javafx.scene.layout.VBox;
import fr.insa.espinola.infos3.Interface.VuePrincipale;
import java.sql.SQLException;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;

/**
 *
 * @author nicol
 */
public class VBoxGauche extends VBox {

    private VuePrincipale main;
    private VBoxCategories vboxcategories;
    private Label prix;
    private Label barre;
    private TextField filtreprix;
    private TextField barrerecherche;
    private Accordion accordion;

    public VBoxGauche(VuePrincipale main) throws SQLException {
        this.main = main;
        this.barre = new Label("Barre de Recherche");
        this.barrerecherche = new TextField();
        this.vboxcategories = new VBoxCategories(this.main);
        this.prix = new Label("Prix Maximal");
        this.filtreprix = new TextField();
        this.accordion = new Accordion();
        TitledPane pane1 = new TitledPane("Categories", this.vboxcategories);
        this.accordion.getPanes().add(pane1);

        this.setPadding(
                new javafx.geometry.Insets(20, 20, 20, 20));
        this.setSpacing(15);
        this.getChildren().addAll(this.barre, this.barrerecherche, this.accordion, this.prix, this.filtreprix);
    }

}
