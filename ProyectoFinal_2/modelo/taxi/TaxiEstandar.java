package modelo.taxi;

public class TaxiEstandar extends Taxi {

    public TaxiEstandar(String placa, String nombreConductor) {
        super(placa, nombreConductor);
    }

    @Override
    public double calcularTarifa(int distancia) {
        return TARIFA_BASE + (distancia * 800.0);
    }

    @Override
    public String getTipo() {
        return "ESTANDAR";
    }
}
