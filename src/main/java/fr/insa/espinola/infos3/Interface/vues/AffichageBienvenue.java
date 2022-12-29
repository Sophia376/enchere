
package fr.insa.espinola.infos3.Interface.vues;

import fr.insa.espinola.infos3.Interface.VuePrincipale;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleButton;
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
    
    public AffichageBienvenue(VuePrincipale main) {
        this.main = main;
        Label titre = new Label("Bienvenue sur le site d'enchères le plus ouf du monde");
        Font font = Font.font("Baskerville",FontWeight.BOLD,  35);
        titre.setTextFill(Color.web("#D80110"));
        titre.setFont(font);
        this.getChildren().add(titre);
        this.getChildren().add(new Label("merci de vous connecter"));
        TextArea taMoche = new TextArea("Cette interface en javaFX est la traduction quasi à l'identique\n"
                + "d'une interface web faite en vaadin\n"
                + "==> elle n'était déjà pas très belle en vaadin,\n"
                + "==> on pourrait faire moins moche et plus adapté à JavaFX");
        taMoche.setEditable(false);
        this.setSpacing(10);
        this.setPadding(new javafx.geometry.Insets(100,300,100,300));
        ToggleButton inscription = new ToggleButton("inscription");

        this.getChildren().add(inscription);
        this.getChildren().add(taMoche);

        
        
    }
    
}
