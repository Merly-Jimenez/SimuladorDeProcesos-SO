
package Controlador;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author Merly Jimenez
 */
public class MainSimulador extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        try{
            
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainSimulador.class.getResource("/Vista/Acciones.fxml"));
            Pane ventana = (Pane) loader.load();
            
            Scene scene = new Scene (ventana);
            primaryStage.setScene(scene);
            primaryStage.show();
                        
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
