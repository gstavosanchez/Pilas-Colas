/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.gustavosanchez.controlador;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import org.gustavosanchez.bean.Factura;
import org.gustavosanchez.bean.ProductoOferta;
import org.gustavosanchez.manejadores.ManejadorArchivos;
import org.gustavosanchez.manejadores.ManejadorCarrito;
import org.gustavosanchez.manejadores.ManejadorErrores;
import org.gustavosanchez.manejadores.ManejadorFactura;

/**
 * FXML Controller class
 *
 * @author elmer
 */
public class FacturaController implements Initializable {

    private ObservableList<Factura> observableList;
    private ObservableList<Factura> filtro;
    private Factura factura;
    @FXML
    private TableView<Factura> tableView;
    @FXML
    private TableColumn columnId;
    @FXML
    private TableColumn columnNombre;
    @FXML
    private TableColumn columnNit;
    @FXML
    private TableColumn columnTotal;
    @FXML
    private JFXTextField txtBuscar;
    @FXML
    private JFXButton bttnPdf;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        cargarTabla();
    }

    @FXML
    private void seleccionar(MouseEvent event) {
        try {
            if (this.tableView.getSelectionModel().getSelectedItem() != null) {
                this.factura = this.tableView.getSelectionModel().getSelectedItem();
                System.out.println("Seleccionado nit:"+this.factura.getNit());
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @FXML
    private void filtrarPorNombre(KeyEvent event) {
        String filtroNombre = this.txtBuscar.getText();
        if (filtroNombre.isEmpty()) {
            this.tableView.setItems(observableList);
        }else{
            this.filtro.clear();
            for(Factura f: this.observableList){
                if (f.getNombre().toLowerCase().contains(filtroNombre.toLowerCase()) || f.getNit().toLowerCase().contains(filtroNombre.toLowerCase())) {
                    this.filtro.add(f);
                }
            }
            this.tableView.setItems(filtro);
        } 
    }

    @FXML
    private void clickPdf(ActionEvent event) {
        if (this.factura != null) {
            ManejadorArchivos.getInstance().pdfGenerarAdmin(factura);
            ManejadorErrores.getInstance().alertaExito("Se genera la factura ver carpeta grafix");
        }else {
            ManejadorErrores.getInstance().alertaFallo("Se debe seleccionar una factra");
        }
    }

    private void updateObservableList() {
        observableList = FXCollections.observableArrayList(ManejadorFactura.getInstance().getArray());
        filtro = FXCollections.observableArrayList();

    }

    private void updateTableViewItemns() {
        updateObservableList();
        tableView.setItems(observableList);

    }

    private void cargarTabla() {
        this.columnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.columnNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        this.columnNit.setCellValueFactory(new PropertyValueFactory<>("nit"));
        this.columnTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        tableView.setItems(observableList);
        updateTableViewItemns();

    }

}
