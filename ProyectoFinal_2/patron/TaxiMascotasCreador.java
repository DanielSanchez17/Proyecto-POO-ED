package patron;

import modelo.taxi.Taxi;
import modelo.taxi.TaxiMascotas;

public class TaxiMascotasCreador extends TaxiCreador {

    @Override
    protected Taxi crearTaxi(String placa, String nombreConductor) {
        return new TaxiMascotas(placa, nombreConductor);
    }
}
