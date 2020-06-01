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
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.gustavosanchez.bean.Usuario;
import org.gustavosanchez.estructura.ColaPrioridad;
import org.gustavosanchez.estructura.LCircular;
import org.gustavosanchez.estructura.Nodo;
import org.gustavosanchez.manejadores.ManejadorErrores;
import org.gustavosanchez.manejadores.ManejadorUsuario;

/**
 * FXML Controller class
 *
 * @author elmer
 */
public class LoginController implements Initializable {

    private ColaPrioridad<String> cola = new ColaPrioridad<>();
    @FXML
    private JFXTextField txtFiedUsuario;
    @FXML
    private JFXPasswordField passField;
    @FXML
    private JFXButton bttnIngresar;
    @FXML
    private JFXButton bttnRegristrar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // prueba();
    }

    @FXML
    private void clickIngresar(ActionEvent event) {
        loggearse();
        //prueba();

    }

    private void loggearse() {

        if (!this.txtFiedUsuario.getText().isEmpty() && !this.passField.getText().isEmpty()) {
            if (this.txtFiedUsuario.getText() != null && this.passField.getText() != null) {
                String nick = this.txtFiedUsuario.getText().trim();
                String password = this.passField.getText().trim();
                Usuario user = ManejadorUsuario.getInstance().verificar(nick, password);
                if (user != null) {
                    if (user.getTipoUsuario().getNombre().equals("ADMIN")) {
                        limpiar();
                        System.out.println("Admin");

                        ManejadorErrores.getInstance().alertaExito("Bienvenido: " + user.getNombre());
                        openMenuAdmin();

                    } else if (user.getTipoUsuario().getNombre().equals("BASIC")) {
                        limpiar();
                        System.out.println("Basico");
                        ManejadorErrores.getInstance().alertaExito("Bienvenido: " + user.getNombre());
                        openMenuCliente();
                        
                    }
                } else {

                    ManejadorErrores.getInstance().alertaFallo("Usuario o Contraseña ERRONEO. . .!");
                }

            } else {
                ManejadorErrores.getInstance().alertaFallo("Campos NULOS. . .!");
            }
        } else {
            ManejadorErrores.getInstance().alertaFallo("Campos VACIOS. . .!");
        }

    }

    @FXML
    private void clickRegistrase(ActionEvent event) {
        openRegistrarse();
        // prueba();
    }

    private void limpiar() {
        this.txtFiedUsuario.setText("");
        this.passField.setText("");
    }

    private void openRegistrarse() {
        registrarseScene();
        closeVentanaRegistrarse();

    }

    private void closeVentanaIngresar() {
        Stage stage = (Stage) this.bttnIngresar.getScene().getWindow();
        stage.close();
    }

    private void closeVentanaRegistrarse() {
        Stage stage = (Stage) this.bttnRegristrar.getScene().getWindow();
        stage.close();
    }

    private void registrarseScene() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/gustavosanchez/vista/Registrarse.fxml"));
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

    private void closeVentanaLogin() {
        Stage stage = (Stage) this.bttnIngresar.getScene().getWindow();
        stage.close();
    }

    private void openMenuAdmin() {
        closeVentanaLogin();
        menuAdminScene();

    }
    private void openMenuCliente() {
        closeVentanaLogin();
        menuClienteScene();

    }

    private void prueba() {
        cola.add("Hola");
        cola.add("Pato");
        cola.add("Lupe");
        for (int i = 0; i < cola.size(); i++) {
            String a = cola.getElemento(i);
            System.out.println(a);
        }
        System.out.println("Vaciar lista");
        cola.clear();
        for (int i = 0; i < cola.size(); i++) {
            String a = cola.getElemento(i);
            System.out.println(a);
        }
        System.out.println("Llenar otra vez");
        cola.add("Hola");
        cola.add("Pato");
        cola.add("Lupe");
        for (int i = 0; i < cola.size(); i++) {
            String a = cola.getElemento(i);
            System.out.println(a);
        }

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

}
