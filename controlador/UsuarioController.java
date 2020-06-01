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
import javafx.beans.Observable;
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
import javafx.util.Callback;
import org.gustavosanchez.bean.Usuario;
import org.gustavosanchez.manejadores.ManejadorUsuario;

/**
 * FXML Controller class
 *
 * @author elmer
 */
public class UsuarioController implements Initializable {

    private ObservableList<Usuario> observableList;
    @FXML
    private JFXButton btnRegresar;
    @FXML
    private TableColumn columnNombre;
    @FXML
    private TableColumn columnApellido;
    @FXML
    private TableColumn columnCorreo;
    @FXML
    private TableColumn columnUsuario;
    @FXML
    private TableColumn columnTipoUsuario;
    @FXML
    private TableView<Usuario> tableViewUsuario;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       updateTableViewItems();
       cargarTabla();
       updateTableViewItems();
    }

    @FXML
    private void clickRegresar(ActionEvent event) {
        menuAdminScene();
        closeVentana();
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
        Stage stage = (Stage) this.btnRegresar.getScene().getWindow();
        stage.close();
    }

    private void updateObservableList() {
        observableList = FXCollections.observableArrayList(ManejadorUsuario.getInstance().getListArray());

    }

    public void updateTableViewItems() {
        updateObservableList();
        tableViewUsuario.setItems(observableList);
    }

    private void cargarTabla() {
        this.columnNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        this.columnApellido.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        this.columnCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));
        this.columnUsuario.setCellValueFactory(new PropertyValueFactory<>("nick"));
        this.columnTipoUsuario.setCellValueFactory(new PropertyValueFactory<>("tipoUsuario"));
        updateTableViewItems();
        this.tableViewUsuario.setItems(observableList);

    }

}
