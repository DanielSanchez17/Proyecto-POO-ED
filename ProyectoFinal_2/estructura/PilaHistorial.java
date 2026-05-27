package estructura;

import modelo.Solicitud;
import java.util.ArrayList;
import java.util.List;

public class PilaHistorial {

    private NodoHistorial cima;

    private int tamanio;

    public void push(Solicitud solicitud) {
        NodoHistorial nuevo = new NodoHistorial(solicitud);
        nuevo.siguiente = cima;
        cima = nuevo;
        tamanio++;
    }

    public Solicitud pop() {
        if (cima == null) {
            return null; 
        }
        Solicitud temporal = cima.dato;
        cima = cima.siguiente; 
        tamanio--;
        return temporal;
    }

    public Solicitud peek() {
        if (cima == null) return null;
        return cima.dato;
    }

    public List<Solicitud> listar() {
        List<Solicitud> lista = new ArrayList<>();
        NodoHistorial actual = cima;
        while (actual != null) {
            lista.add(actual.dato);
            actual = actual.siguiente;
        }
        return lista;
    }

    public boolean estaVacia() {
        return cima == null;
    }

    public int getTamanio() {
        return tamanio;
    }
}
