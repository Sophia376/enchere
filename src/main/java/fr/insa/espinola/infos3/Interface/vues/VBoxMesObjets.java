/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.espinola.infos3.Interface.vues;

import fr.insa.espinola.infos3.Interface.VuePrincipale;
import fr.insa.espinola.infos3.tables.Objets;
import java.sql.SQLException;
import java.util.List;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 *
 * @author Sophia
 */
public class VBoxMesObjets extends VBox {

    private VuePrincipale main;
    private List<Objets> objets;
    private VBoxEncheres vboxencheres;

    public VBoxMesObjets(VuePrincipale main, VBoxEncheres vboxencheres) {
        this.main = main;
        this.vboxencheres = vboxencheres;
        this.setSpacing(50);
        int id1 = this.main.getUtilisateurs().getUtilisateurID();
        try {
            this.objets = Objets.objetsUtilisateur(this.main.getUtilisateurs().getConBdD(), id1);
        } catch (SQLException ex) {
            this.getChildren().add(new Label("Problème BdD : " + ex.getLocalizedMessage()));
        }

        int taille = this.objets.size();
        for (int i = 0; i < taille; i++) {
            this.getChildren().add(new AfficherObjetPerso(this.main, this.objets.get(i), this.vboxencheres));
        }
        this.setPadding(new javafx.geometry.Insets(20, 20, 20, 200));
    }
}
