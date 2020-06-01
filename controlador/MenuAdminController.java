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
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.gustavosanchez.manejadores.ManejadorArchivos;
import org.gustavosanchez.manejadores.ManejadorErrores;
import org.gustavosanchez.manejadores.ManejadorFactura;

/**
 * FXML Controller class
 *
 * @author elmer
 */
public class MenuAdminController implements Initializable {
    
    @FXML
    private HBox hBox;
    @FXML
    private JFXButton bttnUsuario;
    @FXML
    private JFXButton bttnProducto;
    @FXML
    private JFXButton bttnOferta;
    @FXML
    private HBox hBoxAbajo;
    @FXML
    private JFXButton bttnReportes;
    @FXML
    private JFXButton bttnFactura;
    @FXML
    private JFXButton bttnRegresar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        // ManejadorArchivos.getInstance().escribirArchivo();
    }
    
    @FXML
    private void clickUsuario(ActionEvent event) {
        goUser();
        closeVentanaUsuario();
    }
    
    @FXML
    private void clickProducto(ActionEvent event) {
        goProduct();
        closeVentanaProducto();
    }
    
    @FXML
    private void clickOferta(ActionEvent event) {
        goOferta();
        closeVentanaOferta();
    }
    
    @FXML
    private void clickReportes(ActionEvent event) {
        goReportes();
    }
    
    @FXML
    private void clicFactura(ActionEvent event) {
        int vacio = ManejadorFactura.getInstance().getSize();
       if (vacio != 0) {
            goFactura();
        } else {
            ManejadorErrores.getInstance().alertaFallo("Actualmente no hay facturas");
        }
    }
    
    private void backLogin() {
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
    
    private void goUser() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/gustavosanchez/vista/Usuario.fxml"));
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
    
    private void goProduct() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/gustavosanchez/vista/Producto.fxml"));
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
    
    private void goOferta() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/gustavosanchez/vista/Oferta.fxml"));
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
    
    private void closeVentanaOferta() {
        Stage stage = (Stage) this.bttnOferta.getScene().getWindow();
        stage.close();
    }
    
    private void closeVentanaProducto() {
        Stage stage = (Stage) this.bttnProducto.getScene().getWindow();
        stage.close();
    }
    
    private void closeVentanaRegresar() {
        Stage stage = (Stage) this.bttnRegresar.getScene().getWindow();
        stage.close();
    }
    
    private void closeVentanaUsuario() {
        Stage stage = (Stage) this.bttnUsuario.getScene().getWindow();
        stage.close();
    }
    
    @FXML
    private void clickRegresar(ActionEvent event) {
        backLogin();
        closeVentanaRegresar();
    }
    
    private void goReportes() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/gustavosanchez/vista/Grafica.fxml"));
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
    
    private void goFactura() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/gustavosanchez/vista/Factura.fxml"));
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
