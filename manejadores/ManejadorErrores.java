/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.gustavosanchez.manejadores;

/**
 *
 * @author elmer
 */
import javafx.scene.control.Alert;

public class ManejadorErrores {

    private static ManejadorErrores instancia;


    private ManejadorErrores() {
    }

    public static synchronized ManejadorErrores getInstance() {
        if (instancia == null) {
            instancia = new ManejadorErrores();
        }
        return instancia;
    }

    public void alertaFallo(String input) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Error");
        alert.setContentText(input);
        alert.showAndWait();
    }

    public void alertaExito(String input) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle("Exito");
        alert.setContentText(input);
        alert.showAndWait();
    }




}
