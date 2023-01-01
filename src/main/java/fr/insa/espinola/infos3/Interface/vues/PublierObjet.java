/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.espinola.infos3.Interface.vues;

import fr.insa.espinola.infos3.Interface.JavaFXUtils;
import fr.insa.espinola.infos3.Interface.VuePrincipale;
import fr.insa.espinola.infos3.tables.Objets;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.GridPane;

/**
 *
 * @author Sophia
 */
public class PublierObjet extends GridPane {

    private VuePrincipale main;

    private TextField titre;
    private TextField description;
    private Timestamp debut;
    private TextField fin;
    private TextField prixbase;
    private TextField proposer;
    private TextField categorie;
    private TextField prix;
    private ToggleButton valider;
    private ToggleButton retour;
    private Objets objet;

    public PublierObjet(VuePrincipale main) {
        this.main = main;
        this.titre = new TextField("titre");
        this.description = new TextField("description");
        this.fin = new TextField("fin: 2018-09-01 09:01:15 ");
        this.prixbase = new TextField("prixbase");
        this.categorie = new TextField("categorie");
        this.proposer = new TextField("id utilisateur");
        this.prix = new TextField("prix");
        this.valider = new ToggleButton("Valider");
        this.retour = new ToggleButton("Retour");

        this.retour.setOnAction((event) -> {
            this.main.setPagePrincipale(new AffichageBienvenue(this.main));
        });

        this.valider.setOnAction((event) -> {
            Connection con = this.main.getUtilisateurs().getConBdD();
            String titre = this.titre.getText();
            String description = this.description.getText();
            Timestamp debut = Timestamp.from(Instant.now());
            String str = this.fin.getText();
            Timestamp fin1 = Timestamp.valueOf(str);
            String fin = this.fin.getText();
            int prixbase = Integer.parseInt(this.prixbase.getText());
            int proposer1 = Integer.parseInt(this.proposer.getText());
            int categorie = Integer.parseInt(this.categorie.getText());
            int prix1 = Integer.parseInt(this.prix.getText());
            try {
                int id = Objets.CreerObjet(con, titre, description, debut, fin1, prixbase, proposer1, categorie, prix1);
                this.objet = new Objets(id, titre, description, debut, fin1, prixbase, proposer1, categorie, prix1);
                JavaFXUtils.showInfoInAlert("Objet  " + titre + " créé");
                this.main.setPagePrincipale(new VBoxEncheres(this.main));   
                this.main.setGauche(new VBoxGauche(this.main));
                this.main.setDroite(new VBoxDroite(this.main));
            } catch (SQLException ex) {
                JavaFXUtils.showErrorInAlert("Problème BdD : " + ex.getLocalizedMessage());
            }
        });
        int lig = 0;
        this.add(this.retour, 0, lig);
        lig++;
        this.add(new Label("titre : "), 0, lig);
        this.add(this.titre, 1, lig);
        lig++;
        this.add(new Label("description : "), 0, lig);
        this.add(this.description, 1, lig);
        lig++;
        this.add(new Label("fin : "), 0, lig);
        this.add(this.fin, 1, lig);
        lig++;
        this.add(new Label("prixbase : "), 0, lig);
        this.add(this.prixbase, 1, lig);
        lig++;
        this.add(new Label("proposer : "), 0, lig);
        this.add(this.proposer, 1, lig);
        lig++;
        this.add(new Label("categorie : "), 0, lig);
        this.add(this.categorie, 1, lig);
        lig++;
        this.add(new Label("prix : "), 0, lig);
        this.add(this.prix, 1, lig);

        lig++;
        this.add(this.valider, 0, lig);
    }

}
