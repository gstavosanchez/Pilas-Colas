/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.gustavosanchez.estructura;

/**
 *
 * @author elmer
 */
public class ListaDobleCircular<T> {

    private NodoDoble<T> cabeza;
    private int tamanio;

    public ListaDobleCircular() {
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
        NodoDoble<T> nuevo = new NodoDoble<>();
        nuevo.setElemnto(elemento);
        if (isEmpty()) {
            this.cabeza = nuevo;
            this.cabeza.setSiguiente(this.cabeza);
            this.cabeza.setAnterior(this.cabeza);
        } else {
            NodoDoble<T> aux = this.cabeza;
            while (!aux.getSiguiente().equals(this.cabeza)) {
                aux = aux.getSiguiente();
            }
            aux.setSiguiente(nuevo);
            nuevo.setAnterior(aux);
            nuevo.setSiguiente(this.cabeza);
            this.cabeza.setAnterior(nuevo);
        }
        tamanio++;
        return this.cabeza.getElemnto();

    }

    public NodoDoble<T> getPrimero() {
        if (isEmpty()) {
            return null;
        } else {
            return this.cabeza;
        }
    }

    public NodoDoble<T> getUltimo() {
        if (isEmpty()) {
            return null;
        } else {
            NodoDoble<T> aux = this.cabeza;
            while (!aux.getSiguiente().equals(this.cabeza)) {
                aux = aux.getSiguiente();
            }
            return aux;
        }
    }

    public NodoDoble<T> getNodo(int index) {
        if (isEmpty()) {
            return null;
        } else if (index == 0) {
            return getPrimero();
        } else if (index == size() - 1) {
            return getUltimo();
        } else {
            NodoDoble<T> buscado = cabeza;

            int contador = 0;
            while (contador < index) {
                contador++;
                buscado = buscado.getSiguiente();
            }

            return buscado;
        }
    }

    public T modify(T elemento, int index) {

        if (isEmpty() || (index < 0 || index >= size())) {
            return null;
        } else {

            NodoDoble<T> aux = getNodo(index);

            System.out.println("Elemento buscado,antes del update" + aux.getElemnto());
            aux.setElemnto(elemento);
            System.out.println("To String modficar:" + toString());
            return aux.getElemnto();

        }

    }

    public int indexOf(T elemento) {

        if (isEmpty()) {
            return -1;
        } else {

            NodoDoble<T> aux = cabeza;

            int posicion = 0;
            int contador = 0;
            while (contador < size()) {
                contador++;
                if (elemento.equals(aux.getElemnto())) {
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

            T elemento = cabeza.getElemnto();

            NodoDoble<T> aux = cabeza.getSiguiente();
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

            T elemento = getUltimo().getElemnto();

            NodoDoble<T> aux = getNodo(size() - 2);

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

            NodoDoble<T> nodo_anterior = getNodo(index - 1);

            NodoDoble<T> nodo_actual = getNodo(index);

            T elemento = nodo_actual.getElemnto();

            NodoDoble<T> nodo_siguiente = nodo_actual.getSiguiente();

            nodo_actual = null;

            nodo_anterior.setSiguiente(nodo_siguiente);

            tamanio--;

            return elemento;

        }
    }

}
