package excepcion;

import modelo.TipoServicio;

public class ConductorNoHabilitadoException extends Exception {

    public ConductorNoHabilitadoException(String conductor, TipoServicio tipo) {
        super("El conductor '" + conductor + "' no esta habilitado para servicio: " + tipo);
    }
}
