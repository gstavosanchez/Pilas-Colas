/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.gustavosanchez.controlador;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.gustavosanchez.bean.Usuario;
import org.gustavosanchez.manejadores.ManejadorErrores;
import org.gustavosanchez.manejadores.ManejadorUsuario;

/**
 * FXML Controller class
 *
 * @author elmer
 */
public class MiPerfilController implements Initializable {

    @FXML
    private JFXTextField txtNombre;
    @FXML
    private JFXTextField txtApellido;
    @FXML
    private JFXTextField txtUsuario;
    @FXML
    private JFXTextField txtTarjeta;
    @FXML
    private JFXTextField txtCorreo;
    @FXML
    private JFXPasswordField passworldField;
    @FXML
    private JFXButton bttnRegresar;
    @FXML
    private JFXButton bttnModificar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        cargar();
    }

    @FXML
    private void clickRegresar(ActionEvent event) {
        menuClienteScene();
        closeVentanaCliente();
    }

    @FXML
    private void clickModificar(ActionEvent event) {
        modificar();
    }

    private void menuClienteScene() {
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

    private void closeVentanaCliente() {
        Stage stage = (Stage) this.bttnRegresar.getScene().getWindow();
        stage.close();
    }

    private void closeVentanaClienteMod() {
        Stage stage = (Stage) this.bttnModificar.getScene().getWindow();
        stage.close();
    }

    private void cargar() {
        Usuario user = ManejadorUsuario.getInstance().getUsuarioLogeado();
        this.txtNombre.setText(user.getNombre());
        this.txtApellido.setText(user.getApellido());
        this.txtCorreo.setText(user.getCorreo());
        this.txtUsuario.setText(user.getNick());
        this.passworldField.setText(user.getPassword());
        this.txtTarjeta.setText(user.getTarjeta());
    }

    private void modificar() {
        if (this.txtNombre.getText() != null && this.txtApellido.getText() != null
                && this.txtCorreo.getText() != null && this.txtUsuario.getText() != null
                && this.passworldField.getText() != null && this.txtTarjeta.getText() != null) {
            if (!this.txtNombre.getText().isEmpty() && !this.txtApellido.getText().isEmpty()
                    && !this.txtCorreo.getText().isEmpty() && !this.txtUsuario.getText().isEmpty()
                    && !this.passworldField.getText().isEmpty() && !this.txtTarjeta.getText().isEmpty()) {
                String nombre = this.txtNombre.getText().trim();
                String apellido = this.txtApellido.getText().trim();
                String correo = this.txtCorreo.getText().trim();
                String usuario = this.txtUsuario.getText().trim();
                String password = this.passworldField.getText().trim();
                String tarjeta = this.txtTarjeta.getText().trim();
                boolean estado = ManejadorUsuario.getInstance().modificar(nombre, apellido, correo, usuario, password, tarjeta);
                if (estado) {
                    ManejadorErrores.getInstance().alertaExito("Se modifico exitosamete");
                    menuClienteScene();
                    closeVentanaClienteMod();
                } else {

                    ManejadorErrores.getInstance().alertaFallo("No se puede modficar su perfil revise sus datos");
                }

            } else {
                ManejadorErrores.getInstance().alertaFallo("Campos Vacios");
            }

        } else {
            ManejadorErrores.getInstance().alertaFallo("Campos Nulos");

        }

    }

}
