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
public class Connexion extends GridPane{
    private VuePrincipale main;
    
    private TextField email;
    private PasswordField pass;
    private ToggleButton seconnecter;
    
    public Connexion(VuePrincipale main){
        this.main = main;
        this.email = new TextField();
        this.pass = new PasswordField();
        this.seconnecter = new ToggleButton("Se connecter");
        
        int lig = 0;
        this.add(new Label("email : "), 0, lig);
        this.add(this.email, 1, lig);
        lig ++;
        this.add(new Label("mot de passe : "), 0, lig);
        this.add(this.pass, 1, lig);
        lig ++;
        this.add(this.seconnecter, 0, lig,2,1);
        lig ++;
        this.seconnecter.setOnAction((event) -> {
            this.SeConnecter();
        });
        
        
    }
    
    public void SeConnecter() {
        String mail = this.email.getText();
        String pass = this.pass.getText();
        try {
            Connection con = this.main.getUtilisateurs().getConBdD();
            Optional<Clients> utilisateur = GestionbD.login(con, mail, pass);
            if(utilisateur.isEmpty()) {
                JavaFXUtils.showErrorInAlert("Email ou mot de passe invalide");
            } else {
                this.main.getUtilisateurs().setUtilisateur(utilisateur);
                this.main.setPagePrincipale(new AffichageBienvenue(this.main));
            }
        } catch (SQLException ex) {
            JavaFXUtils.showErrorInAlert("Problème interne : " + ex.getLocalizedMessage());
        }        
    }
}
