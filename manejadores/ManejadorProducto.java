/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.gustavosanchez.manejadores;

import java.util.ArrayList;
import org.gustavosanchez.bean.Producto;
import org.gustavosanchez.estructura.LCircular;

/**
 *
 * @author elmer
 */
public class ManejadorProducto {

    private static ManejadorProducto instancia;
    //private ListaCircular<Producto> listaProdcuto;
    private LCircular<Producto> listaCircular;
    private ArrayList<Producto> listaArrayProducto;
    private int contadorProducto = 1;

    private ManejadorProducto() {
        listaCircular = new LCircular<>();
        listaArrayProducto = new ArrayList<>();

    }

    public static synchronized ManejadorProducto getInstance() {
        if (instancia == null) {
            instancia = new ManejadorProducto();
        }
        return instancia;
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
        for (int i = 0; i < listaCircular.size(); i++) {
            Producto pro = listaCircular.getElemento(i);
            if (pro.getIdentificador().equals(input)) {
                return true;
            }
        }
        return false;

    }

    public boolean isDouble(String input) {
        try {
            Double.parseDouble(input);
            System.out.println(Double.parseDouble(input));
            return true;
        } catch (NumberFormatException ex) {
            return false;

        }

    }

    public boolean agregarProducto(String identificador, String nombre, String precio, String descripcion, String existencia, String imagen) {
        System.out.println("Esta antes del if");
        if (!datosDuplicados(identificador) && isInteger(identificador) && isDouble(precio) && isInteger(existencia)) {
            System.out.println("Paso el if");
            Producto producto = new Producto();
            producto.setId(contadorProducto);
            producto.setIdentificador(identificador);
            producto.setNombre(nombre);
            producto.setPreicio(Double.parseDouble(precio));
            producto.setDescripcion(descripcion);
            producto.setExistencias(Integer.parseInt(existencia));
            producto.setImagen(imagen);
            listaCircular.add(producto);
            contadorProducto++;
            //llenarArray();
            return true;
        }

        return false;
    }

    public int getContadorProducto() {
        return contadorProducto;
    }

    public void setContadorProducto(int contadorProducto) {
        this.contadorProducto = contadorProducto;
    }

    public boolean llenarArray() {
        listaArrayProducto.clear();
        System.out.println("LLenar array  tamano:" + listaCircular.size());

        for (int i = 0; i < listaCircular.size(); i++) {
            System.out.println("------------------------------------");
            System.out.println(listaCircular.getElemento(i).getNombre() + " identificadro:" + listaCircular.getElemento(i).getIdentificador());
            //System.out.println("------------------------------------");
            listaArrayProducto.add(listaCircular.getElemento(i));
            //return true;

        }
        return true;
    }

    public ArrayList<Producto> getListaArrayProducto() {
        if (llenarArray()) {

            return listaArrayProducto;
        }
        return listaArrayProducto;
    }

    public LCircular<Producto>  getCircular() {
        return listaCircular;
    }

    public void mostrarArrayList() {
        for (Producto p : listaArrayProducto) {
            System.out.println("Mostrar arrayList: " + p.getNombre() + " ident:" + p.getIdentificador());
        }
    }

    public Producto seachProducto(String input) {
        for (int i = 0; i < listaCircular.size(); i++) {
            Producto pro = listaCircular.getElemento(i);
            if (isInteger(input)) {
                if (pro.getId() == Integer.parseInt(input)) {
                    return pro;
                }
            } else {

                if (pro.getIdentificador().equalsIgnoreCase(input) || pro.getNombre().equalsIgnoreCase(input)) {
                    return pro;
                }
            }
        }
        return null;
    }

    public Producto buscar(String input) {
        for (Producto pro : listaArrayProducto) {
            if (pro != null) {
                if (pro.getIdentificador().equalsIgnoreCase(input) || pro.getNombre().equalsIgnoreCase(input)) {
                    return pro;
                }
            }
        }
        return null;

    }

    public boolean modificar(String id, String identificador, String nombre, String precio, String descripcion, String existencia, String imagen) {
        Producto buscado = seachProducto(id);
        if (buscado != null && !datosDuplicados(identificador)) {
            int posicion = listaCircular.indexOf(buscado);
            Producto producto = new Producto();
            producto.setId(buscado.getId());
            producto.setIdentificador(identificador);
            producto.setNombre(nombre);
            producto.setPreicio(Double.parseDouble(precio));
            producto.setDescripcion(descripcion);
            producto.setExistencias(Integer.parseInt(existencia));
            producto.setImagen(imagen);
            listaCircular.modify(producto, posicion);
            llenarArray();
            return true;
        }

        return false;
    }

    public boolean eliminar(String id) {
        Producto buscado = seachProducto(id);
        if (buscado != null) {
            int posicion = listaCircular.indexOf(buscado);
            listaCircular.remove(posicion);
            //llenarArray();
            return true;

        }

        return false;
    }

}
