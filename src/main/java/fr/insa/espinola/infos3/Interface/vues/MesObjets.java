/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.espinola.infos3.Interface.vues;

import fr.insa.espinola.infos3.Interface.VuePrincipale;
import fr.insa.espinola.infos3.tables.Objets;
import java.io.ByteArrayInputStream;
import java.sql.SQLException;
import java.util.List;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

/**
 *
 * @author Sophia
 */
public class MesObjets extends GridPane {

    private VuePrincipale main;
    private List<Objets> objets;
    private VBoxEncheres vboxencheres;

    public MesObjets(VuePrincipale main, VBoxEncheres vboxencheres) {
        this.main = main;
        this.vboxencheres = vboxencheres;
        this.setHgap(100);
        this.setVgap(50);
        
        this.setPadding(new javafx.geometry.Insets(20,20,20,200));
        int id1 = this.main.getUtilisateurs().getUtilisateurID();
        try {
            this.objets = Objets.objetsUtilisateur(this.main.getUtilisateurs().getConBdD(), id1);
        } catch (SQLException ex) {
            this.getChildren().add(new Label("Problème BdD : " + ex.getLocalizedMessage()));
        }

        int taille = this.objets.size();
        for (int i = 0; i < taille; i++) {
            this.add(new AfficherObjetPerso(this.main,this.objets.get(i), this.vboxencheres), 1, i);
                byte[] imageBytes = this.objets.get(i).getImage();
                if (imageBytes != null){
                    Image image = new Image(new ByteArrayInputStream(imageBytes));
                    ImageView imageView = new ImageView(image);
                    imageView.setFitWidth(300); // fixer la largeur à 200 pixels
                    imageView.setFitHeight(150); // fixer la hauteur à 200 pixels
                    imageView.setPreserveRatio(true); // conserver le ratio de l'image
                    this.add(imageView, 0, i);
                }
        }
        
    }
}
