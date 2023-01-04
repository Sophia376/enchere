/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.espinola.infos3.Interface.vues;

import fr.insa.espinola.infos3.Interface.JavaFXUtils;
import fr.insa.espinola.infos3.Interface.VuePrincipale;
import fr.insa.espinola.infos3.tables.Categories;
import fr.insa.espinola.infos3.tables.Clients;
import fr.insa.espinola.infos3.tables.Encheres;
import fr.insa.espinola.infos3.tables.Objets;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 *
 * @author nicol
 */
public class AfficherObjet extends GridPane{
    private VuePrincipale main;
    private Objets objet;
    private ToggleButton encherir;
    private VBoxEncheres vboxencheres;
    public AfficherObjet(VuePrincipale main, Objets objet, VBoxEncheres vboxencheres) {
        this.main = main;
        this.objet = objet;
        this.vboxencheres = vboxencheres;
        
        this.encherir = new ToggleButton("Enchérir");       
        this.encherir.setOnAction((event) -> {
            Encherir();
        });
        try{
            int lig = 0;
            Label titre = new Label(this.objet.getTitre());
            Font font = Font.font("Arial",FontWeight.BOLD,11);
            titre.setFont(font);
            this.add(titre,0,lig);
            lig++;
            this.add(new Label("Description : " + this.objet.getDescription()), 0, lig);
            lig ++;
            this.add(new Label("Date de début : " + this.objet.getDebut()), 0, lig);
            lig ++;
            this.add(new Label("Date de fin : " + this.objet.getFin()), 0, lig);
            lig ++;
            this.add(new Label("Prix de base : " + this.objet.getPrixbase() + "€") , 0, lig);
            lig ++;
            this.add(new Label("Proposé par : " + Clients.ConversionNomPrenomClient(this.main.getUtilisateurs().getConBdD(),this.objet.getProposer())), 0, lig);
            lig ++;
            this.add(new Label("Catégorie : " + Categories.ConversionIdCategorie(this.main.getUtilisateurs().getConBdD(),this.objet.getCategorie())), 0, lig);
            lig ++;
            this.add(new Label("Montant actuel de l'enchère : " + this.objet.getPrix() + "€") , 0, lig);
            lig ++;
            this.add(new Label("Dernier à avoir enchéri : " + Clients.ConversionEmailNPClient(this.main.getUtilisateurs().getConBdD(), Clients.DernierEncherisseur(this.main.getUtilisateurs().getConBdD(), this.objet.getId()))) , 0, lig);
            this.add(this.encherir, 10, lig/2);
        } catch (SQLException ex) {
            this.getChildren().add(new Label("Problème BdD : " + ex.getLocalizedMessage()));
        }
        
    }
    
    public void Encherir(){
        
        Timestamp quand = Timestamp.valueOf(LocalDateTime.now());
        
        
        Dialog<ButtonType> enchere = new Dialog<ButtonType>();
        enchere.setTitle("Enchérir");
        enchere.setHeaderText("Saisir le montant de votre enchère");
        Label mont = new Label("Montant : ");
        TextField mon = new TextField();
        GridPane prix = new GridPane();
        prix.add(mont, 1, 1);
        prix.add(mon, 2, 1);
        enchere.getDialogPane().setContent(prix);
        ButtonType saisie = new ButtonType("Valider", ButtonBar.ButtonData.OK_DONE);
        enchere.getDialogPane().getButtonTypes().add(saisie);
        Optional<ButtonType> p = enchere.showAndWait();
        p.ifPresent(r -> {
            Connection con = this.main.getUtilisateurs().getConBdD();
            try{
                try{
                    int montant = Integer.parseInt(mon.getText());
                    if (montant > this.objet.getPrix()){
                        Encheres.CreerEnchere(con, quand, montant, this.objet.getId(), this.main.getUtilisateurs().getUtilisateurID());
                        this.objet.setPrix(montant);
                        this.objet.NouveauPrix(con, this.objet.getId(), montant);
                        
                    }else{
                        JavaFXUtils.showErrorInAlert("Le montant saisie doit être supérieur à celui actuel");
                    }
                }catch(SQLException ex){
                }

            }catch(Exception e){
                JavaFXUtils.showErrorInAlert("Vous avez fait une erreur de saisie");
            }
            vboxencheres.getAllEncheres().setContent(new VBoxAllEncheres(this.main, this.vboxencheres));

        });
    }
    
    
}
