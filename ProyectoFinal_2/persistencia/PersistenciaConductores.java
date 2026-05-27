package persistencia;

import modelo.Conductor;
import modelo.TipoServicio;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PersistenciaConductores implements IPersistencia<Conductor> {

    @Override
    public void guardar(List<Conductor> conductores, String rutaArchivo) {
        try {
            PrintWriter escritor = new PrintWriter(new FileWriter(rutaArchivo));

            for (Conductor c : conductores) {
                String servicios = "";

                for (int i = 0; i < c.getServiciosHabilitados().size(); i++) {
                    servicios += c.getServiciosHabilitados().get(i).name();

                    if (i < c.getServiciosHabilitados().size() - 1) {
                        servicios += ",";
                    }
                }

                String linea = c.getId() + ";" +
                        c.getNombre() + ";" +
                        c.getPlaca() + ";" +
                        c.isDisponible() + ";" +
                        servicios;

                escritor.println(linea);
            }

            escritor.close();

        } catch (Exception e) {
            System.out.println("Error al guardar conductores.");
        }
    }

    @Override
    public List<Conductor> cargar(String rutaArchivo) {
        List<Conductor> conductores = new ArrayList<>();

        try {
            File archivo = new File(rutaArchivo);

            if (!archivo.exists()) {
                return conductores;
            }

            Scanner lector = new Scanner(archivo);

            while (lector.hasNextLine()) {
                String linea = lector.nextLine();
                String[] datos = linea.split(";");

                if (datos.length >= 5) {
                    String id = datos[0];
                    String nombre = datos[1];
                    String placa = datos[2];
                    boolean disponible = Boolean.parseBoolean(datos[3]);

                    Conductor conductor = new Conductor(id, nombre, placa);
                    conductor.setDisponible(disponible);

                    String[] servicios = datos[4].split(",");

                    for (String servicio : servicios) {
                        if (!servicio.equals("")) {
                            try {
                                TipoServicio tipo = TipoServicio.valueOf(servicio);
                                conductor.habilitarServicio(tipo);
                            } catch (Exception e) {
                                System.out.println("Servicio no válido: " + servicio);
                            }
                        }
                    }

                    conductores.add(conductor);
                }
            }

            lector.close();

        } catch (Exception e) {
            System.out.println("Error al cargar conductores.");
        }

        return conductores;
    }
}