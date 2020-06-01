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
public class PilaDinamica<T> {

    private Nodo<T> top;
    private int tamanio;

    public PilaDinamica() {
        this.top = null;
        this.tamanio = 0;

    }

    public boolean isEmpty() {
        return top == null;
    }

    public int size() {
        return this.tamanio;
    }

    public T top() {
        if (isEmpty()) {
            return null;
        } else {
            return top.getElemento();
        }

    }

    public T pop() {
        if (isEmpty()) {
            return null;

        } else {
            T elemento = top.getElemento();
            Nodo<T> aux = top.getSiguiente();
            top = null;
            top = aux;
            this.tamanio--;

            return elemento;
        }

    }

    public void push(T elemento) {
        Nodo<T> aux = new Nodo<T>(elemento, top);
        top = aux;
        this.tamanio++;
    }

    public String toString() {
        if (isEmpty()) {
            return "La pila esta vacia";
        } else {
            String resulatdo = "";
            Nodo<T> aux = top;
            while (aux != null) {
                resulatdo += aux.toString();
                aux = aux.getSiguiente();
            }
            return resulatdo;
        }

    }

    public Nodo<T> getPrimero() {
        if (isEmpty()) {
            return null;
        } else {
            return this.top;
        }
    }

    public Nodo<T> getUltimo() {
        if (isEmpty()) {
            return null;
        } else {
            Nodo aux = this.top;
            while (aux.getSiguiente() != null) {
                aux = aux.getSiguiente();

            }
            return aux;
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
            Nodo<T> buscado = this.top;

            int contador = 0;
            while (contador < index) {
                contador++;
                buscado = buscado.getSiguiente();
            }

            return buscado;
        }
    }

}
