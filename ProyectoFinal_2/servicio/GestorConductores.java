package servicio;

import estructura.ListaConductores;
import modelo.Conductor;
import modelo.TipoServicio;

import java.util.List;

public class GestorConductores {

    private ListaConductores listaConductores;

    public GestorConductores() {
        listaConductores = new ListaConductores();
    }

    public void registrarConductor(Conductor conductor) {
        listaConductores.agregar(conductor);
        System.out.println("Conductor registrado: " + conductor.getNombre());
    }

    public Conductor asignarConductor(TipoServicio tipo) {
        Conductor conductor = listaConductores.buscarDisponiblePara(tipo);
        if (conductor != null) {
            conductor.setDisponible(false); 
        }
        return conductor;
    }

    // libera al conductor cuando el servicio termina o se cancela
    public void liberarConductor(Conductor conductor) {
        if (conductor != null) {
            conductor.setDisponible(true);
        }
    }

    // devuelve todos los conductores para mostrar o persistir
    public List<Conductor> listarConductores() {
        return listaConductores.listar();
    }

    // busca conductor por ID (usado al cargar desde archivo)
    public Conductor buscarPorId(String id) {
        return listaConductores.buscarPorId(id);
    }

    public boolean hayDisponibles(TipoServicio tipo) {
        return listaConductores.buscarDisponiblePara(tipo) != null;
    }
}
