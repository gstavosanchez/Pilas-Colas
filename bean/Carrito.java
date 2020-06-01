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
public class Carrito {
    private int idCarrito;
    private Usuario usuario;
    private LCircular<ProductoOferta> listaProducto;
    private double total;
    private int totalProducto;

    public Carrito() {
    }

    public Carrito(int idCarrito, Usuario usuario, LCircular<ProductoOferta> listaProducto, double total, int totalProducto) {
        this.idCarrito = idCarrito;
        this.usuario = usuario;
        this.listaProducto = listaProducto;
        this.total = total;
        this.totalProducto = totalProducto;
    }

    public int getTotalProducto() {
        return totalProducto;
    }

    public void setTotalProducto(int totalProducto) {
        this.totalProducto = totalProducto;
    }

    

    
    
    public int getIdCarrito() {
        return idCarrito;
    }

    public void setIdCarrito(int idCarrito) {
        this.idCarrito = idCarrito;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public LCircular<ProductoOferta> getListaProducto() {
        return listaProducto;
    }

    public void setListaProducto(LCircular<ProductoOferta> listaProducto) {
        this.listaProducto = listaProducto;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
    
    
    
}
