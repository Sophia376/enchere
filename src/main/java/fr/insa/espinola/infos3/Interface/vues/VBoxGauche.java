/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.espinola.infos3.Interface.vues;

import javafx.scene.layout.VBox;
import fr.insa.espinola.infos3.Interface.VuePrincipale;
import fr.insa.espinola.infos3.tables.Objets;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
    private List<Objets> objets1;
    private List<Objets> objets2;

    private List<Objets> objets;

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
        this.objets = new ArrayList();
        Connection con = this.main.getUtilisateurs().getConBdD();

        EventHandler<ActionEvent> event = (ActionEvent e) -> {
            if (!"".equals(barrerecherche.getText())) {
                String sd = filtreprix.getText();
                String t = barrerecherche.getText();
                try {
                    objets1 = Objets.objetsTitre(con, t);
                } catch (SQLException ex) {
                    Logger.getLogger(VBoxGauche.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                try {
                    objets1 = Objets.tousLesObjets(con);
                } catch (SQLException ex) {
                    Logger.getLogger(VBoxGauche.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if ((objets2 != null) && (objets1 != null)) {
                if (objets1.size() < objets2.size()) {
                    objets = objets1;
                    for (int j = 0; j < objets2.size(); j++) {
                        if (!(objets.contains(objets2.get(j)))) {
                            objets.remove(objets2.get(j));
                        }
                    }
                } else {
                    objets = objets2;
                    for (int j = 0; j < objets1.size(); j++) {
                        if (!(objets.contains(objets1.get(j)))) {
                            objets.remove(objets1.get(j));
                        }
                    }
                }
            } else if ((objets1 != null) && (objets2 == null)) {
                objets = objets1;
            }
            Choisir(objets);
        };
        EventHandler<ActionEvent> event2 = (ActionEvent e) -> {
            int p;
            if (!"".equals(filtreprix.getText())) {
                String sd = filtreprix.getText();
                p = Integer.parseInt(sd);
                try {
                    objets2 = Objets.objetsPrix(con, p);
                } catch (SQLException ex) {
                    Logger.getLogger(VBoxGauche.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                try {
                    objets2 = Objets.tousLesObjets(con);
                } catch (SQLException ex) {
                    Logger.getLogger(VBoxGauche.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if ((objets2 != null) && (objets1 != null)) {
                if (objets1.size() < objets2.size()) {
                    objets = objets1;
                    for (int j = 0; j < objets2.size(); j++) {
                        if (!(objets.contains(objets2.get(j)))) {
                            objets.remove(objets2.get(j));
                        }
                    }
                } else {
                    objets = objets2;
                    for (int j = 0; j < objets1.size(); j++) {
                        if (!(objets.contains(objets1.get(j)))) {
                            objets.remove(objets1.get(j));
                        }
                    }
                }
            } else if ((objets2 != null) && (objets1 == null)) {
                objets = objets2;
            }
            Choisir(objets);
        };

        barrerecherche.setOnAction(event);
        filtreprix.setOnAction(event2);

        this.setPadding(
                new javafx.geometry.Insets(30, 20, 20, 20));
        this.setSpacing(15);
        this.getChildren().addAll(this.barre, this.barrerecherche, this.accordion, this.prix, this.filtreprix);

        /*var image = new Image("https://retec2000.com/Media/retectablerosypuertas/_Profiles/294eef10/4958ff96/NEGRO%20ref.421.jpg?v=637085590089472190");
        var bgImage = new BackgroundImage(
                image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(1.0, 1.0, true, true, false, false)
        );

        this.setBackground(new Background(bgImage));
        this.accordion.setBackground(new Background(bgImage));*/
    }

    public void Choisir(List<Objets> objets) {
        this.main.setPagePrincipale(new ObjetsCat(this.main, objets));
    }
}
