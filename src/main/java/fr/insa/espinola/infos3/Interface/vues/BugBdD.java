
package fr.insa.espinola.infos3.Interface.vues;

import fr.insa.espinola.infos3.Interface.VuePrincipale;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;


/**
 *
 * @author nicol
 */
public class BugBdD extends VBox {
    
    private VuePrincipale main;
    
    public BugBdD(VuePrincipale main) {
        this.main = main;
        this.getChildren().add(new Label("Base de donnée non accessible"));
    }
    
}
