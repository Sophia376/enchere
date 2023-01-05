/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.espinola.infos3.Interface.vues;

import fr.insa.espinola.infos3.Interface.JavaFXUtils;
import fr.insa.espinola.infos3.Interface.VuePrincipale;
import fr.insa.espinola.infos3.tables.Encheres;
import fr.insa.espinola.infos3.tables.Objets;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author nicol
 */
public class VBoxMesEncheres extends VBox{
    private VuePrincipale main;
    private List<Encheres> encheres;
    private VBoxEncheres vboxencheres;

    public VBoxMesEncheres(VuePrincipale main, VBoxEncheres vboxencheres) {
        this.main = main;
        this.vboxencheres = vboxencheres;
        this.setSpacing(50);
        int id1 = this.main.getUtilisateurs().getUtilisateurID();
        try {
            this.encheres = Encheres.encheresUtilisateur(this.main.getUtilisateurs().getConBdD(), id1);
        } catch (SQLException ex) {
            this.getChildren().add(new Label("Problème BdD : " + ex.getLocalizedMessage()));
        }

        int taille = this.encheres.size();
        this.getChildren().add(new HBoxTitres(this.main));
        for (int i = 0; i < taille; i++) {
            
            this.getChildren().add(new AfficherEncheresPerso(this.main, this.encheres.get(i), this.vboxencheres ));
        }
        this.setPadding(new javafx.geometry.Insets(20, 20, 20, 200));
    }

    
    
    
}
