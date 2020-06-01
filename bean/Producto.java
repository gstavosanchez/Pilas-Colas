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
public class Producto {
    private int id;
    private String identificador;
    private String nombre;
    private String descripcion;
    private double preicio;
    private int existencias;
    private String imagen; 

    public Producto() {
    }

    public Producto(int id, String identificador, String nombre, String descripcion, double preicio, int existencias, String imagen) {
        this.id = id;
        this.identificador = identificador;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.preicio = preicio;
        this.existencias = existencias;
        this.imagen = imagen;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPreicio() {
        return preicio;
    }

    public void setPreicio(double preicio) {
        this.preicio = preicio;
    }

    public int getExistencias() {
        return existencias;
    }

    public void setExistencias(int existencias) {
        this.existencias = existencias;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    @Override
    public String toString() {
        return nombre;
    }
    
    
    
}
