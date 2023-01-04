/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.espinola.infos3.Interface.vues;

import fr.insa.espinola.infos3.Interface.VuePrincipale;
import fr.insa.espinola.infos3.tables.Categories;
import fr.insa.espinola.infos3.tables.Objets;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.VBox;

/**
 *
 * @author nicol
 */
public class VBoxGauche extends VBox {

    private VuePrincipale main;
    private ToggleButton choixcategorie;
    private int idcategorie;
    private List<Objets> objets;

    public VBoxGauche(VuePrincipale main) {
        this.main = main;
        this.idcategorie = -1;
        this.choixcategorie = new ToggleButton("Choisir une catégorie");
        this.getChildren().add(this.choixcategorie);

        this.choixcategorie.setOnAction((event) -> {
            this.idcategorie = choixCategorie();
            try {
                this.objets = Categories.ChoisirCategorie(this.main.getUtilisateurs().getConBdD(), this.idcategorie);

            } catch (SQLException ex) {
                this.getChildren().add(new Label("Problème BdD : " + ex.getLocalizedMessage()));
            }
            int taille = this.objets.size();
            for (int i = 0; i < taille; i++) {
                // affiche les obje3ts choisis
            }
        });
        this.setPadding(new javafx.geometry.Insets(20, 20, 20, 20));

    }

    public int choixCategorie() {
        int idcategorie = -1;
        ArrayList<Categories> categories = new ArrayList();
        try {
            categories = Categories.ToutesLesCategories(this.main.getUtilisateurs().getConBdD());
        } catch (SQLException ex) {
            this.getChildren().add(new Label("Problème BdD : " + ex.getLocalizedMessage()));
        }
        ChoiceDialog dialog = new ChoiceDialog(categories.get(0), categories);
        dialog.setTitle("Choix d'une catégorie");
        dialog.setHeaderText("Choisir la catégorie de votre objet");

        Optional<Categories> saisie = dialog.showAndWait();

        if (saisie.isPresent()) {
            Categories categorieSaisie = saisie.get();
            idcategorie = categorieSaisie.getId();
        }
        return idcategorie;
    }
}
