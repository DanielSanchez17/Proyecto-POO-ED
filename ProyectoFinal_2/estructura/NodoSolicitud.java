package estructura;

import modelo.Solicitud;

public class NodoSolicitud {

    Solicitud dato;
    NodoSolicitud siguiente;

    public NodoSolicitud(Solicitud dato) {
        this.dato = dato;
        this.siguiente = null;
    }
}
