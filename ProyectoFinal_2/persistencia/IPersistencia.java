package persistencia;

import java.util.List;

public interface IPersistencia<T> {

    void guardar(List<T> elementos, String rutaArchivo);
    List<T> cargar(String rutaArchivo);
}
