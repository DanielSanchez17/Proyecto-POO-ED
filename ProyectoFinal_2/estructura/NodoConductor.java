package estructura;

import modelo.Conductor;

public class NodoConductor {

    Conductor dato;
    NodoConductor siguiente;

    public NodoConductor(Conductor dato) {
        this.dato = dato;
        this.siguiente = null;
    }
}
