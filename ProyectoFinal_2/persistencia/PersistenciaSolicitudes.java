package persistencia;

import modelo.EstadoSolicitud;
import modelo.Solicitud;
import modelo.TipoServicio;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PersistenciaSolicitudes implements IPersistencia<Solicitud> {

    @Override
    public void guardar(List<Solicitud> solicitudes, String rutaArchivo) {
        try {
            PrintWriter escritor = new PrintWriter(new FileWriter(rutaArchivo));

            for (Solicitud s : solicitudes) {
                String conductor = "";

                if (s.getConductorAsignado() != null) {
                    conductor = s.getConductorAsignado().getNombre();
                }

                String motivo = "";

                if (s.getMotivoCancelacion() != null) {
                    motivo = s.getMotivoCancelacion();
                }

                String linea = s.getId() + ";"
                        + s.getZonaOrigen() + ";"
                        + s.getZonaDestino() + ";"
                        + s.getTipoServicio().name() + ";"
                        + s.getEstado().name() + ";"
                        + s.getTarifaCalculada() + ";"
                        + s.getDistanciaMetros() + ";"
                        + conductor + ";"
                        + s.getFechaHora() + ";"
                        + motivo;

                escritor.println(linea);
            }

            escritor.close();

        } catch (Exception e) {
            System.out.println("Error al guardar historial de solicitudes.");
        }
    }

    @Override
    public List<Solicitud> cargar(String rutaArchivo) {
        List<Solicitud> solicitudes = new ArrayList<>();

        try {
            File archivo = new File(rutaArchivo);

            if (!archivo.exists()) {
                return solicitudes;
            }

            Scanner lector = new Scanner(archivo);

            while (lector.hasNextLine()) {
                String linea = lector.nextLine();

                if (!linea.equals("")) {
                    String[] datos = linea.split(";", 10);

                    if (datos.length >= 9) {
                        try {
                            int id = Integer.parseInt(datos[0]);
                            String origen = datos[1];
                            String destino = datos[2];
                            TipoServicio tipo = TipoServicio.valueOf(datos[3]);
                            EstadoSolicitud estado = EstadoSolicitud.valueOf(datos[4]);
                            double tarifa = Double.parseDouble(datos[5]);
                            int distancia = Integer.parseInt(datos[6]);

                            Solicitud solicitud = new Solicitud(id, origen, destino, tipo);

                            solicitud.setEstado(estado);
                            solicitud.setTarifaCalculada(tarifa);
                            solicitud.setDistanciaMetros(distancia);

                            if (datos.length == 10 && !datos[9].equals("")) {
                                solicitud.setMotivoCancelacion(datos[9]);
                            }

                            solicitudes.add(solicitud);

                        } catch (Exception e) {
                            System.out.println("No se pudo cargar una solicitud del archivo.");
                        }
                    }
                }
            }

            lector.close();

        } catch (Exception e) {
            System.out.println("Error al cargar historial de solicitudes.");
        }

        return solicitudes;
    }
}