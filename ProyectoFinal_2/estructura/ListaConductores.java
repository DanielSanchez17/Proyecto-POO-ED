package estructura;

import modelo.Conductor;
import modelo.TipoServicio;
import java.util.ArrayList;
import java.util.List;

public class ListaConductores {

    private NodoConductor cabeza;

    private int tamanio;

    public void agregar(Conductor conductor) {
        NodoConductor nuevo = new NodoConductor(conductor);

        if (cabeza == null) {
            cabeza = nuevo;
            tamanio++;
            return;
        }

        NodoConductor actual = cabeza;
        while (actual.siguiente != null) {
            actual = actual.siguiente;
        }
        actual.siguiente = nuevo;
        tamanio++;
    }

    public Conductor buscarDisponiblePara(TipoServicio tipo) {
        NodoConductor actual = cabeza;
        while (actual != null) {
            Conductor c = actual.dato;
            if (c.isDisponible() && c.estaHabilitadoPara(tipo)) {
                return c;
            }
            actual = actual.siguiente;
        }
        return null;
    }

    public Conductor buscarPorId(String id) {
        NodoConductor actual = cabeza;
        while (actual != null) {
            if (actual.dato.getId().equals(id)) {
                return actual.dato;
            }
            actual = actual.siguiente;
        }
        return null;
    }

    public List<Conductor> listar() {
        List<Conductor> lista = new ArrayList<>();
        NodoConductor actual = cabeza;
        while (actual != null) {
            lista.add(actual.dato);
            actual = actual.siguiente;
        }
        return lista;
    }

    public boolean estaVacia() {
        return cabeza == null;
    }

    public int getTamanio() {
        return tamanio;
    }
}
