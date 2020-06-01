/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.gustavosanchez.controlador;

import com.jfoenix.controls.JFXButton;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.gustavosanchez.bean.Oferta;
import org.gustavosanchez.bean.Producto;
import org.gustavosanchez.manejadores.ManejadorOferta;
import org.gustavosanchez.manejadores.ManejadorProducto;

/**
 * FXML Controller class
 *
 * @author elmer
 */
public class VerOfertaController implements Initializable {

    private ObservableList<Oferta> observableList;
    @FXML
    private JFXButton bttnRegresar;
    @FXML
    private TableView<Oferta> tableView;
    @FXML
    private TableColumn columnId;
    @FXML
    private TableColumn columnDescuento;
    @FXML
    private TableColumn columnDescripcion;
    @FXML
    private TableColumn columnPrioridad;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        cargarTabla();
    }

    @FXML
    private void clickRegresar(ActionEvent event) {
        goMenuOferta();
        closeVentana();
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
    
        private void updateObservableList() {
        observableList = FXCollections.observableArrayList(ManejadorOferta.getInstance().getArrayListaOferta());
    }

    private void updateTableViewItemns() {
        updateObservableList();
        tableView.setItems(observableList);
    }

    private void cargarTabla() {
        this.columnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.columnDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        this.columnDescuento.setCellValueFactory(new PropertyValueFactory("descuento"));
        this.columnPrioridad.setCellValueFactory(new PropertyValueFactory("prioridad"));
        updateTableViewItemns();

    }

}
