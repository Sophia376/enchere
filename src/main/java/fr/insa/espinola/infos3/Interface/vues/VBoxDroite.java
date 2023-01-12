/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.espinola.infos3.Interface.vues;

import fr.insa.espinola.infos3.Interface.JavaFXUtils;
import fr.insa.espinola.infos3.Interface.VuePrincipale;
import java.io.InputStream;
import java.util.Optional;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

/**
 *
 * @author nicol
 */
public class VBoxDroite extends VBox {

    private VuePrincipale main;
    private ToggleButton deconnexion;
    private ToggleButton propose;

    public VBoxDroite(VuePrincipale main) {
        this.main = main;

        this.deconnexion = new ToggleButton("Se déconnecter");
        this.deconnexion.setOnAction((event) -> {
            this.Deconnexion();
        });
        this.setSpacing(20);
        this.setPadding(new javafx.geometry.Insets(20, 20, 20, 20));
        InputStream complet = this.getClass().getResourceAsStream("images/IconeProfil.png");
        ImageView imageView = new ImageView(new Image(complet));
        
        

        this.propose = new ToggleButton("Publier un Objet");
        this.propose.setOnAction((event) -> {
            this.main.setPagePrincipale(new PublierObjet(this.main));
        });
        imageView.setFitWidth(50); // fixer la largeur à 200 pixels
        imageView.setFitHeight(50); // fixer la hauteur à 200 pixels
        imageView.setPreserveRatio(true); // conserver le ratio de l'image
        this.getChildren().add(imageView);
        this.getChildren().add(this.propose);
        this.getChildren().add(this.deconnexion);

    }

    public void Deconnexion() {
        this.main.getUtilisateurs().setUtilisateur(Optional.empty());
        this.main.setPagePrincipale(new AffichageBienvenue(this.main));
        this.main.setGauche(new Vide(main));
        this.main.setDroite(new Vide(main));
    }

}
