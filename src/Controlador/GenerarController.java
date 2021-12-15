
package Controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Merly Jimenez
 */
public class GenerarController implements Initializable {

    @FXML
    private Button btn_Crear;
    @FXML
    private TextField txt_CantP;
    @FXML
    private Button btn_Salir;

    private int cant=0;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        this.txt_CantP.setText("");
    }    

    @FXML
    private void Crear_Procesos(ActionEvent event) {
        if(!"".equalsIgnoreCase(this.txt_CantP.getText())){
            
            try{
                cant = Integer.parseInt(this.txt_CantP.getText());
                
                Stage myStage = (Stage) this.btn_Crear.getScene().getWindow();
                myStage.close();
                
            }catch(NumberFormatException e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setTitle("Error");
                alert.setContentText("Formato incorrecto \nPor favor escriba numeros enteros");
                alert.showAndWait();
            }
            this.txt_CantP.setText("");
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Para iniciar primero debe ingresar la cantidad de procesos\ncon la que desea iniciar la simulación");
            alert.showAndWait();
        }
    }

    @FXML
    private void Salir(ActionEvent event) {
        Stage myStage = (Stage) this.btn_Salir.getScene().getWindow();
        myStage.close();
    }
    
    public int getCantidad(){
        return cant;
    }
    
}
