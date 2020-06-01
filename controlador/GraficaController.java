/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.gustavosanchez.controlador;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import org.gustavosanchez.bean.Cancelado;
import org.gustavosanchez.bean.Producto;
import org.gustavosanchez.estructura.LCircular;
import org.gustavosanchez.manejadores.ManejadorArchivos;
import org.gustavosanchez.manejadores.ManejadorCarrito;
import org.gustavosanchez.manejadores.ManejadorErrores;
import org.gustavosanchez.manejadores.ManejadorOferta;
import org.gustavosanchez.manejadores.ManejadorProducto;

/**
 * FXML Controller class
 *
 * @author elmer
 */
public class GraficaController implements Initializable {

    @FXML
    private BarChart<?, ?> grafica;
    @FXML
    private NumberAxis ejeY;
    @FXML
    private CategoryAxis ejeX;
    @FXML
    private JFXButton bttnGenerar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        inicializar();
    }

    private void inicializar() {
        ArrayList<Producto> lista = ManejadorProducto.getInstance().getListaArrayProducto();
        XYChart.Series setUno = new XYChart.Series<>();
        for (Producto pro : lista) {
            setUno.getData().add(new XYChart.Data(pro.getNombre(), pro.getPreicio()));
            

        }

        setUno.setName("Producto");
        grafica.getData().addAll(setUno);

    }

    @FXML
    private void clickGenerar(ActionEvent event) {
        LCircular<Cancelado> lista = ManejadorCarrito.getInstance().getListaCancelado();
        if (lista.size() != 0) {
            ManejadorArchivos.getInstance().abrirPDFCanceladoGenerar(lista);
                ManejadorErrores.getInstance().alertaExito("Se genero el pdf correntamete ver en carpeta Grafix");
            
        }else {
            ManejadorErrores.getInstance().alertaFallo("Actualmente nadiea ha cancelado ninguna compra");
        }
        
    }

}
