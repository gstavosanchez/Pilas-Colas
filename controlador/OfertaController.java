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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.gustavosanchez.manejadores.ManejadorErrores;
import org.gustavosanchez.manejadores.ManejadorOferta;

/**
 * FXML Controller class
 *
 * @author elmer
 */
public class OfertaController implements Initializable {

    @FXML
    private JFXButton bttnRegresar;
    @FXML
    private JFXButton bttnAgregar;
    @FXML
    private JFXButton bttnMostrar;
    @FXML
    private JFXButton bttnEliminar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void clickRegresar(ActionEvent event) {
        menuAdminScene();
        closeVentana();
    }

    @FXML
    private void clickAgregar(ActionEvent event) {
        goAgregarOferta();
        closeVentanaAgregar();
    }

    @FXML
    private void clickMostrar(ActionEvent event) {
        goVerOferta();
        closeVentanaVer();
    }

    @FXML
    private void clickEliminar(ActionEvent event) {
        boolean estado = ManejadorOferta.getInstance().eliminar();
        if (estado) {
            ManejadorErrores.getInstance().alertaExito("Se elimino exitosamente");
        }else {
            ManejadorErrores.getInstance().alertaFallo("Error al eliminar la oferta");
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

    private void goAgregarOferta() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/gustavosanchez/vista/NuevaOferta.fxml"));
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

    private void closeVentanaAgregar() {
        Stage stage = (Stage) this.bttnAgregar.getScene().getWindow();
        stage.close();
    }

    private void goVerOferta() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/gustavosanchez/vista/VerOferta.fxml"));
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

    private void closeVentanaVer() {
        Stage stage = (Stage) this.bttnMostrar.getScene().getWindow();
        stage.close();
    }

}
