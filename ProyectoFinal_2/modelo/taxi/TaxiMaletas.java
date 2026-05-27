package modelo.taxi;

public class TaxiMaletas extends Taxi {

    private static final double RECARGO_MALETAS = 3000.0;

    public TaxiMaletas(String placa, String nombreConductor) {
        super(placa, nombreConductor);
    }

    @Override
    public double calcularTarifa(int distancia) {
        return TARIFA_BASE + RECARGO_MALETAS + (distancia * 1000.0);
    }

    @Override
    public String getTipo() {
        return "MALETAS";
    }
}
