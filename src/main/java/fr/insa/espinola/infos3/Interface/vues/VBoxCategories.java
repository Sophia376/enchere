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
import javafx.scene.control.CheckBox;
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
            //Label l1 = new Label(Categories.ConversionIdCategorie(con, idCategorie) + " not selected");
            //String s1 = Categories.ConversionIdCategorie(con, idCategorie);
            EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
                public void handle(ActionEvent e) {
                    if (c.isSelected()) {
                        try {
                            objets = Categories.ChoisirCategorie(con, idCategorie);
                            for (int j = 0; j < objets.size(); j++) {
                                if (!(p_categories.contains(objets.get(j)))) {
                                    //System.out.println(p_categories);
                                    p_categories.add(objets.get(j));
                                    //System.out.println(p_categories);
                                }
                            }
                            //l1.setText(s1 + " selected ");
                        } catch (SQLException ex) {
                            Logger.getLogger(VBoxGauche.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        try {
                            //l1.setText(s1 + " not selected ");
                            objets = Categories.ChoisirCategorie(con, idCategorie);
                            for (int k = 0; k < p_categories.size(); k++) {
                                for (int j = 0; j < objets.size(); j++) {
                                    if (p_categories.get(k).getId() == objets.get(j).getId()) {
                                        //System.out.println(p_categories);
                                        p_categories.remove(k);
                                        //System.out.println(p_categories);
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

        this.setSpacing(20);
    }

    public void Choisir(List<Objets> objets) {
        this.main.setPagePrincipale(new ObjetsCat(this.main, objets));
    }

    public void Choisir2() {
        this.main.setPagePrincipale(new VBoxEncheres(this.main));
    }

}
