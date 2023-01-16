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
public class AfficherEnchereGagne extends GridPane{
    private VuePrincipale main;
    private Encheres enchere;

    public AfficherEnchereGagne(VuePrincipale main, Encheres enchere) {
        this.main = main;
        this.enchere = enchere;
        
        try{
            int lig = 0;
            this.add(new Label("Vous gagnez l'enchère sur : " + Objets.ConversionIdObjet(this.main.getUtilisateurs().getConBdD(),this.enchere.getSur())), 0, lig);
            lig++;
            this.add(new Label("Le montant actuel de l'enchère est de : " + enchere.getMontant() + "€"), 0, lig);
        }catch(SQLException ex){
            
        }
  
    }
    
    
}
