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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.text.Font;

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
                //String sd = filtreprix.getText();
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
            }//sophia.espinola@insa-strasbourg.fr
            if ((objets2 != null) && (objets1 != null)) {
                if (objets1.size() < objets2.size()) {
                    objets = objets1;
                    for (int j = 0; j < objets2.size(); j++) {
                        if (!(objets.contains(objets2.get(j)))) {
                            objets.remove(objets2.get(j));
                        }
                    }
                } else if (objets1.size() == objets2.size()) {
                    for (int j = 0; j < objets2.size(); j++) {
                        if (!(objets1.contains(objets2.get(j)))) {
                            objets1.remove(objets1.get(j));
                        }
                    }
                    objets = objets1;
                } else {
                    for (int j = 0; j < objets1.size(); j++) {
                        if (!(objets2.contains(objets1.get(j)))) {
                            objets2.remove(objets1.get(j));
                        }
                    }
                    objets = objets2;
                }
            } else if ((objets1 != null) && (objets2 == null)) {
                objets = objets1;
            }
            if (objets.isEmpty()) {
                Choisir2();
                showAlert();
            } else {
                Choisir(objets);
            }
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
                if (objets1.size() <= objets2.size()) {
                    objets = objets1;
                    for (int j = 0; j < objets2.size(); j++) {
                        if (!(objets.contains(objets2.get(j)))) {
                            objets.remove(objets2.get(j));
                        }
                    }
                } else if (objets1.size() == objets2.size()) {
                    for (int j = 0; j < objets2.size(); j++) {
                        if (!(objets1.contains(objets2.get(j)))) {
                            objets1.remove(objets1.get(j));
                        }
                    }
                    objets = objets1;
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
            if (objets.isEmpty()) {
                Choisir2();
                showAlert();
            } else {
                Choisir(objets);
            }
        };

        barrerecherche.setOnAction(event);
        filtreprix.setOnAction(event2);

        this.setPadding(
                new javafx.geometry.Insets(20, 20, 20, 20));
        this.setSpacing(15);
        this.getChildren().addAll(this.barre, this.barrerecherche, this.accordion, this.prix, this.filtreprix);

        var image = new Image("https://th.bing.com/th/id/OIP.cSCTCMoEpXHDj6Xt1RXcgAHaNK?w=115&h=184&c=7&r=0&o=5&pid=1.7");
        var bgImage = new BackgroundImage(
                image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(1.0, 1.0, true, true, false, false)
        );

        this.accordion.setPrefSize(200, 15);
        this.setBackground(new Background(bgImage));
        this.accordion.setBackground(new Background(bgImage));
        barre.setFont(new Font("Roboto", 15));
        prix.setFont(new Font("Roboto", 15));
        this.accordion.setPrefSize(150, 20);
        this.accordion.setStyle("Roboto");
        this.barrerecherche.setPrefSize(150, 20);
        this.filtreprix.setPrefSize(150, 20);
    }

    private void showAlert() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Recherche");

        // Header Text: null
        alert.setHeaderText(null);
        alert.setContentText("Aucun article ne correspond aux critères choisis!");

        alert.showAndWait();
    }

    public void Choisir(List<Objets> objets) {
        this.main.setPagePrincipale(new ObjetsCat(this.main, objets));
    }

    public void Choisir2() {
        this.main.setPagePrincipale(new VBoxEncheres(this.main));
    }
}
