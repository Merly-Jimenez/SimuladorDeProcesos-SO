
package Controlador;

import App.Proceso;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Merly Jimenez
 */
public class AccionesController implements Initializable {

    @FXML
    private Button btn_Ejecutar;
    @FXML
    private Button btn_Terminar;
    @FXML
    private TableView<Proceso> tblProcesos;
    @FXML
    private TableColumn col_Nom_Proceso;
    @FXML
    private TableColumn col_ID_Proceso;
    @FXML
    private TableColumn col_Estado_Proceso;
    @FXML
    private TableColumn col_Contador_Proceso;
    @FXML
    private Button btn_Camb_Estado;
    @FXML
    private RadioButton rbtn_Block;
    @FXML
    private RadioButton rbtn_Listo;
    @FXML
    private TextArea txt_Mensajes;
    @FXML
    private Button btn_Salir;
    @FXML
    private Button btn_CrearP;
    @FXML
    private ToggleGroup Estado;
    
    private ObservableList<Proceso> procesos = null;
    private static int total = 0;
    private int cant;

    /**
     * Initializes the controller class.
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        this.btn_Camb_Estado.setDisable(true);
        this.btn_Ejecutar.setDisable(true);
        this.btn_Terminar.setDisable(true);
        this.rbtn_Block.setDisable(true);
        this.rbtn_Listo.setDisable(true);
        
        this.col_Nom_Proceso.setCellValueFactory(new PropertyValueFactory("NomProceso"));
        this.col_ID_Proceso.setCellValueFactory(new PropertyValueFactory("IdProceso"));
        this.col_Estado_Proceso.setCellValueFactory(new PropertyValueFactory("EstadoDeProceso"));
        this.col_Contador_Proceso.setCellValueFactory(new PropertyValueFactory("ContadorPrograma"));
        
        procesos = FXCollections.observableArrayList();
        this.tblProcesos.setItems(procesos);
        
        final ObservableList<Proceso> selec_Tabla = this.tblProcesos.getSelectionModel().getSelectedItems();
        selec_Tabla.addListener(Selec_Tabla_Proceso);
        
    }
    
    @FXML
    private void Crear_Proceso(ActionEvent event) {
        
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vista/Generar.fxml"));
            Parent root = loader.load();
            
            GenerarController control = loader.getController();
            
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();
            
            cant = control.getCantidad();
            
            for(int i=1; i<=cant; i++){
                Random numRnd = new Random();
        
                int cont = numRnd.nextInt(20)+1;
                total++;

                Proceso proceso = new Proceso(total);
                proceso.setContadorPrograma(cont);

                procesos.add(proceso);
            }
            
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
        
    }

    @FXML
    private void Ejecutar_Proceso(ActionEvent event) {
        
        Proceso aux = getTablaProcesoSeleccionado();
        Random numRnd = new Random();
        int cont = aux.getContadorPrograma();
        
        this.txt_Mensajes.appendText("\n BLOQUE DE CONTROL DE PROCESOS");
        this.txt_Mensajes.appendText("\n Nombre: " + aux.getNomProceso());
        this.txt_Mensajes.appendText("\n Id: " + aux.getIdProceso());
        this.txt_Mensajes.appendText("\n Ciclos restantes: " + cont);
        this.txt_Mensajes.appendText("\n Estado: Ejecutando...");
        
        int ciclos = numRnd.nextInt(cont)+1;
        
        for(int i = 1; i<=ciclos; i++){
            this.txt_Mensajes.appendText("\n Ciclo" + i);
        }
        
        int rest = cont - ciclos;
        Proceso aux2 = aux;
        aux2.setContadorPrograma(rest);
        
        this.txt_Mensajes.appendText("\n Operación E/S\n Guardando bloque de control de procesos...");
        this.txt_Mensajes.appendText("\n Nombre: " + aux.getNomProceso());
        this.txt_Mensajes.appendText("\n Id: " + aux.getIdProceso());
        this.txt_Mensajes.appendText("\n Ciclos restantes: " + aux.getContadorPrograma());
        
        if(aux.getContadorPrograma() == 0){
            this.txt_Mensajes.appendText("\n Estado: Finalizado");

            procesos.remove(aux);
            this.txt_Mensajes.appendText("\n El proceso ha terminado exitosamente \n");

        }else{
            aux2.setEstadoDeProceso("Bloqueado");
            this.txt_Mensajes.appendText("\n Estado: " + aux.getEstadoDeProceso()); 
            procesos.remove(aux);
            procesos.add(aux2);
        }
        this.txt_Mensajes.appendText("\n Bloque guardado\n\n");
       
    }

    @FXML
    private void Finalizar_Proceso(ActionEvent event) {
        Proceso aux = getTablaProcesoSeleccionado();
        
        aux.setEstadoDeProceso("Finalizado");
        this.txt_Mensajes.appendText("\n Nombre: " + aux.getNomProceso());
        this.txt_Mensajes.appendText("\n Id: " + aux.getIdProceso());
        this.txt_Mensajes.appendText("\n Ciclos restantes: " + aux.getContadorPrograma());
        this.txt_Mensajes.appendText("\n Estado: " + aux.getEstadoDeProceso());
        
        if(aux.getContadorPrograma() != 0){
            this.txt_Mensajes.appendText("\n El proceso termino antes de lo esperado...");
        }
        procesos.remove(aux);
        this.txt_Mensajes.appendText("\n El proceso ha terminado exitosamente \n");
        
    }

    @FXML
    private void Cambiar_Estado_Proceso(ActionEvent event) {
        
        Proceso aux = getTablaProcesoSeleccionado();
        Proceso aux2 = aux;
        
        if(this.rbtn_Block.isSelected()){
            this.txt_Mensajes.appendText("\n Nombre: " + aux.getNomProceso());
            this.txt_Mensajes.appendText("\n Id: " + aux.getIdProceso());
            this.txt_Mensajes.appendText("\n Ciclos restantes: " + aux.getContadorPrograma());
            this.txt_Mensajes.appendText("\n Estado: " + aux.getEstadoDeProceso());
            this.txt_Mensajes.appendText("\n Actualizando estado...");
            
            aux2.setEstadoDeProceso("Bloqueado");
            procesos.remove(aux);
            procesos.add(aux2);

            this.txt_Mensajes.appendText("\n Nuevo estado: " + aux2.getEstadoDeProceso() + "\n");
        } else if(this.rbtn_Listo.isSelected()){
            if("Listo".equals(aux.getEstadoDeProceso())){
                this.txt_Mensajes.appendText("\n No ha sido posible cambiar el estado del proceso\n");
                
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setTitle("Error");
                alert.setContentText("No es posible realizar el cambio de estado\nEl proceso ya está listo");
                alert.showAndWait();
            }else{
                this.txt_Mensajes.appendText("\n Nombre: " + aux.getNomProceso());
                this.txt_Mensajes.appendText("\n Id: " + aux.getIdProceso());
                this.txt_Mensajes.appendText("\n Ciclos restantes: " + aux.getContadorPrograma());
                this.txt_Mensajes.appendText("\n Estado: " + aux.getEstadoDeProceso());
                this.txt_Mensajes.appendText("\n Actualizando estado...");
                
                aux2.setEstadoDeProceso("Listo");
                procesos.remove(aux);
                procesos.add(aux2);
                
                this.txt_Mensajes.appendText("\n Nuevo estado: " + aux2.getEstadoDeProceso() + "\n");
            }
        }else {
                this.txt_Mensajes.appendText("\n No ha sido posible cambiar el estado del proceso\n");
                
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setTitle("Error");
                
                if("Listo".equals(aux.getEstadoDeProceso())){
                    alert.setContentText("No es posible realizar el cambio de estado\nSeleccione el estado al que quiere cambiar el proceso\n\n\tEl proceso ya está Listo");
                }else if("Bloqueado".equals(aux.getEstadoDeProceso())){
                    alert.setContentText("No es posible realizar el cambio de estado\nSeleccione el estado al que quiere cambiar el proceso\n\n\tEl proceso ya está bloqueado");
                }
                
                alert.showAndWait();
        }
        
    }

    @FXML
    public void Salir(ActionEvent event) {
        Stage myStage = (Stage) this.btn_Salir.getScene().getWindow();
        myStage.close();
    }
    
    /**
     * Listener de la tabla
     */
    private final ListChangeListener<Proceso> Selec_Tabla_Proceso = new ListChangeListener<Proceso>(){
        @Override
        public void onChanged(ListChangeListener.Change<? extends Proceso> c) {
            ponerProcesoSeleccionado();
        }
            
    };

