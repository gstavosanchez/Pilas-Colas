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
public class Factura {
    private int id;
    private String nombre;
    private String nit;
    private TipoCliente tipoClinete;
    private String fecha;
    private String direccion;
    private double descuento;
    private double subTotal;
    private double total;
    private Usuario usuario;
    private LCircular<ProductoOferta> lista;


    public Factura() {
    }

    public Factura(int id, String nombre, String nit, TipoCliente tipoClinete, String fecha, String direccion, double descuento, double subTotal, double total, Usuario usuario) {
        this.id = id;
        this.nombre = nombre;
        this.nit = nit;
        this.tipoClinete = tipoClinete;
        this.fecha = fecha;
        this.direccion = direccion;
        this.descuento = descuento;
        this.subTotal = subTotal;
        this.total = total;
        this.usuario = usuario;
    }

    public LCircular<ProductoOferta> getLista() {
        return lista;
    }

    public void setLista(LCircular<ProductoOferta> lista) {
        this.lista = lista;
    }
    

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public TipoCliente getTipoClinete() {
        return tipoClinete;
    }

    public void setTipoClinete(TipoCliente tipoClinete) {
        this.tipoClinete = tipoClinete;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
    
    

   
    
    
}
