package consola;

import estructura.GrafoCiudad;
import excepcion.*;
import modelo.*;
import persistencia.PersistenciaConductores;
import persistencia.PersistenciaSolicitudes;
import servicio.AsignadorServicio;
import servicio.GestorConductores;
import servicio.GestorSolicitudes;

import java.util.List;
import java.util.Scanner;


public class MenuPrincipal {

    private static final String ARCHIVO_CONDUCTORES = "conductores.txt";
    private static final String ARCHIVO_HISTORIAL   = "historial.txt";

    private GestorSolicitudes gestorSolicitudes;
    private GestorConductores gestorConductores;
    private AsignadorServicio asignadorServicio;
    private GrafoCiudad grafoCiudad;

    private PersistenciaConductores persistenciaConductores;
    private PersistenciaSolicitudes persistenciaSolicitudes;

    private Scanner scanner;

    private Solicitud solicitudEnCurso;

    public MenuPrincipal() {
        gestorConductores  = new GestorConductores();
        grafoCiudad        = new GrafoCiudad();
        gestorSolicitudes  = new GestorSolicitudes();
        asignadorServicio  = new AsignadorServicio(gestorConductores, grafoCiudad);

        persistenciaConductores = new PersistenciaConductores();
        persistenciaSolicitudes = new PersistenciaSolicitudes();

        scanner = new Scanner(System.in);
    }

    public void ejecutar() {
        inicializarDatos();

        boolean continuar = true;
        while (continuar) {
            mostrarMenu();
            String opcion = scanner.nextLine().trim();

            switch (opcion) {
                case "1": registrarSolicitud();  break;
                case "2": verSolicitudesEspera(); break;
                case "3": atenderSolicitud();     break;
                case "4": cancelarSolicitud();    break;
                case "5": finalizarSolicitud();   break;
                case "6": verHistorial();         break;
                case "7": verConductores();       break;
                case "8": verRedVial();           break;
                case "9": cerrarVia();            break;
                case "0":
                    guardarDatos();
                    System.out.println("\nSistema cerrado. Datos guardados.");
                    continuar = false;
                    break;
                default:
                    System.out.println("Opcion no valida. Intente de nuevo.");
            }
        }
    }

    // ========================== MENU ==========================

    private void mostrarMenu() {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║  COOPERATIVA DE TAXIS MULTIZONA      ║");
        System.out.println("╠══════════════════════════════════════╣");
        System.out.println("║  1. Registrar solicitud              ║");
        System.out.println("║  2. Ver solicitudes en espera        ║");
        System.out.println("║  3. Atender siguiente solicitud      ║");
        System.out.println("║  4. Cancelar solicitud               ║");
        System.out.println("║  5. Finalizar servicio en curso      ║");
        System.out.println("║  6. Ver historial                    ║");
        System.out.println("║  7. Ver conductores                  ║");
        System.out.println("║  8. Ver red vial                     ║");
        System.out.println("║  9. Cierres viales                   ║");
        System.out.println("║  0. Salir                            ║");
        System.out.println("╚══════════════════════════════════════╝");
        if (solicitudEnCurso != null) {
            System.out.println("[!] Servicio en curso: Solicitud #" + solicitudEnCurso.getId());
        }
        System.out.print("Opcion: ");
    }


