/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.espinola.infos3.Interface.vues;

import fr.insa.espinola.infos3.Interface.JavaFXUtils;
import fr.insa.espinola.infos3.Interface.VuePrincipale;
import java.util.Optional;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.VBox;

/**
 *
 * @author nicol
 */
public class VBoxDroite extends VBox {

    private VuePrincipale main;
    private ToggleButton deconnexion;
    private ToggleButton propose;
    private ToggleButton supprimer;

    public VBoxDroite(VuePrincipale main) {
        this.main = main;

        this.deconnexion = new ToggleButton("Se déconnecter");
        this.deconnexion.setOnAction((event) -> {
            this.Deconnexion();
        });
        this.setSpacing(20);
        this.setPadding(new javafx.geometry.Insets(20, 20, 20, 20));
        this.getChildren().add(this.deconnexion);

        this.propose = new ToggleButton("Publier un Objet");
        this.propose.setOnAction((event) -> {
            this.main.setPagePrincipale(new PublierObjet(this.main));
        });
        this.getChildren().add(this.propose);

        this.supprimer = new ToggleButton("Supprimer un Objet");
        this.supprimer.setOnAction((event) -> {
            this.main.setPagePrincipale(new SupprimerObjet(this.main));
        });
        this.getChildren().add(this.supprimer);
    }

    public void Deconnexion() {
        this.main.getUtilisateurs().setUtilisateur(Optional.empty());
        this.main.setPagePrincipale(new AffichageBienvenue(this.main));
        this.main.setGauche(new Vide(main));
        this.main.setDroite(new Vide(main));
    }

}
