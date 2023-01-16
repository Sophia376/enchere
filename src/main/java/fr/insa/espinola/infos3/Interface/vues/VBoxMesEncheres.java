/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.espinola.infos3.Interface.vues;

import fr.insa.espinola.infos3.Interface.VuePrincipale;
import fr.insa.espinola.infos3.tables.Encheres;
import java.sql.SQLException;
import java.util.List;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 *
 * @author nicol
 */
public class VBoxMesEncheres extends VBox {

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

            this.getChildren().add(new AfficherEncheresPerso(this.main, this.vboxencheres));
        }
        this.setPadding(new javafx.geometry.Insets(20, 20, 20, 100));
    }

}
