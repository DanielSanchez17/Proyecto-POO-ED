package servicio;

import excepcion.SinConductoresDisponiblesException;
import excepcion.SinConectividadException;
import excepcion.ZonaInexistenteException;
import estructura.GrafoCiudad;
import modelo.Conductor;
import modelo.Solicitud;
import modelo.TipoServicio;
import patron.TaxiCreador;
import patron.TaxiEstandarCreador;
import patron.TaxiMaletasCreador;
import patron.TaxiMascotasCreador;

public class AsignadorServicio {

    // dependencias inyectadas (DIP)
    private GestorConductores gestorConductores;
    private GrafoCiudad grafoCiudad;

    public AsignadorServicio(GestorConductores gestorConductores, GrafoCiudad grafoCiudad) {
        this.gestorConductores = gestorConductores;
        this.grafoCiudad = grafoCiudad;
    }

    public void asignar(Solicitud solicitud)
            throws ZonaInexistenteException,
                   SinConectividadException,
                   SinConductoresDisponiblesException {

        String origen = solicitud.getZonaOrigen();
        String destino = solicitud.getZonaDestino();
        TipoServicio tipo = solicitud.getTipoServicio();

        // 1. verificar que existe ruta habilitada
        if (!grafoCiudad.hayConectividad(origen, destino)) {
            throw new SinConectividadException(origen, destino);
        }

        // 2. calcular distancia en metros
        int distancia = grafoCiudad.calcularDistancia(origen, destino);
        solicitud.setDistanciaMetros(distancia);

        // 3. buscar conductor disponible habilitado para el tipo de servicio
        Conductor conductor = gestorConductores.asignarConductor(tipo);
        if (conductor == null) {
            throw new SinConductoresDisponiblesException(tipo);
        }

        // 4. calcular tarifa usando Factory Method (DIP: no se instancia Taxi directamente)
        TaxiCreador creador = obtenerCreador(tipo);
        double tarifa = creador.calcularTarifa(conductor.getPlaca(), conductor.getNombre(), distancia);

        // 5. actualizar la solicitud con los datos calculados
        solicitud.setConductorAsignado(conductor);
        solicitud.setTarifaCalculada(tarifa);
    }

    private TaxiCreador obtenerCreador(TipoServicio tipo) {
        switch (tipo) {
            case MALETAS:   return new TaxiMaletasCreador();
            case MASCOTAS:  return new TaxiMascotasCreador();
            default:        return new TaxiEstandarCreador();
        }
    }
}
