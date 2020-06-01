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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.gustavosanchez.bean.Carrito;
import org.gustavosanchez.bean.ProductoOferta;
import org.gustavosanchez.bean.Usuario;
import org.gustavosanchez.estructura.LCircular;
import org.gustavosanchez.manejadores.ManejadorArchivos;
import org.gustavosanchez.manejadores.ManejadorCarrito;
import org.gustavosanchez.manejadores.ManejadorErrores;
import org.gustavosanchez.manejadores.ManejadorUsuario;

/**
 * FXML Controller class
 *
 * @author elmer
 */
public class DetalleCarritoController implements Initializable {

    private Usuario usuarioLogueado = ManejadorUsuario.getInstance().getUsuarioLogeado();
    private ObservableList<ProductoOferta> observableList;
    private Carrito carrito;
    private int cantidad;
    private double total;
    private ProductoOferta producto;
    @FXML
    private Label labelNombre;
    @FXML
    private JFXButton bttnRegreso;
    @FXML
    private Label labelCantidad;
    @FXML
    private Label labelTotal;
    @FXML
    private JFXButton bttnPagar;
    @FXML
    private JFXButton bttnEliminar;
    @FXML
    private JFXButton bttnCancelar;
    @FXML
    private TableView<ProductoOferta> tableView;
    @FXML
    private TableColumn columnProducto;
    @FXML
    private TableColumn columnPrecio;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        inicar();

    }

    @FXML
    private void clickRegreso(ActionEvent event) {
        clienteScene();
        closeVentana();
    }

    @FXML
    private void clickPagar(ActionEvent event) {
        clientePagoScene();
        closePagarVentana();
    }

    @FXML
    private void clickEliminar(ActionEvent event) {
        eliminar();
    }

    @FXML
    private void clickCancelar(ActionEvent event) {
        if (ManejadorCarrito.getInstance().eliminar("cancelar")) {
            ManejadorErrores.getInstance().alertaExito("Se cancelo la transicción");
            clienteScene();
            closeVentanaCancelar();
        } else {
            ManejadorErrores.getInstance().alertaFallo("No se puede eliminar el carrito");
        }

    }

    @FXML
    private void seleccionarTabla(MouseEvent event) {
        if (this.tableView.getSelectionModel().getSelectedItem() != null) {
            this.producto = this.tableView.getSelectionModel().getSelectedItem();
        }
    }

    public void iniciar() {
        String usuario = this.usuarioLogueado.getNombre();
        this.cantidad = this.carrito.getTotalProducto();
        this.total = this.carrito.getTotal();
        this.labelNombre.setText(usuario);
        this.labelCantidad.setText(String.valueOf(cantidad));
        this.labelTotal.setText(String.valueOf(total));

    }

    private void clienteScene() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/gustavosanchez/vista/Cliente.fxml"));
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
        Stage stage = (Stage) this.bttnRegreso.getScene().getWindow();
        stage.close();
    }

    private void closeVentanaCancelar() {
        Stage stage = (Stage) this.bttnCancelar.getScene().getWindow();
        stage.close();
    }

    public void setCarrito() {
        this.carrito = ManejadorCarrito.getInstance().getCarrito();

    }

    private void updateObservableList() {
        observableList = FXCollections.observableArrayList(ManejadorCarrito.getInstance().getArrayList());

    }

    private void updateTableViewItemns() {
        updateObservableList();
        tableView.setItems(observableList);

    }

    private void cargarTabla() {
        this.columnProducto.setCellValueFactory(new PropertyValueFactory<>("producto"));
        this.columnPrecio.setCellValueFactory(new PropertyValueFactory<>("precioOferta"));
        tableView.setItems(observableList);
        updateTableViewItemns();

    }

    public void inicar() {
        setCarrito();
        iniciar();
        cargarTabla();
    }

    private void eliminar() {
        Carrito car = ManejadorCarrito.getInstance().getCarrito();
        LCircular<ProductoOferta> list = car.getListaProducto();
        ProductoOferta pro = this.producto;
        if (pro != null) {
            int posicion = list.indexOf(pro);
            list.remove(posicion);
            this.cantidad--;
            this.total = total - pro.getPrecioOferta();
            ///Pendiente de hacer la modificacion
            if (ManejadorCarrito.getInstance().modificar(list, cantidad, total)) {
                this.labelCantidad.setText(cantidad + "");
                this.labelTotal.setText(total + "");
                cargarTabla();
            } else {
                ManejadorErrores.getInstance().alertaFallo("No se puedo eliminar el producto");
            }
        } else {
            ManejadorErrores.getInstance().alertaFallo("Debe seleccionar un producto");
        }

    }

    private void clientePagoScene() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/gustavosanchez/vista/Pago.fxml"));
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

    private void closePagarVentana() {
        Stage stage = (Stage) this.bttnPagar.getScene().getWindow();
        stage.close();
    }
}
