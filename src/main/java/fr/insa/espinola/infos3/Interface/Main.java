
package fr.insa.espinola.infos3.Interface;

import java.io.InputStream;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

 
public class Main extends Application {

    @Override
    public void start(Stage stage) {
        Scene sc = new Scene(new VuePrincipale(), 1500,700);
        // stage.setWidth(1200);
        //stage.setHeight(600);
        
        stage.setTitle("Encheres2000");
        InputStream complet = this.getClass().getResourceAsStream("vues/images/Encheres.png");
        if (complet != null) {
            stage.getIcons().add(new Image(complet));
        }
        stage.setScene(sc);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}