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
public class LCircular<T> implements Iterable<T> {

    private Nodo<T> cabeza;
    private int tamanio;

    public LCircular() {
        cabeza = null;
        tamanio = 0;
    }

    public boolean isEmpty() {
        return tamanio == 0;
    }

    public int size() {
        return tamanio;
    }

    public void clear() {
        this.cabeza = null;
        this.tamanio = 0;
    }

    public T add(T elemento) {
        Nodo<T> nuevo = new Nodo<>();
        nuevo.setElemento(elemento);
        if (isEmpty()) {
            this.cabeza = nuevo;
            this.cabeza.setSiguiente(this.cabeza);
        } else {
            Nodo<T> aux = cabeza;
            while (!aux.getSiguiente().equals(this.cabeza)) {
                aux = aux.getSiguiente();
            }
            aux.setSiguiente(nuevo);
            aux.getSiguiente().setSiguiente(cabeza);
            System.out.println("Elemento: " + aux.getSiguiente().getElemento() + " Apunta a: " + aux.getSiguiente().getSiguiente().getElemento());

        }

        tamanio++;

        return cabeza.getElemento();
    }

    public String toString() {

        String contenido = "";

        if (isEmpty()) {
            contenido = "Lista vacia";
        } else {

            Nodo<T> aux = cabeza;

            int contador = 0;
            while (contador < size()) {
                contador++;
                contenido += aux.toString();
                aux = aux.getSiguiente();
            }

        }

        return contenido;

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

    public T getElemntoUltimo() {
        if (isEmpty()) {
            return null;
        } else {
            Nodo<T> aux = this.cabeza;
            while (!aux.getSiguiente().equals(this.cabeza)) {
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
            while (!aux.getSiguiente().equals(this.cabeza)) {
                aux = aux.getSiguiente();

            }
            return aux;
        }
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

    public T removeLast() {

        if (isEmpty()) {
            return null;
        } else {

            T elemento = getElemntoUltimo();

            Nodo<T> aux = getNodo(size() - 2);

            if (aux == null) {

                if (size() == 2) {
                    cabeza = aux;
                } else {
                    cabeza = null;
                }

            } else {

                cabeza = aux;
                cabeza.setSiguiente(this.cabeza);
            }

            tamanio--;

            return elemento;

        }

    }

    public T remove(int index) {

        if (isEmpty() || (index < 0 || index >= size())) {
            return null;
        } else if (index == 0) {
            return removeFirst();
        } else if (index == size() - 1) {
            return removeLast();
        } else {

            Nodo<T> nodo_anterior = getNodo(index - 1);

            Nodo<T> nodo_actual = getNodo(index);

            T elemento = nodo_actual.getElemento();

            Nodo<T> nodo_siguiente = nodo_actual.getSiguiente();

            nodo_actual = null;

            nodo_anterior.setSiguiente(nodo_siguiente);

            tamanio--;

            return elemento;

        }
    }

    @Override
    public Iterator<T> iterator() {
        return new MyIterator();
    }

    private class MyIterator<ListaCircular> implements Iterator<T> {

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
