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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.gustavosanchez.bean.Carrito;
import org.gustavosanchez.bean.Producto;
import org.gustavosanchez.bean.ProductoOferta;
import org.gustavosanchez.estructura.LCircular;
import org.gustavosanchez.manejadores.ManejadorCarrito;
import org.gustavosanchez.manejadores.ManejadorErrores;
import org.gustavosanchez.manejadores.ManejadorProducto;

/**
 * FXML Controller class
 *
 * @author elmer
 */
public class ClienteProductoController implements Initializable {

    private ObservableList<Producto> observableList;
    private Producto producto;
    private ProductoOferta productoOferta;
    private String dirrecionURL;
    private int contador = 0;
    private double total = 0;
    private LCircular<ProductoOferta> listaProductoOferta = new LCircular<>();
    private int contadores = 200;
    @FXML
    private TableView<Producto> tablaView;
    @FXML
    private TableColumn columnNombre;
    @FXML
    private TableColumn columnPrecio;
    @FXML
    private ImageView imageView;
    @FXML
    private JFXButton bttnAgregarCarrito;
    @FXML
    private JFXButton bttnRegresar;
    @FXML
    private JFXButton bttnCarrtio;
    @FXML
    private Label labelCarrito;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        cargarTabla();
        inicializar();
    }

    @FXML
    private void selectTableView(MouseEvent event) {
        if (this.tablaView.getSelectionModel().getSelectedItem() != null) {
            Producto prod = this.tablaView.getSelectionModel().getSelectedItem();
            this.producto = prod;
            llenarImagen(prod.getImagen());
        }
    }

    @FXML
    private void clickAgregarCarrito(ActionEvent event) {
        if (this.producto != null) {
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

    @FXML
    private void clickRegresar(ActionEvent event) {
        clienteScene();
        closeVentana();
    }

    private void updateObservableList() {
        observableList = FXCollections.observableArrayList(ManejadorProducto.getInstance().getListaArrayProducto());
    }

    private void updateTableViewItemns() {
        updateObservableList();
        tablaView.setItems(observableList);
    }

    private void cargarTabla() {

        this.columnNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        this.columnPrecio.setCellValueFactory(new PropertyValueFactory("preicio"));

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

    private void inicializar() {
        String carrito = "(" + contador + ") Q" + total;
        this.labelCarrito.setText(carrito);
    }

    public boolean agregarListaProducto() {

        if (this.producto != null) {
            productoOferta = new ProductoOferta();
            productoOferta.setId(contadores);
            productoOferta.setPrecioOferta(producto.getPreicio());
            productoOferta.setPrecio(0);
            productoOferta.setProducto(producto);
            contadores++;
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
            } else {
                ManejadorErrores.getInstance().alertaFallo("Actualemete no posee nada ");
            }
        }

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
        Stage stage = (Stage) this.bttnCarrtio.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void clickCarrito(ActionEvent event) {
        agregarCarrito();
    }

}
