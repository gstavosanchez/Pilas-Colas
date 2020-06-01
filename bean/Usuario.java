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
public class Usuario {
    private int id;
    private String nombre;
    private String apellido;
    private String correo;
    private String nick;
    private String password;
    private String tarjeta;
    private TipoUsuario tipoUsuario;
    private TipoCliente tipoCliente;
    private String fecha;
    private String nombreTarjeta;

    public Usuario() {
    }

    public Usuario(int id, String nombre, String apellido, String correo, String nick, String password, String tarjeta, TipoUsuario tipoUsuario, TipoCliente tipoCliente, String fecha, String nombreTarjeta) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.nick = nick;
        this.password = password;
        this.tarjeta = tarjeta;
        this.tipoUsuario = tipoUsuario;
        this.tipoCliente = tipoCliente;
        this.fecha = fecha;
        this.nombreTarjeta = nombreTarjeta;
    }
    

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getNombreTarjeta() {
        return nombreTarjeta;
    }

    public void setNombreTarjeta(String nombreTarjeta) {
        this.nombreTarjeta = nombreTarjeta;
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

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTarjeta() {
        return tarjeta;
    }

    public void setTarjeta(String tarjeta) {
        this.tarjeta = tarjeta;
    }

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public TipoCliente getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(TipoCliente tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    @Override
    public String toString() {
        return nombre;
    }
    
    
    
    
}
