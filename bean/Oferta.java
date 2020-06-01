/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.gustavosanchez.bean;

import org.gustavosanchez.estructura.LCircular;

/**
 *
 * @author elmer
 */
public class Oferta {
    private int id;
    private String descripcion;
    private String descuento;
    private LCircular<Producto> listaProductoOferta;
    private TipoOferta prioridad;
    private int prio;

    public int getPrio() {
        return prio;
    }

    public void setPrio(int prio) {
        this.prio = prio;
    }

    public Oferta(int id, String descripcion, String descuento, LCircular<Producto> listaProductoOferta, TipoOferta prioridad, int prio) {
        this.id = id;
        this.descripcion = descripcion;
        this.descuento = descuento;
        this.listaProductoOferta = listaProductoOferta;
        this.prioridad = prioridad;
        this.prio = prio;
    }

    

    public Oferta() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescuento() {
        return descuento;
    }

    public void setDescuento(String descuento) {
        this.descuento = descuento;
    }

    public LCircular<Producto> getListaProductoOferta() {
        return listaProductoOferta;
    }

    public void setListaProductoOferta(LCircular<Producto> listaProductoOferta) {
        this.listaProductoOferta = listaProductoOferta;
    }

    public TipoOferta getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(TipoOferta prioridad) {
        this.prioridad = prioridad;
    }

    @Override
    public String toString() {
        return  descripcion;
    }
    
    
    
    
    
}
