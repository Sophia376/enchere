/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.espinola.infos3.Interface.vues;

import fr.insa.espinola.infos3.Interface.VuePrincipale;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author nicol
 */
public class VBoxEncheres extends VBox {

    private VuePrincipale main;
    private Tab allEncheres;
    private Tab persoEncheres;
    private Tab persoObjets;
    private TabPane allTabs;

    public VBoxEncheres(VuePrincipale main) {
        this.main = main;

        this.allEncheres = new Tab("Toutes les enchères en cours");
        this.allEncheres.setOnSelectionChanged((t) -> {
            this.allEncheres.setContent(new VBoxAllEncheres(this.main, this));
        });

        this.persoObjets = new Tab("Mes objets");
        this.persoObjets.setOnSelectionChanged((t) -> {
            this.persoObjets.setContent(new VBoxMesObjets(this.main, this));
        });

        // this.allEncheres.setContent(new VBoxAllEncheres(this.main, this));
        this.persoEncheres = new Tab("Mes enchères ");
        this.persoEncheres.setOnSelectionChanged((t) -> {
            this.persoEncheres.setContent(new VBoxAllEncheres(this.main, this));
        });

        //this.persoEncheres.setContent(new VBoxAllEncheres(this.main, this));
        this.allTabs = new TabPane(this.allEncheres, this.persoEncheres, this.persoObjets);
        this.getChildren().addAll(this.allTabs);
        this.allTabs.getSelectionModel().select(this.allEncheres);
    }

    public Tab getAllEncheres() {
        return allEncheres;
    }

    public Tab getPersoObjets() {
        return persoObjets;
    }

    public Tab getPersoEncheres() {
        return persoObjets;
    }

}