    /**
     * PARA SELECCIONAR UNA CELDA DE LA TABLA
     * @return 
     */
    public Proceso getTablaProcesoSeleccionado(){
        if(this.tblProcesos != null){
            List<Proceso> tabla = this.tblProcesos.getSelectionModel().getSelectedItems();
            if(tabla.size() == 1){
                final Proceso seleccionado;
                seleccionado = tabla.get(0);
                return seleccionado;
            }
        }
        return null;
    }

    /**
     * Método para mostrar datos del elemento que selccionemos
     */
    private void ponerProcesoSeleccionado(){
        
        Proceso aux = this.getTablaProcesoSeleccionado();
        
        if(aux != null){
            
            if("Listo".equals(aux.getEstadoDeProceso())){
                this.btn_Ejecutar.setDisable(false);
                this.btn_Camb_Estado.setDisable(false);
                this.btn_Terminar.setDisable(false);
                
                this.rbtn_Block.setDisable(false);
                this.rbtn_Listo.setDisable(true);
                this.rbtn_Listo.setSelected(false);
            }else if("Bloqueado".equals(aux.getEstadoDeProceso())){
                this.btn_Ejecutar.setDisable(true);
                this.btn_Camb_Estado.setDisable(false);
                this.btn_Terminar.setDisable(false);
                
                this.rbtn_Block.setDisable(true);
                this.rbtn_Block.setSelected(false);
                this.rbtn_Listo.setDisable(false);
            }else{
                this.btn_Ejecutar.setDisable(true);
                this.btn_Camb_Estado.setDisable(true);
                this.btn_CrearP.setDisable(true);
                this.btn_Terminar.setDisable(true);
                this.btn_Salir.setDisable(true);
                
                this.rbtn_Block.setDisable(true);
                this.rbtn_Block.setSelected(false);
                this.rbtn_Listo.setDisable(true);
                this.rbtn_Listo.setSelected(false);
            }
        }
        
        
    }
    
}