    private void registrarSolicitud() {
        System.out.println("\n--- NUEVA SOLICITUD ---");
        System.out.println("Zonas disponibles: " + grafoCiudad.getZonas());

        System.out.print("Zona de origen: ");
        String origen = scanner.nextLine().trim();

        System.out.print("Zona de destino: ");
        String destino = scanner.nextLine().trim();

        System.out.println("Tipo de servicio:");
        System.out.println("  1. ESTANDAR");
        System.out.println("  2. MALETAS");
        System.out.println("  3. MASCOTAS");
        System.out.print("Opcion: ");
        String tipoOp = scanner.nextLine().trim();

        TipoServicio tipo;
        switch (tipoOp) {
            case "2": tipo = TipoServicio.MALETAS;  break;
            case "3": tipo = TipoServicio.MASCOTAS; break;
            default:  tipo = TipoServicio.ESTANDAR; break;
        }

        try {
            Solicitud s = gestorSolicitudes.crearSolicitud(origen, destino, tipo);
            System.out.println("Solicitud registrada: " + s);
        } catch (SolicitudInvalidaException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void verSolicitudesEspera() {
        System.out.println("\n--- SOLICITUDES EN ESPERA ---");
        List<Solicitud> lista = gestorSolicitudes.getSolicitudesEnEspera();
        if (lista.isEmpty()) {
            System.out.println("No hay solicitudes en espera.");
        } else {
            System.out.println("Total: " + lista.size());
            for (Solicitud s : lista) {
                System.out.println("  " + s);
            }
        }
    }

    private void atenderSolicitud() {
        if (solicitudEnCurso != null) {
            System.out.println("Ya hay un servicio en curso (#" + solicitudEnCurso.getId()
                    + "). Finalicelo o cancelelo primero.");
            return;
        }

        if (!gestorSolicitudes.hayEnEspera()) {
            System.out.println("No hay solicitudes en espera.");
            return;
        }

        System.out.println("\n--- ATENDIENDO SIGUIENTE SOLICITUD ---");
        Solicitud s = gestorSolicitudes.atenderSiguiente();
        System.out.println("Solicitud extraida: " + s);

        try {
            asignadorServicio.asignar(s);
            solicitudEnCurso = s;
            System.out.println("Conductor asignado: " + s.getConductorAsignado().getNombre());
            System.out.println("Distancia: " + s.getDistanciaMetros() + " metros");
            System.out.printf("Tarifa calculada: $%.0f%n", s.getTarifaCalculada());
            System.out.println("Estado: EN ATENCION");
        } catch (ZonaInexistenteException | SinConectividadException e) {
            System.out.println("Error de ruta: " + e.getMessage());
            try {
                gestorSolicitudes.cancelarSolicitud(s, "Ruta no disponible: " + e.getMessage());
            } catch (CancelacionInvalidaException ex) {
            }
        } catch (SinConductoresDisponiblesException e) {
            System.out.println("Error: " + e.getMessage());
            System.out.println("La solicitud se devuelve a la cola.");
            try {
                gestorSolicitudes.cancelarSolicitud(s, "Sin conductores: " + e.getMessage());
            } catch (CancelacionInvalidaException ex) {
            }
        }
    }

    private void cancelarSolicitud() {
        System.out.println("\n--- CANCELAR SOLICITUD ---");

        if (solicitudEnCurso != null) {
            System.out.println("Solicitud en curso: #" + solicitudEnCurso.getId());
            System.out.print("Cancelar esta solicitud? (s/n): ");
            if (scanner.nextLine().trim().equalsIgnoreCase("s")) {
                System.out.print("Motivo: ");
                String motivo = scanner.nextLine().trim();
                try {
                    if (solicitudEnCurso.getConductorAsignado() != null) {
                        gestorConductores.liberarConductor(solicitudEnCurso.getConductorAsignado());
                    }
                    gestorSolicitudes.cancelarSolicitud(solicitudEnCurso, motivo);
                    System.out.println("Solicitud #" + solicitudEnCurso.getId() + " cancelada.");
                    solicitudEnCurso = null;
                } catch (CancelacionInvalidaException e) {
                    System.out.println("Error: " + e.getMessage());
                }
                return;
            }
        }

        List<Solicitud> espera = gestorSolicitudes.getSolicitudesEnEspera();
        if (espera.isEmpty()) {
            System.out.println("No hay solicitudes en espera para cancelar.");
            return;
        }

        System.out.println("Solicitudes en espera:");
        for (Solicitud s : espera) {
            System.out.println("  " + s);
        }
        System.out.print("ID de la solicitud a cancelar: ");
        try {
            int id = Integer.parseInt(scanner.nextLine().trim());
            Solicitud s = gestorSolicitudes.buscarEnColaById(id);
            if (s == null) {
                System.out.println("No se encontro solicitud con ID " + id + " en la cola.");
                return;
            }
            System.out.print("Motivo: ");
            String motivo = scanner.nextLine().trim();
            gestorSolicitudes.cancelarSolicitud(s, motivo);
            System.out.println("Solicitud #" + id + " cancelada.");
        } catch (NumberFormatException e) {
            System.out.println("ID invalido.");
        } catch (CancelacionInvalidaException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void finalizarSolicitud() {
        if (solicitudEnCurso == null) {
            System.out.println("No hay servicio en curso para finalizar.");
            return;
        }

        System.out.println("\n--- FINALIZAR SERVICIO ---");
        System.out.println("Solicitud: " + solicitudEnCurso);
        System.out.printf("Tarifa a cobrar: $%.0f%n", solicitudEnCurso.getTarifaCalculada());
        System.out.print("Confirmar finalizacion? (s/n): ");

        if (scanner.nextLine().trim().equalsIgnoreCase("s")) {
            Conductor conductor = solicitudEnCurso.getConductorAsignado();
            gestorSolicitudes.finalizarSolicitud(
                    solicitudEnCurso,
                    conductor,
                    solicitudEnCurso.getTarifaCalculada());
            gestorConductores.liberarConductor(conductor);
            System.out.println("Servicio finalizado. Tarifa cobrada: $"
                    + String.format("%.0f", solicitudEnCurso.getTarifaCalculada()));
            solicitudEnCurso = null;
        } else {
            System.out.println("Operacion cancelada.");
        }
    }

    private void verHistorial() {
        System.out.println("\n--- HISTORIAL (mas reciente primero) ---");
        List<Solicitud> historial = gestorSolicitudes.getHistorial();
        if (historial.isEmpty()) {
            System.out.println("El historial esta vacio.");
        } else {
            System.out.println("Total de registros: " + historial.size());
            for (Solicitud s : historial) {
                System.out.println("  " + s);
                if (s.getMotivoCancelacion() != null && !s.getMotivoCancelacion().isEmpty()) {
                    System.out.println("    Motivo cancelacion: " + s.getMotivoCancelacion());
                }
            }
        }
    }

    private void verConductores() {
        System.out.println("\n--- CONDUCTORES REGISTRADOS ---");
        List<Conductor> conductores = gestorConductores.listarConductores();
        if (conductores.isEmpty()) {
            System.out.println("No hay conductores registrados.");
        } else {
            for (Conductor c : conductores) {
                System.out.println("  " + c);
            }
        }
    }

    private void verRedVial() {
        System.out.println();
        grafoCiudad.mostrarRed();
    }

    private void cerrarVia() {
        System.out.println("\n--- CIERRES VIALES ---");
        System.out.println("Zonas disponibles: " + grafoCiudad.getZonas());
        System.out.print("Zona A: ");
        String zonaA = scanner.nextLine().trim();
        System.out.print("Zona B: ");
        String zonaB = scanner.nextLine().trim();
        System.out.print("Habilitar (1) o Cerrar (2): ");
        String accion = scanner.nextLine().trim();

        boolean habilitada = accion.equals("1");
        try {
            grafoCiudad.setConexionHabilitada(zonaA, zonaB, habilitada);
            System.out.println("Conexion " + zonaA + " <-> " + zonaB
                    + (habilitada ? " HABILITADA." : " CERRADA."));
        } catch (ZonaInexistenteException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    private void inicializarDatos() {
        String[] zonas = {"Centro", "Norte", "Sur", "Aeropuerto", "Estadio"};
        for (String z : zonas) {
            grafoCiudad.agregarZona(z);
        }

        try {
            grafoCiudad.agregarConexion("Centro",     "Norte",      2000);
            grafoCiudad.agregarConexion("Centro",     "Sur",        1500);
            grafoCiudad.agregarConexion("Centro",     "Aeropuerto", 4500);
            grafoCiudad.agregarConexion("Norte",      "Aeropuerto", 3000);
            grafoCiudad.agregarConexion("Norte",      "Estadio",    1800);
            grafoCiudad.agregarConexion("Sur",        "Estadio",    2200);
            grafoCiudad.agregarConexion("Aeropuerto", "Estadio",    5000);
        } catch (ZonaInexistenteException e) {
            System.err.println("Error al configurar red vial: " + e.getMessage());
        }

        List<Conductor> conductoresCargados = persistenciaConductores.cargar(ARCHIVO_CONDUCTORES);

        if (conductoresCargados.isEmpty()) {
            Conductor c1 = new Conductor("C001", "Carlos", "ABC-123");
            c1.habilitarServicio(TipoServicio.ESTANDAR);
            c1.habilitarServicio(TipoServicio.MALETAS);

            Conductor c2 = new Conductor("C002", "Maria", "DEF-456");
            c2.habilitarServicio(TipoServicio.MASCOTAS);
            c2.habilitarServicio(TipoServicio.ESTANDAR);

            Conductor c3 = new Conductor("C003", "Juan", "GHI-789");
            c3.habilitarServicio(TipoServicio.ESTANDAR);
            c3.habilitarServicio(TipoServicio.MALETAS);
            c3.habilitarServicio(TipoServicio.MASCOTAS);

            gestorConductores.registrarConductor(c1);
            gestorConductores.registrarConductor(c2);
            gestorConductores.registrarConductor(c3);
        } else {
            for (Conductor c : conductoresCargados) {
                gestorConductores.registrarConductor(c);
            }
            System.out.println("Conductores cargados desde archivo: " + conductoresCargados.size());
        }

        List<modelo.Solicitud> historialCargado = persistenciaSolicitudes.cargar(ARCHIVO_HISTORIAL);
        for (modelo.Solicitud s : historialCargado) {
            gestorSolicitudes.cargarAlHistorial(s);
        }
        if (!historialCargado.isEmpty()) {
            System.out.println("Historial cargado: " + historialCargado.size() + " registros.");
        }

        System.out.println("Sistema iniciado. Red vial configurada con " + grafoCiudad.getZonas().size() + " zonas.");
    }

    private void guardarDatos() {
        persistenciaConductores.guardar(gestorConductores.listarConductores(), ARCHIVO_CONDUCTORES);
        persistenciaSolicitudes.guardar(gestorSolicitudes.getHistorial(), ARCHIVO_HISTORIAL);
    }
}
