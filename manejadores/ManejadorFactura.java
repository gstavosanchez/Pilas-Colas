/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.gustavosanchez.manejadores;

import java.util.ArrayList;
import java.util.Date;
import org.gustavosanchez.bean.Factura;
import org.gustavosanchez.bean.ProductoOferta;
import org.gustavosanchez.bean.TipoCliente;
import org.gustavosanchez.estructura.LCircular;
import org.gustavosanchez.estructura.PilaDinamica;

/**
 *
 * @author elmer
 */
public class ManejadorFactura {

    private static ManejadorFactura instancia;
    private PilaDinamica<Factura> pila;
    private int contador = 1;
    private ArrayList<Factura> array;

    public static synchronized ManejadorFactura getInstance() {
        if (instancia == null) {
            instancia = new ManejadorFactura();
        }
        return instancia;
    }

    private ManejadorFactura() {
        pila = new PilaDinamica<>();
        array = new ArrayList<>();
    }

    public boolean agregarFacutra(String nombre, String nit, TipoCliente tipoCliente, String direccion, double descuento, double subTotal, double total,LCircular<ProductoOferta> lista) {
        if (ManejadorUsuario.getInstance().isInteger(nit)) {
            Factura fact = new Factura();
            fact.setId(contador);
            fact.setNombre(nombre);
            fact.setNit(nit);
            fact.setTipoClinete(tipoCliente);
            fact.setFecha(getFecha());
            fact.setDireccion(direccion);
            fact.setDescuento(descuento);
            fact.setSubTotal(subTotal);
            fact.setTotal(total);
            fact.setUsuario(ManejadorUsuario.getInstance().getUsuarioLogeado());
            fact.setLista(lista);
            contador++;
            pila.push(fact);
            ManejadorArchivos.getInstance().abrirPdfGenerar(fact, ManejadorCarrito.getInstance().getCarrito().getListaProducto());
            //ManejadorArchivos.getInstance().pdfGenerar(fact, ManejadorCarrito.getInstance().getCarrito().getListaProducto());
            
            return true;
        }

        return false;
    }

    public String getFecha() {
        Date fecha = new Date();
        String fechaActual = String.valueOf(fecha);
        return fechaActual;

    }
    public void llenar(){
        array.clear();
        for (int i = 0; i < pila.size(); i++) {
            Factura fac = pila.getNodo(i).getElemento();
            System.out.println("Fac: " + fac.getNombre());
            array.add(fac);
        }
    
    }

    public ArrayList<Factura> getArray() {
        llenar();
        return array;
    }
    
    public int getSize(){
    
        return pila.size();
    
    }
    

}
