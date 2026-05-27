package modelo;

import java.util.ArrayList;
import java.util.List;

public class Conductor {

    private String id;
    private String nombre;
    private String placa;
    private boolean disponible;

    private List<TipoServicio> serviciosHabilitados;

    public Conductor(String id, String nombre, String placa) {
        this.id = id;
        this.nombre = nombre;
        this.placa = placa;
        this.disponible = true;
        this.serviciosHabilitados = new ArrayList<>();
    }

    public void habilitarServicio(TipoServicio tipo) {
        if (!serviciosHabilitados.contains(tipo)) {
            serviciosHabilitados.add(tipo);
        }
    }

    public boolean estaHabilitadoPara(TipoServicio tipo) {
        return serviciosHabilitados.contains(tipo);
    }


    public String getId() { return id; }

    public String getNombre() { return nombre; }

    public String getPlaca() { return placa; }

    public boolean isDisponible() { return disponible; }

    public void setDisponible(boolean disponible) { this.disponible = disponible; }

    public List<TipoServicio> getServiciosHabilitados() {
        return new ArrayList<>(serviciosHabilitados); 
    }

    @Override
    public String toString() {
        return String.format("[%s] %s | Placa: %s | Disponible: %s | Servicios: %s",
                id, nombre, placa, disponible ? "Si" : "No", serviciosHabilitados);
    }
}
