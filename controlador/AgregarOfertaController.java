/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.gustavosanchez.controlador;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.gustavosanchez.bean.Producto;
import org.gustavosanchez.estructura.LCircular;
import org.gustavosanchez.manejadores.ManejadorArchivos;
import org.gustavosanchez.manejadores.ManejadorErrores;
import org.gustavosanchez.manejadores.ManejadorProducto;
import org.gustavosanchez.manejadores.ManejadorUsuario;

/**
 * FXML Controller class
 *
 * @author elmer
 */
public class AgregarOfertaController implements Initializable {

    private LCircular<Producto> lista = new LCircular<>();
    private ObservableList<Producto> observableList;
    @FXML
    private JFXTextArea txtDescripcion;
    @FXML
    private JFXTextField txtDescuento;
    @FXML
    private JFXComboBox<Producto> cmboBoxProducto;
    @FXML
    private JFXButton bttnAgregarProducto;
    @FXML
    private JFXButton bttnCargaMasica;
    @FXML
    private JFXButton bttnAgregarOferta;
    @FXML
    private JFXButton bttnRegresar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        updateComboBox();
    }

    @FXML
    private void clickAgregarProducto(ActionEvent event) {
        if (this.cmboBoxProducto.getSelectionModel().getSelectedItem() != null) {
            Producto pro = this.cmboBoxProducto.getSelectionModel().getSelectedItem();
            lista.add(pro);
            ManejadorErrores.getInstance().alertaExito("Se agrego el producto a lista: "+ pro.getNombre());
        }else {
            ManejadorErrores.getInstance().alertaFallo("Debe seleccionar un producto");
        }
    }

    @FXML
    private void clickCargaMasica(ActionEvent event) {
    }

    @FXML
    private void clickAgregarOferta(ActionEvent event) {
    }

    private void goMenuOferta() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/gustavosanchez/vista/Oferta.fxml"));
            Parent root = loader.load();

            // MenuPrincipalVistaController controlador = loader.getController();
            Scene scene = new Scene(root);
            Stage stage = new Stage();

            stage.setScene(scene);
            //stage.getIcons().add(new Image("/org/gustavosanchez/resourse/icon2.png"));
            stage.show();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    private void closeVentana() {
        Stage stage = (Stage) this.bttnRegresar.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void clickRegresar(ActionEvent event) {
        goMenuOferta();
        closeVentana();

    }

    private void updateObservableList() {
        this.observableList = FXCollections.observableArrayList(ManejadorProducto.getInstance().getListaArrayProducto());
       
    }
    private void updateComboBox(){
        updateObservableList();
        this.cmboBoxProducto.setItems(observableList);
    }
    private void agregar(){
        if (this.txtDescripcion.getText() != null && this.txtDescuento.getText() != null) {
            if (!this.txtDescripcion.getText().isEmpty() && !this.txtDescuento.getText().isEmpty()) {
                if (this.cmboBoxProducto.getSelectionModel().getSelectedItem() != null) {
                    
                    Producto pro = this.cmboBoxProducto.getSelectionModel().getSelectedItem();
                    
                }else {
                    ManejadorErrores.getInstance().alertaFallo("Seleccione un campo");
                }
            }else {
                ManejadorErrores.getInstance().alertaFallo("No se acepta campos vacios");
            }
        }else{
            ManejadorErrores.getInstance().alertaFallo("No se puede agregar datos nulos");
        }
    
    
    }
    
    
    

}
