
package fr.insa.espinola.infos3.Interface;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.stage.Stage;

 
public class Main extends Application {

    @Override
    public void start(Stage stage) {
        Scene sc = new Scene(new VuePrincipale());
        //Scene sc = new Scene(new VuePrincipale());
        stage.setWidth(1200);
        stage.setHeight(600);
        stage.setScene(sc);
        stage.setTitle("Encheres2000");
          stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}