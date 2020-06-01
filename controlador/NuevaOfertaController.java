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
import java.io.File;
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
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.gustavosanchez.bean.Producto;
import org.gustavosanchez.bean.TipoOferta;
import org.gustavosanchez.estructura.LCircular;
import org.gustavosanchez.manejadores.ManejadorArchivos;
import org.gustavosanchez.manejadores.ManejadorErrores;
import org.gustavosanchez.manejadores.ManejadorOferta;
import org.gustavosanchez.manejadores.ManejadorProducto;
import org.gustavosanchez.manejadores.ManejadorUsuario;

/**
 * FXML Controller class
 *
 * @author elmer
 */
public class NuevaOfertaController implements Initializable {

    private LCircular<Producto> lista = new LCircular<>();
    private ObservableList<Producto> observableList;
    private ObservableList<TipoOferta> observableListTipo;
    private int contador = 0;
    @FXML
    private JFXButton bttnRegresar;
    @FXML
    private JFXTextArea txtDescripcion;
    @FXML
    private JFXTextField txtDescuento;
    @FXML
    private JFXComboBox<TipoOferta> comboPrioridad;
    @FXML
    private JFXButton bttnProducto;
    @FXML
    private JFXButton bttnCarga;
    @FXML
    private JFXComboBox<Producto> comboProducto;
    @FXML
    private JFXTextField txtContador;
    @FXML
    private JFXButton bttnAgregarOferta;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        updateComboBox();
        this.txtContador.setText(String.valueOf(contador));
    }

    @FXML
    private void clickRegresar(ActionEvent event) {
        goMenuOferta();
        closeVentana();
    }

    @FXML
    private void clicAgregarProducto(ActionEvent event) {

        if (this.comboProducto.getSelectionModel().getSelectedItem() != null && !this.txtDescuento.getText().isEmpty()) {
            Producto pro = this.comboProducto.getSelectionModel().getSelectedItem();
            if (!datosDuplicados(pro.getIdentificador())) {
                double decimal = ((Double.parseDouble(this.txtDescuento.getText().trim())) / 100);
                double des = ((pro.getPreicio()) * (decimal));
                double precioTotal = ((pro.getPreicio()) - (des));
                ManejadorArchivos.getInstance().agregarProductoOferta(pro, precioTotal);
                lista.add(pro);
                ManejadorErrores.getInstance().alertaExito("Se agrego el producto a lista: " + pro.getNombre());
                contador++;
                this.txtContador.setText(String.valueOf(contador));
            } else {
                ManejadorErrores.getInstance().alertaFallo("Ya existe ese producto: " + pro.getNombre());
            }

        } else {
            ManejadorErrores.getInstance().alertaFallo("Debe seleccionar un producto");
        }
    }

    @FXML
    private void clickCargaMasiva(ActionEvent event) {
        System.out.println("Cargar Archivo");
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Buscar Archivo");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PROOFER ", "*.proofer")
        );
        File file = fileChooser.showOpenDialog(stage);
        String stringFile = String.valueOf(file);
        System.out.println(file);
        System.out.println(stringFile);
        ManejadorArchivos.getInstance().readInput(stringFile, "O");
        ManejadorErrores.getInstance().alertaExito("Se agrego exitosamente el producto");

    }

    @FXML
    private void clickAgregarOferta(ActionEvent event) {
        agregar();
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
        this.observableList = FXCollections.observableArrayList(ManejadorProducto.getInstance().getListaArrayProducto());
        this.observableListTipo = FXCollections.observableArrayList(ManejadorOferta.getInstance().getListaTipoOferta());

    }

    private void updateComboBox() {
        updateObservableList();
        this.comboProducto.setItems(observableList);
        this.comboPrioridad.setItems(observableListTipo);
    }

    private void agregar() {
        if (this.txtDescripcion.getText() != null && this.txtDescuento.getText() != null) {
            if (!this.txtDescripcion.getText().isEmpty() && !this.txtDescuento.getText().isEmpty()) {
                if (this.comboProducto.getSelectionModel().getSelectedItem() != null
                        && this.comboPrioridad.getSelectionModel().getSelectedItem() != null) {
                    if (this.lista != null) {
                        String descripcion = this.txtDescripcion.getText();
                        String descuento = this.txtDescuento.getText().trim();
                        TipoOferta prioridad = this.comboPrioridad.getSelectionModel().getSelectedItem();
                        contador = 0;
                        this.txtContador.setText(String.valueOf(contador));
                        boolean estado = ManejadorOferta.getInstance().agregarOfertaInterfaz(descripcion, descuento, lista, prioridad);
                        if (estado) {
                            lista.clear();
                            ManejadorErrores.getInstance().alertaExito("Se agrego la oferta exitosamente");
                            this.txtDescripcion.setText("");
                            this.txtDescuento.setText("");

                        } else {
                            ManejadorErrores.getInstance().alertaFallo("No se puedo crear la oferta");
                        }

                    } else {
                        ManejadorErrores.getInstance().alertaFallo("Seleccione un producto");
                    }

                } else {
                    ManejadorErrores.getInstance().alertaFallo("Seleccione un campo");
                }
            } else {
                ManejadorErrores.getInstance().alertaFallo("No se acepta campos vacios");
            }
        } else {
            ManejadorErrores.getInstance().alertaFallo("No se puede agregar datos nulos");
        }

    }

    public boolean datosDuplicados(String input) {
        for (int i = 0; i < lista.size(); i++) {
            Producto pro = lista.getElemento(i);
            if (pro.getIdentificador().equals(input)) {
                return true;
            }
        }
        return false;

    }

    @FXML
    private void verificarDescripcion(KeyEvent event) {

        String filtroNombre = this.txtDescripcion.getText();
        if (!filtroNombre.isEmpty()) {
            if (ManejadorUsuario.getInstance().isInteger(filtroNombre)) {
                ManejadorErrores.getInstance().alertaFallo("Solo se acepta letras");
                this.txtDescripcion.setText("");
            }
        }
    }

    @FXML
    private void verificarDescuento(KeyEvent event) {

        String filtroNombre = this.txtDescuento.getText();
        if (!filtroNombre.isEmpty()) {
            if (!ManejadorProducto.getInstance().isDouble(filtroNombre)) {
                ManejadorErrores.getInstance().alertaFallo("Solo se acepta numeros");
                this.txtDescuento.setText("");
            }
        }
    }

}
