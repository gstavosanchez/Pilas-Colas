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
public class ListaCircular<T> implements Iterable<T> {

    private Nodo<T> primero;
    private Nodo<T> ultimo;
    private int tamanio;

    public ListaCircular() {
        primero = null;
        ultimo = null;
        tamanio = 0;
    }

    public boolean isEmpty() {
        return tamanio == 0;
    }

    public T getFirst() {

        if (isEmpty()) {
            return null;
        } else {
            return primero.getElemento();
        }
    }

    public T getLast() {

        if (isEmpty()) {
            return null;
        } else {
            return ultimo.getElemento();
        }
    }

    public T get(int index) {

        if (isEmpty() || (index < 0 || index >= size())) {
            return null;
        } else if (index == 0) {
            return getFirst();
        } else if (index == size() - 1) {
            return getLast();
        } else {

            Nodo<T> buscado = getNode(index);

            return buscado.getElemento();

        }

    }

    public int size() {
        return tamanio;
    }

    public Nodo<T> getNode(int index) {

        if (isEmpty() || (index < 0 || index >= size())) {
            return null;
        } else if (index == 0) {
            return primero;
        } else if (index == size() - 1) {
            return ultimo;
        } else {

            Nodo<T> buscado = primero;

            int contador = 0;
            while (contador < index) {
                contador++;
                buscado = buscado.getSiguiente();
            }

            return buscado;

        }

    }

    /* public T addFirst(T elemento) {

        Nodo<T> nuevo = new Nodo<>();

        nuevo.setElemento(elemento);
        if (isEmpty()) {
            primero = nuevo;
            ultimo = nuevo;
            ultimo.setSiguiente(primero);
        } else {
            nuevo.setSiguiente(primero);
            primero = nuevo;
            ultimo.setSiguiente(primero);

        }

        tamanio++;
        return primero.getElemento();

    } */
    public T addFirst(T elemento) {

        Nodo<T> aux;

        if (isEmpty()) {
            aux = new Nodo<>(elemento, null);
            primero = aux;
            ultimo = aux;
            ultimo.setSiguiente(primero);
        } else {

            Nodo<T> primeroActual = primero;
            aux = new Nodo<>(elemento, primeroActual);
            primero = aux;
            ultimo.setSiguiente(primero);

        }

        tamanio++;
        System.out.println("Este: " + primero.getElemento() + " Apunta  hacia: " + primero.getSiguiente().getElemento() + " Tamano " + tamanio);
        System.out.println("To string:" + toString());
        return primero.getElemento();

    }

    public T addLast(T elemento) {

        Nodo<T> aux;

        if (isEmpty()) {
            return addFirst(elemento);
        } else {

            aux = new Nodo<>(elemento, primero);

            ultimo.setSiguiente(aux);
            ultimo = aux;

        }

        tamanio++;
        System.out.println("Este: " + ultimo.getElemento() + " Apunta hacia:" + ultimo.getSiguiente().getElemento() + " Tamano " + tamanio);
        System.out.println("To string last:" + toString());
        return ultimo.getElemento();

    }

    /*public T addLast(T elemento) {

        Nodo<T> nuevo = new Nodo<>();

        if (isEmpty()) {
            return addFirst(elemento);
        } else {
            ultimo.setSiguiente(nuevo);
            nuevo.setSiguiente(nuevo);
            ultimo = nuevo;

        }

        tamanio++;
        return ultimo.getElemento();

    } */
    public T add(T elemento, int index) {

        if (index == 0) {
            return addFirst(elemento);
        } else if (index == size()) {
            return addLast(elemento);
        } else if ((index < 0 || index >= size())) {
            return null;
        } else {

            Nodo<T> buscado_anterior = getNode(index - 1);

            Nodo<T> buscado_actual = getNode(index);

            Nodo<T> aux = new Nodo<>(elemento, buscado_actual);

            buscado_anterior.setSiguiente(aux);

            tamanio++;
            System.out.println(getNode(index).getSiguiente().getElemento());
            System.out.println(toString());
            return getNode(index).getElemento();

        }

    }

    public String toString() {

        String contenido = "";

        if (isEmpty()) {
            contenido = "Lista vacia";
        } else {

            Nodo<T> aux = primero;

            int contador = 0;
            while (contador < size()) {
                contador++;
                contenido += aux.toString();
                aux = aux.getSiguiente();
            }

        }

        return contenido;

    }

    public boolean exists(T elemento) {

        if (isEmpty()) {
            return false;
        } else {

            Nodo<T> aux = primero;

            int contador = 0;
            while (contador < size()) {
                contador++;
                if (elemento.equals(aux.getElemento())) {
                    return true;
                }
                aux = aux.getSiguiente();
            }

            return false;

        }
    }

    public int indexOf(T elemento) {

        if (isEmpty()) {
            return -1;
        } else {

            Nodo<T> aux = primero;

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

            Nodo<T> aux = getNode(index);

            System.out.println("Elemento buscado,antes del update" + aux.getElemento());
            aux.setElemento(elemento);
            System.out.println("To String modficar:" + toString());
            return aux.getElemento();

        }

    }

    public T removeLast() {

        if (isEmpty()) {
            return null;
        } else {

            T elemento = ultimo.getElemento();

            Nodo<T> aux = getNode(size() - 2);

            if (aux == null) {

                ultimo = null;

                if (size() == 2) {
                    ultimo = primero;
                } else {
                    primero = null;
                }

            } else {

                ultimo = null;
                ultimo = aux;
                ultimo.setSiguiente(primero);
            }

            tamanio--;

            return elemento;

        }

    }

    public T removeFirst() {

        if (isEmpty()) {
            return null;
        } else {

            T elemento = primero.getElemento();

            Nodo<T> aux = primero.getSiguiente();
            primero = null;
            primero = aux;

            if (size() == 1) {
                ultimo = null;
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

            Nodo<T> nodo_anterior = getNode(index - 1);

            Nodo<T> nodo_actual = getNode(index);

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
            return getNode(siguiente) != null;
        }

        @Override
        public T next() {
            T elemento = getNode(siguiente).getElemento();
            siguiente++;
            return elemento;
        }

    }

}
