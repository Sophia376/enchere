
package fr.insa.espinola.infos3.Interface.vues;

import fr.insa.espinola.infos3.Interface.VuePrincipale;
import java.io.InputStream;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 *
 * @author nicol
 */
public class AffichageBienvenue extends VBox{
    private VuePrincipale main;
    private HBoxBoutons boutons; 
    
    public AffichageBienvenue(VuePrincipale main) {
        this.main = main;
        this.boutons = new HBoxBoutons(this.main);
        
        this.setSpacing(20);
        this.setPadding(new javafx.geometry.Insets(100,300,100,300));
        
        Label titre = new Label("Bienvenue sur le site d'enchères le plus ouf du monde");
        Font font = Font.font("Baskerville",FontWeight.BOLD,  35);
        titre.setTextFill(Color.web("#D80110"));
        titre.setFont(font);
        
        TextArea taMoche = new TextArea("Cette interface en javaFX est la traduction quasi à l'identique\n"
                + "d'une interface web faite en vaadin\n"
                + "==> elle n'était déjà pas très belle en vaadin,\n"
                + "==> on pourrait faire moins moche et plus adapté à JavaFX");
        taMoche.setEditable(false);
        Image image1 = new Image("https://img.freepik.com/photos-premium/fond-motifs-geometriques-pastel_53876-149767.jpg?w=1060");
        BackgroundSize bSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false);
            this.setBackground(new Background(new BackgroundImage(image1,
             BackgroundRepeat.NO_REPEAT,
            BackgroundRepeat.NO_REPEAT,
             BackgroundPosition.CENTER,
             bSize)));
        InputStream complet = this.getClass().getResourceAsStream("LogoINSA.png");
        ImageView imageView = new ImageView(new Image(complet));                      
        
              
        this.getChildren().addAll(titre, taMoche, this.boutons, imageView);
            
        
        
    }
    
}
