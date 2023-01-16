/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.espinola.infos3.Interface.vues;

import fr.insa.espinola.infos3.Interface.VuePrincipale;
import fr.insa.espinola.infos3.tables.Clients;
import fr.insa.espinola.infos3.tables.Encheres;
import fr.insa.espinola.infos3.tables.Objets;
import java.sql.SQLException;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.GridPane;

/**
 *
 * @author nicol
 */
public class AfficherEncherePerd extends GridPane{
    private VuePrincipale main;
    private ToggleButton encherir;
    private VBoxEncheres vboxencheres;
    private Encheres enchere;

    public AfficherEncherePerd(VuePrincipale main, Encheres enchere, VBoxEncheres vboxencheres){
        this.main = main;
        this.encherir = new ToggleButton("Enchérir");
        this.enchere = enchere;
        this.vboxencheres = vboxencheres;
        this.encherir.setOnAction((event) -> {
            try{
                AfficherObjet.Encherir(this.main.getUtilisateurs().getConBdD(),Encheres.ConversionIdObjet(this.main.getUtilisateurs().getConBdD(),this.enchere.getSur()),this.main.getUtilisateurs().getUtilisateurID(),this.vboxencheres);
                this.vboxencheres.getPersoEncheres().setContent(new AfficherEncheresPerso(this.main, this.vboxencheres));
            }catch(SQLException ex){
                
            }
        });
        try{
            int lig = 0;
            this.add(new Label("Vous perdez l'enchère sur : " + Objets.ConversionIdObjet(this.main.getUtilisateurs().getConBdD(),this.enchere.getSur())), 0, lig);
            lig++;
            this.add(new Label("Le dernier à avoir enchéri est : " + Clients.ConversionEmailNPClient(this.main.getUtilisateurs().getConBdD(),Clients.DernierEncherisseur(this.main.getUtilisateurs().getConBdD(), this.enchere.getSur()))), 0, lig);
            lig++;
            this.add(new Label("Le montant actuel de l'enchère est de : " + enchere.getMontant() + "€"), 0, lig);
            this.add(this.encherir, 1, lig/2);
        }catch(SQLException ex){
            
        }
    }
    
    
}
