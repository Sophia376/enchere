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
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.GridPane;

/**
 *
 * @author nicol
 */
public class Inscription extends GridPane{
    private VuePrincipale main;

    private TextField nom;
    private TextField prenom;
    private TextField email;
    private TextField codepostal;
    private PasswordField pass;
    private ToggleButton valider;
    
    public Inscription(VuePrincipale main){
        this.main = main;
        this.nom = new TextField("nom");
        this.prenom = new TextField("prenom");
        this.email = new TextField("email");
        this.pass = new PasswordField();
        this.codepostal = new TextField("code postal");
        this.valider = new ToggleButton("Valider");
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
        this.add(new Label("Nom : "), 0, lig);
        this.add(this.nom, 1, lig);
        lig++;
        this.add(new Label("Prénom : "), 0, lig);
        this.add(this.prenom, 1, lig);
        lig++;
        this.add(new Label("Email : "), 0, lig);
        this.add(this.email, 1, lig);
        lig++;
        this.add(new Label("Mot de passe : "), 0, lig);
        this.add(this.pass, 1, lig);
        lig++;
        this.add(new Label("Code postal : "), 0, lig);
        this.add(this.codepostal, 1, lig);
        
        lig++;
        this.add(this.valider, 0, lig, 6, 5);
        lig++;
    }
    
    
}
