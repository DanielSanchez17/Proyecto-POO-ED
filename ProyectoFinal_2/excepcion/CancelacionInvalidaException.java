package excepcion;

public class CancelacionInvalidaException extends Exception {

    public CancelacionInvalidaException(int idSolicitud) {
        super("No se puede cancelar la solicitud #" + idSolicitud
                + ": ya fue finalizada o cancelada.");
    }
}
