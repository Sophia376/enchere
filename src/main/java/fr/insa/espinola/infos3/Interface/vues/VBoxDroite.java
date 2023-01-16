/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.espinola.infos3.Interface.vues;

import com.sun.prism.paint.Color;
import fr.insa.espinola.infos3.Interface.VuePrincipale;
import fr.insa.espinola.infos3.tables.Clients;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Optional;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Border;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

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
        this.setSpacing(15);
        this.setPadding(new javafx.geometry.Insets(20, 20, 20, 20));
        InputStream complet = this.getClass().getResourceAsStream("images/IconeProfil.png");
        ImageView imageView = new ImageView(new Image(complet));

        this.propose = new ToggleButton("Publier un Objet");
        this.propose.setOnAction((event) -> {
            this.main.setPagePrincipale(new PublierObjet(this.main));
        });
        try {

            Label label = new Label(Clients.ConversionEmailNPClient(this.main.getUtilisateurs().getConBdD(), this.main.getUtilisateurs().getUtilisateurEmail()));
            label.setFont(new Font("Roboto", 15));
            imageView.setFitWidth(50); // fixer la largeur à 200 pixels
            imageView.setFitHeight(50); // fixer la hauteur à 200 pixels
            imageView.setPreserveRatio(true); // conserver le ratio de l'image
            this.getChildren().add(imageView);
            this.getChildren().add(label);
            this.getChildren().add(this.propose);
            this.getChildren().add(this.deconnexion);
            this.setAlignment(Pos.TOP_CENTER);
        } catch (SQLException ex) {

        }
        var image = new Image("https://th.bing.com/th/id/OIP.cSCTCMoEpXHDj6Xt1RXcgAHaNK?w=115&h=184&c=7&r=0&o=5&pid=1.7");
        var bgImage = new BackgroundImage(
                image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(1.0, 1.0, true, true, false, false)
        );
        this.setBackground(new Background(bgImage));
        
        
        this.deconnexion.setPrefSize(120, 15);
        this.propose.setPrefSize(120, 15);
        this.deconnexion.setFont(new Font("Roboto", 13));
        this.propose.setFont(new Font("Roboto", 13));

        
    }

    public void Deconnexion() {
        this.main.getUtilisateurs().setUtilisateur(Optional.empty());
        this.main.setPagePrincipale(new AffichageBienvenue(this.main));
        this.main.setGauche(new Vide(main));
        this.main.setDroite(new Vide(main));
    }

}
