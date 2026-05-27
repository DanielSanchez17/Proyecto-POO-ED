package servicio;

import estructura.ColaSolicitudes;
import estructura.PilaHistorial;
import excepcion.CancelacionInvalidaException;
import excepcion.SolicitudInvalidaException;
import modelo.Conductor;
import modelo.EstadoSolicitud;
import modelo.Solicitud;
import modelo.TipoServicio;

import java.util.List;

public class GestorSolicitudes {

    private ColaSolicitudes cola;      // solicitudes en espera (FIFO)
    private PilaHistorial historial;   // solicitudes finalizadas/canceladas (LIFO)
    private int contadorId;

    public GestorSolicitudes() {
        cola = new ColaSolicitudes();
        historial = new PilaHistorial();
        contadorId = 1;
    }

    public Solicitud crearSolicitud(String origen, String destino, TipoServicio tipo)
            throws SolicitudInvalidaException {

        if (origen == null || origen.trim().isEmpty()) {
            throw new SolicitudInvalidaException("la zona de origen no puede estar vacia");
        }
        if (destino == null || destino.trim().isEmpty()) {
            throw new SolicitudInvalidaException("la zona de destino no puede estar vacia");
        }
        if (tipo == null) {
            throw new SolicitudInvalidaException("el tipo de servicio es obligatorio");
        }
        if (origen.trim().equalsIgnoreCase(destino.trim())) {
            throw new SolicitudInvalidaException("el origen y el destino no pueden ser iguales");
        }

        Solicitud solicitud = new Solicitud(contadorId++, origen.trim(), destino.trim(), tipo);
        cola.encolar(solicitud);
        return solicitud;
    }

    public Solicitud atenderSiguiente() {
        Solicitud solicitud = cola.desencolar();
        if (solicitud != null) {
            solicitud.setEstado(EstadoSolicitud.EN_ATENCION);
        }
        return solicitud;
    }

    public void cancelarSolicitud(Solicitud solicitud, String motivo)
            throws CancelacionInvalidaException {

        if (solicitud.getEstado() == EstadoSolicitud.FINALIZADA
                || solicitud.getEstado() == EstadoSolicitud.CANCELADA) {
            throw new CancelacionInvalidaException(solicitud.getId());
        }

        cola.eliminarPorId(solicitud.getId());

        solicitud.setEstado(EstadoSolicitud.CANCELADA);
        solicitud.setMotivoCancelacion(motivo);
        historial.push(solicitud);
    }

    public void finalizarSolicitud(Solicitud solicitud, Conductor conductor, double tarifa) {
        solicitud.setConductorAsignado(conductor);
        solicitud.setTarifaCalculada(tarifa);
        solicitud.setEstado(EstadoSolicitud.FINALIZADA);
        historial.push(solicitud); 
    }

    // devuelve la lista de solicitudes actualmente en espera
    public List<Solicitud> getSolicitudesEnEspera() {
        return cola.listar();
    }

    // devuelve el historial completo (mas reciente primero, LIFO)
    public List<Solicitud> getHistorial() {
        return historial.listar();
    }

    public boolean hayEnEspera() {
        return !cola.estaVacia();
    }

    public int getCantidadEnEspera() {
        return cola.getTamanio();
    }

    // busca una solicitud en cola por ID (para operaciones de cancelacion)
    public Solicitud buscarEnColaById(int id) {
        return cola.buscarPorId(id);
    }

    // permite cargar solicitudes del historial desde archivo
    public void cargarAlHistorial(Solicitud solicitud) {
        historial.push(solicitud);
    }
}
