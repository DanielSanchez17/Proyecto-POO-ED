package patron;

import modelo.taxi.Taxi;
import modelo.taxi.TaxiEstandar;

public class TaxiEstandarCreador extends TaxiCreador {

    @Override
    protected Taxi crearTaxi(String placa, String nombreConductor) {
        return new TaxiEstandar(placa, nombreConductor);
    }
}
