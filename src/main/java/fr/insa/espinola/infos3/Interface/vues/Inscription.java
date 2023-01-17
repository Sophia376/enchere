/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.espinola.infos3.Interface.vues;

import fr.insa.espinola.infos3.Interface.JavaFXUtils;
import fr.insa.espinola.infos3.Interface.VuePrincipale;
import fr.insa.espinola.infos3.tables.Clients;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;
import javafx.geometry.Insets;
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
public class Inscription extends GridPane {

    private VuePrincipale main;

    private TextField nom;
    private TextField prenom;
    private TextField email;
    private TextField codepostal;
    private PasswordField pass;
    private ToggleButton valider;
    private ToggleButton retour;

    public Inscription(VuePrincipale main) {
        this.main = main;
        this.nom = new TextField("nom");
        this.prenom = new TextField("prenom");
        this.email = new TextField("email");
        this.pass = new PasswordField();
        this.codepostal = new TextField("code postal");
        this.valider = new ToggleButton("Valider");
        this.retour = new ToggleButton("Retour");
        ButtonBar btnbar = new ButtonBar();
        btnbar.getButtons().addAll(this.retour, this.valider);

        Label emailL = new Label("Email : ");
        Label nomL = new Label("Nom : ");
        Label prenomL = new Label("Prenom : ");
        Label passeL = new Label("Mot de passe : ");
        Label codeL = new Label("Code Postal : ");

        this.retour.setOnAction((event) -> {
            this.main.setPagePrincipale(new AffichageBienvenue(this.main));
        });

        this.valider.setOnAction((event) -> {
            Connection con = this.main.getUtilisateurs().getConBdD();
            String nom = this.nom.getText();
            String prenom = this.prenom.getText();
            String email = this.email.getText();
            String pass = this.pass.getText();
            String codepostal = this.codepostal.getText();
            try {
                int id = Clients.CreerClient(con, nom, prenom, email, codepostal, pass);
                Clients utilisateur = new Clients(id, nom, prenom, email, pass, codepostal);
                this.main.getUtilisateurs().setUtilisateur(Optional.of(utilisateur));
                JavaFXUtils.showInfoInAlert("Utilisateur " + email + " créé");
                this.main.setPagePrincipale(new AffichageBienvenue(this.main));

            } catch (Clients.EmailExisteDejaException ex) {
                JavaFXUtils.showErrorInAlert("Cet email existe déjà, choississez en un autre");
            } catch (SQLException ex) {
                JavaFXUtils.showErrorInAlert("Problème BdD : " + ex.getLocalizedMessage());
            }
        });
        int lig = 0;
        this.add(nomL, 0, lig);
        this.add(this.nom, 1, lig);
        lig++;
        this.add(prenomL, 0, lig);
        this.add(this.prenom, 1, lig);
        lig++;
        this.add(emailL, 0, lig);
        this.add(this.email, 1, lig);
        lig++;
        this.add(passeL, 0, lig);
        this.add(this.pass, 1, lig);
        lig++;
        this.add(codeL, 0, lig);
        this.add(this.codepostal, 1, lig);
        lig++;
        this.add(btnbar, 1, lig);

        this.setHgap(5);
        this.setVgap(5);
        this.setPadding(new Insets(20, 20, 20, 20));

        emailL.setFont(new Font("Roboto", 17));
        passeL.setFont(new Font("Roboto", 17));
        nomL.setFont(new Font("Roboto", 17));
        prenomL.setFont(new Font("Roboto", 17));
        codeL.setFont(new Font("Roboto", 17));

        emailL.setStyle("-fx-text-fill: white");
        passeL.setStyle("-fx-text-fill: white");
        nomL.setStyle("-fx-text-fill: white");
        prenomL.setStyle("-fx-text-fill: white");
        codeL.setStyle("-fx-text-fill: white");

        this.email.setPrefWidth(200);
        this.pass.setPrefWidth(200);

        var image = new Image("https://images.fineartamerica.com/images/artworkimages/mediumlarge/1/yellow-fractel-david-lane.jpg");
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

    }

}
