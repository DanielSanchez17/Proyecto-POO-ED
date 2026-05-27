package patron;

import modelo.taxi.Taxi;
import modelo.taxi.TaxiMaletas;

public class TaxiMaletasCreador extends TaxiCreador {

    @Override
    protected Taxi crearTaxi(String placa, String nombreConductor) {
        return new TaxiMaletas(placa, nombreConductor);
    }
}
