package excepcion;

import modelo.TipoServicio;

public class SinConductoresDisponiblesException extends Exception {

    public SinConductoresDisponiblesException(TipoServicio tipo) {
        super("No hay conductores disponibles para el servicio: " + tipo);
    }
}
