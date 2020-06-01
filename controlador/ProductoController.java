/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.gustavosanchez.controlador;

import com.jfoenix.controls.JFXButton;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.gustavosanchez.bean.Producto;
import org.gustavosanchez.estructura.LCircular;
import org.gustavosanchez.estructura.ListaCircular;
import org.gustavosanchez.manejadores.ManejadorArchivos;
import org.gustavosanchez.manejadores.ManejadorErrores;
import org.gustavosanchez.manejadores.ManejadorProducto;
import org.gustavosanchez.manejadores.ManejadorUsuario;

/**
 * FXML Controller class
 *
 * @author elmer
 */
public class ProductoController implements Initializable {

    private Image image;
    private File imgFile;
    private String dirrecionURL;
    private ObservableList<Producto> observableList;
    private Producto producto;
    @FXML
    private JFXButton bttnURL;
    @FXML
    private JFXButton bttnRegresar;
    @FXML
    private JFXButton bttnAgregar;
    @FXML
    private JFXButton bttnEditar;
    @FXML
    private JFXButton bttnEliminar;
    @FXML
    private TableView<Producto> tableView;
    @FXML
    private TableColumn columnIdentificador;
    @FXML
    private TableColumn columnNombre;
    @FXML
    private TableColumn columnPrecio;
    @FXML
    private TableColumn collumnExistencia;
    @FXML
    private ImageView imageView;
    @FXML
    private JFXTextArea txtDescripcion;
    @FXML
    private JFXTextField txtExistencia;
    @FXML
    private JFXTextField txtPrecio;
    @FXML
    private JFXTextField txtNombre;
    @FXML
    private JFXTextField txtIdentificador;
    @FXML
    private JFXButton bttnCargaMasiva;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        cargarTabla();
    }

    @FXML
    private void clickURL(ActionEvent event) {
        agregarImagen();
    }

    @FXML
    private void clickRegresar(ActionEvent event) {
        menuAdminScene();
        closeVentana();
    }

    @FXML
    private void clickAgregar(ActionEvent event) {
        agregarProducto();
       
    }

    @FXML
    private void clickEditar(ActionEvent event) {
        modificar();
    }

    @FXML
    private void selectTableView(MouseEvent event) {
        try {
            if (this.tableView.getSelectionModel().getSelectedItem() != null) {
                producto = this.tableView.getSelectionModel().getSelectedItem();
                this.txtIdentificador.setText(producto.getIdentificador());
                this.txtNombre.setText(producto.getNombre());
                this.txtPrecio.setText(String.valueOf(producto.getPreicio()));
                this.txtDescripcion.setText(producto.getDescripcion());
                this.txtExistencia.setText(String.valueOf(producto.getExistencias()));
                llenarImagen(producto.getImagen());

            }
        } catch (Exception ex) {
            System.out.println(ex);
            ManejadorErrores.getInstance().alertaFallo("Error en la tabla: " + ex);
        }
    }

    @FXML
    private void clickEliminar(ActionEvent event) {
        if (producto != null) {
            boolean estado = ManejadorProducto.getInstance().eliminar(String.valueOf(producto.getId()));
            if (estado) {
                ManejadorErrores.getInstance().alertaExito("se elimino exitosamente");
                vaciarCampos();
                updateTableViewItemns();
            } else {
                ManejadorErrores.getInstance().alertaFallo("No se puedo eliminar, vuelva intentarlo");
            }
        } else {
            ManejadorErrores.getInstance().alertaFallo("Debe seleccionar el producto a elimnar");
        }

    }

    private void menuAdminScene() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/gustavosanchez/vista/MenuAdmin.fxml"));
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

    private void agregarImagen() {
        String nulo = null;
        setDirrecionURL(nulo);
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Buscar Imagen");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );
        imgFile = fileChooser.showOpenDialog(stage);
        if (imgFile != null) {
            image = new Image("file:" + imgFile.getAbsolutePath());
            //image = new Image(imgFile.getAbsolutePath());
            setDirrecionURL(String.valueOf(imgFile));
            //System.out.println("Direccion: " + imgFile);
            imageView.setImage(image);
            //System.out.println(image);

        }

    }

    public String getDirrecionURL() {
        return dirrecionURL;
    }

    public void setDirrecionURL(String dirrecionURL) {

        this.dirrecionURL = dirrecionURL;
    }

    private void agregarProducto() {

        if (this.txtIdentificador.getText() != null && this.txtNombre.getText() != null
                && this.txtPrecio.getText() != null && this.txtDescripcion.getText() != null
                && this.txtExistencia.getText() != null) {

            if (!this.txtIdentificador.getText().isEmpty() && !this.txtNombre.getText().isEmpty()
                    && !this.txtPrecio.getText().isEmpty() && !this.txtDescripcion.getText().isEmpty()
                    && !this.txtExistencia.getText().isEmpty()) {
                if (getDirrecionURL() != null) {
                    String identificador = this.txtIdentificador.getText().trim();
                    String nombre = this.txtNombre.getText().trim();
                    String precio = this.txtPrecio.getText().trim();
                    String descripcion = this.txtDescripcion.getText();
                    String existencia = this.txtExistencia.getText();
                    String imagen = getDirrecionURL();
                    boolean estado = ManejadorProducto.getInstance().agregarProducto(identificador, nombre, precio, descripcion, existencia, imagen);
                    if (estado) {
                        ManejadorErrores.getInstance().alertaExito("Se agrego el producto exitosamente");
                        vaciarCampos();
                        cargarTabla();
                    } else {
                        ManejadorErrores.getInstance().alertaFallo("Fallo en los datos");
                    }

                } else {
                    ManejadorErrores.getInstance().alertaFallo("No selecciono ninguna imagen");
                }

            } else {
                ManejadorErrores.getInstance().alertaFallo("Campo Vacio");
            }

        } else {
            ManejadorErrores.getInstance().alertaFallo("Campo NULO");
        }

    }

    private void vaciarCampos() {
        this.txtIdentificador.setText("");
        this.txtNombre.setText("");
        this.txtPrecio.setText("");
        this.txtDescripcion.setText("");
        this.txtExistencia.setText("");

        image = new Image("file:");
        //image = new Image(imgFile.getAbsolutePath());
        dirrecionURL = String.valueOf(imgFile);
        //System.out.println("Direccion: " + imgFile);
        imageView.setImage(image);
        //System.out.println(image);

    }

    private void llenarImagen(String input) {

        if (input != null) {
            setDirrecionURL(input);
            Image imageTemp = new Image("file:" + getDirrecionURL());

            this.imageView.setImage(imageTemp);

        }

    }

    private void updateObservableList() {
        observableList = FXCollections.observableArrayList(ManejadorProducto.getInstance().getListaArrayProducto());
    }

    private void updateTableViewItemns() {
        updateObservableList();
        tableView.setItems(observableList);
    }

    private void cargarTabla() {
        this.columnIdentificador.setCellValueFactory(new PropertyValueFactory<>("identificador"));
        this.columnNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        this.columnPrecio.setCellValueFactory(new PropertyValueFactory("preicio"));
        this.collumnExistencia.setCellValueFactory(new PropertyValueFactory("existencias"));
        updateTableViewItemns();

    }

    private void modificar() {

        if (this.txtIdentificador.getText() != null && this.txtNombre.getText() != null
                && this.txtPrecio.getText() != null && this.txtDescripcion.getText() != null
                && this.txtExistencia.getText() != null) {

            if (!this.txtIdentificador.getText().isEmpty() && !this.txtNombre.getText().isEmpty()
                    && !this.txtPrecio.getText().isEmpty() && !this.txtDescripcion.getText().isEmpty()
                    && !this.txtExistencia.getText().isEmpty()) {
                if (getDirrecionURL() != null) {
                    String identificador = this.txtIdentificador.getText().trim();
                    String nombre = this.txtNombre.getText().trim();
                    String precio = this.txtPrecio.getText().trim();
                    String descripcion = this.txtDescripcion.getText();
                    String existencia = this.txtExistencia.getText();
                    String imagen = getDirrecionURL();
                    boolean estado = ManejadorProducto.getInstance().modificar(String.valueOf(producto.getId()), identificador, nombre, precio, descripcion, existencia, imagen);
                    if (estado) {
                        ManejadorErrores.getInstance().alertaExito("Se modifoco el producto exitosamente");
                        vaciarCampos();
                        cargarTabla();
                        //this.tableView.refresh();

                    } else {
                        ManejadorErrores.getInstance().alertaFallo("Fallo en los datos");
                    }

                } else {
                    ManejadorErrores.getInstance().alertaFallo("No selecciono ninguna imagen");
                }

            } else {
                ManejadorErrores.getInstance().alertaFallo("Campo Vacio");
            }

        } else {
            ManejadorErrores.getInstance().alertaFallo("Campo NULO");
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
        ManejadorArchivos.getInstance().readInput(stringFile, "P");
        cargarTabla();
        ManejadorErrores.getInstance().alertaExito("Se agrego exitosamente el producto");
        LCircular<Producto> lista = ManejadorProducto.getInstance().getCircular();
        //ManejadorArchivos.getInstance().escribirArchivo(lista);
       // ManejadorArchivos.getInstance().pdfGenerar("Elmer");
    }

    @FXML
    private void verificarDescipcion(KeyEvent event) {
        String filtroNombre = this.txtDescripcion.getText();
        if (!filtroNombre.isEmpty()) {
            if (ManejadorUsuario.getInstance().isInteger(filtroNombre)) {
                ManejadorErrores.getInstance().alertaFallo("Solo se acepta letras");
                this.txtDescripcion.setText("");
            }
        }
    }

    @FXML
    private void verificarExistencia(KeyEvent event) {
        String filtroNombre = this.txtExistencia.getText();
        if (!filtroNombre.isEmpty()) {
            if (!ManejadorUsuario.getInstance().isInteger(filtroNombre)) {
                ManejadorErrores.getInstance().alertaFallo("Solo se acepta numeros");
                this.txtExistencia.setText("");
            }
        }
    }

    @FXML
    private void verficarPrecio(KeyEvent event) {
        String filtroNombre = this.txtPrecio.getText();
        if (!filtroNombre.isEmpty()) {
            if (!ManejadorProducto.getInstance().isDouble(filtroNombre)) {
                ManejadorErrores.getInstance().alertaFallo("Solo se acepta numeros");
                this.txtPrecio.setText("");
            }
        }
    }

    @FXML
    private void verificarNombre(KeyEvent event) {
        String filtroNombre = this.txtNombre.getText();
        if (!filtroNombre.isEmpty()) {
            if (ManejadorUsuario.getInstance().isInteger(filtroNombre)) {
                ManejadorErrores.getInstance().alertaFallo("Solo se acepta letras");
                this.txtNombre.setText("");
            }
        }
    }

    @FXML
    private void verificarIdentificador(KeyEvent event) {
        String filtroNombre = this.txtIdentificador.getText();
        if (!filtroNombre.isEmpty()) {
            if (!ManejadorUsuario.getInstance().isInteger(filtroNombre)) {
                ManejadorErrores.getInstance().alertaFallo("Solo se acepta numeros");
                this.txtIdentificador.setText("");
            }
        }
    }

}
