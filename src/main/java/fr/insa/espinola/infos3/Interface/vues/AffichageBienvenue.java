
package fr.insa.espinola.infos3.Interface.vues;

import fr.insa.espinola.infos3.Interface.VuePrincipale;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;

/**
 *
 * @author nicol
 */
public class AffichageBienvenue extends Pane{
    private VuePrincipale main;
    private VBoxBienvenue vbox; 
    
    public AffichageBienvenue(VuePrincipale main) {
        this.main = main;
        this.vbox = new VBoxBienvenue(this.main);
        
        
        var image = new Image("https://img.freepik.com/photos-premium/fond-motifs-geometriques-pastel_53876-149767.jpg?w=1060");
        var bgImage = new BackgroundImage(
        image,
        BackgroundRepeat.NO_REPEAT,
        BackgroundRepeat.NO_REPEAT,
        BackgroundPosition.DEFAULT,
        new BackgroundSize(1.0, 1.0, true, true, false, false)
        );
        this.setBackground(new Background(bgImage));
                               
        
        this.getChildren().addAll(this.vbox);
            
        
        
    }
    
}
