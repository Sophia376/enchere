/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.espinola.infos3.Interface.vues;

import fr.insa.espinola.infos3.Interface.JavaFXUtils;
import fr.insa.espinola.infos3.Interface.VuePrincipale;
import fr.insa.espinola.infos3.tables.Objets;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.GridPane;

/**
 *
 * @author Sophia
 */
public class SupprimerObjet extends GridPane {

    private VuePrincipale main;
    private ToggleButton valider;
    private ToggleButton retour;

    public SupprimerObjet(VuePrincipale main) {
        this.main = main;
        this.valider = new ToggleButton("Valider");
        this.retour = new ToggleButton("Retour");

        this.retour.setOnAction((event) -> {
            this.main.setPagePrincipale(new VBoxEncheres(this.main));
        });

        this.valider.setOnAction((event) -> {
            Connection con = this.main.getUtilisateurs().getConBdD();

        });

    }
}
