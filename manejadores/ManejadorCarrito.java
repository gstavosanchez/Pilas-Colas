/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.gustavosanchez.manejadores;

import java.util.ArrayList;
import org.gustavosanchez.bean.Cancelado;
import org.gustavosanchez.bean.Carrito;
import org.gustavosanchez.bean.ProductoOferta;
import org.gustavosanchez.estructura.LCircular;
import org.gustavosanchez.estructura.ListaDobleCircular;

/**
 *
 * @author elmer
 */
public class ManejadorCarrito {

    private static ManejadorCarrito instancia;
    private ListaDobleCircular<Carrito> listaCarrita;
    private ArrayList<Carrito> arrayCarrito;
    private int contador = 1;
    private boolean estado = false;
    private LCircular<Cancelado> listaCancelado;
    int contadorCancelado = 1;

    private ManejadorCarrito() {
        listaCarrita = new ListaDobleCircular<>();
        arrayCarrito = new ArrayList<>();
        listaCancelado = new LCircular<>();
    }

    public static synchronized ManejadorCarrito getInstance() {
        if (instancia == null) {
            instancia = new ManejadorCarrito();
        }
        return instancia;
    }

    public boolean agregarCarrito(LCircular<ProductoOferta> list, double total, int cantidad) {
        if (listaCarrita.size() == 0) {
            Carrito nuevo = new Carrito();
            nuevo.setIdCarrito(contador);
            nuevo.setTotal(total);
            nuevo.setUsuario(ManejadorUsuario.getInstance().getUsuarioLogeado());
            nuevo.setListaProducto(list);
            nuevo.setTotalProducto(cantidad);
            contador++;
            listaCarrita.add(nuevo);
            return true;
        } else {
            Carrito car = getCarrito();
            if (car == null) {
                Carrito nuevo = new Carrito();
                nuevo.setIdCarrito(contador);
                nuevo.setTotal(total);
                nuevo.setUsuario(ManejadorUsuario.getInstance().getUsuarioLogeado());
                nuevo.setListaProducto(list);
                nuevo.setTotalProducto(cantidad);
                contador++;
                listaCarrita.add(nuevo);
                return true;
            } else {
                if (actualizar(list, car, total, cantidad)) {
                    return true;
                }
            }

        }
        /*   for (int i = 0; i < listaCarrita.size(); i++) {
            Carrito car = listaCarrita.getNodo(i).getElemnto();

            if (car == null) {
                Carrito nuevo = new Carrito();
                nuevo.setIdCarrito(contador);
                nuevo.setTotal(total);
                nuevo.setUsuario(ManejadorUsuario.getInstance().getUsuarioLogeado());
                nuevo.setListaProducto(list);
                nuevo.setTotalProducto(cantidad);
                contador++;
                listaCarrita.add(nuevo);
                estado = true;
                return true;
            } else {
                if (actualizar(list, car, total, cantidad)) {
                    return true;
                }
            }
        } */
        return true;
    }

    public boolean actualizar(LCircular<ProductoOferta> lista, Carrito car, double total, int cantidad) {
        if (car != null && car.getUsuario().equals(ManejadorUsuario.getInstance().getUsuarioLogeado())) {
            LCircular<ProductoOferta> li = car.getListaProducto();
            for (int i = 0; i < lista.size(); i++) {
                ProductoOferta pro = lista.getElemento(i);
                li.add(pro);
            }
            car.setListaProducto(li);
            car.setTotal(car.getTotal() + total);
            car.setTotalProducto(car.getTotalProducto() + cantidad);
            int posicion = listaCarrita.indexOf(car);
            listaCarrita.modify(car, posicion);
            return true;
        }
        return false;
    }

    public Carrito getCarrito() {
        for (int i = 0; i < listaCarrita.size(); i++) {
            Carrito car = listaCarrita.getNodo(i).getElemnto();
            if (car.getUsuario().equals(ManejadorUsuario.getInstance().getUsuarioLogeado())) {
                return car;
            }
        }
        return null;

    }

    public ArrayList<ProductoOferta> getArrayList() {
        System.out.println("Metodo ver listado en carrito:");
        Carrito car = getCarrito();
        LCircular<ProductoOferta> list = car.getListaProducto();
        System.out.println("Metodo ver listado en carrito:" + list.size());
        ArrayList<ProductoOferta> array = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            ProductoOferta pro = list.getNodo(i).getElemento();
            System.out.println(pro.getProducto().getNombre());
            array.add(pro);
        }
        return array;
    }

    public boolean modificar(LCircular<ProductoOferta> lista, int cantidad, double total) {
        Carrito car = getCarrito();
        if (car != null) {
            int posicion = listaCarrita.indexOf(car);
            car.setListaProducto(lista);
            car.setTotal(total);
            car.setTotalProducto(cantidad);
            listaCarrita.modify(car, posicion);
            return true;
        }

        return false;
    }

    public boolean eliminar(String parametro) {
        Carrito car = getCarrito();
        if (car != null) {
            int posicion = listaCarrita.indexOf(car);
            if (parametro.equalsIgnoreCase("cancelar")) {
                agregarCancelado();
                listaCarrita.remove(posicion);
                return true;
            } else if (parametro.equalsIgnoreCase("pagar")) {
                listaCarrita.remove(posicion);
                return true;
            }

        }

        return false;
    }

    public void agregarCancelado() {
        int numero = listaCancelado.size();
        int c = 1;
        if (numero == 0) {
            Cancelado can = new Cancelado();
            can.setId(contadorCancelado);
            can.setUsuario(ManejadorUsuario.getInstance().getUsuarioLogeado());
            can.setVeces(c);
            contadorCancelado++;
            listaCancelado.add(can);
        } else {
            Cancelado ca = getCancelado();
            if (ca == null) {
                Cancelado cancelado = new Cancelado();
                cancelado.setId(contador);
                cancelado.setUsuario(ManejadorUsuario.getInstance().getUsuarioLogeado());
                cancelado.setVeces(c);
                contadorCancelado++;
                listaCancelado.add(cancelado);
            } else {
                updateCancelado(ca);
            }
        }

    }

    public void updateCancelado(Cancelado ca) {
        if (ca != null) {
            int posicion = listaCancelado.indexOf(ca);
            Cancelado actualizado = new Cancelado();
            actualizado.setId(ca.getId());
            actualizado.setUsuario(ca.getUsuario());
            actualizado.setVeces(ca.getVeces() + 1);
            listaCancelado.modify(actualizado, posicion);

        }
    }

    public Cancelado getCancelado() {
        for (int i = 0; i < listaCancelado.size(); i++) {
            Cancelado ca = listaCancelado.getNodo(i).getElemento();
            if (ca != null) {
                if (ca.getUsuario().equals(ManejadorUsuario.getInstance().getUsuarioLogeado())) {
                    return ca;
                }
            }
        }
        return null;
    }
    
    
    public LCircular<Cancelado> getListaCancelado(){
        return listaCancelado;
    
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

}
