package excepcion;

public class ZonaInexistenteException extends Exception {

    public ZonaInexistenteException(String zona) {
        super("La zona '" + zona + "' no existe en la red vial.");
    }
}
