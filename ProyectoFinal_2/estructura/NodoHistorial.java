package estructura;

import modelo.Solicitud;

public class NodoHistorial {

    Solicitud dato;
    NodoHistorial siguiente;

    public NodoHistorial(Solicitud dato) {
        this.dato = dato;
        this.siguiente = null;
    }
}
