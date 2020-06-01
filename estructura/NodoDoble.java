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
public class NodoDoble<T> {
    private T elemnto;
    private NodoDoble<T> siguiente;
    private NodoDoble<T> anterior;

    public NodoDoble() {
    }

    public NodoDoble(T elemnto, NodoDoble<T> siguiente, NodoDoble<T> anterior) {
        this.elemnto = elemnto;
        this.siguiente = siguiente;
        this.anterior = anterior;
    }

    public T getElemnto() {
        return elemnto;
    }

    public void setElemnto(T elemnto) {
        this.elemnto = elemnto;
    }

    public NodoDoble<T> getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(NodoDoble<T> siguiente) {
        this.siguiente = siguiente;
    }

    public NodoDoble<T> getAnterior() {
        return anterior;
    }

    public void setAnterior(NodoDoble<T> anterior) {
        this.anterior = anterior;
    }
    
}
