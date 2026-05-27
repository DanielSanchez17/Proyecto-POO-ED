package patron;

import modelo.taxi.Taxi;

public abstract class TaxiCreador {

    protected abstract Taxi crearTaxi(String placa, String nombreConductor);

    public double calcularTarifa(String placa, String nombreConductor, int distancia) {
        Taxi taxi = crearTaxi(placa, nombreConductor);
        return taxi.calcularTarifa(distancia);
    }

    public String getTipoServicio() {
        Taxi taxi = crearTaxi("", "");
        return taxi.getTipo();
    }
}
