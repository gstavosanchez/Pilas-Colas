/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.gustavosanchez.controlador;

import com.jfoenix.controls.JFXButton;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.gustavosanchez.bean.Carrito;
import org.gustavosanchez.bean.Oferta;
import org.gustavosanchez.bean.Producto;
import org.gustavosanchez.bean.ProductoOferta;
import org.gustavosanchez.estructura.ColaPrioridad;
import org.gustavosanchez.estructura.LCircular;
import org.gustavosanchez.manejadores.ManejadorArchivos;
import org.gustavosanchez.manejadores.ManejadorCarrito;
import org.gustavosanchez.manejadores.ManejadorErrores;
import org.gustavosanchez.manejadores.ManejadorOferta;
import org.gustavosanchez.manejadores.ManejadorProducto;
import org.gustavosanchez.manejadores.ManejadorUsuario;

/**
 * FXML Controller class
 *
 * @author elmer
 */
public class ClienteController implements Initializable {

    private ObservableList<ProductoOferta> observableList;
    private LCircular<ProductoOferta> listaProductoOferta = new LCircular<>();
    private String dirrecionURL;
    private ProductoOferta productoOferta;
    private Image imagenLoad;
    @FXML
    private ImageView imageView;
    private int contador = 0;
    private double total = 0;
    @FXML
    private Label labelNombre;
    @FXML
    private Label labelCarrito;
    @FXML
    private JFXButton bttnPerfil;
    @FXML
    private JFXButton bttnCarrito;
    @FXML
    private JFXButton bttnCerrar;
    @FXML
    private JFXButton bttnPagar;
    @FXML
    private JFXButton bttnProducto;
    @FXML
    private TableView<ProductoOferta> tableView;
    @FXML
    private TableColumn columnImagen;
    @FXML
    private TableColumn colunPrecio;
    @FXML
    private TableColumn columPrecioAhora;
    @FXML
    private JFXButton bttnAgregarCarrito;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        inicializar();
        //mostrarOferta();
        cargarTabla();
    }

    @FXML
    private void clickPerfil(ActionEvent event) {
        menuClientePerfilScene();
        closeVentanaCliente();
    }

    @FXML
    private void clickCarrito(ActionEvent event) {
        agregarCarrito();
    }

    @FXML
    private void clickCerra(ActionEvent event) {
        registrarseScene();
        closeVentanaRegresar();
        // ManejadorCarrito.getInstance().setEstado(false);
    }

    @FXML
    private void clickPagar(ActionEvent event) {
    }

    private void inicializar() {
        String user = ManejadorUsuario.getInstance().getUsuarioLogeado().getNombre();
        this.labelNombre.setText(user);
        String carrito = "(" + contador + ") Q" + total;
        this.labelCarrito.setText(carrito);
    }

    @FXML
    private void clickProductos(ActionEvent event) {
        clienteProductoScene();
        closeClienteScene();
    }

    private void mostrarOferta() {

    }

    private void menuClientePerfilScene() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/gustavosanchez/vista/MiPerfil.fxml"));
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

    private void closeVentanaCliente() {
        Stage stage = (Stage) this.bttnPerfil.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void seleccionarTabla(MouseEvent event) {
        if (this.tableView.getSelectionModel().getSelectedItem() != null) {
            ProductoOferta po = this.tableView.getSelectionModel().getSelectedItem();
            productoOferta = po;
            System.out.println(po.getProducto().getNombre());
            llenarImagen(po.getProducto().getImagen());

        }
    }

    @FXML
    private void clickAgregarCarrito(ActionEvent event) {
        if (this.productoOferta != null) {
            boolean estado = agregarListaProducto();
            if (estado) {
                this.contador++;
                this.total = total + productoOferta.getPrecioOferta();
                String carrito = "(" + contador + ") Q" + total;
                this.labelCarrito.setText(carrito);

            } else {
                ManejadorErrores.getInstance().alertaFallo("No se puedo agregar el producto");
            }

        } else {
            ManejadorErrores.getInstance().alertaFallo("Debe seleccionar un producto");
        }
    }

    public boolean agregarListaProducto() {
        if (this.productoOferta != null) {
            //ProductoOferta pro = new ProductoOferta();
            //pro.setId(productoOferta.getId());
            //pro.setPrecioOferta(productoOferta.getPrecioOferta());
            //pro.setPrecio(0);
            //pro.setProducto(productoOferta.getProducto());
            listaProductoOferta.add(this.productoOferta);
            return true;
        }
        return false;
    }

    public void agregarCarrito() {
        if (contador != 0) {

            int cantidad = contador;
            double totalGeneral = total;
            //LCircular<ProductoOferta> litaPro = this.listaProductoOferta;
            if (ManejadorCarrito.getInstance().agregarCarrito(this.listaProductoOferta, total, cantidad)) {
                //this.listaProductoOferta.clear();
                this.contador = 0;
                this.total = 0;
                String carrito = "(" + contador + ") Q" + total;
                this.labelCarrito.setText(carrito);
                clienteCarritoScene();
                closeClienteCarritoScene();
            }
        } else {
            Carrito carrito = ManejadorCarrito.getInstance().getCarrito();
            if (carrito != null) {
                clienteCarritoScene();
                closeClienteCarritoScene();
            }else {
                ManejadorErrores.getInstance().alertaFallo("Actualemete no posee nada ");
            }
        }

    }

    private void updateObservableList() {
        observableList = FXCollections.observableArrayList(ManejadorArchivos.getInstance().getListaProOferta());

    }

    private void updateTableViewItemns() {
        updateObservableList();
        tableView.setItems(observableList);

    }

    private void cargarTabla() {
        this.columnImagen.setCellValueFactory(new PropertyValueFactory<>("producto"));
        this.colunPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        this.columPrecioAhora.setCellValueFactory(new PropertyValueFactory("precioOferta"));
        tableView.setItems(observableList);
        updateTableViewItemns();

    }

    private void llenarImagen(String input) {

        if (input != null) {
            setDirrecionURL(input);
            Image imageTemp = new Image("file:" + getDirrecionURL());

            this.imageView.setImage(imageTemp);

        }

    }

    public String getDirrecionURL() {
        return dirrecionURL;
    }

    public void setDirrecionURL(String dirrecionURL) {
        this.dirrecionURL = dirrecionURL;
    }

    private void registrarseScene() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/gustavosanchez/vista/Login.fxml"));
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

    private void closeVentanaRegresar() {
        Stage stage = (Stage) this.bttnCerrar.getScene().getWindow();
        stage.close();
    }

    private void clienteProductoScene() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/gustavosanchez/vista/ClienteProducto.fxml"));
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

    private void closeClienteScene() {
        Stage stage = (Stage) this.bttnProducto.getScene().getWindow();
        stage.close();
    }

    private void clienteCarritoScene() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/gustavosanchez/vista/DetalleCarrito.fxml"));
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

    private void closeClienteCarritoScene() {
        Stage stage = (Stage) this.bttnCarrito.getScene().getWindow();
        stage.close();
    }

}
