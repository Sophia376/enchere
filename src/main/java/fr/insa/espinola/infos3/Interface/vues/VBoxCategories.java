/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.espinola.infos3.Interface.vues;

import fr.insa.espinola.infos3.Interface.VuePrincipale;
import fr.insa.espinola.infos3.tables.Categories;
import fr.insa.espinola.infos3.tables.Objets;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.VBox;

/**
 *
 * @author nicol
 */
public class VBoxCategories extends VBox {

    private VuePrincipale main;
    private int idcategorie;
    private List<Objets> objets;
    private VBoxEncheres vboxencheres;
    private List<Objets> p_categories;

    public VBoxCategories(VuePrincipale main) throws SQLException {
        this.main = main;
        this.idcategorie = -1;
        this.p_categories = new ArrayList();

        Connection con = this.main.getUtilisateurs().getConBdD();

        int i = Categories.nbreCategories(con);

        for (int v = 0; v < i; v++) {
            int idCategorie = v + 1;
            CheckBox c = new CheckBox(Categories.ConversionIdCategorie(con, idCategorie));
            this.getChildren().add(c);
            EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
                public void handle(ActionEvent e) {
                    if (c.isSelected()) {
                        try {
                            objets = Categories.ChoisirCategorie(con, idCategorie);
                            for (int j = 0; j < objets.size(); j++) {
                                if (!(p_categories.contains(objets.get(j)))) {
                                    p_categories.add(objets.get(j));
                                }
                            }
                        } catch (SQLException ex) {
                            Logger.getLogger(VBoxGauche.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        if (objets.isEmpty()){
                        showAlert();
                            
                        }
                    } else {
                        try {
                            objets = Categories.ChoisirCategorie(con, idCategorie);
                            for (int k = 0; k < p_categories.size(); k++) {
                                for (int j = 0; j < objets.size(); j++) {
                                    if (p_categories.get(k).getId() == objets.get(j).getId()) {
                                        p_categories.remove(k);
                                    }
                                }
                            }
                        } catch (SQLException ex) {
                            Logger.getLogger(VBoxGauche.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    if (!(p_categories.isEmpty())) {
                        Choisir(p_categories);
                    } else {
                        Choisir2();
                        
                    }
                }
            };
            c.setOnAction(event);
        }

        
        var image = new Image("https://de-production.imgix.net/colors/browser/de6096.jpg");
        var bgImage = new BackgroundImage(
                image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(1.0, 1.0, true, true, false, false)
        );
        
        this.setBackground(new Background(bgImage));
        this.setSpacing(20);
        this.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));
    }

    public void Choisir(List<Objets> objets) {
        this.main.setPagePrincipale(new ObjetsCat(this.main, objets));
    }

    public void Choisir2() {
        this.main.setPagePrincipale(new VBoxEncheres(this.main));
    }
    
    private void showAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Recherche");

        // Header Text: null
        alert.setHeaderText(null);
        alert.setContentText("Aucun article ne correspond aux critères choisis!");

        alert.showAndWait();
    }

}
