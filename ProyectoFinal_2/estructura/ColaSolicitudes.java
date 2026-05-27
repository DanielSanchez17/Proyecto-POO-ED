package estructura;

import modelo.Solicitud;
import java.util.ArrayList;
import java.util.List;

public class ColaSolicitudes {

    private NodoSolicitud frente;
    private NodoSolicitud fin;
    private int tamanio;

    public void encolar(Solicitud solicitud) {
        NodoSolicitud nuevo = new NodoSolicitud(solicitud);
        if (fin == null) {
            frente = nuevo;
            fin = nuevo;
        } else {
            fin.siguiente = nuevo;
            fin = nuevo;
        }
        tamanio++;
    }

    public void encolarAlFrente(Solicitud solicitud) {
        NodoSolicitud nuevo = new NodoSolicitud(solicitud);
        if (frente == null) {
            frente = nuevo;
            fin = nuevo;
        } else {
            nuevo.siguiente = frente;
            frente = nuevo;
        }
        tamanio++;
    }

    public Solicitud desencolar() {
        if (frente == null) {
            return null; 
        }
        Solicitud temporal = frente.dato;
        frente = frente.siguiente;
        if (frente == null) {
            fin = null; 
        }
        tamanio--;
        return temporal;
    }

    public Solicitud buscarPorId(int id) {
        NodoSolicitud actual = frente;
        while (actual != null) {
            if (actual.dato.getId() == id) {
                return actual.dato;
            }
            actual = actual.siguiente;
        }
        return null;
    }

    public boolean eliminarPorId(int id) {
        if (frente == null) return false;

        if (frente.dato.getId() == id) {
            frente = frente.siguiente;
            if (frente == null) fin = null;
            tamanio--;
            return true;
        }

        NodoSolicitud anterior = frente;
        while (anterior.siguiente != null) {
            if (anterior.siguiente.dato.getId() == id) {
                if (anterior.siguiente == fin) {
                    fin = anterior; 
                }
                anterior.siguiente = anterior.siguiente.siguiente;
                tamanio--;
                return true;
            }
            anterior = anterior.siguiente;
        }
        return false;
    }

    public List<Solicitud> listar() {
        List<Solicitud> lista = new ArrayList<>();
        NodoSolicitud actual = frente;
        while (actual != null) {
            lista.add(actual.dato);
            actual = actual.siguiente;
        }
        return lista;
    }

    public boolean estaVacia() {
        return frente == null;
    }

    public int getTamanio() {
        return tamanio;
    }
}
