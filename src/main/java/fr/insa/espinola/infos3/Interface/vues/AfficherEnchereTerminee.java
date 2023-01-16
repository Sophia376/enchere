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
import javafx.scene.layout.GridPane;

/**
 *
 * @author nicol
 */
public class AfficherEnchereTerminee extends GridPane{
    private VuePrincipale main;
    private Encheres enchere;

    public AfficherEnchereTerminee(VuePrincipale main,Encheres enchere, boolean gagne) {
        
        this.main = main;
        this.enchere = enchere;
        int lig = 0;
        try{
            if(gagne){
                
                this.add(new Label("Vous avez gagné l'enchère sur : " + Objets.ConversionIdObjet(this.main.getUtilisateurs().getConBdD(),this.enchere.getSur())), 0, lig);
                lig++;
                this.add(new Label("Elle a pris fin le " + Objets.ConversionFinObjet(this.main.getUtilisateurs().getConBdD(), this.enchere.getSur())), 0, lig);
                lig++; 
                this.add(new Label("Vous devez " + enchere.getMontant() + "€ à l'utilisateur : " + Clients.ConversionIdClient(this.main.getUtilisateurs().getConBdD(),Objets.ConversionProposePar(this.main.getUtilisateurs().getConBdD(),this.enchere.getSur()))), 0, lig);
            }else{
                this.add(new Label("Vous avez perdu l'enchère sur : " + Objets.ConversionIdObjet(this.main.getUtilisateurs().getConBdD(),this.enchere.getSur())), 0, lig);
                lig++;
                this.add(new Label("Elle a pris fin le " + Objets.ConversionFinObjet(this.main.getUtilisateurs().getConBdD(), this.enchere.getSur())), 0, lig);
                lig++;
                this.add(new Label("L'utilisateur " + Clients.DernierEncherisseur(this.main.getUtilisateurs().getConBdD(), this.enchere.getSur()) + " a gagné l'enchère avec un montant de : " + enchere.getMontant() + "€") , 0, lig);
                
            }
        }catch(SQLException ex){
            
        }
    }
    
}
