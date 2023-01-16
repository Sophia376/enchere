/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.espinola.infos3.Interface.vues;

import fr.insa.espinola.infos3.Interface.VuePrincipale;
import fr.insa.espinola.infos3.bdd.GestionbD;
import fr.insa.espinola.infos3.tables.Clients;
import fr.insa.espinola.infos3.tables.Encheres;
import fr.insa.espinola.infos3.tables.Objets;
import java.sql.SQLException;
import java.util.List;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 *
 * @author nicol
 */
public class AfficherEncheresPerso extends GridPane {

    private VuePrincipale main;
    private VBoxEncheres vboxencheres;
    private List<Encheres> encheres;
    
    public AfficherEncheresPerso(VuePrincipale main, VBoxEncheres vboxencheres) {
        this.setHgap(150);
        this.setVgap(25);
        this.main = main;
        this.vboxencheres = vboxencheres;
        int id1 = this.main.getUtilisateurs().getUtilisateurID();
        Label t1 = new Label("Enchères que vous gagnez");
        Label t2 = new Label("Enchères que vous perdez");
        Label t3 = new Label("Enchères terminées");
        Font font = Font.font("Arial", FontWeight.BOLD, 16);
        t1.setFont(font);
        t2.setFont(font);
        t3.setFont(font);
        this.add(t1, 0, 0);
        this.add(t2, 1, 0);
        this.add(t3, 2, 0);
        int lig0 = 1;
        int lig1 = 1;
        int lig2 = 1;
        
        try {
            this.encheres = Encheres.DernieresEncheres(this.main.getUtilisateurs().getConBdD(), this.main.getUtilisateurs().getUtilisateurID());
            int taille = this.encheres.size();
            for (int i = 0; i < taille; i++) {
                boolean termine = GestionbD.EnchereTerminee(Objets.ConversionFinObjet(this.main.getUtilisateurs().getConBdD(), this.encheres.get(i).getSur()),Objets.ConversionDebutObjet(this.main.getUtilisateurs().getConBdD(), this.encheres.get(i).getSur()));
            
                boolean gagne = GestionbD.DenierEncheri(this.main.getUtilisateurs().getConBdD(), Clients.ConversionIdClient(this.main.getUtilisateurs().getConBdD(),this.encheres.get(i).getDe()), this.encheres.get(i).getSur());
                if(termine){
                    this.add(new AfficherEnchereTerminee(this.main, this.encheres.get(i), gagne), 2, lig2);
                    lig2++;
                }else{
                    if(gagne){
                        this.add(new AfficherEnchereGagne(this.main, this.encheres.get(i)), 0, lig0);
                        lig0++;
                    }else{
                        this.add(new AfficherEncherePerd(this.main, this.encheres.get(i), this.vboxencheres), 1, lig1);
                        lig1++;
                    }
                }
            
            }
            
        } catch (SQLException ex) {
            this.getChildren().add(new Label("Problème BdD : " + ex.getLocalizedMessage()));
        }
        this.setPadding(new javafx.geometry.Insets(20, 20, 20, 100));

    }
    
}
