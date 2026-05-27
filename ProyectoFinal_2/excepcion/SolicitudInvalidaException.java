package excepcion;

public class SolicitudInvalidaException extends Exception {

    public SolicitudInvalidaException(String motivo) {
        super("Solicitud invalida: " + motivo);
    }
}
