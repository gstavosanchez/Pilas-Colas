/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.gustavosanchez.estructura;

import java.util.Iterator;

/**
 *
 * @author elmer
 */
public class ListaDinamica<T> implements Iterable<T> {

    private Nodo<T> cabeza;
    private int tamanio;

    public ListaDinamica() {
        this.cabeza = null;
        this.tamanio = 0;
    }

    public boolean isEmpty() {
        return tamanio == 0;
    }

    public int size() {
        return tamanio;
    }

    public T add(T elemento) {
        Nodo<T> nuevo = new Nodo(elemento, null);
        //nuevo.setElemento(elemento);
        if (isEmpty()) {
            this.cabeza = nuevo;
            this.cabeza.setSiguiente(null);
        } else {
            Nodo<T> aux = cabeza;
            while (aux.getSiguiente() != null) {
                aux = aux.getSiguiente();
            }
            aux.setSiguiente(nuevo);

            System.out.println("Elmento: " + aux.getElemento() + " apunta a" + aux.getSiguiente().getElemento());
        }
        tamanio++;
        return cabeza.getElemento();
    }

    public Nodo<T> getPrimero() {
        if (isEmpty()) {
            return null;
        } else {
            return this.cabeza;
        }
    }

    public T getElemntoFirst() {

        if (isEmpty()) {
            return null;
        } else {
            return cabeza.getElemento();
        }
    }

    public T getElemntoUltimo() {
        if (isEmpty()) {
            return null;
        } else {
            Nodo<T> aux = this.cabeza;
            while (aux.getSiguiente() != null) {
                aux = aux.getSiguiente();

            }
            return aux.getElemento();
        }
    }

    public Nodo<T> getUltimo() {
        if (isEmpty()) {
            return null;
        } else {
            Nodo aux = this.cabeza;
            while (aux.getSiguiente() != null) {
                aux = aux.getSiguiente();

            }
            return aux;
        }
    }

    public void clear() {
        this.cabeza = null;
        this.tamanio = 0;
    }

    public T getElemento(int index) {
        if (isEmpty()) {
            return null;
        } else if (index == 0) {
            return getElemntoFirst();
        } else if (index == size() - 1) {
            return getElemntoUltimo();
        } else {
            Nodo<T> buscado = cabeza;

            int contador = 0;
            while (contador < index) {
                contador++;
                buscado = buscado.getSiguiente();
            }

            return buscado.getElemento();
        }
    }

    public Nodo<T> getNodo(int index) {
        if (isEmpty()) {
            return null;
        } else if (index == 0) {
            return getPrimero();
        } else if (index == size() - 1) {
            return getUltimo();
        } else {
            Nodo<T> buscado = cabeza;

            int contador = 0;
            while (contador < index) {
                contador++;
                buscado = buscado.getSiguiente();
            }

            return buscado;
        }
    }

    public int indexOf(T elemento) {

        if (isEmpty()) {
            return -1;
        } else {

            Nodo<T> aux = cabeza;

            int posicion = 0;
            int contador = 0;
            while (contador < size()) {
                contador++;
                if (elemento.equals(aux.getElemento())) {
                    return posicion;
                }
                posicion++;
                aux = aux.getSiguiente();
            }

            return -1;

        }

    }

    public T modify(T elemento, int index) {

        if (isEmpty() || (index < 0 || index >= size())) {
            return null;
        } else {

            Nodo<T> aux = getNodo(index);

            System.out.println("Elemento buscado,antes del update" + aux.getElemento());
            aux.setElemento(elemento);
            System.out.println("To String modficar:" + toString());
            return aux.getElemento();

        }

    }

    public T removeFirst() {

        if (isEmpty()) {
            return null;
        } else {

            T elemento = cabeza.getElemento();

            Nodo<T> aux = cabeza.getSiguiente();
            cabeza = null;
            cabeza = aux;

            tamanio--;

            return elemento;

        }

    }

    @Override
    public Iterator<T> iterator() {
        return new MyIterator();
    }

    private class MyIterator<ListaDinamica> implements Iterator<T> {

        private int siguiente;

        @Override
        public boolean hasNext() {
            return getNodo(siguiente) != null;
        }

        @Override
        public T next() {
            T elemento = getNodo(siguiente).getElemento();
            siguiente++;
            return elemento;
        }

    }

}
