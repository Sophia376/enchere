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
import java.util.List;
import javafx.scene.control.Label;

/**
 *
 * @author nicol
 */
public class VBoxGauche extends VBox {

    private VuePrincipale main;
    private int idcategorie;
    private List<Objets> objets;
    private Label l1;
    private VBoxEncheres vboxencheres;

    public VBoxGauche(VuePrincipale main) throws SQLException {
        this.main = main;
        this.idcategorie = -1;

        Connection con = this.main.getUtilisateurs().getConBdD();
        this.l1 = new Label(" Choisir des catégories ");
        this.getChildren().add(this.l1);

        int i = Categories.nbreCategories(con);

        for (int v = 0; v < i; v++) {
            int idCategorie = v + 1;
            CheckBox c = new CheckBox(Categories.ConversionIdCategorie(con, idCategorie));
            this.getChildren().add(c);
            Label l1 = new Label(Categories.ConversionIdCategorie(con, idCategorie) + " not selected");
            String s1 = Categories.ConversionIdCategorie(con, idCategorie);
            EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
                public void handle(ActionEvent e) {
                    if (c.isSelected()) {
                        try {
                            int idCat = Categories.ConversionIdCategorie2(con, s1);
                            objets = Categories.ChoisirCategorie(con, idCat);
                            System.out.println(objets);
                            Choisir(objets);
                        } catch (SQLException ex) {
                            Logger.getLogger(VBoxGauche.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        l1.setText(s1 + " not selected ");
                        Choisir2();

                    }
                }
            };
            this.setPadding(new javafx.geometry.Insets(20, 20, 20, 200));
            c.setOnAction(event);

            this.getChildren().add(l1);
        }

        this.setPadding(
                new javafx.geometry.Insets(20, 20, 20, 20));
    }

    public void Choisir(List<Objets> objets) {
        this.main.setPagePrincipale(new ObjetsCat(this.main, objets));
    }

    public void Choisir2() {
        this.main.setPagePrincipale(new VBoxEncheres(this.main));
    }

}
