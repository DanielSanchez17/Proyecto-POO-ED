package excepcion;

public class SinConectividadException extends Exception {

    public SinConectividadException(String origen, String destino) {
        super("No existe ruta habilitada entre '" + origen + "' y '" + destino + "'.");
    }
}
