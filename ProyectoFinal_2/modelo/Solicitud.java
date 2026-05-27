package modelo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Solicitud {

    private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private int id;
    private String zonaOrigen;
    private String zonaDestino;
    private TipoServicio tipoServicio;
    private EstadoSolicitud estado;
    private Conductor conductorAsignado;  
    private double tarifaCalculada;       
    private int distanciaMetros;
    private String fechaHora;
    private String motivoCancelacion;     

    public Solicitud(int id, String zonaOrigen, String zonaDestino, TipoServicio tipoServicio) {
        this.id = id;
        this.zonaOrigen = zonaOrigen;
        this.zonaDestino = zonaDestino;
        this.tipoServicio = tipoServicio;
        this.estado = EstadoSolicitud.EN_ESPERA;
        this.fechaHora = LocalDateTime.now().format(FORMATO);
    }


    public int getId() { return id; }

    public String getZonaOrigen() { return zonaOrigen; }

    public String getZonaDestino() { return zonaDestino; }

    public TipoServicio getTipoServicio() { return tipoServicio; }

    public EstadoSolicitud getEstado() { return estado; }

    public Conductor getConductorAsignado() { return conductorAsignado; }

    public double getTarifaCalculada() { return tarifaCalculada; }

    public int getDistanciaMetros() { return distanciaMetros; }

    public String getFechaHora() { return fechaHora; }

    public String getMotivoCancelacion() { return motivoCancelacion; }

    public void setEstado(EstadoSolicitud estado) { this.estado = estado; }

    public void setConductorAsignado(Conductor conductor) { this.conductorAsignado = conductor; }

    public void setTarifaCalculada(double tarifa) { this.tarifaCalculada = tarifa; }

    public void setDistanciaMetros(int distancia) { this.distanciaMetros = distancia; }

    public void setMotivoCancelacion(String motivo) { this.motivoCancelacion = motivo; }

    @Override
    public String toString() {
        String conductor = (conductorAsignado != null) ? conductorAsignado.getNombre() : "Sin asignar";
        String tarifa = (tarifaCalculada > 0) ? String.format("$%.0f", tarifaCalculada) : "Pendiente";
        return String.format(
                "#%d | %s -> %s | Tipo: %s | Estado: %s | Conductor: %s | Tarifa: %s | Fecha: %s",
                id, zonaOrigen, zonaDestino, tipoServicio, estado, conductor, tarifa, fechaHora);
    }
}
