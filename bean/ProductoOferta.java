/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.gustavosanchez.bean;

/**
 *
 * @author elmer
 */
public class ProductoOferta {
    private int id;
    private Producto producto;
    private double precioOferta;
    private double precio;

    public ProductoOferta() {
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public ProductoOferta(int id, Producto producto, double precioOferta, double precio) {
        this.id = id;
        this.producto = producto;
        this.precioOferta = precioOferta;
        this.precio = precio;
    }

  

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public double getPrecioOferta() {
        return precioOferta;
    }

    public void setPrecioOferta(double precioOferta) {
        this.precioOferta = precioOferta;
    }
    
}
