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
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;

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
                new javafx.geometry.Insets(30, 20, 20, 20));
        this.setSpacing(15);
        this.getChildren().addAll(this.barre, this.barrerecherche, this.accordion, this.prix, this.filtreprix);

        var image = new Image("https://i.pinimg.com/originals/da/c4/b5/dac4b51bf09669c46c47b17f403321f7.jpg");
        var bgImage = new BackgroundImage(
                image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(1.0, 1.0, true, true, false, false)
        );

        this.setBackground(new Background(bgImage));
    }

}
