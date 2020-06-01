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
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import org.gustavosanchez.manejadores.ManejadorErrores;
import org.gustavosanchez.manejadores.ManejadorUsuario;

/**
 * FXML Controller class
 *
 * @author elmer
 */
public class RegistrarseController implements Initializable {

    @FXML
    private JFXButton bttnRegresar;
    @FXML
    private JFXTextField txtNombre;
    @FXML
    private JFXPasswordField passField;
    @FXML
    private JFXTextField txtApellido;
    @FXML
    private JFXTextField txtCorreo;
    @FXML
    private JFXTextField txtUsuario;
    @FXML
    private JFXTextField txtTarjeta;
    @FXML
    private JFXButton bttnRegistrase;
    @FXML
    private JFXTextField txtNombreTarjeta;
    @FXML
    private DatePicker date;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void clickRegresar(ActionEvent event) {
        openLogin();
    }

    @FXML
    private void clickRegistrase(ActionEvent event) {
        agregarUsuario();
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
        Stage stage = (Stage) this.bttnRegresar.getScene().getWindow();
        stage.close();
    }

    private void closeVentanaRegistrar() {
        Stage stage = (Stage) this.bttnRegistrase.getScene().getWindow();
        stage.close();
    }

    private void openLogin() {
        registrarseScene();
        closeVentanaRegresar();
    }

    private void openLoginRegistrarse() {
        registrarseScene();
        closeVentanaRegistrar();
    }

    private void agregarUsuario() {
        if (!this.txtNombre.getText().isEmpty() && !this.txtApellido.getText().isEmpty()
                && !this.txtCorreo.getText().isEmpty() && !this.txtUsuario.getText().isEmpty()
                && !this.passField.getText().isEmpty() && !this.txtTarjeta.getText().isEmpty()) {
            if (this.txtNombre.getText() != null && this.txtApellido.getText() != null
                    && this.txtCorreo.getText() != null && this.txtUsuario.getText() != null
                    && this.passField.getText() != null && this.txtTarjeta.getText() != null
                    && this.date.getValue() != null && this.txtNombreTarjeta.getText() != null) {
                String nombre = this.txtNombre.getText().trim();
                String apellido = this.txtApellido.getText().trim();
                String correo = this.txtCorreo.getText().trim();
                String nick = this.txtUsuario.getText().trim();
                String password = this.passField.getText().trim();
                String tarjeta = this.txtTarjeta.getText().trim();
                LocalDate fechaInicio = this.date.getValue();
                String fecha = String.valueOf(fechaInicio);
                String nombreTarjeta = this.txtNombreTarjeta.getText().trim();
                boolean estado = ManejadorUsuario.getInstance().agregarUsuario(nombre, apellido, correo, nick, password, tarjeta, fecha, nombreTarjeta);

                if (estado) {
                    ManejadorErrores.getInstance().alertaExito("Se agrego exitosamente . . . !");
                    openLoginRegistrarse();
                } else {
                    ManejadorErrores.getInstance().alertaFallo("El usuario ya existe . . . !");
                }

            } else {
                ManejadorErrores.getInstance().alertaFallo("Campos NULOS. . .!");

            }

        } else {
            ManejadorErrores.getInstance().alertaFallo("Campos VACIOS. . .!");

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
    private void verificarApellido(KeyEvent event) {
        String filtroNombre = this.txtApellido.getText();
        if (!filtroNombre.isEmpty()) {
            if (ManejadorUsuario.getInstance().isInteger(filtroNombre)) {
                ManejadorErrores.getInstance().alertaFallo("Solo se acepta letras");
                this.txtApellido.setText("");
            }
        }
    }

    @FXML
    private void verificarTarjeta(KeyEvent event) {
        String filtroNombre = this.txtTarjeta.getText();
        if (!filtroNombre.isEmpty()) {
            if (!ManejadorUsuario.getInstance().isInteger(filtroNombre)) {
                ManejadorErrores.getInstance().alertaFallo("Solo se acepta numeros");
                this.txtTarjeta.setText("");
            }
        }
    }

}
