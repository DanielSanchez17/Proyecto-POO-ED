package modelo.taxi;

public class TaxiMascotas extends Taxi {

    private static final double RECARGO_MASCOTAS = 5000.0;

    public TaxiMascotas(String placa, String nombreConductor) {
        super(placa, nombreConductor);
    }

    @Override
    public double calcularTarifa(int distancia) {
        return TARIFA_BASE + RECARGO_MASCOTAS + (distancia * 1200.0);
    }

    @Override
    public String getTipo() {
        return "MASCOTAS";
    }
}
