/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.espinola.infos3.Interface.vues;

import fr.insa.espinola.infos3.Interface.JavaFXUtils;
import fr.insa.espinola.infos3.Interface.VuePrincipale;
import fr.insa.espinola.infos3.bdd.GestionbD;
import fr.insa.espinola.infos3.tables.Clients;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

/**
 *
 * @author nicol
 */
public class Connexion extends GridPane {

    private VuePrincipale main;
    private TextField email;
    private PasswordField pass;
    private ToggleButton seconnecter;
    private ToggleButton retour;

    public Connexion(VuePrincipale main) {
        this.setHeight(1000);
        this.main = main;
        this.email = new TextField();
        this.pass = new PasswordField();
        this.seconnecter = new ToggleButton("Valider");
        this.retour = new ToggleButton("Retour");
        ButtonBar btnbar = new ButtonBar();
        btnbar.getButtons().addAll(this.retour, this.seconnecter);
        Label emailL = new Label("Email ");
        Label passL = new Label("Mot de passe ");

        this.retour.setOnAction((event) -> {
            this.main.setPagePrincipale(new AffichageBienvenue(this.main));
        });

        int lig = 0;
        this.add(emailL, 0, lig, 1, 1);
        this.add(this.email, 1, lig, 1, 1);
        lig++;
        this.add(passL, 0, lig, 1, 1);
        this.add(this.pass, 1, lig, 1, 1);
        lig++;
        this.add(btnbar, 1, lig, 1, 1);
        lig++;

        this.setHgap(5);
        this.setVgap(5);
        this.setPadding(new Insets(20, 20, 20, 20));

        emailL.setFont(new Font("Roboto", 20));
        passL.setFont(new Font("Roboto", 19));
        emailL.setStyle("-fx-text-fill: black");
        passL.setStyle("-fx-text-fill: black");
        emailL.setPrefWidth(150);
        passL.setPrefSize(150, 50);

        this.email.setPrefWidth(200);
        this.pass.setPrefWidth(200);

        this.seconnecter.setOnAction((event) -> {
            this.SeConnecter();
        });

        var image = new Image("https://wallpapercave.com/wp/wp1915308.jpg");
        var bgImage = new BackgroundImage(
                image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(1.0, 1.0, true, true, false, false)
        );

        this.setPrefSize(main.getWidth(), main.getHeight());
        this.setMinSize(main.getWidth(), main.getHeight());
        

        this.setBackground(new Background(bgImage));
        this.setAlignment(Pos.CENTER);
    }

    public void SeConnecter() {
        String mail = this.email.getText();
        String pass = this.pass.getText();
        try {
            Connection con = this.main.getUtilisateurs().getConBdD();
            Optional<Clients> utilisateur = GestionbD.login(con, mail, pass);
            if (utilisateur.isEmpty()) {
                JavaFXUtils.showErrorInAlert("Email ou mot de passe invalide");
            } else {
                this.main.getUtilisateurs().setUtilisateur(utilisateur);
                this.main.setPagePrincipale(new VBoxEncheres(this.main));
                this.main.setGauche(new VBoxGauche(this.main));
                this.main.setDroite(new VBoxDroite(this.main));
            }
        } catch (SQLException ex) {
            JavaFXUtils.showErrorInAlert("Problème interne : " + ex.getLocalizedMessage());
        }
    }

}
