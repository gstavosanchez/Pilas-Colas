/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.gustavosanchez.controlador;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.gustavosanchez.bean.Carrito;
import org.gustavosanchez.bean.ProductoOferta;
import org.gustavosanchez.bean.Usuario;
import org.gustavosanchez.estructura.LCircular;
import org.gustavosanchez.manejadores.ManejadorArchivos;
import org.gustavosanchez.manejadores.ManejadorCarrito;
import org.gustavosanchez.manejadores.ManejadorErrores;
import org.gustavosanchez.manejadores.ManejadorFactura;
import org.gustavosanchez.manejadores.ManejadorUsuario;

/**
 * FXML Controller class
 *
 * @author elmer
 */
public class PagoController implements Initializable {

    private Usuario user;
    private ObservableList<ProductoOferta> observableList;
    @FXML
    private Label labelTitulo;
    @FXML
    private Label labelNombre;
    @FXML
    private JFXTextField txtTarjeta;
    @FXML
    private JFXTextField txtNombre;
    @FXML
    private JFXTextField txtDireccion;
    @FXML
    private JFXTextField txtNombreFac;
    @FXML
    private JFXTextField txtNit;
    @FXML
    private DatePicker date;
    @FXML
    private Label labelCantidad;
    @FXML
    private Label labelTotal;
    @FXML
    private JFXButton bttnPagar;
    @FXML
    private TableView<ProductoOferta> tableView;
    @FXML
    private TableColumn columnNombre;
    @FXML
    private TableColumn columnPrecio;
    @FXML
    private AnchorPane anchoPaneAlerta;
    @FXML
    private JFXButton bttnSi;
    @FXML
    private JFXButton bttnNo;
    @FXML
    private JFXButton bttnRegresar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        iniciar();
    }

    @FXML
    private void clickPagar(ActionEvent event) {
        validarPago();

    }

    @FXML
    private void clickSi(ActionEvent event) {
        // ManejadorArchivos.getInstance().escribirArchivo(ManejadorCarrito.getInstance().getCarrito().getListaProducto());
        generarFactura();
    }

    @FXML
    private void clickNo(ActionEvent event) {
        ManejadorCarrito.getInstance().eliminar("cancelar");
        clienteScene();
        closeNoVentana();
    }

    @FXML
    private void clickRegresar(ActionEvent event) {
        clienteScene();
        closeVentana();
    }

    private void iniciar() {
        this.anchoPaneAlerta.setVisible(false);
        user = ManejadorUsuario.getInstance().getUsuarioLogeado();
        Carrito car = ManejadorCarrito.getInstance().getCarrito();
        if (car != null && user != null) {
            this.labelNombre.setText(user.getNombre());
            String nombre = user.getNombre() + " " + user.getApellido();
            this.labelCantidad.setText(car.getTotalProducto() + "");
            this.labelTotal.setText(car.getTotal() + "");
            this.txtTarjeta.setText(user.getTarjeta());
            this.txtNombre.setText(user.getNombreTarjeta());
            this.txtNombreFac.setText(nombre);
            cargarTabla();
        }
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
        Stage stage = (Stage) this.bttnRegresar.getScene().getWindow();
        stage.close();
    }

    private void updateObservableList() {
        observableList = FXCollections.observableArrayList(ManejadorCarrito.getInstance().getArrayList());

    }

    private void updateTableViewItemns() {
        updateObservableList();
        tableView.setItems(observableList);

    }

    private void cargarTabla() {
        this.columnNombre.setCellValueFactory(new PropertyValueFactory<>("producto"));
        this.columnPrecio.setCellValueFactory(new PropertyValueFactory<>("precioOferta"));
        tableView.setItems(observableList);
        updateTableViewItemns();

    }

    private void validarPago() {
        if (this.date.getValue() != null) {
            LocalDate fechaInicio = this.date.getValue();
            String fecha = String.valueOf(fechaInicio);
            if (this.user.getFecha().equalsIgnoreCase(fecha)
                    && !this.txtDireccion.getText().isEmpty() && !this.txtNit.getText().isEmpty()
                    && !this.txtNombre.getText().isEmpty() && !this.txtNombreFac.getText().isEmpty()) {

                this.anchoPaneAlerta.setVisible(true);

            } else {
                ManejadorErrores.getInstance().alertaFallo("Campo incorrecta");
            }
        }

    }

    private void generarFactura() {
        String direccion = this.txtDireccion.getText().trim();
        String nameTarjeta = this.txtNombre.getText().trim();
        String nombreFacutra = this.txtNombreFac.getText().trim();
        String nit = this.txtNit.getText().trim();
        LCircular<ProductoOferta> lista = ManejadorCarrito.getInstance().getCarrito().getListaProducto();
        Carrito car = ManejadorCarrito.getInstance().getCarrito();
        double precioNormal = 0;
        for (int i = 0; i < lista.size(); i++) {
            ProductoOferta pro = lista.getNodo(i).getElemento();
            precioNormal = precioNormal + pro.getProducto().getPreicio();
        }
        double descuento = precioNormal - car.getTotal();
        System.out.println("Descuento: " + descuento);
        boolean estado = ManejadorFactura.getInstance().agregarFacutra(nombreFacutra, nit, user.getTipoCliente(), direccion, descuento, precioNormal, car.getTotal(),lista);
        if (estado) {
            ManejadorErrores.getInstance().alertaExito("Se genero la factura correctamente");
            
            //ManejadorArchivos.getInstance().escribirArchivo(lista);
            ManejadorArchivos.getInstance().abrirDOTGenerar(lista);
            //ManejadorArchivos.getInstance().reporteHTML(car);
            ManejadorArchivos.getInstance().abrir(car);
            ManejadorCarrito.getInstance().eliminar("pagar");
            clienteScene();
            closePagoVentana();
        }
    }

    private void closePagoVentana() {
        Stage stage = (Stage) this.bttnSi.getScene().getWindow();
        stage.close();
    }

    private void closeNoVentana() {
        Stage stage = (Stage) this.bttnSi.getScene().getWindow();
        stage.close();
    }

}
