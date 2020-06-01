/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.gustavosanchez.manejadores;

import java.util.ArrayList;
import org.gustavosanchez.bean.TipoCliente;
import org.gustavosanchez.bean.TipoUsuario;
import org.gustavosanchez.bean.Usuario;
import org.gustavosanchez.estructura.ListaDinamica;

/**
 *
 * @author elmer
 */
public class ManejadorUsuario {

    private static ManejadorUsuario instancia;
    private TipoUsuario[] listaTipo;
    private TipoCliente[] listaCliente;
    private ListaDinamica<Usuario> listaUsuario;
    private int contadorUsuario = 0;
    private Usuario usuarioLogeado;
    private ArrayList<Usuario> listArray;

    private ManejadorUsuario() {
        inicializate();
    }

    public static synchronized ManejadorUsuario getInstance() {
        if (instancia == null) {
            instancia = new ManejadorUsuario();
        }
        return instancia;
    }

    private void inicializate() {

        listArray = new ArrayList<>();
        listaUsuario = new ListaDinamica<>();
        listaTipo = new TipoUsuario[2];
        listaCliente = new TipoCliente[2];
        TipoUsuario admin = new TipoUsuario(1, "ADMIN");
        TipoUsuario basic = new TipoUsuario(2, "BASIC");
        listaTipo[0] = admin;
        listaTipo[1] = basic;

        TipoCliente normal = new TipoCliente(1, "Normal");
        TipoCliente frecuente = new TipoCliente(1, "Frecuente");
        listaCliente[0] = normal;
        listaCliente[1] = frecuente;

        Usuario user = new Usuario(contadorUsuario, "Gustavo", "Sanchez", "gustavosacnehz10059@gmail.com", "ADMIN", "ADMIN", "23423423", admin, frecuente,"2020-11-14","Gustavo Sanchez");
        listaUsuario.add(user);
        listArray.add(user);
        System.out.println(listaUsuario.indexOf(user));
        contadorUsuario++;
        Usuario userCleinte = new Usuario(contadorUsuario, "Elmer", "Garcia", "gustavosacnehz10059@gmail.com", "meme", "123", "23423423", basic, frecuente,"2020-11-14","Elmer Garcia");
        listaUsuario.add(userCleinte);
        listArray.add(userCleinte);
        contadorUsuario++;
    }

    public Usuario getUsuarioLogeado() {
        return usuarioLogeado;
    }

    public Usuario verificar(String nick, String password) {
        for (Usuario us : listaUsuario) {
            if (us != null) {
                if (us.getNick().equals(nick) && us.getPassword().equals(password)) {
                    this.usuarioLogeado = us;
                    return us;
                }
            }
        }
        return null;

    }

    public boolean agregarUsuario(String nombre, String apellido, String correo, String nick, String password, String tarjeta,String fecha,String nombreTarjeta) {

        if (!datosDuplicados(nick)) {
            Usuario us = new Usuario();
            us.setId(contadorUsuario);
            us.setNombre(nombre);
            us.setApellido(apellido);
            us.setCorreo(correo);
            us.setNick(nick);
            us.setPassword(password);
            us.setTarjeta(tarjeta);
            us.setTipoUsuario(getTipoUsuario("2"));
            us.setTipoCliente(getTipoCliente("1"));
            us.setFecha(fecha);
            us.setNombreTarjeta(nombreTarjeta);
            listaUsuario.add(us);
            //listArray.add(us);
            contadorUsuario++;
            System.out.println(listaUsuario.indexOf(us));
            return true;

        }

        return false;

    }

    public boolean modificar(String nombre, String apellido, String correo, String nick, String password, String tarjeta) {
        int posicionUsuario = listaUsuario.indexOf(usuarioLogeado);
        if (posicionUsuario >= 0) {
            if (!datosDuplicados(nick)) {
                Usuario us = new Usuario();
                us.setId(contadorUsuario);
                us.setNombre(nombre);
                us.setApellido(apellido);
                us.setCorreo(correo);
                us.setNick(nick);
                us.setPassword(password);
                us.setTarjeta(tarjeta);
                us.setTipoUsuario(getTipoUsuario("2"));
                us.setTipoCliente(getTipoCliente("1"));
                listaUsuario.modify(us, posicionUsuario);
                usuarioLogeado = us;
                System.out.println(listaUsuario.indexOf(us));
                return true;

            }
        }

        return false;

    }

    public boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (Exception ex) {
            return false;

        }

    }

    public boolean datosDuplicados(String input) {
        for (Usuario us : listaUsuario) {
            if (us != null) {
                if (us.getNick().equals(input)) {
                    return true;
                }

            }
        }
        return false;

    }

    private TipoUsuario getTipoUsuario(String id) {
        if (isInteger(id)) {
            for (TipoUsuario tP : listaTipo) {
                if (tP.getId() == Integer.parseInt(id)) {
                    return tP;
                }
            }

        } else {
            for (TipoUsuario tipoUsuario : listaTipo) {
                if (tipoUsuario.getNombre().equalsIgnoreCase(id) || id.equalsIgnoreCase(tipoUsuario.getNombre())) {
                    return tipoUsuario;
                }
            }
        }
        return null;
    }

    private TipoCliente getTipoCliente(String id) {
        if (isInteger(id)) {
            for (TipoCliente tP : listaCliente) {
                if (tP.getId() == Integer.parseInt(id)) {
                    return tP;
                }
            }

        } else {
            for (TipoCliente tipoUsuario : listaCliente) {
                if (tipoUsuario.getNombre().equalsIgnoreCase(id) || id.equalsIgnoreCase(tipoUsuario.getNombre())) {
                    return tipoUsuario;
                }
            }
        }
        return null;
    }

    public ListaDinamica<Usuario> getListaUsuario() {
        return listaUsuario;
    }

    public ArrayList<Usuario> getListArray() {
        llenarArray();
        return listArray;
    }

    public void llenarArray() {
        listArray.clear();
        for (Usuario us : listaUsuario) {
            if (us != null) {
                listArray.add(us);
            }
        }

    }

}
