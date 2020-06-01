/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.gustavosanchez.manejadores;

import java.util.ArrayList;
import org.gustavosanchez.bean.Oferta;
import org.gustavosanchez.bean.Producto;
import org.gustavosanchez.bean.TipoOferta;
import org.gustavosanchez.estructura.ColaPrioridad;
import org.gustavosanchez.estructura.LCircular;

/**
 *
 * @author elmer
 */
public class ManejadorOferta {
    
    private static ManejadorOferta instancia;
    private ColaPrioridad<Oferta> colaPrioridadAlta;
    private ColaPrioridad<Oferta> colaPrioridadBaja;
    private ColaPrioridad<Oferta> listaCola;
    private ArrayList<Oferta> arrayListaOferta;
    private int contador = 1;
    private TipoOferta[] listaTipo;
    
    private ManejadorOferta() {
        colaPrioridadAlta = new ColaPrioridad<>();
        colaPrioridadBaja = new ColaPrioridad<>();
        arrayListaOferta = new ArrayList<>();
        listaCola = new ColaPrioridad<>();
        listaTipo = new TipoOferta[2];
        inicializar();
    }
    
    public static synchronized ManejadorOferta getInstance() {
        if (instancia == null) {
            instancia = new ManejadorOferta();
        }
        return instancia;
    }
    
    public boolean agregarOfertaInterfaz(String descripcion, String descuento, LCircular<Producto> lista, TipoOferta prioridad) {
        if (prioridad != null) {
            if (prioridad.getNombre().equalsIgnoreCase("Alta")) {
                
                if (ManejadorProducto.getInstance().isInteger(descuento)) {
                    Oferta nueva = new Oferta();
                    nueva.setId(contador);
                    nueva.setDescripcion(descripcion);
                    nueva.setDescuento(descuento);
                    nueva.setListaProductoOferta(lista);
                    nueva.setPrioridad(prioridad);
                    nueva.setPrio(2);
                    colaPrioridadAlta.add(nueva);
                    contador++;
                    return true;
                }
            } else if (prioridad.getNombre().equalsIgnoreCase("Baja")) {
                if (ManejadorProducto.getInstance().isInteger(descuento)) {
                    Oferta nueva = new Oferta();
                    nueva.setId(contador);
                    nueva.setDescripcion(descripcion);
                    nueva.setDescuento(descuento);
                    nueva.setListaProductoOferta(lista);
                    nueva.setPrioridad(prioridad);
                    nueva.setPrio(1);
                    colaPrioridadBaja.add(nueva);
                    contador++;
                    return true;
                }
                
            }
            
        }
        
        return false;
    }
    
    public void inicializar() {
        TipoOferta tipo = new TipoOferta(1, "Alta");
        TipoOferta tipo2 = new TipoOferta(2, "Baja");
        listaTipo[0] = tipo;
        listaTipo[1] = tipo2;
    }
    
    public TipoOferta getTipoOferta(String dato) {
        for (TipoOferta t : listaTipo) {
            if (t != null) {
                if (t.getNombre().toLowerCase().equalsIgnoreCase(dato.toLowerCase())) {
                    return t;
                }
            }
        }
        return null;
        
    }
    
    private void agregarColaPrincipal() {
        this.listaCola.clear();
        for (int i = 0; i < colaPrioridadAlta.size(); i++) {
            Oferta of = colaPrioridadAlta.getElemento(i);
            listaCola.add(of);
            
        }
        for (int i = 0; i < colaPrioridadBaja.size(); i++) {
            Oferta of = colaPrioridadBaja.getElemento(i);
            listaCola.add(of);
        }
        arrayListaOferta.clear();
        for (int i = 0; i < listaCola.size(); i++) {
            Oferta of = this.listaCola.getElemento(i);
            this.arrayListaOferta.add(of);
        }
        
    }
    
    public ArrayList<Oferta> getArrayListaOferta() {
        agregarColaPrincipal();
        return arrayListaOferta;
    }
    
    public TipoOferta[] getListaTipoOferta() {
        return listaTipo;
        
    }
    
    public boolean eliminar() {
        if (!listaCola.isEmpty()) {
            Oferta of = listaCola.getElemntoFirst();
            if (of.getPrioridad().getNombre().equalsIgnoreCase("Alta")) {
                colaPrioridadAlta.removeFirst();
                return true;
            } else if (of.getPrioridad().getNombre().equalsIgnoreCase("Baja")) {
                colaPrioridadBaja.removeFirst();
                return true;
            }
        } 
        
        return false;
    }
    
}
